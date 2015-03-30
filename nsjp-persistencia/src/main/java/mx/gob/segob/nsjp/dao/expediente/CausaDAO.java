package mx.gob.segob.nsjp.dao.expediente;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Causa;

public interface CausaDAO extends GenericDao<Causa, Long>{
	
	Causa buscarCausaByCCausaPenalIdPJ(String cCausaPenalIdPJ);

}
