/**
* Nombre del Programa : AvisoDetencionDAOImpl.java
* Autor                            : Hugo Serrano
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.dao.avisodetencion.impl;


import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.avisodetencion.AvisoDetencionDAO;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.AvisoDetencion;
import mx.gob.segob.nsjp.model.Detencion;
import mx.gob.segob.nsjp.model.Documento;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author Tattva-IT
 *
 */

@Repository
public class AvisoDetencionDAOImpl extends GenericDaoHibernateImpl<AvisoDetencion, Long>
			implements AvisoDetencionDAO {

	@Override
	public Boolean involucradoTieneSolicitudDefensor(Long idInvolucrado){
		final StringBuffer qryStr = new StringBuffer();
		qryStr.append("select d.avisoDetencion from Detencion d where d.involucrado.elementoId=")
			.append(idInvolucrado);
		Query query = super.getSession().createQuery(qryStr.toString());
		@SuppressWarnings("unchecked")
		List<AvisoDetencion> listAvsDet = query.list();
		if(listAvsDet != null && listAvsDet.size() > 0) return true; return false;
	}
	
	@Override
	public Boolean involucradoTieneDenuncia(Long idInvolucrado){
		boolean existeDen	= false;
		final StringBuffer strEle = new StringBuffer();
		strEle.append(" FROM  Actividad a WHERE a.expediente.expedienteId=").append(idInvolucrado);
		Query qryEle 		= super.getSession().createQuery(strEle.toString());
		@SuppressWarnings("unchecked")
		List<Actividad> listActDen 	= qryEle.list();
		if( listActDen != null && listActDen.size() > 0  ){
			for( Actividad act	: listActDen )  {
				if( act.getDocumento() != null ){
					StringBuffer strDoc = new StringBuffer();
					strDoc.append(" from Documento d where d.documentoId=").append(act.getDocumento().getDocumentoId());
					Query qryDoc	= super.getSession().createQuery(strDoc.toString());
					Documento doc 	= (Documento) qryDoc.uniqueResult();
					if( doc.getTipoDocumento().getValorId().toString().equals("2078") ){
						existeDen	= true;
						break;
					}
					else{ existeDen	= false;}
				}
			}
			
		}
		return existeDen;
	}
	
	@Override
	public Boolean tieneAvisoDetencion(Long idInvolucrado){
		boolean existeDen			= false;
		final StringBuffer strQuery = new StringBuffer();
		strQuery.append(" FROM Detencion d  WHERE d.involucrado = ").append(idInvolucrado);
		Query qry 					= super.getSession().createQuery(strQuery.toString());
		Detencion detencion 		= (Detencion)qry.uniqueResult();
		if( detencion != null ){
			if( detencion.getDetencionId() != null ){
				StringBuffer strAviDet = new StringBuffer();
				strAviDet.append(" FROM AvisoDetencion ad WHERE ad.detencion.detencionId=").append( detencion.getDetencionId());
				Query qryAviDet	= super.getSession().createQuery(strAviDet.toString());
				AvisoDetencion ad 	= (AvisoDetencion) qryAviDet.uniqueResult();
				if( ad != null ){
					existeDen = true;
				}
				
			}
		}
		
		return existeDen;
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<AvisoDetencion> obtenerAvisosDetencionPorEstatus(Long estatus, Long discriminanteId) {

		final StringBuffer query = new StringBuffer();

		query.append("FROM  AvisoDetencion ad ")
				.append("WHERE  ad.estatus=").append(estatus);
		if(discriminanteId != null && discriminanteId > 0)
				query.append(" and ad.discriminanteDestino.catDiscriminanteId = " + discriminanteId);

		final PaginacionDTO pag = PaginacionThreadHolder.get();
	    logger.debug("pag :: " + pag);
	    if(pag!=null && pag.getCampoOrd() != null){
	    	query.append(" ORDER BY ");
	    	int orden = NumberUtils.toInt(pag.getCampoOrd(), 0);
	    	switch(orden){
		    	case 2002: //Numero General de Caso
		    		query.append(" ad.expediente.caso.numeroGeneralCaso");
		    		break;
		    	case 2009: // Imputado
		    		query.append(" ad.detenido");
		    		break;
		    	case 2012: // Fecha hora aviso
		    		query.append(" ad.fechaCreacion");
		    		break;
		    	case 2011: // Fecha hora detencion
		    		query.append(" ad.detencion.fechaFinDetencion");
		    		break;
		       		//FIXME DAJV poner la ruta de ordenamientos
		    	default:
		    		query.append(" ad.documentoId");
		    		break;
	    	}
	    	query.append(" "+pag.getDirOrd());
	    }

	    return super.ejecutarQueryPaginado(query, pag);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AvisoDetencion> consultarAvisosDetencionHistoricoByEstatus(
			Date fechHistorico, Long estatusAviso) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM AvisoDetencion ad WHERE ")
					.append("CONVERT (nvarchar, ad.fechaCreacion, 112)>=")
					.append(DateUtils.formatearBD(fechHistorico)).append(" AND ad.estatus=")
					.append(estatusAviso);
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	public List<AvisoDetencion> consultarAvisosDetencionPorExpediente(Long expedienteId) {


        final StringBuffer queryStr = new StringBuffer();

        queryStr.append("FROM  AvisoDetencion ad ")
                .append("WHERE  ad.expediente.expedienteId=").append(expedienteId);

        final Query query = super.getSession().createQuery(queryStr.toString());
        return query.list();


	}
	
	public AvisoDetencion consultarAvisoDetencionPorDetencionID(Long detencionId){
		final StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append(" FROM AvisoDetencion ad ")
		.append(" WHERE ad.detencion.detencionId ="+detencionId);
		Query query = super.getSession().createQuery(hqlQuery.toString());
		List<AvisoDetencion> adlist = query.list();
		if(adlist!=null && adlist.size()>0){
			return adlist.get(0);
		}
		else{
			return null;
		}
	}
	
	
	

}
