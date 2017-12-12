package projetoccp;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import projeto.ccp.rest.RestClientJDVAPI;

public class TestaRestClient {

	RestClientJDVAPI cliente;
	JSONArray cursosCliente;
	JSONArray produtosCliente;
	
	@Before 
	public void setUp(){
		cliente = new RestClientJDVAPI();
	}
	
	@Test
	public void testeCursosDaAPI(){
		cursosCliente = cliente.getCursosFromAPI("AC58");
		Assert.assertTrue(cursosCliente.length()> 0);
	}
	
	@Test
	public void testaFalhaCursosDaAPI(){
		 cursosCliente = cliente.getCursosFromAPI("P300");
		Assert.assertFalse(cursosCliente.length() > 0);
	}
	
	@Test
	public void testaProdutosDaAPI(){
		produtosCliente = cliente.getProdutosFromAPI("C58");
		Assert.assertTrue(produtosCliente.length() > 0);
	}	
	
	@Test
	public void testaFalhaProdutosDaAPI(){
		produtosCliente = cliente.getProdutosFromAPI("C580");
		Assert.assertFalse(produtosCliente.length() > 0);
	}		
	
	
	
}
