package mx.gob.segob.nsjp.dao.test.entidadFederativa.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.domicilio.EntidadFederativaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.EntidadFederativa;

public class EntidadFederativaDAOImplTest extends BaseTestPersistencia<EntidadFederativaDAO> {
	
	public void testConsultarTodas() {
	      List<EntidadFederativa> entidades = super.daoServcice.findAll("nombre",true);
	      logger.debug("formas.size() :: "+entidades.size());
	      for (EntidadFederativa ff : entidades) {
	  		logger.info(" ID :  "+ ff.getEntidadFederativaId());
	  		logger.info(" nombre: "+ ff.getNombre());
	  		logger.info(" abreviacion: "+ ff.getAbreviacion());
	      }
	  }

}
