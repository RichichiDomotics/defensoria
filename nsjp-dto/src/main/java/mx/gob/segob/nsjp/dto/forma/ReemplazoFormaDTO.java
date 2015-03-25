/**
* Nombre del Programa : ReemplazoFormaDTO.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 11/07/2012
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
package mx.gob.segob.nsjp.dto.forma;

import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class ReemplazoFormaDTO  extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8225448823010520734L;

	private List<Map<String, String>> parametrosReemplazo;
	
	private boolean isTabla;
	
	private boolean isLista;
	
	private String separador;

	/**
	 * Método de acceso al campo parametrosReemplazo.
	 * @return El valor del campo parametrosReemplazo
	 */
	public List<Map<String, String>> getParametrosReemplazo() {
		return parametrosReemplazo;
	}

	/**
	 * Asigna el valor al campo parametrosReemplazo.
	 * @param parametrosReemplazo el valor parametrosReemplazo a asignar
	 */
	public void setParametrosReemplazo(List<Map<String, String>> parametrosReemplazo) {
		this.parametrosReemplazo = parametrosReemplazo;
	}

	/**
	 * Método de acceso al campo isTabla.
	 * @return El valor del campo isTabla
	 */
	public boolean isTabla() {
		return isTabla;
	}

	/**
	 * Asigna el valor al campo isTabla.
	 * @param isTabla el valor isTabla a asignar
	 */
	public void setTabla(boolean isTabla) {
		this.isTabla = isTabla;
	}

	/**
	 * Método de acceso al campo isLista.
	 * @return El valor del campo isLista
	 */
	public boolean isLista() {
		return isLista;
	}

	/**
	 * Asigna el valor al campo isLista.
	 * @param isLista el valor isLista a asignar
	 */
	public void setLista(boolean isLista) {
		this.isLista = isLista;
	}

	/**
	 * Método de acceso al campo separador.
	 * @return El valor del campo separador
	 */
	public String getSeparador() {
		return separador;
	}

	/**
	 * Asigna el valor al campo separador.
	 * @param separador el valor separador a asignar
	 */
	public void setSeparador(String separador) {
		this.separador = separador;
	}

}
