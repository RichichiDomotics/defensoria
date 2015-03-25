/**
* Nombre del Programa : HTMLUtils.java
* Autor                            : Emigdio Hernández
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 01/06/2011
* Marca de cambio        : N/A
* Descripcion General    : Clase de utilerías para la manipulación y conversión de elementos/caracteres/códigos HTML
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
package mx.gob.segob.nsjp.comun.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.tidy.Tidy;

/**
 * Clase de utilerías para la manipulación y conversión de elementos/caracteres/códigos HTML.
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
public class HTMLUtils {
	
	private static Logger log = Logger.getLogger(HTMLUtils.class);
    private static Log4jPrintWriter log4j = new Log4jPrintWriter(log, Level.ERROR);
	
    /**
     * Convierte los caracteres especiales HTML de una cadena a códigos unicode 
     * @param cadena Cadena HTML fuente
     * @return cadena transformada
     */
    public static String encodeHtmlToXhtml(String cadena) throws NSJPNegocioException  {
    	
    	try {
    		
    		InputStream inputStream = new ByteArrayInputStream (cadena.getBytes());
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Tidy tidy = new Tidy();
            tidy.setShowWarnings(true);
            tidy.setShowErrors(1);
            tidy.setXHTML(true);
            tidy.setDocType("omit");
            tidy.setXHTML(true);
            tidy.setForceOutput(true);
            tidy.setErrout(log4j);
            tidy.parseDOM(inputStream, baos);
            return baos.toString();
    		
		} catch (Exception e) {
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);
		}
    	
	}

    public static void main(String[] args) {
	
    	String cadena = "<body>&oacute;</body>";
    	try {
			System.out.println(HTMLUtils.encodeHtmlToXhtml(cadena));
		} catch (NSJPNegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
