/**
* Nombre del Programa : OrdenDeAprehensionTransformer.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 01/08/2012
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
package mx.gob.segob.nsjp.service.ordenaprehension.impl.transform;

import mx.gob.segob.nsjp.dto.ordenaprehension.OrdenDeAprehensionDTO;
import mx.gob.segob.nsjp.model.OrdenDeAprehension;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.MandamientoJudicialTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class OrdenDeAprehensionTransformer {

	
	public static OrdenDeAprehensionDTO transformar (OrdenDeAprehension  entity){
		OrdenDeAprehensionDTO  dto = null;
		if(entity != null){
			dto = new OrdenDeAprehensionDTO();
			dto.setOrdenDeAprehensionId(entity.getOrdenDeAprehensionId());
			dto.setAudienciaDTO(AudienciaTransformer.transformarBasico(entity
					.getAudiencia()));
			
			dto.setInvolucradoDTO(InvolucradoTransformer
					.transformarInvolucrado(entity.getInvolucrado()));
			
			dto.setMandamientoDTO(MandamientoJudicialTransformer
					.transformarMandamiento(entity.getMandamiento()));

			dto.setNumeroExpedienteDTO(ExpedienteTransformer.transformarExpediente(entity.getNumeroExpediente()));
			
			dto.setEsCumplida(entity.getEsCumplida());
			dto.setSePresentaVoluntariamente(entity.getSePresentaVoluntariamente());
			dto.setValidarDocumentoDigital(entity.getValidarDocumentoDigital());
		}
		
		return dto;
	}
	
	public static OrdenDeAprehension transformar (OrdenDeAprehensionDTO  dto){
		OrdenDeAprehension  entity = null;
		if(dto != null){
			entity = new OrdenDeAprehension();
			entity.setOrdenDeAprehensionId(dto.getOrdenDeAprehensionId());
			entity.setAudiencia(AudienciaTransformer.transformarAudiencia(dto
					.getAudienciaDTO()));
			
			entity.setInvolucrado(InvolucradoTransformer
					.transformarInvolucrado(dto.getInvolucradoDTO()));
			
			entity.setMandamiento(MandamientoJudicialTransformer
					.transformarMandamientoDTO(dto.getMandamientoDTO()));

			entity.setNumeroExpediente(ExpedienteTransformer.obtenerDeExpedienteDTO(dto.getNumeroExpedienteDTO()));
			
			entity.setEsCumplida(dto.getEsCumplida());
			entity.setSePresentaVoluntariamente(dto.getSePresentaVoluntariamente());
			entity.setValidarDocumentoDigital(dto.getValidarDocumentoDigital());
		}
		
		return entity;
	}
	
	
	
}
