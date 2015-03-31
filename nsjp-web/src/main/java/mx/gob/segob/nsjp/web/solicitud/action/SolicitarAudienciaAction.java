package mx.gob.segob.nsjp.web.solicitud.action;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.converters.SolicitudMichConverter;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.delegate.actuaciones.ActuacionesDelegate;
import mx.gob.segob.nsjp.delegate.caso.CasoDelegate;
import mx.gob.segob.nsjp.delegate.configuracion.ConfiguracionDelegate;
import mx.gob.segob.nsjp.delegate.delito.DelitoDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.SituacionImputadoDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.solicitud.*;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.solicitud.form.SolicitudAudienciaForm;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import sun.org.mozilla.javascript.internal.regexp.SubString;



/**
 *
 * @author Jacob Lobaco
 */
public class SolicitarAudienciaAction extends GenericAction {

    private static final Logger logger =
            Logger.getLogger(SolicitarAudienciaAction.class);
    @Autowired
    private InvolucradoDelegate involucradoDelegate;
    @Autowired
    private ActuacionesDelegate actuacionesDelegate;
    @Autowired
    private CasoDelegate casoDelegate;
    @Autowired
    private ExpedienteDelegate expedienteDelegate;
    @Autowired
	private ConfiguracionDelegate  configuracionDelegate;
    @Autowired
    private DelitoDelegate delitoDelegate;
    @Autowired
    private SolicitudDelegate solicitudDelegate;
    
    @Autowired
    private DocumentoDelegate documentoDelegate;
    @Autowired
    private DelitoPersonaDAO delitoPersonaDAO;
    @Autowired
    private NumeroExpedienteDAO numeroExpedienteDAO;
    
    @Autowired
    private ValorDAO valorDAO;
    
    /**
     * Obtiene el "numeroeExpediente" del expediente que se esta trabajando en
     * sesion.
     * @param request
     * @return
     */
    private String obtenNumeroExpediente(HttpServletRequest request) {
        String numeroExpediente = (String) request.getParameter("numeroExpediente");
        if (logger.isDebugEnabled()) {
            logger.debug("numeroExpediente = " + numeroExpediente);
        }
        return numeroExpediente;
    }

    /**
     * Obtiene el caso asociado al expediente que esta en sesion.
     * @param request
     * @return
     * @throws NSJPNegocioException
     */
    private CasoDTO obtenerCasoPorNumeroExpediente(HttpServletRequest request)
            throws NSJPNegocioException {
        String numeroExpediente = obtenNumeroExpediente(request);
        ExpedienteDTO expedienteDTO = getExpedienteTrabajo(request, numeroExpediente);
        if (logger.isDebugEnabled()) {
            logger.debug("expedienteDTO.getExpedienteId() = " + expedienteDTO.getExpedienteId());
        }
        return expedienteDTO.getCasoDTO();
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward consultarCasoPorExpediente(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("consultarCasoPorExpediente");
            }
            // Consultamos el caso asociado al expediente que esta en sesion.
            CasoDTO casoDTO = obtenerCasoPorNumeroExpediente(request);
            if (casoDTO == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("No existe una caso asociado al expediente = "
                            + obtenNumeroExpediente(request));
                }
                throw new NSJPNegocioException(CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);
            } else {
                String xml = converter.toXML(casoDTO);
                escribirRespuesta(response, xml);
                response.setContentType("text/xml");
                PrintWriter pw = response.getWriter();
                pw.print(xml);
                pw.flush();
                pw.close();
            }
        } catch (NSJPNegocioException ex) {
            logger.error("Ocurrio un error en consultarCasoPorExpediente", ex);
            escribirError(response, ex);
        }
        return null;
    }

    public ActionForward consultarInvolucradoPorCalidadCaso(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("consultarInvolucradoPorCalidadCaso");
            }
            // De nuevo obtenemos el caso del expediente en session.
            CasoDTO casoDTO = obtenerCasoPorNumeroExpediente(request);
            
//            SolicitudMichDTO solicitudMichDTO = new SolicitudMichDTO();
            
            SituacionImputadoDTO situaciocImputadoDTO = new SituacionImputadoDTO();
