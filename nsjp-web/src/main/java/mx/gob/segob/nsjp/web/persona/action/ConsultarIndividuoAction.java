/**
* Nombre del Programa 	: ConsultarIndividuoAction.java                                    
* Autor               	: Alejandro Galaviz, Cuauhtemoc Paredes, Jose Luis Perez, Jorge Fernández                                              
* Compania            	: Ultrasist                                                
* Proyecto            	: NSJP              			Fecha:03/03/2011 
* Marca de cambio     	: N/A                                                     
* Descripcion General   : Acciones para el CU consultar individuo
* Programa Dependiente  : N/A                                                      
* Programa Subsecuente 	: N/A                                                      
* Cond. de ejecucion    : N/A                                                   
* Dias de ejecucion     : N/A                           Horario: N/A
*                               MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                 :Jorge Ignacio Fernandez Ortiz                                                           
* Compania              :Ultrasist                                                           
* Proyecto              :NSJP                           Fecha: 08/03/2011       
* Modificacion          :Se modifica el for que llena el grid para que incluya todos los 
* 						 nombres de todos los involucrados.                                                          
*------------------------------------------------------------------------------      
*/
package mx.gob.segob.nsjp.web.persona.action;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.organizacion.TipoOrganizacion;
import mx.gob.segob.nsjp.delegate.catalogo.AdministrarCatalogoDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.MediaFiliacionDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.TelefonoDTO;
import mx.gob.segob.nsjp.dto.relacion.RelacionDetencionDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase que implementa las acciones para el CU consultar individuo
 * @version 1.0
 * @author Alejandro Galavíz
 */
public class ConsultarIndividuoAction extends GenericAction{
	/**Log de clase*/
	private static final Logger log  = Logger.getLogger(
			ConsultarIndividuoAction.class);
	@Autowired
	public InvolucradoDelegate involucradoDelegate;
	
