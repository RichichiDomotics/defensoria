package mx.gob.segob.nsjp.dto.audiencia.pjsiaj;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;

public class DocumentoWSDTO extends GenericWSDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8070799140273530781L;
	private String nombre;
    private String archivo;
    private String formato;
    private int juez;
    private int juzgado;
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the archivo
	 */
	public String getArchivo() {
		return archivo;
	}
	/**
	 * @param archivo the archivo to set
	 */
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	/**
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}
	/**
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}
	/**
	 * @return the juez
	 */
	public int getJuez() {
		return juez;
	}
	/**
	 * @param juez the juez to set
	 */
	public void setJuez(int juez) {
		this.juez = juez;
	}
	/**
	 * @return the juzgado
	 */
	public int getJuzgado() {
		return juzgado;
	}
	/**
	 * @param juzgado the juzgado to set
	 */
	public void setJuzgado(int juzgado) {
		this.juzgado = juzgado;
	}
}
