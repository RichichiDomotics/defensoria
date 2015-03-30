/**
 * Nombre del Programa : ConsultarPermisosAudienciaServiceImpl.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementación del servicio para Consultar Permisos Audiencia
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
package mx.gob.segob.nsjp.service.audiencia.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.PermisoAudienciaDAO;
import mx.gob.segob.nsjp.dao.bitacora.BitacoraDescargaDAO;
import mx.gob.segob.nsjp.dto.audiencia.CatEstadoPermisoDTO;
import mx.gob.segob.nsjp.dto.audiencia.PermisoAudienciaDTO;
import mx.gob.segob.nsjp.dto.bitacora.BitacoraDescargaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.BitacoraDescarga;
import mx.gob.segob.nsjp.model.PermisoAudiencia;
import mx.gob.segob.nsjp.service.audiencia.ConsultarEstadoPermisoService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarPermisosAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.CatEstadoPermisoTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.PermisoAudienciaTransformer;
import mx.gob.segob.nsjp.service.bitacora.impl.transform.BitacoraDescargaTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.usuario.UsuarioService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio para Consultar Permisos Audiencia.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ConsultarPermisosAudienciaServiceImpl implements ConsultarPermisosAudienciaService {

	@SuppressWarnings("unused")
	private final static Logger logger = Logger
            .getLogger(ConsultarPermisosAudienciaServiceImpl.class);

    @Autowired
    private PermisoAudienciaDAO perAudDao;
    
    @Autowired
    private BitacoraDescargaDAO bitDesao;
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConsultarEstadoPermisoService consultarEstadoPermisoservice;
    
   	public List<BitacoraDescargaDTO> buscarBitacoraDescargaPorFecha(Date fechaInicio, Date fechaFin, Long discriminanteId) throws NSJPNegocioException{
   		if(fechaInicio == null || fechaFin == null || discriminanteId == null || discriminanteId.equals(0L)){
   			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
   		}
   		
   		List<BitacoraDescarga> bitDescargas = bitDesao.consultarBitacoraDescargaPorFecha(fechaInicio, fechaFin, discriminanteId);
   		List<BitacoraDescargaDTO> bitDescargasDto = new LinkedList<BitacoraDescargaDTO>();
		
		for(BitacoraDescarga ba: bitDescargas){
			bitDescargasDto.add(BitacoraDescargaTransformer.transformarBitacoraDescarga(ba));
		}
		
		return bitDescargasDto;
	}
   	
   	@SuppressWarnings("null")
	public List<BitacoraDescargaDTO> buscarBitacoraDescargaPorAudiencia(Long audiencia, Long discriminanteId) throws NSJPNegocioException{
   		if(audiencia == null || audiencia.equals(0L) || discriminanteId!=null || discriminanteId.equals(0L)){
   			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
   		}
   		
   		List<BitacoraDescarga> bitDescargas = bitDesao.consultarBitacoraDescargaPorAudiencia(audiencia, discriminanteId);
   		List<BitacoraDescargaDTO> bitDescargasDto = new LinkedList<BitacoraDescargaDTO>();
		
		for(BitacoraDescarga ba: bitDescargas){
			bitDescargasDto.add(BitacoraDescargaTransformer.transformarBitacoraDescarga(ba));
		}
		
		return bitDescargasDto;
	}
   	
   	public List<PermisoAudienciaDTO> buscarPermisosAudienciaPorEstado(Long estado, Date fechaInicio, Date fechaFin, Long discriminanteId) throws NSJPNegocioException{
   		if(estado == null || estado.equals(0L) || discriminanteId == null  || discriminanteId.equals(0L)){
   			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
   		}
		
		List<PermisoAudiencia> permisosAudienciasDao = perAudDao.consultarPermisosAudienciaPorEstado(estado, fechaInicio, fechaFin, discriminanteId);
		List<PermisoAudienciaDTO> resultDto = new LinkedList<PermisoAudienciaDTO>();
		for(PermisoAudiencia PA : permisosAudienciasDao){
			resultDto.add(PermisoAudienciaTransformer.transformarPermisoAudienciaBasico(PA));
		}
		
		return resultDto;
	}

	public List<PermisoAudienciaDTO> consultarPermisosAudienciaPorNumeroAudiencia(Long numeroAudiencia, String claveUsuario) throws NSJPNegocioException{
   		if(numeroAudiencia==null || claveUsuario==null || numeroAudiencia.equals(0L) || claveUsuario.equals("")){
   			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
   		}

		List<PermisoAudiencia> permisosAudienciasDao = perAudDao.consultarPermisosAudienciaPorNumeroAudienciaInterno(numeroAudiencia, claveUsuario);
		List<PermisoAudienciaDTO> resultDto = new ArrayList<PermisoAudienciaDTO>();
		for(PermisoAudiencia PA : permisosAudienciasDao){
			resultDto.add(PermisoAudienciaTransformer.transformarPermisoAudienciaBasico(PA));
		}
		
		return resultDto;
	}
	
	public List<PermisoAudienciaDTO> consultarPermisosAudienciaPorFuncionarioExterno(Long numeroAudiencia, Long cveFunExt) throws NSJPNegocioException{
   		if(numeroAudiencia==null || cveFunExt==null || numeroAudiencia.equals(0L) || cveFunExt.equals(0L)){
   			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
   		}

		List<PermisoAudiencia> permisosAudienciasDao = perAudDao.consultarPermisosAudienciaPorFuncionarioExterno(numeroAudiencia, cveFunExt);
		List<PermisoAudienciaDTO> resultDto = new ArrayList<PermisoAudienciaDTO>();
		for(PermisoAudiencia PA : permisosAudienciasDao){
			resultDto.add(PermisoAudienciaTransformer.transformarPermisoAudienciaBasico(PA));
		}
		
		return resultDto;
	}

	public Long cambiarEstadoPermisoAudiencia(Long estado, Long permisoAudiencia, Date fechaHoy, String claveUsuarioAsignador)
    		throws NSJPNegocioException{
		
		if(estado==null || estado.equals(0L) || permisoAudiencia==null || permisoAudiencia.equals(0L) || fechaHoy==null || claveUsuarioAsignador==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Long respuesta = 0L;
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		CatEstadoPermisoDTO estadoPermisoDTO = new CatEstadoPermisoDTO();					
		
		if(!claveUsuarioAsignador.equals("")){
			usuarioDTO = usuarioService.consultarUsuarioPorClaveUsuario(claveUsuarioAsignador);
			
			if(!estado.equals(0L)){
				estadoPermisoDTO = consultarEstadoPermisoservice.buscarEstadoPermisoPorEstado(estado);
			}						
		}
		
		if(usuarioDTO==null || estadoPermisoDTO==null){
			return respuesta;
		}		
			
		if(permisoAudiencia!=null && !permisoAudiencia.equals(0L)){
			PermisoAudiencia PA = perAudDao.read(permisoAudiencia);
						
			if(PA!=null){
				
				PermisoAudiencia newPA = new PermisoAudiencia();
				
				if(PA.getAudiencia()!=null){
					newPA.setAudiencia(PA.getAudiencia());
				}
				if(PA.getConfInstitucion()!=null){
					newPA.setConfInstitucion(PA.getConfInstitucion());
				}
				if(PA.getEsExterno()!=null){
					newPA.setEsExterno(PA.getEsExterno());
				}
				if(PA.getFechaSolicitud()!=null){
					newPA.setFechaSolicitud(PA.getFechaSolicitud());
				}
				if(PA.getUsuario()!=null){
					newPA.setUsuario(PA.getUsuario());
				}
				if(PA.getFuncionarioExterno()!=null){
					newPA.setFuncionarioExterno(PA.getFuncionarioExterno());
				}
				newPA.setEsVigente(true);
				newPA.setCatEstadoPermiso(CatEstadoPermisoTransformer.transformarEstadoPermisoDTO(estadoPermisoDTO));
				newPA.setFechaAsignacion(fechaHoy);								
				newPA.setUsuarioAsignador(UsuarioTransformer.transformarDTO(usuarioDTO));				
				
				PA.setEsVigente(false);
				
				perAudDao.update(PA);				
				perAudDao.create(newPA);

				respuesta = 1L;
			}			
		}
		
		return respuesta;
	}
}