//            List<Long> situacionImpIds= null;
            
            int institucionActual = 0;
			ConfInstitucionDTO loInstitucion = configuracionDelegate.consultarInstitucionActual();
            if(loInstitucion != null && loInstitucion.getConfInstitucionId() != null)
				institucionActual = loInstitucion.getConfInstitucionId().intValue();
            
            logger.info("Institución Actual ---- " + institucionActual);
            
            if (casoDTO == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("No existe una caso asociado al expediente = "
                            + obtenNumeroExpediente(request));
                }
                throw new NSJPNegocioException(CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);
            } else {
                CalidadDTO calidadDTO = new CalidadDTO();
                // Consultamos los involucrados que sean probables responsables.
                calidadDTO.setCalidades(Calidades.PROBABLE_RESPONSABLE_PERSONA);
                calidadDTO.setCalidadId(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId());
                List<InvolucradoDTO> involucradosProbablesResponsables =
                        involucradoDelegate.consultarInvolucradoPorCalidadCaso(casoDTO, calidadDTO);
                
//                for (InvolucradoDTO involucradoDTO : involucradosProbablesResponsables) {
//					
//                	Long invId =  involucradoDTO.getElementoId();
//                	
//                	logger.info("Involucrado Id PR ::::::::: " + invId);
//                	situacionImpIds =  delitoPersonaDAO.obtenerSituacionImputadoPorInvolucrado(invId);
//                	logger.info("Situacion Imputado Id PR ---------- " + situacionImpIds);
//				}
//                
//                situaciocImputadoDTO = delitoDelegate.consultarSituacionImputadoPorId(situacionImpIds.get(0));
                
                
                // Consultamos a los involucrados que sean victimas.
                calidadDTO.setCalidades(Calidades.VICTIMA_PERSONA);
                calidadDTO.setCalidadId(Calidades.VICTIMA_PERSONA.getValorId());
                List<InvolucradoDTO> involucradosVictimas =
                        involucradoDelegate.consultarInvolucradoPorCalidadCaso(casoDTO, calidadDTO);
                
                //Consultar los Denunciantes que son víctimas
                calidadDTO.setCalidades(Calidades.DENUNCIANTE);
                calidadDTO.setCalidadId(Calidades.DENUNCIANTE.getValorId());
                List<InvolucradoDTO> involucradosDenunciantesVictimas =
                        involucradoDelegate.consultarInvolucradoPorCalidadCaso(casoDTO, calidadDTO);
                for (InvolucradoDTO involucradoDTO : involucradosDenunciantesVictimas) {
                	involucradoDTO.setSituacionImpDTO(situaciocImputadoDTO); //Se  setea la Situacion del Imputado
                	                	
                	//Validar si es víctima
                	if(involucradoDTO.getCondicion() != null && involucradoDTO.getCondicion().equals(new Short("1"))){
                		involucradosProbablesResponsables.add(involucradoDTO);
                	}
                	else if(institucionActual == Instituciones.DEF.getValorId()){
                		involucradosProbablesResponsables.add(involucradoDTO);
                	}
				}
                converter.alias("involucrado", InvolucradoDTO.class);
                converter.alias("nombreDemografico", NombreDemograficoDTO.class);
                // Los juntamos en una sola lista que sera filtrada en la vista
                // con javascript
                involucradosProbablesResponsables.addAll(involucradosVictimas);
                if (logger.isDebugEnabled()) {
                    logger.debug("involucradosProbablesResponsables.size() = "
                            + involucradosProbablesResponsables.size());
                }
                
                String xml = converter.toXML(involucradosProbablesResponsables);
                if (logger.isDebugEnabled()) {
                    logger.debug("involucrados = " + xml);
                }
                escribirRespuesta(response, xml);
                response.setContentType("text/xml");
                PrintWriter pw = response.getWriter();
                pw.print(xml);
                pw.flush();
                pw.close();
            }
        } catch (NSJPNegocioException ex) {
            logger.error("Ocurrio un error al consultarInvolucradoPor"
                    + "CalidadCaso", ex);
            escribirError(response, ex);
        }
        return null;
    }

    public ActionForward validarExisteCaso(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String numeroDeCaso = request.getParameter("numeroDeCaso");
            if (logger.isDebugEnabled()) {
                logger.debug("numeroDeCaso = " + numeroDeCaso);
            }
            if (numeroDeCaso != null) {
                CasoDTO casoDTO = new CasoDTO();
                casoDTO.setNumeroGeneralCaso(numeroDeCaso);
                List<CasoDTO> casos = casoDelegate.consultarCasoPorNumero(casoDTO);
                Boolean existeCaso = casos != null && casos.size() > 0;
                String existeCasoXML = converter.toXML(existeCaso);
                escribirRespuesta(response, existeCasoXML);
            }
        } catch (NSJPNegocioException ex) {
            logger.error("Ocurrio un error en validarExisteCaso", ex);
            escribirError(response, ex);
        }
        return null;
    }
    /**
     * Envía la solicutd de audiencia.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward enviarSolicitudAudiencia(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("enviarSolicitudAudiencia");
        }
        if (form != null) {
            try {
                SolicitudAudienciaForm forma = (SolicitudAudienciaForm) form;
                if (logger.isDebugEnabled()) {
                    logger.debug("Se obtuvo la forma = " + forma);
                }
                
                logger.info("Documentos ::::::::::::::::::::::::>>>>>>> = " + forma.getIdsDoctsSelecc());
                logger.info("Fecha Enviooooooooooo ................... " + forma.getFechaEnvio());
                logger.info("Fecha Inicioooo ------------------------ " + forma.getFechaInicio());
                
                SolicitudAudienciaDTO solicitudAudienciaDTO = new SolicitudAudienciaDTO();
                AudienciaDTO audienciaDTO = new AudienciaDTO();
                ValorDTO tipoAudienciaDto = new ValorDTO();
                if (logger.isDebugEnabled()) {
                    logger.debug("forma.getTipoDeAudiencia() = " + forma.getTipoDeAudiencia());
                }
                tipoAudienciaDto.setIdCampo(forma.getTipoDeAudiencia());
                audienciaDTO.setTipoAudiencia(tipoAudienciaDto);
                solicitudAudienciaDTO.setAudiencia(audienciaDTO);
                solicitudAudienciaDTO.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.AUDIENCIA.getValorId()));
                AreaDTO areaActual = getUsuarioFirmado(request).getAreaActual();
                if (areaActual != null) {
                    solicitudAudienciaDTO.setAreaOrigen(areaActual.getAreaId());
                }
                UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
                if (usuarioFirmado.getFuncionario() != null
                 && usuarioFirmado.getFuncionario().getDepartamento() != null
                 && usuarioFirmado.getFuncionario().getDepartamento().getArea() != null) {
                    forma.setInstitucionSolicitante(usuarioFirmado.getFuncionario().
                            getDepartamento().getArea().getAreaId());
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("forma.getInstitucionSolicitante() = "
                            + forma.getInstitucionSolicitante());
                }
                solicitudAudienciaDTO.setInstitucion(null);
                solicitudAudienciaDTO.setMotivo(forma.getFundamentoDeLaSolicitud());
                if (logger.isDebugEnabled()) {
                    logger.debug("forma.getFundamentoDeLaSolicitud() = "
                            + forma.getFundamentoDeLaSolicitud());
                }
                solicitudAudienciaDTO.setNombreSolicitante(forma.getNombreDelSolicitante());
                if (logger.isDebugEnabled()) {
                    logger.debug("forma.getNombreDelSolicitante() = "
                            + forma.getNombreDelSolicitante());
                }
                solicitudAudienciaDTO.setNumeroCasoAsociado(forma.getNumeroDeCaso());
                if (logger.isDebugEnabled()) {
                    logger.debug("forma.getNumeroDeCaso() = "
                            + forma.getNumeroDeCaso());
                }
                solicitudAudienciaDTO.setStrFechaCreacion(new Date() + "");
                solicitudAudienciaDTO.setStrHoraCreacion(System.currentTimeMillis() + "");
                solicitudAudienciaDTO.setStrFechaLimite(forma.getFechaLimiteAudiencia());
                // En la fecha de creacion va la fecha y la hora
                solicitudAudienciaDTO.setFechaCreacion(new Date());
                // en la fecha limite va la fecha y la hora hh:mm
                // 15/6/2011 14:22
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String fechaCompleta = forma.getFechaLimiteAudiencia() + " "+ forma.getHoraLimiteAudiencia();
                try {
                    Date fechaLimite = df.parse(fechaCompleta);
                    solicitudAudienciaDTO.setFechaLimite(fechaLimite);
                    if (logger.isDebugEnabled()) {
                        logger.debug("fechaLimite = " + fechaLimite);
                    }
                } catch (ParseException ex) {
                    logger.error("Error de formato (dd/MM/yyyy HH:mm) con la fecha: "+ fechaCompleta);
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("forma.getFechaLimiteAudiencia() = "+ forma.getFechaLimiteAudiencia());
                }
                solicitudAudienciaDTO.setStrHoraLimite(forma.getHoraLimiteAudiencia());
                if (logger.isDebugEnabled()) {
                    logger.debug("forma.getHoraLimiteAudiencia() = "+ forma.getHoraLimiteAudiencia());
                }
                solicitudAudienciaDTO.setSolicitanteExterno(usuarioFirmado.getFuncionario().getClaveFuncionario());
                solicitudAudienciaDTO.setUsuarioSolicitante(usuarioFirmado.getFuncionario());
                if(usuarioFirmado!=null &&
                   usuarioFirmado.getFuncionario()!=null &&
                   usuarioFirmado.getFuncionario().getPuesto()!=null &&
                   usuarioFirmado.getFuncionario().getPuesto().getValor()!=null){
                	solicitudAudienciaDTO.getUsuarioSolicitante().getPuesto().setNombreCampo(usuarioFirmado.getFuncionario().getPuesto().getValor());
                }
                if(usuarioFirmado!=null &&
                   usuarioFirmado.getFuncionario()!=null &&
                   usuarioFirmado.getFuncionario().getJerarquiaOrganizacional()!=null &&
                   usuarioFirmado.getFuncionario().getJerarquiaOrganizacional().getNombre()!=null){
                	solicitudAudienciaDTO.getUsuarioSolicitante().getJerarquiaOrganizacional().setNombre(
                			usuarioFirmado.getFuncionario().getJerarquiaOrganizacional().getNombre());
                }
                
                /*Obtener expediente de sesión*/
                ExpedienteDTO expedienteDTO=new ExpedienteDTO();
