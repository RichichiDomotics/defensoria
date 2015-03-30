/**
 * Enable JC. Compartir solicitudes UAVD
 */

package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.solicitud.PermisoSolicitudDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.PermisoSolicitud;
import mx.gob.segob.nsjp.model.PermisoSolicitudId;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.service.solicitud.RegistrarPermisoSolicitudService;

@Transactional
@Service
public class RegistrarPermisoSolicitudServiceImpl implements
		RegistrarPermisoSolicitudService {

	private static final Logger logger = Logger.getLogger(RegistrarPermisoSolicitudServiceImpl.class);

	@Autowired
	private SolicitudDAO solicitudDAO;

	@Autowired
	private FuncionarioDAO funcionarioDAO;

	@Autowired
	private PermisoSolicitudDAO permisoSolicitudDAO;

	@Override
	public void registrarPermisoSolicitudFuncionario(Long claveFuncionario, Long solicitudId, Date fechaLimite, Boolean permiso)
			throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA REGISTRAR LOS PERMISOS DE EXPEDIENTE ****/");

		PermisoSolicitud permisoSolicitud = new PermisoSolicitud();
		Solicitud solicitud = new Solicitud();
		Funcionario funcionario = new Funcionario();

		PermisoSolicitudId permisoExpedienteId = new PermisoSolicitudId();

		if (solicitudId!=null) {
			solicitud = solicitudDAO.read(solicitudId);
			permisoSolicitud.setSolicitud(solicitud);
			permisoExpedienteId.setSolicitudId(solicitudId);
		}

		if (funcionario!=null) {
			funcionario = funcionarioDAO.read(claveFuncionario);
			permisoSolicitud.setFuncionario(funcionario);
			permisoExpedienteId.setIclaveFuncionario(claveFuncionario);
		}

		if (fechaLimite!=null)
			permisoSolicitud.setFechaLimite(fechaLimite);

		permisoSolicitud.setEsEscritura(permiso);

		permisoSolicitud.setId(permisoExpedienteId);

		permisoSolicitudDAO.saveOrUpdate(permisoSolicitud);

		if (logger.isDebugEnabled())
			logger.debug("/**** EL REGISTRO DEL PERMISO DEL FUNCIONARIO SOBRE EL EXPEDIENTE SE REALIZO CON EXITO ****/");
	}

	public void eliminarPermisoSolicitudFuncionario(Long funcionarioId, Long solicitudId) throws NSJPNegocioException{
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA ELIMINAR LOS PERMISOS DE EXPEDIENTE ****/");

		if (funcionarioId == null || solicitudId == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		PermisoSolicitud permisoExpediente = new PermisoSolicitud();
		PermisoSolicitudId permisoExpedienteId = new PermisoSolicitudId();

		permisoExpedienteId.setIclaveFuncionario(funcionarioId);
		permisoExpedienteId.setSolicitudId(solicitudId);

		permisoExpediente = permisoSolicitudDAO.read(permisoExpedienteId);

		permisoSolicitudDAO.delete(permisoExpediente);

		if (logger.isDebugEnabled())
			logger.debug("/**** SE ELIMINO CORRECTAMENTE EL PERMISO DEL EXPEDIENTE CON EXITO ****/");
	}
}
