/**
 * Nombre del Programa : EstatusPermisoAudiencia.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 01 Jun 2012
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeración para el estatus del permiso de la audiencia.
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
package mx.gob.segob.nsjp.comun.enums.audiencia;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeración para el estatus del permiso de la audiencia.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */

public enum EstatusPermisosAudiencia {
    CONCEDIDO(1L, "Concedido"), CANCELADO(2L, "Cancelado"), SIN_ASIGNAR(3L, "Sin Asignar"),
    
    NUEVA_SOLICITUD(4L), FALLO(5L), ES_PJ(3L), NO_ES_JAVS(7L), NO_HAY_SOLICITUD(8L);

    private Long valorId;
    private String valor;
    
	private final static Map<Long, EstatusPermisosAudiencia> hash = new HashMap<Long, EstatusPermisosAudiencia>();

    static {
        EstatusPermisosAudiencia[] acts = EstatusPermisosAudiencia.values();
        int pos = 0;
        while (pos < acts.length) {
            hash.put(acts[pos].getValorId(), acts[pos]);
            pos++;
        }
    }
    private EstatusPermisosAudiencia(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }

    private EstatusPermisosAudiencia(Long valorIdPredefinido, String valorPredefinido) {
        this.valorId = valorIdPredefinido;
        this.valor = valorPredefinido;
    }
    
    public static EstatusPermisosAudiencia getByValor(Long valorIdPredefinido) {
        return hash.get(valorIdPredefinido);
    }

    /**
     * Método de acceso al campo valorId.
     * 
     * @return El valor del campo valorId asociado en la BD.
     */
    public Long getValorId() {
        return valorId;
    }

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}
}
