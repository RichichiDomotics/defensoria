package mx.gob.segob.nsjp.dao.test.municipio.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.domicilio.MunicipioDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Municipio;

public class MunicipioDAOImplTest extends BaseTestPersistencia<MunicipioDAO> {
	
	public void testConsultarTodas() {
	      List<Municipio> municipios = super.daoServcice.findAll("nombreMunicipio",true);
	      logger.debug("formas.size() :: "+municipios.size());
	      for (Municipio ff : municipios) {
	  		logger.info(" ID :  "+ ff.getMunicipioId());
	  		logger.info(" nombre: "+ ff.getNombreMunicipio());
	      }
	  }

}
