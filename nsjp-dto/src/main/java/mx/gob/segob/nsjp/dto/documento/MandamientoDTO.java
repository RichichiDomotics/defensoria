/**
* Nombre del Programa : MandatoDTO.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24/08/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.dto.documento;

import java.util.Date;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;

/**
 * DTO para el mandato judicial
 * @version 1.0
 * @author Emigdio
 *
 */
public class MandamientoDTO extends DocumentoDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7283897765791407211L;
	private Date fechaInicial;
	private Date fechaFinal;
	
	private ValorDTO tipoMandamiento;
	private ValorDTO tipoSentencia;
	private MedidaDTO medida;
	private ResolutivoDTO resolutivo;
	private ValorDTO estatus;
	
	private InvolucradoDTO involucrado;
	private Date fechaEjecucion;
	
	private String fechaInicioStr;
	private String fechaFinStr;
	private String fechaEjecucionStr;

	
	/**
	 * Método de acceso al campo fechaInicial.
	 * @return El valor del campo fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}
	/**
	 * Asigna el valor al campo fechaInicial.
	 * @param fechaInicial el valor fechaInicial a asignar
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
		if(fechaInicial != null)
			fechaInicioStr = DateUtils.formatear(fechaInicial);
	}
	/**
	 * Método de acceso al campo fechaFinal.
	 * @return El valor del campo fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}
	/**
	 * Asigna el valor al campo fechaFinal.
	 * @param fechaFinal el valor fechaFinal a asignar
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
		if(fechaFinal != null)
			fechaFinStr = DateUtils.formatear(fechaFinal);
		
	}
	/**
	 * Método de acceso al campo tipoMandamiento.
	 * @return El valor del campo tipoMandamiento
	 */
	public ValorDTO getTipoMandamiento() {
		return tipoMandamiento;
	}
	/**
	 * Asigna el valor al campo tipoMandamiento.
	 * @param tipoMandamiento el valor tipoMandamiento a asignar
	 */
	public void setTipoMandamiento(ValorDTO tipoMandamiento) {
		this.tipoMandamiento = tipoMandamiento;
	}
	/**
	 * Método de acceso al campo tipoSentencia.
	 * @return El valor del campo tipoSentencia
	 */
	public ValorDTO getTipoSentencia() {
		return tipoSentencia;
	}
	/**
	 * Asigna el valor al campo tipoSentencia.
	 * @param tipoSentencia el valor tipoSentencia a asignar
	 */
	public void setTipoSentencia(ValorDTO tipoSentencia) {
		this.tipoSentencia = tipoSentencia;
	}
	/**
	 * Método de acceso al campo medida.
	 * @return El valor del campo medida
	 */
	public MedidaDTO getMedida() {
		return medida;
	}
	/**
	 * Asigna el valor al campo medida.
	 * @param medida el valor medida a asignar
	 */
	public void setMedida(MedidaDTO medida) {
		this.medida = medida;
	}
	/**
	 * Método de acceso al campo resolutivo.
	 * @return El valor del campo resolutivo
	 */
	public ResolutivoDTO getResolutivo() {
		return resolutivo;
	}
	/**
	 * Asigna el valor al campo resolutivo.
	 * @param resolutivo el valor resolutivo a asignar
	 */
	public void setResolutivo(ResolutivoDTO resolutivo) {
		this.resolutivo = resolutivo;
	}
	/**
	 * Método de acceso al campo estatus.
	 * @return El valor del campo estatus
	 */
	public ValorDTO getEstatus() {
		return estatus;
	}
	/**
	 * Asigna el valor al campo estatus.
	 * @param estatus el valor estatus a asignar
	 */
	public void setEstatus(ValorDTO estatus) {
		this.estatus = estatus;
	}
	/**
	 * Método de acceso al campo involucrado.
	 * @return El valor del campo involucrado
	 */
	public InvolucradoDTO getInvolucrado() {
		return involucrado;
	}
	/**
	 * Asigna el valor al campo involucrado.
	 * @param involucrado el valor involucrado a asignar
	 */
	public void setInvolucrado(InvolucradoDTO involucrado) {
		this.involucrado = involucrado;
	}
	/**
	 * @param fechaEjecuacion the fechaEjecuacion to set
	 */
	public void setFechaEjecuacion(Date fechaEjecuacion) {		
		this.fechaEjecucion = fechaEjecuacion;
		if(fechaEjecucion != null)
			fechaEjecucionStr = DateUtils.formatear(fechaEjecucion);
	}
	/**
	 * @return the fechaEjecuacion
	 */
	public Date getFechaEjecuacion() {
		return fechaEjecucion;
	}
	/**
	 * @return the fechaEjecucion
	 */
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}
	/**
	 * @param fechaEjecucion the fechaEjecucion to set
	 */
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}
	/**
	 * @return the fechaInicioStr
	 */
	public String getFechaInicioStr() {
		return fechaInicioStr;
	}
	/**
	 * @param fechaInicioStr the fechaInicioStr to set
	 */
	public void setFechaInicioStr(String fechaInicioStr) {
		this.fechaInicioStr = fechaInicioStr;
	}
	/**
	 * @return the fechaFinStr
	 */
	public String getFechaFinStr() {
		return fechaFinStr;
	}
	/**
	 * @param fechaFinStr the fechaFinStr to set
	 */
	public void setFechaFinStr(String fechaFinStr) {
		this.fechaFinStr = fechaFinStr;
	}
	/**
	 * @return the fechaEjecucionStr
	 */
	public String getFechaEjecucionStr() {
		return fechaEjecucionStr;
	}
	/**
	 * @param fechaEjecucionStr the fechaEjecucionStr to set
	 */
	public void setFechaEjecucionStr(String fechaEjecucionStr) {
		this.fechaEjecucionStr = fechaEjecucionStr;
	}

	
	
}
