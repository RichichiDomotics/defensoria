/**
* Nombre del Programa : BitacoraDescargaDAOImpl.java
* Autor                            : AAAV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 31/08/2011
* Marca de cambio        : N/A
* Descripcion General    : Implementación del DAO para BitacoraDescarga.
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
package mx.gob.segob.nsjp.dao.bitacora.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.bitacora.BitacoraDescargaDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.BitacoraDescarga;

import org.springframework.stereotype.Repository;

/**
 * Implementación del DAO para BitacoraDescarga.
 * @version 1.0
 * @author AAAV
 */
@Repository
public class BitacoraDescargaDAOImpl extends GenericDaoHibernateImpl<BitacoraDescarga, Long> implements BitacoraDescargaDAO {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BitacoraDescarga> consultarBitacoraDescargaPorFecha(Date fechaInicio, Date fechaFin, Long discriminanteId) {
		
		StringBuffer query = new StringBuffer();
		query.append("FROM BitacoraDescarga bd")
			 .append(" WHERE 1=1 ");
		
		if (fechaFin != null && fechaInicio!=null) {
			query.append(" AND CONVERT (nvarchar, bd.fechaDescarga, 112) between ");
			query.append(DateUtils.formatearBD(fechaInicio));
			query.append(" AND ").append(DateUtils.formatearBD(fechaFin));
		} else {
			if (fechaInicio != null) {
				query.append(" AND CONVERT (nvarchar, bd.fechaDescarga, 112) = ");
				query.append(DateUtils.formatearBD(fechaInicio));
			}
			else if(fechaFin != null){
				query.append(" AND CONVERT (nvarchar, bd.fechaDescarga, 112) = ");
				query.append(DateUtils.formatearBD(fechaFin));				
			}
		}
		
		if(discriminanteId !=null && !discriminanteId.equals(0L)){
			query.append(" AND bd.permisoAudiencia.audiencia.numeroExpediente.expediente.discriminante.catDiscriminanteId = " + discriminanteId);
		}
		query.append(" order by bd.bitacoraDescargaId desc");
		
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);        
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BitacoraDescarga> consultarBitacoraDescargaPorAudiencia(Long audiencia, Long discriminanteId) {
		
		StringBuffer query = new StringBuffer();
		query.append("FROM BitacoraDescarga bd");
		query.append(" WHERE bd.permisoAudiencia.audiencia="+audiencia);
		
		if(discriminanteId !=null && !discriminanteId.equals(0L)){
			query.append(" AND bd.permisoAudiencia.audiencia.numeroExpediente.expediente.discriminante.catDiscriminanteId = " + discriminanteId);
		}
		
		query.append(" order by bd.bitacoraDescargaId desc");
		
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);        
	}	
}
