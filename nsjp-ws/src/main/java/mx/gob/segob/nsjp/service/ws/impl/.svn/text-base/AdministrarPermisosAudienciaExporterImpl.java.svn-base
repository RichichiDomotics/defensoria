/**
* Nombre del Programa : 	AdministrarPermisosAudienciaExporterImpl.java
* Autor                            : AAAV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 03/11/2011
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
package mx.gob.segob.nsjp.service.ws.impl;

import javax.jws.WebService;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaJAVSTransporteWSDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoWSDTO;
import mx.gob.segob.nsjp.service.audiencia.AdministrarPermisosAudienciaService;
import mx.gob.segob.nsjp.service.ws.AdministrarPermisosAudienciaExporter;
import mx.gob.segob.nsjp.service.ws.infra.impl.ApplicationContextAwareWSNSJPImpl;

/**
 * Implementación para exponer los servicios como web services.
 * @version 1.0
 * @author AAAV
 *
 */
@WebService(endpointInterface = "mx.gob.segob.nsjp.service.ws.AdministrarPermisosAudienciaExporter")
public class AdministrarPermisosAudienciaExporterImpl implements
	AdministrarPermisosAudienciaExporter {

	private AdministrarPermisosAudienciaService administrarPermisosService; 

	@Override
	public Long generarPermisoUsuarioExterno
			(FuncionarioExternoWSDTO funExtWSDTO) throws NSJPNegocioException {
		
		administrarPermisosService = (AdministrarPermisosAudienciaService) ApplicationContextAwareWSNSJPImpl.springApplicationContext.
											getBean("administrarPermisosAudienciaService");
				
		return administrarPermisosService.generarPermisoUsuarioExterno(funExtWSDTO);
	}
	
	@Override
	public AudienciaJAVSTransporteWSDTO consultarPermisoAudienciaUsuarioExterno
			(FuncionarioExternoWSDTO funExtWSDTO) throws NSJPNegocioException {
		
		administrarPermisosService = (AdministrarPermisosAudienciaService) ApplicationContextAwareWSNSJPImpl.springApplicationContext.
											getBean("administrarPermisosAudienciaService");
				
		return administrarPermisosService.consultarPermisoAudienciaUsuarioExterno(funExtWSDTO);
	}
	
	@Override
	public byte[] consultarDirectoriosConsultaJAVS
			(Long audienciaId) throws NSJPNegocioException {
		
		administrarPermisosService = (AdministrarPermisosAudienciaService) ApplicationContextAwareWSNSJPImpl.springApplicationContext.
											getBean("administrarPermisosAudienciaService");
				
		return administrarPermisosService.consultarDirectoriosConsultaJAVS(audienciaId);
	}

	@Override
	public byte[] consultarRegistroMaestroJVL(Long audienciaId)
			throws NSJPNegocioException {
		
		administrarPermisosService = (AdministrarPermisosAudienciaService) ApplicationContextAwareWSNSJPImpl.springApplicationContext.
											getBean("administrarPermisosAudienciaService");
				
		return administrarPermisosService.consultarRegistroMaestroJVL(audienciaId);
	}
	
	@Override
	public String datosConexion(Long audienciaId)
			throws NSJPNegocioException {
		
		administrarPermisosService = (AdministrarPermisosAudienciaService) ApplicationContextAwareWSNSJPImpl.springApplicationContext.
											getBean("administrarPermisosAudienciaService");
				
		return administrarPermisosService.datosConexion(audienciaId);
	}
	
	@Override
	public AudienciaJAVSTransporteWSDTO consultarParametrosConsultaAudienciaJavs(Long audienciaId)
			throws NSJPNegocioException {
		
		administrarPermisosService = (AdministrarPermisosAudienciaService) ApplicationContextAwareWSNSJPImpl.springApplicationContext.
											getBean("administrarPermisosAudienciaService");
				
		return administrarPermisosService.consultarParametrosConsultaAudienciaJavs(audienciaId);
	}
}
