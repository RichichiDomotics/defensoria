/**
 * Nombre del Programa 		: IngresarCadenacustodiaAction.java
 * Autor                     : ArmandoCT
 * Compania                  : Ultrasist
 * Proyecto                  : NSJP                    Fecha: 25/julio/2011
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase Action para ingresar la cadena de custodia
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion        : N/A
 * Dias de ejecucion         : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                     : N/A
 * Compania              	: N/A
 * Proyecto                 	: N/A                                 Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.almacen.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.almacen.form.GestionarAlmacenForm;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.almacen.AlmacenDelegate;
import mx.gob.segob.nsjp.delegate.almacen.EncargadoAlmacenDelegate;
import mx.gob.segob.nsjp.delegate.eslabon.EslabonDelegate;
import mx.gob.segob.nsjp.delegate.evidencia.EvidenciaDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudPericialDelegate;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.domicilio.TipoAsentamientoDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase Action para ingresar cadena de custodia.
 * 
 * @version 1.0
 * @author ArmandoCT
 * 
 */
public class AdministrarAlmacenAction extends GenericAction {

	/* Log de clase */
	private static final Logger log = Logger
			.getLogger(AdministrarAlmacenAction.class);

	
	@Autowired
	private EvidenciaDelegate evidenciaDelegate;
	@Autowired
	private AlmacenDelegate almacenDelegate;
	@Autowired
	private EncargadoAlmacenDelegate encargadoAlmacenDelegate;
	@Autowired
	private SolicitudPericialDelegate solicitudPericialDelegate;
	@Autowired
	private EslabonDelegate eslabonDelegate;

