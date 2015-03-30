/**
* Nombre del Programa : EnviarInformePolicialHomologadoServiceImpl.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 07/09/2011
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
package mx.gob.segob.nsjp.service.informepolicial.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.informepolicial.InformePolicialHomologadoDTO;
import mx.gob.segob.nsjp.dto.informepolicial.InformePolicialHomologadoWSDTO;
import mx.gob.segob.nsjp.model.CatDelito;
import mx.gob.segob.nsjp.service.expediente.RecibirExpedienteService;
import mx.gob.segob.nsjp.service.informepolicial.EnviarInformePolicialHomologadoService;
import mx.gob.segob.nsjp.service.informepolicial.impl.transform.CatDelitoTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarinformepolicialhomologado.InformePolicialHomologadoWSDTOTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;
import mx.gob.segob.nsjp.services.dtos.print.ExpedientePrintIPH;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n del servicio que permite enviar el Informe Policial Homologado
 * a otra instituci�n mediante un WebService. 
 * @version 1.0
 * @author GustavoBP
 *
 */
@Service("enviarInformePolicialHomologadoService")
@Transactional
public class EnviarInformePolicialHomologadoServiceImpl  implements EnviarInformePolicialHomologadoService {
	   
	private final static Logger logger = Logger
	           .getLogger(EnviarInformePolicialHomologadoServiceImpl.class);
	   

