
package mx.gob.segob.nsjp.web.evento.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.audiencia.Eventos;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.funcionario.TipoEspecialidad;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TipoMandamiento;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.documento.MandamientoJudicialDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.leycodigo.LeyCodigoDelegate;
import mx.gob.segob.nsjp.delegate.ordenaprehension.OrdenDeAprehensionDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.LeyCodigoDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.EventoDTO;
import mx.gob.segob.nsjp.dto.audiencia.FiltroAudienciaDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.CartaCumplimientoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioExtranjeroDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioAudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoAudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoViewDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudTranscripcionAudienciaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.ReporteBaseAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author AlejandroGA
 *
 */
public class ConsultaEventosPJENSAction extends ReporteBaseAction{

	/**Log de clase*/
	private static final Logger log  = Logger.getLogger(ConsultaEventosPJENSAction.class);
	
	@Autowired
	public AudienciaDelegate audienciaDelegate;
	
	@Autowired
	public SolicitudDelegate solicitudDelegate;
	
	@Autowired
	public InvolucradoDelegate involucradoDelegate;
	
	@Autowired
	public LeyCodigoDelegate leyCodigoDelegate;
	
	@Autowired
	public DocumentoDelegate delegateDelegate;
	@Autowired
	public MandamientoJudicialDelegate mandamientoDelegate;
	
	@Autowired
	public ExpedienteDelegate expedienteDelegate;
			
	@Autowired
	public OrdenDeAprehensionDelegate ordenDeAprehensionDelegate;
	
	private final static String KEY_SESSION_EVENTO = "KEY_SESSION_EVENTO_DTO";
	
//	private final static String KEY_SESSION_EXPEDIENTE = "KEY_SESSION_EXPEDIENTE_DTO";
	
	private static final String KEY_REQUEST_ID_AUDIENCIA = "audienciaId";
	private static final String KEY_REQUEST_ID_ORGANIZACION = "organizacionId";
	private static final String KEY_REQUEST_ID_INVOLUCRADO = "involucradoId";
			
	
	/**
	 * Metodo utilizado para las Solicitud de defensores.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarSolicitudesDefensor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR SOLICITUDES DE DEFENSOR)");
			
			Long audienciaId = Long.parseLong(request.getParameter("audienciaId"));
						
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");			
			PrintWriter writer = response.getWriter();
			
			List<SolicitudDefensorDTO> solicitudDefensoresDTO=new ArrayList<SolicitudDefensorDTO>();
			solicitudDefensoresDTO = solicitudDelegate.obtenerSolicitudDefensorPorAudienciaID(audienciaId); 
						
			writer.print("<rows>");
			final PaginacionDTO pag = PaginacionThreadHolder.get();
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }
            
            if(!solicitudDefensoresDTO.isEmpty())
				for (SolicitudDefensorDTO solicitudDefensorDTO : solicitudDefensoresDTO) {
					log.info("FOR EACH DE solicitudDefensorDTO VIEW DTO" + solicitudDefensorDTO);
					writer.print("<row id='"+ solicitudDefensorDTO.getDocumentoId() + "'>");
					writer.print("<cell>"+solicitudDefensorDTO.getDetenido()+ "</cell>");
					writer.print("<cell>"+ solicitudDefensorDTO.getFolioSolicitud()+ "</cell>");
					writer.print("<cell>"+ solicitudDefensorDTO.getFechaCreacion() + "</cell>");
					writer.print("</row>");
				}
            writer.print("</rows>");
			writer.flush();
			writer.close();				

		} catch (Exception e) {
			log.error(e.getCause(), e);

		}
 	return null;
	}
	
	/**
	 * Metodo utilizado para la consulta de audiencias del dia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarAudienciaDelDiaPJENS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR EVENTOS DEI DIA (AUDIENCIAS y id Expediente)");

			FiltroAudienciaDTO filtro = new FiltroAudienciaDTO();
			Date fechaHoy = new Date();
			fechaHoy = DateUtils.obtener(DateUtils.formatear(fechaHoy));
			filtro.setFechaInicial(fechaHoy);
			filtro.setFechaFinal(fechaHoy);
			UsuarioDTO usuario = getUsuarioFirmado(request);
			filtro.setUsuario(usuario);
			List<AudienciaDTO> listaDeAudiencias = audienciaDelegate.buscarAudiencias(filtro);
			
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }
            
			for (AudienciaDTO audienciaDTO : listaDeAudiencias) {
			    writer.print("<row id='"+ audienciaDTO.getId() +"'>");
				writer.print("<cell>"+(((audienciaDTO.getExpediente()!= null)&&( audienciaDTO.getExpediente().getCasoDTO())!= null)?audienciaDTO.getExpediente().getCasoDTO().getNumeroGeneralCaso():"")+"</cell>");
				writer.print("<cell>"+ (audienciaDTO.getExpediente()!=null ? audienciaDTO.getExpediente().getNumeroExpediente():"-") +  "</cell>");
				writer.print("<cell>"+ audienciaDTO.getCaracter() + "</cell>");
				writer.print("<cell>"+ audienciaDTO.getTipoAudiencia().getValor()+ "</cell>");
				String fechaSolicitud=DateUtils.formatear(audienciaDTO.getFechaEvento());
				writer.print("<cell>"+ fechaSolicitud + "</cell>");
				String horaSolicitud=DateUtils.formatearHora(audienciaDTO.getFechaEvento());
				writer.print("<cell>"+ horaSolicitud + "</cell>");
				writer.print("<cell>"+ (audienciaDTO.getSala()!= null && audienciaDTO.getSala().getNombreSala() != null ? 
						audienciaDTO.getSala().getNombreSala(): "-")+ "</cell>");
				writer.print("<cell>"+ (audienciaDTO.getEstatusAudiencia() != null && audienciaDTO.getEstatusAudiencia().getValor() != null ? audienciaDTO.getEstatusAudiencia().getValor(): "-" )+ "</cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.error(e.getCause(), e);

		}
		return null;
	}
	
	/**
	 * Metodo utilizado para realizar el paso del parametro id del evento
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward visorAudienciaPJENS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action visorAudienciaPJENS");
			
			String idEvento = request.getParameter("idEvento");
			String numExpediente = request.getParameter("numExpediente");
	
			log.info("ID-DEL EVENTO:::"+ idEvento);
			log.info("NUMERO EXPEDIENTE:::"+ numExpediente);
			
			ExpedienteDTO expedienteDto = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numExpediente);
            
			request.getSession().setAttribute("idEvento",idEvento);
			super.setExpedienteTrabajo(request,expedienteDto);
			
		} catch (Exception e) {		
			log.error(e.getCause(),e);
			
		}
		return mapping.findForward("succes");
	}
	
	
	/**
	 * Metodo utilizado para realizar del detalle de eventos
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward detalleAudienciasDelDiaPJENS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO CONSULTA DETALLE AUDIENCIAS DEL DIA");
			
			//Se obtiene el id del evento a consultar a detalle
			String idEvento = request.getParameter("idEvento");
			//Se obtiene el tipo de respuesta que se desea, 
			//String tipoDeRespuesta = request.getParameter("tipoDeRespuesta");
			
			log.info("_____________________________________________________________");
			//log.info("tipo respuesta::::::::::"+tipoDeRespuesta);
			log.info("id del envento::::::::::"+ idEvento);
			log.info("_____________________________________________________________");
			
			AudienciaDTO audienciaDTO = new AudienciaDTO(); 
			audienciaDTO.setId(Long.parseLong(idEvento));
			audienciaDTO.setTipoEvento(Eventos.AUDENCIA);			
				
			log.info("antes del delegate:::::");
			audienciaDTO = audienciaDelegate.obtenerAudiencia(audienciaDTO);
			//audienciaDTO.getSolicitud().setA;
			setExpedienteTrabajo(request, audienciaDTO.getExpediente());
			log.info("Seteando expediente de trabajo ::::: "+audienciaDTO.getExpediente().getExpedienteId());
			log.info("depues del delegate::: eventoDTO"+ audienciaDTO);
			converter.alias("audienciaDTO", AudienciaDTO.class);
			String xml = converter.toXML(audienciaDTO);
			escribir(response, xml,null);
				
			
		} catch (Exception e) {		
			log.error("ERROR AL CONSULTAR EL EVENTO ---- consultaDetalleNotificaciones");
			log.error(e.getCause(),e);
			escribir(response, "consultaDetalleNotificaciones",null);
			
		}
		return null;
	}
	/**
	 * Metodo utilizado para la consulta de los imputados de una audiencia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarImputadoAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR IMPUTADO AUDIENCIA)");
			
			Long idEvento = Long.parseLong(request.getParameter("idEvento"));
			
			Long tipo = Long.parseLong(request.getParameter("tipo"));
			
			log.info("LLEGA ID EVENTO Y TIPO---- " + idEvento + tipo);
			
			

			AudienciaDTO audienciaDTO = new AudienciaDTO();		
			audienciaDTO.setId(idEvento);	
			
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");			
			PrintWriter writer = response.getWriter();
			
			List<InvolucradoViewDTO> listaInvolucrados = involucradoDelegate.obtenerImputadosAudiencia(audienciaDTO); 
						
			writer.print("<rows>");
			final PaginacionDTO pag = PaginacionThreadHolder.get();
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }
			/*Enable IT : agregando L para validacion*/
			if(tipo.equals(1L)){
			log.info("LISTA DE IMPUTADOS CON DELITOS" + listaInvolucrados);
				for (InvolucradoViewDTO involucradoViewDTO : listaInvolucrados) {
					log.info("FOR EACH DE INVOLUCRADO VIEW DTO" + involucradoViewDTO);
					writer.print("<row id='"+ involucradoViewDTO.getInvolucradoId() + "'>");
					writer.print("<cell>"+involucradoViewDTO.getNombreCompleto()+ "</cell>");
					writer.print("<cell>"+ involucradoViewDTO.getCalidad() + "</cell>");
					//writer.print("<cell>"+ involucradoViewDTO.getDelitos() + "</cell>");
					List<DelitoDTO> listaDelitos = involucradoViewDTO.getDelitosCometidos();
					
					if(listaDelitos != null){
						writer.print("<cell><![CDATA[" +  
						"<select id='cbxDelitos' style='width: 150px;'>");
							for (DelitoDTO delito: listaDelitos) {
								writer.print("<option>"+delito.getNombreDelito()+"</option>");			    										    								    								    			
							}				    			
						writer.print("</select>"+ "]]></cell>");					
					}else{
						writer.print("<cell>"+ " " + "</cell>");
					}
					writer.print("<cell>"+ involucradoViewDTO.getDetenido() + "</cell>");
					writer.print("</row>");
				}			
				request.getSession().setAttribute(KEY_SESSION_EVENTO + idEvento, audienciaDTO);
			}			
			else{				
				/**
				 * En esta parte de la respuesta se desea enviar la informacion del grid,
				 * correspondiente a la TAB notificaciones de la pantalla 
				 * atencionSolicitudAudienciaNotificador.jsp
				 */
				audienciaDTO = (AudienciaDTO) request.getSession().getAttribute(KEY_SESSION_EVENTO+idEvento);
				for (InvolucradoViewDTO involucradoViewDTO : listaInvolucrados) {
					writer.print("<row id='"+ involucradoViewDTO.getInvolucradoId() + "'>");
					writer.print("<cell>"+ involucradoViewDTO.getNombre() + "</cell>");
					writer.print("<cell>"+ involucradoViewDTO.getApellidoPaterno() + "</cell>");
					writer.print("<cell>"+ involucradoViewDTO.getApellidoMaterno() + "</cell>");
					writer.print("</row>");
				}
			}
			
