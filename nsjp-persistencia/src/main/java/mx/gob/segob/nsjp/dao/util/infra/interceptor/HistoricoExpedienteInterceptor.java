/**
 * Nombre del Programa : HistoricoExpedienteInterceptor.java
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
package mx.gob.segob.nsjp.dao.util.infra.interceptor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import mx.gob.segob.nsjp.model.HistoricoExpediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

/**
 * 
 * 
 * @version 1.0
 * @author GustavoBP
 * 
 */

public class HistoricoExpedienteInterceptor extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4264841626629466816L;


	private final static Logger LOG = Logger
			.getLogger(HistoricoExpedienteInterceptor.class);

	private Set<HistoricoExpediente> inserts = new HashSet<HistoricoExpediente>(0);

	/**
	 * Función que opera cuando se realiza un GUARDADO en Base de datos
	 * Se empleará para IAM
	 */
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		
		LOG.info("onSave");
		 
		if (entity instanceof NumeroExpediente){
			NumeroExpediente numeroExpediente = (NumeroExpediente)entity;
			HistoricoExpediente historicoExpediente = new HistoricoExpediente();
			historicoExpediente.setDFechaInicio(numeroExpediente.getFechaApertura());
			historicoExpediente.setFuncionarioActual(numeroExpediente.getFuncionario());
			historicoExpediente.setNumeroExpediente(numeroExpediente);
			inserts.add(historicoExpediente);
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public void afterTransactionCompletion(Transaction tx){
		if (tx.wasCommitted()) {
			try {
				if(inserts != null && !inserts.isEmpty()) {
					for (Iterator it = inserts.iterator(); it.hasNext();) {
						HistoricoExpediente historicoExpediente = (HistoricoExpediente)it.next();
						HistoricoExpedienteHelper.guardarHistorico(historicoExpediente);
					}
				}

			} finally {
				if(inserts != null && !inserts.isEmpty()) {
					inserts.clear();
				}
			}

		}
	}
	
	// called after committed into database
	@SuppressWarnings("rawtypes")
	public void postFlush(Iterator iterator) {
	}
}
