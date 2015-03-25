/**
* Nombre del Programa : 			CatAreasNegocioDTO.java
* Autor               : AlejandroGA
* Compania            : Ultrasist
* Proyecto            : NSJP                    Fecha: 21/05/2012
* Marca de cambio     : N/A
* Descripcion General : DTO para las Areas de Negocio
* Programa Dependiente: N/A
* Programa Subsecuente: N/A
* Cond. de ejecucion  : N/A
* Dias de ejecucion   : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor               :N/A
* Compania            :N/A
* Proyecto            :N/A                                 Fecha: N/A
* Modificacion        :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dto.catalogo;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;

/**
 * DTO para el Catalogo de areas de negocio.
 * Fecha: 21/05/2012
 * @version 1.0
 * @author AlejandroGA
 * 
 */
public class CatAreasNegocioDTO extends GenericDTO {

	private static final long serialVersionUID = -1588268463046770574L;
	private Long catAreasNegocioId;
	private String nombreCatAreaNegocio;
	private Boolean esUIE;
	private ConfInstitucionDTO confInstitucion;
	
	
	/**
	 * Constructor default
	 */
	public CatAreasNegocioDTO() {
		super();
	}
	
	
	/**
	 * Constructor minimo
	 * @param catAreasNegocioId
	 */
	public CatAreasNegocioDTO(Long catAreasNegocioId) {
		super();
		this.catAreasNegocioId = catAreasNegocioId;
	}
	
	
	/**
	 * Constructor completo
	 * @param catAreasNegocioId
	 * @param nombreCatAreaNegocio
	 * @param esUIE
	 * @param confInstitucion
	 */
	public CatAreasNegocioDTO(Long catAreasNegocioId,
			String nombreCatAreaNegocio, Boolean esUIE,
			ConfInstitucionDTO confInstitucion) {
		super();
		this.catAreasNegocioId = catAreasNegocioId;
		this.nombreCatAreaNegocio = nombreCatAreaNegocio;
		this.esUIE = esUIE;
		this.confInstitucion = confInstitucion;
	}
	
	
	/**
	 * @return the catAreasNegocioId
	 */
	public Long getCatAreasNegocioId() {
		return catAreasNegocioId;
	}
	
	/**
	 * @param catAreasNegocioId the catAreasNegocioId to set
	 */
	public void setCatAreasNegocioId(Long catAreasNegocioId) {
		this.catAreasNegocioId = catAreasNegocioId;
	}
	
	/**
	 * @return the nombreCatAreaNegocio
	 */
	public String getNombreCatAreaNegocio() {
		return nombreCatAreaNegocio;
	}
	
	/**
	 * @param nombreCatAreaNegocio the nombreCatAreaNegocio to set
	 */
	public void setNombreCatAreaNegocio(String nombreCatAreaNegocio) {
		this.nombreCatAreaNegocio = nombreCatAreaNegocio;
	}
	
	/**
	 * @return the esUIE
	 */
	public Boolean getEsUIE() {
		return esUIE;
	}
	
	/**
	 * @param esUIE the esUIE to set
	 */
	public void setEsUIE(Boolean esUIE) {
		this.esUIE = esUIE;
	}
	
	/**
	 * @return the confInstitucion
	 */
	public ConfInstitucionDTO getConfInstitucion() {
		return confInstitucion;
	}
	
	/**
	 * @param confInstitucion the confInstitucion to set
	 */
	public void setConfInstitucion(ConfInstitucionDTO confInstitucion) {
		this.confInstitucion = confInstitucion;
	}
}