			writer.print("</rows>");
			writer.flush();
			writer.close();				

		} catch (Exception e) {
			log.error(e.getCause(), e);

		}
 	return null;
	}
	
	
	/**
	 * Metodo utilizado para la consulta de los imputados de una audiencia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarImputadoMaximaAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
			try {
				
				log.info("EJECUTANDO ACTION ---- CONSULTAR IMPUTADO  MAXIMA AUDIENCIA)");
	
				Long idEvento = NumberUtils.toLong(
						request.getParameter("idEvento"), 0L);
				log.info("LLEGA ID EVENTO---- " + idEvento);
	
				AudienciaDTO audienciaDTO = new AudienciaDTO();
				audienciaDTO.setId(idEvento);
	
				List<InvolucradoViewDTO> listaInvolucrados = involucradoDelegate
						.obtenerImputadosSiguienteAudiencia(audienciaDTO);
	
				log.info("LISTA DE IMPUTADOS CON DELITOS" + listaInvolucrados);
	
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
	
				writer.print("<rows>");
	
				int lTotalRegistros = listaInvolucrados.size();
	
				writer.print("<records>" + lTotalRegistros + "</records>");
				for (InvolucradoViewDTO involucradoViewDTO : listaInvolucrados) {
	
					log.info("FOR EACH DE INVOLUCRADO VIEW DTO"
							+ involucradoViewDTO);
	
					writer.print("<row id='"
							+ involucradoViewDTO.getInvolucradoId() + "'>");
					writer.print("<cell>" + involucradoViewDTO.getNombre() + " "
							+ involucradoViewDTO.getApellidoPaterno() + " "
							+ involucradoViewDTO.getApellidoMaterno() + "</cell>");
					// writer.print("<cell>"+ involucradoViewDTO.getCalidad() +
					// "</cell>");
					// writer.print("<cell>"+ involucradoViewDTO.getDelitos() +
					// "</cell>");
					// writer.print("<cell>"+ involucradoViewDTO.getDetenido() +
					// "</cell>");
					writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
	
			} catch (Exception e) {
				log.error(e.getCause(), e);
	
			}
	 	return null;
	}
	
	
	/**
	 * Metodo utilizado para la consulta de los involucrados de la audiencia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarInvolucradosAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR INVOLUCRADOS DE AUDIENCIA)");
			
			Long idEvento = Long.parseLong(request.getParameter("idEvento"));
			
			log.info("LLEGA ID EVENTO ---- " + idEvento);

			AudienciaDTO audienciaDTO = new AudienciaDTO();		
			audienciaDTO.setId(idEvento);		
			
			List<InvolucradoViewDTO> listaInvolucrados = involucradoDelegate.obtenerInvolucradosAudiencia(audienciaDTO);
			
			log.info("LISTA DE INVOLUCRADOS CON DELITOS" + listaInvolucrados);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			int lTotalRegistros = listaInvolucrados.size();

			writer.print("<records>" + lTotalRegistros + "</records>");
			for (InvolucradoViewDTO involucradoViewDTO : listaInvolucrados) {

				log.info("FOR EACH DE INVOLUCRADO VIEW DTO" + involucradoViewDTO);

				writer.print("<row id='"+ involucradoViewDTO.getInvolucradoId() + "'>");
				writer.print("<cell>"+ involucradoViewDTO.getNombre() +" "+ involucradoViewDTO.getApellidoPaterno()+" "+ involucradoViewDTO.getApellidoMaterno()+ "</cell>");
				writer.print("<cell>"+ (involucradoViewDTO.getNombreInstitucion() != null ? involucradoViewDTO.getNombreInstitucion() :"")+ "</cell>");
				writer.print("<cell>"+ involucradoViewDTO.getCalidad()+ "</cell>");
				writer.print("<cell>"+ involucradoViewDTO.isFuncionario()+ "</cell>");
				
				
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.error(e.getCause(), e);

		}
 	return null;
	}
	
	/**
	 * Metodo utilizado para la consulta de los involucrados de la audiencia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward enviarSolicitudDefensor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- REGISTRAR SOLICITUD DE DEFENSOR)");			
			Long idAudiencia = Long.parseLong(request.getParameter("idAudiencia"));				
			
			log.info("LLEGA ID AUDIENCIA ---- " + idAudiencia);
			AudienciaDTO audienciaDTO = new AudienciaDTO();		
			audienciaDTO.setId(idAudiencia);

			//Long idImputado = Long.parseLong(request.getParameter("idImputado"));
			
			String  idsImputados = request.getParameter("idImputado");
			log.info("LLEGA IDS DE IMPUTADOS---- " + idsImputados);
			
			if(idsImputados != null && !idsImputados.isEmpty()){
				String listaIds[] = idsImputados.split(",");
				Long idImputado = 0L;
				for(String id : listaIds){
					idImputado = (NumberUtils.toLong(id, 0L));
					if(idImputado > 0L){
						solicitudDelegate.registrarSolicitudDefensorEncargadoSala(audienciaDTO, idImputado);
						escribirRespuesta(response, converter.toXML(1));		
					}
				}
			}			
		} catch (Exception e) {
			log.error(e.getCause(), e);

		}
 	return null;
	}
	
	
	/**
	 * Metodo utilizado para consultar los objetos en encargado de sala
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarObjetosPJENS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action consultarObjetosPJENS");
			
			Long idEvento =Long.parseLong(request.getParameter("idEvento"));
			
			log.info("id evento:::"+ idEvento);
			
			EventoDTO eventoDTO = new EventoDTO();
			eventoDTO.setId(idEvento);			
			
			List<EvidenciaDTO> objetoDTOs = audienciaDelegate.consultarObjetosAudiencia(eventoDTO);
			log.info("Lista de Objetos:::"+ objetoDTOs);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			int lTotalRegistros = objetoDTOs.size();

			writer.print("<records>" + lTotalRegistros + "</records>");
			for (EvidenciaDTO evidenciaTDO : objetoDTOs) {

				log.info("FOR EACH DE OBJETOS" + evidenciaTDO);

				writer.print("<row id='"+ evidenciaTDO.getEvidenciaId() + "'>");
				writer.print("<cell>"+ evidenciaTDO.getObjeto().getInstitucionPresenta().getNombreInst()+ "</cell>");
				writer.print("<cell>"+ evidenciaTDO.getNumeroEvidencia() + "</cell>");
				writer.print("<cell>"+ evidenciaTDO.getObjeto().getCadenaDeCustodia().getFolio()+ "</cell>");
				writer.print("<cell>"+ evidenciaTDO.getObjeto().getDescripcion()+ "</cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (Exception e) {		
			log.error(e.getCause(),e);
			
		}
		return mapping.findForward("succes");
	}	

	/**
	 * M&eacute;todo para obtener los datos para rellenar el grid de los resolutivos
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward consultarResolutivosAudiencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, NSJPNegocioException {
		
		log.debug("ENTRA A CONSULTAR RESOLUTIVOS DE AUDIENCIA");
		 
		String audienciaId = request.getParameter("idEvento");
		
		log.debug("Audiencia id" + audienciaId);
		
		List<ResolutivoViewDTO> resolutivos;
		
		resolutivos = audienciaDelegate.leerResolutivosAudiencia(Long.parseLong(audienciaId));
		 
		log.debug("resolutivos" + resolutivos);
		
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();
		
		writer.print("<rows>");
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		log.debug("pag :: " + pag);
        if (pag != null && pag.getTotalRegistros() != null) {
            writer.print("<total>" + pag.getTotalPaginas() + "</total>");
            writer.print("<records>" + pag.getTotalRegistros() + "</records>");
        } else {
            writer.print("<total>0</total>");
            writer.print("<records>0</records>");
        }        

		for (ResolutivoViewDTO resolutivoViewDTO : resolutivos) {
			
			writer.print("<row id='" + resolutivoViewDTO.getResolutivoId()+ "'>");
			writer.print("<cell>" + resolutivoViewDTO.getTemporizador()+ "</cell>");
			writer.print("<cell>" + resolutivoViewDTO.getDetalle()+ "</cell>");			
			writer.print("</row>");
		}			
		
		writer.print("</rows>");
		writer.flush();
		writer.close();
		
		
		return null;
	}
	
	/**
	 * Metodo utilizado para consultar los objetos en encargado de sala
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward registrarResolutivosPJENS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action registrar resolutivos de audienciaPJENS");
			
			Long idEvento =Long.parseLong(request.getParameter("idEvento"));			
			log.info("id evento:::"+ idEvento);
			
			String detalle =request.getParameter("resolutivo");			
			log.info("detalle del resolutivo"+ detalle);
			
			String temporizador=request.getParameter("tempVideo");			
			log.info("temporizador"+ temporizador);
			
			String anioMesDia = DateUtils.formatear(new Date());
			
			Date fechaTemporizador = DateUtils.obtener(anioMesDia, temporizador);
			
			AudienciaDTO audienciaDTO = new AudienciaDTO();
			audienciaDTO.setId(idEvento);
			
			ResolutivoDTO resolutivo = new ResolutivoDTO();
			resolutivo.setDetalle(detalle);
			resolutivo.setTemporizador(fechaTemporizador);
			resolutivo.setAudiencia(audienciaDTO);
			
			Long idResolutivo = audienciaDelegate.registrarResolutivoAudiencia(resolutivo);
			log.info("llega id del resolutivo" + idResolutivo);
			
			escribirRespuesta(response, converter.toXML(idResolutivo));
			
		} catch (Exception e) {		
			log.error(e.getCause(),e);
			
		}
		return null;
	}	
	
	/**
	 * Metodo utilizado para consultar los objetos en encargado de sala
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward modificarResolutivosPJENS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action registrar resolutivos de audienciaPJENS");
			
			Long idResolutivo =Long.parseLong(request.getParameter("idResolutivo"));					
			
			String detalle =request.getParameter("resolutivo");			
			log.info("detalle del resolutivo"+ detalle);
			
			String temporizador=request.getParameter("tempVideo");			
			log.info("temporizador"+ temporizador);
			
			DateFormat formato = new SimpleDateFormat("hh:mm:ss");
			Date fechaTemporizador = null;
					
			try {
				fechaTemporizador = formato.parse(temporizador);
			} catch (ParseException e) {
				fechaTemporizador = Calendar.getInstance().getTime();
				e.printStackTrace();
			}

			ResolutivoDTO resolutivo = new ResolutivoDTO();
			resolutivo.setResolutivoId(idResolutivo);
			resolutivo.setDetalle(detalle);
			resolutivo.setTemporizador(fechaTemporizador);
			
			audienciaDelegate.modificarResolutivoAudiencia(resolutivo);
			
			escribirRespuesta(response, converter.toXML(idResolutivo));
			
		} catch (Exception e) {		
			log.error(e.getCause(),e);
		}
		return null;
	}	
	
	/**
	 * Metodo utilizado para consultar los objetos en encargado de sala
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward eliminarResolutivosPJENS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action registrar resolutivos de audienciaPJENS");
			
			Long idResolutivo =Long.parseLong(request.getParameter("idResolutivo"));			
			log.info("id evento:::"+ idResolutivo);
			
			ResolutivoDTO resolutivo = new ResolutivoDTO();
			resolutivo.setResolutivoId(idResolutivo);
			
			audienciaDelegate.eliminarResolutivoAudiencia(resolutivo);
			
			escribirRespuesta(response, converter.toXML(idResolutivo));
			
		} catch (Exception e) {		
			log.error(e.getCause(),e);
			
		}
		return null;
	}	
	
	/**
	 * Metodo utilizado para consultar el detalle de involucrados
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarDetalleInvolucradoPJENS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO CONSULTA DETALLE DE INVOLUCRADO");
			
			InvolucradoDTO involucradoDTO = new InvolucradoDTO();
			
			//Se obtiene el id del evento a consultar a detalle
			String idInvolucrado = request.getParameter("idInvolucrado");			
			log.info("id del involucrado::::::::::"+ idInvolucrado);
			
			involucradoDTO.setElementoId(Long.parseLong(idInvolucrado));
			
			involucradoDTO = involucradoDelegate.obtenerInvolucrado(involucradoDTO);						
			log.info("depues del delegate::: involucradoDTO"+ involucradoDTO);
			
			request.getSession().setAttribute("involucradoDTO", involucradoDTO);
			
			converter.alias("involucradoDTO", InvolucradoDTO.class);
			String xml = converter.toXML(involucradoDTO);
			escribir(response, xml,null);
							
		} catch (Exception e) {		
			log.error("ERROR AL CONSULTAR EL DETALLE DEL INVOLUCRADO");
			log.error(e.getCause(),e);
			escribir(response, "consultaDetalleNotificaciones",null);
			
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para realizar del detalle de eventos
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward modificarDatosInvolucrado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO MODIFICAR DATOS GENERALES DEL INVOLUCRADO");	
			
			
					DomicilioDTO domicilioDTO = new DomicilioDTO();
					InvolucradoDTO involucradoDTO = new InvolucradoDTO();
					NombreDemograficoDTO nombreDemograficoDTO = new NombreDemograficoDTO();
					CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();	
					ConfInstitucionDTO institucionPresenta  = new ConfInstitucionDTO();
					List<NombreDemograficoDTO> listnomDem = new ArrayList<NombreDemograficoDTO>();
					List<CorreoElectronicoDTO> listcorreo = new ArrayList<CorreoElectronicoDTO>();
					
					involucradoDTO = (InvolucradoDTO) request.getSession().getAttribute("involucradoDTO");
					
					//datos simples
					
					Long domicilioId = Long.parseLong(request.getParameter("domicilioId")) ;
					Long idInvolucrado = Long.parseLong(request.getParameter("idInvolucrado")) ;
					Long institucion =  Long.parseLong(request.getParameter("institucion")) ;
					String correo = request.getParameter("correo");
					String nombre = request.getParameter("nombre");
					String apaterno = request.getParameter("aPaterno");
					String amaterno = request.getParameter("aMaterno");
														
					log.info("id Involucrado"+ domicilioId);
					log.info("id Domicilio"+ idInvolucrado);
					log.info("INSTITUCION"+ institucion);
					log.info("NOMBRE"+ nombre);
					log.info("CORREO"+ correo);					
														
					//Seteamos el nombre, apPat y apMat
					
					nombreDemograficoDTO.setNombre(nombre);
					nombreDemograficoDTO.setApellidoMaterno(amaterno);
					nombreDemograficoDTO.setApellidoPaterno(apaterno);
					listnomDem.add(nombreDemograficoDTO);
					correoElectronicoDTO.setDireccionElectronica(correo);
					listcorreo.add(correoElectronicoDTO);
					institucionPresenta.setConfInstitucionId(institucion);
					domicilioDTO.setElementoId(domicilioId);
					
					involucradoDTO.setElementoId(idInvolucrado);
					involucradoDTO.setInstitucionPresenta(institucionPresenta);
					involucradoDTO.setNombresDemograficoDTO(listnomDem);
					involucradoDTO.setCorreosDTO(listcorreo);
					
//					involucradoDTO.getNombresDemograficoDTO().get(0).setNombre(nombre);
//					involucradoDTO.getNombresDemograficoDTO().get(0).setApellidoPaterno(apaterno);
//					involucradoDTO.getNombresDemograficoDTO().get(0).setApellidoMaterno(amaterno);
//					involucradoDTO.getCorreosDTO().get(0).setDireccionElectronica(correo);
//					involucradoDTO.getInstitucionPresenta().setConfInstitucionId(institucion);
					log.info("calidad"+ involucradoDTO.getCalidadDTO());	
					CalidadDTO calDTO = new CalidadDTO();
					calDTO.setCalidades(Calidades.TESTIGO);
					involucradoDTO.setCalidadDTO(calDTO);
					//domicilio
					String pais = request.getParameter("pais");
					String codigoPostal = request.getParameter("codigoPostal");
					String entidadFederativa = request.getParameter("entidadFederativa");
					String ciudad = request.getParameter("ciudad");
					String delegacionMunicipio = request.getParameter("delegacionMunicipio");
					String asentamientoColonia = request.getParameter("asentamientoColonia");
					String tipoAsentamiento = request.getParameter("tipoAsentamiento");
					String tipoCalle = request.getParameter("tipoCalle");
					String calle = request.getParameter("calle");
					String numExterior = request.getParameter("numExterior");
					String numInterior = request.getParameter("numInterior");
					String referencias = request.getParameter("referencias");
					String entreCalle = request.getParameter("entreCalle");
					String ycalle = request.getParameter("ycalle");
					String aliasDomicilio = request.getParameter("aliasDomicilio");
					String edificio = request.getParameter("edificio");
					String longitud = request.getParameter("longitud");
					String latitud = request.getParameter("latitud");
					
					log.info("PAIS="+pais);
					log.info("CP="+codigoPostal);
					log.info("ENTIDAD FEDERATIVA="+entidadFederativa);
					log.info("CIUDAD="+ciudad);
					log.info("DELEGACION-MUNICIPIO="+delegacionMunicipio);
					log.info("ASENTAMIENTO COLONIA="+asentamientoColonia);
					log.info("TIPO ASENTAMIENTO="+tipoAsentamiento);
					log.info("TIPO CALLE="+tipoCalle);
					log.info("CALLE="+calle);
					log.info("NUMERO EXTERIOR="+numExterior);
					log.info("NUMERO INTERIOR="+numInterior);
					log.info("REFERENCIAS="+referencias);
					log.info("ENTRE CALLE="+entreCalle);
					log.info("Y CALLE="+ycalle);
					log.info("ALIAS DOMICILIO="+aliasDomicilio);
					log.info("EDIFICIO="+edificio);
					log.info("LONGITUD="+longitud);
					log.info("LATITUD="+latitud);						
							
				//CUANDO EL PAIS ES MEXICO
				if((Long.parseLong(pais)==10 || pais.equalsIgnoreCase("mexico") || pais.equalsIgnoreCase("méxico")) && (Long.parseLong(pais)!= -1L)){
									
					//parte izquierda de la pantalla ingresar domicilio				
						//entidad federativa
					if(!entidadFederativa.equalsIgnoreCase("")){
						
						if(Long.parseLong(entidadFederativa)!= -1L ){
							EntidadFederativaDTO entidadDTO = new EntidadFederativaDTO();
							entidadDTO.setEntidadFederativaId(Long.parseLong(entidadFederativa));
							domicilioDTO.setEntidadDTO(entidadDTO);
						}
					}
					
						//ciudad
					if(!ciudad.equalsIgnoreCase("")){
						
						if(Long.parseLong(ciudad)!= -1L ){
							CiudadDTO ciudadDTO = new CiudadDTO();
							ciudadDTO.setCiudadId(Long.parseLong(ciudad));
							domicilioDTO.setCiudadDTO(ciudadDTO);
						}
					}
						//delegacion-municipio
					if(!delegacionMunicipio.equalsIgnoreCase("")){
						
						if(Long.parseLong(delegacionMunicipio)!= -1L ){
							MunicipioDTO municipioDTO = new MunicipioDTO();
							municipioDTO.setMunicipioId(Long.parseLong(delegacionMunicipio));
							domicilioDTO.setMunicipioDTO(municipioDTO);
						}
					}
						
						//asentamiento-colonia
					if(!asentamientoColonia.equalsIgnoreCase("")){
						
						if(Long.parseLong(asentamientoColonia)!= -1L ){
							AsentamientoDTO asentamientoDTO = new AsentamientoDTO();
							asentamientoDTO.setAsentamientoId(Long.parseLong(asentamientoColonia));
							domicilioDTO.setAsentamientoDTO(asentamientoDTO);		
						}
					}
						
						//tipo de calle
					if(!tipoCalle.equalsIgnoreCase("")){
						
						if(Long.parseLong(tipoCalle) != -1){
							
							ValorDTO valorCalleId = new ValorDTO(Long.parseLong(tipoCalle));
							domicilioDTO.setValorCalleId(valorCalleId);
						}
					}					
					
					//parte derecha de la pantalla ingresar domicilio
					domicilioDTO.setCalle(calle);
					domicilioDTO.setNumeroExterior(numExterior);
					domicilioDTO.setNumeroInterior(numInterior);
					domicilioDTO.setEntreCalle1(entreCalle);
					domicilioDTO.setEntreCalle2(ycalle);
					domicilioDTO.setAlias(aliasDomicilio);
					domicilioDTO.setEdificio(edificio);
					domicilioDTO.setReferencias(referencias);
					
					if(!longitud.equalsIgnoreCase("")){
						domicilioDTO.setLongitud(longitud);
					}
					else{
						domicilioDTO.setLongitud(null);
					}
					if(latitud != null && !latitud.isEmpty()){
						domicilioDTO.setLatitud(latitud);
					}
					else{
						domicilioDTO.setLatitud(null);
					}
									
					//Seteamos la calidad del domicilio
					CalidadDTO calidadDomicilioDTO = new CalidadDTO();
					calidadDomicilioDTO.setCalidades(Calidades.DOMICILIO);
					domicilioDTO.setCalidadDTO(calidadDomicilioDTO);				
					domicilioDTO.setFechaCreacionElemento(new Date());

					//Seteamos el testigo con su domicilio
					involucradoDTO.setDomicilio(domicilioDTO);				
		
				}
				//CUANDO EL PAIS NO ES MEXICO
				else{
					
					DomicilioExtranjeroDTO domicilioExtranjeroDTO = new DomicilioExtranjeroDTO();
					
					//Parte izq de la pantalla ingresar domicilio
					if(!pais.equalsIgnoreCase("")){
						if(Long.parseLong(pais)!= -1L){
						
								//id del pais
							domicilioExtranjeroDTO.setPais(pais);
						}
					}
					
					domicilioExtranjeroDTO.setCodigoPostal(codigoPostal);
					domicilioExtranjeroDTO.setEstado(entidadFederativa);
					domicilioExtranjeroDTO.setCiudad(ciudad);
					domicilioExtranjeroDTO.setMunicipio(delegacionMunicipio);
					domicilioExtranjeroDTO.setAsentamientoExt(asentamientoColonia);
					
					//parte derecha de la pantalla ingresar domicilio
					domicilioExtranjeroDTO.setCalle(calle);
					domicilioExtranjeroDTO.setNumeroExterior(numExterior);
					domicilioExtranjeroDTO.setNumeroInterior(numInterior);
					domicilioExtranjeroDTO.setEntreCalle1(entreCalle);
					domicilioExtranjeroDTO.setEntreCalle2(ycalle);
					domicilioExtranjeroDTO.setAlias(aliasDomicilio);
					domicilioExtranjeroDTO.setEdificio(edificio);
					domicilioExtranjeroDTO.setReferencias(referencias);
					
					if(!longitud.equalsIgnoreCase("")){
						domicilioExtranjeroDTO.setLongitud(longitud);
					}
					else{
						domicilioExtranjeroDTO.setLongitud(null);
					}
					if(!latitud.equalsIgnoreCase("")){
						domicilioExtranjeroDTO.setLatitud(latitud);
					}
					else{
						domicilioExtranjeroDTO.setLatitud(null);
					}
									
					//Seteamos la calidad del domicilio
					
					CalidadDTO calidadDomicilioExtranjeroDTO = new CalidadDTO();
					calidadDomicilioExtranjeroDTO.setCalidades(Calidades.DOMICILIO);
					domicilioExtranjeroDTO.setCalidadDTO(calidadDomicilioExtranjeroDTO);
					domicilioExtranjeroDTO.setFechaCreacionElemento(new Date());
					domicilioExtranjeroDTO.setElementoId(domicilioId);
									
					//Seteamos el domicilio extranjero de notificaciones a la persona
					involucradoDTO.setDomicilio(domicilioExtranjeroDTO);
			}									
					involucradoDelegate.actualizarIndividuo(involucradoDTO);
					
					
													
		} catch (Exception e) {		
			log.error("ERROR AL MODIFICAR EL INVOLUCRADO");
			log.error(e.getCause(),e);
			escribir(response, "error al midificar involucrado",null);
			
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para consultar las leyes
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarLeyesCodigos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO CONSULTA DE LEYES");
			
			//Se obtiene el tipo de ley
			String tipoLey = request.getParameter("tipoLey");	
			
			log.info("Tipo de Ley::::::::::"+ tipoLey);
			
			List<LeyCodigoDTO> liCodigoDTOs = new ArrayList<LeyCodigoDTO>();
			
			if (tipoLey != null){
				
				liCodigoDTOs = leyCodigoDelegate.obtenerNormasCategoria(Long.parseLong(tipoLey));
					}
			
			log.info("LISTA DE CODIGOS DESPUES DEL DELEGATE"+ liCodigoDTOs);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			
            final PaginacionDTO pag = PaginacionThreadHolder.get();
		    log.debug("pag :: " + pag);
		    if (pag != null && pag.getTotalRegistros() != null) {
		        writer.print("<total>" + pag.getTotalPaginas() + "</total>");
		        writer.print("<records>" + pag.getTotalRegistros() + "</records>");
		    } else {
		        writer.print("<total>0</total>");
		        writer.print("<records>0</records>");
		    }
		    //int lTotalRegistros = liCodigoDTOs.size();
		    //writer.print("<records>" + lTotalRegistros + "</records>");
			for (LeyCodigoDTO codigoDTO : liCodigoDTOs) {

				log.info("FOR EACH DE OBJETOS" + codigoDTO);

				writer.print("<row id='"+ codigoDTO.getLeyCodigoId()+ "'>");
//				writer.print("<cell>"+ codigoDTO.getNombre() + "</cell>");
//				writer.print("<cell>"+ codigoDTO.getUrl() + "</cell>");
				writer.print("<cell><![CDATA[<a href='"+ codigoDTO.getUrl() + "' target='_blank'>"+codigoDTO.getNombre()+"</a>]]></cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
						
		} catch (Exception e) {		
			log.error("ERROR AL CONSULTAR LAS LEYES");
			log.error(e.getCause(),e);
			escribir(response, "ERROR AL CONSULTAR LAS LEYES",null);
			
		}
		return null;
	}
	/**
	 * Metodo utilizado para consultar las leyes
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward abrirPDF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO ABRIR PDF");
			
			//Se obtiene el idley
			String idLey = request.getParameter("idLey");	
			String nombreArchivo = request.getParameter("nombreArchivo");
			
			log.info("id Ley::::::::::"+ idLey);
			log.info("nombreArchivo::::::::::"+ nombreArchivo);
			
			ByteArrayOutputStream archivo =leyCodigoDelegate.leerLeyCodigo(Long.parseLong(idLey));	
			
			escribirReporte(response, archivo, nombreArchivo);
			
						
		} catch (Exception e) {		
			log.error("EJECUTANDO ABRIR PDF");
			log.error(e.getCause(),e);
						
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para registrar la solicitud de audio video y transcripcion
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward registrarSolicitudPJENS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO REGISTRAR SOLICITUD PJENS");
			
			Long tipoSolicitud = Long.parseLong(request.getParameter("tipoSolicitud"));
			Long idAudiencia = Long.parseLong(request.getParameter("idEvento"));
			log.info("tipo solicitud::::::::::"+ tipoSolicitud);
			SolicitudTranscripcionAudienciaDTO solicitud = new SolicitudTranscripcionAudienciaDTO();
			
			AudienciaDTO audiencia = new AudienciaDTO();
			audiencia.setId(idAudiencia);
			audiencia = audienciaDelegate.obtenerAudiencia(audiencia);
			ConfInstitucionDTO confInstitucionDTO = new ConfInstitucionDTO();
			confInstitucionDTO.setConfInstitucionId(Instituciones.PJ.getValorId());	
			UsuarioDTO usuario = super.getUsuarioFirmado(request);
			solicitud.setTipoSolicitudDTO(new ValorDTO(tipoSolicitud));
			solicitud.setExpedienteDTO(audiencia.getExpediente());	
			solicitud.setFechaCreacion(Calendar.getInstance().getTime());
			solicitud.setInstitucion(confInstitucionDTO);
			solicitud.setNombreSolicitante(usuario.getFuncionario().getNombreCompleto());
			solicitud.setAudiencia(audiencia);
			solicitud = solicitudDelegate.registrarSolicitudTranscripcionAudiencia(solicitud);
						
		} catch (Exception e) {		
			log.error("EJECUTANDO ABRIR PDF");
			log.error(e.getCause(),e);
						
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para consultar finalizar la audiencia en encargado de sala
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward finalizarAudienciaPJENS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String mensaje = "";
		try {
			log.info("ejecutando action finalizarAudienciaPJENS idAudiencia ::"+ request.getParameter("idAudiencia"));
			Long idAudiencia = Long.parseLong(request.getParameter("idAudiencia"));			
			AudienciaDTO audiencia = new AudienciaDTO(idAudiencia);
			//SE FINALIZA LA AUDIENCIA
			audienciaDelegate.finalizarAudiencia(audiencia);
			
			SolicitudTranscripcionAudienciaDTO solicitud = new SolicitudTranscripcionAudienciaDTO();			
			audiencia.setId(idAudiencia);
			audiencia = audienciaDelegate.obtenerAudiencia(audiencia);
			UsuarioDTO usuario = super.getUsuarioFirmado(request);
			solicitud.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA.getValorId()));
			solicitud.setExpedienteDTO(audiencia.getExpediente());	
			solicitud.setFechaCreacion(Calendar.getInstance().getTime());
			
			if(usuario != null && usuario.getFuncionario() != null && usuario.getFuncionario().getClaveFuncionario() != null){
				solicitud.setIdFuncionarioSolicitante(usuario.getFuncionario().getClaveFuncionario());
				solicitud.setNombreSolicitante(usuario.getFuncionario().getNombreCompleto());
			}
			else{
				solicitud.setIdFuncionarioSolicitante(null);
				solicitud.setNombreSolicitante(null);
			}
			
			solicitud.setUsuarioSolicitante(null);
			
			solicitud.setAudiencia(audiencia);
			solicitud.setEstatus(new ValorDTO(EstatusSolicitud.EN_PROCESO.getValorId()));
			solicitud.setFormaDTO(new FormaDTO(Formas.TRANSCIPCION.getValorId()));
			solicitud.setTipoDocumentoDTO(new ValorDTO(TipoDocumento.SOLICITUD.getValorId()));
			solicitud.setNombreDocumento("SOLICITUD_");
			//SE REGISTRA LA SOLICITUD DE TRANSCRIPCI&Oacute;N DE AUDIENCIA
			solicitudDelegate.registrarSolicitudTranscripcionAudiencia(solicitud);
			solicitud.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.AUDIO_VIDEO.getValorId()));
			//SE REGISTRA LA SOLICITUD DE AUDIO Y VIDEO
			solicitud.setFormaDTO(new FormaDTO(Formas.TRANSCIPCION.getValorId()));
			solicitudDelegate.registrarSolicitudTranscripcionAudiencia(solicitud);

			mensaje = "exito";
			
		} catch (NSJPNegocioException e) {		
			log.error(e.getCause(),e);
			if(e.getCodigo() == CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO){
				mensaje = "falla";
			}
			log.info(mensaje);
		}
		converter.alias("mensaje", String.class);
		escribirRespuesta(response, converter.toXML(mensaje));
		return null;
	}	
	
	/**
	 * Metodo utilizado para la consulta de los involucrados de la audiencia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarInvolucradosAudienciaIndividualizacion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR INVOLUCRADOS DE AUDIENCIA)");		
			Long idEvento = Long.parseLong(request.getParameter("idEvento"));
			String tipoConsulta = request.getParameter("tipoConsulta");
			log.info("ID DE LA AUDIENCIA---- " + idEvento);
			log.info("TIPO DE CONSULTA---- " + tipoConsulta);

			AudienciaDTO audienciaDTO = new AudienciaDTO();		
			audienciaDTO.setId(idEvento);		
			
			//Permite consultar funcionarios de una audiencia por especialidad
			List<Long> especialidades = new ArrayList<Long>();
			//Permite consultar involucrados de una audiencia por calidad
	    	List<Long> calidades = new ArrayList<Long>();
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			List<InvolucradoViewDTO> listaResultados = new ArrayList<InvolucradoViewDTO>(); 
			
			
			if (tipoConsulta.equals("0")){
				calidades.add(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId());		    	
		    	listaResultados = involucradoDelegate.obtenerInvolucradosAudienciaPorCalidades(audienciaDTO, calidades);
			}
			if (tipoConsulta.equals("1")){
		    	especialidades.add(TipoEspecialidad.MINISTERIO_PUBLICO.getValorId());		    	
		    	listaResultados = involucradoDelegate.obtenerFuncionarioAudienciaPorTipoEspecialidad(audienciaDTO, especialidades);
			}
			if (tipoConsulta.equals("2")){
				especialidades.add(TipoEspecialidad.DEFENSOR.getValorId());
		    	listaResultados = involucradoDelegate.obtenerFuncionarioAudienciaPorTipoEspecialidad(audienciaDTO, especialidades);
			}
			if (tipoConsulta.equals("3")){
				calidades.add(Calidades.TESTIGO.getValorId());		    	
		    	listaResultados = involucradoDelegate.obtenerInvolucradosAudienciaPorCalidades(audienciaDTO, calidades);
			}
			if (tipoConsulta.equals("4")){
				especialidades.add(TipoEspecialidad.PERICIAL.getValorId());		    	
		    	listaResultados = involucradoDelegate.obtenerFuncionarioAudienciaPorTipoEspecialidad(audienciaDTO, especialidades);
			}
			if (tipoConsulta.equals("5")){
				especialidades.add(TipoEspecialidad.POLICIA.getValorId());		    	
		    	listaResultados = involucradoDelegate.obtenerFuncionarioAudienciaPorTipoEspecialidad(audienciaDTO, especialidades);
			}
			if (tipoConsulta.equals("6")){
				calidades.add(Calidades.VICTIMA_PERSONA.getValorId());
				calidades.add(Calidades.DENUNCIANTE.getValorId());
				calidades.add(Calidades.BANDERA_DENUNCIANTE_VICTIMA.getValorId());
		    	listaResultados = involucradoDelegate.obtenerInvolucradosAudienciaPorCalidades(audienciaDTO, calidades);
			}
			if (tipoConsulta.equals("7")){
				calidades.add(Calidades.DENUNCIANTE.getValorId());		    		    	
		    	listaResultados = involucradoDelegate.obtenerInvolucradosAudienciaPorCalidades(audienciaDTO, calidades);
			}
			
			writer.print("<rows>");
			final PaginacionDTO pag = PaginacionThreadHolder.get();
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }			
			
			if (tipoConsulta.equals("0")){
				for (InvolucradoViewDTO involucradoViewDTO : listaResultados){	
					writer.print("<row id='"+involucradoViewDTO.getInvolucradoId()+ "'>");
						writer.print("<cell>"+ involucradoViewDTO.getNombre()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoPaterno()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoMaterno()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getNombreInstitucion()+ "</cell>");
						writer.print("<cell>"+"<![CDATA[<input type='checkbox' checked='checked' >]]>"+"</cell>");
					writer.print("</row>");
				}
			}
			
			if (tipoConsulta.equals("1")){
				for (InvolucradoViewDTO involucradoViewDTO : listaResultados){	
					String involucradoID = involucradoViewDTO.getInvolucradoId().toString();
					FuncionarioAudienciaDTO involucrado = audienciaDelegate.consultarAsistenciaFuncionario(involucradoViewDTO.getInvolucradoId(), idEvento);
					writer.print("<row id='"+involucradoViewDTO.getInvolucradoId()+ "'>");
						writer.print("<cell>"+ involucradoViewDTO.getNombre()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoPaterno()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoMaterno()+ "</cell>");
						if(involucrado != null && involucrado.getEsTitular() != null && involucrado.getEsTitular() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkTit_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkTit_"+involucradoID+"'>]]></cell>");
						}
						if(involucrado != null && involucrado.getEsPresente() != null && involucrado.getEsPresente() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"'>]]></cell>");
						}
						writer.print("</row>");
				}
			}
			
			if (tipoConsulta.equals("2")){								
				for (InvolucradoViewDTO involucradoViewDTO : listaResultados){	
					String involucradoID = involucradoViewDTO.getInvolucradoId().toString();
					FuncionarioAudienciaDTO involucrado = audienciaDelegate.consultarAsistenciaFuncionario(involucradoViewDTO.getInvolucradoId(), idEvento);
					writer.print("<row id='"+involucradoViewDTO.getInvolucradoId()+ "'>");
						writer.print("<cell>"+ involucradoViewDTO.getNombre()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoPaterno()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoMaterno()+ "</cell>");
						if(involucrado != null && involucrado.getEsTitular() != null && involucrado.getEsTitular() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkTit_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkTit_"+involucradoID+"'>]]></cell>");
						}
						if(involucrado != null && involucrado.getEsPresente() != null && involucrado.getEsPresente() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"'>]]></cell>");
						}
					writer.print("</row>");
				}
			}
			
			if (tipoConsulta.equals("3")){
				for (InvolucradoViewDTO involucradoViewDTO : listaResultados){	
					String involucradoID = involucradoViewDTO.getInvolucradoId().toString();
					InvolucradoAudienciaDTO involucrado = audienciaDelegate.consultarAsistenciaInvolucradoAudiencia(involucradoViewDTO.getInvolucradoId(), idEvento);
					writer.print("<row id='"+involucradoViewDTO.getInvolucradoId()+ "'>");
						writer.print("<cell>"+ involucradoViewDTO.getNombre()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoPaterno()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoMaterno()+ "</cell>");
						if(involucrado != null && involucrado.getEsPresente() != null && involucrado.getEsPresente() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaInvolucradoAudiencia("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaInvolucradoAudiencia("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"'>]]></cell>");
						}
						writer.print("<cell>"+ "<![CDATA[<input type='checkbox' >]]>"+ "</cell>");
					writer.print("</row>");
				}
			}
			
			if (tipoConsulta.equals("4")){			
				for (InvolucradoViewDTO involucradoViewDTO : listaResultados){	
					String involucradoID = involucradoViewDTO.getInvolucradoId().toString();
					FuncionarioAudienciaDTO involucrado = audienciaDelegate.consultarAsistenciaFuncionario(involucradoViewDTO.getInvolucradoId(), idEvento);
					writer.print("<row id='"+involucradoViewDTO.getInvolucradoId()+ "'>");
						writer.print("<cell>"+ involucradoViewDTO.getNombre()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoPaterno()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoMaterno()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getNombreInstitucion()+ "</cell>");
						writer.print("<cell>"+ (involucradoViewDTO.getTipoEspecialidad() != null ? involucradoViewDTO.getTipoEspecialidad().getValor():"")+ "</cell>");
						if(involucrado != null && involucrado.getEsPresente() != null && involucrado.getEsPresente() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"'>]]></cell>");
						}
						if(involucrado != null && involucrado.getEsTitular() != null && involucrado.getEsTitular() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkTit_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
									
									" id='chkTit_"+involucradoID+"'>]]></cell>");
						}
					writer.print("</row>");
				}
			}
			
			if (tipoConsulta.equals("5")){
				for (InvolucradoViewDTO involucradoViewDTO : listaResultados){	
					String involucradoID = involucradoViewDTO.getInvolucradoId().toString();
					FuncionarioAudienciaDTO involucrado = audienciaDelegate.consultarAsistenciaFuncionario(involucradoViewDTO.getInvolucradoId(), idEvento);
					writer.print("<row id='"+involucradoViewDTO.getInvolucradoId()+ "'>");
						writer.print("<cell>"+ involucradoViewDTO.getNombre()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoPaterno()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoMaterno()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getNombreInstitucion()+ "</cell>");
						if(involucrado != null && involucrado.getEsPresente() != null && involucrado.getEsPresente() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"'>]]></cell>");
						}
						if(involucrado != null && involucrado.getEsTitular() != null && involucrado.getEsTitular() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkTit_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaFuncionario("+involucradoID+");'" +
											" id='chkTit_"+involucradoID+"'>]]></cell>");
						}
					writer.print("</row>");
				}
			}
			
			if (tipoConsulta.equals("6")){
				for (InvolucradoViewDTO involucradoViewDTO : listaResultados){	
					String involucradoID = involucradoViewDTO.getInvolucradoId().toString();
					InvolucradoAudienciaDTO involucrado = audienciaDelegate.consultarAsistenciaInvolucradoAudiencia(involucradoViewDTO.getInvolucradoId(), idEvento);
					writer.print("<row id='"+involucradoViewDTO.getInvolucradoId()+ "'>");
						writer.print("<cell>"+ involucradoViewDTO.getNombre()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoPaterno()+ "</cell>");
						writer.print("<cell>"+ involucradoViewDTO.getApellidoMaterno()+ "</cell>");
						if(involucrado != null && involucrado.getEsPresente() != null && involucrado.getEsPresente() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaInvolucradoAudiencia("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaInvolucradoAudiencia("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"'>]]></cell>");
						}
						writer.print("<cell>"+ "<![CDATA[<input type='checkbox' >]]>"+ "</cell>");
					writer.print("</row>");
				}
			}
			
			if (tipoConsulta.equals("7")){
				for (InvolucradoViewDTO involucradoViewDTO : listaResultados){	
					String involucradoID = involucradoViewDTO.getInvolucradoId().toString();
					InvolucradoAudienciaDTO involucrado = audienciaDelegate.consultarAsistenciaInvolucradoAudiencia(involucradoViewDTO.getInvolucradoId(), idEvento);
					writer.print("<row id='"+involucradoViewDTO.getInvolucradoId()+ "'>");
						if((involucradoViewDTO.getNombre()==null || involucradoViewDTO.getNombre().equals("")) &&
						   (involucradoViewDTO.getApellidoPaterno()==null || involucradoViewDTO.getApellidoPaterno().equals("")) &&	
						   (involucradoViewDTO.getApellidoMaterno()==null || involucradoViewDTO.getApellidoMaterno().equals(""))){						
							writer.print("<cell>An&oacute;nimo</cell>");
							writer.print("<cell></cell>");
							writer.print("<cell></cell>");
						}
						else{
							writer.print("<cell>"+ involucradoViewDTO.getNombre()+ "</cell>");
							writer.print("<cell>"+ involucradoViewDTO.getApellidoPaterno()+ "</cell>");
							writer.print("<cell>"+ involucradoViewDTO.getApellidoMaterno()+ "</cell>");
						}
						if(involucrado != null && involucrado.getEsPresente() != null && involucrado.getEsPresente() == true){
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaInvolucradoAudiencia("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"' checked='checked'>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[" +
									"<input type='checkbox' onclick='guardarAsistenciaInvolucradoAudiencia("+involucradoID+");'" +
											" id='chkPre_"+involucradoID+"'>]]></cell>");
						}
						writer.print("<cell>"+ "<![CDATA[<input type='checkbox' >]]>"+ "</cell>");
					writer.print("</row>");
				}
			}	
			
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.error(e.getCause(), e);

		}
 	return null;
	}
	
	/**
	 * Metodo utilizado para la consulta de audiencias del dia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarAudienciaDelDiaPJENSExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR EVENTOS DEI DIA (AUDIENCIAS)");

			FiltroAudienciaDTO filtro = new FiltroAudienciaDTO();
			Date fechaHoy = new Date();
			fechaHoy = DateUtils.obtener(DateUtils.formatear(fechaHoy));
			filtro.setFechaInicial(fechaHoy);
			filtro.setFechaFinal(fechaHoy);
			List<AudienciaDTO> listaDeAudiencias = audienciaDelegate.buscarAudiencias(filtro);
			
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }

			for (AudienciaDTO audienciaDTO : listaDeAudiencias) {
			    writer.print("<row id='"+ audienciaDTO.getId() +"*"+audienciaDTO.getExpediente().getNumeroExpediente()+"'>");
				writer.print("<cell>"+(((audienciaDTO.getExpediente()!= null)&&( audienciaDTO.getExpediente().getCasoDTO())!= null)?audienciaDTO.getExpediente().getCasoDTO().getNumeroGeneralCaso():"")+"</cell>");
				writer.print("<cell>"+ (audienciaDTO.getExpediente()!=null ? audienciaDTO.getExpediente().getNumeroExpediente():"-") +  "</cell>");
				writer.print("<cell>"+ audienciaDTO.getCaracter() + "</cell>");
				writer.print("<cell>"+ audienciaDTO.getTipoAudiencia().getValor()+ "</cell>");
				String fechaSolicitud=DateUtils.formatear(audienciaDTO.getFechaEvento());
				writer.print("<cell>"+ fechaSolicitud + "</cell>");
				String horaSolicitud=DateUtils.formatearHora(audienciaDTO.getFechaEvento());
				writer.print("<cell>"+ horaSolicitud + "</cell>");
				writer.print("<cell>"+ audienciaDTO.getSala().getUbicacionSala()+ "</cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.error(e.getCause(), e);

		}
		return null;
	}
	
	
	
	
	/**
	 * Metodo utilizado para la consulta de audiencias del dia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward condultaGridControvercias(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("Grid Controvercias");

			List<CartaCumplimientoDTO> cumplimientoDTOs = new ArrayList<CartaCumplimientoDTO>();
			cumplimientoDTOs = delegateDelegate.consultarControversiasResueltas(TipoDocumento.CARTA_DE_CUMPLIMIENTO_DE_ACUERDO.getValorId());
			log.info("Grid Controvercias resultado"+cumplimientoDTOs);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			int lTotalRegistros = cumplimientoDTOs.size();

			
			writer.print("<records>" + lTotalRegistros + "</records>");
			for (CartaCumplimientoDTO doc : cumplimientoDTOs) {
				log.info("Número de caso: "+doc.getExpedienteDTO().getCasoDTO().getCasoId()+" ("+doc.getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso()+")");
				log.info("Identificador de la controversia resuelta: "+doc.getDocumentoId());
				log.info("Nombre completo del fiscal que llevó a cabo la controversia: "+doc.getResponsableDocumento().getNombreCompleto());
				log.info("Nombre del documento: "+doc.getNombreDocumento());
				log.info("Bandera si ya ha sido leída la controversia: "+doc.getEsLeido());
//				log.info("Fecha de envío de la misma(creacion doc): "+doc.getFechaCreacion());
				log.info("Fecha de envío de la misma(creacion act): "+doc.getActividadDTO().getFechaCreacion());
				log.info("Archivo digital: "+doc.getArchivoDigital().getArchivoDigitalId());
			   
				
				writer.print("<row id='"+ doc.getExpedienteDTO().getCasoDTO().getCasoId()+"'>");
				writer.print("<cell>"+doc.getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso()+"</cell>");
				writer.print("<cell>"+ doc.getDocumentoId()+  "</cell>");
				writer.print("<cell>"+ doc.getResponsableDocumento().getNombreCompleto() + "</cell>");
				writer.print("<cell>"+ doc.getNombreDocumento()+ "</cell>");
				
				writer.print("<cell>"+ doc.getEsLeido() + "</cell>");
				
				writer.print("<cell>"+doc.getActividadDTO().getFechaCreacion()+ "</cell>");
				writer.print("<cell>"+doc.getArchivoDigital().getArchivoDigitalId()+ "</cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.error(e.getCause(), e);

		}
		return null;
	}
	
	
	public ActionForward cargaGradodeParticipacion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
    	try {
    		log.info("ejecutando Action Cargar Combo Grado Participacion"); 
    		List<CatalogoDTO> listaCatalogo= new ArrayList<CatalogoDTO>();
    		//catDelegate.recuperarCatalogoCompleto(Catalogos.MODO_PARTICIPACION_DELITO);
    		//FIXME consultar el catálogo real
    		
    		
    		//Los que sin acuerdo previo, intervengan con otros en su comisión, cuando no se pueda precisar el resultado que cada quien produjo.
			//0.1
    		CatalogoDTO cat = new CatalogoDTO();
    		cat.setClave(1L);
    		cat.setValor("Los que sin acuerdo previo, intervengan con otros en su comisión, cuando no se pueda precisar el resultado que cada quien produjo.");
    		cat.addValorExtra(new ValorDTO(1L,".1"));
    		listaCatalogo.add(cat);
    		//Los que sabiendo que se está cometiendo un delito o se va a cometer y, teniendo el deber legal de impedir su ejecución, no lo impiden.
			//0.2
    		cat = new CatalogoDTO();
    		cat.setClave(2L);
    		cat.setValor("Los que sabiendo que se está cometiendo un delito o se va a cometer y, teniendo el deber legal de impedir su ejecución, no lo impiden.");
    		cat.addValorExtra(new ValorDTO(1L,".2"));
    		listaCatalogo.add(cat);
    		//Los que con posterioridad a su ejecución, auxilien al sujeto activo del delito, por acuerdo previo
			//0.3
    		cat = new CatalogoDTO();
    		cat.setClave(3L);
    		cat.setValor("Los que con posterioridad a su ejecución, auxilien al sujeto activo del delito, por acuerdo previo");
    		cat.addValorExtra(new ValorDTO(1L,".3"));
    		listaCatalogo.add(cat);
    		//Los que intencionalmente presten ayuda o auxilio para su comisión
			//0.4
    		cat = new CatalogoDTO();
    		cat.setClave(4L);
    		cat.setValor("Los que intencionalmente presten ayuda o auxilio para su comisión");
    		cat.addValorExtra(new ValorDTO(1L,".4"));
    		listaCatalogo.add(cat);
    		//Los que dolosamente hagan tomar una resolución a otro para cometerlo
			//0.5
    		cat = new CatalogoDTO();
    		cat.setClave(5L);
    		cat.setValor("Los que dolosamente hagan tomar una resolución a otro para cometerlo");
    		cat.addValorExtra(new ValorDTO(1L,".5"));
    		listaCatalogo.add(cat);
    		//Los que instigan o compelen a su ejecución
			//0.6
    		cat = new CatalogoDTO();
    		cat.setClave(6L);
    		cat.setValor("Los que instigan o compelen a su ejecución");
    		cat.addValorExtra(new ValorDTO(1L,".6"));
    		listaCatalogo.add(cat);
    		//Los que lo lleven a cabo sirviéndose de otro.
			//0.7
    		cat = new CatalogoDTO();
    		cat.setClave(7L);
    		cat.setValor("Los que lo lleven a cabo sirviéndose de otro.");
    		cat.addValorExtra(new ValorDTO(1L,".7"));
    		listaCatalogo.add(cat);

			//A la persona que se sirvió de un menor de dieciocho años de edad o persona que tenga un trastorno mental o desarrollo intelectual retardado para la realización de un delio, se le impondrá, además de la pena correspondiente, un tercio más.
			//0.8 
    		cat = new CatalogoDTO();
    		cat.setClave(8L);
    		cat.setValor("A la persona que se sirvió de un menor de dieciocho años de edad o persona que tenga un trastorno mental o desarrollo intelectual retardado para la realización de un delio, se le impondrá, además de la pena correspondiente, un tercio más.");
    		cat.addValorExtra(new ValorDTO(1L,".8"));
    		listaCatalogo.add(cat);
    		
    		//Los que intervienen en su concepción, preparación o ejecución
			//0.9
    		cat = new CatalogoDTO();
    		cat.setClave(9L);
    		cat.setValor("Los que intervienen en su concepción, preparación o ejecución");
    		cat.addValorExtra(new ValorDTO(1L,".8"));
    		listaCatalogo.add(cat);
    		
    		
    		
    		
    		
    		converter.alias("valorDTO", ValorDTO.class);
			converter.alias("listaCatalogo", java.util.List.class);
			converter.alias("catParticipacion", CatalogoDTO.class);
			
			String xml = converter.toXML(listaCatalogo);
			escribirRespuesta(response, xml);
			
			}
		catch (Exception e) {
			log.info(e);
		}
			
	
		return null;

		
		
		
	}
	
	public ActionForward cargaCatalogoCalificativa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
//		
    	try {
    		log.info("ejecutando Action Cargar Combo Grado Participacion"); 
    		//List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogoCompleto();
    		
    		
    		
    	
			converter.alias("listaCatalogo", java.util.List.class);
			converter.alias("catParticipacion", CatalogoDTO.class);
			
			String xml = converter.toXML("");
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			}
		catch (Exception e) {
			log.info(e);
		}
			
			
//			e.printStackTrace();
//		}
		return null;

		
		
		
	}
	
	/**
	 * Metodo utilizado para la consulta de los involucrados de la audiencia para calculo de pena.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarInvolucradosAudienciaCalculoPena(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR INVOLUCRADOS DE AUDIENCIA calculo de Pena)");
			
			Long idEvento = Long.parseLong(request.getParameter("idEvento"));
			
			log.info("LLEGA ID EVENTO ---- " + idEvento);

			AudienciaDTO audienciaDTO = new AudienciaDTO();		
			audienciaDTO.setId(idEvento);		
			
			List<InvolucradoViewDTO> listaInvolucrados = involucradoDelegate.obtenerImputadosAudiencia(audienciaDTO);
			
			log.info("LISTA DE INVOLUCRADOS CON DELITOS" + listaInvolucrados);

			converter.alias("listaInvolucrados", java.util.List.class);
			converter.alias("InvolucradoViewDTO", InvolucradoViewDTO.class);
			converter.alias("ValorDTO", ValorDTO.class);
			converter.alias("delitoDTO", DelitoDTO.class);
			String xml = converter.toXML(listaInvolucrados);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();

		} catch (Exception e) {
			log.error(e.getCause(), e);

		}
 	return null;
	}
	
	/**
	 * Crea un nuevo objeto de mandamiento judicial listo para que su documento sea emitido 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 * @throws NSJPNegocioException 
	 */
	public ActionForward crearMandamientoJudicial(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, NSJPNegocioException {
		
		log.info("EJECUTANDO ACTION ---- CREAR MANDAMIENTO JUDICIAL");

		log.info("VERIFICANDO LOS PARAMETROS****************************");
		log.info("resolutivoId="+request.getParameter("resolutivoId"));
		log.info("tipoMandamiento="+request.getParameter("tipoMandamiento"));
		log.info("idImputado="+request.getParameter("idImputado"));
		log.info("tipoSentencia="+request.getParameter("tipoSentencia"));
		log.info("fechaInicio="+request.getParameter("fechaInicio"));
		log.info("fechaFin="+request.getParameter("fechaFin"));
		log.info("fechaEjecucion="+request.getParameter("fechaEjecucion"));
		log.info("descripcionMandamiento="+request.getParameter("descripcionMandamiento"));
		
		Long resolutivoId = NumberUtils.toLong(request.getParameter("resolutivoId"),0L);
		Long tipoMandamiento = NumberUtils.toLong(request.getParameter("tipoMandamiento"),0L);
		Long idImputado = NumberUtils.toLong(request.getParameter("idImputado"),0L) ;
		Long tipoSentencia = NumberUtils.toLong(request.getParameter("tipoSentencia"), 0L);
		Long idAudiencia = NumberUtils.toLong(request.getParameter("idAudiencia"),0L) ;
		Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpedienteId"), 0L);
		String descripcionMandamiento = request.getParameter("descripcionMandamiento");
		
		Date fechaInicio = new Date();
		
		if(!request.getParameter("fechaInicio").equals("")){
			fechaInicio= DateUtils.obtener(request.getParameter("fechaInicio"));
		}
		
		Date fechaFin = new Date();
		
		if(!request.getParameter("fechaFin").equals("")){
			fechaFin = DateUtils.obtener(request.getParameter("fechaFin"));
		}
		
		Date fechaEjecuacion = new Date();

		if(!request.getParameter("fechaEjecuacion").equals("")){
			fechaEjecuacion= DateUtils.obtener(request.getParameter("fechaEjecuacion"));
		}
		
		if(resolutivoId > 0 && idImputado >0 && tipoMandamiento > 0){
			

			MandamientoDTO mandamiento = new MandamientoDTO();
			mandamiento.setResolutivo(new ResolutivoDTO());
			mandamiento.getResolutivo().setResolutivoId(resolutivoId);
			mandamiento.setTipoMandamiento(new ValorDTO(tipoMandamiento));
			mandamiento.setFechaCreacion(new Date());
			mandamiento.setInvolucrado(new InvolucradoDTO(idImputado));
			mandamiento.setFechaEjecuacion(fechaEjecuacion);
			mandamiento.setDescripcion(descripcionMandamiento);
			
			//Para sentencia
			if(tipoMandamiento.equals(TipoMandamiento.SENTENCIA.getValorId())){
				
				mandamiento.setTipoSentencia(new ValorDTO(tipoSentencia));
				mandamiento.setFechaInicial(fechaInicio);
				mandamiento.setFechaFinal(fechaFin);
			}
			
			mandamiento.setUsuario(super.getUsuarioFirmado(request));
			
			try {
				mandamiento = mandamientoDelegate.registrarMandamientoJudicial(mandamiento);
			
				//Para cuando es una Orden de Aprehension
				if(tipoMandamiento.equals(TipoMandamiento.ORDEN_DE_APREHENSION.getValorId())){
				
					OrdenDeAprehensionDTO ordenDeAprehensionDTO = new OrdenDeAprehensionDTO();
					ordenDeAprehensionDTO.setMandamientoDTO(mandamiento);
					ordenDeAprehensionDTO.setAudienciaDTO(new AudienciaDTO(idAudiencia));
					ordenDeAprehensionDTO.setEsCumplida(Boolean.FALSE);
					ordenDeAprehensionDTO.setSePresentaVoluntariamente(Boolean.FALSE);
					ordenDeAprehensionDTO.setInvolucradoDTO(new InvolucradoDTO(idImputado));
					ordenDeAprehensionDTO.setNumeroExpedienteDTO(new ExpedienteDTO(null, numeroExpedienteId, null));
					
					ordenDeAprehensionDelegate.registrarOrdenDeAprehension(ordenDeAprehensionDTO);
				}
				
				converter.alias("mandamientoDTO", MandamientoDTO.class);
				//setExpedienteTrabajo(request, mandamiento.getResolutivo().getAudiencia().getExpediente());
				log.info("MANDAMIENTO JUDICIAL, RESPUESTA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!::::::::::::::::::::::::::::::::::::::::::::::::::::="+mandamiento);
				escribirRespuesta(response, converter.toXML(mandamiento));
				
			} catch (NSJPNegocioException e) {
				log.error(e.getMessage(),e);
			}
		}
		return null;
	}
	
	
	/**
	 * Crea un nuevo objeto de mandamiento judicial listo para que su documento sea emitido 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward recuperaIdSiguienteAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		log.info("EJECUTANDO ACTION ---- RECUPERA ID SIGUIENTE AUDIENCIA)");
		Long idAudiencia = NumberUtils.toLong(request.getParameter("idAudiencia"));
		log.info("ID AUDIENCIA ACTUAL="+idAudiencia);
		AudienciaDTO audienciaDTO = new AudienciaDTO();
		audienciaDTO.setId(idAudiencia);
		try{
			Long audiencia;
		audiencia = audienciaDelegate.crearAudienciaSiguiente(audienciaDTO);
		
		converter.alias("audienciaDTO", AudienciaDTO.class);
		String xml = converter.toXML(audiencia);
		response.setContentType("text/xml");
		PrintWriter pw = response.getWriter();
		pw.print(xml);
		pw.flush();
		pw.close();
		}catch (NSJPNegocioException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	/**
	 * Asocia un involucrado a una audiencia, el ID del involucrado y el ID de audiencia
	 * son enviados como par&aacute;metros del request
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @author Emigdio Hern&aacute;ndez
	 * @throws IOException
	 */
	public ActionForward asociarInvolucradoAAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Long involucradoId = NumberUtils.toLong(request.getParameter("involucradoId"));
		Long audienciaId = NumberUtils.toLong(request.getParameter("audienciaId"));
		try {
			audienciaDelegate.asociarInvolucradoAAudiencia(involucradoId, audienciaId);
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(),e);
		}
		
		
		escribirRespuesta(response, converter.toXML(involucradoId));
		
		return null;
	}
	
	/**
	 * Metodo utilizado para realizar el paso del parametro id del evento
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward visorDocumentoDetalle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action visorDocumentoDetalle");
			
			
			String numExpediente = request.getParameter("numExpediente");
			String numCaso = request.getParameter("numCaso");
			ExpedienteDTO expedienteDto = null;
			
			log.info("NUMERO EXPEDIENTE:::"+ numExpediente);
			
			
			
			if(numCaso != null && !numCaso.trim().equals("")){
				//Buscar por Numero de expediente y Caso
				expedienteDto = expedienteDelegate.obtenerExpedientePorNumeroExpedienteYNumeroCaso(numExpediente,numCaso);
			}				
			else
				expedienteDto = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numExpediente);
			
			List<InvolucradoDTO> involucradoDTOs = new ArrayList<InvolucradoDTO>();
			involucradoDTOs=expedienteDto.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA);
			List<InvolucradoDTO> involucradoDTO = new ArrayList<InvolucradoDTO>();
			involucradoDTO=expedienteDto.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION);
			for (InvolucradoDTO involucradoDTO2 : involucradoDTO) {
				involucradoDTOs.add(involucradoDTO2);
			}
			expedienteDto.setInvolucradosDTO(involucradoDTOs);
			super.setExpedienteTrabajo(request, expedienteDto);
			converter.alias("expedienteDto", ExpedienteDTO.class);
			converter.alias("delitoDto", DelitoDTO.class);
			converter.alias("involucradoDTO", InvolucradoDTO.class);
			converter.alias("nombreDemograficoDTO", NombreDemograficoDTO.class);
			String xml = converter.toXML(expedienteDto);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			
		} catch (Exception e) {		
			log.error(e.getCause(),e);
			
		}
		return null;
	}
	
	
	
	/**
	 * Metodo utilizado para realizar el paso del parametro id del evento
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarImputadosParaMandamientoXAudiencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTADO ACTION CONSULTAR IMPUTADOS PARA MANDAMIENTO X AUDIENCIA");
			log.info("ID DE AUDIENCIA="+request.getParameter("idAudiencia"));
			
			Long idAudiencia = NumberUtils.toLong(request.getParameter("idAudiencia"), 0L);
			
						
			if(idAudiencia > 0L){
				
				AudienciaDTO audienciaDTO = new AudienciaDTO();		
				audienciaDTO.setId(idAudiencia);	
				List<InvolucradoViewDTO> listaImputados = involucradoDelegate.obtenerImputadosAudiencia(audienciaDTO);
				
				converter.alias("listaImputados", java.util.List.class);
				converter.alias("imputado", InvolucradoViewDTO.class);
				String xml = converter.toXML(listaImputados);
				response.setContentType("text/xml");
				PrintWriter pw = response.getWriter();
				pw.print(xml);
				pw.flush();
				pw.close();
			}
			
		} catch (Exception e) {
			escribir(response, "", null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la asociaci&oacute;n de una organizaci&oacute;n 
	 * a una audiencia, de esta manera se modela la relaci&oacute;n de los intervinientes
	 * de tipo persona moral con la audiencia.
	 * @param mapping - mapeo de struts para redirigir el flujo de la aplicaci&oacute;n
	 * @param form - forma de struts con la informaci&oacute;n capturada desde la vista
	 * @param request - Objeto de java que representa la petici&oacute;n de HTML
	 * @param response - Objeto de java que representa la respuesta de HTML
	 * @return null - Debido a que se escribe el xml con la respuesta en el response
	 * @throws IOException - En el caso de que ocurra alguna falla al momento de escribir
	 * 						 la respuesta en el xml.
	 */
	public ActionForward asociarOrganizacionAAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Long involucradoId = NumberUtils.toLong(request.getParameter(KEY_REQUEST_ID_INVOLUCRADO));
		Long organizacionId = NumberUtils.toLong(request.getParameter(KEY_REQUEST_ID_ORGANIZACION));
		Long audienciaId = NumberUtils.toLong(request.getParameter(KEY_REQUEST_ID_AUDIENCIA));
		try {
			audienciaDelegate.asociarOrganizacionAAudiencia(organizacionId, audienciaId, involucradoId);
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(),e);
		}
		
		escribirRespuesta(response, converter.toXML(organizacionId));
		
		return null;
	}
	
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las organizaciones que han sido 
	 * asociadas a una audiencia en especi&acute;co para regresarlas dentro del response
	 * y poder mostrarlas dentro de un grid. 
	 * @param mapping - mapeo de struts para redirigir el flujo de la aplicaci&oacute;n
	 * @param form - forma de struts con la informaci&oacute;n capturada desde la vista
	 * @param request - Objeto de java que representa la petici&oacute;n de HTML
	 * @param response - Objeto de java que representa la respuesta de HTML
	 * @return null - Debido a que se escribe el xml con la respuesta en el response.
	 * @throws IOException - En el caso de que ocurra alguna falla al momento de escribir
	 * 						 la respuesta en el xml.
	 */
	public ActionForward consultarOrganizacionesDeAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Long audienciaId = NumberUtils.toLong(request.getParameter(KEY_REQUEST_ID_AUDIENCIA));
		AudienciaDTO audiencia = new AudienciaDTO();
		audiencia.setId(audienciaId);
		
		try {
			List<OrganizacionDTO> organizaciones = audienciaDelegate.consultarOrganizacionesAudiencia(audiencia);
			escribirXmlOrganizacionesAudiencia(response, organizaciones);
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(),e);
		}
		
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la escritura del xml con los datos de la organizaci&oacute;n
	 * dentro del response de java para ser interpretados por el grid para la consulta de las 
	 * organizaciones.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada de regreso
	 * 					 al navegador para su interpretaci&oacute;n
	 * @param organizaciones - Lista con los datos de las organizaciones que ser&aacute;n regresadas 
	 * 						   dentro del grid.
	 */
	private void escribirXmlOrganizacionesAudiencia(HttpServletResponse response, 
			List<OrganizacionDTO> organizaciones){
		
		response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML +" charset=ISO-8859-1");
		response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL, ConstantesGenerales.ENCABEZADO_NOCACHE);
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.print("<rows>");
			final PaginacionDTO pag2 = PaginacionThreadHolder.get();
			if (pag2 != null){
				if (pag2 != null && pag2.getTotalRegistros() != null) {
					writer.print("<total>" + pag2.getTotalPaginas() + "</total>");
					writer.print("<records>" + pag2.getTotalRegistros() + "</records>");
				} else {
					writer.print("<total>0</total>");
					writer.print("<records>0</records>");
				}
			}
			List<List<String>> tablaValores = new ArrayList<List<String>>();
			for (OrganizacionDTO org : organizaciones) {
				tablaValores.add(crearFilaParaGrid(org));
			}
			String datos = crearDatosGrid(tablaValores);
			writer.print(datos);
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la creaci&oacute;n de listas de cadenas
	 * con la informaci&oacute;n que se debe de mostrar para el grid de consulta 
	 * de organizaciones.
	 * @param org - OrganizacionDTO del cual se obtiene la informaci&oacute;n a 
	 * 				mostrar dentro del grid de organizaciones.
	 * @return List<String> - Lista con la cadena de informaci&oacute;n de la cual 
	 * 						  se va a crear el xml para ser interpretado por el grid.
	 */
	private List<String> crearFilaParaGrid(OrganizacionDTO org){
		List<String> fila = new ArrayList<String>();
		fila.add(org.getElementoId().toString());
		fila.add(org.getNombreOrganizacion());
		fila.add(org.getNombreCorto());
		if (org.getValorByTipoOrganizacionVal() != null){
			fila.add(org.getValorByTipoOrganizacionVal().getValor());
		}else{
			fila.add(null);
		}
		return fila;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la escritura de cada uno de los registros de 
	 * una lista dentro de un xml para que &eacute;stos puedan ser interpretados 
	 * por el plugin del jqGrid de jQuery.
	 * @param matrizValores - Lista de listas de cadenas que representa la informaci&oacute;n 
	 * 						  que ser&aacute; vaciada dentro del grid.
	 * @return xml - Cadena que respresenta los registros del grid como un xml.
	 */
	private String crearDatosGrid(List<List<String>> matrizValores){
		StringBuilder builder = new StringBuilder();
		if (matrizValores != null && !matrizValores.isEmpty()){
			for (List<String> fila : matrizValores){
				if (fila != null && !fila.isEmpty()){
					for (int i=0; i<fila.size();i++){
						if (i==0){
							builder.append("<row id='"+ fila.get(i)+"'>");
						}else{
							builder.append("<cell>"+ConstantesGenerales.C_DATA_OPEN +"<div class='celdaGrid'>");
							if (fila.get(i) != null){
								builder.append(fila.get(i));
							}else{
								builder.append(" - ");
							}
							builder.append("</div>"+ConstantesGenerales.C_DATA_CLOSE+"</cell>");
						}
					}
					builder.append("</row>");
				}
			}
		}
		return builder.toString();
	}
}
