/**
* Nombre del Programa : ActualizarCarpetaDeInvestigacionServiceImpl.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 28/07/2011
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
package mx.gob.segob.nsjp.service.expediente.impl;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoMovimiento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.hecho.HechoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.model.*;
import mx.gob.segob.nsjp.service.bitacora.RegistrarBitacoraService;
import mx.gob.segob.nsjp.service.delito.GuardarDelitoService;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.expediente.ActualizarCarpetaDeInvestigacionService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.hecho.IngresarHechoService;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;
import mx.gob.segob.nsjp.service.objeto.IngresarObjetoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Definicion de servisios para actualizar la información de la carpeta de investigacion (Expediente)
 * que proviene de Procuraduria.
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
@Service
@Transactional
public class ActualizarCarpetaDeInvestigacionServiceImpl implements ActualizarCarpetaDeInvestigacionService {
    private final static Logger logger = Logger.getLogger(ActualizarCarpetaDeInvestigacionServiceImpl.class);
    
	@Autowired 
	private SolicitudDAO solicitudDAO;
	@Autowired  
	private IngresarIndividuoService ingresarIndividuoService;
	@Autowired  
	private GuardarDocumentoService guardarDocumentoService;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private IngresarObjetoService ingresarObjetoService;
	@Autowired
	private IngresarHechoService ingresarHechoService;
	@Autowired
	private RegistrarBitacoraService regBitService;
    @Autowired
    private DelitoDAO delitoDao;
    @Autowired
    private DelitoPersonaDAO delitoPerDao;
    @Autowired
    private InvolucradoDAO involucradoDAO;
    @Autowired
    private CatDelitoDAO catDelitoDAO; 
    @Autowired
    private GuardarDelitoService guardarDelitoService;
    
	
	@Override
    public ExpedienteDTO consultarExpedientePorFolioSolicitud(
            String folioSolicitud) throws NSJPNegocioException {

        if (folioSolicitud == null || folioSolicitud.trim().isEmpty())
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        System.out.print("************ ANTES DE CONSULTAR SOLICITUD POR FOLIO ************ "+folioSolicitud);
        Solicitud solicitud = solicitudDAO
                .consultarSolicitudPorFolio(folioSolicitud);
        System.out.print("************  DESPUES DE CONSULTAR SOLICITUD POR FOLIO ************ "+ String.valueOf(solicitud));
        System.out.print("************  TRAER EL DOCUMENTO ID DEL OBJETO SOLICITUD ************ "+ solicitud.getDocumentoId());
        if (solicitud == null || solicitud.getDocumentoId() == null
                || solicitud.getDocumentoId() < 0)
            throw new NSJPNegocioException(
                    CodigoError.INFORMACION_PARAMETROS_ERRONEA);
        System.out.print("************ DESPUES DE LA VALIDACION DE SI SOLICITUD ESTA VACIA ************ ");
        System.out.print("************ ANTES DE LA CONSULTA POR NUMERO DE EXPEDIENTE  ************ "+solicitud.getDocumentoId());
        Expediente expediente = solicitudDAO
                .consultarExpedienteDeNumeroExpedienteSolicitud(solicitud
                        .getDocumentoId());
        System.out.print("************ DESPUES DE CONSULTA POR NUMERO DE EXPEDIENTE  ************ "+ String.valueOf(expediente));
        ExpedienteDTO expedienteDTO = ExpedienteTransformer
                .transformaExpediente(expediente);
        System.out.print("************ DESPUES DE EXPEDIENTE TRANSFORMADO  ************ "+ String.valueOf(expedienteDTO));
        System.out.print("************ ANTES DE ASIGNACION DEL NUMERO DE EXPEDIENTE  ************ ");
        expedienteDTO.setNumeroExpedienteId(solicitud.getNumeroExpediente()
                .getNumeroExpedienteId());
        System.out.print("************ DESPUES DE ASIGNACION DEL NUMERO DE EXPEDIENTE  ANTES DEL RETURN************ ");
        return expedienteDTO;
    }
	
	@Override
    public Long actualizarExpedientePorFolioSolicitud(String folioSolicitud,
            EstatusExpediente estatus) throws NSJPNegocioException {
        if (folioSolicitud == null || folioSolicitud.trim().isEmpty())
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

        Solicitud solicitud = solicitudDAO
                .consultarSolicitudPorFolio(folioSolicitud);
        if (solicitud == null || solicitud.getDocumentoId() == null
                || solicitud.getDocumentoId() < 0)
            throw new NSJPNegocioException(
                    CodigoError.INFORMACION_PARAMETROS_ERRONEA);
        logger.info("Numero de Expediente a actualizar "
                + solicitud.getNumeroExpediente().getNumeroExpediente());
        NumeroExpediente expediente = solicitud.getNumeroExpediente();
        expediente.setEstatus(new Valor(estatus.getValorId()));
        numeroExpedienteDAO.update(expediente);
        RegistroBitacora regBitEta = new RegistroBitacora();

        regBitEta.setFechaInicio(new Date());
        regBitEta.setTipoMovimiento(new Valor(
                TipoMovimiento.CAMBIO_DE_ESTATUS_DE_EXPEDIENTE.getValorId()));
        regBitEta.setNuevo(String.valueOf(estatus.getValorId()));
        regBitEta.setNumeroExpediente(expediente);
        regBitService.registrarMovimientoDeExpedienteEnBitacora(regBitEta);
        return expediente.getNumeroExpedienteId();

    }
	
	/*se sobrecarga el metodo para no interferir con otras funcionalidades/*ByYolo*/
	public ExpedienteDTO actualizarExpedienteDeCarpetaInvestigacionDefensoria(
            ExpedienteDTO expedienteDTO, DelitoDTO delitoPrincipalDTO) throws NSJPNegocioException {

        try {
            ExpedienteDTO expedienteDTOTemp = actualizarExpedienteDeCarpetaInvestigacionDefensoria(expedienteDTO);
        }catch(Exception errores){
            System.out.println("ERROR AL ACTUALIZAR EXPEDIENTE CARPETA INVESTIGACION DEFENSORIA"+errores.getMessage());
        }
        /*se ingresan delito a BD/*ByYolo*/
      if (delitoPrincipalDTO != null) {
    	  ExpedienteDTO expedienteTempDTO = new ExpedienteDTO(expedienteDTO.getExpedienteId());
    	  List<DelitoDTO> delitoPrincipal = new ArrayList<DelitoDTO>();
    	  delitoPrincipal.add(delitoPrincipalDTO);
    	  guardarDelitoService.guardarDelito(delitoPrincipal,expedienteTempDTO, "0");
      }        
        /*Fin yolo*/	
		return expedienteDTO;
	}
	
	/*Fin metodo*/
	
    public ExpedienteDTO actualizarExpedienteDeCarpetaInvestigacionDefensoria(ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
        logger.info(" *********** Servicio actualizarExpedienteDeCarpetaInvestigacionDefensoria***********");
        System.out.println("*********** Servicio actualizarExpedienteDeCarpetaInvestigacionDefensoria***********");
        System.out.println("EXPEDIENTE "+String.valueOf(expedienteDTO));
        logger.info(" Expediente:" + expedienteDTO);
        System.out.println("*********** Expediente id ***********"+expedienteDTO.getExpedienteId());
        //System.out.println("*********** Expediente id ***********"+expedienteDTO.getNumeroExpedienteId());
        if (expedienteDTO == null || expedienteDTO.getExpedienteId() == null || expedienteDTO.getExpedienteId() < 0){
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        System.out.println("*********** PASO DESPUES DEL EXPEDIENTEID ***********");
        System.out.println(" Expediente ID:" + expedienteDTO.getNumeroExpedienteId());
        System.out.println("*********** ANTES DE LA VALIDACION DEL EXPEDIENTEDTO.GETOBJETOSDTO ***********"+String.valueOf(expedienteDTO.getObjetosDTO()));
        if (expedienteDTO.getObjetosDTO() != null && !expedienteDTO.getObjetosDTO().isEmpty()) {
            System.out.println("*********** ENTRO AL INGRESAR OBJETOS DTO ***********");
            List<ObjetoDTO> objetosDTO = expedienteDTO.getObjetosDTO();
            for (ObjetoDTO objetoDTO : objetosDTO) {
                System.out.println("*********** BARRE OBJETO DTO  ***********"+objetosDTO);
                objetoDTO.setExpedienteDTO(new ExpedienteDTO(expedienteDTO.getExpedienteId()));
                //objetoDTO.setExpedienteDTO(new ExpedienteDTO(expedienteDTO.getNumeroExpedienteId()));
                Long idObjeto = ingresarObjetoService.ingresarObjetoCarpetaInvestigacion(objetoDTO);
                logger.info(" Se ingreso en objeto con id: " + idObjeto);
            }
        }
        System.out.println("*********** SALE DEL IF DE INGRESAR OBJETOS DTO ***********");
        // Ingresar involucrado con toda su dependencia
        System.out.println("*********** ANTES DE INGRESAR INVOLUCRADO DTO ***********"+String.valueOf(expedienteDTO.getInvolucradosDTO()));
        if (expedienteDTO.getInvolucradosDTO() != null && !expedienteDTO.getInvolucradosDTO().isEmpty()) {
            for (InvolucradoDTO involucradoDTO : expedienteDTO.getInvolucradosDTO()) {
                involucradoDTO.setExpedienteDTO(new ExpedienteDTO(expedienteDTO.getExpedienteId()));
                final Involucrado involucrado 	= involucradoDAO.consultarInvolucradoPorFolioElemento(involucradoDTO.getFolioElemento());
                Long idIndividuo 				= ingresarIndividuoService.ingresarIndividuoInterInstitucion(involucradoDTO, true);
                logger.info("Se ingreso un individuo con id: " + idIndividuo);
                //Si se encontro el involucrado es posible que tenga delitos relacionados
                if(involucrado!=null){
                	//Se debe de eliminar la informacion del delito y delitoPersona
                	/*Enable IT descomentando linea para no repetir delitos de las solicitudes*/
                	this.eliminarDelitos(idIndividuo) ;
                }
                // guarda delito cometidos
                if (involucradoDTO.getDelitosCometidos() != null) {
                	logger.debug("Delitos a guardar para el involucrado :: " + idIndividuo);
                    for (DelitoDTO deli : involucradoDTO.getDelitosCometidos()) {
                    	Delito delitoPojo = new Delito();
                    	//Comienza
                    	CatDelito catDelitoFiltro = new CatDelito();
                    	List<CatDelito> catDelitosEncontrados = new ArrayList<CatDelito>();
                        //BUSCAR EL DELITO POR CLAVE DELITO PARA EVITAR LA REPLICACION DE DELITOS ENTRE BASES DE DATOS//
                    	if(deli.getCatDelitoDTO() != null && deli.getCatDelitoDTO().getClaveInterInstitucional()!= null){
                    		catDelitoFiltro.setClaveInterInstitucional(deli.getCatDelitoDTO().getClaveInterInstitucional());
                    		try{
                    			catDelitosEncontrados = catDelitoDAO.consultarCatDelitoPorFilro(catDelitoFiltro);
                    		}catch (Exception e) {
                    			logger.debug("NO EXISTE EL CAT_DELITO CON LA CLAVE INTERINSTITUCIONAL::::");
							}
//                    		catDelitosEncontrados = catDelitoDAO.consultarCatDelitoPorFilro(catDelitoFiltro);
                    		if(catDelitosEncontrados == null || catDelitosEncontrados.size() <= 0){
								logger.debug("NO EXISTE EL CAT_DELITO CON LA CLAVE INTERINSTITUCIONAL::::"+ deli.getCatDelitoDTO().getClaveInterInstitucional());
                    			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
                    		}
                    	}
                    	delitoPojo.setCatDelito(new CatDelito(catDelitosEncontrados.get(0).getCatDelitoId()));
                        logger.debug("DELITO ENCONTRADO PARA ASOCIACION :: " + catDelitoFiltro.getCatDelitoId());
                        //Termina
                        delitoPojo.setEsProbable(Boolean.TRUE);
                        delitoPojo.setExpediente(new Expediente(expedienteDTO.getExpedienteId()));
                        delitoPojo.setEsPrincipal(deli.getEsPrincipal());
                        Long idDelito = this.delitoDao.create(delitoPojo);
                        logger.info(" Delito creado:" + idDelito);
                        Delito delitoPojoBD = delitoDao.read(idDelito);
                        Involucrado involucrado2 = involucradoDAO.read(idIndividuo);
                        DelitoPersona delPerPojo = new DelitoPersona();
                        delPerPojo.setDelito(delitoPojoBD);
                        delPerPojo.setEsActivo(Boolean.TRUE);
                        delPerPojo.setProbableResponsable(involucrado2);
                        Long idDelitoPersona = this.delitoPerDao.create(delPerPojo);
                        logger.info(" idDelitoPersona: "+ idDelitoPersona);
                    }
                } 
            }
        }
        // Hechos.. Utilizados en IPH
        if (logger.isDebugEnabled()) {
            logger.debug(" Se ingresará el Hecho (Lugar-Domicilio, Tiempo): "+ expedienteDTO.getHechoDTO());
        }
        if (expedienteDTO.getHechoDTO() != null) {
            HechoDTO hechoDTO = expedienteDTO.getHechoDTO();
            hechoDTO.setExpediente(new ExpedienteDTO(expedienteDTO.getExpedienteId()));
            if (hechoDTO.getLugar() != null)hechoDTO.getLugar().setExpedienteDTO(new ExpedienteDTO(expedienteDTO.getExpedienteId()));
            if (hechoDTO.getDomicilio() != null)
                hechoDTO.getDomicilio().setExpedienteDTO(new ExpedienteDTO(expedienteDTO.getExpedienteId()));
            //Se agrega la fecha de arribo
            hechoDTO.setFechaDeArribo(expedienteDTO.getHechoDTO().getFechaDeArribo());
            ingresarHechoService.ingresarHecho(hechoDTO);
            logger.info(" Se ingreso el Hecho");
        }
        // Delitos
        if (logger.isDebugEnabled()) {
        	logger.debug(" Se ingresará los delitos: "+ expedienteDTO.getDelitos());
        }
//        if (expedienteDTO.getDelitos() != null
//                && !expedienteDTO.getDelitos().isEmpty()) {
//            ExpedienteDTO expedienteTempDTO = new ExpedienteDTO(
//                    expedienteDTO.getExpedienteId());
//            guardarDelitoService.guardarDelito(expedienteDTO.getDelitos(),
//                    expedienteTempDTO);
//        }

        // Documentos y sus Archivos Digitales
        if (logger.isDebugEnabled()) {
            logger.debug(" Se ingresará los documentos: "+ expedienteDTO.getDocumentosDTO());
        }
        if (expedienteDTO.getDocumentosDTO() != null && !expedienteDTO.getDocumentosDTO().isEmpty()) {
            for (DocumentoDTO documentoDTO : expedienteDTO.getDocumentosDTO()) {
                Long idDocumento = guardarDocumentoService.guardarDocumentoIntraInstitucion(documentoDTO,expedienteDTO);
                logger.info(" Se ingreso el documento con id: " + idDocumento);
            }
        }
        return expedienteDTO;
    }
    
    public void eliminarDelitos(Long idIndividuo) throws NSJPNegocioException {
    	//Se debe de eliminar la informaci�n del delito y delitoPersona
        List<DelitoPersona> delitosPersona = delitoPerDao.consultarDelitoPerByInvolucrado(idIndividuo);
        List<Long> listIdDelitos=new ArrayList<Long>();
        for (DelitoPersona delitoPersona : delitosPersona) {
        	Long delitoId = delitoPersona.getDelito().getDelitoId();
        	try{
        		listIdDelitos.add(delitoId);
        		delitoPerDao.delete(delitoPersona);
            	
        	}catch (Exception e) {
        		 logger.info(" No pudo Eliminar el delito persona: " + e);
			}
        	
		}
        //delitosPersona=null;
//        for (Long long1 : listIdDelitos) {
//        	Delito delito = delitoDao.read(long1);
//        	try{
//        		delitoDao.delete(delito);        		
//        	}catch (Exception e) {
//        		 logger.info(" No pudo Eliminar el delito relacionado con la persona: " + e);
//        		 delitoDao=null;
//        		 break;
//			}
//        	
//		}
        
    }
    
}
