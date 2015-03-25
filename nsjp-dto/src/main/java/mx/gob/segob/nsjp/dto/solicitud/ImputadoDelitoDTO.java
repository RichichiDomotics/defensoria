package mx.gob.segob.nsjp.dto.solicitud;

import java.util.Date;

public class ImputadoDelitoDTO {

	
//	private int consec_imp;
	private String consec_imp;
//	private short delito;
	private String delito;
//	private short estado_psicofisico;
	private String estado_psicofisico;
//	private Date fecha_delito;
	private String fecha_delito;
//	private short grado_consumacion;
//	private short intencionalidad;
	private String grado_consumacion;
	private String intencionalidad;

	
	public String getConsec_imp() {
		return consec_imp;
	}
	public void setConsec_imp(String consec_imp) {
		this.consec_imp = consec_imp;
	}
	public String getDelito() {
		return delito;
	}
	public void setDelito(String delito) {
		this.delito = delito;
	}
	public String getEstado_psicofisico() {
		return estado_psicofisico;
	}
	public void setEstado_psicofisico(String estado_psicofisico) {
		this.estado_psicofisico = estado_psicofisico;
	}
	public String getFecha_delito() {
		return fecha_delito;
	}
	public void setFecha_delito(String fecha_delito) {
		this.fecha_delito = fecha_delito;
	}
	public String getGrado_consumacion() {
		return grado_consumacion;
	}
	public void setGrado_consumacion(String grado_consumacion) {
		this.grado_consumacion = grado_consumacion;
	}
	public String getIntencionalidad() {
		return intencionalidad;
	}
	public void setIntencionalidad(String intencionalidad) {
		this.intencionalidad = intencionalidad;
	}

	
}
