/**
 * Nombre del Programa : Jerarquias.java
 * Autor                            : encesarvarga (Enable IT) 
 * Compania                    : Enable IT
 * Proyecto                      : NSJP                    Fecha: 02 May 2013
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeración de las Jerarquias (compelta).
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
package mx.gob.segob.nsjp.comun.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeración de las areas.
 * 
 * @version 1.0
 * @author encesarvarga (Enable IT)
 * 
 */
public enum Jerarquias {

	   
    // 0
    NA(0L), 
    PGJ(1L),//Procuraduria General de Justicia
    DEF(2L), //Defensoria
    PJ(3L),//Poder Judicial
    SSP(4L),//Secretaria de Seguridad Publica
    RS(5L),//Reinserción social
    //6
    COORDINACION_ATENCION_TEMPRANA_PG(6L), 
    JUSTICIA_ALTERNATIVA_RESTAURATIVA(7L),
    ALMACEN(8L),
    COORDINACION_UNIDAD_INVESTIGACION(9L),
    // 10
    UNIDAD_INVESTIGACION(10L), 
    FISCAL_FACILITADOR(11L),
    COORDINACION_ATENCION_VICTIMAS(12L),
    COORDINACION_PERICIALES_PG(13L),
    SERVICIOS_PERICIALES_PG(14L),
    COORDINACION_POLICIA_MINISTERIAL(15L),
    COORDINACION_VISITADURIA(16L),
    ATENCION_TEMPRANA_DEFENSORIA(17L), 
    COORDINACION_DEFENSORIA(18L),
    DEFENSORIA(19L),
    //20
    COORDINACION_PERICIALES_DEF(20L),
    SERVICIOS_PERICIALES_DEF(21L),
    ENLACE_DEF(22L),
    ADMINISTRATIVO_EJECUCION(23L),
    ADMINISTRACION_JUDICIAL(24L),
    ATENCION_TEMPRANA_PJ(25L), //enlace 
    DEPARTAMENTO_CAUSA(26L),
    DEPARTAMENTO_INFORMATICA(27L),
    DEPARTAMENTO_SALA(28L),
    DEPARTAMENTO_SEGUNDA_INSTANCIA(29L),
    // 30
    JUZGADO(30L), //juez
    DEPARTAMENTO_NOTIFICACIONES(31L),
    TRANSCRIPTORES(32L),
    EJECUCION_PENAS_MEDIDA_CAUTELARES(33L),
    CENTROS_DETENCION(34L),
    DIRECCION_POLICIA_PROCESAL(35L),
    MEDICOS(36L),
    POLICIA_SSP(37L),
    POLICIA_PROCESAL(38L),
    EXTERNO_PERITOS_PG(39L),
    // 40
    EXTERNO_DEFENSORES(40L),
    EXTERNO_PERITOS_DEF(41L),
    UMAN(42L), 
    REGISTRO_INICIAL(43L),
    ATENCION_TEMPRANA_PG_PENAL(44L),
    ATENCION_TEMPRANA_PG_NO_PENAL(45L),
    UNIDAD_CAPTURA_SSP(46L),
    COORDINACION_SEGUIMIENTO_SSP(47L),
    @Deprecated COORDINACION_INVESTIGADORES_ROBO_VEHICULO(48L),//NO EXISTE EN BD
    @Deprecated UNIDAD_INVESTIGACION_ROBO_VEHICULO(49L),//NO EXISTE EN BD
    //50
    @Deprecated COORDINACION_INVESTIGADORES_DELITOS_SEXUALES(50L),//NO EXISTE EN BD
    @Deprecated UNIDAD_INVESTIGACION_DELITOS_SEXUALES(51L),//NO EXISTE EN BD
    @Deprecated COORDINACION_INVESTIGADORES_ROBO_VIOLENCIA(52L),//NO EXISTE EN BD
    @Deprecated UNIDAD_INVESTIGACION_ROBO_VIOLENCIA(53L),//NO EXISTE EN BD
    POLICIA_MINISTERIAL(54L),
    VISITADURIA(55L),
    ATENCION_VICTIMAS(56L),//ahora esta funciona para trabajo social
    ATENCION_PSICOLOGICA(57L),
    ATENCION_JURIDICA(58L),
    AGENCIA_DEL_MINISTERIO_PUBLICO(59L),
    //60
    ASE_DE_REINSERCION(60L), 
    DSE_DE_REINSERCION(61L),
    CER_DE_REINSERCION(62L),
    POP_DE_REINSERCION(63L),
    MED_DE_REINSERCION(64L),
    CPR_DE_REINSERCION(65L),
    CMT_DE_REINSERCION(66L),
    DEF_DP(67L);
    
    private Long valorId;

    private final static Map<Long, Jerarquias> hash = new HashMap<Long, Jerarquias>();

    static {
    	Jerarquias[] tipoObj = Jerarquias.values();
        int pos = 0;
        while (pos < tipoObj.length) {
            hash.put(tipoObj[pos].getValorId(), tipoObj[pos]);
            pos++;
        }
    }
    private Jerarquias(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }

    public static Jerarquias getByValor(Long valorIdPredefinido) {
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
