package mx.gob.segob.nsjp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PermisoSolicitudId implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4530044345134935829L;
	private Long solicitudId;
	private Long iclaveFuncionario;

	public PermisoSolicitudId(){}

	public PermisoSolicitudId(Long solicitudId, Long iclaveFuncionario) {
		super();
		this.solicitudId = solicitudId;
		this.iclaveFuncionario = iclaveFuncionario;
	}

	@Column(name="Solicitud_id", nullable=false, precision=18, scale=0)
	public Long getSolicitudId() {
		return solicitudId;
	}
	public void setSolicitudId(Long solicitudId) {
		this.solicitudId = solicitudId;
	}

	@Column(name="iClaveFuncionario", nullable=false, precision=18, scale=0)
	public Long getIclaveFuncionario() {
		return iclaveFuncionario;
	}

	public void setIclaveFuncionario(Long iclaveFuncionario) {
		this.iclaveFuncionario = iclaveFuncionario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((iclaveFuncionario == null) ? 0 : iclaveFuncionario
						.hashCode());
		result = prime * result
				+ ((solicitudId == null) ? 0 : solicitudId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PermisoSolicitudId)) {
			return false;
		}
		PermisoSolicitudId other = (PermisoSolicitudId) obj;
		if (iclaveFuncionario == null) {
			if (other.iclaveFuncionario != null) {
				return false;
			}
		} else if (!iclaveFuncionario.equals(other.iclaveFuncionario)) {
			return false;
		}
		if (solicitudId == null) {
			if (other.solicitudId != null) {
				return false;
			}
		} else if (!solicitudId.equals(other.solicitudId)) {
			return false;
		}
		return true;
	}
}
