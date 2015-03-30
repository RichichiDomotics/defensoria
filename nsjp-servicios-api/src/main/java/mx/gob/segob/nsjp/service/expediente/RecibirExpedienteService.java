/**
* Nombre del Programa : RecibirExpedienteService.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 08/09/2011
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
package mx.gob.segob.nsjp.service.expediente;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.informepolicial.InformePolicialHomologadoDTO;

/**
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
public interface RecibirExpedienteService {
	/**
	 * Metodo que permite crear un expediente asociado a una agencia, permitira crear un numero expediente con
	 * 2 digitos para el Distrito y 3 para las agencias
	 * @param expDTO
	 * @param idAgencia
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO guardarExpedienteRecibido(InformePolicialHomologadoDTO informePolicialHomologadoDTO, ExpedienteDTO expDTO, Long idAgencia)throws NSJPNegocioException ;
	
	/**
	 * Metodo que permite crear un expediente asociado a una agencia y agrega el numero de IPH generado en SSP, 
	 * permitira crear un numero expediente con
	 * 2 digitos para el Distrito y 3 para las agencias
	 * @param expDTO
	 * @param idAgencia
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO generarCasoExpediente(Long idAgencia, InformePolicialHomologadoDTO iph) throws NSJPNegocioException ;
}
