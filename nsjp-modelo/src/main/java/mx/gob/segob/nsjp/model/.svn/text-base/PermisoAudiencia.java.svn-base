/**
* Nombre del Programa 		: PermisoAudiencia.java
* Autor 					: AAAV
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 10 Apr 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Clase de persistencia para la tabla PermisoAudiencia
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.model;

import java.util.Date;

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
 * Entity para PermisoAudiencia.
 * @version 1.0
 * @author AAAV
 */
@Entity
@Table(name = "PermisoAudiencia")
public class PermisoAudiencia implements java.io.Serializable {
	
	private static final long serialVersionUID = 4987939497995724876L;
	private Long permisoAudienciaId;
    private Audiencia audiencia;
	private Boolean esVigente;
	private Boolean esExterno;
    private CatEstadoPermiso catEstadoPermiso;
    private Usuario usuario;
    private FuncionarioExterno funcionarioExterno;
    private Usuario usuarioAsignador;
    private Date fechaSolicitud;
    private Date fechaAsignacion;
    private ConfInstitucion confInstitucion;    
        
    public PermisoAudiencia() {
		super();
	}

	public PermisoAudiencia(Long id) {
        this.permisoAudienciaId = id;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PermisoAudiencia_id", unique = true, nullable = false, precision = 18, scale = 0)
    public Long getPermisoAudienciaId() {
		return permisoAudienciaId;
	}
	public void setPermisoAudienciaId(Long permisoAudiencia_id) {
		this.permisoAudienciaId = permisoAudiencia_id;
	}

	@Column(name = "esVigente", precision = 1, scale = 0)
	public Boolean getEsVigente() {
		return esVigente;
	}
	public void setEsVigente(Boolean esVigente) {
		this.esVigente = esVigente;
	}

	@Column(name = "esExterno", precision = 1, scale = 0)
	public Boolean getEsExterno() {
		return esExterno;
	}
	public void setEsExterno(Boolean esExterno) {
		this.esExterno = esExterno;
	}

	@Column(name = "dFechaSolicitud", length = 23)
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	@Column(name = "dFechaAsignacion", length = 23)
	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}
	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CatEstadoPermiso_id")
	public CatEstadoPermiso getCatEstadoPermiso() {
		return catEstadoPermiso;
	}
	public void setCatEstadoPermiso(CatEstadoPermiso catEstadoPermiso) {
		this.catEstadoPermiso = catEstadoPermiso;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_id")
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FuncionarioExterno_id")
	public FuncionarioExterno getFuncionarioExterno() {
		return funcionarioExterno;
	}
	public void setFuncionarioExterno(FuncionarioExterno funcionarioExterno) {
		this.funcionarioExterno = funcionarioExterno;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuarioAsignador_id")
    public Usuario getUsuarioAsignador() {
		return usuarioAsignador;
	}
	public void setUsuarioAsignador(Usuario usuarioAsignador) {
		this.usuarioAsignador = usuarioAsignador;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Audiencia_id")
    public Audiencia getAudiencia() {
		return audiencia;
	}
	public void setAudiencia(Audiencia audiencia) {
		this.audiencia = audiencia;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ConfInstitucion_id")
    public ConfInstitucion getConfInstitucion() {
		return confInstitucion;
	}
	public void setConfInstitucion(ConfInstitucion confInstitucion) {
		this.confInstitucion = confInstitucion;  
	}
}