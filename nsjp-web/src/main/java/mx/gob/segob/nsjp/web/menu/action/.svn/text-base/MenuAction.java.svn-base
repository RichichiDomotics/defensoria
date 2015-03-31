/**
 * Nombre del Programa 	: MenuAction.java
 * Autor               	: Antonio Brindiz
 * Compania            	: Ultrasist
 * Proyecto            	: NSJP              			Fecha:25/05/2012
 * Marca de cambio     	: N/A
 * Descripcion General   : Clase para la generación de menús
 * Programa Dependiente  : N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion    : N/A
 * Dias de ejecucion     : N/A                             Horario: N/A
 *                               MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                 :N/A
 * Compania              :N/A
 * Proyecto              :N/A                                   Fecha: N/A
 * Modificacion          :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.menu.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.expediente.TipoTurno;
import mx.gob.segob.nsjp.comun.enums.menu.TipoMenu;
import mx.gob.segob.nsjp.delegate.elementomenu.ElementoMenuDelegate;
import mx.gob.segob.nsjp.delegate.expediente.TurnoDelegate;
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.expediente.TurnoDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase action menu
 * 
 * @version 1.0
 * @author AntonioBV
 */
public class MenuAction extends GenericAction {
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MenuAction.class);

	@Autowired
	private ElementoMenuDelegate elementoMenuDelegate;

	@Autowired
	private TurnoDelegate turnoDelegate;

	/***
	 * Método para discriminar el menu por su tipo de menu
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */

	@SuppressWarnings("rawtypes")
	public ActionForward obtenerMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		ActionRedirect actionRedirect = null;
		LOGGER.info("ejecutando Action Login en metodo obtenerSubMenus");
		try {
			Enumeration en = request.getParameterNames();
			String cForward = request.getParameter("cForward");
			if (cForward != null) {
				actionRedirect = new ActionRedirect(
						mapping.findForward(cForward));
				if (actionRedirect != null) {
					while (en.hasMoreElements()) {
						String nombreParametro = (String) en.nextElement();
						String valorParametro = request
								.getParameter(nombreParametro);
						actionRedirect.addParameter(nombreParametro,
								valorParametro);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.info("TurnoSiguienteError");
			LOGGER.info(e.getCause(), e);
		}
		return actionRedirect;
	}

	/***
	 * Método para discriminar el menu por su tipo de menu
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */

	public ActionForward obtenerConfMenu(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String rolIdPadre = (String) request.getParameter("idRolPadre");
		String rolId = (String) request.getParameter("idRol");
		String posicion = (String) request.getParameter("posicion");
		String resp = "";

		RolDTO rolPadreDTO = new RolDTO(Long.valueOf(rolIdPadre));
		RolDTO rolDTO = new RolDTO(Long.valueOf(rolId));
		LOGGER.info("ejecutando Action Login en metodo obtenerConfSubMenus");
		try {
			List<ElementoMenuDTO> eMPadre=null;
			List<ElementoMenuDTO> eM=null;
			if (posicion.equals("0")){
				eMPadre = elementoMenuDelegate.consultarElementosMenuXRol(rolPadreDTO, TipoMenu.IZQUIERDO);
				eM = elementoMenuDelegate.consultarElementosMenuXRol(rolDTO, TipoMenu.IZQUIERDO);
			}else if (posicion.equals("1")){
				eMPadre = elementoMenuDelegate.consultarElementosMenuXRol(rolPadreDTO, TipoMenu.ARRIBA);
				eM = elementoMenuDelegate.consultarElementosMenuXRol(rolDTO, TipoMenu.ARRIBA);
			}
			eMPadre = obtenerConfiguracion(eMPadre, eM);
			resp = generaArbolMenuHTML(eMPadre);
			response.setContentType("text/javascript; charset=ISO-8859-1");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print(resp);
			writer.flush();
			writer.close();

		} catch (Exception e) {

			LOGGER.info(e.getCause(), e);
		}
		return null;
	}

	private List<ElementoMenuDTO> obtenerConfiguracion(
			List<ElementoMenuDTO> lstEMPadre, List<ElementoMenuDTO> lstEM) {
		List<ElementoMenuDTO> resp = null;
		int j = 0;
		// Revisa que las listas no sean nulas
		if (lstEMPadre != null && lstEM != null) {
			// Recorro la lista del padre, que es mayor o igual a la del hijo
			for (ElementoMenuDTO eMPadre : lstEMPadre) {
				j = 0;
				// Mientas existan valores en el padre y el hijo no tenga el
				// elemento del padre y el elemento no sea obligatorio
				while (j < lstEM.size()	&& !eMPadre.isEsObligatorio() && eMPadre.getElementoMenuId() != lstEM.get(j)
								.getElementoMenuId()) {
					j++;
				}
				if (j < lstEM.size()) {
					eMPadre.setEsActivo(true);
					// Se ve si tiene hijos
					if (lstEM.get(j).getElementoMenuHijosDTO() != null) {
						eMPadre.setElementoMenuHijosDTO(obtenerConfiguracion(
								eMPadre.getElementoMenuHijosDTO(), lstEM.get(j)
										.getElementoMenuHijosDTO()));
					}
				} else {
					eMPadre.setEsActivo(false);
				}
			}
		}
		resp = lstEMPadre;
		return resp;

	}

	private boolean esParcial(List<ElementoMenuDTO> lstEM) {
		boolean resp = false;
		int i = 0;
		if (lstEM != null) {
			while (i < lstEM.size() && resp == false) {
				if (lstEM.get(i).isEsActivo() == false) {
					resp = true;
				}
				if (lstEM.get(i).getElementoMenuHijosDTO() != null) {
					resp = esParcial(lstEM.get(i).getElementoMenuHijosDTO());
				}
				i++;
			}
		}
		return resp;
	}

	private String generaArbolMenuHTML(List<ElementoMenuDTO> lstEM) {
		String resp = "";
		if (lstEM != null && !lstEM.isEmpty()) {
			resp += "<ul>";
			for (ElementoMenuDTO eM : lstEM) {
				// Es padre
				if (eM.getElementoMenuHijosDTO() != null) {
					//Esta checado
					if (eM.isEsActivo() == true) {
							if (esParcial(eM.getElementoMenuHijosDTO()) == true) {
								resp += " <li id='"
									+ eM.getElementoMenuId()
									+ "' class='jstree-closed jstree-undetermined'> <a href='javascript:void(0);'> <ins class='jstree-checkbox'> </ins> "
									+ eM.getcNombre() + " </a> ";
							}else{
								resp += " <li id='"
									+ eM.getElementoMenuId()
									+ "' class='jstree-closed jstree-checked'> <a href='javascript:void(0);'> <ins class='jstree-checkbox'> </ins> "
									+ eM.getcNombre() + " </a> ";
								
							}
							// No está checado
					} else {
						resp += " <li id='"
								+ eM.getElementoMenuId()
								+ "' class='jstree-closed jstree-unchecked'> <a href='javascript:void(0);'> <ins class='jstree-checkbox'> </ins> "
								+ eM.getcNombre() + " </a> ";
					}

					// Es Hoja
				} else {
					if (eM.isEsActivo() == true) {
						resp += " <li id='"
								+ eM.getElementoMenuId()
								+ "' class='jstree-leaf jstree-checked'> <a href='javascript:void(0);'> <ins class='jstree-checkbox'> </ins> "
								+ eM.getcNombre() + " </a> ";
					}else {
						resp += " <li id='"
								+ eM.getElementoMenuId()
								+ "' class='jstree-leaf jstree-unchecked'> <a href='javascript:void(0);'> <ins class='jstree-checkbox'> </ins> "
								+ eM.getcNombre() + " </a> ";
					}

				}

				// **jstree-checked jstree-open
				// jstree-checked jstree-closed
				// jstree-unchecked jstree-open
				// jstree-unchecked jstree-closed
				// jstree-undetermined jstree-closed
				// jstree-undetermined jstree-closed
				// jstree-leaf jstree-unchecked

				if (eM.getElementoMenuHijosDTO() != null
						&& !eM.getElementoMenuHijosDTO().isEmpty()) {
					resp += generaArbolMenuHTML(eM.getElementoMenuHijosDTO());
				}
				resp += "</li>";
			}
			resp += "</ul>";
		}
		return resp;
	}

	/***
	 * Método para obtener los menus hijos
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */

	public ActionForward obtenerSubMenus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.info("ejecutando Action Login en metodo obtenerSubMenus");
		try {

			String root = request.getParameter("root");
			StringBuffer html = new StringBuffer();

			Long idElementoMenuPadre = Long.parseLong(root);
			ElementoMenuDTO elementoMenuDTO = new ElementoMenuDTO();
			elementoMenuDTO.setElementoMenuId(idElementoMenuPadre);
			UsuarioDTO usuario = this.getUsuarioFirmado(request);
			RolDTO rolDTO = usuario.getRolACtivo().getRol();
			elementoMenuDTO = elementoMenuDelegate.consultarElementoMenu(
					elementoMenuDTO, rolDTO);

			if (elementoMenuDTO.getElementoMenuHijosDTO() != null) {

				for (ElementoMenuDTO temp : elementoMenuDTO
						.getElementoMenuHijosDTO()) {
					html.append("<li ");
					html.append(" id=\"" + temp.getElementoMenuId() + "\" ");
					html.append(" class=\"" + temp.getcClassHTML() + "\" ");
						html.append(" rel=\"" + temp.getcForward() + "\" ");
					html.append(" >");
					html.append("<a href=\"javascript:void(0);\" ");
					html.append(" id=\"" + temp.getcIdHTML() + "\" ");
					html.append(" onclick=\"" + temp.getcComando() + "\" ");
					html.append(" cForward=\"" + temp.getcForward() + "\" ");
					html.append(">");
					html.append(temp.getcNombre() != null ? temp.getcNombre()
							: "&nbsp;");
					html.append("</a>");
					html.append("</li>");
				}

			}

			response.setContentType("text/javascript; charset=ISO-8859-1");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print(html.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LOGGER.info("TurnoSiguienteError");
			LOGGER.info(e.getCause(), e);
		}
		return null;
	}

	/***
	 * Método para obtener los turnos como si fueran menú
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */

	public ActionForward obtenerTurnos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.info("ejecutando Action Login en metodo obtenerSubMenus");
		try {

			// String root = request.getParameter("root");
			String tipoMenu = request.getParameter("tipoMenu");
			// String cForward = request.getParameter("cForward");
			StringBuffer html = new StringBuffer();
			TipoTurno tipoTurno = null;
			List<TurnoDTO> lstTurnoDTO = null;
			UsuarioDTO usuarioDTO = getUsuarioFirmado(request);

			TurnoDTO turnoDTO = new TurnoDTO();
			turnoDTO.setDiscriminante(usuarioDTO.getFuncionario()
					.getDiscriminante());

			if (tipoMenu != null) {
				if (tipoMenu.equals("penalUrgente")) {
					tipoMenu = "penal";
					turnoDTO.setEsUrgente(Boolean.TRUE);
				}
				tipoTurno = TipoTurno.valueOf(tipoMenu.toUpperCase());
			}

			turnoDTO.setTipoTurno(tipoTurno);

			lstTurnoDTO = turnoDelegate.consultarTurnosAtendidos(turnoDTO);

			for (TurnoDTO temp : lstTurnoDTO) {
				html.append("<li ");
				html.append(" id=\"" + temp.getTurnoId() + "\" ");
				html.append(" class=\"jstree-leaf\" ");
					html.append(" rel=\"turnos_leaf\" ");
				html.append(" >");
				html.append("<a href=\"javascript:void(0);\" ");
				html.append(" id=\"" + temp.getNumeroTurno() + "\" ");
				html.append(" onclick=\"ejecutarFuncionTurno("
						+ temp.getTurnoId() + "," + temp.getNumeroTurno()
						+ ");\" ");
				html.append(" cForward=\"turnos\" ");
				html.append(">");
				html.append(temp.getNumeroTurno() != null ? temp
						.getNumeroTurno() : "&nbsp;");
				html.append("</a>");
				html.append("</li>");
			}

			response.setContentType("text/javascript; charset=ISO-8859-1");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print(html.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LOGGER.info("TurnoSiguienteError");
			LOGGER.info(e.getCause(), e);
		}
		return null;
	}

}
