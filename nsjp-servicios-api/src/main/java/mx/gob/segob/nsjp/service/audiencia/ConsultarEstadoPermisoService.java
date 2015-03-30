/**
 * Nombre del Programa : ConsultarEstadoPermisoService.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato del servicio para consultar Estados de Permisos
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

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.CatEstadoPermisoDTO;

/**
 * Contrato del servicio para consultar Estados de Permisos.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public interface ConsultarEstadoPermisoService {

	/**
	 * Consulta los estados de permiso
	 *  
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<CatEstadoPermisoDTO> buscarEstadosPermiso() throws NSJPNegocioException;
	
	/**
	 * Busca los estados de permiso por un filtro específico
	 * 
	 * @param estado
	 * @return
	 * @throws NSJPNegocioException
	 */
	CatEstadoPermisoDTO buscarEstadoPermisoPorEstado(Long estado) throws NSJPNegocioException;
}
