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

/**
 * Entity para DescriptorAudiencia.
 * @version 1.0
 * @author AAAV
 */
@Entity
@Table(name = "DescriptorAudiencia")
public class DescriptorAudiencia implements java.io.Serializable {

	private static final long serialVersionUID = -7456863222649537583L;
	private Audiencia audiencia;
	private String mensaje;
	private Long resultado;
	private byte[] paths;
	private byte[] archivo;	
	private Long descriptorAudienciaId;
	
    /** minimal constructor */
    public DescriptorAudiencia() {
		// TODO Auto-generated constructor stub
	}
    
    public DescriptorAudiencia(Long descriptorAudienciaId) {
        this.descriptorAudienciaId = descriptorAudienciaId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DescriptorAudiencia_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getDescriptorAudienciaId() {
		return descriptorAudienciaId;
	}
	public void setDescriptorAudienciaId(Long descriptorAudienciaId) {
		this.descriptorAudienciaId = descriptorAudienciaId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Audiencia_id")    
	public Audiencia getAudiencia() {
		return audiencia;
	}
	public void setAudiencia(Audiencia audiencia) {
		this.audiencia = audiencia;
	}
	@Column(name = "sMensaje", nullable = true, length = 150)
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	@Column(name = "iResultado", nullable = true)
	public Long getResultado() {
		return resultado;
	}
	public void setResultado(Long resultado) {
		this.resultado = resultado;
	}
	@Column(name = "bPaths", nullable = false)
	public byte[] getPaths() {
		return paths;
	}
	public void setPaths(byte[] paths) {
		this.paths = paths;
	}
	@Column(name = "bArchivo", nullable = false)
	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
}