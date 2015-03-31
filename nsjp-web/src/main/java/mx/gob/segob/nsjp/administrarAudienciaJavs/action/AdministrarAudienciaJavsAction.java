/**
 *  
 */
package mx.gob.segob.nsjp.administrarAudienciaJavs.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.parametro.ParametroDelegate;
import mx.gob.segob.nsjp.dto.ParametroDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.objeto.form.JavsForm;

import org.apache.commons.lang.BooleanUtils;
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
public class AdministrarAudienciaJavsAction extends GenericAction{
	
	/* Log de clase*/
	private static final Logger log  = Logger.getLogger(AdministrarAudienciaJavsAction.class);
	
	@Autowired 
	public AudienciaDelegate audienciaDelegate;
	
	@Autowired
	private ParametroDelegate parametroDelegate;
	
	//agendar consultar eliminar
	public ActionForward consultarAudienciaJavs (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try{
			
			log.info("EJECUTANDO ACTION AGENDAR AUDIENCIA JAVS");
			log.info("******************VERIFICANDO PARAMETROS*********************");
			log.info("audienciaId="+request.getParameter("idAudiencia"));
			
			Long idAudiencia= NumberUtils.toLong(request.getParameter("idAudiencia"), 0L);
			
			if( idAudiencia > 0){
		
				Long estatus = audienciaDelegate.consultarAudiencia(idAudiencia);
				log.info("ESTATUS*********************"+estatus);

				converter.alias("respuesta",Long.class);
				String respuesta = converter.toXML(estatus);
				escribirRespuesta(response, respuesta);
			}
			
			
		}catch(NSJPNegocioException e){
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}
	
		public ActionForward datosSalaJAVS (ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException {
			try{				
				log.info("EJECUTANDO ACTION DATOS JAVS");
				log.info("******************VERIFICANDO PARAMETROS*********************");
				log.info("audienciaId="+request.getParameter("idAudiencia"));
				
				Long idAudiencia= NumberUtils.toLong(request.getParameter("idAudiencia"), 0L);
				
				if( idAudiencia > 0){
			
					String datos = audienciaDelegate.datosSalaJAVS(idAudiencia);
					log.info("DATOS*********************"+datos);

					converter.alias("respuesta",String.class);
					String respuesta = converter.toXML(datos);
					escribirRespuesta(response, respuesta);
				}								
			}catch(NSJPNegocioException e){
				log.error(e);
				e.printStackTrace();
			}			
			return null;
		}

		public ActionForward dirsJAVS (ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException {
			try{				
				log.info("EJECUTANDO ACTION DIRS JAVS");
				log.info("******************VERIFICANDO PARAMETROS*********************");
				log.info("audienciaId="+request.getParameter("idAudiencia"));
				
				Long idAudiencia= NumberUtils.toLong(request.getParameter("idAudiencia"), 0L);
				
				if( idAudiencia > 0){
			
					String datos = audienciaDelegate.obteniendoPathsJAVS(idAudiencia);
					log.info("DATOS*********************"+datos);

					converter.alias("respuesta",String.class);
					String respuesta = converter.toXML(datos);
					escribirRespuesta(response, respuesta);
				}								
			}catch(NSJPNegocioException e){
				log.error(e);
				e.printStackTrace();
			}			
			return null;
		}

		public ActionForward jvlJAVS (ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException {
			try{				
				log.info("EJECUTANDO ACTION DIRS JAVS");
				log.info("******************VERIFICANDO PARAMETROS*********************");
				log.info("audienciaId="+request.getParameter("idAudiencia"));
				
				Long idAudiencia= NumberUtils.toLong(request.getParameter("idAudiencia"), 0L);
				
				if( idAudiencia > 0){
			
					byte[] datos = audienciaDelegate.generandoRegistroMaestroJVL(idAudiencia);
					log.info("DATOS*********************"+datos);

					converter.alias("respuesta",byte[].class);
					String respuesta = converter.toXML(datos);
					escribirRespuesta(response, respuesta);
				}								
			}catch(NSJPNegocioException e){
				log.error(e);
				e.printStackTrace();
			}			
			return null;
		}
		
		public ActionForward conglomeradoJAVS (ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException {
			try{				
				log.info("EJECUTANDO ACTION CONGLOMERADO JAVS");
				log.info("******************VERIFICANDO PARAMETROS*********************");
				log.info("audienciaId="+request.getParameter("idAudiencia"));
				
				Long idAudiencia= NumberUtils.toLong(request.getParameter("idAudiencia"), 0L);
				Long resultado = 0L;
				
				if( idAudiencia > 0){				
					 resultado = audienciaDelegate.conglomeradoJAVS(idAudiencia);
				}								
								
				converter.alias("long",Long.class);
				String xml = converter.toXML(resultado);
				response.setContentType("text/xml");
				PrintWriter pw = response.getWriter();
				pw.print(xml);
				pw.flush();
				pw.close();
				
			}catch(NSJPNegocioException e){
				log.error(e);
				e.printStackTrace();
			}			
			return null;
		}

		public ActionForward eliminarAudienciaJavs (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try{
			
			log.info("EJECUTANDO ACTION AGENDAR AUDIENCIA JAVS");
			log.info("******************VERIFICANDO PARAMETROS*********************");
			log.info("audienciaId="+request.getParameter("idAudiencia"));
			
			Long idAudiencia= NumberUtils.toLong(request.getParameter("idAudiencia"), 0L);
			
			if( idAudiencia > 0){
		
				Long estatus = audienciaDelegate.eliminarAudiencia(idAudiencia);
				log.info("ESTATUS*********************"+estatus);

				converter.alias("respuesta",Long.class);
				String respuesta = converter.toXML(estatus);
				escribirRespuesta(response, respuesta);
			}
			
			
		}catch(NSJPNegocioException e){
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}		
	
	public ActionForward cancelarAudiencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try{
			
			log.info("EJECUTANDO ACTION CANCELAR AUDIENCIA");
			log.info("******************VERIFICANDO PARAMETROS*********************");
			log.info("audienciaId="+request.getParameter("idAudiencia"));
			
			Long idAudiencia= NumberUtils.toLong(request.getParameter("idAudiencia"), 0L);
			Long Resultado= 0L;
			
			if( idAudiencia > 0){
		
				Resultado=audienciaDelegate.cancelarAudiencia(idAudiencia);
				
				String respuesta = converter.toXML(Resultado);
				escribirRespuesta(response, respuesta);
			}
			
			
		}catch(NSJPNegocioException ne){
			log.info(ne.getCause(), ne);
			if(ne.getCodigo().equals(CodigoError.AUDIENCIA_CANCELADA)){
				converter.alias("respuesta",String.class);
				escribirRespuesta(response,converter.toXML("falla_audiencia_cancelada"));
			}
			if(ne.getCodigo().equals(CodigoError.FALLA_CANCELACION_AUDIENCIA)){
				converter.alias("respuesta",String.class);
				escribirRespuesta(response,converter.toXML("falla_cancelacion_audiencia_por_estatus"));
			}
		}
		
		return null;
	}

	
	 /* @author AlejandroGA
	 *  @param request 
	 *  @param response
	 *  @return succes - En caso de que el proceso sea correcto
	 *  @throws IOException En caso de obtener una exception
	 */
	public ActionForward acarreaParametrosApplet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			
			log.info("EJECUTANDO ACTION ACARREAR PARAETROS");
			
			
			log.info("__________________VERIFICANDO PARAMETROS_________________________");
			
			
			JavsForm forma = (JavsForm) form;
			String cadena=forma.getCadena();
			
			String[] elementos = cadena.split("|");
			
			String ancho="";
			String alto="";
			String Audiencia="";
			String Usuario="";
			String Password="";
			String DirIP="";
			String RutaDirs="";
			String ArchivoJVL="";
			
			if(elementos.length>0){
				ancho = elementos[0];
				alto = elementos[1];
				Audiencia = elementos[2];
				Usuario = elementos[3];
				Password = elementos[4];
				DirIP = elementos[5];
				RutaDirs = elementos[6];
				ArchivoJVL = elementos[7];
			}
			
			// Subimos la informacion al attribute
			request.setAttribute("ancho", ancho);
			request.setAttribute("alto", alto);
			request.setAttribute("Audiencia", Audiencia);
			request.setAttribute("Usuario", Usuario);
			request.setAttribute("Password", Password);
			request.setAttribute("DirIP", DirIP);
			request.setAttribute("RutaDirs", RutaDirs);
			request.setAttribute("ArchivoJVL", ArchivoJVL);
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return mapping.findForward("succes");
	}
	
	
	public ActionForward tieneAccesoAlDetalleAudiencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			log.info("ejecutando Action ::: tieneAccesoAlDetalleAudiencia");
			try {
				Long audienciaId = NumberUtils.toLong(request.getParameter("audienciaId"),0);
				Boolean autorizarAcceso = Boolean.TRUE;/* Enable IT :Se elimina el umbral de audiencia para que pueda acceder en cualquier momento a la audiencia*/
				ParametroDTO loParametroUmbral = null;
				
				String loParametro = parametroDelegate.consultarParametro(Parametros.APLICAR_REGLA_UMBRAL_AUDIENCIA).getValor();				
				Boolean loParametroReglaUmbral = BooleanUtils.isTrue( loParametro.equals("1")? true: false);
				String respuestaAutorizarAcceso = new String();

				
				//Aplica la regla
				if(loParametroReglaUmbral == true){
					
					AudienciaDTO loAudienciaDTO = audienciaDelegate.obtenerAudiencia(new AudienciaDTO(audienciaId));					
					Date fechaEvento = loAudienciaDTO.getFechaEvento();
					Date fechaActual = new Date();				
					
					loParametroUmbral = parametroDelegate.consultarParametro(Parametros.UMBRAL_ATENDER_AUDIENCIAS);
					log.info("Minutos de umbral: " + Integer.parseInt(loParametroUmbral.getValor()));
					
					//Se obtiene la fecha y hora del servidor menos la hora definida del umbral
					Date fechaEventoLimiteParaAbrirAudiencia = DateUtils.sumarMinutos(fechaEvento,-(Integer.parseInt(loParametroUmbral.getValor())));

					//Se autoriza el acceso
					if(fechaEventoLimiteParaAbrirAudiencia.before(fechaActual)){ 
						autorizarAcceso = Boolean.TRUE;
					}
				}else{
					//No debe de aplicar la regla por lo tanto siempre autoriza
					autorizarAcceso = Boolean.TRUE;
				}
				
				
				if(autorizarAcceso){
					respuestaAutorizarAcceso = "autorizado";
				}else{
					respuestaAutorizarAcceso = "Solo se puede ingresar al expediente con " + loParametroUmbral.getValor()  + " minutos antes del inicio de la audiencia";
				}
				
				converter.alias("respuestaAutorizarAcceso", String.class);
				String xml = converter.toXML(respuestaAutorizarAcceso);
				//mandamos la respuesta al cliente
				escribir(response, xml,null);	
		} catch (Exception e) {	
			log.info(e.getCause(),e);
			converter.alias("respuestaAutorizarAcceso", String.class);
			String xml = converter.toXML("error");
			escribir(response, xml,null);
		}
		return null;
	}
	
}
