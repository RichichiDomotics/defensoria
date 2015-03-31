/**
* Nombre del Programa : AtenderSolicitudPericialAction.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 22/07/2011
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
package mx.gob.segob.nsjp.web.pericial.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudPericialDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DictamenDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudPericialDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action que atiende las solicitudes de vista con el objetivo 
 * de anteder el procedimiento de una solicitud pericial asignada a un perido, ya sea
 * para generar dictámenes, informes o agregar notas a un dictamen
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
public class AtenderSolicitudPericialAction extends GenericAction {
	
	@Autowired
	SolicitudPericialDelegate solicitudPericialDelegate;
	@Autowired
	SolicitudDelegate solicitudDelegate;
	@Autowired
	DocumentoDelegate documentoDelegate;
	
	/**Log de clase*/
	private static final Logger log  = Logger.getLogger(AtenderSolicitudPericialAction.class);
	
	/*inicio de metodo/*ByYolo*/
	
	/**
	 * funcion para consultar los datos Documentos generados para el visor de elementos
	 * @author encesarvarga (Enable IT)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward cancelarSolicitudPericial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		
		try {
			
			String solicitudId = request.getParameter("solicitudId");
			String observaciones = request.getParameter("observaciones");
			SolicitudDTO solicitudDTO = solicitudDelegate.consultarSolicitudXId(NumberUtils.toLong(solicitudId));
			solicitudDTO.setObservaciones(observaciones);
			solicitudDelegate.actualizaEstatusSolicitud(solicitudDTO, EstatusSolicitud.CANCELADA);
			log.debug("Llego el mensaje yolotzin::::"+solicitudId);
			
//			String xml = converter.toXML(solicitudDTO);
			String xml = "<esActualizado>true</esActualizado>";
			escribirRespuesta(response,xml);
			log.info("XML::::"+xml );

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	/*fin de metodo/*ByYolo*/
		
