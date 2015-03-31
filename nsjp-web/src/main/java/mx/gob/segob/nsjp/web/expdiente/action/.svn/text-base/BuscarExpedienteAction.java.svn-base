/**
* Nombre del Programa 	: BuscarExpedientePorAliasAction.java                                    
* Autor               	: Andres Gomez Godinez                                              
* Compania            	: Ultrasist                                                
* Proyecto            	: NSJP              			Fecha:09/03/2011 
* Marca de cambio     	: N/A                                                     
* Descripcion General   : Clase action que implementa las acciones para el CU Buscar Expediente Por Alias
* Programa Dependiente  : N/A                                                      
* Programa Subsecuente 	: N/A                                                      
* Cond. de ejecucion    : struts-config.xml                                                    
* Dias de ejecucion     : N/A                             Horario: N/A
*                               MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                 :N/A                                                           
* Compania              :N/A                                                           
* Proyecto              :N/A                                   Fecha: N/A       
* Modificacion          :N/A                                                           
*------------------------------------------------------------------------------      
*/
package mx.gob.segob.nsjp.web.expdiente.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.bitacora.BitacoraMovimientoDelegate;
import mx.gob.segob.nsjp.delegate.delito.DelitoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.expediente.HistoricoExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.bitacora.BitacoraMovimientoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.expediente.form.BuscarExpedientePorAliasForm;
import mx.gob.segob.nsjp.web.expediente.form.BuscarExpedientePorNombreDePersonaForm;
import mx.gob.segob.nsjp.web.funcionario.form.FuncionarioForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *Clase que implementa las acciones para el CU Buscar Expediente Por Alias
 * @version 1.0
 * @author AndresGG
 */

public class BuscarExpedienteAction extends GenericAction{
	private static final Logger log  = Logger.getLogger(BuscarExpedienteAction.class);
		
