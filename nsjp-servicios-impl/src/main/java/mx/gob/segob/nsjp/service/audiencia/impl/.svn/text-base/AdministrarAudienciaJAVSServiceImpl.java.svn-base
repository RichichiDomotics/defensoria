/**
* Nombre del Programa : AdministrarAudienciaJAVSServiceImpl.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 8 Nov 2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusPermisosAudiencia;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.DescriptorAudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.CatEstadoPermisoDAO;
import mx.gob.segob.nsjp.dao.audiencia.PermisoAudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.SalaJAVSDAO;
import mx.gob.segob.nsjp.dao.bitacora.BitacoraDescargaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioAudienciaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.persona.CorreoElectronicoDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioDAO;
import mx.gob.segob.nsjp.dto.archivo.DescriptorAudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaJAVSTransporteDTO;
import mx.gob.segob.nsjp.dto.audiencia.PermisoAudienciaDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.tarea.EventoCitaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.BitacoraDescarga;
import mx.gob.segob.nsjp.model.CatEstadoPermiso;
import mx.gob.segob.nsjp.model.CorreoElectronico;
import mx.gob.segob.nsjp.model.DescriptorAudiencia;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.SalaJAVS;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.archivo.impl.transform.DescriptorAudienciaTransformer;
import mx.gob.segob.nsjp.service.audiencia.AdministrarAudienciaJAVSService;
import mx.gob.segob.nsjp.service.audiencia.CalcularCargaTrabajoAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarPermisosAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.CatEstadoPermisoTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.PermisoAudienciaTransformer;
import mx.gob.segob.nsjp.service.caso.BuscarCasoService;
import mx.gob.segob.nsjp.service.configuracion.ObtenerConfiguracionService;
import mx.gob.segob.nsjp.service.eventocita.ConsultarEventosCitasPorUsuarioService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.infra.JAVSClienteService;
import mx.gob.segob.nsjp.service.infra.PJClienteService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Contrato de los servicios que permiten consumir el Cliente 
 * que se conecta a los WS de .net alojados en el servidor de JAVS. 
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
@Repository
public class AdministrarAudienciaJAVSServiceImpl implements
		AdministrarAudienciaJAVSService {

	/**
     * Logger de la clase.
     */
   private final static Logger logger = Logger
           .getLogger(AdministrarAudienciaJAVSServiceImpl.class);
   
   @Autowired
   private JAVSClienteService javsClienteService;
   @Autowired
   private AudienciaDAO audienciaDAO;
   @Autowired
   private DescriptorAudienciaDAO descriptorAudienciaDAO;   
   @Autowired
   private PermisoAudienciaDAO permisoAudienciaDAO;
   @Autowired
   private UsuarioDAO usuarioDAO;
   @Autowired
   private CorreoElectronicoDAO correoElectronicoDAO;
   @Autowired
   private SalaJAVSDAO salaJAVSDAO;
   @Autowired
   private CatEstadoPermisoDAO catEstadoPermisoDAO;
   @Autowired
   private PJClienteService pjClienteService;      
   @Autowired
   CalcularCargaTrabajoAudienciaService calcularCargaTrabajoAudienciaService;
   @Autowired
   BuscarCasoService buscarCasoService;
   @Autowired
   FuncionarioDAO funcionarioDAO;
   @Autowired
   BitacoraDescargaDAO bitacoraDescargaDAO;
   @Autowired
   ObtenerConfiguracionService obtenerConfiguracionService;
   @Autowired
   private FuncionarioAudienciaDAO funcionarioAudienciaDAO;
   @Autowired
   private ConsultarEventosCitasPorUsuarioService consultarEventosCitasPorUsuarioService;
   @Autowired
   private ConsultarPermisosAudienciaService consultarPermisosAudienciaService;    	   
   
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.AdministrarAudienciaJAVSService#agendarAudiencia(java.lang.Long)
	 */
	@Override
	public Long agendarAudiencia(Long audienciaId) throws NSJPNegocioException {
		logger.info("JAVSClienteService - Servicio para agendarAudiencia: "+ audienciaId);
		
		Boolean esSalaJavs = seDesarrollaraEnSalaJAVS(audienciaId);
		
		Long idEvento=(long) ConstantesGenerales.NO_ES_JAVS;
		if(esSalaJavs ){
			Usuario usuario = consultarUsuarioPorAudiencia(audienciaId);
		
			//TODO FLUJO JAVS
			//*idEvento = javsClienteService.agendarAudiencia(audienciaId, usuario.getClaveUsuario());
		}
		return idEvento;
	}
	
	@Override
	public Long reagendarAudiencia(Long audienciaId) throws NSJPNegocioException {
		logger.info("JAVSClienteService - Servicio para agendarAudiencia: "+ audienciaId);
		
		Boolean esSalaJavs = seDesarrollaraEnSalaJAVS(audienciaId);
		Long idEvento=(long) ConstantesGenerales.NO_ES_JAVS;
		if(esSalaJavs ){
			Usuario usuario = consultarUsuarioPorAudiencia(audienciaId);
			
			//TODO FLUJO JAVS
			
			//*idEvento = javsClienteService.estadoAudiencia(audienciaId, usuario.getClaveUsuario());
			
			if(idEvento == ConstantesGenerales.AUDIENCIA_NO_ACTIVA){
				//*idEvento = javsClienteService.eliminarAudiencia(audienciaId, usuario.getClaveUsuario());
				
				if(idEvento == ConstantesGenerales.EXITO_ELIMINACION || idEvento == ConstantesGenerales.NO_HAY_AUDIENCIAS){
					//*idEvento = javsClienteService.agendarAudiencia(audienciaId, usuario.getClaveUsuario());
				}
			}						
		}
		return idEvento;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.AdministrarAudienciaJAVSService#agendarAudiencia(java.lang.Long)
	 */
	@Override
	public Boolean esJAVS(Long audienciaId) throws NSJPNegocioException {
		logger.info("JAVSClienteService - Servicio para agendarAudiencia: "+ audienciaId);
		
		Boolean esSalaJavs = seDesarrollaraEnSalaJAVS(audienciaId);
		return esSalaJavs;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.AdministrarAudienciaJAVSService#estadoAudiencia(java.lang.Long)
	 */
	@Override
	public Long consultarEstadoAudiencia(Long audienciaId) throws NSJPNegocioException {
		logger.info("JAVSClienteService - Servicio para agendarAudiencia: "+ audienciaId);
		
		Boolean esSalaJavs = seDesarrollaraEnSalaJAVS(audienciaId);
		//Long idEvento=-1L;
		Long idEvento=(long) ConstantesGenerales.NO_ES_JAVS;
		if(esSalaJavs ){
			Usuario usuario = consultarUsuarioPorAudiencia(audienciaId);
			//TODO FLUJO JAVS
			//*idEvento = javsClienteService.estadoAudiencia(audienciaId, usuario.getClaveUsuario());
		}
		return idEvento;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.AdministrarAudienciaJAVSService#consultarAudiencia(java.lang.Long)
	 */
	@Override
	public Long consultarAudiencia(Long audienciaId)
			throws NSJPNegocioException {
		logger.info("JAVSClienteService - Servicio para consultarAudiencia: "+ audienciaId);
		
		Boolean esSalaJavs = seDesarrollaraEnSalaJAVS(audienciaId);
		Long idEvento = (long) ConstantesGenerales.NO_ES_JAVS;
		if(esSalaJavs ){
			Usuario usuario = consultarUsuarioPorAudiencia(audienciaId);
			//TODO FLUJO JAVS
			//*idEvento = javsClienteService.consultarAudiencia(audienciaId, usuario.getClaveUsuario());
		}
		return idEvento;
	}

	@Override
	public Long generarPermiso(Long audienciaId, UsuarioDTO usuarioDTO, Date fechaHoy)
			throws NSJPNegocioException {
		if(audienciaId==null || audienciaId.equals(0L) || usuarioDTO==null || fechaHoy==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.info("JAVSClienteService - Servicio para generar permisos: " + audienciaId);
		
		Long idEvento = EstatusPermisosAudiencia.FALLO.getValorId();
		
		if(usuarioDTO.getClaveUsuario()==null || usuarioDTO.getClaveUsuario().equals("")){
			return idEvento;
		}

		ConfInstitucionDTO cfDTO = obtenerConfiguracionService.consultarInstitucionActual();		
		
		if(cfDTO!=null && cfDTO.getConfInstitucionId()!=null && cfDTO.getConfInstitucionId().equals(EstatusPermisosAudiencia.ES_PJ.getValorId())){
			
			List<PermisoAudienciaDTO> pesDTO = consultarPermisosAudienciaService.consultarPermisosAudienciaPorNumeroAudiencia(audienciaId, usuarioDTO.getClaveUsuario());
			
			if(pesDTO!=null && pesDTO.size()==1){
				PermisoAudienciaDTO peDTO = pesDTO.get(0);
				if(peDTO.getCatEstadoPermiso().getCatEstadoPermisoId().equals(EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId()) ||
						peDTO.getCatEstadoPermiso().getCatEstadoPermisoId().equals(EstatusPermisosAudiencia.CONCEDIDO.getValorId())){
					idEvento = peDTO.getCatEstadoPermiso().getCatEstadoPermisoId();												
				}
				else{
					peDTO.setEsVigente(false);
					permisoAudienciaDAO.update(PermisoAudienciaTransformer.transformarPermisoAudienciaBasicoDTO(peDTO));
					
					Long numeroPermiso=peDTO.getPermisoAudienciaId();
					PermisoAudienciaDTO newpeDTO = new PermisoAudienciaDTO();
					newpeDTO.setPermisoAudienciaId(numeroPermiso);
					Audiencia audiencia = audienciaDAO.read(audienciaId);
					newpeDTO.setAudiencia(AudienciaTransformer.transformarBasico(audiencia));
					newpeDTO.setEsVigente(true);
					newpeDTO.setEsExterno(false);
					Long valor = EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId();
					CatEstadoPermiso ep = catEstadoPermisoDAO.read(valor);
					newpeDTO.setCatEstadoPermiso(CatEstadoPermisoTransformer.transformarEstadoPermiso(ep));
					newpeDTO.setUsuario(usuarioDTO);
					newpeDTO.setConfInstitucion(cfDTO);
					newpeDTO.setFuncionarioExterno(null);
					newpeDTO.setUsuarioAsignador(null);
					newpeDTO.setFechaSolicitud(fechaHoy);
					newpeDTO.setFechaAsignacion(null);

					permisoAudienciaDAO.create(PermisoAudienciaTransformer.transformarPermisoAudienciaBasicoDTO(newpeDTO));

					idEvento = EstatusPermisosAudiencia.NUEVA_SOLICITUD.getValorId();
				}
			}
			else if(pesDTO==null || pesDTO.size()==0){

				PermisoAudienciaDTO newpeDTO = new PermisoAudienciaDTO();
				Audiencia audiencia = audienciaDAO.read(audienciaId);
				newpeDTO.setAudiencia(AudienciaTransformer.transformarBasico(audiencia));
				newpeDTO.setEsVigente(true);
				newpeDTO.setEsExterno(false);
				Long valor = EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId();
				CatEstadoPermiso ep = catEstadoPermisoDAO.read(valor);
				newpeDTO.setCatEstadoPermiso(CatEstadoPermisoTransformer.transformarEstadoPermiso(ep));
				newpeDTO.setUsuario(usuarioDTO);
				newpeDTO.setConfInstitucion(cfDTO);
				newpeDTO.setFuncionarioExterno(null);
				newpeDTO.setUsuarioAsignador(null);
				newpeDTO.setFechaSolicitud(fechaHoy);
				newpeDTO.setFechaAsignacion(null);

				permisoAudienciaDAO.create(PermisoAudienciaTransformer.transformarPermisoAudienciaBasicoDTO(newpeDTO));
				
				idEvento = EstatusPermisosAudiencia.NUEVA_SOLICITUD.getValorId();
			}
		}

		return idEvento;
	}

	@Override
	public Long generarPermisoExterno(Long audiencia, UsuarioDTO usuarioDTO, Long ConfInstId)
			throws NSJPNegocioException {
		if(audiencia==null || usuarioDTO==null || ConfInstId==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.info("JAVSClienteService - Servicio para generar permisos audiencia: " + audiencia);
		
		//ConfInstId
		String nombreFuncionario="";
		String apPatFuncionario="";
		String apMatFuncionario="";
		String nombreAreaFuncionario="";
		String nombrePuestoFuncionario="";
		String emailFuncionario="";
		Long claveFuncionario=0L;	
		
		Long idEvento = EstatusPermisosAudiencia.FALLO.getValorId();
		
		if(usuarioDTO!=null && usuarioDTO.getFuncionario()!=null){
			claveFuncionario  = usuarioDTO.getFuncionario().getClaveFuncionario();
			nombreFuncionario = usuarioDTO.getFuncionario().getNombreFuncionario();
			apPatFuncionario  = usuarioDTO.getFuncionario().getApellidoPaternoFuncionario();
			apMatFuncionario  = usuarioDTO.getFuncionario().getApellidoMaternoFuncionario();
			
			if(usuarioDTO.getFuncionario().getJerarquiaOrganizacional()!=null && usuarioDTO.getFuncionario().getJerarquiaOrganizacional().getNombre()!=null){
				nombreAreaFuncionario = usuarioDTO.getFuncionario().getJerarquiaOrganizacional().getNombre();
			}
			
			if(usuarioDTO.getFuncionario().getPuesto()!=null && usuarioDTO.getFuncionario().getPuesto().getValor()!=null){
				nombrePuestoFuncionario = usuarioDTO.getFuncionario().getPuesto().getValor();
			}
			
			if(claveFuncionario!=null){
				List<CorreoElectronico> correoElects = correoElectronicoDAO.consultarCorreosByFuncionario(claveFuncionario);
				if(correoElects!=null && !correoElects.isEmpty() && correoElects.get(0)!=null && correoElects.get(0).getDireccionElectronica()!=null){
						emailFuncionario  = correoElects.get(0).getDireccionElectronica();
				}
			}
		}
		
		if(ConfInstId!=null              && !ConfInstId.equals(0L)			    &&
 		   nombreFuncionario!=null     	 && !nombreFuncionario.trim().equals("")       &&
		   apPatFuncionario!=null      	 && !apPatFuncionario.trim().equals("")        &&
		   apMatFuncionario!=null      	 && !apMatFuncionario.trim().equals("")        &&
		   claveFuncionario!=null        && !claveFuncionario.equals(0L)){

			idEvento = pjClienteService.generarPermisoAudienciaUsuarioExterno(
				ConfInstId, nombreFuncionario, apPatFuncionario, apMatFuncionario,
				nombreAreaFuncionario, nombrePuestoFuncionario, emailFuncionario,
				claveFuncionario, audiencia);
		}
			
		return idEvento;
	}

	@Override
	public AudienciaJAVSTransporteDTO verificarPermisoExterno(Long audienciaId, UsuarioDTO usuarioDTO, ConfInstitucionDTO confInstDto)
			throws NSJPNegocioException {
		if(audienciaId==null || usuarioDTO==null || confInstDto==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		AudienciaJAVSTransporteDTO audienciaJAVSTransporteDTO = new AudienciaJAVSTransporteDTO();
		
		logger.info("JAVSClienteService - Servicio para generar permisos audiencia: " + audienciaId);
		
		//ConfInstId
		String nombreFuncionario="";
		String apPatFuncionario="";
		String apMatFuncionario="";
		String nombreAreaFuncionario="";
		String nombrePuestoFuncionario="";
		String emailFuncionario="";
		Long claveFuncionario=0L;	
				
		if(usuarioDTO!=null && usuarioDTO.getFuncionario()!=null){
			claveFuncionario  = usuarioDTO.getFuncionario().getClaveFuncionario();
			nombreFuncionario = usuarioDTO.getFuncionario().getNombreFuncionario();
			apPatFuncionario  = usuarioDTO.getFuncionario().getApellidoPaternoFuncionario();
			apMatFuncionario  = usuarioDTO.getFuncionario().getApellidoMaternoFuncionario();
			
			if(usuarioDTO.getFuncionario().getJerarquiaOrganizacional()!=null && usuarioDTO.getFuncionario().getJerarquiaOrganizacional().getNombre()!=null){
				nombreAreaFuncionario = usuarioDTO.getFuncionario().getJerarquiaOrganizacional().getNombre();
			}
			
			if(usuarioDTO.getFuncionario().getPuesto()!=null && usuarioDTO.getFuncionario().getPuesto().getValor()!=null){
				nombrePuestoFuncionario = usuarioDTO.getFuncionario().getPuesto().getValor();
			}
			
			if(claveFuncionario!=null){
				List<CorreoElectronico> correoElects = correoElectronicoDAO.consultarCorreosByFuncionario(claveFuncionario);
				if(correoElects!=null && !correoElects.isEmpty() && correoElects.get(0)!=null && correoElects.get(0).getDireccionElectronica()!=null){
						emailFuncionario  = correoElects.get(0).getDireccionElectronica();
				}
			}
		}
		
		if(confInstDto!=null              && !confInstDto.getConfInstitucionId().equals(0L)	&&
 		   nombreFuncionario!=null     	 && !nombreFuncionario.equals("")       &&
		   apPatFuncionario!=null      	 && !apPatFuncionario.equals("")        &&
		   apMatFuncionario!=null      	 && !apMatFuncionario.equals("")        &&
		   claveFuncionario!=null        && !claveFuncionario.equals(0L)){
			
			//TODO FLUJO JAVS
//			audienciaJAVSTransporteDTO = pjClienteService.verificarPermisoAudienciaUsuarioExterno(					
//				confInstDto.getConfInstitucionId(), nombreFuncionario, apPatFuncionario, apMatFuncionario,
//				nombreAreaFuncionario, nombrePuestoFuncionario, emailFuncionario,
//				claveFuncionario, audienciaId);
		}
			
		return audienciaJAVSTransporteDTO;
	}

	
	@Override
	public Long verificarPermiso(Long audienciaId, UsuarioDTO usuarioDTO)
			throws NSJPNegocioException {
		
		if(audienciaId==null || audienciaId.equals(0L) || usuarioDTO==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.info("JAVSClienteService - Servicio para consultar permisos: "+ audienciaId);
		
		Long idEvento = EstatusPermisosAudiencia.FALLO.getValorId();
		
		if(usuarioDTO.getClaveUsuario()==null || usuarioDTO.getClaveUsuario().equals("")){
			return idEvento;
		}
			
		List<PermisoAudienciaDTO> pesDTO = consultarPermisosAudienciaService.consultarPermisosAudienciaPorNumeroAudiencia(audienciaId, usuarioDTO.getClaveUsuario());
		
		if(pesDTO!=null && pesDTO.size()==1){
			PermisoAudienciaDTO peDTO = pesDTO.get(0);
			if(peDTO.getCatEstadoPermiso()!=null && peDTO.getCatEstadoPermiso().getCatEstadoPermisoId()!=null){
				idEvento = peDTO.getCatEstadoPermiso().getCatEstadoPermisoId();
				
				if(idEvento == EstatusPermisosAudiencia.CONCEDIDO.getValorId()){
					BitacoraDescarga bd = new BitacoraDescarga();
					bd.setFechaDescarga(new Date());
				  	bd.setPermisoAudiencia(PermisoAudienciaTransformer.transformarPermisoAudienciaBasicoDTO(peDTO));		  	
					bitacoraDescargaDAO.create(bd);
				}
			}
		}
		if(pesDTO.size()==0){
			idEvento = EstatusPermisosAudiencia.NO_HAY_SOLICITUD.getValorId();
		}
		
		return idEvento;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.AdministrarAudienciaJAVSService#eliminarAudiencia(java.lang.Long)
	 */
	@Override
	public Long eliminarAudiencia(Long audienciaId)
			throws NSJPNegocioException {
		
		logger.info("JAVSClienteService - Servicio para eliminarAudiencia: "+ audienciaId);
		
		Boolean esSalaJavs = seDesarrollaraEnSalaJAVS(audienciaId);
		Long idRespuesta = (long) ConstantesGenerales.NO_ES_JAVS;
		if(esSalaJavs ){
			Usuario usuario = consultarUsuarioPorAudiencia(audienciaId);
			//TODO FLUJO JAVS
			//*idRespuesta = javsClienteService.eliminarAudiencia(audienciaId, usuario.getClaveUsuario());
		}
		
		return idRespuesta;
	}

	private Usuario consultarUsuarioPorAudiencia(Long audienciaId) throws NSJPNegocioException {

		//Se requiere el id del usuario,al que se tiene asociado la audiencia
		//a través del número de Expediente, Funcionario y finalmente usuario.
		Funcionario fun = audienciaDAO.obtenerFuncionarioDeNumExpedienteDeAudiencia(audienciaId);
		if(fun==null)
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		Usuario usuario = usuarioDAO.consultarUsuarioPorClaveFuncionario(fun.getClaveFuncionario());
		
		return usuario;
	}
	
	public UsuarioDTO consultarUsuarioPorAudienciaId(Long audienciaId) throws NSJPNegocioException {
		UsuarioDTO usuaDto = new UsuarioDTO();

		usuaDto = UsuarioTransformer.transformarUsuario(consultarUsuarioPorAudiencia(audienciaId));
		return usuaDto;
	}
	
	private Boolean seDesarrollaraEnSalaJAVS(Long audienciaId) throws NSJPNegocioException {
		if(audienciaId==null || audienciaId.equals(0L)){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.info(" seDesarrollaraEnSalaJAVS: "+ audienciaId);
		Audiencia audiencia = audienciaDAO.consultarAudienciaById(audienciaId); 
		
		if(audiencia.getSalaAudiencia()!= null ){
			Long salaId = audiencia.getSalaAudiencia().getSalaAudienciaId();
			if( salaId!= null && salaId > 0){
				logger.info(" SalaID "+ salaId);
				//Verificar si existe una sala JAVS con el mismo ID de Sala Audiencia
				SalaJAVS salaJAVS = salaJAVSDAO.recuperarSalaJAVS(salaId);
				
				if(salaJAVS!= null){
					logger.info(" Si es una Sala JAVS "+ salaJAVS.getSalaJAVSId());
					return true;
				}
			}
		}
		return false;
	}
	
	public String obteniendoPathsJAVS(Long audienciaId) throws NSJPNegocioException {
		logger.info("JAVSClienteService - Servicio para consultar paths: "+ audienciaId);
		if(audienciaId==null || audienciaId.equals(0L)){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Boolean esSalaJavs = seDesarrollaraEnSalaJAVS(audienciaId);

		String path="";
		if(esSalaJavs ){
			Usuario usuario = consultarUsuarioPorAudiencia(audienciaId);
			//TODO FLUJO JAVS
			//*path = javsClienteService.obteniendoPathsJAVS(audienciaId, usuario.getClaveUsuario());
		}
		return path;
	}

	public byte[] generandoRegistroMaestroJVL(Long audienciaId) throws NSJPNegocioException {
		
		if(audienciaId==null || audienciaId.equals(0L)){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.info("JAVSClienteService - Servicio para consultar el jvl: "+ audienciaId);

		byte[] regMaestroJVL = null;
		Boolean esSalaJavs = seDesarrollaraEnSalaJAVS(audienciaId);
		if(esSalaJavs ){
			Usuario usuario = consultarUsuarioPorAudiencia(audienciaId);
			//TODO FLUJO JAVS
			//*regMaestroJVL = javsClienteService.generandoRegistroMaestroJVL(audienciaId, usuario.getClaveUsuario());
		}
		return regMaestroJVL;
	}
	
	public Long generandoConglomeradoJAVS(Long audienciaId) throws NSJPNegocioException {
		
		if(audienciaId==null || audienciaId.equals(0L)){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.info("JAVSClienteService - Servicio para consultar el jvl: "+ audienciaId);

		byte[] regMaestroJVL = null;
		String carpetasConsultaJAVS = "";		
		Long descriptorAudienciaId = 0L;
		
		Boolean esSalaJavs = seDesarrollaraEnSalaJAVS(audienciaId);
		if(esSalaJavs ){
			Usuario usuario = consultarUsuarioPorAudiencia(audienciaId);			
			
			//TODO FLUJO JAVS
			//*regMaestroJVL = javsClienteService.generandoRegistroMaestroJVL(audienciaId, usuario.getClaveUsuario());
			//*carpetasConsultaJAVS = javsClienteService.obteniendoPathsJAVS(audienciaId, usuario.getClaveUsuario());
			
			DescriptorAudienciaDTO newDesAudDTO = new DescriptorAudienciaDTO();
			DescriptorAudiencia newDesAud = new DescriptorAudiencia();
			
			AudienciaDTO aud = AudienciaTransformer.transformarBasico(audienciaDAO.read(audienciaId));
			
			newDesAudDTO.setAudiencia(aud);
			newDesAudDTO.setArchivo(regMaestroJVL);
			byte[] arregloBytes = carpetasConsultaJAVS.getBytes();
			newDesAudDTO.setPaths(arregloBytes);
			
			List<DescriptorAudiencia> desAudDTO = descriptorAudienciaDAO.consultarDescriptorAudienciasPorAudiencia(audienciaId);
			
			if(desAudDTO!=null && desAudDTO.size()>0){				
				descriptorAudienciaId = desAudDTO.get(desAudDTO.size()-1).getDescriptorAudienciaId();
			}
			
			if(descriptorAudienciaId!=0L){
				newDesAud = descriptorAudienciaDAO.read(descriptorAudienciaId);
				newDesAud = DescriptorAudienciaTransformer.transformarDescriptorAudienciaDeDTO(newDesAudDTO, newDesAud);
				descriptorAudienciaDAO.update(newDesAud);
			}
			else{
				descriptorAudienciaId = descriptorAudienciaDAO.create(DescriptorAudienciaTransformer.transformarDescriptorAudienciaDTO(newDesAudDTO));
			}
		}				
		return descriptorAudienciaId;
	}
	
	public String laSalaJAVS(Long audienciaId) throws NSJPNegocioException {
		
		logger.info(" seDesarrollaraEnSalaJAVS: "+ audienciaId);
		Audiencia audiencia = audienciaDAO.consultarAudienciaById(audienciaId); 
		String cadena="";
		
		if(audiencia.getSalaAudiencia()!= null ){
			Long salaId = audiencia.getSalaAudiencia().getSalaAudienciaId();
			if( salaId!= null && salaId > 0){
				logger.info(" SalaID "+ salaId);
				//Verificar si existe una sala JAVS con el mismo ID de Sala Audiencia
				SalaJAVS salaJAVS = salaJAVSDAO.recuperarSalaJAVS(salaId);
				
				if(salaJAVS!= null){
					logger.info(" Si es una Sala JAVS "+ salaJAVS.getSalaJAVSId());
					cadena=salaJAVS.getDireccionIP() + "," + salaJAVS.getPassword() + "," + salaJAVS.getUsuario(); 
				}
			}
		}
		return cadena;
	}	
	
	public Long cancelarAudienciaSistema(Long audienciaId) throws NSJPNegocioException {
		logger.info("Cancelando la audiencia id: "+ audienciaId);

		Long resultado=(long) ConstantesGenerales.NO_ES_JAVS;
		Audiencia loAudiencia = audienciaDAO.read(audienciaId);
		
		//Si la audiencia ya fue cancelada se notifica al usuario
		
		if(loAudiencia.getEstatus() != null && loAudiencia.getEstatus().getValorId() != null){

							//Elimina EventoCitas en las agendas de los participantes
			
			//TODO FLUJO JAVS
							//eliminarEventos(funcionarioAudienciaDAO.consultarFuncionariosPorAudiencia(audienciaId),
							//		loAudiencia.getFechaAudiencia(), loAudiencia.getFechaAudiencia());
					
							loAudiencia = audienciaDAO.read(audienciaId);
							//Se cambia el estatus de la audiencia
							loAudiencia.setEstatus(new Valor(EstatusAudiencia.SOLICITADA.getValorId()));
					
							//Libera la sala para que se pueda volver a utilizar
							loAudiencia.setDuracionEstimada(0);
							loAudiencia.setSalaAudiencia(null);
							loAudiencia.setFechaAsignacionSala(null);
							loAudiencia.setFechaAudiencia(null);
							loAudiencia.setFechaHoraFin(null);
							audienciaDAO.update(loAudiencia);							
		}
		return resultado;				
	}
	
	
	public Long cancelarAudiencia(Long audienciaId) throws NSJPNegocioException {
		
		//TODO FLUJO JAVS
		
//		logger.info("Cancelando la audiencia id: "+ audienciaId);
//		
//		if (audienciaId == null || audienciaId <= 0 ){
//			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
//		}
//			
//		
//
//		Long resultado=(long) ConstantesGenerales.NO_ES_JAVS;
//		Long estadoJAVS=0L;
//		Audiencia loAudiencia = audienciaDAO.read(audienciaId);
//		boolean bandera=true;
//		boolean esJAVS=false;
//		
//		//Si la audiencia ya fue cancelada se notifica al usuario
//		
//		if(loAudiencia.getEstatus() != null && loAudiencia.getEstatus().getValorId() != null){
//			if(loAudiencia.getEstatus().getValorId().longValue()  == EstatusAudiencia.CANCELADA.getValorId().longValue()){
//				throw new NSJPNegocioException(CodigoError.AUDIENCIA_CANCELADA);
//			}else{
		
//				//Se cancela la audiencia programada si y solo si el estatus de la audiencia es Solicitada, Programada o Reprogramada
//				if(loAudiencia.getEstatus().getValorId().longValue()  == EstatusAudiencia.SOLICITADA.getValorId().longValue() ||
//						loAudiencia.getEstatus().getValorId().longValue()  == EstatusAudiencia.PROGRAMADA.getValorId().longValue()	||
//						loAudiencia.getEstatus().getValorId().longValue()  == EstatusAudiencia.REPROGRAMADA.getValorId().longValue()
//						
//					){			
//					
//					esJAVS=seDesarrollaraEnSalaJAVS(audienciaId);
//					if(esJAVS==true){
//						estadoJAVS=consultarEstadoAudiencia(audienciaId);
//						if(estadoJAVS != ConstantesGenerales.AUDIENCIA_NO_ACTIVA){
//							bandera=false;
//						} 
//					}
//					
//					if(bandera==true){
//						
//						if(esJAVS==true){
//							//Se manda a cancelar la audiencia JAVS mediante el Cliente de Javs
//							if(audienciaId!=0L){
//								resultado=eliminarAudiencia(audienciaId);
//							}
//							
//							if(resultado==ConstantesGenerales.ERROR_ELIMINACION || resultado==ConstantesGenerales.ERROR_SERVICIO_ELIMINACION){
//								bandera=false;
//							}
//						}
//						else{
//							resultado=(long) ConstantesGenerales.NO_ES_JAVS;							
//						}
//
//						if(bandera==true){
//							//Elimina EventoCitas en las agendas de los participantes
//							eliminarEventos(funcionarioAudienciaDAO.consultarFuncionariosPorAudiencia(audienciaId),
//									loAudiencia.getFechaAudiencia(), loAudiencia.getFechaAudiencia());
//					
//							loAudiencia = audienciaDAO.read(audienciaId);
//							//Se cambia el estatus de la audiencia
//							loAudiencia.setEstatus(new Valor(EstatusAudiencia.CANCELADA.getValorId()));
//					
//							//Libera la sala para que se pueda volver a utilizar
//							loAudiencia.setDuracionEstimada(0);
//							loAudiencia.setSalaAudiencia(null);
//							audienciaDAO.update(loAudiencia);							
//						}
//					}					
//					else{
//						resultado=estadoJAVS;
//					}
//					
//				}else{
//					throw new NSJPNegocioException(CodigoError.FALLA_CANCELACION_AUDIENCIA);
//				}
//
//			}
//		}
//		return resultado;
		
		
		logger.info("*********SERVICIO PARA CANCELAR AUDIENCIA CON AUDIENCIA_ID="
				+ audienciaId);

		if (audienciaId == null || audienciaId <= 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Long resultado = (long) ConstantesGenerales.NO_ES_JAVS;

		Audiencia loAudiencia = audienciaDAO.read(audienciaId);
		// Si la audiencia ya fue cancelada se notifica al usuario

		if (loAudiencia != null && loAudiencia.getEstatus() != null
				&& loAudiencia.getEstatus().getValorId() != null) {

			if (loAudiencia.getEstatus().getValorId()
					.equals(EstatusAudiencia.CANCELADA.getValorId())) {

				throw new NSJPNegocioException(CodigoError.AUDIENCIA_CANCELADA);

			} else {
				// Se cancela la audiencia programada si y solo si el estatus de
				// la audiencia es Solicitada, Programada o Reprogramada
				if (loAudiencia.getEstatus().getValorId()
						.equals(EstatusAudiencia.SOLICITADA.getValorId())
						|| loAudiencia
								.getEstatus()
								.getValorId()
								.equals(EstatusAudiencia.PROGRAMADA
										.getValorId())
						|| loAudiencia
								.getEstatus()
								.getValorId()
								.equals(EstatusAudiencia.REPROGRAMADA
										.getValorId())) {

					// Elimina EventoCitas en las agendas de los participantes
					eliminarEventos(
							funcionarioAudienciaDAO
									.consultarFuncionariosPorAudiencia(audienciaId),
							loAudiencia.getFechaAudiencia(), loAudiencia
									.getFechaAudiencia());

					//loAudiencia = audienciaDAO.read(audienciaId);
					// Se cambia el estatus de la audiencia
					loAudiencia.setEstatus(new Valor(EstatusAudiencia.CANCELADA
							.getValorId()));

					// Libera la sala para que se pueda volver a utilizar
					loAudiencia.setDuracionEstimada(0);
					loAudiencia.setSalaAudiencia(null);
					audienciaDAO.update(loAudiencia);
				} else {
					throw new NSJPNegocioException(
							CodigoError.FALLA_CANCELACION_AUDIENCIA);
				}
			}
		} else {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		return resultado;
	}
	
	public boolean cancelarAudienciaJAVS(Long audienciaId) throws NSJPNegocioException {
		logger.info("Cancelando la audiencia id: "+ audienciaId);

		boolean esJAVS=seDesarrollaraEnSalaJAVS(audienciaId);
		if(esJAVS==true){
			eliminarAudiencia(audienciaId);			
		}
		return esJAVS;			
	}
	/**
	 * Metodo que permite eliminar de la agenda eventos asociados a una lista de Jueces
	 */
	private void eliminarEventos(List<Funcionario> jueces, Date fechaInicial, Date fechaFinal) {
		try {
			
			for (Funcionario juez : jueces) {
				List<EventoCitaDTO> eventosJuez = consultarEventosCitasPorUsuarioService.
					consultarEventosCitasPorFuncionario(new FuncionarioDTO(juez.getClaveFuncionario()), fechaInicial, fechaFinal);
				//Elimina el evento(s) del Juez
				for (EventoCitaDTO envento : eventosJuez) {
					logger.info("SE ELIMINA EL EVENTO " + envento.getEventoCitaId());
					consultarEventosCitasPorUsuarioService.eliminarEventoCita(envento);
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}		
	}
	
	
	public void actualizaCaracterAudiencia(Long audienciaId, Boolean esPublica) throws NSJPNegocioException {
		logger.info("Audiencia id: "+ audienciaId);
		logger.info("esPublica: "+ esPublica);
		
		if (audienciaId == null || audienciaId <= 0 )
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		if (esPublica == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		Audiencia loAudiencia = audienciaDAO.read(audienciaId);
		loAudiencia.setEsPublica(esPublica);
		audienciaDAO.update(loAudiencia);
	}
	
}
