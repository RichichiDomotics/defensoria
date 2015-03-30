/**
* Nombre del Programa : ConsultarEslabonService.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 30/08/2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato del (de los) servicio(s) para consultar los eslabones 
* 							asociados a las evidencias.
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
package mx.gob.segob.nsjp.service.eslabon;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;

/**
 * Contrato del (de los) servicio(s) para consultar los eslabones 
 * asociados a las evidencias.
 * 
 * @version 1.0
 * @author GustavoBP
 */
public interface ConsultarEslabonService {
	
	/**
	 * Consulta los eslabones asociados a una evidencia
	 * 
	 * @param evidenciaDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<EslabonDTO> consultarEslabonesPorEvidencia(EvidenciaDTO evidenciaDTO) throws NSJPNegocioException;
	
	/**
	 * Servicio que consulta si un objeto, que sea evidencia y, que esté, o no, en un eslabon.
	 * 	
	 * @param idObjeto
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean tieneEslabonPorEvidenciaYObjeto(Long idObjeto)  throws NSJPNegocioException;
	
	/**
	 * Servicio que consulta si un objeto, activo, es una evidencia y no tiene un eslabon asociado.
	 * 
	 * @param idObjeto
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean esEvidenciaNoTieneEslabon(Long idObjeto)  throws NSJPNegocioException;
	
	 /**
     * Consulta el ultimo eslabon asociado a una evidencia, opcionalmente por tipo de eslabon
     * @param EvidenciaDTO La evidencia de la que se requiere el ultimo eslabon.
     * @param tipoEslabon Representa el tipo de eslabon
     * @return El ultimo eslabon asociado a una evidencia o {@code null} en caso
     * que la evidencia solicitada no exista o que no tenga eslabones asociados.
     */
	EslabonDTO consultaUltimoEslabonXEvidenciaYTipo(EvidenciaDTO evidenciaDTO, Long tipoEslabon) throws NSJPNegocioException;

	/**
	 * Permite obtener el id de expediente que tiene al cual pertenece un eslabon
	 * @param eslabonId
	 * @return
	 */
	public Long obtenIdExpedienteDeUnEslabon(Long eslabonId) throws NSJPNegocioException;

	/**
	 * Permite consultar el detalle de un eslabon por identificador
	 * @param idEslabon
	 * @return
	 * @throws NSJPNegocioException
	 */
	public EslabonDTO consultaEslabonPorId(Long idEslabon) throws NSJPNegocioException;


}
