package mx.gob.segob.nsjp.service.correo;

import mx.gob.segob.nsjp.comun.exceptions.CorreoNSJPException;

public interface EnviarCorreo {

	public void enviarCorreoPorAudiencia(Long idAudiencia);

}
