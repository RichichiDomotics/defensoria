package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * MutilacionCorporal entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MutilacionCorporal" )
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "MutilacionCorporal_id")
public class MutilacionCorporal extends Objeto {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7211565146020650525L;
	private Short cantidad;

	// Constructors

	/** default constructor */
	public MutilacionCorporal() {
	}


	@Column(name = "iCantidad", precision = 4, scale = 0)
	public Short getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Short icantidad) {
		this.cantidad = icantidad;
	}

}