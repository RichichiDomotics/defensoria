/**
 * 
 */
package mx.gob.segob.nsjp.service.almacen.impl;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.almacen.AlmacenDAO;
import mx.gob.segob.nsjp.dao.almacen.EncargadoAlmacenDAO;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Almacen;
import mx.gob.segob.nsjp.model.EncargadoAlmacen;
import mx.gob.segob.nsjp.model.EncargadoAlmacenId;
import mx.gob.segob.nsjp.service.almacen.EncargadoAlmacenService;

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
public class EncargadoAlmacenServiceImpl implements EncargadoAlmacenService {
	
	public final static Logger logger = 
		Logger.getLogger(EncargadoAlmacenServiceImpl.class);
	
	@Autowired
	private EncargadoAlmacenDAO encargadoAlmacenDAO;
	@Autowired
	private AlmacenDAO almacenDAO;		

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.almacen.EncargadoAlmacenService#asignarEncargadoDAlmacen(mx.gob.segob.nsjp.dto.almacen.AlmacenDTO, mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO)
	 */
	@Override
	public Long asignarEncargadoDAlmacen(AlmacenDTO almacenDTO,
			FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {
		
		if(almacenDTO==null || almacenDTO.getIdentificadorAlmacen()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		Long idResultado=0L;
		
		if(funcionarioDTO!=null){
			
			EncargadoAlmacen newInstance=new EncargadoAlmacen();
			EncargadoAlmacenId encargadoAlmacenId=new EncargadoAlmacenId(almacenDTO.getIdentificadorAlmacen(), funcionarioDTO.getClaveFuncionario());
			newInstance.setEncargadoAlmacenId(encargadoAlmacenId);
			EncargadoAlmacenId idResp = encargadoAlmacenDAO.create(newInstance);

			if(idResp!=null){
				idResultado = idResp.getFuncionarioId();
			}
		}
		else{
			Almacen almacen = almacenDAO.read(almacenDTO.getIdentificadorAlmacen());
			if(almacen!=null){
				almacen.setNombreRespExt(almacenDTO.getNombreRespExt());
				almacen.setApellidoPatRespExt(almacenDTO.getApellidoPatRespExt());
				almacen.setApellidoMatRespExt(almacenDTO.getApellidoMatRespExt());
				almacenDAO.update(almacen);
			}
		}
		
		return idResultado;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.almacen.EncargadoAlmacenService#removerEncargadoDAlmacen(mx.gob.segob.nsjp.dto.almacen.AlmacenDTO, mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO)
	 */
	@Override
	public Long removerEncargadoDAlmacen(AlmacenDTO almacenDTO,
			FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {
		
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA REMOVER ENCARGADO DE ALMACEN ****/");
		
		if(almacenDTO==null||funcionarioDTO==null || funcionarioDTO.getClaveFuncionario()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if(almacenDTO.getIdentificadorAlmacen()==null||funcionarioDTO.getClaveFuncionario()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		Long idEncargado=0L;
		
		if(funcionarioDTO.getClaveFuncionario()>0){
			
			EncargadoAlmacen persistentObject=new EncargadoAlmacen();
			EncargadoAlmacenId encargadoAlmacenId=new EncargadoAlmacenId(almacenDTO.getIdentificadorAlmacen(), funcionarioDTO.getClaveFuncionario());
			persistentObject.setEncargadoAlmacenId(encargadoAlmacenId);
			encargadoAlmacenDAO.delete(persistentObject);		
			idEncargado=funcionarioDTO.getClaveFuncionario();
		}
		else{
			Almacen almacen = almacenDAO.read(almacenDTO.getIdentificadorAlmacen());
			if(almacen!=null){
				almacen.setNombreRespExt(null);
				almacen.setApellidoPatRespExt(null);
				almacen.setApellidoMatRespExt(null);
				almacenDAO.update(almacen);
				idEncargado = almacen.getIdentificadorAlmacen();
			}
		}
		
		return idEncargado;
	}
}
