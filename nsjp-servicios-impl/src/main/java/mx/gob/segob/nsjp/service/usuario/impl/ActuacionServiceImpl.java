/**
 * 
 */
package mx.gob.segob.nsjp.service.usuario.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.usuario.RolDAO;
import mx.gob.segob.nsjp.dto.ActuacionDTO;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.service.usuario.ActuacionService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ActuacionTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.RolTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

/**
 * @author LuisMG
 * 
 */
@Service
@Transactional
public class ActuacionServiceImpl implements ActuacionService {

	@Autowired
	private ConfActividadDocumentoDAO cadDAO;

	@Autowired
	private RolDAO rolDAO;
	
	@Autowired
	private ValorDAO valorDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.usuario.ActuacionService#consultarActuacionPorRol
	 * (mx.gob.segob.nsjp.dto.usuario.RolDTO)
	 */
	@Override
	public List<ActuacionDTO> consultarActuacionPorRol(RolDTO rolDTO)
			throws NSJPNegocioException {
		List<ActuacionDTO> resp = null;
		if (rolDTO != null) {
			try {
				// Consulto el RolPara Obtener la Jerarquia
				Rol rol = rolDAO.consultarRol(RolTransformer.transformarMinimo(rolDTO));
				rolDTO = RolTransformer
						.transformarMedio(rol);
				resp = ActuacionTransformer.transformarCAD(cadDAO
						.consultarActuacionesPorRol(RolTransformer
								.transformarMinimo(rolDTO)));
				for (ActuacionDTO aDTO: resp){
					aDTO.setGrupoActividad(ValorTransformer.transformar(valorDAO.read(aDTO.getGrupoActividad().getIdCampo())));
				}
				if (rolDTO.getConfActividadDocumentoDTO() != null
						&& resp != null) {
					int j = 0;
					for (ConfActividadDocumentoDTO CAD : rolDTO.getConfActividadDocumentoDTO()) {
						j = 0;
						while (j < resp.size()
								&& resp.get(j).getConfActividadDocumentoId() != CAD.getConfActividadDocumentoId()) {
							j++;
						}
						if (j < resp.size()) {
							resp.get(j).setEsSeleccionado(true);
						}
					}
				}
			} catch (NSJPNegocioException e) {
				e.printStackTrace();
			}
		}

		return resp;
	}

}
