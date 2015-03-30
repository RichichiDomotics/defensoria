package mx.gob.segob.nsjp.service.medida.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.medida.MedidaDAO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.model.Medida;
import mx.gob.segob.nsjp.service.medida.CambiarEstatusMedidaService;
import mx.gob.segob.nsjp.service.medidascautelares.impl.transform.MedidaCautelarTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CambiarEstatusMedidaServiceImpl implements
		CambiarEstatusMedidaService {

	private final static Logger logger = Logger.getLogger(MedidaCautelarTransformer.class);
	
	@Autowired 
	MedidaDAO medidaDAO;
	@Override
	public MedidaCautelarDTO cambiarEstatusMedida(Long idMedida, Long idEstatus)
			throws NSJPNegocioException {
		logger.debug("idMedida : " + idMedida + " idEstatus : " + idEstatus);
		MedidaCautelarDTO medidaCautelarDTO = null;
		medidaDAO.cambiarEstatusMedida(idMedida, idEstatus);
		
		Medida medida = medidaDAO.read(idMedida);
		
		if (medida != null) {
			medidaCautelarDTO = new MedidaCautelarDTO();
			medidaCautelarDTO.setDocumentoId(medida.getDocumentoId());
			logger.debug("DocumentoId : : " + medidaCautelarDTO.getDocumentoId());
			if (medida.getConfInstitucion() != null
					&& medida.getConfInstitucion().getConfInstitucionId() != null) {
				medidaCautelarDTO.setConfInstitucion(new ConfInstitucionDTO(
						medida.getConfInstitucion().getConfInstitucionId()));
				logger.debug("ConfInstitucion Id : : " + medidaCautelarDTO.getConfInstitucion().getConfInstitucionId());
			}
			logger.debug("FolioDocumentoId : " + medida.getFolioDocumento());
			medidaCautelarDTO.setFolioDocumento(medida.getFolioDocumento());
		}
		return medidaCautelarDTO;
	}
}
