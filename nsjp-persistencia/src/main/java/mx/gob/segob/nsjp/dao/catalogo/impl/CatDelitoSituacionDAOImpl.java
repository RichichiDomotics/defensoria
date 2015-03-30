/**
 * 
 */
package mx.gob.segob.nsjp.dao.catalogo.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoSituacionDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.SituacionImputado;

import org.springframework.stereotype.Repository;

/**
 * @author EduardoAD
 *
 */
@Repository
public class CatDelitoSituacionDAOImpl extends
GenericDaoHibernateImpl<SituacionImputado, Long> implements CatDelitoSituacionDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<SituacionImputado> consultarTodos() {
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM SituacionImputado si ");
		queryStr.append("ORDER BY si.descripcion");

		logger.debug("queryStr :: " + queryStr);
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);

        return super.ejecutarQueryPaginado(queryStr, pag);
	}
}
