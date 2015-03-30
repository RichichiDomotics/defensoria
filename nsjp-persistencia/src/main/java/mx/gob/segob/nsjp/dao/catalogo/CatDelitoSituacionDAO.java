/**
 * 
 */
package mx.gob.segob.nsjp.dao.catalogo;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.SituacionImputado;

/**
 * @author EduardoAD
 *
 */
public interface CatDelitoSituacionDAO extends GenericDao<SituacionImputado, Long> {
	/**
     * Consulta todos
     * 
     * @return
     */
   List<SituacionImputado> consultarTodos();
}
