package projeto.ccp.mail;

import java.io.BufferedReader;
import java.io.IOException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.PersistenceException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import projeto.ccp.model.Parceiro;
import projeto.ccp.model.Relatorio;
import projeto.ccp.rest.RestClientJDVAPI;
import projeto.ccp.services.ParceiroService;
import projeto.ccp.services.RelatorioService;

@WebServlet(value = "/receiver")
public class DataReceiver extends HttpServlet {

	@Resource(mappedName = "java:jboss/mail/projetoccp")
	public Session mailSession;

	private static final long serialVersionUID = 1L;
	private static Logger LOG = LoggerFactory.getLogger(DataReceiver.class);
	private MailBodyData corpo = new MailBodyData();

	@Inject
	private ParceiroService ps;

	@Inject
	private RelatorioService rs;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		{
			LOG.info("Novo cadastro realizado, lendo conteúdo recebido...");
			String[] dadosSeparados = retornaBodyRequest(request);
			LOG.info("Informações carregadas com sucesso.");

			String mensagem = "";
			String parceiro = dadosSeparados[0];
			String acreditacoes = dadosSeparados[1];
			String emailFuncionario = "pdelbell@redhat.com";

			JSONObject parceiroJson = new JSONObject(parceiro);
			JSONArray acreditacoesJson = new JSONArray(acreditacoes);

			Parceiro pco = criaParceiro(parceiroJson);

			try {
				LOG.info("Verificando status parceiro");
				ps.insere(pco);
				
				LOG.info("Verificando status acreditacoes");

				response.setStatus(insereAcreditacoes(acreditacoesJson, pco));
				if (response.getStatus() == 201) {
					geraEmail(parceiroJson, acreditacoesJson, mensagem, emailFuncionario);
				} else {
					LOG.info("E-mail não gerado");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

	private String[] retornaBodyRequest(HttpServletRequest request) throws IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String dados = buffer.toString();
		String[] dadosSeparados = dados.split("//");
		return dadosSeparados;
	}

	private Parceiro criaParceiro(JSONObject obj) {
		Parceiro parceiro = new Parceiro();
		parceiro.setCpf_parceiro(obj.getString("cpf_parceiro"));
		parceiro.setNome_completo(obj.getString("nome_completo"));
		parceiro.setCargo(obj.getString("cargo"));
		parceiro.setEmail(obj.getString("email"));
		parceiro.setNome_empresa(obj.getString("nome_empresa"));
		parceiro.setTelefone(obj.getString("telefone"));
		return parceiro;
	}

	public void geraEmail(JSONObject parceiroJson, JSONArray acreditacoesJson,
			String mensagem, String emailFuncionario) throws IOException {

		mensagem = corpo.criaMensagemParaParceiro(parceiroJson, acreditacoesJson);
		LOG.info("Enviando mensagem para parceiro.");
		enviaEmail(parceiroJson.getString("email"), mensagem);
		LOG.info("Mensagem para parceiro enviada.");

		mensagem = corpo.criaMensagemParaFuncionario(acreditacoesJson, parceiroJson);
		LOG.info("Enviando mensagem para o Alexandre sobre o novo registro.");
		enviaEmail(emailFuncionario, mensagem);
		LOG.info("Mensagem para Alexandre enviada.");

	}

	private void enviaEmail(String destinatario, String mensagem) throws IOException {

		try {
			MimeMessage m = new MimeMessage(mailSession);
			Address from = new InternetAddress("parceiro.redhat@gmail.com");
			Address[] to = new InternetAddress[] { new InternetAddress(destinatario) };

			m.setFrom(from);
			m.setRecipients(Message.RecipientType.TO, to);
			m.setSubject("Novo Cadastro na Plataforma de Capacitação de Parceiros!");
			m.setSentDate(new java.util.Date());
			m.setContent(mensagem, "text/plain");
			Transport.send(m);

		} catch (javax.mail.MessagingException e) {
			e.printStackTrace();
			LOG.error("Houve um erro ao encaminhar e-mail para " + destinatario);
		}

	}

	private Integer insereAcreditacoes(JSONArray acreditacoes, Parceiro pco) {
		RestClientJDVAPI rc = new RestClientJDVAPI();
		LOG.info("Iniciando geração de relatórios");
		int status = 201;
		try {
			for (int i = 0; i < acreditacoes.length(); i++) {
				JSONArray cursos = rc.getCursosFromAPI(acreditacoes.getJSONObject(i).getString("id_acreditacao"));
				if (cursos.length() > 0) {
					for (int j = 0; j < cursos.length(); j++) {

						JSONArray produtos = rc.getProdutosFromAPI(cursos.getJSONObject(j).getString("id_curso"));

						LOG.info("No curso " + cursos.getJSONObject(j).getString("nome_curso") + " Há "
								+ produtos.length() + " produtos");
						if (produtos.length() > 0) {
						} else {
							status = 400;
							throw new PersistenceException(" Um curso não contém produtos relacionados.");
						}
					}
				} else {
					status = 400;
					throw new PersistenceException("Uma acreditação não contém cursos.");
				}

			}
		} catch (PersistenceException e) {
			LOG.error("Houve um erro ", e);
		} finally {
			LOG.info("Checagem de acreditacoes concluida, resultado: " + status);
		}
		
		
		if (status == 201) {
			try {
				for (int i = 0; i < acreditacoes.length(); i++) {
					JSONArray cursos = rc.getCursosFromAPI(acreditacoes.getJSONObject(i).getString("id_acreditacao"));
					if (cursos.length() > 0) {
						for (int j = 0; j < cursos.length(); j++) {
							JSONArray produtos = rc.getProdutosFromAPI(cursos.getJSONObject(j).getString("id_curso"));
							if (produtos.length() > 0) {
								for (int k = 0; k < produtos.length(); k++) {
									Relatorio relatorio = new Relatorio();
									relatorio.setCpfParceiro(pco.getCpf_parceiro());
									relatorio.setNomeAcreditacao(
											acreditacoes.getJSONObject(i).getString("nome_acreditacao"));
									relatorio.setNomePerfil(acreditacoes.getJSONObject(i).getString("nome_perfil"));
									relatorio.setNomeProduto(produtos.getJSONObject(k).getString("nome_produto"));
									relatorio.setNomeCurso(cursos.getJSONObject(j).getString("nome_curso"));
									rs.insere(relatorio);
								}
							} 
						}
					} 
				}
				LOG.info("Inserção no banco ok, prosseguindo com e-mail. ");
			} catch (PersistenceException e) {
				LOG.error("Houve um erro ", e);
				status = 400;
			}
		}
		return status;

	}
}