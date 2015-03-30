/**
 * Nombre del Programa : CatAreaNegocioTransformer.java
 * Autor               : AlejandroGA
 * Compania            : Ultrasist
 * Proyecto            : NSJP                    Fecha: 23/05/2012
 * Marca de cambio     : N/A
 * Descripcion General : Transformer para CatAreasNegocio
 * Programa Dependiente: N/A
 * Programa Subsecuente: N/A
 * Cond. de ejecucion  : N/A
 * Dias de ejecucion   : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor               :N/A
 * Compania            :N/A
 * Proyecto            :N/A                                 Fecha: N/A
 * Modificacion        :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.catalogo.impl.transform;

import mx.gob.segob.nsjp.dto.catalogo.CatAreasNegocioDTO;
import mx.gob.segob.nsjp.model.CatAreasNegocio;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;

/**
 * Transformar de entity a dto y viceversa
 * Fecha: 23/05/2012
 * @version 1.0
 * @author AlejandroGA
 */
public class CatAreaNegocioTransformer {

	public static CatAreasNegocio transformarCatAreasNegocio(CatAreasNegocioDTO dto) {
		if(dto == null){
			return null;
		}
		
		CatAreasNegocio loCatAreasNegocio = new CatAreasNegocio();
		
		loCatAreasNegocio.setCatAreasNegocioId(dto.getCatAreasNegocioId());		
		loCatAreasNegocio.setNombreCatAN(dto.getNombreCatAreaNegocio());
		loCatAreasNegocio.setEsUIE(dto.getEsUIE());
		
		if(dto.getConfInstitucion() != null)
			loCatAreasNegocio.setConfInstitucion(ConfInstitucionTransformer.transformarInstitucion(dto.getConfInstitucion()));
		
		return loCatAreasNegocio;
	}

	public static CatAreasNegocioDTO transformarCatAreasNegocio(CatAreasNegocio entity) {
		if(entity == null){
			return null;
		}
		
		CatAreasNegocioDTO loCatAreasNegocio = new CatAreasNegocioDTO();
		
		loCatAreasNegocio.setCatAreasNegocioId(entity.getCatAreasNegocioId());		
		loCatAreasNegocio.setNombreCatAreaNegocio(entity.getNombreCatAN());
		loCatAreasNegocio.setEsUIE(entity.getEsUIE());
		
		if(entity.getConfInstitucion() != null)
			loCatAreasNegocio.setConfInstitucion(ConfInstitucionTransformer.transformarInstitucion(entity.getConfInstitucion()));
		
		return loCatAreasNegocio;
	}

	/**
	 * Metodo para transformar el objeto sin perder su referncia, usado para hacer update
	 * @param inputCatAreaNegDto
	 * @param updateCatAreaNeg
	 * @return el objeto actualizado sin perder su referencia
	 */
	public static CatAreasNegocio traformarCatAreaNegocioDtoUpdate(CatAreasNegocioDTO inputCatAreaNegDto, CatAreasNegocio updateCatAreaNeg){
	
		if(inputCatAreaNegDto != null){
			
			if(inputCatAreaNegDto.getCatAreasNegocioId() != null){
				updateCatAreaNeg.setCatAreasNegocioId(inputCatAreaNegDto.getCatAreasNegocioId());
			}
			
			if(inputCatAreaNegDto.getNombreCatAreaNegocio() != null){
				updateCatAreaNeg.setNombreCatAN(inputCatAreaNegDto.getNombreCatAreaNegocio());
			}
			
			if(inputCatAreaNegDto.getEsUIE() != null){
				updateCatAreaNeg.setEsUIE(inputCatAreaNegDto.getEsUIE());
			}
			
			if(inputCatAreaNegDto.getConfInstitucion() != null && inputCatAreaNegDto.getConfInstitucion().getConfInstitucionId() != null){
				updateCatAreaNeg.setConfInstitucion(new ConfInstitucion(inputCatAreaNegDto.getConfInstitucion().getConfInstitucionId()));
			}
		}
		return updateCatAreaNeg;
	}

}