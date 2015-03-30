/**
* Nombre del Programa : CatFormaNotificacionDAOImplTest.java
* Autor                            : EdgarTE
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 16 Aug 2012
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
package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.documento.CatFormaNotificacionDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CatFormaNotificacion;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class CatFormaNotificacionDAOImplTest extends BaseTestPersistencia<CatFormaNotificacionDAO> {
	
	public void testConsultarFormasNotificacionValidas(){
		List<CatFormaNotificacion> data = daoServcice.consultarFormasNotificacionValidas();
		for (CatFormaNotificacion cfn : data){
			logger.info("Id: " + cfn.getCatFormaNotificacionId());
			logger.info("Forma notificacion: " + cfn.getFormaNotificacion());
			logger.info("Descripcion: " + cfn.getDescripcion());
			logger.info("Valido : " + cfn.isValida());
			logger.info("_________________________________________________________________");
		}
		assertNotNull("La lista no puede ser nula", data);
	}

}
