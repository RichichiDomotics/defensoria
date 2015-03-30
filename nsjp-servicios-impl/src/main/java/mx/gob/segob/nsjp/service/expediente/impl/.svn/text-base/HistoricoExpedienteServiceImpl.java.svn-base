/**
* Nombre del Programa : HistoricoExpedienteServiceImpl.java
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
package mx.gob.segob.nsjp.service.expediente.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPSistemaException;
import mx.gob.segob.nsjp.dao.expediente.HistoricoExpedienteDAO;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;
import mx.gob.segob.nsjp.model.HistoricoExpediente;
import mx.gob.segob.nsjp.service.expediente.HistoricoExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.HistoricoExpedienteTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */

@Service
@Transactional
public class HistoricoExpedienteServiceImpl implements
		HistoricoExpedienteService {

	protected final Log LOG = LogFactory.getLog(getClass());	
	
	@Autowired
	HistoricoExpedienteDAO historicoExpedienteDAO;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.expediente.HistoricoExpedienteService#consultarHistoricoExpediente(mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO)
	 */
	@Override
	public List<HistoricoExpedienteDTO> consultarHistoricoExpediente(
			HistoricoExpedienteDTO historicoExpedienteDTO)
			throws NSJPSistemaException {
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("SERVICE: consultarHistoricoExpediente");
		}
		
		HistoricoExpediente historicoExpediente = null;		
		List<HistoricoExpediente> lstHistoricoExpedientes = null;
		List<HistoricoExpedienteDTO> lstHistoricoExpedientesDTO = null;
		try {
			historicoExpediente = HistoricoExpedienteTransformer.transformar(historicoExpedienteDTO);		
			lstHistoricoExpedientes = historicoExpedienteDAO.consultarHistoricoExpediente(historicoExpediente);
			lstHistoricoExpedientesDTO = new ArrayList<HistoricoExpedienteDTO>();
			if (lstHistoricoExpedientes != null){
				for (HistoricoExpediente temp : lstHistoricoExpedientes) {
					HistoricoExpedienteDTO tempDTO = HistoricoExpedienteTransformer.transformar(temp);
					lstHistoricoExpedientesDTO.add(tempDTO);
				}
			}
		} catch (Exception e) {
			throw new NSJPSistemaException(e.getMessage(), e);
		}
		return lstHistoricoExpedientesDTO; 
		
		
		
	}

}
