package mx.gob.segob.nsjp.dao.documento.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.SolicitudDefensorDelito;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AvisoDesignacionDAOImpl extends GenericDaoHibernateImpl<AvisoDesignacion, Long>
		implements AvisoDesignacionDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<AvisoDesignacion> consultarAvisosDesignacion(Long estado,
			Long claveFuncionario) throws NSJPNegocioException {
		final StringBuffer query = new StringBuffer();
		query.append("from AvisoDesignacion d");
		query.append(" where d.estatus = "+estado);
		if(claveFuncionario != null){
			query.append(" and d.funcionario.claveFuncionario = "+claveFuncionario);
		}
		final PaginacionDTO pag = PaginacionThreadHolder.get();
	    logger.debug("pag :: " + pag);
	    if(pag!=null && pag.getCampoOrd() != null){
	    	query.append(" ORDER BY ");
	    	int orden = NumberUtils.toInt(pag.getCampoOrd(), 0);
	    	switch(orden){
		    	case 2002: //Numero General de Caso
		    		query.append(" d.expediente.caso.numeroGeneralCaso");
		    		break;
		    	case 2006: // Defensor
		    		query.append(" d.funcionario.claveFuncionario");
		    		break;
		    	case 2007: // Fecha hora designacion
		    		query.append(" d.fechaCreacion");
		    		break;
		    	case 2041: // Folio Notificacion
		    		query.append(" d.folioNotificacion");
		    		break;
		    	case 2042: // Audiencia FIXME DAJV poner ruta correcta 
		    		query.append(" d.fechaCreacion");
		    		break;
		    	case 2001: // Institucion Origen
		    		//FIXME VAP Es necesario hacer un ordenamiento en base a la institucion origen de la solicitud y la notificacion
		    	case 2003: // Numero de Expediente
		    		query.delete(0, query.length());
		    		query.append("select d");
		    		query.append(" from AvisoDesignacion d inner join d.expediente e, inner join e.numeroExpedientes n");
		    		query.append(" where d.estatus = "+estado);
		    		if(claveFuncionario != null){
		    			query.append(" and d.funcionario.claveFuncionario = "+claveFuncionario);		    		
		    		}
		    		query.append(" n.numeroExpediente");
		    	case 2005: // Involucrado es detenido
		    		//FIXME DAJV poner la ruta de ordenamientos
		    	default:
		    		query.append(" d.documentoId");
		    		break;
	    	}
	    	query.append(" "+pag.getDirOrd());
	    }
	    
	    return super.ejecutarQueryPaginado(query, pag);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AvisoDesignacion> consultarAvisosDesignacionPorIdExpediente(Long idExpediente) throws NSJPNegocioException{
		final StringBuffer query = new StringBuffer();
		query.append("from AvisoDesignacion d");
		query.append(" where d.expediente.expedienteId = "+idExpediente);
		query.append(" order by d.documentoId desc");
		Query hquery = super.getSession().createQuery(query.toString());
		return (List<AvisoDesignacion>)hquery.list();
	}
	
	public AvisoDesignacion consultarAvisosDesignacionPorIdAvisoDetencion(Long avisoDetencionID) throws NSJPNegocioException{
		final StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append(" FROM AvisoDesignacion di ")
		.append(" WHERE di.avisoDetencion ="+avisoDetencionID);
		Query query = super.getSession().createQuery(hqlQuery.toString());
		return (AvisoDesignacion) query.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AvisoDesignacion> obtenerAvisosDesignacionPorEstatus(Long estatus, Long discriminanteId) {
		final StringBuffer aviso = new StringBuffer();
		aviso.append(" FROM  AvisoDesignacion ad LEFT JOIN ad.expediente ex LEFT JOIN ex.elementos ele LEFT JOIN ad.solicitudDefensor sd ")
		     .append(" LEFT JOIN sd.solicitudDefensorDelitos sdd LEFT JOIN sdd.delito  dl  ")
			 .append(" WHERE ad.confInstitucion = 1 ")
			 .append(" AND ad.estatus = 2143 ")
			 .append(" AND ele.tipoElemento = 427   ")
			 .append(" AND ele.expediente 	= ex.expedienteId ");
		final PaginacionDTO pag 				= PaginacionThreadHolder.get();
	    ///List<AvisoDesignacion> listDesignacion  = super.ejecutarQueryPaginado(aviso, pag);
	    List<Object[]> listObj	  				= super.ejecutarQueryPaginado(aviso, pag);
	    List<AvisoDesignacion> listDesignacion	= new  ArrayList<AvisoDesignacion>();    
	    for(Object[] obj  : listObj ){
	    	AvisoDesignacion desig	= (AvisoDesignacion) obj[0];
	    		if(!listDesignacion.contains(desig) ){
	    	    	desig.setExpediente((Expediente)obj[1]);
	    	    	desig.setInvolucrado( (Involucrado)obj[2]);
	    	    	SolicitudDefensor solicitud 				= new SolicitudDefensor();
	    	    	Set<SolicitudDefensorDelito> solDefDelitos	= new HashSet<SolicitudDefensorDelito>();
	    	    	SolicitudDefensorDelito defensorDelitos		= new SolicitudDefensorDelito();
	    	    	defensorDelitos 	= (SolicitudDefensorDelito) obj[4];
	    	    	defensorDelitos.setDelito((Delito)obj[5]);
	    	    	solDefDelitos.add(defensorDelitos);
	    	    	solicitud			= (SolicitudDefensor)obj[3];
	    	    	solicitud.setSolicitudDefensorDelitos(solDefDelitos);
	    	    	desig.setSolicitudDefensor(solicitud);
	    	    	
	    	    	final StringBuffer nombre = new StringBuffer();
	    	    	nombre.append(" FROM  NombreDemografico nd ")
	    	    		  .append(" WHERE nd.persona.elementoId =").append(desig.getInvolucrado().getElementoId() );
	    			Query query = super.getSession().createQuery(nombre.toString());
	    			NombreDemografico nomDem	= new NombreDemografico();
	    			if(query.list().size() > 1 ){
	    				nomDem		=  (NombreDemografico) query.list().get(0);
	    			}
	    			else{
		    			nomDem		=  (NombreDemografico) query.uniqueResult();    				
	    			}

	    			Set<NombreDemografico> nombres	= new HashSet<NombreDemografico>();
	    			nombres.add(nomDem);
	    			desig.getInvolucrado().setNombreDemograficos(nombres);
	    			listDesignacion.add(desig);
	    		}

	    }
	    return listDesignacion;

	}
	
	
}
