/**
* Nombre del Programa : AdministrarAudienciaJAVSService.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 8 Nov 2011
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

import java.util.Date;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaJAVSTransporteDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Implementación de los servicios que permiten consumir el Cliente 
 * que se conecta a los WS de .net alojados en el servidor de JAVS.
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
public interface AdministrarAudienciaJAVSService {

	/**
	 * Se conecta al Servidor del JAVS para agendar la audiencia.
	 *  
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long agendarAudiencia(Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Se conecta al Servidor del JAVS para reagendar la audiencia.
	 *  
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long reagendarAudiencia(Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Consulta si una audiencia se agendo en JAVS.
	 *  
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */	
	Boolean esJAVS(Long audienciaId) throws NSJPNegocioException;

	/**
	 * Consulta la sala audiencia JAVS asociada a una audiencia.
	 *  
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */	
	String laSalaJAVS(Long audienciaId) throws NSJPNegocioException;

	/**
	 * Consulta los directorios de consulta del servidor JAVS
	 *  
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */		
	String obteniendoPathsJAVS(Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Obtiene el registro maestro del servidor JAVS
	 *  
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	byte[] generandoRegistroMaestroJVL(Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Genera el registro de DescriptorAudiencia
	 * @param audienciaId
	 * @throws NSJPNegocioException
	 */
	Long generandoConglomeradoJAVS(Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Se conecta al Servidor del JAVS para consultar la audiencia.
	 *  
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long consultarAudiencia(Long audienciaId) throws NSJPNegocioException;

	/**
	 * Se conecta al Servidor del JAVS para consultar el estado de la audiencia.
	 *  
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long consultarEstadoAudiencia(Long audienciaId) throws NSJPNegocioException;
	/**
	 * Se conecta al Servidor del JAVS para eliminar la audiencia.
	 *  
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long eliminarAudiencia(Long audienciaId) throws NSJPNegocioException;
	
	
	/**
	 * Permite cambiar el estatus de una audiencia a CANCELADA
	 * @param audienciaId
	 * @return 
	 * @throws NSJPNegocioException 
	 */
	Long cancelarAudiencia(Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Permite cambiar el estatus de una audiencia a CANCELADA
	 * @param audienciaId
	 * @return 
	 * @throws NSJPNegocioException 
	 */
	Long cancelarAudienciaSistema(Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Permite eliminar una audiencia en JAVS
	 * @param audienciaId
	 * @return 
	 * @throws NSJPNegocioException 
	 */	
	boolean cancelarAudienciaJAVS(Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Permite crear un permiso de audiencia
	 * @param audienciaId
	 * @param usuarioDTO
	 * @param fechaHoy
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long generarPermiso(Long audienciaId, UsuarioDTO usuarioDTO, Date fechaHoy)
			throws NSJPNegocioException;

	/**
	 * Permite crear un permiso de audiencia para usuario externo
	 * @param audiencia
	 * @param usuarioDTO
	 * @param ConfInstId
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long generarPermisoExterno(Long audiencia, UsuarioDTO usuarioDTO, Long ConfInstId)
			throws NSJPNegocioException;
	
	/**
	 * Permite verificar el estado de una audiencia
	 * @param audienciaId
	 * @param usuarioDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long verificarPermiso(Long audienciaId, UsuarioDTO usuarioDTO)
			throws NSJPNegocioException;

	/**
	 * Permite verificar el estado de un permiso de audiencia externa
	 * @param audienciaId
	 * @param usuarioDTO
	 * @param confInstDto
	 * @return
	 * @throws NSJPNegocioException
	 */
	public AudienciaJAVSTransporteDTO verificarPermisoExterno(Long audienciaId, UsuarioDTO usuarioDTO, ConfInstitucionDTO confInstDto)
			throws NSJPNegocioException;
	
	/**
	 * Permite consultar el usuario de una audiencia
	 * @param Long audienciaId
	 * @return 
	 * @throws NSJPNegocioException 
	 */	
	public UsuarioDTO consultarUsuarioPorAudienciaId(Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Permite actualizar el Caracter de una audiencia, es decir:
	 *  - Pública
	 *  - Privada
	 * @param audienciaId
	 * @param esPublica
	 * @throws NSJPNegocioException
	 */
	public void actualizaCaracterAudiencia(Long audienciaId, Boolean esPublica) throws NSJPNegocioException;

}
