package mx.gob.segob.nsjp.dao.test.ciudad.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.domicilio.CiudadDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Ciudad;

public class CiudadDAOImplTest extends BaseTestPersistencia<CiudadDAO> {
	
	public void testConsultarTodas() {
      List<Ciudad> ciudades = super.daoServcice.findAll("nombreCiudad",true);
      logger.debug("formas.size() :: "+ciudades.size());
      for (Ciudad ff : ciudades) {
  		logger.info(" ID :  "+ ff.getCiudadId());
  		logger.info(" nombre: "+ ff.getNombreCiudad());
  		logger.info(" abreviacion: "+ ff.getAbreviacion());
      }
  }

}
