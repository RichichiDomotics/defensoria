/**
 * Nombre del Programa : EnviarSolicitudDeAudienciaServiceImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 14-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.net.URL;

import javax.xml.namespace.QName;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.infra.PJClienteService;
import mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudDeAudienciaService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudService;
import mx.gob.segob.nsjp.ws.enable.moreliapj.MoreliaBridgePJ;
import mx.gob.segob.nsjp.ws.enable.moreliapj.MoreliaBridgePJSoap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
@Repository
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class EnviarSolicitudDeAudienciaServiceImpl implements
        EnviarSolicitudDeAudienciaService {

    /**
      * Logger de la clase.
      */
    private final static Logger logger = Logger
            .getLogger(EnviarSolicitudDeAudienciaServiceImpl.class);

    @Autowired
    private PJClienteService clientePJWebService;

    @Autowired
    private RegistrarSolicitudService registrarSolicitudService;

    @Autowired
    private BuscarExpedienteService expedienteService;
    @Autowired
    private NumeroExpedienteDAO numExoDao;
    
    @Autowired
    ConfInstitucionDAO confInstitucion;
    
    @Override
    @Transactional
    public SolicitudAudienciaDTO enviarSolicitudDeAudiencia(SolicitudAudienciaDTO
            solicitudAudienciaDto, ExpedienteDTO expedienteInput,
            Long idDistrito, Long idTribunal, Long idClaveFuncionario ) throws NSJPNegocioException {
        logger.info("Inicia - enviarSolicitudDeAudiencia(...)");
    	if(solicitudAudienciaDto==null || expedienteInput==null || idDistrito==null || idTribunal==null || idClaveFuncionario==null )
    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
    	
    	Expediente exp = this.numExoDao.read(expedienteInput.getNumeroExpedienteId()).getExpediente();

        ExpedienteDTO expParam = ExpedienteTransformer
                 .transformarExpedienteBasico(exp);
    	logger.debug("Expediente localizado");

    	expParam.setInvolucradosSolicitados(true);
    	expParam.setDomicliosInvolucradoSolicitados(true);
    	expParam.setObjetosSolicitados(true);
    	expParam.setDocumentosSolicitados(true);
    	
    	final ExpedienteDTO expComplete = this.expedienteService.obtenerExpediente(expParam);
    	expComplete.setUsuario(expedienteInput.getUsuario());
    	solicitudAudienciaDto.setExpedienteDTO(expedienteInput);
        registrarSolicitudService.registrarSolicitud(solicitudAudienciaDto);
        logger.info(" ENVIAR LA SOLICITUD: " + 
        			" idDistrito:" + idDistrito + 
        			" idTribunal:" +idTribunal + 
        			" idClaveFuncionario:" + idClaveFuncionario);
        SolicitudAudienciaDTO solicitudAudiencia = clientePJWebService.
                enviarSolicitudAudiencia(solicitudAudienciaDto, expComplete, idDistrito, idTribunal, idClaveFuncionario);
        
        logger.info("Fin - enviarSolicitudDeAudiencia(...)");
        return solicitudAudiencia;
    }
    
    @Override
    @Transactional
    public SolicitudDTO registrarSolicitudAudienciaMich(SolicitudAudienciaDTO solicitudAudienciaDto, ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
    	
    	logger.info("Inicia - enviarSolicitudDeAudiencia(...)");
    	if(solicitudAudienciaDto==null || expedienteDTO==null )
    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
    	
    	SolicitudDTO solicitudDTO = new SolicitudDTO();
    	
    	Expediente exp = this.numExoDao.read(expedienteDTO.getNumeroExpedienteId()).getExpediente();

        ExpedienteDTO expParam = ExpedienteTransformer
                 .transformarExpedienteBasico(exp);
    	logger.debug("Expediente localizado");

    	expParam.setInvolucradosSolicitados(true);
    	expParam.setDomicliosInvolucradoSolicitados(true);
    	expParam.setObjetosSolicitados(true);
    	expParam.setDocumentosSolicitados(true);
    	
//    	final ExpedienteDTO expComplete = this.expedienteService.obtenerExpediente(expParam);
//    	expComplete.setUsuario(expedienteDTO.getUsuario());
    	solicitudAudienciaDto.setExpedienteDTO(expedienteDTO);
    	solicitudDTO = registrarSolicitudService.registrarSolicitud(solicitudAudienciaDto);
        logger.info(" ENVIAR LA SOLICITUD: " );

        return solicitudDTO;
	}
    
    public String enviarSolAudienciaWs(String xml){
    	
        ConfInstitucion confInst = confInstitucion.consultarIntitucionPorId(Instituciones.PJ.getValorId());
        
        final QName SERVICE_NAME = new QName("http://moreliapj.enable.ws.nsjp.segob.gob.mx/", "MoreliaBridgePJ");

        String respuesta="";
		URL wsdlURL;
		try {
			wsdlURL = new URL(confInst.getUrlInst() + "/MoreliaBridgePJ.asmx?wsdl" );
    		MoreliaBridgePJ ss = new MoreliaBridgePJ(wsdlURL, SERVICE_NAME);
    		MoreliaBridgePJSoap port = ss.getMoreliaBridgePJSoap12();
    		respuesta = port.solicitarAudiencia(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return respuesta;		
    	
    }

   
}
