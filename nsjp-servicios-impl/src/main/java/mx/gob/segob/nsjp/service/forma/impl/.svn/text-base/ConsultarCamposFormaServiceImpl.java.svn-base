/**
* Nombre del Programa : ConsultarCamposFormaServiceImpl.java
* Autor                            : Emigdio Hernández
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 06/06/2011
* Marca de cambio        : N/A
* Descripcion General    : Implementación del servicio de negocio para consultar
* los campos configurables de una forma
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
package mx.gob.segob.nsjp.service.forma.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.dao.forma.CamposFormaDAO;
import mx.gob.segob.nsjp.dto.forma.CamposFormaDTO;
import mx.gob.segob.nsjp.model.CamposForma;
import mx.gob.segob.nsjp.service.forma.ConsultarCamposFormaService;
import mx.gob.segob.nsjp.service.forma.impl.transform.CamposFormaTransformer;

/**
 * Implementación del servicio de negocio para consultar
 * los campos configurables de una forma
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
@Service
@Transactional(readOnly=true)
public class ConsultarCamposFormaServiceImpl implements
		ConsultarCamposFormaService {
	@Autowired
	private CamposFormaDAO camposFormaDAO;
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.forma.ConsultarCamposFormaService#consultarCamposForma()
	 */
	@Override
	public List<CamposFormaDTO> consultarCamposForma(CamposFormaDTO camposFormaDTO) {
		CamposForma camposForma = CamposFormaTransformer.transformar(camposFormaDTO);
		List <CamposForma> campos = camposFormaDAO.obtenerCamposForma(camposForma);
		List <CamposFormaDTO> camposDTO = new ArrayList<CamposFormaDTO>();
		for (CamposForma campoF : campos) {
			camposDTO.add(CamposFormaTransformer.transformarCamposForma(campoF));
		}
		return camposDTO;
	}

}
