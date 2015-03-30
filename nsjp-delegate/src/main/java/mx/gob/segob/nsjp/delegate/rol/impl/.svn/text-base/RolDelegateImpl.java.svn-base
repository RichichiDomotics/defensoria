/**
 * 
 */
package mx.gob.segob.nsjp.delegate.rol.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.rol.RolDelegate;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.rol.FiltroRolesDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.service.usuario.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LuisMG
 * 
 */
@Service
@Transactional
public class RolDelegateImpl implements RolDelegate {

	@Autowired
	private RolService rolService;

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.delegate.rol.RolDelegate#consultarRoles()
	 */
	@Override
	public List<RolDTO> consultarRoles(FiltroRolesDTO fRolesDTO)
			throws NSJPNegocioException {
		// TODO Auto-generated method stub
		return rolService.consultarRoles(fRolesDTO);
	}

	@Override
	public List<RolDTO> consultarRolesPadre(FiltroRolesDTO fRolesDTO)
			throws NSJPNegocioException {
		// TODO Auto-generated method stub
		return rolService.consultarRolesPadre(fRolesDTO);
	}

	@Override
	public List<RolDTO> consultarSubRoles(RolDTO rolDTO)
			throws NSJPNegocioException {
		return rolService.consultarSubRoles(rolDTO);
	}

	@Override
	public RolDTO consultarRolCompleto(RolDTO rol) throws NSJPNegocioException {
		return rolService.consultarRolCompleto(rol);
	}

	@Override
	public RolDTO consultarRol(RolDTO rol) throws NSJPNegocioException {
		return rolService.consultarRol(rol);
	}

	@Override
	public RolDTO consultarRolMinimo(RolDTO rol) throws NSJPNegocioException {
		return rolService.consultarRolMinimo(rol);
	}

	@Override
	public boolean actualizarModulosRol(RolDTO rolDTO)
			throws NSJPNegocioException {
		boolean resp = false;
		resp = rolService.actualizaModulosRol(rolDTO);

		return resp;

	}

	@Override
	public boolean actualizarConfiguracionRol(RolDTO rolDTO)
			throws NSJPNegocioException {
		boolean resp = false;
		List<ElementoMenuDTO> lstEMs = rolDTO.getElementosMenu();
		
		List<ConfActividadDocumentoDTO> lstCADs = rolDTO
				.getConfActividadDocumentoDTO();
		rolDTO = rolService.consultarRol(rolDTO);
		// Validación de existencia del rol que se quiere configurar
		if (rolDTO != null) {
			// Validación para diferenciar entre roles padre y roles hijo
			// Es rol padre
			if (rolDTO.getRolPadre() == null) {
				// TODO reglas para configuración de un rol padre
				// Es rol hijo
			} else {
				// Para agregar los menús obligatorios al rol
				rolDTO.setElementosMenu(lstEMs);
				rolDTO.setConfActividadDocumentoDTO(lstCADs);
				resp = rolService.actualizarConfiguracionRol(rolDTO);
			}
		}
		return resp;

	}
	


	@Override
	public RolDTO consultarConfiguracionRol(RolDTO rolDTO)
			throws NSJPNegocioException {
		RolDTO resp = null;
		resp = rolService.consultarConfiguracionRol(rolDTO);
		return resp;
	}

	@Override
	public boolean crearRol(RolDTO rolDTO) throws NSJPNegocioException {

		return rolService.crearRol(rolDTO);
	}
	
	@Override
	public boolean modificarRol (RolDTO rolDTO) throws NSJPNegocioException{
		return rolService.modificarRol(rolDTO);
	}

	public Long crearSubRol(RolDTO rolDTO) throws NSJPNegocioException {
		return rolService.crearSubRol(rolDTO);
	}

}
