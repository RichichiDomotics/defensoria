package mx.gob.segob.nsjp.dto.catalogo;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

public class CatAudienciaDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1259808608634753271L;
	private Long catAudienciaId;
	private String descripcion;
	private Boolean bActivo;
	
	public CatAudienciaDTO(){}
	
	public CatAudienciaDTO(Long idCatAudiencia) {
    	super();
	}
	
	public Long getCatAudienciaId() {
		return catAudienciaId;
	}
	public void setCatAudienciaId(Long catAudienciaId) {
		this.catAudienciaId = catAudienciaId;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Boolean getbActivo() {
		return bActivo;
	}
	public void setbActivo(Boolean bActivo) {
		this.bActivo = bActivo;
	}
}
