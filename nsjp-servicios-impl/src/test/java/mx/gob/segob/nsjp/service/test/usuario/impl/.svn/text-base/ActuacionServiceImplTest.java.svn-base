/**
 * 
 */
package mx.gob.segob.nsjp.service.test.usuario.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.ActuacionDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;
import mx.gob.segob.nsjp.service.usuario.ActuacionService;

/**
 * @author LuisMG
 *
 */
public class ActuacionServiceImplTest extends BaseTestServicios<ActuacionService>{
	
	public void consultarActuacionPorRol(){
		RolDTO rolDTO = new RolDTO(107L);
		List<ActuacionDTO> lstADTO=new ArrayList<ActuacionDTO>();
		try{
			lstADTO=service.consultarActuacionPorRol(rolDTO);
			for (ActuacionDTO aDTO :lstADTO){
				System.out.print("ConfActividadDocumento_id : " + aDTO.getConfActividadDocumentoId());
				System.out.print("Estado_Inicial: " + aDTO.getGrupoActividad().getValor());
				System.out.print("Estado_Final: " + aDTO.getEstadoCambioExpediente().getValor());
				System.out.println("cValor: " + aDTO.getNombreActividad());
				
				
			}
		}catch (NSJPNegocioException e){
			e.printStackTrace();
		}
	}

}
