/**
 * Nombre del Programa : ArchivoDigitalDAOImplTest.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 May 2011
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
package mx.gob.segob.nsjp.dao.test.archivo.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.archivo.DescriptorAudienciaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.DescriptorAudiencia;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class DescriptorAudienciaDAOImplTest
        extends
            BaseTestPersistencia<DescriptorAudienciaDAO> {
    
	@SuppressWarnings("unused")
	public void testConsulta() {
        List<DescriptorAudiencia> das= daoServcice.consultarDescriptorAudienciasPorAudiencia(136L);
    }
}
