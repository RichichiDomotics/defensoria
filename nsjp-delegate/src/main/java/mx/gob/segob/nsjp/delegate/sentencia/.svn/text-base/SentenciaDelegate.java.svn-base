/**
 * 
 */
package mx.gob.segob.nsjp.delegate.sentencia;

import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;

/**
 * @author AntonioBV
 *
 */
public interface SentenciaDelegate {
	/**
	 * M&eacute;todo que consulta un NUS en base al CURP del Involucrado 
	 * @param involucradoDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<SentenciaDTO> consultarNUS(InvolucradoDTO involucradoDTO)throws NSJPNegocioException;

	HashMap<String, String> consultarNUSTOJSON(InvolucradoDTO involucradoDTO)throws NSJPNegocioException;

	/**
	 * M&eacute;todo que crea o actualiza una sentencia. 
	 * @param sentenciaDTO sentencia a crear o actualizar.
	 * @return Sentencia con los datos persistidos.
	 * @throws NSJPNegocioException
	 */
	SentenciaDTO realizarAltasCambiosASentencia(SentenciaDTO sentenciaDTO) throws NSJPNegocioException;

	
	/**
	 * M&eacute;todo que crea o actualiza una sentencia. 
	 * @author Gustavo
	 * @param sentenciaDTO sentencia a crear o actualizar.
	 * @return Sentencia con los datos persistidos.
	 * @throws NSJPNegocioException
	 */
	
	SentenciaDTO realizarAltaSentencia(SentenciaDTO sentenciaDTO) throws NSJPNegocioException;
	
	
}
