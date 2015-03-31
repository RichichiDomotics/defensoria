package mx.gob.segob.nsjp.service.ws.impl;

import java.text.ParseException;

import javax.jws.WebService;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.pjsiaj.NotificacionSIAJWSDTO;
import mx.gob.segob.nsjp.dto.audiencia.pjsiaj.RespuestaWSDTO;
import mx.gob.segob.nsjp.service.audiencia.NotificacionPJSIAJService;
import mx.gob.segob.nsjp.service.audiencia.RecibirNotificacionAudienciaService;
import mx.gob.segob.nsjp.service.ws.NotificacionPJSIAJServiceExporter;
import mx.gob.segob.nsjp.service.ws.infra.impl.ApplicationContextAwareWSNSJPImpl;

import org.apache.log4j.Logger;

@WebService(endpointInterface = "mx.gob.segob.nsjp.service.ws.NotificacionPJSIAJServiceExporter")
public class NotificacionPJSIAJServiceExporterImpl implements
		NotificacionPJSIAJServiceExporter {
	
	private final static Logger logger =
	        Logger.getLogger(NotificacionPJSIAJServiceExporterImpl.class);
	
	private NotificacionPJSIAJService notificacionpjsiajService;

	@Override
	public RespuestaWSDTO recibeNotificacionAudiencia(
			NotificacionSIAJWSDTO notificacionWSDTO) throws NSJPNegocioException, ParseException {
		logger.debug(" NotificacionPJSIAJServiceExporterImpl - recibeNotificacionAudiencia - " + notificacionWSDTO);
		notificacionpjsiajService = (NotificacionPJSIAJService) ApplicationContextAwareWSNSJPImpl.springApplicationContext
                .getBean("notificacionPJSIAJServiceImpl");
		
		return notificacionpjsiajService.recibeNotificacionAudiencia(notificacionWSDTO);
	}

}
