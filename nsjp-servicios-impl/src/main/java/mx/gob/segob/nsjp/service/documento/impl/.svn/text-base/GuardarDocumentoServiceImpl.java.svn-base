/**
 * Nombre del Programa : GuardarDocumentoServiceImpl.java
 * Autor                            : Emigdio
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31/05/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci?n del servicio de negocio para almacenar documentos
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

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoOficioEstructurado;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.documento.CuerpoOficioEstructuradoDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.IndiceEstructuradoDAO;
import mx.gob.segob.nsjp.dao.documento.OficioEstructuradoDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.forma.FormaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudTranscricpionAudienciaDAO;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.CuerpoOficioEstructuradoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.CuerpoOficioEstructurado;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.IndiceEstructurado;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.OficioEstructurado;
import mx.gob.segob.nsjp.model.SolicitudTranscripcionAudiencia;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.actividad.AdministradorActividadesService;
import mx.gob.segob.nsjp.service.archivo.GuardarArchivoDigitalService;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.CuerpoOficioEstructuradoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.OficioEstructuradoTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.util.logging.resources.logging;

/**
 * Implementaci?n del servicio de negocio para almacenar documentos.
 * 
 * @version 1.0
 * @author Emigdio Hern?ndez
 * 
 */
@Service
@Transactional
public class GuardarDocumentoServiceImpl implements GuardarDocumentoService {
	
	public final static Logger LOGGER = 
		Logger.getLogger(GuardarDocumentoServiceImpl.class);

	@Autowired
	ExpedienteDAO expedienteDAO;	
	@Autowired
	DocumentoDAO documentoDAO;
	@Autowired
	GuardarArchivoDigitalService guardarArchivoDigitalService;
	@Autowired
	ActividadDAO actividadDAO;
	@Autowired
	ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	FuncionarioDAO funcionarioDAO;
	@Autowired
	AdministradorActividadesService administradorActividadesService;
	@Autowired
	SolicitudTranscricpionAudienciaDAO solicitudTranscricpionAudienciaDAO;
	@Autowired
	OficioEstructuradoDAO oficioDAO;
	@Autowired
	CuerpoOficioEstructuradoDAO cuerpoDAO;
	@Autowired
	NumeroExpedienteDAO numExpDAO;
	@Autowired
	FormaDAO formaDAO;
	@Autowired
	IndiceEstructuradoDAO indiceEstructuradoDAO;
	@Autowired
	ConfActividadDocumentoDAO confActividadDocumentoDAO;
	@Autowired
	NumeroExpedienteDAO numeroExpedienteDAO;
	