//                expedienteDTO.setExpedienteId(NumberUtils.toLong(forma.getIdExpedienteSoli(),0));
                if(forma.getIdNumeroExpediente() != null){
//                	expedienteDTO.setNumeroExpedienteId(NumberUtils.toLong(forma.getIdNumeroExpediente(),0));
                	expedienteDTO.setNumeroExpedienteId(Long.parseLong(forma.getIdNumeroExpediente()));
                	expedienteDTO.setExpedienteId(expedienteDelegate.obtenerExpedienteIdPorNumExpId(expedienteDTO));
                }
                expedienteDTO.setUsuario(usuarioFirmado);  
                /**
                 *Se agrega lo referente al envío de solicitud 
                 */
                if (logger.isDebugEnabled()) {
                    logger.debug("**********************************forma.getDistrito() = "
                            + forma.getDistrito());
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("**********************************forma.getTribunal() = "
                            + forma.getTribunal());
                }
                
                if (logger.isDebugEnabled()) {
                    logger.debug("**********************************forma.getFuncionarioDestinatario() = "
                            + forma.getFuncionarioDestinatario());
                }
                
                String respuesta="";
//                List<DocumentoDTO> documentos=new ArrayList<DocumentoDTO>();
                
                logger.info("enviarSolicitudAudiencia - areaUsuarioFirmado : "+usuarioFirmado.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId());
                
                //Se atiende inciencia para Enviar Solicitud y Generar denuncia NO SIEMPRE APLICA
                //Se cambia por consultar tipo de actividad
                //--2242	Generar querella
                //--1647	Generar denuncia
                //--2134	Atender canalización en la Unidad de Fiscales Investigadores
//                Long[] idTA= {Actividades.GENERAR_QUERELLA.getValorId(),
//        				Actividades.GENERAR_DENUNCIA_EN_ATP.getValorId(),
//        				Actividades.ATENDER_CANALIZACION_UI.getValorId()  //FIXME GBP verificar si no se estan guardado el Tipo documento: 
//        				//  82 Solicitud  Denuncia en Atención Temprana --  Tipo 82 Forma 1 ****Debe de ser 83 Pero hay 82 
//        				//  83 Acta Denuncia en Unidad de Investigación --  Tipo 82 Forma 50    ...Esta bien....
//        				};
//        		List<Long> idTipoActividades= Arrays.asList(idTA);
//        		Boolean documentoRec = true; //Debe de generarse la actuación con documento 
//                List<ActividadDTO> actividadesDTO = actividadDelegate.consultarActividadesPorTipoActividadExpedienteId(expedienteDTO.getExpedienteId(), idTipoActividades, documentoRec); 

