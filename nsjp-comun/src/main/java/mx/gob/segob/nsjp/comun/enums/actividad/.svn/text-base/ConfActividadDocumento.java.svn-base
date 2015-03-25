/**
* Nombre del Programa : ConfActividad.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 07/08/2012
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
package mx.gob.segob.nsjp.comun.enums.actividad;

import java.util.HashMap;
import java.util.Map;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public enum ConfActividadDocumento {

	GENERAR_PLAN_DE_REINSERCION_SOCIAL(1231L),
	GENERAR_ACUSE_RECIBO_PERTENENCIAS(1235L),
	GENERAR_MANDAMIENTO_JUDICIAL(1236L),
	GENERAR_MEDIDA_CAUTELAR(1237L),
	GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL(1238L),
	GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR(1239L),
	EVALUAR_DESEMPENIO(194L, "evaluarDesempenioActuacion", "ACTUACIONES");	
	
	// Valor que debe corresponder a la BD 
	private Long valorId;
	// Valores para Reinsercion Social
	// Ruta del paso intermedio
    private String cForward;
    //si es en el JSP de actuacionesGenerales o en el de elaborarSolicitud
    private String posicion;

    private final static Map<Long, ConfActividadDocumento> HASH = new HashMap<Long, ConfActividadDocumento>();

    public final static String ACTUACIONES = "ACTUACIONES";
    public final static String ELABORAR_SOLICITUD = "ELABORAR_SOLICITUD"; 

    
    static {
        final ConfActividadDocumento[] acts = ConfActividadDocumento.values();
        int pos = 0;
        while (pos < acts.length) {
            HASH.put(acts[pos].getValorId(), acts[pos]);
            pos++;
        }
    }
    
    private ConfActividadDocumento(final Long valorId) {
        this.valorId = valorId;
    }    

    private ConfActividadDocumento(final Long valorId, final String cForward) {
        this.valorId = valorId;
        this.cForward = cForward;
    }    
    private ConfActividadDocumento(final Long valorId, final String cForward, final String posicion) {
        this.valorId = valorId;
        this.cForward = cForward;
        this.posicion = posicion;
    }    
    
    
    public static ConfActividadDocumento getByValor(final Long valorId) {
        return HASH.get(valorId);
    }

    /**
     * M&eacute;todo de acceso al campo valorId.
     * 
     * @return El valor del campo valorId asociado en le BD.
     */
    public Long getValorId() {
        return valorId;
    }
    
	/**
	 * @return the cForward
	 */
	public String getcForward() {
		return cForward;
	}

	/**
	 * Método de acceso al campo posicion.
	 * @return El valor del campo posicion
	 */
	public String getPosicion() {
		return posicion;
	}
    
}
