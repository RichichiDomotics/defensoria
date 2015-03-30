/**
 *
 * Nombre del Programa : DomicilioDAOImpl.java                                    
 * Autor                            : Cesar Agustin                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 30/03/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Implementaci�n para el DAO de la entidad Elemento                      
 * Programa Dependiente  :N/A                                                      
 * Programa Subsecuente :N/A                                                      
 * Cond. de ejecucion        :N/A                                                      
 * Dias de ejecucion          :N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.dao.elemento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Elemento;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author CesarAgustin
 * @version 1.0
 * 
 */
@Repository("elementoDAO")
public class ElementoDAOImpl extends GenericDaoHibernateImpl<Elemento, Long>
        implements
            ElementoDAO {
    public Session getHBSession() {
        return super.getSession();
    }

    /**
     * Operaci�n que realiza la funcionalidad de consultar los elementos y las
     * relaciones impl�citas de los elementos relacionados al expediente. Recibe
     * el expediente del cual va a buscar la informaci�n
     * 
     * @return Devuelve un listado de objetos de tipo Elemento
     */
    @SuppressWarnings("unchecked")
    public List<Elemento> consultarElementosPorCalidad(String numeroExpediente,
            Calidades calidadId) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT el ").append(" FROM Expediente e")
                .append(" JOIN e.elementos el")
                .append(" WHERE e.numeroExpediente like :numeroExpediente ")
                .append(" and el.calidad.calidadId =:calidadId ");
        queryString.append( " AND el.esActivo = true ");
        
        Query query = super.getSession().createQuery(queryString.toString());
        query.setParameter("numeroExpediente", numeroExpediente);
        query.setParameter("calidadId", calidadId.getValorId());
        return query.list();
    }
    
    /*metodo/*ByYolo*/
    @SuppressWarnings("unchecked")
    @Override
    public Elemento findElementosByFolioElemento(String folioEle) {
    	StringBuilder sb = new StringBuilder();
        
    	sb.append(" FROM Elemento o")
         .append(" WHERE o.folioElemento = ")
         .append("'"+folioEle+"'"); 
    	logger.debug("Yolo::: query findElementosByFolioElemento: "+ sb);
        Query query = super.getSession().createQuery(sb.toString());
        return (Elemento) query.uniqueResult();

    }
    /*fin yolo*/

    /*Metodo/*ByYolo*/
	public Long obtenerUltimoElemento() {
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("select e.elementoId ");
		queryStr.append(" from Elemento e where e.elementoId =");
		queryStr.append(" (select MAX(obj.elementoId) from Elemento obj)");
		logger.debug("queryStr :: " + queryStr);
		Query qry = super.getSession().createQuery(queryStr.toString());
		return (Long) qry.uniqueResult();
	}

    /*Fin yolo*/
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Elemento> findElementosByExpedienteId(Long expedienteId) {
    	StringBuffer queryStr = new StringBuffer();
       // return getHibernateTemplate().find(
         //       "from Elemento o where o.expediente.expedienteId = ? AND o.esActivo = true",
           //     expedienteId);
        
    	queryStr.append(" FROM Elemento o")
         .append(" WHERE o.expediente.expedienteId = ")
         .append(expedienteId)
         .append(" AND o.esActivo = true");
        
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
                queryStr.append(" order by ");
                queryStr.append("o.expedienteId");
                queryStr.append(" ").append(pag.getDirOrd());
            }
          
        }

        return super.ejecutarQueryPaginado(queryStr, pag);

    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Elemento> consultarElementoXActividad(Long idActividad) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT elems FROM Actividad a ").
                append("INNER JOIN a.expediente e ").
                append("INNER JOIN e.elementos elems ").
                append("WHERE a = ").append(idActividad);
        sb.append(" AND elems.esActivo = true ");
        
        Query query = super.getSession().createQuery(sb.toString());
        return query.list();
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Elemento> consultarElementoXIdExpedienteTipoValor(Long idExpediente, String valorDescripcion) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT E FROM Elemento E, Valor V ").
                append(" WHERE  E.tipoElemento = V.valorId").
                append(" AND E.expediente.expedienteId = ").append(idExpediente).
                append(" AND UPPER(V.valor) LIKE '").append(valorDescripcion).append("'");
        sb.append(" AND E.esActivo = true ");
        sb.append(" ORDER BY E.calidad.tipoCalidad.valor");
        logger.info("Query:"+sb);
        Query query = super.getSession().createQuery(sb.toString());
        return query.list();
    }
    
    @Override
    public Elemento consultarElementoXIdExpXIdElemento(Long idExpediente, Long idElemento) {
		final StringBuffer elem = new StringBuffer();
		elem.append(" FROM  Elemento ele ")
			 .append(" WHERE ele.elementoId =").append(idElemento)
			 .append(" AND   ele.expediente =").append(idExpediente);
		Query query = super.getSession().createQuery(elem.toString());
		return (Elemento) query.uniqueResult();
    }
    
    
    
}
