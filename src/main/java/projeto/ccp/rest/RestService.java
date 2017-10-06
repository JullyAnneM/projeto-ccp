package projeto.ccp.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("parceiro")
public class RestService {
	
	@GET
	@Path("oi")
	public String retornaOi() { 
		return "Oi";
	}

}
