/**
 * Nombre del Programa : PermisoAudienciaDTO.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 01 Jun 2012
 * Marca de cambio        : N/A
 * Descripcion General    : Objeto de Transferencia para el permiso de la audiencia.
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dto.audiencia;

import java.util.Date;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Objeto de Transferencia para el permiso de la audiencia.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public class PermisoAudienciaDTO extends GenericDTO{
	
	private static final long serialVersionUID = -3218565837376841356L;
	
	private Long permisoAudienciaId;
	private Boolean esVigente;
	private Boolean esExterno;
    private Date fechaSolicitud;
    private Date fechaAsignacion;
    private AudienciaDTO audiencia;
    private CatEstadoPermisoDTO catEstadoPermiso;
    private UsuarioDTO usuario;
    private FuncionarioExternoDTO funcionarioExterno;
    private UsuarioDTO usuarioAsignador;
    private ConfInstitucionDTO confInstitucion;

	// Property accessors
    public Long getPermisoAudienciaId() {
		return permisoAudienciaId;
	}
	public void setPermisoAudienciaId(Long permisoAudienciaId) {
		this.permisoAudienciaId = permisoAudienciaId;
	}

	public Boolean getEsVigente() {
		return esVigente;
	}
	public void setEsVigente(Boolean esVigente) {
		this.esVigente = esVigente;
	}

	public Boolean getEsExterno() {
		return esExterno;
	}
	public void setEsExterno(Boolean esExterno) {
		this.esExterno = esExterno;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}
	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public CatEstadoPermisoDTO getCatEstadoPermiso() {
		return catEstadoPermiso;
	}
	public void setCatEstadoPermiso(CatEstadoPermisoDTO catEstadoPermiso) {
		this.catEstadoPermiso = catEstadoPermiso;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public FuncionarioExternoDTO getFuncionarioExterno() {
		return funcionarioExterno;
	}
	
	public void setFuncionarioExterno(FuncionarioExternoDTO funcionarioExterno) {
		this.funcionarioExterno = funcionarioExterno;
	}

    public UsuarioDTO getUsuarioAsignador() {
		return usuarioAsignador;
	}
	public void setUsuarioAsignador(UsuarioDTO usuarioAsignador) {
		this.usuarioAsignador = usuarioAsignador;
	}

    public AudienciaDTO getAudiencia() {
		return audiencia;
	}
	public void setAudiencia(AudienciaDTO audiencia) {
		this.audiencia = audiencia;
	}
	
	public ConfInstitucionDTO getConfInstitucion() {
		return confInstitucion;
	}
	public void setConfInstitucion(ConfInstitucionDTO confInstitucion) {
		this.confInstitucion = confInstitucion;  
	}
}
