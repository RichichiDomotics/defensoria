/**
 * Nombre del Programa : IndicadorDTO.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 19/06/2012
 * Marca de cambio        : N/A
 * Descripcion General    : DTO para la transferencia de parametros de Indicador entre la vista y servicios.
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
package mx.gob.segob.nsjp.dto.indicador;

import java.util.List;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * DTO para la transferencia de parametros de Indicador entre la vista y
 * servicios.
 * 
 * @author GustavoBP
 */
public class IndicadorDTO extends GenericDTO  {

	private static final long serialVersionUID = 5909875943307668602L;
	
	private Long indicadorId;
	private String titulo;
	private Long institucionId;
	private String institucion;
	private List<String>  nombreColumnas;
	private List<String>  parametros;
	/**
	 * @return the indicadorId
	 */
	public Long getIndicadorId() {
		return indicadorId;
	}
	/**
	 * @param indicadorId the indicadorId to set
	 */
	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the institucionId
	 */
	public Long getInstitucionId() {
		return institucionId;
	}
	/**
	 * @param institucionId the institucionId to set
	 */
	public void setInstitucionId(Long institucionId) {
		this.institucionId = institucionId;
	}
	/**
	 * @return the institucion
	 */
	public String getInstitucion() {
		return institucion;
	}
	/**
	 * @param institucion the institucion to set
	 */
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	/**
	 * @return the nombreColumnas
	 */
	public List<String> getNombreColumnas() {
		return nombreColumnas;
	}
	/**
	 * @param nombreColumnas the nombreColumnas to set
	 */
	public void setNombreColumnas(List<String> nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}
	/**
	 * @return the parametros
	 */
	public List<String> getParametros() {
		return parametros;
	}
	/**
	 * @param parametros the parametros to set
	 */
	public void setParametros(List<String> parametros) {
		this.parametros = parametros;
	}
}
