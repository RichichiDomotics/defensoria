/**
* Nombre del Programa : BitacoraDescargaTransformer.java
* Autor                            : AAAV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 31/08/2011
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
package mx.gob.segob.nsjp.service.bitacora.impl.transform;

import mx.gob.segob.nsjp.dto.bitacora.BitacoraDescargaDTO;
import mx.gob.segob.nsjp.model.BitacoraDescarga;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.PermisoAudienciaTransformer;

/**
 * Clase para convertir objetos BitacoraDescarga a BitacoraDescarga y viceversa.
 * 
 * @version 1.0
 * @author AAAV
 *
 */
public class BitacoraDescargaTransformer {

	public static BitacoraDescarga transformarBitacoraDescargaDTO(BitacoraDescargaDTO bdDTO) {
        
        if (bdDTO == null) {
            return null;
        }
                       
        final BitacoraDescarga bd = new BitacoraDescarga();
        
        if(bdDTO.getBitacoraDescargaId()!=null){
        	bd.setBitacoraDescargaId(bdDTO.getBitacoraDescargaId());        	
        }
        
        bd.setPermisoAudiencia(PermisoAudienciaTransformer.transformarPermisoAudienciaBasicoDTO(bdDTO.getPermisoAudienciaDTO()));
        
        bd.setFechaDescarga(bdDTO.getFechaDescarga());
        
        return bd;
	}        
	
	public static BitacoraDescargaDTO transformarBitacoraDescarga(BitacoraDescarga bd) {
        
        if (bd == null) {
            return null;
        }
                       
        final BitacoraDescargaDTO bdDTO = new BitacoraDescargaDTO();
        
        if(bd.getBitacoraDescargaId()!=null){
        	bdDTO.setBitacoraDescargaId(bd.getBitacoraDescargaId());        	
        }
        
        bdDTO.setPermisoAudienciaDTO(PermisoAudienciaTransformer.transformarPermisoAudienciaBasico(bd.getPermisoAudiencia()));
        
        bdDTO.setFechaDescarga(bd.getFechaDescarga());
        
        return bdDTO;
	}        
}
