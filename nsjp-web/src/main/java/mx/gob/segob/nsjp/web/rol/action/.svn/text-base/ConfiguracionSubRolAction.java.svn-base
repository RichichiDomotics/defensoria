/**
 * 
 */
package mx.gob.segob.nsjp.web.rol.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.elementomenu.ElementoMenuDelegate;
import mx.gob.segob.nsjp.delegate.rol.RolDelegate;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

/**
 * @author LuisMG
 * 
 */
public class ConfiguracionSubRolAction extends GenericAction {
	private static final Logger log = Logger
			.getLogger(ConfiguracionSubRolAction.class);

	@Autowired
	public RolDelegate rolDelegate;

	@Autowired
	public ElementoMenuDelegate elementoMenuDelegate;

	public ActionForward consultarConfiguracionSubRol(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			RolDTO resp=null;
		try {
			String idRol = request.getParameter("idRol");

			if (idRol!=""){
			resp= rolDelegate.consultarConfiguracionRol(new RolDTO(Long.valueOf(idRol)));
			}
			converter.alias("lstElementosMenu", java.util.List.class);
			converter.alias("ElementoMenu", ElementoMenuDTO.class);
			converter.alias("Actuacion",ConfActividadDocumentoDTO.class);
			String xml = converter.toXML(resp.getElementosMenu());
			log.info("Elementos Menu:: " + xml);
			// mandamos la respuesta al cliente
			escribir(response, xml, null);
		} catch (NSJPNegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ActionForward guardarConfiguracionSubRol(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String lstEM = request.getParameter("lstEM");
		String idRol = request.getParameter("idRol");
		String lstAct =request.getParameter("lstAct");
		boolean resp=false;
		int i=0;
		if (idRol != "" && (lstEM != "" || lstAct != "")) {
			RolDTO rolDTO = new RolDTO(Long.valueOf(idRol));
			if (lstEM != "") {
				List<ElementoMenuDTO> lstEMDTO = new ArrayList<ElementoMenuDTO>();
				for (String s : lstEM.split(",")) {
					i = 0;
					while (i < lstEMDTO.size()
							&& lstEMDTO.get(i).getElementoMenuId() != Long
									.valueOf(s)) {
						i++;
					}
					if (i == lstEMDTO.size()) {
						lstEMDTO.add(new ElementoMenuDTO(Long.valueOf(s)));
					}
					
				}
				rolDTO.setElementosMenu(lstEMDTO);
			}
			if (lstAct!=""){
				List<ConfActividadDocumentoDTO> lstADTO = new ArrayList<ConfActividadDocumentoDTO>();
				for (String s : lstAct.split(",")) {
					i = 0;
					while (i < lstADTO.size()
							&& lstADTO.get(i).getConfActividadDocumentoId() != Long
									.valueOf(s)) {
						i++;
					}
					if (i == lstADTO.size()) {
						lstADTO.add(new ConfActividadDocumentoDTO(Long.valueOf(s)));
					}
					
				}
				rolDTO.setConfActividadDocumentoDTO(lstADTO);
			}
			try{
				resp=rolDelegate.actualizarConfiguracionRol(rolDTO);
				converter.alias("resp", Boolean.class);
				String xml = converter.toXML(resp);
				log.info("Elementos Menu:: " + xml);
				// mandamos la respuesta al cliente
				escribir(response, xml, null);
			}catch (NSJPNegocioException e){
				e.printStackTrace();
			}
			
		}

		return null;

	}

}
