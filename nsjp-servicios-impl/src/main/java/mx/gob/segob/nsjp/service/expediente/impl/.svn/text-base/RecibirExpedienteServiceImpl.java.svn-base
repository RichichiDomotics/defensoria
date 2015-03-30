/**
* Nombre del Programa : RecibirExpedienteServiceImpl.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 08/09/2011
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.caso.EstatusCaso;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.CatDiscriminateDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.informepolicial.InformePolicialHomologadoDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.informepolicial.InformePolicialHomologadoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.InformePolicialHomologado;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.service.caso.AsignarNumeroCasoService;
import mx.gob.segob.nsjp.service.catalogo.ConsultarDiscriminanteService;
import mx.gob.segob.nsjp.service.expediente.ActualizarCarpetaDeInvestigacionService;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.RecibirExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.services.dtos.print.ExpedientePrint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
@Service
@Transactional
public class RecibirExpedienteServiceImpl implements
		RecibirExpedienteService {
	private final static Logger logger = Logger.getLogger(RecibirExpedienteServiceImpl.class);
	
    @Autowired
    private AsignarNumeroCasoService casoService;
    @Autowired
    private AsignarNumeroExpedienteService asignarNumeroExpedienteService;
    @Autowired 
    private ActualizarCarpetaDeInvestigacionService actualizarCarpetaDeInvestigacionService;
    @Autowired 
    private CatDiscriminateDAO catDiscriminateDAO;
    @Autowired 
    private ConsultarDiscriminanteService consultarDiscriminanteService;
    /*variables/*ByYolo*/
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private FuncionarioDAO funcionarioDAO;
    @Autowired
    private InformePolicialHomologadoDAO informePolicialHomologadoDAO;
    
    
    public ExpedienteDTO guardarExpedienteRecibido(InformePolicialHomologadoDTO informePolicialHomologadoDTO, ExpedienteDTO expDTO, Long idAgencia)throws NSJPNegocioException {
    
    	/*insertando el expediente generado en Unidad al iph para visualizar los involucrados/*ByYolo*/
    	Long confInstitucionIph = new Long(informePolicialHomologadoDTO.getExpediente().getPertenceConfInst().getConfInstitucionId());
    	logger.debug("Yolo::: confInstitucionIph"+confInstitucionIph);
    	InformePolicialHomologado informePolicialHomologado = new InformePolicialHomologado();
    	
    	logger.info("++++++++++++++++++++++++ IMPRIMIENDO EXPEDIENTE RECIBIDO POR WS +++++++++++++++++++");
    	ExpedientePrint.imprimirDatosExpediente(expDTO);
    	
    	
    	if (Areas.PGJ.ordinal()== (int)(Integer.parseInt(confInstitucionIph.toString()))) {
    		informePolicialHomologado = informePolicialHomologadoDAO.consultarInformePorId(informePolicialHomologadoDTO.getInformePolicialHomologadoId());
			logger.debug("Yolotzin::: Iph generado en Procuraduria (Fiscalia), se actualizara al nuevo expediente para Unidad ");
		}
    	/*Fin yolo*/
    	
    	ExpedienteDTO  expNuevo = generarCasoExpediente(idAgencia, informePolicialHomologadoDTO);
    	
    	
    	/*insertando el expediente generado en Unidad al iph para visualizar los involucrados/*ByYolo*/
    	if (informePolicialHomologado !=null && 
    			Areas.PGJ.ordinal()== (int)(Integer.parseInt(confInstitucionIph.toString()))) {
			informePolicialHomologado.setExpediente(new Expediente(expNuevo.getExpedienteId()));
			informePolicialHomologadoDAO.saveOrUpdate(informePolicialHomologado);
			logger.debug("Yolotzin::: Se actualizo el IPH generado en Procuraduria ");
		}
    	/*fin yolo*/
    	/*insertando en unidad el delito/*ByYolo*/
    	if(expDTO.getDelitoPrincipal()!=null){
        	DelitoDTO delitoDTO = new DelitoDTO();
        	delitoDTO = expDTO.getDelitoPrincipal();
    		delitoDTO.setEsPrincipal(true);
    		delitoDTO.setEsProbable(true);
    		//se inserta el expediente al delito 
        	delitoDTO.setExpedienteDTO(expNuevo);	
    	}
    	
    	
    	
    	expDTO.setExpedienteId(expNuevo.getExpedienteId());
        expDTO.setCasoDTO(expNuevo.getCasoDTO());
        expDTO.setNumeroExpedienteId(expNuevo.getNumeroExpedienteId());
        expDTO.setNumeroExpediente(expNuevo.getNumeroExpediente());
       
        logger.info("**********+++++++ EXPEDIENTE GENERADO++++++**********************");
        logger.info(" ExpDTO: " + expDTO);
        logger.info(" ExpDTO - ExpedienteId: " + expDTO.getExpedienteId());
        logger.info(" ExpDTO - NumeroExpediente: " + expDTO.getNumeroExpediente());
        logger.info(" ExpDTO - NumeroExpedienteId: " + expDTO.getNumeroExpedienteId());
        logger.info(" ExpDTO - CasoDTO: " + expDTO.getCasoDTO());
		logger.info(" ExpDTO - idAgencia: "+ idAgencia);


        //Settear los ID de involucrados en 0
        List<InvolucradoDTO> involucradosDTO = new ArrayList<InvolucradoDTO>();
        for (InvolucradoDTO involucradoDTO : expDTO.getInvolucradosDTO()) {
			involucradoDTO.setElementoId(0L);
			involucradosDTO.add(involucradoDTO);
		}
        expDTO.setInvolucradosDTO(involucradosDTO);
            
        //TODO GBP  Verificar el delito como delito Principal
        //Se genera una actividad que se asocia a un Expediente
//        expDTO = actualizarCarpetaDeInvestigacionService.actualizarExpedienteDeCarpetaInvestigacionDefensoria(expDTO);
       //se el metodo anterior por esta sobrecarga para evitar conflictos
        expDTO = actualizarCarpetaDeInvestigacionService.actualizarExpedienteDeCarpetaInvestigacionDefensoria(expDTO, expDTO.getDelitoPrincipal());
        
    	return expDTO;
    }
    
	@Override
	public synchronized ExpedienteDTO generarCasoExpediente(Long idAgencia, InformePolicialHomologadoDTO iph)
			throws NSJPNegocioException {
		//TODO GBP Preguntar si deber�a sincronizarse con el servicio AsignarNumeroExpedienteService -> asignarNumeroExpediente(TurnoDTO turno)
		
		//Se genera el caso para ser asociado al Expediente
//        CasoDTO casoDTO = new CasoDTO();
//        casoDTO.setFechaApertura(new Date());
//        casoDTO.setEstatus(EstatusCaso.INVESTIGACION);
//        casoDTO = casoService.asignarNumeroCaso(casoDTO,obtenerFuncionario());

        //Verificar el expediente que se recibe
        ExpedienteDTO expParam = new ExpedienteDTO();
        expParam.setFechaApertura(new Date());
        
        //solucion a la primera parte de que no enviava en IPH
        /*hardcodeado, se sustituyen las lineas por las 2 siguientes/*Byyolo*/
//        UsuarioDTO usuario = new UsuarioDTO(8L); //Se sette al usuario coordinadorAmp
//        FuncionarioDTO funcionario = new FuncionarioDTO(8L); //mismo funcionario
        List<Usuario> usuarios= new ArrayList<Usuario>();
        Long catuieId=null;
        if(iph!=null)
        	if(iph.getExpediente()!=null)
        		if(iph.getExpediente().getDelitoPrincipal()!=null)
        			if(iph.getExpediente().getDelitoPrincipal().getCatDelitoDTO()!=null)
        				if(iph.getExpediente().getDelitoPrincipal().getCatDelitoDTO().getUnidadIEspecializada()!=null)
        					if(iph.getExpediente().getDelitoPrincipal().getCatDelitoDTO().getUnidadIEspecializada().getCatUIEId()!=null)
        				catuieId = iph.getExpediente().getDelitoPrincipal().getCatDelitoDTO().getUnidadIEspecializada().getCatUIEId();
        
        //se consultan los usuarios que coincidan directamente con agencia y Area
        if(catuieId!=null){
        	usuarios=usuarioDAO.consultarUsuarioPorAgenciaYArea(catuieId, Areas.COORDINACION_UNIDAD_INVESTIGACION);	
        }
        
        //en caso de que no halla usuario de esa unidad de investigacion se busca cualquier coordinador
        if(usuarios.isEmpty())
        	usuarios = usuarioDAO.consultarUsuarioPorAgenciaYArea(null, Areas.COORDINACION_UNIDAD_INVESTIGACION);
       
        if(usuarios.isEmpty())
        	throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        
        /*Se buscan todos los coordinadores de esa unidad y se obtiene el primero de la lista para asignarle el iph*/
        Usuario usuario=usuarios.get(0);
        Funcionario funcionario = funcionarioDAO.consultaFuncionarioPorNombreInstitucionPuesto(usuario.getFuncionario());
        
        /*se sustituyeron esta lineas/*ByYolo*/
//        usuario.setFuncionario(funcionario);
//        expParam.setUsuario(usuario);
        usuario.setFuncionario(funcionario);
        UsuarioDTO usuarioDTO= UsuarioTransformer.transformarUsuario(usuario);
        expParam.setUsuario(usuarioDTO);
        expParam.setArea(new AreaDTO(Areas.UNIDAD_INVESTIGACION) );
//        expParam.setCasoDTO(casoDTO);
        
        expParam.setEstatus(new ValorDTO(EstatusExpediente.PENDIENTE_REVISION_COMO_IPH.getValorId()));
        //Se asocia el id de la agencia al expediente
        if(idAgencia != null && idAgencia > 0 && expParam.getUsuario() != null && expParam.getUsuario().getFuncionario() != null){
        	CatDiscriminanteDTO loCatDiscriminante = consultarDiscriminanteService.consultarDiscriminanteXId(idAgencia);
       		expParam.getUsuario().getFuncionario().setDiscriminante(loCatDiscriminante);
        }
        	
        // Es necesario para generar el Numero de Expediente
        ExpedienteDTO expNuevo = asignarNumeroExpedienteService.asignarNumeroExpediente(expParam);
//        expNuevo.setCasoDTO(casoDTO);
		return expNuevo;
	}

	//Obtener funcionario con Area de Unidad de investigacion
    private FuncionarioDTO obtenerFuncionario() {     
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		DepartamentoDTO departamento = new DepartamentoDTO();
		AreaDTO area = new AreaDTO(Areas.PGJ ); 
		departamento.setArea(area);
		funcionarioDTO.setDepartamento(departamento);
		return funcionarioDTO;
	}
    
}
