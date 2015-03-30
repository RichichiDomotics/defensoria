/**
 * Nombre del Programa PermisoAudienciaDAOImpl.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 01 Jun 2012
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
package mx.gob.segob.nsjp.dao.audiencia.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.audiencia.PermisoAudienciaDAO;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.PermisoAudiencia;

import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
@Repository
public class PermisoAudienciaDAOImpl extends GenericDaoHibernateImpl<PermisoAudiencia, Long>
		implements PermisoAudienciaDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<PermisoAudiencia> consultarPermisosAudienciaPorEstado(Long estado, Date fechaInicio, Date fechaFin, Long discriminanteId) {
		
		StringBuffer query = new StringBuffer();
		query.append("FROM PermisoAudiencia pa")
			 .append(" WHERE pa.catEstadoPermiso.catEstadoPermisoId=")
			 .append(estado).append(" AND ")
			 .append(" pa.esVigente = true");
		
		if (fechaFin != null && fechaInicio!=null) {
			query.append(" AND CONVERT (nvarchar, pa.fechaSolicitud, 112) between ");
			query.append(DateUtils.formatearBD(fechaInicio));
			query.append(" AND ").append(DateUtils.formatearBD(fechaFin));
		} else {
			if (fechaInicio != null) {
				query.append(" AND CONVERT (nvarchar, pa.fechaSolicitud, 112) = ");
				query.append(DateUtils.formatearBD(fechaInicio));
			}
			else if(fechaFin != null){
				query.append(" AND CONVERT (nvarchar, pa.fechaSolicitud, 112) = ");
				query.append(DateUtils.formatearBD(fechaFin));				
			}
		}
		
		if(discriminanteId !=null && !discriminanteId.equals(0L)){
			query.append(" AND pa.audiencia.numeroExpediente.expediente.discriminante.catDiscriminanteId = " + discriminanteId);
		}
		
		query.append(" order by pa.audiencia desc");
		
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);        
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PermisoAudiencia> consultarPermisosAudienciaPorNumeroAudienciaInterno(Long numeroAudiencia, String claveUsuario) {
		
		StringBuffer query = new StringBuffer();
		query.append("FROM PermisoAudiencia pu")
			 .append(" WHERE pu.audiencia.audienciaId=")
			 .append(numeroAudiencia).append(" AND ")
			 .append(" pu.usuario.claveUsuario='")
			 .append(claveUsuario).append("' AND ")
			 .append(" pu.esVigente = true");
		query.append(" order by pu.audiencia desc");
		
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);        
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PermisoAudiencia> consultarPermisosAudienciaPorFuncionarioExterno(Long numeroAudiencia, Long cveFunExt) {
		
		StringBuffer query = new StringBuffer();
		query.append("FROM PermisoAudiencia pu")
			 .append(" WHERE pu.audiencia.audienciaId=")
			 .append(numeroAudiencia).append(" AND ")
			 .append(" pu.funcionarioExterno.funcionarioExternoId=")
			 .append(cveFunExt).append(" AND ")
			 .append(" pu.esVigente = true");
		query.append(" order by pu.audiencia desc");
		
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);        
	}	
}
