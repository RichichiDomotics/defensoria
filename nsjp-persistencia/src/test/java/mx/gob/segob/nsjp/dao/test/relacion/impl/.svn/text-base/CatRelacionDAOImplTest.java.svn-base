package mx.gob.segob.nsjp.dao.test.relacion.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.relacion.CatRelacionDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CatCategoriaRelacion;
import mx.gob.segob.nsjp.model.CatRelacion;

public class CatRelacionDAOImplTest extends BaseTestPersistencia<CatRelacionDAO> {
	
	public void testConsultarCatRelacionesXCatCategoriaRelacion () {
		
		CatCategoriaRelacion catCategoriaRelacion = new CatCategoriaRelacion();
		catCategoriaRelacion.setCatCategoriaRelacionId(1L);
		
		List<CatRelacion> catRelacion = daoServcice.consultarCatRelacionesXCatCategoriaRelacion(catCategoriaRelacion);
		
		logger.info("Relaciones ID: " + catRelacion.size());
		assertTrue("El identificado debe ser mayor a cero : ", catRelacion.size()>0);
		for (CatRelacion rel : catRelacion) {
			logger.info("Id: "+rel.getCatRelacionId());
			logger.info("Descr: "+rel.getDescripcionRelacion());
		}
		
	}
	
	public void testconsultarParentescos(){
		List<CatRelacion> catRelaciones = daoServcice.consultarParentescos();
		logger.info("tam: "+catRelaciones.size());
		for(CatRelacion catrel : catRelaciones){
			logger.info("ID: "+catrel.getCatRelacionId());
			logger.info("ID: "+catrel.getDescripcionRelacion());
		}
	}

}
