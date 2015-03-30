/**
* Nombre del Programa : ConsultarEnviarInformePolicialHomologadoServiceImpl.java
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

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.informepolicial.InformePolicialHomologadoDTO;
import mx.gob.segob.nsjp.model.CatDelito;
import mx.gob.segob.nsjp.service.informepolicial.ConsultarEnviarInformePolicialHomologadoService;
import mx.gob.segob.nsjp.service.informepolicial.ConsultarInformePolicialHomologadoService;
import mx.gob.segob.nsjp.service.informepolicial.impl.transform.CatDelitoTransformer;
import mx.gob.segob.nsjp.service.infra.ProcuraduriaClienteService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n del servicio que permite Consultar el IPH por Folio
 * y posteriormente invocar al WS para ser enviado PGJ
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
@Service
@Transactional
public class ConsultarEnviarInformePolicialHomologadoServiceImpl implements
		ConsultarEnviarInformePolicialHomologadoService {

	private final static Logger logger = Logger.getLogger(ConsultarInformePolicialHomologadoServiceImpl.class);
			
	@Autowired 
	private ProcuraduriaClienteService procuraduriaClienteService;
	@Autowired 
	private ConsultarInformePolicialHomologadoService iphService;
	/*variables/*ByYolo*/
	@Autowired
	private CatDelitoDAO catDelitoDAO;
	
	@Override
	public InformePolicialHomologadoDTO consultarEnviarInformePolicialHomologado(
			Long folioIPH, Long idAgencia)
			throws NSJPNegocioException {
		if(folioIPH==null || folioIPH<0)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			
		InformePolicialHomologadoDTO informePolicialHomologadoDTO = iphService.consultarInformePolicialHomologadoPorFolio(folioIPH);
		
		if(informePolicialHomologadoDTO==null || informePolicialHomologadoDTO.getInformePolicialHomologadoId()==null)
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);

		
		/*se obtiene la clave del delito /*ByYolo*/
		if(!informePolicialHomologadoDTO.getDelitoIph().isEmpty() && informePolicialHomologadoDTO.getDelitoIph().get(0).getId().getCatDelitoIdDTO()!=null){
			CatDelito catDelito = new CatDelito();
			
			long catDelitoId = informePolicialHomologadoDTO.getDelitoIph().get(0).getId().getCatDelitoIdDTO();
			catDelito.setCatDelitoId(catDelitoId);
			
			/*se obtiene la lista de delitos pero aqui solo obtendra solo uno ya que solo se pasa el catDelitoId*/
			List<CatDelito> listaCatDelitos= catDelitoDAO.consultarCatDelitoPorFilro(catDelito);
			CatDelitoDTO catDelitoDTO = new CatDelitoDTO();
			DelitoDTO delitoDTO = new DelitoDTO();
			
			if(!listaCatDelitos.isEmpty())
				catDelitoDTO= CatDelitoTransformer.transformarCatDelito(listaCatDelitos.get(0));//solo devuelve un resultado es por ello que no se itera
			
			/*Armando el delito se asigna catDelitoDTO que es lo unico que enviaremos por WebService ya que el expediente se genera en MP 
			 * y los  campos bEsProbable y bEsPrincipal se asignara en MP tambien ambos estaran en valor 1*/
			delitoDTO.setCatDelitoDTO(catDelitoDTO);

			/*Se asigna el delito a informePolicialHomologadoDTO*/
			informePolicialHomologadoDTO.getExpediente().setDelitoPrincipal(delitoDTO);
			logger.debug("Yolo::: Delito obtenido::: "+informePolicialHomologadoDTO.getExpediente().getDelitoPrincipal());
			logger.debug("Yolo::: Nombre del delito::: "+informePolicialHomologadoDTO.getExpediente().getDelitoPrincipal().getCatDelitoDTO().getNombre());
			//Agregando confInstitucionId
			informePolicialHomologadoDTO.getExpediente().setPertenceConfInst(ConfInstitucionTransformer.transformarInstitucion(catDelitoDAO.consultarInsitucionActual())  );
			logger.debug("Yolo::: informePolicialHomologadoDTO.getExpediente().getPertenceConfInst().getConfInstitucionId() "+informePolicialHomologadoDTO.getExpediente().getPertenceConfInst().getConfInstitucionId());
										
		}

		/*fin yolo*/
		
		
		return procuraduriaClienteService.enviarInformePolicialHomologado(informePolicialHomologadoDTO, idAgencia);
	}

}
