package mx.gob.segob.nsjp.dao.expediente.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.CausaDAO;
import mx.gob.segob.nsjp.model.Causa;
import mx.gob.segob.nsjp.model.ConfInstitucion;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CausaDAOImpl extends GenericDaoHibernateImpl<Causa, Long> implements CausaDAO {

	@Override
	public Long create(Causa causa) {
		return super.create(causa);
	}

	@Override
	public void createAll(List<Causa> lista) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdate(Causa newInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void merge(Causa newInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Causa read(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Causa transientObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Causa persistentObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(List<Causa> list2Delete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Long[] id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Long> findAllId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdateAll(List<Causa> list2Update) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ConfInstitucion consultarInsitucionActual() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Causa> findAll(String order, boolean asc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Causa buscarCausaByCCausaPenalIdPJ(String cCausaPenalIdPJ) {
		final StringBuffer qryStr = new StringBuffer();
		qryStr.append("select c from Causa as c");
		qryStr.append(" where c.cCausaPenalIdPJ like '");
		qryStr.append(cCausaPenalIdPJ);
		qryStr.append("' ");
		logger.debug("qryStr :: " + qryStr);
		final Query qry = super.getSession().createQuery(qryStr.toString());
		return (Causa) qry.uniqueResult();
	}

	
}
