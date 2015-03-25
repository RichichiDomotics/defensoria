/**
* Nombre del Programa : DelitoWSDTO.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 02/07/2011
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
package mx.gob.segob.nsjp.dto.expediente;

/**
 * DTO para transferencia 
 * @version 1.0
 * @author Emigdio
 *
 */
public class DelitoWSDTO {

    private Long idCatDelito;
	private String claveDelito;
	private String nombreDelito;
	private Boolean esGrave;
	private Boolean esPrincipal;
	private Boolean esProbable;
	private String claveInterInstitucional;
	private Long situacionImputado;

	private Long catDelitoClasificacion;
	private Long catDelitoModus;
	private Long catDelitoCausa;

	public Long getCatDelitoClasificacion() {
		return catDelitoClasificacion;
	}
	public void setCatDelitoClasificacion(Long catDelitoClasificacion) {
		this.catDelitoClasificacion = catDelitoClasificacion;
	}
	public Long getCatDelitoModus() {
		return catDelitoModus;
	}
	public void setCatDelitoModus(Long catDelitoModus) {
		this.catDelitoModus = catDelitoModus;
	}
	public Long getCatDelitoCausa() {
		return catDelitoCausa;
	}
	public void setCatDelitoCausa(Long catDelitoCausa) {
		this.catDelitoCausa = catDelitoCausa;
	}
	
	public Long getSituacionImputado() {
		return situacionImputado;
	}
	public void setSituacionImputado(Long situacionImputado) {
		this.situacionImputado = situacionImputado;
	}
	/**
	 * Regresa el valor de la propiedad idCatDelito
	 * @return the idCatDelito
	 */
	public Long getIdCatDelito() {
		return idCatDelito;
	}
	/**
	 * Establece el valor de la propiedad idCatDelito
	 * @param idCatDelito valo idCatDelito a almacenar
	 */
	public void setIdCatDelito(Long idCatDelito) {
		this.idCatDelito = idCatDelito;
	}
	/**
	 * Método de acceso al campo claveDelito.
	 * @return El valor del campo claveDelito
	 */
	public String getClaveDelito() {
		return claveDelito;
	}
	/**
	 * Asigna el valor al campo claveDelito.
	 * @param claveDelito el valor claveDelito a asignar
	 */
	public void setClaveDelito(String claveDelito) {
		this.claveDelito = claveDelito;
	}
	/**
	 * Método de acceso al campo nombreDelito.
	 * @return El valor del campo nombreDelito
	 */
	public String getNombreDelito() {
		return nombreDelito;
	}
	/**
	 * Asigna el valor al campo nombreDelito.
	 * @param nombreDelito el valor nombreDelito a asignar
	 */
	public void setNombreDelito(String nombreDelito) {
		this.nombreDelito = nombreDelito;
	}
	/**
	 * Método de acceso al campo esGrave.
	 * @return El valor del campo esGrave
	 */
	public Boolean isEsGrave() {
		return esGrave;
	}
	/**
	 * Asigna el valor al campo esGrave.
	 * @param esGrave el valor esGrave a asignar
	 */
	public void setEsGrave(Boolean esGrave) {
		this.esGrave = esGrave;
	}
	
	/**
	 * Método de acceso al campo esPrincipal.
	 * @return El valor del campo esPrincipal
	 */
	public Boolean isEsPrincipal() {
		return esPrincipal;
	}
	/**
	 * Asigna el valor al campo esPrincipal.
	 * @param esPrincipal el valor esPrincipal a asignar
	 */
	public void setEsPrincipal(Boolean esPrincipal) {
		this.esPrincipal = esPrincipal;
	}
	/**
	 * Método de acceso al campo esProbable.
	 * @return El valor del campo esProbable
	 */
	public Boolean isEsProbable() {
		return esProbable;
	}
	/**
	 * Asigna el valor al campo esProbable.
	 * @param esProbable el valor esProbable a asignar
	 */
	public void setEsProbable(Boolean esProbable) {
		this.esProbable = esProbable;
	}
	/**
	 * @return the claveInterInstitucional
	 */
	public String getClaveInterInstitucional() {
		return claveInterInstitucional;
	}
	/**
	 * @param claveInterInstitucional the claveInterInstitucional to set
	 */
	public void setClaveInterInstitucional(String claveInterInstitucional) {
		this.claveInterInstitucional = claveInterInstitucional;
	}
	
	
}
