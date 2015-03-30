/**
* Nombre del Programa : AvisoDetencionDelegate.java
* Autor                            : Hugo Serrano
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Delegate para los Avisos de Detencion
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
package mx.gob.segob.nsjp.delegate.avisodesignacion;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDetencionDTO;



/**
 * Contrato Delegate para los Avisos de Detencion
 * @version 1.0
 * @author Tattva-IT
 *
 */
public interface AvisoDesignacionDelegate {
	
	/**
	 * Obtiene los avisos de detencion por estatus
	 * @return List<NotificacionDTO>
	 * @throws NSJPNegocioException
	 */

	List<AvisoDesignacionDTO> obtenerAvisosDesignacionPorEstatus(EstatusNotificacion estatusnotificacion, Long discriminanteId) throws NSJPNegocioException;

	/**
	 *
	 * @param avisoDetencionDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	AvisoDesignacionDTO atenderAvisoDesignacion(AvisoDesignacionDTO avisoDesignacionnDTO) throws NSJPNegocioException;
    

}
