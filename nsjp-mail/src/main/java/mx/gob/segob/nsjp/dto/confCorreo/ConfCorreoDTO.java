package mx.gob.segob.nsjp.dto.confCorreo;

public class ConfCorreoDTO {

	private Long confCorreoId;
	private String llave;
	private String valor;
	private Long tipoObjeto;

	public ConfCorreoDTO() {
	}

	public ConfCorreoDTO(Long confCorreoId) {
		this.confCorreoId = confCorreoId;
	}

	public ConfCorreoDTO(Long confCorreoId, String llave, String valor) {
		this.confCorreoId = confCorreoId;
		this.llave = llave;
		this.valor = valor;
	}

	public Long getConfCorreoId() {
		return confCorreoId;
	}

	public void setConfCorreoId(Long confCorreoId) {
		this.confCorreoId = confCorreoId;
	}

	public String getLlave() {
		return llave;
	}

	public void setClave(String llave) {
		this.llave = llave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Long getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(Long tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

}
