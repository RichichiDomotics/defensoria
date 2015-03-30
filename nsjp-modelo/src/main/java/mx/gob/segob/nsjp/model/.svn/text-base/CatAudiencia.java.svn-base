package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CatAudiencia")
public class CatAudiencia implements java.io.Serializable {

	private Long catAudienciaId;
	private String descripcion;
	private Boolean bActivo;
	
	public CatAudiencia(){
	}
	
	public CatAudiencia(Long id){
		this.catAudienciaId = id;
	}
	
	@Id
    @Column(name = "catAudiencia_id", unique = true, nullable = false, precision = 18, scale = 0)
    public Long getCatAudienciaId() {
		return catAudienciaId;
	}
	public void setCatAudienciaId(Long catAudienciaId) {
		this.catAudienciaId = catAudienciaId;
	}
	
	@Column(name = "cDescripcion", nullable = false, length = 100)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name = "bActivo", precision = 1, scale = 0)
	public Boolean getbActivo() {
		return bActivo;
	}
	public void setbActivo(Boolean bActivo) {
		this.bActivo = bActivo;
	}
	
	
}
