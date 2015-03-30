/**
* Nombre del Programa			: OrganizacionAudiencia.java
* Autor 						: EdgarTE
* Compania 						: Ultrasist
* Proyecto 						: NSJP 						Fecha: 1 Aug 2012
* Marca de cambio 				: N/A
* Descripcion General 			: Describir el objetivo de la clase de manera breve
* Programa Dependiente 			: N/A
* Programa Subsecuente 			: N/A
* Cond. de ejecucion 			: N/A
* Dias de ejecucion 			: N/A 						Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 				: N/A
* Compania 				: N/A
* Proyecto 				: N/A 								Fecha: N/A
* Modificacion 			: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OrganizacionAudiencia")
public class OrganizacionAudiencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818158165303218060L;
	
	private OrganizacionAudienciaId id;
	private Organizacion organizacion;
	private Audiencia audiencia;
	
	/** default constructor */
	public OrganizacionAudiencia() {
	}

	/** full constructor */
	public OrganizacionAudiencia(OrganizacionAudienciaId id,
			Organizacion organizacion, Audiencia audiencia) {
		this.id = id;
		this.organizacion = organizacion;
		this.audiencia = audiencia;
	}
	
	/**
	 * Método de acceso al campo id.
	 * @return El valor del campo id
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "audienciaId", column = @Column(name = "Audiencia_id", nullable = false, precision = 18, scale = 0)),
			@AttributeOverride(name = "organizacionId", column = @Column(name = "Organizacion_id", nullable = false, precision = 18, scale = 0)) })
	public OrganizacionAudienciaId getId() {
		return id;
	}

	/**
	 * Asigna el valor al campo id.
	 * @param id el valor id a asignar
	 */
	public void setId(OrganizacionAudienciaId id) {
		this.id = id;
	}

	/**
	 * M&eacute;todo de acceso al campo organizacion.
	 * @return El valor del campo organizacion
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Organizacion_id", nullable = false, insertable = false, updatable = false)
	public Organizacion getOrganizacion() {
		return organizacion;
	}
	
	/**
	 * Asigna el valor al campo organizacion.
	 * @param organizacion el valor organizacion a asignar
	 */
	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	
	/**
	 * M&eacute;todo de acceso al campo audiencia.
	 * @return El valor del campo audiencia
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Audiencia_id", nullable = false, insertable = false, updatable = false)
	public Audiencia getAudiencia() {
		return audiencia;
	}
	
	/**
	 * Asigna el valor al campo audiencia.
	 * @param audiencia el valor audiencia a asignar
	 */
	public void setAudiencia(Audiencia audiencia) {
		this.audiencia = audiencia;
	}
}
