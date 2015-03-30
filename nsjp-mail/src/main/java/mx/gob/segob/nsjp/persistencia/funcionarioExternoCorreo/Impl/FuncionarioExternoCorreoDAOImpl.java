package mx.gob.segob.nsjp.persistencia.funcionarioExternoCorreo.Impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudAudienciaDAO;
import mx.gob.segob.nsjp.model.CorreoElectronico;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.SolicitudAudiencia;
import mx.gob.segob.nsjp.persistencia.funcionarioExternoCorreo.FuncionarioExternoCorreoDAO;
import mx.gob.segob.nsjp.service.funcionario.impl.ActualizarFuncionarioServiceImpl;

@Repository
public class FuncionarioExternoCorreoDAOImpl extends
GenericDaoHibernateImpl<CorreoElectronico, Long> implements FuncionarioExternoCorreoDAO{

	private final static Logger logger = Logger.getLogger(FuncionarioExternoCorreoDAOImpl.class);
	
	@Override
	public FuncionarioExterno consultarCorreoFuncionarioExternosPorAudienciaId(
			Long cveFuncionarioInstExt) {

		final StringBuffer queryString = new StringBuffer();
		
		queryString.append("FROM FuncionarioExterno WHERE funcionarioExternoId =  ")
		    .append("(SELECT MAX(fe.funcionarioExternoId) FROM FuncionarioExterno fe")
			.append(" WHERE fe.cveFuncionarioInstExt="+cveFuncionarioInstExt).append(")");
	
		logger.info("Query:::"+ queryString);
		Query query = super.getSession().createQuery(queryString.toString());
		
		return (FuncionarioExterno) query.uniqueResult();
	}


}
