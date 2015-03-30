/**
 * Nombre del Programa : FuncionarioExternoDAOImplTest.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Archivo de pruebas FuncionarioExternoDAOImplTest.java 
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
package mx.gob.segob.nsjp.dao.test.funcionarioExterno;

import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.FuncionarioExterno;

/**
 * Permite consultar funcionarios por audiencia
 * @version 1.0
 * @author AAAV
 */
public class FuncionarioExternoDAOImplTest extends
		BaseTestPersistencia<FuncionarioExternoDAO> {

	public void testConsultarFuncionarioExterno() {
		FuncionarioExterno fe = daoServcice.consultarFuncExternoPorClaveFuncExt(8L, 1L);
		if(fe==null){
			logger.info("NADA");
		}
		else{
			logger.info("fe.getFuncionarioExternoId():"+fe.getFuncionarioExternoId());
		}
	}
}