	@Override
	public Long guardarDocumento(DocumentoDTO documento,
			ExpedienteDTO expedienteActual, Long nuevaActividad) throws NSJPNegocioException {

		if(expedienteActual==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Long idDocumentoGen=0L;
		
		if(expedienteActual.getExpedienteId()==null && expedienteActual.getNumeroExpedienteId()!=null){
			Expediente consulta = null;
			consulta = expedienteDAO.consultarExpedientePorIdNumerExpediente(expedienteActual.getNumeroExpedienteId());
			if(consulta.getExpedienteId()!=null){
				expedienteActual.setExpedienteId(consulta.getExpedienteId());
			}
			if(consulta.getNumeroExpedienteId()!=null){
				expedienteActual.setNumeroExpedienteId(consulta.getNumeroExpedienteId());
			}			
		}
		
		// Nuevo documento - nueva actividad
		if(documento.getDocumentoId()==null){
			
			Actividad actividadActual = actividadDAO.obtenerActividadActual(expedienteActual.getExpedienteId());
			
			if((actividadActual!=null && actividadActual.getDocumento()!=null) ||
			   (nuevaActividad!=null && nuevaActividad!=0L)){
				ActividadDTO actividadDTO = administradorActividadesService
						.generarActividadEnBaseAOtra(actividadActual.getActividadId());
				actividadActual = actividadDAO.read(actividadDTO.getActividadId());
				
				if(nuevaActividad!=null && nuevaActividad!=0L){
					actividadActual.setTipoActividad(new Valor(nuevaActividad));
				}						
			}			

			Documento docBD = new Documento(); 
			docBD = DocumentoTransformer.transformarDocumentoDTO(documento);
			List<ConfActividadDocumento> actDocumento=null;
			if(actividadActual!=null)
				if(actividadActual.getTipoActividad()!=null)
					if(actividadActual.getTipoActividad().getValorId()!=null){
						actDocumento = confActividadDocumentoDAO.consultarPorTipoActFormaAndJerarquia(actividadActual.getTipoActividad().getValorId(),
								documento.getFormaDTO().getFormaId(), documento.getJerarquiaOrganizacional());						
					}


			if (actDocumento!=null && !actDocumento.isEmpty()) {
				docBD.setTipoDocumento(new Valor(actDocumento.get(0).getTipoDocumento().getValorId()));
			} else if (documento.getTipoDocumentoDTO()!=null && documento.getTipoDocumentoDTO().getIdCampo()!=null) {			
				docBD.setTipoDocumento(new Valor (documento.getTipoDocumentoDTO().getIdCampo()));
			} else {
				docBD.setTipoDocumento(new Valor(TipoDocumento.DOCUMENTO.getValorId()));
			}
			
			if (docBD.getArchivoDigital() != null) {
				archivoDigitalDAO.create(docBD.getArchivoDigital());
			}

			idDocumentoGen = documentoDAO.create(docBD);
			
			if(docBD.getFolioDocumento()==null || docBD.getFolioDocumento().equals("")){
				docBD.setFolioDocumento(docBD.getDocumentoId().toString());
			}

			documentoDAO.saveOrUpdate(docBD);
			
			if(actividadActual!=null){
				actividadActual.setDocumento(docBD);

				actividadDAO.saveOrUpdate(actividadActual);		
			}	


		}
		else{
			
			// Modificación de documento, recuperación de la actividad asociada
			
			Actividad actividadActual = null;
			actividadActual = actividadDAO.consultarActividadXExpedienteYDocumento(null, documento.getDocumentoId());
			
			Documento docBD = new Documento(); 
			docBD = DocumentoTransformer.transformarDocumentoDTO(documento);
			List<ConfActividadDocumento> actDocumento = null; 
			
			if(actividadActual != null){
				actDocumento =confActividadDocumentoDAO.consultarPorTipoActFormaAndJerarquia(actividadActual.getTipoActividad().getValorId(),
					documento.getFormaDTO().getFormaId(), documento.getJerarquiaOrganizacional());
			}
			
			if (actDocumento!=null && !actDocumento.isEmpty()) {
				docBD.setTipoDocumento(new Valor(actDocumento.get(0).getTipoDocumento().getValorId()));
			} else if (documento.getTipoDocumentoDTO()!=null && documento.getTipoDocumentoDTO().getIdCampo()!=null) {			
				docBD.setTipoDocumento(new Valor (documento.getTipoDocumentoDTO().getIdCampo()));
			} else {
				docBD.setTipoDocumento(new Valor(TipoDocumento.DOCUMENTO.getValorId()));
			}
			
			if (docBD.getArchivoDigital() != null) {
				archivoDigitalDAO.create(docBD.getArchivoDigital());
				docBD.setTextoParcial(null);
			}
			
			if(docBD.getFolioDocumento()==null || docBD.getFolioDocumento().equals("")){
				docBD.setFolioDocumento(docBD.getDocumentoId().toString());
			}
			
			documentoDAO.saveOrUpdate(docBD);

			if (actividadActual != null) {
				actividadActual.setDocumento(docBD);
	
				idDocumentoGen = docBD.getDocumentoId();
				
				actividadDAO.saveOrUpdate(actividadActual);
			} else if (nuevaActividad != null && nuevaActividad.equals(Actividades.SOLICITAR_TRANSCRIPCION_DE_AUDIENCIA.getValorId())){//Enable IT: REQ 82
				actividadActual = new Actividad();
				actividadActual.setDocumento(docBD);
				actividadActual.setExpediente(new Expediente(expedienteActual.getExpedienteId()));
				actividadActual.setFechaCreacion(new Date());
				actividadActual.setFuncionario(new Funcionario(documento.getResponsableDocumento().getClaveFuncionario()));
				actividadActual.setTipoActividad(new Valor(nuevaActividad));
				actividadDAO.saveOrUpdate(actividadActual);
			}
		}

		return idDocumentoGen;
	}

	@Override
	public Long guardarDocumentoTranscripcion(DocumentoDTO documentoDTO,
			Long idSolTrans) throws NSJPNegocioException {
		final Actividad actividad = new Actividad();

		SolicitudTranscripcionAudiencia solTranAudi = solicitudTranscricpionAudienciaDAO
				.read(idSolTrans);
		ExpedienteDTO expedienteDTO = ExpedienteTransformer
				.transformaExpediente(solTranAudi.getExpediente());
		// final Funcionario funcionario =
		// FuncionarioTransformer.transformarFuncionario(expTrabajo.getUsuario().getFuncionario());
		Valor tipoActividad = new Valor();
		tipoActividad.setValorId(Actividades.ELABORAR_TRANSCRIPCION_DE_AUDIENCIA
				.getValorId());
		actividad.setExpediente(solTranAudi.getExpediente());
		// actividad.setFuncionario(funcionario);
		actividad.setTipoActividad(tipoActividad);
		actividadDAO.create(actividad);

		return this.guardarDocumento(documentoDTO, expedienteDTO, null);
	}

	@Override
	public Long guardarActaAudiencia(DocumentoDTO documento,
			ExpedienteDTO expTrabajo) throws NSJPNegocioException {
		final Actividad actividad = new Actividad();
		Expediente expediente = ExpedienteTransformer
				.transformarExpediente(expTrabajo);
		final Funcionario funcionario = FuncionarioTransformer
				.transformarFuncionario(expTrabajo.getUsuario()
						.getFuncionario());
		Valor tipoActividad = new Valor();
		tipoActividad.setValorId(Actividades.FINAL_DE_AUDIENCIA.getValorId());
		actividad.setExpediente(expediente);
		actividad.setFuncionario(funcionario);
		actividad.setTipoActividad(tipoActividad);
		actividadDAO.create(actividad);
		return this.guardarDocumento(documento, expTrabajo, null);
	}

	@Override
	public String guardarTeoriaDeCaso(DocumentoDTO documentoDTO)
			throws NSJPNegocioException {
		if (documentoDTO == null ||
			documentoDTO.getOficioEstructuradoDTO() == null ||
			documentoDTO.getExpedienteDTO() == null ||
			documentoDTO.getOficioEstructuradoDTO() == null ||
			documentoDTO.getOficioEstructuradoDTO().getCuerposOficio() == null ||
			documentoDTO.getOficioEstructuradoDTO().getCuerposOficio().size() < 0){ 
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Documento teo = actividadDAO.consultarDocumentosXExpediente(
				documentoDTO.getExpedienteDTO().getExpedienteId(),
				TipoDocumento.TEORIA_DEL_CASO.getValorId());
		
		// valorRetorno concatena id del documento , id del cuerpoOficioEstructurado
		String valorRetorno = "";
		Long cuerpoOficioEstructuradoId = 0L;
		
		if (teo == null) {// SI NO Existe TEORIA DEL CASO por el expediente
							// -->Guarda objeto estructurado y guarda documento
			OficioEstructurado oficio = OficioEstructuradoTransformer
					.transformarOficioDTO(documentoDTO
							.getOficioEstructuradoDTO());

			// Obligatorios de Oficio 
			oficio.setEncabezado("TEORIA DEL CASO");
			oficio.setPie("");
			oficio.setTipoOficio(new Valor(TipoOficioEstructurado.TEORIA_DEL_CASO
					.getValorId()));
			
			Long idOficio = oficio.getOficioEstructuradoId(); //Siempre regresa nulo? Ver CU
			
			documentoDTO.setFechaCreacion(new Date());
			FormaDTO formaDTO=new FormaDTO();
			formaDTO.setFormaId(Formas.ACTA.getValorId());
			documentoDTO.setFormaDTO(formaDTO);
			documentoDTO.setNombreDocumento("Teoria del caso");
			ValorDTO valorDTO=new ValorDTO();
			valorDTO.setIdCampo(TipoDocumento.TEORIA_DEL_CASO.getValorId());
			documentoDTO.setTipoDocumentoDTO(valorDTO);
			Documento documento=new Documento();
			DocumentoTransformer.tranformarDocumentoUpdate(documento, documentoDTO);
			oficio.setDocumento(documento);
			
			if(idOficio==null){ //Se crea el oficio
				List<CuerpoOficioEstructurado> cuerposVista = oficio.getCuerposOficio();
				oficio.setCuerposOficio(null);
				idOficio = oficioDAO.create(oficio);//Al momento de crear el Oficio se crea el documento asociado
				oficio.setCuerposOficio(cuerposVista);
			}
			//Se guardan los Cuerpos de Oficio 
			if (oficio.getCuerposOficio() != null
					&& oficio.getCuerposOficio().size() > 0) {
				List<CuerpoOficioEstructurado> cuerpos = oficio
						.getCuerposOficio();
				for (CuerpoOficioEstructurado coe : cuerpos) {
					coe.setOficioEstructurado(new OficioEstructurado(idOficio));
					if (coe.getCuerpoOficioEstructuradoId() == null) {
						//Si es el que se modifico el texto se guarda el texto que se envia
						//Si no lo es, se tienen que consulta del Indice Estructurado
						if(coe.getInteresa()== null || !coe.getInteresa()){
							IndiceEstructurado indice =  indiceEstructuradoDAO.read(coe.getIndiceEstructurado().getIndiceEstructuradoId());
							coe.setTexto( indice.getTextoEtiqueta());
						}
						cuerpoOficioEstructuradoId = cuerpoDAO.create(coe);
					}
				}
			}
			Long idDocumento = 0L;
			if( oficio!= null && oficio.getDocumento()!= null && oficio.getDocumento().getDocumentoId()!= null)
				idDocumento = oficio.getDocumento().getDocumentoId();
			else{
				//Leer el documento asociado al oficio	
				OficioEstructurado oficioLect = oficioDAO.read(idOficio);
				idDocumento = oficioLect.getDocumento().getDocumentoId();
			}
	
			/* Asignar en actividad */
			Documento docu = new Documento();
			docu.setDocumentoId(idDocumento);
			Actividad actividadActual = actividadDAO
					.obtenerActividadActual(documentoDTO.getExpedienteDTO().getExpedienteId());
			if (actividadActual != null) {
				actividadActual.setDocumento(docu);
				if (actividadActual.getDocumento() == null) {
					actividadDAO.update(actividadActual);
				} else {
					actividadActual.setFechaCreacion(new Date());
					actividadDAO.create(actividadActual);
				}
			} else {
				Actividad nuevaAct = new Actividad();
				nuevaAct.setDocumento(docu);
				nuevaAct.setExpediente(new Expediente(documentoDTO.getExpedienteDTO().getExpedienteId()));
				nuevaAct.setFechaCreacion(new Date());
				nuevaAct.setTipoActividad(new Valor(
						Actividades.ATENDER_CANALIZACION_JAR.getValorId()));
				NumeroExpediente numero = numExpDAO.obtenerNumeroExpedienteXExpediente(documentoDTO.getExpedienteDTO().getExpedienteId());
				Long claveFunc=1L;
				if(numero!=null){
				Funcionario funcionar=funcionarioDAO.consultarFuncionarioXExpediente(numero);
				if(funcionar!=null)
					claveFunc=funcionar.getClaveFuncionario();
				}
				
				nuevaAct.setFuncionario(new Funcionario(claveFunc));
				actividadDAO.create(nuevaAct);
			}

			valorRetorno = idDocumento.toString() + "," + cuerpoOficioEstructuradoId.toString();

		} else {// SI Existe TEORIA DEL CASO por expediente --> Guarda, Elimina
				// y Actualiza objeto estructurado

			List<CuerpoOficioEstructurado> cuerposVista = sacaConvierteCuerpos(documentoDTO
					.getOficioEstructuradoDTO().getCuerposOficio());

			if (teo.getOficioEstructurado() != null && teo.getOficioEstructurado().getCuerposOficio() != null) {
				
				List<CuerpoOficioEstructurado> cuerposBD = teo.getOficioEstructurado().getCuerposOficio();

				//Para los Cuerpos estructurados asociados, la busqueda es sobre BD y se divide en 3 pasos 
				siguiente:
				for (int contBD=0; contBD<cuerposBD.size(); contBD++) {
					CuerpoOficioEstructurado coeBD = cuerposBD.get(contBD);
					LOGGER.info(" cuerposBD ("+ cuerposBD.size()+") - contBD->"+contBD+": "+ cuerposBD);
					for (int contVista = 0; contVista< cuerposVista.size(); contVista++) {
						CuerpoOficioEstructurado coeVI = cuerposVista.get(contVista);
						LOGGER.info(" cuerposVista ("+ cuerposVista.size()+") - contVista->"+contVista+": "+ cuerposVista);
						
						//1.- Si tiene ID (Vista)  y se tiene en BD  --> Actualizar
						if(coeVI.getCuerpoOficioEstructuradoId()!= null &&
								coeVI.getCuerpoOficioEstructuradoId().equals(coeBD.getCuerpoOficioEstructuradoId())){
							
							//Si no es el que interesa, se conserva el que tiene en BD
							if(coeVI.getInteresa()== null || !coeVI.getInteresa())
								coeBD.setTexto( coeBD.getTexto());
							else //Si interesa entonces se debe setear el texto de vista
								coeBD.setTexto( coeVI.getTexto());
														
							CuerpoOficioEstructuradoTransformer.transformarCuerpoUpdate(coeBD, coeVI);
							cuerpoDAO.update(coeBD);
							cuerpoOficioEstructuradoId=coeBD.getCuerpoOficioEstructuradoId();
							
							LOGGER.info("Actualiz? el cuerpo: "+coeBD.getCuerpoOficioEstructuradoId());
							
							cuerposBD.remove(contBD);
							cuerposVista.remove(contVista);
							LOGGER.info(" cuerposBD ("+ cuerposBD.size()+") - contBD->"+contBD+": "+ cuerposBD);
							LOGGER.info(" cuerposVista ("+ cuerposVista.size()+") - contVista->"+contVista+": "+ cuerposVista);
							contBD--;
							continue siguiente;
						}
					}
				}
				
				//2.- Los que estan en la lista de cuerpos BD se eliminan No se hace uso del deleteAll 
				for (CuerpoOficioEstructurado coeBD : cuerposBD) {
					coeBD.setIndiceEstructurado(null);
					coeBD.setOficioEstructurado(null);
					LOGGER.info("Va a eliminar el cuerpo: "+coeBD.getCuerpoOficioEstructuradoId());
					LOGGER.info("texto: "+coeBD.getTexto());
					cuerpoDAO.delete(coeBD);
				}
				
				//3.- Los que estan en cuerposVista se crean. No se hace uso del CreateAll
				for (CuerpoOficioEstructurado coeVI : cuerposVista) {
					coeVI.setOficioEstructurado(new OficioEstructurado(teo.getOficioEstructurado().getOficioEstructuradoId()));
					//Si es el que se modifico el texto se guarda el texto que se envia
					//Si no lo es, se tienen que consulta del Indice Estructurado
					if(coeVI.getInteresa()== null || !coeVI.getInteresa()){
						IndiceEstructurado indice =  indiceEstructuradoDAO.read(coeVI.getIndiceEstructurado().getIndiceEstructuradoId());
						coeVI.setTexto( indice.getTextoEtiqueta());
					}
					
					cuerpoOficioEstructuradoId=cuerpoDAO.create(coeVI);
				}
			}


			valorRetorno = teo.getDocumentoId().toString() + "," + cuerpoOficioEstructuradoId.toString();
		}
		
		return valorRetorno;
	}
	
	/**
	 * Metodo para guardar el pliego de consignacion
	 */
	
	@Override
	public Long guardarPliegoConsignacion(DocumentoDTO documentoDTO)
			throws NSJPNegocioException {
		if (documentoDTO == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if (documentoDTO.getOficioEstructuradoDTO() == null
				|| documentoDTO.getExpedienteDTO() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if(documentoDTO.getOficioEstructuradoDTO() == null ||
				documentoDTO.getOficioEstructuradoDTO().getCuerposOficio() == null ||
				documentoDTO.getOficioEstructuradoDTO().getCuerposOficio().size() < 0) 
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		Documento teo = actividadDAO.consultarDocumentosXExpediente(
				documentoDTO.getExpedienteDTO().getExpedienteId(),
				TipoDocumento.PLIEGO_DE_CONSIGNACION.getValorId());
		if (teo == null) {// SI NO Existe PLIEGOO DE CONSIGNACION por el expediente
							// -->Guarda objeto estructurado y guarda documento
			OficioEstructurado oficio = OficioEstructuradoTransformer
					.transformarOficioDTO(documentoDTO
							.getOficioEstructuradoDTO());

			// Obligatorios de Oficio 
			oficio.setEncabezado("PLIEGO DE CONSIGNACION");
			oficio.setPie("");
			oficio.setTipoOficio(new Valor(TipoOficioEstructurado.PLIEGO_DE_CONSIGNACION
					.getValorId()));
			
			Long idOficio = oficio.getOficioEstructuradoId(); //Siempre regresa nulo? Ver CU
			
			documentoDTO.setFechaCreacion(new Date());
			FormaDTO formaDTO=new FormaDTO();
			formaDTO.setFormaId(Formas.ACTA.getValorId());
			documentoDTO.setFormaDTO(formaDTO);
			documentoDTO.setNombreDocumento("Pliego de Consignacion");
			ValorDTO valorDTO=new ValorDTO();
			valorDTO.setIdCampo(TipoDocumento.PLIEGO_DE_CONSIGNACION.getValorId());
			documentoDTO.setTipoDocumentoDTO(valorDTO);
			Documento documento=new Documento();
			DocumentoTransformer.tranformarDocumentoUpdate(documento, documentoDTO);
			oficio.setDocumento(documento);
			
			if(idOficio==null){ //Se crea el oficio
				List<CuerpoOficioEstructurado> cuerposVista = oficio.getCuerposOficio();
				oficio.setCuerposOficio(null);
				idOficio = oficioDAO.create(oficio);//Al momento de crear el Oficio se crea el documento asociado
				oficio.setCuerposOficio(cuerposVista);
			}
			//Se guardan los Cuerpos de Oficio 
			if (oficio.getCuerposOficio() != null
					&& oficio.getCuerposOficio().size() > 0) {
				List<CuerpoOficioEstructurado> cuerpos = oficio
						.getCuerposOficio();
				for (CuerpoOficioEstructurado coe : cuerpos) {
					coe.setOficioEstructurado(new OficioEstructurado(idOficio));
					if (coe.getCuerpoOficioEstructuradoId() == null) {
						//Si es el que se modifico el texto se guarda el texto que se envia
						//Si no lo es, se tienen que consulta del Indice Estructurado
						if(coe.getInteresa()== null || !coe.getInteresa()){
							IndiceEstructurado indice =  indiceEstructuradoDAO.read(coe.getIndiceEstructurado().getIndiceEstructuradoId());
							coe.setTexto( indice.getTextoEtiqueta());
						}
						cuerpoDAO.create(coe);
					}
				}
			}
			Long idDocumento = 0L;
			if( oficio!= null && oficio.getDocumento()!= null && oficio.getDocumento().getDocumentoId()!= null)
				idDocumento = oficio.getDocumento().getDocumentoId();
			else{
				//Leer el documento asociado al oficio	
				OficioEstructurado oficioLect = oficioDAO.read(idOficio);
				idDocumento = oficioLect.getDocumento().getDocumentoId();
			}
	
			/* Asignar en actividad */
			Documento docu = new Documento();
			docu.setDocumentoId(idDocumento);
			Actividad actividadActual = actividadDAO
					.obtenerActividadActual(documentoDTO.getExpedienteDTO().getExpedienteId());
			if (actividadActual != null) {
				actividadActual.setDocumento(docu);
				if (actividadActual.getDocumento() == null) {
					actividadDAO.update(actividadActual);
				} else {
					actividadActual.setFechaCreacion(new Date());
					actividadDAO.create(actividadActual);
				}
			} else {
				Actividad nuevaAct = new Actividad();
				nuevaAct.setDocumento(docu);
				nuevaAct.setExpediente(new Expediente(documentoDTO.getExpedienteDTO().getExpedienteId()));
				nuevaAct.setFechaCreacion(new Date());
				nuevaAct.setTipoActividad(new Valor(
						Actividades.ATENDER_CANALIZACION_JAR.getValorId()));
				NumeroExpediente numero = numExpDAO.obtenerNumeroExpedienteXExpediente(documentoDTO.getExpedienteDTO().getExpedienteId());
				Long claveFunc=1L;
				if(numero!=null){
				Funcionario funcionar=funcionarioDAO.consultarFuncionarioXExpediente(numero);
				if(funcionar!=null)
					claveFunc=funcionar.getClaveFuncionario();
				}
				
				nuevaAct.setFuncionario(new Funcionario(claveFunc));
				actividadDAO.create(nuevaAct);
			}

			return idDocumento;

		} else {// SI Existe PLIEGO DE CONSIGNACION por expediente --> Guarda, Elimina
				// y Actualiza objeto estructurado

			List<CuerpoOficioEstructurado> cuerposVista = sacaConvierteCuerpos(documentoDTO
					.getOficioEstructuradoDTO().getCuerposOficio());

			if (teo.getOficioEstructurado() != null && teo.getOficioEstructurado().getCuerposOficio() != null) {
				
				List<CuerpoOficioEstructurado> cuerposBD = teo.getOficioEstructurado().getCuerposOficio();

				//Para los Cuerpos estructurados asociados, la busqueda es sobre BD y se divide en 3 pasos 
				siguiente:
				for (int contBD=0; contBD<cuerposBD.size(); contBD++) {
					CuerpoOficioEstructurado coeBD = cuerposBD.get(contBD);
					LOGGER.info(" cuerposBD ("+ cuerposBD.size()+") - contBD->"+contBD+": "+ cuerposBD);
					for (int contVista = 0; contVista< cuerposVista.size(); contVista++) {
						CuerpoOficioEstructurado coeVI = cuerposVista.get(contVista);
						LOGGER.info(" cuerposVista ("+ cuerposVista.size()+") - contVista->"+contVista+": "+ cuerposVista);
						
						//1.- Si tiene ID (Vista)  y se tiene en BD  --> Actualizar
						if(coeVI.getCuerpoOficioEstructuradoId()!= null &&
								coeVI.getCuerpoOficioEstructuradoId().equals(coeBD.getCuerpoOficioEstructuradoId())){
							
							//Si no es el que interesa, se conserva el que tiene en BD
							if(coeVI.getInteresa()== null || !coeVI.getInteresa())
								coeBD.setTexto( coeBD.getTexto());
							else //Si interesa entonces se debe setear el texto de vista
								coeBD.setTexto( coeVI.getTexto());
														
							CuerpoOficioEstructuradoTransformer.transformarCuerpoUpdate(coeBD, coeVI);
							cuerpoDAO.update(coeBD);
							
							LOGGER.info("Actualiz? el cuerpo: "+coeBD.getCuerpoOficioEstructuradoId());
							
							cuerposBD.remove(contBD);
							cuerposVista.remove(contVista);
							LOGGER.info(" cuerposBD ("+ cuerposBD.size()+") - contBD->"+contBD+": "+ cuerposBD);
							LOGGER.info(" cuerposVista ("+ cuerposVista.size()+") - contVista->"+contVista+": "+ cuerposVista);
							contBD--;
							continue siguiente;
						}
					}
				}
				
				//2.- Los que estan en la lista de cuerpos BD se eliminan No se hace uso del deleteAll 
				for (CuerpoOficioEstructurado coeBD : cuerposBD) {
					coeBD.setIndiceEstructurado(null);
					coeBD.setOficioEstructurado(null);
					LOGGER.info("Va a eliminar el cuerpo: "+coeBD.getCuerpoOficioEstructuradoId());
					LOGGER.info("texto: "+coeBD.getTexto());
					cuerpoDAO.delete(coeBD);
				}
				
				//3.- Los que estan en cuerposVista se crean. No se hace uso del CreateAll
				for (CuerpoOficioEstructurado coeVI : cuerposVista) {
					coeVI.setOficioEstructurado(new OficioEstructurado(teo.getOficioEstructurado().getOficioEstructuradoId()));
					//Si es el que se modifico el texto se guarda el texto que se envia
					//Si no lo es, se tienen que consulta del Indice Estructurado
					if(coeVI.getInteresa()== null || !coeVI.getInteresa()){
						IndiceEstructurado indice =  indiceEstructuradoDAO.read(coeVI.getIndiceEstructurado().getIndiceEstructuradoId());
						coeVI.setTexto( indice.getTextoEtiqueta());
					}
					
					Long idLoc=cuerpoDAO.create(coeVI);
					LOGGER.info("Creo el cuerpo: "+idLoc);
				}
			}

			return teo.getDocumentoId();
		}
	}

	private List<CuerpoOficioEstructurado> sacaConvierteCuerpos(
			List<CuerpoOficioEstructuradoDTO> cuerposOficio) {
		List<CuerpoOficioEstructurado> cuerpos = new ArrayList<CuerpoOficioEstructurado>();
		for (CuerpoOficioEstructuradoDTO cdto : cuerposOficio) {
			cuerpos.add(CuerpoOficioEstructuradoTransformer
					.transformarCuerpoDTO(cdto));
		}
		return cuerpos;
	}

    @Override
    public Long guardarDocumentoIntraInstitucion(DocumentoDTO documentoDTO,
            ExpedienteDTO expedienteDTO) {
        NumeroExpediente neActual = this.numExpDAO.read(expedienteDTO
                .getNumeroExpedienteId());
        Documento docBD = DocumentoTransformer
                .transformarDocumentoDTO(documentoDTO);
        docBD.setForma(new Forma(Formas.PLANTILLA_EN_BLANCO.getValorId())); // se pone forma Id de plantilla en blanco !!
        LOGGER.info("FORMA ID del Documneto ::::   "+ docBD.getForma().getFormaId());
        if(docBD!= null && docBD.getArchivoDigital()!= null ){
           
        	ArchivoDigital arch = docBD.getArchivoDigital();
            arch.setArchivoDigitalId(null);
            arch.setArchivoDigitalId(this.archivoDigitalDAO.create(arch));
            docBD.setArchivoDigital(arch);
        }
        	

        
        docBD.setResponsableDocumento(neActual.getFuncionario());
        docBD.setDocumentoId(this.documentoDAO.create(docBD));

        Actividad act = new Actividad();
        
        if (documentoDTO.getActividadDTO() != null && documentoDTO.getFechaCreacion() != null && documentoDTO.getActividadDTO().getTipoActividad() != null && documentoDTO.getActividadDTO().getTipoActividad().getValorId()!=null){
            LOGGER.info("SE AGREGA EL TIPO DE A LA ACTIVIDAD ORIGINAL:: " + documentoDTO.getActividadDTO().getTipoActividad().getValorId());
            act.setTipoActividad(new Valor(documentoDTO.getActividadDTO().getTipoActividad().getValorId()));
            
            LOGGER.info("SE AGREGAN LA FECHA DE CREACION ORIGINAL A LA ACTIVIDAD:: " + documentoDTO.getActividadDTO().getFechaCreacion() );
            act.setFechaCreacion(documentoDTO.getActividadDTO().getFechaCreacion());
        }
        else{
        	act.setTipoActividad(new Valor(2300L));
        	act.setFechaCreacion(new Date());
        }
        
        act.setFuncionario(neActual.getFuncionario());
        act.setExpediente(neActual.getExpediente());
        
        act.setDocumento(docBD);
        
        this.actividadDAO.create(act);

        return docBD.getDocumentoId();
    }

	@Override
	public Long guardarDocumentoConActividadDocumento(DocumentoDTO documentoDto,
			ExpedienteDTO expedienteDto) throws NSJPNegocioException {

		if (expedienteDto == null
				|| expedienteDto.getExpedienteId() == null
				|| documentoDto == null
				|| documentoDto.getActividadDTO() == null
				|| documentoDto.getActividadDTO().getTipoActividad() == null
				|| documentoDto.getActividadDTO().getTipoActividad().getValorId() == null) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		//OBTENEMOS EL FUNCIONARIO RESPONSABLE DEL NUMERO DE EXPEDIENTE
		NumeroExpediente numExp = null;
		
		//List<NumeroExpediente> numeroExpedientes = numeroExpedienteDAO.consultarNumeroExpedientesXIdExpAreaDiscriminante(expedienteDto.getExpedienteId(),Areas.UNIDAD_INVESTIGACION.parseLong(),null);
		List<NumeroExpediente> numeroExpedientes = numeroExpedienteDAO.consultarNumeroExpedientesXIdExpAreaDiscriminante(expedienteDto.getExpedienteId(),null,null);
		
		
		if (numeroExpedientes == null || numeroExpedientes.size() < 1
				|| numeroExpedientes.get(0).getFuncionario() == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		numExp = numeroExpedientes.get(0);
		
		
		//TRANSFORMAMOS EL DOCUMENTO
		Documento documento = DocumentoTransformer.transformarDocumentoDTO(documentoDto);
		
		if(documentoDto.getActividadDTO().getTipoActividad().getValorId().equals(Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR.getValorId())){
			
			documentoDto
					.getActividadDTO()
					.setActividadId(
							mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR
									.getValorId());
		}
		if(documentoDto.getActividadDTO().getTipoActividad().getValorId().equals(Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL.getValorId())){
			documentoDto
			.getActividadDTO()
			.setActividadId(
					mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL
							.getValorId());
		}
		
		ConfActividadDocumento confActividadDocumento = null;
		
		//OBTENEMOS EL REGISTRO DE LA ACTIVIDAD EN BASE A LA ACTIVIDAD RECIBIDA COMO PARAMETRO
		if(documentoDto.getActividadDTO().getActividadId() != null){
			confActividadDocumento = confActividadDocumentoDAO.read(documentoDto.getActividadDTO().getActividadId());
		}
		
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
		
		documento.setForma(new Forma(confActividadDocumento.getForma().getFormaId()));
		documento.setTipoDocumento(new Valor(confActividadDocumento.getTipoDocumento().getValorId()));
		documento.setNombreDocumento(confActividadDocumento.getTipoDocumento().getValor());
		
		
		
		//GENERAR LA ACTIVIDAD ASOCIADA AL DOCUMENTO Y AL EXPEDIENTE
		Actividad actividad = new Actividad();
		actividad.setFuncionario(numExp.getFuncionario());
		actividad.setExpediente(new Expediente(expedienteDto.getExpedienteId()));
		
		Valor tipoActividad = new Valor(confActividadDocumento
				.getTipoActividad().getValorId(), confActividadDocumento
				.getTipoActividad().getValor());
		
		actividad.setTipoActividad(tipoActividad);
		actividad.setFechaCreacion(new Date());
		
		documento.setActividad(actividad);
		actividad.setDocumento(documento);
		
		if (documento.getArchivoDigital() != null) {
			archivoDigitalDAO.create(documento.getArchivoDigital());
		}

		return documentoDAO.create(documento);
	}

}
