package mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoWSDTO;
import mx.gob.segob.nsjp.model.CatDistrito;

/**
 * 
 * @author rgama
 * @version 1.0
 */
public class CatDistritoWSDTOTransformer extends GenericWSDTO {

	private static final long serialVersionUID = -3462467783299506405L;


	public static CatDistritoWSDTO transformarCatDistrito(
			CatDistritoDTO scr) {
		
		CatDistritoWSDTO wsdto = new CatDistritoWSDTO();
		
		if(scr == null )
			return wsdto;
		wsdto.setCatDistritoId(scr.getCatDistritoId());
		wsdto.setClaveDistrito(scr.getClaveDistrito());
		wsdto.setNombreDist(scr.getNombreDist());
		return wsdto;
	}
	
	
	public static CatDistritoWSDTO transformarCatDistrito(
			CatDistrito scr) {
		
		CatDistritoWSDTO wsdto = new CatDistritoWSDTO();
		
		if(scr == null )
			return wsdto;
		wsdto.setCatDistritoId(scr.getCatDistritoId());
		wsdto.setClaveDistrito(scr.getClaveDistrito());
		wsdto.setNombreDist(scr.getNombreDist());
		return wsdto;
	}
	

	public static CatDistritoDTO transformarCatDistrito(
			mx.gob.segob.nsjp.ws.cliente.consultardistritos.CatDistritoWSDTO scr) {
		
		CatDistritoDTO dto = new CatDistritoDTO();		
		if(scr == null )
			return dto;
		
		dto.setCatDistritoId(scr.getCatDistritoId());
		dto.setClaveDistrito(scr.getClaveDistrito());
		dto.setNombreDist(scr.getNombreDist());
		
		return dto;
	}
}
