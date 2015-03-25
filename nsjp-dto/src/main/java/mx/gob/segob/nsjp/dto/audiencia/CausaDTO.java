package mx.gob.segob.nsjp.dto.audiencia;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

public class CausaDTO extends GenericDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7465293037194850273L;
	private long causaId;
	private String cCausaPenalIdPJ;
	public long getCausaId() {
		return causaId;
	}
	public void setCausaId(long causaId) {
		this.causaId = causaId;
	}
	public String getcCausaPenalIdPJ() {
		return cCausaPenalIdPJ;
	}
	public void setcCausaPenalIdPJ(String cCausaPenalIdPJ) {
		this.cCausaPenalIdPJ = cCausaPenalIdPJ;
	}
	
	

}
