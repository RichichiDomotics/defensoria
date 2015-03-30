/**
 * Nombre del Programa : EslabonDocumentoDAO.java
 * Autor                            : rgama
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 05-Mar-2012
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.eslabon;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.EslabonDocumento;
import mx.gob.segob.nsjp.model.EslabonDocumentoId;

/**
 * 
 * @version 1.0
 * @author rgama
 */
public interface EslabonDocumentoDAO extends GenericDao<EslabonDocumento, EslabonDocumentoId>{
   
	/**
	 * Consulta los documentos asociados a un Eslabon
	 * @param idEslabon
	 * @return Lista de documentos
	 */
	public List<Documento> consultarDocumentosXIdEslabon(Long idEslabon);
	
	
}
