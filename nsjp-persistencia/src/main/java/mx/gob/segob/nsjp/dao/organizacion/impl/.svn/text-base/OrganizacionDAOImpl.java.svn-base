/**
* Nombre del Programa : OrganizacionDAOImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 20 Apr 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion de los metodos de acceso a datos de Organizacion
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
package mx.gob.segob.nsjp.dao.organizacion.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.CatRelacion;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Organizacion;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementacion de los metodos de acceso a datos de Organizacion.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Repository("organizacionDAO")
public class OrganizacionDAOImpl extends
		GenericDaoHibernateImpl<Organizacion, Long> implements OrganizacionDAO {
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO#obtenerOrganizacionByRelacion(java.lang.Long, java.lang.Long)
	 */
	@Override
	public Organizacion obtenerOrganizacionByRelacion(Long elementoId, Long catRelacionId){
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT d ")
					.append("FROM Organizacion d, Relacion r ")
					.append("WHERE r.elementoBySujetoId.elementoId=:elementoId ")
					.append("AND r.catRelacion.catRelacionId=:catRelacionId ")
					.append("AND r.elementoByComplementoId.elementoId=d.elementoId");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("elementoId", elementoId);
		query.setParameter("catRelacionId", catRelacionId);
		return (Organizacion) query.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO#consultarOrganizacionesAudiencia(mx.gob.segob.nsjp.model.Audiencia)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Organizacion> consultarOrganizacionesAudiencia(Audiencia audiencia){
		
		StringBuffer queryString = new StringBuffer("SELECT oa.organizacion ");
								   queryString.append("FROM OrganizacionAudiencia oa ")
								   			  .append("WHERE oa.audiencia.audienciaId = "+audiencia.getAudienciaId());
		
		PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(queryString, pag);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO#obtenerInvolucradoByRelacion(mx.gob.segob.nsjp.model.Organizacion, mx.gob.segob.nsjp.dto.relacion.CatRelacionDTO)
	 */
	@Override
	public Involucrado obtenerInvolucradoByRelacion(Organizacion organizacion,
			CatRelacion catRelacion) {
		StringBuilder queryString = new StringBuilder("SELECT d ");
								   queryString.append("FROM Involucrado d, Relacion r ")
								   .append("WHERE r.elementoByComplementoId.elementoId=:elementoId ")
								   .append("AND r.catRelacion.catRelacionId=:catRelacionId ")
								   .append("AND r.elementoBySujetoId.elementoId=d.elementoId");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("elementoId", organizacion.getElementoId());
		query.setParameter("catRelacionId", catRelacion.getCatRelacionId());
		return (Involucrado) query.uniqueResult();
	}	
}
