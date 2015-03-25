package mx.gob.segob.nsjp.dto.solicitud;

import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;

public class SolicitudTranscripcionAudienciaDTO extends SolicitudDTO {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 48311818306951522L;
	private AudienciaDTO audiencia;
	private ValorDTO estatusSolicitud;

	/**
	 * Regresa el valor de la propiedad audiencia
	 * @return the audiencia
	 */
	public AudienciaDTO getAudiencia() {
		return audiencia;
	}

	/**
	 * Establece el valor de la propiedad audiencia
	 * @param audiencia valo audiencia a almacenar
	 */
	public void setAudiencia(AudienciaDTO audiencia) {
		this.audiencia = audiencia;
	}

	/**
	 * Campo para guardar el estatus de la solicitud, para presentarlo en vista
	 * @return el estatus de la solicitud
	 */
	public ValorDTO getEstatusSolicitud() {
		return estatusSolicitud;
	}

	/**
	 * Campo para guardar el estatus de la solicitud
	 * @param estatusSolicitud
	 */
	public void setEstatusSolicitud(ValorDTO estatusSolicitud) {
		this.estatusSolicitud = estatusSolicitud;
	}
}
