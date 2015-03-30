/**
 * Nombre del Programa 	: EliminarFuncionarioAudienciaServiceImplTests.java
 * Autor               	: AlejandroGA
 * Compania            	: Ultrasist
 * Proyecto             : NSJP                    Fecha: 6 Ago 2012
 * Marca de cambio      : N/A
 * Descripcion General  : Clase para tests para eliminar Funcionario de Audiencia
 * Programa Dependiente :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion   :N/A
 * Dias de ejecucion    :N/A                             Horario: N/A
 *                           MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor				:N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.test.audiencia.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.service.audiencia.EliminarFuncionarioAudienciaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * @author AlejandroGA
 *
 */
public class EliminarFuncionarioAudienciaServiceImplTests extends BaseTestServicios<EliminarFuncionarioAudienciaService> {

	
	public void testConsultarTipoAudienciaPorIdentificador() {
		
		AudienciaDTO audiencia = new AudienciaDTO();
		audiencia.setId(452L);
		
		FuncionarioDTO funcionario = new FuncionarioDTO();
		funcionario.setClaveFuncionario(98L);
		
		try {
			service.eliminarFuncionarioAudiencia(funcionario, audiencia);
			logger.info("EL REGISTRO SE HA ELIMINADO");
			
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}

}
