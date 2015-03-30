/**
* Nombre del Programa : HistoricoExpedienteServiceImplTest.java
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
package mx.gob.segob.nsjp.service.test.expediente.impl;

import java.util.List;

import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;
import mx.gob.segob.nsjp.service.expediente.HistoricoExpedienteService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class HistoricoExpedienteServiceImplTest extends BaseTestServicios<HistoricoExpedienteService> {

	/**
	 * Test method for {@link mx.gob.segob.nsjp.service.expediente.impl.HistoricoExpedienteServiceImpl#consultarHistoricoExpediente(mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO)}.
	 */
	public void testConsultarHistoricoExpediente() {
		
		HistoricoExpedienteDTO historicoExpedienteDTO = null;
		List<HistoricoExpedienteDTO> lstHistoricoExpedientesDTO = service.consultarHistoricoExpediente(historicoExpedienteDTO);
		assertNotNull(lstHistoricoExpedientesDTO);
		assertTrue((lstHistoricoExpedientesDTO.size() > 0));		
		
	}

}
