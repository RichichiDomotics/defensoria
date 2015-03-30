/**
 * Nombre del Programa : IndicadorTransformer.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 20/06/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Clase para convertir elementos del enum Indicador a IndicadorDTO
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
package mx.gob.segob.nsjp.service.indicador.impl.transform;

import mx.gob.segob.nsjp.comun.indicador.Indicadores;
import mx.gob.segob.nsjp.dto.indicador.IndicadorDTO;

/**
 * Clase para convertir elementos del enum Indicador a IndicadorDTO
 * 
 * @author GustavoBP
 */
public class IndicadorTransformer {
	
	public static IndicadorDTO transformarIndicadorDTO(Indicadores indicador){
		if(indicador==null){
			return null;
		}
		IndicadorDTO indicadorDTO = new IndicadorDTO();
		indicadorDTO.setIndicadorId(indicador.getIndicadorId());
		indicadorDTO.setInstitucionId(indicador.getInstitucion().getValorId());
		indicadorDTO.setInstitucion(indicador.getInstitucion().name());
		indicadorDTO.setTitulo(indicador.getTitulo());
		
		indicadorDTO.setNombreColumnas(indicador.getNombreColumnas());
		indicadorDTO.setParametros(indicador.getParametros());
		
		return indicadorDTO;
	}
}
