/**
 * Nombre del Programa : AcronimoNumExpAlterno.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 25/07/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeración para el Acronimo de los Numeros de Expedientes Alternos.
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
package mx.gob.segob.nsjp.comun.enums.expediente;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeración para el Acronimo de los Numeros de Expedientes Alternos.
 * @author GustavoBP
 *
 */
public enum AcronimoNumExpAlterno {
	SIN_AREA(0L, "--"),
	ATENCION_TEMPRANA_PG_PENAL(1L, "UATP"),
	ATENCION_TEMPRANA_PG_NO_PENAL(2L, "UATA"),
	JUSTICIA_ALTERNATIVA_RESTAURATIVA(3L, "CJA"),
	UNIDAD_INVESTIGACION(4L, "UI"),
	COORDINACION_UNIDAD_INVESTIGACION(5L, "UI"),
	COORDINACION_POLICIA_MINISTERIAL(6L, "PINV"),
	VISITADURIA(7L, "VIS"),
	COORDINACION_ATENCION_VICTIMAS(8L, "APVT"),
	AGENCIA_DEL_MINISTERIO_PUBLICO(9L, "AP");
	
	
	private Long acronimoId;
    private String acronimo;
    
    private final static Map<Long, AcronimoNumExpAlterno> hash = new HashMap<Long, AcronimoNumExpAlterno>();

    static {
    	AcronimoNumExpAlterno[] objs = AcronimoNumExpAlterno.values();
        int pos = 0;
        while (pos < objs.length) {
            hash.put(objs[pos].getAcronimoId(), objs[pos]);
            pos++;
        }
    }

	/**
	 * @param acronimoId
	 * @param acronimo
	 */
	private AcronimoNumExpAlterno(Long acronimoId, String acronimo) {
		this.acronimoId = acronimoId;
		this.acronimo = acronimo;
	}

	  
	public static AcronimoNumExpAlterno getByValor(Long acronimoId) {
		return hash.get(acronimoId);
	}
	  
	/**
	 * @return the acronimoId
	 */
	public Long getAcronimoId() {
		return acronimoId;
	}

	/**
	 * @param acronimoId the acronimoId to set
	 */
	public void setAcronimoId(Long acronimoId) {
		this.acronimoId = acronimoId;
	}

	/**
	 * @return the acronimo
	 */
	public String getAcronimo() {
		return acronimo;
	}

	/**
	 * @param acronimo the acronimo to set
	 */
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}
}
