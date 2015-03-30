package mx.gob.segob.nsjp.dao.test.centoDetencion.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.detencion.CentroDetencionDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CentroDetencion;

public class CentroDetencionDAOImplTest extends BaseTestPersistencia<CentroDetencionDAO> {
	
	public void testConsultarTodas() {
	      List<CentroDetencion> cd = super.daoServcice.consultarCentrosDetencionPorTipo(2751L);
	      logger.debug("formas.size() :: "+cd.size());
	      for (CentroDetencion ff : cd) {
	  		logger.info(" ID :  "+ ff.getCentroDetencionId());
	  		logger.info(" nombre: "+ ff.getNombre());
	      }
	  }

}
