/**
* Nombre del Programa : ConsultarDocumentoServiceImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 2 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servicio para realizar las consultas de Documento
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
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoForma;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.AudienciaDocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.MandamientoAdjuntosDAO;
import mx.gob.segob.nsjp.dao.documento.NotaDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.medida.MedidaAdjuntosDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.documento.NotaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Nota;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.NotaTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ActividadTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para realizar las consultas de Documento.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Service
@Transactional
public class ConsultarDocumentoServiceImpl implements ConsultarDocumentoService {

	/**
	 * 
	 */
	public final static Logger LOGGER = Logger.getLogger(ConsultarDocumentoServiceImpl.class); 

	@Autowired
	private DocumentoDAO documentoDAO;
	@Autowired
	private NotaDAO notaDAO;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private AudienciaDocumentoDAO audienciaDocumentoDAO;
	@Autowired
	private MandamientoAdjuntosDAO mandamientoAdjuntoDAO;
	@Autowired
	private MedidaAdjuntosDAO medidaAdjuntosDAO;
	/*/*ByYolo*/
	@Autowired
	private ExpedienteDAO expedienteDAO;
	
	@Override
	public List<DocumentoDTO> consultarDocumentosExpediente(ExpedienteDTO expedienteDTO, UsuarioDTO usuarioDTO)
			throws NSJPNegocioException {

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("/**** SERVICIO PARA CONSULTAR DOCUMENTOS DE UN EXPEDIENTE ****/");
		
		if (expedienteDTO==null || usuarioDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		/*se instancia nuevo usuariodto para cumplir con la firma del metodo "documentoDAO.consultarDocumentoPorExpediente"
		 *  y se envia usrDTO null para no afectar la implementacion/*byYolo*/
		UsuarioDTO usrDTO = new UsuarioDTO();
		List<Documento> documentosExpediente = documentoDAO.consultarDocumentoPorExpediente(expedienteDTO.getExpedienteId(), usrDTO);
		
		List<DocumentoDTO> documentosDTO = new ArrayList<DocumentoDTO>();
		for (Documento documento : documentosExpediente) {
			if (usuarioDTO.getArea().getAreaId().equals(new Long(Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal()))) {
				if (documento.getForma().getFormaId().equals(new Long(1)))
					documentosDTO.add(DocumentoTransformer.transformarDocumento(documento));
			}			
		}
		
		return documentosDTO;
	}

	@Override
	public List<NotaDTO> consultarNotasPorDocumento(Long idDocumento) throws NSJPNegocioException{
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Servicio para consultar las Notas asociadas a un documento.");
		
		if (idDocumento==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		List<NotaDTO> lNotasDTO = new ArrayList<NotaDTO>();
		
		List<Nota> lNotas = notaDAO.consultarNotasPorDocumento(idDocumento);
		
		for (Nota nota : lNotas) {
			lNotasDTO.add(NotaTransformer.transformarNota(nota));
		}
		return lNotasDTO;
	}
	
	@Override
	public List<DocumentoDTO> consultarDocumentosExpediente(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("/**** SERVICIO PARA CONSULTAR DOCUMENTOS DE UN EXPEDIENTE ****/");
		}
		
		if (expedienteDTO.getNumeroExpedienteId()==null && expedienteDTO.getExpedienteId()==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
					
		if(expedienteDTO.getExpedienteId() == null){
			NumeroExpediente loNumExp = numeroExpedienteDAO.read(expedienteDTO.getNumeroExpedienteId());    	
			expedienteDTO.setExpedienteId(loNumExp.getExpediente().getExpedienteId());
		}
    	/*1 Linea. se pasa el usuarioDTO para condicionar la creacion del query/*byYolo*/
		List<Documento> documentosExpediente = documentoDAO.consultarDocumentoPorExpediente(expedienteDTO.getExpedienteId(), expedienteDTO.getUsuario());
		
		/*Inicio. se busca expediente padre para hacer la consulta y agregar los documentos generados en UAVD/*byYolo*/
		if(expedienteDTO.getUsuario()!=null)
		if(expedienteDTO.getUsuario().getRolActivo()!=null)
			if(expedienteDTO.getUsuario().getRolActivo().equals("atpenal")
					||expedienteDTO.getUsuario().getRolActivo().equals("coordinadorAmp")
					||expedienteDTO.getUsuario().getRolActivo().equals("agentemp")){
			
				List<NumeroExpediente> numeroExpedienteXIdYArea=null;
				List<NumeroExpediente> numeroExpedienteXNumeroExpedientePadreId=null;
				List<Documento> documentosExpedienteEnUAVD=null;
				long numeroExpedientePadreId=0L;
				List<Long> numeroExpedienteIdAConsultar = new ArrayList<Long>();
				
				if (expedienteDTO.getExpedienteId()!=null){
					numeroExpedienteXIdYArea = numeroExpedienteDAO.consultarNumeroExpedientes(expedienteDTO.getExpedienteId(), (long)(Areas.UNIDAD_INVESTIGACION.ordinal()));
				}
				//solo se valida el primer index debido a un solo cNumeroExpediente ('%AtePenal%' � '%Unidad%') de la tabla NumeroExpediente puede ser padre de varios cNumeroExpediente '%Vict%'
				//esto solo limita la busqueda de 1 expediente padre en la vista de documentos de AgenteMP o CoordinadorAMP
				if(!numeroExpedienteXIdYArea.isEmpty())
				if (numeroExpedienteXIdYArea.get(0).getNumeroExpedienteId()!=null){
					numeroExpedientePadreId =numeroExpedienteXIdYArea.get(0).getNumeroExpedienteId();
				}
				
				if (numeroExpedientePadreId!=0){
					Funcionario funcionario = new Funcionario();
					numeroExpedienteXNumeroExpedientePadreId = numeroExpedienteDAO.consultarNumeroExpedientePorFiltro(null, null, funcionario, null, numeroExpedientePadreId);
				}
				
				if (numeroExpedienteXNumeroExpedientePadreId!=null){
					for (int i = 0; i < numeroExpedienteXNumeroExpedientePadreId.size(); i++) {
						numeroExpedienteIdAConsultar.add(expedienteDAO.consultarExpedienteIdPorNumeroExpediente(numeroExpedienteXNumeroExpedientePadreId.get(i).getNumeroExpediente()));
					}	 
				}
				
				//se agregan los expedientes generaddos en UAVD a los mostrados originalmente 
				if (!numeroExpedienteIdAConsultar.isEmpty()){
					for (int i = 0; i < numeroExpedienteIdAConsultar.size(); i++) {
						documentosExpedienteEnUAVD=null;
						documentosExpedienteEnUAVD=documentoDAO.consultarDocumentoPorExpediente(numeroExpedienteIdAConsultar.get(i), expedienteDTO.getUsuario());
						for (int j = 0; j < documentosExpedienteEnUAVD.size(); j++) {
							documentosExpediente.add(documentosExpedienteEnUAVD.get(j));	
						}
					}	
				}			
		}
		/*fin/*byYolo*/
		
		
		List<DocumentoDTO> documentosDTO = new ArrayList<DocumentoDTO>();
		for (Documento documento : documentosExpediente) {
				documentosDTO.add(DocumentoTransformer.transformarDocumento(documento));
			}		
		
		return documentosDTO;
	}

	
	@Override
	public List<DocumentoDTO> consultarDocumentosIdSeleccionados(String idSeleccionados)
			throws NSJPNegocioException {

		List<Documento> documentosExpediente = documentoDAO.consultarDocumentoPorIds( idSeleccionados );
		
		List<DocumentoDTO> documentosDTO = new ArrayList<DocumentoDTO>();
		for (Documento documento : documentosExpediente) {
				documentosDTO.add(DocumentoTransformer.transformarDocumento(documento));
		}
		return documentosDTO;
	}
	
	
	@Override
	public List<DocumentoDTO> consultarDocumentosAudienciaByTipoForma(
			AudienciaDTO audienciaDTO, TipoForma tipoForma) throws NSJPNegocioException {
		List<Documento> documentos = documentoDAO.consultarDocumentosAudienciaByTipoForma(audienciaDTO.getId(), tipoForma.getValorId());
		List<DocumentoDTO> documentosDTO = new ArrayList<DocumentoDTO>();
		for (Documento documento : documentos) {
			documentosDTO.add(DocumentoTransformer.transformarDocumento(documento));
		}
		
		return documentosDTO;
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService#consultarDetalleNotaPorId(java.lang.Long)
	 */
	@Override
	public NotaDTO consultarDetalleNotaPorId(Long notaId)
			throws NSJPNegocioException {
		return NotaTransformer.transformarNota(notaDAO.read(notaId));
	}


    /**
     * Consulta los Documentos que estén asociados a un expediente y  Usuario de Reinsercion Social
     * 
     * @param usuario
     *            El usuario del que se consultan sus documentos
     * @param documento
     *            Los datos del documento solicitado, por default el NumeroExpediente_id.
     * @return El listado de documentos asociados al Usuario. Si el usuario no
     *         existe en la base de datos o si no tiene documentos asociados, se
     *         regresa la lista vacia.
     */    
	public List<DocumentoDTO> consultarDocumentosReinsercionSocial(FuncionarioDTO funcionarioDTO, DocumentoDTO documentoDTO) throws NSJPNegocioException {
		Funcionario funcionario = FuncionarioTransformer.transformarFuncionario(funcionarioDTO);
		if (funcionario != null 
				&& funcionario.getUsuario() == null){
			funcionario.setUsuario(UsuarioTransformer.transformarUsuarioMinimo(funcionarioDTO.getUsuario()));
		}
		Documento documento = DocumentoTransformer.transformarDocumentoDTO(documentoDTO);
		ExpedienteDTO expedienteDTO = documentoDTO.getExpedienteDTO();
		NumeroExpediente numeroExpediente = new NumeroExpediente(
				expedienteDTO.getNumeroExpedienteId(),
				expedienteDTO.getNumeroExpediente(),
				null);
		
		List<Documento> lstDocumentos = documentoDAO.consultarDocumentosReinsercionSocial(funcionario, documento, numeroExpediente);
		List<DocumentoDTO>  lstDocumentosDTO = new ArrayList<DocumentoDTO>();
		for (Documento tmp : lstDocumentos){
			DocumentoDTO tmpDTO = DocumentoTransformer.transformarDocumento(tmp);
			lstDocumentosDTO.add(tmpDTO);
		}
		
		return lstDocumentosDTO;
	}

	@Override
	public List<DocumentoDTO> consultarDocumentosAudiencia(
			AudienciaDTO audienciaDTO) throws NSJPNegocioException {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("/**** SERVICIO PARA CONSULTAR DOCUMENTOS DE UN EXPEDIENTE ****/");
		
		if (audienciaDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);		
		if (audienciaDTO.getId()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		List<Documento> lstDocumentos = audienciaDocumentoDAO.consultarDocumentosAudiencia(audienciaDTO.getId());
		List<DocumentoDTO>  lstDocumentosDTO = new ArrayList<DocumentoDTO>();
		for (Documento tmp : lstDocumentos){
			DocumentoDTO tmpDTO = DocumentoTransformer.transformarDocumento(tmp);
			lstDocumentosDTO.add(tmpDTO);
		}
		
		return lstDocumentosDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService#consultarDocumentoXId(java.lang.Long)
	 */
	@Override
	public DocumentoDTO consultarDocumentoXId(Long idDocumento) {
		Documento d = documentoDAO.read(idDocumento);
		return DocumentoTransformer.transformarDocumento(d);
	}

	@Override
	public List<DocumentoDTO> consultarDocumentosDeMandamientoJudicialPorExpediente(
			ExpedienteDTO expedienteDto, MandamientoDTO mandamientoDto,
			Long tipoDocumento) throws NSJPNegocioException {

		LOGGER.debug("/**** SERVICIO PARA CONSULTAR DOCUMENTOS DE UN EXPEDIENTE POR TIPO ****/");

		if (tipoDocumento == null || tipoDocumento <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		} else {
			if (tipoDocumento.equals(TipoDocumento.ARCHIVO_ADJUNTADO
					.getValorId())) {
				if (mandamientoDto == null
						|| mandamientoDto.getDocumentoId() == null) {
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				}
			} else {
				if (expedienteDto == null
						|| expedienteDto.getNumeroExpedienteId() == null
						|| expedienteDto.getNumeroExpedienteId() <= 0L) {
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				}
			}
		}

		List<DocumentoDTO> lstDocumentosDTO = new ArrayList<DocumentoDTO>();
		List<Documento> lstDocumentos = null;

		/**
		 * Si el tipo de documento que se busca es diferente a archivo adjuntado
		 * (es decir de tipo mandamiento judicial o de tipo cambio de estado de
		 * mandamiento judicial)
		 */
		if (!tipoDocumento.equals(TipoDocumento.ARCHIVO_ADJUNTADO.getValorId())) {
			lstDocumentos = documentoDAO
					.consultarDocumentosXExpedienteYTipoDocumento(
							expedienteDto.getNumeroExpedienteId(),
							tipoDocumento);
		} else { // Si el tipo de documento es Archivo Adjunto al mandamiento

			lstDocumentos = mandamientoAdjuntoDAO
					.consultarDocumentoMandamientoAdjuntoPorMandamientoId(mandamientoDto
							.getDocumentoId());
		}

		for (Documento tmp : lstDocumentos) {
			DocumentoDTO tmpDTO = DocumentoTransformer
					.transformarDocumento(tmp);
			lstDocumentosDTO.add(tmpDTO);
		}

		return lstDocumentosDTO;
	}

	
	
	@Override
	public List<DocumentoDTO> consultarDocumentosDeMedidasCautelaresPorExpedienteYTipo(
			ExpedienteDTO expedienteDto, MedidaCautelarDTO medidaCautelarDTO,
			Long tipoDocumento) throws NSJPNegocioException {

		LOGGER.debug("/**** SERVICIO PARA CONSULTAR DOCUMENTOS DE UN EXPEDIENTE POR TIPO ****/");

		if (tipoDocumento == null || tipoDocumento <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		} else {
			if (tipoDocumento.equals(TipoDocumento.ARCHIVO_ADJUNTADO
					.getValorId())) {
				if (medidaCautelarDTO == null
						|| medidaCautelarDTO.getDocumentoId() == null) {
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				}
			} else {
				if (expedienteDto == null
						|| expedienteDto.getNumeroExpedienteId() == null
						|| expedienteDto.getNumeroExpedienteId() <= 0L) {
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				}
			}
		}

		List<DocumentoDTO> lstDocumentosDTO = new ArrayList<DocumentoDTO>();
		List<Documento> lstDocumentos = null;

		/**
		 * Si el tipo de documento que se busca es diferente a archivo adjuntado
		 * (es decir de tipo medida cautelar o de tipo cambio de estado de
		 * medida cautelar)
		 */
		if (!tipoDocumento.equals(TipoDocumento.ARCHIVO_ADJUNTADO.getValorId())) {
			lstDocumentos = documentoDAO
					.consultarDocumentosXExpedienteYTipoDocumento(
							expedienteDto.getNumeroExpedienteId(),
							tipoDocumento);
		} else { // Si el tipo de documento es Archivo Adjunto a la medida cautelar

			lstDocumentos = medidaAdjuntosDAO
					.consultarDocumentosMedidaCautelarAdjuntosPorMedidaCautelarId(medidaCautelarDTO
							.getDocumentoId());
		}

		for (Documento tmp : lstDocumentos) {
			DocumentoDTO tmpDTO = DocumentoTransformer
					.transformarDocumento(tmp);
			lstDocumentosDTO.add(tmpDTO);
		}

		return lstDocumentosDTO;
	}
	
	
	@Override
	public DocumentoDTO consultarDocumentoFiltro(DocumentoDTO filtroDTO) throws NSJPNegocioException {
		DocumentoDTO documentoDTO = null;
		try {
			Documento filtro = DocumentoTransformer.transformarDocumentoDTO(filtroDTO);
			if (filtro != null  && filtroDTO != null){
				if(filtroDTO.getActividadDTO() != null ) {
					filtro.setActividad(ActividadTransformer.transformarActividadDTO(filtroDTO.getActividadDTO()));
				}
				
				if(filtroDTO.getExpedienteDTO()!= null){
					Actividad actividad = filtro.getActividad() != null ? filtro.getActividad() : new Actividad();
					Expediente expediente = ExpedienteTransformer.transformarExpediente(filtroDTO.getExpedienteDTO());
					expediente.setNumeroExpedienteId(filtroDTO.getExpedienteDTO().getNumeroExpedienteId());
					actividad.setExpediente(expediente);
					filtro.setActividad(actividad);
				}
				Documento  documento = documentoDAO.consultarDocumentoFiltro(filtro);
				documentoDTO =  DocumentoTransformer.transformarDocumento(documento);
			} 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
    		throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);
		}
		
		return documentoDTO;
	
	}
	
	@Override
	public DocumentoDTO consultarUltimoDocumentoPorActividadYExpedienteId(
			DocumentoDTO filtroDTO) throws NSJPNegocioException {

		if (filtroDTO == null
				|| filtroDTO.getActividadDTO() == null
				|| filtroDTO.getActividadDTO().getTipoActividad() == null
				|| filtroDTO.getActividadDTO().getTipoActividad().getValorId() == null
				|| filtroDTO.getExpedienteDTO() == null
				|| filtroDTO.getExpedienteDTO().getNumeroExpedienteId() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<Documento> lstDocumentos = null;

		DocumentoDTO tmpDTO = null;

		lstDocumentos = documentoDAO
				.consultarDocumentosPorTipoActividadYNumExpedienteId(filtroDTO
						.getExpedienteDTO().getNumeroExpedienteId(), filtroDTO
						.getActividadDTO().getTipoActividad().getValorId());

		if (lstDocumentos != null && lstDocumentos.size() > 0) {
			tmpDTO = DocumentoTransformer.transformarDocumento(lstDocumentos
					.get(0));
		}
		
		return tmpDTO;
	}
}
