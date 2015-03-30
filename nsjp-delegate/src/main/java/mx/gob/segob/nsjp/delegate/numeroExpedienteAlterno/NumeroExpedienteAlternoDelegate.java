/**
 * Nombre del Programa 			: NumeroExpedienteAlternoDelegate.java
 * Autor                     	: AlejandroGA
 * Compania                  	: Ultrasist
 * Proyecto                  	: NSJP                    Fecha: 26/junio/2012
 * Marca de cambio        		: N/A
 * Descripcion General    		: Clase interface delegate para administrar el numero
 * 								: de expediente alterno
 * Programa Dependiente  		: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion        	: N/A
 * Dias de ejecucion         	: N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                     	: N/A
 * Compania              		: N/A
 * Proyecto                 	: N/A                                 Fecha: N/A
 * Modificacion           		: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.delegate.numeroExpedienteAlterno;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Clase Interface delegate para administrar el numero de expediente alterno
 * @author AlejandroGA
 * @version 1.0
 */
@Deprecated
public interface NumeroExpedienteAlternoDelegate {
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n de un n&uacute;mero de expediente alterno en base al
	 * usuario firmado.
	 * @param usuario - El usuario del cual se va a obtener la unidad y el discriminante sobre el cual se va a 
	 * 					generar el n&uacute;mero alterno del expediente.  
	 * @return numExpAlterno - el n&uacute;mero de expediente alterno generado.
	 * @throws NSJPNegocioException - En el caso de que el usuario pasado como par&aacute;metro, no cuente con un 
	 * 								  distrito asociado.
	 */
	String obtenerNumeroExpedienteAlterno(UsuarioDTO usuario,ExpedienteDTO expediente) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para consultar el numero de expediente Alterno
	 * @param usuario
	 * @param expediente
	 * @return consulta el numero de expediente alterno
	 * @throws NSJPNegocioException
	 */
	String consultarNumeroExpedienteAlterno(ExpedienteDTO expediente) throws NSJPNegocioException;

	
}
