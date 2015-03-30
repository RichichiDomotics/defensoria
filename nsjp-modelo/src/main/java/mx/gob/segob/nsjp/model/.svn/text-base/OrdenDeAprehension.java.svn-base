package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * OrdenDeAprehension entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OrdenDeAprehension")
public class OrdenDeAprehension implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7197124526182459810L;
	private Long ordenDeAprehensionId;
	private Mandamiento mandamiento;
	private NumeroExpediente numeroExpediente;
	private Involucrado involucrado;
	private Audiencia audiencia;
	private Boolean esCumplida;
	private Boolean sePresentaVoluntariamente;
	private Boolean validarDocumentoDigital;
	// Constructors

	/** default constructor */
	public OrdenDeAprehension() {
	}

	/** full constructor */
	public OrdenDeAprehension(Long ordenDeAprehensionId,
			Mandamiento mandamiento, NumeroExpediente numeroExpediente,
			Involucrado involucrado, Audiencia audiencia, Boolean esCumplida) {
		this.ordenDeAprehensionId = ordenDeAprehensionId;
		this.mandamiento = mandamiento;
		this.numeroExpediente = numeroExpediente;
		this.involucrado = involucrado;
		this.audiencia = audiencia;
		this.esCumplida = esCumplida;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OrdenDeAprehension_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getOrdenDeAprehensionId() {
		return this.ordenDeAprehensionId;
	}

	public void setOrdenDeAprehensionId(Long ordenDeAprehensionId) {
		this.ordenDeAprehensionId = ordenDeAprehensionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Mandamiento_id", nullable = false)
	public Mandamiento getMandamiento() {
		return this.mandamiento;
	}

	public void setMandamiento(Mandamiento mandamiento) {
		this.mandamiento = mandamiento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NumeroExpediente_id", nullable = false)
	public NumeroExpediente getNumeroExpediente() {
		return this.numeroExpediente;
	}

	public void setNumeroExpediente(NumeroExpediente numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Involucrado_id", nullable = false)
	public Involucrado getInvolucrado() {
		return this.involucrado;
	}

	public void setInvolucrado(Involucrado involucrado) {
		this.involucrado = involucrado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Audiencia_id", nullable = false)
	public Audiencia getAudiencia() {
		return this.audiencia;
	}

	public void setAudiencia(Audiencia audiencia) {
		this.audiencia = audiencia;
	}

	@Column(name = "esCumplida", nullable = false, precision = 1, scale = 0)
	public Boolean getEsCumplida() {
		return this.esCumplida;
	}
	
	public void setEsCumplida(Boolean esCumplida) {
		this.esCumplida = esCumplida;
	}

	@Column(name = "sePresentaVoluntariamente", nullable = false, precision = 1, scale = 0)
	public Boolean getSePresentaVoluntariamente() {
		return sePresentaVoluntariamente;
	}
	
	public void setSePresentaVoluntariamente(Boolean sePresentaVoluntariamente) {
		this.sePresentaVoluntariamente = sePresentaVoluntariamente;
	}

	/**
	 * Método de acceso al campo validarDocumentoDigital.
	 * @return El valor del campo validarDocumentoDigital
	 */
	@Transient
	public Boolean getValidarDocumentoDigital() {
		return validarDocumentoDigital;
	}

	/**
	 * Asigna el valor al campo validarDocumentoDigital.
	 * @param validarDocumentoDigital el valor validarDocumentoDigital a asignar
	 */
	public void setValidarDocumentoDigital(Boolean validarDocumentoDigital) {
		this.validarDocumentoDigital = validarDocumentoDigital;
	}

}