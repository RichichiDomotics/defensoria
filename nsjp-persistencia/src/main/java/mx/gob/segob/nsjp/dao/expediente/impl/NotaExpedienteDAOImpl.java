/**
 * Nombre del Programa : NotaExpedienteDAOImpl.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 07/07/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementación para el DAO de NotaExpediente
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
package mx.gob.segob.nsjp.dao.expediente.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.NotaExpedienteDAO;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NotaExpediente;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementación para el DAO de NotaExpediente
 * 
 * @version 1.0
 * @author rgama
 *
 */
@Repository
public class NotaExpedienteDAOImpl extends GenericDaoHibernateImpl<NotaExpediente, Long> implements NotaExpedienteDAO {

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
	
	@SuppressWarnings("unchecked")
	@Override
    public List<NotaExpediente> consultarNotasPorExpediente(Expediente expediente, Funcionario funcionario) {
    	List<NotaExpediente> notas = new ArrayList<NotaExpediente>();
        StringBuffer queryString = new StringBuffer();
        queryString.append(" from NotaExpediente o where o.expediente.expedienteId  = ");
        queryString.append(expediente.getExpedienteId());
        // si se recibe un funcionario, solo consultara las notas de ese funcionario en ese expediente
        // de lo contrario consultara todas aquellas donde el funcionario sea null
        if(funcionario != null) {
        	queryString.append(" and o.funcionario.claveFuncionario = " + funcionario.getClaveFuncionario()).append(" ");
        } else {
        	queryString.append(" and o.funcionario.claveFuncionario IS NULL ");
        }
        
        logger.debug("Query:" + queryString);
        Query query = super.getSession().createQuery(queryString.toString());
        notas = query.list();
        logger.debug("Total de notas recuperadas:" + notas.size() + " asociadas al expediente "+ expediente.getExpedienteId());
        return notas;
    }

  
}
