package mx.gob.segob.nsjp.dto.solicitud;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;

public class SolicitudMandamientoDTO extends SolicitudDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1019854865946493888L;
	public ValorDTO tipoMandamiento;
	
	/**
	 * @return the mandamiento
	 */
	public ValorDTO getTipoMandamiento() {
		return tipoMandamiento;
	}
	
	/**
	 * @param mandamiento the mandamiento to set
	 */
	public void setTipoMandamiento(ValorDTO mandamiento) {
		this.tipoMandamiento = mandamiento;
	}	
}
