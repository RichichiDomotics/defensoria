package mx.gob.segob.nsjp.service.correo.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.persona.CorreoElectronicoDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.correo.CorreoDTO;
import mx.gob.segob.nsjp.dto.destinatario.DestinatariosDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.CorreoElectronico;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.persistencia.correo.CorreoDAO;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.correo.ObtenerCorreos;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;

@Service
public class ObtenerCorreosImpl implements ObtenerCorreos {

	@Autowired
	private AudienciaDAO audienciaDAO;
	@Autowired
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private CorreoElectronicoDAO correoElectronicoDAO;
	@Autowired
	private CorreoDAO correoDAO;
	@Autowired
	private InvolucradoDAO involucradoDAO;

	@Override
	public CorreoDTO consultarCorreosByAudiencia(Long audienciaId) {

		CorreoDTO correoDTO = new CorreoDTO();
		DestinatariosDTO destinatariosDTO = new DestinatariosDTO();
		List<CorreoElectronico> correoElectronicos = correoDAO.ConsultarCorreosFuncionariosEInvolucradosPorAudienciaId(audienciaId);
		if (!correoElectronicos.isEmpty()) {
			try {
				destinatariosDTO.setPara(correoElectronicos);
			} catch (AddressException e) {
				System.out.println("no se pudieron agregar los destinatarios");
				e.printStackTrace();
			}

		}

		correoDTO.setDestinatarios(destinatariosDTO);
	
		

		return correoDTO;
	}

}
