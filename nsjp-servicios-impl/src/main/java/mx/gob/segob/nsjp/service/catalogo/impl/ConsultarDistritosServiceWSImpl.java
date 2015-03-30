/**
 * 
 */
package mx.gob.segob.nsjp.service.catalogo.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.CatDistritoDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoWSDTO;
import mx.gob.segob.nsjp.model.CatDistrito;
import mx.gob.segob.nsjp.service.catalogo.ConsultarDistritosServiceWS;
import mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo.CatDistritoWSDTOTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rgama
 *
 */
@Service("consultarDistritosServiceWS")
@Transactional
public class ConsultarDistritosServiceWSImpl implements
	ConsultarDistritosServiceWS {
	
	public static final Logger logger = Logger.getLogger(ConsultarDistritosServiceWSImpl.class);
	
	@Autowired 
	private CatDistritoDAO catDistritoDAO;


	@Override
	public List<CatDistritoWSDTO> consultarDistritos()
			throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR DISTRITOS ****/");

		List<CatDistritoWSDTO> distritosWSDTO = new ArrayList<CatDistritoWSDTO>();
		List<CatDistrito> loDistritosBD = catDistritoDAO.findAll("nombreDist", true);
		for (CatDistrito discr : loDistritosBD) {
			distritosWSDTO.add(CatDistritoWSDTOTransformer.transformarCatDistrito(discr));
		}		
		return distritosWSDTO;
	}
}
