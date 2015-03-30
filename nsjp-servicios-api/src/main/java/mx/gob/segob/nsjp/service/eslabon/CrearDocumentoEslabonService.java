package mx.gob.segob.nsjp.service.eslabon;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;

/**
 * @author rgama
 *
 */
public interface CrearDocumentoEslabonService {

	/**
	 * Operación que pérmite crear el documento de un Eslabon
	 * @param eslabon: eslabonId
	 * @return DocumentoDTO
	 * @throws NSJPNegocioException
	 */
	DocumentoDTO crearDocumentoEslabonService(EslabonDTO eslabon, Boolean esGuardado)throws NSJPNegocioException;

}
