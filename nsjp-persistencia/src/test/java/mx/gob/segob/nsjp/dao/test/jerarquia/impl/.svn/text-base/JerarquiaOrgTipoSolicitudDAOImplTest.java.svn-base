package mx.gob.segob.nsjp.dao.test.jerarquia.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrgTipoSolicitudDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Valor;

public class JerarquiaOrgTipoSolicitudDAOImplTest extends
		BaseTestPersistencia<JerarquiaOrgTipoSolicitudDAO> {
	
	public void consultarTipoSolicitudPorJerarquiaOrganizacionalTest(){
		
		List<Valor> listaValoresTipoSolicitud = 
			daoServcice.consultarTipoSolicitudPorJerarquiaOrganizacional(10L);
		logger.info("Size: " + listaValoresTipoSolicitud.size());
		assertTrue("El identificado debe ser mayor a cero : ", listaValoresTipoSolicitud.size()>0);
		for(Valor val: listaValoresTipoSolicitud){
			logger.info("valor: " + val.getValorId());
			logger.info("valor: " + val.getValor());
		}
	}

}
