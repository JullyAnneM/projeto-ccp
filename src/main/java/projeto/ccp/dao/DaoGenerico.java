package projeto.ccp.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Transactional
public class DaoGenerico<ENTIDADE> {

    private static Logger LOG = LoggerFactory.getLogger(DaoGenerico.class);

	@PersistenceContext(unitName = "projeto-ccp", type = PersistenceContextType.EXTENDED)
    public EntityManager em;
	private Class<ENTIDADE> classeEntidade;
	private String nomeClasseEntidade;

	public DaoGenerico() {
		if (classeEntidade == null) {
			Type type = getClass().getGenericSuperclass();
			if (!(type instanceof ParameterizedType)) {
				type = getClass().getSuperclass().getGenericSuperclass();
			}
			
			ParameterizedType paramType = (ParameterizedType) type;
			classeEntidade = (Class<ENTIDADE>) paramType.getActualTypeArguments()[0];
		}
		nomeClasseEntidade = classeEntidade.getSimpleName();
		
	}
	

	public ENTIDADE insere(ENTIDADE entidade) throws PersistenceException{
		try {
			em.persist(entidade);
		} catch (PersistenceException pe) {
			String msg = "Erro ao inserir " + entidade + " no banco de dados.";
			LOG.error(msg, pe);
			throw new PersistenceException(msg, pe);
		}
		return entidade;
	}
	
	public ENTIDADE findById(Serializable id){
		ENTIDADE instancia = em.find(getEntityClass(), id);
		return instancia;
	}
	
    protected Class<ENTIDADE> getEntityClass() {
        return classeEntidade;
    }
    
    
    public ENTIDADE update (ENTIDADE entidade) throws Exception{
    	ENTIDADE ent = null;
    	try {
    		ent = em.merge(entidade);
    	} catch (PersistenceException e){
    		String msg = "Erro ao atualizar " + entidade + " no banco de dados";
    		LOG.error(msg, e);
    	}
    	return ent;
    }

}
