/**
 * Nombre del Programa : DescriptorAudienciaDAOImpl.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementación para accesar a la entidad descriptor audiencia
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
package mx.gob.segob.nsjp.dao.archivo.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.archivo.DescriptorAudienciaDAO;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.model.DescriptorAudiencia;

import org.springframework.stereotype.Repository;

/**
 * Implementación para accesar a la entidad descriptor audiencia.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
@Repository
public class DescriptorAudienciaDAOImpl
        extends
            GenericDaoHibernateImpl<DescriptorAudiencia, Long>
        implements
            DescriptorAudienciaDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DescriptorAudiencia> consultarDescriptorAudienciasPorAudiencia(
			Long audienciaId) {
		final StringBuffer qryStr = new StringBuffer();
		qryStr.append("from DescriptorAudiencia da");
		qryStr.append(" where da.audiencia.audienciaId = ");
		qryStr.append(audienciaId);
		qryStr.append(" order by da.descriptorAudienciaId");

		logger.debug("qryStr :: " + qryStr);

		return super.getHibernateTemplate().find(qryStr.toString());

	}

}
