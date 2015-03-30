/**
 * Nombre del Programa : EslabonDocumentoDAOImpls.java
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
package mx.gob.segob.nsjp.dao.eslabon.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.eslabon.EslabonDocumentoDAO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.EslabonDocumento;
import mx.gob.segob.nsjp.model.EslabonDocumentoId;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @version 1.0
 * @author rgama
 */
@Repository
public class EslabonDocumentoDAOImpl
    extends GenericDaoHibernateImpl<EslabonDocumento, EslabonDocumentoId>
    implements EslabonDocumentoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosXIdEslabon(Long idEslabon) {
		StringBuilder sb = new StringBuilder();
		
        sb.append("SELECT ed.documento FROM EslabonDocumento ed ")
          .append(" WHERE ed.eslabon.eslabonId ="+idEslabon);
        Query query = super.getSession().createQuery(sb.toString());
        return query.list();
	}
}
