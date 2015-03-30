
package mx.gob.segob.nsjp.service.sentencia.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.sentencia.SentenciaDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioDAO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaWSDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Sentencia;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.persona.impl.transform.NombreDemograficoTransformer;
import mx.gob.segob.nsjp.service.sentencia.SentenciaService;
import mx.gob.segob.nsjp.service.sentencia.impl.transform.SentenciaTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AntonioBV
 *
 */
@Service("sentenciaService")
@Transactional
public class SentenciaServiceImpl implements SentenciaService{

	@Autowired
	SentenciaDAO sentenciaDAO;
	
	@Autowired
	InvolucradoDAO involucradoDAO;
	
	@Autowired
	ExpedienteDAO expedienteDAO;
	
	@Autowired
	ClienteGeneralService clienteGeneralService;
	
	@Autowired
	CasoDAO casoDAO;
	
	@Autowired
	private AsignarNumeroExpedienteService asignarNumeroExpedienteService;
	
	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	FuncionarioDAO funcionarioDAO;
	
	@Autowired
	NumeroExpedienteDAO numeroExpedienteDAO;
	
	@Override
	public List<SentenciaDTO> consultarNUS(NombreDemograficoDTO nombreDemograficoDTO)
			throws NSJPNegocioException {
		
		List<SentenciaDTO> lstResultadoSentenciaDTO = new ArrayList<SentenciaDTO>();
		SentenciaDTO sentenciaDTO  = null;
		
		NombreDemografico nombreDemografico = NombreDemograficoTransformer.transformarNombreDemografico(nombreDemograficoDTO);
		List<Sentencia> lstSentencias = null;
		
		if(nombreDemografico != null && nombreDemografico.getCurp() != null && !nombreDemografico.getCurp().isEmpty() ){
			lstSentencias = sentenciaDAO.consultarNUS(nombreDemografico, Boolean.TRUE);
		}
		
		if (lstSentencias != null && !lstSentencias.isEmpty()) {
			sentenciaDTO = SentenciaTransformer.transformar(lstSentencias.get(0));
			sentenciaDTO.setEsUnicoNUS(Boolean.TRUE);
			lstResultadoSentenciaDTO.add(sentenciaDTO);
		} else {
			
			lstSentencias = sentenciaDAO.consultarNUS(nombreDemografico, Boolean.FALSE);
			if (lstSentencias != null && !lstSentencias.isEmpty()) {
				
				for (Sentencia sentencia : lstSentencias){
					sentenciaDTO = SentenciaTransformer.transformar(sentencia);
					sentenciaDTO.setEsUnicoNUS(Boolean.FALSE);
					lstResultadoSentenciaDTO.add(sentenciaDTO);
				}
			}
		}
			
			
		
		
			
				
		return lstResultadoSentenciaDTO;
	}


	@Override
	public SentenciaDTO consultarSentenciaCompleta(SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		
		Sentencia sentencia = sentenciaDAO.read(sentenciaDTO.getSentenciaId());
		
		Involucrado involucrado = involucradoDAO.read(sentencia.getInvolucrado().getElementoId());
		
		Expediente expediente = expedienteDAO.read(sentencia.getNumeroExpediente().getNumeroExpedienteId());
		
		SentenciaDTO sentenciaCompletaDTO = SentenciaTransformer.transformar(sentencia);
		
		InvolucradoDTO involucradoDTO = InvolucradoTransformer.transformarInvolucrado(involucrado);
		
		ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformaExpediente(expediente);
		
		sentenciaCompletaDTO.setInvolucradoDTO(involucradoDTO);
		
		sentenciaCompletaDTO.setNumeroExpedienteDTO(expedienteDTO);
		
		return sentenciaCompletaDTO;
	}

