package mx.gob.segob.nsjp.service.test.evidencia.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.service.evidencia.GeneraEvidenciasConRetrasoService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * @author rgama
 *
 */
public class GeneraEvidenciasConRetrasoServiceImplTest extends
		BaseTestServicios<GeneraEvidenciasConRetrasoService> {

	public void generaEvidenciasConRetraso() {

		try {
			service.generaEvidenciasConRetraso();
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
	}

}
