/**
* Nombre del Programa 	: DatosGeneralesReinsercionAction.java
* Autor              	: EdgarTE
* Compania            	: Ultrasist
* Proyecto           	: NSJP 						Fecha: 21 Feb 2012
* Marca de cambio    	: N/A
* Descripcion General 	: Describir el objetivo de la clase de manera breve
* Programa Dependiente	: N/A
* Programa Subsecuente 	: N/A
* Cond. de ejecucion 	: N/A
* Dias de ejecucion 	: N/A 						Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor  				: N/A
* Compania 				: N/A
* Proyecto           	: N/A 						Fecha: N/A
* Modificacion     		: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.reinsercionsocial.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.remisiones.CatTipoRemision;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.delegate.sentencia.SentenciaDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaAlternaDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionMedidaAlternaDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CatProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CatTipoRemisionDTO;
import mx.gob.segob.nsjp.dto.programas.ProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.reinsercionsocial.form.DatosGeneralesReinsercionForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase que representa el action de struts que consulta la información presentada
 * dentro de las pantallas de datos generales para Reinserción Social.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class DatosGeneralesReinsercionAction extends GenericAction {
	
	/* Log de clase*/
	private static final Logger LOG  = Logger.getLogger(ReinsercionSocialAction.class);
	
	private static final String DATOS_GENERALES = "datosGenerales";
	public static final String CERTIFICADO_MEDICO = "certificadoMedicoRS";
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	public AsignacionProgramaDelegate asignacionProgD;

	@Autowired
	public ExpedienteDelegate expedienteDelegate;

	@Autowired
	public SentenciaDelegate sentenciaDelegate;

	@Autowired
	public InvolucradoDelegate involucradoDelegate;

	
	public ActionForward obtenerSentenciasGrid (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			LOG.info("ejecutando Action ReinsercionSocialAction m�todo llenarGrid");
			try {
				
				Long estatus = NumberUtils.toLong(request.getParameter("estatusExpediente"), 0);
				
				SentenciaDTO filtroDTO = new SentenciaDTO();
				ExpedienteDTO filtroExpedienteDTO = new ExpedienteDTO();
				filtroExpedienteDTO.setEstatus(new ValorDTO(estatus));
				filtroDTO.setNumeroExpedienteDTO(filtroExpedienteDTO);
				
				List<SentenciaDTO> lstSentenciasDTO = asignacionProgD.consultarSentenciasXEstado(filtroDTO);	
//				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("################## Resultados :::::::::" + lstSentenciasDTO.size());
				}
				
				response.setContentType("text/xml; charset=ISO-8859-1");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
			
				writer.print("<rows>");
				final PaginacionDTO pag2 = PaginacionThreadHolder.get();
				if (pag2 != null){
					//Caso particular para considerar el total de registros
					//pag2.setTotalRegistros(new Long(listaObjetos.size()));
	                LOG.debug("pag :: " + pag2);
	                if (pag2 != null && pag2.getTotalRegistros() != null) {
	                    writer.print("<total>" + pag2.getTotalPaginas() + "</total>");
	                    writer.print("<records>" + pag2.getTotalRegistros() + "</records>");
	                } else {
	                    writer.print("<total>0</total>");
	                    writer.print("<records>0</records>");
	                    writer.print("<page>0</page>");
	                }
				}		
				
				for (SentenciaDTO sentenciaDTO : lstSentenciasDTO) {
					String numeroCaso = ""; 
					String carpeta = "";
					String nombreSentenciado = "";
					String fechaCreacion = "";
					String numeroCausa = sentenciaDTO.getCnumeroCausa();
					Long numeroExpedienteId  = 0L;
					if ( sentenciaDTO.getNumeroExpedienteDTO() != null ) {						
						ExpedienteDTO expedienteDTO = sentenciaDTO.getNumeroExpedienteDTO();
						if(expedienteDTO.getCasoDTO() != null){
							CasoDTO casoDTO = expedienteDTO.getCasoDTO();
							numeroCaso = casoDTO.getNumeroGeneralCaso();
							
						}
						carpeta = expedienteDTO.getNumeroExpediente();
						numeroExpedienteId = expedienteDTO.getNumeroExpedienteId();
						fechaCreacion = SDF.format(expedienteDTO.getFechaApertura());						
					}
					
					if(sentenciaDTO.getInvolucradoDTO() != null) {
						InvolucradoDTO involucradoDTO = sentenciaDTO.getInvolucradoDTO();
						nombreSentenciado = involucradoDTO.getNombreCompleto();
					}

					writer.print("<row id='"+ sentenciaDTO.getSentenciaId() +"'>");
						writer.print("<cell><![CDATA[<div class='celdaGrid'>");
						writer.print(numeroCaso);
						writer.print("</div>]]></cell>");
						writer.print("<cell><![CDATA[<div class='celdaGrid'>");
						writer.print(numeroCausa);
						writer.print("</div>]]></cell>");
						writer.print("<cell><![CDATA[<div class='celdaGrid'>");
						writer.print(carpeta);
						writer.print("</div>]]></cell>");
						writer.print("<cell><![CDATA[<div class='celdaGrid'>");
						writer.print(nombreSentenciado);
						writer.print("</div>]]></cell>");
						writer.print("<cell><![CDATA[<div class='celdaGrid'>");
						writer.print(fechaCreacion);
						writer.print("</div>]]></cell>");
						writer.print("<cell><![CDATA[<div class='celdaGrid'>");
						writer.print(numeroExpedienteId);
						writer.print("</div>]]></cell>");
					writer.print("</row>");

				}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		
		} catch (Exception e) {		
			LOG.info(e.getCause(),e);
		}
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarDatosGeneralesRS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String forward = "";
		
		try {
			DatosGeneralesReinsercionForm datosGralesRF = (DatosGeneralesReinsercionForm)form;
			SentenciaDTO sentenciaDTO = null;
			Long sentenciaId = NumberUtils.toLong(request.getParameter("sentenciaId"), 0);
			String cNumeroExpediente = request.getParameter("cNumeroExpediente");
			
			String enviarA = request.getParameter("enviarA");
			
			
			if(sentenciaId > 0L) {
				SentenciaDTO filtro = new SentenciaDTO();
				filtro.setSentenciaId(sentenciaId);
				sentenciaDTO = asignacionProgD.consultarSentenciaPorId(filtro);
				
			} else if (sentenciaId == 0L && cNumeroExpediente != null) {
				SentenciaDTO filtro = new SentenciaDTO();
				filtro.setNumeroExpedienteDTO(new ExpedienteDTO(null, null, cNumeroExpediente));
				sentenciaDTO = asignacionProgD.consultarSentenciaPorId(filtro);
			}
			
			if (sentenciaDTO != null) {

				ExpedienteDTO expedienteDTO = sentenciaDTO.getNumeroExpedienteDTO();
				
				if(expedienteDTO.getEstatus() == null) {
					throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
				}
				
//				if (expedienteDTO.getEstatus().getIdCampo().compareTo(EstatusExpediente.POR_ATENDER.getValorId()) == 0) {
//					expedienteDTO.setEstatus(new ValorDTO(EstatusExpediente.ABIERTO.getValorId()));
//					expedienteDelegate.actualizarEstatusExpediente(expedienteDTO);
//					sentenciaDTO = asignacionProgD.consultarSentenciaPorId(sentenciaDTO);
//				}
				
				datosGralesRF = obtenerDatosGenerales(datosGralesRF, sentenciaDTO);
				if ( sentenciaDTO.getNumeroExpedienteDTO() != null ) {
					ExpedienteDTO tmp = sentenciaDTO.getNumeroExpedienteDTO();
					super.setExpedienteTrabajo(request, tmp);
				}
			}
			
			if(enviarA != null){
				forward = enviarA;
			} else {
				forward = DATOS_GENERALES;
			}
			
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return mapping.findForward(forward);
	}

	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward manejarSentencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		try {
			DatosGeneralesReinsercionForm datosGralesRF = (DatosGeneralesReinsercionForm)form;
			SentenciaDTO sentenciaDTO = new SentenciaDTO();
			Long sentenciaId = NumberUtils.toLong(request.getParameter("sentenciaId"), 0);
			Boolean estaLesionado = request.getParameter("estaLesionado") != null
									? Boolean.valueOf(request.getParameter("estaLesionado"))
									: null;
			
			String fechaNacimiento = request.getParameter("fechaNacimiento");
			
			Boolean estaArraigado = request.getParameter("estaArraigado") != null
									? Boolean.valueOf(request.getParameter("estaArraigado"))
									 :null;

			String motivo = request.getParameter("motivo");
			
			InvolucradoDTO involucradoDTO = null;
			AsignacionCentroDetencionDTO centroDetencionDTO = null;
			
			
			if(sentenciaId != null && sentenciaId.compareTo(0L) > 0) {
				sentenciaDTO.setSentenciaId(sentenciaId);
				sentenciaDTO = asignacionProgD.consultarSentenciaPorId(sentenciaDTO);
				if(sentenciaDTO != null) {
					if(estaLesionado != null) {
						sentenciaDTO.setBlesionado(estaLesionado);
					}
					
					if (estaArraigado != null || motivo != null) {;
						if (sentenciaDTO.getAsignacionCentroDetencions() != null
								&& !sentenciaDTO.getAsignacionCentroDetencions().isEmpty()){
							for (AsignacionCentroDetencionDTO asignacionCentroDetencionDTO : sentenciaDTO.getAsignacionCentroDetencions()) {
								//TODO: validar cual es el CENTRO ACTIVO
								
								centroDetencionDTO = new AsignacionCentroDetencionDTO();
								centroDetencionDTO.setAsignacionCentroDetencionId(asignacionCentroDetencionDTO.getAsignacionCentroDetencionId());

								if(estaArraigado != null) {
									centroDetencionDTO.setBarraigado(estaArraigado);
								}
								
								if(motivo != null){
									centroDetencionDTO.setCmotivo(motivo);
								}
							}
						}
					}
					
					involucradoDTO = sentenciaDTO.getInvolucradoDTO();
				}
				
				if (involucradoDTO != null
						&& fechaNacimiento != null
						&& involucradoDTO.getNombresDemograficoDTO() != null
						&& !involucradoDTO.getNombresDemograficoDTO().isEmpty()){
					NombreDemograficoDTO nombreDemograficoDTO = involucradoDTO.getNombresDemograficoDTO().get(0);
					nombreDemograficoDTO.setFechaNacimiento(
							SDF.parse(fechaNacimiento)
					);
					
					involucradoDelegate.actualizarIndividuo(involucradoDTO);
				}
				sentenciaDelegate.realizarAltasCambiosASentencia(sentenciaDTO);
				
				if(centroDetencionDTO != null){
					asignacionProgD.actualizarCentroDetencion(centroDetencionDTO);
				}
					
			}
			
			datosGralesRF = obtenerDatosGenerales(datosGralesRF, sentenciaDTO);
			converter.alias("datosGralesRF", DatosGeneralesReinsercionForm.class);
			String xml = converter.toXML(datosGralesRF);
			//mandamos la respuesta al cliente
			escribir(response, xml,null);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	private DatosGeneralesReinsercionForm obtenerDatosGenerales(
			DatosGeneralesReinsercionForm datosGralesRF,
			SentenciaDTO sentenciaDTO) {
		
		if (datosGralesRF == null){
			datosGralesRF = new DatosGeneralesReinsercionForm();
		}
		
		datosGralesRF.setSentenciaId(sentenciaDTO.getSentenciaId().toString());
		
		if(sentenciaDTO.getInvolucradoDTO() != null) {
			InvolucradoDTO involucradoDTO = sentenciaDTO.getInvolucradoDTO();
			List<NombreDemograficoDTO> lstNombreDemograficoDTO = involucradoDTO.getNombresDemograficoDTO();
			if(lstNombreDemograficoDTO != null && !lstNombreDemograficoDTO.isEmpty()){
				NombreDemograficoDTO nombreDemograficoDTO = lstNombreDemograficoDTO.get(0);
				datosGralesRF.setApellidoMaterno(nombreDemograficoDTO.getApellidoMaterno());
				datosGralesRF.setApellidoPaterno(nombreDemograficoDTO.getApellidoPaterno());
				datosGralesRF.setNombre(nombreDemograficoDTO.getNombre());
				nombreDemograficoDTO.getEdadAproximada();
				datosGralesRF.setEdad(""+calcularEdadAproximada(nombreDemograficoDTO.getFechaNacimiento()));
			}
		}
		
		if ( sentenciaDTO.getNumeroExpedienteDTO() != null ) {
		
			ExpedienteDTO expedienteDTO = sentenciaDTO.getNumeroExpedienteDTO();
			if(expedienteDTO.getCasoDTO() != null){
				CasoDTO casoDTO = expedienteDTO.getCasoDTO();
				datosGralesRF.setCaso(casoDTO.getNumeroGeneralCaso());
			}
		
			datosGralesRF.setCarpeta(expedienteDTO.getNumeroExpediente());
			datosGralesRF.setExpedienteId(""+expedienteDTO.getExpedienteId());
			datosGralesRF.setNumeroExpedienteId(""+expedienteDTO.getNumeroExpedienteId());
		}
		
		datosGralesRF.setFechaInicioPenaSTR(SDF.format(sentenciaDTO.getDfechaInicioPena()));
		datosGralesRF.setFechaFinPenaSTR(SDF.format(sentenciaDTO.getDfechaFinPena()));
		if(sentenciaDTO.getBlesionado()){
			datosGralesRF.setLesionado("1");
		}else{
			datosGralesRF.setLesionado("0");
		}		
		
		datosGralesRF.setCausa(sentenciaDTO.getCnumeroCausa());
		
		if(sentenciaDTO.getAsignacionCentroDetencions() != null){
			AsignacionCentroDetencionDTO centroDetencionAsignado = null;
			for(AsignacionCentroDetencionDTO dto : sentenciaDTO.getAsignacionCentroDetencions()){
				if(dto.getBarraigado()){
					centroDetencionAsignado = dto;
				}
			}
			if(centroDetencionAsignado != null){
				datosGralesRF.setEncarcelado("1");
				datosGralesRF.setMotivo(centroDetencionAsignado.getCmotivo());
			}else{
				datosGralesRF.setEncarcelado("0");
				datosGralesRF.setMotivo("");
			}
		}
		if(sentenciaDTO.getValorDTO() !=null ) {
			datosGralesRF.setTipoSentencia(sentenciaDTO.getValorDTO().getValor());
		}
		
		List<String> lstMedidasAlternativas = new ArrayList<String>();
		if(sentenciaDTO.getAsignacionMedidaAlternas()!= null){
			for (AsignacionMedidaAlternaDTO asigMedidaAlternaDTO : sentenciaDTO.getAsignacionMedidaAlternas()) {
				MedidaAlternaDTO  medidaAlternaDTO = asigMedidaAlternaDTO.getMedidaAlternaDTO();
				lstMedidasAlternativas.add(medidaAlternaDTO.getDescripcionMedida());	
			}
		}
		datosGralesRF.setLstMedidasAlternativas(lstMedidasAlternativas);
		
		
		List<String> lstProgramas = new ArrayList<String>();
		if(sentenciaDTO.getAsignacionProgramas()!= null) {
			for (AsignacionProgramaDTO asigProgramaDTO:sentenciaDTO.getAsignacionProgramas()) {
				ProgramaDTO programaDTO = asigProgramaDTO.getProgramaDTO();
				if(programaDTO.getCatProgramaDTO() != null) {
					CatProgramaDTO catProgramaDTO = programaDTO.getCatProgramaDTO();
					lstProgramas.add(catProgramaDTO.getNombre());					
				}
			}
		}
		
		datosGralesRF.setLstProgramas(lstProgramas);
		
		datosGralesRF.setNus(sentenciaDTO.getCnus());
		
		datosGralesRF.setPorcentajeCubiertoPena(
				calcularPorcentajeCumplimientoPena(datosGralesRF.getFechaInicioPenaSTR(), 
						datosGralesRF.getFechaFinPenaSTR()));
		
		datosGralesRF.setPuntosAcumulados(sentenciaDTO.getTotalPuntosObtenidos().intValue());
		
		if(sentenciaDTO.getIpuntosPorAcumular() != null) {
			datosGralesRF.setPuntosTotales(sentenciaDTO.getIpuntosPorAcumular().intValue());
		}
		datosGralesRF.setPorcentajeCumplido(
				calcularPorcentajeCumplimientoPuntos(datosGralesRF.getPuntosTotales(),
						datosGralesRF.getPuntosAcumulados()));
		
		if(sentenciaDTO.getRemisions() != null){
			datosGralesRF.setBeneficio("0");
			datosGralesRF.setCandidatoBeneficio("0");
			datosGralesRF.setPreliberacion("0");
			datosGralesRF.setRemisionParcial("0");
			datosGralesRF.setReparacionDanio("0");
			datosGralesRF.setMontoDanioReparado("0");

			for (RemisionDTO remisionDTO : sentenciaDTO.getRemisions()) {
				CatTipoRemisionDTO catTipoRemisionDTO = remisionDTO.getCatTipoRemisionDTO();
				if(catTipoRemisionDTO != null){
					
					if(		catTipoRemisionDTO.getCatTipoRemisionId() == CatTipoRemision.CONDICIONAL.getId() 
						||  catTipoRemisionDTO.getCatTipoRemisionId() == CatTipoRemision.DEFINITIVA.getId() ) {
						datosGralesRF.setBeneficio(""+catTipoRemisionDTO.getCatTipoRemisionId());
						datosGralesRF.setPreliberacion("1");
					}
					
					if(catTipoRemisionDTO.getCatTipoRemisionId() == CatTipoRemision.REMISION_PARCIAL_DE_LA_PENACONDICIONAL.getId()) {
						datosGralesRF.setRemisionParcial("1");
					}
					
					if(catTipoRemisionDTO.getCatTipoRemisionId() == CatTipoRemision.REPARACION_DEL_DANIO.getId()) {
						datosGralesRF.setReparacionDanio("1");
						datosGralesRF.setMontoDanioReparado(""+remisionDTO.getImontoDanioReparado());
					}				
					
					
					if(datosGralesRF.getPorcentajeCubiertoPena() >= catTipoRemisionDTO.getIporcentajeCumplido()){
						datosGralesRF.setCandidatoBeneficio("1");
					}
				}
			}
		}
						
		return datosGralesRF;
		
	}
	
	private int calcularPorcentajeCumplimientoPena(String fechaInicioPena,String fechaFinPena){
		int porcentajeCumplido = 0;
		try {
			
			Date fechaFin = SDF.parse(fechaFinPena);
			double millisFin = fechaFin.getTime();
			
			Date fechaInicio = SDF.parse(fechaInicioPena);
			double millisInicio = fechaInicio.getTime();
			
			Calendar cal = Calendar.getInstance();
			Date fechaActual = cal.getTime();
			
			double millisActual = fechaActual.getTime();
			if (millisActual > millisFin){
				return 100;
			}else{
				double intervaloSentencia = millisFin - millisInicio;
				double intervaloCumplido = millisActual - millisInicio;
				porcentajeCumplido = (int) ((intervaloCumplido / intervaloSentencia)*100);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return porcentajeCumplido;
	}
	
	private int calcularPorcentajeCumplimientoPuntos(int puntosTotales, int puntosAcumulados){
		int porcentaje=0;
		if (puntosAcumulados > puntosTotales){
			porcentaje = 100;
		}else{
			double dTotal = puntosTotales;
			double dAcumulado = puntosAcumulados;
			porcentaje = (int) ((dAcumulado / dTotal)*100);			
		}
		return porcentaje;
	}

	private int calcularEdadAproximada(Date fechaNacimiento){
		int anios = 0;
		if(fechaNacimiento != null){
			int anioActual = Calendar.getInstance().get(Calendar.YEAR);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaNacimiento);
			int anioNacimiento = calendar.get(Calendar.YEAR);
			anios = anioActual - anioNacimiento;
		}
		return anios;
	}
	
}
