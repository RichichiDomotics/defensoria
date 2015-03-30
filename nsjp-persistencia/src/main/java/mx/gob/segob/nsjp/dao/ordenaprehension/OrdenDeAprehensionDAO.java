/**
 * Nombre del Programa : OrdenDeAprehensionDAO.java
 * Autor                            : AntonioBV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 01/08/2012
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
package mx.gob.segob.nsjp.dao.ordenaprehension;

import java.util.List;

import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.OrdenDeAprehension;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AntonioBV
 * 
 */
@Repository
public interface OrdenDeAprehensionDAO extends
		GenericDao<OrdenDeAprehension, Long> {

	/**
	 * M&eacute;todo que consulta las  ordenes de aprehensi&oacute;n en base a un filtro
	 * @param filtro Datos por los cuales se va a filtrar la consulta 
	 * @return Lista de <code>OrdenDeAprehension</code> con los resultados de la consulta.
	 * @throws NSJPNegocioException
	 */
	List<OrdenDeAprehension> consultarOrdenDeAprehension (OrdenDeAprehension filtro) throws NSJPNegocioException;
	
}
