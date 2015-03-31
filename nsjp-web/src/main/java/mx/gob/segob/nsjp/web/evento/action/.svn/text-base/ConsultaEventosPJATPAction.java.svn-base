package mx.gob.segob.nsjp.web.evento.action;

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

import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.audiencia.Eventos;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.ordenaprehension.OrdenDeAprehensionDelegate;
import mx.gob.segob.nsjp.delegate.sentencia.SentenciaDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.EventoDTO;
import mx.gob.segob.nsjp.dto.audiencia.FiltroAudienciaDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudTranscripcionAudienciaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.evento.form.RegistrarSolicitudPJForm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author AlejandroGA
 * 
 */
public class ConsultaEventosPJATPAction extends GenericAction {

	/** Log de clase */
	private static final Logger log = Logger
			.getLogger(ConsultaEventosPJATPAction.class);

	@Autowired
	public AudienciaDelegate audienciaDelegate;

	@Autowired
	public SolicitudDelegate solicitudDelegate;

	@Autowired
	InvolucradoDelegate involucradoDelegate;

	@Autowired
	OrdenDeAprehensionDelegate ordenDeAprehensionDelegate;

	private boolean esSentencia;

	private final static String KEY_SESSION_EVENTO = "KEY_SESSION_EVENTO_DTO";

	private final static String TIPO_RESPUESTA_CERO = "0";
	private final static String TIPO_RESPUESTA_UNO = "1";


	@Autowired
	public SentenciaDelegate sentenciaDelegate;
	
	/**
	 * Metodo utilizado para realizar el paso del parametro id del evento a la
	 * JSP de atencionSolicitudAudienciaNotificador
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward acarrearNuevaSolicitud(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando action acarrear NuevaSOlicitud ");

			String nuevaSolicitud = request.getParameter("nuevaSolicitud");
			log.info("ID-DEL EVENTO:::" + nuevaSolicitud);
			request.setAttribute("nuevaSolicitud", nuevaSolicitud);

			String numeroExpedienteId = request
					.getParameter("numeroExpedienteId");
			log.info("ID DEL NUMERO DE EXPEDIENTE=" + numeroExpedienteId);
			request.setAttribute("numeroExpedienteId", numeroExpedienteId);

			String numeroCausa = request.getParameter("numeroCausa");
			log.info("ID DEL NUMERO DE CAUSA=" + numeroCausa);
			request.setAttribute("numeroCausa", numeroCausa);

			String numeroGeneralDeCaso = request
					.getParameter("numeroGeneralDeCaso");
			log.info("ID DEL NUMERO GENERAL DE CASO=" + numeroGeneralDeCaso);
			request.setAttribute("numeroGeneralDeCaso", numeroGeneralDeCaso);

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return mapping.findForward("succes");
	}

	/**
	 * Metodo utilizado para realizar la consulta de casos por fecha
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarAudienciasPorFecha(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			log.info("ejecutando buscar Audiencias por fecha");

			String fechaInicio = request.getParameter("fechaInicio");
			String fechaFin = request.getParameter("fechaFin");

			String[] idsEstatusAudiencia = request
					.getParameterValues("idsEstatusAudiencia");
			
			/*Enable tanto audiencias como solicitudes */
			String idEstatusSolicitud = request.getParameter("idsEstatusSolicitud");

			log.info("Estatus Solicitud :: "+idEstatusSolicitud);
			
			log.info("fecha de inicio" + fechaInicio + "fecha fin" + fechaFin);

			DateFormat formato = new SimpleDateFormat("dd/MM/yy");
			Date fechaCreacionInicio = null;
			Date fechaCreacionFin = null;

