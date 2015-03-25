package mx.gob.segob.nsjp.dto.audiencia.pjsiaj;

import java.util.Date;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;

public class SolicitudWSDTO extends GenericWSDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1517491006746881491L;
	private long solicitudId;
    private int tipoPromocion;
    private String solicitudRespuesta;
    private Date fechaRespuesta;
    private int promovente;
	/**
	 * @return the solicitudId
	 */
	public long getSolicitudId() {
		return solicitudId;
	}
	/**
	 * @param solicitudId the solicitudId to set
	 */
	public void setSolicitudId(long solicitudId) {
		this.solicitudId = solicitudId;
	}
	/**
	 * @return the tipoPromocion
	 */
	public int getTipoPromocion() {
		return tipoPromocion;
	}
	/**
	 * @param tipoPromocion the tipoPromocion to set
	 */
	public void setTipoPromocion(int tipoPromocion) {
		this.tipoPromocion = tipoPromocion;
	}
	/**
	 * @return the solicitudRespuesta
	 */
	public String getSolicitudRespuesta() {
		return solicitudRespuesta;
	}
	/**
	 * @param solicitudRespuesta the solicitudRespuesta to set
	 */
	public void setSolicitudRespuesta(String solicitudRespuesta) {
		this.solicitudRespuesta = solicitudRespuesta;
	}
	/**
	 * @return the fechaRespuesta
	 */
	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}
	/**
	 * @param fechaRespuesta the fechaRespuesta to set
	 */
	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}
	/**
	 * @return the promovente
	 */
	public int getPromovente() {
		return promovente;
	}
	/**
	 * @param promovente the promovente to set
	 */
	public void setPromovente(int promovente) {
		this.promovente = promovente;
	}
}
