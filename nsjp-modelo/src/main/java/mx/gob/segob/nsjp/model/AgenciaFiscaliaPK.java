package mx.gob.segob.nsjp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class AgenciaFiscaliaPK implements Serializable {
	private Long catDiscriminantePadreId;
	private Long catDiscriminanteId;
	
	@Column(name="catDiscriminantePadre_id", nullable=true, unique=false, precision=18, scale=0)
	public Long getCatDiscriminantePadreId() {
		return catDiscriminantePadreId;
	}
	public void setCatDiscriminantePadreId(Long catDiscriminantePadreId) {
		this.catDiscriminantePadreId = catDiscriminantePadreId;
	}
	
	@Column(name="catDiscriminante_id", nullable=false, unique=false, precision=18, scale=0)
	public Long getCatDiscriminanteId() {
		return catDiscriminanteId;
	}
	public void setCatDiscriminanteId(Long catDiscriminanteId) {
		this.catDiscriminanteId = catDiscriminanteId;
	}
}
