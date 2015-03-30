/**
 * 
 */
package mx.gob.segob.nsjp.dao.test.mandamientoAdjuntos.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.documento.MandamientoAdjuntosDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Documento;

/**
 * @author AlejandroGA
 *
 */
public class MandamientoAdjuntosDAOImplTest extends BaseTestPersistencia<MandamientoAdjuntosDAO>{
	
	//@Autowired
	//private MandamientoAdjuntosDAO mandamientoAdjuntosDAO;
	
	public void testConsultarDocumentoMandamientoAdjuntoPorMandamientoId(){
		
		Long mandamientoId = 927L; 
		
		List<Documento> documentosAdjuntos = daoServcice.consultarDocumentoMandamientoAdjuntoPorMandamientoId(mandamientoId);
		
		if(documentosAdjuntos != null){
			logger.info("Documentos obtenidos : " + documentosAdjuntos.size());
		}
		
		for(Documento docAdj:documentosAdjuntos){
			logger.info("DocumentoId="+docAdj.getDocumentoId());
			logger.info("nombre="+docAdj.getNombreDocumento());
			logger.info("forma="+docAdj.getForma().getNombre());
			logger.info("tipo="+docAdj.getTipoDocumento());
			logger.info("fecha="+docAdj.getFechaCreacion());
		}
	}
}
