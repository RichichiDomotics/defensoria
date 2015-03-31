/**
 * 
 */
package mx.gob.segob.nsjp.web.rol.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.actuaciones.ActuacionesDelegate;
import mx.gob.segob.nsjp.delegate.modulo.ModuloDelegate;
import mx.gob.segob.nsjp.delegate.rol.RolDelegate;
import mx.gob.segob.nsjp.dto.ActuacionDTO;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.usuario.ModuloDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

/**
 * @author LuisMG
 * 
 */
public class GuardarModulosRolAction extends GenericAction {

	private static final Logger log = Logger
			.getLogger(GuardarModulosRolAction.class);

	@Autowired
	public ModuloDelegate moduloDelegate;

	@Autowired
	public RolDelegate rolDelegate;

	@Autowired
	private ActuacionesDelegate actuacionDelegate;

	public ActionForward findUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String idsMod = request.getParameter("idsMod");
		String idRol = request.getParameter("idRol");
		try {
			RolDTO rolDTO = rolDelegate.consultarRol(new RolDTO(Long
					.valueOf(idRol)));
			List<ModuloDTO> lstModulos = new ArrayList<ModuloDTO>();
			if (!idsMod.isEmpty()) {
				String[] modVec = idsMod.split(",");

				for (int i = 0; i < modVec.length; i++) {
					lstModulos.add(new ModuloDTO(Long.valueOf(modVec[i])));
				}
			}
			rolDTO.setModulos(lstModulos);
			log.debug("Ids Seleccionados: " + idsMod);
			request.setAttribute("guardar", 1);

			boolean resp = rolDelegate.actualizarModulosRol(rolDTO);

			converter.alias("resp", Boolean.class);
			String xml = converter.toXML(resp);
			log.info("Modulo:: " + xml);
			// mandamos la respuesta al cliente
			escribir(response, xml, null);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward consultaRolPapa(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String rolSelect = request.getParameter("rolSelect");
			RolDTO rol = new RolDTO();
			rol.setRolId(Long.parseLong(rolSelect));
			rol = rolDelegate.consultarRolMinimo(rol);
			boolean resp = false;
			if (rol != null && rol.getRolPadre() == null) {
				resp = true;
			}
			converter.alias("resp", Boolean.class);
			String xml = converter.toXML(resp);
			log.info("Modulo:: " + xml);
			// mandamos la respuesta al cliente
			escribir(response, xml, null);
		} catch (NSJPNegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward guardarSubRol(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String nameSubRol = request.getParameter("nameSubRol");
			String descSubRol = request.getParameter("decSubRol");
			String rolSelect = request.getParameter("rolSelect");
			String op = request.getParameter("op");
			Pattern p = Pattern.compile("[!@#$%^&*()_]+");
			Matcher m, n;
			String resp = "error";
			RolDTO rolPadre = null;
			RolDTO rolNuevo = null;
			RolDTO rolCons = null;
			List<ActuacionDTO> lstADTO = null;
			List<ConfActividadDocumentoDTO> lstCADDTO = null;
			// Validamos que el nombre del rol no se encuentre vacío
			if (!nameSubRol.isEmpty() && !op.isEmpty()) {
				// Eliminamos espacios
				nameSubRol = nameSubRol.trim();
				m = p.matcher(nameSubRol);
				n = p.matcher(descSubRol);
				if (m.find() == false && n.find() == false) {
					// Validación de existencia de un rol
					// rol es nuevo

					if (op.equals("1")) {
						rolCons = rolDelegate.consultarRolMinimo(new RolDTO(
								nameSubRol));
						if (rolCons == null) {
							lstADTO = new ArrayList<ActuacionDTO>();
							lstCADDTO = new ArrayList<ConfActividadDocumentoDTO>();
							rolPadre = new RolDTO();
							rolPadre.setRolId(Long.parseLong(rolSelect));
							rolPadre = rolDelegate.consultarRolMinimo(rolPadre);
							rolPadre = rolDelegate
									.consultarConfiguracionRol(rolPadre);
							rolNuevo = new RolDTO();
							rolNuevo.setDescripcionRol(descSubRol);
							rolNuevo.setEsPrincipal(true);
							rolNuevo.setNombreRol(nameSubRol);
							rolNuevo.setRolPadre(rolPadre);
							rolNuevo.setJerarquiaOrganizacionalDTO(rolPadre
									.getJerarquiaOrganizacionalDTO());
							rolNuevo.setInstitucionPertenece(rolPadre
									.getInstitucionPertenece());
							rolNuevo.setElementosMenu(rolPadre
									.getElementosMenu());
							lstADTO = actuacionDelegate
									.consultarActuacionPorRol(rolPadre);
							for (ActuacionDTO aDTO : lstADTO) {
								lstCADDTO.add(new ConfActividadDocumentoDTO(
										aDTO.getConfActividadDocumentoId()));
							}
							Long idRolHijo = rolDelegate.crearSubRol(rolNuevo);
							rolNuevo.setRolId(idRolHijo);
							rolNuevo.setConfActividadDocumentoDTO(lstCADDTO);
							if (rolDelegate
									.actualizarConfiguracionRol(rolNuevo)) {
								resp = "exito";
							}
						} else {
							resp = "nombreRepetido";
						}
					} else if (op.equals("2")) {
						// Se quiere editar el mismo rol
						rolCons = rolDelegate.consultarRolMinimo(new RolDTO(
								nameSubRol));
						// Se puede usar el nombre
						if (rolCons == null) {
							//Se consulta la información del rol a módificar
							rolCons = rolDelegate.consultarRol((new RolDTO(Long.valueOf(rolSelect))));
							if (rolCons != null) {
									rolNuevo = rolCons;
									rolNuevo.setNombreRol(nameSubRol);
									rolNuevo.setDescripcionRol(descSubRol);
									rolDelegate.modificarRol(rolNuevo);
									resp = "exito";
								
							}else{//No existe el rol que se quiere editar
								resp="error";
							}
						} else {
							resp = "nombreRepetido";
						}
					} else {
						resp = "error";
					}

				} else {
					resp = "caracteresEspeciales";
				}
			} else {
				resp = "nombreVacio";
			}
			converter.alias("resp", String.class);
			String xml = converter.toXML(resp);
			// mandamos la respuesta al cliente
			escribir(response, xml, null);
		} catch (NSJPNegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward editarSubRol(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String idRol = request.getParameter("idRol");
			String resp = "error";
			RolDTO rolDTO = null;
			String xml = "";
			// Valida que no venga el valor vacío
			if (!idRol.isEmpty()) {
				// Validación de existencia de un rol
				rolDTO = new RolDTO(Long.valueOf(idRol));
				rolDTO = rolDelegate.consultarRolMinimo(rolDTO);
				if (rolDTO != null) {
					resp = "exito";
					rolDTO.setRolPadre(null);
					converter.alias("rol", RolDTO.class);
					xml += converter.toXML(rolDTO);
				}
			}
			converter.alias("resp", String.class);
			xml += converter.toXML(resp);
			log.info("Rol:: " + xml);
			// mandamos la respuesta al cliente
			escribir(response, xml, null);
		} catch (NSJPNegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
