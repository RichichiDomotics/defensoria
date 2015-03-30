/**
 * Nombre del Programa : ConsultarSolicitudServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci�n para la consulta de las solicitudes
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
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.solicitud.PermisoSolicitudDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.solicitud.PermisoSolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.PermisoSolicitud;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudAudienciaTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudDefensorTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudTransformer;
import mx.gob.segob.nsjp.service.usuario.UsuarioService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n para la consulta de las solicitudes.
 *
 * @version 1.0
 * @author vaguirre
 *
 */
@Repository
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ConsultarSolicitudServiceImpl implements ConsultarSolicitudService {
	private final static Logger logger = Logger
			.getLogger(ConsultarSolicitudServiceImpl.class);
	@Autowired
	private SolicitudDAO solDao;
	@Autowired
	private ExpedienteDAO expDAO;
	@Autowired
	private NumeroExpedienteDAO numExpDAO;
	@Autowired
	private CasoDAO casoDAO;

	//Enable JC. Solicitudes compartidas UAVD
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PermisoSolicitudDAO permisoSolicitudDAO;
	//Enable JC. Solicitudes compartidas UAVD

	@Override
	public List<PermisoSolicitudDTO> buscarSolicitudesConPermisoFuncionario(Long funcionarioId) throws NSJPNegocioException{
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER LAS SOLICITUDES CON PERMISO DEL FUNCIONARIO ****/");
		if (funcionarioId == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		List<PermisoSolicitud> solicitudesFuncionario = permisoSolicitudDAO.consultarSolicitudesConPermisoFuncionario(funcionarioId);
		List<PermisoSolicitudDTO> permisosSolsFuncionarioDTO = new ArrayList<PermisoSolicitudDTO>();

		for (PermisoSolicitud permisoSolicitud : solicitudesFuncionario) {
			PermisoSolicitudDTO permisoSolicitudDTO = new PermisoSolicitudDTO();
			SolicitudDTO solicitudDTO = SolicitudTransformer.solicitudTransformer(permisoSolicitud.getSolicitud());
			permisoSolicitudDTO.setSolicitudDTO(solicitudDTO);

			permisoSolicitudDTO.setFechaLimite(permisoSolicitud.getFechaLimite());
			permisoSolicitudDTO.setEsEscritura(permisoSolicitud.getEsEscritura());
			permisosSolsFuncionarioDTO.add(permisoSolicitudDTO);
		}

		return permisosSolsFuncionarioDTO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService#
	 * conultarSolicitud(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public List<SolicitudDTO> consultarSolicitudesPorExpediente(
			ExpedienteDTO filtro) throws NSJPNegocioException {
		logger.debug("filtro.getNumeroExpedienteId() :: "
				+ filtro.getNumeroExpedienteId());
		List<Solicitud> fromBD = this.solDao
				.consultarSolicitudesPorExpediente(filtro
						.getNumeroExpedienteId());

		return SolicitudAudienciaTransformer.transformarSolicitudes(fromBD);
	}

	@Override
	public SolicitudDTO obtenerSolicitud(SolicitudDTO sol)
			throws NSJPNegocioException {
		Solicitud solicitud;
		long id = sol.getDocumentoId();
        logger.debug("solicitudDTO.getDocumentoId() :: " + id);
		solicitud = solDao.consultarSolicitudPorDocumentoId(id);

		if(solicitud.getExpediente()!=null){
		NumeroExpediente numeroExp=numExpDAO.obtenerNumeroExpedienteXExpediente(solicitud.getExpediente().getExpedienteId());
		Expediente expeTemp = solicitud.getExpediente();
		expeTemp.setNumeroExpediente(numeroExp.getNumeroExpediente());
		}

		if (solicitud instanceof SolicitudDefensor) {
			logger.debug("/**** SOLICITUD DEFENSORIA ****/");

			return SolicitudDefensorTransformer.transformarSolicitudDefensoria((SolicitudDefensor)solicitud);
		}

		return SolicitudTransformer.solicitudTransformer(solicitud);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService#consultarSolicitudesPorNumeroExpedienteYTipo(java.lang.Long, mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes)
	 */
	@Override
	public List<SolicitudDTO> consultarSolicitudesPorNumeroExpedienteYTipo(
			Long numeroExpedienteId, TiposSolicitudes tipo)
			throws NSJPNegocioException {
		List<SolicitudDTO> res = new ArrayList<SolicitudDTO>();
		List<Solicitud> solsBD = solDao.consultarSolicitudesPorNumeroExpedienteYTipo(numeroExpedienteId, tipo);
		for(Solicitud sol:solsBD){
			res.add(SolicitudTransformer.solicitudTransformer(sol));
		}
		return res;
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService#consultarNumeroExpedienteDeSolicitud(java.lang.Long)
	 */
	@Override
	public Long consultarNumeroExpedienteDeSolicitud(Long solicitudId) {
		return solDao.consultarNumeroExpedienteDeSolicitud(solicitudId);
	}

	@Override
	public SolicitudDTO consultarDatosDeSolicitud(String folioSolicitud)
			throws NSJPNegocioException {

		if( folioSolicitud == null || folioSolicitud.equals(""))
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		Solicitud solBD = solDao.obtenerSolicitudPorFolio(folioSolicitud);
		return SolicitudTransformer.solicitudTransformer(solBD);
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesGeneradas(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaOrigen, Long idFuncionarioSolicitante ) throws NSJPNegocioException{
		logger.info("Servicio para consultarSolicitudesGeneradas");

		logger.info("Lista idEstatusSolicitud:"+ idEstatusSolicitud +
				"Lista idTipoSolicitud:"+ idTipoSolicitud +
				"idAreaOrigen:"+ idAreaOrigen +
				"idFuncionarioDestinatario "+ idFuncionarioSolicitante );

		//List<Solicitud> solicitudes = solDao.consultarSolicitudesGeneradas(idEstatusSolicitud, idTipoSolicitud, idAreaOrigen, idFuncionarioSolicitante );
		List<Solicitud> solicitudes = solDao.consultarSolicitudesGeneradasPorNumeroExpediente(idEstatusSolicitud, idTipoSolicitud, idAreaOrigen, idFuncionarioSolicitante,null);

		logger.info("Solicitudes:"+ solicitudes.size());
		List<SolicitudDTO> solicitudesDTO = new ArrayList<SolicitudDTO>();
		for(Solicitud sol:solicitudes){
			solicitudesDTO.add(SolicitudTransformer.solicitudTransformer(sol));
		}
		return solicitudesDTO;
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesGeneradasPorNumeroExpediente(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaOrigen, Long idFuncionarioSolicitante, String numeroExpediente  ) throws NSJPNegocioException {
		logger.info("Servicio para consultarSolicitudesGeneradasPorNumeroExpediente");

		logger.info("Lista idEstatusSolicitud:"+ idEstatusSolicitud +
				"Lista idTipoSolicitud:"+ idTipoSolicitud +
				"idAreaOrigen:"+ idAreaOrigen +
				"idFuncionarioDestinatario "+ idFuncionarioSolicitante );

		List<Solicitud> solicitudes = solDao
				.consultarSolicitudesGeneradasPorNumeroExpediente(
						idEstatusSolicitud, idTipoSolicitud, idAreaOrigen,
						idFuncionarioSolicitante, numeroExpediente);

		logger.info("Solicitudes:"+ solicitudes.size());
		List<SolicitudDTO> solicitudesDTO = new ArrayList<SolicitudDTO>();
		for(Solicitud sol:solicitudes){
			solicitudesDTO.add(SolicitudTransformer.solicitudTransformer(sol));
		}
		return solicitudesDTO;

	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesParaAtender(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaDestino, Long idFuncionarioDestinatario, Long catDiscriminanteOrigen) throws NSJPNegocioException{

		logger.info("Servicio para consultarSolicitudesParaAtender");

		logger.info("Lista idEstatusSolicitud:"+ idEstatusSolicitud +
				"Lista idTipoSolicitud:"+ idTipoSolicitud +
				"idAreaDestino:"+ idAreaDestino +
				"catDiscriminanteOrigen:"+ catDiscriminanteOrigen +
				"idFuncionarioDestinatario "+ idFuncionarioDestinatario );

		List<Solicitud> solicitudes = solDao.consultarSolicitudesParaAtender(idEstatusSolicitud, idTipoSolicitud, idAreaDestino, idFuncionarioDestinatario,catDiscriminanteOrigen);

		logger.info("Solicitudes:"+ solicitudes.size());
		List<SolicitudDTO> solicitudesDTO = new ArrayList<SolicitudDTO>();
		for(Solicitud sol:solicitudes){
			SolicitudDTO solicitudDTO=SolicitudTransformer.solicitudTransformer(sol);
			if(solicitudDTO.getExpedienteDTO()==null){
				String numeroCaso=sol.getNumeroCasoAsociado();
				Caso caso=casoDAO.consultarCasoPorNumeroCaso(numeroCaso);
				List<NumeroExpediente>list=numExpDAO.consultarNumeroExpedientePorNumeroCaso(numeroCaso);
				for (NumeroExpediente numeroExpediente : list) {
					if(numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(10L)){
						solicitudDTO.setExpedienteDTO(ExpedienteTransformer.transformaExpediente(numeroExpediente.getExpediente()));
						solicitudDTO.getExpedienteDTO().setNumeroExpediente(numeroExpediente.getNumeroExpediente());
					}

				}
			}
			solicitudesDTO.add(solicitudDTO);
		}
		return solicitudesDTO;
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesPorTipoYEstatus(
			TiposSolicitudes tipoSolicitud, EstatusSolicitud estatusSolicitud, Long claveFuncionario,UsuarioDTO usuario) throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR LAS SOLICITUDES POR TIPO Y ESTATUS ****/");

		/*
		* Usado para obtener el discriminante Id
		*/
		  long discriminanteId = 0L;


		if (usuario != null
				&& usuario.getFuncionario() != null
				&& usuario.getFuncionario().getDiscriminante() != null
				&& usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

			discriminanteId = usuario.getFuncionario().getDiscriminante()
					.getCatDiscriminanteId();
		}

		List<SolicitudDTO> solRetorno = new ArrayList<SolicitudDTO>();
		// TODO GUS: Cuando es beneficio de preliberacion consulta solicitudes normales. Identificar usos
//		if (tipoSolicitud.equals(TiposSolicitudes.BENEFICIO_PRELIBERACION)) {
//			List<SolicitudAudiencia> solicitudesAud = solicitudAudienciaDAO.consultarSolicitudesAudienciaPorTipoyEstado(tipoSolicitud, estatusSolicitud);
//
//			for (SolicitudAudiencia solicitudAudiencia : solicitudesAud) {
//				SolicitudAudienciaDTO solAudDTO = SolicitudAudienciaTransformer.transformarSolicitud(solicitudAudiencia);
//				solAudDTO.setNumCarpetaEjecucion(solicitudAudiencia.getNumeroExpediente().getNumeroExpediente());
//
//				NumeroExpediente causa = numExpDAO.obtenerCausaByExpediente(solicitudAudiencia.getNumeroExpediente().getExpediente().getExpedienteId());
//				if (causa!=null)
//					solAudDTO.setNumCausa(causa.getNumeroExpediente());
//
//				solRetorno.add(solAudDTO);
//			}
//		} else  {
			List<Solicitud> solicitudes = solDao.consultarSolicitudesPorTipoYEstatus(tipoSolicitud.getValorId(), estatusSolicitud.getValorId(), claveFuncionario,discriminanteId);

			for (Solicitud solicitud : solicitudes) {
				SolicitudDTO solDTO = new SolicitudDTO();
				solDTO = SolicitudTransformer.solicitudTransformer(solicitud);
				solDTO.setNumCarpetaEjecucion(solicitud.getNumeroExpediente().getNumeroExpediente());

				NumeroExpediente causa = numExpDAO.obtenerCausaByExpediente(solicitud.getNumeroExpediente().getExpediente().getExpedienteId());
				solDTO.setNumCausa(causa.getNumeroExpediente());

				solRetorno.add(solDTO);
			}
//		}
		logger.debug("/**** Respuesta :. " + solRetorno.size());
		return solRetorno;
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesPorTipoYNoEstatus(
			TiposSolicitudes tipoSolicitud, EstatusSolicitud estatusSolicitud, Long claveFuncionario) throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR LAS SOLICITUDES POR TIPO Y ESTATUS ****/");

		List<Solicitud> solicitudes = solDao.consultarSolicitudesPorTipoYNoEstatus(tipoSolicitud.getValorId(), estatusSolicitud.getValorId(), claveFuncionario);

		List<SolicitudDTO> solRetorno = new ArrayList<SolicitudDTO>();
		for (Solicitud solicitud : solicitudes) {
			SolicitudDTO solDTO = new SolicitudDTO();
			solDTO = SolicitudTransformer.solicitudTransformer(solicitud);
			solRetorno.add(solDTO);
		}

		return solRetorno;
	}

	@Override
	public SolicitudDTO consultarSolicitudXId(Long solicitudId)throws NSJPNegocioException{

		SolicitudDTO detSolicitud = new SolicitudDTO();
		if(solicitudId==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		Solicitud solOrigen = solDao.read(solicitudId);
		detSolicitud = SolicitudTransformer.solicitudTransformer(solOrigen);
		return detSolicitud;
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesConCriterios(
			SolicitudDTO solicitudDTO, List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, String tipoConsulta)
			throws NSJPNegocioException {
		Solicitud solicitud = SolicitudTransformer.solicitudTransformer(solicitudDTO);

		List<Solicitud> lstSolicitudes = solDao.consultarSolicitudesConCriterios(solicitud, idEstatusSolicitud, idTipoSolicitud, tipoConsulta);
		List<SolicitudDTO> lstSolicitudesDTO = new ArrayList<SolicitudDTO>();
		for (Solicitud tmp : lstSolicitudes){
			SolicitudDTO tmpDTO = SolicitudTransformer.solicitudTransformer(tmp);
			lstSolicitudesDTO.add(tmpDTO);
		}

		return lstSolicitudesDTO;
	}
}
