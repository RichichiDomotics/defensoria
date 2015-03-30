package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class EslabonDocumentoId implements java.io.Serializable {

	// Fields

	private Long eslabonId;
	private Long documentoId;

	// Constructors

	/** default constructor */
	public EslabonDocumentoId() {
	}

	/** full constructor */
	public EslabonDocumentoId(Long eslabonId, Long documentoId) {
		this.eslabonId = eslabonId;
		this.documentoId = documentoId;
	}

	// Property accessors

	@Column(name = "Eslabon_id", nullable = false, precision = 18, scale = 0)
	public Long getEslabonId() {
		return this.eslabonId;
	}

	public void setEslabonId(Long eslabonId) {
		this.eslabonId = eslabonId;
	}

	@Column(name = "Documento_id", nullable = false, precision = 18, scale = 0)
	public Long getDocumentoId() {
		return this.documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EslabonDocumentoId))
			return false;
		EslabonDocumentoId castOther = (EslabonDocumentoId) other;

		return ((this.getEslabonId() == castOther.getEslabonId()) || (this
				.getEslabonId() != null
				&& castOther.getEslabonId() != null && this.getEslabonId().equals(
				castOther.getEslabonId())))
				&& ((this.getDocumentoId() == castOther.getDocumentoId()) || (this
						.getDocumentoId() != null
						&& castOther.getDocumentoId() != null && this
						.getDocumentoId()
						.equals(castOther.getDocumentoId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEslabonId() == null ? 0 : this.getEslabonId().hashCode());
		result = 37
				* result
				+ (getDocumentoId() == null ? 0 : this.getDocumentoId()
						.hashCode());
		return result;
	}

}