/**
* Nombre del Programa : ProgramarAudienciaServiceImpl.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 23/06/2011
* Marca de cambio        : N/A
* Descripcion General    : Implementación del servicio de negocio para guardar la programación de una audiencia
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
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.SalaTemporalDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioAudienciaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.usuario.RolDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioRolDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.FuncionarioAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioAudienciaId;

import mx.gob.segob.nsjp.model.SalaAudiencia;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.UsuarioRol;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.audiencia.AdministrarAudienciaJAVSService;
import mx.gob.segob.nsjp.service.audiencia.AsignarSalaTemporalService;
import mx.gob.segob.nsjp.service.audiencia.CalcularCargaTrabajoAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.IngresarInvolucradoAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.ProgramarAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.EventoTransformer;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de negocio para guardar la programación de una audiencia
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
@Service
@Transactional
public class ProgramarAudienciaServiceImpl implements ProgramarAudienciaService {
    private static final Logger logger = Logger
    .getLogger(ProgramarAudienciaServiceImpl.class);
	@Autowired
	AudienciaDAO audienciaDAO;
	@Autowired
	SalaTemporalDAO salaTemporalDAO;
	@Autowired
	AsignarSalaTemporalService asignarSalaTemporalService;
	@Autowired
	FuncionarioDAO funcionarioDAO;
	@Autowired
	CalcularCargaTrabajoAudienciaService calcularCargaTrabajoAudienciaService;
	@Autowired
	GenerarFolioSolicitudService generarFolioService;
	@Autowired
	private IngresarInvolucradoAudienciaService audienciaIngresarInvolucradoAudienciaService;
	@Autowired
	AdministrarAudienciaJAVSService administrarAudienciaJAVSService;
	@Autowired
	FuncionarioAudienciaDAO funcionarioAudienciaDAO;
	@Autowired
	RolDAO rolDAO;
	@Autowired
	UsuarioDAO usuarioDAO;
	@Autowired
	UsuarioRolDAO usuarioRolDAO;
	
	
		



	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.ProgramarAudienciaService#guardarProgramacionAudiencia(mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO)
	 */
	@Override
	public void guardarProgramacionAudiencia(AudienciaDTO audienciaDTO) throws NSJPNegocioException {
		logger.info("guardarProgramacionAudiencia En el Servicio");
		

		Audiencia audiencia = audienciaDAO.read(audienciaDTO.getId());
		List<FuncionarioDTO> juecesSeleccionadosEnVista= new ArrayList<FuncionarioDTO>();
		juecesSeleccionadosEnVista.addAll(audienciaDTO.getFuncionarios());
		List<FuncionarioAudiencia> juecesTotalesEnBD= new ArrayList<FuncionarioAudiencia>();
		List<FuncionarioAudiencia> juecesSeleccionadosEnVistaYaExistentenEnLAAudiencia= new ArrayList<FuncionarioAudiencia>();
		List<FuncionarioAudiencia> eliminarJueces= new ArrayList<FuncionarioAudiencia>();
		List<FuncionarioAudiencia> agregarJueces= new ArrayList<FuncionarioAudiencia>();
	

			if(audiencia != null){
				

				//La udiencia ya ha sido programada por otro usuario
				if(audiencia.getFechaAsignacionSala() != null && audienciaDTO.getEsReprogramacionDeAudiencia() == false){
					throw new NSJPNegocioException(CodigoError.RANGO_FECHAS_CRUZADAS);
				}
		
				
				



				if(audiencia.getFechaAsignacionSala() != null && audienciaDTO.getEsReprogramacionDeAudiencia() == true){
					//Se cancela la audiencia y se procede a reprogramarla					
					administrarAudienciaJAVSService.cancelarAudiencia(audiencia.getAudienciaId());
					
					// Se eliminan lista de jueces asignados a la audiencia
//					for (FuncionarioAudiencia loRelacion : audiencia.getFuncionarioAudiencias()) {
//							loRelacion.setFuncionario(null);
//							loRelacion.setAudiencia(null);
//							funcionarioAudienciaDAO.delete(loRelacion);
//					}
					boolean esRolJuez;
					
					/*
					 * Recorrido para obtener los juecesTotales dentro de la tabla de FuncionariosAudiencia y los que coiciden
					 * en BD con los seleccionado en la vista (juecesExistenteEnBD)
					 */
					for (FuncionarioAudiencia loRelacion : audiencia.getFuncionarioAudiencias()) {
						esRolJuez=false;
						
						Usuario usuario = usuarioDAO.consultarUsuarioXClaveFuncionario(loRelacion.getId().getClaveFuncionario());
						
						// se valida que el usuario no este vacio para no contemplar a los usuario que llegan por WS de Procuraduria
						if(usuario!=null){
							List <UsuarioRol> usuarioRols = usuarioRolDAO.consultarRolesDeUsuario(usuario.getClaveUsuario());
							//recorro los roles del usuario para ver si alguno es juez
							for (UsuarioRol usuarioRol : usuarioRols) {
								if(usuarioRol.getId().getRolId()==24L){
									esRolJuez=true;
									break;
								}
							}	
						}
						
						if (esRolJuez) {
							juecesTotalesEnBD.add(loRelacion);
						}	
						
					}
					
					/*
					 * se carga un arreglo donde se colocan los jueces que se seleccionaron en la vista y ya se encuentran dentro
					 * de la audiencia esto para limitar los funcionarios que se agregaran y los que se eliminaran
					 */
					for (FuncionarioAudiencia juezBD : juecesTotalesEnBD) {
						for (FuncionarioDTO juezVista : juecesSeleccionadosEnVista) {
							if(juezBD.getId().getClaveFuncionario()==juezVista.getClaveFuncionario()){
								juecesSeleccionadosEnVistaYaExistentenEnLAAudiencia.add(juezBD);
							}
						}						
					}
					
					/*
					 * en caso de que los jueces seleccionados en la vista no existan en BD se eliminan todos los jueces en BD y se cargan los
					 * seleccionados en vista
					 */
					if(juecesSeleccionadosEnVistaYaExistentenEnLAAudiencia.isEmpty()){
						eliminarJueces.addAll(juecesTotalesEnBD);
						FuncionarioAudiencia juezAudiencia = null;
						for (FuncionarioDTO juezVista : audienciaDTO.getFuncionarios()) {
							juezAudiencia = new FuncionarioAudiencia();
							juezAudiencia.setId(new FuncionarioAudienciaId(audiencia.getAudienciaId(), juezVista.getClaveFuncionario()));
							agregarJueces.add(juezAudiencia);
						}
//						
					}else{
						for (FuncionarioAudiencia juezBD : juecesTotalesEnBD) {
							for (FuncionarioAudiencia juezVistaBD : juecesSeleccionadosEnVistaYaExistentenEnLAAudiencia) {
								if(juezBD.getId().getClaveFuncionario() != juezVistaBD.getId().getClaveFuncionario()){
									eliminarJueces.add(juezBD);
								}
							}
							
						for (FuncionarioDTO juezVista : juecesSeleccionadosEnVista) {
							for (FuncionarioAudiencia juezBD2 : juecesTotalesEnBD) {
								if(juezVista.getClaveFuncionario() != juezBD2.getId().getClaveFuncionario()){
									FuncionarioAudiencia juezAudiencia = new FuncionarioAudiencia();
									juezAudiencia.setId(new FuncionarioAudienciaId(audiencia.getAudienciaId(), juezVista.getClaveFuncionario()));
									agregarJueces.add(juezAudiencia);
								}
							}
						}
						
						}
						
					}

					
					
					//eliminando los jueces

					for (FuncionarioAudiencia loRelacion : audiencia.getFuncionarioAudiencias()) {
						for (FuncionarioAudiencia funcionarioAudiencia : eliminarJueces) {
							if(loRelacion.getId().getClaveFuncionario()==funcionarioAudiencia.getId().getClaveFuncionario()){
								loRelacion.setFuncionario(null);
								loRelacion.setAudiencia(null);
								funcionarioAudienciaDAO.delete(loRelacion);
							}
						}
					}
					
					
					
				} else {//Fix error: 64 Agregar jueces en audiencias programadas por encargadosala
					for (FuncionarioDTO juezVista : juecesSeleccionadosEnVista) {
						FuncionarioAudiencia juezAudiencia = new FuncionarioAudiencia();
						juezAudiencia.setId(new FuncionarioAudienciaId(audiencia.getAudienciaId(), juezVista.getClaveFuncionario()));
						agregarJueces.add(juezAudiencia);
					}					
				}
				

				


				//Asignar la fecha de fin de la audiencia apartir de la fecha de inicio mas la duración estimada
				if( audienciaDTO.getFechaEvento()!=null && audienciaDTO.getDuracionEstimada()!=null){
					Date fechaFin = DateUtils.sumarMinutos(audienciaDTO.getFechaEvento(), audienciaDTO.getDuracionEstimada());
					audienciaDTO.setFechaHoraFin(fechaFin);
					

					if (!audienciaDTO.getSala().isTemporal()) {
						Boolean respConsulta = audienciaDAO.consultarAudienciasByFechaAudienciaYSala(
								audienciaDTO.getFechaEvento(), audienciaDTO
										.getFechaHoraFin(), audienciaDTO.getSala()
										.getSalaAudienciaId());
						if(respConsulta.equals(false)){
							throw new NSJPNegocioException(CodigoError.SALA_OCUPADA);
						}
					}
				}
				

				//actualizar datos de audiencia y cear/actualizar sala o sala temporal				
				if(audienciaDTO.getSala().isTemporal()){
					audiencia.setSalaAudiencia(null);
					asignarSalaTemporalService.asignarSalaTemporal(audienciaDTO);
				}else{
					if(audiencia.getSalaTemporal() != null){
						salaTemporalDAO.delete(audiencia.getSalaTemporal());
					}
					audiencia.setSalaTemporal(null);
					audiencia.setSalaAudiencia(new SalaAudiencia(audienciaDTO.getSala().getSalaAudienciaId()));
				}	
				


				//Se actualiza el estatus a Reprogramada
				if(audiencia.getFechaAsignacionSala() != null && audienciaDTO.getEsReprogramacionDeAudiencia() == true){
					audiencia.setEstatus(new Valor(EstatusAudiencia.REPROGRAMADA.getValorId()));
				}
				else{
					audiencia.setEstatus(new Valor(EstatusAudiencia.PROGRAMADA.getValorId()));
				}
				
				


				EventoTransformer.tranformarAudienciaUpdateBasico(audiencia,audienciaDTO);
				
				


				//asignar los jueces a la audiencia
				Double cargaTrabajoAudiencia = calcularCargaTrabajoAudienciaService.calcularCargaTrabajoAudiencia(audienciaDTO);
				if(cargaTrabajoAudiencia==null || 
						Double.isInfinite(cargaTrabajoAudiencia)){
					cargaTrabajoAudiencia = 0.0;
				}
				//codigo Original comentado por yolo
//				FuncionarioAudiencia juezAudiencia = null;
//				Funcionario juezBD = null;
//				for(FuncionarioDTO juez:audienciaDTO.getFuncionarios()){
//					juezAudiencia = new FuncionarioAudiencia();
//					juezAudiencia.setId(new FuncionarioAudienciaId(audiencia.getAudienciaId(), juez.getClaveFuncionario()));
//					if(!existeFuncionarioAudiencia(juez,audiencia.getFuncionarioAudiencias())){
//						funcionarioAudienciaDAO.create(juezAudiencia);
//						logger.info("Si agrega el Juez: " +  juezAudiencia);
//					}
//					
//					juezBD = funcionarioDAO.read(juez.getClaveFuncionario());
//					if(juezBD != null){
//						juezBD.setCargaTrabajo((juezBD.getCargaTrabajo()!=null?juezBD.getCargaTrabajo().doubleValue():0.00)+
//								cargaTrabajoAudiencia.doubleValue());
//						juezBD.setEsPar(audiencia.getNumeroExpediente()!=null?audiencia.getNumeroExpediente().getEsPar():null);
//						funcionarioDAO.saveOrUpdate(juezBD);
//						logger.info("guardarProgramacionAudiencia var juezBD: "+ juezBD.getClaveFuncionario());
//					}
//					
//				}

				//codigo sustituto yolo
				Funcionario juezBD = null;
				for(FuncionarioAudiencia juez:agregarJueces){




						funcionarioAudienciaDAO.create(juez);
						logger.info("Si agrega el Juez: " +  juez);
						


					juezBD = funcionarioDAO.read(juez.getId().getClaveFuncionario());
					if(juezBD != null){
						juezBD.setCargaTrabajo((juezBD.getCargaTrabajo()!=null?juezBD.getCargaTrabajo().doubleValue():0.00)+
								cargaTrabajoAudiencia.doubleValue());
						juezBD.setEsPar(audiencia.getNumeroExpediente()!=null?audiencia.getNumeroExpediente().getEsPar():null);
						funcionarioDAO.saveOrUpdate(juezBD);
						logger.info("guardarProgramacionAudiencia var juezBD: "+ juezBD.getClaveFuncionario());
					}
					

				}
				
				//fin codigo sustituto

				audiencia.setFolioAudiencia(generarFolioService.generarFolioAudiencia());
				

				//Asociar involucrados a la Audiencia
				if( audienciaDTO.getInvolucrados()!= null &&  ! audienciaDTO.getInvolucrados().isEmpty()){
					for (InvolucradoDTO  involucradoDTO: audienciaDTO.getInvolucrados()) {
						if(involucradoDTO!=null && involucradoDTO.getElementoId()!= null)
							audienciaIngresarInvolucradoAudienciaService.asociarInvolucradoAAudiencia(involucradoDTO.getElementoId(), audienciaDTO.getId());		
					}
				}
				

				audienciaDAO.saveOrUpdate(audiencia);

				

			}
	}
	
	


	/**
	 * Quita los jueces del set de funcionariosAudiencia que no se encuentren en funcionarios
	 * @param funcionarioAudiencias
	 * @param funcionarios
	 */
	@SuppressWarnings("unused")
	private List<FuncionarioAudiencia> quitarJuecesEliminados(
			Set<FuncionarioAudiencia> funcionarioAudiencias,
			List<FuncionarioDTO> funcionarios) {
		List<FuncionarioAudiencia> aEliminar = new ArrayList<FuncionarioAudiencia>();
		boolean encontrado = false;
		for(FuncionarioAudiencia funcActual:funcionarioAudiencias){
			//buscar en la lista
			encontrado = false;
			for(FuncionarioDTO funDTO:funcionarios){
				if(funDTO.getClaveFuncionario().equals(funcActual.getId().getClaveFuncionario())){
					encontrado = true;
					break;
				}
			}
			if(!encontrado){
				aEliminar.add(funcActual);
			}
		}
		return aEliminar;
	}
	/**
	 * Verifica si existe el funcionario en la lista, para no insertarlo repetido
	 * @param juez
	 * @param funcionarioAudiencias
	 * @return
	 */
	private boolean existeFuncionarioAudiencia(FuncionarioDTO juez,
			Set<FuncionarioAudiencia> funcionarioAudiencias) {
		for(FuncionarioAudiencia funcionario:funcionarioAudiencias){
			if(funcionario.getId().getClaveFuncionario().equals(juez.getClaveFuncionario())){
				return true;
			}
		}
		return false;
	}
	@Override
	public Long crearAudienciaSiguiente(AudienciaDTO audienciaDTO)
			throws NSJPNegocioException {
		

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CREAR UNA AUDIENCIA VACÍA SIGUIENTE A UNA DADA ****/");
		

		if(audienciaDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if(audienciaDTO.getId()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		

		Audiencia audienciaEnCurso = audienciaDAO.read(audienciaDTO.getId());
		/*Compone la nueva audiencia*/
		Audiencia audienciaNueva=new Audiencia();
		audienciaNueva.setNumeroExpediente(audienciaEnCurso.getNumeroExpediente());
		audienciaNueva.setConsecutivo((short) (audienciaEnCurso.getConsecutivo()+1));
		audienciaNueva.setEstatus(new Valor(EstatusAudiencia.SOLICITADA.getValorId()));
		audienciaNueva.setTipo(audienciaEnCurso.getTipo());
		Long idAudiencia = audienciaDAO.create(audienciaNueva);
		

		return idAudiencia;
	}

}
