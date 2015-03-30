/**
 * Enable JC. DAO para manipular solicitudes compartidas en UAVD
 */
package mx.gob.segob.nsjp.dao.solicitud.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.solicitud.PermisoSolicitudDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.PermisoSolicitud;
import mx.gob.segob.nsjp.model.PermisoSolicitudId;

@Repository
public class PermisoSolicitudDAOImpl extends GenericDaoHibernateImpl<PermisoSolicitud, PermisoSolicitudId>
		implements PermisoSolicitudDAO {
	private static final Logger logger = Logger.getLogger(PermisoSolicitudDAOImpl.class);


	@SuppressWarnings("unchecked")
	@Override
	public List<PermisoSolicitud> consultarSolicitudesConPermisoFuncionario(Long funcionarioId){
		if(funcionarioId == null){
			logger.warn("El funcionarioId es NULL. Se regresa lista vacia.");
			return new ArrayList<PermisoSolicitud>();
		}else{
			Calendar diaActual = Calendar.getInstance();
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT ps FROM PermisoSolicitud ps WHERE ")
						.append("ps.funcionario=").append(funcionarioId)
						.append(" AND CONVERT (VARCHAR, ps.fechaLimite, 112)>=").append(DateUtils.formatearBD(diaActual.getTime()));

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			return ejecutarQueryPaginado(queryString, pag);
		}
	}
}
