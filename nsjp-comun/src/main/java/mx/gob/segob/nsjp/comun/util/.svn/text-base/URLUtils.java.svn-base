/**
* Nombre del Programa 		: URLUtils.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 22 Aug 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 							Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 							Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.comun.util;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class URLUtils {
	
	private static final String REGEX_URL_WSDL_IP = "https?://[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}:[0-9]{1,5}/[a-zA-Z0-9-_]+";
	private static final String REGEX_URL_WSDL_DOMAIN = "https?://[a-zA-Z0-9-_\\.]+:[0-9]{1,5}/[a-zA-Z0-9-_]+";
	
	/**
	 * M&eacute;todo que lleva a cabo la validaci&oacute;n de las URL's utilizadas para llevar a cabo la 
	 * conexi&oacute;n a los distintos web services utilizados por la aplicaci&oacute;n
	 * @param url - El URL que se va a validar si se encuentra bien formado.
	 * @return valida - Bandera que especifica si el URL se encuentra bien formado o no. 
	 */
	public static boolean validaURLWSDL (String url){
		boolean valida = false;
		if (url != null && !url.trim().isEmpty()){
			valida = url.matches(REGEX_URL_WSDL_IP);
			if (!valida){
				valida = url.matches(REGEX_URL_WSDL_DOMAIN);
			}
		}
		return valida;
	}

}
