package mx.gob.segob.nsjp.service.test.leycodigo.imp;

import java.io.ByteArrayOutputStream;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.LeyCodigoDTO;
import mx.gob.segob.nsjp.service.leycodigo.LeyCodigoService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

public class LeyCodigoServiceTest extends BaseTestServicios<LeyCodigoService> {
	
	public void testObtenerNormasCategoria() throws NSJPNegocioException{
		
		List<LeyCodigoDTO> result = service.obtenerNormasCategoria(2072L);
		
		for (LeyCodigoDTO leyCodigo : result) {
			logger.info("== NOMBRE :: "+leyCodigo.getNombre());
			logger.info("== ID     :: "+leyCodigo.getLeyCodigoId());
			logger.info("== URL    :: "+leyCodigo.getUrl());
		}
	}
	
	public void testLeerLeyCodigo() throws NSJPNegocioException{
		
		ByteArrayOutputStream baf = service.leerLeyCodigo(5L);
		
		logger.info("== TAMAÑO DEL ARCHIVO :: "+baf.size());
		
	}

}
