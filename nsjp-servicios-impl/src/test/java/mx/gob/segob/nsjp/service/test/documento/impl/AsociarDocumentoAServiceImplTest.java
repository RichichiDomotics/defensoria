/**

 * 
 */
package mx.gob.segob.nsjp.service.test.documento.impl;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.service.documento.AsociarDocumentoAService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * @author AlejandroGA
 *
 */
public class AsociarDocumentoAServiceImplTest extends BaseTestServicios<AsociarDocumentoAService>{

	public void testAsociarDocumentoAAudiencia() throws Exception
	 {
		logger.debug("EJECUTANDO TESTS ASOCIAR DOCUMENTO A AUDIENCIA");
		
		DocumentoDTO respuesta = new DocumentoDTO();
		
		AudienciaDTO aud = new AudienciaDTO();
		aud.setId(206L);
		
		DocumentoDTO doc = new DocumentoDTO();
		doc.setDocumentoId(407L);
		
		try{
			respuesta = service.asociarDocumentoAAudiencia(aud,doc);
		}
		catch (NSJPNegocioException ne) {
			if(ne.getCodigo().equals(CodigoError.DOCUMENTO_YA_ASOCIADO)){
				logger.debug("EL DOCUMENTO SE ENCUENTRA ASOCIADO A LA AUDIENCIA CON ANTERIORIDAD::::::::::::::::::::::::::::::::::::");
			}
		}	
		logger.debug("La respuesta es: " +respuesta);
		logger.debug("La respuesta es: " +respuesta.getDocumentoId());
	 }
	

	/**
	 * Test method for {@link mx.gob.segob.nsjp.service.documento.impl.ActualizarControversiaServiceImpl#actualizarControversia(mx.gob.segob.nsjp.dto.documento.CartaCumplimientoDTO)}.
	 */
	public void testAsociarDocumentoAEslabon() {
		EslabonDTO eslabon = new EslabonDTO(11L);
		DocumentoDTO documento = new DocumentoDTO(277L);
		try {
			service.asociarDocumentoAEslabon(eslabon, documento);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	
}

