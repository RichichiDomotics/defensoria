/**
 * Nombre del Programa : ConsultarEstadoPermisoServiceImpl.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementación del servicio para Consultar Estado Permiso 
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

import java.util.LinkedList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.CatEstadoPermisoDAO;
import mx.gob.segob.nsjp.dto.audiencia.CatEstadoPermisoDTO;
import mx.gob.segob.nsjp.model.CatEstadoPermiso;
import mx.gob.segob.nsjp.service.audiencia.ConsultarEstadoPermisoService;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.CatEstadoPermisoTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio para Consultar Estado Permiso.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ConsultarEstadoPermisoServiceImpl implements ConsultarEstadoPermisoService {

	@SuppressWarnings("unused")
	private final static Logger logger = Logger
            .getLogger(ConsultarPermisosAudienciaServiceImpl.class);

    @Autowired
    private CatEstadoPermisoDAO epDao;

	public List<CatEstadoPermisoDTO> buscarEstadosPermiso() throws NSJPNegocioException{
		
		List<CatEstadoPermiso> EPs = epDao.consultarEstadosPermisos();
		List<CatEstadoPermisoDTO> EPsDto = new LinkedList<CatEstadoPermisoDTO>();
		for(CatEstadoPermiso EP: EPs){
			EPsDto.add(CatEstadoPermisoTransformer.transformarEstadoPermiso(EP));
		}
		
		return EPsDto;
	}	
	
	public CatEstadoPermisoDTO buscarEstadoPermisoPorEstado(Long estado)throws NSJPNegocioException{
		if(estado==null || estado.equals(0L)){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
	
		CatEstadoPermiso ep = epDao.read(estado);
		CatEstadoPermisoDTO epDTO = CatEstadoPermisoTransformer.transformarEstadoPermiso(ep);
		
		return epDTO;
	}		
}
