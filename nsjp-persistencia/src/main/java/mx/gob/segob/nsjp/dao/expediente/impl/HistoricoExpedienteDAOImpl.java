/**
* Nombre del Programa : HistoricoExpedienteDAOImpl.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 29/06/2012
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
package mx.gob.segob.nsjp.dao.expediente.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPSistemaException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.HistoricoExpedienteDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.HistoricoExpediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
@Repository("historicoExpedienteDAO")
public class HistoricoExpedienteDAOImpl extends GenericDaoHibernateImpl<HistoricoExpediente, Long> implements HistoricoExpedienteDAO {

	protected final Log LOG = LogFactory.getLog(getClass());
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.expediente.HistoricoExpedienteDAO#consultarHistoricoExpediente(mx.gob.segob.nsjp.model.HistoricoExpediente)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoExpediente> consultarHistoricoExpediente(
			HistoricoExpediente historicoExpediente)
			throws NSJPSistemaException {
		
		final StringBuffer queryStr = new StringBuffer();
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		Funcionario funcionario = null;
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("DAO: consultarHistoricoExpediente");
		}
		
		queryStr.append(" FROM HistoricoExpediente he ");
		
		if (historicoExpediente != null){					
			queryStr.append(" WHERE 1=1 ");
	
			if(historicoExpediente.getNumeroExpediente() != null){
				NumeroExpediente numeroExpediente = historicoExpediente.getNumeroExpediente(); 
				queryStr.append(" AND he.numeroExpediente.numeroExpedienteId = '" + numeroExpediente.getNumeroExpedienteId() + "' ");	
			}

			if(historicoExpediente.getFuncionarioActual() != null){
				funcionario = historicoExpediente.getFuncionarioActual(); 
				queryStr.append(" AND he.funcionarioActual.claveFuncionario = '" + funcionario.getClaveFuncionario() + "' ");	
			}
			
			if(historicoExpediente.getFuncionarioAnterior() != null){
				funcionario = historicoExpediente.getFuncionarioAnterior(); 
				queryStr.append(" AND he.funcionarioAnterior.claveFuncionario = '" + funcionario.getClaveFuncionario() + "' ");	
			}

			if(historicoExpediente.getFuncionarioAsigna() != null){
				funcionario = historicoExpediente.getFuncionarioAsigna(); 
				queryStr.append(" AND he.funcionarioAsigna.claveFuncionario = '" + funcionario.getClaveFuncionario() + "' ");
			}
			
			if(historicoExpediente.isConsultarUltimo() != null &&
					historicoExpediente.isConsultarUltimo()){
				queryStr.append(" AND he.historicoExpedienteId IN " +
						"( " +
						" SELECT MAX(tmp.historicoExpedienteId) FROM HistoricoExpediente tmp " +
						" WHERE he.numeroExpediente.numeroExpedienteId = tmp.numeroExpediente.numeroExpedienteId " +
						" ) "); 
			}			
			
		}
		
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("NumeroExpediente")) {
				queryStr.append(" ORDER BY ");
				queryStr.append(" he.numeroExpediente.numeroExpedienteId ");
				queryStr.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("FuncionarioActual")) {
				queryStr.append(" ORDER BY ");
				queryStr.append(" he.funcionarioActual.claveFuncionario ");
				queryStr.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("FuncionarioAnterior")) {
				queryStr.append(" ORDER BY ");
				queryStr.append(" he.funcionarioAnterior.claveFuncionario ");
				queryStr.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("FuncionarioAsigna")) {
				queryStr.append(" ORDER BY ");
				queryStr.append(" he.funcionarioAsigna.claveFuncionario ");
				queryStr.append(" ").append(pag.getDirOrd());
			}				
		}
				
		return ejecutarQueryPaginado(queryStr, pag);
	}
}
