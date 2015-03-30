/**
 * 
 */
package mx.gob.segob.nsjp.dao.test.catDiscriminante.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.catalogo.TipoDiscriminante;
import mx.gob.segob.nsjp.dao.catalogo.CatDiscriminateDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CatDiscriminante;

/**
 * @author AlejandroGA
 *
 */
public class CatDiscriminanteDAOImplTest extends BaseTestPersistencia<CatDiscriminateDAO> {
	
	public void testConsultarCarDiscriminantePorId(){
		
		Long discriminanteId = 0L;
		
		CatDiscriminante catDis =  daoServcice.consultarDiscriminantePorId(discriminanteId);
		logger.info(" Cat Discriminante Obtenido: "+ catDis);
		logger.info(" Cat DiscriminanteId Obtenido: "+ catDis.getCatDiscriminanteId());
	}
	
public void testconsultarDiscriminantesXDistrito(){
		
		Long distritoId = 1L;
		
		List<CatDiscriminante> catDis =  daoServcice.consultarDiscriminantesXDistrito(distritoId,null);
		for(CatDiscriminante discr: catDis){
			logger.info(" Cat Discriminante Obtenido: "+ discr.getCatDiscriminanteId());
			logger.info(" Cat DiscriminanteId Obtenido: "+ discr.getNombre());
		}
		
	}

public void testconsultarDiscriminantesXTipo(){
	
	List<CatDiscriminante> catDis =  daoServcice.consultarDiscriminantesXTipo(TipoDiscriminante.AGENCIA.ordinal());
	for(CatDiscriminante discr: catDis){
		logger.info(" Cat Discriminante Obtenido: "+ discr.getCatDiscriminanteId());
		logger.info(" Cat DiscriminanteId Obtenido: "+ discr.getNombre());
	}
}
public void testFindAll(){
	
	List<CatDiscriminante> catDis =  daoServcice.findAll("nombre", true);
	for(CatDiscriminante discr: catDis){
		logger.info(" Cat Discriminante Obtenido: "+ discr.getCatDiscriminanteId());
		logger.info(" Cat DiscriminanteId Obtenido: "+ discr.getNombre());
	}
}

}
