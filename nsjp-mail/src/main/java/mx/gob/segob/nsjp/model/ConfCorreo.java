package mx.gob.segob.nsjp.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Calidad entity. @author encesarvarga (EnableIT)
 */
@Entity
@Table(name = "confCorreo")
public class ConfCorreo implements java.io.Serializable {

	// Fields

	private Long confCorreoId;
	private String llave;
	private String valor;
	private Long tipoObjeto;

	public ConfCorreo() {

	}

	public ConfCorreo(Long confCorreoId) {
		this.confCorreoId = confCorreoId;
	}

	public ConfCorreo(Long confCorreoId, String llave, String valor,Long tipoObjeto) {
		this.confCorreoId = confCorreoId;
		this.llave = llave;
		this.valor = valor;
		this.tipoObjeto=tipoObjeto;
	}

	@Id
	@Column(name = "confCorreo_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getConfCorreoId() {
		return confCorreoId;
	}

	public void setConfCorreoId(Long confCorreoId) {
		this.confCorreoId = confCorreoId;
	}

	@Column(name = "llave", nullable = true, length = 300)
	public String getllave() {
		return llave;
	}

	public void setllave(String llave) {
		this.llave = llave;
	}

	@Column(name = "valor", nullable = true, length = 300)
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Column(name = "tipo_Objeto",  nullable = false, precision = 18, scale = 0)
	public Long getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(Long tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	
}
