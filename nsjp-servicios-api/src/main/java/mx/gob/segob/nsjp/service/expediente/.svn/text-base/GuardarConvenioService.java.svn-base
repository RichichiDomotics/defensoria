/**
 * 
 */
package mx.gob.segob.nsjp.service.expediente;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ConvenioDTO;

/**
 * @author adrian
 *
 */
public interface GuardarConvenioService {

	/**
 	 * Operación que permite registrar o modificar un acuerdo restaurativo
 	 * formaID -> Se recupera al cargar las actuaciones, de venir este parámetro con
 	 * valor nulo, se muestra la plantilla en blanco.
	 * idAcuerdo==null entonces REGISTRA
	 * idAcuerdo!=null entonces MODIFICA
	 * @param restaurativoDTO
	 * @param formaID
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long guardarConvenio(ConvenioDTO restaurativoDTO, Long formaID)throws NSJPNegocioException;

}
