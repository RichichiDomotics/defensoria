
package mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.1
 * 2011-11-03T20:06:02.104-06:00
 * Generated source version: 2.4.1
 */

@WebFault(name = "NSJPNegocioException", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/")
public class NSJPNegocioException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 153118268197234391L;
	private mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida.NSJPNegocioException nsjpNegocioException;

    public NSJPNegocioException_Exception() {
        super();
    }
    
    public NSJPNegocioException_Exception(String message) {
        super(message);
    }
    
    public NSJPNegocioException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public NSJPNegocioException_Exception(String message, mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida.NSJPNegocioException nsjpNegocioException) {
        super(message);
        this.nsjpNegocioException = nsjpNegocioException;
    }

    public NSJPNegocioException_Exception(String message, mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida.NSJPNegocioException nsjpNegocioException, Throwable cause) {
        super(message, cause);
        this.nsjpNegocioException = nsjpNegocioException;
    }

    public mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida.NSJPNegocioException getFaultInfo() {
        return this.nsjpNegocioException;
    }
}
