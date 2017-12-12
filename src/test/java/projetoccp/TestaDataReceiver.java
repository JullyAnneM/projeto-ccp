package projetoccp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import projeto.ccp.mail.DataReceiver;
import projeto.ccp.model.Parceiro;

public class TestaDataReceiver {

	String mensagem = "";
	String email = "pdelbell@redhat.com";
	DataReceiver dr;
	String pcoToParse = "{'telefone': '20470873', 'nome_completo': 'Patrick Del Bello', 'cpf_parceiro': '4846448192', 'nome_empresa': 'Red Hat', 'cargo': 'SA', 'email': 'pdelbell@redhat.com'}";
	String acrToParse = "[{'nome_perfil': 'Vendas', 'id_perfil': 'P1', 'id_acreditacao': 'AC18', 'nome_acreditacao':'Red Hat Sales Specialist - How to sell Ansible Tower by Red Hat'}]";
	JSONObject objPco;
	JSONArray arrayAcr;
	Parceiro pco;
	
	
	@Before
	public void setUp(){
		dr = new DataReceiver();
		objPco = new JSONObject(pcoToParse);
		arrayAcr = new JSONArray(acrToParse);
		System.out.println(arrayAcr);
	}

	
	@Test
	public void testaCriaParceiro(){
		pco = dr.criaParceiro(objPco);
		Assert.assertEquals(pco.getNome_completo(), "Patrick Del Bello");
	}
	
	
	
	
}