	/**
	 * Permite consultar las solicitudes de almacen
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarSolicitudesDeAlmacen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		try {
			
		String numeroGeneralCaso = request.getParameter("numeroGeneralCaso");
			
			CasoDTO loCasoDt = new CasoDTO();
			if(numeroGeneralCaso != "")
				loCasoDt.setNumeroGeneralCaso(numeroGeneralCaso);
			else
				loCasoDt.setNumeroGeneralCaso(null);

			List<EvidenciaDTO> cadenas= evidenciaDelegate.consultarEvidenciasXAlmacen(getUsuarioFirmado(request),null,loCasoDt,null,null);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();		
			writer.print("<rows>");
			writer.print("<records>" + "desconocido" + "</records>");	
			
			for (EvidenciaDTO dto : cadenas) {			
				writer.print("<row id='"+dto.getEvidenciaId()+"'>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getFuncionario() != null && dto.getFuncionario().getNombreCompleto()!= null ? dto.getFuncionario().getNombreCompleto() :"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getCadenaDeCustodia() != null && dto.getCadenaDeCustodia().getExpedienteDTO()!= null&& dto.getCadenaDeCustodia().getExpedienteDTO().getCasoDTO() != null ? dto.getCadenaDeCustodia().getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getEvidenciaId() != null ? dto.getEvidenciaId():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getObjeto() != null && dto.getObjeto().getTipoObjeto()!= null ? dto.getObjeto().getTipoObjeto() :"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getCodigoBarras() != null ? dto.getCodigoBarras() :"-") +" </div>]]></cell>");				
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getUltimoEslabon() != null ? dto.getUltimoEslabon().getTipoEslabon().getValor():"-") +" </div>]]></cell>");				
				writer.print("</row>");
			}
			
			
		writer.print("</rows>");
		writer.flush();
		writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	
	
	/**
	 * Permite generar un inventario de las evidencias en un almacen
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward generaInventarioDeAlmacen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		try {
			
			String numeroGeneralCaso = request.getParameter("numeroGeneralCaso");
			//Cambio que permite consultar las evidencias asociadas a un numero de caso
			//dado el id del Almacen que ingresa en la opcion de Gestinoar Almacen.	
            Long idAlmacen = NumberUtils.toLong(request.getParameter("idAlmacen"),0);
                  	
            
			CasoDTO loCasoDt = new CasoDTO();
			if(numeroGeneralCaso != "")
				loCasoDt.setNumeroGeneralCaso(numeroGeneralCaso);
			else
				loCasoDt.setNumeroGeneralCaso(null);

			List<EvidenciaDTO> cadenas= evidenciaDelegate.consultarEvidenciasXAlmacen(getUsuarioFirmado(request),null,loCasoDt,idAlmacen,null);
			
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
			
			for (EvidenciaDTO dto : cadenas) {			
				writer.print("<row id='"+dto.getEvidenciaId()+"'>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getCadenaDeCustodia() != null && dto.getCadenaDeCustodia().getExpedienteDTO()!= null&& dto.getCadenaDeCustodia().getExpedienteDTO().getCasoDTO() != null ? dto.getCadenaDeCustodia().getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getCadenaDeCustodia() != null && dto.getCadenaDeCustodia().getFolio() != null? dto.getCadenaDeCustodia().getFolio():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getEvidenciaId() != null ? dto.getEvidenciaId():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getObjeto() != null && dto.getObjeto().getTipoObjeto()!= null ? dto.getObjeto().getTipoObjeto() :"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getCodigoBarras() != null ? dto.getCodigoBarras() :"-") +" </div>]]></cell>");				
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+  dto.getCantidad() +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getEstatus() != null ? dto.getEstatus().getValor() :"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getUltimoEslabon() != null && dto.getUltimoEslabon().getFechaFinMovimiento() != null ? DateUtils.formatear(dto.getUltimoEslabon().getFechaFinMovimiento()):"-") +" </div>]]></cell>");
				writer.print("</row>");
			}
			
			
		writer.print("</rows>");
		writer.flush();
		writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	
	/**
	 * Metodo para consultar el detalle de una solicitud
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarEvidenciaPorId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO CONSULTA DETALLE DE EVIDENCIAS POR ID");
			
			//Se obtiene el id de la solicitud a consultar 
			String evidenciaId = request.getParameter("evidenciaId");
			
			log.info("id de la evidencia::::::::::"+ evidenciaId);			
			EvidenciaDTO evidenciaDTO = evidenciaDelegate.consultaEvidencia(Long.parseLong(evidenciaId));
			evidenciaDTO.getCadenaDeCustodia().setEvidencias(null);
			
			converter.alias("evidenciaDTO", EvidenciaDTO.class);
			String xml = converter.toXML(evidenciaDTO);
			log.info("xml de la solicitud respuesta: :::::::::"+ xml);
			
			escribir(response, xml,null);												
			
		} catch (Exception e) {		
			log.info("ERROR AL CONSULTAR LA SOLICITUD ---- consultaSolicitudesEvidenciasPorId");
			log.info(e.getCause(),e);
			escribir(response, "consultaSolicitudesEvidenciasPorId",null);
			
		}
		return null;
	}
	
	/**
	 * funcion para consultar los datos Documentos asociados a una evidencia
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarDocumentosXIdEvidencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		try {
			String idEvidencia=request.getParameter("idEvidencia");
			
			log.info("$$$$ ID de Evidencia $$$ : "+idEvidencia);
			EvidenciaDTO evidenciaDTO = new EvidenciaDTO();
			evidenciaDTO.setEvidenciaId(Long.parseLong(idEvidencia));
			
			List<DocumentoDTO> listaDocumentoDTOs=evidenciaDelegate.consultarDocumentosXEslabonesDEvidencia(evidenciaDTO);
			request.getSession().setAttribute("totalRegistrosDocumentos", listaDocumentoDTOs.size());
			request.setAttribute("totalRegistrosDocumentos", listaDocumentoDTOs.size());
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
		
			writer.print("<rows>");
			int lTotalRegistros=listaDocumentoDTOs.size();
			writer.print("<records>" + lTotalRegistros + "</records>");	
			
			for (DocumentoDTO documentoDTO : listaDocumentoDTOs) {
				writer.print("<row id='"+documentoDTO.getDocumentoId()+"'>");
				
				//Area de negocio del dueno del documento
				if (documentoDTO.getActividadDTO() != null
						&& documentoDTO.getActividadDTO().getUsuario() != null
						&& documentoDTO.getActividadDTO().getUsuario()
								.getFuncionario() != null
						&& documentoDTO.getActividadDTO().getUsuario()
								.getFuncionario().getCatAreaNegocio() != null
						&& documentoDTO.getActividadDTO().getUsuario()
								.getFuncionario().getCatAreaNegocio()
								.getNombreCatAreaNegocio() != null) {
					
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"
							+ documentoDTO.getActividadDTO().getUsuario()
									.getFuncionario().getCatAreaNegocio()
									.getNombreCatAreaNegocio()
							+ " </div>]]></cell>");
				}else{
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ "-" +" </div>]]></cell>");
				}				
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (documentoDTO.getActividadDTO() != null && documentoDTO.getActividadDTO().getFechaCreacion() != null? DateUtils.formatear(documentoDTO.getActividadDTO().getFechaCreacion()):"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (documentoDTO.getActividadDTO() != null && documentoDTO.getActividadDTO().getNombre() != null? documentoDTO.getActividadDTO().getNombre():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+documentoDTO.getTipoDocumentoDTO().getValor()+" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+documentoDTO.getNombreDocumento()+" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+documentoDTO.getStrFechaCreacion()+" </div>]]></cell>");
				writer.print("</row>");
			}
		writer.print("</rows>");
		writer.flush();
		writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	
	
	/**
	 * Metodo para actualizar el estatus de una evidencia
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward actualizarEstatusEvidencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO ACTUALIZACION DE ESTATUS DE EVIDENCIA");
			
			//Se obtiene el id de la evidencia a actualizar 
			String evidenciaId = request.getParameter("evidenciaId");
			log.info("id de la evidencia::::::::::"+ evidenciaId);		
			
			//Se obtiene el id del estatus a actualizar 
			String estatusId = request.getParameter("estatusId");
			log.info("id del estatus ::::::::::"+ estatusId);		

			EvidenciaDTO evidenciaDTO = new EvidenciaDTO();
			evidenciaDTO.setEvidenciaId(Long.parseLong(evidenciaId));
			evidenciaDTO.setEstatus(new ValorDTO(Long.parseLong(estatusId)));
			
					
			Long idEvidencia= evidenciaDelegate.cambiarEstatusEvidencia(evidenciaDTO);
			
			converter.alias("idObjeto", Boolean.class);
			String xml = converter.toXML(idEvidencia > 0? true : false);
			log.info("xml de la evidencia respuesta: :::::::::"+ xml);
			
			escribir(response, xml,null);												
			
		} catch (Exception e) {		
			log.info("ERROR AL CONSULTAR LA EVIDENCIA ---- actualizarEstatusEvidencia");
			log.info(e.getCause(),e);
			escribir(response, "consultaSolicitudesEvidenciasPorId",null);
			
		}
		return null;
	}
	
	

	/**
	 * Permite consultar las evidencias que no se se devolvieron en la fecha establecida 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarEvidenciasNoDevueltas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		try {
			
			//FIXME GAMA: La nueva firma requiere el usuario con funcionarioId
//			List<EvidenciaDTO> cadenas = evidenciaDelegate.consultarEvidenciasNoDevueltas(new AlmacenDTO(1L));
			List<EvidenciaDTO> cadenas = evidenciaDelegate.consultarEvidenciasNoDevueltas(null);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();		
			writer.print("<rows>");
			writer.print("<records>" + "desconocido" + "</records>");	
			
			for (EvidenciaDTO dto : cadenas) {			
				writer.print("<row id='"+dto.getEvidenciaId()+"'>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getFuncionario() != null && dto.getFuncionario().getNombreCompleto()!= null ? dto.getFuncionario().getNombreCompleto() :"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getCadenaDeCustodia() != null && dto.getCadenaDeCustodia().getExpedienteDTO()!= null&& dto.getCadenaDeCustodia().getExpedienteDTO().getCasoDTO() != null ? dto.getCadenaDeCustodia().getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getEvidenciaId() != null ? dto.getEvidenciaId():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getObjeto() != null && dto.getObjeto().getTipoObjeto()!= null ? dto.getObjeto().getTipoObjeto() :"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getUltimoEslabon() != null && dto.getUltimoEslabon().getTipoEslabon() != null && dto.getUltimoEslabon().getTipoEslabon().getValor() != null ? dto.getUltimoEslabon().getTipoEslabon().getValor():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getUltimoEslabon() != null && dto.getUltimoEslabon().getFuncionariEntrega() != null && dto.getUltimoEslabon().getFuncionariEntrega().getNombreCompleto() != null ? dto.getUltimoEslabon().getFuncionariEntrega().getNombreCompleto():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getUltimoEslabon() != null && dto.getUltimoEslabon().getFuncionariRecibe() != null && dto.getUltimoEslabon().getFuncionariRecibe().getNombreCompleto() != null ? dto.getUltimoEslabon().getFuncionariRecibe().getNombreCompleto():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (dto.getUltimoEslabon() != null && dto.getUltimoEslabon().getFechaFinMovimiento() != null ? DateUtils.formatear(dto.getUltimoEslabon().getFechaFinMovimiento()):"-") +" </div>]]></cell>");
				writer.print("</row>");
			}
			
			
		writer.print("</rows>");
		writer.flush();
		writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}	

	
	public ActionForward guardarAlmacen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("Ejecutando Action Guarda Almacen");
			Long idAlmacen = NumberUtils.toLong(request.getParameter("idAlmacen"),0);
			log.info("idAlmacen::: " + idAlmacen); 

			GestionarAlmacenForm forma = (GestionarAlmacenForm) form;
			
			AlmacenDTO almacenDTO = new AlmacenDTO();
			DomicilioDTO domicilioDTO = new DomicilioDTO();
			AsentamientoDTO asentamientoDTO = new AsentamientoDTO();
			ValorDTO valorGenericoDTO = new ValorDTO();
			CalidadDTO calidadDTO = new CalidadDTO();
			EntidadFederativaDTO entidadFederativaDTO = new EntidadFederativaDTO();
			
			almacenDTO.setNombreAlmacen(forma.getNombreAlmacen());
			if(idAlmacen == 0){
				//Si no una evidencia nueva se parsea la fecha
				almacenDTO.setFechaAlta(DateUtils.obtener(forma.getFechaAlta()));
			}

			almacenDTO.setDescripcion(forma.getDescripcionAlmacen());
			almacenDTO.setEsVirtual(BooleanUtils.toBooleanObject(forma.getRadioEsVirtual()));
			almacenDTO.setResguardaEvidencias(BooleanUtils.toBooleanObject(forma.getRadioResguardaEV()));
			
			if (!forma.getEntidadFederativa().equals("")
					&& !forma.getEntidadFederativa().equals("-1")) {
				entidadFederativaDTO.setEntidadFederativaId(new Long(forma
						.getEntidadFederativa()));
				domicilioDTO.setEntidadDTO(entidadFederativaDTO);
			}

			if (!forma.getPais().equals("") && !forma.getPais().equals("-1")) {
				valorGenericoDTO = new ValorDTO();
				valorGenericoDTO.setValor(forma.getPais());
				entidadFederativaDTO.setValorIdPais(valorGenericoDTO);
			}
			if (!forma.getDelegacionMunicipio().equals("")
					&& !forma.getDelegacionMunicipio().equals("-1")) {
				MunicipioDTO municipio = new MunicipioDTO(new Long(
						forma.getDelegacionMunicipio()), "", entidadFederativaDTO);
				asentamientoDTO.setMunicipioDTO(municipio);
				domicilioDTO.setMunicipioDTO(municipio);
			}

			CiudadDTO ciudad = new CiudadDTO();
			if (!forma.getCiudad().equals("")
					&& !forma.getCiudad().equals("-1")) {
				ciudad.setCiudadId(new Long(forma.getCiudad()));
				domicilioDTO.setCiudadDTO(ciudad);
			}
			asentamientoDTO.setCiudadDTO(ciudad);

			if (!forma.getAsentamientoColonia().equals("")
					&& !forma.getAsentamientoColonia().equals("-1")) {
				asentamientoDTO.setAsentamientoId(new Long(forma
						.getAsentamientoColonia()));
			}
			if (!forma.getTipoAsentamiento().equals("")
					&& !forma.getTipoAsentamiento().equals("-1")) {
				TipoAsentamientoDTO tipoAsentamientoDTO=new TipoAsentamientoDTO(Long.parseLong(forma.getTipoAsentamiento()), "");
				asentamientoDTO.setTipoAsentamientoDTO(tipoAsentamientoDTO);
			}
			if (!(forma.getLatitudN()== null) && !forma.getLatitudN().equals("")) {
				String lat= forma.getLatitudN()+forma.getLatitudGrados()+"°"+forma.getLatitudMinutos()+"'"+forma.getLatitudSegundos()+"\"";
				domicilioDTO.setLatitud(lat);
			}
			if (!(forma.getLongitudE()== null) && !forma.getLongitudE().equals("")) {
				String longitud= forma.getLongitudE()+forma.getLongitudGrados()+"°"+forma.getLongitudMinutos()+"'"+forma.getLongitudSegundos()+"\"";
				domicilioDTO.setLongitud(longitud);
			}
			
			domicilioDTO.setAsentamientoDTO(asentamientoDTO);
			if(forma.getCalle()!="" && !forma.getCalle().isEmpty()){
				domicilioDTO.setCalle(forma.getCalle());
			}
			if(forma.getNumExterior()!="" && !forma.getNumExterior().isEmpty()){
				domicilioDTO.setNumeroExterior(forma.getNumExterior());
			}
			if(forma.getNumInterior()!="" && !forma.getNumInterior().isEmpty()){
				domicilioDTO.setNumeroInterior(forma.getNumInterior());
			}
			if(forma.getEntreCalle()!="" && !forma.getEntreCalle().isEmpty()){
				domicilioDTO.setEntreCalle1(forma.getEntreCalle());
			}
			if(forma.getYcalle()!="" && !forma.getYcalle().isEmpty()){
				domicilioDTO.setEntreCalle2(forma.getYcalle());
			}
			if(forma.getAliasDomicilio()!="" && !forma.getAliasDomicilio().isEmpty()){
				domicilioDTO.setAlias(forma.getAliasDomicilio());
			}
			if(forma.getEdificio()!="" && !forma.getEdificio().isEmpty()){
				domicilioDTO.setEdificio(forma.getEdificio());
			}
			if(forma.getReferencias()!="" && !forma.getReferencias().isEmpty()){
				domicilioDTO.setReferencias(forma.getReferencias());
			}
			if (!forma.getTipoCalle().equals("")
					&& !forma.getTipoCalle().equals("-1")) {
				domicilioDTO.setValorCalleId(new ValorDTO(new Long(forma.getTipoCalle())));// Tipo de calle
			}
			calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.DOMICILIO);
			domicilioDTO.setCalidadDTO(calidadDTO);

			almacenDTO.setDomicilio(domicilioDTO);
			almacenDTO.setIdentificadorAlmacen(idAlmacen);
			almacenDTO.setEstatusActivo(true);
			idAlmacen = almacenDelegate.registrarAlmacen(almacenDTO);
			log.info("Datos Almacen Respuesta::::::"+idAlmacen);
			converter.alias("almacen", AlmacenDTO.class);
			
			String xml = converter.toXML(idAlmacen);
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
		}
		catch (Exception e) {		
			log.error(e.getCause(),e);
		}
		return null;
	}
	
	
	public ActionForward consultarCatalogoFuncionariosAlmacen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("Ejecutando Action Catalogo Encargados Almacen");
			
			List<FuncionarioDTO> almacenistas = new ArrayList<FuncionarioDTO>();
			FuncionarioDTO filtro = new FuncionarioDTO();
			filtro.setJerarquiaOrganizacional(new JerarquiaOrganizacionalDTO(Areas.ALMACEN.parseLong()));
			almacenistas = solicitudPericialDelegate.consultarFuncionarioPorFiltro(filtro,null);
			//Permite consultar los Funcionarios que ya estan asignados a un almacen
			List<FuncionarioDTO> funcionariosAsignados = encargadoAlmacenDelegate.consultarEncargadosDAlmacen(null);
			
			converter.alias("listaFuncionarioDTOs", List.class);
			converter.alias("funcionarioDTO", FuncionarioDTO.class);
			
			almacenistas.removeAll(funcionariosAsignados);
			String xml = converter.toXML(almacenistas);
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
		}
		catch (Exception e) {		
			log.error(e.getCause(),e);
		}
		return null;
	}
	
	
	public ActionForward consultargridEncargadosAlmacen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		try {
			
			AlmacenDTO almacenDTO = new AlmacenDTO();
			String idAlmacen = request.getParameter("idAlmacen");
			log.info("Id del Almacen::::::" + idAlmacen);
			almacenDTO.setIdentificadorAlmacen(NumberUtils.toLong(idAlmacen));
			
			List<FuncionarioDTO> funcionarioDTOs = new ArrayList<FuncionarioDTO>();
			funcionarioDTOs = encargadoAlmacenDelegate.consultarEncargadosInternosExternosDAlmacen(almacenDTO);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();		
			writer.print("<rows>");
			writer.print("<records>" + funcionarioDTOs.size() + "</records>");	
			
			for (FuncionarioDTO funcionarioDTO : funcionarioDTOs) {
				if(funcionarioDTO.getClaveFuncionario()!=null){
					writer.print("<row id='"+funcionarioDTO.getClaveFuncionario()+"'>");
				}
				else{
					writer.print("<row id='"+0+"'>");
				}
								
				writer.print("<cell><![CDATA[<div class='celdaGrid'>");
				if(funcionarioDTO.getClaveFuncionario()!=null){
					writer.print("Funcionario");
				}
				else{
					writer.print("Responsable Externo");
				}
				writer.print("</div>]]></cell>");
				
				
				writer.print("<cell><![CDATA[<div class='celdaGrid'>");
				if(funcionarioDTO.getNombreCompleto()!=null || funcionarioDTO.getNombreCompleto()!=""){
					writer.print(funcionarioDTO.getNombreCompleto()); 
				}else {
					writer.print("---"); 
				}				
				writer.print("</div>]]></cell>");
				
				writer.print("</row>");
			}
			
			
		writer.print("</rows>");
		writer.flush();
		writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}	

	
	public ActionForward asociaFuncionarioAlmacen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("Ejecutando Action Asociar Almacen a Funcionario:::::");
			
			String esInterno = request.getParameter("esInterno");
			
			String idAlmacen = request.getParameter("idAlmacen");
			String idFuncionario = request.getParameter("idFuncionario");
			
			String nombreRespExt = request.getParameter("nombreRespExt");
			String apPatRespExt =  request.getParameter("apPatRespExt");
			String apMatRespExt =  request.getParameter("apMatRespExt");
			
			AlmacenDTO almacenDTO = new AlmacenDTO();
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			almacenDTO.setIdentificadorAlmacen(NumberUtils.toLong(idAlmacen));
			
			if(esInterno==null || esInterno.isEmpty() || esInterno.equals("si")){
				funcionarioDTO.setClaveFuncionario(NumberUtils.toLong(idFuncionario));	
			}
			else{
				funcionarioDTO = null;
				almacenDTO.setNombreRespExt(nombreRespExt);
				almacenDTO.setApellidoPatRespExt(apPatRespExt);
				almacenDTO.setApellidoMatRespExt(apMatRespExt);
			}					
			
			//Long resultado=
					encargadoAlmacenDelegate.asignarEncargadoDAlmacen(almacenDTO, funcionarioDTO);
			
			converter.alias("funcionarioDTO", java.util.List.class);
			converter.alias("funcionarioDTO", FuncionarioDTO.class);
			
			String xml = converter.toXML("");
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
		}
		catch (Exception e) {		
			log.error(e.getCause(),e);
		}
		return null;
	}
	
	public ActionForward removerFuncionarioAlmacen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("Ejecutando Action Asociar Almacen a Funcionario:::::");
			
			String idAlmacen = request.getParameter("idAlmacen");
			log.info("Id del Almacen::::::" + idAlmacen);
			String idFuncionario = request.getParameter("idFuncionario");
			log.info("Id del Funcionario::::::" + idFuncionario);
			
			AlmacenDTO almacenDTO = new AlmacenDTO();
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			
			almacenDTO.setIdentificadorAlmacen(NumberUtils.toLong(idAlmacen));
			
			funcionarioDTO.setClaveFuncionario(NumberUtils.toLong(idFuncionario));
			
			encargadoAlmacenDelegate.removerEncargadoDAlmacen(almacenDTO, funcionarioDTO);
			
			converter.alias("almacenDTOs", java.util.List.class);
			converter.alias("almacenDTO", AlmacenDTO.class);
			
			String xml = converter.toXML("");
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
		}
		catch (Exception e) {		
			log.error(e.getCause(),e);
		}
		return null;
	}
	
	
	public ActionForward consultarEvidenciaPorAlmacenoExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		try {

			String numeroGeneralCaso = request.getParameter("numeroGeneralCaso");
			String idExpediente = request.getParameter("idExpediente");
			Long idAlmacen = NumberUtils.toLong(request.getParameter("idAlmacen"),0);
			Long estatusEvidencia = NumberUtils.toLong(request.getParameter("estatusEvidencia"),0);
			
			Long consultarEvidenciasSinImportarAlmacen = NumberUtils.toLong(request.getParameter("consultarEvidenciasSinImportarAlmacen"),0);
			
			Boolean tieneSolicitudPorAtender = Boolean.parseBoolean(request.getParameter("tieneSolicitudPorAtender"));

			
			UsuarioDTO usuarioDTO = getUsuarioFirmado(request);			

				
			CasoDTO loCasoDt = new CasoDTO();
			if(numeroGeneralCaso != "")
				loCasoDt.setNumeroGeneralCaso(numeroGeneralCaso);
			else
				loCasoDt.setNumeroGeneralCaso(null);
						
			log.info("Id del Almacen::::::" + idAlmacen);
			log.info("Id del idExpediente::::::" + idExpediente);

			List<EvidenciaDTO> evidenciaDTOs = new ArrayList<EvidenciaDTO>();		
			
			if(consultarEvidenciasSinImportarAlmacen == 1 ){
				//Consulta para el agentemp o para el coordinadorAmp
				evidenciaDTOs= evidenciaDelegate.consultarevidenciaXEstatusXFuncionario(estatusEvidencia, usuarioDTO);
				log.info(":::: Se aplica la consulta para el rol de agentemp o para el cooridnadorAMP");

			}else{
				//Consulta para el alamacenista
				evidenciaDTOs= evidenciaDelegate.consultarEvidenciasXAlmacen(usuarioDTO,estatusEvidencia,loCasoDt, idAlmacen, tieneSolicitudPorAtender);
				log.info(":::: Se aplica la consulta para el rol de alamacenista");

			}
			
			
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
			
			for (EvidenciaDTO dto : evidenciaDTOs) {			
				writer.print("<row id='"+dto.getEvidenciaId()+"'>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>");
				if(dto.getCadenaDeCustodia()!=null){
					writer.print((dto.getCadenaDeCustodia()!= null &&
							dto.getCadenaDeCustodia().getExpedienteDTO() != null && 
							dto.getCadenaDeCustodia().getExpedienteDTO().getCasoDTO() != null &&
							dto.getCadenaDeCustodia().getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso() != null ? 
						    dto.getCadenaDeCustodia().getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso(): "-")); 
				}else {
					writer.print("-"); 
				}				
				writer.print("</div>]]></cell>");
				
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>");
				if(dto.getCadenaDeCustodia().getExpedienteDTO()!=null){
					writer.print((dto.getCadenaDeCustodia()!= null &&
							dto.getCadenaDeCustodia().getExpedienteDTO() != null && 
							dto.getCadenaDeCustodia().getExpedienteDTO().getNumeroExpediente() != null  ? 
							dto.getCadenaDeCustodia().getExpedienteDTO().getNumeroExpediente(): "-")); 
				}else {
					writer.print("-"); 
				}				
				writer.print("</div>]]></cell>");
				
				
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>");
				if(dto.getCadenaDeCustodia()!=null){
					writer.print(dto.getCadenaDeCustodia().getFolio()); 
				}else {
					writer.print("-"); 
				}				
				writer.print("</div>]]></cell>");
				
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>");
				if(dto.getNumeroEvidencia()!=null){
					writer.print(dto.getEvidenciaId()); 
				}else {
					writer.print("-"); 
				}				
				writer.print("</div>]]></cell>");
				

				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>");
				if(dto.getDescripcion()!=null){
					writer.print(dto.getDescripcion()); 
				}else {
					writer.print("-"); 
				}
				writer.print("</div>]]></cell>");
				
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>");
				if(dto.getEstatus()!=null){
					writer.print(dto.getEstatus().getValor()); 
				}else {
					writer.print("-"); 
				}
				writer.print("</div>]]></cell>");
				
				writer.print("</row>");
			}
			
			
		writer.print("</rows>");
		writer.flush();
		writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}	
	
	
	
	
	public ActionForward consultarAlmacenXID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("Ejecutando Action Asociar Almacen a Funcionario:::::");
			
			Long idAlmacen = NumberUtils.toLong(request.getParameter("idAlmacen"));
			log.info("Id del Almacen::::::" + idAlmacen);
			AlmacenDTO almacenDTO = new AlmacenDTO();
			almacenDTO = almacenDelegate.consultarDetalleAlmacenPorId(idAlmacen);
			
			converter.alias("almacenDTOs", java.util.List.class);
			converter.alias("almacenDTO", AlmacenDTO.class);
			
			String xml = converter.toXML(almacenDTO);
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
		}
		catch (Exception e) {		
			log.error(e.getCause(),e);
		}
		return null;
	}
	
	public ActionForward consultaUltimoEslabonXEvidenciaYTipo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String idCadena=request.getParameter("idEvidencia");
			Long tipoEslabon = NumberUtils.toLong(request.getParameter("tipoEslabon"),0);
			
			EvidenciaDTO evidenciaDTO=new EvidenciaDTO(Long.parseLong(idCadena));
			EslabonDTO loEslabon = eslabonDelegate.consultaUltimoEslabonXEvidenciaYTipo(evidenciaDTO, tipoEslabon);
			String xml = null;
			converter.alias("eslabonDTO",EslabonDTO.class);
			xml = converter.toXML(loEslabon);
			if(loEslabon!=null){
				escribirRespuesta(response, xml);
			}
			else{
				xml="<error>1</error>";
				log.info(xml);
				escribirRespuesta(response, xml);
			}
		}
		catch(Exception ex)
		{
			log.error(ex.getMessage(), ex);
			String xml="<exito><bandera>0</bandera></exito>";
			log.info(xml);
			escribirError(response,null);
		}
		return null;
	}
	
	
	/**
	 * funcion para consultar los datos Documentos asociados a un Eslabon
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarDocumentosXIdEslabon(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		try {
			String idEslabon=request.getParameter("idEslabon");
			
			log.info("$$$$ ID de Eslabon $$$ : "+idEslabon);
			EslabonDTO eslabonDTO = new EslabonDTO();
			eslabonDTO.setEslabonId(Long.parseLong(idEslabon));
			
			List<DocumentoDTO> listaDocumentoDTOs=eslabonDelegate.consultarDocumentosXIdEslabon(eslabonDTO);
			request.getSession().setAttribute("totalRegistrosDocumentos", listaDocumentoDTOs.size());
			request.setAttribute("totalRegistrosDocumentos", listaDocumentoDTOs.size());
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
		
			writer.print("<rows>");
			int lTotalRegistros=listaDocumentoDTOs.size();
			writer.print("<records>" + lTotalRegistros + "</records>");	
			
			for (DocumentoDTO documentoDTO : listaDocumentoDTOs) {
				writer.print("<row id='"+documentoDTO.getDocumentoId()+"'>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (documentoDTO.getActividadDTO() != null && documentoDTO.getActividadDTO().getUsuario() != null && documentoDTO.getActividadDTO().getUsuario().getArea()!= null
						&& documentoDTO.getActividadDTO().getUsuario().getArea().getNombre()!= null ? documentoDTO.getActividadDTO().getUsuario().getArea().getNombre():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (documentoDTO.getActividadDTO() != null && documentoDTO.getActividadDTO().getFechaCreacion() != null? DateUtils.formatear(documentoDTO.getActividadDTO().getFechaCreacion()):"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+ (documentoDTO.getActividadDTO() != null && documentoDTO.getActividadDTO().getNombre() != null? documentoDTO.getActividadDTO().getNombre():"-") +" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+documentoDTO.getTipoDocumentoDTO().getValor()+" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+documentoDTO.getNombreDocumento()+" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+documentoDTO.getStrFechaCreacion()+" </div>]]></cell>");
				writer.print("</row>");
			}
		writer.print("</rows>");
		writer.flush();
		writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	
	public ActionForward asociarEvidenciasAAlmacen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			try {
				String idsEvidencias=request.getParameter("idsEvidencias");
				log.info("idsEvidencias:"+idsEvidencias);
				
	            Long almacenId = NumberUtils.toLong(request.getParameter("almacenId"),0);
				log.info("almacenId:"+idsEvidencias);
				
				List<EvidenciaDTO> evidenciasDTO = new ArrayList<EvidenciaDTO>();
				
				if(idsEvidencias != null && !idsEvidencias.equals("")){
					//Permite recuperar los id´s de las evidencias
					String[] listaIdsEvidencias= idsEvidencias.split(",");
					
					for (String idEvidencia : listaIdsEvidencias) {
						evidenciasDTO.add(new EvidenciaDTO(Long.parseLong(idEvidencia)));
					}				
				}	
				
				evidenciaDelegate.asociarEvidenciasPorAlmacen(evidenciasDTO, new AlmacenDTO(almacenId));

				String xml = "<respuesta><bandera>1</bandera></respuesta>";
				if(log.isDebugEnabled())
				{
					log.info(xml);
				}
				escribirRespuesta(response, xml);
			} catch (Exception e) {		
				log.error(e.getMessage(),e);
				String xml = "<respuesta><bandera>0</bandera></respuesta>";
				escribirRespuesta(response, xml);
			}
			return null;
	}
	
	
	public ActionForward consultaEslabonPorId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			Long idEslabon = NumberUtils.toLong(request.getParameter("idEslabon"),0);
			
			EslabonDTO loEslabon = eslabonDelegate.consultaEslabonPorId(idEslabon);
			String xml = null;
			converter.alias("eslabonDTO",EslabonDTO.class);
			xml = converter.toXML(loEslabon);
			escribirRespuesta(response, xml);
		}
		catch(Exception ex)
		{
			log.error(ex.getMessage(), ex);
			String xml="<exito><bandera>0</bandera></exito>";
			log.info(xml);
			escribirError(response,null);
		}
		return null;
	}
	
	
	
}
