package mx.gob.segob.nsjp.dao.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.AvisoDetencion;

public interface AvisoDesignacionDAO extends GenericDao<AvisoDesignacion, Long> {

	/**
	 * Consulta los avisos de Designacion por estatus y por clave de funcionario
	 * @param estado
	 * @param funcionario
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<AvisoDesignacion> consultarAvisosDesignacion(Long estado, Long claveFuncionario) throws NSJPNegocioException;

	/**
	 * Consulta los avisos de designacion para un expediente específico
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<AvisoDesignacion> consultarAvisosDesignacionPorIdExpediente(
			Long idExpediente) throws NSJPNegocioException;
	
	/**
	 * Consulta los avisos de designacion por un id de AvisoDetencionId
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public AvisoDesignacion consultarAvisosDesignacionPorIdAvisoDetencion(Long avisoDetencionID) throws NSJPNegocioException;
	
	/**
	 * Servicio para obtener avisos de detencion por un estatus y por discriminante
	 * @param estatus
	 * @return List<AvisoDetencion>
	 */

	List<AvisoDesignacion> obtenerAvisosDesignacionPorEstatus(Long estatus, Long discriminanteId);
	
}
