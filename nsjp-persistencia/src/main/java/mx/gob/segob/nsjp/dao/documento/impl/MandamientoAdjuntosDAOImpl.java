/**
 * Nombre del Programa : MandamientoAdjuntosDAOImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 1 Sep 2011
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
package mx.gob.segob.nsjp.dao.documento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.MandamientoAdjuntosDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.MandamientoAdjuntos;
import mx.gob.segob.nsjp.model.MandamientoAdjuntosId;

import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository
public class MandamientoAdjuntosDAOImpl
        extends
            GenericDaoHibernateImpl<MandamientoAdjuntos, MandamientoAdjuntosId> implements MandamientoAdjuntosDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentoMandamientoAdjuntoPorMandamientoId(
			Long mandamientoId) {

		 final PaginacionDTO pag = PaginacionThreadHolder.get();
		 StringBuffer queryString = new StringBuffer();
		 
		 queryString.append("SELECT new Documento( d.documentoId, d.nombreDocumento, d.forma.formaId, d.tipoDocumento.valorId, d.tipoDocumento.valor, d.fechaCreacion )")
		 .append(" FROM Documento d WHERE d.archivoDigital.archivoDigitalId IN ( SELECT ma.id.archivoDigitalId FROM MandamientoAdjuntos ma")
		 .append(" WHERE ma.id.mandamientoId = ").append(mandamientoId);
		 queryString.append(")");
				 
		 return super.ejecutarQueryPaginado(queryString, pag);
	}

}
