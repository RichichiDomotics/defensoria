/**
 * Nombre del Programa  : RolServiceImpl.java
 * Autor                : Daniel Jiménez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 27 Jul 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Servicio que administra la información de Roles
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.usuario.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.usuario.RolDAO;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.rol.FiltroRolesDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.service.elementomenu.ElementoMenuService;
import mx.gob.segob.nsjp.service.usuario.RolService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.RolTransformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio que administra la información de Roles
 * 
 * @version 1.0
 * @author Daniel Jiménez
 * 
 */
@Service
@Transactional
public class RolServiceImpl implements RolService {

	public static final Logger logger = Logger.getLogger(RolServiceImpl.class);

	@Autowired
	private RolDAO rolDAO;
	
	@Autowired
	private ElementoMenuService eMService;
	
	@Autowired
	private ConfActividadDocumentoDAO cadDAO;

	@Override
	public List<RolDTO> consultarRoles(FiltroRolesDTO filtroRolesDTO)
			throws NSJPNegocioException {

		if (filtroRolesDTO.getConfInstitucionId() == null
				|| filtroRolesDTO.getConfInstitucionId() == 0L){
			ConfInstitucion institucionActual = rolDAO.consultarInsitucionActual();
			filtroRolesDTO.setConfInstitucionId(institucionActual
					.getConfInstitucionId());
		}
		List<Rol> result = rolDAO.consultarRoles(filtroRolesDTO);
		List<RolDTO> roles = new LinkedList<RolDTO>();
		for (Rol rol : result) {
				roles.add(RolTransformer.transformarMinimo(rol));
		}
		return roles;
	}
	
	@Override
	public List<RolDTO> consultarRolesPadre (FiltroRolesDTO filtroRolesDTO) throws NSJPNegocioException{
		ConfInstitucion institucionActual = rolDAO.consultarInsitucionActual();
		filtroRolesDTO.setConfInstitucionId(institucionActual
				.getConfInstitucionId());
		List<Rol> result = rolDAO.consultarRoles(filtroRolesDTO);
		List<RolDTO> roles = new LinkedList<RolDTO>();
		for (Rol rol : result) {
			if (rol.getRolPadre() == null) {
				roles.add(RolTransformer.transformarMinimo(rol));
			}
		}
		return roles;
	}

	@Override
	public void actualizarRol(RolDTO rolDTO) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("SERVICIO PARA ACTUALIZAR LA INFORMACION DE UN ROL");

		if (rolDTO.getRolId() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		

	}

	/**
	 * Método que consulta la información de un rol, módulos y funciones
	 * asociados - LOMG
	 * 
	 * @param rol
	 * @return
	 * @throws NSJPNegocioException
	 */
	public RolDTO consultarRolCompleto(RolDTO rolDTO)
			throws NSJPNegocioException {
		RolDTO rolResp = null;
		if (rolDTO != null) {
			rolResp = new RolDTO();
			rolResp = RolTransformer.transformar(rolDAO
					.consultarRol(RolTransformer.transformar(rolDTO)));
		}
		return rolResp;
	}

	@Override
	public List<RolDTO> consultarSubRoles(RolDTO rolDTO)
			throws NSJPNegocioException {
		List<RolDTO> resp = null;
		List<Rol> subRoles = null;
		subRoles = rolDAO.consultarSubRoles(RolTransformer.transformar(rolDTO));
		if (subRoles != null) {
			resp = new ArrayList<RolDTO>();
			for (int i = 0; i < subRoles.size(); i++) {
				resp.add(RolTransformer.transformarMinimo(subRoles.get(i)));
			}
		}
		return resp;
	}

	@Override
	public RolDTO consultarRol(RolDTO rolDTO) throws NSJPNegocioException {
		RolDTO rolResp = null;
		if (rolDTO != null) {
			rolResp = new RolDTO();
			rolResp = RolTransformer.transformarMedio(rolDAO
					.consultarRol(RolTransformer.transformarMedio(rolDTO)));

		}
		return rolResp;
	}

	/**
	 * Método que consulta, ÚNICAMENTE, la información de un rol - LOMG
	 * 
	 * @param rol
	 * @return
	 * @throws NSJPNegocioException
	 */
	public RolDTO consultarRolMinimo(RolDTO rolDTO) throws NSJPNegocioException {
		RolDTO rolResp = null;
		if (rolDTO != null) {
			rolResp = new RolDTO();
			rolResp = RolTransformer.transformarMinimo(rolDAO
					.consultarRol(RolTransformer.transformarMinimo(rolDTO)));
		}
		return rolResp;
	}

