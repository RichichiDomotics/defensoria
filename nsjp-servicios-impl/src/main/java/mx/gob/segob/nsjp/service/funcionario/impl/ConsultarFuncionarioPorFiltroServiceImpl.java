/**
* Nombre del Programa : ConsultarPeritosPorFiltroServiceImpl.java
* Autor                            : rgama
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servicio para consultar Peritos
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
package mx.gob.segob.nsjp.service.funcionario.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionarioPorFiltroService;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.RolTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para consultar Peritos por filtro.
 * @version 1.0
 * @author rgama
 *
 */
@Service
@Transactional
public class ConsultarFuncionarioPorFiltroServiceImpl implements ConsultarFuncionarioPorFiltroService {
	
	private final static Logger logger = Logger.getLogger(ConsultarFuncionarioPorFiltroServiceImpl.class);
	
	@Autowired
	private FuncionarioDAO peritoDAO;
	@Autowired
	private JerarquiaOrganizacionalDAO jerarquiaDAO;
	@Autowired
	private ClienteGeneralService clienteGeneralService;
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionarioPorFiltroService#consultarFuncionarioPorFiltro(mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO)
	 */
	@Override
	public List<FuncionarioDTO> consultarFuncionarioPorFiltro(FuncionarioDTO filtro, Long rolId)
			throws NSJPNegocioException {							
		
		if(logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR FUNCIONARIOS POR FILTRO ****/");
		if(filtro == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		List<Funcionario> loResultadoBD = new ArrayList<Funcionario>();
		
		Funcionario loPerito = FuncionarioTransformer.transformarFuncionario(filtro);		
		
		if(rolId != null && rolId > 0L){
			loResultadoBD = peritoDAO.consultarFuncionariosPorFiltroYRol(loPerito,rolId);
		}else{
			loResultadoBD = peritoDAO.consultarFuncionarioXFiltro(loPerito,filtro.getEsCoordinadoGenaral());
		}

		List<FuncionarioDTO> loPeritosDTO = new ArrayList<FuncionarioDTO>();
		if(loResultadoBD!=null){
			for (Funcionario row : loResultadoBD) {
				if(row.getArea()!=null){
					JerarquiaOrganizacional jerarquia = jerarquiaDAO.read(row.getArea().getJerarquiaOrganizacionalId());
					row.setArea(jerarquia);
				}
				
				FuncionarioDTO funcionarioDTO = FuncionarioTransformer.transformarFuncionario(row);
				
				if(row.getRol() != null){
					if(funcionarioDTO.getUsuario() != null) {
						Set<UsuarioRolDTO> usuarioRolesDTO = new HashSet<UsuarioRolDTO>();
						UsuarioRolDTO usuarioRolDTO = new UsuarioRolDTO();
						RolDTO rolDTO = RolTransformer.transformar(row.getRol());
						usuarioRolDTO.setRol(rolDTO);
						usuarioRolDTO.setEsPrincipal(Boolean.TRUE);
						usuarioRolesDTO.add(usuarioRolDTO);
						funcionarioDTO.getUsuario().setUsuarioRoles(usuarioRolesDTO);
					}
				}
				
				loPeritosDTO.add(funcionarioDTO);
			}		
		}		
		return loPeritosDTO;
	}

	@Override
	public List<FuncionarioDTO> consultarFuncionarioPorDepartamentoYArea(Long idDepartamento)
	throws NSJPNegocioException {
		
		if(idDepartamento==null || idDepartamento<0)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		List<FuncionarioDTO> loFuncionarioAreaDepartamentoDTO = new ArrayList<FuncionarioDTO>();
		
		//Obtener los funcionarios por el departamento
		logger.info("Obtener los funcionarios del departamento" + idDepartamento);
		
		Funcionario loPerito = new Funcionario();
		loPerito.setArea(new JerarquiaOrganizacional(idDepartamento));
		List<Funcionario> loFuncionariosDepartamentoBD = peritoDAO.consultarFuncionarioXFiltro(loPerito, null);

		for (Funcionario funcionario : loFuncionariosDepartamentoBD) {
			if(funcionario.getArea()!=null){
				JerarquiaOrganizacional jerarquia = jerarquiaDAO.read(funcionario.getArea().getJerarquiaOrganizacionalId());
				funcionario.setArea(jerarquia);
			}
			loFuncionarioAreaDepartamentoDTO.add(FuncionarioTransformer.transformarFuncionario(funcionario));			
		}		
		return loFuncionarioAreaDepartamentoDTO;
	}

	@Override
	public List<FuncionarioDTO> consultarFuncionariosXAgencia(Long idAgencia)
			throws NSJPNegocioException {
		if(logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR FUNCIONARIOS POR AGENCIA ****/");
		
		if(idAgencia==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		List<Funcionario> funcionarios =peritoDAO.consultarFuncionariosXAgencia(idAgencia);
		List<FuncionarioDTO> funcionariosDTO= new ArrayList<FuncionarioDTO>();
		for (Funcionario func : funcionarios) {
			funcionariosDTO.add(FuncionarioTransformer.transformarFuncionarioIntermedio(func));
		}
		
		return funcionariosDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionarioPorFiltroService#consultarFuncionariosXCriterio(mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO)
	 */
	@Override
	public List<FuncionarioDTO> consultarFuncionariosXCriterio(
			CriterioConsultaFuncionarioExternoDTO criterioConsultaFuncionarioExternoDTO)
			throws NSJPNegocioException {
		
		List<FuncionarioDTO> funcionariosDTO = null;
		List<Funcionario> funcionarios = peritoDAO.consultarFuncionariosXCriterio(criterioConsultaFuncionarioExternoDTO, null);
		if (funcionarios != null && !funcionarios.isEmpty()){
			funcionariosDTO = new ArrayList<FuncionarioDTO>();
			for (Funcionario f : funcionarios){
				funcionariosDTO.add(FuncionarioTransformer.transformarFuncionario(f));
			}
		}
		return funcionariosDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionarioPorFiltroService#consultarFuncionariosExternosXCriterio(mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO, mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
	 */
	@Override
	public List<FuncionarioDTO> consultarFuncionariosExternosXCriterio(
			CriterioConsultaFuncionarioExternoDTO criterioConsultaFuncionarioExternoDTO,
			Instituciones target) throws NSJPNegocioException {
		return clienteGeneralService.consultarFuncionariosXCriterio(criterioConsultaFuncionarioExternoDTO, target);
	}
}
