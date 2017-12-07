package projeto.ccp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Relatorio")
public class Relatorio {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_relatorio")
	private Integer idRelatorio;

	@Column(name="nome_acreditacao")
	private String nomeAcreditacao;
	
	@Column(name="nome_perfil")
	private String nomePerfil;
	
	@Column(name="nome_curso")
	private String nomeCurso;

	@Column(name="nome_produto")
	private String nomeProduto;
	
	@Column(name="cpf_parceiro")
	private String cpfParceiro;

	@Column(name="data_inscricao")
	private Date dataInscricao;

	public Integer getIdRelatorio() {
		return idRelatorio;
	}

	public void setIdRelatorio(Integer idRelatorio) {
		this.idRelatorio = idRelatorio;
	}

	public String getNomeAcreditacao() {
		return nomeAcreditacao;
	}

	public void setNomeAcreditacao(String nomeAcreditacao) {
		this.nomeAcreditacao = nomeAcreditacao;
	}

	public String getNomePerfil() {
		return nomePerfil;
	}

	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getCpfParceiro() {
		return cpfParceiro;
	}

	public void setCpfParceiro(String cpfParceiro) {
		this.cpfParceiro = cpfParceiro;
	}

	public Date getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nomeAcreditacao + "\n" + this.nomeCurso + "\n" + this.nomeProduto + "\n" + this.nomePerfil + "\n" + this.cpfParceiro + "\n" + this.dataInscricao;
	}
}
