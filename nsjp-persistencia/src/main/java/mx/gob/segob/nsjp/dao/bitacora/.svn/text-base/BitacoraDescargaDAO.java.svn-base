/**
 * Nombre del Programa : BitacoraDescargaDAO.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31/08/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato de DAO para BitacoraDescarga.
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
package mx.gob.segob.nsjp.dao.bitacora;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.BitacoraDescarga;

/**
 * Contrato de DAO para BitacoraDescarga.
 * 
 * @version 1.0
 * @author AAAV
 */
public interface BitacoraDescargaDAO extends GenericDao<BitacoraDescarga, Long> {

	/**
	 * Consulta la bitácora de descarga por fecha
	 * @param fechaInicio
	 * @param fechaFin
	 * @param discriminanteId
	 * @return
	 */
	List<BitacoraDescarga> consultarBitacoraDescargaPorFecha(Date fechaInicio, Date fechaFin, Long discriminanteId);	
	
	/**
	 *  Consulta la bitácora de descarga por audiencia
	 * @param audiencia
	 * @param discriminanteId
	 * @return
	 */
	List<BitacoraDescarga> consultarBitacoraDescargaPorAudiencia(Long audiencia, Long discriminanteId);	 
}
