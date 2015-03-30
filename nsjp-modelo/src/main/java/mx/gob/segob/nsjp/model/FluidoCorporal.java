package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * FluidoCorporal entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FluidoCorporal")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "FluidoCorporal_id")
public class FluidoCorporal extends Objeto {

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = -6772634101562740696L;
	private Short cantidad;

    // Constructors

    /** default constructor */
    public FluidoCorporal() {
    }


    @Column(name = "iCantidad", precision = 4, scale = 0)
    public Short getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Short icantidad) {
        this.cantidad = icantidad;
    }

}