package mx.gob.segob.nsjp.delegate.correo.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.exceptions.CorreoNSJPException;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.persona.CorreoElectronicoDAO;
import mx.gob.segob.nsjp.delegate.correo.CorreoDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.correo.CorreoDTO;
import mx.gob.segob.nsjp.dto.destinatario.DestinatariosDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.CorreoElectronico;
import mx.gob.segob.nsjp.model.Elemento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.correo.EnviarCorreo;
import mx.gob.segob.nsjp.service.correo.ObtenerCorreos;
import mx.gob.segob.nsjp.service.correo.OrdenarRecipientes;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;

@Service
public class CorreoDelegateImpl implements CorreoDelegate {

	@Autowired
	private EnviarCorreo enviarCorreo;

	@Override
	public void enviarCorreoPorAudiencia(Long idAudiencia) {
		enviarCorreo.enviarCorreoPorAudiencia(idAudiencia);

	}

}
