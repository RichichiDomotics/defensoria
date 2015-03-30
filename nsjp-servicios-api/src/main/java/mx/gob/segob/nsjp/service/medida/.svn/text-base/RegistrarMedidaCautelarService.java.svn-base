/**
 * Nombre del Programa  : RecibirMedidaCautelarService.java
 * Autor                : Daniel Jiménez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 29 Ago 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Servicio encargado de recibir y registrar en la BDD
 * objetos de tipo Medida Cautelar
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.medida;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.medida.MedidaCautelarWSDTO;


public interface RegistrarMedidaCautelarService {

	public void registrarMedidaCautelar(MedidaCautelarWSDTO medidaCautelar) throws NSJPNegocioException;
	
	/**
	 * Servicio que se encarga de actualizar el estatus de la Medida Cautelar
	 * y de acuerdo de la institución origen, se hace la búsqueda para actualizar 
	 * la medida cautelar. La institucion de origen es de donde se genero incialmente.
	 *   
	 * @param medidaCautelar
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Boolean actualizarEstatusMedidaCautelar(MedidaCautelarWSDTO medidaCautelar) throws NSJPNegocioException;
	
}
