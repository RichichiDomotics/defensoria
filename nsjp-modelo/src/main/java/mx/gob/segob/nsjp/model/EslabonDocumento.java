package mx.gob.segob.nsjp.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * EslabonOcupacion entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EslabonDocumento")
public class EslabonDocumento implements java.io.Serializable {

	// Fields

	private EslabonDocumentoId id;
	private Eslabon eslabon;
	private Documento doumento;	

	// Constructors

	/** default constructor */
	public EslabonDocumento() {
	}

	/** full constructor */
	public EslabonDocumento(EslabonDocumentoId id,
			Eslabon eslabon, Documento doumento) {
		this.id = id;
		this.eslabon = eslabon;
		this.doumento = doumento;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "eslabonId", column = @Column(name = "Eslabon_id", nullable = false, precision = 18, scale = 0)),
		@AttributeOverride(name = "documentoId", column = @Column(name = "Documento_id", nullable = false, precision = 18, scale = 0)) })
	public EslabonDocumentoId getId() {
		return this.id;
	}

	public void setId(EslabonDocumentoId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Eslabon_id", nullable = false, insertable = false, updatable = false)
	public Eslabon getEslabon() {
		return this.eslabon;
	}

	public void setEslabon(Eslabon eslabon) {
		this.eslabon = eslabon;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Documento_id", nullable = false, insertable = false, updatable = false)
	public Documento getDocumento() {
		return this.doumento;
	}

	public void setDocumento(Documento doumento) {
		this.doumento = doumento;
	}
	
}
