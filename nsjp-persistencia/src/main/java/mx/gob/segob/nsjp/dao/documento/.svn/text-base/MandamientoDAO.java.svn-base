/**
* Nombre del Programa : MandamientoDAO.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 23/08/2011
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
package mx.gob.segob.nsjp.dao.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.NumeroExpediente;

/**
 * DAO para el acceso a los mandamientos judiciales
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
public interface MandamientoDAO extends GenericDao<Mandamiento, Long> {
	/**
	 * Consulta los mandamientos judiciales relacionados a un numero de Expediente
	 * @param numeroExpediente Numero de expediente a filtrar
	 * @return Lista de mandamientos judiciales encontrados
	 */
	List<Mandamiento> consultarMandamientosPorNumeroExpediente(String numeroExpediente,Long discriminanteId);

	Mandamiento obtenerMandamientoPorFolioDoc(String folioDocumento);
	
	public List<Mandamiento> consultarMandamientoPorFiltro(
			Mandamiento mandamiento, String numeroExpediente, Instituciones institucion);
	
	/**
	 * Consulta mandamientos judiciales por un filtro, de necesitarse más restricciones, se pueden
	 * agregar, no repercute en el desarrollo actual
	 * @param mandamiento
	 * @return
	 */
	List<NumeroExpediente> consultarMandamientosJudicialesPorFiltro(
			MandamientoDTO mandamiento);	
}
