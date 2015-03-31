/**
* Nombre del Programa 			: ConsultaEventosAction.java
* Autor                         : AlejandroGA
* Compania                    	: Ultrasist
* Proyecto                      : NSJP                    Fecha: 01 June 2011
* Marca de cambio        		: N/A
* Descripcion General    		: Clase encargada de consultar los eventos para
* 								  notificaciones
* Programa Dependiente  		:N/A
* Programa Subsecuente 			:N/A
* Cond. de ejecucion        	:N/A
* Dias de ejecucion          	:N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       	:N/A
* Compania               		:N/A
* Proyecto                		:N/A                                 Fecha: N/A
* Modificacion          		:N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.evento.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.audiencia.BandejaNotificador;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.audiencia.Eventos;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.funcionario.TipoEspecialidad;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dao.transcripcion.CopiasAudioVideoDAO;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.documento.MandamientoJudicialDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.notificacion.NotificacionDelegate;
import mx.gob.segob.nsjp.delegate.persona.PersonaDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.EventoDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioExtranjeroDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.CopiasAudioVideo;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.StringUtils;
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
public class ConsultaEventosAction extends GenericAction{

	/**Log de clase*/
	private static final Logger log  = Logger.getLogger(ConsultaEventosAction.class);
	
	@Autowired
	public AudienciaDelegate audienciaDelegate;
	
	@Autowired
	public PersonaDelegate personaDelegate;
	
	@Autowired
	public MandamientoJudicialDelegate mandamientoJudicialDelegate;
	
	@Autowired
	public InvolucradoDelegate involucradoDelegate;
	
	@Autowired
	public CatalogoDelegate catalogoDelegate;
	
	@Autowired
	public NotificacionDelegate notificacionDelegate;
	
	/****************************/
	@Autowired
	public CopiasAudioVideoDAO copias;
	
	@Autowired
	public SolicitudDAO solicitudDao;
	
	
	private final static String KEY_SESSION_EVENTO = "KEY_SESSION_EVENTO_DTO";

	/**
	 * Metodo utilizado para realizar la consulta de nuevos eventos 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultaNuevasNotificaciones(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Consulta Nuevas Notificaciones - Carga eventos");
			
			EventoDTO eventoDTO = new EventoDTO(); 
			eventoDTO.setBandeja(BandejaNotificador.NUEVO);
			
			List<EventoDTO> listaNuevosEventos=audienciaDelegate.consultarEventosParaNotificar(eventoDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			writer.print("<rows>");
			
//			int lTotalRegistros=listaNuevosEventos.size();
			
//			writer.print("<records>" + lTotalRegistros + "</records>");
			
			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros() + "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}
			
			log.info("ANTES DE ENTRAR AL FOR");
			for (EventoDTO eventoNuevoDTO : listaNuevosEventos) {
				
				log.info("EVENTO"+eventoNuevoDTO);
				writer.print("<row id='" + eventoNuevoDTO.getId()+ "'>");
					if( eventoNuevoDTO.getExpediente() != null && eventoNuevoDTO.getExpediente().getNumeroExpediente() != null){
						writer.print("<cell>" + eventoNuevoDTO.getExpediente().getNumeroExpediente()+ "</cell>");
					}
					else{
						writer.print("<cell>" + "---" + "</cell>");
					}
					if( eventoNuevoDTO.getTipo() != null && eventoNuevoDTO.getTipo().getValor() != null ){
						writer.print("<cell>" + eventoNuevoDTO.getTipo().getValor()+ "</cell>");
					}
					else{
						writer.print("<cell>" + "---" + "</cell>");
					}
					if(eventoNuevoDTO.getTipoEvento() != null && eventoNuevoDTO.getTipoEvento().getNombre() != null){
						
						if(eventoNuevoDTO.getEstatusAudiencia().getIdCampo() == EstatusAudiencia.CANCELADA.getValorId().longValue())
							writer.print("<cell>Audiencia Cancelada</cell>");
						else{
							if(eventoNuevoDTO.getEstatusAudiencia().getIdCampo() == EstatusAudiencia.REPROGRAMADA.getValorId().longValue())
								writer.print("<cell>Audiencia Reprogramada</cell>");
							else
								writer.print("<cell>" + eventoNuevoDTO.getTipoEvento().getNombre()+ "</cell>");
						}
					}
					else{
						writer.print("<cell>" + "---" + "</cell>");
					}
					if(eventoNuevoDTO.getFechaSolicitud() != null){
						String fechaSolicitud=DateUtils.formatear(eventoNuevoDTO.getFechaSolicitud());
						String horaSolicitud=DateUtils.formatearHora(eventoNuevoDTO.getFechaSolicitud());
						writer.print("<cell>" + fechaSolicitud+" "+horaSolicitud+ "</cell>");
					}
					else{
						writer.print("<cell>" + "---" + "</cell>");
					}
					if( eventoNuevoDTO.getFechaEvento() != null){

						String fechaEvento=DateUtils.formatear(eventoNuevoDTO.getFechaEvento());
						String horaEvento=DateUtils.formatearHora(eventoNuevoDTO.getFechaEvento());
						writer.print("<cell>" + fechaEvento+" "+horaEvento+ "</cell>");
					}
					else{
						writer.print("<cell>" + "---" + "</cell>");
					}
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
	 * Metodo utilizado para realizar del detalle de eventos
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultaDetalleNotificaciones(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO CONSULTA DETALLE NOTIFICACIONES - CARGA DETALLE DEL EVENTO");
			
			//Se obtiene el id del evento a consultar a detalle
			String idEvento = request.getParameter("idEvento");
			//Se obtiene el tipo de respuesta que se desea, 
			String tipoDeRespuesta = request.getParameter("tipoDeRespuesta");
			//Permite saber si se desea consultar Funcionarios o Involucrados
			String esFuncionario = request.getParameter("esFuncionario");
			
			String solicitudId= request.getParameter("solicitudId");
			
			
			log.info("_____________________________________________________________");
			log.info("tipo respuesta::::::::::"+tipoDeRespuesta);
			log.info("id del envento::::::::::"+ idEvento);
			log.info("solicitudId :::::::::::::"+solicitudId);
			log.info("_____________________________________________________________");
			
			EventoDTO eventoDTO = new EventoDTO(); 
			eventoDTO.setId(Long.parseLong(idEvento));
			eventoDTO.setTipoEvento(Eventos.AUDENCIA);
			
			
			if(Integer.parseInt(tipoDeRespuesta) == 1){
				
				log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA DETALLE DE EVENTO:::::");
				/**
				 * Se desea enviar el objeto evento y en esta, parte de la respuesta
				 * solo llenar los campos que la TAB Detalle evento de la pantalla
				 * atencionSolicitudAudienciaNotificador.jsp
				 */
				
				log.info("antes del delegate:::::");
				eventoDTO = audienciaDelegate.obtenerEvento(eventoDTO);
				request.setAttribute("numeroExpediente", eventoDTO.getExpediente().getNumeroExpediente());
				setExpedienteTrabajo(request, eventoDTO.getExpediente());
				log.info("depues del delegate::: eventoDTO"+ eventoDTO);
				converter.alias("eventoDTO", EventoDTO.class);
				String xml = converter.toXML(eventoDTO);
				escribir(response, xml,null);
				//Se sube a sesion el objeto evento con todos sus atributos
				request.getSession().setAttribute(KEY_SESSION_EVENTO+idEvento, eventoDTO);
			}
			else{
				
				log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA GRID:::::");				
				AudienciaDTO audienciaDTO = new AudienciaDTO();		
				audienciaDTO.setId(Long.parseLong(idEvento));
						
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				writer.print("<rows>");		
						
				//Permite consultar funcionarios de una audiencia por especialidad
				List<Long> especialidades = new ArrayList<Long>();
				//Permite consultar involucrados de una audiencia por calidad
		    	List<Long> calidades = new ArrayList<Long>();

				
				List<InvolucradoDTO> listaInvolucrados = new ArrayList<InvolucradoDTO>();
				List<FuncionarioDTO> listaFuncionarios = new ArrayList<FuncionarioDTO>();

				
				if (esFuncionario.equals("0")){
			    	listaInvolucrados = involucradoDelegate.obtenerInvolucradosDTOAudienciaPorCalidades(audienciaDTO, calidades);

				}
				if (esFuncionario.equals("1")){
			    	listaFuncionarios = involucradoDelegate.obtenerFuncionariosDTOAudienciaPorTipoEspecialidad(audienciaDTO, especialidades);
				}


				final PaginacionDTO pag = PaginacionThreadHolder.get();
	            if (pag != null && pag.getTotalRegistros() != null) {
	                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
	                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
	            } else {
	                writer.print("<total>0</total>");
	                writer.print("<records>0</records>");
	            }			
	            
	            
				if (esFuncionario.equals("0")){
					
					int a=0;
					
					for (InvolucradoDTO invo : listaInvolucrados){						
						log.info("INVOLUCRADO:::"+invo);
						writer.print("<row id='" + invo.getElementoId()+ "'>");
							//Nombre del Involucrado
							writer.print("<cell>" + invo.getNombreCompleto()+ "</cell>");
							
							//Tipo evento a notificar
							if (eventoDTO.getTipoEvento() != null) {
								Eventos tipo = eventoDTO.getTipoEvento();
								if(	tipo != null &&
										!tipo.getNombre().isEmpty()) { 
									writer.print("<cell>" + tipo.getNombre()  + "</cell>");
								} else {
									writer.print("<cell>-</cell>");
								}
							} else {
								writer.print("<cell>-</cell>");	
							}
							
							//Calidad
							CalidadDTO calidadDTO = invo.getCalidadDTO();
							if(calidadDTO!= null) { 
								ValorDTO valorDTO = calidadDTO.getValorIdCalidad();
								if(	valorDTO != null &&
									!valorDTO.getValor().isEmpty()) { 
									writer.print("<cell>" + valorDTO.getValor()  + "</cell>");
								} else {
									writer.print("<cell>-</cell>");
								}
							} else {
								writer.print("<cell>-</cell>");
							}
							
							//Ultima Notificacion Creada/Recibida
							String fechaCreacion="";
							String horaCreacion="";
							
							String fechaRecepcion="";
							String horaRecepcion="";
							
							if( invo.getNotificaciones()!= null && invo.getNotificaciones().size() > 0){
								
								Integer ultimaNotif = invo.getNotificaciones().size()-1;
								
								fechaCreacion=DateUtils.formatear(invo.getNotificaciones().get(ultimaNotif).getFechaCreacion());
								horaCreacion=DateUtils.formatearHora(invo.getNotificaciones().get(ultimaNotif).getFechaCreacion());
								fechaRecepcion=DateUtils.formatear(invo.getNotificaciones().get(ultimaNotif).getFechaCitado());
								horaRecepcion=DateUtils.formatearHora(invo.getNotificaciones().get(ultimaNotif).getFechaCitado());
								
								writer.print("<cell>" + fechaCreacion+" "+horaCreacion+ "</cell>");
								writer.print("<cell>" + fechaRecepcion+" "+horaRecepcion+ "</cell>");
							}else{
								writer.print("<cell> - </cell>");
								writer.print("<cell> - </cell>");

							}
							
							//Numero de notificaciones
							if( invo.getNotificaciones() != null){
			            		
								String valor = request.getParameter("infromatica");

								if(valor != null){
			            			if(valor.equals("1")){
			            				
			            				Solicitud sol = new Solicitud();
			            				Long solicitud=Long.parseLong(solicitudId);
			            				sol = solicitudDao.consultarSolicitudPorId(solicitud);
			            				
			            				CopiasAudioVideo copia=null;
			            				
			            				if(sol.getEstatus().getValorId()==1776L){
			            				
				            				Long audienciaId=Long.parseLong(idEvento);
				        
				            				copia=copias.leerResponsable(audienciaId,solicitud,invo.getElementoId());
				            				
				            				log.debug("numero de copia :: "+copia.getNumeroCopias());
			            				}
			            				
			            				writer.print("<cell><![CDATA[<select id='numcp2_"+a+"'>");
			            				
		                                 for (int i = 0; i <= 20; i++) {
		                                        writer.print("<option value='"+i);
//		                                        writer.print("'>" +i + "</option>");
		                                        if(copia!=null){
		                                        	if(i==copia.getNumeroCopias()){
		                                        		writer.print("'SELECTED>"+i);
		                                        	}	
		                                        }
		                                        else{
		                                        	writer.print("'>" +i + "</option>");
		                                        }
		                                 }                                       
//				                         writer.print("' SELECTED>0");     
				                         writer.print("</select>"+ "]]></cell>");
			            			}
								}else{
									writer.print("<cell>" + invo.getNotificaciones().size()+ "</cell>");	
									//log.info("INVOLUCRADO :: "+invo.getNotificaciones().size()+ "id"+invo.getNotificaciones());
								}
								a++;
							}
							else{
								writer.print("<cell>0</cell>");
							}
							
							//Es funcionario
							writer.print("<cell>0</cell>");
							
						writer.print("</row>");
					}
				}
				
	            if (esFuncionario.equals("1")){
	            	
	            	int a =0;
	            	
	            	for (FuncionarioDTO func : listaFuncionarios) {					
	            		log.info("funcioanrio ::: "+func);
	            		writer.print("<row id='" + func.getClaveFuncionario()+ "'>");
	            		//Nombre del Involucrado
	            		writer.print("<cell>" + func.getNombreCompleto()+ "</cell>");
	            		
	            		
	            		//Institucion
	            		
	            		String nombreBDFGE = catalogoDelegate.consultarIntitucionPorClave("FGE").getNombreInst();
	            		String nombreBDDef = catalogoDelegate.consultarIntitucionPorClave("DEF").getNombreInst();
	            		String nombreBDPJ= catalogoDelegate.consultarIntitucionPorClave("PJ").getNombreInst();
	            		
	            		if(TipoEspecialidad.getByValor(func.getTipoEspecialidad().getIdCampo()) != null){
	            			switch(TipoEspecialidad.getByValor(func.getTipoEspecialidad().getIdCampo())){
		            		case DEFENSOR:
		            			func.getInstitucion().setNombreInst(nombreBDDef);
		            			func.getInstitucion().setConfInstitucionId(Instituciones.DEF.getValorId());
		            			break;
		            		case MINISTERIO_PUBLICO:
		            			func.getInstitucion().setNombreInst(nombreBDFGE);
		            			func.getInstitucion().setConfInstitucionId(Instituciones.PGJ.getValorId());
		            			break;
		            		case PERICIAL:
		            			func.getInstitucion().setNombreInst(nombreBDFGE);
		            			func.getInstitucion().setConfInstitucionId(Instituciones.PGJ.getValorId());
		            			break;
		            		case POLICIA:
		            			func.getInstitucion().setNombreInst(nombreBDFGE);
		            			func.getInstitucion().setConfInstitucionId(Instituciones.PGJ.getValorId());
		            			break;
		            		default:
		            			func.getInstitucion().setNombreInst(nombreBDPJ);
		            			func.getInstitucion().setConfInstitucionId(Instituciones.PJ.getValorId());		            			
		            			break;
		            		}	            			
	            		}else{
	            			func.getInstitucion().setNombreInst("-");
	            			func.getInstitucion().setConfInstitucionId(Instituciones.PJ.getValorId());	
	            		}
	            		
	            		/**
	            		 * Enable se cambia confinstitucion por nombre institucion ya que en funcionario no replica informacion 
	            		 * y siempre mostrara la institucion en que se encuentra logeado pudiendo ser de otra institucion diferente
	            		 * */
	            		
	            		writer.print("<cell>"+func.getInstitucion().getConfInstitucionId()+"</cell>");
	            		//writer.print("<cell>"+func.getInstitucion().getNombreInst()+"</cell>");
	            		writer.print("<cell>"+func.getInstitucion().getNombreInst()+"</cell>");
	            		
	            		//Tipo 
	            		if (eventoDTO.getTipoEvento() != null) {
	            			Eventos tipo = eventoDTO.getTipoEvento();
	            			if(	tipo != null &&
	            					!tipo.getNombre().isEmpty()) { 
	            				writer.print("<cell>" + tipo.getNombre()  + "</cell>");
	            			} else {
	            				writer.print("<cell>-</cell>");
	            			}
	            		} else {
	            			writer.print("<cell>-</cell>");	
	            		}
	            		
	            		//Notificacion Enviada
	            		String fechaCreacion="";
	            		String horaCreacion="";
	            		String fechaRecepcion="";
	            		String horaRecepcion="";
	            		
	            		if( func.getNotificaciones()!= null && func.getNotificaciones().size() > 0){
	            			
	            			Integer ultimaNotif = func.getNotificaciones().size()-1;
	            			
	            			fechaCreacion=DateUtils.formatear(func.getNotificaciones().get(ultimaNotif).getFechaCreacion());
	            			horaCreacion=DateUtils.formatearHora(func.getNotificaciones().get(ultimaNotif).getFechaCreacion());
	            			fechaRecepcion=DateUtils.formatear(func.getNotificaciones().get(ultimaNotif).getFechaCitado());
	            			horaRecepcion=DateUtils.formatearHora(func.getNotificaciones().get(ultimaNotif).getFechaCitado());
	            			
	            			writer.print("<cell>" + fechaCreacion+" "+horaCreacion+ "</cell>");
	            			writer.print("<cell>" + fechaRecepcion+" "+horaRecepcion+ "</cell>");
	            		}else{
	            			writer.print("<cell> - </cell>");
	            			writer.print("<cell> - </cell>");
	            		}
	            		
	            		if( func.getNotificaciones() != null){	//writer.print("<cell>" + func.getNotificaciones().size()+ "</cell>");	
            				//log.info("FUNCIONARIO :: "+func.getNotificaciones().size()+ "id"+func.getNotificaciones());
	            			
						String valor = request.getParameter("infromatica");

						if(valor != null){
	            			if(valor.equals("1")){
	            				
	            				Solicitud sol = new Solicitud();
	            				Long solicitud=Long.parseLong(solicitudId);
	            				sol = solicitudDao.consultarSolicitudPorId(solicitud);
	            				
	            				CopiasAudioVideo copia=null;
	            				
	            				if(sol.getEstatus().getValorId()==1776L){
	            				
		            				Long audienciaId=Long.parseLong(idEvento);
		        
		            				copia=copias.leerResponsable(audienciaId,solicitud,func.getClaveFuncionario());
		            				
		            				log.debug("numero de copia :: "+copia.getNumeroCopias());
	            				}
	            				
	            				writer.print("<cell><![CDATA[<select id='numcp_"+a+"'>");
	            				
                                 for (int i = 0; i <= 20; i++) {
                                        writer.print("<option value='"+i);
//                                        writer.print("'>" +i + "</option>");
                                        if(copia!=null){
                                        	if(i==copia.getNumeroCopias()){
                                        		writer.print("'SELECTED>"+i);
                                        	}	
                                        }
                                        else{
                                        	writer.print("'>" +i + "</option>");
                                        }
                                 }                                       
//		                         writer.print("' SELECTED>0");     
		                         writer.print("</select>"+ "]]></cell>");
	            			}
						}else{
							writer.print("<cell>" + func.getNotificaciones().size()+ "</cell>");	
							//log.info("INVOLUCRADO :: "+invo.getNotificaciones().size()+ "id"+invo.getNotificaciones());
						}
						a++;
            		}

	            		else{
	            			writer.print("<cell>0</cell>");
	            		}
	            		
	            		writer.print("<cell>1</cell>");
	            		
	            		writer.print("</row>");
	            	}	
	            }
			
				writer.print("</rows>");
				writer.flush();
				writer.close();			
			}						
			
		} catch (Exception e) {		
			log.info("ERROR AL CONSULTAR EL EVENTO ---- consultaDetalleNotificaciones");
			log.info(e.getCause(),e);
			escribir(response, "consultaDetalleNotificaciones",null);
			
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para realizar la consulta de eventos por expediente (SOLO HISTORCIO) 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultaEventosPorExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Consulta Eventos por expediente- HISTORICO");
			
			EventoDTO eventoDTO = new EventoDTO(); 
			eventoDTO.setBandeja(BandejaNotificador.HISTORICO);
			
			
			String numeroExpedienteId = request.getParameter("numeroExpedienteId");
			log.info("ID DEL NUMERO DE EXPEDIENTE="+numeroExpedienteId);
			
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();	
			expedienteDTO.setNumeroExpedienteId(Long.parseLong(numeroExpedienteId));
			eventoDTO.setExpediente(expedienteDTO);
	
			
			List<EventoDTO> listaNuevosEventos=audienciaDelegate.consultarEventosParaNotificar(eventoDTO);

			log.info("EVENTOs"+listaNuevosEventos);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			writer.print("<rows>");
			
			int lTotalRegistros=listaNuevosEventos.size();
			
			writer.print("<records>" + lTotalRegistros + "</records>");
				for (EventoDTO eventoNuevoDTO : listaNuevosEventos) {
					
					log.info("EVENTO"+eventoNuevoDTO);
					
					writer.print("<row id='" + eventoNuevoDTO.getId()+ "'>");
						if(eventoNuevoDTO.getExpediente() != null && eventoNuevoDTO.getExpediente().getNumeroExpediente() != null){
							writer.print("<cell>" + eventoNuevoDTO.getExpediente().getNumeroExpediente()+ "</cell>");
						}
						else{
							writer.print("<cell>" + "---" + "</cell>");
						}
						if( eventoNuevoDTO.getTipo() != null && eventoNuevoDTO.getTipo().getValor() != null){
							writer.print("<cell>" + eventoNuevoDTO.getTipo().getValor()+ "</cell>");
						}
						else{
							writer.print("<cell>" + "---" + "</cell>");
						}
						if( eventoNuevoDTO.getTipoEvento() != null && eventoNuevoDTO.getTipoEvento().getNombre() != null ){
							writer.print("<cell>" + eventoNuevoDTO.getTipoEvento().getNombre()+ "</cell>");
						}
						else{
							writer.print("<cell>" + "---" + "</cell>");
						}
						if(eventoNuevoDTO.getFechaSolicitud() != null ){
							String fechaSolicitud=DateUtils.formatear(eventoNuevoDTO.getFechaSolicitud());
							String horaSolicitud=DateUtils.formatearHora(eventoNuevoDTO.getFechaSolicitud());
							writer.print("<cell>" + fechaSolicitud+" "+horaSolicitud+ "</cell>");
						}
						else{
							writer.print("<cell>" + "---" + "</cell>");
						}
						if( eventoNuevoDTO.getFechaEvento() != null){
							String fechaEvento=DateUtils.formatear(eventoNuevoDTO.getFechaEvento());
							String horaEvento=DateUtils.formatearHora(eventoNuevoDTO.getFechaEvento());
							writer.print("<cell>" + fechaEvento+" "+horaEvento+ "</cell>");
						}
						else{
							writer.print("<cell>" + "---" + "</cell>");
						}
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
	 * Metodo utilizado para realizar el seguimiento a audiencias
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward darSeguimientoEventos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUNTANDO ACTION CONSULTAR EVENTOS POR ESTATUS NOTIFICACION");


			NotificacionDTO notificacionDto = new NotificacionDTO();
			
			Long estatusNotificacion = NumberUtils.toLong(request.getParameter("estatusNotificacion"),0L);

			//Si el estatus en NULL o CERO consultara las audiecias que no tengan notificaciones asociadas
			if(estatusNotificacion != null && estatusNotificacion > 0L){
				ValorDTO estatus = new ValorDTO();
				estatus.setIdCampo(estatusNotificacion);
				notificacionDto.setEstatus(estatus);
			}
			
//			String tipoEvento = request.getParameter("eventoTipo");

//			if(tipoEvento.equalsIgnoreCase("audiencia")){
//				log.info("SEGUIMENTO AUDIENCIAS");
//				eventoDTO.setTipoEvento(Eventos.AUDENCIA);
//			}
//
//			if(tipoEvento.equalsIgnoreCase("recurso")){
//				log.info("SEGUIMENTO RECURSO");
//				eventoDTO.setTipoEvento(Eventos.RECURSO);
//			}

			List<EventoDTO> listaNuevosEventos=audienciaDelegate.consultarEventosPorEstatusNotificacion(notificacionDto);

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
			
			for (EventoDTO eventoNuevoDTO : listaNuevosEventos) {

				log.info("EVENTO"+eventoNuevoDTO);
				writer.print("<row id='" + eventoNuevoDTO.getId()+ "'>");
				if(eventoNuevoDTO.getExpediente() != null && eventoNuevoDTO.getExpediente().getNumeroExpediente() != null){
					writer.print("<cell>" + eventoNuevoDTO.getExpediente().getNumeroExpediente()+ "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}
				if(eventoNuevoDTO.getTipo() != null &&  eventoNuevoDTO.getTipo().getValor() != null){
					writer.print("<cell>" + eventoNuevoDTO.getTipo().getValor()+ "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}
				if(eventoNuevoDTO.getTipoEvento() != null && eventoNuevoDTO.getTipoEvento().getNombre() != null){
					writer.print("<cell>" + eventoNuevoDTO.getTipoEvento().getNombre()+ "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}
				if( eventoNuevoDTO.getFechaSolicitud() != null){
					String fechaSolicitud=DateUtils.formatear(eventoNuevoDTO.getFechaSolicitud());
					String horaSolicitud=DateUtils.formatearHora(eventoNuevoDTO.getFechaSolicitud());
					writer.print("<cell>" + fechaSolicitud+" "+horaSolicitud+ "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}						
				if( eventoNuevoDTO.getFechaEvento() != null){
					String fechaEvento=DateUtils.formatear(eventoNuevoDTO.getFechaEvento());
					String horaEvento=DateUtils.formatearHora(eventoNuevoDTO.getFechaEvento());
					writer.print("<cell>" + fechaEvento+" "+horaEvento+ "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}
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
	 * Metodo utilizado para realizar el paso del parametro id del evento
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward acarrearIdEvento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action acarrearIdEvento");
			
			String idEvento = request.getParameter("idEvento");
			log.info("ID-DEL EVENTO:::"+ idEvento);
			request.setAttribute("idEvento",idEvento);
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return mapping.findForward("succes");
	}	
	
	/**
	 * Metodo utilizado para realizar la consulta de el detalle de la persona por notificacion 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultaDetalleNotificacionesPersona(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		try {	
			
			log.info("EJECUTANDO CONSULTA DETALLE NOTIFICACIONES PERSONA");
			
			//Se obtiene el id del elemento
			String idInvolucrado = request.getParameter("rowID");
			//Se obtiene el tipo de respuesta que se desea, 
			String idEvento = request.getParameter("idEvento");
			//Se obtiene el tipo de respuesta que se desea 
			String tipoDeRespuesta = request.getParameter("tipoDeRespuesta");
			//Se obtiene el tipo de respuesta que se desea 
			String esFuncionario = request.getParameter("esFuncionario");
			log.info("_____________________________________________________________");
			log.info("id del evento::::::::::"+ idEvento);
			log.info("id del involucrado::::::::::"+ idInvolucrado);
			log.info("tipo respuesta::::::::::"+tipoDeRespuesta);
			log.info("_____________________________________________________________");
			
			if(idEvento != null && idInvolucrado != null && !idInvolucrado.equalsIgnoreCase("") && tipoDeRespuesta != null && !tipoDeRespuesta.equalsIgnoreCase("")){
				
				if(Integer.parseInt(tipoDeRespuesta) == 1){
					
					EventoDTO eventoDTO = new EventoDTO();
					eventoDTO.setId(Long.parseLong(idEvento));
					eventoDTO.setTipoEvento(Eventos.AUDENCIA);
					eventoDTO = (EventoDTO) audienciaDelegate.obtenerEvento(eventoDTO);
					
					log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA DETALLE DE LOS DATOS DE LA PERSONA:::::");
					/**
					 * Se desea enviar el objeto evento y en esta, parte de la respuesta
					 * solo llenar los campos que la TAB Detalle evento de la pantalla
					 * atencionSolicitudAudienciaNotificador.jsp
					 */
					InvolucradoDTO involucrado = null;
					FuncionarioDTO funcionario = null;
					String xml = "";
					if(esFuncionario.equals("1")){
						for (FuncionarioDTO func : eventoDTO.getFuncionarios()) {					
							if(func.getClaveFuncionario() == Long.parseLong(idInvolucrado)){
								funcionario = func;
								log.info("FUNCIONARIO_DTO, OBTENIDO:::::"+func);
							}
						}
						converter.alias("funcionario", FuncionarioDTO.class);
						xml = converter.toXML(funcionario);
					}else{
						for (InvolucradoDTO invo : eventoDTO.getInvolucrados()) {					
							if(invo.getElementoId() == Long.parseLong(idInvolucrado)){
								involucrado = invo;
								log.info("INVOLUCRADO_DTO, OBTENIDO:::::"+invo);
							}
						}
						converter.alias("involucrado", InvolucradoDTO.class);
						xml = converter.toXML(involucrado);
					}
					

					escribir(response, xml, null);
				}
				else{
					
					log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA GRID DETALLE NOTIFICACIONES:::::");					
					
					/**
					 * En esta parte de la respuesta se desea enviar la informacion del grid,
					 * correspondiente al detalle de notificaciones de una persona en la ventana modal de  
					 * atencionSolicitudAudienciaNotificador.jsp
					 */
					List<NotificacionDTO> lista = null;
					if(esFuncionario.equals("1")){
						Boolean consultarNotDeFuncionario = true;
						lista = notificacionDelegate.consultaNotificaciones(Long.parseLong(idEvento),Long.parseLong(idInvolucrado),consultarNotDeFuncionario);
					}else{
						Boolean consultarNotDeFuncionario = false;
						lista = notificacionDelegate.consultaNotificaciones(Long.parseLong(idEvento),Long.parseLong(idInvolucrado),consultarNotDeFuncionario);
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
						
					for(NotificacionDTO notificacionDTO : lista){
						
						log.info("NOTIFICACION:::::::::::::::"
								+ notificacionDTO);
						writer.print("<row id='"
								+ notificacionDTO.getDocumentoId() + "'>");

						// NUMERO DE NOTIFICACION
						if (notificacionDTO.getConsecutivoNotificacion() != null) {
							writer.print("<cell>"
									+ notificacionDTO
											.getConsecutivoNotificacion()
									+ "</cell>");
						} else {
							writer.print("<cell>" + "---" + "</cell>");
						}

						// FORMA NOTIFICACION
						if (notificacionDTO.getCatFormaNotificacionDTO() != null
								&& notificacionDTO.getCatFormaNotificacionDTO()
										.getFormaNotificacion() != null) {
							writer.print("<cell>"
									+ notificacionDTO
											.getCatFormaNotificacionDTO()
											.getFormaNotificacion() + "</cell>");
						} else {
							writer.print("<cell>" + "---" + "</cell>");
						}
							
							
						// FECHA / HORA DE CREACION DE LA NOTIFICACION
						if (notificacionDTO.getFechaCreacion() != null) {
							String fechaCreacion = DateUtils
									.formatear(notificacionDTO
											.getFechaCreacion());
							String horaCreacion = DateUtils
									.formatearHora(notificacionDTO
											.getFechaCreacion());
							writer.print("<cell>" + fechaCreacion + " "
									+ horaCreacion + "</cell>");
						} else {
							writer.print("<cell>" + "---" + "</cell>");
						}
											
							
						//FECHA DE RECEPCION DE LA NOTIFICACION
						if(notificacionDTO.getFechaCitado() != null){
							String fechaRecepcion=DateUtils.formatear(notificacionDTO.getFechaCitado());
							writer.print("<cell>" + fechaRecepcion +"</cell>");
						}
						else{
							
							if (notificacionDTO.getEstatus() != null
									&& notificacionDTO.getEstatus()
											.getIdCampo() != null
									&& notificacionDTO
											.getEstatus()
											.getIdCampo()
											.equals(EstatusNotificacion.NO_ATENDIDA
													.getValorId())){
								
								writer.print("<cell>" + "NA" +"</cell>");
							}
							else{
								writer.print("<cell><![CDATA[<input type='text'  style='width:70px;' readonly='readonly' id='fechaRecepcion_"
										+ notificacionDTO.getDocumentoId()
										+ "'>]]></cell>");
							}
						}
							
							
						//HORA DE RECEPCION DE LA NOTIFICACION
						if(notificacionDTO.getFechaCitado() != null){
							String horaRecepcion=DateUtils.formatearHora(notificacionDTO.getFechaCitado());
							writer.print("<cell>"+ horaRecepcion + "</cell>");
						}
						else{
							
							if (notificacionDTO.getEstatus() != null
									&& notificacionDTO.getEstatus()
											.getIdCampo() != null
									&& notificacionDTO
											.getEstatus()
											.getIdCampo()
											.equals(EstatusNotificacion.NO_ATENDIDA
													.getValorId())){
								
								writer.print("<cell>" + "NA" +"</cell>");
							}else{
								// writer.print("<cell><![CDATA[<input type='text'  style='width:40px;' readonly='readonly' id='horaRecepcion_"+notificacionDTO.getDocumentoId()+"'>]]></cell>");
								writer.print("<cell><![CDATA[<input type='text' readonly='readonly' style='width:40px;'  id='horaRecepcion_"
										+ notificacionDTO.getDocumentoId()
										+ "'>"
										+ "<input type='button' value='Guardar'  style='width:55px;' id='btnConfirmarFechaHora'  onclick='confirmarFechaHoraRecepcion("
										+ notificacionDTO.getDocumentoId()
										+ ")'/>"
										
										+ "<input type='button' value='NA' style='width:25px;' id='btnNoAplica'  onclick='confirmarNoAplica("
										+ notificacionDTO.getDocumentoId()
										+ ")'/>"
										
										+ "]]></cell>");
							}
						}
						
						//OBSERVACIONES
						if(notificacionDTO.getMotivo() != null && !notificacionDTO.getMotivo().isEmpty()){
							writer.print("<cell><![CDATA[<textarea readonly='readonly' id='textArea_"
									+ notificacionDTO.getDocumentoId()
									+ "'>"+notificacionDTO.getMotivo()+"</textarea>]]></cell>");
						}else{
							writer.print("<cell><![CDATA[<textarea id='textArea_"
									+ notificacionDTO.getDocumentoId()
									+ "'/>]]></cell>");
						}
						

						//NOMBRE DEL DOCUMENTO
						if(notificacionDTO.getNombreDocumento() != null){
							//writer.print("<cell>" +notificacionDTO.getNombreDocumento()+"</cell>");
							writer.print("<cell><![CDATA[<a href='#' title='Ver Documento' onclick='abreDocumento("+ notificacionDTO.getDocumentoId() + ","+ notificacionDTO.getEsGuardadoParcial() + ")'>"+notificacionDTO.getNombreDocumento()+"</a>]]></cell>");
						}
						else{
							writer.print("<cell>" + "---"+ "</cell>");
						}
							
						writer.print("</row>");
					}
				
					writer.print("</rows>");
					writer.flush();
					writer.close();
				}
			}
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para agregar un destinatario
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward agregarDestinatario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO ACTION AGREGAR DESTINATARIO");
			
			String idEventoStr = request.getParameter("idEvento");
			String idExpediente = request.getParameter("idExpediente");
			String nombreDest = request.getParameter("nombreDest");
			String apPatDest = request.getParameter("apPatDest");
			String apMatDest = request.getParameter("apMatDest");
			// String institucionDest = request.getParameter("institucionDest");
			String dirElectDest = request.getParameter("dirElectDest");
			String pais = request.getParameter("pais");
			String codigoPostal = request.getParameter("codigoPostal");
			String entidadFederativa = request.getParameter("entidadFederativa");
			String ciudad = request.getParameter("ciudad");
			String delegacionMunicipio = request.getParameter("delegacionMunicipio");
			String asentamientoColonia = request.getParameter("asentamientoColonia");
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
		
			//Seteamos el id del expedienta
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setExpedienteId(Long.parseLong(idExpediente));
			
			//SETEAMOS LOS DATOS DE LA PERSONA
				//Para setear la calidad
			CalidadDTO calidadDTO = new CalidadDTO();
				//cambiar la calidad del individuo
			calidadDTO.setCalidades(Calidades.SIN_CALIDAD_JURIDICA);
			
				//Nombre
			NombreDemograficoDTO nombre = new NombreDemograficoDTO();
			
			nombre.setNombre(nombreDest);
			nombre.setApellidoPaterno(apPatDest);
			nombre.setApellidoMaterno(apMatDest);
			
			List<NombreDemograficoDTO> nombresDemograficoDTO = new ArrayList<NombreDemograficoDTO>();
			
			
			nombresDemograficoDTO.add(nombre);
			
			InvolucradoDTO involucradoNuevo = new InvolucradoDTO();
			involucradoNuevo.setCalidadDTO(calidadDTO);
			involucradoNuevo.setNombresDemograficoDTO(nombresDemograficoDTO);
				
				//Fecha de creacion del elemento
			involucradoNuevo.setFechaCreacionElemento(new Date());
			
			//Se agrega la dirección de correo electronico al Involucrado
			List<CorreoElectronicoDTO> llCorreos = new ArrayList<CorreoElectronicoDTO>();
			llCorreos.add(new CorreoElectronicoDTO(dirElectDest));			
			involucradoNuevo.setCorreosDTO(llCorreos);
			
			
			//CUANDO EL PAIS ES MEXICO
			if((Long.parseLong(pais)==10 || pais.equalsIgnoreCase("mexico") || pais.equalsIgnoreCase("méxico")) && (Long.parseLong(pais)!= -1L)){
				
				DomicilioDTO domicilioDTO = new DomicilioDTO();
				
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
				if(tipoCalle.equalsIgnoreCase("")){
					
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
				
				if(StringUtils.isNotBlank(longitud)){
					domicilioDTO.setLongitud(longitud);
				}
				else{
					domicilioDTO.setLongitud(null);
				}
				if(StringUtils.isNotBlank(latitud)){
					domicilioDTO.setLatitud(latitud);
				}
				else{
					domicilioDTO.setLatitud(null);
				}
				//Seteamos la fecha de creacion del elemento
				domicilioDTO.setFechaCreacionElemento(new Date());
				
				//seteamos el expediente
				domicilioDTO.setExpedienteDTO(expedienteDTO);
				
				//Seteamos la calidad del domicilio
				CalidadDTO calidadDomicilioDTO = new CalidadDTO();
				calidadDomicilioDTO.setCalidades(Calidades.DOMICILIO);
				domicilioDTO.setCalidadDTO(calidadDomicilioDTO);
				
				//Seteamos el id del aexpediente al elemento persona
				involucradoNuevo.setExpedienteDTO(expedienteDTO);
				//Seteamos el domicilio de notificaciones a la persona
				involucradoNuevo.setDomicilioNotificacion(domicilioDTO);				
	
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
				//Seteamos la fecha de creacion del elemento
				domicilioExtranjeroDTO.setFechaCreacionElemento(new Date());
				
				//Seteamos el expediente
				domicilioExtranjeroDTO.setExpedienteDTO(expedienteDTO);
				
				//Seteamos la calidad del domicilio
				CalidadDTO calidadDomicilioExtranjeroDTO = new CalidadDTO();
				calidadDomicilioExtranjeroDTO.setCalidades(Calidades.DOMICILIO);
				domicilioExtranjeroDTO.setCalidadDTO(calidadDomicilioExtranjeroDTO);
				
				involucradoNuevo.setExpedienteDTO(expedienteDTO);
				//Seteamos el domicilio extranjero de notificaciones a la persona
				involucradoNuevo.setDomicilioNotificacion(domicilioExtranjeroDTO);
		}
		
			//Delegate para ingresar
			involucradoNuevo.setElementoId(involucradoDelegate.guardarInvolucrado(involucradoNuevo));
			
			Long idEvento = NumberUtils.toLong(idEventoStr,0L);
			if(idEvento > 0){
				audienciaDelegate.asociarInvolucradoAAudiencia(involucradoNuevo.getElementoId(), idEvento);
			}
			
			String xml = converter.toXML("ok");
			escribir(response, xml,null);
			
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;		
	}

	/**
	 * M&eacute;todo para obtener los Expedientes por Mandamientos Judiciales en funci&oacute;n de un filtro
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ok - En caso de que el proceso se cumpla correctamente
	 */
	public ActionForward consultaMandamientosJudicialesGenerico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String inicio 			= request.getParameter("inicio");
		String fin    			= request.getParameter("fin");
		String numeroExpediente = request.getParameter("numeroExpediente");
		Long  estatusMedida = NumberUtils.toLong(request.getParameter("estatus"),0L);

		
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

			MandamientoDTO loMandamientoDTO = new MandamientoDTO();
			
			loMandamientoDTO.setFechaFinal(fechaFin);
			loMandamientoDTO.setFechaInicial(fechaInicio);
			ExpedienteDTO loExpedienteDTO = new ExpedienteDTO();
			loExpedienteDTO.setNumeroExpediente(numeroExpediente);
			loMandamientoDTO.setExpedienteDTO(loExpedienteDTO);
			
			if(estatusMedida != 0L)
				loMandamientoDTO.setEstatus(new ValorDTO(estatusMedida));
			
			List<MandamientoDTO> listaMandamientos = mandamientoJudicialDelegate.consultarMandamientoPorFiltro(loMandamientoDTO,numeroExpediente);
				
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
			
			for (MandamientoDTO mandamiento : listaMandamientos) {
			
				writer.print("<row id='"+ mandamiento.getInvolucrado().getElementoId()+","+mandamiento.getDocumentoId() + "'>");
				
				writer.print("<cell>" +  ( (mandamiento!=null 
						&& mandamiento.getExpedienteDTO()!=null
		    			&& mandamiento.getExpedienteDTO().getCasoDTO() != null 
		    			&& mandamiento.getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso() != null)
		    			? mandamiento.getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso():"") + "</cell>");
					
					writer.print("<cell>" +  ( (mandamiento!=null && mandamiento.getExpedienteDTO()!=null
			    			&& mandamiento.getExpedienteDTO().getNumeroExpediente() != null) 
			    			?  mandamiento.getExpedienteDTO().getNumeroExpediente():"") + "</cell>");
			    	
		    	
				
			    	writer.print("<cell>" + mandamiento.getInvolucrado().getNombreCompleto() + "</cell>");
			    	
			    	writer.print("<cell>" +  (mandamiento!=null?mandamiento.getTipoMandamiento().getValor():"") + "</cell>");
			    	
			    	writer.print("<cell>" + (mandamiento!=null && mandamiento.getFechaCreacion() != null ? DateUtils.formatear(mandamiento.getFechaCreacion()): "-") + "</cell>");
			    	
			    	
			    	writer.print("<cell>" +  ( (mandamiento!=null && mandamiento.getTipoSentencia()!=null && 
			    			mandamiento.getTipoSentencia().getValor()!=null)? mandamiento.getTipoSentencia().getValor() :"-") + "</cell>");
			    	writer.print("<cell>" + (mandamiento!=null && mandamiento.getFechaInicial() != null ? DateUtils.formatear(mandamiento.getFechaInicial()): "-") + "</cell>");			    	
			    	writer.print("<cell>" + (mandamiento!=null && mandamiento.getFechaFinal() != null ? DateUtils.formatear(mandamiento.getFechaFinal()): "-") + "</cell>");
			    	
			    	writer.print("<cell>" +  (mandamiento!=null && mandamiento.getDescripcion()!= null?mandamiento.getDescripcion():"") + "</cell>");
			    	
			    	writer.print("<cell>" +  ( (mandamiento!=null && mandamiento.getEstatus()!=null)? mandamiento.getEstatus().getValor():"") + "</cell>");
			    				    	
			    	writer.print("<cell>" +  (mandamiento!=null && mandamiento.getInvolucrado() != null
			    			&& mandamiento.getInvolucrado().getCalidadDTO() != null
			    			&& mandamiento.getInvolucrado().getCalidadDTO().getCalidades() != null? mandamiento.getInvolucrado().getCalidadDTO().getCalidades():"") + "</cell>");


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
	
	public ActionForward consultaMandamientoJudicialPJENC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
			try {
				
			Long idMandamientoJudicial = NumberUtils.toLong(request.getParameter("idMandamientoJudicial"),0L);

			if(idMandamientoJudicial != null && idMandamientoJudicial > 0){
				MandamientoDTO loMandamiento= mandamientoJudicialDelegate.consultarMandamientoPorId(idMandamientoJudicial);

				converter.alias("mandamientoJudicial", MandamientoDTO.class);
				converter.alias("involucrado", InvolucradoDTO.class);
				converter.alias("nombresDemograficoDTO", NombreDemograficoDTO.class);
				
				String xml = converter.toXML(loMandamiento);
				response.setContentType("text/xml");
				PrintWriter pw = response.getWriter();
				pw.print(xml);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