//                logger.info("enviarSolicitudAudiencia - areaUsuarioFirmado atpenal : "+Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong());
//                logger.info("enviarSolicitudAudiencia - areaUsuarioFirmado agenteMP: "+Areas.UNIDAD_INVESTIGACION.parseLong());
//                
//    			if(usuarioFirmado.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().longValue()== Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong())
//    			{	
//    				logger.info("enviarSolicitudAudiencia - Consultando documentos por ATENCION_TEMPRANA_PG_PENAL.....");
//    				documentos=documentoDelegate.consultarDocumentosXTipoDocumento(expedienteDTO,83L);
//    			}
//    			else if(usuarioFirmado.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().longValue()== Areas.UNIDAD_INVESTIGACION.parseLong())
//    			{
//    				logger.info("enviarSolicitudAudiencia - Consultando documentos por area UNIDAD_INVESTIGACION .....");
//    				documentos=documentoDelegate.consultarDocumentosXTipoDocumento(expedienteDTO,82L);
//    			}
//    			logger.info("enviarSolicitudAudiencia - tamano lista documentos:: "+documentos.size());
    			
    		       // se obtienen
                Long idDistrito = forma.getDistrito();
                Long idTribunal = forma.getTribunal();
                Long idClaveFuncionario = forma.getFuncionarioDestinatario();
                //eNABLE IT se recuperan los Ids de los documentos seleccionados para enviarse con la solicitud de audiencia.
                solicitudAudienciaDTO.setIdsDocumentos(forma.getIdsDoctsSelecc());
    			
    			//if(documentos.size()>0)
                //if(actividadesDTO.size()>0)

                //Se consulta si el xpediente fue replicado antes de enviar la solicitud.
    			expedienteDTO.setInvolucradosSolicitados(true);
                ExpedienteDTO expDTO = expedienteDelegate.obtenerExpedientePorExpedienteId(expedienteDTO);
                
                //Incian cambios RGG: Incidencia 35-1
                int institucionActual = 0;
    			ConfInstitucionDTO loInstitucion = configuracionDelegate.consultarInstitucionActual();
                if(loInstitucion != null && loInstitucion.getConfInstitucionId() != null)
    				institucionActual = loInstitucion.getConfInstitucionId().intValue();
                
                //Si la institucion es diferente a PG 
                if(institucionActual == Instituciones.PGJ.getValorId()){
                    //Si ha sido replicado, es posible su envío
                    if( expDTO!= null && expDTO.getEsReplicado() != null && expDTO.getEsReplicado() )
    				{
    					actuacionesDelegate.enviarSolicitudDeAudiencia(solicitudAudienciaDTO, expedienteDTO, idDistrito, idTribunal, idClaveFuncionario );
    					respuesta="Solicitud enviada correctamente.";
    				}
    				else
    				{
    					respuesta="No se ha generado la denuncia, favor de realizarla.";
    				}
                }else{
                		//Si la institucion actual NO es PGJ entonces envia la solicitud de audicencia sin importar si la denuncia fue creada
    					actuacionesDelegate.enviarSolicitudDeAudiencia(solicitudAudienciaDTO, expedienteDTO, idDistrito, idTribunal, idClaveFuncionario );
    					respuesta="Solicitud enviada correctamente.";
                }
                //Finalizan cambios RGG: Incidencia 35-1
            
    			
    			//mandamos la respuesta
                escribirRespuesta(response, respuesta);
               
            } catch (NSJPNegocioException ex) {
                logger.error("Ocurrio un error en enviarSolicitudAudiencia", ex);
                escribirError(response, ex);
            }
        }
        return null;
    }

    public ActionForward validarPlazoConstitucionalDeAudiencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        return null;
    }
    
    
    /**
     * Envía la solicutd de audiencia Michoacan.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */

    public ActionForward enviarSolicitudAudienciaMich(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    				throws IOException {
    	if (logger.isDebugEnabled()) {
    		logger.debug("enviarSolicitudAudienciaMich");
    	}

    	List<ImputadoDTO> imputadosDTO = new ArrayList<ImputadoDTO>();
    	List<OfendidoDTO> ofendidosDTO = new ArrayList<OfendidoDTO>();
    	List<ImpDelOfendidoDTO> impOfendidosDTO = new ArrayList<ImpDelOfendidoDTO>();
    	List<ImputadoDelitoDTO> imputadoDelitosDTO = new ArrayList<ImputadoDelitoDTO>();
    	List<DocumentoWSMichDTO> documentosWsMichDTO = new ArrayList<DocumentoWSMichDTO>();

    	if (form != null) {
    		try {
    			SolicitudAudienciaForm forma = (SolicitudAudienciaForm) form;
    			if (logger.isDebugEnabled()) {
    				logger.debug("Se obtuvo la forma = " + forma);
    			}

    			NumeroExpediente numExpediente = numeroExpedienteDAO.read(Long.parseLong(forma.getIdNumeroExpediente()));
    			String numExp = numExpediente.getNumeroExpediente();
    			ExpedienteDTO expedientDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numExp);
    			logger.info("numero Expediente ID _:::: " + expedientDTO.getExpedienteId());
    			CasoDTO casoDTO = casoDelegate.consultarCasoPorExpediente(expedientDTO);

    			SituacionImputadoDTO situacionImputadoDTO = new SituacionImputadoDTO();
    			List<Long> situacionImpIds= null;
    			String mensDetencion = "";

    			if (casoDTO == null) {
    				if (logger.isDebugEnabled()) {
    					logger.debug("No existe una caso asociado al expediente = "
    							+ obtenNumeroExpediente(request));
    				}
    				throw new NSJPNegocioException(CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);
    			} else {

    				CalidadDTO calidadDTO = new CalidadDTO();
    				// Consultamos los involucrados que sean probables responsables.
    				calidadDTO.setCalidades(Calidades.PROBABLE_RESPONSABLE_PERSONA);
    				calidadDTO.setCalidadId(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId());
    				List<InvolucradoDTO> involucradosProbablesResponsables = involucradoDelegate.consultarInvolucradoPorCalidadCaso(casoDTO, calidadDTO);


    				ImpDelOfendidoDTO impDelOfendidoDTO = null;

    				// Consultamos a los involucrados que sean victimas.
    				calidadDTO.setCalidades(Calidades.VICTIMA_PERSONA);
    				calidadDTO.setCalidadId(Calidades.VICTIMA_PERSONA.getValorId());
    				List<InvolucradoDTO> involucradosVictimas = involucradoDelegate.consultarInvolucradoPorCalidadCaso(casoDTO, calidadDTO);


    				//Consultar los Denunciantes que son víctimas
    				calidadDTO.setCalidades(Calidades.DENUNCIANTE);
    				calidadDTO.setCalidadId(Calidades.DENUNCIANTE.getValorId());
    				List<InvolucradoDTO> involucradosDenunciantesVictimas = involucradoDelegate.consultarInvolucradoPorCalidadCaso(casoDTO, calidadDTO);
    				logger.info("Documentos IDSSSSSSSS ::::::::::::::::::::::::>>>>>>> = " + forma.getIdsDoctsSelecc());


    				for (InvolucradoDTO involucradoDTO : involucradosProbablesResponsables) {
    					ImputadoDTO imputadoDTO = new ImputadoDTO();
    					imputadoDTO.setConsec_imp(Integer.toString(involucradoDTO.getElementoId().intValue()));
    					imputadoDTO.setAp_pater(involucradoDTO.getPrimerNombreDemografico().getApellidoPaterno());
    					imputadoDTO.setAp_mater(involucradoDTO.getPrimerNombreDemografico().getApellidoMaterno());
    					imputadoDTO.setEdad((involucradoDTO.getPrimerNombreDemografico().getEdadAproximada()).toString());
    					imputadoDTO.setFec_registro(formatearFecha((involucradoDTO.getFechaCreacionElemento()).toString()));
//    									  imputadoDTO.setFec_nac(formatearFecha((involucradoDTO.getPrimerNombreDemografico().getFechaNacimiento()).toString()));
    					imputadoDTO.setNombre(involucradoDTO.getPrimerNombreDemografico().getNombre());
    					imputadoDTO.setSexo(involucradoDTO.getPrimerNombreDemografico().getSexo());
    					


    					logger.info("Involucrado Id PR ::::::::: " + involucradoDTO.getElementoId());
    					situacionImpIds =  delitoPersonaDAO.obtenerSituacionImputadoPorInvolucrado(involucradoDTO.getElementoId());
    					logger.info("Situacion Imputado Id PR ---------- " + situacionImpIds);

    					situacionImputadoDTO = delitoDelegate.consultarSituacionImputadoPorId(situacionImpIds.get(0));

    					imputadoDTO.setSituacion((situacionImputadoDTO.getSituacionImputadoId()).toString());

    					if (forma.getTipoIni().equals("1")){
							if (forma.getFechaEnvio() == null || involucradoDTO.getUltimaDetencion().getFechaInicioDetencion() == null 
							|| involucradoDTO.getUltimaDetencion().getFechaFinDetencion() == null){
								if (forma.getTipoDeAudiencia() == 20794 || forma.getTipoDeAudiencia() == 292 || forma.getTipoDeAudiencia() == 1715)
    								mensDetencion = "Los Datos de Detención Son Requeridos para este Tipo de Audiencia";
								else if (expedientDTO.getArea().getAreaId() == Areas.DEFENSORIA.ordinal()){
	        						imputadoDTO.setFec_disposicion_pj(formatearFecha(forma.getFechaEnvio()));
	        						imputadoDTO.setFec_detencion(formatearFecha((involucradoDTO.getUltimaDetencion().getFechaInicioDetencion()).toString()));
	        						logger.info("Fecha Ini Detencion :::: --- " + involucradoDTO.getUltimaDetencion().getFechaInicioDetencion());
	        						logger.info("Fecha Ini Detencion List ::: " + involucradoDTO.getDetenciones().get(0).getFechaInicioDetencion());
	        						logger.info("Fecha Fin Detencion Str :::: --- " + involucradoDTO.getDetenciones().get(0).getStrFechaRecepcionDetencion());
	        						imputadoDTO.setFecha_presmp(formatearFecha((involucradoDTO.getDetenciones().get(0).getStrFechaRecepcionDetencion())));
	        						// Id del Defensor
	        						imputadoDTO.setId_defensor("2696");
	        						imputadoDTO.setTipo_defensor("1");
	        						
	        						imputadoDTO.setEsDefensoria(true);
//	        						InvolucradoDTO invDTO = involucradoDelegate.obtenerInvolucrado(involucradoDTO);
//
//	        						logger.info("Domicilioooo Inv DTO    Imputadoooooooo ----- " + invDTO.getDomicilio().getDireccion());
//	        						
//	        						if (forma.getTipoDeAudiencia() == 1715){
//	        							imputadoDTO.setColonia_id_imp("1");
//		        						imputadoDTO.setColonia_localidad_imp("53");
//		        						imputadoDTO.setColonia_municipio_imp("53");
//		        						imputadoDTO.setColonia_zona_imp("U");
//		        						imputadoDTO.setColonia_estado_imp("16");
//		        						imputadoDTO.setDomicilio_imp(invDTO.getDomicilio().getDireccion());
//	        						}
								}
									
    						}
    						else{
        						imputadoDTO.setFec_disposicion_pj(formatearFecha(forma.getFechaEnvio()));
        						imputadoDTO.setFec_detencion(formatearFecha((involucradoDTO.getUltimaDetencion().getFechaInicioDetencion()).toString()));
        						logger.info("Fecha Detencion :::: --- " + involucradoDTO.getUltimaDetencion().getFechaInicioDetencion());
        						logger.info("Fecha Detencion List ::: " + involucradoDTO.getDetenciones().get(0).getFechaInicioDetencion());
        						imputadoDTO.setFecha_presmp(formatearFecha((involucradoDTO.getUltimaDetencion().getFechaFinDetencion()).toString()));
        						// Id del Defensor
        						imputadoDTO.setId_defensor("2696");
        						imputadoDTO.setTipo_defensor("1");
        						
        						InvolucradoDTO invDTO = involucradoDelegate.obtenerInvolucrado(involucradoDTO);

        						logger.info("Domicilioooo Inv DTO    Imputadoooooooo ----- " + invDTO.getDomicilio().getDireccion());
        						
        						if (forma.getTipoDeAudiencia() == 1715){
        							imputadoDTO.setColonia_id_imp("1");
	        						imputadoDTO.setColonia_localidad_imp("53");
	        						imputadoDTO.setColonia_municipio_imp("53");
	        						imputadoDTO.setColonia_zona_imp("U");
	        						imputadoDTO.setColonia_estado_imp("16");
	        						imputadoDTO.setDomicilio_imp(invDTO.getDomicilio().getDireccion());
        						}
    						}
    					} 
    					else if (forma.getTipoDeAudiencia() == 20794 || forma.getTipoDeAudiencia() == 292 || forma.getTipoDeAudiencia() == 1715) {
    						mensDetencion = "Los Datos de Detención Son Requeridos para este Tipo de Audiencia";
    					}

    					logger.info("consecImp   Imputado Delito y Imp Del Ofendido" + involucradoDTO.getElementoId());
    					List<DelitoDTO> delitos = involucradoDTO.getDelitosCometidos();
    					logger.info("Numero de Delitos :::: " + delitos.size());

    					for (DelitoDTO delitoDTO : delitos) {
    						impDelOfendidoDTO = new ImpDelOfendidoDTO();
    						logger.info("Delito n   "  + delitoDTO.getDelitoId());
    						impDelOfendidoDTO.setConsec_imp((involucradoDTO.getElementoId()).toString());
    						impDelOfendidoDTO.setDelito((delitoDTO.getCatDelitoDTO().getClaveInterInstitucional()));
    						logger.info("Clave Interinst  Delito ---- " + delitoDTO.getCatDelitoDTO().getClaveInterInstitucional());
    						logger.info("CatDelito Id --- " + delitoDTO.getCatDelitoDTO().getCatDelitoId());
    						logger.info("Clave Delito :::: " + delitoDTO.getCatDelitoDTO().getClaveDelito());

    						List<DelitoPersona> delitoPersonas = delitoPersonaDAO.consultarVictimaImputadoPorDelito(delitoDTO.getDelitoId(), delitoDTO.getExpedienteDTO().getExpedienteId());

    						impDelOfendidoDTO.setOfendido(delitoPersonas.get(0).getVictima().getElementoId().toString());
    						logger.info("Victima ElemId  ImpDel Ofendido ............ " + delitoPersonas.get(0).getVictima().getElementoId().toString() );

    						impOfendidosDTO.add(impDelOfendidoDTO);

    					}
    					List<DelitoPersonaDTO> delitosPersona = delitoDelegate.consultarDelitosVictimaPorImputado(involucradoDTO.getElementoId());

    					for (DelitoPersonaDTO delitoPersonaDTO : delitosPersona) {
    						ImputadoDelitoDTO imputadoDelitoDTO = new ImputadoDelitoDTO();
    						imputadoDelitoDTO.setEstado_psicofisico((delitoPersonaDTO.getCatDelitoCausaId()).toString());
    						imputadoDelitoDTO.setGrado_consumacion((delitoPersonaDTO.getCatDelitoModusId()).toString());
    						imputadoDelitoDTO.setIntencionalidad((delitoPersonaDTO.getCatDelitoClasificacionId()).toString());
    						imputadoDelitoDTO.setFecha_delito(formatearFecha(forma.getFechaInicio()));
    						imputadoDelitoDTO.setConsec_imp(delitoPersonaDTO.getProbableResponsable().getElementoId().toString());
    						imputadoDelitoDTO.setDelito(delitoPersonaDTO.getDelito().getCatDelitoDTO().getClaveInterInstitucional());

    						imputadoDelitosDTO.add(imputadoDelitoDTO);
    					}


    					imputadosDTO.add(imputadoDTO);
    				}



    				for (InvolucradoDTO involucradoVictimaDTO : involucradosVictimas) {
    					OfendidoDTO ofendidoDTO = new OfendidoDTO();
    					ofendidoDTO.setAp_mater(involucradoVictimaDTO.getPrimerNombreDemografico().getApellidoMaterno());
    					ofendidoDTO.setAp_pater(involucradoVictimaDTO.getPrimerNombreDemografico().getApellidoPaterno());
    					ofendidoDTO.setNombre(involucradoVictimaDTO.getPrimerNombreDemografico().getNombre());
    					ofendidoDTO.setOfendido((involucradoVictimaDTO.getElementoId()).toString());
    					ofendidoDTO.setSexo(involucradoVictimaDTO.getPrimerNombreDemografico().getSexo());
    					ofendidoDTO.setTipo_parte("1");
    					ofendidoDTO.setTipo_persona((involucradoVictimaDTO.getTipoPersona())==1?"F":"M");

    					ofendidosDTO.add(ofendidoDTO);
    				}

    				for (InvolucradoDTO involucradoDenuncianteVicDTO : involucradosDenunciantesVictimas) {
    					//Validar si es víctima
    					if(involucradoDenuncianteVicDTO.getCondicion().equals(new Short("1"))){

    						OfendidoDTO ofendidoDTO = new OfendidoDTO();
    						ofendidoDTO.setAp_mater(involucradoDenuncianteVicDTO.getPrimerNombreDemografico().getApellidoMaterno());
    						ofendidoDTO.setAp_pater(involucradoDenuncianteVicDTO.getPrimerNombreDemografico().getApellidoPaterno());
    						ofendidoDTO.setNombre(involucradoDenuncianteVicDTO.getPrimerNombreDemografico().getNombre());
    						ofendidoDTO.setOfendido((involucradoDenuncianteVicDTO.getElementoId()).toString());
    						ofendidoDTO.setSexo(involucradoDenuncianteVicDTO.getPrimerNombreDemografico().getSexo());
    						ofendidoDTO.setTipo_parte("1");
    						ofendidoDTO.setTipo_persona((involucradoDenuncianteVicDTO.getTipoPersona())==1?"F":"M");

    						ofendidosDTO.add(ofendidoDTO);
    					}
    				}
    			}

    			if(forma.getIdsDoctsSelecc()!=null || forma.getIdsDoctsSelecc().equals("")){

    				Base64 base64 = new Base64();
    				String archivoCodificado;


    				List<DocumentoDTO> documentosDTO = documentoDelegate.consultarDocumentosIdSeleccionados(forma.getIdsDoctsSelecc());

    				for (DocumentoDTO documentoDTO : documentosDTO) {
    					DocumentoWSMichDTO documentoWsMichDTO = new DocumentoWSMichDTO();
    					archivoCodificado = "";
    					archivoCodificado = base64.encodeToString(documentoDTO.getArchivoDigital().getContenido());
    					documentoWsMichDTO.setArchivo(archivoCodificado);
    					documentoWsMichDTO.setConsec_doc(documentoDTO.getDocumentoId().toString());
    					documentoWsMichDTO.setFormato(documentoDTO.getArchivoDigital().getTipoArchivo().substring(1,documentoDTO.getArchivoDigital().getTipoArchivo().length()));
    					documentoWsMichDTO.setNombre(documentoDTO.getArchivoDigital().getNombreArchivo());

    					documentosWsMichDTO.add(documentoWsMichDTO);
    				}

    			}

    			Valor tipo =valorDAO.read(forma.getTipoDeAudiencia());

    			Valor complejidad=valorDAO.consultarComplejidadTipoAudiencia(tipo, true);
    			/*Transformación*/
    			ValorDTO compDTO = new ValorDTO(complejidad.getValorId(), complejidad.getValor(), complejidad.getCampoCatalogo().getNombreCampo());
    			logger.info("COMPLEJIDAD ------ " +compDTO.getIdCampo() + " " + compDTO.getValor() + " " + compDTO.getNombreCampo() + " " + compDTO.getCatalogoPadre());

    			Valor caracter=valorDAO.consultarComplejidadTipoAudiencia(tipo, false);
    			/*Transformación*/
    			ValorDTO caracterDTO = new ValorDTO(caracter.getValorId(), caracter.getValor(), caracter.getCampoCatalogo().getNombreCampo());
    			logger.info("CARACTER ------ " +caracterDTO.getIdCampo() + " " + caracterDTO.getValor() + " " + caracterDTO.getNombreCampo() + " " + caracterDTO.getCatalogoPadre());


    			SolicitudMichDTO solicitudMichDTO = new SolicitudMichDTO();

    			SolicitudAudienciaDTO solicitudAudienciaDTO = new SolicitudAudienciaDTO();
    			AudienciaDTO audienciaDTO = new AudienciaDTO();
    			ValorDTO tipoAudienciaDto = new ValorDTO();
    			if (logger.isDebugEnabled()) {
    				logger.debug("forma.getTipoDeAudiencia() = " + forma.getTipoDeAudiencia());
    			}


    			tipoAudienciaDto.setIdCampo(forma.getTipoDeAudiencia());
    			audienciaDTO.setTipoAudiencia(tipoAudienciaDto);
    			solicitudAudienciaDTO.setAudiencia(audienciaDTO);
    			solicitudAudienciaDTO.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.AUDIENCIA.getValorId()));
    			AreaDTO areaActual = getUsuarioFirmado(request).getAreaActual();
    			if (areaActual != null) {
    				solicitudAudienciaDTO.setAreaOrigen(areaActual.getAreaId());
    			}
    			UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
    			if (usuarioFirmado.getFuncionario() != null
    					&& usuarioFirmado.getFuncionario().getDepartamento() != null
    					&& usuarioFirmado.getFuncionario().getDepartamento().getArea() != null) {
    				forma.setInstitucionSolicitante(usuarioFirmado.getFuncionario().
    						getDepartamento().getArea().getAreaId());
    			}
    			if (logger.isDebugEnabled()) {
    				logger.debug("forma.getInstitucionSolicitante() = "
    						+ forma.getInstitucionSolicitante());
    			}
    			solicitudAudienciaDTO.setInstitucion(null);
    			solicitudAudienciaDTO.setMotivo(forma.getFundamentoDeLaSolicitud());
    			if (logger.isDebugEnabled()) {
    				logger.debug("forma.getFundamentoDeLaSolicitud() = "
    						+ forma.getFundamentoDeLaSolicitud());
    			}
    			solicitudAudienciaDTO.setNombreSolicitante(forma.getNombreDelSolicitante());
    			if (logger.isDebugEnabled()) {
    				logger.debug("forma.getNombreDelSolicitante() = "
    						+ forma.getNombreDelSolicitante());
    			}
    			solicitudAudienciaDTO.setNumeroCasoAsociado(forma.getNumeroDeCaso());
    			if (logger.isDebugEnabled()) {
    				logger.debug("forma.getNumeroDeCaso() = "
    						+ forma.getNumeroDeCaso());
    			}
    			solicitudAudienciaDTO.setStrFechaCreacion(new Date() + "");
    			solicitudAudienciaDTO.setStrHoraCreacion(System.currentTimeMillis() + "");
    			solicitudAudienciaDTO.setStrFechaLimite(forma.getFechaLimiteAudiencia());
    			// En la fecha de creacion va la fecha y la hora
    			solicitudAudienciaDTO.setFechaCreacion(new Date());
    			// en la fecha limite va la fecha y la hora hh:mm
    			// 15/6/2011 14:22
    			DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    			String fechaCompleta = forma.getFechaLimiteAudiencia() + " "+ forma.getHoraLimiteAudiencia();
    			try {
    				Date fechaLimite = df1.parse(fechaCompleta);
    				solicitudAudienciaDTO.setFechaLimite(fechaLimite);
    				if (logger.isDebugEnabled()) {
    					logger.debug("fechaLimite = " + fechaLimite);
    				}
    			} catch (ParseException ex) {
    				logger.error("Error de formato (dd/MM/yyyy HH:mm) con la fecha: "+ fechaCompleta);
    			}
    			if (logger.isDebugEnabled()) {
    				logger.debug("forma.getFechaLimiteAudiencia() = "+ forma.getFechaLimiteAudiencia());
    			}
    			solicitudAudienciaDTO.setStrHoraLimite(forma.getHoraLimiteAudiencia());
    			if (logger.isDebugEnabled()) {
    				logger.debug("forma.getHoraLimiteAudiencia() = "+ forma.getHoraLimiteAudiencia());
    			}
    			solicitudAudienciaDTO.setSolicitanteExterno(usuarioFirmado.getFuncionario().getClaveFuncionario());
    			solicitudAudienciaDTO.setUsuarioSolicitante(usuarioFirmado.getFuncionario());
    			if(usuarioFirmado!=null &&
    					usuarioFirmado.getFuncionario()!=null &&
    					usuarioFirmado.getFuncionario().getPuesto()!=null &&
    					usuarioFirmado.getFuncionario().getPuesto().getValor()!=null){
    				solicitudAudienciaDTO.getUsuarioSolicitante().getPuesto().setNombreCampo(usuarioFirmado.getFuncionario().getPuesto().getValor());
    			}
    			if(usuarioFirmado!=null &&
    					usuarioFirmado.getFuncionario()!=null &&
    					usuarioFirmado.getFuncionario().getJerarquiaOrganizacional()!=null &&
    					usuarioFirmado.getFuncionario().getJerarquiaOrganizacional().getNombre()!=null){
    				solicitudAudienciaDTO.getUsuarioSolicitante().getJerarquiaOrganizacional().setNombre(
    						usuarioFirmado.getFuncionario().getJerarquiaOrganizacional().getNombre());
    			}

    			/*Obtener expediente de sesión*/
    			ExpedienteDTO expedienteDTO=new ExpedienteDTO();
    			if(forma.getIdNumeroExpediente() != null){
    				expedienteDTO.setNumeroExpedienteId(Long.parseLong(forma.getIdNumeroExpediente()));
    				expedienteDTO.setExpedienteId(expedienteDelegate.obtenerExpedienteIdPorNumExpId(expedienteDTO));
    			}
    			expedienteDTO.setUsuario(usuarioFirmado);  
    			/**
    			 *Se agrega lo referente al envío de solicitud 
    			 */
    			if (logger.isDebugEnabled()) {
    				logger.debug("**********************************forma.getDistrito() = "
    						+ forma.getDistrito());
    			}
    			if (logger.isDebugEnabled()) {
    				logger.debug("**********************************forma.getTribunal() = "
    						+ forma.getTribunal());
    			}

    			if (logger.isDebugEnabled()) {
    				logger.debug("**********************************forma.getFuncionarioDestinatario() = "
    						+ forma.getFuncionarioDestinatario());
    			}


    			logger.info("enviarSolicitudAudiencia - areaUsuarioFirmado : "+usuarioFirmado.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId());

    			solicitudAudienciaDTO.setIdsDocumentos(forma.getIdsDoctsSelecc());

    			//Se consulta si el xpediente fue replicado antes de enviar la solicitud.
    			expedienteDTO.setInvolucradosSolicitados(true);

    			SolicitudDTO solDTO = actuacionesDelegate.registrarSolicitudAudienciaMich(solicitudAudienciaDTO, expedienteDTO);

    			logger.info("solDTO ID _____--------_____ " + solDTO.getDocumentoId());


    			solicitudMichDTO.setFec_inicio(formatearFecha(forma.getFechaInicio()));
    			solicitudMichDTO.setFec_envio(formatearFecha(forma.getFechaEnvio()));
    			solicitudMichDTO.setMateria(forma.getMateria());
    			solicitudMichDTO.setMp(forma.getCveFuncionario());
    			solicitudMichDTO.setNombre(forma.getNombreDelSolicitante());
    			solicitudMichDTO.setNuc(forma.getNumeroDeCaso());
    			solicitudMichDTO.setPrioridad(situacionImputadoDTO.getPrioridad().toString());
    			solicitudMichDTO.setPromovente(expedientDTO.getArea().getAreaId() == Areas.DEFENSORIA.ordinal()?"2":forma.getPromovente());
    			solicitudMichDTO.setSolicitudId(solDTO.getDocumentoId().toString());
    			solicitudMichDTO.setTipo_despacho(compDTO.getValor());
    			solicitudMichDTO.setTipo_ini(forma.getTipoIni());
//    			solicitudMichDTO.setTipo_ini("2");
    			solicitudMichDTO.setTipo_promocion(caracterDTO.getValor());
    			solicitudMichDTO.setTipo_solicitud("S");
    			solicitudMichDTO.setUnidad_inv(forma.getUnidadInv());

    			solicitudMichDTO.setImputados(imputadosDTO);
    			solicitudMichDTO.setImputadosDelitos(imputadoDelitosDTO);
    			solicitudMichDTO.setOfendidos(ofendidosDTO);
    			solicitudMichDTO.setImpDeOfendidos(impOfendidosDTO);
    			solicitudMichDTO.setDocumentos(documentosWsMichDTO);

    			XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
    			converter = new XStream(new DomDriver("UTF-8", replacer));
    			converter.alias("org:RecibirSolicitudPGJE", SolicitudMichDTO.class);
    			converter.registerConverter(new SolicitudMichConverter());

    			String xml = converter.toXML(solicitudMichDTO);
    			logger.info("Solicitud Mich " + xml);

    			if (mensDetencion != ""){
    				escribirRespuesta(response, mensDetencion);
    			}
    			else {
        			// Enviamos la Solicitud por WS
        			String respuestaWs = actuacionesDelegate.enviarSolAudienciaWs(xml);

        			if (!respuestaWs.equals("") || !respuestaWs.equals(null)){
        				logger.info("Respuesta de Web Service" + respuestaWs);
        				solDTO.setObservaciones(respuestaWs);
        				solicitudDelegate.actualizaObservacionesSolicitud(solDTO);
        				logger.info("Se Actualizo la Solicitud Correctamente ");
        				// mandamos escribir la respuesta
        				escribirRespuesta(response, respuestaWs);
        			}
        			else
        				escribirRespuesta(response, "La solicitud no se ha registrado ya que es nula o vacia");
    			}


    		} catch (NSJPNegocioException ex) {
    			logger.error("Ocurrio un error en enviarSolicitudAudiencia", ex);
    			escribirError(response, ex);
    		}
    	}
    	return null;
    }
	
	public static String formatearFecha(String fec){	
		String nuevaFec="";
		try {
			if(fec.contains("/")){
				nuevaFec = fec.replace("/", "-");
				nuevaFec = nuevaFec.replace(" ", "T");
			}
			else{
				nuevaFec = fec.substring(0, fec.indexOf("."));
				nuevaFec = nuevaFec.replace(" ", "T");
			}
			DateTime dt = new DateTime(nuevaFec);
			DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ssZZ");
			nuevaFec = fmt.print(dt);
		} catch (Exception e) {
			logger.error("Ocurrio un errror al formatear la fecha", e);
		}

		return nuevaFec;
	}
    
    
}
