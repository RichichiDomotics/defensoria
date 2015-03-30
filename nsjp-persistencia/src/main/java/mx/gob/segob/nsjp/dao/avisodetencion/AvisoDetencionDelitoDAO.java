package mx.gob.segob.nsjp.dao.avisodetencion;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.AvisoDetencionDelito;
import mx.gob.segob.nsjp.model.AvisoDetencionDelitoId;

public interface AvisoDetencionDelitoDAO extends GenericDao<AvisoDetencionDelito, AvisoDetencionDelitoId>{

	
	/**
	 * Consulta los avisos de detencion por un id de AvisoDetencionId
	 * @param AvisoDetencionId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public AvisoDetencionDelito consultaAvisoDetencionDelito(Long avisoDetencionID) throws NSJPNegocioException;
	
}
