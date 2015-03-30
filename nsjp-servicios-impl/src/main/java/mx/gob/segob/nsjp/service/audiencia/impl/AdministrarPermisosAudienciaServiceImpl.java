/**
 * Nombre del Programa : AdministrarPermisosAudienciaServiceImpl.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementación del servicio para administrar Permisos de Audiencias de usuarios externos.
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

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusPermisosAudiencia;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.DescriptorAudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.CatEstadoPermisoDAO;
import mx.gob.segob.nsjp.dao.audiencia.PermisoAudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.SalaJAVSDAO;
import mx.gob.segob.nsjp.dao.bitacora.BitacoraDescargaDAO;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaJAVSTransporteWSDTO;
import mx.gob.segob.nsjp.dto.audiencia.PermisoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoWSDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.BitacoraDescarga;
import mx.gob.segob.nsjp.model.CatEstadoPermiso;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.DescriptorAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.SalaJAVS;
import mx.gob.segob.nsjp.service.audiencia.AdministrarAudienciaJAVSService;
import mx.gob.segob.nsjp.service.audiencia.AdministrarPermisosAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarPermisosAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.CatEstadoPermisoTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.PermisoAudienciaTransformer;
import mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform.FuncionarioExternoTransformer;
import mx.gob.segob.nsjp.service.infra.JAVSClienteService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio para administrar Permisos de Audiencias de usuarios externos.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
@Service("administrarPermisosAudienciaService")
@Transactional
public class AdministrarPermisosAudienciaServiceImpl implements AdministrarPermisosAudienciaService {

	private final static Logger logger = Logger.getLogger(AdministrarPermisosAudienciaServiceImpl.class);
	
	@Autowired
	BitacoraDescargaDAO bitacoraDescargaDAO;	  
	
	@Autowired
	AudienciaDAO audienciaDAO;
	
	@Autowired
	SalaJAVSDAO salaJAVSDAO;
	
	@Autowired
	DescriptorAudienciaDAO descriptorAudienciaDAO;	  
	
	@Autowired
    private AudienciaDAO audDao;
	
	@Autowired
    private CatEstadoPermisoDAO catEstadoPermisoDAO;
	
	@Autowired
	private AdministrarAudienciaJAVSService administrarAudienciaJAVSService;
	
	@Autowired
	private JAVSClienteService javsClienteService;
	
	@Autowired
    private FuncionarioExternoDAO funExtDao;
	
	@Autowired
    private PermisoAudienciaDAO permisoAudienciaDAO;

	@Autowired
    private ConfInstitucionDAO confInstitucionDao;
		
	@Autowired
    private ConsultarPermisosAudienciaService consultarPermisosAudienciaService;
	
	@Override
	public Long generarPermisoUsuarioExterno(FuncionarioExternoWSDTO funExtWSDTO)
			throws NSJPNegocioException {
		if(funExtWSDTO==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Long numeroAudiencia = 0L;
		Long valorRetorno = EstatusPermisosAudiencia.FALLO.getValorId();
		
		if(funExtWSDTO.getAudienciaId()!=null){
			numeroAudiencia = funExtWSDTO.getAudienciaId();
		}
		
		if(numeroAudiencia==null || numeroAudiencia.equals(0L) || funExtWSDTO.getCveFuncionarioInstExt()==null ||
				funExtWSDTO.getConfInstId()==null){
			return valorRetorno;
		}
		
		if(administrarAudienciaJAVSService.esJAVS(numeroAudiencia).equals(false)){
			return EstatusPermisosAudiencia.NO_ES_JAVS.getValorId();
		}
		
		logger.info("JAVSClienteService - Servicio para generar permisos de audiencia de usuarios externos: " + numeroAudiencia);

		FuncionarioExterno funExterno = null;
				
		if(funExtWSDTO.getCveFuncionarioInstExt()!=null && funExtWSDTO.getConfInstId()!=null){
				funExterno = funExtDao.consultarFuncExternoPorClaveFuncExt(funExtWSDTO.getCveFuncionarioInstExt(), funExtWSDTO.getConfInstId());
		}
		
		Long cveFunExternoPJ = null;
		
		if(funExterno!=null){
			
			cveFunExternoPJ = funExterno.getFuncionarioExternoId();
			
			funExterno.setApellidoMaterno(funExtWSDTO.getApellidoMaterno());
			funExterno.setApellidoPaterno(funExtWSDTO.getApellidoPaterno());
			funExterno.setArea(funExtWSDTO.getArea());
			ConfInstitucion confInst = new ConfInstitucion();
			confInst = confInstitucionDao.read(funExtWSDTO.getConfInstId());
			funExterno.setConfInstitucion(confInst);
			funExterno.setCveFuncionarioInstExt(funExtWSDTO.getCveFuncionarioInstExt());
			funExterno.setEmail(funExtWSDTO.getEmail());
			funExterno.setNombre(funExtWSDTO.getNombre());
			funExterno.setPuesto(funExtWSDTO.getPuesto());
			funExtDao.update(funExterno);
		}
		else{
			funExterno = new FuncionarioExterno();
			funExterno.setApellidoMaterno(funExtWSDTO.getApellidoMaterno());
			funExterno.setApellidoPaterno(funExtWSDTO.getApellidoPaterno());
			funExterno.setArea(funExtWSDTO.getArea());
			ConfInstitucion confInst = new ConfInstitucion();
			confInst = confInstitucionDao.read(funExtWSDTO.getConfInstId());
			funExterno.setConfInstitucion(confInst);
			funExterno.setCveFuncionarioInstExt(funExtWSDTO.getCveFuncionarioInstExt());
			funExterno.setEmail(funExtWSDTO.getEmail());
			funExterno.setNombre(funExtWSDTO.getNombre());
			funExterno.setPuesto(funExtWSDTO.getPuesto());
			
			cveFunExternoPJ = funExtDao.create(funExterno);
		}
		
		List<PermisoAudienciaDTO> pesDTO = consultarPermisosAudienciaService.consultarPermisosAudienciaPorFuncionarioExterno(numeroAudiencia, cveFunExternoPJ);
	
		Date fechaHoy = new Date();
		
		if(pesDTO!=null && pesDTO.size()==1){
			PermisoAudienciaDTO peDTO = pesDTO.get(0);
			if(peDTO.getCatEstadoPermiso().getCatEstadoPermisoId().equals(EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId()) ||
					peDTO.getCatEstadoPermiso().getCatEstadoPermisoId().equals(EstatusPermisosAudiencia.CONCEDIDO.getValorId())){
					valorRetorno = peDTO.getCatEstadoPermiso().getCatEstadoPermisoId();												
			}
			else{
				peDTO.setEsVigente(false);
				permisoAudienciaDAO.update(PermisoAudienciaTransformer.transformarPermisoAudienciaBasicoDTO(peDTO));
				
				PermisoAudienciaDTO newpeDTO = new PermisoAudienciaDTO();				
				
				Audiencia audiencia = audDao.read(numeroAudiencia);
				newpeDTO.setAudiencia(AudienciaTransformer.transformarBasico(audiencia));
				Long valor = EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId();
				CatEstadoPermiso ep = catEstadoPermisoDAO.read(valor);				
				newpeDTO.setCatEstadoPermiso(CatEstadoPermisoTransformer.transformarEstadoPermiso(ep));
				ConfInstitucion confInst = new ConfInstitucion();
				confInst = confInstitucionDao.read(funExtWSDTO.getConfInstId());
				newpeDTO.setConfInstitucion(ConfInstitucionTransformer.transformarInstitucion(confInst));
				newpeDTO.setEsExterno(true);
				newpeDTO.setEsVigente(true);
				newpeDTO.setFechaSolicitud(fechaHoy);
				newpeDTO.setFuncionarioExterno(FuncionarioExternoTransformer.transformar(funExtDao.read(cveFunExternoPJ)));
				
				permisoAudienciaDAO.create(PermisoAudienciaTransformer.transformarPermisoAudienciaBasicoDTO(newpeDTO));

				valorRetorno = EstatusPermisosAudiencia.NUEVA_SOLICITUD.getValorId();
			}
		}
		else if(pesDTO==null || pesDTO.size()==0){

				PermisoAudienciaDTO newpeDTO = new PermisoAudienciaDTO();				
			
				Audiencia audiencia = audDao.read(numeroAudiencia);
				newpeDTO.setAudiencia(AudienciaTransformer.transformarBasico(audiencia));
				Long valor = EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId();
				CatEstadoPermiso ep = catEstadoPermisoDAO.read(valor);				
				newpeDTO.setCatEstadoPermiso(CatEstadoPermisoTransformer.transformarEstadoPermiso(ep));
				ConfInstitucion confInst = new ConfInstitucion();
				confInst = confInstitucionDao.read(funExtWSDTO.getConfInstId());
				newpeDTO.setConfInstitucion(ConfInstitucionTransformer.transformarInstitucion(confInst));
				newpeDTO.setEsExterno(true);
				newpeDTO.setEsVigente(true);
				newpeDTO.setFechaSolicitud(fechaHoy);
				newpeDTO.setFuncionarioExterno(FuncionarioExternoTransformer.transformar(funExtDao.read(cveFunExternoPJ)));
			
				permisoAudienciaDAO.create(PermisoAudienciaTransformer.transformarPermisoAudienciaBasicoDTO(newpeDTO));

				valorRetorno = EstatusPermisosAudiencia.NUEVA_SOLICITUD.getValorId();				
		}

		return valorRetorno;
	}
	
	@Override
	public AudienciaJAVSTransporteWSDTO consultarPermisoAudienciaUsuarioExterno(FuncionarioExternoWSDTO funExtWSDTO)
			throws NSJPNegocioException {
		if(funExtWSDTO==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
				
		Long numeroAudiencia = 0L;
		AudienciaJAVSTransporteWSDTO audienciaJAVSTransporteWSDTO = new AudienciaJAVSTransporteWSDTO();
		Long valorRetorno = EstatusPermisosAudiencia.FALLO.getValorId();
		
		if(funExtWSDTO.getAudienciaId()!=null){
			numeroAudiencia = funExtWSDTO.getAudienciaId();
		}
		
		if(numeroAudiencia==null || numeroAudiencia.equals(0L) || funExtWSDTO.getCveFuncionarioInstExt()==null ||
			funExtWSDTO.getConfInstId()==null){
			audienciaJAVSTransporteWSDTO.setResultadoPermisoAudiencia(valorRetorno);
			return audienciaJAVSTransporteWSDTO;
		}
		
		logger.info("JAVSClienteService - Servicio para verificar permisos de audiencia de usuarios externos: " + numeroAudiencia);

		FuncionarioExterno funExterno = new FuncionarioExterno();
				
		if(funExtWSDTO.getCveFuncionarioInstExt()!=null && funExtWSDTO.getConfInstId()!=null){
				funExterno = funExtDao.consultarFuncExternoPorClaveFuncExt(funExtWSDTO.getCveFuncionarioInstExt(), funExtWSDTO.getConfInstId());
		}
		
		Long cveFunExternoPJ = 0L;
		valorRetorno = EstatusPermisosAudiencia.NO_HAY_SOLICITUD.getValorId();
		
		if(funExterno!=null){			
			
			cveFunExternoPJ = funExterno.getFuncionarioExternoId();
			
			if(administrarAudienciaJAVSService.esJAVS(numeroAudiencia).equals(false)){
				valorRetorno = EstatusPermisosAudiencia.NO_ES_JAVS.getValorId();
			}
			else{
				List<PermisoAudienciaDTO> pesDTO = consultarPermisosAudienciaService.consultarPermisosAudienciaPorFuncionarioExterno(numeroAudiencia, cveFunExternoPJ);
								
				if(pesDTO!=null && pesDTO.size()==1){
					PermisoAudienciaDTO peDTO = pesDTO.get(0);
					if(peDTO.getCatEstadoPermiso()!=null && peDTO.getCatEstadoPermiso().getCatEstadoPermisoId()!=null){
						valorRetorno = peDTO.getCatEstadoPermiso().getCatEstadoPermisoId();
						if(valorRetorno.equals(EstatusPermisosAudiencia.CONCEDIDO.getValorId())){
							
							UsuarioDTO usuDto = new UsuarioDTO();
							usuDto = administrarAudienciaJAVSService.consultarUsuarioPorAudienciaId(numeroAudiencia);
							valorRetorno = EstatusPermisosAudiencia.FALLO.getValorId();
							
							if(usuDto!=null && usuDto.getClaveUsuario()!=null){
								String claveUsuario =  usuDto.getClaveUsuario();
								valorRetorno = javsClienteService.consultarAudiencia(numeroAudiencia, claveUsuario);
							}							
												
							if(valorRetorno!=null && valorRetorno.equals(ConstantesGenerales.AUDIENCIA_ACTUALIZADA)){

								BitacoraDescarga bd = new BitacoraDescarga();
								bd.setFechaDescarga(new Date());
							  	bd.setPermisoAudiencia(PermisoAudienciaTransformer.transformarPermisoAudienciaBasicoDTO(peDTO));		  	
								bitacoraDescargaDAO.create(bd);								
								
								Long descriptor =
								administrarAudienciaJAVSService.generandoConglomeradoJAVS(numeroAudiencia);
								
								if(descriptor==null || descriptor.equals(0L)){
									valorRetorno = EstatusPermisosAudiencia.FALLO.getValorId();
								}
							}							
						}	
					}
				}
			}
		}
		else{
			valorRetorno = EstatusPermisosAudiencia.NO_HAY_SOLICITUD.getValorId();
		}
		
		audienciaJAVSTransporteWSDTO.setResultadoPermisoAudiencia(valorRetorno);
		return audienciaJAVSTransporteWSDTO;
	}

	@Override
	public AudienciaJAVSTransporteWSDTO consultarParametrosConsultaAudienciaJavs(Long numeroAudiencia)
			throws NSJPNegocioException {
		AudienciaJAVSTransporteWSDTO audienciaJAVSTransporteWSDTO = null;
		
		if(numeroAudiencia==null || numeroAudiencia.equals(0L)){
			return audienciaJAVSTransporteWSDTO;
		}
				
		audienciaJAVSTransporteWSDTO = new AudienciaJAVSTransporteWSDTO();
		
		Audiencia audiencia = audienciaDAO.consultarAudienciaById(numeroAudiencia); 
		
		if(audiencia.getSalaAudiencia()!= null ){
			Long salaId = audiencia.getSalaAudiencia().getSalaAudienciaId();
			if( salaId!= null && salaId > 0){
				SalaJAVS salaJAVS = salaJAVSDAO.recuperarSalaJAVS(salaId);
				
				if(salaJAVS!= null){
					audienciaJAVSTransporteWSDTO.setDirIPJAVS(salaJAVS.getDireccionIP());
					audienciaJAVSTransporteWSDTO.setPasswordJAVS(salaJAVS.getPassword());
					audienciaJAVSTransporteWSDTO.setUsuarioJAVS(salaJAVS.getUsuario());

				}
			}
		}
		
		audienciaJAVSTransporteWSDTO.setAudiencia(numeroAudiencia);		
							
		List<DescriptorAudiencia> desAudDTO = descriptorAudienciaDAO.consultarDescriptorAudienciasPorAudiencia(numeroAudiencia);
		
		if(desAudDTO!=null && desAudDTO.size()>0){
			audienciaJAVSTransporteWSDTO.setBytesRegistroMaestroJVL(desAudDTO.get(desAudDTO.size()-1).getArchivo());
			audienciaJAVSTransporteWSDTO.setBytesDirectoriosServidorJAVS(desAudDTO.get(desAudDTO.size()-1).getPaths());			
		}
		
		return audienciaJAVSTransporteWSDTO;
	}

	@Override
	public byte[] consultarDirectoriosConsultaJAVS(Long audienciaId)
			throws NSJPNegocioException {
		if(audienciaId==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		List<DescriptorAudiencia> listDescAud = descriptorAudienciaDAO.consultarDescriptorAudienciasPorAudiencia(audienciaId);
		if(listDescAud!=null){
			byte[] cadenaRetorno = listDescAud.get(listDescAud.size()-1).getPaths();
			return cadenaRetorno;
		}
		else{
			return null;
		}
	}
	
	@Override
	public byte[] consultarRegistroMaestroJVL(Long audienciaId)
			throws NSJPNegocioException {
		if(audienciaId==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		List<DescriptorAudiencia> listDescAud = descriptorAudienciaDAO.consultarDescriptorAudienciasPorAudiencia(audienciaId);
		if(listDescAud!=null){
			byte[] cadenaArchivo = listDescAud.get(listDescAud.size()-1).getArchivo();
			return cadenaArchivo;
		}
		else{
			return null;
		}
	}
	
	@Override
	public String datosConexion(Long audienciaId)
			throws NSJPNegocioException {
		if(audienciaId==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		Audiencia audiencia = audienciaDAO.read(audienciaId);
		String cadenaDatosJAVS = "";
		if(audiencia!=null &&
		   audiencia.getSalaAudiencia()!=null &&
		   audiencia.getSalaAudiencia().getSalaJAVS()!=null &&
		   audiencia.getSalaAudiencia().getSalaJAVS().getDireccionIP()!=null &&
		   audiencia.getSalaAudiencia().getSalaJAVS().getPassword()!=null &&
		   audiencia.getSalaAudiencia().getSalaJAVS().getUsuario()!=null){
				cadenaDatosJAVS = audiencia.getSalaAudiencia().getSalaJAVS().getDireccionIP().toString() + "," +
								  audiencia.getSalaAudiencia().getSalaJAVS().getPassword().toString() + "," +
								  audiencia.getSalaAudiencia().getSalaJAVS().getUsuario().toString();		
		} 		
		return cadenaDatosJAVS;
	}
}
