/**
 * 
 */
package mx.gob.segob.nsjp.delegate.eslabon;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;

/**
 * @author adrian
 *
 */
public interface EslabonDelegate {

	/**
	 * Operación que realiza la funcionalidad de guardar(actualizar) el eslabón con la asociación a la evidencia que le corresponde
	 * @param evidenciaDTO: idEvidencia
	 * @param eslabonDTO: Objeto
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long guardarEslabon(EvidenciaDTO evidenciaDTO,EslabonDTO eslabonDTO)throws NSJPNegocioException;
	
	/**
	 * Operación que permite asociar un documento al eslabón
	 * @param eslabonDTO: idEslabon y idDocumento
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long asociarDocumentoAEslabon(EslabonDTO eslabonDTO)throws NSJPNegocioException;
	
	/**
	 * Consulta los eslabones asociados a una evidencia
	 * 
	 * @param evidenciaDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<EslabonDTO> consultarEslabonesPorEvidencia(EvidenciaDTO evidenciaDTO) throws NSJPNegocioException;
	
	
	/**
	 * Servicio que consulta si un objeto, que sea evidencia y, que esté, o no, en un eslabon.
	 * 	
	 * @param idObjeto
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean tieneEslabonPorEvidenciaYObjeto(Long idObjeto)  throws NSJPNegocioException;
	
	/**
	 * Servicio que consulta si un objeto, activo, es una evidencia y no tiene un eslabon asociado.
	 * 
	 * @param idObjeto
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean esEvidenciaNoTieneEslabon(Long idObjeto)  throws NSJPNegocioException;
	

    /**
     * Consulta el ultimo eslabon asociado a una evidencia, opcionalmente por tipo de eslabon
     * @param evidencia La evidencia de la que se requiere el ultimo eslabon.
     * @param tipoEslabon Representa el tipo de eslabon
     * @return El ultimo eslabon asociado a una evidencia o {@code null} en caso
     * que la evidencia solicitada no exista o que no tenga eslabones asociados.
     */
	EslabonDTO consultaUltimoEslabonXEvidenciaYTipo(EvidenciaDTO evidenciaDTO, Long tipoEslabon) throws NSJPNegocioException;
	
	/**
	 * Consulta documentos asociados a un Eslabon
	 * @param eslabonDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<DocumentoDTO> consultarDocumentosXIdEslabon(EslabonDTO eslabonDTO) throws NSJPNegocioException;
	
	/**
	 * Permite consultar el id del expediente asociado a un eslabon
	 * @param eslabonId
	 * @return
	 */
	public Long obtenIdExpedienteDeUnEslabon(Long eslabonId)throws NSJPNegocioException;
	
	/**
	 * Permite consultar un eslabon por identificador
	 * @param idEslabon
	 * @return
	 * @throws NSJPNegocioException
	 */
	public EslabonDTO consultaEslabonPorId(Long idEslabon) throws NSJPNegocioException;


	
}
