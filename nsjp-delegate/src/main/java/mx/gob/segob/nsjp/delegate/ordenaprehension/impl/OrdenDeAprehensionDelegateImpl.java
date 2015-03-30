/**
* Nombre del Programa : OrdenDeAprehensionDelegateImpl.java
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
package mx.gob.segob.nsjp.delegate.ordenaprehension.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.ordenaprehension.OrdenDeAprehensionDelegate;
import mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO;
import mx.gob.segob.nsjp.service.ordenaprehension.OrdenDeAprehensionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
@Service
public class OrdenDeAprehensionDelegateImpl implements
		OrdenDeAprehensionDelegate {

	@Autowired
	OrdenDeAprehensionService ordenDeAprehensionService;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.ordenaprehension.OrdendeAprehensionDelegate#registrarOrdenDeAprehension(mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO)
	 */
	@Override
	public OrdenDeAprehensionDTO registrarOrdenDeAprehension(
			OrdenDeAprehensionDTO ordenDeAprehensionDTO)
			throws NSJPNegocioException {
		return ordenDeAprehensionService.registrarOrdenDeAprehension(ordenDeAprehensionDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.ordenaprehension.OrdendeAprehensionDelegate#consultarOrdenDeAprehension(mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO)
	 */
	@Override
	public List<OrdenDeAprehensionDTO> consultarOrdenDeAprehension(
			OrdenDeAprehensionDTO filtro) throws NSJPNegocioException {
		return ordenDeAprehensionService.consultarOrdenDeAprehension(filtro);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.ordenaprehension.OrdendeAprehensionDelegate#ordenDeAprehendionCumplida(mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO)
	 */
	@Override
	public void ordenDeAprehendionCumplida(
			OrdenDeAprehensionDTO ordenDeAprehensionDTO)
			throws NSJPNegocioException {
		ordenDeAprehensionService.ordenDeAprehendionCumplida(ordenDeAprehensionDTO);
	}

}
