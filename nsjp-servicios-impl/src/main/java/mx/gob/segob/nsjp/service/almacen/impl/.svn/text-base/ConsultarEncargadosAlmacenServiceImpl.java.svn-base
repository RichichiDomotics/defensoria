/**
 * 
 */
package mx.gob.segob.nsjp.service.almacen.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.almacen.AlmacenDAO;
import mx.gob.segob.nsjp.dao.almacen.EncargadoAlmacenDAO;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Almacen;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.service.almacen.ConsultarEncargadosAlmacenService;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adrian
 *
 */
@Service
@Transactional
public class ConsultarEncargadosAlmacenServiceImpl implements
		ConsultarEncargadosAlmacenService {
	
	public final static Logger logger = 
		Logger.getLogger(ConsultarEncargadosAlmacenServiceImpl.class);
	
	@Autowired
	private EncargadoAlmacenDAO encargadoAlmacenDAO;
	@Autowired
	private AlmacenDAO almacenDAO;

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.almacen.ConsultarEncargadosAlmacenService#consultarEncargadosDAlmacen(mx.gob.segob.nsjp.dto.almacen.AlmacenDTO)
	 */
	@Override
	public List<FuncionarioDTO> consultarEncargadosDAlmacen(
			AlmacenDTO almacenDTO) throws NSJPNegocioException {
		
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA ASIGNAR ENCARGADO DE ALMACEN ****/");
		
		List<Funcionario> encargados=encargadoAlmacenDAO.consultarEncargadosDAlmacen(almacenDTO != null && almacenDTO.getIdentificadorAlmacen() != null? 
				almacenDTO.getIdentificadorAlmacen():null );
		List<FuncionarioDTO> encargadosDTO=new ArrayList<FuncionarioDTO>();
		for (Funcionario fun : encargados) {
			encargadosDTO.add(FuncionarioTransformer.transformarFuncionarioBasico(fun));
		}
		
		return encargadosDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.almacen.ConsultarEncargadosAlmacenService#consultarEncargadosInternosExternosDAlmacen(mx.gob.segob.nsjp.dto.almacen.AlmacenDTO)
	 */
	@Override
	public List<FuncionarioDTO> consultarEncargadosInternosExternosDAlmacen(
			AlmacenDTO almacenDTO) throws NSJPNegocioException {
		
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA ASIGNAR ENCARGADO DE ALMACEN ****/");
		
		List<Funcionario> encargados=encargadoAlmacenDAO.consultarEncargadosDAlmacen(almacenDTO != null && almacenDTO.getIdentificadorAlmacen() != null? 
				almacenDTO.getIdentificadorAlmacen():null );
		List<FuncionarioDTO> encargadosDTO=new ArrayList<FuncionarioDTO>();
		for (Funcionario fun : encargados) {
			encargadosDTO.add(FuncionarioTransformer.transformarFuncionarioBasico(fun));
		}
		
		if(almacenDTO != null && almacenDTO.getIdentificadorAlmacen()!=null){
			Almacen almacen = almacenDAO.read(almacenDTO.getIdentificadorAlmacen());
			if(almacen.getNombreRespExt()!=null || almacen.getApellidoPatRespExt()!=null || almacen.getApellidoMatRespExt()!=null){
				Funcionario fun = new Funcionario();
				fun.setNombreFuncionario(almacen.getNombreRespExt());
				fun.setApellidoPaternoFuncionario(almacen.getApellidoPatRespExt());
				fun.setApellidoMaternoFuncionario(almacen.getApellidoMatRespExt());
				// los responsables externos, se van a distinguir por medio de la clave de funcionario,
				// ya que no van a contar con esta información.
				encargadosDTO.add(FuncionarioTransformer.transformarFuncionarioBasico(fun));
			}
		}
			
		return encargadosDTO;
	}
}
