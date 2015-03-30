package mx.gob.segob.nsjp.persistencia.funcionarioExternoCorreo;

import java.util.List;

import mx.gob.segob.nsjp.model.FuncionarioExterno;

public interface FuncionarioExternoCorreoDAO {
	
	public FuncionarioExterno consultarCorreoFuncionarioExternosPorAudienciaId(Long cveFuncionarioInstExt);

}
