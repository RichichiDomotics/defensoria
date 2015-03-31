package mx.gob.segob.nsjp.web.MedidasCautelares.Action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusMedida;
import mx.gob.segob.nsjp.comun.enums.documento.Periodicidad;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.delegate.agenda.AgendaDelegate;
import mx.gob.segob.nsjp.delegate.compromiso.CompromisoDelegate;
import mx.gob.segob.nsjp.delegate.configuracion.ConfiguracionDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.medidasalternas.MedidasAlternasDelegate;
import mx.gob.segob.nsjp.delegate.medidascautelares.MedidasCautelaresDelegate;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.medida.CompromisoPeriodicoDTO;
import mx.gob.segob.nsjp.dto.medida.FechaCompromisoDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaAlternaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.service.medida.RegistrarMedidaCautelarService;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class MedidasCautelaresAction extends GenericAction{
	
	/** Log de clase */
	private static final Logger log = Logger.getLogger(MedidasCautelaresAction.class);
	
	private MedidasAlternasDelegate medidasAlternasDelegate;
	
	@Autowired
	private MedidasCautelaresDelegate medidasCautelaresDelegate;
	
	@Autowired
	private AgendaDelegate agendaDelegate;
	
	@Autowired
	private CompromisoDelegate compromisoDelegate;
	
	@Autowired
	private ConfiguracionDelegate configuracionDelegate;
	
	@Autowired
	private DocumentoDelegate documentoDelegate;
	
	@Autowired
	private ExpedienteDelegate expedienteDelegate;

	@Autowired
	private RegistrarMedidaCautelarService _CautelarService;
	/**
	 * Método para obtener las solicitar programarAudienciasCarpetaPreliberacion
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward consultaGridMedidasCautelaresAlternativas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		log.info("consultaGridMedidasCautelaresAlternativas");
		log.info("Action de Consultar beneficios datos:::::");
		String numeroExpedienteId = request.getParameter("numeroExpedienteId");
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		expedienteDTO.setNumeroExpedienteId(Long.parseLong(numeroExpedienteId));
		
		try {
			List<MedidaAlternaDTO> medidasAlternas = medidasAlternasDelegate.consultarMedidasAlternasPorNumeroExpediente(expedienteDTO);
			
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
            
//				Número de causa
//				- Número de carpeta de ejecución
//				- Nombre
//				- Descripcion
//				- Periodicidad
				for (MedidaAlternaDTO medidaAlterna : medidasAlternas) {
					log.info("/******** ::::: /" + medidaAlterna);
					writer.print("<row id='" + medidaAlterna.getDocumentoId() +"'>");
					writer.print("<cell>"+medidaAlterna.getInvolucrado().getNombreCompleto() + "</cell>");
					writer.print("<cell>"+medidaAlterna.getNombreDocumento() + "</cell>");
					writer.print("<cell>"+medidaAlterna.getDescripcionMedida() + "</cell>");	
					writer.print("<cell>"+medidaAlterna.getValorPeriodicidad().getValor() + "</cell>");
					writer.print("<cell>"+medidaAlterna.getCompromisoPeriodico() + "</cell>");	
					writer.print("<cell>"+medidaAlterna.getSeguimiento() + "</cell>");
					
					writer.print("</row>");
				} 
									
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (NSJPNegocioException e) {
			log.error(e);
		}

		
		return null;
		
	}

	/**
	 * Método para obtener las Medidas Cautelares
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward consultaGridMedidasCautelares(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		log.info("Action de Consultar Medidas Cautelares:::::");
		Long estatus = new Long(request.getParameter("estatus"));
		log.debug("estatus ... " + estatus);
		MedidaCautelarDTO medidaCautelarDTO = new MedidaCautelarDTO();
		ValorDTO valorDTO = new ValorDTO();
		valorDTO.setIdCampo(estatus);
		medidaCautelarDTO.setEstatus(valorDTO);
		
		try {
			List<MedidaCautelarDTO> medidasCautelaresDTOs = medidasCautelaresDelegate.consultaMedidasCautelaresPorEstatus(medidaCautelarDTO);
			
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
				for (MedidaCautelarDTO medidacautelarDTO : medidasCautelaresDTOs) {
					log.info("/******** ::::: /" + medidacautelarDTO);
					writer.print("<row id='" + medidacautelarDTO.getDocumentoId() +"'>");
					writer.print("<cell>"+medidacautelarDTO.getInvolucrado().getNombreCompleto()+"</cell>");
					if(medidacautelarDTO.getExpedienteDTO()!=null)
					{
						writer.print("<cell>"+medidacautelarDTO.getExpedienteDTO().getNumeroExpediente()+"</cell>");
					}
					else
					{
						writer.print("<cell>---</cell>");
					}
					if(medidacautelarDTO.getNumeroCaso()!=null)
					{
						writer.print("<cell>"+medidacautelarDTO.getNumeroCaso()+"</cell>");
					}
					else
					{
						writer.print("<cell>---</cell>");
					}
					if(medidacautelarDTO.getNumeroCausa()!=null)
					{
						writer.print("<cell>"+medidacautelarDTO.getNumeroCausa() + "</cell>");
					}
					else
					{
						writer.print("<cell>---</cell>");
					}
					//writer.print("<cell>"+medidacautelarDTO.getNumeroCarpetaEjecucion() + "</cell>");
					writer.print("</row>");
				} 
									
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (NSJPNegocioException e) {
			log.error(e);
		}
		
		return null;		
	}

	/**
	 * Método para obtener los Expedientes por Medidas Cautelares en función de un filtro
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward consultaMedidasCautelaresGenerico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		String inicio 			= request.getParameter("inicio");
		String fin    			= request.getParameter("fin");
		String numeroExpediente = request.getParameter("numeroExpediente");
		Long  estatusMedida = NumberUtils.toLong(request.getParameter("estatusMedida"),0L);

		
		if(numeroExpediente!=null && numeroExpediente==""){
			numeroExpediente=null;
		}
		
		DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicio = null;
		Date fechaFin = null;
		
		if(inicio != null && !inicio.equals("")){
			try {
				fechaInicio = formato.parse(inicio);						
			} catch (ParseException e) {				
				fechaInicio = null;
			}	
		}
		if(fin != null && !fin.equals("")){
			try {
				fechaFin = formato.parse(fin);						
			} catch (ParseException e) {				
				fechaFin = null;
			}	
		}				
				
		try {

			MedidaCautelarDTO medidaCautelarDTO = new MedidaCautelarDTO();
		
			
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(
					GenericAction.KEY_SESSION_USUARIO_FIRMADO);
			medidaCautelarDTO.setFuncionario(usuarioDTO.getFuncionario());			
			
			medidaCautelarDTO.setFechaFin(fechaFin);
			medidaCautelarDTO.setFechaInicio(fechaInicio);
			//número expediante := número de causa en PG
			medidaCautelarDTO.setNumeroCausa(numeroExpediente);
			
			if(estatusMedida != 0L)
				medidaCautelarDTO.setEstatus(new ValorDTO(estatusMedida));
			
			
			List<MedidaCautelarDTO> listaMedidas = medidasCautelaresDelegate.obtenerMedidasCautelaresPorFiltro(medidaCautelarDTO);
				
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<rows>");
			
			final PaginacionDTO pag = PaginacionThreadHolder.get();
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros() + "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}
			
			for (MedidaCautelarDTO medida : listaMedidas) {
			
				writer.print("<row id='"+ medida.getInvolucrado().getElementoId()+","+medida.getDocumentoId() + "'>");
				
			    	writer.print("<cell>" + medida.getInvolucrado().getNombreCompleto() + "</cell>");
			    	
			    	writer.print("<cell>" +  (medida!=null?medida.getValorTipoMedida().getValor():"") + "</cell>");
			    	
			    	writer.print("<cell>" + (medida!=null && medida.getFechaCreacion() != null ? DateUtils.formatear(medida.getFechaCreacion()): "-") + "</cell>");
			    	writer.print("<cell>" + (medida!=null && medida.getFechaInicio() != null ? DateUtils.formatear(medida.getFechaInicio()): "-") + "</cell>");			    	
			    	writer.print("<cell>" + (medida!=null && medida.getFechaFin() != null ? DateUtils.formatear(medida.getFechaFin()): "-") + "</cell>");
			    	
			    	writer.print("<cell>" +  ( (medida!=null && medida.getValorPeriodicidad()!=null && 
			    			medida.getValorPeriodicidad().getValor()!=null)? medida.getValorPeriodicidad().getValor() :"") + "</cell>");
			    	
			    	writer.print("<cell>" +  (medida!=null?medida.getDescripcionMedida():"") + "</cell>");
			    	
			    	writer.print("<cell>" +  ( (medida!=null && medida.getEstatus()!=null)? medida.getEstatus().getValor():"") + "</cell>");
			    	
			    	writer.print("<cell>" +  (medida!=null ? medida.getNumeroCausa():"") + "</cell>");


		    	writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (NSJPNegocioException e) {
			log.error(e);
		}
		
		return null;		
	}
	
	/**
	 * Método para obtener las Medidas Cautelares
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward consultaGridMedidasCautelaresIncumplidas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, NSJPNegocioException {
		
		log.info("Action consultaGridMedidasCautelaresIncumplidas:::::");
		
		Calendar fechaActual =Calendar.getInstance();
		fechaActual.setTime(new Date());
		Date fecha = fechaActual.getTime();
		fecha =null;
		Long[] idES=  
				{EstatusMedida.NO_ATENDIDA.getValorId(), 
				EstatusMedida.EN_PROCESO.getValorId()};
		List<Long> estatusId = Arrays.asList(idES);
		
		List<MedidaCautelarDTO> medidasCautelaresDTOs = medidasCautelaresDelegate.obtenerMedidasCautelaresPorFiltro(fecha, estatusId);
//			List<MedidaCautelarDTO> medidasCautelaresDTOs = medidasCautelaresDelegate.obtenerMedidasCautelaresPorFechaIncumplientoDelDiaAnterior();
		
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();
		writer.print("<rows>");
			writer.print("<records>" + medidasCautelaresDTOs.size() + "</records>");
			for (MedidaCautelarDTO medidacautelarDTO : medidasCautelaresDTOs) {
				log.info("/******** ::::: /" + medidacautelarDTO);
				writer.print("<row id='" + medidacautelarDTO.getDocumentoId() +"'>");
				writer.print("<cell>"+medidacautelarDTO.getInvolucrado().getNombreCompleto()+"</cell>");
				if(medidacautelarDTO.getExpedienteDTO()!=null)
				{
					writer.print("<cell>"+medidacautelarDTO.getExpedienteDTO().getNumeroExpediente()+"</cell>");
				}
				else
				{
					writer.print("<cell>---</cell>");
				}
				if(medidacautelarDTO.getNumeroCaso()!=null)
				{
					writer.print("<cell>"+medidacautelarDTO.getNumeroCaso()+"</cell>");
				}
				else
				{
					writer.print("<cell>---</cell>");
				}
				if(medidacautelarDTO.getNumeroCausa()!=null)
				{
					writer.print("<cell>"+medidacautelarDTO.getNumeroCausa() + "</cell>");
				}
				else
				{
					writer.print("<cell>---</cell>");
				}
				if(medidacautelarDTO.getValorTipoMedida()!= null && medidacautelarDTO.getValorTipoMedida().getValor()!= null){
					writer.print("<cell>"+medidacautelarDTO.getValorTipoMedida().getValor() + "</cell>");
				}
				else
				{
					writer.print("<cell>---</cell>");
				}
				writer.print("</row>");
			} 
								
		writer.print("</rows>");
		writer.flush();
		writer.close();
		
		return null;		
	}
	
	/**
	 * Método para cambiar el estatus de las Medidas Cautelares
	 * @param medidaCautelarId
	 * @param estatus
	 * @throws NSJPNegocioException
	 */
	public void cambiarEstatusMedida(Long medidaCautelarId, Long estatus)
			throws NSJPNegocioException {
		
		log.info("Método para cambiar estatus de Medida Cautelar:::::");
		
		log.debug("MedidaCautelarId  " + medidaCautelarId + "  Estatus  " + estatus);
		//Cambiar estatus localmente
		MedidaCautelarDTO medidaCautelarDTO = medidasCautelaresDelegate.cambiarEstatusMedida(medidaCautelarId, estatus);			
		
		log.debug("ConfInstitucion   " + medidaCautelarDTO.getConfInstitucion().getConfInstitucionId());
		log.debug("ConfInstitucion   " + medidaCautelarDTO.getConfInstitucion());
		// Se agrega condicion para que no replique la medida cautelar a PGJ si se encuentra en ella.
		if(medidaCautelarDTO!= null && medidaCautelarDTO.getConfInstitucion()!=null && medidaCautelarDTO.getConfInstitucion().getConfInstitucionId() != 1){
			Instituciones institucionDestino = null;
			//Replicar a las instituciones de PGJ
			institucionDestino =Instituciones.PGJ;
			log.info("Institucion:"+ institucionDestino);
			Boolean actualizacionExitosa  = medidasCautelaresDelegate.actualizarEstatusMedidaCautelarInstitucion(medidaCautelarId, institucionDestino);
			log.info("actualizacionExitosa  " + actualizacionExitosa);
		}
	}

	/**
	 * Método para consultar una Medida Cautelar por Id
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward consultarMedidaCautelarPorId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		log.info("Action de consultar Medida Cautelar por id:::::");

		Long medidaCautelarId = new Long(request.getParameter("medidaCautelarId"));
		Long estatusId = new Long(request.getParameter("estatusId"));
		
		log.debug("medidaCautelarId... " + medidaCautelarId);
		log.debug("estatusId ... " + estatusId);
		
		try {
			MedidaCautelarDTO medidaCautelarDTO = new MedidaCautelarDTO();
			medidaCautelarDTO = medidasCautelaresDelegate.consultarMedidasCautelaresPorId(medidaCautelarId);
			
			converter.alias("MedidaCautelarDTO", MedidaCautelarDTO.class);
			String xml = converter.toXML(medidaCautelarDTO);
			log.debug("xml consultarMedidaCautelarPorId() ... " + xml);
			escribir(response, xml,null);
			
		} catch (NSJPNegocioException e) {
			log.error(e);
		}
		
		return null;
	}

	/**
	 * Método para guardar los días de compromiso en la base de datos
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward generarCalendarizacion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		log.info("Action de Generar Calendarizacion:::::");

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Long medidaCautelarId = new Long(request.getParameter("medidaCautelarId"));
		Long periodicidadId = new Long(request.getParameter("periodicidadId"));
		Long usuarioId = getUsuarioFirmado(request).getFuncionario().getClaveFuncionario();
		String strFechaInicio = request.getParameter("fechaInicio");
		String strFechaFin = request.getParameter("fechaFin");
		Long estatus = new Long(request.getParameter("estatus"));
		
		log.debug("medidaCautelarId... " + medidaCautelarId);
		log.debug("estatus... " + estatus);
		log.debug("periodicidadId ... " + periodicidadId);
		log.debug("usuarioId ... " + usuarioId);
		log.debug("fechaInicio ... " + strFechaInicio);
		log.debug("fechaFin ... " + strFechaFin);
		
		try {
			//cambiamos el estatus de la medida cautelar
			if(estatus.equals(EstatusMedida.NO_ATENDIDA.getValorId())){
				log.info("Cambiando estatus a en proceso...changeStatusCuatelar...");
				cambiarEstatusMedida(medidaCautelarId, EstatusMedida.EN_PROCESO.getValorId());
				log.info("Cambie estatus a: en proceso...changeStatusCuatelar...");
			}
			
			Date dFechaInicio = (Date)formatter.parse(strFechaInicio);
			Calendar calFechaInicio = Calendar.getInstance();
			calFechaInicio.setTime(dFechaInicio);
			
			Date dFechaFin = (Date)formatter.parse(strFechaFin);
			Calendar calFechaFin = Calendar.getInstance();
			calFechaFin.setTime(dFechaFin);
			
			agendaDelegate.generarCalendarizacion(calFechaInicio, calFechaFin, Periodicidad.getByValor(periodicidadId), 
												  medidaCautelarId, usuarioId);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.flush();
			writer.close();
			
		} catch (NSJPNegocioException e) {
			log.error(e);
		} catch (ParseException e1) {
			log.error(e1);
		}  
			
		return null;
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
	public ActionForward cambiarEstatusMedidaCautelar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		log.info("::::::::::EJECUTANDO ACTION CAMBIAR ESTATUS DE MEDIDA CAUTELAR::::::::::");
		try {
			Long medidaCautelarId = NumberUtils.toLong(request.getParameter("medidaId"), 0L);
			Long estatusId = NumberUtils.toLong(request.getParameter("estatus"), 0L);
			Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpedienteId"), 0L);
			String numeroExpediente = request.getParameter("numeroExpediente");
			String numeroGeneralCaso = request.getParameter("numeroGeneralCaso");
			
			log.debug(":::::::::::VERIFICANDO PARAMETROS::::::::::::::");
			log.debug("medidaCautelarId....." + medidaCautelarId);
			log.debug("estatus.............." + estatusId);
			log.debug("numeroExpedienteId..." + numeroExpedienteId);
			log.debug("numeroExpediente....." + numeroExpediente);
			log.debug("numeroGeneralCaso...." + numeroGeneralCaso);
			
			cambiarEstatusMedida(medidaCautelarId, estatusId);
			
			if(numeroGeneralCaso != null && !numeroGeneralCaso.trim().isEmpty()){

				DocumentoDTO documento = obtenerDocumentoPorActividadYNumeroExpedienteId(
						numeroExpedienteId,
						numeroExpediente,
						Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR);
				
				if (documento != null && documento.getDocumentoId() != null
						&& documento.getDocumentoId() > 0L) {
					
					List<DocumentoDTO> lstDocumentoDTO = new ArrayList<DocumentoDTO>();
					ActividadDTO actividadDTO = new ActividadDTO();
					
					actividadDTO.setTipoActividad(Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR);
					actividadDTO.setActividadId(ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR.getValorId());
					documento.setActividadDTO(actividadDTO);
					lstDocumentoDTO.add(documento);
//					documentoDelegate.enviarDocumentoAInstitucion(lstDocumentoDTO, numeroGeneralCaso, Instituciones.PGJ);
				}
			
			}
				
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.flush();
			writer.close();
			
		} catch (NSJPNegocioException e) {
			log.error(e);
		}
		return null;
	}
	
	/**
	 * Metodo para obtener el ultimo documento de una expediente con una
	 * actividad
	 * 
	 * @param numeroExpedienteId
	 * @param numeroExpediente
	 * @throws NSJPNegocioException
	 */
	public DocumentoDTO obtenerDocumentoPorActividadYNumeroExpedienteId(Long numeroExpedienteId,
			String numeroExpediente, Actividades tipoActividad)
			throws NSJPNegocioException {
		
		log.info("::::::::Metodo obtenerDocumentoPorActividad");
		DocumentoDTO filtroDTO = new DocumentoDTO();
		ActividadDTO actividadDTO = new ActividadDTO();
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();

		actividadDTO.setTipoActividad(tipoActividad);
		filtroDTO.setActividadDTO(actividadDTO);

		if (numeroExpedienteId != null && numeroExpedienteId > 0L) {
			expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
		} else {
			if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
				expedienteDTO.setNumeroExpediente(numeroExpediente);
				expedienteDTO = expedienteDelegate
						.obtenerNumeroExpedienteByNumExp(expedienteDTO, null);
			}
		}

		if (expedienteDTO != null
				&& expedienteDTO.getNumeroExpedienteId() != null) {
			
			filtroDTO.setExpedienteDTO(expedienteDTO);
			filtroDTO = documentoDelegate.consultarUltimoDocumentoPorActividadYExpedienteId(filtroDTO);
		}
		return filtroDTO;
	}
	
	/**
	 * Método para consultar los días de cmpromiso en base a una medida seleccionada para asi poder armar el grid de seguimiento
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward consultarCalendarizacionPorMedidaIdReducido(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		log.info("Action de Consultar Calendarizacion por Medida Id:::::");

		Long medidaCautelarId = new Long(request.getParameter("medidaCautelarId"));
		
		log.debug("medidaCautelarId... " + medidaCautelarId);
		
		try {
			List<FechaCompromisoDTO> fechaCompromisoDTOs = new ArrayList<FechaCompromisoDTO>(); 
			fechaCompromisoDTOs = 
				agendaDelegate.consultarCalendarizacionPorMedidaIdReducido(medidaCautelarId);
			
			log.debug("fechaCompromisoDTOs.size() ... " + fechaCompromisoDTOs.size());
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			
			writer.print("<records>" + fechaCompromisoDTOs.size() + "</records>");
			
			for(FechaCompromisoDTO fechaCompromisoDTO : fechaCompromisoDTOs){
				//Id de fechaCompromiso
				writer.print("<row id='" + fechaCompromisoDTO.getFechaCompromisoId() + "'>");							
			
				//Descripcion del compromiso. 
				//Unicamente sirve para generar una columna para el boton de registro
				writer.print("<cell>");
				if(fechaCompromisoDTO.getDescripcionCompromiso() != null){
					writer.print(fechaCompromisoDTO.getDescripcionCompromiso());
				}
				writer.print("</cell>");
			
				//Fecha Compromiso [dd/MM/yyy] 
				writer.print("<cell>");
				if(fechaCompromisoDTO.getFechaCompromiso() != null){
					Calendar cal = Calendar.getInstance();
					cal.setTime(fechaCompromisoDTO.getFechaCompromiso());
					int day = cal.get(Calendar.DATE);
			        int month = cal.get(Calendar.MONTH) + 1;
			        int year = cal.get(Calendar.YEAR);
					writer.print((day < 10 ? "0" + day : day) + "/" + (month < 10 ? "0" + month : month) + "/" + year);
				}
				writer.print("</cell>");
			
				//Fecha Cumplimiento [HH:mm]
				writer.print("<cell>");
				if(fechaCompromisoDTO.getFechaCumplimiento() != null){
					Calendar cal = Calendar.getInstance();
					cal.setTime(fechaCompromisoDTO.getFechaCumplimiento());
					int hour = cal.get(Calendar.HOUR_OF_DAY);
			        int minutes = cal.get(Calendar.MINUTE);
					writer.print((hour < 10 ? "0" + hour : hour) + ":" + (minutes < 10 ? "0" + minutes : minutes));
				}
				writer.print("</cell>");
				writer.print("</row>");					
			}
					
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (NSJPNegocioException e) {
			log.error(e);
		}  
			
		return null;
		
	}

	/**
	 * Método para actualizar la hora del cumplimiento de un compromiso de una medida cautelar determinada
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward actualizarCumplimiento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		log.info("Action de Actualizar Cumplimiento:::::");

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Long fechaCompromisoId = new Long(request.getParameter("fechaCompromisoId"));
		String strFechaCumplimiento = request.getParameter("fechaCumplimiento");
		
		log.debug("fechaCompromisoId... " + fechaCompromisoId);
		log.debug("strFechaCumplimiento ... " + strFechaCumplimiento);
		
		try {
			Date dFechaCumplimiento = (Date)formatter.parse(strFechaCumplimiento);
			FechaCompromisoDTO fechaCompromisoDTO = new FechaCompromisoDTO();
			fechaCompromisoDTO.setFechaCompromisoId(fechaCompromisoId);
			fechaCompromisoDTO.setFechaCumplimiento(dFechaCumplimiento);
			
			agendaDelegate.actualizarFechaCumplimiento(fechaCompromisoDTO);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.flush();
			writer.close();
			
		} catch (NSJPNegocioException e) {
			log.error(e);
		} catch (ParseException e1) {
			log.error(e1);
		} 
			
		return null;
	}
	
	public ActionForward consultarCalendarizacionPorMedidaId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		log.info("Action de Consultar Calendarizacion por Medida Id:::::");

		Long medidaCautelarId = new Long(request.getParameter("medidaCautelarId"));
		
		log.debug("medidaCautelarId... " + medidaCautelarId);
		
		try {
			MedidaCautelarDTO medidaDTO = new MedidaCautelarDTO();
			medidaDTO.setDocumentoId(medidaCautelarId);
			CompromisoPeriodicoDTO compromisoDTO = new CompromisoPeriodicoDTO(); 
			compromisoDTO = 
				compromisoDelegate.obtenerCompromisoPeriodico(medidaDTO);
			
			log.debug("compromisoDTO.getFechaCompromisos().size() ... " + compromisoDTO.getFechaCompromisos().size());
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			String xml = null;
			converter.alias("compromisoDTO", CompromisoPeriodicoDTO.class);
			xml = converter.toXML(compromisoDTO);
			response.setContentType("text/xml");
			log.info("xml de consultarCalendarizacionPorMedidaId: " + xml );	
			writer = response.getWriter();
			writer.print(xml);
			
			writer.flush();
			writer.close();
			
		} catch (NSJPNegocioException e) {
			log.error(e);
		}  
			
		return null;
		
	}
	
	
	/**
	 * Método para guardar los días de compromiso en la base de datos
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward cancelarMedida(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		log.info("Action de cambiar estatus de medida cautelar o alterna:::::");

		try {
			Long medidaCautelarId = new Long(request.getParameter("medidaCautelarId"));
			log.debug("medidaCautelarId... " + medidaCautelarId);
			
			log.info("cancelando medida...");
			cambiarEstatusMedida(medidaCautelarId, EstatusMedida.CANCELADA.getValorId());
			log.info("medida cancelada");
			
			escribirRespuesta(response, "<bandera>1</bandera>");
			
		} catch (Exception e1) {
			escribirRespuesta(response, "<bandera>0</bandera>");
			log.error(e1);
		}  
			
		return null;
	}
	
	/**
	 * Método para obtener las Medidas Alternas
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward consultaGridMedidasAlternasIncumplidas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		log.info("Action consultaGridMedidasCautelaresIncumplidas:::::");
		try {
			List<MedidaAlternaDTO> medidasAlternasDTOs = medidasAlternasDelegate.obtenerMedidasAlternasIncumplidasDelDiaAnterior();
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<rows>");
				writer.print("<records>" + medidasAlternasDTOs.size() + "</records>");
				for (MedidaAlternaDTO medidacautelarDTO : medidasAlternasDTOs) {
					log.info("/******** ::::: /" + medidacautelarDTO);
					writer.print("<row id='" + medidacautelarDTO.getDocumentoId() +"'>");
					writer.print("<cell>"+medidacautelarDTO.getInvolucrado().getNombreCompleto()+"</cell>");
					if(medidacautelarDTO.getExpedienteDTO()!=null)
					{
						writer.print("<cell>"+medidacautelarDTO.getExpedienteDTO().getNumeroExpediente()+"</cell>");
					}
					else
					{
						writer.print("<cell>---</cell>");
					}
					if(medidacautelarDTO.getNumeroCaso()!=null)
					{
						writer.print("<cell>"+medidacautelarDTO.getNumeroCaso()+"</cell>");
					}
					else
					{
						writer.print("<cell>---</cell>");
					}
					if(medidacautelarDTO.getNumeroCausa()!=null)
					{
						writer.print("<cell>"+medidacautelarDTO.getNumeroCausa() + "</cell>");
					}
					else
					{
						writer.print("<cell>---</cell>");
					}
					writer.print("<cell>"+medidacautelarDTO.getValorTipoMedida().getNombreCampo() + "</cell>");
					//writer.print("<cell>"+medidacautelarDTO.getNumeroCarpetaEjecucion() + "</cell>");
					writer.print("</row>");
				} 
									
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (NSJPNegocioException e) {
			log.error(e);
		}
		
		return null;		
	}
	
	
}
