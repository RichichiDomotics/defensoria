/**
 * 
 */
package mx.gob.segob.nsjp.comun.excepcion;

/**
 * @author vaguirre
 * 
 */
public class NSJPSistemaException extends RuntimeException {

	private static final long serialVersionUID = 6425549276038816737L;

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NSJPSistemaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
