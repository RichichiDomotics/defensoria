/**
 * 
 */
package mx.gob.segob.nsjp.service.ws.impl;

import javax.jws.WebService;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionWSDTO;
import mx.gob.segob.nsjp.service.avisodetencion.RegistrarAvisoSinDetencionDeAreaExterna;
import mx.gob.segob.nsjp.service.ws.RegistrarAvisoSinDetencionDeAreaExternaExporter;
import mx.gob.segob.nsjp.service.ws.infra.impl.ApplicationContextAwareWSNSJPImpl;

import org.apache.log4j.Logger;

/**
 * @author vaguirre
 * 
 */
@WebService(endpointInterface = "mx.gob.segob.nsjp.service.ws.RegistrarAvisoSinDetencionDeAreaExternaExporter")
public class RegistrarAvisoSinDetencionDeAreaExternaExporterImpl implements
		RegistrarAvisoSinDetencionDeAreaExternaExporter {
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(RegistrarAvisoSinDetencionDeAreaExternaExporterImpl.class);
	private RegistrarAvisoSinDetencionDeAreaExterna service;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.avisodetencion.RegistrarAvisoSinDetencionDeAreaExterna
	 * #
	 * registrarAvisoSinDetencion(mx.gob.segob.nsjp.dto.documento.AvisoDesignacionWSDTO
	 * )
	 */
	@Override
	public AvisoDesignacionWSDTO registrarAvisoSinDetencion(AvisoDesignacionWSDTO aviso, Long idAgencia, String claveAgencia,Long idFuncionarioSolicitante)
			throws NSJPNegocioException {
		logger.info("Inicia - registrarAvisoSinDetencion(...)");
		service = (RegistrarAvisoSinDetencionDeAreaExterna) ApplicationContextAwareWSNSJPImpl.springApplicationContext.getBean("registrarAvisoSinDetencionDeAreaExterna");
		return service.registrarAvisoSinDetencion(aviso,idAgencia,claveAgencia,idFuncionarioSolicitante);
	}

}
