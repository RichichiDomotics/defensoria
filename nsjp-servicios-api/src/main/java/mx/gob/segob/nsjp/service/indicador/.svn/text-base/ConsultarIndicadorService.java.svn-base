/**
 *
 * Nombre del Programa : ConsultarIndicadorService.java                                    
 * Autor                            : GustavoBP                                           
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 19/06/2012 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Interface del servicio para obtener los indicadores                      
 * Programa Dependiente  :N/A                                                      
 * Programa Subsecuente :N/A                                                      
 * Cond. de ejecucion        :N/A                                                      
 * Dias de ejecucion          :N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.service.indicador;

import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.indicador.Indicadores;
import mx.gob.segob.nsjp.dto.indicador.IndicadorDTO;

/**
 * Interface del servicio para obtener los indicadores
 * 
 * @author GustavoBP
 */
public interface ConsultarIndicadorService {
	
	/**
	 * Efectua, de acuerdo al indicador selecionado, la consulta en BD
	 * El resultado es una lista de arreglos de objetos, la lista representa 
	 * los renglones y cada datos de cada arreglo es la columna.
	 * El primer elemento de la lista es un arreglo con el nombre de las columnas 
	 * de la consulta.
	 * Lo siguientes elementos son arreglos con los valores de la consulta
	 * El tamanio de los arreglos (columnas) es dínamico.
	 * 
	 * @param indicador
	 * @param valores
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<Object[]> consultarIndicador(Indicadores indicador,
			HashMap<String, String> valores) throws NSJPNegocioException;

	/**
	 * Consulta los indicadores de acuerdo a la institucion actual
	 * 
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<IndicadorDTO> consultarIndicadorPorInstitucionActual()throws NSJPNegocioException;

}
