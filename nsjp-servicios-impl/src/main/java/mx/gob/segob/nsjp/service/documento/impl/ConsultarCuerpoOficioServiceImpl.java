/**
 * 
 */
package mx.gob.segob.nsjp.service.documento.impl;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.CuerpoOficioEstructuradoDAO;
import mx.gob.segob.nsjp.dao.documento.IndiceEstructuradoDAO;
import mx.gob.segob.nsjp.dto.documento.CuerpoOficioEstructuradoDTO;
import mx.gob.segob.nsjp.model.CuerpoOficioEstructurado;
import mx.gob.segob.nsjp.model.IndiceEstructurado;
import mx.gob.segob.nsjp.service.documento.ConsultarCuerpoOficioService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.CuerpoOficioEstructuradoTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adrian
 * 
 */
@Service
@Transactional
public class ConsultarCuerpoOficioServiceImpl implements
		ConsultarCuerpoOficioService {

	public final static Logger logger = Logger
			.getLogger(ConsultarCuerpoOficioServiceImpl.class);
	@Autowired
	private CuerpoOficioEstructuradoDAO estructuradoDAO;
	@Autowired
	private IndiceEstructuradoDAO indiceDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarCuerpoOficioService#
	 * consultarCuerpoOficio(java.lang.Long)
	 */
	@Override
	public CuerpoOficioEstructuradoDTO consultarCuerpoOficio(Long cuerpoOficioId, Long indiceEstructuradoId)
		throws NSJPNegocioException {
			CuerpoOficioEstructuradoDTO cuerpoOEDTO = null;
		
			if((cuerpoOficioId==null && indiceEstructuradoId==null) ||
			   (cuerpoOficioId==0L && indiceEstructuradoId==0L)){
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}

			CuerpoOficioEstructurado cuerpo = new CuerpoOficioEstructurado();
				
			if (cuerpoOficioId!=0L){
				cuerpo = estructuradoDAO.read(cuerpoOficioId);
				cuerpoOEDTO = CuerpoOficioEstructuradoTransformer.transformarCuerpo(cuerpo);
			}
			else{
				IndiceEstructurado indice = indiceDAO.read(indiceEstructuradoId);
				cuerpo.setIndiceEstructurado(indice);
				cuerpoOEDTO = CuerpoOficioEstructuradoTransformer.transformarCuerpo(cuerpo);
			}

			return cuerpoOEDTO;
	}

}
