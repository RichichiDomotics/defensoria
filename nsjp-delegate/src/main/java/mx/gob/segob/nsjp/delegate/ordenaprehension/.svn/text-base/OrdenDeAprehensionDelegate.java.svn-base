/**
* Nombre del Programa : OrdendeAprehensionDelegate.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 01/08/2012
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.delegate.ordenaprehension;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public interface OrdenDeAprehensionDelegate {

	/**
	 * M&eacute;todo que registra una orden de aprehensi&oacute;n
	 * @param ordenDeAprehensionDTO Datos de la ordendeaprehension
	 * @return un <code>OrdenDeAprehensionDTO</code> con el identificador con el que se ha guardado.
	 * @throws NSJPNegocioException
	 */
	OrdenDeAprehensionDTO registrarOrdenDeAprehension(OrdenDeAprehensionDTO ordenDeAprehensionDTO) throws NSJPNegocioException;
	
	
	/**
	 * M&eacute;todo que consulta las  ordenes de aprehensi&oacute;n en base a un filtro
	 * @param filtro Datos por los cuales se va a filtrar la consulta 
	 * @return Lista de <code>OrdenDeAprehensionDTO</code> con los resultados de la consulta.
	 * @throws NSJPNegocioException
	 */
	List<OrdenDeAprehensionDTO> consultarOrdenDeAprehension(OrdenDeAprehensionDTO filtro) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que marca como cumplida una orden de aprehensi&oacute;n
	 * @param ordenDeAprehensionDTO Datos de la orden de aprehensi&oacute;n
	 * @throws NSJPNegocioException
	 */
	void ordenDeAprehendionCumplida(OrdenDeAprehensionDTO ordenDeAprehensionDTO) throws NSJPNegocioException;
	
	
}
