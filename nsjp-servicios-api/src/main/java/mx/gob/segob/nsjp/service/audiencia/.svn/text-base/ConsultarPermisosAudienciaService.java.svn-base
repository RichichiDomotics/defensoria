/**
 * Nombre del Programa : ConsultarPermisosAudienciaService.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato del servicio para consultar Permisos de Audiencias. 
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.audiencia;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.PermisoAudienciaDTO;
import mx.gob.segob.nsjp.dto.bitacora.BitacoraDescargaDTO;

/**
 * Contrato del servicio para consultar Permisos de Audiencias.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public interface ConsultarPermisosAudienciaService {

	/**
	 * Filtra los permisos de audiencias por estado
	 * @param estado
	 * @param fechaInicio
	 * @param fechaFin
	 * @param discriminanteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<PermisoAudienciaDTO> buscarPermisosAudienciaPorEstado(Long estado, Date fechaInicio, Date fechaFin, Long discriminanteId)
    		throws NSJPNegocioException;
	
	/**
	 * Filtra la bitacora de descargas por fecha
	 * @param fechaInicio
	 * @param fechaFin
	 * @param discriminanteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<BitacoraDescargaDTO> buscarBitacoraDescargaPorFecha(Date fechaInicio, Date fechaFin, Long discriminanteId)
    		throws NSJPNegocioException;
	
	/**
	 * Filtra la bitacora de descargas por audiencia
	 * @param audiencia
	 * @param discriminanteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<BitacoraDescargaDTO> buscarBitacoraDescargaPorAudiencia(Long audiencia, Long discriminanteId)
    		throws NSJPNegocioException;
	
	/**
	 * Reasigna el estado de un permiso de audiencia
	 * @param estado
	 * @param permisoAudiencia
	 * @param fechaHoy
	 * @param claveUsuarioAsignador
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long cambiarEstadoPermisoAudiencia(Long estado, Long permisoAudiencia, Date fechaHoy, String claveUsuarioAsignador)
    		throws NSJPNegocioException;

	/**
	 * Consulta los permisos de audiencia por numero de audiencia y clave de usuario
	 * @param numeroAudiencia
	 * @param claveUsuario
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<PermisoAudienciaDTO> consultarPermisosAudienciaPorNumeroAudiencia(Long numeroAudiencia, String claveUsuario) throws NSJPNegocioException;

	/**
	 * Consulta los permisos de audiencia por funcionario externo
	 * @param numeroAudiencia
	 * @param cveFunExt
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<PermisoAudienciaDTO> consultarPermisosAudienciaPorFuncionarioExterno(Long numeroAudiencia, Long cveFunExt) throws NSJPNegocioException;	   	
}
