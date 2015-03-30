/**
* Nombre del Programa : HistoricoExpedienteDAOImplTest.java
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
package mx.gob.segob.nsjp.dao.test.expediente.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.expediente.HistoricoExpedienteDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.HistoricoExpediente;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class HistoricoExpedienteDAOImplTest extends BaseTestPersistencia<HistoricoExpedienteDAO> {

	/**
	 * Test method for {@link mx.gob.segob.nsjp.dao.expediente.impl.HistoricoExpedienteDAOImpl#consultarHistoricoExpediente(mx.gob.segob.nsjp.model.HistoricoExpediente)}.
	 */
	public void testConsultarHistoricoExpediente() {
		HistoricoExpediente historicoExpediente = null;
		List<HistoricoExpediente> lstHistoricoExpedientes = daoServcice.consultarHistoricoExpediente(historicoExpediente);
		assertNotNull(lstHistoricoExpedientes);
		assertTrue((lstHistoricoExpedientes.size() > 0));
	}
}
