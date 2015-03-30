/**
* Nombre del Programa : AvisoDetencionDAO.java
* Autor                            : Hugo Serrano
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato del DAO para avisos de detencion
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
package mx.gob.segob.nsjp.dao.avisodetencion;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.AvisoDetencion;

/**
 * Contrato del DAO para avisos de detencion
 * @version 1.0
 * @author Tattva-IT
 *
 */
public interface AvisoDetencionDAO extends GenericDao<AvisoDetencion, Long> {

	/**
	 * Enable JC.
	 * @param idInvolucrado
	 * @return true en caso de tener una solicitud de defensor asociada, false en caso contrario
	 */
	Boolean involucradoTieneSolicitudDefensor(Long idInvolucrado);
	
	/**
	 * .
	 * @param idInvolucrado
	 * @return true en caso de tener una actucacion con Generar Denuncia
	 */
	Boolean involucradoTieneDenuncia(Long idInvolucrado);
	
	/**
	 * .
	 * @param idInvolucrado
	 * @return true en caso de tener una actucacion con Generar Denuncia
	 */
	Boolean tieneAvisoDetencion(Long idInvolucrado);

	/**
	 * Servicio para obtener avisos de detencion por un estatus y por discriminante
	 * @param estatus
	 * @return List<AvisoDetencion>
	 */

	List<AvisoDetencion> obtenerAvisosDetencionPorEstatus(Long estatus, Long discriminanteId);

	/**
	 *
	 * @param time
	 * @param valorId
	 * @return
	 */
	List<AvisoDetencion> consultarAvisosDetencionHistoricoByEstatus(Date fechHistorico, Long estatusAviso);
	/**
	 * Consulta los avisos por expediente.
	 * @param expedienteId
	 * @return
	 */
	 List<AvisoDetencion> consultarAvisosDetencionPorExpediente(Long expedienteId);
	 
	 /**
	 * Permite consultar un Aviso Detencion por detencionID
	 * @param numeroExpedienteId
	 * @return Involucrado
	 * @throws NSJPNegocioException
	 */
	 AvisoDetencion consultarAvisoDetencionPorDetencionID(Long detencionId);
	 
	 
}
