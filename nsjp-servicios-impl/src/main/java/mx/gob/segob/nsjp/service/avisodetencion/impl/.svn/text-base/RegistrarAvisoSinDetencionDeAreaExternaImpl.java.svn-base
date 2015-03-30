/**
* Nombre del Programa : RegistrarAvisoDetencionDeAreaExternaImpl.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 01/07/2011
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
package mx.gob.segob.nsjp.service.avisodetencion.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.involucrado.PersonalidadJuridica;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.involucrado.DetencionDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDelitoDAO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionWSDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoWSDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.CatDelito;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.SolicitudDefensorDelito;
import mx.gob.segob.nsjp.model.SolicitudDefensorDelitoId;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.avisodetencion.RegistrarAvisoSinDetencionDeAreaExterna;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedientePorCasoImputadoService;
import mx.gob.segob.nsjp.service.expediente.ClonarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de negocio para el registro de un aviso de detención
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
@Service("registrarAvisoSinDetencionDeAreaExterna")
@Transactional
public class RegistrarAvisoSinDetencionDeAreaExternaImpl implements RegistrarAvisoSinDetencionDeAreaExterna {
	
	private final static Logger logger = Logger.getLogger(RegistrarAvisoSinDetencionDeAreaExternaImpl.class);
	

	@Autowired
	private SolicitudDefensorDelitoDAO solicitudDefensorDelitoDAO;
	@Autowired
	private AvisoDesignacionDAO avisoDesignacionDAO;
	@Autowired
	private DetencionDAO detencionDAO;
	@Autowired
	private DelitoDAO delitoDAO;
	@Autowired
	private BuscarExpedientePorCasoImputadoService buscarExpedientePorCasoImputadoService;
	@Autowired
	private CasoDAO casoDAO;
	@Autowired
	private SolicitudDefensorDAO solicitudDAO;
	@Autowired
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private IngresarIndividuoService ingresarIndividuoService;
    @Autowired
    private ClonarExpedienteService clonarExpService;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.avisodetencion.RegistrarAvisoSinDetencionDeAreaExterna#registrarAvisoSinDetencion(mx.gob.segob.nsjp.dto.documento.AvisoDesignacionWSDTO)
	 */
    @Override
	public AvisoDesignacionWSDTO registrarAvisoSinDetencion(AvisoDesignacionWSDTO aviso,Long idAgencia, String datosAgencia,Long idFuncionarioSolicitante) throws NSJPNegocioException{
		logger.debug("Recibiendo :: " + aviso);
		
		if(aviso != null){
			InvolucradoDTO imputado = new InvolucradoDTO();
			imputado.setFolioElemento(aviso.getSolicitud().getFolioElemento()); 
			logger.info("Folio Elemento del Individuo ::: " + aviso.getSolicitud().getFolioElemento());
			logger.info("Folio Solicitud del Individuo ::: " + aviso.getSolicitud().getFolioSolicitud());
			NombreDemograficoDTO ndDTO = new NombreDemograficoDTO();
			ndDTO.setNombre(aviso.getSolicitud().getNombreImputado());
			ndDTO.setApellidoPaterno(aviso.getSolicitud().getApellidoPaternoImputado());
			ndDTO.setApellidoMaterno(aviso.getSolicitud().getApellidoMaternoImputado());
			imputado.getNombresDemograficoDTO().add(ndDTO);
			CalidadDTO calidad = new CalidadDTO();
			calidad.setCalidades(Calidades.PROBABLE_RESPONSABLE_PERSONA);
			imputado.setCalidadDTO(calidad);
            // por defecto es detenido
			imputado.setEsDetenido(false);
			// por defecto es fisica   
			imputado.setTipoPersona(new Long(PersonalidadJuridica.FISICA.ordinal())); 
			ExpedienteDTO expedienteDTO = buscarExpedientePorCasoImputadoService.buscarExpedientePorCasoImputado(aviso.getNumeroCasoAsociado(), imputado);
//			TODO VAP si el expediente ya tiene un aviso clonarlo
            if (expedienteDTO != null) {
//            	List<AvisoDetencion> avisosExistentes =  avisoDetencionDAO.consultarAvisosDetencionPorExpediente(expedienteDTO.getExpedienteId());
//            	if (avisosExistentes != null && !avisosExistentes.isEmpty()) {
//            		expedienteDTO = clonarExpService.clonarExpediente(expedienteDTO.getExpedienteId());
//            	}
            } else {
                Caso caso = casoDAO.obtenerCasoByNumeroGeneral(aviso.getNumeroCasoAsociado());
                Expediente expediente = new Expediente();
                if (caso != null) {
                    expediente.setCaso(caso);
                }
                expediente.setFechaCreacion(Calendar.getInstance().getTime());
                expedienteDTO = new ExpedienteDTO();
                expediente.setPertenceConfInst(new ConfInstitucion( Instituciones.DEF.getValorId()));
                expediente.setEstatus(new Valor(EstatusExpediente.ABIERTO.getValorId()));
                expedienteDTO.setExpedienteId(expedienteDAO.create(expediente));
            }
			imputado.setExpedienteDTO(expedienteDTO);
			imputado.setElementoId(ingresarIndividuoService.ingresarIndividuoInterInstitucion(imputado,true));
			AvisoDesignacion designacionInterno	= new AvisoDesignacion();
			designacionInterno.setConsecutivoNotificacion("1");
			designacionInterno.setEstatus(new Valor(EstatusNotificacion.NO_ATENDIDA.getValorId()));
			designacionInterno.setConfInstitucion(new ConfInstitucion(aviso.getConfInstitucionId()));
			designacionInterno.setFechaCreacion(new Date());
			designacionInterno.setFolioNotificacion(aviso.getFolioNotificacion());
			designacionInterno.setNumeroCasoAsociado(aviso.getNumeroCasoAsociado());
			designacionInterno.setExpediente( new Expediente(expedienteDTO.getExpedienteId()));
	        designacionInterno.setNombreDocumento("Aviso de Designacion "+aviso.getSolicitud().getNombreImputado() +" "+aviso.getSolicitud().getApellidoPaternoImputado()+" "+aviso.getSolicitud().getApellidoMaternoImputado());
	        designacionInterno.setTipoDocumento(new Valor(82L));
	        designacionInterno.setForma(new Forma(13l));
			///designacionInterno.setDocumentoId(avisoDesignacionDAO.create(designacionInterno));
			avisoDesignacionDAO.create(designacionInterno);
			//Crear la solicitud de defensor
				SolicitudDefensor solicitud	= new SolicitudDefensor();
				//solicitud.setAvisoDesignacion(designacionInterno);
				solicitud.setDetenido(aviso.getSolicitud().getNombreImputado() +" "+aviso.getSolicitud().getApellidoPaternoImputado()+" "+aviso.getSolicitud().getApellidoMaternoImputado());
				solicitud.setDocumentoId(designacionInterno.getDocumentoId());
//				solicitud.setNombreDocumento("Aviso designacion de "+aviso.getSolicitud().getNombreImputado() +" "+aviso.getSolicitud().getApellidoPaternoImputado()+" "+aviso.getSolicitud().getApellidoMaternoImputado());
//				solicitud.setTipoDocumento(new Valor(3L));
//				solicitud.setForma(new Forma(1l));
				solicitud.setNombreDocumento(designacionInterno.getNombreDocumento());
				solicitud.setTipoDocumento(designacionInterno.getTipoDocumento());
				solicitud.setForma(designacionInterno.getForma());
				solicitud.setFechaCreacion(new Date());
				solicitud.setAudiencia(null);
				solicitud.setFechaDesignacion(new Date());
				solicitud.setEsDetenido(false);
				solicitud.setFolioElementoDetenido(aviso.getFolioNotificacion());
				solicitud.setTipoSolicitud(new Valor(1L));
				solicitud.setConfInstitucion(new ConfInstitucion(1L));
				solicitud.setFolioSolicitud(aviso.getSolicitud().getFolioSolicitud());
				Long idSolicitud	= solicitudDAO.create(solicitud);
				//Registrar Delitos de la solicitud defensor
				SolicitudDefensorDelito defensorDelito 	= null;
				SolicitudDefensorDelitoId id 			= null;	
	            Delito del = null;
	            if (aviso.getDelitos() != null) {
	                for (DelitoWSDTO delito : aviso.getDelitos()) {
	                	defensorDelito	= new SolicitudDefensorDelito();
	                	defensorDelito.setSolicitudDefensor(solicitud);
	                	Set<SolicitudDefensorDelito> defDelitos = new  HashSet<SolicitudDefensorDelito>();
		                    del = new Delito();
		                    del.setEsPrincipal(delito.isEsPrincipal());
		                    del.setCatDelito(new CatDelito(delito.getIdCatDelito()));
		                    del.setEsProbable(delito.isEsProbable());
		                    del.setExpediente(ExpedienteTransformer.transformarExpediente(expedienteDTO));
		                    del.setDelitoId(delitoDAO.create(del));
		                defensorDelito.setDelito(del);
		                    id	= new SolicitudDefensorDelitoId();
		                    id.setSolicitudDefensorId(idSolicitud);
		                    id.setDelitoId(del.getDelitoId());
		                defensorDelito.setId(id);
		                solicitudDefensorDelitoDAO.create(defensorDelito);
		                defDelitos.add(defensorDelito);
		                solicitud.setSolicitudDefensorDelitos(defDelitos);
	                }
	            }
	         designacionInterno.setSolicitudDefensor(solicitud);
			//Permite agregar el id de la agencia y su clave
            if(datosAgencia != null){            	
            	//Aviso de PG a DEF
            	String[] valores = datosAgencia.split("\\|");
            	designacionInterno.setClaveDiscriminanteOrigen(valores[0]);
            	designacionInterno.setCatDiscriminanteOrigen(NumberUtils.toLong(valores[1],0));
            }
            logger.debug("avisoInterno.getCatDiscriminanteOrigen() :: " + designacionInterno.getCatDiscriminanteOrigen());
            logger.debug("avisoInterno.getClaveDiscriminanteOrigen() :: " + designacionInterno.getClaveDiscriminanteOrigen());
            designacionInterno.setIdFuncionarioSolicitante(idFuncionarioSolicitante);
			avisoDesignacionDAO.saveOrUpdate(designacionInterno);
			//aviso.setAvisoDesignacionId(designacionInterno.getDocumentoId());
		}
		return aviso;
	}

}