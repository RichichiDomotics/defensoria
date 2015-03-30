/**
* Nombre del Programa 		: CatNotificacionTransformer.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 16 Aug 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 							Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 							Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.notificacion.impl;

import mx.gob.segob.nsjp.dto.documento.CatFormaNotificacionDTO;
import mx.gob.segob.nsjp.model.CatFormaNotificacion;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class CatFormaNotificacionTransformer {
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un Entity de tipo
	 * CatFormaNotificacion al DTO correspondiente.
	 * @param catFormaNotificacion - El Entity a transformar
	 * @return dto- El CatFormaNotificacionDTO transformado.
	 */
	public static CatFormaNotificacionDTO transformar(CatFormaNotificacion catFormaNotificacion){
		CatFormaNotificacionDTO dto = null;
		if (catFormaNotificacion != null){
			dto = new CatFormaNotificacionDTO();
			dto.setCatFormaNotificacionId(catFormaNotificacion.getCatFormaNotificacionId());
			dto.setFormaNotificacion(catFormaNotificacion.getFormaNotificacion());
			dto.setDescripcion(catFormaNotificacion.getDescripcion());
			dto.setValida(catFormaNotificacion.isValida());
		}
		return dto;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un DTO de tipo
	 * CatFormaNotificacionDTO al Entity correspondiente.
	 * @param catFormaNotificacionDTO - El DTO a transformar
	 * @return entity - El CatFormaNotificacion transformado.
	 */
	public static CatFormaNotificacion transformar (CatFormaNotificacionDTO catFormaNotificacionDTO){
		CatFormaNotificacion entity = null;
		if (catFormaNotificacionDTO != null){
			entity = new CatFormaNotificacion();
			entity.setCatFormaNotificacionId(catFormaNotificacionDTO.getCatFormaNotificacionId());
			entity.setFormaNotificacion(catFormaNotificacionDTO.getFormaNotificacion());
			entity.setDescripcion(catFormaNotificacionDTO.getDescripcion());
			entity.setValida(catFormaNotificacionDTO.isValida());
		}
		return entity;
	}

}
