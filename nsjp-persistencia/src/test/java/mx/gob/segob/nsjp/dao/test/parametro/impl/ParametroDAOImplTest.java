package mx.gob.segob.nsjp.dao.test.parametro.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Parametro;

public class ParametroDAOImplTest extends BaseTestPersistencia<ParametroDAO> {
	
	public void testConsultarTodas() {
//        List<Parametro> parametros = super.daoServcice.findAll("clave",true);
        List<Parametro> parametros = super.daoServcice.findAll("descripcion",true);
        logger.debug("formas.size() :: "+parametros.size());
        for (Parametro ff : parametros) {
    		logger.info(" ID :  "+ ff.getParametroId());
    		logger.info(" Desc: "+ ff.getDescripcion());
    		logger.info(" nombre: "+ ff.getClave());
    		logger.info(" Valor: "+ ff.getValor());
        }
    }

}
