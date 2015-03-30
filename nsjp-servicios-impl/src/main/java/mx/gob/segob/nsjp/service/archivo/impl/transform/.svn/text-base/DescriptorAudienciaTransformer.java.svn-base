/**
 * Nombre del Programa : DescriptorAudienciaTransformer.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 30/05/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Clase tranformadora para el objeto de Descriptor Audiencia
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
package mx.gob.segob.nsjp.service.archivo.impl.transform;

import mx.gob.segob.nsjp.dto.archivo.DescriptorAudienciaDTO;
import mx.gob.segob.nsjp.model.DescriptorAudiencia;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;

/**
 * Clase transformadora para el objeto de Descriptor Audiencia
 * @author AAAV
 *
 */
public class DescriptorAudienciaTransformer {

	public static DescriptorAudiencia transformarDescriptorAudienciaDTO(DescriptorAudienciaDTO srcDTO){
		DescriptorAudiencia dest= null;
		
		if(srcDTO != null){
			
			dest = new DescriptorAudiencia();
				
			dest.setArchivo(srcDTO.getArchivo());
			dest.setAudiencia(AudienciaTransformer.transformarAudiencia(srcDTO.getAudiencia()));
			dest.setDescriptorAudienciaId(srcDTO.getDescriptorAudienciaId());
			dest.setMensaje(srcDTO.getMensaje());
			dest.setPaths(srcDTO.getPaths());
			dest.setResultado(srcDTO.getResultado());
		}		
		return dest;
	}

	public static DescriptorAudiencia transformarDescriptorAudienciaDeDTO(DescriptorAudienciaDTO srcDTO, DescriptorAudiencia src){
		
		if(srcDTO==null){
			return null;
		}
		
		if(src != null){
			src.setArchivo(srcDTO.getArchivo());
			src.setAudiencia(AudienciaTransformer.transformarAudiencia(srcDTO.getAudiencia()));
			src.setDescriptorAudienciaId(srcDTO.getDescriptorAudienciaId());
			src.setMensaje(srcDTO.getMensaje());
			src.setPaths(srcDTO.getPaths());
			src.setResultado(srcDTO.getResultado());
		}		
		
		return src;
	}

	public static DescriptorAudienciaDTO transformarDescriptorAudiencia(DescriptorAudiencia src){
		DescriptorAudienciaDTO destDTO= null;
		
		if(src != null){
			
			destDTO = new DescriptorAudienciaDTO();
				
			destDTO.setArchivo(src.getArchivo());
			destDTO.setAudiencia(AudienciaTransformer.transformarBasico(src.getAudiencia()));
			destDTO.setDescriptorAudienciaId(src.getDescriptorAudienciaId());
			destDTO.setMensaje(src.getMensaje());
			destDTO.setPaths(src.getPaths());
			destDTO.setResultado(src.getResultado());
		}		
		return destDTO;
	}
}
