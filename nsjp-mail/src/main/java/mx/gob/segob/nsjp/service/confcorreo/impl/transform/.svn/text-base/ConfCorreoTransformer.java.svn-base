package mx.gob.segob.nsjp.service.confcorreo.impl.transform;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.confCorreo.ConfCorreoDTO;
import mx.gob.segob.nsjp.model.ConfCorreo;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;

import org.apache.log4j.Logger;

public class ConfCorreoTransformer {

	private static final Logger log = Logger
			.getLogger(ConfCorreoTransformer.class);

	private ConfCorreoTransformer() {
	}

	public static ConfCorreoDTO Transformar(ConfCorreo confCorreo) {
		ConfCorreoDTO confCorreoDTO = new ConfCorreoDTO();

		if (confCorreo != null) {
			if (confCorreo.getConfCorreoId() != null)
				confCorreoDTO.setConfCorreoId(confCorreo.getConfCorreoId());
			if (confCorreo.getValor() != null)
				confCorreoDTO.setValor(confCorreo.getValor());
			if (confCorreo.getllave() != null)
				confCorreoDTO.setClave(confCorreo.getllave());
			if (confCorreo.getTipoObjeto() != null)
				confCorreoDTO.setTipoObjeto(confCorreoDTO.getTipoObjeto());

		}

		return confCorreoDTO;
	}

	public static ConfCorreo Transformar(ConfCorreoDTO confCorreoDTO) {
		ConfCorreo confCorreo = new ConfCorreo();
		if (confCorreoDTO != null) {
			if (confCorreoDTO.getConfCorreoId() != null)
				confCorreo.setConfCorreoId(confCorreoDTO.getConfCorreoId());
			if (confCorreoDTO.getValor() != null)
				confCorreo.setValor(confCorreoDTO.getValor());
			if (confCorreoDTO.getLlave() != null)
				confCorreo.setllave(confCorreoDTO.getLlave());
			if (confCorreoDTO.getTipoObjeto() != null)
				confCorreo.setTipoObjeto(confCorreoDTO.getTipoObjeto());

		}
		return confCorreo;
	}

}
