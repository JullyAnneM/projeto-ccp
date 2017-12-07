package projeto.ccp.services;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import projeto.ccp.dao.RelatorioDAO;
import projeto.ccp.model.Relatorio;

public class RelatorioService {

	@Inject
	RelatorioDAO relatorioDao;

	@Produces
	@RequestScoped
	public void insere(Relatorio relatorio) throws PersistenceException {
		Date data = new Date();
		relatorio.setDataInscricao(data);
		relatorioDao.insere(relatorio);
	}
	

}
