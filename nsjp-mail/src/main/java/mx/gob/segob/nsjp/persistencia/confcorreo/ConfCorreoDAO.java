package mx.gob.segob.nsjp.persistencia.confcorreo;

import java.util.List;

import mx.gob.segob.nsjp.model.ConfCorreo;

public interface ConfCorreoDAO {
	
	public List<ConfCorreo> obtenerConfiguracionSession();
	public List<ConfCorreo> obtenerConfiguracionConexion();
	public ConfCorreo obtenerConfiguracionTransport();
	public List<ConfCorreo> obtenerConfiguracionRecipientes();
	public List<ConfCorreo> obtenerConfiguracionAsuntoContenido();
	public ConfCorreo esCorreoActivo();

}
