package mx.gob.segob.nsjp.persistencia.correo;

import java.util.List;

import mx.gob.segob.nsjp.model.ConfCorreo;
import mx.gob.segob.nsjp.model.CorreoElectronico;

public interface CorreoDAO {
	
	public List<CorreoElectronico> ConsultarCorreosInvolucradosPorAudienciaId(Long audienciaId);
	public List<CorreoElectronico> ConsultarCorreosFuncionariosPorAudienciaId(Long audienciaId);
	public List<CorreoElectronico> ConsultarCorreosFuncionariosEInvolucradosPorAudienciaId(Long audienciaId);
	

}
