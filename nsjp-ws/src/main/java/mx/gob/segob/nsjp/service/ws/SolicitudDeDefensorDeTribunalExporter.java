/**
 * 
 */
package mx.gob.segob.nsjp.service.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.detencion.SolicitudDefensorWSDTO;

/**
 * @author Asdrubal
 *
 */
@WebService
public interface SolicitudDeDefensorDeTribunalExporter {
	
	@WebMethod
	public String solicitudDeDefensor(SolicitudDefensorWSDTO solicitud) throws NSJPNegocioException;

}
