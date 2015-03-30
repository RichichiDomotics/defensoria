/**
* Nombre del Programa : ActualizarExpedienteServiceImpl.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 17/08/2011
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

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDiscriminateDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatUIEspecializadaDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.HistoricoExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.CatUIEspecializada;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.HistoricoExpediente;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.expediente.ActualizarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio que permite la actualización del Expediente.
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
@Service
@Transactional
public class ActualizarExpedienteServiceImpl implements
		ActualizarExpedienteService {

    private static final Logger logger = Logger.getLogger(ActualizarTipoExpedienteServiceImpl.class);
    
    @Autowired
    private NumeroExpedienteDAO numeroExpedienteDAO;  
    @Autowired
    private ExpedienteDAO expedienteDAO;
    @Autowired
    private CatDiscriminateDAO catDiscriminateDAO;
    @Autowired
    private FuncionarioDAO funcionarioDAO;
    @Autowired
    private HistoricoExpedienteDAO historicoExpedienteDAO;
    @Autowired
    private CatUIEspecializadaDAO catUIEspecializadaDAO;
    @Autowired
    private CasoDAO casoDAO;
    
    
    
    @Autowired
 	private DelitoDAO delitoDAO;
     
     @Autowired
     private InvolucradoDAO involucradoDAO;
     
     @Autowired
     private DelitoPersonaDAO delitoPersonaDAO;
     
     @Autowired
     private CatDelitoDAO cdDelito;
     
    
    
    
	@Override
	public ExpedienteDTO actualizarEstatusExpediente(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		logger.info("Servicio actualizarEstatusExpediente");
		if( expedienteDTO == null || expedienteDTO.getNumeroExpedienteId()==null ||
				expedienteDTO.getEstatus()== null )
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		logger.info("Datos Entrada: ");
		logger.info("NumeroExpedienteId: "+expedienteDTO.getNumeroExpedienteId());
		logger.info("Estatus: "+expedienteDTO.getEstatus());
		NumeroExpediente numeroExpediente = numeroExpedienteDAO.read(expedienteDTO.getNumeroExpedienteId());
		
		if(numeroExpediente==null)
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);		
		logger.info("Datos BD: ");
//		logger.info("Estatus: "+numeroExpediente.getEstatus());
		if(numeroExpediente.getEstatus()!= null)
			logger.info("Estatus: "+numeroExpediente.getEstatus().getValorId());

		//Se settean los valores
		numeroExpediente.setEstatus(new Valor(expedienteDTO.getEstatus().getIdCampo()));
		
		logger.info("Actualizar...");
		numeroExpedienteDAO.update(numeroExpediente);
		logger.info("Actualizado..." + numeroExpediente.getEstatus().getValorId());
		
		expedienteDTO = ExpedienteTransformer.transformarExpedienteBasico(numeroExpediente);
		
		return expedienteDTO;
	}

	
	@Override
	public Boolean actualizarCatDiscriminanteDeExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {

		try{

			if (expedienteDTO == null || expedienteDTO.getNumeroExpedienteId() == null) {
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}

			if (expedienteDTO.getDiscriminante() == null
					|| expedienteDTO.getDiscriminante().getCatDiscriminanteId() == null) {
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
			
			Long expedienteIdObtenido = expedienteDAO.obtenerExpedienteIdPorIdNumerExpediente(expedienteDTO.getNumeroExpedienteId());
			
			if(expedienteIdObtenido != null){
				
				CatDiscriminante catDiscriminante = new CatDiscriminante();
				
				catDiscriminante = catDiscriminateDAO.consultarDiscriminantePorId(expedienteDTO.getDiscriminante().getCatDiscriminanteId());
				
				if(catDiscriminante != null){
					
					Expediente expedienteBD = expedienteDAO.read(expedienteIdObtenido);
					if(expedienteBD != null){
						
						CatUIEspecializada catUIEspecializada=catUIEspecializadaDAO.read(expedienteDTO.getCatUIE());
						expedienteBD.setDiscriminante(catDiscriminante);
						if(catUIEspecializada!=null){
							expedienteBD.setCatUIEspecializada(catUIEspecializada);
						}
						expedienteDAO.update(expedienteBD);
						
						return true;
						
					}else{
						return false;
					}
				}
				else{
					return false;
				}
			}else{
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}
	
	
	@Override
	public Boolean reasignarFuncionarioDeNumeroExpediente (
			List<ExpedienteDTO> lstExpedienteDTO, 
			HistoricoExpedienteDTO historicoExpedienteDTO
			) throws NSJPNegocioException{
		Boolean resultado = false;
		try{

			if( lstExpedienteDTO == null || lstExpedienteDTO.isEmpty() ){
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
			
			for (ExpedienteDTO expedienteDTO : lstExpedienteDTO) {
	
				if (expedienteDTO == null || 
						expedienteDTO.getNumeroExpedienteId() == null ||
						expedienteDTO.getResponsableTramite() == null ||
						historicoExpedienteDTO == null ||
						historicoExpedienteDTO.getFuncionarioAsigna() == null
					) {
					throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				}
	
				NumeroExpediente numeroExpediente = numeroExpedienteDAO.read(expedienteDTO.getNumeroExpedienteId());
				
				if(numeroExpediente != null){
					FuncionarioDTO actualDTO = expedienteDTO.getResponsableTramite();
					FuncionarioDTO asignaDTO = historicoExpedienteDTO.getFuncionarioAsigna();
					Funcionario anterior = numeroExpediente.getFuncionario();
					Funcionario actual = funcionarioDAO.read(actualDTO.getClaveFuncionario());
					Funcionario asigna = funcionarioDAO.read(asignaDTO.getClaveFuncionario());

					numeroExpediente.setFuncionario(actual);
					
					numeroExpedienteDAO.update(numeroExpediente);
					
					HistoricoExpediente historicoExpediente = new HistoricoExpediente();
					Date fechaDelSistema = new Date();
					historicoExpediente.setNumeroExpediente(numeroExpediente);
					historicoExpediente.setConsultarUltimo(Boolean.TRUE);
					
					List<HistoricoExpediente> lstHistoricoExpedientes = historicoExpedienteDAO.consultarHistoricoExpediente(historicoExpediente);
					
					if(lstHistoricoExpedientes != null && !lstHistoricoExpedientes.isEmpty()){
						historicoExpediente = lstHistoricoExpedientes.get(0);
						historicoExpediente.setDFechaFin(fechaDelSistema);
						historicoExpedienteDAO.update(historicoExpediente);
					}
					
					historicoExpediente = new HistoricoExpediente();
					historicoExpediente.setDFechaInicio(fechaDelSistema);
					historicoExpediente.setFuncionarioActual(actual);
					historicoExpediente.setFuncionarioAnterior(anterior);
					historicoExpediente.setFuncionarioAsigna(asigna);
					historicoExpediente.setNumeroExpediente(numeroExpediente);
					
					historicoExpedienteDAO.create(historicoExpediente);
					
					resultado = true;
				}else{
					resultado = false;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);
		}
		
		return resultado;
	}


	@Override
	public ExpedienteDTO asignarNumeroCasoSolicitudDefensor(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		try{
			if( expedienteDTO == null || expedienteDTO.getNumeroExpedienteId()==null || expedienteDTO.getCasoDTO()==null || expedienteDTO.getCasoDTO().getNumeroGeneralCaso()==null ){
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
			NumeroExpediente list=numeroExpedienteDAO.read(expedienteDTO.getNumeroExpedienteId());
			if(list!=null){
				expedienteDTO.setExpedienteId(list.getExpediente().getExpedienteId());
				Expediente expediente=expedienteDAO.read(expedienteDTO.getExpedienteId());
				Caso caso=casoDAO.consultarCasoPorNumeroCaso(expedienteDTO.getCasoDTO().getNumeroGeneralCaso());
				if(caso==null || caso.getNumeroGeneralCaso()==null){
					caso=new Caso();
					caso.setNumeroGeneralCaso(expedienteDTO.getCasoDTO().getNumeroGeneralCaso());
					caso.setFechaApertura(new Date());
					Long idCaso=casoDAO.create(caso);
					caso.setCasoId(idCaso);
				}
				expediente.setCaso(caso);
				expedienteDAO.update(expediente);
			}
			
			return null;	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);
		}
	}

	

	@Override
	public ExpedienteDTO crearRelacion(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {

		Involucrado inv=new Involucrado();
		Expediente expnuevo = expedienteDAO.read(expedienteDTO.getExpedienteId());

		
		Long delitoPersona;
		Delito del=new Delito();
		del.setCatDelito(cdDelito.read(expedienteDTO.getDelitoPrincipal().getDelitoId()));
		del.setExpediente(expnuevo);
		del.setEsPrincipal(true);
		del.setEsProbable(true);
		
		Long idDelito=delitoDAO.create(del);
		
       if (expnuevo!=null){

    	   inv=involucradoDAO.read(expedienteDTO.getInputado().getElementoId());
    	   logger.debug("El id del expediente creado es :: "+expnuevo.getExpedienteId());

    	   Delito delito= delitoDAO.read(idDelito);
    	   delito.setExpediente(expnuevo);

    	   DelitoPersona dp=new DelitoPersona();
    	   dp.setDelito(delito);
    	   dp.setProbableResponsable(inv);
    	   dp.setEsActivo(true);
    	   
    	   delitoPersona=delitoPersonaDAO.create(dp);

           expedienteDTO.setDelitoPrincipal(new DelitoDTO(idDelito));
           
       }
       

        return expedienteDTO;
		
	}

	
}
