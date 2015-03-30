/**
* Nombre del Programa : MandamientoJudicialServiceImpl.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24/08/2011
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.ResolutivoDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.MandamientoAdjuntosDAO;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.forma.FormaDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.MandamientoAdjuntos;
import mx.gob.segob.nsjp.model.MandamientoAdjuntosId;
import mx.gob.segob.nsjp.model.MedidaCautelar;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Resolutivo;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.ResolutivoTransformer;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatalogoTransformer;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoTransfromer;
import mx.gob.segob.nsjp.service.documento.MandamientoJudicialService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.MandamientoJudicialTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.infra.SSPClienteService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de negocio para la manipulación de mandamientos judiciales
 * @version 1.0
 * @author Emigdio
 *
 */
@Service
@Transactional
public class MandamientoJudicialServiceImpl implements
		MandamientoJudicialService {
	
	private final static Logger logger = Logger
		.getLogger(MandamientoJudicialServiceImpl.class);
			
	@Autowired
	private MandamientoDAO mandamientoDAO;
	@Autowired
	private InvolucradoDAO involucradoDAO;	
	@Autowired
	private DelitoDAO delitoDAO;	
	@Autowired
	private ResolutivoDAO resolutivoDAO;
	@Autowired
	private SSPClienteService sspClientService;
	@Autowired
	private GenerarFolioSolicitudService folioService;
	@Autowired
	private AudienciaDAO audienciaDAO;
	@Autowired
	private DocumentoDAO documentoDAO;
	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	private FormaDAO formaDAO;
	@Autowired
	private MandamientoAdjuntosDAO mandamientoAdjuntosDAO; 
	@Autowired
	private ConfActividadDocumentoDAO confActividadDocumentoDAO;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private ClienteGeneralService clienteGeneralService;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.MandamientoJudicialService#registrarMandamientoJudicial(mx.gob.segob.nsjp.dto.documento.MandamientoDTO)
	 */
	@Override
	public MandamientoDTO registrarMandamientoJudicial(
			MandamientoDTO mandamiento) throws NSJPNegocioException {
		
		logger.info("*****SERVICIO PARA REGISTRAR UN MANDAMIENTO JUDICIAL******"
				+ mandamiento);
		
		if (mandamiento == null || mandamiento.getResolutivo() == null
				|| mandamiento.getResolutivo().getResolutivoId() == null
				|| mandamiento.getInvolucrado() == null
				|| mandamiento.getInvolucrado().getElementoId() == null
				|| mandamiento.getInvolucrado().getElementoId() < 0
				|| mandamiento.getFechaEjecuacion() == null
				|| mandamiento.getUsuario() == null
				|| mandamiento.getUsuario().getFuncionario() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Resolutivo resol = resolutivoDAO.read(mandamiento.getResolutivo().getResolutivoId());
		
		if(resol==null){
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
			
		mandamiento.setResolutivo(ResolutivoTransformer.transformarResolutivo(resol));
		
		if(resol.getDocumento() != null && resol.getDocumento().getDocumentoId() != null){
			//El resolutivo ya tiene mandamiento
			if(resol.getDocumento().getArchivoDigital() != null){
				mandamiento.setArchivoDigital(ArchivoDigitalTransformer.transformarArchivoDigitalBasico(resol.getDocumento().getArchivoDigital()));
			}
			
			mandamiento.setDocumentoId(resol.getDocumento().getDocumentoId());
			
		}else{
			//El resolutivo NO tiene mandamiento

			//OBTENER CONF ACTIVIDAD DOCUMENTO
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
			
			//Seteamos los parametros de forma, nombre y tipo documento al mandamiento
			mandamiento.setFormaDTO(new FormaDTO(confActividadDocumento.getForma().getFormaId()));
			mandamiento.setTipoDocumentoDTO(new ValorDTO(confActividadDocumento.getTipoDocumento().getValorId()));
			mandamiento.setNombreDocumento(confActividadDocumento.getTipoDocumento().getValor());
			
			Mandamiento mandamientoBD = MandamientoJudicialTransformer.transformarMandamientoDTO(mandamiento);
			String folio= folioService.generarFoliodDocumento();
			mandamientoBD.setFolioDocumento(folio);
			mandamientoBD.setDescripcion(mandamiento.getDescripcion());
			mandamientoBD.setResolutivo(resol);
			mandamientoBD.setEstatus(new Valor(EstatusMandamiento.NO_ATENDIDO.getValorId()));
			
			mandamientoBD.setInvolucrado(new Involucrado(mandamiento.getInvolucrado().getElementoId()));
			
			//Obtener el id_Expediente de la audiencia:
			Audiencia audiencia  = audienciaDAO.consultarAudienciaById(resol.getAudiencia().getAudienciaId());
			Long expedienteId = audiencia.getExpediente().getExpedienteId();
			
			//GENERAR LA ACTIVIDAD ASOCIADA AL DOCUMENTO Y AL EXPEDIENTE
			Actividad actividad = new Actividad();
			actividad.setFuncionario(FuncionarioTransformer.transformarFuncionario(mandamiento.getUsuario().getFuncionario()));
			actividad.setExpediente(audiencia.getExpediente());
			
			Valor tipoActividad = new Valor(confActividadDocumento
					.getTipoActividad().getValorId(), confActividadDocumento
					.getTipoActividad().getValor());
			
			actividad.setTipoActividad(tipoActividad);
			actividad.setFechaCreacion(new Date());
			
			mandamientoBD.setActividad(actividad);
			actividad.setDocumento(mandamientoBD);		
			
			Long mandamientoId = mandamientoDAO.create(mandamientoBD);
			
			mandamientoBD.setDocumentoId(mandamientoId);
			resol.setDocumento(mandamientoBD);
			
			resolutivoDAO.update(resol);		
			
			mandamiento.setDocumentoId(mandamientoBD.getDocumentoId());
			
			
			
			//Si es una sentencia se debe de crear un nuevo expediente = Carpeta de ejecucion 
			
			/*if(mandamiento.getTipoMandamiento().getIdCampo().equals(TipoMandamiento.SENTENCIA.getValorId())){
				logger.info("asignarNumeroExpedienteCarpetaEjecucion sobre:"+ expedienteId);
				
				//Generar la Carpeta de Ejecucion
				ExpedienteDTO carpetaEjecucion = asignarNumeroExpedienteService.asignarNumeroExpedienteCarpetaEjecucion(expedienteId);
				
				logger.info("El involucrado a Copiar es:"+ mandamiento.getInvolucrado().getElementoId());
				InvolucradoDTO sentenciado = new InvolucradoDTO(mandamiento.getInvolucrado().getElementoId());
				sentenciado.setValorSituacionJuridica(new ValorDTO(SituacionJuridica.SENTENCIADO.getValorId()));
				
				//Obtener el documento de la sentencia
				logger.info("Documento de la sentencia **:"+ mandamientoId);
				Documento documento = documentoDAO.consultarDocumentoPorId(mandamientoId);
				DocumentoDTO documentoDTO = DocumentoTransformer.transformarDocumento(documento);
				// De requerir la creación de una nueva actividad de un tipo específico, basta con cambiarle el valor al parámetro
				// nuevaActividad = null, por el nuevo valor. En caso contrario, es el flujo normal.
				Long idDocumento= documentoService.guardarDocumento(documentoDTO,new ExpedienteDTO( carpetaEjecucion.getExpedienteId()),null);
				logger.info("Sentencia - Documento generado :" + idDocumento);
			}*/
		}
		return mandamiento;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.MandamientoJudicialService#consultarMandamientosPorNumeroExpediente(java.lang.String)
	 */
	@Override
	public List<MandamientoDTO> consultarMandamientosPorNumeroExpediente(
			String numeroExpediente,UsuarioDTO usuario) throws NSJPNegocioException {
		
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
	
		List<Mandamiento> mandamientosBD = mandamientoDAO.consultarMandamientosPorNumeroExpediente(numeroExpediente,discriminanteId);	
		List<MandamientoDTO> resultado = new ArrayList<MandamientoDTO>();
		for(Mandamiento mandamiento:mandamientosBD){
			resultado.add(MandamientoJudicialTransformer.transformarMandamiento(mandamiento));
		}
		return resultado;
	}
	
	public List<ExpedienteDTO> consultaMandamientosJudicialesPorFiltro(MandamientoDTO mandamiento)
			throws NSJPNegocioException{
		
		List<ExpedienteDTO> resultado = new ArrayList<ExpedienteDTO>();
		List<NumeroExpediente> numeroExpedientes= mandamientoDAO.consultarMandamientosJudicialesPorFiltro(mandamiento);
		ExpedienteDTO expDto = null;
		for(NumeroExpediente numExp:numeroExpedientes){
			
			expDto = new ExpedienteDTO();
			expDto.setNumeroExpedienteId(numExp.getNumeroExpedienteId());
			expDto.setNumeroExpediente(numExp.getNumeroExpediente());
			
			if(numExp.getExpediente()!=null){

				expDto.setFechaApertura(numExp.getExpediente().getFechaCreacion());
				expDto.setExpedienteId(numExp.getExpediente().getExpedienteId());
				
				List<Delito> listDelitos = delitoDAO.obtenerDelitoPorExpediente(numExp.getExpediente().getExpedienteId());

				for (Delito delito : listDelitos) {
					if (delito.getEsPrincipal() == true) {
						DelitoDTO delitoDTO = DelitoTransfromer
								.transformarDelito(delito);
						expDto.setDelitoPrincipal(delitoDTO);
					}
				}
				
				expDto.setOrigen(ValorTransformer.transformar(numExp.getExpediente().getOrigen()));
				expDto.setEstatus(ValorTransformer.transformar(numExp.getExpediente().getEstatus()));
				
	        	List<Involucrado> involucradosExp = 
	        			involucradoDAO.obtenerInvByIdExpAndCalidad(numExp.getExpediente().getExpedienteId(), Calidades.DENUNCIANTE.getValorId(), null);
	        	List<InvolucradoDTO> involucradosDTO = new ArrayList<InvolucradoDTO>();
	        	
	        	InvolucradoDTO inv = new InvolucradoDTO();
	        	
				for (Involucrado involucrado : involucradosExp) {
					inv = InvolucradoTransformer.transformarInvolucrado(involucrado);
					involucradosDTO.add(inv);
				}

	            expDto.setInvolucradosDTO(involucradosDTO);

			}
						
			resultado.add(expDto);
		}
		return resultado;				
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.MandamientoJudicialService#consultarMandamientosPorNumeroExpediente(java.lang.String)
	 */
	@Override
	public List<MandamientoDTO> consultarMandamientoPorFiltro(
			MandamientoDTO mandamientoDTO, String numeroExpediente) throws NSJPNegocioException {
		
		List<MandamientoDTO> resultado = new ArrayList<MandamientoDTO>();
		Mandamiento mandamientoBD = MandamientoJudicialTransformer.transformarMandamientoDTO(mandamientoDTO);
		
		ConfInstitucion institucionActual = mandamientoDAO.consultarInsitucionActual();
		List<Mandamiento> mandamientosBD = null;
		if(institucionActual != null && institucionActual.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())){
			mandamientosBD = mandamientoDAO.consultarMandamientoPorFiltro(mandamientoBD, numeroExpediente, Instituciones.PGJ);			
		}else{
			mandamientosBD = mandamientoDAO.consultarMandamientoPorFiltro(mandamientoBD, numeroExpediente,Instituciones.PJ);
		}
		
		 
		for(Mandamiento loMandamientoBD :mandamientosBD){
			MandamientoDTO  loMandamientoDTO = MandamientoJudicialTransformer.transformarMandamiento(loMandamientoBD);
			ExpedienteDTO loExp = new ExpedienteDTO();
			CasoDTO loCasoDTO = new CasoDTO();

			if(institucionActual != null && institucionActual.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())){
				//Se obtiene el numero de expediente y caso en base a la actividad
				if(loMandamientoBD.getActividad() != null && loMandamientoBD.getActividad().getExpediente() != null && loMandamientoBD.getActividad().getExpediente().getExpedienteId() != null){
					List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO.consultarNumeroExpedientesXIdExpAreaDiscriminante(loMandamientoBD.getActividad().getExpediente().getExpedienteId(),Areas.UNIDAD_INVESTIGACION.parseLong(),null);
					//Se obtiene el numero de expediente del area		
					if(numerosExpedientes != null && numerosExpedientes.size()> 0)
						loExp.setNumeroExpediente(numerosExpedientes.iterator().next().getNumeroExpediente());
					if(loMandamientoBD.getActividad() != null && loMandamientoBD.getActividad().getExpediente() != null && loMandamientoBD.getActividad().getExpediente().getCaso() != null){
						loCasoDTO.setNumeroGeneralCaso(loMandamientoBD.getActividad().getExpediente().getCaso().getNumeroGeneralCaso());
						loExp.setCasoDTO(loCasoDTO);							
					}
				}
				
			}else{
				//Se obtiene el numero de expediente y caso en a la audiencia asociada a un resolutivo
				if(loMandamientoBD.getResolutivo() != null && loMandamientoBD.getResolutivo().getAudiencia() != null && loMandamientoBD.getResolutivo().getAudiencia().getNumeroExpediente() != null){
					loExp.setNumeroExpediente(loMandamientoBD.getResolutivo().getAudiencia().getNumeroExpediente().getNumeroExpediente());
					if(loMandamientoBD.getResolutivo().getAudiencia().getExpediente().getCaso() != null){
						loCasoDTO.setCasoId(loMandamientoBD.getResolutivo().getAudiencia().getExpediente().getCaso().getCasoId());
						loCasoDTO.setNumeroGeneralCaso(loMandamientoBD.getResolutivo().getAudiencia().getExpediente().getCaso().getNumeroGeneralCaso());					
						loExp.setCasoDTO(loCasoDTO);
					}
				}
			}
			loMandamientoDTO.setExpedienteDTO(loExp);
			resultado.add(loMandamientoDTO);
		}
		return resultado;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.MandamientoJudicialService#actualizarMandamiento(mx.gob.segob.nsjp.dto.documento.MandamientoDTO)
	 */
	@Override
	public void actualizarMandamiento(MandamientoDTO mandamiento) throws NSJPNegocioException{

		if(mandamiento == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Mandamiento mandamientoBD = mandamientoDAO.read(mandamiento.getDocumentoId());
		if(mandamientoBD != null){
			mandamientoBD.setEstatus(CatalogoTransformer.transformValor(mandamiento.getEstatus()));
			mandamientoDAO.saveOrUpdate(mandamientoBD);
			//Se manda a actualizar el estatus en PGR
			clienteGeneralService.actualizarEstatusMandamientoInstitucion(mandamientoBD, Instituciones.PGJ);
		}
		
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.MandamientoJudicialService#enviarMandamientoJudicial(java.lang.Long)
	 */
	@Override
	public void enviarMandamientoJudicial(Long mandamientoId) throws NSJPNegocioException {
		Mandamiento mandamiento = mandamientoDAO.read(mandamientoId);
		if(mandamiento == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		sspClientService.enviarMandamiento(mandamiento);
	}

	@Override
	public Long adjuntarDocumentoAMandamientoJudicial(
			DocumentoDTO documentoDTO, MandamientoDTO mandamientoJudicialDTO,
			TipoDocumento tipoDocumento) throws NSJPNegocioException {
		
		if (mandamientoJudicialDTO == null
				|| mandamientoJudicialDTO.getDocumentoId() == null
				|| documentoDTO == null
				|| documentoDTO.getArchivoDigital() == null
				|| documentoDTO.getArchivoDigital().getContenido() == null
				|| documentoDTO.getArchivoDigital().getNombreArchivo() == null
				|| documentoDTO.getArchivoDigital().getTipoArchivo() == null) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		Documento documento = DocumentoTransformer.transformarDocumentoDTO(documentoDTO);
		ArchivoDigital archivoDig = documento.getArchivoDigital();
		
		if(archivoDig.getNombreArchivo() != null && archivoDig.getNombreArchivo().length() > 60){
			archivoDig.setNombreArchivo(archivoDig.getNombreArchivo().substring(0, 60));
		}
		if(archivoDig.getTipoArchivo() != null && archivoDig.getTipoArchivo().length()>10){
			archivoDig.setTipoArchivo(archivoDig.getTipoArchivo().substring(0,10));
		}
		
		Long idArchivo = archivoDigitalDAO.create(archivoDig);				
		ArchivoDigital archD=new ArchivoDigital();
		archD.setArchivoDigitalId(idArchivo);		
		documento.setArchivoDigital(archD);
		
		/*Obligatorios de Documento*/
		documento.setNombreDocumento(archivoDig.getNombreArchivo());
		documento.setFechaCreacion(new Date());
		//Se asigna el tipo de documento
		if(tipoDocumento != null && tipoDocumento.getValorId() > 0){
			documento.setTipoDocumento(new Valor(tipoDocumento.getValorId()));
		}
		else{
			documento.setTipoDocumento(new Valor(TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()));			
		}
		
		documento.setForma(formaDAO.consultarFormaPorId(Formas.PLANTILLA_EN_BLANCO.getValorId()));
		documentoDAO.create(documento);
		
		Long ArchivoDigId = null;
		
		if(idArchivo != null && mandamientoJudicialDTO.getDocumentoId() != null){
			
			MandamientoAdjuntosId mandamientoAdjuntoId = new MandamientoAdjuntosId(
					mandamientoJudicialDTO.getDocumentoId(), idArchivo);
			
			MandamientoAdjuntos mandamientoAdjuntosBD = new MandamientoAdjuntos();
			mandamientoAdjuntosBD.setId(mandamientoAdjuntoId);
			
			mandamientoAdjuntoId = mandamientoAdjuntosDAO.create(mandamientoAdjuntosBD);
			
			if(mandamientoAdjuntoId.getArchivoDigitalId() != null){
				ArchivoDigId = mandamientoAdjuntoId.getArchivoDigitalId();
			}
		}
			
		return ArchivoDigId;
	}

	public MandamientoDTO consultarMandamientoPorId(Long idMandamiento) throws NSJPNegocioException {		
		
		if(idMandamiento == null || idMandamiento < 0)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		ExpedienteDTO loExp = new ExpedienteDTO();
		CasoDTO loCasoDTO = new CasoDTO();
		
		Mandamiento mandamientosBD = mandamientoDAO.read(idMandamiento);
		ConfInstitucion institucionActual = mandamientoDAO.consultarInsitucionActual();
		
		
		MandamientoDTO  loMandamientoDTO = MandamientoJudicialTransformer.transformarMandamiento(mandamientosBD);

		if(institucionActual != null && institucionActual.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())){
			//Se obtiene el numero de expediente y caso en base a la actividad
			if(mandamientosBD.getActividad() != null && mandamientosBD.getActividad().getExpediente() != null && mandamientosBD.getActividad().getExpediente().getExpedienteId() != null){
				List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO.consultarNumeroExpedientesXIdExpAreaDiscriminante(mandamientosBD.getActividad().getExpediente().getExpedienteId(),Areas.UNIDAD_INVESTIGACION.parseLong(),null);
				//Se obtiene el numero de expediente del area		
				if(numerosExpedientes != null && numerosExpedientes.size()> 0)
					loExp.setNumeroExpediente(numerosExpedientes.iterator().next().getNumeroExpediente());
					loExp.setNumeroExpedienteId(numerosExpedientes.iterator().next().getNumeroExpedienteId());
				if(mandamientosBD.getActividad() != null && mandamientosBD.getActividad().getExpediente() != null && mandamientosBD.getActividad().getExpediente().getCaso() != null){
					loCasoDTO.setNumeroGeneralCaso(mandamientosBD.getActividad().getExpediente().getCaso().getNumeroGeneralCaso());
					loExp.setCasoDTO(loCasoDTO);							
				}
			}
			
		}else{
			//Se obtiene el numero de expediente y caso en a la audiencia asociada a un resolutivo
			if(mandamientosBD.getResolutivo() != null && mandamientosBD.getResolutivo().getAudiencia() != null && mandamientosBD.getResolutivo().getAudiencia().getNumeroExpediente() != null){
				loExp.setNumeroExpediente(mandamientosBD.getResolutivo().getAudiencia().getNumeroExpediente().getNumeroExpediente());
				loExp.setNumeroExpedienteId(mandamientosBD.getResolutivo().getAudiencia().getNumeroExpediente().getNumeroExpedienteId());
				if(mandamientosBD.getResolutivo().getAudiencia().getExpediente().getCaso() != null){
					loCasoDTO.setCasoId(mandamientosBD.getResolutivo().getAudiencia().getExpediente().getCaso().getCasoId());
					loCasoDTO.setNumeroGeneralCaso(mandamientosBD.getResolutivo().getAudiencia().getExpediente().getCaso().getNumeroGeneralCaso());					
					loExp.setCasoDTO(loCasoDTO);
				}
			}
		}
		loMandamientoDTO.setExpedienteDTO(loExp);
		return loMandamientoDTO; 
		
		
		
	}
	
	
	public Boolean actualizarEstatusMandamientoJudicialInstitucion(
			Long mandamientoId, Instituciones institucionDestino)
			throws NSJPNegocioException {
		Boolean regreso = false;
		if(mandamientoId==null || institucionDestino==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Mandamiento loMandamiento = mandamientoDAO.read(mandamientoId);
		if(loMandamiento != null){
			logger.debug("Enviando mandamiento Judicial: " + loMandamiento.getDocumentoId());
			regreso= clienteGeneralService.actualizarEstatusMandamientoInstitucion(loMandamiento, institucionDestino);
		}
		return regreso;
	}
}
