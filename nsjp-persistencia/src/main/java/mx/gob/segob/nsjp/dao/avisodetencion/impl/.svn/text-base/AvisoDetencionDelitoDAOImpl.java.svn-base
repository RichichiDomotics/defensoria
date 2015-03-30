package mx.gob.segob.nsjp.dao.avisodetencion.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.avisodetencion.AvisoDetencionDelitoDAO;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.model.AvisoDetencionDelito;
import mx.gob.segob.nsjp.model.AvisoDetencionDelitoId;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AvisoDetencionDelitoDAOImpl extends GenericDaoHibernateImpl<AvisoDetencionDelito, AvisoDetencionDelitoId>
		implements AvisoDetencionDelitoDAO {
	
	public AvisoDetencionDelito consultaAvisoDetencionDelito(Long avisoDetencionID) throws NSJPNegocioException{
		final StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append(" FROM AvisoDetencionDelito di ")
		.append(" WHERE di.avisoDetencion ="+avisoDetencionID);
		Query query = super.getSession().createQuery(hqlQuery.toString());
		return (AvisoDetencionDelito) query.uniqueResult();
	}
	
	
}
