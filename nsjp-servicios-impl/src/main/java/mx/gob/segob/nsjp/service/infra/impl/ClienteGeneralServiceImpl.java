/**
 * Nombre del Programa : ClienteGeneralServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Aug 2011
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
package mx.gob.segob.nsjp.service.infra.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.namespace.QName;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.InvolucradoAudiencia;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.MedidaCautelar;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo.CatDiscriminanteWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo.CatDistritoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo.CriterioConsultaFuncionarioExternoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo.FuncionarioWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviardocumento.DocumentoWSDTOTransformer;
import mx.gob.segob.nsjp.service.sentencia.impl.transform.SentenciaTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudTransformer;
import mx.gob.segob.nsjp.ws.cliente.consultardistritos.CatDistritoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.consultardistritos.ConsultarDistritosServiceWSExporter;
import mx.gob.segob.nsjp.ws.cliente.consultardistritos.ConsultarDistritosServiceWSExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.ConsultarFuncionarioExternoExporter;
import mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.ConsultarFuncionarioExternoExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.ConsultarFuncionariosXTribunalExporter;
import mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.ConsultarFuncionariosXTribunalExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.consultartribunalespordistrito.CatDiscriminanteWSDTO;
import mx.gob.segob.nsjp.ws.cliente.consultartribunalespordistrito.ConsultarTribunalesPorDistritoServiceExporter;
import mx.gob.segob.nsjp.ws.cliente.consultartribunalespordistrito.ConsultarTribunalesPorDistritoServiceExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.enviardocumentoainstitucion.DocumentoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviardocumentoainstitucion.EnviarDocumentoAInstitucionExporter;
import mx.gob.segob.nsjp.ws.cliente.enviardocumentoainstitucion.EnviarDocumentoAInstitucionExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.ActualizarEstatusMandamientoInstitucionalExporter;
import mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.ActualizarEstatusMandamientoInstitucionalExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.MandamientoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.medidacautelar.ArchivoDigitalWSDTO;
import mx.gob.segob.nsjp.ws.cliente.medidacautelar.MedidaCautelarWSDTO;
import mx.gob.segob.nsjp.ws.cliente.medidacautelar.RegistrarMedidaCautelarServiceExporter;
import mx.gob.segob.nsjp.ws.cliente.medidacautelar.RegistrarMedidaCautelarServiceExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.AudienciaWSDTO;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.NotificacionWSDTO;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.RecibirNotificacionAudienciaExporter;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.RecibirNotificacionAudienciaExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.SolicitudAudienciaBasicoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CalidadWSDTO;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CasoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.DelitoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.InvolucradoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.NSJPNegocioException_Exception;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.NombreDemograficoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.RegistrarReplicaCasoExporter;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.RegistrarReplicaCasoExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.sentencia.SentenciaExporterImpl;
import mx.gob.segob.nsjp.ws.cliente.sentencia.SentenciaExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.sentencia.SentenciaWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicitud.EnviarSolicitudServiceExporterImpl;
import mx.gob.segob.nsjp.ws.cliente.solicitud.EnviarSolicitudServiceExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.solicitud.SolicitudWSDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service
@Transactional
public class ClienteGeneralServiceImpl implements ClienteGeneralService {
    /**
     * Logger.
     */
    private final static Logger logger = Logger
            .getLogger(ClienteGeneralServiceImpl.class);
    @Autowired
    private ConfInstitucionDAO institucionDao;
    @Autowired
    private DelitoPersonaDAO delitoPersonaDao;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.segob.nsjp.service.infra.ClienteGeneralService#enviarAcuseRecibo
     * (java.lang.Long, mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
     */
    @Override
    public void enviarAcuseRecibo(Long idSolicitud, Instituciones target)
            throws NSJPNegocioException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.segob.nsjp.service.infra.ClienteGeneralService#enviarReplicaCaso
     * (mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO,
     * mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
     */
    @Override
    public void enviarReplicaCaso(ExpedienteDTO expConCaso,
            ConfInstitucion target) throws NSJPNegocioException {
        URL ep;
        try {
            ep = new URL(target.getUrlInst()+ "/RegistrarReplicaCasoExporterImplService?wsdl");
            final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/",
                    "RegistrarReplicaCasoExporterImplService");
            RegistrarReplicaCasoExporterImplService ss 	= new RegistrarReplicaCasoExporterImplService(ep, SERVICE_NAME);
            RegistrarReplicaCasoExporter port 			= ss.getRegistrarReplicaCasoExporterImplPort();
            logger.debug("Port obtenido....");
            CasoWSDTO toSend = new CasoWSDTO();
            
            toSend.setNumeroGeneralCaso(expConCaso.getCasoDTO().getNumeroGeneralCaso());
            toSend.setFechaApertura(WsTransformer.transformFecha(expConCaso.getCasoDTO().getFechaApertura()));
        
            List<InvolucradoWSDTO> invos2s = new ArrayList<InvolucradoWSDTO>();
            List<DelitoWSDTO> delis2s = null;
            List<String> alias2s = null;
            List<NombreDemograficoWSDTO> noms2s = null;
            
            InvolucradoWSDTO invo=null;
            DelitoWSDTO deli =null;
            CalidadWSDTO cali=null;
            NombreDemograficoWSDTO nomb=null;
            
            List<InvolucradoDTO> invoFuente = recuperarInvolucrados(expConCaso);
            for (InvolucradoDTO dto : invoFuente) {
            	logger.info("ID del Involucrado -----  " + dto.getElementoId());
                invo = new InvolucradoWSDTO();
                cali = new CalidadWSDTO();
                cali.setValorIdCalidad(dto.getCalidadDTO().getValorIdCalidad().getIdCampo());
                cali.setDescripcionEstadoFisico(dto.getCalidadDTO().getDescripcionEstadoFisico());
                invo.setCalidad(cali);
                invo.setTipoPersona(dto.getTipoPersona());
                invo.setFolioElemento(dto.getFolioElemento());
                invo.setDesconocido(dto.getDesconocido());
                logger.info("Condicion Involucrado Procuraduria -------  "+dto.getCondicion());
                invo.setCondicion(dto.getCondicion());
                
                noms2s = new ArrayList<NombreDemograficoWSDTO>();
                for (NombreDemograficoDTO nomDto: dto.getNombresDemograficoDTO()) {
                    nomb = new NombreDemograficoWSDTO();
                    nomb.setNombre(nomDto.getNombre());
                    nomb.setApellidoPaterno(nomDto.getApellidoPaterno());
                    nomb.setApellidoMaterno(nomDto.getApellidoMaterno());
                    //Se agrega Edad al Involucrado by Dorian
                    nomb.setEdadAproximada(nomDto.getEdadAproximada());
                    logger.info("Edad del Involucrado Procu ------ " + nomDto.getEdadAproximada());
                    nomb.setSexo(nomDto.getSexo());
                    logger.info("Sexo del Involucrado Procu ----- " + nomDto.getSexo());
                    noms2s.add(nomb);
                }
                
                delis2s = new ArrayList<DelitoWSDTO>();
                for (DelitoDTO delCometidoDto : dto.getDelitosCometidos()) {
                    deli = new DelitoWSDTO();
                    deli.setIdCatDelito(delCometidoDto.getCatDelitoDTO().getCatDelitoId());
                    deli.setEsPrincipal(delCometidoDto.getEsPrincipal());
                    List<Long> sitImputado = delitoPersonaDao.obtenerSituacionImputadoPorInvolucrado(dto.getElementoId());
                    logger.info("Situacion Imputado ---- " + sitImputado.get(0));
                    deli.setSituacionImputado(sitImputado.get(0));
                    List<DelitoPersona> delitoPersona = delitoPersonaDao.consultarDelitoPerByInvolucrado(dto.getElementoId());
                    logger.info(" Delito Personaa CatDelitoCausaId ---- " + delitoPersona.get(0).getCatDelitoCausaId());
                    logger.info(" Delito Personaa CatDelitoClasificacionId ---- " + delitoPersona.get(0).getCatDelitoClasificacionId());
                    logger.info(" Delito Personaa getCatDelitoModusId ---- " + delitoPersona.get(0).getCatDelitoModusId());
                    logger.info(" Delito Personaa getClaveInterInstitucional ---- " + delitoPersona.get(0).getDelito().getCatDelito().getClaveInterInstitucional());
                    logger.info(" Delito Personaa ElementoID PR ---- " + delitoPersona.get(0).getProbableResponsable().getElementoId());
                    deli.setCatDelitoClasificacion(delitoPersona.get(0).getCatDelitoClasificacionId());
                    deli.setCatDelitoModus(delitoPersona.get(0).getCatDelitoModusId());
                    deli.setCatDelitoCausa(delitoPersona.get(0).getCatDelitoCausaId());
                    delis2s.add(deli);
                }
                invo.setDelitosCometidos(delis2s);
                
                alias2s = new ArrayList<String>();
                for (AliasInvolucradoDTO aliasDto: dto.getAliasInvolucradoDTO()) {
                    alias2s.add(aliasDto.getAlias());
                }
                invo.setNombresDemograficos(noms2s);
                //invo.setDelitosCometidos(delis2s);
                invo.setAliasInvolucrado(alias2s);
                invos2s.add(invo);
            }
            toSend.setInvolucradosDTO(invos2s);
            logger.debug("Invocando registrarReplicaCaso...");
            port.registrarReplicaCaso(toSend);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        }
    }

    /**
     * @param expConCaso
     * @return
     */
    private List<InvolucradoDTO> recuperarInvolucrados(ExpedienteDTO expConCaso) {
        final List<InvolucradoDTO> invoFuente = new ArrayList<InvolucradoDTO>();
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA));
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA));
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION));
        
        List<InvolucradoDTO> tempDenunciante = expConCaso.getInvolucradoByCalidad(Calidades.DENUNCIANTE);
        if (tempDenunciante!=null) {
            for (InvolucradoDTO invoDen:tempDenunciante) {
                if (invoDen.getCondicion()!=null && invoDen.getCondicion().intValue()==1) {
                    invoFuente.add(invoDen);
                }
            }
        }

        tempDenunciante = expConCaso.getInvolucradoByCalidad(Calidades.DENUNCIANTE_ORGANIZACION);
        if (tempDenunciante!=null) {
            for (InvolucradoDTO invoDen:tempDenunciante) {
                if (invoDen.getCondicion()!=null && invoDen.getCondicion().intValue()==1) {
                    invoFuente.add(invoDen);
                }
            }
        }
        return invoFuente;
    }

    @Override
    public Boolean enviarNotificacionAudienciaFuncionarioExtenor(
            Audiencia audiencia, String nombreFuncionario,
            Instituciones target,
            String folioNotificacion, String consecutivoNotificacion) throws NSJPNegocioException {
        URL ep;
        Boolean resp = null;
        logger.info("Inicia - enviarNotificacionAudienciaFuncionarioExtenor(...)");
        try {
            ConfInstitucion destino = this.institucionDao.read(target.getValorId());
            ep = new URL(destino.getUrlInst()
                    + "/RecibirNotificacionAudienciaExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "RecibirNotificacionAudienciaExporterImplService");
        RecibirNotificacionAudienciaExporterImplService ss = new RecibirNotificacionAudienciaExporterImplService(ep, SERVICE_NAME);
        RecibirNotificacionAudienciaExporter port = ss.getRecibirNotificacionAudienciaExporterImplPort();  
        
        SolicitudAudienciaBasicoWSDTO toSend = new SolicitudAudienciaBasicoWSDTO(); 
        toSend.setNombreSolicitante(nombreFuncionario);
        AudienciaWSDTO aud = new AudienciaWSDTO();
        if (audiencia.getNumeroExpediente().getExpediente().getCaso()!=null) {
            aud.setNumeroGeneralCaso(audiencia.getNumeroExpediente().getExpediente().getCaso().getNumeroGeneralCaso());
        }
        if (audiencia.getSalaAudiencia()!=null) {
        	logger.debug(audiencia.getSalaAudiencia());
            logger.debug(audiencia.getSalaAudiencia().getNombreSala());
            aud.setNombreSala(audiencia.getSalaAudiencia().getNombreSala());
            aud.setDomicilioSala(audiencia.getSalaAudiencia().getDomicilioSala());
            aud.setUbicacionSala(audiencia.getSalaAudiencia().getUbicacionSala());
        } else {
            aud.setNombreSala("Temporal");
            aud.setDomicilioSala(audiencia.getSalaTemporal().getDomicilioSala());
            aud.setUbicacionSala(audiencia.getSalaTemporal().getUbicacionSala()); 
        }

        aud.setDuracionEstimada(audiencia.getDuracionEstimada());
        aud.setFechaHoraAudiencia(WsTransformer.transformFecha(audiencia.getFechaAudiencia()));
        Calendar auxFin = Calendar.getInstance();
        auxFin.setTime(audiencia.getFechaAudiencia());
        auxFin.add(Calendar.MINUTE, audiencia.getDuracionEstimada());
        aud.setFechaFin(WsTransformer.transformFecha(auxFin.getTime()));
        aud.setFechaInicio(WsTransformer.transformFecha(audiencia.getFechaAudiencia()));
        aud.setFolioAudiencia(audiencia.getFolioAudiencia());
        aud.setTipoAudienciaId(audiencia.getTipo().getValorId());
        
        NotificacionWSDTO notiws = new NotificacionWSDTO();
        notiws.setConsecutivoNotificacion(consecutivoNotificacion);
        notiws.setFolioNotificacion(folioNotificacion);
        aud.setNotificaion(notiws);
        
        List<mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.InvolucradoWSDTO> invos = new ArrayList<mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.InvolucradoWSDTO>();
       for (InvolucradoAudiencia ia : audiencia.getInvlucradoAudiencias()){
           mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.InvolucradoWSDTO invoWs= new mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.InvolucradoWSDTO();
           mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.CalidadWSDTO cali = new mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.CalidadWSDTO();
           cali.setValorIdCalidad(ia.getInvolucrado().getCalidad().getTipoCalidad().getValorId());
           invoWs.setFolioElemento(ia.getInvolucrado().getFolioElemento());
           logger.debug("Enviando los siguientes involucrados :: " + ia.getInvolucrado().getFolioElemento());
           invoWs.setCalidad(cali);
           invos.add(invoWs);
       }
       toSend.setInvolucradosDTO(invos);
        
        toSend.setConfInstitucionId(this.institucionDao.consultarInsitucionActual().getConfInstitucionId());
        
        toSend.setAudiencia(aud);
        resp =port.recibirNotificacionAudiencia(toSend);
        logger.info("Fin - enviarNotificacionAudienciaFuncionarioExtenor(...)");
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        }
        return resp;
    }
    
    
    
    @Override
    public List<FuncionarioDTO> consultarFuncionariosXTribunal(
   		 Long catDiscriminanteId,Instituciones target) throws NSJPNegocioException {
        URL ep;
        List<mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.FuncionarioWSDTO> resp = null;
        List<FuncionarioDTO> loFuncionarioDTO = new ArrayList<FuncionarioDTO>();
        logger.info("Inicia - consultarFuncionariosXTribunal(...)");
        try {
            ConfInstitucion destino = this.institucionDao.read(target.getValorId());
            ep = new URL(destino.getUrlInst()
                    + "/ConsultarFuncionariosXTribunalExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "ConsultarFuncionariosXTribunalExporterImplService");
            
        ConsultarFuncionariosXTribunalExporterImplService ss = new ConsultarFuncionariosXTribunalExporterImplService(ep, SERVICE_NAME);
        ConsultarFuncionariosXTribunalExporter port = ss.getConsultarFuncionariosXTribunalExporterImplPort();

        resp = port.consultarFuncionariosXTribunal(catDiscriminanteId);
        
        for (mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.FuncionarioWSDTO funcionarioWSDTO : resp) {
        	loFuncionarioDTO.add(FuncionarioWSDTOTransformer.transformarFuncionario(funcionarioWSDTO));
		}
        
        logger.info("Fin - consultarFuncionariosXTribunal(...)");
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        }
        return loFuncionarioDTO;
    }


    @Override
    public List<CatDiscriminanteDTO> consultarTribunalesPorDistrito( Long distritoId, Instituciones target) throws NSJPNegocioException {
    	
    	logger.info("Inicia - consultarTribunalesPorDistrito(...)");

    	List<CatDiscriminanteDTO> discriminantesDTO = new ArrayList<CatDiscriminanteDTO>();
		URL ep;
		try {
			// Configuración de WS
			ConfInstitucion destino = this.institucionDao.read(target
					.getValorId());
			ep = new URL(
					destino.getUrlInst()
							+ "/ConsultarTribunalesPorDistritoServiceExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"ConsultarTribunalesPorDistritoServiceExporterImplService");

			ConsultarTribunalesPorDistritoServiceExporterImplService ss = new ConsultarTribunalesPorDistritoServiceExporterImplService(
					ep, SERVICE_NAME);

			ConsultarTribunalesPorDistritoServiceExporter port = ss
					.getConsultarTribunalesPorDistritoServiceExporterImplPort();

			// Invocación del WS
			List<CatDiscriminanteWSDTO> discriminantesWSDTO = port
					.consultarTribunalesPorDistrito(distritoId);

			logger.info("Respuesta - consultarTribunalesPorDistrito:"
					+ discriminantesWSDTO);

			if (discriminantesWSDTO != null && !discriminantesWSDTO.isEmpty()) {
				logger.info("discriminantesWSDTO.size(): "
						+ discriminantesWSDTO.size());
				for (CatDiscriminanteWSDTO catDiscriminanteWSDTO : discriminantesWSDTO) {
					CatDiscriminanteDTO discriminanteDTO = CatDiscriminanteWSDTOTransformer
							.transformarCatDiscriminante(catDiscriminanteWSDTO);
					logger.info(" DiscriminanteDTO : " + discriminantesDTO);
					discriminantesDTO.add(discriminanteDTO);
				}
			}
			logger.info("Fin - consultarTribunalesPorDistrito(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.consultartribunalespordistrito.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

		return discriminantesDTO;
    }
    

    @Override
    public Long enviarDocumentoAInstitucion( List<DocumentoDTO> lstDocumentoDTO, String numeroDeCaso, Instituciones target) throws NSJPNegocioException {
    	if(lstDocumentoDTO != null ) {
		   for (DocumentoDTO documentoDTO : lstDocumentoDTO) {
			   logger.debug("ClienteGeneralServiceImpl - enviarDocumentoAInstitucion ");
			   logger.debug("ClienteGeneralServiceImpl - documento = " + documentoDTO);
			   logger.debug("ClienteGeneralServiceImpl - numeroExpediente = " + numeroDeCaso);
			   logger.debug("ClienteGeneralServiceImpl - documento - ArchivoDigital = " + documentoDTO.getArchivoDigital());		   
		   }
	   }
	   
	   URL ep;
	    	 
		try {
			
			// Configuracion para acceder al web service
			ConfInstitucion destino = this.institucionDao.read(target
					.getValorId());
			ep = new URL(
					destino.getUrlInst()
							+ "/EnviarDocumentoAInstitucionExporterImplService?wsdl");
			
			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"EnviarDocumentoAInstitucionExporterImplService");	   
			
			EnviarDocumentoAInstitucionExporterImplService ss =
				new EnviarDocumentoAInstitucionExporterImplService(ep, SERVICE_NAME);
			
			EnviarDocumentoAInstitucionExporter port =
				ss.getEnviarDocumentoAInstitucionExporterImplPort();
			// Hasta aqui finaliza la configuracion para acceder al web service

			// Aqui inicia el proceso de transformar los dto en wsDto
			List<DocumentoWSDTO> lstDocumentoWSDTO = new ArrayList<DocumentoWSDTO>();
			
			for (DocumentoDTO documentoDTO : lstDocumentoDTO) {
				DocumentoWSDTO documentoIncumplimientoWSDTO = DocumentoWSDTOTransformer.transformarWSDTO(documentoDTO);
				lstDocumentoWSDTO.add(documentoIncumplimientoWSDTO);

				logger.info(" DocumentoDTO:" + documentoDTO);
				logger.debug("PJCLienteService - documento - ArchivoDigital Before Transformer = " + documentoDTO.getArchivoDigital());
				
				logger.info(" DocumentoWSDTO:" + documentoIncumplimientoWSDTO);
				logger.debug("ClienteGeneralServiceImpl - documento - ArchivoDigital After Transformer = " + documentoIncumplimientoWSDTO.getArchivoDigital());
				
			}

			Long idDocumento = port.enviarDocumentoAInstitucion(lstDocumentoWSDTO, numeroDeCaso);
			logger.debug("----------------------------------------ClienteGeneralServiceImpl: DocumentoEnviado: idDocumento = " + idDocumento);
			
			return idDocumento;

		} catch (mx.gob.segob.nsjp.ws.cliente.enviardocumentoainstitucion.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}		
		
	}
	
    @Override
    public List<CatDiscriminanteDTO> consultarAgenciasPorDistrito( Long distritoId, Instituciones target) throws NSJPNegocioException {
    
    	logger.info("Inicia - consultarAgenciasPorDistrito(...)");

    	List<CatDiscriminanteDTO> discriminantesDTO = new ArrayList<CatDiscriminanteDTO>();
		URL ep;
		try {
			// Configuración de WS
			ConfInstitucion destino = this.institucionDao.read(target
					.getValorId());
			ep = new URL(
					destino.getUrlInst()
							+ "/ConsultarTribunalesPorDistritoServiceExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"ConsultarTribunalesPorDistritoServiceExporterImplService");

			ConsultarTribunalesPorDistritoServiceExporterImplService ss = new ConsultarTribunalesPorDistritoServiceExporterImplService(
					ep, SERVICE_NAME);

			ConsultarTribunalesPorDistritoServiceExporter port = ss
					.getConsultarTribunalesPorDistritoServiceExporterImplPort();

			// Invocación del WS
			List<CatDiscriminanteWSDTO> discriminantesWSDTO = port
					.consultarAgenciasPorDistrito(distritoId);

			logger.info("Respuesta - consultarAgenciasPorDistrito:"
					+ discriminantesWSDTO);

			if (discriminantesWSDTO != null && !discriminantesWSDTO.isEmpty()) {
				logger.info("discriminantesWSDTO.size(): "
						+ discriminantesWSDTO.size());
				for (CatDiscriminanteWSDTO catDiscriminanteWSDTO : discriminantesWSDTO) {
					CatDiscriminanteDTO discriminanteDTO = CatDiscriminanteWSDTOTransformer
							.transformarCatDiscriminante(catDiscriminanteWSDTO);
					logger.info(" DiscriminanteDTO : " + discriminantesDTO);
					discriminantesDTO.add(discriminanteDTO);
}
			}
			logger.info("Fin - consultarAgenciasPorDistrito(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.consultartribunalespordistrito.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

		return discriminantesDTO;
    }

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.infra.ClienteGeneralService#consultarFuncionariosXCriterio(mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO, mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
	 */
	@Override
	public List<FuncionarioDTO> consultarFuncionariosXCriterio(
			CriterioConsultaFuncionarioExternoDTO criterioConsultaFuncionarioExternoDTO,
			Instituciones target) throws NSJPNegocioException {
		URL ep;
        List<mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.FuncionarioWSDTO> resp = null;
        List<FuncionarioDTO> loFuncionarioDTO = new ArrayList<FuncionarioDTO>();
        logger.info("Inicia - consultarFuncionariosXCriterio(...)");
        try {
            ConfInstitucion destino = this.institucionDao.read(target.getValorId());
            ep = new URL(destino.getUrlInst()
                    + "/ConsultarFuncionarioExternoExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "ConsultarFuncionarioExternoExporterImplService");
            
        ConsultarFuncionarioExternoExporterImplService ss = new ConsultarFuncionarioExternoExporterImplService(ep, SERVICE_NAME);
        ConsultarFuncionarioExternoExporter port = ss.getConsultarFuncionarioExternoExporterImplPort();

        resp = port.consultarFuncionariosXCriterio(CriterioConsultaFuncionarioExternoWSDTOTransformer.transformarWSDTOCliente(
        		criterioConsultaFuncionarioExternoDTO));
        
        for (mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.FuncionarioWSDTO funcionarioWSDTO : resp) {
        	loFuncionarioDTO.add(FuncionarioWSDTOTransformer.transformarFuncionario(funcionarioWSDTO));
		}
        
        logger.info("Fin - consultarFuncionariosXCriterio(...)");
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.NSJPNegocioException_Exception e) {
        	logger.error(e.getMessage());
        	throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} 
        return loFuncionarioDTO;
	}

	@Override
	public Boolean enviarSentencia(SentenciaDTO sentenciaDTO,
			Instituciones institucion) throws NSJPNegocioException {
		boolean resultado = false;
    	logger.info("Inicia - enviarSentencia(...)");

		URL ep;
		try {
			// Configuracion de WS
			ConfInstitucion destino = this.institucionDao.read(institucion.getValorId());
			ep = new URL(destino.getUrlInst()+ "/SentenciaExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/", 
					"SentenciaExporterImplService");
			
			SentenciaWSDTO sentenciaWSDTO = SentenciaTransformer.transformarDTO2ClienteWSDTO(sentenciaDTO);
			
	        SentenciaExporterImplService ss = new SentenciaExporterImplService(ep, SERVICE_NAME);
	        SentenciaExporterImpl port = ss.getSentenciaExporterImplPort();  
	        
	        port.crearSentencia(sentenciaWSDTO);
			
			logger.info("Fin - enviarSentencia(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.sentencia.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
		return resultado;
	}
	
	
	
    public List<CatDistritoDTO> consultarDistritos(Instituciones target) throws NSJPNegocioException {
        
    	logger.info("Inicia - consultarDistritos(...)");

    	List<CatDistritoDTO> distritosDTO = new ArrayList<CatDistritoDTO>();
		URL ep;
		try {
			// Configuración de WS
			ConfInstitucion destino = this.institucionDao.read(target
					.getValorId());
			ep = new URL(
					destino.getUrlInst()
							+ "/ConsultarDistritosServiceWSExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"ConsultarDistritosServiceWSExporterImplService");

			ConsultarDistritosServiceWSExporterImplService ss = new ConsultarDistritosServiceWSExporterImplService(
					ep, SERVICE_NAME);

			ConsultarDistritosServiceWSExporter port =  ss.getConsultarDistritosServiceWSExporterImplPort();
					
			

			// Invocación del WS
			List<CatDistritoWSDTO> distritosWSDTO = port.consultarDistritos();
					

			logger.info("Respuesta - consultarDistritos:"
					+ distritosWSDTO);

			if (distritosWSDTO != null && !distritosWSDTO.isEmpty()) {
				logger.info("distritosWSDTO.size(): "
						+ distritosWSDTO.size());
				for (CatDistritoWSDTO catDistritoWSDTO : distritosWSDTO) {
					CatDistritoDTO discriminanteDTO = CatDistritoWSDTOTransformer.transformarCatDistrito(catDistritoWSDTO);
					distritosDTO.add(discriminanteDTO);
}
			}
			logger.info("Fin - consultarDistritos(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.consultardistritos.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

		return distritosDTO;
    }

    @Override
    public void enviarMedidaCautelarInstitucion(MedidaCautelar medidaCautelar, Instituciones institucionEnviar)
            throws NSJPNegocioException {

        if (medidaCautelar.getInvolucrado() == null
                || medidaCautelar.getInvolucrado().getNombreDemograficos() == null
                || medidaCautelar.getArchivoDigital() == null
                || medidaCautelar.getFechaCreacion() == null
                || medidaCautelar.getFechaInicio() == null
                || medidaCautelar.getFechaFin() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        try {
        	
        	ConfInstitucion destino = this.institucionDao.read(institucionEnviar.getValorId());
//        	URL url = new URL(confInsDao.read(Instituciones.SSP.getValorId())
            URL url = new URL(
            		destino.getUrlInst()
                    + "/RegistrarMedidaCautelarServiceExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "RegistrarMedidaCautelarServiceExporterImplService");

            RegistrarMedidaCautelarServiceExporterImplService ss = new RegistrarMedidaCautelarServiceExporterImplService(
                    url, SERVICE_NAME);

            RegistrarMedidaCautelarServiceExporter port = ss
                    .getRegistrarMedidaCautelarServiceExporterImplPort();

            MedidaCautelarWSDTO toSend = new MedidaCautelarWSDTO();

            toSend.setActivo((medidaCautelar.getEsActivo() != null && medidaCautelar
                    .getEsActivo()));
            NombreDemografico nombre = null;
            if (medidaCautelar.getInvolucrado().getNombreDemograficos() != null) {
                for (NombreDemografico nd : medidaCautelar.getInvolucrado()
                        .getNombreDemograficos()) {
                    if (nd.getEsVerdadero()) {
                        nombre = nd;
                    }
                }
                if (nombre == null) {
                    nombre = medidaCautelar.getInvolucrado().getNombreDemograficos()
                            .iterator().next();
                }
                toSend.setNombreSujeto(nombre.getNombre());
                toSend.setAPaternoSujeto(nombre.getApellidoPaterno());
                toSend.setAMaternoSujeto(nombre.getApellidoMaterno());
            }
            
            //Setear el folio del elemento para identificar entre instituciones
			toSend.setFolioProbableResponsable(medidaCautelar.getInvolucrado()
					.getFolioElemento());
            
            toSend.setDescripcionMedida(medidaCautelar.getDescripcionMedida());
            toSend.setFechaCreacion(WsTransformer.transformFecha(medidaCautelar
                    .getFechaCreacion()));
            toSend.setFechaInicio(WsTransformer.transformFecha(medidaCautelar
                    .getFechaInicio()));
            toSend.setFechaFin(WsTransformer.transformFecha(medidaCautelar.getFechaFin()));
            toSend.setFolioDocumento(medidaCautelar.getFolioDocumento());
            if(medidaCautelar.getValorPeriodicidad()!= null && medidaCautelar.getValorPeriodicidad().getValorId()!=null){
            	toSend.setIdValorPeriodicidad(medidaCautelar.getValorPeriodicidad()
                    .getValorId());
            }
            toSend.setIdValorTipoMedida(medidaCautelar.getValorTipoMedida().getValorId());
            toSend.setNumeroCarpetaEjecucion(medidaCautelar.getNumeroCarpetaEjecucion());
            toSend.setNumeroCaso(medidaCautelar.getNumeroCaso());
            toSend.setNumeroCausa(medidaCautelar.getNumeroCausa());
            toSend.setFormaId(medidaCautelar.getForma().getFormaId());
            toSend.setTipoDocumentoDTO(medidaCautelar.getTipoDocumento().getValorId());
            toSend.setConfInstitucionId(medidaCautelar.getConfInstitucion().getConfInstitucionId());
            
            ArchivoDigitalWSDTO archivo = new ArchivoDigitalWSDTO();
            ArchivoDigital original = medidaCautelar.getArchivoDigital();

            archivo.setContenido(original.getContenido());
            archivo.setNombreArchivo(original.getNombreArchivo());
            archivo.setTipoArchivo(original.getTipoArchivo());
            toSend.setArchivoDigital(archivo);
            logger.debug("Enviando medida " + medidaCautelar.getFolioDocumento() + " a través de " + url);
            port.registrarMedidaCautelar(toSend);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.medidacautelar.NSJPNegocioException_Exception e) {
        	logger.error(e.getMessage(), e);
        	throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

    }

    
    public Boolean actualizarEstatusMedidaCautelarInstitucion(MedidaCautelar medidaCautelar, Instituciones institucionDestino) throws NSJPNegocioException {
        //actualizarEstatusMedidaCautelar
    	logger.info("Inicia - actualizarEstatusMedidaCautelarInstitucion(...)");
    	logger.info("Institucion destino:"+ institucionDestino);
    	logger.info("Institucion destino:"+ institucionDestino.getValorId());
    	
    	Boolean envioExitoso= false;
		try {
			// Configuración de WS
			ConfInstitucion destino = this.institucionDao.read(institucionDestino
					.getValorId());
			URL url = new URL(
					destino.getUrlInst()
							+ "/RegistrarMedidaCautelarServiceExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"RegistrarMedidaCautelarServiceExporterImplService");

			RegistrarMedidaCautelarServiceExporterImplService ss = new RegistrarMedidaCautelarServiceExporterImplService(
                    url, SERVICE_NAME);

            RegistrarMedidaCautelarServiceExporter port = ss
                    .getRegistrarMedidaCautelarServiceExporterImplPort();

            MedidaCautelarWSDTO medidaCautelarEnviar = new MedidaCautelarWSDTO();
            
            medidaCautelarEnviar.setActivo((medidaCautelar.getEsActivo() != null && medidaCautelar
                    .getEsActivo()));
            medidaCautelarEnviar.setDescripcionMedida(medidaCautelar.getDescripcionMedida());
            medidaCautelarEnviar.setFechaCreacion(WsTransformer.transformFecha(medidaCautelar
                    .getFechaCreacion()));
            medidaCautelarEnviar.setFechaInicio(WsTransformer.transformFecha(medidaCautelar
                    .getFechaInicio()));
            medidaCautelarEnviar.setFechaFin(WsTransformer.transformFecha(medidaCautelar.getFechaFin()));
            medidaCautelarEnviar.setFolioDocumento(medidaCautelar.getFolioDocumento());
            if(medidaCautelar.getValorPeriodicidad()!= null && medidaCautelar.getValorPeriodicidad().getValorId()!=null){
            	medidaCautelarEnviar.setIdValorPeriodicidad(medidaCautelar.getValorPeriodicidad()
                    .getValorId());
            }
            medidaCautelarEnviar.setIdValorTipoMedida(medidaCautelar.getValorTipoMedida().getValorId());
            medidaCautelarEnviar.setJuezOrdena(medidaCautelar.getJuezOrdena());
            medidaCautelarEnviar.setNumeroCarpetaEjecucion(medidaCautelar.getNumeroCarpetaEjecucion());
            medidaCautelarEnviar.setNumeroCaso(medidaCautelar.getNumeroCaso());
            medidaCautelarEnviar.setNumeroCausa(medidaCautelar.getNumeroCausa());
            medidaCautelarEnviar.setFormaId(medidaCautelar.getForma().getFormaId());
            medidaCautelarEnviar.setTipoDocumentoDTO(medidaCautelar.getTipoDocumento().getValorId());
            medidaCautelarEnviar.setConfInstitucionId(medidaCautelar.getConfInstitucion().getConfInstitucionId());
            
            ArchivoDigitalWSDTO archivo = new ArchivoDigitalWSDTO();
            ArchivoDigital original = medidaCautelar.getArchivoDigital();

            archivo.setContenido(original.getContenido());
            archivo.setNombreArchivo(original.getNombreArchivo());
            archivo.setTipoArchivo(original.getTipoArchivo());
            medidaCautelarEnviar.setArchivoDigital(archivo);

            if(medidaCautelar.getEstatus()!=null && medidaCautelar.getEstatus().getValorId() != null){
            	medidaCautelarEnviar.setIdEstatus(medidaCautelar.getEstatus().getValorId());
            }
            
			// Invocación del WS
            envioExitoso = port.actualizarEstatusMedidaCautelar(medidaCautelarEnviar);
            
			logger.info("Fin - actualizarEstatusMedidaCautelarInstitucion(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.medidacautelar.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

		return envioExitoso;
    }
    
    public Boolean actualizarEstatusMandamientoInstitucion(Mandamiento mandamientoJudicial, Instituciones institucionDestino) throws NSJPNegocioException {
        //actualizarEstatusMandamientoJudicial
    	logger.info("Inicia - actualizarEstatusMandamientoJudicialInstitucion(...)");
    	logger.info("Institucion destino:"+ institucionDestino);
    	logger.info("Institucion destino:"+ institucionDestino.getValorId());
    	
    	Boolean envioExitoso= false;
		try {
			// Configuración de WS
			ConfInstitucion destino = this.institucionDao.read(institucionDestino
					.getValorId());
			URL url = new URL(
					destino.getUrlInst()
							+ "/ActualizarEstatusMandamientoInstitucionalExporterImplService?wsdl");
			final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
					"ActualizarEstatusMandamientoInstitucionalExporterImplService");

			ActualizarEstatusMandamientoInstitucionalExporterImplService ss = new ActualizarEstatusMandamientoInstitucionalExporterImplService(
	                   url, SERVICE_NAME);
			ActualizarEstatusMandamientoInstitucionalExporter port = ss
	                    .getActualizarEstatusMandamientoInstitucionalExporterImplPort();

            MandamientoWSDTO toSend = new MandamientoWSDTO();
            
            toSend.setFolioDocumento(mandamientoJudicial.getFolioDocumento());
            

            if(mandamientoJudicial.getEstatus()!=null && mandamientoJudicial.getEstatus().getValorId() != null){
            	toSend.setEstatus(mandamientoJudicial.getEstatus().getValorId());
            }
            logger.info("URL:"+ url.getHost());
            logger.info("URL:"+ url.getPort());
        	logger.info("URL:"+ url.getFile());

            envioExitoso = port.actualizarEstatusMandamientoJudicial(toSend);
			logger.info("Fin - actualizarEstatusMandamientoJudicialInstitucion(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

		return envioExitoso;
    }


	@Override
	public SolicitudDTO enviarSolicitud(SolicitudDTO solicitudDTO,
			Instituciones destino, List<DocumentoDTO> lstDocumentoAdjuntos,
			SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
    	logger.info("Inicia - enviarSolicitud(...)");

		URL ep;
		SolicitudDTO respuesta = null;
		try {
			
			if (solicitudDTO == null 
					|| destino == null ){
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			} 
			// Configuracion de WS
			ConfInstitucion instDestino = this.institucionDao.read(destino.getValorId());
			ep = new URL(instDestino.getUrlInst()+ "/EnviarSolicitudServiceExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "EnviarSolicitudServiceExporterImplService");
			      
	        EnviarSolicitudServiceExporterImplService ss = new EnviarSolicitudServiceExporterImplService(ep, SERVICE_NAME);
	        EnviarSolicitudServiceExporterImpl port = ss.getEnviarSolicitudServiceExporterImplPort();  

	        SolicitudWSDTO solicitudWSDTO = SolicitudTransformer.transformarDTO2ClientWSDTO(solicitudDTO);
	        
	        if (lstDocumentoAdjuntos != null
	        		&& !lstDocumentoAdjuntos.isEmpty()){	        	
	        	for (DocumentoDTO documentoDTO : lstDocumentoAdjuntos) {
	        		solicitudWSDTO.getLstDocumentosAdjuntos().add(DocumentoWSDTOTransformer.transformarWSDTOSolicitud(documentoDTO));
	        	}
	        }
	        
	        if (sentenciaDTO != null){
	        	solicitudWSDTO.setNumeroCausaSentencia(sentenciaDTO.getCnumeroCausa());
	        }

	        solicitudWSDTO = port.recibirSolicitud(solicitudWSDTO);
	        
	        respuesta = SolicitudTransformer.transformarClientWSDTO2DTO(solicitudWSDTO);
	        
			logger.info("Fin - enviarSolicitud(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.solicitud.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
		return respuesta;
	}    
}
