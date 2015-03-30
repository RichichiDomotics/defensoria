/**
* Nombre del Programa : HistoricoExpedienteDelegateImpl.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 29/06/2012
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
package mx.gob.segob.nsjp.delegate.expediente.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.nsjp.comun.excepcion.NSJPSistemaException;
import mx.gob.segob.nsjp.delegate.expediente.HistoricoExpedienteDelegate;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;
import mx.gob.segob.nsjp.service.expediente.HistoricoExpedienteService;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
@Service("historicoExpedienteService")
public class HistoricoExpedienteDelegateImpl implements
		HistoricoExpedienteDelegate {

	
	@Autowired
	HistoricoExpedienteService historicoExpedienteService;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.expediente.HistoricoExpedienteDelegate#consultarHistoricoExpediente(mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO)
	 */
	@Override
	public List<HistoricoExpedienteDTO> consultarHistoricoExpediente(
			HistoricoExpedienteDTO historicoExpedienteDTO)
			throws NSJPSistemaException {
		return historicoExpedienteService.consultarHistoricoExpediente(historicoExpedienteDTO);
	}

}
