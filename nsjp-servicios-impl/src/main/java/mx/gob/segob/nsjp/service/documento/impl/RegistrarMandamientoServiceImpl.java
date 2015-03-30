/**
 * Nombre del Programa : RegistrarMandamientoServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 7 Sep 2011
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
package mx.gob.segob.nsjp.service.documento.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.documento.RegistrarMandamientoService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para registrar el mandamiento enviado por PJ.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service("registrarMandamientoService")
@Transactional
public class RegistrarMandamientoServiceImpl implements
		RegistrarMandamientoService {
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(RegistrarMandamientoServiceImpl.class);
	@Autowired
	private MandamientoDAO mandamientoDao;
	@Autowired
	private ConfActividadDocumentoDAO confActividadDocumentoDAO;
	@Autowired
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired
	private CasoDAO casoDAO;
	@Autowired
	private BuscarExpedienteService buscarExpedienteService;
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.documento.RegistrarMandamientoService#
	 * registrarMandamiento(mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO)
	 */
	@Override
	public void registrarMandamiento(MandamientoWSDTO mandamientoWSDTO)
			throws NSJPNegocioException {

		Mandamiento mandamiento = new Mandamiento();
		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA REGISTRAR MANDAMIENTO JUDICIAL EN PG ****/");
		}

		ExpedienteDTO expGenerado = null;
		ConfInstitucion institucionActual = confInstitucionDAO
				.consultarInsitucionActual();
		if (Instituciones.PGJ.getValorId().equals(
				institucionActual.getConfInstitucionId())
				&& mandamientoWSDTO.getNumeroCaso() != null) {

			expGenerado = new ExpedienteDTO();

			Caso casoBD = casoDAO.consultarCasoPorNumeroCaso(mandamientoWSDTO
					.getNumeroCaso());

			if (casoBD != null && casoBD.getCasoId() != null) {

				// Buscar para el usuario del Area de unidad de investigación
				CasoDTO casoDTO = new CasoDTO();
				casoDTO.setCasoId(casoBD.getCasoId());

				UsuarioDTO usuarioDTO = new UsuarioDTO();
				usuarioDTO
						.setAreaActual(new AreaDTO(Areas.UNIDAD_INVESTIGACION));
				casoDTO.setUsuario(usuarioDTO);

				List<ExpedienteDTO> respuesta = buscarExpedienteService
						.consultarExpedientesPorCaso(casoDTO);

				if (respuesta != null && !respuesta.isEmpty()
						&& respuesta.get(0) != null
						&& respuesta.get(0).getNumeroExpedienteId() != null) {
					logger.info("Numero existente!!!!");
					// Obtener el Numero de Expediente Id a asociar
					expGenerado.setNumeroExpedienteId(respuesta.get(0)
							.getNumeroExpedienteId());
					expGenerado.setExpedienteId(respuesta.get(0)
							.getExpedienteId());
				}
			}
		}
		if (expGenerado == null || expGenerado.getNumeroExpedienteId() == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		logger.debug("/**** SE OBTUVO EL EXPEDINTE CON ID :: "
				+ expGenerado.getExpedienteId() + "****/");

		Expediente expediente = expedienteDAO.read(expGenerado
				.getExpedienteId());

		Involucrado involucrado = involucradoDAO
				.consultarInvolucradoPorFolioElemento(mandamientoWSDTO
						.getFolioInvolucrado());
		mandamiento.setInvolucrado(involucrado);

		if (mandamientoWSDTO.getArchivoDigital() != null) {
			ArchivoDigital archivo = new ArchivoDigital();
			archivo.setNombreArchivo(mandamientoWSDTO.getArchivoDigital()
					.getNombreArchivo());
			archivo.setTipoArchivo(mandamientoWSDTO.getArchivoDigital()
					.getTipoArchivo());
			archivo.setContenido(mandamientoWSDTO.getArchivoDigital()
					.getContenido());
			archivo.setArchivoDigitalId(archivoDigitalDAO.create(archivo));

			mandamiento.setArchivoDigital(archivo);
		}

		mandamiento.setTextoParcial(mandamientoWSDTO.getTextoParcial());
		mandamiento.setFolioDocumento(mandamientoWSDTO.getFolioDocumento());
		if (mandamientoWSDTO.getIdTipoSentencia() != null && mandamientoWSDTO.getIdTipoSentencia() > 0) {
			mandamiento.setTipoSentencia(new Valor(mandamientoWSDTO.getIdTipoSentencia()));
		}
		if (mandamientoWSDTO.getIdTipoMandamiento() != null && mandamientoWSDTO.getIdTipoMandamiento() > 0) {
			mandamiento.setTipoMandamiento(new Valor(mandamientoWSDTO.getIdTipoMandamiento()));
		}
		mandamiento.setFechaInicial(mandamientoWSDTO.getFechaInicial());
		mandamiento.setFechaFinal(mandamientoWSDTO.getFechaFinal());
		mandamiento.setFechaEjecuacion(mandamientoWSDTO.getFechaEjecuacion());
		mandamiento.setDescripcion(mandamientoWSDTO.getDescripcion());
		mandamiento.setFechaCreacion(mandamientoWSDTO.getFechaCreacion());
		mandamiento.setEstatus(new Valor(EstatusMandamiento.NO_ATENDIDO
				.getValorId()));

		// Se obtiene de la institucion que se envia
		if (mandamientoWSDTO.getConfInstitucionId() != null) {
			mandamiento.setConfInstitucion(new ConfInstitucion(mandamientoWSDTO
					.getConfInstitucionId()));
		}

		// OBTENER CONF ACTIVIDAD DOCUMENTO
		ConfActividadDocumento confActividadDocumento = confActividadDocumentoDAO
				.read(mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento.GENERAR_MANDAMIENTO_JUDICIAL
						.getValorId());

		if (confActividadDocumento == null
				|| confActividadDocumento.getForma() == null
				|| confActividadDocumento.getForma().getFormaId() == null
				|| confActividadDocumento.getTipoDocumento() == null
				|| confActividadDocumento.getTipoDocumento().getValorId() == null
				|| confActividadDocumento.getTipoDocumento().getValor() == null
				|| confActividadDocumento.getTipoActividad() == null
				|| confActividadDocumento.getTipoActividad().getValorId() == null
				|| confActividadDocumento.getTipoActividad().getValor() == null) {

			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		mandamiento.setForma(new Forma(confActividadDocumento.getForma()
				.getFormaId()));
		mandamiento.setTipoDocumento(new Valor(confActividadDocumento
				.getTipoDocumento().getValorId()));
		mandamiento.setNombreDocumento(confActividadDocumento
				.getTipoDocumento().getValor());

		// Obtenemos el numero de expediente para obtener el funcionario
		// responsable
		NumeroExpediente numExp = numeroExpedienteDAO.read(expGenerado
				.getNumeroExpedienteId());

		// GENERAR LA ACTIVIDAD ASOCIADA AL DOCUMENTO Y AL EXPEDIENTE

		Actividad actividad = new Actividad();
		// Seteamos el funcionario responsable
		actividad.setFuncionario(numExp.getFuncionario());

		actividad.setExpediente(expediente);

		Valor tipoActividad = new Valor(confActividadDocumento
				.getTipoActividad().getValorId(), confActividadDocumento
				.getTipoActividad().getValor());

		actividad.setTipoActividad(tipoActividad);
		actividad.setFechaCreacion(new Date());

		mandamiento.setActividad(actividad);
		actividad.setDocumento(mandamiento);

		mandamiento.setDocumentoId(mandamientoDao.create(mandamiento));
	}

}
