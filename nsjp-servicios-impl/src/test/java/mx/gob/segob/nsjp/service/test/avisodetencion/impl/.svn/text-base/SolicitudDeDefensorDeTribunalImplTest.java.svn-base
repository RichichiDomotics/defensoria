/**
* Nombre del Programa : RegistrarAvisoDetencionDeAreaExternaImplTest.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 01/07/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
* Programa Dependiente  :N/A
* Programa Subsecuente :N/A
* Cond. de ejecucion        :N/A
* Dias de ejecucion          :N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       :N/A
* Compania               :N/A
* Proyecto                 :N/A                                 Fecha: N/A
* Modificacion           :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.test.avisodetencion.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.detencion.SolicitudDefensorWSDTO;
import mx.gob.segob.nsjp.service.avisodetencion.SolicitudDeDefensorDeTribunal;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Clase de pruebas unitarias para el registro de un aviso de detención de
 * área externa
 * @version 1.0
 * @author Asdrubal
 *
 */
public class SolicitudDeDefensorDeTribunalImplTest extends BaseTestServicios<SolicitudDeDefensorDeTribunal>{
	
	public void testRegistrarSolDefensor(){
		
		SolicitudDefensorWSDTO aviso = new SolicitudDefensorWSDTO();
		
		String backval=null;
		
		aviso.setApellidoMaternoImputado("Pastrana__");
		aviso.setApellidoPaternoImputado("Cedeño__");
		//aviso.setConfInstitucionId("3");
		aviso.setFolioElemento("PJ002");
		aviso.setFolioSolicitud("ASDR/TEST/002");
		aviso.setNombreImputado("Asdrubal__");
		aviso.setNumeroCausa("5/2013");

		
		List<String> delitosIDs = new ArrayList<String>();
		
		delitosIDs.add("4");
		
		aviso.setDelitos(delitosIDs);
		
		
			try {
				backval = service.solicitudDefensorTribunal(aviso);
			} catch (NSJPNegocioException e) {
				e.printStackTrace();
			}

			logger.info("El resultado fue:"+ backval);
		
	}

}
