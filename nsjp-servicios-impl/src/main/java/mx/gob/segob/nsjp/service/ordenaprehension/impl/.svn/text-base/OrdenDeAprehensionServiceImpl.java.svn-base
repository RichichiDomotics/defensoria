/**
* Nombre del Programa : OrdenDeAprehensionServiceImpl.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 01/08/2012
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
package mx.gob.segob.nsjp.service.ordenaprehension.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.ordenaprehension.OrdenDeAprehensionDAO;
import mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO;
import mx.gob.segob.nsjp.model.OrdenDeAprehension;
import mx.gob.segob.nsjp.service.ordenaprehension.OrdenDeAprehensionService;
import mx.gob.segob.nsjp.service.ordenaprehension.impl.transform.OrdenDeAprehensionTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
@Service
public class OrdenDeAprehensionServiceImpl implements OrdenDeAprehensionService {

	@Autowired
	OrdenDeAprehensionDAO ordenDeAprehensionDAO;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.ordenaprehension.OrdenDeAprehensionService#registrarOrdenDeAprehension(mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO)
	 */
	@Override
	public OrdenDeAprehensionDTO registrarOrdenDeAprehension(
			OrdenDeAprehensionDTO ordenDeAprehensionDTO)
			throws NSJPNegocioException {
		OrdenDeAprehension ordenDeAprehension = OrdenDeAprehensionTransformer.transformar(ordenDeAprehensionDTO);
		Long id = ordenDeAprehensionDAO.create(ordenDeAprehension);
		ordenDeAprehensionDTO.setOrdenDeAprehensionId(id);
		return ordenDeAprehensionDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.ordenaprehension.OrdenDeAprehensionService#consultarOrdenDeAprehension(mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO)
	 */
	@Override
	public List<OrdenDeAprehensionDTO> consultarOrdenDeAprehension(
			OrdenDeAprehensionDTO filtro) throws NSJPNegocioException {
		OrdenDeAprehension filtroEntity = OrdenDeAprehensionTransformer.transformar(filtro);
		List<OrdenDeAprehension> lstOrdenDeAprehension = ordenDeAprehensionDAO.consultarOrdenDeAprehension(filtroEntity);
		List<OrdenDeAprehensionDTO> lstOrdenDeAprehensionDTO = null;
		if(lstOrdenDeAprehension != null){
			lstOrdenDeAprehensionDTO = new ArrayList<OrdenDeAprehensionDTO>();
			for (OrdenDeAprehension ordenDeAprehension : lstOrdenDeAprehension) {
				lstOrdenDeAprehensionDTO.add(OrdenDeAprehensionTransformer.transformar(ordenDeAprehension));
			}
		}
		return lstOrdenDeAprehensionDTO;

	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.ordenaprehension.OrdenDeAprehensionService#ordenDeAprehendionCumplida(mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO)
	 */
	@Override
	public void ordenDeAprehendionCumplida(
			OrdenDeAprehensionDTO ordenDeAprehensionDTO)
			throws NSJPNegocioException {
		OrdenDeAprehension ordenDeAprehension = OrdenDeAprehensionTransformer.transformar(ordenDeAprehensionDTO);
		ordenDeAprehensionDAO.saveOrUpdate(ordenDeAprehension);
	}

}
