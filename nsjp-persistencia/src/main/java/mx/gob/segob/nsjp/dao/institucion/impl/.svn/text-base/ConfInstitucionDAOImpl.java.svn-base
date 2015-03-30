/**
 * Nombre del Programa : ConfInstitucionDAOImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci�n de ConfInstitucionDAO
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.institucion.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.model.ConfInstitucion;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementaci�n de ConfInstitucionDAO.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository
public class ConfInstitucionDAOImpl
        extends
            GenericDaoHibernateImpl<ConfInstitucion, Long>
        implements
            ConfInstitucionDAO {

    @SuppressWarnings("unchecked")
	@Override
    public List<ConfInstitucion> consultarCatalogoSingle() {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new ConfInstitucion(v.confInstitucionId, v.nombreInst) from ConfInstitucion v ");
        queryStr.append(" order by v.nombreInst");
        return super.ejecutarQueryPaginado(queryStr,  PaginacionThreadHolder.get());
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<ConfInstitucion> consultarDemasIntituciones()
            throws NSJPNegocioException {
        final StringBuffer query = new StringBuffer();
        query.append("FROM ConfInstitucion c ");
        query.append("WHERE c.esInstalacionActual = false ");
        logger.debug("query :: " + query);
        Query hbq = super.getSession().createQuery(query.toString());
        return hbq.list();
    }


	@Override
	public ConfInstitucion consultarIntitucionActual() {
		final StringBuffer query = new StringBuffer();
		
        query.append("FROM ConfInstitucion c ");
        query.append("WHERE c.esInstalacionActual = true ");
        logger.debug("query :: " + query);
        
        Query hbq = super.getSession().createQuery(query.toString());
        return (ConfInstitucion) hbq.uniqueResult();
	}
	
	@Override
	public ConfInstitucion consultarIntitucionPorClave(String clave) {
		final StringBuffer query = new StringBuffer();
		
        query.append("FROM ConfInstitucion c ");
        query.append("WHERE LOWER (c.clave) like '").append(clave.toLowerCase()).append("'");
        logger.debug("query :: " + query);
        
        Query hbq = super.getSession().createQuery(query.toString());
        return (ConfInstitucion) hbq.uniqueResult();
	}


	@Override
	public ConfInstitucion consultarIntitucionPorId(Long confInstitucionId) {
		final StringBuffer query = new StringBuffer();
		
        query.append("FROM ConfInstitucion c ");
        query.append("WHERE c.confInstitucionId = ").append(confInstitucionId);
        logger.debug("query :: " + query);
        
        Query hbq = super.getSession().createQuery(query.toString());
        return (ConfInstitucion) hbq.uniqueResult();
	}
	
	

}
