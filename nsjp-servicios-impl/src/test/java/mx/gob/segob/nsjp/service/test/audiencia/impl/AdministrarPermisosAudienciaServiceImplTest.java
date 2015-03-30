/**
* Nombre del Programa : ActualizarCarpetaDeInvestigacionServiceImplTest.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 28/07/2011
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
package mx.gob.segob.nsjp.service.test.audiencia.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.service.audiencia.AdministrarPermisosAudienciaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Clase de servicios de pruebas para actualizar la carpeta de investigacion enviada por 
 * procuraduria 
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
public class AdministrarPermisosAudienciaServiceImplTest extends
BaseTestServicios<AdministrarPermisosAudienciaService>{
	
	public void testConsulta(){
		Long idAudiencia = 136L;
		try {
			mx.gob.segob.nsjp.dto.audiencia.AudienciaJAVSTransporteWSDTO audienciaJAVSTransporteWSDTO = service.consultarParametrosConsultaAudienciaJavs(idAudiencia);
			logger.info("Prueba: " + audienciaJAVSTransporteWSDTO.getBytesRegistroMaestroJVL());
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
