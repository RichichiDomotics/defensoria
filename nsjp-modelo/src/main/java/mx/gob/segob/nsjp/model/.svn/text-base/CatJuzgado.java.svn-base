package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CatJuzgado" )
public class CatJuzgado implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5335532493151790332L;
	long catJuzgadoId;
	String cDescripcion;
	String cDireccion;
	
		
	public CatJuzgado() {
		super();
	}
	
	public CatJuzgado(long id) {
		this.catJuzgadoId = id;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "catJuzgado_id", unique = true, nullable = false, precision = 18, scale = 0)
	public long getCatJuzgadoId() {
		return catJuzgadoId;
	}
	public void setCatJuzgadoId(long catJuzgadoId) {
		this.catJuzgadoId = catJuzgadoId;
	}
	
	@Column(name = "cDescripcion", nullable = false, length = 50)
	public String getcDescripcion() {
		return cDescripcion;
	}
	public void setcDescripcion(String cDescripcion) {
		this.cDescripcion = cDescripcion;
	}
	
	@Column(name = "cDireccion", nullable = false, length = 100)
	public String getcDireccion() {
		return cDireccion;
	}
	public void setcDireccion(String cDireccion) {
		this.cDireccion = cDireccion;
	}
	
	
}
