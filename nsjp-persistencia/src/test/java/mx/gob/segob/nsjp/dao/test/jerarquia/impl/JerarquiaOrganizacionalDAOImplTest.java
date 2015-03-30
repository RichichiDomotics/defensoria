package mx.gob.segob.nsjp.dao.test.jerarquia.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrgTipoSolicitudDAO;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.Valor;

public class JerarquiaOrganizacionalDAOImplTest extends
		BaseTestPersistencia<JerarquiaOrganizacionalDAO> {
	
	public void testGetArbolJerarquiasDependientes(){
		
		JerarquiaOrganizacional raiz = new JerarquiaOrganizacional(1L);
		
		List<JerarquiaOrganizacional> lstJerarquias = 
			daoServcice.getArbolJerarquiasDependientes(raiz);
		logger.info("Size: " + lstJerarquias.size());
		assertTrue("El identificado debe ser mayor a cero : ", lstJerarquias.size()>0);
		for(JerarquiaOrganizacional jerarquiaOrganizacional: lstJerarquias){
			logger.info("valor: " + jerarquiaOrganizacional.getJerarquiaOrganizacionalId());
			logger.info("valor: " + jerarquiaOrganizacional.getNombre());
		}
	}

}