			try {
				if (fechaInicio != null && fechaFin != null) {
					fechaCreacionInicio = formato.parse(fechaInicio);
					fechaCreacionFin = formato.parse(fechaFin);
				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

			FiltroAudienciaDTO filtro = new FiltroAudienciaDTO();
			filtro.setFechaInicial(fechaCreacionInicio);
			filtro.setFechaFinal(fechaCreacionFin);
			UsuarioDTO usuario = getUsuarioFirmado(request);
			filtro.setUsuario(usuario);

			// Filtra por estatus de Audiencia
			if (idsEstatusAudiencia != null) {
				List<Long> estatusAudiencia = new ArrayList<Long>();
				for (String valor : idsEstatusAudiencia) {
					estatusAudiencia.add(Long.parseLong(valor));
				}
				filtro.setEstatusAudiencia(estatusAudiencia);
			}
			/*Enable tanto solicitudes como audiencias canceladas 
			 * No va ahi pero se lo seteo para poder consultar tanto audiencias como solicitudes*/
			if(idEstatusSolicitud!= null){
				filtro.setEstatusSolicitud((Long.parseLong(idEstatusSolicitud)));	      
			}			
			

			List<AudienciaDTO> listaDeAudiencias = audienciaDelegate
					.buscarAudiencias(filtro);

			log.info("SOLICITUD AUDIENCIA" + listaDeAudiencias);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros()
						+ "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}

			// int lTotalRegistros = listaDeAudiencias.size();
			// writer.print("<records>" + lTotalRegistros + "</records>");

			for (AudienciaDTO audienciaDTO : listaDeAudiencias) {

				log.info("SOLICITUD AUDIENCIA" + audienciaDTO);

				writer.print("<row id='" + audienciaDTO.getId() + "'>");

				// Caso
				writer.print("<cell>"
						+ (((audienciaDTO.getExpediente() != null) && (audienciaDTO
								.getExpediente().getCasoDTO()) != null) ? audienciaDTO
								.getExpediente().getCasoDTO()
								.getNumeroGeneralCaso()
								: "") + "</cell>");
				// Expediente
				if (audienciaDTO.getExpediente() != null
						&& audienciaDTO.getExpediente().getNumeroExpediente() != null) {
					writer.print("<cell>"
							+ audienciaDTO.getExpediente()
									.getNumeroExpediente() + "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}
				// Caracter
				if (audienciaDTO.getCaracter() != null) {
					writer.print("<cell>" + audienciaDTO.getCaracter()
							+ "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}

				// Tipo de audiencia
				if (audienciaDTO.getTipoAudiencia() != null
						&& audienciaDTO.getTipoAudiencia().getValor() != null) {
					writer.print("<cell>"
							+ audienciaDTO.getTipoAudiencia().getValor()
							+ "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}

				// Fecha de la audiencia
				if (audienciaDTO.getFechaEvento() != null) {
					String fechaSolicitud = DateUtils.formatear(audienciaDTO
							.getFechaEvento());
					writer.print("<cell>" + fechaSolicitud + "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}

				// Hora de la audiencia
				if (audienciaDTO.getFechaEvento() != null) {
					String horaSolicitud = DateUtils.formatearHora(audienciaDTO
							.getFechaEvento());
					writer.print("<cell>" + horaSolicitud + "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}

				// Sala
				if (audienciaDTO.getSala() != null
						&& audienciaDTO.getSala().getNombreSala() != null) {
					writer.print("<cell>"
							+ audienciaDTO.getSala().getNombreSala()
							+ "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}

				writer.print("<cell>"
						+ (audienciaDTO.getEstatusAudiencia() != null
								&& audienciaDTO.getEstatusAudiencia()
										.getValor() != null ? audienciaDTO
								.getEstatusAudiencia().getValor() : "---")
						+ "</cell>");

				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}

	/**
	 * Metodo utilizado para realizar la consulta de eventos por expediente
	 * (SOLO HISTÓRCIO)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultaEventosPorExpedientePJATP(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando Consulta Solicitudes por expediente atencion al publico");

			String numeroExpedienteId = request
					.getParameter("numeroExpedienteId");
			log.info("ID DEL NUMERO DE EXPEDIENTE=" + numeroExpedienteId);

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setNumeroExpedienteId(Long
					.parseLong(numeroExpedienteId));

			List<SolicitudDTO> listaSolicitudes = solicitudDelegate
					.consultarSolicitudesPorExpediente(expedienteDTO);

			log.info("Solicitud" + listaSolicitudes);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			int lTotalRegistros = listaSolicitudes.size();

			writer.print("<records>" + lTotalRegistros + "</records>");
			for (SolicitudDTO solicitudesDto : listaSolicitudes) {

				log.info("Solicitud" + solicitudesDto);

				writer.print("<row id='" + solicitudesDto.getDocumentoId()
						+ "'>");
				writer.print("<cell>"
						+ solicitudesDto.getExpedienteDTO()
								.getNumeroExpediente() + "</cell>");
				writer.print("<cell>"
						+ solicitudesDto.getTipoSolicitudDTO().getValor()
						+ "</cell>");
				String fechaSolicitud = DateUtils.formatear(solicitudesDto
						.getFechaCreacion());
				writer.print("<cell>" + fechaSolicitud + "</cell>");
				String horaSolicitud = DateUtils.formatearHora(solicitudesDto
						.getFechaCreacion());
				writer.print("<cell>" + horaSolicitud + "</cell>");
				writer.print("<cell>"
						+ (solicitudesDto.getNombreInstitucionSolicitante() != null ? solicitudesDto
								.getNombreInstitucionSolicitante() : "-")
						+ "</cell>");
				writer.print("<cell>"
						+ (solicitudesDto.getNombreSolicitante() != null ? solicitudesDto
								.getNombreSolicitante() : "-") + "</cell>");
				writer.print("<cell>" + solicitudesDto.getEstatus().getValor()
						+ "</cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}

	/**
	 * Metodo utilizado para realizar el paso del parametro id del evento a la
	 * JSP de atencionSolicitudAudienciaNotificador
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward visorAtnAlPublicoAudiencias(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando action visorAtnAlPublicoAudiencias");

			String idEvento = request.getParameter("idEvento");
			log.info("ID-DEL EVENTO:::" + idEvento);
			request.setAttribute("idEvento", idEvento);

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return mapping.findForward("succes");
	}

	/**
	 * Metodo utilizado para realizar del detalle de eventos
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward detalleAudienciasDelDia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO CONSULTA DETALLE AUDIENCIAS DEL DIA");

			// Se obtiene el id del evento a consultar a detalle
			String idEvento = request.getParameter("idEvento");
			// Se obtiene el tipo de respuesta que se desea,
			String tipoDeRespuesta = request.getParameter("tipoDeRespuesta");

			log.info("_____________________________________________________________");
			log.info("tipo respuesta::::::::::" + tipoDeRespuesta);
			log.info("id del envento::::::::::" + idEvento);
			log.info("_____________________________________________________________");

			AudienciaDTO audienciaDTO = new AudienciaDTO();
			audienciaDTO.setId(Long.parseLong(idEvento));
			audienciaDTO.setTipoEvento(Eventos.AUDENCIA);

			if (tipoDeRespuesta.equals(TIPO_RESPUESTA_UNO)) {

				log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA DETALLE DE EVENTO:::::");
				/**
				 * Se desea enviar el objeto evento y en esta, parte de la
				 * respuesta solo llenar los campos que la TAB Detalle evento de
				 * la pantalla atencionSolicitudAudienciaNotificador.jsp
				 */

				log.info("antes del delegate:::::");
				audienciaDTO = audienciaDelegate.obtenerAudiencia(audienciaDTO);
				log.info("depues del delegate::: eventoDTO" + audienciaDTO);
				converter.alias("eventoDTO", EventoDTO.class);
				String xml = converter.toXML(audienciaDTO);
				escribir(response, xml, null);
				// Se sube a sesion el objeto evento con todos sus atributos
				request.getSession().setAttribute(
						KEY_SESSION_EVENTO + idEvento, audienciaDTO);

			} else if (tipoDeRespuesta.equals(TIPO_RESPUESTA_CERO)) {

				log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA GRID:::::");

				/**
				 * En esta parte de la respuesta se desea enviar la informacion
				 * del grid, correspondiente a la TAB notificaciones de la
				 * pantalla atencionSolicitudAudienciaNotificador.jsp
				 */
				audienciaDTO = (AudienciaDTO) request.getSession()
						.getAttribute(KEY_SESSION_EVENTO + idEvento);

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				writer.print("<rows>");
				writer.print("<records>"
						+ audienciaDTO.getInvolucrados().size() + "</records>");
				for (ObjetoDTO objeto : audienciaDTO.getEvidencias()) {

					log.info("INVOLUCRADO:::" + objeto);
					writer.print("<row id='" + objeto.getElementoId() + "'>");
					writer.print("<cell>" + objeto.getDescripcion() + "</cell>");

					if (objeto.getFechaRecepcion() != null) {
						writer.print("<cell>" + "Si" + "</cell>");
						writer.print("<cell>" + objeto.getFechaRecepcion()
								+ "</cell>");

					} else {
						writer.print("<cell>" + "No" + "</cell>");
						writer.print("<cell>" + "--" + "</cell>");
					}

					writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();

				request.getSession().setAttribute(
						KEY_SESSION_EVENTO + idEvento, audienciaDTO);

			} else {
				log.info("********************************************************************************");
				log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA GRID:::::");
				log.info("ID DE LA SOLICITUD DE LA AUDIENCIA==" + idEvento);
				log.info("********************************************************************************");

				/**
				 * En esta parte de la respuesta se desea enviar la informacion
				 * del grid, correspondiente a la TAB Detalle de solicitud de la
				 * pantalla visorSolicitudAudiencia.jsp
				 */
				List<Long> calidades = new ArrayList<Long>();
				AudienciaDTO audienciaDto = new AudienciaDTO();

				Long audienciaId = NumberUtils.toLong(idEvento, 0L);
				audienciaDto.setId(audienciaId);

				calidades.add(Calidades.VICTIMA_PERSONA.getValorId());
				calidades.add(Calidades.DENUNCIANTE.getValorId());
				calidades.add(Calidades.TESTIGO.getValorId());
				calidades.add(Calidades.PROBABLE_RESPONSABLE_PERSONA
						.getValorId());
				calidades.add(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION
						.getValorId());

				List<InvolucradoDTO> listaInvolucrados = involucradoDelegate
						.obtenerInvolucradosDTOAudienciaPorCalidades(
								audienciaDTO, calidades);
				escribeXmlInvolucradosAudiencia(listaInvolucrados, response);
			}
		} catch (Exception e) {
			log.info("ERROR AL CONSULTAR EL EVENTO ---- consultaDetalleNotificaciones");
			log.info(e.getCause(), e);
			escribir(response, "consultaDetalleNotificaciones", null);

		}
		return null;
	}

	/**
	 * Metodo utilizado para realizar el paso del parametro id del evento a la
	 * JSP de atencionSolicitudAudienciaNotificador
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward visorAtnAlPublicoSolicitudes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando action visorAtnAlPublicoSolicitudes");

			String idEvento = request.getParameter("idEvento");
			log.info("ID-DEL EVENTO:::" + idEvento);
			request.setAttribute("idEvento", idEvento);

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return mapping.findForward("succes");
	}

	/**
	 * Metodo utilizado para realizar del detalle de eventos
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward detalleSolicitudesPJATP(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO CONSULTA DETALLE SOLICITUDES PJATP");

			// Se obtiene el id del evento a consultar a detalle
			String idDocumento = request.getParameter("idEvento");

			log.info("id del envento::::::::::" + idDocumento);

			SolicitudDTO solicitudDTO = new SolicitudDTO();
			solicitudDTO.setDocumentoId(Long.parseLong(idDocumento));

			SolicitudDTO solicitudDTO2 = solicitudDelegate
					.obtenerDetalleSolicitud(solicitudDTO);
			log.info("depues del delegate::: solicitudDTO" + solicitudDTO2);
			converter.alias("solicitudDTO", SolicitudDTO.class);
			String xml = converter.toXML(solicitudDTO2);
			escribir(response, xml, null);

		} catch (Exception e) {
			log.info("ERROR AL CONSULTAR EL EVENTO ---- consultaDetalleNotificaciones");
			log.info(e.getCause(), e);
			escribir(response, "consultaDetalleNotificaciones", null);

		}
		return null;
	}

	/**
	 * Metodo utilizado para realizar la consulta de eventos por expediente
	 * (SOLO HISTÓRCIO)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultaNuevaSolicitudPJATP(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando Consulta de numero de causa para un unevo registro");

			String numeroExpedienteId = request
					.getParameter("numeroExpedienteId");
			log.info("ID DEL NUMERO DE EXPEDIENTE=" + numeroExpedienteId);

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setNumeroExpedienteId(Long
					.parseLong(numeroExpedienteId));

			List<SolicitudDTO> listaSolicitudes = solicitudDelegate
					.consultarSolicitudesPorExpediente(expedienteDTO);

			log.info("Solicitud" + listaSolicitudes);

			converter.alias("solicitudDTO", SolicitudDTO.class);
			String xml = converter.toXML(listaSolicitudes);
			escribir(response, xml, null);

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}

	/**
	 * Metodo utilizado para realizar la consulta de eventos por expediente
	 * (SOLO HISTÓRCIO)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward registrarSolicitudPJATP(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Boolean esPresentacion = Boolean.FALSE;
	
		try {

			RegistrarSolicitudPJForm forma = (RegistrarSolicitudPJForm) form;
			
			esPresentacion = request.getParameter("esPresentacion") != null ? Boolean.valueOf(request.getParameter("esPresentacion")) : Boolean.FALSE ;
			
			Date fechaHora = new Date();

			// Datos para audiencia

			Date fechaHoraLimite = Calendar.getInstance().getTime();
			if (StringUtils.isNotBlank(forma.getFechaLimite())
					&& StringUtils.isNotBlank(forma.getHoraLimite())) {
				fechaHoraLimite = DateUtils.obtener(forma.getFechaLimite(),
						forma.getHoraLimite());
				log.info("fecha hora limite" + fechaHoraLimite);
			} else {
				fechaHoraLimite = new Date();
				log.info("fecha/hora Limite formateada Y VACIA::::::::."
						+ fechaHoraLimite);
			}

			ValorDTO tipoSolicitudDTO = new ValorDTO();
			tipoSolicitudDTO.setIdCampo(forma.getTipoSolicitud());

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();

			ConfInstitucionDTO confInstitucionDTO = null;

			if (forma.getInstitucionSolicitante() != null
					&& forma.getInstitucionSolicitante() > 0) {
				confInstitucionDTO = new ConfInstitucionDTO();
				confInstitucionDTO.setConfInstitucionId(forma
						.getInstitucionSolicitante());
			}

			if (forma.getNumeroExpedienteId() != null
					&& forma.getNumeroExpedienteId() > 0) {
				expedienteDTO.setNumeroExpedienteId(forma
						.getNumeroExpedienteId());
				expedienteDTO.setNumeroExpediente(forma.getNumeroCausa());
				setExpedienteTrabajo(request, expedienteDTO);
			}
			// verificamos el tipo de solicitud
			if (forma.getTipoSolicitud().longValue() != TiposSolicitudes.AUDIENCIA
					.getValorId().longValue() 
					&& forma.getTipoSolicitud().longValue() != TiposSolicitudes.CUMPLIMIENTO_DE_ORDEN_DE_APREHENSION
					.getValorId().longValue() ){
				log.info("ENTRA A REGISTRAR SOLICITUD:::____:::_______::::_____:::_____");

				SolicitudTranscripcionAudienciaDTO solicitudDTO = new SolicitudTranscripcionAudienciaDTO();
				solicitudDTO.setTipoSolicitudDTO(tipoSolicitudDTO);
				solicitudDTO.setExpedienteDTO(expedienteDTO);
				solicitudDTO.setFechaCreacion(fechaHora);
				solicitudDTO.setInstitucion(confInstitucionDTO);
				solicitudDTO.setNombreSolicitante(forma.getNombreSolicitante());
				solicitudDTO.setEstatus(new ValorDTO(EstatusSolicitud.ABIERTA
						.getValorId()));
				solicitudDTO.setFormaDTO(new FormaDTO(Formas.ACUSE_RECIBO
						.getValorId()));
				solicitudDTO.setAudiencia(new AudienciaDTO(NumberUtils
						.toLong(forma.getAudienciaId())));
				solicitudDTO.setUsuario(getUsuarioFirmado(request));
				solicitudDTO.setUsuarioSolicitante(getUsuarioFirmado(request)
						.getFuncionario());

				solicitudDTO = solicitudDelegate
						.registrarSolicitudTranscripcionAudiencia(solicitudDTO);

				if (forma.getArchivoAdjunto() != null
						&& StringUtils.isNotBlank(forma.getArchivoAdjunto()
								.getFileName())) {
					ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();
					adjunto.setContenido(forma.getArchivoAdjunto()
							.getFileData());
					adjunto.setNombreArchivo(forma.getArchivoAdjunto()
							.getFileName());
					if (StringUtils.isNotBlank(forma.getArchivoAdjunto()
							.getFileName())) {
						String[] nombres = forma.getArchivoAdjunto()
								.getFileName().split("\\.");
						if (nombres != null && nombres.length > 0) {
							adjunto.setTipoArchivo("."
									+ nombres[nombres.length - 1]);
							adjunto.setNombreArchivo(nombres[0]);
						} else {
							adjunto.setTipoArchivo(StringUtils.EMPTY);
						}
					}

					adjunto.setUsuario(getUsuarioFirmado(request));
					solicitudDelegate.adjuntarArchivoASolicitudBasico(
							solicitudDTO, adjunto);

				}
				request.setAttribute("solicitudNueva",
						solicitudDTO.getDocumentoId());

			} else {
				log.info("ENTRA A REGISTRAR SOLICITUD  DE AUDIENCIA:::____:::_______::::_____:::_____");
				SolicitudAudienciaDTO solicitudAudiencia = new SolicitudAudienciaDTO();
				solicitudAudiencia.setAudiencia(new AudienciaDTO());
				solicitudAudiencia.getAudiencia().setEstatusAudiencia(
						new ValorDTO(EstatusAudiencia.SOLICITADA.getValorId()));
				solicitudAudiencia.getAudiencia().setTipoAudiencia(
						new ValorDTO(parseLong(forma.getTipoAudiencia())));
				solicitudAudiencia.setMotivo(forma.getMotivoSolicitud());
				solicitudAudiencia.getAudiencia().setInvolucrados(
						new ArrayList<InvolucradoDTO>());
				if (StringUtils.isNotBlank(forma.getInvolucradosAudiencia())) {
					String[] involucradosId = forma.getInvolucradosAudiencia()
							.split(",");
					if (involucradosId != null) {
						for (String involucradoId : involucradosId) {
							solicitudAudiencia
									.getAudiencia()
									.getInvolucrados()
									.add(new InvolucradoDTO(NumberUtils
											.toLong(involucradoId)));
						}
					}
				}

				solicitudAudiencia.getAudiencia().setFechaSolicitud(fechaHora);
				solicitudAudiencia.getAudiencia().setExpediente(expedienteDTO);
				solicitudAudiencia.getAudiencia().setTocaOrCarpeta(
						expedienteDTO);
				if (forma.getNumeroExpedienteId() != null
						&& forma.getNumeroExpedienteId() > 0) {
					expedienteDTO.setNumeroExpedienteId(forma
							.getNumeroExpedienteId());
					solicitudAudiencia.setExpedienteDTO(expedienteDTO);
				} else {
					solicitudAudiencia.setExpedienteDTO(null);
				}
				solicitudAudiencia.setFechaLimite(fechaHoraLimite);
				solicitudAudiencia.setFechaCreacion(fechaHora);
				solicitudAudiencia.setUsuario(getUsuarioFirmado(request));
				solicitudAudiencia.setInstitucion(confInstitucionDTO);
				solicitudAudiencia.setNombreSolicitante(forma
						.getNombreSolicitante());
				solicitudAudiencia.setEstatus(new ValorDTO(
						EstatusSolicitud.ABIERTA.getValorId()));
				ValorDTO tipoSolicitudAudiencia = new ValorDTO();
				tipoSolicitudAudiencia.setIdCampo(forma.getTipoSolicitud());
				solicitudAudiencia.setTipoSolicitudDTO(tipoSolicitudAudiencia);
				solicitudAudiencia.getAudiencia().setFechaSolicitud(
						solicitudAudiencia.getFechaCreacion());
				solicitudAudiencia.setFormaDTO(new FormaDTO(Formas.ACUSE_RECIBO
						.getValorId()));
				solicitudAudiencia = solicitudDelegate
						.registrarSolicitudAudiencia(solicitudAudiencia);

				if (forma.getArchivoAdjunto() != null
						&& StringUtils.isNotBlank(forma.getArchivoAdjunto()
								.getFileName())) {
					ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();
					adjunto.setContenido(forma.getArchivoAdjunto()
							.getFileData());
					adjunto.setNombreArchivo(forma.getArchivoAdjunto()
							.getFileName());
					if (StringUtils.isNotBlank(forma.getArchivoAdjunto()
							.getFileName())) {
						String[] nombres = forma.getArchivoAdjunto()
								.getFileName().split("\\.");
						if (nombres != null && nombres.length > 0) {
							adjunto.setTipoArchivo("."
									+ nombres[nombres.length - 1]);
							adjunto.setNombreArchivo(nombres[0]);
						} else {
							adjunto.setTipoArchivo(StringUtils.EMPTY);
						}
					}

					adjunto.setUsuario(getUsuarioFirmado(request));
					solicitudDelegate.adjuntarArchivoASolicitudBasico(
							solicitudAudiencia, adjunto);
					
					
				}

				if (esPresentacion){
					OrdenDeAprehensionDTO ordenDeAprehensionDTO = new OrdenDeAprehensionDTO();
					ordenDeAprehensionDTO.setOrdenDeAprehensionId(forma.getOrdenDeAprehensionId());
					List<OrdenDeAprehensionDTO> lstAprehensionDTO = ordenDeAprehensionDelegate
					.consultarOrdenDeAprehension(ordenDeAprehensionDTO);
					
					if(lstAprehensionDTO != null && !lstAprehensionDTO.isEmpty()){
						ordenDeAprehensionDTO = lstAprehensionDTO.get(0);
						ordenDeAprehensionDTO.setEsCumplida(Boolean.TRUE);
						ordenDeAprehensionDTO.setSePresentaVoluntariamente(forma.getEsVoluntario());
						ordenDeAprehensionDelegate.ordenDeAprehendionCumplida(ordenDeAprehensionDTO);
					}
				}
				
				log.info("guarda con exito solicitud de audiencia"
						+ solicitudAudiencia);
				request.setAttribute("solicitudNueva",
						solicitudAudiencia.getDocumentoId());

			}
			
			request.setAttribute("numeroExpediente", forma.getNumeroCausa());

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		if (esPresentacion){
			return mapping.findForward("esPresentacion");
		}else {
			return mapping.findForward("sucess");
		}
	}

	/**
	 * Metodo utilizado para realizar la consulta de eventos por expediente
	 * (SOLO HISTÓRCIO)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward buscarAudienciaPorCausaOTOCA(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando buscar Audiencia Por Causa o TOCA");

			String noTOCA = request.getParameter("noTOCA");
			log.info("noTOCA=" + noTOCA);

			String noCausa = request.getParameter("noCausa");
			log.info("noCausa=" + noCausa);

			FiltroAudienciaDTO filtro = new FiltroAudienciaDTO();

			if (noTOCA != null && !noTOCA.isEmpty()) {

				// filtro.setNumeroExpedienteId(Long.parseLong(noTOCA));
				filtro.setCausa(false);
				filtro.setNumeroExpediente(noTOCA);

			} else {

				filtro.setNumeroExpediente(noCausa);
				filtro.setCausa(true);

			}

			List<AudienciaDTO> listAudienciaDTOs = audienciaDelegate
					.buscarAudiencias(filtro);

			log.info("Solicitud" + listAudienciaDTOs);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			int lTotalRegistros = listAudienciaDTOs.size();

			writer.print("<records>" + lTotalRegistros + "</records>");
			for (AudienciaDTO audienciaDTO : listAudienciaDTOs) {

				log.info("Solicitud" + audienciaDTO);

				writer.print("<row id='" + audienciaDTO.getId() + "'>");
				writer.print("<cell>"
						+ audienciaDTO.getExpediente().getNumeroExpediente()
						+ "</cell>");
				writer.print("<cell>"
						+ audienciaDTO.getTipoAudiencia().getValor()
						+ "</cell>");
				writer.print("<cell>" + audienciaDTO.getStrFechaEvento() + "-"
						+ audienciaDTO.getStrHoraEvento() + "</cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}

	/**
	 * Metodo utilizado para realizar la consulta de eventos por expediente
	 * (SOLO HISTÓRCIO)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward adjuntarArchivo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO REGISTRAR SOLICITUD DE RECURSO PJATP");

			ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();
			RegistrarSolicitudPJForm solicitudRecurso = (RegistrarSolicitudPJForm) form;

			log.info("DESPUES DE LA FORMA");

			String formaId = solicitudRecurso.getFormaId();
			log.info("FORMA ID:::" + formaId);
			request.setAttribute("formaId", formaId);

			FormFile archivo = solicitudRecurso.getArchivoAdjunto();
			String contentType = archivo.getContentType();
			String fileName = archivo.getFileName();
			byte[] fileData = archivo.getFileData();

			Date fechaHora = DateUtils.obtener(
					solicitudRecurso.getFechaSolicitud(),
					solicitudRecurso.getHoraSolicitud());

			adjunto.setContenido(fileData);
			adjunto.setNombreArchivo(fileName);
			adjunto.setTipoArchivo(contentType);
			adjunto.setUsuario(super.getUsuarioFirmado(request));

			ValorDTO tipoSolicitudDTO = new ValorDTO();
			SolicitudDTO solicitudDTO = new SolicitudDTO();
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			ConfInstitucionDTO confInstitucionDTO = null;

			if (solicitudRecurso.getInstitucionSolicitante() != null
					&& solicitudRecurso.getInstitucionSolicitante() > 0) {
				confInstitucionDTO = new ConfInstitucionDTO();
				confInstitucionDTO.setConfInstitucionId(solicitudRecurso
						.getInstitucionSolicitante());
			}

			expedienteDTO.setNumeroExpedienteId(solicitudRecurso
					.getNumeroExpedienteId());
			tipoSolicitudDTO.setIdCampo(solicitudRecurso.getTipoSolicitud());
			solicitudDTO.setFechaCreacion(fechaHora);
			solicitudDTO.setTipoSolicitudDTO(tipoSolicitudDTO);
			solicitudDTO.setExpedienteDTO(expedienteDTO);
			solicitudDTO.setInstitucion(confInstitucionDTO);
			solicitudDTO.setNombreSolicitante(solicitudRecurso
					.getNombreSolicitante());
			solicitudDTO.setEstatus(new ValorDTO(EstatusSolicitud.ABIERTA
					.getValorId()));

			solicitudDelegate.adjuntarArchivoASolicitud(solicitudDTO, adjunto);

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return mapping.findForward("succes");
	}

	/**
	 * Realiza la consulta de datos para llenar un grid de involucrados y
	 * víctimas relacionados a un número de expediente
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarInvolucradosVictimasYPRPorNumeroExp(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

	try{
		String limpiaGrid = request.getParameter("limpiar");
		/*bandera usada para las sentencias checar involucrados vs sentenciados*/
		String sentencia = request.getParameter("sentencia");
		
		log.info("Valor de Sentencia en consultarInvolucradosVictimasYPRPorNumeroExp :: "+sentencia);

		if (limpiaGrid != null && limpiaGrid.equalsIgnoreCase("true")) {

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			writer.print("<records>" + 0 + "</records>");
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} else {
					
			Calidades[] calidades = null;
			
			if(sentencia != null  && !sentencia.equals("")){
			
				if(sentencia.equals("true")){
					esSentencia=true;
					 
					calidades = new Calidades[] {
							Calidades.PROBABLE_RESPONSABLE_PERSONA,
							Calidades.PROBABLE_RESPONSABLE_ORGANIZACION,
							};	
					
				}
				
				
			}else{
			
				calidades = new Calidades[] {
						Calidades.VICTIMA_PERSONA,
						Calidades.PROBABLE_RESPONSABLE_PERSONA,
						Calidades.PROBABLE_RESPONSABLE_ORGANIZACION,
						Calidades.DENUNCIANTE };
			}
			
			// List<InvolucradoDTO> involucrados = involucradoDelegate.
			// obtenerInvolucradosByCasoYCalidades(request.getParameter("numeroCaso"),
			// calidades);
			
			
			List<InvolucradoDTO> involucrados = null;
			
			//if(esSentencia){
			//	involucrados = involucradoDelegate.consultarInvolucradoPorCalidad(calidades);
			//}else{
				Long expedienteId = NumberUtils.toLong(
						request.getParameter("expedienteId"), 0L);
				
					involucrados = involucradoDelegate
					.obtenerInvolucradosPorExpedienteYCalidades(request.getParameter("numExpediente"),calidades, null, expedienteId);
			//}
			List<InvolucradoDTO> invTmp = new ArrayList<InvolucradoDTO>();
			// quitar a los denunciates que no son victimas
			for (InvolucradoDTO inv : involucrados) {
				if (inv.getCalidadDTO().getCalidades().getValorId()
						.longValue() == Calidades.DENUNCIANTE.getValorId()
						.longValue()) {
					if (inv.isVictima()) {
						invTmp.add(inv);
					}
				} else {
					invTmp.add(inv);
				}
			}


			if(esSentencia){
				
				List<SentenciaDTO> listaSentenciados;
				List<InvolucradoDTO> invtmp=new ArrayList<InvolucradoDTO>();
				
				for ( InvolucradoDTO invol : involucrados) {
					
					listaSentenciados=sentenciaDelegate.consultarNUS(invol);	
					if(!listaSentenciados.isEmpty()){
						//involucrados.remove(invol);
						invtmp.add(invol);
					}
				}
				
				
				for (InvolucradoDTO involucradoDTO : invtmp) {
					involucrados.remove(involucradoDTO);
					
				}
					
			}	
			//}
			
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			writer.print("<records>" + involucrados.size() + "</records>");
			for (InvolucradoDTO inv : involucrados) {

				writer.print("<row id='" + inv.getElementoId() + "'>");
				writer.print("<cell>"
						+ (inv.getNombreCompleto() != null ? inv
								.getNombreCompleto() : "") + "</cell>");
				writer.print("<cell>"
						+ inv.getCalidadDTO().getValorIdCalidad()
								.getValor() + "</cell>");

				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		}

	} catch (Exception e) {
		log.info(e.getCause(), e);

	}
	return null;
//		try {
//
//			String limpiaGrid = request.getParameter("limpiar");
//
//			if (limpiaGrid != null && limpiaGrid.equalsIgnoreCase("true")) {
//
//				response.setContentType("text/xml; charset=UTF-8");
//				response.setHeader("Cache-Control", "no-cache");
//				PrintWriter writer = response.getWriter();
//
//				writer.print("<rows>");
//				writer.print("<records>" + 0 + "</records>");
//				writer.print("</rows>");
//				writer.flush();
//				writer.close();
//
//			} else {
//
//				Calidades[] calidades = new Calidades[] {
//						Calidades.VICTIMA_PERSONA,
//						Calidades.PROBABLE_RESPONSABLE_PERSONA,
//						Calidades.PROBABLE_RESPONSABLE_ORGANIZACION,
//						Calidades.DENUNCIANTE };
//
//				// List<InvolucradoDTO> involucrados = involucradoDelegate.
//				// obtenerInvolucradosByCasoYCalidades(request.getParameter("numeroCaso"),
//				// calidades);
//				Long expedienteId = NumberUtils.toLong(
//						request.getParameter("expedienteId"), 0L);
//				List<InvolucradoDTO> involucrados = involucradoDelegate
//						.obtenerInvolucradosPorExpedienteYCalidades(
//								request.getParameter("numExpediente"),
//								calidades, null, expedienteId);
//				List<InvolucradoDTO> invTmp = new ArrayList<InvolucradoDTO>();
//				// quitar a los denunciates que no son victimas
//				for (InvolucradoDTO inv : involucrados) {
//					if (inv.getCalidadDTO().getCalidades().getValorId()
//							.longValue() == Calidades.DENUNCIANTE.getValorId()
//							.longValue()) {
//						if (inv.isVictima()) {
//							invTmp.add(inv);
//						}
//					} else {
//						invTmp.add(inv);
//					}
//				}
//
//				response.setContentType("text/xml; charset=UTF-8");
//				response.setHeader("Cache-Control", "no-cache");
//				PrintWriter writer = response.getWriter();
//
//				writer.print("<rows>");
//
//				writer.print("<records>" + involucrados.size() + "</records>");
//				for (InvolucradoDTO inv : involucrados) {
//
//					writer.print("<row id='" + inv.getElementoId() + "'>");
//					writer.print("<cell>"
//							+ (inv.getNombreCompleto() != null ? inv
//									.getNombreCompleto() : "") + "</cell>");
//					writer.print("<cell>"
//							+ inv.getCalidadDTO().getValorIdCalidad()
//									.getValor() + "</cell>");
//
//					writer.print("</row>");
//				}
//				writer.print("</rows>");
//				writer.flush();
//				writer.close();
//			}
//
//		} catch (Exception e) {
//			log.info(e.getCause(), e);
//
//		}
//		return null;
	}

	/**
	 * Consulta las audiencias finalizadas de un tipo de causa en específico
	 * para llenar el grid donde el usuario selecciona la audiencia a la que se
	 * refiere al dar de alta una solicitud de transcripción, audio/video,
	 * casasión, apelación
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarAudienciasPorNumeroCausa(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			String limpiaGrid = request.getParameter("limpiar");

			if (limpiaGrid != null && limpiaGrid.equalsIgnoreCase("true")) {

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");
				writer.print("<records>" + 0 + "</records>");
				writer.print("</rows>");
				writer.flush();
				writer.close();

			} else {
				Long numeroExpedienteId = NumberUtils.toLong(request
						.getParameter("numeroExpedienteId"));
				ExpedienteDTO exp = new ExpedienteDTO();
				exp.setNumeroExpedienteId(numeroExpedienteId);
				List<AudienciaDTO> audiencias = new ArrayList<AudienciaDTO>();

				audiencias = audienciaDelegate
						.consultarAudienciaByNumeroExpedienteYEstatus(exp,
								EstatusAudiencia.FINALIZADA);

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");
				writer.print("<records>" + audiencias.size() + "</records>");
				for (AudienciaDTO aud : audiencias) {
					writer.print("<row id='" + aud.getId() + "'>");
					writer.print("<cell>"
							+ DateUtils.formatear(aud.getFechaEvento())
							+ "</cell>");
					writer.print("<cell>"
							+ DateUtils.formatearHora(aud.getFechaEvento())
							+ "</cell>");
					writer.print("<cell>" + aud.getTipoAudiencia().getValor()
							+ "</cell>");
					writer.print("<cell>" + aud.getSala().getNombreSala()
							+ "</cell>");

					writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();

			}

		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Consulta las audiencias de diferentes estatus de una causa
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarAudienciasPorNumeroCausaYEstatus(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			String limpiaGrid = request.getParameter("limpiar");

			if (limpiaGrid != null && limpiaGrid.equalsIgnoreCase("true")) {

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");
				writer.print("<records>" + 0 + "</records>");
				writer.print("</rows>");
				writer.flush();
				writer.close();

			} else {
				Long numeroExpedienteId = NumberUtils.toLong(request
						.getParameter("numeroExpedienteId"));
				ExpedienteDTO exp = new ExpedienteDTO();
				exp.setNumeroExpedienteId(numeroExpedienteId);
				List<AudienciaDTO> audiencias = new ArrayList<AudienciaDTO>();
				// List<AudienciaDTO> audienciasEstatus = new
				// ArrayList<AudienciaDTO>();

				audiencias = audienciaDelegate
						.consultarAudienciaByNumeroExpedienteYListaEstatus(exp,
								new EstatusAudiencia[] {
										EstatusAudiencia.FINALIZADA,
										EstatusAudiencia.EN_PROCESO,
										EstatusAudiencia.PROGRAMADA,
										EstatusAudiencia.CANCELADA,
										EstatusAudiencia.REPROGRAMADA });

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");

				final PaginacionDTO pag = PaginacionThreadHolder.get();

				log.debug("pag :: " + pag);
				if (pag != null && pag.getTotalRegistros() != null) {
					writer.print("<total>" + pag.getTotalPaginas() + "</total>");
					writer.print("<records>" + pag.getTotalRegistros()
							+ "</records>");
				} else {
					writer.print("<total>0</total>");
					writer.print("<records>0</records>");
				}

				for (AudienciaDTO aud : audiencias) {
					writer.print("<row id='" + aud.getId() + "'>");
					writer.print("<cell>"
							+ DateUtils.formatear(aud.getFechaEvento())
							+ "</cell>");
					writer.print("<cell>"
							+ DateUtils.formatearHora(aud.getFechaEvento())
							+ "</cell>");
					writer.print("<cell>" + aud.getTipoAudiencia().getValor()
							+ "</cell>");
					if (aud.getSala() != null
							&& aud.getSala().getNombreSala() != null) {
						writer.print("<cell>" + aud.getSala().getNombreSala()
								+ "</cell>");
					} else {
						if (aud.getSalaTemporal() != null
								&& aud.getSalaTemporal().getSalaTemporalId() != null) {
							writer.print("<cell>" + "Temporal" + "</cell>");
						} else {
							writer.print("<cell>" + "Sin sala" + "</cell>");
						}
					}
					writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();

			}

		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Realiza la consulta de datos para llenar un grid de involucrados con
	 * orden de Aprehensi&oacute;n vigente
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarOrdenesDeAprehension(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			OrdenDeAprehensionDTO filtro = new OrdenDeAprehensionDTO();
			filtro.setEsCumplida(Boolean.FALSE);
			
			Boolean validarArchivoDigital = request.getParameter("validarArchivoDigital")!= null 
			? Boolean.parseBoolean(request.getParameter("validarArchivoDigital"))
					: Boolean.FALSE;
			filtro.setValidarDocumentoDigital(validarArchivoDigital);

			List<OrdenDeAprehensionDTO> lstOrdenDeAprehensionDTO = ordenDeAprehensionDelegate
					.consultarOrdenDeAprehension(filtro);

			
				
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros()
						+ "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}
			for (OrdenDeAprehensionDTO ordenDeAprehensionDTO : lstOrdenDeAprehensionDTO) {
					if (ordenDeAprehensionDTO.getNumeroExpedienteDTO() != null
							&& ordenDeAprehensionDTO.getInvolucradoDTO() != null
							&& ordenDeAprehensionDTO.getMandamientoDTO() != null) {
						
						ExpedienteDTO numeroExpedienteDTO = ordenDeAprehensionDTO.getNumeroExpedienteDTO();
						InvolucradoDTO involucradoDTO = ordenDeAprehensionDTO.getInvolucradoDTO();
						MandamientoDTO mandamientoDTO = ordenDeAprehensionDTO.getMandamientoDTO();
						
						writer.print("<row id='" + involucradoDTO.getElementoId() + "'>");
						
						writer.print("<cell>"
								+ numeroExpedienteDTO.getNumeroExpedienteId()
								+ "</cell>");
						
						writer.print("<cell>"
								+ numeroExpedienteDTO.getNumeroExpediente()
								+ "</cell>");
						
						writer.print("<cell>"
								+ (involucradoDTO.getNombreCompleto() != null ? involucradoDTO
										.getNombreCompleto() : "") + "</cell>");

						writer.print("<cell>"
								+ "<![CDATA[<div class='celdaGrid'>" 
								+ "<input type='button' " +
										" id='consultaPDF"+mandamientoDTO.getDocumentoId()+"'" +
										" value='Ver Documento' " +
										" onclick='consultaPDF("+ mandamientoDTO.getDocumentoId()+");' " +
										" class='btn_Generico' />"
								+ "</div>]]>"
								+ "</cell>");
						writer.print("<cell>"
								+ ordenDeAprehensionDTO.getOrdenDeAprehensionId()
								+ "</cell>");

						writer.print("</row>");

				}
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}

}
