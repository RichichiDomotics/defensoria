/**
 * Nombre del Programa : PermisoAudienciaTransformer.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Transformador para  Permiso Audiencia
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
package mx.gob.segob.nsjp.service.audiencia.impl.transform;

import mx.gob.segob.nsjp.dto.audiencia.CatEstadoPermisoDTO;
import mx.gob.segob.nsjp.dto.audiencia.PermisoAudienciaDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.model.CatEstadoPermiso;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.PermisoAudiencia;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform.FuncionarioExternoTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;

import org.apache.log4j.Logger;

/**
 * Transformador para Permiso Audiencia.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public class PermisoAudienciaTransformer {

    private final static Logger logger = Logger
            .getLogger(PermisoAudienciaTransformer.class);

	public static PermisoAudienciaDTO transformarPermisoAudienciaBasico(PermisoAudiencia PA) {
        
        if (PA == null) {
            return null;
        }
                
		logger.debug("PA.getAudiencia().getAudienciaId(): " + PA.getAudiencia().getAudienciaId());
        
        final PermisoAudienciaDTO PADto = new PermisoAudienciaDTO();
        
       	PADto.setEsVigente(PA.getEsVigente());        
       	PADto.setEsExterno(PA.getEsExterno());        
        PADto.setPermisoAudienciaId(PA.getPermisoAudienciaId());
        PADto.setFechaAsignacion(PA.getFechaAsignacion());
        PADto.setFechaSolicitud(PA.getFechaSolicitud());

        if(PA.getAudiencia()!=null){
        	PADto.setAudiencia(AudienciaTransformer.transformarBasico(PA.getAudiencia()));        	
        }

        if(PA.getUsuario()!=null){
        	PADto.setUsuario(UsuarioTransformer.transformarUsuario(PA.getUsuario()));        	
        }

        if(PA.getUsuarioAsignador()!=null){
        	PADto.setUsuarioAsignador(UsuarioTransformer.transformarUsuario(PA.getUsuarioAsignador()));        	
        }

        if(PA.getCatEstadoPermiso()!=null){
        	CatEstadoPermisoDTO EPDto = CatEstadoPermisoTransformer.transformarEstadoPermiso(PA.getCatEstadoPermiso());
        	PADto.setCatEstadoPermiso(EPDto);
        }

        if(PA.getFuncionarioExterno()!=null){
        	FuncionarioExternoDTO FEDto = FuncionarioExternoTransformer.transformar(PA.getFuncionarioExterno());
        	PADto.setFuncionarioExterno(FEDto);
        }
        
        if(PA.getConfInstitucion()!=null){
        	ConfInstitucionDTO confInstitucionDTO = ConfInstitucionTransformer.transformarInstitucion(PA.getConfInstitucion());
        	PADto.setConfInstitucion(confInstitucionDTO);
        }

        return PADto;
    }
	
	public static PermisoAudiencia transformarPermisoAudienciaBasicoDTO(PermisoAudienciaDTO paDTO) {
        
        if (paDTO == null) {
            return null;
        }
                
		logger.debug("PA.getAudiencia().getAudienciaId(): " + paDTO.getAudiencia().getId());
        
        final PermisoAudiencia pa = new PermisoAudiencia();
        
       	pa.setEsVigente(paDTO.getEsVigente());
       	pa.setEsExterno(paDTO.getEsExterno());        
       	pa.setPermisoAudienciaId(paDTO.getPermisoAudienciaId());
       	pa.setFechaAsignacion(paDTO.getFechaAsignacion());
       	pa.setFechaSolicitud(paDTO.getFechaSolicitud());

        if(paDTO.getAudiencia()!=null){
        	pa.setAudiencia(AudienciaTransformer.transformarAudiencia(paDTO.getAudiencia()));
        }

        if(paDTO.getUsuario()!=null){
        	pa.setUsuario(UsuarioTransformer.transformarDTO(paDTO.getUsuario()));
        }

        if(paDTO.getUsuarioAsignador()!=null){
        	pa.setUsuarioAsignador(UsuarioTransformer.transformarDTO(paDTO.getUsuarioAsignador()));        	
        }

        if(paDTO.getCatEstadoPermiso()!=null){
        	CatEstadoPermiso ep = CatEstadoPermisoTransformer.transformarEstadoPermisoDTO(paDTO.getCatEstadoPermiso());
        	pa.setCatEstadoPermiso(ep);
        }

        if(paDTO.getFuncionarioExterno()!=null){
        	FuncionarioExterno FE = FuncionarioExternoTransformer.transformar(paDTO.getFuncionarioExterno()); 
        	pa.setFuncionarioExterno(FE);
        }
        
        if(paDTO.getConfInstitucion()!=null){
        	ConfInstitucion confInstitucion = ConfInstitucionTransformer.transformarInstitucion(paDTO.getConfInstitucion());
        	pa.setConfInstitucion(confInstitucion);
        }

        return pa;
    }    
}
