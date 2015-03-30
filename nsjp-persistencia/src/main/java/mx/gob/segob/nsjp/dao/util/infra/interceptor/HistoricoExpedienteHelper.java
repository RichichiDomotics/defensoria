/**
 * Nombre del Programa : HistoricoExpedienteHelper.java
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

import mx.gob.segob.nsjp.dao.expediente.HistoricoExpedienteDAO;
import mx.gob.segob.nsjp.model.HistoricoExpediente;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AntonioBV
 * 
 */
public class HistoricoExpedienteHelper  {

	public static void guardarHistorico( HistoricoExpediente historicoExpediente) {

		HistoricoExpedienteDAO historicoExpedienteDAO = (HistoricoExpedienteDAO) mx.gob.segob.nsjp.dao.util.infra.impl.ApplicationContextAwareNSJPImpl.springApplicationContext
		.getBean("historicoExpedienteDAO");
		
		try {
			historicoExpedienteDAO.create(historicoExpediente);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
