/**
 * Nombre del Programa : EnviarAvisoDetencionServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 8 Aug 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Servicio par aenviar el aviso de detención.
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

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.avisodetencion.AvisoDetencionDAO;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.involucrado.DetencionDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dto.audiencia.SituacionImputadoDTO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.AvisoDetencion;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Detencion;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.avisodetencion.EnviarAvisoDetencionService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.infra.DefensoriaClienteService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio par aenviar el aviso de detención.
 *
 * @version 1.0
 * @author vaguirre
 *
 */
@Service
@Transactional
public class EnviarAvisoDetencionServiceImpl
        implements
            EnviarAvisoDetencionService {

    /**
     * Logger.
     */
    private final static Logger logger = Logger.getLogger(EnviarAvisoDetencionServiceImpl.class);


    @Autowired
    private ConfInstitucionDAO confInsDao;
    @Autowired
    private DetencionDAO detencionDao;
    @Autowired
    private InvolucradoDAO involucradoDAO;
    @Autowired
    private DefensoriaClienteService clienteWs;
    @Autowired
    private GenerarFolioSolicitudService generarFolioSolicitudService;
    @Autowired
    private AvisoDetencionDAO avisoDao;
    @Autowired
    private ElementoDAO elementoDAO;
    @Autowired
    private AvisoDesignacionDAO designacionDAO;
    @Autowired
    private SolicitudDefensorDAO  defensorDAO;
    
    @Override
    public void enviarAvisoDetencion(DetencionDTO input,Long idAgencia, String claveAgencia, Long idFuncionarioSolicitante)
            throws NSJPNegocioException {
    	
    	boolean flagDetencion = false;
    	
        logger.info("Inicia - enviarAvisoDetencion(...)");
        logger.debug("input.getFechaRecepcionDetencion() :: " + input.getFechaRecepcionDetencion());

        if (input == null || input.getInvolucradoDTO() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        //Verificamos si el involucrado ya tiene un aviso de detencion by Dorian
        if (avisoDao.involucradoTieneSolicitudDefensor(input.getInvolucradoDTO().getElementoId())){
        	flagDetencion = true;
        }
        SituacionImputadoDTO situacionImpDTO = new SituacionImputadoDTO();
        if (input.getInvolucradoDTO().getSituacionImpDTO()!=null){
        	situacionImpDTO = input.getInvolucradoDTO().getSituacionImpDTO();
        	logger.info("Situacion Imputado ... " + situacionImpDTO.getSituacionImputadoId());
        }
        	
        Detencion detencion = new Detencion();
        detencion.setFechaInicioDetencion(input.getFechaInicioDetencion());
        detencion.setFechaFinDetencion(input.getFechaFinDetencion());
        detencion.setFechaRecepcionDetencion(input.getFechaRecepcionDetencion());
        Involucrado inv = involucradoDAO.read(input.getInvolucradoDTO().getElementoId());
        logger.info("Involucrado ID Leidoo :: " + input.getInvolucradoDTO().getElementoId());
        input.setInvolucradoDTO(InvolucradoTransformer.transformarInvolucrado(inv));
        input.getInvolucradoDTO().setSituacionImpDTO(situacionImpDTO);
        detencion.setInvolucrado(inv);
	        AvisoDetencion aviso = new AvisoDetencion();
	        if (input.getDetencionId()!=null) {
	        	aviso.setDetencion(new Detencion(input.getDetencionId()));
	        }
	        aviso.setNombreDocumento("Aviso detencion de"+ input.getInvolucradoDTO().getNombreCompleto());
	        aviso.setDetenido(input.getInvolucradoDTO().getNombreCompleto());
	        aviso.setFechaCreacion(new Date());
	        // TODO VAP definir forma y tipo de documento
	        aviso.setForma(new Forma(Formas.AUDIENCIA.getValorId()));
	        aviso.setTipoDocumento(new Valor(TipoDocumento.ACUSE.getValorId()));
	        aviso.setFolioNotificacion(generarFolioSolicitudService.generarFolioNotificacion());
	        ConfInstitucion confI = confInsDao.consultarInsitucionActual();
	        aviso.setConfInstitucion(confI);
	        aviso.setConsecutivoNotificacion("1");
	        aviso.setEstatus(new Valor(EstatusNotificacion.EN_PROCESO.getValorId()));
	        if(!flagDetencion){
	        	this.avisoDao.create(aviso);
	        }
        detencion.setAvisoDetencion(aviso);
        
        

        AvisoDetencionDTO avisoDto = new AvisoDetencionDTO();
        avisoDto.setFolioNotificacion(aviso.getFolioNotificacion());
        
        CasoDTO caso = (input.getInvolucradoDTO().getExpedienteDTO() != null && input.getInvolucradoDTO().getExpedienteDTO().getCasoDTO() != null ?
        				input.getInvolucradoDTO().getExpedienteDTO().getCasoDTO(): null );
        if(caso != null && caso.getNumeroGeneralCaso() != null){
        	avisoDto.setNumeroCasoAsociado(caso.getNumeroGeneralCaso());
        }
        this.transmitir(input, avisoDto, caso,idAgencia,claveAgencia,idFuncionarioSolicitante);
        if (input.getDetencionId()==null) {
        	this.detencionDao.create(detencion);
        }
        logger.debug("guardado local [OK]");
        logger.debug("transmision [OK]");
    }
    
    @Override
    public void enviarAvisoSinDetencion( SolicitudDefensorDTO solicitud, Long idAgencia, String claveAgencia, Long idFuncionarioSolicitante)
            throws NSJPNegocioException {
    	
        logger.info("Inicia - enviarAvisoSinDetencion(...)");
        logger.debug("solicitud.getNombreDetenido(): " + solicitud.getNombreDetenido() );

        if (solicitud == null || solicitud.getNombreDetenido() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        SolicitudDefensor defensor		= new SolicitudDefensor();
        defensor.setDetenido(solicitud.getNombreDetenido());
        defensor.setAudiencia(null);
        defensor.setFechaDesignacion(new Date());
        defensor.setEsDetenido(false);
        defensor.setFolioElementoDetenido(solicitud.getFolioElementoDetenido());
        defensor.setFolioSolicitud(solicitud.getFolioSolicitud());
        defensor.setTipoAsesoria(null);
        Involucrado inv = involucradoDAO.read(solicitud.getInvolucradoDTO().getElementoId());
        defensor.setInvolucradoSolicitante(inv);
        	AvisoDesignacion designacion	= new AvisoDesignacion();
	        /*if (solicitud.getDocumentoId()  != null) {
	        	designacion.setDetencion(new Detencion(input.getDetencionId()));
	        }*/
	        designacion.setNombreDocumento("Aviso designacion de "+solicitud.getInvolucradoDTO().getNombreCompleto());
	        designacion.setAvisoDetencion(null);
	        designacion.setFechaCreacion(new Date());
	        designacion.setForma(new Forma(Formas.SOLICITUD.getValorId()));
	        designacion.setTipoDocumento(new Valor(TipoDocumento.ACUSE.getValorId()));
	        designacion.setFolioNotificacion(generarFolioSolicitudService.generarFolioNotificacion());
	        ConfInstitucion confI = confInsDao.consultarInsitucionActual();
	        designacion.setConfInstitucion(confI);
	        designacion.setConsecutivoNotificacion("1");
	        designacion.setExpediente( ExpedienteTransformer.transformarExpediente(solicitud.getExpedienteDTO()));
	        designacion.setFuncionario( FuncionarioTransformer.transformarFuncionario(solicitud.getFuncionario()) );
	        designacion.setEstatus(new Valor(EstatusNotificacion.EN_PROCESO.getValorId()));
	        this.designacionDAO.create(designacion);
        //defensor.setAvisoDesignacion(designacion);
        
	    AvisoDesignacionDTO designacionDTO 	= new AvisoDesignacionDTO();
	    designacionDTO.setFolioNotificacion(designacion.getFolioNotificacion());
	    CasoDTO caso = ( solicitud.getInvolucradoDTO() != null && solicitud.getInvolucradoDTO().getExpedienteDTO().getCasoDTO() != null ?
	    		solicitud.getInvolucradoDTO().getExpedienteDTO().getCasoDTO(): null );
	    if(caso != null && caso.getNumeroGeneralCaso() != null){
	    	designacionDTO.setNumeroCasoAsociado(caso.getNumeroGeneralCaso());
        }
	    this.transmitirAviso(solicitud, designacionDTO, caso, idAgencia, claveAgencia, idFuncionarioSolicitante);
	    this.designacionDAO.create(designacion);
        logger.debug("guardado local [OK]");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    private void transmitir(DetencionDTO input, AvisoDetencionDTO avisoDto,
            CasoDTO caso,Long idAgencia, String claveAgencia,Long idFuncionarioSolicitante) throws NSJPNegocioException {
        logger.debug("Inicia transimision...");
        clienteWs.enviarAvisoDetencion(input, avisoDto, caso,idAgencia,claveAgencia, idFuncionarioSolicitante);
        logger.debug("transmision [OK]");
    }
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    private void transmitirAviso(SolicitudDefensorDTO solicitud,  AvisoDesignacionDTO designacionDTO,
            CasoDTO caso,Long idAgencia, String claveAgencia,Long idFuncionarioSolicitante) throws NSJPNegocioException {
        logger.debug("Inicia transmitirAviso...");
        clienteWs.enviarAvisoSinDetencion(solicitud, designacionDTO, caso,idAgencia,claveAgencia, idFuncionarioSolicitante);
        logger.debug("transmitirAviso [OK]");
    }
    
    
}
