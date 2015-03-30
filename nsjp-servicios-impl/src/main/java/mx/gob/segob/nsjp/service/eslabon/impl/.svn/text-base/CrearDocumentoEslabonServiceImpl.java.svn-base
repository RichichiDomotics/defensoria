/**
 * 
 */
package mx.gob.segob.nsjp.service.eslabon.impl;

import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.BODY_TAG_APERTURA;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.BODY_TAG_CIERRE;

import java.io.ByteArrayOutputStream;
import java.io.StringBufferInputStream;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.HTMLUtils;
import mx.gob.segob.nsjp.dao.eslabon.EslabonDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.forma.FormaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.Eslabon;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.service.actividad.RegistrarActividadService;
import mx.gob.segob.nsjp.service.documento.AsociarDocumentoAService;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.eslabon.CrearDocumentoEslabonService;
import mx.gob.segob.nsjp.service.evidencia.impl.transform.EvidenciaTransformer;
import mx.gob.segob.nsjp.service.forma.impl.transform.FormaTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * @author rgama
 *
 */
@Service
@Transactional
public class CrearDocumentoEslabonServiceImpl implements CrearDocumentoEslabonService {
	
	private static final Logger log  = Logger.getLogger(CrearDocumentoEslabonServiceImpl.class);

	@Autowired
	private FuncionarioDAO funcionarioDAO;
	@Autowired
	private GuardarDocumentoService guardarDocumentoService;
	@Autowired
	private FormaDAO formaDAO;
	@Autowired
	private RegistrarActividadService registrarActividadService;
	@Autowired
	private EslabonDAO eslabonDAO;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private ActividadDAO actividadDAO;
	@Autowired
	private AsociarDocumentoAService documentoAService;
	
	
	
	public final static Logger logger = Logger
	.getLogger(CrearDocumentoEslabonServiceImpl.class);
	
	@Override
	public DocumentoDTO crearDocumentoEslabonService(EslabonDTO eslabon, Boolean esGuardado)
		throws NSJPNegocioException{
		if (log.isDebugEnabled())
			log.debug("/**** SERVICIO PARA GENERAR EL HTML DE UN ESLABON ****/");

		if (eslabon == null || esGuardado == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if (eslabon.getEslabonId() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		Eslabon loEslabonBD = eslabonDAO.read(eslabon.getEslabonId());
		EslabonDTO eslabonDTO = EslabonTransformer.transformarEslabon(loEslabonBD);
		EvidenciaDTO evidenciaDTO = null;
		
		if(loEslabonBD != null && loEslabonBD.getEvidencia() != null)
			evidenciaDTO = EvidenciaTransformer.transformarEvidencia(loEslabonBD.getEvidencia(),true);
		
		//Se recupera el id del Expediente en base al id del Eslabon
		Long expedienteId = eslabonDAO.obtenIdExpedienteDeUnEslabon(eslabon.getEslabonId());
		

		/* Recupera el expediente y la actividad Actual */
		Actividad actividadActual = actividadDAO
				.obtenerActividadActual(expedienteId);

		DocumentoDTO documento = null;
		// buscar si en la actividadActual existe un documento con guardado parcial
		if (actividadActual != null
				&& actividadActual.getDocumento() != null
				&& StringUtils.isNotBlank(actividadActual.getDocumento()
						.getTextoParcial())
				&& actividadActual.getDocumento().getArchivoDigital() == null) {
			documento = DocumentoTransformer
					.transformarDocumento(actividadActual.getDocumento());
		}
		if (actividadActual == null) {
			actividadActual = new Actividad();
			Funcionario claveFuncionario = funcionarioDAO
					.consultarFuncionarioXExpediente(numeroExpedienteDAO
							.consultarNumeroExpedienteXExpedienteId(expedienteId));
			actividadActual.setActividadId(registrarActividadService
					.registrarActividad(
							new ExpedienteDTO(expedienteId),
							new FuncionarioDTO(claveFuncionario
									.getClaveFuncionario()),
							Actividades.ADJUNTAR_ARCHIVO_DIGITAL.getValorId()));
		}
		// si no existe un documento con guardado parcial entonces se crea un
		// documento con el cuerpo de la
		// forma que se va a emitir en ese momento
		if (documento == null) {
			documento = new DocumentoDTO();
			if (eslabonDTO != null) {
				StringBuffer txtDocumento = new StringBuffer();
				if(evidenciaDTO != null){
					//Muestra el detalle de la evidencia
					txtDocumento.append(evidenciaDTO.toString());
					txtDocumento.append("<p/>");
				}		
				//Muestra el detalle del eslabon
				txtDocumento.append(eslabonDTO.toString());
				documento.setTextoParcial(txtDocumento.toString());
				/* Genera PDF */
				documento.setDocumentoId(guardadoDirecto(documento, eslabonDTO,	new ExpedienteDTO(expedienteId),
						esGuardado));
			}
		}
		//Se asocia el documento al Eslabon
		if(documento.getDocumentoId() != null){
			documentoAService.asociarDocumentoAEslabon(eslabonDTO, documento);
		}
			
		return documento;
	}
		private Long guardadoDirecto(DocumentoDTO documento,
			EslabonDTO eslabonDto, ExpedienteDTO expDTO,
			Boolean esGuardado) throws NSJPNegocioException {
		Double version = 1.0;
	
		ArchivoDigitalDTO archivo = null;
	
		ByteArrayOutputStream archivoPDF = null;
	
		archivoPDF = generarReportePDFDeHTML(documento.getTextoParcial());
		// Crear el archivo digital
		archivo = new ArchivoDigitalDTO();
		archivo.setContenido(archivoPDF.toByteArray());
		archivo.setNombreArchivo("Movimiento cadena de custodia");
		archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
		documento.setArchivoDigital(archivo);
		documento.setFechaCreacion(new Date());
		FormaDTO forma = FormaTransformer.transformarForma(formaDAO.consultarFormaPorId(Formas.PLANTILLA_EN_BLANCO.getValorId()));
		documento.setFormaDTO(forma);// Plantilla en blanco
		documento.setTipoDocumentoDTO(new ValorDTO(TipoDocumento.SOLICITUD
				.getValorId()));
		documento.setNombreDocumento("Movimiento cadena de custodia");
		documento.setVersion(version);
		if (esGuardado) {
			documento.setTextoParcial(null);
			// De requerir la creación de una nueva actividad de un tipo específico, basta con cambiarle el valor al parámetro
			// nuevaActividad = null, por el nuevo valor. En caso contrario, es el flujo normal.
			return guardarDocumentoService.guardarDocumento(documento, expDTO, null);
		} else
			return 0L;
	}
	
	/**
	 * Utiliza las librerías de XHTML y iText para generar un reporte en PDF a
	 * partir de un archivo XHTML
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected ByteArrayOutputStream generarReportePDFDeHTML(String xHTML) {
	
		ByteArrayOutputStream archivoPDF = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(new StringBufferInputStream(HTMLUtils
					.encodeHtmlToXhtml(BODY_TAG_APERTURA + xHTML
							+ BODY_TAG_CIERRE)));
	
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(doc, null);
	
			archivoPDF = new ByteArrayOutputStream();
	
			renderer.layout();
	
			renderer.createPDF(archivoPDF);
		} catch (Exception e) {
			logger.error(e);
		}
	
		return archivoPDF;

}


}
