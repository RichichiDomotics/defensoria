/**
* Nombre del Programa : IngresarInvolucradoAudienciaService.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 21/09/2011
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
package mx.gob.segob.nsjp.service.audiencia;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.dto.relacion.CatRelacionDTO;

/**
 * Interfaz del servicio de negocio para asociar involucrados a una audiencia
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
public interface IngresarInvolucradoAudienciaService {
	/**
	 * Asocia un involucrado existente a una audiencia existente, si 
	 * el involucrado ya está asociado a la audiencia no envía error, se hace una validación primero
	 * @param involucradoId ID del involucrado a asociar
	 * @param AudienciaId ID de la audiencia a asociar
	 * @throws NSJPNegocioException
	 */
	void asociarInvolucradoAAudiencia(Long involucradoId,Long AudienciaId) throws NSJPNegocioException;
	
	
	/**
	 * M&eacute;todo que lleva a cabo la asignaci&oacute;n de una organizaci&oacute;n a una audiencia, 
	 * esto con el objetivo de poder asociar las organizaciones como v&iacute;mas en los 
	 * intervinientes de una audiencia.
	 * @param organizacionId - El identificador de la organizaci&oacute;n que se va a asociar con la audiencia.
	 * @param audienciaId - El identificador de la audiencia a la cual se va a asociar la organizaci&oacute;n
	 * @throws NSJPNegocioException - En el caso de que no se cuente con alguno de los par&aacute;metros necesarios 
	 * 								  para llevar a cabo la asociaci&oacute;n 
	 */
	public void asociarOrganizacionAAudiencia(Long organizacionId, Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo encargado de llevar a cabo la consulta de las organizaciones que
	 * se encuentran relacionadas con una audiencia en espec&iacute;fico.
	 * @param audiencia - La audiencia sobre la cual se consultan las organizaciones
	 * 					  relacionadas.
	 * @return List<OrganizacionDTO> - Las organizaciones relacionadas a la audiencia pasada
	 * 								   como par&aacute;metro.
	 * @throws NSJPNegocioException - En el caso de que la audiencia sobre la cual se van
	 * 								  a consultar las organizaciones sea <code>null</code>
	 * 								  o no cuente con un identificador v&aacute;lido.
	 */
	public List<OrganizacionDTO> consultarOrganizacionesAudiencia(AudienciaDTO audiencia) 
	throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de un involucrado a trav&eacute;s del tipo 
	 * de relaci&oacute;n y una organizaci&oacute;n
	 * @param organizacionDTO - La organizaci&oacute;n a partir de la cual se va a obtener el 
	 * 						    involucrado
	 * @param catRelacionDTO - El tipo de relaci&oacute;n a partir de la cual se va a obtener 
	 * 						   el involucrado.
	 * @return involucradoDTO - El involucrado que se encuentra relacionado con la 
	 * 							organizaci&oacute;n
	 */
	public InvolucradoDTO obtenerInvolucradoByRelacion(OrganizacionDTO organizacionDTO,
			CatRelacionDTO catRelacionDTO) throws NSJPNegocioException;
}
