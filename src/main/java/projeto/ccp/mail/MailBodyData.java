package projeto.ccp.mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MailBodyData {
	Date data = new Date();
	String criaMensagemParaParceiro(JSONObject parceiroJson, JSONArray acreditacoesJson) {

		String mensagem = "Olá " + parceiroJson.getString("nome_completo")
				+ ", \n\n Segue abaixo os dados referentes ao seu novo cadastro no Projeto de Capacitação de Parceiros:  "
				+ "\n\n Acreditacoes selecionadas: \n\n ";

		mensagem += ordenaAcreditacoes(acreditacoesJson);

		mensagem += ("\n Obrigado por registrar seu plano de carreira, \n Sistema de Cadastros");
		return mensagem;
	}

	String criaMensagemParaFuncionario(JSONArray acreditacoes, JSONObject parceiro) {
		String mensagem = "Olá Alexandre, \n\nHouve um novo cadastro na plataforma de capacitação de parceiros. \nO parceiro "
				+ parceiro.getString("nome_completo") + " (e-mail: " + parceiro.getString("email")
				+ ") se inscreveu para as seguintes acreditações: \n";

		mensagem += ordenaAcreditacoes(acreditacoes);
		mensagem += "\n\n [LOG - " + data + "]\n";
		return mensagem;
	}

	private String ordenaAcreditacoes(JSONArray acreditacoes) {
		JSONArray arrayOrdenado = new JSONArray();
		List<JSONObject> lista = new ArrayList<JSONObject>();
		String relatorioAcreditacoes = "";
		for (int i = 0; i < acreditacoes.length(); i++) {
			lista.add(acreditacoes.getJSONObject(i));
		}

		Collections.sort(lista, new Comparator<JSONObject>() {

			public int compare(JSONObject a, JSONObject b) {
				String valorA = new String();
				String valorB = new String();

				try {
					valorA = (String) a.get("nome_perfil");
					valorB = (String) b.get("nome_perfil");
				} catch (JSONException e) {

				}
				return valorA.compareTo(valorB);
			}

		});

		for (int i = 0; i < acreditacoes.length(); i++) {
			arrayOrdenado.put(lista.get(i));
		}
		
		relatorioAcreditacoes += adicionaAcreditacoes("Vendas", arrayOrdenado);
		relatorioAcreditacoes += adicionaAcreditacoes("Pré-Vendas", arrayOrdenado);
		relatorioAcreditacoes += adicionaAcreditacoes("Entrega de Serviços", arrayOrdenado);
		
		relatorioAcreditacoes += "\n\n";
		return relatorioAcreditacoes;
	}

	private String adicionaAcreditacoes(String perfil, JSONArray arrayOrdenado) {
		String resposta = "";
		int cont = 0;

		for (int i = 0; i < arrayOrdenado.length(); i++) {
			if (arrayOrdenado.getJSONObject(i).getString("nome_perfil").equals(perfil)) {
				cont++;
				if (cont == 1) {
					resposta += "\n\nAcreditações de " + perfil;
				}
				resposta += "\n\t - Nome da Acreditação: "
						+ arrayOrdenado.getJSONObject(i).getString("nome_acreditacao");
			}
		}

		return resposta;
	}
}