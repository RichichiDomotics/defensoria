/**
* Nombre del Programa 			: ConfiguracionAction.java
* Autor                         : AlejandroGA
* Compania                    	: Ultrasist
* Proyecto                      : NSJP                    Fecha: 22 May 2011
* Marca de cambio        		: N/A
* Descripcion General    		: Clase encargada de consultar los aspectos de configuracion
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
package mx.gob.segob.nsjp.web.configuracion.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.delegate.configuracion.ConfiguracionDelegate;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase encargada de consultar los aspectos de configuracion.
 * @version 1.0
 * @author AlejandroGA
 *
 */
public class ConfiguracionAction extends GenericAction{
	
	/** Log de clase */
	private static final Logger log = Logger
			.getLogger(ConfiguracionAction.class);
			
	@Autowired
	private ConfiguracionDelegate configuracionDelegate;
	
	public ActionForward consultarInstalacionActual(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("EJECUTANDO ACTION CONSULTAR INSTALACION ACTUAL");
			ConfInstitucionDTO institucionActual = configuracionDelegate.consultarInstitucionActual();
			
			converter.alias("institucionActual", ConfInstitucionDTO.class);
			String xml = converter.toXML(institucionActual);
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
