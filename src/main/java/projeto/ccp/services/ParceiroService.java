package projeto.ccp.services;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import projeto.ccp.dao.ParceiroDAO;
import projeto.ccp.model.Parceiro;

public class ParceiroService {
	private static Logger LOG = LoggerFactory.getLogger(ParceiroService.class);

	@Inject
	ParceiroDAO parceiroDao;

	Date hoje = new Date();

	@Produces
	@RequestScoped
	public void insere(Parceiro parceiro) throws Exception {
		LOG.info("Iniciando registro no sistema. Registro de: " + parceiro.getNome_completo());
		Parceiro pco = new Parceiro();
		pco = parceiroDao.findById(parceiro.getCpf_parceiro());
		
		try {
			if (pco == null) {
				parceiro.setData_cadastro(hoje);
				parceiro.setData_atualizacao(hoje);
				parceiroDao.insere(parceiro);
				LOG.info(parceiro.getNome_completo() + " foi adicionado ao sistema.");
			} else {
				parceiro.setData_cadastro(pco.getData_cadastro());
				parceiro.setData_atualizacao(hoje);
				parceiroDao.update(parceiro);
				LOG.info(parceiro.getNome_completo() + " teve seus dados atualizados");
			}
		} catch (Exception e) {
			LOG.error("Houve erro no ParceiroService" + e.getMessage());
		}
	}
	
	

}
