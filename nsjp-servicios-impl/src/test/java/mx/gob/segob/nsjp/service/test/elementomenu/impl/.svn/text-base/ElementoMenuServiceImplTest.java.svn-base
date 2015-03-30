/**
 * 
 */
package mx.gob.segob.nsjp.service.test.elementomenu.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.menu.TipoMenu;
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.service.elementomenu.ElementoMenuService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * @author LuisMG
 * 
 */
public class ElementoMenuServiceImplTest extends
		BaseTestServicios<ElementoMenuService> {

	public void testConsultarTodosElementosMenu() {
		List<RolDTO> roles = new ArrayList<RolDTO>();
		List<TipoMenu> tipos = new ArrayList<TipoMenu>();
		List<ElementoMenuDTO> elementosMenuDTO = new ArrayList<ElementoMenuDTO>();
		roles.add(new RolDTO(1L));
		roles.add(new RolDTO(3L));
		roles.add(new RolDTO(5L));
		roles.add(new RolDTO(7L));
		roles.add(new RolDTO(56L));
		tipos.add(TipoMenu.IZQUIERDO);
		tipos.add(TipoMenu.ARRIBA);
		try {
			for (int j = 0; j < roles.size(); j++) {
				System.out.println("Rol: " + roles.get(j).getRolId());
				for (int k = 0; k < tipos.size(); k++) {
					System.out.println("Menú: " + tipos.get(k).name());
					elementosMenuDTO = service.consultarElementosMenuXRol(
							roles.get(j), tipos.get(k));
					if (elementosMenuDTO != null) {
						for (int i = 0; i < elementosMenuDTO.size(); i++) {
							imprimeArbol(elementosMenuDTO.get(i), 0);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testConsultarElementosMenuXRol() {
		RolDTO rolDTO = new RolDTO(5L);
		//5 y 7
		TipoMenu tm = TipoMenu.IZQUIERDO;
		List<ElementoMenuDTO> elementosMenuDTO = new ArrayList<ElementoMenuDTO>();
		try {
			elementosMenuDTO = service.consultarElementosMenuXRol(rolDTO, tm);
			// assertNotNull(elementoMenuDTO);
			if (elementosMenuDTO != null) {
				for (int i = 0; i < elementosMenuDTO.size(); i++) {
					imprimeArbol(elementosMenuDTO.get(i), 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void testConsultarElementosMenuObligatorios() {
		List<ElementoMenuDTO> elementosMenuDTO = new ArrayList<ElementoMenuDTO>();
		try {
			elementosMenuDTO = service.consultarElementosMenuObligatorios();
			// assertNotNull(elementoMenuDTO);
			if (elementosMenuDTO != null) {
				for (int i = 0; i < elementosMenuDTO.size(); i++) {
					imprimeArbol(elementosMenuDTO.get(i), 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testConsultarElementoMenu() {
		ElementoMenuDTO eMDTO = new ElementoMenuDTO(16L);
		RolDTO rolDTO = new RolDTO(3L);
		try {
			eMDTO = service.consultarElementoMenu(rolDTO,eMDTO);
			imprimeArbol(eMDTO, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void imprimeArbol(ElementoMenuDTO elementoMenu, int nivel) {
		if (elementoMenu != null) {
			if (nivel == 0)
				System.out
						.println("-----------------------------------------------------------------");
			for (int i = 0; i < nivel * 5; i++)
				System.out.print("*");
			System.out.println("Clave: " + elementoMenu.getElementoMenuId()
					+ " Nombre: " + elementoMenu.getcNombre());

			if (elementoMenu.getElementoMenuHijosDTO() != null
					&& !elementoMenu.getElementoMenuHijosDTO().isEmpty())
				for (int i = 0; i < elementoMenu.getElementoMenuHijosDTO()
						.size(); i++) {
					imprimeArbol(elementoMenu.getElementoMenuHijosDTO().get(i),
							nivel + 1);
				}
		}
	}

}