	@Autowired
	public AdministrarCatalogoDelegate administrarCatalogoDelegate;
	
	
	/**
	 * Metodo utilizado para realizar la carga del combo Calidad 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward cargarCalidadIndividuo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {
//		
//		try {
//			log.info("ejecutando Action Cargar Combo");			
//			ArrayList<CalidadDTO> lstCalidadDTO = null;
//			lstCalidadDTO = calidadBDelegate.obtenerCalidadCmb();
//			log.info("lstCalidad::::IngresarIndividuoAction"+lstCalidadDTO);
//			log.info("lista"+lstCalidadDTO.get(0).getGcDescripcion());
//			converter.alias("listaCalidades", java.util.List.class);
//			converter.alias("calidades", CalidadDTO.class);				
//			String xml = converter.toXML(lstCalidadDTO);				
//			response.setContentType("text/xml");				
//			PrintWriter pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();
//		} catch (Exception e) {		
//			log.info(e);
//		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para realizar la carga de la tabla que se presenta
	 * en la JSP consultar individuo en calidad de traductor-interprete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarCalidadIndividuo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {
//		
//		try {
//			log.info("ejecutando Action Consultar");
//			
//			ArrayList<InvolucradoDTO> lstInvolucradoDTO = 
//				new  ArrayList<InvolucradoDTO>();
//			/**FORMA EN LA QUE SE RECIBIRA EL NO. DE EXPEDIENTE Y 
//			 * EL ID DE LA CALIDAD
//			 * 
//			 */
//			ConsultarIndividuoForm forma=(ConsultarIndividuoForm) form;
//			log.info("NO DE EXPEDIENTE="+forma.getNoExpediente());
//			log.info("CALIDAD DEL INDIVIDUO="+forma.getCalidadIndividuo());
//			
//			lstInvolucradoDTO = involucradoBDelegate.consultarIndividuos(null, null, null);		
//			response.setContentType("text/xml; charset=UTF-8");
//			response.setHeader("Cache-Control", "no-cache");
//			PrintWriter writer = response.getWriter();
//			List<NombreDemograficoDTO> lstNombreDemograficoDTO = null;
//			
//			writer.print("<rows>");
//			int lTotalRegistros=0;
//			for (InvolucradoDTO involucradoDTO : lstInvolucradoDTO) {
//				lTotalRegistros=+involucradoDTO.getListaNombreDemograficoDTO().size();
//			}
//			writer.print("<records>" + lTotalRegistros + "</records>");
//			for (InvolucradoDTO involucradoDTO : lstInvolucradoDTO) {
//				lstNombreDemograficoDTO = involucradoDTO.getListaNombreDemograficoDTO();
//				for (NombreDemograficoDTO nombreDemograficoDTO : lstNombreDemograficoDTO) {
//					writer.print("<row id='" + nombreDemograficoDTO.getGlNombreDemograficoId() + "'>");
//					writer.print("<cell><![CDATA[<a href='#' onclick='consultaDetalleIndividuo(" + involucradoDTO.getGlInvolucradoId() +","+nombreDemograficoDTO.getGlNombreDemograficoId()+ "  )' title='Consulta a detalle individuo'>" + nombreDemograficoDTO.getGcNombre() + "</a>]]></cell>");
//					writer.print("<cell>" + nombreDemograficoDTO.getGcApellidoPaterno() + "</cell>");
//					writer.print("<cell>" + nombreDemograficoDTO.getGcApellidoMaterno() + "</cell>");
//					writer.print("</row>");
//				}
//			}
//			
//			
//			
//			writer.print("</rows>");
//			writer.flush();
//			writer.close();
//			
//			
//		} catch (Exception e) {		
//			log.info(e);
//			log.info("#####"+e.getCause());
//		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para consultar los datos del involucrado
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarDetalleInvolucrado(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {
//		try {
//			
//			log.info("ejecutando ConsultarIndividuoAction.consultarDetalleInvolucrado");
//			ConsultarIndividuoForm forma=(ConsultarIndividuoForm) form;
//			InvolucradoDTO involucrado=new InvolucradoDTO();
//			involucrado.setGlInvolucradoId(Long.parseLong(forma.getIdIndividuo()));
//			InvolucradoDTO invol=involucradoBDelegate.consultarDetalleInvolucrado(involucrado);
//			Date lFechaActual=involucradoBDelegate.obtenerFechaActual();
//			Long idNombreDemografico=Long.parseLong(forma.getIdNombreDemografico());
//			ArrayList<NombreDemograficoDTO> listaNombreDemograficoDTOs=invol.getListaNombreDemograficoDTO();
//			NombreDemograficoDTO seleccionNombreDemograficoDTO=null;
//			for (NombreDemograficoDTO nombreDemograficoDTO : listaNombreDemograficoDTOs) {
//				if(nombreDemograficoDTO.getGlNombreDemograficoId()==idNombreDemografico){
//					seleccionNombreDemograficoDTO=nombreDemograficoDTO;
//				}
//			}
//			//Queda pendiente el caso de uso.
//			
//		}catch (Exception e) {
//			log.error(e);
//			log.info(e.getMessage());
//		}
//		
//		
		return null;
	}
	
	
	/**
	 * Metodo utilizado para consultar los datos del involucrado
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarInvolucrado(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {
		try{
			String idInvolucrado=request.getParameter("idInvolucrado");
			if( idInvolucrado.equals("undefined") ){
				idInvolucrado	= request.getSession().getAttribute("idImputadoDef").toString();
			}
			log.info("%%%%%%%%%%%%Este es el id del involucrado a consultar: "+idInvolucrado);
			InvolucradoDTO involucradoDTO=new InvolucradoDTO();
			involucradoDTO.setElementoId(Long.parseLong(idInvolucrado));
			involucradoDTO=involucradoDelegate.obtenerInvolucrado(involucradoDTO);
			if(involucradoDTO.getDomicilio()!=null){
				if(involucradoDTO.getDomicilio().getLatitud()!=null && !involucradoDTO.getDomicilio().getLatitud().equals("")){
					String latitud=involucradoDTO.getDomicilio().getLatitud();
					log.info("LATITUD_COMPLETA_1:: "+involucradoDTO.getDomicilio().getLatitud());
					
					String subLatitud=latitud.substring(1,latitud.length());//quitamos la letra de la cadena
					String[] arr=subLatitud.split("°");//separlo los grados de los minutos y segundos
					String[] arrDos=arr[1].split("'");//separamos los minutos y segundos
					String segundos=arrDos[1].substring(0,arrDos[1].length()-1);
					//seteamos los valores
					involucradoDTO.getDomicilio().setLatitudN(latitud.substring(0,1));log.info("domicilio hechoDTO NO NULL!!! 1");
					involucradoDTO.getDomicilio().setLatitudGrados(arr[0]);log.info("domicilio hechoDTO NO NULL!!! 2");
					involucradoDTO.getDomicilio().setLatitudMinutos(arrDos[0]);log.info("domicilio hechoDTO NO NULL!!! 3");
					involucradoDTO.getDomicilio().setLatitudSegundos(segundos);log.info("domicilio hechoDTO NO NULL!!! 4");
				}
				if(involucradoDTO.getDomicilio().getLongitud()!=null && !involucradoDTO.getDomicilio().getLongitud().equals("")){
					String longitud=involucradoDTO.getDomicilio().getLongitud();
					log.info("LONGITUD_COMPLETA_1:: "+involucradoDTO.getDomicilio().getLongitud());
					String subLongitud=longitud.substring(1,longitud.length());//quitamos la letra de la cadena
					String[] arr=subLongitud.split("°");//separlo los grados de los minutos y segundos
					String[] arrDos=arr[1].split("'");//separamos los minutos y segundos
					String segundos=arrDos[1].substring(0,arrDos[1].length()-1);
					
					involucradoDTO.getDomicilio().setLongitudE(longitud.substring(0,1));log.info("domicilio hechoDTO NO NULL!!! 5");
					involucradoDTO.getDomicilio().setLongitudGrados(arr[0]);log.info("domicilio hechoDTO NO NULL!!! 6");
					involucradoDTO.getDomicilio().setLongitudMinutos(arrDos[0]);log.info("domicilio hechoDTO NO NULL!!! 7");
					involucradoDTO.getDomicilio().setLongitudSegundos(segundos);log.info("domicilio hechoDTO NO NULL!!! 8");
				}
			}
			
			if(involucradoDTO.getDomicilioNotificacion()!=null){
				if(involucradoDTO.getDomicilioNotificacion().getLatitud()!=null && !involucradoDTO.getDomicilioNotificacion().getLatitud().equals("")){
					String latitud=involucradoDTO.getDomicilioNotificacion().getLatitud();
					
					String subLatitud=latitud.substring(1,latitud.length());//quitamos la letra de la cadena
					String[] arr=subLatitud.split("°");//separlo los grados de los minutos y segundos
					String[] arrDos=arr[1].split("'");//separamos los minutos y segundos
					String segundos=arrDos[1].substring(0,arrDos[1].length()-1);
					//seteamos los valores
					involucradoDTO.getDomicilioNotificacion().setLatitudN(latitud.substring(0,1));log.info("domicilio hechoDTO NO NULL!!! 1");
					involucradoDTO.getDomicilioNotificacion().setLatitudGrados(arr[0]);log.info("domicilio hechoDTO NO NULL!!! 2");
					involucradoDTO.getDomicilioNotificacion().setLatitudMinutos(arrDos[0]);log.info("domicilio hechoDTO NO NULL!!! 3");
					involucradoDTO.getDomicilioNotificacion().setLatitudSegundos(segundos);log.info("domicilio hechoDTO NO NULL!!! 4");
				}
				if(involucradoDTO.getDomicilioNotificacion().getLongitud()!=null && !involucradoDTO.getDomicilioNotificacion().getLongitud().equals("")){
					String longitud=involucradoDTO.getDomicilioNotificacion().getLongitud();
					
					String subLongitud=longitud.substring(1,longitud.length());//quitamos la letra de la cadena
					String[] arr=subLongitud.split("°");//separlo los grados de los minutos y segundos
					String[] arrDos=arr[1].split("'");//separamos los minutos y segundos
					String segundos=arrDos[1].substring(0,arrDos[1].length()-1);
					
					involucradoDTO.getDomicilioNotificacion().setLongitudE(longitud.substring(0,1));log.info("domicilio hechoDTO NO NULL!!! 5");
					involucradoDTO.getDomicilioNotificacion().setLongitudGrados(arr[0]);log.info("domicilio hechoDTO NO NULL!!! 6");
					involucradoDTO.getDomicilioNotificacion().setLongitudMinutos(arrDos[0]);log.info("domicilio hechoDTO NO NULL!!! 7");
					involucradoDTO.getDomicilioNotificacion().setLongitudSegundos(segundos);log.info("domicilio hechoDTO NO NULL!!! 8");
				}
			}
			//Condicion para revisar si existe una organizacion en el involicruda y si tiene domocilio separarlo
			//ACT- 20120521
			if(involucradoDTO.getOrganizacionDTO()!=null && involucradoDTO.getOrganizacionDTO().getDomicilioDTO()!=null)
			{
				if(involucradoDTO.getOrganizacionDTO().getDomicilioDTO().getLatitud()!=null && !involucradoDTO.getOrganizacionDTO().getDomicilioDTO().getLatitud().equals("")){
					String latitud=involucradoDTO.getOrganizacionDTO().getDomicilioDTO().getLatitud();
					
					String subLatitud=latitud.substring(1,latitud.length());//quitamos la letra de la cadena
					log.info("LATITUD_ORG:: "+latitud);
					String[] arr=subLatitud.split("°");//separlo los grados de los minutos y segundos
					String[] arrDos=arr[1].split("'");//separamos los minutos y segundos
					String segundos=arrDos[1].substring(0,arrDos[1].length()-1);
					//seteamos los valores
					involucradoDTO.getOrganizacionDTO().getDomicilioDTO().setLatitudN(latitud.substring(0,1));log.info("domicilio hechoDTO NO NULL!!! 1");
					involucradoDTO.getOrganizacionDTO().getDomicilioDTO().setLatitudGrados(arr[0]);log.info("domicilio hechoDTO NO NULL!!! 2");
					involucradoDTO.getOrganizacionDTO().getDomicilioDTO().setLatitudMinutos(arrDos[0]);log.info("domicilio hechoDTO NO NULL!!! 3");
					involucradoDTO.getOrganizacionDTO().getDomicilioDTO().setLatitudSegundos(segundos);log.info("domicilio hechoDTO NO NULL!!! 4");
				}
				if(involucradoDTO.getOrganizacionDTO().getDomicilioDTO().getLongitud()!=null && !involucradoDTO.getOrganizacionDTO().getDomicilioDTO().getLongitud().equals("")){
					String longitud=involucradoDTO.getOrganizacionDTO().getDomicilioDTO().getLongitud();
					log.info("LONGITUD_ORG:: "+longitud);
					String subLongitud=longitud.substring(1,longitud.length());//quitamos la letra de la cadena
					String[] arr=subLongitud.split("°");//separlo los grados de los minutos y segundos
					String[] arrDos=arr[1].split("'");//separamos los minutos y segundos
					String segundos=arrDos[1].substring(0,arrDos[1].length()-1);
					
					involucradoDTO.getOrganizacionDTO().getDomicilioDTO().setLongitudE(longitud.substring(0,1));log.info("domicilio hechoDTO NO NULL!!! 5");
					involucradoDTO.getOrganizacionDTO().getDomicilioDTO().setLongitudGrados(arr[0]);log.info("domicilio hechoDTO NO NULL!!! 6");
					involucradoDTO.getOrganizacionDTO().getDomicilioDTO().setLongitudMinutos(arrDos[0]);log.info("domicilio hechoDTO NO NULL!!! 7");
					involucradoDTO.getOrganizacionDTO().getDomicilioDTO().setLongitudSegundos(segundos);log.info("domicilio hechoDTO NO NULL!!! 8");
				}
				log.info("ID Tipo de organizacion::::: "+involucradoDTO.getOrganizacionDTO().getValorByTipoOrganizacionVal().getIdCampo());
				//Recuperar los datos de la organizacion de tipo publico
				if (involucradoDTO.getOrganizacionDTO() != null
						&& involucradoDTO.getOrganizacionDTO().getElementoId() != null
						&& involucradoDTO.getOrganizacionDTO().getValorByTipoOrganizacionVal() != null
						&& involucradoDTO.getOrganizacionDTO().getValorByTipoOrganizacionVal()
								.getIdCampo().equals(TipoOrganizacion.SECTOR_PUBLICO.getValorId())
						&& involucradoDTO.getOrganizacionDTO().getValorBySectorGubernamentalVal()!= null
						&& involucradoDTO.getOrganizacionDTO().getValorBySectorGubernamentalVal().
								getIdCampo()!=null
								)
				{
					//ORGANIZACION_SECTOR_PUBLICO (233) Con valor del Sector Gubernamental
					CatalogoDTO catalogo = new CatalogoDTO();
					catalogo.setClave(involucradoDTO.getOrganizacionDTO().getValorBySectorGubernamentalVal().getIdCampo()); //input.setClave(1460L);
  	                catalogo.setIdCatalogo(new Long(Catalogos.ORGANIZACION_SECTOR_PUBLICO.ordinal()));
  	                
					catalogo = administrarCatalogoDelegate.obtenerValor(catalogo);
					//Extraer los datos de la clave asociada, la cual se encuentra en ValoresExtra, 
					//para obtener el valorId de TIPO_DEPENDENCIA a partir del ORGANIZACION_SECTOR_PUBLICO:
					Long valorTipoDependenciaId = null;
					if(catalogo!= null && catalogo.getValoresExras()!=null && !catalogo.getValoresExras().isEmpty()){
		                   for(ValorDTO valor : catalogo.getValoresExras()){
		                          if( valor.getValor()!=null && !valor.getValor().isEmpty()){
		                                  valorTipoDependenciaId = Long.parseLong(valor.getValor());
		                                  involucradoDTO.getOrganizacionDTO().setValorByTipoDependencia(new ValorDTO(valorTipoDependenciaId));
		                         }
		                   }
		            }

					//TIPO_DEPENDENCIA
		            Long valorNivelDependenciaId = null;
		            if(valorTipoDependenciaId!=null){
			            catalogo.setIdCatalogo(new Long(Catalogos.TIPO_DEPENDENCIA.ordinal()));
			            catalogo.setClave(valorTipoDependenciaId);
			            
			             CatalogoDTO catalogoNivelDependencia = administrarCatalogoDelegate.obtenerValor(catalogo);
			                
			                if(catalogoNivelDependencia!= null && catalogoNivelDependencia.getValoresExras()!=null && !catalogoNivelDependencia.getValoresExras().isEmpty()){
			                   for(ValorDTO valor : catalogoNivelDependencia.getValoresExras()){
			                           if( valor.getValor()!=null && !valor.getValor().isEmpty()){
			                                  valorNivelDependenciaId = Long.parseLong(valor.getValor());
			                                  involucradoDTO.getOrganizacionDTO().setValorByNivelDependencia(new ValorDTO(valorNivelDependenciaId));
			                          }
			                   }
			                }   
		            }
		            //DATOS DEL NIVEL_DEPENDENCIA
//		            if(valorNivelDependenciaId!=null){
//			            catalogo.setIdCatalogo(new Long(Catalogos.NIVEL_DEPENDENCIA.ordinal()));
//			            catalogo.setClave(valorNivelDependenciaId);
//			            CatalogoDTO catalogoNivelDependencia = administrarCatalogoDelegate.obtenerValor(catalogo);
//		            }            
				}
			}
				
				
			if (log.isDebugEnabled()) {
				log.debug("::::::::::::::INVOLUCRADO:::::::::Consulta:::::::::::"+ involucradoDTO);
			}
			//Esta linea se aplica ya ke como carga el DTO de datos demograficos al consultar el xml duplica los nombres
			//involucradoDTO.setDetenciones(null);
			String xml = null;
			PrintWriter pw = null;
			converter.alias("involucradoDTO",InvolucradoDTO.class);
			converter.alias("AsentamientoDTO",AsentamientoDTO.class);
			converter.alias("mediaFiliacionDTO",MediaFiliacionDTO.class);
			converter.alias("MunicipioDTO",MunicipioDTO.class);
			converter.alias("domicilioDTO",DomicilioDTO.class);
			converter.alias("EntidadFederativaDTO",EntidadFederativaDTO.class);
			converter.alias("aliasInvolucradoDTO",AliasInvolucradoDTO.class);
			converter.alias("DetencionDTO",DetencionDTO.class);
			//Agregados para la consulta de medios de contacto
			converter.alias("TelefonoDTO",TelefonoDTO.class);
			converter.alias("CorreoElectronicoDTO",CorreoElectronicoDTO.class);
			converter.alias("relacionDetencionDTO",RelacionDetencionDTO.class);
			
			
			xml = converter.toXML(involucradoDTO);
			log.info("consulta_involucrado:: "+xml);
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			
		}catch (Exception e) {
			log.error("ERROR - consultarInvolucrado");
			log.error(e.getCause(),e);
		}
		return null;
	}
		
}
