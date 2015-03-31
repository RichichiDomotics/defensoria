/**
* Nombre del Programa : ConsultarDistritosServiceWSExporterImpl.java
* Autor                            : rgama
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 02/01/2012
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

import java.util.List;

import javax.jws.WebService;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoWSDTO;
import mx.gob.segob.nsjp.service.catalogo.ConsultarDistritosServiceWS;
import mx.gob.segob.nsjp.service.ws.ConsultarDistritosServiceWSExporter;
import mx.gob.segob.nsjp.service.ws.infra.impl.ApplicationContextAwareWSNSJPImpl;

/**
 * Implementación para exponer los servicios como web services.
 * @version 1.0
 * @author rgama
 *
 */
@WebService(endpointInterface = "mx.gob.segob.nsjp.service.ws.ConsultarDistritosServiceWSExporter")
public class ConsultarDistritosServiceWSExporterImpl implements ConsultarDistritosServiceWSExporter {

	
	private ConsultarDistritosServiceWS  consultarDistritoService;

	@Override
	public List<CatDistritoWSDTO> consultarDistritos()
			throws NSJPNegocioException {	
		consultarDistritoService = (ConsultarDistritosServiceWS) ApplicationContextAwareWSNSJPImpl.springApplicationContext.getBean(
		        "consultarDistritosServiceWS");
		
		return consultarDistritoService.consultarDistritos();
	}

	
	
}

