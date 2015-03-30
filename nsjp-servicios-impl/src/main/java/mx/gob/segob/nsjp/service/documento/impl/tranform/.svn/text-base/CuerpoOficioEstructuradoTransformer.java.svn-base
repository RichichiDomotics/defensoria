/**
 * 
 */
package mx.gob.segob.nsjp.service.documento.impl.tranform;

import mx.gob.segob.nsjp.dto.documento.CuerpoOficioEstructuradoDTO;
import mx.gob.segob.nsjp.model.CuerpoOficioEstructurado;
import mx.gob.segob.nsjp.model.OficioEstructurado;

/**
 * @author adrian
 * 
 */
public class CuerpoOficioEstructuradoTransformer {

	public static CuerpoOficioEstructuradoDTO transformarCuerpo(
			CuerpoOficioEstructurado cuerpo) {
		
		if(cuerpo==null){
			return null;
		}
		
		CuerpoOficioEstructuradoDTO dto = new CuerpoOficioEstructuradoDTO();
		
		dto.setCuerpoOficioEstructuradoId(cuerpo.getCuerpoOficioEstructuradoId());
		dto.setNumeracion(cuerpo.getNumeracion());
		dto.setSecuencia(cuerpo.getSecuencia());
		dto.setTexto(cuerpo.getTexto());
		
		if(cuerpo.getIndiceEstructurado()!=null)
			dto.setIndiceEstructurado(IndiceEstructuradoTransformer.transformarIndiceEstructurado(cuerpo.getIndiceEstructurado()));

		return dto;
	}
	
	/**
	 * Transforma todo menos el texto para que en vista no sea tan pesado
	 * @param cuerp
	 * @return
	 */
	public static CuerpoOficioEstructuradoDTO transformarCuerpoSimple(
			CuerpoOficioEstructurado cuerp) {
		CuerpoOficioEstructuradoDTO dto = new CuerpoOficioEstructuradoDTO();
		if (cuerp.getCuerpoOficioEstructuradoId() != null)
			dto.setCuerpoOficioEstructuradoId(cuerp
					.getCuerpoOficioEstructuradoId());
		if (cuerp.getNumeracion() != null)
			dto.setNumeracion(cuerp.getNumeracion());
		if (cuerp.getSecuencia() != null)
			dto.setSecuencia(cuerp.getSecuencia());
		dto.setTexto(cuerp.getTexto());
		if(cuerp.getIndiceEstructurado()!=null)
			dto.setIndiceEstructurado(IndiceEstructuradoTransformer.transformarIndiceEstructurado(cuerp.getIndiceEstructurado()));

		return dto;
	}

	public static CuerpoOficioEstructurado transformarCuerpoDTO(
			CuerpoOficioEstructuradoDTO cuerp) {
		CuerpoOficioEstructurado ent = new CuerpoOficioEstructurado();
		if (cuerp.getCuerpoOficioEstructuradoId() != null)
			ent.setCuerpoOficioEstructuradoId(cuerp
					.getCuerpoOficioEstructuradoId());
		if (cuerp.getNumeracion() != null)
			ent.setNumeracion(cuerp.getNumeracion());
		if (cuerp.getSecuencia() != null)
			ent.setSecuencia(cuerp.getSecuencia());
		ent.setTexto(cuerp.getTexto());
		if(cuerp.getIndiceEstructurado()!=null)
			ent.setIndiceEstructurado(IndiceEstructuradoTransformer.transformarIndiceEstructurado(cuerp.getIndiceEstructurado()));
		if(cuerp.getInteresa()!=null)
			ent.setInteresa(cuerp.getInteresa());
		if(cuerp.getOficioEstructuradoDTO()!=null)
			ent.setOficioEstructurado(new OficioEstructurado(cuerp.getOficioEstructuradoDTO().getOficioEstructuradoId()));

		return ent;
	}

	public static void transformarCuerpoUpdate(
			CuerpoOficioEstructurado cuerpBD, CuerpoOficioEstructurado cuerpVista) {
		if(cuerpBD == null || cuerpVista == null )
			return; 
		
		if (cuerpVista.getCuerpoOficioEstructuradoId() != null)
			cuerpBD.setCuerpoOficioEstructuradoId(cuerpVista
					.getCuerpoOficioEstructuradoId());
		if (cuerpVista.getNumeracion() != null)
			cuerpBD.setNumeracion(cuerpVista.getNumeracion());
		if (cuerpVista.getSecuencia() != null)
			cuerpBD.setSecuencia(cuerpVista.getSecuencia());
		//EL Texto no se cambia, ya que en vista se manda el mismo para todos
//		cuerpBD.setTexto(cuerpVista.getTexto());
	}
}
