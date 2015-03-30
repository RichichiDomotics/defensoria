/**
* Nombre del Programa : 			CatAreasNegocio.java
* Autor               : AlejandroGA
* Compania            : Ultrasist
* Proyecto            : NSJP                    Fecha: 17/05/2012
* Marca de cambio     : N/A
* Descripcion General : Entidad de la tabla CatAreasNegocio
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
package mx.gob.segob.nsjp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

 /**
 * Catalogo de areas de negocio.
 * Fecha: 17/05/2012
 * @version 1.0
 * @author AlejandroGA
 * 
 */
@Entity
@Table(name = "CatAreasNegocio")
public class CatAreasNegocio implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -290264627291853704L;
	private Long catAreasNegocioId;
	private String nombreCatAN;
	private Boolean esUIE;
	private ConfInstitucion confInstitucion;
	
	
	/**
	 * @return the catAreasNegocioId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CatAreasNegocio_id", unique = true, nullable = false, precision = 18, scale = 0)
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
	 * @return nombre del area de negocio
	 */
	@Column(name = "cNombre", unique = true, nullable = false, length = 150)
	public String getNombreCatAN() {
		return nombreCatAN;
	}
	
	/**
	 * @param nombreCatAN the nombreCatAN to set
	 */
	public void setNombreCatAN(String nombreCatAN) {
		this.nombreCatAN = nombreCatAN;
	}
	
	/**
	 * @return the esUIE
	 */
	@Column(name = "bEsEspecializada", nullable = false, precision = 1, scale = 0)
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ConfInstitucion_id", nullable = false)
	public ConfInstitucion getConfInstitucion() {
		return confInstitucion;
	}

	/**
	 * @param confInstitucion the confInstitucion to set
	 */
	public void setConfInstitucion(ConfInstitucion confInstitucion) {
		this.confInstitucion = confInstitucion;
	}
	
}
