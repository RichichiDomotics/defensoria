/**
 * 
 */
package mx.gob.segob.nsjp.service.catalogo.impl.transform;

import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.CatDistrito;

/**
 * @author AlineGS
 *
 */
public class CatDiscriminanteTransformer {

	public static CatDiscriminanteDTO transformarCatDiscriminante(
			CatDiscriminante scr) {
		
		CatDistritoDTO catDistrito=new CatDistritoDTO(scr.getDistrito().getCatDistritoId(), scr.getDistrito().getClaveDistrito(), scr.getDistrito().getNombreDist(),scr.getDistrito().getClaveRomanaDistrito());
		
		CatDiscriminanteDTO dto= new CatDiscriminanteDTO(scr.getCatDiscriminanteId(), catDistrito, scr.getClave(), scr.getNombre(), scr.getClasificacion());
		return dto;
	}

	public static CatDiscriminanteDTO transformarCatDiscriminanteSimple(
			CatDiscriminante scr) {
		CatDistritoDTO catDistrito=null;
		
		CatDiscriminanteDTO dto= new CatDiscriminanteDTO(scr.getCatDiscriminanteId(), catDistrito, scr.getClave(), scr.getNombre(), scr.getClasificacion());
		return dto;
	}

	public static CatDiscriminante transformarCatDiscriminanteDTO(
			CatDiscriminanteDTO dto) {

		CatDiscriminante disc = null;

		if (dto != null) {

			disc = new CatDiscriminante();

			if (dto.getCatDiscriminanteId() != null) {
				disc.setCatDiscriminanteId(dto.getCatDiscriminanteId());
			}

			if (dto.getDistrito() != null) {
				CatDistrito catDistrito = new CatDistrito(dto.getDistrito()
						.getCatDistritoId());
				disc.setDistrito(catDistrito);
			}

			if (dto.getClave() != null) {
				disc.setClave(dto.getClave());
			}

			if (dto.getNombre() != null) {
				disc.setNombre(dto.getNombre());
			}
			if (dto.getTipo() != null) {
				disc.setClasificacion(dto.getTipo());
			}
		}
		return disc;
	}

	public static CatDiscriminante transformarCatDiscriminanteUpdate(CatDiscriminante discBD,
			CatDiscriminanteDTO dto) {
		
		discBD.setClasificacion(dto.getTipo());
		discBD.setClave(dto.getClave());
		discBD.setNombre(dto.getNombre());
		
		return discBD;
	}


}
