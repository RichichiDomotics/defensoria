/**
* Nombre del Programa : EnviarSolicitudServiceExporterimpl.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 03/09/2012
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
package mx.gob.segob.nsjp.service.ws.impl;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO;
import mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudService;
import mx.gob.segob.nsjp.service.ws.EnviarSolicitudServiceExporter;
import mx.gob.segob.nsjp.service.ws.infra.impl.ApplicationContextAwareWSNSJPImpl;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
@WebService
public class EnviarSolicitudServiceExporterImpl implements
		EnviarSolicitudServiceExporter {

	private EnviarSolicitudService enviarSolicitudService;
	
	@WebMethod (exclude = true)
	private void init() throws NSJPNegocioException{
		try{
			enviarSolicitudService = (EnviarSolicitudService)ApplicationContextAwareWSNSJPImpl
						.springApplicationContext.getBean("enviarSolicitudService");
		}catch(Exception e){
			e.printStackTrace();
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);			
		}
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudService#enviarSolicitud(mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO, mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
	 */
	@Override
	@WebMethod (exclude = true)
	public SolicitudDTO enviarSolicitud(SolicitudDTO solicitudDTO,
			Instituciones destino,
			List<DocumentoDTO> lstDocumentoAdjuntos, SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		return null;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudService#recibirSolicitud(mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO)
	 */
	@Override
	@WebMethod
	public SolicitudWSDTO recibirSolicitud(SolicitudWSDTO solicitudWSDTO) throws NSJPNegocioException {
		init();
		return enviarSolicitudService.recibirSolicitud(solicitudWSDTO);
	}

}
