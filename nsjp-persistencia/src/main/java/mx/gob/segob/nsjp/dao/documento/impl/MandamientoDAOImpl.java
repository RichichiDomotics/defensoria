/**
 * Nombre del Programa : MandamientoDAOImpl.java
 * Autor                            : Emigdio
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 23/08/2011
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
package mx.gob.segob.nsjp.dao.documento.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.NumeroExpediente;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementación del DAO para el acceso a los mandamientos
 * 
 * @version 1.0
 * @author Emigdio
 * 
 */
@Repository
public class MandamientoDAOImpl
        extends
            GenericDaoHibernateImpl<Mandamiento, Long>
        implements
            MandamientoDAO {
    /*
     * (non-Javadoc)
     * 
     * @see mx.gob.segob.nsjp.dao.documento.MandamientoDAO#
     * consultarMandamientosPorNumeroExpediente(java.lang.String)
     */
	@SuppressWarnings("unchecked")
    @Override
    public List<Mandamiento> consultarMandamientosPorNumeroExpediente(
            String numeroExpediente,Long discriminanteId) {
		
		logger.info("Numero Expediente: " + numeroExpediente);
		
		 return getHibernateTemplate()
	                .find("from Mandamiento m where"
	                        + " m.resolutivo.audiencia.numeroExpediente.numeroExpediente = ? "
	                        + " and m.resolutivo.audiencia.numeroExpediente.expediente.discriminante.catDiscriminanteId = ? "
	                        + " order by m.resolutivo.audiencia.fechaAudiencia desc",
	                        numeroExpediente,discriminanteId);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<NumeroExpediente> consultarMandamientosJudicialesPorFiltro(
			MandamientoDTO mandamiento){
		
		final StringBuffer queryString = new StringBuffer();
		
		queryString.append("SELECT DISTINCT ne FROM Mandamiento ma LEFT JOIN ma.medida m LEFT JOIN m.numeroExpediente ne INNER JOIN ne.expediente e WHERE 1=1 ");
				
		if(mandamiento==null){
			Date hoy  = new Date();
			Calendar resta = Calendar.getInstance();
			// -3 -> meses a restar en la consulta. Dícese, de hace 3 meses a la fecha
			resta.add(Calendar.MONTH, -3);
			queryString.append(" AND CONVERT (varchar, e.fechaCreacion, 112) BETWEEN ").append(DateUtils.formatearBD(resta.getTime()))
						.append(" AND ").append(DateUtils.formatearBD(hoy));
		}
		else{
			if(mandamiento.getExpedienteDTO()!=null &&
			   mandamiento.getExpedienteDTO().getNumeroExpediente()!=null){
				queryString.append(" AND ne.numeroExpediente='"+mandamiento.getExpedienteDTO().getNumeroExpediente()+"'");
			}
			if(mandamiento.getFechaInicial()!=null && mandamiento.getFechaFinal()!=null){
				queryString.append(" AND CONVERT (varchar, e.fechaCreacion, 112) BETWEEN ")
						   .append(DateUtils.formatearBD(mandamiento.getFechaInicial()))
				           .append(" AND ").append(DateUtils.formatearBD(mandamiento.getFechaFinal()));				
			}
		}
		
		if(mandamiento.getUsuario()!=null &&
		   mandamiento.getUsuario().getFuncionario()!=null){
			
			if(mandamiento.getUsuario().getFuncionario().getClaveFuncionario()!=null){
				queryString.append(" AND ne.funcionario.claveFuncionario = " + 
						mandamiento.getUsuario().getFuncionario().getClaveFuncionario());
			}
			if(mandamiento.getUsuario().getFuncionario().getJerarquiaOrganizacional()!=null &&
					mandamiento.getUsuario().getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId()!=null){
					queryString.append(" AND ne.jerarquiaOrganizacional = " + 
							mandamiento.getUsuario().getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId());		
			}
		}
				
		queryString.append(" order by ne.numeroExpediente desc");									

        final PaginacionDTO pag = PaginacionThreadHolder.get();
        return super.ejecutarQueryPaginado(queryString, pag);        
	}

	@SuppressWarnings("unchecked")
    @Override
    public List<Mandamiento> consultarMandamientoPorFiltro(
			Mandamiento mandamiento, String numeroExpediente, Instituciones institucionActual) {
		
		
		StringBuffer queryString = new StringBuffer();
		if(institucionActual != null && institucionActual.getValorId() != null && institucionActual.getValorId().equals(Instituciones.PGJ.getValorId())){
			queryString.append("SELECT m from Mandamiento m LEFT JOIN m.actividad.expediente.numeroExpedientes numExps where 1=1 " );
		}else{
			queryString.append("SELECT m from Mandamiento m where 1=1");			
		}

        if(numeroExpediente != null && numeroExpediente!=""){
    		if(institucionActual != null && institucionActual.getValorId() != null && institucionActual.getValorId().equals(Instituciones.PGJ.getValorId())){
    			queryString.append(" and numExps.numeroExpediente ='").append(numeroExpediente).append("'");
        		
    		}else{
    			queryString.append(" and m.resolutivo.audiencia.numeroExpediente.numeroExpediente ='").append(numeroExpediente).append("'");
    		}    			
        }
		
        if(mandamiento!=null){
        	if(mandamiento.getFechaInicial()!=null && mandamiento.getFechaFinal()!=null){
            	queryString.append(" AND CONVERT (varchar, m.fechaCreacion, 112) BETWEEN ")
    					   .append(DateUtils.formatearBD(mandamiento.getFechaInicial()))
    			           .append(" AND ").append(DateUtils.formatearBD(mandamiento.getFechaFinal()));				
    		}		
        }
        
        if(mandamiento.getEstatus()!=null)
        	queryString.append(" and m.estatus.valorId =").append(mandamiento.getEstatus().getValorId());			

        logger.info(queryString);
		
        final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("m.fechaInicial");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("2")) {
				queryString.append(" order by ");
				queryString.append("m.fechaFinal");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("3")) {
				queryString.append(" order by ");
				queryString.append("m.tipoMandamiento.valorId");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("4")) {
				queryString.append(" order by ");
				queryString.append("m.tipoSentencia.valorId");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("5")) {
				queryString.append(" order by ");
				queryString.append("m.estatus.valorId");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("6")) {
				queryString.append(" order by ");
				queryString.append("m.fechaCreacion");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("7")) {
				queryString.append(" order by ");
				queryString.append("m.fechaEjecuacion");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		return super.ejecutarQueryPaginado(queryString, pag);  
	}


    @Override
    public Mandamiento obtenerMandamientoPorFolioDoc(String folioDocumento) {

        StringBuffer queryString = new StringBuffer();
        queryString.append(" FROM Mandamiento m  ")
                .append(" WHERE m.folioDocumento = '").append(folioDocumento)
                .append("'");
        Query query = super.getSession().createQuery(queryString.toString());
        return (Mandamiento) query.uniqueResult();
    }

}
