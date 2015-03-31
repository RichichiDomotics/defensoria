/**
* Nombre del Programa : InformePHAction.java
* Autor                            : SergioDC
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 2 Aug 2011
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
package mx.gob.segob.nsjp.web.seguridadPublica.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.involucrado.TipoEvento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.delito.DelitoDelegate;
import mx.gob.segob.nsjp.delegate.detencion.CentroDetencionDelegate;
import mx.gob.segob.nsjp.delegate.detencion.DetencionDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.informepolicial.InformePolicialHomologadoDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.notificacion.NotificacionDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.detencion.CentroDetencionDTO;
import mx.gob.segob.nsjp.dto.detencion.PertenenciaDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoHechoDelictivoDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.LugarDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.domicilio.TipoAsentamientoDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.informepolicial.InformePolicialHomologadoDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.ReporteBaseAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Action para invocar servicios de Registro de Detenci&oacute;n.
 * @version 1.0
 * @author EduardoG
 *
 */
public class RegistroDetencionAction extends ReporteBaseAction{
	private static final Logger log  = Logger.getLogger(RegistroDetencionAction.class);
	
	@Autowired
	private CentroDetencionDelegate centroDetencionDelegate;
	@Autowired
	private InvolucradoDelegate involucradoDelegate;
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	@Autowired
	private NotificacionDelegate notificacionDelegate;
	@Autowired
	private DetencionDelegate detencionDelegate;
	@Autowired
	private InformePolicialHomologadoDelegate informePolicialHomologadoDelegate;
	@Autowired
	private DelitoDelegate delitoDelegate;
	@Autowired
	private CatalogoDelegate catalogoDelegate;
	
	
	
