/**
 * Nombre del Programa : PermisoAudienciaDAOImplTest.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Describir el objetivo de la clase de manera breve
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.test.audiencia.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.audiencia.PermisoAudienciaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.PermisoAudiencia;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public class PermisoAudienciaDAOImplTest extends BaseTestPersistencia<PermisoAudienciaDAO> {

	public void testConsultarAudienciasPorEstatus(){
		List<PermisoAudiencia> permisoAudiencias = daoServcice.consultarPermisosAudienciaPorEstado(1L, null, null, null);
		logger.debug("Existen "+permisoAudiencias.size()+" permisos de audiencias");
		for (PermisoAudiencia pu : permisoAudiencias) {
			logger.info("--------------------------------------------");			
			logger.info("Audiencia: "+pu.getAudiencia().getAudienciaId());
			logger.info("Fecha: "+pu.getFechaSolicitud());
			logger.debug("Funcionario: "+pu.getFuncionarioExterno().getCveFuncionarioInstExt());
		}
    }
	
	public void testConsultarSolicitudesPorAudiencia(){		
		List<PermisoAudiencia> permisoAudiencias = daoServcice.consultarPermisosAudienciaPorNumeroAudienciaInterno(336L,"admonPJ");
		logger.debug("Existen "+permisoAudiencias.size()+" permisos de audiencias");
    }
}
