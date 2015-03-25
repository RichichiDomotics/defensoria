/**
 * Nombre del Programa : TipoAudiencia.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 24 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeración para el tipo de audiencias.
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
 * Enumeración para el tipo de audiencias.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public enum TipoAudiencia { 

	EXTRACCION(292L),APROBACION_DE_CONVENIO(294L),CONTROL(1714L), IMPUTACION(1715L), VINCULACION(1716L), CATEO(1717L), APREHENSION(
            1718L), JUICIO_ORAL(2021L), SOBRESEIMIENTO(2097L), INTERMEDIA(2774L), INDIVIDUALIZACION_DE_SANCION(2777L), LECTURA(2780L)
            , SSP(2783L), VERIFICACION_SSP(2786L), MASC(2789L), VERIFICACION_MASC(2792L), ABREVIADO(2795L), PROVIDENCIAS_PRECAUTORIAS(10533L),
            PROCEDIMIENTO_ABREVIADO(10534L), PRUEBA_ANTICIPADA(10535L), CASACION(10536L), REVISION(10537L), QUEJA(10538L), REVOCACION (10539L),
            APELACION(10540L), ACCION_PENAL_PRIVADA(10541L) ,EJECUCION(10542L), ACUMULACION(10543L), RECUSACION(10544L), MEDIDA_CAUTELAR(20036L),
            MEDIDA_CAUTELAR_ANTICIPADA(20039L), INTERVENCION_COMUNICACIONES(20450L), EXHUMACION_CADAVER(20453L), SOLICITUD_EMBARGO(20456L),
            PROCEDIMIENTO_SIMPLIFICADO(20459L), SPP(20462L), ACUERDOS_REPARATORIOS(20465L);
    private Long valorId;

    private final static Map<Long, TipoAudiencia> hash = new HashMap<Long, TipoAudiencia>();

    static {
        TipoAudiencia[] acts = TipoAudiencia.values();
        int pos = 0;
        while (pos < acts.length) {
            hash.put(acts[pos].getValorId(), acts[pos]);
            pos++;
        }
    }
    private TipoAudiencia(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }

    public static TipoAudiencia getByValor(Long valorIdPredefinido) {
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
