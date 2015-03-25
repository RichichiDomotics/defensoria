/**
 * Nombre del Programa : NotaExpedienteDTO.java
 * Autor                            : rgama
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 07-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */package mx.gob.segob.nsjp.dto.expediente;

import java.util.Date;
import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;

/**
 * Objeto de transferencia para una nota asociada a un expediente.
 * @author rgama
 */
public class NotaExpedienteDTO extends GenericDTO {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5787062499767645263L;
	private Long idNota;
    private String nombreNota;
    private Date fechaCreacion;
    private String descripcion;
    private Date fechaActualizacion;
    private ExpedienteDTO expediente;
    private FuncionarioDTO funcionario;
    
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ExpedienteDTO getExpediente() {
        return expediente;
    }

    public void setExpediente(ExpedienteDTO expediente) {
        this.expediente = expediente;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdNota() {
        return idNota;
    }

    public void setIdNota(Long idNota) {
        this.idNota = idNota;
    }

    public String getNombreNota() {
        return nombreNota;
    }

    public void setNombreNota(String nombreNota) {
        this.nombreNota = nombreNota;
    }

	/**
	 * Método de acceso al campo funcionario.
	 * @return El valor del campo funcionario
	 */
	public FuncionarioDTO getFuncionario() {
		return funcionario;
	}

	/**
	 * Asigna el valor al campo funcionario.
	 * @param funcionario el valor funcionario a asignar
	 */
	public void setFuncionario(FuncionarioDTO funcionario) {
		this.funcionario = funcionario;
	}
}
