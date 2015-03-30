/**
* Nombre del Programa : OrganizacionDAO.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 20 Apr 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato para los metodos de acceso a datos de Organizacion
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
package mx.gob.segob.nsjp.dao.organizacion;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.CatRelacion;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Organizacion;

/**
 * Contrato para los metodos de acceso a datos de Organizacion.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public interface OrganizacionDAO extends GenericDao<Organizacion, Long> {
	Organizacion obtenerOrganizacionByRelacion(Long elementoId, Long catRelacionId);
	
	/**
	 * M&eacute;todo encargado de llevar a cabo la consulta de las organizaciones que
	 * se encuentran relacionadas con una audiencia en espec&iacute;fico.
	 * @param audiencia - La audiencia sobre la cual se consultan las organizaciones
	 * 					  relacionadas.
	 * @return List<Organizacion> - Las organizaciones relacionadas a la audiencia pasada
	 * 								como par&aacute;metro.
	 * @throws NSJPNegocioException - En el caso de que la audiencia sobre la cual se van
	 * 								  a consultar las organizaciones sea <code>null</code>
	 * 								  o no cuente con un identificador v&aacute;lido.
	 */
	public List<Organizacion> consultarOrganizacionesAudiencia(Audiencia audiencia);
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de un involucrado a trav&eacute;s del tipo 
	 * de relaci&oacute;n y una organizaci&oacute;n
	 * @param organizacion - La organizaci&oacute;n a partir de la cual se va a obtener el 
	 * 						 involucrado
	 * @param catRelacion - El cat&aacute;logo de relaci&oacute;n a partir de la cual se va
	 * 						a obtener el involucrado.
	 * @return involucrado - El involucrado que se encuentra relacionado con la organizaci&oacute;n
	 */
	public Involucrado obtenerInvolucradoByRelacion(Organizacion organizacion, 
			CatRelacion catRelacion);
}
