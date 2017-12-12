package projetoccp;

import org.junit.Before;
import org.junit.Test;

import projeto.ccp.model.Parceiro;
import projeto.ccp.services.ParceiroService;

public class TestaParceiroService {

	ParceiroService pcoservice;
	Parceiro pco;
	
	@Before
	public void setUp(){
		pcoservice = new ParceiroService();
		pco  = new Parceiro("48475689852", "Red Hat", "Patrick Del Bello", "SA", "20470879", "pdelbell@redhat.com");
	}

}
