package mx.gob.segob.nsjp.delegate.correo.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.nsjp.delegate.correo.EsActivoDelegate;
import mx.gob.segob.nsjp.persistencia.confcorreo.ConfCorreoDAO;
import mx.gob.segob.nsjp.service.correo.EsActivo;

@Service
public class EsActivoDelegateImpl implements EsActivoDelegate {
	@Autowired
	private EsActivo activo;

	@Override
	public Boolean esActivo() {
		
		return activo.esActivo();
	}

}
