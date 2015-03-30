/**
 * Nombre del Programa : OrdenDeAprehensionDAOImpl.java
 * Autor                            : AntonioBV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 01/08/2012
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
package mx.gob.segob.nsjp.dao.ordenaprehension.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.ordenaprehension.OrdenDeAprehensionDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.OrdenDeAprehension;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AntonioBV
 * 
 */
@Repository
public class OrdenDeAprehensionDAOImpl extends
		GenericDaoHibernateImpl<OrdenDeAprehension, Long> implements
		OrdenDeAprehensionDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<OrdenDeAprehension> consultarOrdenDeAprehension(
			OrdenDeAprehension filtro) throws NSJPNegocioException {

		final StringBuffer queryStr = new StringBuffer();
		queryStr.append(" FROM OrdenDeAprehension oa where 1 = 1 ");

		if (filtro.getOrdenDeAprehensionId() != null) {
			queryStr.append(" AND oa.ordenDeAprehensionId =  ").append(
					filtro.getOrdenDeAprehensionId());
		}
		
		if (filtro.getAudiencia() != null
				&& filtro.getAudiencia().getAudienciaId() != null) {
			queryStr.append(" AND oa.audiencia.audienciaId =  ").append(
					filtro.getAudiencia().getAudienciaId());
		}

		if (filtro.getMandamiento() != null
				&& filtro.getMandamiento().getDocumentoId() != null) {
			queryStr.append(" AND oa.mandamiento.documentoId =  ").append(
					filtro.getMandamiento().getDocumentoId());
		}

		if (filtro.getNumeroExpediente() != null
				&& filtro.getNumeroExpediente().getNumeroExpedienteId() != null) {
			queryStr.append(" AND oa.numeroExpediente.numeroExpedienteId =  ")
					.append(filtro.getNumeroExpediente()
							.getNumeroExpedienteId());
		}

		if (filtro.getEsCumplida() != null) {
			queryStr.append(" AND oa.esCumplida =  ")
					.append(filtro.getEsCumplida());
		}

		if (filtro.getSePresentaVoluntariamente() != null) {
			queryStr.append(" AND oa.sePresentaVoluntariamente =  ")
					.append(filtro.getEsCumplida());
		}		
		
		if(filtro.getValidarDocumentoDigital() != null && filtro.getValidarDocumentoDigital()){
			queryStr.append(" AND oa.mandamiento.archivoDigital IS NOT NULL ");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(queryStr);
		}

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(queryStr, pag);
	}

}
