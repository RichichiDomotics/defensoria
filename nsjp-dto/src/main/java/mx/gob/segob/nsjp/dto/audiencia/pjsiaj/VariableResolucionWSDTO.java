package mx.gob.segob.nsjp.dto.audiencia.pjsiaj;

import java.util.Date;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;

public class VariableResolucionWSDTO extends GenericWSDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2198316004653000810L;
	private int consec;
    private int consecImp;
    private String audienciaIdPJ;
    private int resolucionId;
    private int pena;
    private Date fechaInicio;
    private Date fechaFin;
    private float cantidad;
    private int anos;
    private int meses;
    private int dias;
    private String observaciones;
	/**
	 * @return the consec
	 */
	public int getConsec() {
		return consec;
	}
	/**
	 * @param consec the consec to set
	 */
	public void setConsec(int consec) {
		this.consec = consec;
	}
	/**
	 * @return the consecImp
	 */
	public int getConsecImp() {
		return consecImp;
	}
	/**
	 * @param consecImp the consecImp to set
	 */
	public void setConsecImp(int consecImp) {
		this.consecImp = consecImp;
	}
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
	 * @return the resolucionId
	 */
	public int getResolucionId() {
		return resolucionId;
	}
	/**
	 * @param resolucionId the resolucionId to set
	 */
	public void setResolucionId(int resolucionId) {
		this.resolucionId = resolucionId;
	}
	/**
	 * @return the pena
	 */
	public int getPena() {
		return pena;
	}
	/**
	 * @param pena the pena to set
	 */
	public void setPena(int pena) {
		this.pena = pena;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the cantidad
	 */
	public float getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the anos
	 */
	public int getAnos() {
		return anos;
	}
	/**
	 * @param anos the anos to set
	 */
	public void setAnos(int anos) {
		this.anos = anos;
	}
	/**
	 * @return the meses
	 */
	public int getMeses() {
		return meses;
	}
	/**
	 * @param meses the meses to set
	 */
	public void setMeses(int meses) {
		this.meses = meses;
	}
	/**
	 * @return the dias
	 */
	public int getDias() {
		return dias;
	}
	/**
	 * @param dias the dias to set
	 */
	public void setDias(int dias) {
		this.dias = dias;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