	/**
	 * M&eacute;todo utilizado para Consultar el Catalogo de Centros de Detenci&oacute;n.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoCentrosDetencion
				(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.debug("ejecutando Action consultar Catalogo Centros de Detencion");
			Long centroDetencion = new Long(request.getParameter("centroDetencion"));
						
			List<CentroDetencionDTO> centroDetencionList = centroDetencionDelegate.consultarCentrosDetencionPorTipo(centroDetencion);
			converter.alias("CentroDetencionDTO", CentroDetencionDTO.class);
			String xml = converter.toXML(centroDetencionList);
			
			log.debug("centroDetencion [" + centroDetencion + "] respuesta consultar catalogo de Centros de Detencion ------- "+xml);

			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo utilizado para consultar los expedientes que contienen un folio de hecho delictivo de acuerdo a su estatus.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarRegistroDeDetencionPorEstatus
				(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.debug("ejecutando Action consultar Registro de Detencion por estatus");
						
			Long estatusExpediente = new Long(request.getParameter("estatus"));
			
			List<ExpedienteDTO> lstExpedienteDTO = new ArrayList<ExpedienteDTO>();
			
			log.debug("ESTATUS A CONSULTAR:" + estatusExpediente);
			UsuarioDTO usuario = super.getUsuarioFirmado(request);
			
			if(estatusExpediente.equals(EstatusExpediente.ABIERTO.getValorId())){
				//lstExpedienteDTO = expedienteDelegate.consultarNumeroExpedienteByEstatus(EstatusExpediente.ABIERTO);
				lstExpedienteDTO = expedienteDelegate.consultarNumeroDeExpedienteConHechoPorFiltros(EstatusExpediente.ABIERTO, usuario, null, null);
			}
			if(estatusExpediente.equals(EstatusExpediente.POR_ATENDER.getValorId())){
				//lstExpedienteDTO = expedienteDelegate.consultarNumeroExpedienteByEstatus(EstatusExpediente.ABIERTO);
				lstExpedienteDTO = expedienteDelegate.consultarNumeroDeExpedienteConHechoPorFiltros(EstatusExpediente.POR_ATENDER, usuario, null, null);
			}
			if(estatusExpediente.equals(EstatusExpediente.ATENDIDAS.getValorId())){
				//lstExpedienteDTO = expedienteDelegate.consultarNumeroExpedienteByEstatus(EstatusExpediente.NO_ATENDIDO);
				lstExpedienteDTO = expedienteDelegate.consultarNumeroDeExpedienteConHechoPorFiltros(EstatusExpediente.ATENDIDAS, usuario, null, null);
			}
			if(estatusExpediente.equals(EstatusExpediente.CANALIZADO.getValorId())){
				//lstExpedienteDTO = expedienteDelegate.consultarNumeroExpedienteByEstatus(EstatusExpediente.CANALIZADO);
				lstExpedienteDTO = expedienteDelegate.consultarNumeroDeExpedienteConHechoPorFiltros(EstatusExpediente.CANALIZADO, usuario, null, null);
			}
			if(estatusExpediente.equals(EstatusExpediente.CERRADO.getValorId())){
				//lstExpedienteDTO = expedienteDelegate.consultarNumeroExpedienteByEstatus(EstatusExpediente.CANALIZADO);
				lstExpedienteDTO = expedienteDelegate.consultarNumeroDeExpedienteConHechoPorFiltros(EstatusExpediente.CERRADO, usuario, null, null);
			}
			
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

			for (ExpedienteDTO expedienteDTO : lstExpedienteDTO) {

				log.info("expedienteDTO ... " + expedienteDTO);

				writer.print("<row id='" + expedienteDTO.getExpedienteId()
						+ "'>");

				writer.print("<cell>" + expedienteDTO.getNumeroExpediente()
						+ "</cell>");

				writer.print("<cell>");
					if (expedienteDTO.getHechoDTO() != null
							&& expedienteDTO.getHechoDTO().getAvisoHechoDelictivo() != null
							&& expedienteDTO.getHechoDTO().getAvisoHechoDelictivo()
									.getFolioNotificacion() != null) {
						writer.print(expedienteDTO.getHechoDTO()
								.getAvisoHechoDelictivo().getFolioNotificacion());
					} else {
						writer.print("---");
					}
				writer.print("</cell>");

				writer.print("<cell>");
					if (expedienteDTO.getHechoDTO() != null
							&& expedienteDTO.getHechoDTO().getAvisoHechoDelictivo() != null
							&& expedienteDTO.getHechoDTO().getAvisoHechoDelictivo()
									.getFechaCreacion() != null) {
						writer.print(DateUtils.formatear(expedienteDTO
								.getHechoDTO().getAvisoHechoDelictivo()
								.getFechaCreacion()));
					} else {
						writer.print("---");
					}
				writer.print("</cell>");

				writer.print("<cell>");
					if (expedienteDTO.getHechoDTO() != null
							&& expedienteDTO.getHechoDTO().getAvisoHechoDelictivo()
									.getCatDelito() != null
							&& expedienteDTO.getHechoDTO().getAvisoHechoDelictivo()
									.getCatDelito().getNombre() != null) {
						writer.print(expedienteDTO.getHechoDTO()
								.getAvisoHechoDelictivo().getCatDelito()
								.getNombre());
					} else {
						writer.print("---");
					}
				writer.print("</cell>");

				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo utilizado para consultar los expedientes que contienen un folio de hecho delictivo de acuerdo a su estatus.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarDetencionPorEstatus
				(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.debug("EJECUTANDO ACTION:::CONSULTAR DETENCION POR ESTATUS");
						
			Long estatusExpediente = new Long(request.getParameter("estatus"));
			
			List<ExpedienteDTO> lstExpedienteDTO = new ArrayList<ExpedienteDTO>();
			
			log.debug("ESTATUS A CONSULTAR:" + estatusExpediente);
			UsuarioDTO usuario = super.getUsuarioFirmado(request);
			
			if(estatusExpediente.equals(EstatusExpediente.ABIERTO.getValorId())){
				lstExpedienteDTO = expedienteDelegate.consultarNumeroDeExpedienteSinHechoPorFiltros(EstatusExpediente.ABIERTO, usuario, null, null);
			}
			if(estatusExpediente.equals(EstatusExpediente.POR_ATENDER.getValorId())){
				lstExpedienteDTO = expedienteDelegate.consultarNumeroDeExpedienteSinHechoPorFiltros(EstatusExpediente.POR_ATENDER, usuario, null, null);
			}
			if(estatusExpediente.equals(EstatusExpediente.ATENDIDAS.getValorId())){
				lstExpedienteDTO = expedienteDelegate.consultarNumeroDeExpedienteSinHechoPorFiltros(EstatusExpediente.ATENDIDAS, usuario, null, null);
			}
			if(estatusExpediente.equals(EstatusExpediente.CANALIZADO.getValorId())){
				lstExpedienteDTO = expedienteDelegate.consultarNumeroDeExpedienteSinHechoPorFiltros(EstatusExpediente.CANALIZADO, usuario, null, null);
			}
			if(estatusExpediente.equals(EstatusExpediente.CERRADO.getValorId())){
				lstExpedienteDTO = expedienteDelegate.consultarNumeroDeExpedienteSinHechoPorFiltros(EstatusExpediente.CERRADO, usuario, null, null);
			}
			
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

			for (ExpedienteDTO expedienteDTO : lstExpedienteDTO) {

				log.info("expedienteDTO ... " + expedienteDTO);

				writer.print("<row id='" + expedienteDTO.getExpedienteId() + "'>");
				writer.print("<cell>" + expedienteDTO.getNumeroExpediente() + "</cell>");				
				writer.print("<cell>" + expedienteDTO.getStrFechaApertura()	+ "</cell>");
				
				if(expedienteDTO.getEstatusNumeroExpediente()!=null &&
				   expedienteDTO.getEstatusNumeroExpediente().getValor()!=null){
					writer.print("<cell>" + expedienteDTO.getEstatusNumeroExpediente().getValor() + "</cell>");
				}else{
					writer.print("<cell> --- </cell>");
				}

				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para consultar un Registro de Detenci&oacute;n de acuerdo a un expediente seleccionado.
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward obtenerAvisoPorIdExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("Consultando los datos de un Registro de Detencion por ExpedienteId");
			Long expedienteId = new Long(request.getParameter("expedienteId"));
			String numeroExpediente = request.getParameter("numeroExpediente");
			log.debug("expedienteId ... " + expedienteId);
			log.debug("numeroExpediente ... " + numeroExpediente);
			
			AvisoHechoDelictivoDTO avisoHechoDelictivoDTO = new AvisoHechoDelictivoDTO();
			avisoHechoDelictivoDTO = notificacionDelegate.obtenerAvisoPorIdExpediente(expedienteId);
			UsuarioDTO usuario = getUsuarioFirmado(request);
			avisoHechoDelictivoDTO.setUsuario(usuario);
			converter.alias("avisoHechoDelictivoDTO", AvisoHechoDelictivoDTO.class);
			String xml = converter.toXML(avisoHechoDelictivoDTO);
			log.debug(xml);
			escribir(response, xml, null);
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
		
	/**
	 * Metodo utilizado para guardar los datos de un Probable Responsable.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward agregarProbableResponsable(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando Action agregarProbableResponsable");			
			Long expedienteId = new Long(request.getParameter("expedienteId"));
			String numeroExpediente = request.getParameter("numeroExpediente");
			Long idCatDelito =NumberUtils.toLong(request.getParameter("idCatDelito"),0) ;
			Long tipoEvento =NumberUtils.toLong(request.getParameter("tipoEvento"),0) ;
			Long subtipoDeEvento =NumberUtils.toLong(request.getParameter("subtipoDeEvento"),0) ;

			
			log.info("Identificador del CatDelito: "+idCatDelito);
			log.debug("expedienteId ... " + expedienteId);
			log.debug("numeroExpediente ... " + numeroExpediente);
			log.debug("tipoEvento ... " + tipoEvento);
			log.debug("subtipoDeEvento ... " + subtipoDeEvento);
			
			
			String nombre = request.getParameter("nombre");
			String apellidoPaterno = request.getParameter("apellidoPaterno");
			String apellidoMaterno = request.getParameter("apellidoMaterno");
			String detencion = request.getParameter("detencion");
			String desconocido = request.getParameter("desconocido");
			String fechaDetencion = request.getParameter("fechaDetencion");
			String horarioDetencion = request.getParameter("horarioDetencion");

			InvolucradoDTO involucradoDTO = new InvolucradoDTO();
			ExpedienteDTO expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
			involucradoDTO.setExpedienteDTO(expedienteDTO);
			CalidadDTO calidadDTO = new CalidadDTO();			
			calidadDTO.setCalidades(Calidades.PROBABLE_RESPONSABLE_PERSONA);
			involucradoDTO.setCalidadDTO(calidadDTO);
			involucradoDTO.setTipoPersona(1L);
			
			//IngresarIndividuoForm forma = (IngresarIndividuoForm) form;
			
			log.info("Anonimo:"+desconocido);
			involucradoDTO.setDesconocido(desconocido.equals("Si") ? "true" : "false");

			log.info("Detenido:"+detencion);					
			involucradoDTO.setEsDetenido(detencion.equals("Si") ? true : false);
			
			if(detencion.equals("Si")){
				log.info("Entra a setear datos si esta detenido:"+detencion);						
				ArrayList<DetencionDTO> detencionDTOs = new ArrayList<DetencionDTO>();
				DetencionDTO detencionDTO = new DetencionDTO();
				detencionDTO.setFechaInicioDetencion(fechaDetencion);
				detencionDTO.setHoraInicioDetencion(horarioDetencion);
				detencionDTOs.add(detencionDTO);
				involucradoDTO.setDetenciones(detencionDTOs);
			}
			
			List<NombreDemograficoDTO> lstDatosGenerales = new ArrayList<NombreDemograficoDTO>();
			NombreDemograficoDTO datosGenerales = new NombreDemograficoDTO();
			datosGenerales.setNombre(nombre);
			datosGenerales.setApellidoPaterno(apellidoPaterno);
			datosGenerales.setApellidoMaterno(apellidoMaterno);
			lstDatosGenerales.add(datosGenerales);
			involucradoDTO.setNombresDemograficoDTO(lstDatosGenerales);
			involucradoDTO.setTipoEvento(tipoEvento.shortValue());
			involucradoDTO.setSubtipoDeEvento(subtipoDeEvento.shortValue());

			log.info("InvolucradoDTO ... " + involucradoDTO);
			
			Long idInvolucrado=null;
			idInvolucrado = involucradoDelegate.guardarInvolucrado(involucradoDTO);
			
			if(idCatDelito > 0 ){
				DelitoDTO loDelitoDTO = new DelitoDTO();
				loDelitoDTO.setEsPrincipal(true);
				loDelitoDTO.setEsProbable(true);
				loDelitoDTO.setCatDelitoDTO(new CatDelitoDTO(idCatDelito));

				delitoDelegate.asociarDelitoResponsableVictima(null,loDelitoDTO,
						new InvolucradoDTO(idInvolucrado), null, null, null, null, null, null, null, null, idInvolucrado);
				
			}

			
			log.info("el valor de la respuesta es:" + idInvolucrado);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.flush();
			writer.close();			
		} catch (Exception e) {
			log.info(e.getCause(), e);
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para realizar la carga del combo Calidad
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarProbableResponsablePorNumeroExpediente(
				ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando Action consultarProbableResponsablePorExpediente");
			Long expedienteId = new Long(request.getParameter("expedienteId"));
			String numeroExpediente = request.getParameter("numeroExpediente");
			log.debug("expedienteId ... " + expedienteId);
			log.debug("numeroExpediente ... " + numeroExpediente);

			List<InvolucradoDTO> lstInvolucradoDTO = new ArrayList<InvolucradoDTO>();
			
			
			Calidades[] calidades = new Calidades[]{Calidades.PROBABLE_RESPONSABLE_PERSONA}; 
			lstInvolucradoDTO = involucradoDelegate.obtenerInvolucradosPorExpedienteYCalidades(numeroExpediente,calidades,true,null); 
				
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

			for(InvolucradoDTO involucradoDTO : lstInvolucradoDTO){
				
				writer.print("<row id='"+involucradoDTO.getElementoId()+ "'>");
				writer.print("<cell>"+ involucradoDTO.getNombresDemograficoDTO().get(0).getNombre() +"</cell>");
				writer.print("<cell>"+ involucradoDTO.getNombresDemograficoDTO().get(0).getApellidoPaterno() +"</cell>");
				writer.print("<cell>"+ involucradoDTO.getNombresDemograficoDTO().get(0).getApellidoMaterno() +"</cell>");
				
				writer.print("<cell>");
				if(involucradoDTO.getEsDetenido()){
					writer.print("Si");
				}else{
					writer.print("No");
				}
				writer.print("</cell>");

				writer.print("<cell>");
				if(involucradoDTO.getEsDetenido() && involucradoDTO.getDetenciones() != null && !involucradoDTO.getDetenciones().isEmpty()){
					writer.print(DateUtils.formatear(involucradoDTO.getDetenciones().get(0).getFechaInicioDetencion()) 
								 + " " 
								 + involucradoDTO.getDetenciones().get(0).getHoraInicioDetencion());
				}else{
					writer.print("---");
				}
				writer.print("</cell>");
				
				writer.print("<cell>");
				if(involucradoDTO.getNombresDemograficoDTO().get(0).getEdadAproximada() != null){
					writer.print(involucradoDTO.getNombresDemograficoDTO().get(0).getEdadAproximada() >= 18 ? "Si" : "No");
				}else{
					writer.print("No");
				}
				writer.print("</cell>");
				

				writer.print("<cell>");
				if(involucradoDTO.getTipoEvento() != null && involucradoDTO.getTipoEvento() > 0){
					writer.print(involucradoDTO.getTipoEvento());
				}else{
					writer.print("0");
				}
				writer.print("</cell>");				

				
				
				writer.print("<cell>");
				if(involucradoDTO.getTipoEvento() != null && (involucradoDTO.getTipoEvento() == TipoEvento.DELITO.getId().shortValue())){
					writer.print(TipoEvento.DELITO.getDescripcion());
				}else{
					writer.print(TipoEvento.FALTA_ADMINISTRATIVA.getDescripcion());
				}
				writer.print("</cell>");				
				
				
				writer.print("<cell>");
				if(involucradoDTO.getSubtipoDeEvento() != null && involucradoDTO.getSubtipoDeEvento() > 0){
					writer.print(involucradoDTO.getSubtipoDeEvento());
				}else{
					writer.print("0");
				}
				writer.print("</cell>");
				
				
				writer.print("<cell>");
				if(involucradoDTO.getTipoEvento() != null && (involucradoDTO.getTipoEvento() == TipoEvento.DELITO.getId().shortValue())){
					//Se obtiene el nombre del delito
					if(involucradoDTO.getSubtipoDeEvento() != null && involucradoDTO.getSubtipoDeEvento() > 0)
						writer.print(catalogoDelegate.consultarDelito(involucradoDTO.getSubtipoDeEvento().longValue()).getNombre());
					else
						writer.print("-");
				}else{
					//Se obtiene el nombre del area Administrativa
					if(involucradoDTO.getSubtipoDeEvento() != null && involucradoDTO.getSubtipoDeEvento() > 0)
						writer.print(catalogoDelegate.consultarFaltaAdministrativa(involucradoDTO.getSubtipoDeEvento().longValue()).getNombreFalta());
					else
						writer.print("-");
				}
				writer.print("</cell>");
				
				
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para eliminar un Probable Responsable.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward eliminarProbableResponsable(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando Action eliminarProbableResponsable");
			
			Long involucradoId = new Long(request.getParameter("involucradoId"));
			detencionDelegate.eliminarInvolucrado(new InvolucradoDTO(involucradoId));
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.flush();
			writer.close();			
		} catch (Exception e) {
			log.info(e.getCause(), e);
		}
		return null;
	}

	/**
	 * M&eacute;todo utilizado para consultar las detenciones de acuerdo a un expediente proporcionado.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarLugarDeDetencionPorNumeroExpediente
				(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.debug("ejecutando Action consultar Lugar de Detencion por Numero de Expediente");						
			String numeroExpediente = request.getParameter("numeroExpediente");
			log.debug("numeroExpediente ... " + numeroExpediente);
			ExpedienteDTO expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
			log.debug("dto [expediente] ... " + expedienteDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			List<InvolucradoDTO> lstInvolucradoDTO = expedienteDTO.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA);

			writer.print("<rows>");		

			int lTotalRegistros = 0;

			for(InvolucradoDTO involucradoDTO : lstInvolucradoDTO){
				DetencionDTO detencionDTO = (involucradoDTO.getDetenciones() != null && involucradoDTO.getDetenciones() != null && involucradoDTO.getDetenciones().size() > 0? involucradoDTO.getDetenciones().get(0): null);
				if(detencionDTO != null){
					if(involucradoDTO.getEsDetenido() && detencionDTO.getLugarDetencionDTO() != null){
						writer.print("<row id='"+involucradoDTO.getElementoId()+ "'>");
						
						if(involucradoDTO.getNombresDemograficoDTO() != null && involucradoDTO.getNombresDemograficoDTO().get(0) != null){
							writer.print("<cell>"+ (involucradoDTO.getNombresDemograficoDTO().get(0).getNombre() != null? involucradoDTO.getNombresDemograficoDTO().get(0).getNombre(): "") 
									 + " " 
									 + (involucradoDTO.getNombresDemograficoDTO().get(0).getApellidoPaterno() != null ? involucradoDTO.getNombresDemograficoDTO().get(0).getApellidoPaterno() : "")
									 + " "
									 + (involucradoDTO.getNombresDemograficoDTO().get(0).getApellidoMaterno()!= null ? involucradoDTO.getNombresDemograficoDTO().get(0).getApellidoMaterno() : "")
									 +"</cell>");
							writer.print("<cell>");
						}else
							writer.print("<cell>---</cell>");
						
						if(detencionDTO.getLugarDetencionDTO() != null){
							writer.print(detencionDTO.getObservaciones());
						}else{
							writer.print("---");
						}
						writer.print("</cell>");
						
						writer.print("</row>");
						
						lTotalRegistros = lTotalRegistros + 1;
					}
				}

			}
			
			writer.print("<records>" + lTotalRegistros + "</records>");
			writer.print("</rows>");				
			
			writer.flush();
			writer.close();

		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para registrar el lugar de detencion de un 
	 * Probable responsable
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward agregarLugarDeDetencion(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			log.debug("ejecutando Action agregar Lugar de Detencion");						
			String numeroExpediente = request.getParameter("numeroExpediente");
			Long involucradoId = NumberUtils.toLong(request.getParameter("involucradoId"), 0L);
			Long lugarDetencionId = NumberUtils.toLong(request.getParameter("lugarDetencionId"),0L);
			
			log.debug("numeroExpediente ... " + numeroExpediente);
			log.debug("involucradoId ... " + involucradoId);
			String entidadFederativa = request.getParameter("entidadFederativa");
			String pais = request.getParameter("pais");
			String delegacionMunicipio = request.getParameter("delegacionMunicipio");
			String ciudad = request.getParameter("ciudad");
			String asentamientoColonia = request.getParameter("asentamientoColonia");
			String tipoAsentamiento = request.getParameter("tipoAsentamiento");
			String calle = request.getParameter("calle");
			String numExterior = request.getParameter("numExterior");
			String numInterior = request.getParameter("numInterior");
			String entreCalle = request.getParameter("entreCalle"); 
			String ycalle = request.getParameter("ycalle");
			String aliasDomicilio = request.getParameter("aliasDomicilio");
			String edificio = request.getParameter("edificio");
			String referencias = request.getParameter("referencias");
			String tipoCalle = request.getParameter("tipoCalle");
			Long resp=0L;
			
			ExpedienteDTO expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
			log.debug("dto [expediente] ... " + expedienteDTO);
			
			List<InvolucradoDTO> lstInvolucradoDTO = expedienteDTO.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA); 
			
			for(InvolucradoDTO involucradoDTO : lstInvolucradoDTO){
				if(involucradoDTO.getElementoId().equals(involucradoId)){
					
					DomicilioDTO domicilioDTO = new DomicilioDTO();
					EntidadFederativaDTO entidadDTO = new EntidadFederativaDTO();
					ValorDTO valorGenerico = new ValorDTO();
					MunicipioDTO municipioDTO = new MunicipioDTO();
					CiudadDTO ciudadDTO = new CiudadDTO();
					AsentamientoDTO asentamientoDTO = new AsentamientoDTO();

					if(lugarDetencionId != null && lugarDetencionId > 0L){
						domicilioDTO.setElementoId(lugarDetencionId);
					}
					
					if (!entidadFederativa.equals("") && !entidadFederativa.equals("-1")) {
						entidadDTO.setEntidadFederativaId(new Long(entidadFederativa));
					}

					if (!pais.equals("") && !pais.equals("-1")) {
						valorGenerico = new ValorDTO();
						valorGenerico.setValor(pais);
						entidadDTO.setValorIdPais(valorGenerico);
					}
					
					if (!delegacionMunicipio.equals("") && !delegacionMunicipio.equals("-1")) {
						municipioDTO = new MunicipioDTO(new Long(delegacionMunicipio), "", entidadDTO);
					}
					
					if (!ciudad.equals("") && !ciudad.equals("-1")) {
						ciudadDTO.setCiudadId(new Long(ciudad));
					}
					
					if (!asentamientoColonia.equals("")	&& !asentamientoColonia.equals("-1")) {
						asentamientoDTO.setAsentamientoId(new Long(asentamientoColonia));
					}
					if (!tipoAsentamiento.equals("") && !tipoAsentamiento.equals("-1")) {
						TipoAsentamientoDTO tipoAsentamientoDTO = new TipoAsentamientoDTO(Long.parseLong(tipoAsentamiento), "");
						asentamientoDTO.setTipoAsentamientoDTO(tipoAsentamientoDTO);
					}
					
					if (tipoCalle.equals("") && !tipoCalle.equals("-1")) {
						domicilioDTO.setValorCalleId(new ValorDTO(new Long(
								tipoCalle)));// Tipo de calle
					}		
					
					domicilioDTO.setCalle(calle);
					domicilioDTO.setNumeroExterior(numExterior);
					domicilioDTO.setNumeroInterior(numInterior);
					domicilioDTO.setEntreCalle1(entreCalle);
					domicilioDTO.setEntreCalle2(ycalle);
					domicilioDTO.setAlias(aliasDomicilio);
					domicilioDTO.setEdificio(edificio);
					domicilioDTO.setReferencias(referencias);					
					domicilioDTO.setEntidadDTO(entidadDTO);
					domicilioDTO.setMunicipioDTO(municipioDTO);
					domicilioDTO.setCiudadDTO(ciudadDTO);
					domicilioDTO.setAsentamientoDTO(asentamientoDTO);
					
					CalidadDTO calidadDTO = new CalidadDTO();
					calidadDTO.setCalidades(Calidades.DOMICILIO);
					domicilioDTO.setCalidadDTO(calidadDTO);

					involucradoDTO.getDetenciones().get(0).setObservaciones(domicilioDTO.getCalle() + " " + domicilioDTO.getNumeroExterior());
					involucradoDTO.getDetenciones().get(0).setLugarDetencionDTO(domicilioDTO);
					
					if(domicilioDTO.getElementoId() != null && domicilioDTO.getElementoId() > 0L){
						detencionDelegate.modificarDomicilioDetencion(domicilioDTO);
						resp = domicilioDTO.getElementoId();
						log.info("OBJETO DOMICILIO ID MODIFICADO:" + domicilioDTO.getElementoId());
					}else{
						resp = detencionDelegate.registrarLugarDetencion(involucradoDTO.getDetenciones().get(0));
						log.info("OBJETO DOMICILIO ID GUARDARDO:" + resp);
					}
				}
			}
			converter.alias("lugarId", String.class);
			String xml = converter.toXML(resp.toString());			
			log.info("lugar_Id:: " + xml);
			escribirRespuesta(response, xml);
			
		} catch (Exception e) {
			log.info(e.getCause(), e);
		}
		return null;
	}

	/**
	 * Metodo utilizado para cancelar una Detenci&oacute;n.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward eliminarDetencion(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando Action eliminarDetencion");
		} catch (Exception e) {
			log.info(e.getCause(), e);
		}
		return null;
	}

	/**
	 * M&eacute;todo utilizado para consultar las pertenencias de probables resonsables de acuerdo a un expediente proporcionado.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarPertenenciasPorDetencionId
				(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.debug("ejecutando Action consultar Pertenencias por Expediente");
						
			Long expedienteId = new Long(request.getParameter("expedienteId"));
			String numeroExpediente = request.getParameter("numeroExpediente");
			Long idInvolucrado = new Long(request.getParameter("idInvolucrado"));


			log.debug("expedienteId ... " + expedienteId);
			log.debug("numeroExpediente ... " + numeroExpediente);
			log.debug("idInvolucrado ... " + idInvolucrado);
			
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			
 
			InvolucradoDTO involucradoDTO = involucradoDelegate.obtenerInvolucrado(new InvolucradoDTO(idInvolucrado)); 
			


			if(involucradoDTO.getEsDetenido()){
				Long detencionId = involucradoDTO.getDetenciones().get(0).getDetencionId();
				List<PertenenciaDTO> lstPertenenciaDTO = detencionDelegate.consultarPertenenciaByDetencion(detencionId);
				
				writer.print("<rows>");
				final PaginacionDTO pag = PaginacionThreadHolder.get();
	            if (pag != null && pag.getTotalRegistros() != null) {
	                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
	                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
	            } else {
	                writer.print("<total>0</total>");
	                writer.print("<records>0</records>");
	            }
				
				
				for(PertenenciaDTO pertenenciaDTO : lstPertenenciaDTO){					
					writer.print("<row id='"+pertenenciaDTO.getPertenenciaId()+ "'>");

					writer.print("<cell>");
					if(involucradoDTO.getNombreCompleto() != null){
						writer.print(involucradoDTO.getNombreCompleto());
					}else{
						writer.print(involucradoDTO.getNombresDemograficoDTO().get(0).getNombre()
								     + " " + involucradoDTO.getNombresDemograficoDTO().get(0).getApellidoPaterno()
								     + " " + involucradoDTO.getNombresDemograficoDTO().get(0).getApellidoMaterno());
					}
					writer.print("</cell>");
					
					writer.print("<cell>");
					if(pertenenciaDTO.getCantidad() != null){
						writer.print(pertenenciaDTO.getCantidad());
					}else{
						writer.print("---");
					}
					writer.print("</cell>");

					writer.print("<cell>");
					if(pertenenciaDTO.getTipoPertenencia() != null){
						writer.print(pertenenciaDTO.getTipoPertenencia().getValor());
					}else{
						writer.print("---");
					}
					writer.print("</cell>");

					writer.print("<cell>");
					if(pertenenciaDTO.getCondicionFisica() != null){
						writer.print(pertenenciaDTO.getCondicionFisica().getValor());
					}else{
						writer.print("---");
					}
					writer.print("</cell>");
					
					writer.print("<cell>");
					if(pertenenciaDTO.getDescripcion() != null){
						writer.print(pertenenciaDTO.getDescripcion());
					}else{
						writer.print("---");
					}
					writer.print("</cell>");
										
					writer.print("</row>");

				}
			}
			writer.print("</rows>");				
			
			writer.flush();
			writer.close();

		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para agregar una pertenencia del Probable Responsable. 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward agregarPertenencia(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			
			log.debug("ejecutando Action agregar Pertenencias por Expediente");
			
			Long expedienteId = new Long(request.getParameter("expedienteId"));
			String numeroExpediente = request.getParameter("numeroExpediente");
			log.debug("expedienteId ... " + expedienteId);
			log.debug("numeroExpediente ... " + numeroExpediente);
			Long involucradoId = new Long(request.getParameter("probableResponsableId"));
			Float cantidad = new Float(request.getParameter("cantidad"));
			Long tipoId = new Long(request.getParameter("tipoId"));
			Long condicionFisicaId = new Long(request.getParameter("condicionFisicaId"));
			String descripcion = request.getParameter("descripcion");
			
			ExpedienteDTO expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
			log.debug("dto [expediente] ... " + expedienteDTO);
			
			List<InvolucradoDTO> lstInvolucradoDTO = expedienteDTO.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA); 
			for(InvolucradoDTO involucradoDTO : lstInvolucradoDTO){
				if(involucradoDTO.getElementoId().equals(involucradoId)){
					PertenenciaDTO pertenenciaDTO = new PertenenciaDTO();
					pertenenciaDTO.setCantidad(cantidad);
					ValorDTO valorDTO = new ValorDTO();
					valorDTO.setIdCampo(tipoId);
					pertenenciaDTO.setTipoPertenencia(valorDTO);
					valorDTO = new ValorDTO();
					valorDTO.setIdCampo(condicionFisicaId);
					pertenenciaDTO.setCondicionFisica(valorDTO);
					pertenenciaDTO.setDescripcion(descripcion);
					pertenenciaDTO.setDetencion(involucradoDTO.getDetenciones().get(0));					
					
					Long resp=null;
					resp = detencionDelegate.registrarPertenecia(pertenenciaDTO);
					log.info("el valor de la respuesta es:" + resp);
				}
			}
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.flush();
			writer.close();			
		} catch (Exception e) {
			log.info(e.getCause(), e);
		}
		return null;
	}

	/**
	 * Metodo utilizado para cancelar una Pertenencia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward eliminarPertenencia(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando Action eliminarPertenencia");
		} catch (Exception e) {
			log.info(e.getCause(), e);
		}
		return null;
	}

	/**
	 * Metodo utilizado para enviar el registro de detenci&oacute;n a la unidad de captura.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward enviarAUnidadDeCaptura(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			
			log.info("ejecutando Action enviarAUnidadDeCaptura");
			
			String numeroExpediente = request.getParameter("numeroExpediente");
			log.debug("numeroExpediente ... " + numeroExpediente);			
			ExpedienteDTO expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
			log.debug("dto [expediente] ... " + expedienteDTO);
			
			UsuarioDTO usuario = getUsuarioFirmado(request);
			expedienteDTO.setUsuario(usuario);
			
			InformePolicialHomologadoDTO iph = informePolicialHomologadoDelegate.ObtenerFolioIPH(expedienteDTO);
			super.setExpedienteTrabajo(request, iph.getExpediente());
			log.info("iph.getExpediente().getNumeroExpedienteId() ... " + iph.getExpediente().getNumeroExpedienteId());
			log.info("iph.getExpediente().getExpedienteId() ... " + iph.getExpediente().getExpedienteId());
			//request.getSession().setAttribute("numeroExpedienteId", iph.getExpediente().getNumeroExpedienteId());
			converter.alias("iphDTO", InformePolicialHomologadoDTO.class);
			String xml = converter.toXML(iph);
			log.info("respuesta generar folio IPH ------- "+xml);

			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para mostrar el IPH.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward mostrarIPH(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return null;
	}	

	public ActionForward consultarProbablesResponsablesDetenidosPorNumeroExpediente(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
	try {
			log.info("ejecutando Action consultarProbablesResponsablesDetenidosPorNumeroExpediente");
			Long expedienteId = new Long(request.getParameter("expedienteId"));
			String numeroExpediente = request.getParameter("numeroExpediente");
			log.debug("expedienteId ... " + expedienteId);
			log.debug("numeroExpediente ... " + numeroExpediente);
	
			List<InvolucradoDTO> lstInvolucradoDTO = new ArrayList<InvolucradoDTO>();
			List<InvolucradoDTO> lstInvolucradosDetenidosDTO = new ArrayList<InvolucradoDTO>();		
	
			
			Calidades[] calidades = new Calidades[]{Calidades.PROBABLE_RESPONSABLE_PERSONA}; 
			lstInvolucradoDTO = involucradoDelegate.obtenerInvolucradosPorExpedienteYCalidades(numeroExpediente,calidades,true,null); 
			
			for (InvolucradoDTO involucradoDTO: lstInvolucradoDTO) {
				if(involucradoDTO.getEsDetenido() && involucradoDTO.getDetenciones() != null && !involucradoDTO.getDetenciones().isEmpty())
					lstInvolucradosDetenidosDTO.add(involucradoDTO);
			}
			
			converter.alias("listaInvolucradoDTO", java.util.List.class);
			converter.alias("involucradoDTO", InvolucradoDTO.class);
			String xml = converter.toXML(lstInvolucradosDetenidosDTO);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Metodo para consultar la detencion por el Id del Involucrado
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarDetencionByInvolucradoId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			
			log.info("EJECUTANDO CONSULTAR DETENCION");
			log.info("****************VERIFICANDO PARAMETROS******************");
			log.info("Id involucrado:::::::::"+request.getParameter("idInvolucrado"));
			
			Long idInvolucrado = NumberUtils.toLong(request.getParameter("idInvolucrado"), 0L);
			DetencionDTO detencionDto = new DetencionDTO();
			
			if(idInvolucrado != null && idInvolucrado > 0L){
				 detencionDto = detencionDelegate.consultarDetencion(idInvolucrado,null);
			}
			converter.alias("detencionDTO", DetencionDTO.class);
			/**
			 * Se agrega este alias por la funcion pintaDatosDomicilio
			 * en realidad se trata del lugar de detencion
			 */
			converter.alias("domicilio", LugarDTO.class);
			String xml = converter.toXML(detencionDto);
			log.info("hechoDTO:: " + xml);
			escribirRespuesta(response, xml);
			
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			escribir(response, "", e);
		}
		return null;
	}
	
	/**
	 * Metodo para consultar deomicilio de la detencion por el Id del elemento
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarLugarDetencionById(
			ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			
			log.info("EJECUTANDO CONSULTAR LUGAR DE DETENCION");
			log.info("****************VERIFICANDO PARAMETROS******************");
			log.info("elementoId:::::::::"+request.getParameter("elementoId"));
			
			Long elementoId = NumberUtils.toLong(request.getParameter("elementoId"), 0L);
			DomicilioDTO domicilioDto = new DomicilioDTO();
			
			if(elementoId != null && elementoId > 0L){
				domicilioDto = detencionDelegate.consultarDomicilioById(elementoId);
			}
			converter.alias("detencionDTO", DetencionDTO.class);
			/**
			 * Se agrega este alias por la funcion pintaDatosDomicilio
			 * en realidad se trata del lugar de detencion
			 */
			converter.alias("domicilio", DomicilioDTO.class);
			String xml = converter.toXML(domicilioDto);
			log.info("hechoDTO:: " + xml);
			escribirRespuesta(response, xml);
			
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			escribir(response, "", e);
		}
		return null;
	}
	
}
