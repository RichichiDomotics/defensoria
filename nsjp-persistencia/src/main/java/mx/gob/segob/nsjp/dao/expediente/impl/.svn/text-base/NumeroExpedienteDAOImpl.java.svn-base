/**
 * Nombre del Programa : NumeroExpedienteDAOImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 13 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementación para el DAO del numero del expediente
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
package mx.gob.segob.nsjp.dao.expediente.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.comun.enums.expediente.AcronimoNumExpAlterno;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementación para el DAO del numero del expediente.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository("numeroExpedienteDAO")
public class NumeroExpedienteDAOImpl
        extends GenericDaoHibernateImpl<NumeroExpediente, Long>
        implements
        NumeroExpedienteDAO {

    @Autowired
    private ExpedienteDAO expedienteDao;
    
    private static final String SEPARADOR_ANIO_ENTIDAD = "-";
    private static final String SEPARADOR_ENTIDAD_DISTRITO = "-";
    private static final Integer INICIO_CONSECUTIVO = 0;
    private static final String SEPARADOR_UNIDAD_ANIO = "/";
    private boolean esUIE = false;
    
    @Override
    public NumeroExpediente obtenerNumeroExpediente(Long expedienteId,
            Long areaId) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new NumeroExpediente(v.id, v.numeroExpediente, v.fechaApertura, v.funcionario.claveFuncionario)");
        queryStr.append(" from NumeroExpediente v ");
        queryStr.append(" where v.expediente.expedienteId = ");
        queryStr.append(expedienteId);
        queryStr.append(" and v.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
        queryStr.append(areaId);
        logger.info("Query:"+ queryStr);
        return (NumeroExpediente) super.getSession().
                createQuery(queryStr.toString()).uniqueResult();
    }
    
    @Override
    public Long obteneUltimoNumeroExpedienteIdXAreaExpId(Long expedienteId,
            Long areaId) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select max(ne.numeroExpedienteId)");
        queryStr.append(" from NumeroExpediente ne ");
        queryStr.append(" where ");
    	queryStr.append(" ne.expediente.expedienteId = ");
        queryStr.append(expedienteId);
            
        Query hbq = super.getSession().createQuery(queryStr.toString());
        return (Long) hbq.uniqueResult();
    }
        
    @Override
    public List<NumeroExpediente> consultarNumeroExpedientes(Long expedienteId,
            Long areaId) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new NumeroExpediente(v.id, v.numeroExpediente, v.fechaApertura, v.funcionario.claveFuncionario)");
        queryStr.append(" from NumeroExpediente v ");
        queryStr.append(" where v.expediente.expedienteId = ");
        queryStr.append(expedienteId);
        queryStr.append(" and v.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
        queryStr.append(areaId);
        logger.info("Query:"+ queryStr);
        return super.getSession().createQuery(queryStr.toString()).list();
    }
    
    @Override
    public List<NumeroExpediente> consultarNumeroExpedientesXIdExpAreaDiscriminante(Long expedienteId,
            Long areaId,Long discriminante) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new NumeroExpediente(v.id, v.numeroExpediente, v.fechaApertura, v.funcionario.claveFuncionario)");
        queryStr.append(" from NumeroExpediente v ");
        queryStr.append(" where v.expediente.expedienteId = ");
        queryStr.append(expedienteId);
        
        if(discriminante != null){
        	 queryStr.append(" and v.expediente.discriminante.catDiscriminanteId = ");
            queryStr.append(discriminante);
        }
        if(areaId != null){
        	queryStr.append(" and v.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
            queryStr.append(areaId);
        }
        
        logger.info("Query:"+ queryStr);
        return super.getSession().createQuery(queryStr.toString()).list();
    }
    
    @Override
    public NumeroExpediente obtenerNumeroExpediente(String numeroExpediente,Long discriminanteId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ne FROM NumeroExpediente ne ").
                append("WHERE ne.numeroExpediente = :numeroExpediente");
        if(discriminanteId != null && !discriminanteId.equals(0L) ){
        	sb.append(" AND ne.expediente.discriminante.catDiscriminanteId=").append(discriminanteId);
        }
        	
        
        Query query = super.getSession().createQuery(sb.toString());
        query.setParameter("numeroExpediente", numeroExpediente);
        List<NumeroExpediente> res = (List<NumeroExpediente>)query.list();
        if(!res.isEmpty()){
        	return (NumeroExpediente)res.get(0);
        }
        
        logger.info("Query:"+ sb);
        return null;
    }

    @Override
    public void asociarNumExpediente(Expediente expediente, Usuario usuario) {
        if (logger.isDebugEnabled()) {
            logger.debug("\n Asociando el expediente = " + expediente);
            logger.debug("Con el usuario = " + usuario + "\n\n");
            logger.debug("Buscando el expediente = " + expediente.getNumeroExpediente());
        }
//        TODO cbeltran donde se usa el método, en qué proceso? si el funcionario es not null
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ne FROM NumeroExpediente ne ").
                append("WHERE ne.numeroExpediente = :numeroExpediente");
        Session session = super.getSession();
        Query query = session.createQuery(sb.toString());
        query.setParameter("numeroExpediente", expediente.getNumeroExpediente());
        NumeroExpediente numeroExpediente = (NumeroExpediente) query.uniqueResult();
        numeroExpediente.setFuncionario(usuario.getFuncionario());
//        session.close();
        if (logger.isDebugEnabled()) {
            logger.debug("salvando numeroExpediente = " + numeroExpediente);
            logger.debug("numeroExpediente.getNumeroExpedienteId() = " +
                    numeroExpediente.getNumeroExpedienteId());
        }
        saveOrUpdate(numeroExpediente);
        if (logger.isDebugEnabled()) {
            logger.debug("numeroExpediente guardado");
            logger.debug("Salvado cambios del expediente = " + expediente);
            logger.debug("expediente.getExpedienteId() = " + expediente.getExpedienteId());
        }
        expedienteDao.saveOrUpdate(expediente);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<NumeroExpediente> consultarTOCAPorCausa(Long padre) {
		final StringBuffer queryStr = new StringBuffer();
			
		queryStr.append("FROM NumeroExpediente ne")
				.append(" WHERE ne.numeroExpedientePadre =" + padre);
			
		List<NumeroExpediente> numeros= super.getHibernateTemplate().find(queryStr.toString());
		return numeros;
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO#consultarUltimaParidadAsignadaDeNumeroExpediente()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Boolean consultarUltimaParidadAsignadaDeNumeroExpediente() throws NSJPNegocioException {
		Boolean esPar = null;
		List resExpId = getHibernateTemplate().find("Select max(numeroExpedienteId) " +
				"from NumeroExpediente ne where ne.esPar is not null");
		if(resExpId != null && !resExpId.isEmpty() && resExpId.get(0) != null){
			esPar = (Boolean)getHibernateTemplate().find("Select ne.esPar from NumeroExpediente ne where ne.numeroExpedienteId = ?",
					resExpId.get(0)).get(0);
		}
		
		return esPar;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO#consultarExpedientesConEventosHistorico(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NumeroExpediente> consultarExpedientesConEventosHistorico(
			Long casoId,Long usuarioId) {
		//TODO: VAP agregar la constante de tiempo de consultas históricas hacia atrás
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(new Date());
		fechaInicio.add(Calendar.DATE, ConstantesGenerales.DIAS_ATRAS_CONSULTAS_HISTORICAS*-1);
		
		String query = "Select nExp from NumeroExpediente nExp where exists (" +
				"select audiencia.audienciaId from Audiencia audiencia where " +
				"audiencia.numeroExpediente  = nExp and " +
				"audiencia.fechaAudiencia between ? and ? and " +
				"audiencia.numeroExpediente.expediente.caso.casoId = ?"+
				") ";
		
		return getHibernateTemplate().find(query,fechaInicio.getTime(),new Date(),casoId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarCausasHistorico(Calendar fechaInicio,Long discriminanteId) {
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.setTime(new Date());
		
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT ne FROM NumeroExpediente ne ")
					.append("WHERE  ne.fechaApertura BETWEEN ? ")
					.append(" AND ? ").append(" AND ne.tipoExpediente=")
					.append(TipoExpediente.CAUSA.getValorId())
					//Usado para distritos
					.append(" AND ne.expediente.discriminante.catDiscriminanteId=").append(discriminanteId);
					
		queryString.append(" AND ne.numeroExpedienteId IN (")
					.append("SELECT neP.numeroExpedientePadre FROM NumeroExpediente neP WHERE ")
					.append("neP.tipoExpediente=").append(TipoExpediente.CARPETA_DE_EJECUCION.getValorId())
					.append(" AND neP.estatus=").append(EstatusExpediente.CERRADO.getValorId()).append(")");
		
		logger.debug("Query:: " + queryString);
		
		return getHibernateTemplate().find(queryString.toString(),fechaInicio.getTime(), new Date());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarCarpetasEjecucionPorCausa(
			Long idNumeroExpediente) throws NSJPNegocioException {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM NumeroExpediente ne WHERE ")
					.append("ne.numeroExpedientePadre=").append(idNumeroExpediente)
					.append(" AND ne.tipoExpediente=").append(TipoExpediente.CARPETA_DE_EJECUCION.getValorId());
		
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarNumeroExpedienteByEstatus(
			TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente)
			throws NSJPNegocioException {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM NumeroExpediente ne WHERE ")
					.append("ne.tipoExpediente=").append(tipoExpediente.getValorId())
					.append(" AND ne.estatus=").append(estatusExpediente.getValorId());
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarByUsuarioArea(Long idUsuario,
			Long areaId, Long estatusExpediente, Long discriminanteId,Long catUIE) {
		
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select ne ");
		queryString.append("FROM NumeroExpediente ne ");
			if(catUIE!=null){
				queryString.append(",Expediente e ");
			}
			queryString.append("WHERE ")
					.append("ne.funcionario=").append(idUsuario);

		if(areaId != null && areaId > 0)
						queryString.append(" AND ne.jerarquiaOrganizacional=").append(areaId);
		
		if(estatusExpediente!=null) {
			queryString.append(" AND ( ne.estatus="+estatusExpediente);
			if(estatusExpediente.equals(EstatusExpediente.ABIERTO.getValorId())){
				queryString.append(" OR ne.estatus="+EstatusExpediente.ABIERTO_EJECUCION.getValorId());
				queryString.append(" OR ne.estatus="+EstatusExpediente.ABIERTO_INTEGRACION.getValorId());
				queryString.append(" OR ne.estatus="+EstatusExpediente.ABIERTO_RESTAURATIVA.getValorId());
				queryString.append(" OR ne.estatus="+EstatusExpediente.ABIERTO_TECNICA_CON_CARPETA.getValorId());
				queryString.append(" OR ne.estatus="+EstatusExpediente.ABIERTO_TECNICA_SIN_CARPETA.getValorId());
			}
			queryString.append(" )");
		
			//consulta por agencias
			if(discriminanteId > 0)
				queryString.append(" AND ne.expediente.discriminante.catDiscriminanteId=").append(discriminanteId);
			
			if(catUIE!=null){
				queryString.append(" AND ne.expediente.expedienteId=e.expedienteId");
				queryString.append(" AND ne.expediente.discriminante.catDiscriminanteId=").append(discriminanteId);
			}
			
			
		}
		logger.debug("queryString :: " + queryString);
		//Query query = super.getSession().createQuery(queryString.toString());
		//return query.list();
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        return super.ejecutarQueryPaginado(queryString, pag);
	}

	
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> buscarNumeroExpedienteAbieroPorIdFuncionario(
			Long claveFuncionario) throws NSJPNegocioException {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM NumeroExpediente ne WHERE ")
					.append("ne.funcionario.claveFuncionario=").append(claveFuncionario)
					.append(" AND ne.estatus=").append(EstatusExpediente.ABIERTO.getValorId());
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NumeroExpediente> consultarNumeroExpedientePorCasoDeSolicitud(Long solicitudId) {
		
		return getHibernateTemplate().find("from NumeroExpediente ne " +
				"where ne.expediente.caso.numeroGeneralCaso in " +
				"(select s.numeroCasoAsociado from Solicitud s where s.documentoId=?) ",solicitudId);
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarHistoricoCausasExpediente(Date fechaHistorico) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT(ne) FROM NumeroExpediente ne LEFT JOIN ne.numeroExpedientes nes WHERE ")
					.append("CONVERT (nvarchar, ne.fechaApertura ,112)>=")
					.append(DateUtils.formatearBD(fechaHistorico)).append(" AND ")
					.append("ne.tipoExpediente=").append(TipoExpediente.CAUSA.getValorId())
					.append(" AND nes.tipoExpediente=").append(TipoExpediente.CARPETA_DE_EJECUCION.getValorId())
					.append(" AND nes.estatus=").append(EstatusExpediente.CERRADO.getValorId());

		
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NumeroExpediente> consultarNumeroExpedientePorFiltro(
			Date fechaInicio, Date fechaFin, Funcionario usuario,
			TipoExpediente tipo, Long numeroExpedientePadreId) {
		StringBuffer query = new StringBuffer();
		
		query.append("from NumeroExpediente ne where 1=1 ");
		
		if(usuario.getDiscriminante() != null && usuario.getDiscriminante().getCatDiscriminanteId() != null){
			query.append(" and ne.expediente.discriminante.catDiscriminanteId = ");
			query.append(usuario.getDiscriminante().getCatDiscriminanteId());
		}
		
		if(fechaInicio != null && fechaFin != null){
			query.append(" AND CONVERT (nvarchar, ne.fechaApertura, 112)  BETWEEN ").append(DateUtils.formatearBD(fechaInicio))
			.append(" AND ").append(DateUtils.formatearBD(fechaFin));
		}
		else{
			if(fechaInicio != null){
				query.append(" and CONVERT (nvarchar, ne.fechaApertura, 112) >=  ");
				query.append(DateUtils.formatearBD(fechaInicio));
			}
			if(fechaFin != null){
				query.append(" and CONVERT (nvarchar, ne.fechaApertura, 112)  <= ");
				query.append(DateUtils.formatearBD(fechaFin));
			}		
		}
		
		if(usuario.getClaveFuncionario() != null){
			query.append(" and ne.funcionario.claveFuncionario = ");
			query.append(usuario.getClaveFuncionario());
			
		}
		
		if(tipo != null){
			query.append(" and ne.tipoExpediente.valorId =  ");
			query.append(tipo.getValorId());
			
		}
		if(numeroExpedientePadreId != null){
			query.append(" and ne.numeroExpedientePadre.numeroExpedienteId =  ");
			query.append(numeroExpedientePadreId);
			
		}
		query.append(" order by ne.fechaApertura desc");
				
//		final PaginacionDTO pag = PaginacionThreadHolder.get();
//	    logger.debug("pag :: " + pag);
//	    if(pag!=null && pag.getCampoOrd() != null){
//         	query.append(" ORDER BY ");
//	    	int orden = NumberUtils.toInt(pag.getCampoOrd(), 0);
//	    	switch(orden){
//		    	case 2025: //Tipo Defensa
//		    		query.append(" f.tipoEspecialidad ");
//		    		break;
//		    	case 2027: // Especialidad
//		    		query.append(" f.especialidad ");
//		    		break;
//		    	case 2026: // Nombre 
//		    	default:
//		    		query.append(" f.nombreFuncionario asc, ");
//		    		query.append(" f.apellidoPaternoFuncionario asc, ");
//		    		query.append(" f.apellidoMaternoFuncionario asc");
//		    		break;		    		
//		    	}
//	    	query.append(" "+pag.getDirOrd());
//	    }
//	    logger.debug("query :: " + query);
//	    return super.ejecutarQueryPaginado(query, pag);
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);
		
		
	}

	@Override
	public NumeroExpediente obtenerNumeroExpedienteXExpediente(
			Long expedienteId) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append(" FROM NumeroExpediente ne");
		queryString.append(" WHERE ne.expediente.expedienteId="+expedienteId);
		
		Query query = super.getSession().createQuery(queryString.toString());
		
//		return (NumeroExpediente) query.uniqueResult();
		return (NumeroExpediente) query.list().get(0);
	}
	
    @Override
    public List<NumeroExpediente> consultarNumeroExpedientesXExpediente(
            Long expedienteId) {
        StringBuilder queryString = new StringBuilder();
        
        queryString.append(" FROM NumeroExpediente ne");
        queryString.append(" WHERE ne.expediente.expedienteId="+expedienteId);
        
        Query query = super.getSession().createQuery(queryString.toString());
        
//      return (NumeroExpediente) query.uniqueResult();
        return query.list();
    }	
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO#consultarNumeroExpedientePorNumeroCaso(java.lang.String)
	 */
	@Override
	public List<NumeroExpediente> consultarNumeroExpedientePorNumeroCaso(
			String caso) {
		
		return getHibernateTemplate().find("from NumeroExpediente ne where ne.expediente.caso.numeroGeneralCaso = ?",caso);
		
		
	}

	@Override
	public NumeroExpediente obtenerCarpetaEjecucionByCaso(String numeroCaso,
			Long tipoNumExpediente) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM NumeroExpediente ne WHERE ")
					.append("ne.expediente.caso.numeroGeneralCaso = '")
					.append(numeroCaso).append("' AND ne.tipoExpediente=")
					.append(tipoNumExpediente);
		Query query = super.getSession().createQuery(queryString.toString());
		return (NumeroExpediente) query.uniqueResult();
	}

	@Override
	public NumeroExpediente obtenerCausaByExpediente(Long expedienteId) {		
		StringBuilder queryString = new StringBuilder();
		
		queryString.append(" FROM NumeroExpediente ne")
					.append(" WHERE ne.expediente.expedienteId=").append(expedienteId)
					.append(" AND ne.tipoExpediente=").append(TipoExpediente.CAUSA.getValorId());	;
			
		Query query = super.getSession().createQuery(queryString.toString());
		return (NumeroExpediente) query.uniqueResult();		
	}

	@Override
	public List<NumeroExpediente> consultarNumeroExpedienteByTipoYEstatus(
            Long tipoExp, Long estatusExp,Long discriminanteId) {
        StringBuffer queryString = new StringBuffer();

        queryString.append(" FROM NumeroExpediente ne").append(" WHERE 1=1 ");
        if (tipoExp != null) {
            queryString.append(" AND ne.tipoExpediente=").append(tipoExp);
        }
        if (estatusExp != null) {
            queryString.append(" AND ne.estatus=").append(estatusExp);
        }
        if(!discriminanteId.equals(0L)){
        	queryString.append(" AND ne.expediente.discriminante.catDiscriminanteId=").append(discriminanteId);
        }
        logger.debug("queryString :: " + queryString);
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        return super.ejecutarQueryPaginado(queryString, pag);

	}

	@Override
	public List<NumeroExpediente> consultarNumeroExpedienteByTipoYEstatus(Long estatusExp,Long CatUIED) {
	    StringBuffer queryString = new StringBuffer();

	    
	    if(estatusExp!= null && CatUIED > 0L){

	    	queryString.append("select ne.* from NumeroExpediente as ne,Expediente as e,Delito as d,CatDelito as ct" +
		    		" where  ne.Estatus_val in("+estatusExp+")  and d.bEsPrincipal=1 and ne.Expediente_id=e.Expediente_id" +
		    		" and d.Expediente_id=ne.Expediente_id and d.CatDelito_id=ct.CatDelito_id");
		    queryString.append(" And ct.catUIE_id=").append(CatUIED);
	
	    }
	    
	    logger.debug("queryString :: " + queryString);
	    final PaginacionDTO pag = PaginacionThreadHolder.get();

	    return (List<NumeroExpediente>) super.ejecutarQueryPaginadoSQL(queryString, pag,NumeroExpediente.class);
	    		
	}
	
	 	

	

	
	

	@Override
	public List<NumeroExpediente> consultarnumExpedienteHijos(
			Long numeroExpedienteId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM NumeroExpediente ne WHERE")
					.append(" ne.numeroExpedientePadre=").append(numeroExpedienteId);
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	public NumeroExpediente consultarNumeroExpedienteXExpedienteId(
			Long expedienteId) {

		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM NumeroExpediente ne WHERE")
					.append(" ne.expediente.expedienteId=").append(expedienteId);
		Query query = super.getSession().createQuery(queryString.toString());
		return (NumeroExpediente) query.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> obtenerNumExpPorEstatusYMes(Date fechaInicial,
			Date fechaFinal, EstatusExpediente estatusExpediente) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT MONTH(ne.fechaApertura), COUNT(*) FROM NumeroExpediente ne")
					.append(" WHERE ne.estatus=").append(estatusExpediente.getValorId())
					.append(" AND CONVERT (varchar, ne.fechaApertura, 112) BETWEEN ")
					.append(DateUtils.formatearBD(fechaInicial)).append(" AND ")
					.append(DateUtils.formatearBD(fechaFinal))
					.append("GROUP BY MONTH(ne.fechaApertura)");
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	public NumeroExpediente obtenerCarpetaEjecucionPorInvolucrado(
			Long idInvolucrado) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT s.numeroExpediente ")
					.append("FROM Sentencia s ")
					.append("WHERE s.involucrado=").append(idInvolucrado);
		Query query = super.getSession().createQuery(queryString.toString());		
		return (NumeroExpediente)query.uniqueResult();
	}
	
	@Override
	public NumeroExpediente buscarNumeroExpedientePorCasoFolioImputado(
			String numeroCaso, String folioImputado, Long claveFuncionario) {
	    final StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("select ne from NumeroExpediente ne inner join ne.funcionario f inner join ne.expediente e inner join e.caso c left outer join e.elementos ele");
		hqlQuery.append(" WHERE ele.folioElemento = '"+folioImputado+"'");
		hqlQuery.append(" AND ele.calidad.tipoCalidad.valorId = "+Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId());
		hqlQuery.append(" AND c.numeroGeneralCaso= '"+numeroCaso+"'");
		if(claveFuncionario != null){
			hqlQuery.append(" AND f.claveFuncionario = "+claveFuncionario);
		}
		logger.debug("hqlQuery :: " + hqlQuery);
		Query query = super.getSession().createQuery(hqlQuery.toString());	
		return (NumeroExpediente)query.uniqueResult();
	}

    @Override
    public NumeroExpediente obtenerExpedienteDefensaPorCasoFolioImputado(
            String numeroCaso, String folioImputado, Long claveFuncionario) {
        final StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append("select ne from NumeroExpediente ne inner join ne.funcionario f inner join ne.expediente e inner join e.caso c inner join ne.defensaInvolucrado d inner join d.involucrado i");
        hqlQuery.append(" WHERE i.folioElemento = '"+folioImputado+"'");
        hqlQuery.append(" AND i.calidad.tipoCalidad.valorId = "+Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId());
        hqlQuery.append(" AND c.numeroGeneralCaso= '"+numeroCaso+"'");
        if(claveFuncionario != null){
            hqlQuery.append(" AND f.claveFuncionario = "+claveFuncionario);
        }
        logger.debug("hqlQuery :: " + hqlQuery);
        Query query = super.getSession().createQuery(hqlQuery.toString());  
        return (NumeroExpediente)query.uniqueResult();
    }

	@Override
	public NumeroExpediente consultarNumExpPorFuncionarioYNumExp(
			Long claveFuncionario, Long numExpId) {
		Calendar diaActual = Calendar.getInstance();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT ne FROM NumeroExpediente ne LEFT JOIN ne.permisoExpedientes nep ")
					.append("WHERE (nep.funcionario=").append(claveFuncionario)
					.append(" AND CONVERT (varchar, nep.fechaLimite, 112)>=").append(DateUtils.formatearBD(diaActual.getTime()))
					.append(") OR (ne.funcionario=").append(claveFuncionario).append(") ")			
					.append(" AND ne.numeroExpedienteId=").append(numExpId);
		Query query = super.getSession().createQuery(queryString.toString());		
		return (NumeroExpediente) query.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarNumExpPorFuncionario(
			Long claveFuncionario) {
		Calendar diaActual = Calendar.getInstance();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT ne FROM NumeroExpediente ne LEFT JOIN ne.permisoExpedientes nep ")
					.append("WHERE (nep.funcionario=").append(claveFuncionario)
					.append(" AND CONVERT (varchar, nep.fechaLimite, 112)>=").append(DateUtils.formatearBD(diaActual.getTime()))
					.append(") OR (ne.funcionario=").append(claveFuncionario).append(")");
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(queryString, pag);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarExpedientesDelFuncionario(Funcionario funcionario) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM NumeroExpediente ne WHERE ")
					.append("ne.funcionario=").append(funcionario.getClaveFuncionario());
		if(funcionario.getDiscriminante() != null
				&& funcionario.getDiscriminante().getCatDiscriminanteId() != null
				&& funcionario.getDiscriminante().getCatDiscriminanteId() > 0){
			//Se corrige la consulta a la nueva versión 
			//queryString.append(" AND ne.expediente.discriminante.valorId=").append(agenciaId);
			queryString.append(" AND ne.expediente.discriminante.catDiscriminanteId=").append(funcionario.getDiscriminante().getCatDiscriminanteId());
		}

		if(funcionario.getArea() != null
				&& funcionario.getArea().getJerarquiaOrganizacionalId() != null
				&& funcionario.getArea().getJerarquiaOrganizacionalId() > 0L){
			queryString.append(" AND ne.jerarquiaOrganizacional.jerarquiaOrganizacionalId in (");
			if(funcionario.getArea().getJerarquiaOrgSubordinadas() != null
					&& !funcionario.getArea().getJerarquiaOrgSubordinadas().isEmpty()){
			
				for (JerarquiaOrganizacional jerarquiaOrganizacional: funcionario.getArea().getJerarquiaOrgSubordinadas()) {
					queryString.append(jerarquiaOrganizacional.getJerarquiaOrganizacionalId() + ", ");
				}
			}
			queryString.append(funcionario.getArea().getJerarquiaOrganizacionalId());
			queryString.append("," + Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal() +  " )");
		}
		
		logger.info("Query:" + queryString);
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(queryString, pag);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarExpedientesPorFiltroST(
			Date fechaInicio, Date fechaFin, Long Area,
			List<Long> estatusExpediente,Long discriminanteId, Long rolId, Long idDistrito, Long idFuncionario) {

		
		String idsEstatus = "";
		for (Long id : estatusExpediente) {
			idsEstatus = idsEstatus + id + ",";
		}
		idsEstatus = idsEstatus.substring(0, idsEstatus.lastIndexOf(","));
		
		
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM NumeroExpediente ne")
		.append(" WHERE 1=1 ");
		
		queryStr.append(" AND ne.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").append(Area);
		
		queryStr.append(" AND ne.estatus IN (");
			queryStr.append(idsEstatus);
		queryStr.append(")");
		
		
		
		
		if(fechaInicio != null && fechaFin != null){
			queryStr.append(" AND CONVERT (varchar, ne.fechaApertura, 112)  BETWEEN ").append(DateUtils.formatearBD(fechaInicio))
			.append(" AND ").append(DateUtils.formatearBD(fechaFin));
		}
		if(discriminanteId != null && discriminanteId >0){
			queryStr.append(" AND ne.expediente.discriminante.catDiscriminanteId=").append(discriminanteId);
		}
		
		if(rolId != null && rolId > 0){
			queryStr.append(" AND ne.funcionario.claveFuncionario IN(");			
				queryStr.append(" SELECT F.claveFuncionario FROM Funcionario F ").append(
				" LEFT JOIN  F.usuario.usuarioRoles as UR ").append(
				" WHERE UR.rol.rolId = ").append(rolId).append(	
				" AND F.usuario.esActivo= ").append(Boolean.TRUE);
			queryStr.append(")");
			
		}
		
		if(idDistrito != null && idDistrito > 0 ){
			queryStr.append(" AND ne.expediente.discriminante.distrito.catDistritoId=").append(idDistrito);
		}		
		
		if(idFuncionario != null && idFuncionario > 0 ){
			queryStr.append(" AND ne.funcionario.claveFuncionario = ").append(idFuncionario);
		}
		
		logger.info("Query:" + queryStr);
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(queryStr, pag);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> obtenerNumExpPorFuncionarioYEstatus(
			Long idFuncionario, Long estus) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM NumeroExpediente ne ")
					.append("WHERE ne.funcionario=").append(idFuncionario)
					.append(" AND ne.estatus=").append(estus);
		
		Query query = super.getSession().createQuery(queryString.toString());
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NumeroExpediente> consultarNumeroDeExpedienteConHechoPorFiltros(
			EstatusExpediente estatusExpediente, Long discriminante,
			Date fechaInicio, Date fechaFin) {

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ne FROM NumeroExpediente ne inner join ne.expediente e inner join e.hecho h inner join h.avisoHechoDelictivo avi");
		sb.append(" WHERE ne.estatus = '"+estatusExpediente.getValorId()+"'");
		
		if (discriminante != null){
			sb.append(" AND e.discriminante.catDiscriminanteId=").append(discriminante);
		}
		
		if(fechaInicio != null && fechaFin != null){
			sb.append(" AND CONVERT (varchar, avi.fechaAtencion, 112)  BETWEEN ").append(DateUtils.formatearBD(fechaInicio))
			.append(" AND ").append(DateUtils.formatearBD(fechaFin));
		}
		
		
		if(fechaInicio != null && fechaFin == null){
			sb.append(" AND CONVERT (varchar, avi.fechaAtencion, 112)>=").append(DateUtils.formatearBD(fechaInicio));
		}
		
		if(fechaInicio == null && fechaFin != null){
			sb.append(" AND CONVERT (varchar, avi.fechaAtencion, 112)<=").append(DateUtils.formatearBD(fechaInicio));
		}

		logger.info("Query:" + sb);
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(sb, pag);
	}	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NumeroExpediente> consultarNumeroDeExpedienteSinHechoPorFiltros(
			EstatusExpediente estatusExpediente, Long discriminante,
			Date fechaInicio, Date fechaFin) {

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ne FROM NumeroExpediente ne");
		sb.append(" WHERE ne.expediente.hecho.hechoId NOT IN ( SELECT h.hechoId FROM Hecho h, AvisoHechoDelictivo avi WHERE avi.hecho.hechoId = h.hechoId )");
		sb.append(" AND ne.estatus = '"+estatusExpediente.getValorId()+"'");
		
		if (discriminante != null){
			sb.append(" AND ne.expediente.discriminante.catDiscriminanteId=").append(discriminante);
		}
		
		if(fechaInicio != null && fechaFin != null){
			sb.append(" AND CONVERT (varchar, ne.fechaApertura, 112)  BETWEEN ").append(DateUtils.formatearBD(fechaInicio))
			.append(" AND ").append(DateUtils.formatearBD(fechaFin));
		}
		
		
		if(fechaInicio != null && fechaFin == null){
			sb.append(" AND CONVERT (varchar, ne.fechaApertura, 112)>=").append(DateUtils.formatearBD(fechaInicio));
		}
		
		if(fechaInicio == null && fechaFin != null){
			sb.append(" AND CONVERT (varchar, ne.fechaApertura, 112)<=").append(DateUtils.formatearBD(fechaFin));
		}

		logger.info("Query:" + sb);
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(sb, pag);
	}
	
	
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarNumerosDeExpedientesPorRolST(List<Long> roles, Long idExpediente) {

		
		String idsRoles = "";
		for (Long id : roles) {
			idsRoles = idsRoles + id + ",";
		}
		
		if(idsRoles.length() > 0)
			idsRoles = idsRoles.substring(0, idsRoles.lastIndexOf(","));
		
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM NumeroExpediente ne")
		.append(" WHERE 1=1 ");
		
		if(idExpediente != null && idExpediente > 0 ){
			queryStr.append(" AND ne.expediente.expedienteId=").append(idExpediente);
		}		

		if(roles != null && roles.size() > 0){
			queryStr.append(" AND ne.funcionario.claveFuncionario IN(");			
				queryStr.append(" SELECT F.claveFuncionario FROM Funcionario F ").append(
				" LEFT JOIN  F.usuario.usuarioRoles as UR ").append(
				" WHERE UR.rol.rolId IN (").append(idsRoles).append(	
				" ) AND F.usuario.esActivo= ").append(Boolean.TRUE);
			queryStr.append(")");			
		}
		
		logger.info("Query:" + queryStr);
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(queryStr, pag);
	}

	@Override
	public Long consultarEstatusNumeroExpedienteByNumeroExpedienteId(
			Long numeroExpedienteId) {
		StringBuffer sb = new StringBuffer();

		sb.append(
				" SELECT estatus.valorId FROM NumeroExpediente ne WHERE ne.numeroExpedienteId = ")
				.append(numeroExpedienteId);

		Query query = super.getSession().createQuery(sb.toString());
		return (Long) query.uniqueResult();
	}

	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO#obtenerNumeroExpedienteAlterno(java.lang.String, java.lang.String)
	 */

/*	public String obtenerNumeroExpedienteAlternoConsecutivo(Integer iniConsecutivo, Integer finConsecutivo, Integer incremento, 
			List<String> unidades, String distrito, String anio,String monoEntFederativa) {
		Integer consecutivo = 0;
		try{
			StringBuilder sb = new StringBuilder("SELECT ISNULL(MAX(CAST(SUBSTRING(cNumExpAlterno," +
					iniConsecutivo.toString()+","+ 
					finConsecutivo.toString()+") AS int)),"+
					INICIO_CONSECUTIVO+")+"+
					incremento.toString());
			sb.append(" FROM NumeroExpediente ")
			.append("WHERE cNumExpAlterno LIKE '%")
			.append(unidades.get(0)+SEPARADOR_UNIDAD_ANIO+anio+SEPARADOR_ANIO_ENTIDAD+monoEntFederativa+SEPARADOR_ENTIDAD_DISTRITO+distrito)
			.append("'");
			
			if(unidades.size() > 1){
				for(int i=1;i<unidades.size();i++){
					sb.append(" OR cNumExpAlterno LIKE '%")
					.append(unidades.get(i)+SEPARADOR_UNIDAD_ANIO+anio+SEPARADOR_ANIO_ENTIDAD+monoEntFederativa+SEPARADOR_ENTIDAD_DISTRITO+distrito)
					.append("'");
				}
			}
			
			logger.info("Query : : " +  sb.toString());
			Query q = super.getSession().createSQLQuery(sb.toString());
			
			List<Integer> resultados =(List<Integer>) q.list();
			
			if (resultados != null && resultados.size() > 0){
				if (resultados.get(0) != null ){
					consecutivo = resultados.get(0);
					logger.info("CONSECUTIVO PARA EL EXPEDIENTE::::::::::::::::::::::"+consecutivo);
				}
			}			
		}catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		return crearNumeroExpedienteAlterno(consecutivo,unidades.get(0), anio, distrito,monoEntFederativa);
	}  */ 
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO#obtenerNumeroExpedienteAlterno(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String obtenerNumeroExpedienteAlternoConsecutivo(Integer iniConsecutivo, Integer finConsecutivo, Integer incremento, 
			List<String> unidades, String region, String anio, String agencia, String numeroExpediente, Long areaId) {
		Integer consecutivo = 0;
//		if (numeroExpediente == null || numeroExpediente.equals("")){
//		ENABLE IT: Se buscan expedientes que correspondan a las jerarquias 9 y 10 correspondientes a UI
		String areas = "";
		if (areaId==Areas.UNIDAD_INVESTIGACION.ordinal() || areaId==Areas.COORDINACION_UNIDAD_INVESTIGACION.ordinal()) {
			areas = Areas.UNIDAD_INVESTIGACION.ordinal() + ", " + Areas.COORDINACION_UNIDAD_INVESTIGACION.ordinal();
		} else 
			areas = areaId.toString();
			try{
				StringBuilder sb = new StringBuilder("SELECT ISNULL(MAX(cNumExpAlterno ),"+
						INICIO_CONSECUTIVO+") ");
				/*StringBuilder sb = new StringBuilder("SELECT ISNULL(MAX(CAST(SUBSTRING(cNumExpAlterno," +
						iniConsecutivo.toString()+","+ 
						finConsecutivo.toString()+") AS int)),"+
						INICIO_CONSECUTIVO+")+"+
						incremento.toString());*/
				sb.append(" FROM NumeroExpediente ")
				.append("WHERE JerarquiaOrganizacional_id in (" + areas +") ");
				if(unidades.size() > 1){
					sb.append(" AND cNumExpAlterno LIKE '%")
					.append(SEPARADOR_UNIDAD_ANIO+agencia)
					.append("%'");
					if (unidades.contains(AcronimoNumExpAlterno.UNIDAD_INVESTIGACION.getAcronimo()))
						esUIE = true;
				}
				sb.append(" AND cNumExpAlterno LIKE '%")
				.append(SEPARADOR_UNIDAD_ANIO+anio)
				.append("%'");
//				if(unidades.size() > 1){
					
//					for(int i=1;i<unidades.size();i++){
//						sb.append(" OR cNumExpAlterno LIKE '%")
//	//					.append(unidades.get(i) + SEPARADOR_UNIDAD_ANIO+anio+SEPARADOR_ANIO_ENTIDAD+monoEntFederativa+SEPARADOR_ENTIDAD_DISTRITO+distrito)
//						.append(unidades.get(i) + SEPARADOR_UNIDAD_ANIO+anio)
//						.append("'");
//					}
					
//					if (unidades.contains(AcronimoNumExpAlterno.UNIDAD_INVESTIGACION.getAcronimo()))
//						esUIE = true;
//				}
				logger.info("Area IDDDDDD ::::::::::  " + areaId);
//				if(areaId == Areas.ATENCION_TEMPRANA_PG_NO_PENAL.ordinal() 
//						|| areaId == Areas.ATENCION_TEMPRANA_PG_PENAL.ordinal() 
//						|| areaId == Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal()
//						|| areaId == Areas.UNIDAD_INVESTIGACION.ordinal()
//						|| areaId == Areas.COORDINACION_UNIDAD_INVESTIGACION.ordinal()
//						|| areaId == Areas.FISCAL_FACILITADOR.ordinal())
//					sb.append(" AND JerarquiaOrganizacional_id = " +areaId );
				
				logger.info("Query : : " +  sb.toString());
				Query q = super.getSession().createSQLQuery(sb.toString());
				
				List<String> resultados =(List<String>) q.list();
				
				if (resultados != null && resultados.size() > 0){
					if (resultados.get(0) != null ){
						String[] ultimo	= resultados.get(0).split("/");
						if( ultimo.length > 1 ){
							if( areas.equals("9") || areas.equals("10") || areas.equals("9, 10") || areas.equals("10, 9") ){
								consecutivo = Integer.parseInt(ultimo[2])+1;
							}
							else{
								consecutivo = Integer.parseInt(ultimo[0])+1;
							}
						}
						else{
							consecutivo = Integer.parseInt(ultimo[0])+1;
						}
						//consecutivo = resultados.get(0);
						logger.info("CONSECUTIVO PARA EL EXPEDIENTE::::::::::::::::::::::"+consecutivo);
					}
				}	
			}catch (Exception e){
				logger.error(e.getMessage(), e);
			}
//		}
			
		return crearNumeroExpedienteAlterno(consecutivo,unidades.get(0), anio, region, agencia, numeroExpediente);
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n de un n&uacute;mero de expediente alterno
	 * @param consecutivo - El consecutivo del n&uacute;mero de expediente alterno a generar.
	 * @param unidad - La unidad a la cual pertenece el n&uacute;mero de expediente alterno
	 * @param anio - El a&ntilde;o con el cual se va a generar el n&uacute;mero de expediente alterno.
	 * @param distrito - El distrito con el cual se va a asociar el n&uacute;mero de expediente alterno.
	 * @param entidadFederativa - El monograma de la entidad federativa de despliegue 
	 * @return consecutivoCompleto - el n&uacute;mero de expediente alterno generado. 
	 */
	public String crearNumeroExpedienteAlterno (Integer consecutivo, String unidad, String anio, String region,String agencia, String numeroExpediente){
		String consecutivoCompleto = "";
	
//		if (numeroExpediente == null || numeroExpediente.equals("")){
			
			if (consecutivo < 10 ){
				consecutivoCompleto = "0000"+consecutivo.toString();
			}else if (consecutivo < 100){
				consecutivoCompleto = "000"+consecutivo.toString();
			}else if (consecutivo < 1000){
				consecutivoCompleto = "00"+consecutivo.toString();
			}else if (consecutivo < 10000){
			consecutivoCompleto = "0"+consecutivo.toString();
			}
			else if (consecutivo < 100000){
				consecutivoCompleto = consecutivo.toString();
			}
//		}
//		else
//			consecutivoCompleto = numeroExpediente.substring(0,5);
		
		logger.debug("consecutivoCompleto : : : " + consecutivoCompleto);
		
//		distrito = (distrito != null)?AcronimoNumExpAlterno.ATENCION_TEMPRANA_PG_PENAL.getAcronimo():"";
		logger.debug("Unidad : : : :: : " +  unidad);
		
		
		if(esUIE){
			esUIE = false;
			return region + SEPARADOR_UNIDAD_ANIO + agencia + SEPARADOR_UNIDAD_ANIO + consecutivoCompleto + SEPARADOR_UNIDAD_ANIO + anio;
		}
		else if (unidad.equalsIgnoreCase(AcronimoNumExpAlterno.ATENCION_TEMPRANA_PG_PENAL.getAcronimo()))
			return consecutivoCompleto + SEPARADOR_UNIDAD_ANIO + unidad + SEPARADOR_UNIDAD_ANIO + region + SEPARADOR_UNIDAD_ANIO + anio;
		else if (unidad.equalsIgnoreCase(AcronimoNumExpAlterno.ATENCION_TEMPRANA_PG_NO_PENAL.getAcronimo()))
			return consecutivoCompleto + SEPARADOR_UNIDAD_ANIO + unidad + SEPARADOR_UNIDAD_ANIO + region + SEPARADOR_UNIDAD_ANIO + anio;
		else if (unidad.equalsIgnoreCase(AcronimoNumExpAlterno.JUSTICIA_ALTERNATIVA_RESTAURATIVA.getAcronimo()))
			return consecutivoCompleto + SEPARADOR_UNIDAD_ANIO + region + SEPARADOR_UNIDAD_ANIO + unidad + SEPARADOR_UNIDAD_ANIO + anio;
		else
			return consecutivoCompleto + SEPARADOR_UNIDAD_ANIO + agencia +  SEPARADOR_UNIDAD_ANIO + unidad + SEPARADOR_UNIDAD_ANIO + anio;

	}

	@Override
	public String consultarNumeroExpedienteAlterno(Long numeroExpedienteId) {
		
		StringBuilder sb = new StringBuilder("SELECT numExpAlterno FROM NumeroExpediente ne WHERE ne.numeroExpedienteId = ")
				.append(numeroExpedienteId);
		Query query = super.getSession().createQuery(sb.toString());
		
		return (String) query.uniqueResult();
	}
	
	@Override
	public String consultarPosInicialNumExp(Long areaId, String agencia){
	
		StringBuilder sb = new StringBuilder("SELECT PATINDEX('%/[0-9]%', cNumExpAlterno) + 1 ")
					.append(" FROM NumeroExpediente ")
					.append(" WHERE JerarquiaOrganizacional_id = ").append(areaId)
					.append(" AND cNumExpAlterno LIKE '%")
					.append(agencia).append("%'")
					.append(" ORDER BY NumeroExpediente_id desc");
		
		logger.info("Query : : " +  sb.toString());
		
		Query query = super.getSession().createSQLQuery(sb.toString());
		
		if(query.uniqueResult() != null)
			return query.uniqueResult().toString();

		return (String) query.uniqueResult();
		
	}

	@Override
	public List<NumeroExpediente> consultarExpedientesCompartidosAFuncionario(
			Long claveFuncionario) {
		Calendar diaActual = Calendar.getInstance();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT ne FROM NumeroExpediente ne LEFT JOIN ne.permisoExpedientes nep ")
					.append("WHERE nep.funcionario=").append(claveFuncionario)
					.append(" AND CONVERT (varchar, nep.fechaLimite, 112)>=").append(DateUtils.formatearBD(diaActual.getTime()));			
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(queryString, pag);
	}
}
