package mx.gob.segob.nsjp.service.funcionario.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioDAO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionarioService;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsultarFuncionarioServiceImpl implements
		ConsultarFuncionarioService {

	@Autowired
	private FuncionarioDAO funcionarioDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	public FuncionarioDTO consultarFuncionarioXUsuarioID(Long usuarioID){
		Usuario usr = usuarioDAO.read(usuarioID);
		if(usr != null){
			Funcionario func = usr.getFuncionario();
			if(func != null){
				FuncionarioDTO funcDTO = FuncionarioTransformer.transformarFuncionario(func);
				return funcDTO;
			}
		}
		return null;
	}
	@Override
	public List<FuncionarioDTO> consultarSubordinadosUAVD() throws NSJPNegocioException{
		List<Funcionario> subordinadosBD = funcionarioDAO.consultarSubordinadosUAVD();
		if(subordinadosBD != null){
			return FuncionarioTransformer.transformarFuncionarios(subordinadosBD);
		}
		return new ArrayList<FuncionarioDTO>();
	}

	@Override
	public FuncionarioDTO obtenerFuncionarioDTO(FuncionarioDTO funcionario)
			throws NSJPNegocioException {
		Funcionario func = funcionarioDAO.read(funcionario.getClaveFuncionario());
		return FuncionarioTransformer.transformarFuncionario(func);
	}

}
