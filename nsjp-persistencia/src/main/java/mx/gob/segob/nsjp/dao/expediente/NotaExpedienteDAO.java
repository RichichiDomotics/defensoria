/**
* Nombre del Programa : NotaExpedienteDAO.java
* Autor                            : rgama
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 07/07/2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato del objeto de acceso a datos para la entidad de NotaExpediente.
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
package mx.gob.segob.nsjp.dao.expediente;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NotaExpediente;


/**
 * Contrato del objeto de acceso a datos para la entidad de NotaExpediente.
 * 
 * @version 1.0
 * @author rgama
 *
 */
public interface NotaExpedienteDAO extends GenericDao<NotaExpediente, Long>{

    /**
     * Operación que realiza la funcionalidad de consultar las notas asociadas a un expediente
     * con las siguientes condiciones: <br/>
     * a) si recibe un funcionario, solo consultara las notas correspondientes a ese funcionario.<br/> 
     * b) si no recibe un funcionario, consultara todas las notas que estén asociadas al expediente pero con 
     * funcionario igual a <code>null</code>.
     *
     * @param expediente clave del expediente
     * @param funcionario clave del funcionario
     * @return Devuelve un listado de notas asociadas al expediente
     */
	
	public List<NotaExpediente> consultarNotasPorExpediente(Expediente expediente, Funcionario funcionario);


}
