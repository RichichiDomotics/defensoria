/**
 * Nombre del Programa : EstatusAudiencia.java
 * Autor                            : jfernandez
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 02 AGOS 2012
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeración para el de consulta en el menu de CORJAR.
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
package mx.gob.segob.nsjp.comun.enums.menu;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeración para el estatus de consulta en el menu de CORJAR.
 * 
 * @version 1.0
 * @author jfernandez
 * 
 */
public enum EstatusMenuJAR {
    PORASIGNAR(1L), ASIGNADOS(2L), PROPIOS(3L),PROPIOS_UI(4L);

    private Long valorId;
    private final static Map<Long, EstatusMenuJAR> hash = new HashMap<Long, EstatusMenuJAR>();

    static {
        EstatusMenuJAR[] acts = EstatusMenuJAR.values();
        int pos = 0;
        while (pos < acts.length) {
            hash.put(acts[pos].getValorId(), acts[pos]);
            pos++;
        }
    }
    private EstatusMenuJAR(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }

    public static EstatusMenuJAR getByValor(Long valorIdPredefinido) {
        return hash.get(valorIdPredefinido);
    }

    /**
     * Método de acceso al campo valorId.
     * 
     * @return El valor del campo valorId asociado en le BD.
     */
    public Long getValorId() {
        return valorId;
    }
}
