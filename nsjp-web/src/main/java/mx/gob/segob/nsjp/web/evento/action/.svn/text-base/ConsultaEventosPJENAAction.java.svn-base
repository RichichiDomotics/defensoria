
package mx.gob.segob.nsjp.web.evento.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author AlejandroGA
 *
 */
public class ConsultaEventosPJENAAction extends GenericAction{

	/**Log de clase*/
	private static final Logger log  = Logger.getLogger(ConsultaEventosPJENAAction.class);
	
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
	public ActionForward visorSolicitudPJENA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action visor solicitud PJENA");
			
			String tipoExpediente = request.getParameter("tipoExpediente");
			String idEvento = request.getParameter("idEvento");
			log.info("tipo expediente:::"+ tipoExpediente);
			log.info("id evento:::"+ idEvento);
			request.setAttribute("tipoExpediente",tipoExpediente);
			request.setAttribute("idEvento",idEvento);
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return mapping.findForward("succes");
	}		
}
