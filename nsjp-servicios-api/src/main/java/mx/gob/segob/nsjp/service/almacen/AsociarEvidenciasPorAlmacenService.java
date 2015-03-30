/**
 * Nombre del Programa : AsociarEvidenciasPorAlmacenService.java
 * Autor                            : rgama
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 29-Feb-2012
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.almacen;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;

/**
 * 
 * @version 1.0
 * @author rgama
 */
public interface AsociarEvidenciasPorAlmacenService {

    /**
     * Permite asociar un conjunto de evidencias a un almacen.
     * @param evidencias
     * @param almacen
     * @throws NSJPNegocioException
     */
    public void asociarEvidenciasPorAlmacen(List<EvidenciaDTO> evidencias, AlmacenDTO almacen)
            throws NSJPNegocioException;
    
}
