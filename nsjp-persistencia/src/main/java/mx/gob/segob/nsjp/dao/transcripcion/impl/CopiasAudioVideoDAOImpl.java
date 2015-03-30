package mx.gob.segob.nsjp.dao.transcripcion.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.transcripcion.CopiasAudioVideoDAO;
import mx.gob.segob.nsjp.model.CopiasAudioVideo;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

@Repository
public class CopiasAudioVideoDAOImpl extends GenericDaoHibernateImpl<CopiasAudioVideo, Long> implements CopiasAudioVideoDAO {
	
	@Override
	public Long Registrarcopias(CopiasAudioVideo Copias) {
		Session session = super.getSession();
		session.save(Copias);
		return Copias.getId();
	}

	
	@Override
	public CopiasAudioVideo leerResponsable(long audienciaID, long solicitudID, long involucradoID) {
		List<CopiasAudioVideo> copias = new ArrayList<CopiasAudioVideo>();
		Session session = getSession();
		
		StringBuffer queryString = new StringBuffer();
		//queryString.append("FROM CopiasAudioVideo");
		queryString.append("SELECT C FROM CopiasAudioVideo C WHERE ");
		queryString.append("C.audiencia_id = " + audienciaID + " AND ");
		queryString.append("C.involucrado_id = "+ involucradoID + " AND ");
		queryString.append("C.solicitud_id = "+ solicitudID);

		Query query = session.createQuery(queryString.toString());
		
		copias = query.list();

		return copias.get(0);
	}
	
	
	
}
