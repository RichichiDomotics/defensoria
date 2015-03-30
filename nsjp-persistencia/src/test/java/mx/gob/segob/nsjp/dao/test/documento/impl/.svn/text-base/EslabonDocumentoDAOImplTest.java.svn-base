/**
* Nombre del Programa : EslabonDocumentoDAOImplTest.java
* Autor                            : rgama
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 05 Mar 2012
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
package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.eslabon.EslabonDocumentoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.EslabonDocumento;
import mx.gob.segob.nsjp.model.EslabonDocumentoId;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author rgama
 *
 */
public class EslabonDocumentoDAOImplTest extends BaseTestPersistencia<EslabonDocumentoDAO> {

    
	public void testConsultaEslabonesPorEvidencia(){    	
    	Long idEslabon = 10L;
    	
    	List<Documento> eslabones  = daoServcice.consultarDocumentosXIdEslabon(idEslabon);
    	logger.info(" Total de documentos para el eslabon ("+idEslabon+"): " + eslabones.size());
    }
	
	public void testCreate(){    	
    	Long idEslabon = 10L;
    	Long documentoId = 278L;
    	
    	EslabonDocumento loEslabonDocumento = new EslabonDocumento();
    	loEslabonDocumento.setId(new EslabonDocumentoId(idEslabon, documentoId));
    	EslabonDocumentoId idBD = daoServcice.create(loEslabonDocumento);
    	
    	logger.info("Documento id: " + idBD.getDocumentoId());
    	logger.info("Eslabon id " + idBD.getEslabonId());
    }
	
	public void testConsultaTodos(){
    	List<EslabonDocumento> eslabonesDoc= daoServcice.findAll("documento", false);
    	for (EslabonDocumento eslabonBD : eslabonesDoc) {
    		EslabonDocumentoId idBD = eslabonBD.getId();
    		logger.info("Documento id: " + idBD.getDocumentoId());
        	logger.info("Eslabon id " + idBD.getEslabonId());
		}
    	
    	
    }
    
}