/*inicio de metodo/*ByYolo*/
	/**
	 * @author encesarvarga (Enable IT)
	 * Método para realizar al búsqueda de solcitudes periciales
	 * todas las del dia y que tengan estatus EN_PROCESO
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 */
	public ActionForward consultarSolicitudesDirectorPericiales(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		FuncionarioDTO destinatario = getUsuarioFirmado(request).getFuncionario();
		String estatus=request.getParameter("estatusSolicitud");
		String tipoBusqueda=request.getParameter("tipoBusqueda");
		String idAgenciaRequest =request.getParameter("idAgencia");
		String idFuncionario = request.getParameter("idFuncionario"); 
		List<SolicitudPericialDTO> solicitudes = new ArrayList<SolicitudPericialDTO>();
	
			
		if(estatus!=null && !estatus.equals("") && estatus.equals("abierto")){
				solicitudes = solicitudPericialDelegate.consultarSolicitudesPericialesPorEstatus(EstatusSolicitud.ABIERTA, destinatario.getClaveFuncionario());
		}
		
		if(estatus!=null && !estatus.equals("") && estatus.equals("enProceso")){
			solicitudes = solicitudPericialDelegate.consultarSolicitudesPericialesPorEstatus(EstatusSolicitud.EN_PROCESO, destinatario.getClaveFuncionario());
			
		}
		
		if(estatus!=null && !estatus.equals("") && estatus.equals("cerrado")){
			solicitudes = solicitudPericialDelegate.consultarSolicitudesPericialesPorEstatus(EstatusSolicitud.CERRADA, destinatario.getClaveFuncionario());		
		}
		
		if(tipoBusqueda!=null && !tipoBusqueda.equals("") && tipoBusqueda.equals("0")){
			solicitudes = solicitudPericialDelegate.consultarSolicitudesPericialesTodas();	
		}
		
		if(tipoBusqueda!=null && !tipoBusqueda.equals("") && tipoBusqueda.equals("1")){
			solicitudes = solicitudPericialDelegate.consultarSolicitudesPericialesPorDia();
		}
		
		if(tipoBusqueda!=null && !tipoBusqueda.equals("") && tipoBusqueda.equals("3")){
			Long idAgencia = NumberUtils.toLong(idAgenciaRequest);
			solicitudes = solicitudPericialDelegate.consultarSolicitudesPericialesPorAgencia(idAgencia, destinatario);
		}
		
		if(tipoBusqueda!=null && !tipoBusqueda.equals("") && tipoBusqueda.equals("4")){
			FuncionarioDTO delFuncionario = new FuncionarioDTO(NumberUtils.toLong(idFuncionario));
			solicitudes = solicitudPericialDelegate.consultarSolicitudesPericialesPorUsuario(delFuncionario, destinatario);
		}
				
		
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();

		writer.print("<rows>");
		
		/*Inicio Paginacion/*ByYolo*/
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		log.debug("Yolo::: Paginacion");
		log.debug("pag :: " + pag);
        if (pag != null && pag.getTotalRegistros() != null) {
            writer.print("<total>" + pag.getTotalPaginas() + "</total>");
            writer.print("<records>" + pag.getTotalRegistros() + "</records>");
        } else {
            writer.print("<total>0</total>");
            writer.print("<records>0</records>");
        }		
		/*Fin Paginacion /*ByYolo*/
//		writer.print("<records>" + solicitudes.size() + "</records>");
		Long dictamenId = null;
		for (SolicitudPericialDTO sol : solicitudes) {
			dictamenId = solicitudPericialDelegate.consultarDictamenIdDeSolicitudPericial(sol.getDocumentoId());   
			writer.print("<row id='"+ sol.getDocumentoId() + "," + (dictamenId!=null?dictamenId:"0")+ "'>");
			writer.print("<cell>"+ (sol.getExpedienteDTO()!=null?sol.getExpedienteDTO().getNumeroExpediente():"") +  "</cell>");
			writer.print("<cell>"+ (sol.getExpedienteDTO()!=null&&
									sol.getExpedienteDTO().getCasoDTO()!=null?
									sol.getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso():"") +  "</cell>");
//			writer.print("<cell>"+ DateUtils.formatear(sol.getFechaLimite()) +  "</cell>");
			writer.print("<cell>"+ DateUtils.formatear(sol.getFechaCreacion()) +  "</cell>");
			writer.print("<cell>"+ DateUtils.formatear(sol.getFechaModificacion()) +  "</cell>");
//			writer.print("<cell>"+ (dictamenId!=null?"Si":"No") +  "</cell>");
			writer.print("<cell>"+ sol.getEstatus().getValor() +  "</cell>");
			writer.print("<cell>"+ (sol.getExpedienteDTO()!=null?sol.getExpedienteDTO().getExpedienteId():"") +  "</cell>");
			writer.print("<cell>"+ (sol.getExpedienteDTO()!=null?sol.getExpedienteDTO().getNumeroExpedienteId():"") +  "</cell>");
			log.info("idExpParaDocumentos"+sol.getExpedienteDTO().getNumeroExpedienteId());
			log.info("idExpParaDocumentos"+sol.getExpedienteDTO().getNumeroExpedienteId());
			writer.print("</row>");
		}
								
		
		
		writer.print("</rows>");
		writer.flush();
		writer.close();
		return null;
	}
	/*fin metodo/*ByYolo*/
	
	
	/**
	 * Método para realizar al búsqueda de solcitudes periciales
	 * asignadas al usuario actual y que tengan estatus EN_PROCESO
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 */
	public ActionForward consultarSolicitudesPericialesPeritoEnProceso(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		
		FuncionarioDTO destinatario = getUsuarioFirmado(request).getFuncionario();
		String estatus=request.getParameter("estatusSolicitud");
		List<SolicitudPericialDTO> solicitudes =null;
		if(estatus!=null && !estatus.equals("") && estatus.equals("cerrado")){
			solicitudes = solicitudPericialDelegate.
					consultarSolicitudesPericialesPorEstatusYDestinatario(EstatusSolicitud.CERRADA, destinatario);
		}else{
			solicitudes = solicitudPericialDelegate.
					consultarSolicitudesPericialesPorEstatusYDestinatario(EstatusSolicitud.EN_PROCESO, destinatario);
		}
		
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();

		writer.print("<rows>");
		writer.print("<records>" + solicitudes.size() + "</records>");
		Long dictamenId = null;
		for (SolicitudPericialDTO sol : solicitudes) {
			dictamenId = solicitudPericialDelegate.consultarDictamenIdDeSolicitudPericial(sol.getDocumentoId());   
			writer.print("<row id='"+ sol.getDocumentoId() + "," + (dictamenId!=null?dictamenId:"0")+ "'>");
			writer.print("<cell>"+ (sol.getExpedienteDTO()!=null?sol.getExpedienteDTO().getNumeroExpediente():"") +  "</cell>");
			writer.print("<cell>"+ (sol.getExpedienteDTO()!=null&&
									sol.getExpedienteDTO().getCasoDTO()!=null?
									sol.getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso():"") +  "</cell>");
			// se cambia fecha a peticion de OAXACA
//			writer.print("<cell>"+ DateUtils.formatear(sol.getFechaLimite()) +  "</cell>");
			writer.print("<cell>"+ DateUtils.formatear(sol.getFechaCreacion()) +  "</cell>");
			writer.print("<cell>"+ DateUtils.formatear(sol.getFechaModificacion()) +  "</cell>");
			writer.print("<cell>"+ (dictamenId!=null?"Si":"No") +  "</cell>");
			writer.print("<cell>"+ (sol.getExpedienteDTO()!=null?sol.getExpedienteDTO().getExpedienteId():"") +  "</cell>");
			writer.print("<cell>"+ (sol.getExpedienteDTO()!=null?sol.getExpedienteDTO().getNumeroExpedienteId():"") +  "</cell>");
			log.info("idExpParaDocumentos"+sol.getExpedienteDTO().getNumeroExpedienteId());
			log.info("idExpParaDocumentos"+sol.getExpedienteDTO().getNumeroExpedienteId());
			writer.print("</row>");
		}
								
		
		
		writer.print("</rows>");
		writer.flush();
		writer.close();
		return null;
	}

	/**
	 * Método para crear un nuevo dictamen asociado a la solicitud pericial que sea enviada como
	 * parámetro
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 */
	public ActionForward crearDictamenParaSolicitudPericial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Long solicitudPericialId = NumberUtils.toLong(request.getParameter("solicitudPericialId"),0);
		if(solicitudPericialId > 0){
			
			DictamenDTO dictamen = new DictamenDTO();
			dictamen.setSolicitudPericial(new SolicitudPericialDTO(solicitudPericialId));
			dictamen.setFechaCreacion(new Date());
			dictamen.setFormaDTO(new FormaDTO(NumberUtils.toLong(request.getParameter("formaId"),0)));
			solicitudPericialDelegate.registrarDictamenParaSolicitudPericial(dictamen);
			escribirRespuesta(response, converter.toXML(dictamen));
		}
		
		
		return null;
		
	}
	/**
	 * Método para colocar el expediente en sesión de la solicitud elegida
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 */
	public ActionForward colocarExpedienteParaSolicitudPericial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Long solicitudPericialId = NumberUtils.toLong(request.getParameter("solicitudPericialId"),0);
		SolicitudPericialDTO solicitud = solicitudPericialDelegate.consultarSolicitudPericialPorId(solicitudPericialId);
		setExpedienteTrabajo(request, solicitud.getExpedienteDTO());
		escribirRespuesta(response, converter.toXML(solicitud.getExpedienteDTO()));
		return null;
		
	}
	
	/**
	 * Actualiza el estatus de una solicitud pericial a CERRADO
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 */
	public ActionForward terminarSolicitudPericialPerito(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Long solicitudPericialId = NumberUtils.toLong(request.getParameter("solicitudPericialId"),0);
		
		solicitudDelegate.actualizaEstatusSolicitud(new SolicitudDTO(solicitudPericialId),EstatusSolicitud.CERRADA);
		escribirRespuesta(response, converter.toXML(solicitudPericialId));
		return null;
	}
	
	
	/**
	 * funcion para consultar los  Documentos generados para el visor atencion a solicitudes periciales
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarDocumentosAsociadosASolicitud(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		try {
			Long solicitudId = NumberUtils.toLong(request.getParameter("solicitudId"),0); 
			log.info("::::::::::::: ID de la solicitud para consultar documentos consultarDocumentos ::::::::::::.:: "+solicitudId);

			if(solicitudId >0){
				
				List<ArchivoDigitalDTO> listaDocumentoDTOs = documentoDelegate.consultarArchivosDeSolicitud(solicitudId);
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
			
				writer.print("<rows>");
				int lTotalRegistros=listaDocumentoDTOs.size();
				writer.print("<records>" + lTotalRegistros + "</records>");	
				
				for (ArchivoDigitalDTO documentoDTO : listaDocumentoDTOs) {
					writer.print("<row id='"+documentoDTO.getArchivoDigitalId()+"'>");
					writer.print("<cell>"+documentoDTO.getNombreArchivo()+"</cell>");
					writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
				
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	
	
	/**
	 * funcion para consultar los  Documentos generados para el visor atencion a solicitudes periciales
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarDocumentosPropiosAsociadosASolicitudPericial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		try {		
			Long solicitudId = NumberUtils.toLong(request.getParameter("solicitudId"),0); 
			log.info("::::::::::::: ID de la solicitud hijo para consultar documentos consultarDocumentos ::::::::::::.:: " + solicitudId);
			solicitudId=solicitudPericialDelegate.consultarPadreSolicitudPericial(solicitudId);
			log.info("::::::::::::: ID de la solicitud padre para consultar documentos consultarDocumentos ::::::::::::.:: " + solicitudId);

			if(solicitudId >0){
				
				List<DocumentoDTO> listaDocumentosDTOs = documentoDelegate.consultarArchivosDeSolicitudPericial(solicitudId);			
				
				request.getSession().setAttribute("totalRegistrosDocumentos", listaDocumentosDTOs.size());						
				request.setAttribute("totalRegistrosDocumentos", listaDocumentosDTOs.size());				
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
				for (DocumentoDTO documentoDTO : listaDocumentosDTOs) {
										
					writer.print("<row id='"+documentoDTO.getArchivoDigital().getArchivoDigitalId()+"'>");
					
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+							
							((documentoDTO.getArchivoDigital()!=null && 
							  documentoDTO.getArchivoDigital().getUsuario()!=null && 
							  documentoDTO.getArchivoDigital().getUsuario().getArea()!=null)?
							  documentoDTO.getArchivoDigital().getUsuario().getArea():"-") + " </div>]]></cell>");
					
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+
							((documentoDTO.getActividadDTO()!=null && 
							  documentoDTO.getActividadDTO().getFechaCreacion()!=null)? 
							  documentoDTO.getActividadDTO().getFechaCreacion():"-") + " </div>]]></cell>");
					
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+
							((documentoDTO.getActividadDTO()!=null &&
							  documentoDTO.getActividadDTO().getNombre()!=null)?
							  documentoDTO.getActividadDTO().getNombre():"-") + " </div>]]></cell>");
																	
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+
							((documentoDTO.getArchivoDigital()!=null &&
							  documentoDTO.getArchivoDigital().getTipoArchivo()!=null)?
							  documentoDTO.getArchivoDigital().getTipoArchivo():"-") +  " </div>]]></cell>");
					
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ 
							((documentoDTO.getArchivoDigital()!=null &&
							  documentoDTO.getArchivoDigital().getNombreArchivo()!=null)?
							  documentoDTO.getArchivoDigital().getNombreArchivo():"-") +  " </div>]]></cell>");
					
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ 
							((documentoDTO.getStrFechaCreacion()!=null)?
							  documentoDTO.getStrFechaCreacion():"-") +  " </div>]]></cell>");
												
					writer.print("</row>");					
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
				
			}
			
		} catch (Exception e) {
			log.info(" execpcion metodoo consultarDocumentosPropiosAsociadosASolicitudPericial ");
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	
	/**
	 * Método utilizado para finalizar una solicitud pericial una vez que ya fue generado el dictamen o el informe
	 * Adjunta a la solicitud de pericial el archivo digital recién generado y actualiza
	 * el estado de la solicitud del perito y la solicitud padre del coordinador de periciales a CERRADA
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward finalizarDictamenInformePericialPeritoAMP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		solicitudPericialDelegate.finalizarSolicitudPericial(NumberUtils.toLong(request.getParameter("solicitudId")));
		
		
		
		return null;
	}
	
		
}
