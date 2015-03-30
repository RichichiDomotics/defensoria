package mx.gob.segob.nsjp.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity para CatEstadoPermiso.
 * @version 1.0
 * @author AAAV
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CatEstadoPermiso")
public class CatEstadoPermiso implements java.io.Serializable {

	private Long catEstadoPermisoId;
	private String estatus;

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CatEstadoPermiso_id", unique = true, nullable = false, precision = 18, scale = 0)    			
	public Long getCatEstadoPermisoId() {
		return catEstadoPermisoId;
	}
	public void setCatEstadoPermisoId(Long iEstado) {
		this.catEstadoPermisoId = iEstado;
	}
	
	@Column(name = "cEstatus", length = 30, nullable = false)
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
}
