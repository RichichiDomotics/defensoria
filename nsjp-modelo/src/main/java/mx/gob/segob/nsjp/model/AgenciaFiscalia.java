package mx.gob.segob.nsjp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="AgenciaFiscalia")
public class AgenciaFiscalia implements Serializable {
	private AgenciaFiscaliaPK agenciaFiscaliaId;
	private Boolean esActivo;
	
	@EmbeddedId
	public AgenciaFiscaliaPK getAgenciaFiscaliaId() {
		return agenciaFiscaliaId;
	}
	public void setAgenciaFiscaliaId(AgenciaFiscaliaPK agenciaFiscaliaId) {
		this.agenciaFiscaliaId = agenciaFiscaliaId;
	}
	
	@Column(name="bEsActivo", nullable=false, precision=1, scale=0)
	public Boolean getEsActivo() {
		return esActivo;
	}
	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}
}
