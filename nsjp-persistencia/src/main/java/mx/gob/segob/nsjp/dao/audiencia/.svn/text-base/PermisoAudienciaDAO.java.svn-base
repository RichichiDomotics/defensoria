/**
 * Nombre del Programa : PermisoAudienciaDAO.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 01 Jun 2012
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato para el acceso a datos de la entidad PermisoAudencia
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
package mx.gob.segob.nsjp.dao.audiencia;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.PermisoAudiencia;

/**
 * Contrato para el acceso a datos de la entidad PermisoAudencia.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public interface PermisoAudienciaDAO extends GenericDao<PermisoAudiencia, Long> {

	/**
	 * Consulta los permisos de las audiencias por estado.
	 * @param estado
	 * @param fechaInicio
	 * @param fechaFin
	 * @param discriminanteId
	 * @return
	 */
	List<PermisoAudiencia> consultarPermisosAudienciaPorEstado(Long estado, Date fechaInicio, Date fechaFin, Long discriminanteId);

	/**
	 * Consulta los permisos de la audiencia por número de audiencia para los usuarios internos
	 * @param numeroAudiencia
	 * @param claveUsuario
	 * @return
	 */
	List<PermisoAudiencia> consultarPermisosAudienciaPorNumeroAudienciaInterno(Long numeroAudiencia, String claveUsuario);
	
	/**
	 *  Consulta los permisos de la audiencia por número de audiencia para los usuarios externos
	 * @param numeroAudiencia
	 * @param cveFunExt
	 * @return
	 */
	List<PermisoAudiencia> consultarPermisosAudienciaPorFuncionarioExterno(Long numeroAudiencia, Long cveFunExt);
}
