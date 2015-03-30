/**
* Nombre del Programa : ActualizarExpedienteService.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 17/08/2011
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

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;

/**
 * Contrato del Servicio para la actualizacion del Expediente
 * Se pretende engoblar aquellos servicios relacionados con la actualizacion del Expediente 
 *  
 * @version 1.0
 * @author GustavoBP
 */

public interface ActualizarExpedienteService {

	/**
	 * Servicio que permite actualizar el estatus del NumeroExpediente.
	 * Se requiere el Id del Expediente y el Valor del Estatus. 
	 * 
	 * @param expedienteDTO
	 * @return 
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO actualizarEstatusExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
	
	/**
	 * Servicio que permite actualizar el discriminante de un expediente
	 * Se requiere el numeroExpedienteId  y el cat DiscriminanteId que se desea actualizar 
	 * @param expedienteDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean actualizarCatDiscriminanteDeExpediente (ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

	/**
	 * Servicio que reasigna un Funcionario a una lista de N&uacute;meros De Expedientes
	 * 
	 * @param lstExpedienteDTO N&uacute;mero del Expediente y clave del nuevo Funcionario responsable.
	 * @param historicoExpedienteDTO Datos del funcionario que esta asignando  
	 * @return VERDADERO si realizo la reasignaci&oacute;n de lo contrario regresa FALSO.
	 * @throws NSJPNegocioException
	 */
	
	Boolean reasignarFuncionarioDeNumeroExpediente(List<ExpedienteDTO> lstExpedienteDTO, HistoricoExpedienteDTO historicoExpedienteDTO)
			throws NSJPNegocioException;
	
	/**
     * Asigna un numero de caso.<br>
     * 
     * @param expedienteDTO
     *            Obligatorio la <b>idExpediente</b> y <b>NumeroGeneralCaso</b>.
     * @return expedienteDTO
     * @throws NSJPNegocioException
     *             En caso de ocurrir algún error.
     */
    public ExpedienteDTO asignarNumeroCasoSolicitudDefensor(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;
    
    public ExpedienteDTO crearRelacion(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;
	
}
