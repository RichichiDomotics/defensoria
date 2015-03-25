package mx.gob.segob.nsjp.dto.audiencia.pjsiaj;

import java.util.Date;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;

public class AudienciaWSDTO extends GenericWSDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3372569945031689439L;
	private String audienciaIdPJ;
    private int tipo;
    private String fechaInicio;
    private String fechaTermino;
    private String lugarSala;
    private String status;
    private int motivoId;
    private int audienciaIdReprogramada;
	/**
	 * @return the audienciaIdPJ
	 */
	public String getAudienciaIdPJ() {
		return audienciaIdPJ;
	}
	/**
	 * @param audienciaIdPJ the audienciaIdPJ to set
	 */
	public void setAudienciaIdPJ(String audienciaIdPJ) {
		this.audienciaIdPJ = audienciaIdPJ;
	}
	/**
	 * @return the tipo
	 */
	public int getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaTermino
	 */
	public String getFechaTermino() {
		return fechaTermino;
	}
	/**
	 * @param fechaTermino the fechaTermino to set
	 */
	public void setFechaTermino(String fechaTermino) {
		this.fechaTermino = fechaTermino;
	}
	/**
	 * @return the lugarSala
	 */
	public String getLugarSala() {
		return lugarSala;
	}
	/**
	 * @param lugarSala the lugarSala to set
	 */
	public void setLugarSala(String lugarSala) {
		this.lugarSala = lugarSala;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the motivoId
	 */
	public int getMotivoId() {
		return motivoId;
	}
	/**
	 * @param motivoId the motivoId to set
	 */
	public void setMotivoId(int motivoId) {
		this.motivoId = motivoId;
	}
	/**
	 * @return the audienciaIdReprogramada
	 */
	public int getAudienciaIdReprogramada() {
		return audienciaIdReprogramada;
	}
	/**
	 * @param audienciaIdReprogramada the audienciaIdReprogramada to set
	 */
	public void setAudienciaIdReprogramada(int audienciaIdReprogramada) {
		this.audienciaIdReprogramada = audienciaIdReprogramada;
	}
}
