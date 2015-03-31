/**
 * Enable JC. Compartir Solicitudes UAVD
 */

package mx.gob.segob.nsjp.web.solicitud.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

public class AsignarPermisosAFuncionarioSobreSolicitudAction extends
		GenericAction {

	private static final Logger logger = Logger.getLogger(AsignarPermisosAFuncionarioSobreSolicitudAction.class);

	@Autowired
	private SolicitudDelegate solicitudDelegate;
	@Autowired
	private FuncionarioDelegate funcionarioDelegate;

	public ActionForward asignarPermisosSobreSolicitud(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp){

		try {
			Long claveFuncionario = NumberUtils.toLong(req.getParameter("claveFuncionario"),0);
			Long solicitudId = NumberUtils.toLong(req.getParameter("solicitudId").split(",")[0],0);
			Date fechaVencimietno = DateUtils.obtener(req.getParameter("fechaVencimiento"));
			Boolean permiso = BooleanUtils.toBoolean(req.getParameter("escritura"), "true", "false");
			solicitudDelegate.asignarPermisoSolicitudFuncionario(claveFuncionario, solicitudId, fechaVencimietno, permiso);

			escribir(resp, "Se guardó el permiso con éxito", null);

		} catch (Exception e) {
			escribir(resp, "Se produjo un error al guardar.", null);
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("success");
	}

	public ActionForward eliminarPermisosSobreSolicitud(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			Long claveFuncionario = NumberUtils.toLong(request.getParameter("claveFuncionario"),0);
			//Long numeroExpediente = NumberUtils.toLong(request.getParameter("numeroExpediente"),0);
			Long solicitudId = NumberUtils.toLong(request.getParameter("solicitudId").split(",")[0],0);
			solicitudDelegate.eliminarPermisoSolicitudFuncionario(claveFuncionario, solicitudId);

			escribir(response, "Se eliminó el permiso con éxito", null);
		} catch (Exception e) {
			escribir(response, "Se produjo un error al eliminar.", null);
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("success");
	}
}
