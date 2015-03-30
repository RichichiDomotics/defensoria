/**
* Nombre del Programa : EnviarSolicitudService.java
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
package mx.gob.segob.nsjp.service.solicitud;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */


public interface EnviarSolicitudService {
	
	/**
	 * M&eacute;todo que env&iacute;a una solicitud a otra instituci&oacute;n
	 * @param solicitudDTO
	 * @param destino
	 * @param lstDocumentoAdjuntos
	 * @param sentenciaDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	SolicitudDTO enviarSolicitud(SolicitudDTO solicitudDTO,
			Instituciones destino, List<DocumentoDTO> lstDocumentoAdjuntos,
			SentenciaDTO sentenciaDTO) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que recibe una solicitud una solicitud de otra instituci&oacute;n
	 * @param solicitudDTO
	 * @param destino
	 * @return
	 * @throws NSJPNegocioException 
	 */	
	SolicitudWSDTO recibirSolicitud(SolicitudWSDTO solicitudWSDTO) throws NSJPNegocioException;


 
	
	
}
