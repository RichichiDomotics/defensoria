package mx.gob.segob.nsjp.service.catalogo;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoWSDTO;

/**
 * @author rgama
 *
 */
public interface ConsultarDistritosServiceWS {

	
	/**
	 * Servicio que permite consultar Distritos por medio de un Web Services 
	 * 	 * @return List<CatDiscriminanteWSDTO>
	 * @throws NSJPNegocioException
	 */

	public List<CatDistritoWSDTO> consultarDistritos()throws NSJPNegocioException;	
}
