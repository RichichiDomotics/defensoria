/**
 * 
 */
package mx.gob.segob.nsjp.service.usuario.impl.transformer;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.ActuacionDTO;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.service.forma.impl.transform.FormaTransformer;

/**
 * @author LuisMG
 *
 */
public class ActuacionTransformer {
	
	public static ActuacionDTO transformar (ConfActividadDocumento cad){
		ActuacionDTO resp =null;
		if (cad!=null){
			resp= new ActuacionDTO();
			  resp.setConfActividadDocumentoId(cad.getConfActividadDocumentoId());
		        resp.setMuestraEnCombo(cad.getMuestraEnCombo());
		        resp.setAccion(cad.getAccion());
		        resp.setGrupoActividad(new ValorDTO (Long.valueOf(cad.getGrupoActividad())));
		        resp.setNombreActividad(cad.getTipoActividad().getValor());
		        if(cad.getTipoDocumento()!=null)
		        	resp.setNombreDocumento(cad.getTipoDocumento().getValor());
		        resp.setTipoActividadId(cad.getTipoActividad().getValorId());
		        if(cad.getTipoDocumento()!=null)
		        	resp.setTipoDocumentoId(cad.getTipoDocumento().getValorId());
		        
		        if(cad.getEstadoCambioExpediente()!= null)
		        	resp.setEstadoCambioExpediente(new ValorDTO(
		        			cad.getEstadoCambioExpediente().getValorId(), 
		        			cad.getEstadoCambioExpediente().getValor()));
		        
		        if(cad.getForma()!= null)
		        	resp.setForma(FormaTransformer.transformarForma(cad.getForma()));
		        
		        resp.setUsaEditor(cad.getUsaEditor());
		}
		return resp;
	}
	public static ConfActividadDocumentoDTO transformar (ActuacionDTO aDTO){
		ConfActividadDocumentoDTO resp=null;
		if (aDTO!=null){
			resp= new ConfActividadDocumentoDTO();
			  resp.setConfActividadDocumentoId(aDTO.getConfActividadDocumentoId());
		        resp.setMuestraEnCombo(aDTO.getMuestraEnCombo());
		        resp.setAccion(aDTO.getAccion());
		        resp.setGrupoActividad(Integer.valueOf(aDTO.getGrupoActividad().getValor()));
		        resp.setNombreActividad(aDTO.getNombreActividad());
		       	resp.setNombreDocumento(aDTO.getNombreDocumento());
		        resp.setTipoActividadId(aDTO.getTipoActividadId());
		        resp.setTipoDocumentoId(aDTO.getTipoDocumentoId());
		       	resp.setEstadoCambioExpediente(aDTO.getEstadoCambioExpediente());
		        resp.setForma(aDTO.getForma());
		        resp.setUsaEditor(aDTO.getUsaEditor());
		}
		return resp;
	}
	
	public static List<ActuacionDTO> transformarCAD (List<ConfActividadDocumento> lstCAD){
		List<ActuacionDTO> lstResp =null;
		if (lstCAD!=null){
			lstResp= new ArrayList<ActuacionDTO> ();
			for (ConfActividadDocumento cad : lstCAD){
				lstResp.add(transformar(cad));
			}
		}
		return lstResp;
	}
	
	public static List<ConfActividadDocumentoDTO> transformarActuacion (List<ActuacionDTO> lstA){
		List<ConfActividadDocumentoDTO> lstResp = null;
		if (lstA!=null){
			lstResp=new ArrayList<ConfActividadDocumentoDTO>();
			for (ActuacionDTO a : lstA){
				lstResp.add(transformar(a));
			}
		}
		
		return lstResp;
	}

}
