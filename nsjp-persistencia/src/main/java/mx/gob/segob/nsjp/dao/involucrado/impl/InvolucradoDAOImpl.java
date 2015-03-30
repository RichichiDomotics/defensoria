/**
*
* Nombre del Programa : InvolucradoDAOImpl.java                                    
* Autor                            : Cesar Agustin                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 05/04/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Implementación para el DAO de la entidad Narrativa                      
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
package mx.gob.segob.nsjp.dao.involucrado.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author CesarAgustin
 * @version 1.0
 */
@Repository
public class InvolucradoDAOImpl extends GenericDaoHibernateImpl<Involucrado, Long>
		implements InvolucradoDAO {
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> obtenerInvolucradosAll() {
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM Involucrado ");	 			
		Query query = super.getSession().createQuery(queryStr.toString());	
		return query.list();	
	}

	
	public Expediente obtenerExpediente(Integer expedienteId) {
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("SELECT e ")
				.append("FROM Expediente e ")
				.append("WHERE e.expedienteId=:expedienteId");		
		Query query = super.getSession().createQuery(queryStr.toString());	
		query.setParameter("expedienteId", expedienteId);
		return (Expediente)query.uniqueResult();
	}


	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarInvolucradosByExpediente(
			Long expedienteId) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT i ")
					.append( "FROM Involucrado i ")
//					.append(" LEFT JOIN i.nombreDemograficos as nd ")
					.append(" WHERE i.expediente.expedienteId=").append(expedienteId)
					.append(" and i.esActivo = true")
					.append(" ORDER BY i.elementoId DESC");

//		final PaginacionDTO pag = PaginacionThreadHolder.get();

//		logger.debug("pag :: " + pag);
//		if (pag != null && pag.getCampoOrd() != null) {
//			if (pag.getCampoOrd().equals("1")) {
//				queryString.append(" order by ");
//				queryString.append(" nd.nombre");
//				queryString.append(" ").append(pag.getDirOrd());
//			}
//			if (pag.getCampoOrd().equals("2")) {
//				queryString.append(" order by ");
//				queryString.append(" i.calidad.tipoCalidad.valor");
//				queryString.append(" ").append(pag.getDirOrd());
//			}
//		}
//		return super.ejecutarQueryPaginado(queryString, pag);
		return super.getSession().createQuery(queryString.toString()).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarInvolucradosByExpedienteAsc(
			Long expedienteId) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT i ")
					.append( "FROM Involucrado i ")
					.append(" WHERE i.expediente.expedienteId=").append(expedienteId)
					.append(" and i.esActivo = true");
		return super.getSession().createQuery(queryString.toString()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Involucrado> obtenerInvolucradosPorExpedienteYCalidades(Long expedienteId, Calidades[] calidades, Boolean esActivo) {
		
		final StringBuffer queryString = new StringBuffer();
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		queryString.append("FROM Involucrado i ")
					.append(" WHERE i.expediente = ").append(expedienteId);
		
		if(esActivo != null){
			queryString.append(" AND i.esActivo = ").append(esActivo);
		}
			
		if(calidades!= null && calidades.length>0){
			queryString.append(" and i.calidad.tipoCalidad.valorId in ( ");
			for(int iCal = 0; iCal<calidades.length;iCal++)
				queryString.append(iCal>0?","+calidades[iCal].getValorId():calidades[iCal].getValorId());
			queryString.append(")");
		}
		
		return super.ejecutarQueryPaginado(queryString, pag);   
	}


	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarExpedientesByAlias(
			String aliasInvolucrado) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT i ")
					.append("FROM Involucrado i, AliasInvolucrado a ")
					.append("WHERE a.alias=:aliasInvolucrado ")
					.append("AND a.involucrado.elementoId=i.elementoId");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("aliasInvolucrado", aliasInvolucrado);
				
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarExpedientesByAliasLike(
			String aliasInvolucrado) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT i ")
					.append("FROM Involucrado i, AliasInvolucrado a ")
					.append("WHERE a.alias like :aliasInvolucrado ")
					.append("AND a.involucrado.elementoId=i.elementoId");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("aliasInvolucrado", aliasInvolucrado);
				
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarExpedientesByNombre(
			FiltroExpedienteDTO filtroExpedienteDTO) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT i ")
					.append("FROM Involucrado i LEFT JOIN i.nombreDemograficos nd LEFT JOIN i.expediente ex ,NumeroExpediente ne ")
					.append("WHERE ");					
		if (!filtroExpedienteDTO.getApellidos().equals("%%") && !filtroExpedienteDTO.getApellidoMat().equals("%%") && !filtroExpedienteDTO.getNombre().equals("%%")) {
			queryString.append("nd.nombre like '").append(filtroExpedienteDTO.getNombre()).append("' AND ")
						.append("nd.apellidoPaterno like '").append(filtroExpedienteDTO.getApellidos()).append("' AND ")
						.append("nd.apellidoMaterno like '").append(filtroExpedienteDTO.getApellidoMat()).append("'");
		} else if (!filtroExpedienteDTO.getApellidos().equals("%%") && !filtroExpedienteDTO.getNombre().equals("%%")) {
			queryString.append("nd.nombre like '").append(filtroExpedienteDTO.getNombre()).append("' AND ")
						.append("nd.apellidoPaterno like '").append(filtroExpedienteDTO.getApellidos()).append("'");
		} else if (!filtroExpedienteDTO.getApellidoMat().equals("%%") && !filtroExpedienteDTO.getNombre().equals("%%")) {
			queryString.append("nd.nombre like '").append(filtroExpedienteDTO.getNombre()).append("' AND ")
						.append("nd.apellidoMaterno like '").append(filtroExpedienteDTO.getApellidoMat()).append("'");
		} else if (!filtroExpedienteDTO.getApellidos().equals("%%") && !filtroExpedienteDTO.getApellidoMat().equals("%%")) {
			queryString.append("nd.apellidoPaterno like '").append(filtroExpedienteDTO.getApellidos()).append("' AND ")
						.append("nd.apellidoMaterno like '").append(filtroExpedienteDTO.getApellidoMat()).append("'");
		}else if (!filtroExpedienteDTO.getNombre().equals("%%")) {
			queryString.append("nd.nombre like '").append(filtroExpedienteDTO.getNombre()).append("'");
		} else if (!filtroExpedienteDTO.getApellidoMat().equals("%%")) {
			queryString.append("nd.apellidoMaterno like '").append(filtroExpedienteDTO.getApellidoMat()).append("'");
		} else if (!filtroExpedienteDTO.getApellidos().equals("%%")) {
			queryString.append("nd.apellidoPaterno like '").append(filtroExpedienteDTO.getApellidos()).append("'");
		}
		
		if(filtroExpedienteDTO.getUsuario()!=null && 
				filtroExpedienteDTO.getUsuario().getFuncionario()!=null &&
						filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante()!=null){
			queryString.append(" AND ex.discriminante.catDiscriminanteId = ");
			queryString.append(filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getCatDiscriminanteId());
			if(filtroExpedienteDTO.getIdFuncionario()!=null){
				queryString.append(" AND ex.expedienteId = ne.expediente.expedienteId");
				queryString.append(" AND ne.funcionario.claveFuncionario = ");
				queryString.append(filtroExpedienteDTO.getIdFuncionario());
			}
			if(filtroExpedienteDTO.getUsuario().getAreaActual()!=null && filtroExpedienteDTO.getUsuario().getAreaActual().getAreaId()!=null){
				queryString.append(" AND ne.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
				queryString.append(filtroExpedienteDTO.getUsuario().getAreaActual().getAreaId());
			}
		}
		
		if(filtroExpedienteDTO.getIdArea()!=null ){
			queryString.append(" AND ne.expediente.expedienteId = ex.expedienteId ");
			queryString.append(" AND ne.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
			queryString.append(filtroExpedienteDTO.getIdArea());
		}
								
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Involucrado> obtenerInvByIdExpAndCalidad(Long idExpediente,
			Long calidadId, Boolean esDetenido) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT i ")
					.append(" FROM Involucrado i ")
					.append(" LEFT JOIN i.nombreDemograficos nd ")
					.append(" WHERE i.expediente.expedienteId="+idExpediente)
					.append(" AND i.calidad.tipoCalidad.valorId="+calidadId)
					.append(" and i.esActivo = true");

		if(esDetenido!=null){
			queryString.append(" AND i.esDetenido="+esDetenido);
		}
		queryString.append(" ORDER BY nd.nombreDemograficoId DESC");
		Query query = super.getSession().createQuery(queryString.toString());
		logger.info("Query Involucrado --- :::: >> " + query.toString() );
		final List<Involucrado> resp =  query.list();
		logger.debug("resp.size() :: "+resp.size());
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Involucrado> obtenerInvolucradosByAudiencia(Long audienciaId, Long calidadId) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT ia.involucrado ");
		queryString.append("FROM InvolucradoAudiencia ia ");
//		queryString.append("WHERE ia.id.audienciaId=:audienciaId");
		queryString.append("WHERE ia.id.audienciaId="+audienciaId);
		if(calidadId != -1){
			queryString.append(" AND ia.involucrado.calidad.tipoCalidad.valorId=:calidadId");
		}
		Query query = super.getSession().createQuery(queryString.toString());
//		query.setParameter("audienciaId", audienciaId);
		if(calidadId != -1){
			query.setParameter("calidadId", calidadId);
		}
		return query.list();
	}

	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO#obtenerInvolucradosByCasoYCalidades(java.lang.String, mx.gob.segob.nsjp.comun.enums.calidad.Calidades[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Involucrado> obtenerInvolucradosByCasoYCalidades(
			String numeroCaso, Calidades[] calidades) {
		StringBuilder query = new StringBuilder();
		
		query.append("from Involucrado inv where inv.expediente.caso.numeroGeneralCaso = ? " +
				" and inv.calidad.tipoCalidad.valorId in ( ");
		for(int iCal = 0; iCal<calidades.length;iCal++){
			query.append(iCal>0?","+calidades[iCal].getValorId():calidades[iCal].getValorId());
		}
		query.append(")");
		
		return getHibernateTemplate().find(query.toString(),numeroCaso);
	}

	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarInvolucradosByIds(
			List<Long> idInvolucrados) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT i ")
					.append("FROM Involucrado i ");					
		String lista = idInvolucrados.toString();

		if(!lista.trim().isEmpty() && lista.indexOf('[')!= -1){
			lista= lista.substring(1, lista.length()-1);
			logger.info("Lista:"+ lista);
			queryString.append(" WHERE i.elementoId in (")
			.append( lista + ")" );
		}
		logger.info("Query:" + queryString);
		Query query = super.getSession().createQuery(queryString.toString());
//		query.setParameter("expedienteId", expedienteId);
		return query.list();
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> obtenerExpedientesPorCondicionDetencionInvYMes(
			Date fechaInicio, Date fechaFin, Boolean condicionDet) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT MONTH(i.expediente.fechaCreacion), COUNT(*) FROM Involucrado i ")
					.append("WHERE i.esDetenido=").append(condicionDet)
					.append(" AND CONVERT(VARCHAR, i.expediente.fechaCreacion ,112) BETWEEN ")
					.append(DateUtils.formatearBD(fechaInicio)).append(" AND ")
					.append(DateUtils.formatearBD(fechaFin)).append("GROUP BY MONTH(i.expediente.fechaCreacion)");
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}


	@Override
	public Involucrado consultarInvolucradoPorFolioElemento(String folioElemento) {
		final StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("SELECT i ")
				.append("FROM Involucrado i ")
				.append("WHERE i.folioElemento = '"+folioElemento+"'");

		Query query = super.getSession().createQuery(hqlQuery.toString());
		logger.info("Query:" + query.toString());
		logger.info("size list" + query.list().size());
		if(query.list().size() > 1)
			return (Involucrado)query.list().get(0); //uniqueResult()
		else
			return (Involucrado)query.uniqueResult();
	}

	@Override
	public Involucrado obtenerInvolucradoByFolioElemento(String folioInvolucrado) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Involucrado i WHERE ")
					.append("i.folioElemento='").append(folioInvolucrado)
					.append("'");
		Query query = super.getSession().createQuery(queryString.toString());
		return (Involucrado) query.uniqueResult();
//		return (Involucrado) getHibernateTemplate().find(queryString.toString(), folioInvolucrado);
	}

	
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarInvolucradosDeAudienciaPorCalidad(Long idAudiencia, List<Long> calidades) {

    	StringBuffer queryStr = new StringBuffer();
    	
    	String idsCalidades = "";
    	if(calidades != null && calidades.size() > 0){
    		for (Long id : calidades) {
    			idsCalidades = idsCalidades + id + ",";
    		}
    		idsCalidades = idsCalidades.substring(0, idsCalidades.lastIndexOf(","));
    	}
		
    	    	
		queryStr.append("SELECT i FROM Involucrado i where 1=1 ");
		
		if(calidades != null && calidades.size() > 0){
			queryStr.append(" AND i.calidad.tipoCalidad.valorId IN (");
			queryStr.append(idsCalidades);
			queryStr.append(")");
		}
		
		queryStr.append(" AND i.elementoId IN (");
			queryStr.append(" SELECT ia.involucrado.elementoId FROM InvolucradoAudiencia ia where ia.audiencia.audienciaId = ").append(idAudiencia);
		queryStr.append(")");		
//		eNABLE IT: se permite ver a los denunciantes como victimas cuando han sido calificados asi.
		if (idsCalidades.contains("214") && idsCalidades.contains("215") && idsCalidades.contains("666666")) {
//			queryStr.append(" and (i.condicion is null or i.condicion = 1 or i.condicion = 0)");
			queryStr.append(" and i.condicion = 1");
		}
    
       
        final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryStr.append(" order by ");
				queryStr.append("i.calidad.tipoCalidad.valorId");
				queryStr.append(" ").append(pag.getDirOrd());
			}
		}
		
		return super.ejecutarQueryPaginado(queryStr, pag);   
    }

	@Override
	public Involucrado obtenerInvolucradoByFolioElementoExpediente(String folioInvolucrado, Long expedienteId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Involucrado i WHERE ")
					.append("i.folioElemento='").append(folioInvolucrado)
					.append("'")
					.append(" and i.expediente.expedienteId=").append(expedienteId);
		Query query = super.getSession().createQuery(queryString.toString());
		if( query.list().size() > 1){
			return (Involucrado) query.list().get(0);
		}else{
			return (Involucrado) query.uniqueResult();
		}
	}

	
	@Override
	public Involucrado consultarInvolucradoPorIdTribunal(String folioTribunal) {
		final StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("SELECT i ")
				.append("FROM Involucrado i ")
				.append("WHERE i.idInvTribunal = '"+folioTribunal+"'");

		Query query = super.getSession().createQuery(hqlQuery.toString());
		return (Involucrado)query.uniqueResult();
	}
	
}
