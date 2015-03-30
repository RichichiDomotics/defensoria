/**
* Nombre del Programa : IngresarMedidasCautelaresServiceImpl.java
* Autor                            : Hugo Serrano
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 8 Jul 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del Servicio de Ingresar Medidas Cautelares
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
package mx.gob.segob.nsjp.service.medidascautelares.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.MedidaCautelarDAO;
import mx.gob.segob.nsjp.dao.forma.FormaDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.medida.MedidaAdjuntosDAO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.MedidaAdjuntos;
import mx.gob.segob.nsjp.model.MedidaAdjuntosId;
import mx.gob.segob.nsjp.model.MedidaCautelar;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.medidascautelares.IngresarMedidasCautelaresService;
import mx.gob.segob.nsjp.service.medidascautelares.impl.transform.MedidaCautelarTransformer;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del Servicio de Ingresar Medidas Cautelares
 * @version 1.0
 * @author Tattva-IT
 *
 */

@Service
@Transactional
public class IngresarMedidasCautelaresServiceImpl implements
		IngresarMedidasCautelaresService {

	@Autowired
	private MedidaCautelarDAO medidaCautelarDAO;
	@Autowired
	private ClienteGeneralService clienteGeneralService;
	@Autowired
	private GenerarFolioSolicitudService generarFolioSolicitudService;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired
	private ConfActividadDocumentoDAO confActividadDocumentoDAO;
	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	private FormaDAO formaDAO;
	@Autowired
	private MedidaAdjuntosDAO medidaAdjuntosDAO;
	@Autowired
	private DocumentoDAO documentoDAO;


	private final static Logger logger = Logger.getLogger(IngresarMedidasCautelaresServiceImpl.class);


	@Override
	public Long ingresarMedidaCautelar(MedidaCautelarDTO medidaDTO)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("::::::::::::::::SERVICIO INGRESAR MEDIDAS CAUTELARES:::::::::::::::::::::");
		
		
		if (medidaDTO == null || medidaDTO.getFechaInicio() == null
				|| medidaDTO.getInvolucrado() == null
				|| medidaDTO.getInvolucrado().getElementoId() == null
				|| medidaDTO.getValorTipoMedida() == null
				|| medidaDTO.getValorTipoMedida().getIdCampo() == null
				|| medidaDTO.getExpedienteDTO() == null
				|| medidaDTO.getExpedienteDTO().getNumeroExpedienteId() == null
				|| medidaDTO.getExpedienteDTO().getExpedienteId() == null
				|| medidaDTO.getEstatus() == null
				|| medidaDTO.getEstatus().getIdCampo() == null
				|| medidaDTO.getUsuario() == null
				|| medidaDTO.getUsuario().getFuncionario() == null) {


			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		//OBTENER CONF ACTIVIDAD DOCUMENTO
		ConfActividadDocumento confActividadDocumento = confActividadDocumentoDAO
				.read(mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento.GENERAR_MEDIDA_CAUTELAR
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


		MedidaCautelar medidaBD = MedidaCautelarTransformer.transformarMedidaCautelar(medidaDTO);

		logger.debug("ExpedienteID - Long " + medidaBD.getNumeroExpediente().getNumeroExpedienteId());
		logger.debug("InvolucradoID Long- " + medidaBD.getInvolucrado().getElementoId());
		logger.debug("Periodicidad - Long " + medidaBD.getValorPeriodicidad().getValorId());
		logger.debug("TipoMedida - Long " + medidaBD.getValorTipoMedida());
		logger.debug("FolioDocumento - Long " + medidaBD.getFolioDocumento());
		logger.debug("DescripcionMedida - Long " + medidaBD.getDescripcionMedida());
		logger.debug("Estatus - Long " + medidaBD.getEstatus());
		logger.debug("NumeroCausa - String " + medidaBD.getNumeroCausa());
		logger.debug("NumCarpetaEjecucion - String " + medidaBD.getNumeroCarpetaEjecucion());
		logger.debug("JuezOrdena - String " + medidaBD.getJuezOrdena());
		logger.debug("NumeroDeCaso - String " + medidaBD.getNumeroCaso());
//		logger.debug("FuncionarioID Long- " + medidaBD.getFuncionario().getClaveFuncionario());

		medidaBD.setForma(new Forma(confActividadDocumento.getForma().getFormaId()));
		medidaBD.setTipoDocumento(new Valor(confActividadDocumento.getTipoDocumento().getValorId()));
		medidaBD.setNombreDocumento(confActividadDocumento.getTipoDocumento().getValor());
		medidaBD.setFechaCreacion(new Date());
		medidaBD.setEsActivo(true);

		medidaBD.setFolioDocumento(generarFolioSolicitudService.generarFoliodDocumento());

		logger.debug(generarFolioSolicitudService.generarFoliodDocumento());


		 //Institucion de origen
        ConfInstitucion institucionActual = confInstitucionDAO.consultarInsitucionActual();
        medidaBD.setConfInstitucion(institucionActual);

		logger.debug(institucionActual);

		//GENERAR LA ACTIVIDAD ASOCIADA AL DOCUMENTO Y AL EXPEDIENTE
		Actividad actividad = new Actividad();
		actividad.setFuncionario(FuncionarioTransformer.transformarFuncionario(medidaDTO.getUsuario().getFuncionario()));

		actividad.setExpediente((ExpedienteTransformer.transformarExpediente(medidaDTO.getExpedienteDTO())));

		logger.debug(medidaDTO.getExpedienteDTO().getNumeroExpediente());


		Valor tipoActividad = new Valor(confActividadDocumento
				.getTipoActividad().getValorId(), confActividadDocumento
				.getTipoActividad().getValor());

		logger.debug(tipoActividad.getValor());
		logger.debug(tipoActividad.getValorId());

		actividad.setTipoActividad(tipoActividad);
		actividad.setFechaCreacion(new Date());

		medidaBD.setActividad(actividad);
		actividad.setDocumento(medidaBD);

		logger.debug(medidaBD.getActividad().getActividadId());

		//TODO AGA FALTA QUE DEFINAN EL NEGOCIO PARA INCLUIR ESTOS DATOS
		//Encontrar el juez que ordena

//        List<Audiencia> audienciasInvolucrado = involucradoAudienciaDAO.consultarAudienciasDeInvolucrado(medida.getInvolucrado().getElementoId());
//        if(audienciasInvolucrado.size()>0){
//        	Audiencia audienciaInv = audienciasInvolucrado.get(0);
//        	for(FuncionarioAudiencia funcionario:audienciaInv.getFuncionarioAudiencias()){
//				if(funcionario.getFuncionario().getTipoEspecialidad() != null &&
//						TipoEspecialidad.JUEZ.getValorId().equals(funcionario.getFuncionario().getTipoEspecialidad().getValorId())){
//					medida.setJuezOrdena(funcionario.getFuncionario().getNombreCompleto());
//					break;
//				}
//			}
//        }
//        if(medida.getJuezOrdena() == null){
//        	medida.setJuezOrdena(StringUtils.EMPTY);
//        }

        Long idMedida = this.medidaCautelarDAO.create(medidaBD);

		return idMedida;
	}


    @Override
    public void desactivarMedidaCautelar(MedidaCautelarDTO medidaCautelar)
            throws NSJPNegocioException {

        if (medidaCautelar.getDocumentoId()==null){
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

       MedidaCautelar pojoBd = this.medidaCautelarDAO.read(medidaCautelar.getDocumentoId());

       if (pojoBd==null){
           throw new NSJPNegocioException(CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);
       }

       pojoBd.setEsActivo(Boolean.FALSE);

    }
    /*
     * (non-Javadoc)
     * @see mx.gob.segob.nsjp.service.medidascautelares.IngresarMedidasCautelaresService#enviarMedidaCautelarSSP(java.lang.Long)
     */
	@Override
	public void enviarMedidaCautelarInstitucion(Long medidaId, Instituciones institucionEnviar) throws NSJPNegocioException {
		MedidaCautelar medida = medidaCautelarDAO.read(medidaId);
		if(medida != null){
			try {
				logger.debug("Enviando medida cautelar : " + medidaId);
				medida.setNumeroCaso(medida.getNumeroExpediente().getExpediente().getCaso().getNumeroGeneralCaso());
				medida.setNumeroCausa(medida.getNumeroExpediente().getNumeroExpediente());
				clienteGeneralService.enviarMedidaCautelarInstitucion(medida, institucionEnviar);
				logger.debug("Medida cautelar:" + medidaId +  " - enviada a : "+ institucionEnviar);
			} catch (NSJPNegocioException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	@Override
	public Boolean actualizarEstatusMedidaCautelarInstitucion(
			Long medidaId, Instituciones institucionDestino)
			throws NSJPNegocioException {
		Boolean regreso = false;
		if(medidaId==null || institucionDestino==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		logger.debug("Medida Id : : " + medidaId);
		MedidaCautelar medida = medidaCautelarDAO.read(medidaId);

		if(medida != null){
			logger.debug("Enviando medida cautelar : " + medida.getDocumentoId());
			regreso= clienteGeneralService.actualizarEstatusMedidaCautelarInstitucion(medida, institucionDestino);
		}
		return regreso;
	}

	@Override
	public Long adjuntarDocumentoAMedida(
			DocumentoDTO documentoDTO, MedidaDTO medidaDTO,
			TipoDocumento tipoDocumento) throws NSJPNegocioException {

		if (medidaDTO == null
				|| medidaDTO.getDocumentoId() == null
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

		if(idArchivo != null && medidaDTO.getDocumentoId() != null){

			MedidaAdjuntosId medidaAdjuntoId = new MedidaAdjuntosId(
					medidaDTO.getDocumentoId(), idArchivo);

			MedidaAdjuntos medidaAdjBD = new MedidaAdjuntos();
			medidaAdjBD.setId(medidaAdjuntoId);

			medidaAdjuntoId = medidaAdjuntosDAO.create(medidaAdjBD);

			if(medidaAdjuntoId.getArchivoDigitalId() != null){
				ArchivoDigId = medidaAdjuntoId.getArchivoDigitalId();
			}
		}

		return ArchivoDigId;
	}

}
