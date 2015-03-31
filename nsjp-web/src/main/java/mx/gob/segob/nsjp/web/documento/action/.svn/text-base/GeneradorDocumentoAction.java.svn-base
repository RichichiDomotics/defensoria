/**
 * Nombre del Programa : GeneradorDocumentoAction.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 19 May 2011
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
package mx.gob.segob.nsjp.web.documento.action;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.actividad.ActividadesRS;
import mx.gob.segob.nsjp.comun.enums.documento.OperacionDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.pdf.PDFPropiedad;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.delegate.actividad.ActividadDelegate;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.catalogo.CatUIEspecializadaDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.notificacion.NotificacionDelegate;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.delegate.reinsercion.ReinsercionDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.delegate.usuario.UsuarioDelegate;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatUIEspecializadaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.GrupoObjetosExpedienteDTO;
import mx.gob.segob.nsjp.dto.documento.ObjetoResumenDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ParametrosDocumentoDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.persona.TelefonoDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO;
import mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.web.base.action.ReporteBaseAction;
import mx.gob.segob.nsjp.web.reinsercionsocial.action.InventarioPertenenciasAction;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action encargado de recibir y procesar las solicitudes para la emisi?n de un documento
 * basado en una Forma (plantilla) de la base de datos y los datos de un expediente
 * 
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class GeneradorDocumentoAction extends ReporteBaseAction {
    
	@Autowired
	private DocumentoDelegate documentoDelegate;
	@Autowired
	private SolicitudDelegate solicituDelegate;
	@Autowired
	private ActividadDelegate actividadDelegate;
	@Autowired
	private NotificacionDelegate notificacionDelegate;
	
	@Autowired
	private FuncionarioDelegate funcionarioDelegate;
	@Autowired
	private UsuarioDelegate usuarioDelegate;
	@Autowired
	private AudienciaDelegate audienciaDelegate;
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	@Autowired
	private CatUIEspecializadaDelegate catUIEspecializadaDelegate;
	@Autowired
	private ReinsercionDelegate reinsercionDelegate;
	@Autowired
	private AsignacionProgramaDelegate asignacionProgramaDelegate;
	
	@Autowired
    private AsignarNumeroExpedienteService asignarNumeroExpedienteService;

	
	public static final String PARAM_TEXTO_DOCUMENTO = "texto";
	public static final String PARAM_GUARDAR_PARCIAL = "parcial";
	public static final String PARAM_FORMA_DOCUMENTO = "formaId";
	public static final String PARAM_IDENT_DOCUMENTO = "documentoId";
	public static final String PARAMT_TIPO_DOCUMENTO = "tipoDocumento";
	public static final String PARAMT_TIPO_OPERACION = "tipoOperacion";
	public static final Long   TIPO_DOCUMT_SOLICITUD = TipoDocumento.SOLICITUD.getValorId();
	public static final String TAMANIO_PAPEL = "seleccionTamanioPapel";
	public static final Long TIPO_DOCUMENTO_SOLICITUD = TipoDocumento.CAMBIO_DE_ESTADO_DE_MEDIDA_CAUTELAR.getValorId(); // Se agrega variable para el cambio de estado de medida cautelar
	private static final Logger logger = Logger
            .getLogger(GeneradorDocumentoAction.class);
    /**
     * Crea o actualiza un documento para el expediente que se est? trabajando actualmente
     * 
     * Parametros:
     * 
     * formaId (obligatorio) -opcional si se manda el tipo de documento- El identificador del formato a emitir (acta, denuncia, acuse de recibo, etc)
     * 
     * documentoId (opcional) - Indica un cierto ID de documento con el que se debe de trabajar en lugar de
     * crear uno nuevo. En este caso el documento se actualizar?a en lugar de crearse 
     * 
     * tipoOperacion - Identificador del tipo de operaci?n a realizar una vez que sea impreso el documento
     * <code>OperacionDocumento.ACTUALIZAR_ESTADO_SOLICITUD</code> Actualizar el estado de la solicitud
     * <code>OperacionDocumento.REGISTRAR_ORDEN_DETENCION</code> Registra un solicitud de Orden de Detencion
     * <code>OperacionDocumento.ASOCIAR_DOCUMENTO_A_RESOLUTIVO</code> Asocia el Documento a un resolutivo
     * posterior al emitir un documento 
     * Al utilizar este tipo de operaci?n se debe enviar tambi?n el par?metro de estatusSolicitud indicando
     * el estado de solicitud destino
     * 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward generarDocumento(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		    try{
		    	// se obtiene el texto del editor
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	Long nuevaActividad = NumberUtils.toLong(request.getParameter("nuevaActividad"), 0L);
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;
		    	String parametro = request.getParameter(PARAM_IDENT_DOCUMENTO);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long documentoId = 0L;
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
		    	Long tipoDocumento = 0L;

		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	logger.debug(PARAM_IDENT_DOCUMENTO + " :: " + parametro);
		    	
		    	String audienciaId = request.getParameter("audienciaId");
		    	
		    	UsuarioDTO loUsuario = getUsuarioFirmado(request); 
				FuncionarioDTO responsableDocumento = loUsuario.getFuncionario();
				
				ConfInstitucionDTO confInstitucionDTO = null;
				if(loUsuario.getInstitucion() != null) {
					confInstitucionDTO = new ConfInstitucionDTO();
					Long confInstId = loUsuario.getInstitucion().getConfInstitucionId();
					confInstitucionDTO.setConfInstitucionId(confInstId);	
				}
				
		    	if(StringUtils.isNotBlank(parametro)){
//		    		Se est? editando un documento en espec?fico
		    		documentoId = NumberUtils.toLong(parametro);
		    		documento = documentoDelegate.cargarDocumentoPorId(documentoId);
		    		forma = documento.getFormaDTO();
		    	}else{
//		    		Si es un documento nuevo se obtiene el tipo de forma que se est? editando
		            documento = new DocumentoDTO();
			    	Long formaId = NumberUtils.toLong(request.getParameter(PARAM_FORMA_DOCUMENTO), 1L);
		            forma = documentoDelegate.buscarForma(formaId);
		    	}
		    	
		    	if(forma == null){
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		    	}
		    	documento.setConfInstitucion(confInstitucionDTO);
		    	
		    	ArchivoDigitalDTO archivo = null;
		    	//FIXME La entidad Forma no contiene el campo TipoDocumento. 
//			    Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), forma.getTipoDocumentoDTO().getIdCampo());
//		    	boolean esDocumentoDeMedidaCautelar = request.getParameter("esDocumentoDeMedidaCautelar") != null; // Asi trae el valor como booleano
		    	String esDocumentoDeMedidaCautelar = (request.getParameter("esDocumentoDeMedidaCautelar") == null)? "":request.getParameter("esDocumentoDeMedidaCautelar");
		    	logger.debug(request.getParameter("esDocumentoDeMedidaCautelar") + "   String: : :" + esDocumentoDeMedidaCautelar );
		    	if (esDocumentoDeMedidaCautelar.equalsIgnoreCase("true")) //Condicion para saber si es cambio de estado de medida cautelar y mostrar documento.
		    		tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), TIPO_DOCUMENTO_SOLICITUD);
		    	else
		    		tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), TIPO_DOCUMT_SOLICITUD);
		    	logger.debug("-------------" + tipoDocumento);
		    	
			    boolean parcial = StringUtils.isNotBlank(request.getParameter(PARAM_GUARDAR_PARCIAL));
			    String numeroExpediente = request.getParameter("numeroUnicoExpediente").trim();
			    ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request, numeroExpediente);
			    if(expTrabajo == null && numeroExpediente != null)
			    	expTrabajo = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
		    	UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);
		    	String numeroFolio = request.getParameter("iNumeroOficio");
		    	ByteArrayOutputStream archivoPDF = null;
		    	logger.debug("Numero Folio :: " + request.getParameter("iNumeroOficio"));
		    	logger.debug("parcial :: " + parcial);
			    if(!parcial){
			    	archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
				    archivo = new ArchivoDigitalDTO();
				    archivo.setContenido(archivoPDF.toByteArray());
				    archivo.setNombreArchivo(forma.getNombre());
				    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
		    	}else{
		    	    documento.setTextoParcial(textoPdf);
		    	}
			    
			    documento.setFormaDTO(forma);
			    documento.setArchivoDigital(archivo);
			    documento.setFechaCreacion(new Date());
			    documento.setNombreDocumento(forma.getNombre());
			    documento.setFolioDocumento(numeroFolio);
			    documento.setTipoDocumentoDTO(new ValorDTO(tipoDocumento));
			    documento.setEsGuardadoParcial(parcial);
			    documento.setResponsableDocumento(responsableDocumento);
			    documento.setJerarquiaOrganizacional(usuarioFirmado.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId());
			    
			    if(nuevaActividad!=null && nuevaActividad!=0L){
			    	documentoId = documentoDelegate.guardarDocumento(documento,expTrabajo,nuevaActividad);
			    	if (nuevaActividad.equals(Actividades.GENERAR_ACUSE_RECIBO_PERTENENCIAS.getValorId())){
			    		actualizarInventarioPertenencias(request, parcial, documentoId);
			    	}
			    } else {
			    	documentoId = documentoDelegate.guardarDocumento(documento,expTrabajo,null);	
			    }

				try {
					
					Long audienciaIdRel = NumberUtils.toLong(audienciaId, 0L);
					
					if(audienciaIdRel > 0L){
						AudienciaDTO audienciaRel = new AudienciaDTO();
						audienciaRel.setId(audienciaIdRel);
						
						DocumentoDTO documentoRel = new DocumentoDTO();
						documentoRel.setDocumentoId(documentoId);
						
						audienciaDelegate.asociarDocumentoAAudiencia(audienciaRel, documentoRel);
					}
				} catch (NSJPNegocioException ne) {
					if (ne.getCodigo().equals(CodigoError.DOCUMENTO_YA_ASOCIADO)) {
						logger.debug("DOCUMENTO YA ASOCIADO EN AUDIENCIA");
					}
				}

		    	if(!parcial){
		    		 request.getSession().setAttribute("documentoId",documentoId);
			    	 escribirReporte(response, archivoPDF, forma.getNombre());
			    	 logger.info("/**** ID Retornado :: "+documentoId);			    	 
			    	 if(StringUtils.isNotBlank(request.getParameter(PARAMT_TIPO_OPERACION))){
			    		 documento.setDocumentoId(documentoId);
			    		 ejecutarAccion(request, documento);
			    	 }
			    }else{
		    		escribirRespuesta(response, converter.toXML(documentoId+","+numeroFolio));
			    }
		    }catch (NSJPNegocioException e) {
				logger.error(e.getMessage(), e);
			}
		    
        return null;
    }
    
    /**
     * Genera un PDF, a partir del contenido del Editor de Texto, y de acuerdo a la forma de documento.
     * Requiere de los siguientes parametros del request:
     * -texto: texto del editor Parcial.
     * -formaId: identificador de la forma
     * -documentoId: identificador del documento (Opcional)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward generarVistaPreliminar(ActionMapping mapping,            
    		ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    	logger.debug("*********EJECUTANDO ACTION generarVistaPrevia************");
    	logger.debug("VERIFICANDO PARAMETROS.....");

    	FormaDTO forma =null;
    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
    	String formaIdString = request.getParameter(PARAM_FORMA_DOCUMENTO);
    	String documentoIdString = request.getParameter(PARAM_IDENT_DOCUMENTO);
    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
    	
    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
    	logger.debug(PARAM_IDENT_DOCUMENTO + " :: " + documentoIdString);
    	logger.debug(PARAM_FORMA_DOCUMENTO + " :: " + formaIdString);
    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
    	
    	try{
	    	//Valores para la forma de acuerdo al tipo de documento
	    	
	    	if(StringUtils.isNotBlank(documentoIdString)){
	    		Long documentoId = NumberUtils.toLong(documentoIdString);
	    		DocumentoDTO documento = documentoDelegate.cargarDocumentoPorId(documentoId);
	    		forma = documento.getFormaDTO();
	    	}else{
		    	Long formaId = NumberUtils.toLong(formaIdString, 1L);
	            forma = documentoDelegate.buscarForma(formaId);
	    	}
	    	if(forma == null){
	    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
	    	}
	    	ByteArrayOutputStream archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
	    	escribirReporte(response, archivoPDF, forma.getNombre());
    	} catch (NSJPNegocioException e) {
    		logger.error(e.getMessage(), e);
		}
    	return null;
    }
    
    public ActionForward generarDocumentoNotificacion(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		    try{
		    	logger.debug("*********EJECUTANDO ACTION generarDocumentoNotificacion************");
		    	logger.debug("VERIFICANDO PARAMETROS.....");
		    	    	
		    	// se obtiene el texto del editor
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	logger.debug("textoPdf:: " + textoPdf);
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;
		    	String parametro = request.getParameter(PARAM_IDENT_DOCUMENTO);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);

		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	logger.debug(PARAM_IDENT_DOCUMENTO+" :: "+parametro);
		    	//para verificar parametros
		    	logger.debug(PARAM_FORMA_DOCUMENTO+"::" +request.getParameter(PARAM_FORMA_DOCUMENTO));
		    	logger.debug(PARAMT_TIPO_DOCUMENTO+"::" +request.getParameter(PARAMT_TIPO_DOCUMENTO));
		    	logger.debug("NUMERO UNICO EXPEDIENTE::" +request.getParameter("numeroUnicoExpediente"));
		    	
		    	if(StringUtils.isNotBlank(parametro)){
//		    		Se est? editando un documento en espec?fico
		    		Long documentoId = NumberUtils.toLong(parametro);
		    		documento = documentoDelegate.cargarDocumentoPorId(documentoId);
		    		forma = documento.getFormaDTO();
		    	}else{
//		    		Si es un documento nuevo se obtiene el tipo de forma que se est? editando
		            documento = new DocumentoDTO();
			    	Long formaId = NumberUtils.toLong(request.getParameter(PARAM_FORMA_DOCUMENTO), 1L);
		            forma = documentoDelegate.buscarForma(formaId);
		    	}
		    	
		    	if(forma == null){
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		    	}
		    	
		    	ArchivoDigitalDTO archivo = null;
			    Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), TIPO_DOCUMT_SOLICITUD);
			    logger.debug("tipoDocumento:: " + tipoDocumento);
			    boolean parcial = StringUtils.isNotBlank(request.getParameter(PARAM_GUARDAR_PARCIAL));
		    	ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request, request.getParameter("numeroUnicoExpediente"));
		    	ByteArrayOutputStream archivoPDF = null;
		    	logger.debug("parcial :: " + parcial);
			    if(!parcial){
		    		archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
				    archivo = new ArchivoDigitalDTO();
				    archivo.setContenido(archivoPDF.toByteArray());
				    archivo.setNombreArchivo(forma.getNombre());
				    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
		    	}else{
		    	    documento.setTextoParcial(textoPdf);
		    	}
			    
			    documento.setFormaDTO(forma);
			    documento.setArchivoDigital(archivo);
			    documento.setFechaCreacion(new Date());
			    documento.setNombreDocumento(forma.getNombre());
			    documento.setTipoDocumentoDTO(new ValorDTO(tipoDocumento));
		    	Long documentoId = notificacionDelegate.guardarYEnviarNotificacionAMismaInstitucion(expTrabajo,documento);
		    	if(!parcial){
			    	 escribirReporte(response, archivoPDF, forma.getNombre());
			    	 if(StringUtils.isNotBlank(request.getParameter(PARAMT_TIPO_OPERACION))){
			    		 documento.setDocumentoId(documentoId);
			    		 ejecutarAccion(request, documento);
			    	 }
			    }else{
			    	escribirRespuesta(response, converter.toXML(documentoId));
			    }
		    	return mapping.findForward("success");
		    }catch (NSJPNegocioException e) {
				logger.error(e.getMessage(), e);
				return mapping.findForward("fail");
			}
    }
    
    
    /**
     * 
     * @param request
     * @param documento
     * @throws NSJPNegocioException
     */
    private void ejecutarAccion( HttpServletRequest request, DocumentoDTO documento) throws NSJPNegocioException{
		OperacionDocumento op = OperacionDocumento.getByValor(NumberUtils.toLong(request.getParameter(PARAMT_TIPO_OPERACION)));
		logger.debug(PARAMT_TIPO_OPERACION + " :: " + op);
		logger.info("Inicia - ejecutarAccion(...)");
		switch(op){
			case ACTUALIZAR_ESTADO_SOLICITUD:
				 if(request.getParameter("estatusSolicitud") != null){
					 SolicitudDTO solicitud = new SolicitudDTO(documento.getDocumentoId());
					 EstatusSolicitud estatus = EstatusSolicitud.getByValor(NumberUtils.toLong( request.getParameter("estatusSolicitud")));
					 solicituDelegate.actualizaEstatusSolicitud(solicitud, estatus);
				 }else{
			    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				 }
				 break;
			case ASOCIAR_DOCUMENTO_A_RESOLUTIVO:
			    Long resolutivoId = null;
			    ResolutivoDTO resolutivo = new ResolutivoDTO();
			    if(request.getParameter("idResolutivo") != null){
					resolutivoId = NumberUtils.toLong(request.getParameter("idResolutivo"), 2L);
					resolutivo.setResolutivoId(resolutivoId);
			    	documentoDelegate.asociarDocuementoA(resolutivo, documento);

			    }else{
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			    }
			    break;
			case REGISTRAR_ORDEN_DETENCION:
				SolicitudDTO ordenDetencion = (SolicitudDTO) request.getSession().getAttribute("ordenDetencion");
				if(ordenDetencion != null){
					solicituDelegate.registrarSolicitudOrdenDeDetencion(ordenDetencion  , documento.getDocumentoId());
					request.getSession().removeAttribute("ordenDetencion");
				}else{
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				}
				break;
				
		}
    }
    
    /**
     * Realiza la pre-carga de un documento para el expediente de trabajo.
     * Si no existe ning?n documento del tipo de forma seleccionado entonces prepara
     * un documento nuevo (sin guardarlo en BD), obtiene la plantilla de la forma
     * y la llena con los datos del expediente para presentarla
     * en el CKEditor. Si ya existe alg?n documento con texto parcial para el expediente
     * de trabajo carga la informaci?n que el usuario hab?a guardado parcialmente
     * y la presenta en el CKEditor
     * 
     * Parametros:
     * 
     * formaId (obligatorio-opcional si se env?a el tipo de documento)- El identificador del formato a emitir (acta, denuncia, acuse de recibo, etc)
     * 
     * documentoId (opcional) - Indica un cierto ID de documento con el que se debe de trabajar en lugar de
     * crear uno nuevo. En este caso el documento se actualizar?a en lugar de crearse 
     * 
     * tipoOperacion - Identificador del tipo de operaci?n a realizar una vez que sea impreso el documento
     * <code>OperacionDocumento.ACTUALIZAR_ESTADO_SOLICITUD</code> Actualizar el estado de la solicitud
     * posterior al emitir un documento 
     * Al utilizar este tipo de operaci?n se debe enviar tambi?n el par?metro de estatusSolicitud indicando
     * el estado de solicitud destino
     * 
     * esconderArbol - Enviar este par?metro con uno para no mostrar el ?rbol de datos de expediente, no mandarlo
     * para mostrar el ?rbol
     * 
     * numeroUnicoExpediente (obligatorio) - Identificador del n?mero ?nico de expediente con el que se est? trabajando
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward cargarDocumento(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {    	
    	try {
    		UsuarioDTO usuario = getUsuarioFirmado(request);
    		String numeroExpediente = request.getParameter("numeroUnicoExpediente");
    		String mandaFormaEnConsulta = request.getParameter("mandaFormaEnConsulta");
    		logger.debug("/**** NumeroUnico :::: "+numeroExpediente+"**");
		    ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request, numeroExpediente);
		    if(expTrabajo == null && numeroExpediente != null){
		    	expTrabajo = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
		    }
	    	if(expTrabajo != null){
        		expTrabajo.setArea(getUsuarioFirmado(request).getAreaActual());
            	expTrabajo.setUsuario(getUsuarioFirmado(request));
        	}
	    	
    		Map<String,Object> parametrosExtra = new HashMap<String,Object> ();
    		
    		for(Object llave:request.getParameterMap().keySet()){
    			parametrosExtra.put(llave.toString(), request.getParameter(llave.toString()));
    		}
    		
    		Long formaId = NumberUtils.toLong(request.getParameter("formaId"), 1L);
    		Long documentoId =  StringUtils.isNotBlank(request.getParameter("documentoId"))?NumberUtils.toLong(request.getParameter("documentoId")):null;

    		// si se recibe la 
    		
    		Long sentenciaId = NumberUtils.toLong(request.getParameter("sentenciaId"), 0L);
    		if(sentenciaId > 0L) {
    			Map<String,Object> parametrosSentencia = new HashMap<String,Object>();
    			
    			SentenciaDTO sentenciaDTO = new SentenciaDTO();
    			sentenciaDTO.setSentenciaId(sentenciaId);
    			Long asignacionProgramaId = NumberUtils.toLong(request.getParameter("asignacionProgramaId"), 0L);	
    			if(asignacionProgramaId.compareTo(0L) != 0){
    				List<AsignacionProgramaDTO> lstAsignacionProgramaDTO = new ArrayList<AsignacionProgramaDTO>();
    				AsignacionProgramaDTO asignacionProgramaDTO = new AsignacionProgramaDTO();
    				asignacionProgramaDTO.setAsignacionProgramaId(asignacionProgramaId);
    				lstAsignacionProgramaDTO.add(asignacionProgramaDTO);
    				sentenciaDTO.setAsignacionProgramas(lstAsignacionProgramaDTO);
    			}
    			
    			parametrosExtra.putAll( documentoDelegate.getParametrosExtraSentecia(sentenciaDTO, parametrosSentencia));
    			
    			if (documentoId == null){
    				// se recibe la actividad para obtener la ultima y asi el ultima documentoId
    				Long tipoActividadId = NumberUtils.toLong(request.getParameter("actividadId"));
    				if (tipoActividadId > 0L 
    						&& ActividadesRS.getByValor(tipoActividadId) 
    									== ActividadesRS.ELABORAR_INFORME_FINAL_DE_REINSERCION_SOCIAL_DEL_SENTENCIADO) {
    					
    					ActividadDTO actividadDTO = new ActividadDTO();
    					actividadDTO.setTipoActividad(Actividades.getByValor(tipoActividadId));
    					
    					DocumentoDTO documentoDTO = new DocumentoDTO();
    					documentoDTO.setActividadDTO(actividadDTO);
    					documentoDTO.setExpedienteDTO(expTrabajo);

    					documentoDTO = documentoDelegate.consultarDocumentoFiltro(documentoDTO);
    					
    					if(documentoDTO != null 
    							&& documentoDTO.getDocumentoId() != null
    							&& documentoDTO.getDocumentoId() > 0L) {
    						documentoId = documentoDTO.getDocumentoId();
    					}
    				}
    			}
    		}
    		
    		DocumentoDTO documentoCargado = null;
    		logger.debug("Intentando carga de documento: forma: " + formaId );
    		logger.debug("Intentando carga de documento: documento: " + documentoId );
    		logger.debug("Intentando carga de documento: numeroExpediente: " + request.getParameter("numeroUnicoExpediente"));
    		logger.debug("Expediente de trabajo: " + expTrabajo);
    		//si documentioId es nulo se trabaja con el expediente actual de sesi?n
    		if(documentoId == null){
    			logger.debug("entra formas");
    			Formas forma = Formas.getByValor(formaId);
    			logger.debug("forma_antes_del_switch:: "+forma);
    			if(forma==null)
    			{
    				logger.info("forma_no_en_enum.... formaID:: "+formaId);
    				documentoCargado = documentoDelegate.cargarDocumentoPorExpedienteYForma(expTrabajo, new FormaDTO(formaId),parametrosExtra,mandaFormaEnConsulta);
    			}
    			else
    			{
	    			switch(forma){
	    				case AUDIENCIA:
	    					logger.debug("entra switch audiencia");
	    					Long auidenciaId = NumberUtils.toLong(request.getParameter("idAudiencia"));
	    					logger.debug("LLega Audiencia id: " + auidenciaId );
	    					

	    					actividadDelegate.registrarActividad(expTrabajo, usuario.getFuncionario(), Actividades.FINAL_DE_AUDIENCIA.getValorId());
	    					
	    					AudienciaDTO audiencia = new AudienciaDTO();
	    					audiencia.setId(auidenciaId);
	    					documentoCargado = documentoDelegate.cargarDocumentoPorAudienciaYForma(audiencia, new FormaDTO(formaId));
	    					break;
	    				case DUPLICIDAD_TERMINO_CONSTITUCIONAL:
	    					logger.debug("entra switch duplicidad termino");
	    					auidenciaId = NumberUtils.toLong(request.getParameter("idAudiencia"));
	    					audiencia = new AudienciaDTO();
	    					audiencia.setId(auidenciaId);
	    					Long resolutivoId = NumberUtils.toLong(request.getParameter("idResolutivo"));
	    					logger.debug("LLega Resolutivo: " + resolutivoId );
	    					logger.debug("LLega Audiencia: " + auidenciaId );
	    					ResolutivoDTO resolutivo = new ResolutivoDTO();
	    					resolutivo.setResolutivoId(resolutivoId);
	    					resolutivo.setAudiencia(audiencia);
	    					documentoCargado = documentoDelegate.cargarDocumentoPorResolutivoYForma(resolutivo, new FormaDTO(formaId));
	    					break;
	    				default:
	    					
	    					documentoCargado = documentoDelegate.cargarDocumentoPorExpedienteYForma(expTrabajo, new FormaDTO(formaId),parametrosExtra,mandaFormaEnConsulta);
	    					break;
	    			}
    			}
    		}else{
    			documentoCargado = documentoDelegate.cargarDocumentoPorId(documentoId,expTrabajo);
    		}
			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML);
			PrintWriter pw = response.getWriter();
			logger.info(ConstantesGenerales.BODY_TAG_APERTURA+ConstantesGenerales.C_DATA_OPEN+
					(documentoCargado.getTextoParcial()!=null?documentoCargado.getTextoParcial():StringUtils.EMPTY)+
					ConstantesGenerales.C_DATA_CLOSE+ConstantesGenerales.BODY_TAG_CIERRE);
			pw.print(
					ConstantesGenerales.BODY_TAG_APERTURA+
						// Recuperacio_n del cuerpo del documento
						ConstantesGenerales.CUERPO_DOCUMENTO_TAG_APERTURA+
							ConstantesGenerales.C_DATA_OPEN+
								(documentoCargado.getTextoParcial()!=null?documentoCargado.getTextoParcial():StringUtils.EMPTY)+
							ConstantesGenerales.C_DATA_CLOSE+
						ConstantesGenerales.CUERPO_DOCUMENTO_TAG_CIERRE+
						// Recuperacio_n del nu_mero de folio
						ConstantesGenerales.NUMERO_FOLIO_TAG_APERTURA+
							(documentoCargado.getFolioDocumento()!=null?documentoCargado.getFolioDocumento():StringUtils.EMPTY)+
						ConstantesGenerales.NUMERO_FOLIO_TAG_CIERRE+
						"<documentoId>" +(documentoCargado.getDocumentoId()!=null?documentoCargado.getDocumentoId():StringUtils.EMPTY)+"</documentoId>" +  
					ConstantesGenerales.BODY_TAG_CIERRE
					);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			logger.error("Exception forma :"+e.getMessage(), e);
		}
    	return null;
    	
    }
    
    /**
     * Carga un resumen de expediente para llenar un ?rbol de contenido
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward cargarArbolExpediente(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {   
    	logger.debug("cargarArbolExpediente");
    	ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request, request.getParameter("numeroUnicoExpediente"));	
    	if(expTrabajo != null){
    		expTrabajo.setArea(new AreaDTO(Areas.ATENCION_TEMPRANA_PG_PENAL));
    		expTrabajo.setInvolucradosSolicitados(true);
    		expTrabajo.setObjetosSolicitados(true);
    	}
    	String idnumero=request.getParameter("idNumeroUnicoExpediente");
    	if(idnumero!=null &&!idnumero.equals("")){
    		expTrabajo.setNumeroExpedienteId(Long.parseLong(idnumero));
    	}
    	try{
    		ParametrosDocumentoDTO resumen = documentoDelegate.cargarResumenExpedienteParaDocumento(expTrabajo);
    		
    		//Consultar el Catalogo de Unidades de Investigacion
    		List<CatUIEspecializadaDTO> listaCatalogoUIE = catUIEspecializadaDelegate.consultarUnidadesIEspecializadas();
			
    		if(resumen != null){
    			logger.debug("cargarArbolExpediente - resumen :::: "+resumen);
    			logger.debug("cargarArbolExpediente - listaCatalogoUIE :::: "+listaCatalogoUIE);
    			
    			//Para el catalogo de unidades de investigacion
    			converter.alias("listaCatalogo", java.util.List.class);
    			converter.alias("CatUIEspecializadaDTO", CatUIEspecializadaDTO.class);
    			
    			String xml ="<DatosArbol>"+ escribirXMLArbolExpediente(resumen); 
    			xml += converter.toXML(listaCatalogoUIE) + "</DatosArbol>";  
    			
    			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML);
    			PrintWriter pw = response.getWriter();
    			pw.print( xml );
    			pw.flush();
    			pw.close();
    		}
    		
    	}catch (Exception e) {
    		logger.error(e.getMessage(), e);
		}
    	
    	
    	return null;
    }
    
    public ActionForward cargarUIE(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {   
    	logger.debug("cargarUIE");
    		try{
    		
    		//Consultar el Catalogo de Unidades de Investigacion
    		List<CatUIEspecializadaDTO> listaCatalogoUIE = catUIEspecializadaDelegate.consultarUnidadesIEspecializadas();
			
    			//Para el catalogo de unidades de investigacion
    			converter.alias("listaCatalogo", java.util.List.class);
    			converter.alias("CatUIEspecializadaDTO", CatUIEspecializadaDTO.class);
    			
    			String xml =converter.toXML(listaCatalogoUIE); 
    			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML);
    			PrintWriter pw = response.getWriter();
    			pw.print( xml );
    			pw.flush();
    			pw.close();
    		
    	}catch (Exception e) {
    		logger.error(e.getMessage(), e);
		}
    	
    	
    	return null;
    }
    
    /**
     * Convierte la informaci?n necesaria de un expediente para poder trabajarlo en el documento
     * en forma de ?rbol de informaci?n
     * 
     * 
     * @param resumen
     * @return
     */
	private String escribirXMLArbolExpediente(ParametrosDocumentoDTO resumen) {
		converter.alias("expedienteResumenDTO", ParametrosDocumentoDTO.class);
		converter.alias("involucradoDTO", InvolucradoDTO.class);
		converter.alias("calidadDTO", CalidadDTO.class);
		converter.alias("domicilioDTO", DomicilioDTO.class);
		converter.alias("nombreDemograficoDTO", NombreDemograficoDTO.class);
		converter.alias("telefonoDTO", TelefonoDTO.class);
		converter.alias("correoElectronicoDTO", CorreoElectronicoDTO.class);
		converter.alias("aliasInvolucradoDTO", AliasInvolucradoDTO.class);
		converter.alias("grupoObjetosExpedienteDTO", GrupoObjetosExpedienteDTO.class);
		converter.alias("objetoResumenDTO", ObjetoResumenDTO.class);
		
		return converter.toXML(resumen);
	}
	
	/**
     * Crea o actualiza un documento producto de una transcripci?n
     * 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward generarDocumentoTranscripcion(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		    try{
		    	// se obtiene el texto del editor
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
		    	
		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;
		    	documento = new DocumentoDTO();
		    	//se obtiene el tipo de forma que se est? editando
			    Long formaId = NumberUtils.toLong(request.getParameter("formaId"), 1L);
			    Long solicitudId = NumberUtils.toLong(request.getParameter("solicitudId"), 1L);
		    	
		        forma = documentoDelegate.buscarForma(formaId);
		    	 if(forma != null){
		            	//se copian valores del tipo de forma al documento
		            	String nombreArchivo = forma.getNombre();
					    String nombreDocumento = forma.getNombre();
					    ValorDTO tipoDocumento = new ValorDTO(TipoDocumento.SOLICITUD.getValorId());
					    Double version = 1.2;
					    boolean parcial = StringUtils.isNotBlank(request.getParameter("parcial"));
				    	ArchivoDigitalDTO archivo = null;
				    	//ExpedienteDTO expTrabajo =   super.getExpedienteTrabajo(); //cambiar a expediente de la audiencia
				    	ByteArrayOutputStream archivoPDF = null;
				    	if(!parcial){
				    		archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
						    //Crear el archivo digital
						    archivo = new ArchivoDigitalDTO();
						    archivo.setContenido(archivoPDF.toByteArray());
						    archivo.setNombreArchivo(nombreArchivo);
						    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
				    	}
					    documento.setFechaCreacion(new Date());
					    documento.setFormaDTO(forma);
					    documento.setTipoDocumentoDTO(tipoDocumento);
					    documento.setNombreDocumento(nombreDocumento);
					    documento.setVersion(version);
					    documento.setTextoParcial(parcial?textoPdf:null);
					    //documentoDelegate.guardarDocumentoTranscripcion(documento,expTrabajo);
					    documentoDelegate.guardarDocumentoTranscripcion(documento, solicitudId);
					    if(!parcial){
					    	 escribirReporte(response, archivoPDF, nombreArchivo);
					    }
		            }
		    }catch (NSJPNegocioException e) {
		        logger.error(e.getMessage(), e);
			}
		    
        return null;
    }
    
	/**
     * Crea o actualiza un documento producto de una transcripci?n
     * 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward generarActaAudiencia(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		    try{
		    	// se obtiene el texto del editor
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
		    	
		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;
		    	documento = new DocumentoDTO();
		    	//se obtiene el tipo de forma que se est? editando
			    Long formaId = NumberUtils.toLong(request.getParameter("formaId"), 1L);
		        forma = documentoDelegate.buscarForma(formaId);
		    	 if(forma != null){
		            	//se copian valores del tipo de forma al documento
		            	String nombreArchivo = forma.getNombre();
					    String nombreDocumento = forma.getNombre();
					    ValorDTO tipoDocumento = new ValorDTO(TipoDocumento.SOLICITUD.getValorId());
					    Double version = 1.2;
					    boolean parcial = StringUtils.isNotBlank(request.getParameter("parcial"));
				    	ArchivoDigitalDTO archivo = null;
				    	ExpedienteDTO expTrabajo =   super.getExpedienteTrabajo(request, request.getParameter("numeroUnicoExpediente"));
				    	ByteArrayOutputStream archivoPDF = null;
				    	if(!parcial){
				    		archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
						    //Crear el archivo digital
						    archivo = new ArchivoDigitalDTO();
						    archivo.setContenido(archivoPDF.toByteArray());
						    archivo.setNombreArchivo(nombreArchivo);
						    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
				    	}
					    documento.setFechaCreacion(new Date());
					    documento.setFormaDTO(forma);
					    documento.setTipoDocumentoDTO(tipoDocumento);
					    documento.setNombreDocumento(nombreDocumento);
					    documento.setVersion(version);
					    documento.setTextoParcial(parcial?textoPdf:null);
					    documentoDelegate.guardarActaAudiencia(documento,expTrabajo);
					    if(!parcial){
					    	 escribirReporte(response, archivoPDF, nombreArchivo);
					    }
		            }
		    }catch (NSJPNegocioException e) {
		        logger.error(e.getMessage(), e);
			}
		    
        return null;
    }
    
    /**
     * Realiza la carga y guardado del documento sin pasar por la pantalla de edici?n 
     * del documento.
     * 
     * Se trabaja con los datos del expediente de trabajo
     * 
     * Parametros:
     * 
     * formaId (obligatorio)- El identificador del formato a emitir (acta, denuncia, acuse de recibo, etc)
     * 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward generarDocumentoDirecto(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {    	
    	try {
    		logger.info("GenerarDocumento numeroUnicoExpediente:: "+request.getParameter("numeroUnicoExpediente"));
    		String tipoGuardado 	= request.getParameter("tipoGuardado");
    		String textoRecuperado 	= request.getParameter("textoRecuperado");
    		ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request,request.getParameter("numeroUnicoExpediente")!=null?
    				request.getParameter("numeroUnicoExpediente").trim():null);	
    		
        	expTrabajo.setArea(super.getUsuarioFirmado(request).getAreaActual());
        	expTrabajo.setUsuario(super.getUsuarioFirmado(request));
    		Long formaId = NumberUtils.toLong(request.getParameter("formaId"), 1L);
    		Long documentoId = NumberUtils.toLong(request.getParameter("documentoId"), -1L);
    		Long tipoDocumt = NumberUtils.toLong(request.getParameter("tipoDocumento"), -1L);
    		String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
        	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
        	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
        	
        	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
        	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
        	
    		boolean returnDocument = false;
    		if(request.getParameter("returnDocument") == null || 
    		  (request.getParameter("returnDocument") != null && request.getParameter("returnDocument").equals("1"))){
    			returnDocument = true;
    		} 
    		
    		if (logger.isDebugEnabled()) {
    		    logger.debug("formaId :: " + formaId);
    		    logger.debug("documentoId :: " + documentoId);
    		    logger.debug("tipoDocumt :: " + tipoDocumt);    		        		    
    		}
    		converter.alias("expedienteDTO", ExpedienteDTO.class);
		    logger.info(converter.toXML("MMM::"+expTrabajo));

    		FormaDTO forma = documentoDelegate.buscarForma(formaId);
    		 logger.debug("SE ENCONTRO LA FORMA:: " + forma);
    		DocumentoDTO documentoCargado = null;
    		if(documentoId.longValue() != -1L){
    			documentoCargado = documentoDelegate.cargarDocumentoPorId(documentoId, expTrabajo);
    			forma = documentoCargado.getFormaDTO();
    			logger.debug("FORMA DEL DOCUMENTO CARGADO:: " + documentoCargado.getFormaDTO());
    		}else{
	    		documentoCargado = documentoDelegate.cargarDocumentoPorExpedienteYForma(expTrabajo, new FormaDTO(formaId));
    		}
	    	if(forma != null){
            	//se copian valores del tipo de forma al documento
            	String nombreArchivo = forma.getNombre();
			    String nombreDocumento = forma.getNombre();
			    ValorDTO tipoDocumento = null;
			    if(tipoDocumt.longValue() != -1){
			    	tipoDocumento = new ValorDTO(tipoDocumt);
			    }else{
			    	tipoDocumento = new ValorDTO(TipoDocumento.ACTA.getValorId());
			    }
			    Double version = 1.2;
			    
		    	ArchivoDigitalDTO archivo = null;
		    	
		    	if( formaId.toString().trim().equals("33") | formaId.toString().trim().equals("7") ){
		    		documentoCargado.setTextoParcial(textoRecuperado);
		    	}
		    	
		    	ByteArrayOutputStream archivoPDF = null;
		    	
	    		archivoPDF = generarReportePDFDeHTML(documentoCargado.getTextoParcial(), confPapel);
			    //Crear el archivo digital
			    archivo = new ArchivoDigitalDTO();
			    archivo.setContenido(archivoPDF.toByteArray());
			    archivo.setNombreArchivo(nombreArchivo);
			    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
			    documentoCargado.setArchivoDigital(archivo);
			    documentoCargado.setFechaCreacion(new Date());
			    documentoCargado.setFormaDTO(forma);
			    documentoCargado.setTipoDocumentoDTO(tipoDocumento);
			    documentoCargado.setNombreDocumento(nombreDocumento);
			    documentoCargado.setVersion(version);
			    documentoCargado.setTextoParcial(textoRecuperado);
			    ///documentoCargado.setTextoParcial(null);
			    documentoDelegate.guardarDocumento(documentoCargado,expTrabajo, null);	
			    if(returnDocument){
			    	escribirReporte(response, archivoPDF, nombreArchivo);
			    }
			 }
	    }catch (NSJPNegocioException e) {
	        logger.error(e.getMessage(), e);
		}
        return null;
    }
    /**
     * Genera un archivo digital que corresponde al documento a generar/crear
     * sin embargo no escribe el archivo generado al response del cliente, en vez de esto
     * escribe como XML ?nicamente el ID del documento que se acaba de generar, dejando
     * a la pantalla que invoc? a este m?todo el comportamiento posterior que desea realizar, ya sea
     * para actualizar alg?n estatus o para enviar el documento reci?n creado 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward guardarDocumentoSincrono(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {
    	
    	try{
	    	// se obtiene el texto del editor
	    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
	    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
	    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
	    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
	    	
	    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
	    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
	    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
	    	DocumentoDTO documento = null;
	    	FormaDTO forma =null;
	    	String parametro = request.getParameter(PARAM_IDENT_DOCUMENTO);
	    	if(StringUtils.isNotBlank(parametro)){
	    		//Se est? editando un documento en espec?fico
	    		Long documentoId = NumberUtils.toLong(parametro);
	    		documento = documentoDelegate.cargarDocumentoPorId(documentoId);
	    		forma = documento.getFormaDTO();
	    	}else{
	    		//Si es un documento nuevo se obtiene el tipo de forma que se est? editando
	            documento = new DocumentoDTO();
		    	Long formaId = NumberUtils.toLong(request.getParameter(PARAM_FORMA_DOCUMENTO), 1L);
	            forma = documentoDelegate.buscarForma(formaId);
	    	}
	    	
	    	if(forma == null){
	    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
	    	}
	    	
	    	ArchivoDigitalDTO archivo = null;
		    Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), TIPO_DOCUMT_SOLICITUD);
		    boolean parcial = StringUtils.isNotBlank(request.getParameter(PARAM_GUARDAR_PARCIAL));
	    	ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request, request.getParameter("numeroUnicoExpediente"));
	    	ByteArrayOutputStream archivoPDF = null;
	    	
		    if(!parcial){
	    		archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
			    archivo = new ArchivoDigitalDTO();
			    archivo.setContenido(archivoPDF.toByteArray());
			    archivo.setNombreArchivo(forma.getNombre());
			    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
	    	}else{
	    	    documento.setTextoParcial(textoPdf);
	    	}
		    
		    documento.setFormaDTO(forma);
		    documento.setArchivoDigital(archivo);
		    if(documento.getFechaCreacion() == null)
		    	documento.setFechaCreacion(new Date());	
		    documento.setNombreDocumento(forma.getNombre());
		    if(!(documento.getTipoDocumentoDTO() != null && documento.getTipoDocumentoDTO().getIdCampo() != null))
		    	documento.setTipoDocumentoDTO(new ValorDTO(tipoDocumento));
	    	Long documentoId = documentoDelegate.guardarDocumento(documento,expTrabajo, null);
	    	
		    escribirRespuesta(response, converter.toXML(documentoId));
		    
	    }catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
    	
    	
    	return null;
    }
    
    /***
     * funcion para generar la notificacion de la solicitud de ayuda psicologica UAVD
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward generarDocumentoNotificacionAyudaPsicologicaUAVD(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		    try{
		    	// se obtiene el texto del editor
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
		    	
		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;
		    	String parametro = request.getParameter(PARAM_IDENT_DOCUMENTO);
		    	logger.debug(PARAM_IDENT_DOCUMENTO + " :: " + parametro);
		    	if(StringUtils.isNotBlank(parametro)){
//		    		Se est? editando un documento en espec?fico
		    		Long documentoId = NumberUtils.toLong(parametro);
		    		documento = documentoDelegate.cargarDocumentoPorId(documentoId);
		    		forma = documento.getFormaDTO();
		    	}else{
//		    		Si es un documento nuevo se obtiene el tipo de forma que se est? editando
		            documento = new DocumentoDTO();
			    	Long formaId = NumberUtils.toLong(request.getParameter(PARAM_FORMA_DOCUMENTO), 1L);
		            forma = documentoDelegate.buscarForma(formaId);
		    	}
		    	
		    	if(forma == null){
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		    	}
		    	
		    	ArchivoDigitalDTO archivo = null;
			    Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), TIPO_DOCUMT_SOLICITUD);
			    boolean parcial = StringUtils.isNotBlank(request.getParameter(PARAM_GUARDAR_PARCIAL));
		    	ByteArrayOutputStream archivoPDF = null;
		    	logger.debug("parcial :: " + parcial);
			    if(!parcial){
		    		archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
				    archivo = new ArchivoDigitalDTO();
				    archivo.setContenido(archivoPDF.toByteArray());
				    archivo.setNombreArchivo(forma.getNombre());
				    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
				    /*se agrega la condicion para desactivar la bandera de guardado parcial/*byYolo*/
				    documento.setEsGuardadoParcial(false);
		    	}else{
		    	    documento.setTextoParcial(textoPdf);
		    	}
			    
			    documento.setFormaDTO(forma);
			    documento.setArchivoDigital(archivo);
			    documento.setFechaCreacion(new Date());
			    documento.setNombreDocumento(forma.getNombre());
			    documento.setTipoDocumentoDTO(new ValorDTO(tipoDocumento));
			    
			    logger.debug("idRelacionDelito" + " :: " + request.getParameter("idRelDelito"));
			    logger.debug("idProbableResponsable" + " :: " + request.getParameter("idPR"));
			    logger.debug("idVictima" + " :: " + request.getParameter("idVictima"));
			    logger.debug("idDelito" + " :: " + request.getParameter("idDelito"));
			    logger.debug("idSolicitud" + " :: " + request.getParameter("idSolicitudVa"));
			    logger.debug("numeroUnicoExpediente    : : " + request.getParameter("numeroUnicoExpediente"));
			    
			    String numExpedientePadre_id = request.getParameter("numeroUnicoExpediente");	
			    ExpedienteDTO expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numExpedientePadre_id);
			    
			    Long idSolici=Long.parseLong(request.getParameter("idSolicitudVa"));
			    DelitoPersonaDTO delitoPersonaDTO=new DelitoPersonaDTO();
			    delitoPersonaDTO.setDelitoPersonaId(Long.parseLong(request.getParameter("idRelDelito")));
			    delitoPersonaDTO.setProbableResponsable(new InvolucradoDTO(Long.parseLong(request.getParameter("idPR"))));
			    Long idVictima = NumberUtils.toLong(request.getParameter("idVictima"),0L);
			    if(!idVictima.equals(0L)){
			    	delitoPersonaDTO.setVictima(new InvolucradoDTO(Long.parseLong(request.getParameter("idVictima"))));	
			    }
			    delitoPersonaDTO.setDelito(new DelitoDTO(Long.parseLong(request.getParameter("idDelito"))));
			    
			    List<FuncionarioDTO> listaFuncionario=funcionarioDelegate.consultarFuncionariosPorAreayPuesto(Areas.COORDINACION_ATENCION_VICTIMAS.parseLong(), Puestos.COORDINADOR_ATENCION_VICTIMAS.getValorId());
			    if(listaFuncionario!= null && listaFuncionario.size()!=0){
			    	UsuarioDTO usuarioDTO=usuarioDelegate.consultarUsuarioPorClaveFuncionario(listaFuncionario.get(0).getClaveFuncionario());
			    	delitoPersonaDTO.setUsuario(usuarioDTO);
			    	expedienteDTO.setUsuario(usuarioDTO);
			    }else{
			    	delitoPersonaDTO.setUsuario(super.getUsuarioFirmado(request));
			    }

			    // AsignarNumeroDeExpediente
			    expedienteDTO = asignarNumeroExpedienteService.asignarNumeroExpediente(expedienteDTO);
			    
			    Long documentoId = documentoDelegate.guardarDocumento(documento,expedienteDTO,null);
		    	//Long documentoId = notificacionDelegate.guardarYEnviarNotificacionAMismaInstitucion(expTrabajo,documento);
			    logger.debug("se coloca la solicitud el expediente de UAVD");
			    expedienteDelegate.asociarNumCarpetaASolicitud(expedienteDTO,numExpedientePadre_id, idSolici);
			    logger.debug("fin coloca la solicitud el expediente de UAVD");
			    if(!parcial){
			    	 escribirReporte(response, archivoPDF, forma.getNombre());
			    	 logger.debug("expediente_ayuda_psicoliga:: "+converter.toXML(expedienteDTO)); 
			    	 if(StringUtils.isNotBlank(request.getParameter(PARAMT_TIPO_OPERACION))){
			    		 documento.setDocumentoId(documentoId);
			    		 ejecutarAccion(request, documento);
			    	 }
			    }else{
			    	logger.debug("expediente_ayuda_psicoliga:: "+converter.toXML(expedienteDTO));
			    	escribirRespuesta(response, converter.toXML(expedienteDTO));
			    }
		    }catch (NSJPNegocioException e) {
				logger.error(e.getMessage(), e);
			}
		    
        return null;
    }
    
    /**
     * M&eacute;todo que lleva a cabo la asociación de un documento a un inventario de pertenencias.
     * @param request - petici&oacute;n de HTML de la cual se obtiene el objeto del inventario de 
     * 					pertenencias que se encuentra en la sesión del usuario.
     * @param definitivo - bandera que indica si se trata de un guardado definitivo o parcial.
     * @param documentoId - Identificador del documento con el cual se va a asociar el inventario de 
     * 						pertenencias.
     */
    private void actualizarInventarioPertenencias(HttpServletRequest request, Boolean parcial, Long documentoId){
    	Object attr = request.getSession().getAttribute(InventarioPertenenciasAction.ATTR_INV_PER);
		InventarioPertenenciaDTO invPer = null;
		if (attr != null && attr instanceof InventarioPertenenciaDTO){
			invPer = (InventarioPertenenciaDTO) attr;
			invPer.setDefinitivo(!parcial);
			if (documentoId != null && documentoId > 0L){
				DocumentoDTO docDTO = new DocumentoDTO();
				docDTO.setDocumentoId(documentoId);
				invPer.setDocumentoDTO(docDTO);
			}
			reinsercionDelegate.actualizarInventarioPertenencias(invPer);
		}
    }   
    
	public ActionForward enviarDocumentosAInstitucion (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		logger.debug("ejecutando Action AsignarProgramaReinsercionSocialAction método llenarGrid");

		StringBuilder html = new StringBuilder(); 

		response.setContentType("text/javascript; charset=ISO-8859-1");
		
		try {
				JSONParser parser = new JSONParser();		
				
				StringBuilder sb = new StringBuilder();
			    BufferedReader br = request.getReader();
			    String str;
			    while( (str = br.readLine()) != null ){
			        sb.append(str);
			    }    
			    Object obj = parser.parse(sb.toString());
				
			    JSONObject jsonObject = (JSONObject) obj;
			    
			    List<DocumentoDTO> lstDocumentoDTO = new ArrayList<DocumentoDTO>();

			    if (jsonObject != null && !jsonObject.isEmpty()) {
			    	
			    	Long sentenciaId = NumberUtils.toLong(jsonObject.get("sentenciaId").toString());
			    	SentenciaDTO sentenciaDTO = null;
			    	if(sentenciaId >0L){
			    		sentenciaDTO = new SentenciaDTO();
			    		sentenciaDTO.setSentenciaId(sentenciaId);
			    		sentenciaDTO = asignacionProgramaDelegate.consultarSentenciaPorId(sentenciaDTO);
			    	}
			    	
			    	if(sentenciaDTO != null 
			    			&& sentenciaDTO.getNumeroExpedienteDTO()!= null
			    			&& sentenciaDTO.getNumeroExpedienteDTO().getCasoDTO()!= null
			    			&& sentenciaDTO.getNumeroExpedienteDTO().getCasoDTO().getNumeroGeneralCaso()!= null
			    			&& !sentenciaDTO.getNumeroExpedienteDTO().getCasoDTO().getNumeroGeneralCaso().isEmpty()) {
			    		
			    		CasoDTO casoDTO = sentenciaDTO.getNumeroExpedienteDTO().getCasoDTO();
			    		
				    	JSONArray jsonArray = (JSONArray)jsonObject.get("documentos");
					    
					    if (jsonArray != null && !jsonArray.isEmpty()){
							@SuppressWarnings("unchecked")
							Iterator<JSONObject> iterator = jsonArray.iterator();
							while (iterator.hasNext()) {
								JSONObject json = iterator.next();
								if (json.get("idDocumento") != null){
									Long documentoId = NumberUtils.toLong(json.get("idDocumento").toString());
									if(documentoId>0L){
										DocumentoDTO documentoDTO = documentoDelegate.consultarDocumentoXId(documentoId);
										lstDocumentoDTO.add(documentoDTO);
									}
								}
							}
						}
					    
					    if (!lstDocumentoDTO.isEmpty()){
					    	jsonArray = (JSONArray)jsonObject.get("destinatarios");				    	
						    if (jsonArray != null && !jsonArray.isEmpty()){
								@SuppressWarnings("unchecked")
								Iterator<JSONObject> iterator = jsonArray.iterator();
								while (iterator.hasNext()) {
									JSONObject json = iterator.next();
									if (json.get("idFuncionario") != null
											&& json.get("instId") != null){
										Long idFuncionario = NumberUtils.toLong(json.get("idFuncionario").toString());
										Long idInstitucion = NumberUtils.toLong(json.get("instId").toString());
										if(idFuncionario>0L && idInstitucion>0L){
											FuncionarioDTO funcionarioDTO = new FuncionarioDTO(idFuncionario);
											Instituciones destino = Instituciones.getByValor(idInstitucion);
											
											for (DocumentoDTO documentoDTO : lstDocumentoDTO) {
												documentoDTO.setResponsableDocumento(funcionarioDTO);
											}
											documentoDelegate.enviarDocumentoAInstitucion(lstDocumentoDTO, casoDTO.getNumeroGeneralCaso() , destino);
										}
									}
								}					
								html.append("{\"exito\":\"Los documentos se han enviado con exito.\"}");
							}
					    }
				    } else {
				    	html.append("{\"error\":\"Parametros insuficientes.\"}");	
				    }				    
				} else {
					html.append("{\"error\":\"Parametros insuficientes.\"}");
				}
		} catch (Exception e) {		
			logger.error(e.getCause(),e);
			html.append("{\"error\":\"Ha ocurrido un error, por favor intente mas tarde.\"}");
		} finally {
			try {			
				PrintWriter printWriter = response.getWriter();
				printWriter.print(html.toString());
				printWriter.flush();
				printWriter.close();
			} catch(Exception e) {
				logger.error(e.getCause(),e);	
			}
		}
		return null;
	}
    
}	