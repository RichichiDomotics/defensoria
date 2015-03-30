/**
* Nombre del Programa 		: CatFormasNotificacion.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 16 Aug 2012
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
package mx.gob.segob.nsjp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
@Entity
@Table(name = "CatFormaNotificacion")
public class CatFormaNotificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8448968526429279474L;
	private Long catFormaNotificacionId;
	private String formaNotificacion;
	private String descripcion;
	private Boolean valida;
	
	/**
	 * Método de acceso al campo catFormaNotificacionId.
	 * @return El valor del campo catFormaNotificacionId
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CatFormaNotificacion_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getCatFormaNotificacionId() {
		return catFormaNotificacionId;
	}
	
	/**
	 * Asigna el valor al campo catFormaNotificacionId.
	 * @param catFormaNotificacionId el valor catFormaNotificacionId a asignar
	 */
	public void setCatFormaNotificacionId(Long catFormaNotificacionId) {
		this.catFormaNotificacionId = catFormaNotificacionId;
	}
	
	/**
	 * Método de acceso al campo formaNotificacion.
	 * @return El valor del campo formaNotificacion
	 */
	@Column(name = "cFormaNotificacion", nullable = false, length = 100)
	public String getFormaNotificacion() {
		return formaNotificacion;
	}
	
	/**
	 * Asigna el valor al campo formaNotificacion.
	 * @param formaNotificacion el valor formaNotificacion a asignar
	 */
	public void setFormaNotificacion(String formaNotificacion) {
		this.formaNotificacion = formaNotificacion;
	}
	
	/**
	 * Método de acceso al campo descripcion.
	 * @return El valor del campo descripcion
	 */
	@Column(name = "cDescripcion", nullable = true, length = 200)
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Asigna el valor al campo descripcion.
	 * @param descripcion el valor descripcion a asignar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * Método de acceso al campo valida.
	 * @return El valor del campo valida
	 */
	@Column(name = "bValida", nullable = false, precision = 1, scale = 0)
	public Boolean isValida() {
		return valida;
	}
	
	/**
	 * Asigna el valor al campo valida.
	 * @param valida el valor valida a asignar
	 */
	public void setValida(Boolean valida) {
		this.valida = valida;
	}
	
	
}