	@Autowired
	ExpedienteDelegate expedienteDelegate;
	@Autowired
	BitacoraMovimientoDelegate bitacoraMovimientosDelegate;
	@Autowired
	FuncionarioDelegate funcionarioDelegate;
	@Autowired
	DelitoDelegate delitoDelegate;
	@Autowired
	HistoricoExpedienteDelegate historicoExpedienteDelegate;
	@Autowired
	AsignacionProgramaDelegate asignacionProgramaDelegate;
	
	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorNumeroDeExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
 throws IOException {

		try {

			String noExpediente = request.getParameter("noExpediente");
			String reasignarExpediente = request.getParameter("reasignarExpediente");
			String opcionUI = request.getParameter("opcion");
			Long esCordinadorGeneralAMP = NumberUtils.toLong(request.getParameter("esCordinadorGeneralAMP"),0L);
			if(opcionUI!=null && opcionUI.equals("6")){
				opcionUI=Areas.UNIDAD_INVESTIGACION.parseLong().toString();
			}else{
				opcionUI=null;
			}
			log.info("opcion:"+opcionUI);
			Boolean verHistorico = request.getParameter("verHistorico")!= null ? Boolean.parseBoolean(request.getParameter("verHistorico")): Boolean.FALSE;
			if (log.isDebugEnabled()) {
				log.debug("##################llega noExpediente:::::::::"
						+ noExpediente);
			}
			FiltroExpedienteDTO filtrosBusquedaExpediente = new FiltroExpedienteDTO();
			if(opcionUI!=null){
				filtrosBusquedaExpediente.setFiltroEspecificoDeAreaRolActual(Long.parseLong(opcionUI));
			}
			
			
			UsuarioDTO usuario =super.getUsuarioFirmado(request); 
			Long idFuncionario=usuario.getFuncionario().getClaveFuncionario();
			filtrosBusquedaExpediente.setUsuario(usuario);
			
			UsuarioRolDTO usuarioRolDTO=super.getUsuarioFirmado(request).getRolACtivo();
			Roles rolAsociado = Roles.getByValor(usuarioRolDTO.getRol().getRolId());
			
			switch (rolAsociado) {
				case COORDINADORAMP:{
					filtrosBusquedaExpediente.setIdActividad(Actividades.RECIBIR_CANALIZACION_UI.getValorId());
					filtrosBusquedaExpediente.setIdArea(Areas.UNIDAD_INVESTIGACION.parseLong());
					break;
				}
				case COORDINADORJAR:{
					filtrosBusquedaExpediente.setIdActividad(Actividades.RECIBIR_CANALIZACION_JAR.getValorId());
					filtrosBusquedaExpediente.setIdArea(Areas.FISCAL_FACILITADOR.parseLong());
					break;
				}
				case ATPENAL:
				case FACILITADOR:
				case AGENTEMP:{
					filtrosBusquedaExpediente.setIdFuncionario(super.getUsuarioFirmado(request).getFuncionario().getClaveFuncionario());
					break;
				}
				case DSE :{
					//si es de DSE de  Reinsercion Social se agrega el area del ASE Como subordinada 
					filtrosBusquedaExpediente.getUsuario().getFuncionario().getDiscriminante().setCatDiscriminanteId(0L);
					filtrosBusquedaExpediente.setIdArea(Areas.DSE_DE_REINSERCION.parseLong());
					filtrosBusquedaExpediente.getUsuario().getAreaActual().setAreaId(Areas.DSE_DE_REINSERCION.parseLong());
					Set<JerarquiaOrganizacionalDTO> jerarquiaOrganizacionalDTOs = new HashSet<JerarquiaOrganizacionalDTO>();
					jerarquiaOrganizacionalDTOs.add(new JerarquiaOrganizacionalDTO(Areas.ASE_DE_REINSERCION.parseLong()));
					filtrosBusquedaExpediente.setJerarquiaOrgSubordinadas(jerarquiaOrganizacionalDTOs);
					//se agregan los estatus al 
					List<Long> estatus = new ArrayList<Long>();
					estatus.add(EstatusExpediente.POR_ATENDER.getValorId());
					estatus.add(EstatusExpediente.EN_PROCESO.getValorId());
					filtrosBusquedaExpediente.setEstatusNumeroExpediente(estatus);
				}
				
			}
			
			if(reasignarExpediente != null) {
				filtrosBusquedaExpediente.setIdActividad(null);
				filtrosBusquedaExpediente.getUsuario().getAreaActual().setBuscarEnJerarquia(Boolean.TRUE);
				filtrosBusquedaExpediente.getUsuario().getFuncionario().setClaveFuncionario(0L);
				filtrosBusquedaExpediente.setEsCordinadorGeneralAMP(esCordinadorGeneralAMP);
			}
			
			filtrosBusquedaExpediente.setNumeroExpediente(noExpediente);
			
//			 List<ExpedienteDTO> listExpedienteDTOs=expedienteDelegate.buscarExpedientes(filtrosBusquedaExpediente);
			List<ExpedienteDTO> listExpedienteDTOs = expedienteDelegate
					.buscarExpedientesPorNumExpDiscriminanteArea(filtrosBusquedaExpediente);

			log.debug("TAMAnio LISTA DE EXPEDIENTES="
					+ listExpedienteDTOs.size());

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
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

			for (ExpedienteDTO expedienteDTO : listExpedienteDTOs) {
				if(opcionUI!=null && opcionUI.equals(""+Areas.UNIDAD_INVESTIGACION.parseLong())){
					writer.print("<row id='"
							+ expedienteDTO.getIdNumeroExpedienteBusquedaATP() + "'>");
				}else{
					// se cambio por numeroExpedienteId dado que el solo Id no era
					// el correcto
					writer.print("<row id='"
							+ expedienteDTO.getNumeroExpedienteId() + "'>");
				}

				if (expedienteDTO.getCasoDTO() != null) {
					writer.print("<cell>"
							+ expedienteDTO.getCasoDTO().getNumeroGeneralCaso()
							+ "</cell>");
				} else {
					writer.print("<cell> -- </cell>");
				}
				
				if(expedienteDTO.getInputado()!=null){
					writer.print("<cell>"
							+ expedienteDTO.getInputado().getElementoId()
							+ "</cell>");
			} else {
				writer.print("<cell> -- </cell>");
			}
				
				if(expedienteDTO.getNumeroExpedienteId()!=null){
					writer.print("<cell>"
							+ expedienteDTO.getNumeroExpedienteId()
							+ "</cell>");
			} else {
				writer.print("<cell> -- </cell>");
			}
				
				
				writer.print("<cell>" + ((expedienteDTO.getNumeroExpediente() != null) ? expedienteDTO.getNumeroExpediente() : "---")	+ "</cell>");
				if(reasignarExpediente==null){
					if( expedienteDTO.getResponsableTramite()!=null &&
							 expedienteDTO.getResponsableTramite().getNombreCompleto()!=null){
						writer.print("<cell>"+ expedienteDTO.getResponsableTramite().getNombreCompleto()+ "</cell>");
					}else{
						writer.print("<cell>"+ "---"+ "</cell>");
					}
					if( expedienteDTO.getDiscriminante()!=null &&
							 expedienteDTO.getDiscriminante().getNombre()!=null){
						writer.print("<cell>"+ expedienteDTO.getDiscriminante().getNombre()+ "</cell>");
					}else{
						writer.print("<cell>"+ "---"+ "</cell>");
					}
					
					
				}

				// SOLO PARA CUANDO SE QUIERA REASIGNAR UN EXPEDIENTE
				if(reasignarExpediente != null) {
					
					if (rolAsociado == Roles.DSE) {
						writer.print(datosSentenciaPorNumeroExpediente(expedienteDTO));
					}
					
					ValorDTO estatus = expedienteDTO.getEstatus();
					if (estatus != null) { 
						writer.print("<cell>" + ((estatus.getValor() != null) ? estatus.getValor() : "---")	+ "</cell>");
					} else {
						writer.print("<cell>---</cell>");
					}
					
					try {
						FuncionarioDTO responsable = funcionarioDelegate.consultarFuncionarioXExpediente(expedienteDTO);
						if (responsable != null) {
							writer.print("<cell>" + ((responsable.getClaveFuncionario() != null) ? responsable.getClaveFuncionario() : "---")	+ "</cell>");
							writer.print("<cell>" + ((responsable.getNombreCompleto() != null) ? responsable.getNombreCompleto() : "---")	+ "</cell>");
						} else {
							writer.print("<cell>---</cell>");
							writer.print("<cell>---</cell>");
						}
					}catch(Exception e){
						log.info("Error al consultar Funcionario Responsable: " , e);
						writer.print("<cell>---</cell>");
						writer.print("<cell>---</cell>");
					}
					
					List<DelitoDTO> lstDelitos = delitoDelegate.consultarDelitosExpediente(expedienteDTO); 
					DelitoDTO delitoPrincipal = delitoDelegate.obtenerDelitoPrincipal(lstDelitos);
					
					if (delitoPrincipal != null) {
						CatDelitoDTO catDelitoDTO =(delitoPrincipal.getCatDelitoDTO() != null) ? delitoPrincipal.getCatDelitoDTO() : null;
						if (catDelitoDTO != null) {
							writer.print("<cell>" + ((catDelitoDTO.getNombre() != null) ? catDelitoDTO.getNombre() : "---")	+ "</cell>");
						} else {
							writer.print("<cell>---</cell>");
						}				
					} else {
						writer.print("<cell>---</cell>");
					}				
					if(verHistorico){
						writer.print("<cell>");
						writer.print("<![CDATA[");
						writer.print("<input  type=\"button\" " +
											" name=\"btnVerHistorial" + expedienteDTO.getNumeroExpedienteId() + "\" " +
											" id=\"btnVerHistorial" + expedienteDTO.getNumeroExpedienteId() + "\" " +
											" value=\"Ver Historial\" " +
											" onclick=\"verHistorial(" + expedienteDTO.getNumeroExpedienteId() + ");\" " +
											" class=\"btn_Generico\" />");
						writer.print("]]>");
						writer.print("</cell>");
					}
				}
				writer.print("</row>");
			}

			writer.print("</rows>");
			writer.flush();
			writer.close();
			if(reasignarExpediente != null) {
				usuario.getFuncionario().setClaveFuncionario(idFuncionario);
			}
		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		
		
		return null;
	}

	/**
	 * @param usuario
	 * @param writer
	 * @param expedienteDTO
	 * @throws NSJPNegocioException
	 */
	private String datosSentenciaPorNumeroExpediente(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		
		StringBuilder datosSentencia = new StringBuilder();
		SentenciaDTO sentenciaDTO = new SentenciaDTO();
		sentenciaDTO.setNumeroExpedienteDTO(new ExpedienteDTO(null, expedienteDTO.getNumeroExpedienteId(),expedienteDTO.getNumeroExpediente()));
		List<SentenciaDTO> lstSentencia = asignacionProgramaDelegate.consultarSentencias(sentenciaDTO);
		
		sentenciaDTO = null;
		
		if(lstSentencia != null && !lstSentencia.isEmpty()){
			sentenciaDTO = lstSentencia.get(0);
		}
		
		if (sentenciaDTO != null && sentenciaDTO.getCnus() != null) { 
			datosSentencia.append("<cell>" + ((sentenciaDTO.getCnus() != null) ? sentenciaDTO.getCnus() : "---")	+ "</cell>");
		} else {
			datosSentencia.append("<cell>---</cell>");
		}
		
		if (sentenciaDTO != null && sentenciaDTO.getInvolucradoDTO() != null) {
			
			InvolucradoDTO sentenciado = sentenciaDTO.getInvolucradoDTO();
			if(sentenciado != null) {
				datosSentencia.append("<cell>" + ((sentenciado.getNombreCompleto() != null) ? sentenciado.getNombreCompleto() : "---")	+ "</cell>");
			} else {
				datosSentencia.append("<cell>---</cell>");
			}
		} else {
			datosSentencia.append("<cell>---</cell>");
		}
		
		return datosSentencia.toString();
	
	}
	
	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorEvidencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
			
			
			String evidencia=request.getParameter("evidencia");
			String palabraUno=request.getParameter("palabraUno");
			String palabraDos=request.getParameter("palabraDos");
			String palabraTres=request.getParameter("palabraTres");
			String palabraCuatro=request.getParameter("palabraCuatro");
			String palabraCinco=request.getParameter("palabraCinco");
			
			
			if (palabraUno.equals("")){
				palabraUno=null;
				
			}

			if (palabraDos.equals("")){
				palabraDos=null;
				
			}				
			
			if (palabraTres.equals("")){
				palabraTres=null;
				
			}

			if (palabraCuatro.equals("")){
				palabraCuatro=null;
				
			}		
			
			if (palabraCinco.equals("")){
				palabraCinco=null;
				
			}
			
			if (log.isDebugEnabled()) {
				log.debug("##################llega evidencia id :::::::::" + evidencia);
				log.debug("##################llega evidencia:::::::::" + palabraUno);
				log.debug("##################llega evidencia:::::::::" + palabraDos);
				log.debug("##################llega evidencia:::::::::" + palabraTres);
				log.debug("##################llega evidencia:::::::::" + palabraCuatro);
				log.debug("##################llega evidencia:::::::::" + palabraCinco);
			}			
			
			List<String> lstPalabra = new ArrayList<String>();
			lstPalabra.add(palabraUno);
			lstPalabra.add(palabraDos);
			lstPalabra.add(palabraTres);
			lstPalabra.add(palabraCuatro);
			lstPalabra.add(palabraCinco);			
			
			Objetos tipoObjeto = Objetos.getByValor(new Long(evidencia));
			
			FiltroExpedienteDTO filtrosBusquedaExpediente=new FiltroExpedienteDTO();
			//Se realiza la siguiente validacion dado que para no afectar el arbol de elementos de los
			//editores de texto para la cnsulta del objeto otro y solucionar la consulta de expedientes por evidencias
			if(new Long(evidencia)== Objetos.OTRO.getValorId().longValue())
			{
				filtrosBusquedaExpediente.setNombreEvidencia("Objeto");
				log.debug("##################Entity_nombre_evidencias OTRO::::::");
			}
			else
			{
				filtrosBusquedaExpediente.setNombreEvidencia(tipoObjeto.getNombreEntity());
				log.debug("##################Entity_nombre_evidencias ::::::" + tipoObjeto.getNombreEntity() +" - ID TipoObjeto:: "+ evidencia +" -  enum:: "+Objetos.OTRO.getValorId());
			}
			
			filtrosBusquedaExpediente.setPalabrasClave(lstPalabra);
			filtrosBusquedaExpediente.setUsuario(super.getUsuarioFirmado(request));
			
			UsuarioRolDTO usuarioRolDTO=super.getUsuarioFirmado(request).getRolACtivo();
			Roles rolAsociado = Roles.getByValor(usuarioRolDTO.getRol().getRolId());
			switch (rolAsociado) {
				case COORDINADORAMP:
				case COORDINADORJAR:{
					filtrosBusquedaExpediente.setIdArea(usuarioRolDTO.getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId());
					break;
				}
				case ATPENAL:
				case FACILITADOR:
				case AGENTEMP:{
					filtrosBusquedaExpediente.setIdFuncionario(super.getUsuarioFirmado(request).getFuncionario().getClaveFuncionario());
					break;
				}
			}
			
			
			List<ExpedienteDTO> listExpedienteDTOs=expedienteDelegate.buscarExpedientes(filtrosBusquedaExpediente);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
//			List<NombreDemograficoDTO> listaNombreDemograficoDto = null;
			
			writer.print("<rows>");
			int lTotalRegistros=listExpedienteDTOs.size();

			writer.print("<records>" + lTotalRegistros + "</records>");

			for (ExpedienteDTO expedienteDTO : listExpedienteDTOs) {
				writer.print("<row id='"+ expedienteDTO.getNumeroExpedienteId()+"'>");//se cambio por numeroExpedienteId dado que el solo Id no era el correcto
				writer.print("<cell>" + expedienteDTO.getCasoDTO().getNumeroGeneralCaso()+ "</cell>");
				writer.print("<cell>" + expedienteDTO.getNumeroExpediente()+ "</cell>");
				writer.print("</row>");
			}			
			
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorAlias(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
			
			BuscarExpedientePorAliasForm forma=(BuscarExpedientePorAliasForm) form;
			String alias=forma.getAlias();
			Long tipo=forma.getTipo();
			if (log.isDebugEnabled()) {
				log.debug("llega alias " + alias );
				log.debug("llega tipo " + tipo );
			}
			FiltroExpedienteDTO filtrosBusquedaExpediente=new FiltroExpedienteDTO();
			filtrosBusquedaExpediente.setAlias(alias);
			filtrosBusquedaExpediente.setTipoBusqueda(tipo);
			List<InvolucradoDTO> listInvolucradoDTOs=expedienteDelegate.consultarExpedientesPorFiltros(filtrosBusquedaExpediente);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			List<NombreDemograficoDTO> listaNombreDemograficoDto = null;
			
			writer.print("<rows>");
			
			int lTotalRegistros=0;
			
			for (InvolucradoDTO involucradoDTO : listInvolucradoDTOs) {
				lTotalRegistros=+involucradoDTO.getNombresDemograficoDTO().size();
			}
				
			writer.print("<records>" + lTotalRegistros + "</records>");
			for (InvolucradoDTO involucradoDTO : listInvolucradoDTOs) {
				listaNombreDemograficoDto = involucradoDTO.getNombresDemograficoDTO();
				for (NombreDemograficoDTO nombreDemograficoDTO : listaNombreDemograficoDto) {
				
				writer.print("<row id='" + involucradoDTO.getExpedienteDTO().getNumeroExpedienteId()+ "'>");//se cambio por numeroExpedienteId dado que el solo Id no era el correcto
				writer.print("<cell>" + involucradoDTO.getExpedienteDTO().getNumeroExpediente()+ "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getNombre()+ "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getApellidoPaterno() + "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getApellidoMaterno() + "</cell>");
				writer.print("<cell>" + involucradoDTO.getCalidadDTO().getValorIdCalidad().getValor() + "</cell>");
				writer.print("</row>");
				}			
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	/**
	 * M&eacute;todo utilizado para realizar la carga de la tabla que se presenta
	 * en la JSP BuscarExpedientePorNombreDePersona
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorNombreDePersona(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {		
		try {
			
			BuscarExpedientePorNombreDePersonaForm forma=(BuscarExpedientePorNombreDePersonaForm) form;
			//String nombre=forma.getNombre();
			//String apellido=forma.getApellido();
			
			if (forma.getNombre().equals("")){
				forma.setNombre(null);
				log.info(forma.getNombre());
			}

			if (forma.getApellido().equals("")){
				forma.setApellido(null);
				log.info(forma.getApellido());
			}					
			
			if (log.isDebugEnabled()) {
				log.debug("llega nombre " + forma.getNombre() );
				log.debug("llega apellido " + forma.getApellido() );
				log.debug("llega apellido materno " + forma.getApellidoMat() );
			}
			FiltroExpedienteDTO filtrosBusquedaExpediente=new FiltroExpedienteDTO();
			filtrosBusquedaExpediente.setNombre(forma.getNombre());
			filtrosBusquedaExpediente.setApellidos(forma.getApellido());
			filtrosBusquedaExpediente.setApellidoMat(forma.getApellidoMat());
			
			UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);
			
			UsuarioRolDTO usuarioRolDTO=super.getUsuarioFirmado(request).getRolACtivo();
			Roles rolAsociado = Roles.getByValor(usuarioRolDTO.getRol().getRolId());
			switch (rolAsociado) {
				case COORDINADORAMP:
				case COORDINADORJAR:{
					filtrosBusquedaExpediente.setIdArea(usuarioRolDTO.getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId());
					filtrosBusquedaExpediente.setUsuario(getUsuarioFirmado(request));
					break;
				}
				case ATPENAL:
				case ADMINAT:{
					filtrosBusquedaExpediente.setIdArea(usuarioRolDTO.getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId());
					break;
				}
				case FACILITADOR:
				case AGENTEMP:{
					filtrosBusquedaExpediente.setIdFuncionario(super.getUsuarioFirmado(request).getFuncionario().getClaveFuncionario());
					filtrosBusquedaExpediente.setUsuario(getUsuarioFirmado(request));
					break;
				}
			}
			
			
			List<InvolucradoDTO> listInvolucradoDTOs =expedienteDelegate.consultarExpedientesPorFiltrosYDiscriminante(filtrosBusquedaExpediente,usuarioFirmado);

			if(Roles.DEFENSOR.equals(rolAsociado)||Roles.COORDINADORDEF.equals(rolAsociado)){
				List<InvolucradoDTO> listInvolucradoDTOsTemp= new ArrayList<InvolucradoDTO>();
				for (InvolucradoDTO involucradoDTO : listInvolucradoDTOs) {
					if(involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.PROBABLE_RESPONSABLE_PERSONA)
							||involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION)){
						listInvolucradoDTOsTemp.add(involucradoDTO);
					}
				}
				listInvolucradoDTOs.clear();
				listInvolucradoDTOs.addAll(listInvolucradoDTOsTemp);
			}
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			List<NombreDemograficoDTO> listaNombreDemograficoDto = null;			

			writer.print("<rows>");
			
			int lTotalRegistros=0;			
			for (InvolucradoDTO involucradoDTO : listInvolucradoDTOs) {
				lTotalRegistros=+involucradoDTO.getNombresDemograficoDTO().size();
			}
				
			writer.print("<records>" + lTotalRegistros + "</records>");
			for (InvolucradoDTO involucradoDTO : listInvolucradoDTOs) {
				listaNombreDemograficoDto = involucradoDTO.getNombresDemograficoDTO();
				for (NombreDemograficoDTO nombreDemograficoDTO : listaNombreDemograficoDto) {
				
				writer.print("<row id='" + involucradoDTO.getExpedienteDTO().getNumeroExpedienteId() + "'>");//se cambio por numeroExpedienteId dado que el solo Id no era el correcto
				
					if (involucradoDTO.getExpedienteDTO() != null
							&& involucradoDTO.getExpedienteDTO().getCasoDTO() != null
							&& involucradoDTO.getExpedienteDTO().getCasoDTO()
									.getNumeroGeneralCaso() != null) {
						writer.print("<cell>"
								+ involucradoDTO.getExpedienteDTO()
										.getCasoDTO().getNumeroGeneralCaso()
								+ "</cell>");
					}
					else{
						writer.print("<cell>"+" "+"</cell>");
					}
				
				writer.print("<cell>" + involucradoDTO.getExpedienteDTO().getNumeroExpediente()+ "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getNombre()+ "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getApellidoPaterno() + "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getApellidoMaterno() + "</cell>");
				writer.print("<cell>" + involucradoDTO.getCalidadDTO().getValorIdCalidad().getValor() + "</cell>");
				writer.print("<cell>" + involucradoDTO.getElementoId() + "</cell>");
				writer.print("</row>");
				}			
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	/**
	 * M&eacute;todo utilizado para realizar la carga del combo tipo de evidencia 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException En caso de obtener una exception
	 */
	
	public ActionForward cargarTipoEvidencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Action Buscar Expediente Por Evidencia");
			
			List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.TIPO_OBJETO);

			converter.alias("listaCatalogo", java.util.List.class);
			converter.alias("catEvidencia", CatalogoDTO.class);
			
			String xml = converter.toXML(listaCatalogo);
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
			
		} catch (Exception e) {		
			log.info(e);
		}
		return null;
	}	
	
	public ActionForward generarDetalleExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
			
			Long idExpediente=Long.parseLong(request.getParameter("idRow"));
			
			if (log.isDebugEnabled()) {
				
				log.debug("##################llega idExpediente:::::::::" + idExpediente);
			}

			ExpedienteDTO expedienteDTO=new ExpedienteDTO();
			expedienteDTO.setExpedienteId(idExpediente);
			expedienteDTO.setArea(super.getUsuarioFirmado(request).getAreaActual());
			ExpedienteDTO expedienteDTO2=expedienteDelegate.obtenerExpediente(expedienteDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			if (log.isDebugEnabled()) {
				
				log.debug("##################llega grid detalle:::::::::" + expedienteDTO2 );
			}

			List<NombreDemograficoDTO> list=new ArrayList<NombreDemograficoDTO>();
			
			for (InvolucradoDTO dos : expedienteDTO2.getInvolucradosDTO()) {
				for (NombreDemograficoDTO nombreDemograficoDTO : dos.getNombresDemograficoDTO()) {
					list.add(nombreDemograficoDTO);
				}
			}
			
			writer.print("<rows>");
			int lTotalRegistros=list.size();

			writer.print("<records>" + lTotalRegistros + "</records>");
			
			for (InvolucradoDTO dos : expedienteDTO2.getInvolucradosDTO()) {
				for (NombreDemograficoDTO demograficoDTO : list) {
					
				writer.print("<row id='1'>");
				writer.print("<cell>" + demograficoDTO.getNombre() + "</cell>");
				writer.print("<cell>" + demograficoDTO.getApellidoPaterno()  + "</cell>");
				writer.print("<cell>" + demograficoDTO.getApellidoMaterno()  + "</cell>");
				writer.print("<cell>" + dos.getCalidadDTO().getValorIdCalidad().getValor() + "</cell>");
				writer.print("<cell>" + dos.getExpedienteDTO().getDelitoPrincipal() + "</cell>");
				writer.print("<cell>" + dos.getExpedienteDTO().getCasoDTO()+ "</cell>");
				writer.print("</row>");
				}	
			}
			
			writer.print("</rows>");
			writer.flush();
			writer.close();
									
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	public ActionForward generarDetalleDeBusquedaDeExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
			
			String idRow=request.getParameter("idRow");
			
			if (log.isDebugEnabled()) {
				
				log.debug("##################llega fila:::::::::" + idRow);
			}

			request.setAttribute("idRow", idRow);

			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return mapping.findForward("generarDetalleExp");
	}
	
	public ActionForward cargarTitulo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Action carga titulo");
			
			Long idExp=Long.parseLong(request.getParameter("idRow"));
			
			if (log.isDebugEnabled()) {
				
				log.debug("##################llega idExpediente  titulo:::::::::" + idExp);
			}
			ExpedienteDTO expedienteDTO=new ExpedienteDTO();
			expedienteDTO.setExpedienteId(idExp);
			ExpedienteDTO expedienteDTO2=expedienteDelegate.obtenerExpediente(expedienteDTO);
			
			converter.alias("expediente", ExpedienteDTO.class);
			
			String xml = converter.toXML(expedienteDTO2);
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
			
		} catch (Exception e) {		
			log.info(e);
		}
		return null;
	}	
	
	
	/**
	 * M&eacute;todo utilizado para pasar parametros y diferenciar de donde se hace el llamado del caso d euso buscar expediente
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Action buscar expediente");
			
			String tipo = request.getParameter("tipo");
			log.info("LLEGA TIPO:::"+ tipo);
			UsuarioDTO usuarioDTO=super.getUsuarioFirmado(request);
			if(usuarioDTO.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId().equals(Areas.UNIDAD_INVESTIGACION.parseLong())){
				request.setAttribute("opcion",Areas.UNIDAD_INVESTIGACION.parseLong());
			}
			
			
			request.setAttribute("tipo",tipo);
			
		} catch (Exception e) {		
			log.info(e);
		}
		return mapping.findForward("succes");
	}
	
	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes en forma de xml
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorNumeroDeExpedienteComparaExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
			
			String noExpediente=request.getParameter("noExpediente");
			if (log.isDebugEnabled()) {
				log.debug("##################llega noExpediente:::::::::" + noExpediente);
			}
			FiltroExpedienteDTO filtrosBusquedaExpediente=new FiltroExpedienteDTO();
			filtrosBusquedaExpediente.setNumeroExpediente(noExpediente);
			
			UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
			
			if(usuarioDTO.getAreaActual().getAreaId().longValue() == Areas.COORDINACION_DEFENSORIA.parseLong().longValue()){
				filtrosBusquedaExpediente.setUsuario(new UsuarioDTO());
			}else{
				filtrosBusquedaExpediente.setUsuario(usuarioDTO);
			}
			
			List<ExpedienteDTO> listExpedienteDTOs=expedienteDelegate.buscarExpedientes(filtrosBusquedaExpediente);
			log.debug("##################listExpedienteDTOs::::::::: " + listExpedienteDTOs.size());
			

			converter.alias("listExpedienteDTOs", java.util.List.class);
			converter.alias("expedienteDTO", ExpedienteDTO.class);
			String xml = converter.toXML(listExpedienteDTOs);

			log.debug("##################listExpedienteDTOs   XML::::::::: " + xml);
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	
	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorNumeroDeExpedienteParaDocumentos(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {
			
			UsuarioDTO usuario = getUsuarioFirmado(request);
			
			 //Para que no busque por area
			
			if (usuario != null && usuario.getAreaActual() != null
					&& usuario.getAreaActual().getAreaId() != null) {
				usuario.getAreaActual().setAreaId(null);
			}
			
			log.info(":::::::::::::VERIFICANDO PARAMETROS:::::::::::::::::::::");			
			log.info("Numero de Expediente=" + request.getParameter("numeroExpedienteId"));
			log.info("FechaIni=" + request.getParameter("fechaIni"));
			log.info("FechaFin=" + request.getParameter("fechaFin"));

			String noExpediente = request.getParameter("numeroExpedienteId");
			
			FiltroExpedienteDTO filtrosBusquedaExpediente = new FiltroExpedienteDTO();
			
			List<ExpedienteDTO> listExpedienteDTOs = new ArrayList<ExpedienteDTO>();
			
			filtrosBusquedaExpediente.setNumeroExpediente(noExpediente);
			filtrosBusquedaExpediente.setUsuario(usuario);
			
			if (noExpediente != null && noExpediente != "") {
				listExpedienteDTOs = expedienteDelegate
						.buscarExpedientes(filtrosBusquedaExpediente);
			}else{
				
				if (usuario.getFuncionario() != null
						&& usuario.getFuncionario().getClaveFuncionario() != null) {
					usuario.getFuncionario().setClaveFuncionario(null);
				}
				
				Date fechaInicio = new Date();
				Date fechaFinal = new Date();
				
				if (request.getParameter("fechaIni") != null
						&& request.getParameter("fechaIni") != ""
						&& request.getParameter("fechaFin") != null
						&& request.getParameter("fechaFin") != "") {
					
					fechaInicio = DateUtils.obtener(request.getParameter("fechaIni"),"00:00");
					fechaFinal = DateUtils.obtener(request.getParameter("fechaFin"),"00:00");
					
					listExpedienteDTOs = expedienteDelegate
							.consultarExpedientesPorFiltro(fechaInicio,
									fechaFinal, usuario.getFuncionario(), null, null);
					
				}else{
					
					fechaInicio = new Date();
					Calendar calTempDec = Calendar.getInstance();
					
					calTempDec.setTime(fechaInicio);
					calTempDec.add(Calendar.DATE, 1);
					log.info(calTempDec + "tiempo despues tiempo antes"	+ fechaInicio);
					listExpedienteDTOs = expedienteDelegate
							.consultarExpedientesPorFiltro(fechaInicio,
									calTempDec.getTime(), usuario.getFuncionario(), null,
									null);
				}
			}
			
			log.info("lista de expedientes else" + listExpedienteDTOs);
			
			//List<DocumentoDTO> documentoDTOs = new ArrayList<DocumentoDTO>();

			List<BitacoraMovimientoDTO> bitacoraMovimientoDTOs = new ArrayList<BitacoraMovimientoDTO>();
			
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

			for (ExpedienteDTO expedienteDTO : listExpedienteDTOs) {
				writer.print("<row id='"+ expedienteDTO.getNumeroExpedienteId() + "'>");
				writer.print("<cell>");
					if (expedienteDTO.getNumeroExpediente() != null) {
						writer.print(expedienteDTO.getNumeroExpediente());
					} else {
						writer.print("-");
					}
				writer.print("</cell>");

				writer.print("<cell>");
					if (expedienteDTO.getCasoDTO() != null) {
						writer.print(expedienteDTO.getCasoDTO()
								.getNumeroGeneralCaso());
					} else {
						writer.print("-");
					}
				writer.print("</cell>");

				writer.print("<cell>");
					if (expedienteDTO.getStrFechaApertura() != null) {
						writer.print(expedienteDTO.getStrFechaApertura());
					} else {
						writer.print("-");
					}
				writer.print("</cell>");

				writer.print("<cell>");
					if (expedienteDTO.getNumeroExpediente() != null) {
	
						bitacoraMovimientoDTOs = bitacoraMovimientosDelegate
								.consultarBitacoraMovimientoPorNumeroExpedienteCategoria(
										expedienteDTO.getNumeroExpediente(), null);
						if (bitacoraMovimientoDTOs.isEmpty()) {
							writer.print("-");
						} else {
	
							for (BitacoraMovimientoDTO bitacoraMovimientoDTO : bitacoraMovimientoDTOs) {
								if (bitacoraMovimientoDTO.getFechaMovimiento() != null) {
									writer.print(bitacoraMovimientoDTO
											.getFechaMovimiento());
	
								} else {
									writer.print("-");
									writer.print("</cell>");
								}
							}
						}
	
					} else {
						writer.print("-");
					}
				writer.print("</cell>");

				writer.print("<cell>");
					if (expedienteDTO.getDocumentosDTO() != null) {
						for (DocumentoDTO documentoDTO : expedienteDTO
								.getDocumentosDTO()) {
							if (documentoDTO.getArchivoDigital() != null) {
								writer.print("Si");
							} else {
	
								writer.print("no");
							}
						}
					} else {
						writer.print("-");
					}
				writer.print("</cell>");
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
	 * M&eacute;todo utilizado para realizar la consulta de expedientes por area, por
	 * catDiscriminante del Usuario y por fecha de creacion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward busquedaExpedientesXEstatusXFechaXAreaXDiscriminante(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			if (log.isDebugEnabled()) {
				log.debug("::::::EJECUNTANDO ACTION busquedaExpedientesXEstatusXFechaXAreaXDiscriminante:::::");
			}

			String fechaInicio = request.getParameter("fechaInicio");
			String fechaFin = request.getParameter("fechaFin");
						
			String estatusExpediente = request.getParameter("estatusExpediente");
			Long idRol = NumberUtils.toLong(request.getParameter("idRol"),0);
			Long idDistrito = NumberUtils.toLong(request.getParameter("idDistrito"),0);
			Long idFuncionario = NumberUtils.toLong(request.getParameter("idFuncionario"),0);
			Long idDiscriminante = NumberUtils.toLong(request.getParameter("idDiscriminante"),0);
			
			if (log.isDebugEnabled()) {
				log.debug("VERIFICANDO PARAMETROS:::::::::");
				log.debug("fechaInicio=" + fechaInicio);
				log.debug("fechaFin=" + fechaFin);
				log.debug("estatusExpediente=" + estatusExpediente);
				log.debug("idRol=" + idRol);
				log.debug("idDistrito=" + idDistrito);
				log.debug("idFuncionario=" + idFuncionario);
				log.debug("idDiscriminante=" + idDiscriminante);
			}

			Date iniDate = null;
			Date finDate = null;
			
			if (fechaInicio != null && !fechaInicio.equals("")) {
				iniDate = DateUtils.obtener(fechaInicio);
			}
			if (fechaFin != null && !fechaFin.equals("")){
				finDate = DateUtils.obtener(fechaFin);
			}
								
			//Permite recuperar los ids de estatus
			String[] listaEstatus= estatusExpediente.split(",");
			List<Long> listaEstatusLong = new ArrayList<Long>();

			for (String idEstatus : listaEstatus) {
				listaEstatusLong.add(Long.parseLong(idEstatus));
			}
			
			List<ExpedienteDTO> listExpedienteDTOs = expedienteDelegate
					.consultarExpedientesPorFiltroST(iniDate, finDate,
							super.getUsuarioFirmado(request),
							listaEstatusLong,idDiscriminante,idRol,idDistrito,idFuncionario);

			log.debug("TAMANIO LISTA DE EXPEDIENTES="
					+ listExpedienteDTOs.size());

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

//			int lTotalRegistros = listExpedienteDTOs.size();
//
//			writer.print("<records>" + lTotalRegistros + "</records>");
			
			for (ExpedienteDTO expedienteDTO : listExpedienteDTOs) {

				//Id del renglon
				writer.print("<row id='" + expedienteDTO.getNumeroExpedienteId()
						+ "'>");

				//Numero de Expediente
				writer.print("<cell>" + expedienteDTO.getNumeroExpediente()
						+ "</cell>");

				//Fecha de apertura del expediente
				if (expedienteDTO.getStrFechaApertura() != null) {
					writer.print("<cell>" + expedienteDTO.getStrFechaApertura()
							+ "</cell>");
				} else {
					writer.print("<cell>" + " " + "</cell>");
				}
				
				//Columna Denunciante
				log.info("Este es el expediente con calidad de denunciante"+expedienteDTO.getInvolucradoByCalidad(Calidades.DENUNCIANTE));
				log.info("invol tamanio"+expedienteDTO.getInvolucradoByCalidad(Calidades.DENUNCIANTE).size());
				log.info("invol tamanio de"+expedienteDTO.getInvolucradosDTO().size());
				 boolean op=true;
				for (InvolucradoDTO involucradoDTO : expedienteDTO.getInvolucradoByCalidad(Calidades.DENUNCIANTE)) {
					log.info("numero de involucrado nombre completo perdon:"+involucradoDTO.getNombreCompleto());
					for (NombreDemograficoDTO nombreDemograficoDTO : involucradoDTO.getNombresDemograficoDTO()) {
						log.info("Verdadero nombre:"+nombreDemograficoDTO.getEsVerdadero());
						if(nombreDemograficoDTO.getEsVerdadero()!=null && nombreDemograficoDTO.getEsVerdadero()){
							writer.print("<cell>"+nombreDemograficoDTO.getNombre()+" "+nombreDemograficoDTO.getApellidoPaterno()+" "+nombreDemograficoDTO.getApellidoMaterno() +"</cell>");
							op=false;
						}
					}
				}
				if(op){
					writer.print("<cell>"+"An&#243;nimo"+"</cell>");
				}


				
				//Delito principal
				if (expedienteDTO.getDelitoPrincipal() != null
						&& expedienteDTO.getDelitoPrincipal().getCatDelitoDTO() != null
						&& expedienteDTO.getDelitoPrincipal().getCatDelitoDTO()
								.getNombre() != null) {
					writer.print("<cell>"
							+ expedienteDTO.getDelitoPrincipal()
									.getCatDelitoDTO().getNombre().toLowerCase() + "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");

				}
				
				//Columna Origen 
				if(expedienteDTO.getOrigen()!=null){
					writer.print("<cell>"+expedienteDTO.getOrigen().getValor().toLowerCase()+"</cell>");
				}else{
					writer.print("<cell>"+"Denuncia"+"</cell>");
				}
				
				//Estatus
				if(expedienteDTO.getEstatus()!=null && expedienteDTO.getEstatus().getValor() !=null){
					writer.print("<cell>"+ expedienteDTO.getEstatus().getValor().toLowerCase() +"</cell>");
				}else{
					writer.print("<cell>"+"---"+"</cell>");
				}
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
	
	
	public ActionForward consultarExpedientesSistemaTradicional(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			log.info("EJECUTANDO ACTION consultarExpedientesSistemaTradicional");

			String idNumeroExpediente = request
					.getParameter("idNumeroExpediente");

			log.info("VERIFICANDO PARAMETROS ExpedienteId: "
					+ idNumeroExpediente);

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setArea(new AreaDTO(
					Areas.AGENCIA_DEL_MINISTERIO_PUBLICO));

			if (idNumeroExpediente != null) {
				expedienteDTO.setNumeroExpedienteId(Long
						.parseLong(idNumeroExpediente));
			}
			expedienteDTO = expedienteDelegate.obtenerExpediente(expedienteDTO);

			expedienteDTO.setConsulta(true);
			log.info("ACTION- NUMERO EXPEDIENTE ENCONTRADO:"
					+ expedienteDTO.getNumeroExpediente());

			request.getSession().removeAttribute("numExpConsul");
			request.getSession().setAttribute("numExpConsul",
					expedienteDTO.getNumeroExpediente());
			request.getSession().setAttribute("numeroExpediente",
					expedienteDTO.getNumeroExpediente());
			request.getSession().setAttribute("idNumeroExpedienteop",
					expedienteDTO.getNumeroExpedienteId());
			request.getSession().setAttribute("idExpedienteop",
					expedienteDTO.getExpedienteId());
			request.getSession().setAttribute("idExpedienteConsulop",
					expedienteDTO.getExpedienteId());

			// IMPLEMENTACION ANTERIOR
			// request.getSession().removeAttribute("numExpConsul");
			// request.getSession().setAttribute("numExpConsul",
			// expedienteDTO.getNumeroExpediente());
			// request.getSession().setAttribute("idExpedienteConsul",
			// expedienteDTO.getNumeroExpedienteId());
			// request.getSession().setAttribute("idExpedienteConsulop",
			// expedienteDTO.getExpedienteId());
			// request.getSession().setAttribute("numeroCasoConsul",
			// expedienteDTO.getCasoDTO().getNumeroGeneralCaso());

			log.info("area_exp:: " + expedienteDTO.getArea().getAreaId());

			// asignamos la pantalla solicitada
			if (expedienteDTO.getArea().getAreaId().longValue() == Areas.ATENCION_TEMPRANA_PG_PENAL
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 1);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 2);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.UNIDAD_INVESTIGACION
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.UNIDAD_INVESTIGACION.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 3);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.COORDINACION_UNIDAD_INVESTIGACION
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.UNIDAD_INVESTIGACION.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 4);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.FISCAL_FACILITADOR
					.parseLong()) {
				log.info("area_enum:: " + Areas.FISCAL_FACILITADOR.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 5);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.POLICIA_MINISTERIAL
					.parseLong()) {
				log.info("area_enum:: " + Areas.POLICIA_MINISTERIAL.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 6);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.COORDINACION_VISITADURIA
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.COORDINACION_VISITADURIA.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 7L);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.VISITADURIA
					.parseLong()) {
				log.info("area_enum:: " + Areas.VISITADURIA.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 8);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.AGENCIA_DEL_MINISTERIO_PUBLICO
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.AGENCIA_DEL_MINISTERIO_PUBLICO.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 9);
			}
			super.setExpedienteTrabajo(request, expedienteDTO);
			log.info("SUBE A SESION EL EXPEDIENTE DE TRABAJO: " + expedienteDTO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("succes");
	}

	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes en forma de xml
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward actualizarCatDiscriminanteDeExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
        	Long catDiscriminanteId = NumberUtils.toLong(request.getParameter("catDiscriminanteId"),0L);
        	Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpedienteId"),0L);
        	Long catUIEId = NumberUtils.toLong(request.getParameter("catUIE"),0L);
        	
        	String numeroUnicoExpediente = request.getParameter("numeroUnicoExpediente");
        	
        	if (numeroExpedienteId.equals(0L) && numeroUnicoExpediente != null){
        		numeroUnicoExpediente = numeroUnicoExpediente.trim();
        		ExpedienteDTO exp = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroUnicoExpediente);
        		numeroExpedienteId = exp.getNumeroExpedienteId();
        	}
        	
        	ExpedienteDTO expedienteDTO = new ExpedienteDTO();
        	CatDiscriminanteDTO discriminante = new CatDiscriminanteDTO();
        	discriminante.setCatDiscriminanteId(catDiscriminanteId);
        	expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
        	expedienteDTO.setDiscriminante(discriminante);
        	expedienteDTO.setCatUIE(catUIEId);
        	Boolean respuesta = expedienteDelegate.actualizarCatDiscriminanteDeExpediente(expedienteDTO);

        	if(respuesta!=null){
				log.info("respuesta: " + respuesta);
			}else{
				log.info("respuesta: NULO");
}
			converter.alias("boolean", java.lang.Boolean.class);
			String xml = converter.toXML(respuesta);
			escribir(response, xml,null);
			if(log.isDebugEnabled())
			{
				log.info(xml);
			}	               	
        	
        } catch (Exception e) {
            log.error(e.getMessage(), e);               	        	
        }   
		return null;
	}

	/**
	 * M&eacute;todo que consulta los expedientes de un funcionario
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarExpedientesPorFuncionario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("cargarGridExpedientesFuncionario - ejecuantdo action");
			List<ExpedienteDTO> expedientes = null;
			Boolean verHistorico = request.getParameter("verHistorico")!= null ? Boolean.parseBoolean(request.getParameter("verHistorico")): Boolean.FALSE;
			String reasignarExpediente = request.getParameter("reasignarExpediente");
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			FuncionarioForm funcionarioForm = (FuncionarioForm)form;
			
			
			if(funcionarioForm.getFuncionarioDTO() != null){				
				
				FuncionarioDTO funcionarioDTO = funcionarioForm.getFuncionarioDTO();
				
				usuarioDTO.setFuncionario(funcionarioDTO);
				if(reasignarExpediente != null) {
					usuarioDTO.setAreaActual(new AreaDTO());
					usuarioDTO.getAreaActual().setBuscarEnJerarquia(Boolean.TRUE);
				}
				
				expedientes = funcionarioDelegate.consultarExpedientesDelFuncionario(usuarioDTO);
 				
				UsuarioRolDTO usuarioRolDTO=super.getUsuarioFirmado(request).getRolACtivo();
				Roles rolAsociado = Roles.getByValor(usuarioRolDTO.getRol().getRolId());
								
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
	            
				for (ExpedienteDTO expedienteDTO : expedientes) {
					writer.print("<row id='"+expedienteDTO.getNumeroExpedienteId()+"'>");
					if (expedienteDTO.getCasoDTO() != null) {
						writer.print("<cell>"
								+ expedienteDTO.getCasoDTO().getNumeroGeneralCaso()
								+ "</cell>");
					} else {
						writer.print("<cell> -- </cell>");
					}
					
					writer.print("<cell>" + ((expedienteDTO.getNumeroExpediente() != null) ? expedienteDTO.getNumeroExpediente() : "---")	+ "</cell>");
										
					if (rolAsociado == Roles.DSE) {
						writer.print(datosSentenciaPorNumeroExpediente(expedienteDTO));
					}
					
					ValorDTO estatus = expedienteDTO.getEstatus();
					if (estatus != null) { 
						writer.print("<cell>" + ((estatus.getNombreCampo() != null) ? estatus.getNombreCampo() : "---")	+ "</cell>");
					} else {
						writer.print("<cell>---</cell>");
					}					
					
					
					FuncionarioDTO responsable = expedienteDTO.getResponsableTramite();
					if (responsable != null) {
						writer.print("<cell>" + ((responsable.getClaveFuncionario() != null) ? responsable.getClaveFuncionario() : "---")	+ "</cell>");
						writer.print("<cell>" + ((responsable.getNombreCompleto() != null) ? responsable.getNombreCompleto() : "---")	+ "</cell>");
					} else {
						writer.print("<cell>---</cell>");
						writer.print("<cell>---</cell>");
					}
					
					DelitoDTO delito = expedienteDTO.getDelitoPrincipal();
					if(delito == null && expedienteDTO.getDelitos() != null && !expedienteDTO.getDelitos().isEmpty()){
						delito = expedienteDTO.getDelitos().get(0);
					}
					if(delito != null && delito.getCatDelitoDTO() != null){
						writer.print("<cell>"+delito.getCatDelitoDTO().getNombre()+"</cell>");
					}else{
						writer.print("<cell>-</cell>");
					}
					if(verHistorico){
						writer.print("<cell>");
						writer.print("<![CDATA[");
						writer.print("<input  type=\"button\" " +
											" name=\"btnVerHistorial" + expedienteDTO.getNumeroExpedienteId() + "\" " +
											" id=\"btnVerHistorial" + expedienteDTO.getNumeroExpedienteId() + "\" " +
											" value=\"Ver Historial\" " +
											" onclick=\"verHistorial(" + expedienteDTO.getNumeroExpedienteId() + ");\" " +
											" class=\"btn_Generico\" />");
						writer.print("]]>");						
						writer.print("</cell>");
					}
					writer.print("</row>");
				}
				
				writer.print("</rows>");
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	

	/**
	 * M&eacute;todo que consulta los hist&oacute;ricos de un expediente
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarHistoricoExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("cargarGridExpedientesFuncionario - ejecuantdo action");
			Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpediente"), 0L);		

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			HistoricoExpedienteDTO historicoExpedienteDTO = new HistoricoExpedienteDTO();
			ExpedienteDTO numeroExpedienteDTO = new ExpedienteDTO();
			numeroExpedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
			historicoExpedienteDTO.setNumeroExpediente(numeroExpedienteDTO);
			
			List<HistoricoExpedienteDTO > lstHistoricoExpedienteDTO = historicoExpedienteDelegate.consultarHistoricoExpediente(historicoExpedienteDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();

			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros()
						+ "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}

			for (HistoricoExpedienteDTO temp : lstHistoricoExpedienteDTO) {
			
				writer.print("<row id='" + temp.getHistoricoExpedienteId()  + "'>");
				if (temp.getNumeroExpediente() != null) {
					numeroExpedienteDTO = temp.getNumeroExpediente();
					writer.print("<cell>"
							+ (numeroExpedienteDTO.getNumeroExpediente() != null
									? numeroExpedienteDTO.getNumeroExpediente() 
									: " --- ")
							+ "</cell>");
				} else {
					writer.print("<cell> -- </cell>");
				}

				if (temp.getFuncionarioActual() != null) {
					FuncionarioDTO funcionarioDTO = temp.getFuncionarioActual();
					writer.print("<cell>"
							+ (funcionarioDTO.getClaveFuncionario() != null 
									? funcionarioDTO.getClaveFuncionario()
									: " --- ")
							+ "</cell>");
					writer.print("<cell>"
							+ (funcionarioDTO.getNombreCompleto() != null 
									? funcionarioDTO.getNombreCompleto()
									: " --- ")
							+ "</cell>");					
				} else {
					writer.print("<cell> -- </cell>");
					writer.print("<cell> -- </cell>");
				}
				
				if (temp.getFuncionarioAsigna() != null) {
					FuncionarioDTO funcionarioDTO = temp.getFuncionarioAsigna();
					writer.print("<cell>"
							+ (funcionarioDTO.getClaveFuncionario() != null 
									? funcionarioDTO.getClaveFuncionario()
									: " --- ")
							+ "</cell>");
					writer.print("<cell>"
							+ (funcionarioDTO.getNombreCompleto() != null 
									? funcionarioDTO.getNombreCompleto()
									: " --- ")
							+ "</cell>");					
				} else {
					writer.print("<cell> -- </cell>");
					writer.print("<cell> -- </cell>");
				}

				if (temp.getFuncionarioAnterior() != null) {
					FuncionarioDTO funcionarioDTO = temp.getFuncionarioAnterior();
					writer.print("<cell>"
							+ (funcionarioDTO.getClaveFuncionario() != null 
									? funcionarioDTO.getClaveFuncionario()
									: " --- ")
							+ "</cell>");
					writer.print("<cell>"
							+ (funcionarioDTO.getNombreCompleto() != null 
									? funcionarioDTO.getNombreCompleto()
									: " --- ")
							+ "</cell>");					
				} else {
					writer.print("<cell> -- </cell>");
					writer.print("<cell> -- </cell>");
				}				
				
				if (temp.getdFechaInicio() != null) {
					writer.print("<cell>"
							+ simpleDateFormat.format(temp.getdFechaInicio())
							+ "</cell>");
				} else {
					writer.print("<cell> -- </cell>");
				}				

				if (temp.getdFechaFin() != null) {
					writer.print("<cell>"
							+ simpleDateFormat.format(temp.getdFechaFin())
							+ "</cell>");
				} else {
					writer.print("<cell> -- </cell>");
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
	
	
}
