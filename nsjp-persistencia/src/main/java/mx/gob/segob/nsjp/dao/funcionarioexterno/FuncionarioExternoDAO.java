/**
* Nombre del Programa 		: FuncionarioExternoDAO.java
* Autor 					: AAAV
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 06 Jun 2012
* Marca de cambio        	: N/A
* Descripcion General    	: Objeto de acceso a datos para la entidad FuncionarioExterno
* Programa Dependiente  	: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dao.funcionarioexterno;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.FuncionarioExterno;

/**
 * Clase de acceso a datos que permite llevar a cabo operaciones sobre la entidad FuncionarioExterno.
 * @version 1.0
 * @author AAAV
 *
 */
public interface FuncionarioExternoDAO extends GenericDao<FuncionarioExterno, Long> {

	 /**
     * @param claveFuncExt
     * @return
     */
	FuncionarioExterno consultarFuncExternoPorClaveFuncExt(Long claveFuncExt, Long claveInst);
}
