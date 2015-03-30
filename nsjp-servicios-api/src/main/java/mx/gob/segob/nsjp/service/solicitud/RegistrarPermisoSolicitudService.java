/**
 * Enable JC. Compartir solicitudU UAVD.
 */
package mx.gob.segob.nsjp.service.solicitud;

import java.util.Date;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;

public interface RegistrarPermisoSolicitudService {
	void registrarPermisoSolicitudFuncionario(Long claveFuncionario, Long solicitudId, Date fechaLimite, Boolean permiso)
			throws NSJPNegocioException;

	void eliminarPermisoSolicitudFuncionario(Long funcionarioId, Long solicitudId) throws NSJPNegocioException;
}