	@Autowired
	private RecibirExpedienteService recibirExpedienteService;
	/*Variable/*ByYolo*/
	@Autowired
	private CatDelitoDAO catDelitoDAO;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	
	@Override
	public Long enviarInformePolicialHomologado(InformePolicialHomologadoWSDTO iphWSDTO, Long idAgencia)throws NSJPNegocioException{
		Long id = 0L;
		Long confInstitucionId=null;
		logger.debug("Yolo::: iphWSDTO.getExpediente().getPertenceConfInst()"+iphWSDTO.getExpediente().getPertenceConfInst());
		
		if(iphWSDTO == null)
    		return id;
		
		logger.info("*************** IPH RECIBIDO  ***************");
		logger.info(" IPH: "+ iphWSDTO);
		logger.info(" IPH-ConfInstitucionId: "+ iphWSDTO.getConfInstitucionId());
		logger.info(" IPH-idAgencia: "+ idAgencia);
		logger.info(" IPH-Expediente: "+ iphWSDTO.getExpediente());
		
		if (iphWSDTO.getExpediente()!= null){
			/*Loggers/*ByYolo*/
			if(iphWSDTO.getExpediente().getPertenceConfInst()!=null){
				confInstitucionId = new Long(iphWSDTO.getExpediente().getPertenceConfInst());
				logger.info("Yolo ::: iphWSDTO.getExpediente().getPertenceConfInst()"+iphWSDTO.getExpediente().getPertenceConfInst());	
			}
			/*Fin Yolo*/
			if(iphWSDTO.getExpediente().getInvolucradosDTO()!=null && !iphWSDTO.getExpediente().getInvolucradosDTO().isEmpty()){
				logger.info(" - IPH -Involucrado: " + iphWSDTO.getExpediente().getInvolucradosDTO().get(0));
				if(iphWSDTO.getExpediente().getInvolucradosDTO().get(0)!= null && iphWSDTO.getExpediente().getInvolucradosDTO().get(0).getDomicilio()!= null){
					logger.info(" - IPH -Involucrado: " + iphWSDTO.getExpediente().getInvolucradosDTO().get(0).getDomicilio());
					logger.info(" - IPH -Involucrado - Domicilio -AsentamientoId: " + iphWSDTO.getExpediente().getInvolucradosDTO().get(0).getDomicilio().getAsentamientoId());
					logger.info(" - IPH -Involucrado - Domicilio -ValorCalleId: " + iphWSDTO.getExpediente().getInvolucradosDTO().get(0).getDomicilio().getValorCalleId());
					logger.info(" - IPH -Involucrado - Domicilio -EntidadId: " + iphWSDTO.getExpediente().getInvolucradosDTO().get(0).getDomicilio().getEntidadId());
					logger.info(" - IPH -Involucrado - Domicilio -MunicipioId: " + iphWSDTO.getExpediente().getInvolucradosDTO().get(0).getDomicilio().getMunicipioId());
					logger.info(" - IPH -Involucrado - Domicilio -CiudadId: " + iphWSDTO.getExpediente().getInvolucradosDTO().get(0).getDomicilio().getCiudadId());
				}
				if(iphWSDTO.getExpediente().getHechoDTO()!= null){
					mx.gob.segob.nsjp.dto.hecho.HechoWSDTO hechoWSDTO = iphWSDTO.getExpediente().getHechoDTO() ; 
					logger.info("Hecho - DescNarrativa: "+ hechoWSDTO.getDescNarrativa());
					logger.info("Hecho - Lugar: "+ hechoWSDTO.getLugar());
					if (hechoWSDTO.getLugar()instanceof mx.gob.segob.nsjp.dto.domicilio.LugarWSDTO ){
						mx.gob.segob.nsjp.dto.domicilio.DomicilioWSDTO domicilioWSDTO = (mx.gob.segob.nsjp.dto.domicilio.DomicilioWSDTO) hechoWSDTO.getLugar();
						logger.info(" Descripcion:"+domicilioWSDTO.getDescripcion());
						logger.info(" Calle:"+domicilioWSDTO.getCalle());
						logger.info(" NumeroExterior:"+domicilioWSDTO.getNumeroExterior());
						logger.info(" AsentamientoDTO:"+domicilioWSDTO.getAsentamientoId());
						logger.info(" EntidadDTO:"+domicilioWSDTO.getEntidadId());
						logger.info(" MunicipioDTO:"+domicilioWSDTO.getMunicipioId());
						logger.info(" CiudadDTO:"+domicilioWSDTO.getCiudadId());
						logger.info(" NumeroInterior:"+domicilioWSDTO.getNumeroInterior());
						logger.info(" IPH-FechaDeArribo: "+ iphWSDTO.getExpediente().getHechoDTO().getFechaDeArribo());
					}
					logger.info("Hecho - Domicilio: "+ hechoWSDTO.getDomicilio());
					logger.info("Hecho - Tiempo: "+ hechoWSDTO.getTiempo());
					if(hechoWSDTO.getTiempo()!= null){
						logger.info("Tiempo - TiempoId: N/A");
						logger.info("Tiempo - Descripcion: "+ hechoWSDTO.getTiempo().getDescripcion());
						logger.info("Tiempo - FechaInicio: "+ hechoWSDTO.getTiempo().getFechaInicio());
						logger.info("Tiempo - FechaFin: "+ hechoWSDTO.getTiempo().getFechaFin());
						logger.info("Tiempo - TipoRegistro: "+ hechoWSDTO.getTiempo().getTipoRegistro());
					}
				}
			}
		}
		
		
		//Transformar de WSDTo --> DTO
		logger.info("* IPH A Tranformar  *");
		InformePolicialHomologadoDTO iphDTO = InformePolicialHomologadoWSDTOTransformer.transformarWSDTO(iphWSDTO);
		logger.info("* IPH Tranformado  *");
		logger.info(" IPH: "+ iphDTO);
		logger.info(" IPH-ConfInstitucionId: "+ iphDTO.getFolioIPH());
		logger.info(" IPH-Expediente: "+ iphDTO.getExpediente());
		if(iphDTO.getExpediente()== null)
			return 0L;

		/*Se asigna delito/*ByYolo*/
		if( iphWSDTO.getExpediente().getDelitoPrincipal()!=null && iphWSDTO.getExpediente().getDelitoPrincipal().getClaveDelito()!=null){
			DelitoDTO delitoDTO = new DelitoDTO();
			
			/*Se obtiene CatDelito y transforma a CatDelitoDTO*/
			CatDelito catDelito = new CatDelito();
			catDelito.setClaveDelito(iphWSDTO.getExpediente().getDelitoPrincipal().getClaveDelito());
			List<CatDelito> delitoPrincipal= catDelitoDAO.consultarCatDelitoPorFilro(catDelito);
			CatDelitoDTO catDelitoDTO = CatDelitoTransformer.transformarCatDelito(delitoPrincipal.get(0));//solo devuelde un resultado debido a que solo se manda cClaveDelito
			
			/*se inserta el catDelito a Delito*/
			delitoDTO.setCatDelitoDTO(catDelitoDTO);
			delitoDTO.setNombreDelito(catDelitoDTO.getNombre());
			
			/*Se inserta delito principal a iphDTO*/
			iphDTO.getExpediente().setDelitoPrincipal(delitoDTO);
			
			//Loggers para validar que falta
			logger.debug("yolo::: Antes de guardar justo cuando se recibe::: iphDTO.getInformePolicialHomologadoId()::: "+iphDTO.getInformePolicialHomologadoId());
			logger.debug("yolo::: Antes de guardar justo cuando se recibe::: iphDTO.getExpediente().getNumeroExpediente()::: "+iphDTO.getExpediente().getNumeroExpediente());
			//fin de loggers
			
		}
				
		/*Fin yolo*/
		/*Yolo*/
		if (confInstitucionId !=null) {
			logger.debug("Yolo::: antes de guardarExpedienteRecibido se asigan el confInsitucionId::: "+confInstitucionId);
			ConfInstitucionDTO confInstitucionDTO = ConfInstitucionTransformer.transformarInstitucion(confInstitucionDAO.consultarIntitucionPorId(confInstitucionId));
			logger.debug("yolotzin::: confInstitucionDTO.getClave()"+confInstitucionDTO.getClave());
			logger.debug("yolotzin::: confInstitucionDTO.getConfInstitucionId()"+confInstitucionDTO.getConfInstitucionId());
			logger.debug("yolotzin::: confInstitucionDTO.getNombreInst()"+confInstitucionDTO.getNombreInst());
			logger.debug("yolotzin::: confInstitucionDTO.getUrlInst()"+confInstitucionDTO.getUrlInst());
			
			
			iphDTO.getExpediente().setPertenceConfInst(confInstitucionDTO);
		}
		/*fin Yolo*/

		logger.info("yolo::: BDDelito_id es autoincrementable N/A");
		logger.info("******************LISTA DE DELITOS*************************");
		
		ExpedientePrintIPH.imprimirDatosExpediente(iphDTO.getExpediente());
		/*Se sustituye la linea/*ByYolo*/
//		ExpedienteDTO expedienteNuevoDTO = recibirExpedienteService.guardarExpedienteRecibido(iphDTO.getExpediente(), idAgencia);
		ExpedienteDTO expedienteNuevoDTO = recibirExpedienteService.guardarExpedienteRecibido(iphDTO, iphDTO.getExpediente(), idAgencia);
		if(expedienteNuevoDTO==null)
			return 0L;
				
		return expedienteNuevoDTO.getExpedienteId();
	}
	
	
}