	@Override
	public boolean actualizaModulosRol(RolDTO rolDTO)
			throws NSJPNegocioException {
		boolean resp = false;
		if (rolDTO != null) {
			// rolDAO.merge(RolTransformer.transformar(rolDTO));
			rolDAO.saveOrUpdate(RolTransformer.transformarMedio(rolDTO));
			resp = true;
		}

		return resp;
	}

	@Override
	public boolean actualizarConfiguracionRol(RolDTO rolDTO)
			throws NSJPNegocioException {
		boolean resp = false;
		List<ElementoMenuDTO> lstEMOs =eMService.consultarElementosMenuObligatorios();
		int i=0;
		if (rolDTO != null) {
			// rolDAO.merge(RolTransformer.transformar(rolDTO));
			if (lstEMOs != null) {
				List<ElementoMenuDTO> lstIds = obtieneIds(lstEMOs);
				lstEMOs= new ArrayList<ElementoMenuDTO> ();
				for (ElementoMenuDTO eM: lstIds){
					i=0;
					while (i<rolDTO.getElementosMenu().size() && rolDTO.getElementosMenu().get(i).getElementoMenuId()!=eM.getElementoMenuId()){
						i++;
					}
					if (i==rolDTO.getElementosMenu().size()){
						lstEMOs.add(eM);
					}
				}
				if (lstEMOs.size()>0){
					rolDTO.getElementosMenu().addAll(lstEMOs);
				}
				
			}
			Rol rol = RolTransformer.transformarMedio(rolDTO);
			if (rolDTO.getConfActividadDocumentoDTO()!=null){
				Set<ConfActividadDocumento> stCADs = new HashSet<ConfActividadDocumento>();
				for (ConfActividadDocumentoDTO cad : rolDTO.getConfActividadDocumentoDTO()){
					stCADs.add(cadDAO.read(cad.getConfActividadDocumentoId()));
				}
				rol.setConfActividadDocumentos(stCADs);
			}
	
			rolDAO.merge(rol);
			resp = true;
		}
		

		return resp;
	}
	
	List<ElementoMenuDTO> obtieneIds (List<ElementoMenuDTO> lstEMs){
		List<ElementoMenuDTO> lstResp=null;
		if (lstEMs!=null){
			lstResp=new ArrayList<ElementoMenuDTO> ();
			for (ElementoMenuDTO eM : lstEMs){
				lstResp.add(new ElementoMenuDTO (eM.getElementoMenuId()));
				if (eM.getElementoMenuHijosDTO()!=null){
					lstResp.addAll(obtieneIds(eM.getElementoMenuHijosDTO()));
				}
			}
		}
		
		return lstResp;
	}

	@Override
	public RolDTO consultarConfiguracionRol(RolDTO rolDTO)
			throws NSJPNegocioException {
		RolDTO resp = null;
		if (rolDTO != null) {
			resp = RolTransformer.transformarMedio(rolDAO
					.consultarRol(RolTransformer.transformar(rolDTO)));
		}
		return resp;
	}

	public boolean crearRol(RolDTO rolDTO) throws NSJPNegocioException {
		boolean resp = false;
		if (rolDTO != null) {
			rolDAO.create(RolTransformer.transformarMedio(rolDTO));
			resp = true;
		}
		return resp;
	}
	
	public boolean modificarRol (RolDTO rolDTO) throws NSJPNegocioException{
		boolean resp= false;
		if (rolDTO !=null){
			Rol rol = RolTransformer.transformarMedio(rolDTO);
			if (rolDTO.getConfActividadDocumentoDTO()!=null){
				Set<ConfActividadDocumento> stCADs = new HashSet<ConfActividadDocumento>();
				for (ConfActividadDocumentoDTO cad : rolDTO.getConfActividadDocumentoDTO()){
					stCADs.add(cadDAO.read(cad.getConfActividadDocumentoId()));
				}
				rol.setConfActividadDocumentos(stCADs);
			}
			rolDAO.merge(rol);
			resp=true;
		}
		return resp;
	}
	
	public Long crearSubRol (RolDTO rolDTO) throws NSJPNegocioException{
		Long resp = new Long(-1L);
		if (rolDTO != null) {
			resp= rolDAO.create(RolTransformer.transformarMedio(rolDTO));
		}
		return resp;
		
	}

}
