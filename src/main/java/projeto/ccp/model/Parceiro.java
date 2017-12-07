package projeto.ccp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Parceiro")
public class Parceiro {

	@Id
	private String cpf_parceiro;
	private String nome_empresa;
	private String nome_completo;
	private String cargo;
	private String telefone;
	private String email;
	private Date data_cadastro;
	private Date data_atualizacao;

	
	public Parceiro(){
		
	}
	
	public Parceiro(String cpf_parceiro, String nome_empresa, String nome_completo, String cargo, String telefone,
			String email) {
		super();
		this.cpf_parceiro = cpf_parceiro;
		this.nome_empresa = nome_empresa;
		this.nome_completo = nome_completo;
		this.cargo = cargo;
		this.telefone = telefone;
		this.email = email;
	}



	public Parceiro(String cpf_parceiro, String nome_empresa, String nome_completo, String cargo, String telefone,
			String email, Date data_cadastro, Date data_atualizacao) {
		super();
		this.cpf_parceiro = cpf_parceiro;
		this.nome_empresa = nome_empresa;
		this.nome_completo = nome_completo;
		this.cargo = cargo;
		this.telefone = telefone;
		this.email = email;
		this.data_cadastro = data_cadastro;
		this.data_atualizacao = data_atualizacao;
	}



	public String getCpf_parceiro() {
		return cpf_parceiro;
	}



	public void setCpf_parceiro(String cpf_parceiro) {
		this.cpf_parceiro = cpf_parceiro;
	}



	public String getNome_empresa() {
		return nome_empresa;
	}



	public void setNome_empresa(String nome_empresa) {
		this.nome_empresa = nome_empresa;
	}



	public String getNome_completo() {
		return nome_completo;
	}



	public void setNome_completo(String nome_completo) {
		this.nome_completo = nome_completo;
	}



	public String getCargo() {
		return cargo;
	}



	public void setCargo(String cargo) {
		this.cargo = cargo;
	}



	public String getTelefone() {
		return telefone;
	}



	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Date getData_cadastro() {
		return data_cadastro;
	}



	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}



	public Date getData_atualizacao() {
		return data_atualizacao;
	}



	public void setData_atualizacao(Date data_atualizacao) {
		this.data_atualizacao = data_atualizacao;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Parceiro: " + this.nome_completo + this.cargo + this.cpf_parceiro + this.telefone + this.email + this.nome_empresa ;
	}

}
