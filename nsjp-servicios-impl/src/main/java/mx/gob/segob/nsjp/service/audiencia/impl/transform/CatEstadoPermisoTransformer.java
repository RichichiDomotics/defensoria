/**
 * Nombre del Programa : CatEstadoTransformer.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Transformador para estados permisos
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
import mx.gob.segob.nsjp.model.CatEstadoPermiso;

/**
 * Transformador para estados permisos.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public class CatEstadoPermisoTransformer {

	public static CatEstadoPermisoDTO transformarEstadoPermiso(CatEstadoPermiso EP) {
        
        if (EP == null) {
            return null;
        }
        
        final CatEstadoPermisoDTO EPDto = new CatEstadoPermisoDTO();
        
       	EPDto.setEstatus(EP.getEstatus());
       	EPDto.setCatEstadoPermisoId(EP.getCatEstadoPermisoId());
        
        return EPDto;
    }
	
	public static CatEstadoPermiso transformarEstadoPermisoDTO(CatEstadoPermisoDTO epDTO) {
        
        if (epDTO == null) {
            return null;
        }
        
        final CatEstadoPermiso ep = new CatEstadoPermiso();
        
       	ep.setEstatus(epDTO.getEstatus());
       	ep.setCatEstadoPermisoId(epDTO.getCatEstadoPermisoId());
        
        return ep;
    }    
}
