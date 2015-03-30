/**
 * Enable JC. Permite cargar campos vacios o null con valores concretos.
 */
package mx.gob.segob.nsjp.service.utilerias.enable;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO;
import mx.gob.segob.nsjp.dao.relacion.RelacionDAO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Organizacion;
import mx.gob.segob.nsjp.model.Relacion;

public class ConstructorCamposFaltantes {

	/**
	 * Enable JC. Mostrar el nombre de victimas morales.
	 * Dado que las victimas morales no tienen nombres demograficos asociados, se debe crear un
	 * nombre demografico y llenarlo con el nombre de la Organizacion.
	 * La relacion victima-organizacion esta dada en la tabla Relacion.
	 */
	public static void crearNombresDemograficosParaDTO(RelacionDAO relacionDAO, OrganizacionDAO organizacionDAO,
			Involucrado inv, InvolucradoDTO invDTO){
		if(inv.getNombreDemograficos() == null || inv.getNombreDemograficos().size() == 0){
			if(inv.getRelacionsForSujetoId() != null && inv.getRelacionsForSujetoId().size() > 0){
				List<NombreDemograficoDTO> nombresDemograficoDTO = new ArrayList<NombreDemograficoDTO>();
				Relacion[] relaciones = inv.getRelacionsForSujetoId().toArray(new Relacion[0]);
				for(Relacion rel : relaciones){
					Relacion relBD = relacionDAO.read(rel.getRelacionId());
					if(relBD != null && relBD.getCatRelacion() != null && relBD.getCatRelacion().getCatRelacionId() != null
							&& relBD.getCatRelacion().getCatRelacionId() == 59){//Relacion de Elemento-Organizacion
						Organizacion org = organizacionDAO.read(relBD.getElementoByComplementoId().getElementoId());
						if(org != null && org.getNombreOrganizacion() != null){
							NombreDemograficoDTO nombreDTO = new NombreDemograficoDTO(null, "", org.getNombreOrganizacion(), "");
							nombresDemograficoDTO.add(nombreDTO);
						}else{
							NombreDemograficoDTO nombreDTO = new NombreDemograficoDTO(null, "", "Anonimo", "");
							nombresDemograficoDTO.add(nombreDTO);
						}
					}
				}
				invDTO.setNombresDemograficoDTO(nombresDemograficoDTO);
			}
		}
	}

	public static String crearNombreDeInvolucradoSinNombreDemografico(RelacionDAO relacionDAO, OrganizacionDAO organizacionDAO,
			Involucrado inv){
		if(inv.getNombreDemograficos() == null || inv.getNombreDemograficos().size() == 0){
			if(inv.getRelacionsForSujetoId() != null && inv.getRelacionsForSujetoId().size() > 0){
				Relacion[] relaciones = inv.getRelacionsForSujetoId().toArray(new Relacion[0]);
				for(Relacion rel : relaciones){
					Relacion relBD = relacionDAO.read(rel.getRelacionId());
					if(relBD != null && relBD.getCatRelacion() != null && relBD.getCatRelacion().getCatRelacionId() != null
							&& relBD.getCatRelacion().getCatRelacionId() == 59){//Relacion de Elemento-Organizacion
						Organizacion org = organizacionDAO.read(relBD.getElementoByComplementoId().getElementoId());
						if(org != null && org.getNombreOrganizacion() != null){
							return org.getNombreOrganizacion();
						}
					}
				}
			}
		}
		return "Anonimo";
	}
}
