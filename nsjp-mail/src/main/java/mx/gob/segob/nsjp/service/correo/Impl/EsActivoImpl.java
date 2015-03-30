package mx.gob.segob.nsjp.service.correo.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.persistencia.confcorreo.ConfCorreoDAO;
import mx.gob.segob.nsjp.service.correo.EsActivo;

@Service
@Transactional
public class EsActivoImpl implements EsActivo {
	
	@Autowired
	private ConfCorreoDAO confCorreoDAO;

	@Override
	public Boolean esActivo() {
		String valor = confCorreoDAO.esCorreoActivo().getValor();
		Boolean activo = new Boolean(Boolean.parseBoolean(valor));
		return activo;
	}

}
