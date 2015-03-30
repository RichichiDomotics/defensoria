/**
 * Nombre del Programa : ConsultarAudienciasPorEstatusServiceImpl.java
 * Autor                            : adrian
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 23/06/2011
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
package mx.gob.segob.nsjp.service.audiencia.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.service.audiencia.ConsultarAudienciasPorEstatusService;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.infra.PJClienteService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author adrian
 * 
 */
@Service
@Transactional
public class ConsultarAudienciasPorEstatusServiceImpl implements
		ConsultarAudienciasPorEstatusService {

	private final static Logger logger = Logger
			.getLogger(ConsultarAudienciasPorEstatusServiceImpl.class);

	@Autowired
	private AudienciaDAO audDao;
	@Autowired
	private PJClienteService pjClienteService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.audiencia.ConsultarAudienciasPorEstatusService
	 * #consultarAudienciasPorEstatus(java.lang.String)
	 */
	@Override
	public List<AudienciaDTO> consultarAudienciasPorEstatus(Long estatus)
			throws NSJPNegocioException {
		
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR AUDIENCIAS POR ESTADO ****/");
		
		if (estatus==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		List<Audiencia> audiencias =audDao.consultarAudienciasPorEstatus(estatus);
		
		List<AudienciaDTO> audienciasDTO=new ArrayList<AudienciaDTO>();
		for (Audiencia aud : audiencias) {
			audienciasDTO.add(AudienciaTransformer.transformarDTO(aud));			
		}
		return audienciasDTO;
	}

	@Override
	public List<AudienciaDTO> consultarAudienciasFromPoderJudicial(
			AudienciaDTO audiencia, Long claveFuncionarioExt,
			String cadenaEstatus, String cadenaTipo, Long confInstId)throws NSJPNegocioException{
		if(audiencia == null || audiencia.getFechaFiltroInicio() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		List<AudienciaDTO> listAudienciaDTO=pjClienteService.consultarAudienciasPorFechasYSolicitudTranscripcion(
				audiencia, claveFuncionarioExt, cadenaEstatus, cadenaTipo,confInstId);
		listAudienciaDTO=paginacionManual(listAudienciaDTO);
		return  listAudienciaDTO;
	}
	
	private List<AudienciaDTO> paginacionManual(
			List<AudienciaDTO> audienciaDTO) {
		final PaginacionDTO pag=PaginacionThreadHolder.get();
		int total=audienciaDTO.size()/pag.getRows();
		if((pag.getPage()*pag.getRows())-pag.getRows()>audienciaDTO.size()){
			pag.setPage(1);
		}
		int inicio=0;
		if(pag.getPage()<=1){
			inicio=0;
		}else{
			inicio=((pag.getPage()-1)*pag.getRows());
		}
		int indiceFinal=inicio+pag.getRows();
		List<AudienciaDTO> listaAlterna=audienciaDTO;
		audienciaDTO=new ArrayList<AudienciaDTO>();
		if(indiceFinal>=listaAlterna.size()){
			for (int i = inicio; i < listaAlterna.size(); i++) {
				audienciaDTO.add(listaAlterna.get(i));
			}
		}else{
			for (int i = inicio; i < indiceFinal; i++) {
				audienciaDTO.add(listaAlterna.get(i));
			}
		}
		
		
		pag.setTotalRegistros((long)listaAlterna.size());
		pag.setPaginacionHecha(true);
		PaginacionThreadHolder.set(pag);
		return audienciaDTO;
	}
	
	@Override
	public List<AudienciaDTO> consultarAudienciasFromPoderJudicial(
			AudienciaDTO audiencia) throws NSJPNegocioException {
		if(audiencia == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		return pjClienteService.consultarAudienciasByFechasYEstatus(audiencia);
	}
	
	@Override
	public AudienciaDTO consultarAudienciaFromPoderJudicial(
			AudienciaDTO audiencia) throws NSJPNegocioException {
		if(audiencia == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		return pjClienteService.consultarAudienciasById(audiencia);
	}

}
