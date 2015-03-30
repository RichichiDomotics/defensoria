/**
 * Nombre del Programa : ConsultaNotificacionService.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 20-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.notificacion;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;

/**
 * 
 * @version 1.0
 * @author Jacob Lobaco
 */
public interface ConsultaNotificacionService {

    NotificacionDTO consultaNotificacion(NotificacionDTO consulta)
            throws NSJPNegocioException;

    /**
     * Permite consultar Notificaciones de un involucrado o de un Funcionario en base al Id
     * de una audiencia
     * @param idAudiencia
     * @param idPersona
     * @param esFuncionario
     * @return
     */
	List<NotificacionDTO> consultaNotificaciones(Long idAudiencia,
			Long idPersona, Boolean esFuncionario) throws NSJPNegocioException ;

}
