/**
 * 
 */
package mx.gob.segob.nsjp.dto.catalogo;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;

/**
 * DTO de intercambio entre sistemas para transportar los datos básicos del Catalogo CatDistrito.
 * 
 * @author rgama
 * @version 1.0
 */
public class CatDistritoWSDTO extends GenericWSDTO {

	private static final long serialVersionUID = 5276062969352314700L;
	private Long catDistritoId;
    private String claveDistrito;
    private String nombreDist;
    
	public CatDistritoWSDTO(Long catDistritoId, String claveDistrito,
			String nombreDist) {
		super();
		this.catDistritoId = catDistritoId;
		this.claveDistrito = claveDistrito;
		this.nombreDist = nombreDist;
	}

	public CatDistritoWSDTO() {
	}

	/**
	 * @return the catDistritoId
	 */
	public Long getCatDistritoId() {
		return catDistritoId;
	}

	/**
	 * @param catDistritoId the catDistritoId to set
	 */
	public void setCatDistritoId(Long catDistritoId) {
		this.catDistritoId = catDistritoId;
	}

	/**
	 * @return the claveDistrito
	 */
	public String getClaveDistrito() {
		return claveDistrito;
	}

	/**
	 * @param claveDistrito the claveDistrito to set
	 */
	public void setClaveDistrito(String claveDistrito) {
		this.claveDistrito = claveDistrito;
	}

	/**
	 * @return the nombreDist
	 */
	public String getNombreDist() {
		return nombreDist;
	}

	/**
	 * @param nombreDist the nombreDist to set
	 */
	public void setNombreDist(String nombreDist) {
		this.nombreDist = nombreDist;
	}

}
