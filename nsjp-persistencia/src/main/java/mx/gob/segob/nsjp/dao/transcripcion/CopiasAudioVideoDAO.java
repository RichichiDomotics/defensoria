package mx.gob.segob.nsjp.dao.transcripcion;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.CopiasAudioVideo;
import mx.gob.segob.nsjp.model.Sentencia;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Transactional(rollbackFor = Exception.class)
public interface CopiasAudioVideoDAO extends GenericDao<CopiasAudioVideo, Long> {

	Long Registrarcopias(CopiasAudioVideo Copias);
	
	
	  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	  public CopiasAudioVideo leerResponsable( long audienciaID, long solicitudID, long involucradoID );

}
