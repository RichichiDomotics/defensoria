/**
 * Nombre del Programa : CatEstadoPermisoDAOImplTest.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31 May 2011
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
package mx.gob.segob.nsjp.dao.test.audiencia.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.audiencia.CatEstadoPermisoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CatEstadoPermiso;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AAAV
 */
public class CatEstadoPermisoDAOImplTest extends BaseTestPersistencia<CatEstadoPermisoDAO> {

	public void testConsultarEstadosPermisos(){
		List<CatEstadoPermiso> EPs = daoServcice.consultarEstadosPermisos();
		logger.debug("Existen "+EPs.size()+" permisos de audiencias");
		for (CatEstadoPermiso EP : EPs) {
			logger.info("--------------------------------------------");			
			logger.info("EP.getcEstatus(): "+EP.getEstatus());
			logger.info("EP.getiEstado(): "+EP.getCatEstadoPermisoId());
		}
    }
}