	@Override
	public void crearSentencia(SentenciaWSDTO sentenciaWSDTO) throws NSJPNegocioException {
	
		String cNumeroGeneralCaso = null;
		String folioInvolucrado  = null;
		Caso caso = null;
		
		Sentencia sentencia = SentenciaTransformer.transformarLocalWSDTO2Entity(sentenciaWSDTO);
		
		if (sentencia.getInvolucrado() == null || sentencia.getNumeroExpediente() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		} 
		
		if(sentenciaWSDTO.getNumeroExpedienteDTO() != null) {
			if (sentenciaWSDTO.getNumeroExpedienteDTO().getCasoWSDTO() != null){
				cNumeroGeneralCaso = sentenciaWSDTO.getNumeroExpedienteDTO().getCasoWSDTO().getNumeroGeneralCaso();
			}
		}

		if(cNumeroGeneralCaso != null){
			 caso = casoDAO.consultarCasoPorNumeroCaso(cNumeroGeneralCaso);	
		}

		if (caso != null) {
			Involucrado involucrado = sentencia.getInvolucrado();
			
			folioInvolucrado = involucrado.getFolioElemento();
			
			involucrado = involucradoDAO.obtenerInvolucradoByFolioElemento(involucrado.getFolioElemento());
			
			Expediente expediente = expedienteDAO.buscarExpedientePorCasoFolioInvolucrado(cNumeroGeneralCaso, folioInvolucrado);
			
			if (expediente != null ) {
				
				List<Funcionario> lstFuncionarios = funcionarioDAO.consultarFuncionariosPorRolMultiRol(Roles.DSE.getValorId());
								
				if (lstFuncionarios != null){
					Usuario usuario = null;
					for (Funcionario funcionario : lstFuncionarios) {
						if (funcionario.getUsuario() != null){
							usuario = funcionario.getUsuario();
							break;
						}
					}
					
					if (usuario != null){

						ExpedienteDTO nuevoExp = new ExpedienteDTO();
						nuevoExp.setExpedienteId(expediente.getExpedienteId());
						nuevoExp.setArea(new AreaDTO(Areas.DSE_DE_REINSERCION));
						nuevoExp.setTipoExpediente(new ValorDTO(TipoExpediente.CARPETA_DE_EJECUCION.getValorId()));
						
						UsuarioDTO usuarioDTO = UsuarioTransformer.transformarUsuario(usuario);
						nuevoExp.setUsuario(usuarioDTO);
						
						nuevoExp = asignarNumeroExpedienteService.asignarNumeroExpediente(nuevoExp);
						
						NumeroExpediente numeroExpediente = numeroExpedienteDAO.read(nuevoExp.getNumeroExpedienteId());

						numeroExpediente.setEstatus(new Valor(EstatusExpediente.POR_ATENDER.getValorId()));
						
						numeroExpedienteDAO.update(numeroExpediente);
						
						sentencia = SentenciaTransformer.quitarIDs(sentencia);
						sentencia.setInvolucrado(involucrado);
						sentencia.setNumeroExpediente(numeroExpediente);
						
						sentenciaDAO.create(sentencia);
						
					} else {
						throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
					}
				} else {
					throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				}
			} else {
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
		} else {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);			
		}
	}

	@Override
	public boolean enviarSentencia(SentenciaDTO sentenciaDTO, Instituciones institucion) throws NSJPNegocioException {
		
		//sentenciaDTO = this.consultarSentenciaCompleta(sentenciaDTO);
		
		clienteGeneralService.enviarSentencia(sentenciaDTO, institucion);
		
		return false;
	}

	
	public SentenciaDTO realizarAltasCambiosASentencia(SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		SentenciaDTO actualDTO = null; 
		try{
			Sentencia sentencia = null;
			if(sentenciaDTO != null){
				sentencia = SentenciaTransformer.transformar(sentenciaDTO);
				if (sentencia != null 
						&& sentencia.getSentenciaId() != null ){
					sentenciaDAO.saveOrUpdate(sentencia);	
					sentencia = sentenciaDAO.read(sentenciaDTO.getSentenciaId());
				} else {
					Long sentenciaId = sentenciaDAO.create(sentencia);
					sentencia = sentenciaDAO.read(sentenciaId);
				}
				actualDTO = SentenciaTransformer.transformar(sentencia);
			}
		}catch(Exception e){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES,e);
		}
		return actualDTO;
		
	}

	
	
	@Override
	public SentenciaDTO realizarAltaSentencia(SentenciaDTO sentenciaDTO)throws NSJPNegocioException {
		
		
		Sentencia sentencia = SentenciaTransformer.transformar(sentenciaDTO);
		
		if (sentencia.getInvolucrado() == null || sentencia.getNumeroExpediente() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		} 
			
			Involucrado involucrado = sentencia.getInvolucrado();
			//folioInvolucrado = involucrado.getFolioElemento();
			
			involucrado = involucradoDAO.read(sentencia.getInvolucrado().getElementoId());
			
			
			Long idSentencia=sentenciaDAO.create(sentencia);
			
				if(idSentencia!=null && idSentencia != 0){
					sentenciaDTO.setSentenciaId(idSentencia);
				}else{
					sentenciaDTO.setSentenciaId(null);
				}
				
				return sentenciaDTO;
	}

}
