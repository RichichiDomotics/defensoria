/**
 * Nombre del Programa : AdministrarPermisosAudienciaService.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 06 Jun 2012
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato del servicio para administrar Permisos de Audiencias de usuarios externos. 
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

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaJAVSTransporteWSDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoWSDTO;

/**
 *  Contrato del servicio para administrar Permisos de Audiencias de usuarios externos.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public interface AdministrarPermisosAudienciaService {

	/**
	 * Genera la solicitud de permiso de audiencia del funcionario externo
	 *  
	 * @param funExtWSDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long generarPermisoUsuarioExterno(FuncionarioExternoWSDTO funExtWSDTO)
			throws NSJPNegocioException;
	
	/**
	 * Consultar los datos necesarios para el funcionamiento del Applet
	 * @param numeroAudiencia
	 * @return
	 * @throws NSJPNegocioException
	 */
	AudienciaJAVSTransporteWSDTO consultarParametrosConsultaAudienciaJavs(Long numeroAudiencia)
			throws NSJPNegocioException;
	
	/**
	 * Consulta la solicitud de permiso de audiencia del funcionario externo
	 *  
	 * @param funExtWSDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	AudienciaJAVSTransporteWSDTO consultarPermisoAudienciaUsuarioExterno(FuncionarioExternoWSDTO funExtWSDTO)
			throws NSJPNegocioException;

	/**
	 * Consulta de los directorios de consulta JAVS
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	byte[] consultarDirectoriosConsultaJAVS(Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Consulta del registro maestro del JVL
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	byte[] consultarRegistroMaestroJVL(Long audienciaId) throws NSJPNegocioException;

	/**
	 * Datos de conexión al servidor JAVS
	 * @param audienciaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	String datosConexion(Long audienciaId) throws NSJPNegocioException;
}