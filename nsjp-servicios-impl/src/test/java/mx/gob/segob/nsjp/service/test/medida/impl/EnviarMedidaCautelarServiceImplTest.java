package mx.gob.segob.nsjp.service.test.medida.impl;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.service.medida.EnviarMedidaCautelarService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

public class EnviarMedidaCautelarServiceImplTest extends BaseTestServicios<EnviarMedidaCautelarService> {
	
	public void testEnviarMedidaCautelarInstitucion() throws NSJPNegocioException{
		Long idMedida = 405L;
		Instituciones institucionEnviar = Instituciones.SSP;
		service.enviarMedidaCautelarInstitucion(idMedida, institucionEnviar);
	}

	public void testActualizarEstatusMedidaCautelarInstitucion(){
		Long idMedida = 722L;
		Instituciones institucionEnviar = Instituciones.PJ;
		try {
			MedidaCautelarDTO medidaDTO= service.actualizarEstatusMedidaCautelarInstitucion(idMedida, institucionEnviar);
			logger.info("Valor Regreso:"+ medidaDTO);
		} catch (NSJPNegocioException e) {
			logger.debug(e.getMessage(), e);
		}
		
	}
}
