/**
* Nombre del Programa : SolicitudPericialDAOImpl.java
* Autor                            : rgama
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 3 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacio de metodos de acceso a datos para la entidad SolicitudPericialDAO
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
package mx.gob.segob.nsjp.dao.solicitud.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudPericial;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;


/**
 * Implementacio de metodos de acceso a datos para la entidad SolicitudPericialDAO.
 * @version 1.0
 * @author rgama
 *
 */
@Repository
public class SolicitudPericialDAOImpl extends
		GenericDaoHibernateImpl<SolicitudPericial, Long> implements
		SolicitudPericialDAO {
	
	@SuppressWarnings("unchecked")
	public List<SolicitudPericial> consultarSolicitudesPericiales(Long tipoSolicitud, Long estatus, String puestoUsuarioSolicitante) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT sp ")
					.append(" FROM SolicitudPericial sp ")
					.append(" WHERE sp.estatus.valorId = :estatus")
					.append(" AND sp.tipoSolicitud.valorId = :tipoSolicitud")
					.append(" AND sp.puestoUsuarioSolicitante LIKE :puestoUsuarioSolicitante");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("estatus", estatus);
		query.setParameter("tipoSolicitud", tipoSolicitud);
		query.setParameter("puestoUsuarioSolicitante", puestoUsuarioSolicitante);

		return query.list();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Solicitud> consultarSolicitudesPericialPorTipoEstatusYUsuario(
			Long tipoSol, Long estatusSol, Long idUsuario) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM SolicitudPericial sp WHERE ")
					.append("sp.tipoSolicitud=").append(tipoSol)
					.append(" AND sp.estatus=").append(estatusSol)
					.append(" AND sp.destinatario=").append(idUsuario);

		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append("sp.folioSolicitud");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.debug("queryString :: " + queryString);
        final Query query = super.getSession().createQuery(queryString.toString());
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }
        
        final List<Solicitud> resp = query.list();
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<Solicitud> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
		
		return resp;
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarSolicitudesPericialesPorTipoYEstatusDePuestoDestinatario(mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes, mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud, mx.gob.segob.nsjp.comun.enums.funcionario.Puestos)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorTipoYEstatusDePuestoDestinatario(
			TiposSolicitudes tipo, EstatusSolicitud estado, Puestos puesto)
			throws NSJPNegocioException {
	    
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM SolicitudPericial sp WHERE ")
					.append("sp.estatus.valorId=").append(estado.getValorId())
					.append(" AND sp.tipoSolicitud.valorId=").append(tipo.getValorId())
					.append(" AND sp.destinatario.puesto.valorId=").append(puesto.getValorId());

		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append("sp.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.debug("queryString :: " + queryString);
        final Query query = super.getSession().createQuery(queryString.toString());
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }
        
        final List<SolicitudPericial> resp = query.list();
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<SolicitudPericial> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
		
		return resp;
	}

	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarSolicitudesPericialesPorTipoYEstatusDePuestoDestinatario(mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes, mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud, mx.gob.segob.nsjp.comun.enums.funcionario.Puestos)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorTipoEstatusAreaDestinatario(
			TiposSolicitudes tipo, EstatusSolicitud estado, Long area)
			throws NSJPNegocioException {
	    
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM SolicitudPericial sp WHERE ")
					.append("sp.estatus.valorId=").append(estado.getValorId())
					.append(" AND sp.tipoSolicitud.valorId=").append(tipo.getValorId())
					//.append(" AND sp.destinatario.area.jerarquiaOrganizacionalId=").append(area);
					.append(" AND sp.areaDestino=").append(area);

		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append(" sp.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.debug("queryString :: " + queryString);
        final Query query = super.getSession().createQuery(queryString.toString());
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }
        
        final List<SolicitudPericial> resp = query.list();
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<SolicitudPericial> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
		
		return resp;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarSolicitudesPericialesPorEstatusYDestinatario(mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud, mx.gob.segob.nsjp.model.Funcionario)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorEstatusYDestinatario(
			EstatusSolicitud estatus, Funcionario destinatario)
			throws NSJPNegocioException {

			return getHibernateTemplate().find("from SolicitudPericial sp where sp.estatus.valorId = ? and "+
					" sp.destinatario.claveFuncionario = ?",
					estatus.getValorId(),destinatario.getClaveFuncionario());			

	}

	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarDictamenIdDeSolicitudPericial(java.lang.Long)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Long consultarDictamenIdDeSolicitudPericial(Long solicitudPericialId)
			throws NSJPNegocioException {
		List dictamens= getHibernateTemplate().
		find("select d.documentoId from Dictamen d where d.solicitudPericial.documentoId = ? ",solicitudPericialId);
		if(dictamens.size()>0){
			return (Long)dictamens.get(0);
		}
		return null;
	}


	//FIXME ¿Y la funcionalidad?
	@Override
	public List<SolicitudPericial> actualizarStatusSolicitudPericial(
			SolicitudPericial solicitudPericial) {
		logger.debug("Ejecutando servicio para actualizar solicitud pericial");
		StringBuffer queryString = new StringBuffer();		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarSolicitudesPericialesPorPuestoSolicitanteEstatusYNumeroExpediente(mx.gob.segob.nsjp.comun.enums.funcionario.Puestos, mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud[], java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorPuestoSolicitanteEstatusYNumeroExpediente(
			Puestos puesto, EstatusSolicitud[] estados, Long numeroExpedienteId) {
		
		StringBuffer query = new StringBuffer();
		query.append(" FROM SolicitudPericial sp WHERE sp.documentoId IN ")
					.append("(SELECT MAX(so.documentoId) FROM Solicitud so WHERE ")
					.append(" so.numeroExpediente.numeroExpedienteId =").append(numeroExpedienteId)
					.append(" GROUP BY so.folioSolicitud").append(")");
//					.append(" AND sp.funcionarioSolicitante.puesto.valorId = ?  ");
	
		if(estados != null){
			for(EstatusSolicitud estado:estados){
				query.append(" and sp.estatus.valorId =  " + estado.getValorId() );
			}
		}
			
//		query.append(")");
					
//		
//		StringBuffer query = new StringBuffer();
//		query.append(" FROM SolicitudPericial sp where sp.funcionarioSolicitante.puesto.valorId = ? ");
//		query.append(" FROM SolicitudPericial sp WHERE sp.documentoId in ");
//		query.append(" (SELECT MAX(sp.documentoId) FROM SolicitudPericial sp ");
//		query.append(" WHERE sp.funcionarioSolicitante.puesto.valorId = ?  ");
//		query.append (" AND sp.funcionarioSolicitante.puesto.valorId = ? ");
		
//		if(estados != null){
//			for(EstatusSolicitud estado:estados){
//				query.append(" and sp.estatus.valorId =  " + estado.getValorId() );
//			}
//		}
		
//		List<Object[]> resultados=null;
//		if(estados !=null && estados.length>0 && estados[0].equals(EstatusSolicitud.EN_PROCESO)){
//////			query.append(" and sp.documentoId in  " + estado.getValorId() + " ");
//			
//			StringBuffer sb = new StringBuffer();
//			sb.append("SELECT so.Solicitud_id from Solicitud so where so.NumeroExpediente_id="+numeroExpedienteId+" and so.Estatus_val="+estados[0].getValorId());
//			Query q = super.getSession().createSQLQuery(sb.toString());
//			resultados = q.list();
//			logger.debug("Ejecutando servicio para actualizar solicitud pericial");
//			
//			logger.debug("Ejecutando"+resultados.getClass().getName());
			
			
//			Se comenta codigo para que aparescan las solicitudes en la pestaña de periciales en el visor de elementos en ui a 
//			reserva de que afecte otro flujo por lo tanto solo se comenta y no se borra el codigo
//			if(resultados!=null && !resultados.isEmpty()){
//				query.append(" and sp.documentoId in(");
//				int j=0;
//				for (Object[] objects : resultados) {
//					if(j>0){
//						query.append(",");
//					}
//					Object[] list = resultados.get(j);
//					query.append(list[1].toString());
//					j++;
//				}
//				query.append(")");
//			}
//		}
//		query.append(" and sp.numeroExpediente.numeroExpedienteId = ? ");
//		query.append(" GROUP BY sp.numeroExpediente.numeroExpedienteId ) ");
		
		logger.debug("Query:::"+query);
		return getHibernateTemplate().find(query.toString());
//		return getHibernateTemplate().find(query.toString(),puesto.getValorId(),numeroExpedienteId);
	}

	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarSolicitudesPericialPorFolio(mx.gob.segob.nsjp.model.SolicitudPericial)
	 */
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialPorFolioEstatusNoCerrado(
			SolicitudPericial folioSolicitudPericial)
			throws NSJPNegocioException {
			if(folioSolicitudPericial!=null && folioSolicitudPericial.getFolioSolicitud()!=null && !folioSolicitudPericial.getFolioSolicitud().equals("")){
				List<SolicitudPericial> solicitudes=getHibernateTemplate().find("from SolicitudPericial sp where sp.folioSolicitud = ? and " +
						" sp.estatus.valorId = ?",
						folioSolicitudPericial.getFolioSolicitud(),EstatusSolicitud.CERRADA.getValorId());
						if(!solicitudes.isEmpty()){
							return solicitudes;
						}
			}
			return null;
	}

	/*Inicio de Daos/*ByYolo*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorEstatus(
			EstatusSolicitud estatus, Funcionario destinatario)
			throws NSJPNegocioException {
		StringBuffer queryString = new StringBuffer();
					
		
		if (destinatario.getRol()!=null) {
			if (destinatario.getRol().getNombreRol()!=null) {
				if (destinatario.getRol().getNombreRol().equals("DirectorPericiales")) {
					queryString.append("FROM SolicitudPericial sp where sp.documentoId in ")
								.append("(SELECT MAX (sp.documentoId) FROM SolicitudPericial sp where sp.estatus.valorId = '").append(estatus.getValorId())
								.append("'")
								.append(" GROUP BY sp.numeroExpediente.numeroExpedienteId)");
				}
				
			}
			
		}else{
			queryString.append("FROM SolicitudPericial sp where sp.documentoId in ")
						.append("(SELECT MAX (sp.documentoId) FROM from SolicitudPericial sp where sp.estatus.valorId = ").append(estatus.getValorId())
						.append(" AND sp.destinatario.claveFuncionario = ").append(destinatario.getClaveFuncionario())
						.append("  GROUP BY sp.numeroExpediente.numeroExpedienteId)");			
		}
		
		/*inicio paginacion/*ByYolo*/
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append(" sp.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.debug("queryString :: " + queryString);
        final Query query = super.getSession().createQuery(queryString.toString());
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }
        
        final List<SolicitudPericial> resp = query.list();
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<SolicitudPericial> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
		/*Fin paginacion/*ByYolo*/
		

		return resp;	

	}
	
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesTodas()
			throws NSJPNegocioException {
		StringBuffer queryString = new StringBuffer();
		
//		return getHibernateTemplate().find("FROM SolicitudPericial sp where sp.documentoId in "+ 
//		"(SELECT MAX (sp.documentoId) FROM SolicitudPericial sp WHERE "+
//      "sp.destinatario.claveFuncionario = ? GROUP BY sp.numeroExpediente.numeroExpedienteId)",		
		
		queryString.append("FROM SolicitudPericial sp where sp.documentoId in ")
		.append(" (SELECT MAX (sp.documentoId) FROM SolicitudPericial sp where sp.estatus.valorId IN ('").append(EstatusSolicitud.ABIERTA.getValorId())
		.append("','").append(EstatusSolicitud.EN_PROCESO.getValorId())
		.append("','").append(EstatusSolicitud.CERRADA.getValorId())
		.append("')")
		.append(" GROUP BY sp.numeroExpediente.numeroExpedienteId)");

		
		/*inicio paginacion/*ByYolo*/
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append(" sp.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.debug("queryString :: " + queryString);
        final Query query = super.getSession().createQuery(queryString.toString());
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }
        
        final List<SolicitudPericial> resp = query.list();
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<SolicitudPericial> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
		/*Fin paginacion/*ByYolo*/
		return resp;
	}


	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorAgencia(
			Long idAgencia, FuncionarioDTO destinatario)
			throws NSJPNegocioException {
		
		StringBuffer queryString = new StringBuffer();		
		
		queryString.append("FROM SolicitudPericial sp where sp.documentoId in ")
			.append("(SELECT MAX(sp.documentoId) FROM SolicitudPericial sp ")
			.append("WHERE sp.numeroExpediente.expediente.discriminante = ").append(idAgencia)
			.append(" GROUP BY sp.numeroExpediente.numeroExpedienteId)");
		
		/*inicio paginacion/*ByYolo*/
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append(" sp.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.debug("queryString :: " + queryString);
        final Query query = super.getSession().createQuery(queryString.toString());
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }
        
        final List<SolicitudPericial> resp = query.list();
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<SolicitudPericial> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
		/*Fin paginacion/*ByYolo*/
		return resp;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorUsuario(
			FuncionarioDTO delFuncionario, FuncionarioDTO destinatario)
			throws NSJPNegocioException {
		
//		return getHibernateTemplate().find("from SolicitudPericial sp where "+
//				" sp.destinatario.claveFuncionario = ?",
//				delFuncionario.getClaveFuncionario());		
		
		return getHibernateTemplate().find("FROM SolicitudPericial sp where sp.documentoId in "+ 
										"(SELECT MAX (sp.documentoId) FROM SolicitudPericial sp WHERE "+
				"sp.destinatario.claveFuncionario = ? GROUP BY sp.numeroExpediente.numeroExpedienteId)",
				delFuncionario.getClaveFuncionario());	
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorDia()
			throws NSJPNegocioException {
		final SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		String fecha =formato.format(new Date());
		
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM SolicitudPericial sp where sp.documentoId in")
			.append("(SELECT MAX(sp.documentoId) from SolicitudPericial sp")
			.append(" where CONVERT (nvarchar, sp.fechaLimite, 112)=").append(fecha)
			.append(" group by sp.numeroExpediente.numeroExpedienteId)");
		
		/*inicio paginacion/*ByYolo*/
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append(" sp.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.debug("queryString :: " + queryString);
        final Query query = super.getSession().createQuery(queryString.toString());
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }
        
        final List<SolicitudPericial> resp = query.list();
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<SolicitudPericial> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
		/*Fin paginacion/*ByYolo*/
		return resp;

	}


	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorTipoEstatus(
			TiposSolicitudes tipo, EstatusSolicitud estado)
			throws NSJPNegocioException {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM SolicitudPericial sp WHERE ")
					.append("sp.estatus.valorId=").append(estado.getValorId())
					.append(" AND sp.tipoSolicitud.valorId=").append(tipo.getValorId());
					//.append(" AND sp.destinatario.area.jerarquiaOrganizacionalId=").append(area);
//					.append(" AND sp.areaDestino=").append(area);

		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append(" sp.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.debug("queryString :: " + queryString);
        final Query query = super.getSession().createQuery(queryString.toString());
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }
        
        final List<SolicitudPericial> resp = query.list();
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<SolicitudPericial> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
		
		return resp;

	}	
	
	/*Fin de daos/*ByYolo*/
}
