
package mx.gob.segob.nsjp.ws.cliente.estatusmandamiento;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.2
 * 2012-09-03T16:02:35.821-05:00
 * Generated source version: 2.4.2
 */

@WebFault(name = "NSJPNegocioException", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/")
public class NSJPNegocioException_Exception extends Exception {
    
    private mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.NSJPNegocioException nsjpNegocioException;

    public NSJPNegocioException_Exception() {
        super();
    }
    
    public NSJPNegocioException_Exception(String message) {
        super(message);
    }
    
    public NSJPNegocioException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public NSJPNegocioException_Exception(String message, mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.NSJPNegocioException nsjpNegocioException) {
        super(message);
        this.nsjpNegocioException = nsjpNegocioException;
    }

    public NSJPNegocioException_Exception(String message, mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.NSJPNegocioException nsjpNegocioException, Throwable cause) {
        super(message, cause);
        this.nsjpNegocioException = nsjpNegocioException;
    }

    public mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.NSJPNegocioException getFaultInfo() {
        return this.nsjpNegocioException;
    }
}
