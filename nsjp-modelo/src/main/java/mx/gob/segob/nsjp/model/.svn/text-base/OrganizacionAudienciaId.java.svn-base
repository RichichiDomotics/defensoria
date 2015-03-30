/**
* Nombre del Programa			: OrganizacionAudienciaId.java
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

import javax.persistence.Column;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class OrganizacionAudienciaId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8293798098222779063L;
	private Long audienciaId;
	private Long organizacionId;
	
	/** default constructor */
	public OrganizacionAudienciaId() {
	}

	/** full constructor */
	public OrganizacionAudienciaId(Long audienciaId, Long organizacionId) {
		this.audienciaId = audienciaId;
		this.organizacionId = organizacionId;
	}
	
	/**
	 * Método de acceso al campo audienciaId.
	 * @return El valor del campo audienciaId
	 */
	@Column(name = "audiencia_id", nullable = false, precision = 18, scale = 0)
	public Long getAudienciaId() {
		return audienciaId;
	}
	
	/**
	 * Asigna el valor al campo audienciaId.
	 * @param audienciaId el valor audienciaId a asignar
	 */
	public void setAudienciaId(Long audienciaId) {
		this.audienciaId = audienciaId;
	}
	
	/**
	 * Método de acceso al campo organizacionId.
	 * @return El valor del campo organizacionId
	 */
	@Column(name = "Organizacion_id", nullable = false, precision = 18, scale = 0)
	public Long getOrganizacionId() {
		return organizacionId;
	}
	
	/**
	 * Asigna el valor al campo organizacionId.
	 * @param organizacionId el valor organizacionId a asignar
	 */
	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other)){
			return true;
		}else if ((other == null)){
			return false;			
		}else if (!(other instanceof OrganizacionAudienciaId)){
			return false;	
		}
		OrganizacionAudienciaId castOther = (OrganizacionAudienciaId) other;

		return ((this.getAudienciaId() == castOther.getAudienciaId()) || (this
				.getAudienciaId() != null
				&& castOther.getAudienciaId() != null && this.getAudienciaId().equals(
				castOther.getAudienciaId())))
				&& ((this.getOrganizacionId() == castOther.getOrganizacionId()) || (this
						.getOrganizacionId() != null
						&& castOther.getOrganizacionId() != null && this
						.getOrganizacionId()
						.equals(castOther.getOrganizacionId())));
	}

}
