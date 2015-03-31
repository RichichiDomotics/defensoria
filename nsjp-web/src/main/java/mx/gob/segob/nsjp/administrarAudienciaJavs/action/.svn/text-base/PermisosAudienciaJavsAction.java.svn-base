package mx.gob.segob.nsjp.administrarAudienciaJavs.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusPermisosAudiencia;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.configuracion.ConfiguracionDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaJAVSTransporteDTO;
import mx.gob.segob.nsjp.dto.audiencia.CatEstadoPermisoDTO;
import mx.gob.segob.nsjp.dto.audiencia.PermisoAudienciaDTO;
import mx.gob.segob.nsjp.dto.bitacora.BitacoraDescargaDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class PermisosAudienciaJavsAction extends GenericAction{
	
	/* Log de clase*/
	private static final Logger log  = Logger.getLogger(PermisosAudienciaJavsAction.class);
	
	@Autowired 
	public AudienciaDelegate audienciaDelegate;	
	
	@Autowired 
	public ConfiguracionDelegate configuracionDelegate;	
	
	
	public ActionForward consultarPermisosAudienciasJavs(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			
			log.info("EJECUTANDO ACTION CONSULTAR PERMISOS AUDIENCIAS JAVS");
			log.info("******************VERIFICANDO PARAMETROS*********************");
			
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(
					GenericAction.KEY_SESSION_USUARIO_FIRMADO);
			
			Long estado = NumberUtils.toLong(request.getParameter("estado"), 0L);
			String fechaInicio = "";
			String fechaFin = "";
			Long discriminanteId = 0L;
			
			if(usuarioDTO!=null && usuarioDTO.getFuncionario()!=null && usuarioDTO.getFuncionario().getDiscriminante()!=null
			   && usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId()!=null){
				discriminanteId = usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId();
			}
			
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaCreacionInicio = null;
			Date fechaCreacionFin = null;
			
			if(request.getParameter("inicio") != null && !request.getParameter("inicio").isEmpty()){
				fechaInicio = request.getParameter("inicio");
				try {
					fechaCreacionInicio = formato.parse(fechaInicio);						
				} catch (ParseException e) {				
					fechaCreacionInicio = null;
				}	
			}
			if(request.getParameter("fin") != null && !request.getParameter("fin").isEmpty()){
				fechaFin = request.getParameter("fin");
				try {
					fechaCreacionFin = formato.parse(fechaFin);						
				} catch (ParseException e) {				
					fechaCreacionFin = null;
				}					
			}
			
			if((estado!=null && !estado.equals(0L)) && discriminanteId!=null && !discriminanteId.equals(0L)){
			
				List<PermisoAudienciaDTO> PAsDto = audienciaDelegate.buscarPermisosAudienciasPorEstadoYFecha(estado, fechaCreacionInicio, fechaCreacionFin, discriminanteId);
				
				log.info("LISTA DE PERMISOS " + PAsDto);
				int lTotalRegistros = PAsDto.size();
				
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				writer.print("<rows>");
					writer.print("<records>" + lTotalRegistros + "</records>");
					
					for (PermisoAudienciaDTO PA : PAsDto) {
						writer.print("<row id='" + PA.getPermisoAudienciaId() +"'>");
						
						// Institución
						if(PA.getEsExterno()== null){
							writer.print("<cell>"+ "-----" + "</cell>");
						}
						else{
							if(PA.getEsExterno().equals(false)){
								writer.print("<cell>"+ PA.getConfInstitucion().getNombreInst() + "</cell>");	
							}
							else{
								if(PA.getFuncionarioExterno()!=null && PA.getFuncionarioExterno().getConfInstitucionDTO()!=null && 
										PA.getFuncionarioExterno().getConfInstitucionDTO().getNombreInst()!=null){
										writer.print("<cell>"+ PA.getFuncionarioExterno().getConfInstitucionDTO().getNombreInst() + "</cell>");
								}
								else{
									writer.print("<cell>"+ "-----" + "</cell>");
								}
							}
						}
						
						// Audiencia
						if(PA.getAudiencia()!=null && PA.getAudiencia().getId()!=null){
								writer.print("<cell>"+ PA.getAudiencia().getId() + "</cell>");
						}else{
							writer.print("<cell>"+ "-----" + "</cell>");
						} 						
						
						// Puesto
						if(PA.getEsExterno()!=null){
							if(PA.getEsExterno().equals(false)){
								if(PA.getUsuario()!=null && PA.getUsuario().getFuncionario()!=null &&
								   PA.getUsuario().getFuncionario().getPuesto()!=null &&
								   PA.getUsuario().getFuncionario().getPuesto().getValor()!=null){
									writer.print("<cell>"+ PA.getUsuario().getFuncionario().getPuesto().getValor() + "</cell>");																	
								}
								else{
									writer.print("<cell>"+ "-----" + "</cell>");
								}
							}
							else {
								if(PA.getFuncionarioExterno()!=null && 
								   PA.getFuncionarioExterno().getPuesto()!=null){
									writer.print("<cell>"+ PA.getFuncionarioExterno().getPuesto()+ "</cell>");
								}
								else{
									writer.print("<cell>"+ "-----" + "</cell>");
								}
							}							
						}else{
							writer.print("<cell>"+ "-----" + "</cell>");
						}
						
						// Datos Personales
						if(PA.getEsExterno()!=null){
							if(PA.getEsExterno().equals(false)){
								
								String nombreFuncionarioExterno="";
								
								if(PA.getUsuario()!=null && PA.getUsuario().getFuncionario()!=null && PA.getUsuario().getFuncionario().getNombreFuncionario()!=null){
									
									if(PA.getUsuario().getFuncionario().getNombreFuncionario()!=null){
										nombreFuncionarioExterno = PA.getUsuario().getFuncionario().getNombreFuncionario();
									}
									
									if(PA.getUsuario().getFuncionario().getApellidoPaternoFuncionario()!=null){
										if(!nombreFuncionarioExterno.equals("")){
											nombreFuncionarioExterno = nombreFuncionarioExterno + " " + PA.getUsuario().getFuncionario().getApellidoPaternoFuncionario();
										}
										else{
											nombreFuncionarioExterno = PA.getUsuario().getFuncionario().getApellidoPaternoFuncionario();
										}
									}

									if(PA.getUsuario().getFuncionario().getApellidoMaternoFuncionario()!=null){
										if(!nombreFuncionarioExterno.equals("")){
											nombreFuncionarioExterno = nombreFuncionarioExterno + " " + PA.getUsuario().getFuncionario().getApellidoMaternoFuncionario();
										}
										else{
											nombreFuncionarioExterno = PA.getUsuario().getFuncionario().getApellidoMaternoFuncionario();
										}
									}
									
									if(!nombreFuncionarioExterno.equals("")){
										writer.print("<cell>"+ nombreFuncionarioExterno + "</cell>");											
									}
									else{
										writer.print("<cell>"+ "-----" + "</cell>");
									}

								}								
								else{
									writer.print("<cell>"+ "-----" + "</cell>");
								}
							}
							else{
								String nombreUsuarioExterno="";
								
								if(PA.getFuncionarioExterno()!=null){
									
									if(PA.getFuncionarioExterno().getNombre()!=null){
										nombreUsuarioExterno = PA.getFuncionarioExterno().getNombre();
									}
									
									if(PA.getFuncionarioExterno().getApellidoPaterno()!=null){
										if(!nombreUsuarioExterno.equals("")){
											nombreUsuarioExterno = nombreUsuarioExterno + " " + PA.getFuncionarioExterno().getApellidoPaterno();
										}
										else{
											nombreUsuarioExterno = PA.getFuncionarioExterno().getApellidoPaterno();
										}
									}

									if(PA.getFuncionarioExterno().getApellidoMaterno()!=null){
										if(!nombreUsuarioExterno.equals("")){
											nombreUsuarioExterno = nombreUsuarioExterno + " " + PA.getFuncionarioExterno().getApellidoMaterno();
										}
										else{
											nombreUsuarioExterno = PA.getFuncionarioExterno().getApellidoMaterno();
										}
									}
									
									if(!nombreUsuarioExterno.equals("")){
										writer.print("<cell>"+ nombreUsuarioExterno + "</cell>");											
									}
									else{
										writer.print("<cell>"+ "-----" + "</cell>");
									}

								}								
								else{
									writer.print("<cell>"+ "-----" + "</cell>");
								}
							}							
						}else{
							writer.print("<cell>"+ "-----" + "</cell>");
						}							
						
						if(PA.getFechaSolicitud()!=null){
							writer.print("<cell>"+ PA.getFechaSolicitud() + "</cell>");
						}
						else{
							writer.print("<cell>"+ "-----" + "</cell>");
						}
						
						if(PA.getFechaAsignacion()!=null){
							writer.print("<cell>"+ PA.getFechaAsignacion()+ "</cell>");
						}
						else{
							writer.print("<cell>"+ "-----" + "</cell>");
						}
						
						if(PA.getUsuarioAsignador()!=null){
							String encargado="";

							if(PA.getUsuarioAsignador()!=null && PA.getUsuarioAsignador().getFuncionario()!=null){
								if(PA.getUsuarioAsignador().getFuncionario().getNombreFuncionario()!=null){	
									encargado += PA.getUsuarioAsignador().getFuncionario().getNombreFuncionario();
								}
								
								if(PA.getUsuarioAsignador().getFuncionario().getApellidoPaternoFuncionario()!=null){
									if(encargado.equals("")){
										encargado += PA.getUsuarioAsignador().getFuncionario().getApellidoPaternoFuncionario();
									}
									else{
										encargado += " " + PA.getUsuarioAsignador().getFuncionario().getApellidoPaternoFuncionario();
									}										
								}
								
								if(PA.getUsuarioAsignador().getFuncionario().getApellidoMaternoFuncionario()!=null){
									if(encargado.equals("")){
										encargado += PA.getUsuarioAsignador().getFuncionario().getApellidoMaternoFuncionario();
									}
									else{
										encargado += " " + PA.getUsuarioAsignador().getFuncionario().getApellidoMaternoFuncionario();
									}										
								}
							}
							
							if(!encargado.equals("")){
								writer.print("<cell>"+ encargado + "</cell>");	
							}
							else{
								writer.print("<cell>"+ "-----" + "</cell>");
							}							
						}
						else{
							writer.print("<cell>"+ "-----" + "</cell>");
						}
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
	}
	
	public ActionForward consultarBitacoraAudienciasJavs(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			
			log.info("EJECUTANDO ACTION CONSULTAR BITACORA DESCARGAS JAVS");
			log.info("******************VERIFICANDO PARAMETROS*********************");
			
			String fechaInicio = "";
			String fechaFin = "";
			
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaCreacionInicio = null;
			Date fechaCreacionFin = null;
			
			if(request.getParameter("inicio") != null && !request.getParameter("inicio").isEmpty()){
				fechaInicio = request.getParameter("inicio");
				try {
					fechaCreacionInicio = formato.parse(fechaInicio);						
				} catch (ParseException e) {				
					fechaCreacionInicio = null;
				}								
			}
			if(request.getParameter("fin") != null && !request.getParameter("fin").isEmpty()){
				fechaFin = request.getParameter("fin");
				try {
					fechaCreacionFin = formato.parse(fechaFin);						
				} catch (ParseException e) {				
					fechaCreacionFin = null;
				}				
			}
			
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(
					GenericAction.KEY_SESSION_USUARIO_FIRMADO);
			
			Long discriminanteId = 0L;
			
			if(usuarioDTO!=null && usuarioDTO.getFuncionario()!=null && usuarioDTO.getFuncionario().getDiscriminante()!=null
			   && usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId()!=null){
				discriminanteId = usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId();
			}
			
			Long audiencia = NumberUtils.toLong(request.getParameter("audiencia"), 0L);
			
			if(((fechaCreacionInicio!=null && fechaCreacionFin!=null) || !audiencia.equals(0L)) && !discriminanteId.equals(0L)){
				
				List<BitacoraDescargaDTO> bitDesDTO = new LinkedList<BitacoraDescargaDTO>();
			
				if(!audiencia.equals(0L)){
					bitDesDTO = audienciaDelegate.buscarBitacoraDescargaPorAudiencia(audiencia, discriminanteId);
				}
				else{
					bitDesDTO = audienciaDelegate.buscarBitacoraDescargaPorFecha(fechaCreacionInicio, fechaCreacionFin, discriminanteId);
				}
				
				List<PermisoAudienciaDTO> PAsDto = new LinkedList<PermisoAudienciaDTO>(); 
				
				for (BitacoraDescargaDTO bitDeDTO : bitDesDTO) {
					PAsDto.add(bitDeDTO.getPermisoAudienciaDTO());
				}
				
				log.info("LISTA DE PERMISOS " + PAsDto);
				int lTotalRegistros = PAsDto.size();
				
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				writer.print("<rows>");
					writer.print("<records>" + lTotalRegistros + "</records>");
					
					int i=0;
					for (PermisoAudienciaDTO PA : PAsDto) {
						writer.print("<row id='" + PA.getPermisoAudienciaId() +"'>");
						
						// Institución
						if(PA.getEsExterno()== null){
							writer.print("<cell>"+ "-----" + "</cell>");
						}
						else{
							if(PA.getEsExterno().equals(false)){
								writer.print("<cell>"+ PA.getConfInstitucion().getNombreInst() + "</cell>");	
							}
							else{
								if(PA.getFuncionarioExterno()!=null && PA.getFuncionarioExterno().getConfInstitucionDTO()!=null && 
										PA.getFuncionarioExterno().getConfInstitucionDTO().getNombreInst()!=null){
										writer.print("<cell>"+ PA.getFuncionarioExterno().getConfInstitucionDTO().getNombreInst() + "</cell>");
								}
								else{
									writer.print("<cell>"+ "-----" + "</cell>");
								}
							}
						}
						
						// Audiencia
						if(PA.getAudiencia()!=null && PA.getAudiencia().getId()!=null){
								writer.print("<cell>"+ PA.getAudiencia().getId() + "</cell>");
						}else{
							writer.print("<cell>"+ "-----" + "</cell>");
						} 						
						
						// Puesto
						if(PA.getEsExterno()!=null){
							if(PA.getEsExterno().equals(false)){
								if(PA.getUsuario()!=null && PA.getUsuario().getFuncionario()!=null &&
								   PA.getUsuario().getFuncionario().getPuesto()!=null &&
								   PA.getUsuario().getFuncionario().getPuesto().getValor()!=null){
									writer.print("<cell>"+ PA.getUsuario().getFuncionario().getPuesto().getValor() + "</cell>");																	
								}
								else{
									writer.print("<cell>"+ "-----" + "</cell>");
								}
							}
							else{
								if(PA.getFuncionarioExterno()!=null && 
								   PA.getFuncionarioExterno().getPuesto()!=null){
									writer.print("<cell>"+ PA.getFuncionarioExterno().getPuesto()+ "</cell>");
								}
								else{
									writer.print("<cell>"+ "-----" + "</cell>");
								}
							}							
						}else{
							writer.print("<cell>"+ "-----" + "</cell>");
						}
						
						// Datos Personales
						if(PA.getEsExterno()!=null){
							if(PA.getEsExterno().equals(false)){
								
								String nombreFuncionarioExterno=""; 
								
								if(PA.getUsuario()!=null && PA.getUsuario().getFuncionario()!=null && PA.getUsuario().getFuncionario().getNombreFuncionario()!=null){
									
									if(PA.getUsuario().getFuncionario().getNombreFuncionario()!=null){
										nombreFuncionarioExterno = PA.getUsuario().getFuncionario().getNombreFuncionario();
									}
									
									if(PA.getUsuario().getFuncionario().getApellidoPaternoFuncionario()!=null){
										if(!nombreFuncionarioExterno.equals("")){
											nombreFuncionarioExterno = nombreFuncionarioExterno + " " + PA.getUsuario().getFuncionario().getApellidoPaternoFuncionario();
										}
										else{
											nombreFuncionarioExterno = PA.getUsuario().getFuncionario().getApellidoPaternoFuncionario();
										}
									}

									if(PA.getUsuario().getFuncionario().getApellidoMaternoFuncionario()!=null){
										if(!nombreFuncionarioExterno.equals("")){
											nombreFuncionarioExterno = nombreFuncionarioExterno + " " + PA.getUsuario().getFuncionario().getApellidoMaternoFuncionario();
										}
										else{
											nombreFuncionarioExterno = PA.getUsuario().getFuncionario().getApellidoMaternoFuncionario();
										}
									}
									
									if(!nombreFuncionarioExterno.equals("")){
										writer.print("<cell>"+ nombreFuncionarioExterno + "</cell>");											
									}
									else{
										writer.print("<cell>"+ "-----" + "</cell>");
									}

								}								
								else{
									writer.print("<cell>"+ "-----" + "</cell>");
								}
							}
							else{
								String nombreFuncionarioExterno="";
								
								if(PA.getFuncionarioExterno()!=null){
									
									if(PA.getFuncionarioExterno().getNombre()!=null){
										nombreFuncionarioExterno = PA.getFuncionarioExterno().getNombre();
									}
									
									if(PA.getFuncionarioExterno().getApellidoPaterno()!=null){
										if(!nombreFuncionarioExterno.equals("")){
											nombreFuncionarioExterno = nombreFuncionarioExterno + " " + PA.getFuncionarioExterno().getApellidoPaterno();
										}
										else{
											nombreFuncionarioExterno = PA.getFuncionarioExterno().getApellidoPaterno();
										}
									}

									if(PA.getFuncionarioExterno().getApellidoMaterno()!=null){
										if(!nombreFuncionarioExterno.equals("")){
											nombreFuncionarioExterno = nombreFuncionarioExterno + " " + PA.getFuncionarioExterno().getApellidoMaterno();
										}
										else{
											nombreFuncionarioExterno = PA.getFuncionarioExterno().getApellidoMaterno();
										}
									}
									
									if(!nombreFuncionarioExterno.equals("")){
										writer.print("<cell>"+ nombreFuncionarioExterno + "</cell>");											
									}
									else{
										writer.print("<cell>"+ "-----" + "</cell>");
									}

								}								
								else{
									writer.print("<cell>"+ "-----" + "</cell>");
								}
							}							
						}else{
							writer.print("<cell>"+ "-----" + "</cell>");
						}							
						
						if(bitDesDTO.get(i).getFechaDescarga()!=null){
							writer.print("<cell>"+ bitDesDTO.get(i).getFechaDescarga() + "</cell>");
						}
						else{
							writer.print("<cell>"+ "-----" + "</cell>");
						}
			
						if(PA.getUsuarioAsignador()!=null){
							String encargado="";

							if(PA.getUsuarioAsignador()!=null && PA.getUsuarioAsignador().getFuncionario()!=null){
								if(PA.getUsuarioAsignador().getFuncionario().getNombreFuncionario()!=null){	
									encargado += PA.getUsuarioAsignador().getFuncionario().getNombreFuncionario();
								}
								
								if(PA.getUsuarioAsignador().getFuncionario().getApellidoPaternoFuncionario()!=null){
									if(encargado.equals("")){
										encargado += PA.getUsuarioAsignador().getFuncionario().getApellidoPaternoFuncionario();
									}
									else{
										encargado += " " + PA.getUsuarioAsignador().getFuncionario().getApellidoPaternoFuncionario();
									}										
								}
								
								if(PA.getUsuarioAsignador().getFuncionario().getApellidoMaternoFuncionario()!=null){
									if(encargado.equals("")){
										encargado += PA.getUsuarioAsignador().getFuncionario().getApellidoMaternoFuncionario();
									}
									else{
										encargado += " " + PA.getUsuarioAsignador().getFuncionario().getApellidoMaternoFuncionario();
									}										
								}
							}
							
							if(!encargado.equals("")){
								writer.print("<cell>"+ encargado + "</cell>");	
							}
							else{
								writer.print("<cell>"+ "-----" + "</cell>");
							}							
						}
						else{
							writer.print("<cell>"+ "-----" + "</cell>");
						}
						writer.print("</row>");
						i++;
					}
				writer.print("</rows>");
				writer.flush();
				writer.close();
				
			}
		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}
	
	public ActionForward consultarCondicionesAudiencias(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			
			List<CatEstadoPermisoDTO> EPsDTO = new ArrayList<CatEstadoPermisoDTO>();
			EPsDTO = audienciaDelegate.buscarEstadosPermisos();
			
			converter.alias("listaPermisos", java.util.List.class);
			converter.alias("listaPermisos", ArrayList.class);
			converter.alias("listaPermisos", List.class);
			converter.alias("catEstadoPermisoDTO", CatEstadoPermisoDTO.class);
			
			String xml = converter.toXML(EPsDTO);
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
	
	public ActionForward verificarSolicitudPermiso(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {

			AudienciaJAVSTransporteDTO audienciaJAVSTransporteDTO = new AudienciaJAVSTransporteDTO();
			
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(
					GenericAction.KEY_SESSION_USUARIO_FIRMADO);
			Long audienciaId = NumberUtils.toLong(request.getParameter("audienciaId"), 0L);
			String esExterno = request.getParameter("esExterno");
			
			Long resultado = EstatusPermisosAudiencia.FALLO.getValorId();
			Boolean respuesta = false;
			String xml="";
						
			if(esExterno==null){
				if(!audienciaId.equals(0L) && usuarioDTO!=null){
					respuesta = audienciaDelegate.esAudienciaJAVS(audienciaId);
					if(respuesta==false){
						resultado=EstatusPermisosAudiencia.NO_ES_JAVS.getValorId();
					}
					else{
						resultado=audienciaDelegate.verificarPermiso(audienciaId, usuarioDTO);
					}
				}
			}
			else{
				ConfInstitucionDTO confInstDto = configuracionDelegate.consultarInstitucionActual();
				audienciaJAVSTransporteDTO=audienciaDelegate.verificarPermisoExterno(audienciaId, usuarioDTO, confInstDto);
				
				if(audienciaJAVSTransporteDTO!=null && audienciaJAVSTransporteDTO.getResultadoPermisoAudiencia()!=null){
					resultado=audienciaJAVSTransporteDTO.getResultadoPermisoAudiencia();
					converter.alias("long",Long.class);							
					xml = converter.toXML(resultado.toString());
				}
				else{
					converter.alias("long",Long.class);							
					xml = converter.toXML(EstatusPermisosAudiencia.FALLO.getValorId());
				}
			}		

			if(xml.equals("")){
				converter.alias("long",Long.class);				
				xml = converter.toXML(resultado.toString());
			}
			
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

	public ActionForward solicitarPermisoAudienciaJAVS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {

			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(
					GenericAction.KEY_SESSION_USUARIO_FIRMADO);
			Date fechaHoy = new Date();
			Long audienciaId = NumberUtils.toLong(request.getParameter("audienciaId"), 0L);

			Long resultado = EstatusPermisosAudiencia.FALLO.getValorId();
			Boolean respuesta = false;
			
			if(!audienciaId.equals(0L) && usuarioDTO!=null && fechaHoy!=null){
				respuesta = audienciaDelegate.esAudienciaJAVS(audienciaId);
				if(respuesta==false){
					resultado=EstatusPermisosAudiencia.NO_ES_JAVS.getValorId();
				}
				else{
					resultado=audienciaDelegate.solicitarPermiso(audienciaId, usuarioDTO, fechaHoy);
				}
			}

			converter.alias("long",Long.class);
			String xml = converter.toXML(resultado);
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
	
	public ActionForward solicitarPermisoExternoAudienciaJAVS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {

			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(
					GenericAction.KEY_SESSION_USUARIO_FIRMADO);
			Long audienciaId = NumberUtils.toLong(request.getParameter("audienciaId"), 0L);
			Long confInstId = 0L;

			ConfInstitucionDTO confInstDto = configuracionDelegate.consultarInstitucionActual();
			
			if(confInstDto!=null && confInstDto.getConfInstitucionId()!=null){
				confInstId = confInstDto.getConfInstitucionId();
			}
			
			Long resultado = EstatusPermisosAudiencia.FALLO.getValorId();
			
			if(audienciaId!=null && !audienciaId.equals(0L) && usuarioDTO!=null 
				&& resultado!=null && !resultado.equals(0L)){
				resultado = audienciaDelegate.solicitarPermisoExterno(audienciaId, usuarioDTO, confInstId);
			}

			converter.alias("long",Long.class);
			String xml = converter.toXML(resultado);			
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
	
	public ActionForward updateEstadoPermisosAudiencias(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(
					GenericAction.KEY_SESSION_USUARIO_FIRMADO);
			
			Long estado = NumberUtils.toLong(request.getParameter("estado"), 0L);
			Long permisoAudiencia = NumberUtils.toLong(request.getParameter("seleccionado"), 0L);
			Date fechaHoy = new Date();
			String claveUsuarioAsignador = "";
			Long resultado = 0L;
			
			if(usuarioDTO!=null && usuarioDTO.getClaveUsuario()!=null){
				claveUsuarioAsignador = usuarioDTO.getClaveUsuario();
			}
			
			if(!estado.equals(0L) && !permisoAudiencia.equals(0L) && !claveUsuarioAsignador.equals("") && fechaHoy!=null){
				resultado=audienciaDelegate.cambiarEstadoPermisoAudiencia(estado, permisoAudiencia, fechaHoy, claveUsuarioAsignador);
			}
							
			converter.alias("long", Long.class);			
			String xml = converter.toXML(resultado);
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
}

