/**
 * 
 */
package mx.gob.segob.nsjp.dao.test.catalogo.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.catalogo.CatUIEspecializadaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CatUIEspecializada;

/**
 * @author AlejandroGA
 *
 */
public class CatUIEspecializadaDAOTest extends BaseTestPersistencia<CatUIEspecializadaDAO>{

	public void testConsultarTodos() {
        List<CatUIEspecializada> data = daoServcice.consultarTodos();
        logger.debug("data.size() :: "+data.size());
        for (CatUIEspecializada catUIE : data) {
			logger.info(" ID :  "+ catUIE.getClaveUIE());
			logger.info(" ID :  "+ catUIE.getNombreUIE());	
		}
    }
	
	public void testFindAll() {
        List<CatUIEspecializada> data = daoServcice.findAll("nombreUIE", true);
        logger.debug("data.size() :: "+data.size());
        for (CatUIEspecializada catUIE : data) {
			logger.info(" ID :  "+ catUIE.getClaveUIE());
			logger.info(" ID :  "+ catUIE.getNombreUIE());	
		}
    }
}
