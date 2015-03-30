/**
 * Nombre del Programa : ConsultarCatEstadoPermisoServiceImplTest.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 Jun 2011
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
package mx.gob.segob.nsjp.service.test.audiencia.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.CatEstadoPermisoDTO;
import mx.gob.segob.nsjp.service.audiencia.ConsultarEstadoPermisoService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AAAV 
 * 
 */
public class ConsultarCatEstadoPermisoServiceImplTest
        extends
        	BaseTestServicios<ConsultarEstadoPermisoService> {

    public void testBuscarEstadosPermiso() {
        try {
            List<CatEstadoPermisoDTO> resp = service.buscarEstadosPermiso();
            if(resp!=null){
        		for(CatEstadoPermisoDTO EP : resp){
        			logger.info("---------------------------------------------------------");
        			logger.info("EP.getcEstatus(): " + EP.getEstatus());
        			logger.info("EP.getiEstado(): " + EP.getCatEstadoPermisoId());
        		}        		
            }
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }
    }
 }
