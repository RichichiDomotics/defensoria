/**
* Nombre del Programa : AsociarEvidenciasPorAlmacenServiceImplTest.java
* Autor                            : rgama
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 2 Feb 2012
* Marca de cambio        : N/A
* Descripcion General    : Prueba unitaria para el servicio de consulta de almacenes
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
package mx.gob.segob.nsjp.service.test.almacen.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.service.almacen.AsociarEvidenciasPorAlmacenService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;


public class AsociarEvidenciasPorAlmacenServiceImplTest extends
		BaseTestServicios<AsociarEvidenciasPorAlmacenService> {

	public void testoObtenerAlmacenDelExpediente () {
		List<EvidenciaDTO> evidencias = new ArrayList<EvidenciaDTO>();
		
		EvidenciaDTO loEvidencia1 = new EvidenciaDTO();
		loEvidencia1.setEvidenciaId(1L);
		evidencias.add(loEvidencia1);
		
		EvidenciaDTO loEvidencia2 = new EvidenciaDTO();
		loEvidencia2.setEvidenciaId(2L);
		evidencias.add(loEvidencia2);	
		
		EvidenciaDTO loEvidencia3 = new EvidenciaDTO();
		loEvidencia3.setEvidenciaId(3L);
		evidencias.add(loEvidencia3);	
		
		AlmacenDTO almacen = new AlmacenDTO();
		almacen.setIdentificadorAlmacen(2L);
		
		try {
			service.asociarEvidenciasPorAlmacen(evidencias, almacen);
 		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}		
	}
}
