package mx.gob.segob.nsjp.comun.enums.involucrado;

/**
* Nombre del Programa : TipoEvento.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 07/06/2012
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

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author rgama
 *
 */
public enum TipoEvento {

	DELITO(1L, "Delito"), FALTA_ADMINISTRATIVA(2L, "Falta Administrativa");
	
	private Long id;
	private String descripcion;
	
	private TipoEvento(Long id) {
		this.id = id;
	}
	
	private TipoEvento(Long id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	/**
	 * Metodo de acceso al campo id.
	 * @return El valor del campo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metodo de acceso al campo descripcion.
	 * @return El valor del campo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}


	
}
