package projeto.ccp.rest;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class RestClientJDVAPI {
	String url = "http://localhost:5000";
	public static void main(String[] args) throws Exception {
		RestClientJDVAPI rc = new RestClientJDVAPI();
		rc.getCursosFromAPI("AC13");
		rc.getProdutosFromAPI("C33");
		
	}

	public JSONArray getCursosFromAPI(String acreditacaoId) {
		String saida = "";
		JSONArray cursos = new JSONArray();
		try {
			String path = url + "/vdb/Dados/json/CursosDeAcreditacao/" + acreditacaoId;
			ClientRequest request = new ClientRequest(path);

			request.accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			ClientResponse<String> response = request.get(String.class);

			int apiResponseCode = response.getResponseStatus().getStatusCode();
			if (response.getResponseStatus().getStatusCode() != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + apiResponseCode);
			}

			saida = response.getEntity(String.class);
			saida = saida.substring(11);
			cursos = new JSONArray(saida);
		} catch (Exception e) {

		}
		return cursos;
	}

	public JSONArray getProdutosFromAPI(String cursoId) {
		String saida = "";
		JSONArray produtos = new JSONArray();
		JSONObject produto = new JSONObject();
		try {
			String path = url + "/vdb/Dados/json/ProdutosDeCursos/" + cursoId;
			ClientRequest request = new ClientRequest(path);

			request.accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			ClientResponse<String> response = request.get(String.class);

			int apiResponseCode = response.getResponseStatus().getStatusCode();
			if (response.getResponseStatus().getStatusCode() != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + apiResponseCode);
			}

			saida = response.getEntity(String.class);
			saida = saida.substring(12);
			if (saida.charAt(1) == '{') {
				produto = new JSONObject(saida);
				produtos.put(produto);
			} else {
				produtos = new JSONArray(saida);
			}

		} catch (Exception e) {

		}
		return produtos;

	}

}