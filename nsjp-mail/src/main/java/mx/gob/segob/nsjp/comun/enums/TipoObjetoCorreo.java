package mx.gob.segob.nsjp.comun.enums;

import java.util.HashMap;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia;

public enum TipoObjetoCorreo {
	SESSION(1L),TRANSPORT(2L),CONNECT(3L),RECIPIENT(4L),ASUNTOCONTENIDO(5L),ACTIVO(6L);
	
    private Long valorId;

    private final static Map<Long, TipoObjetoCorreo> hash = new HashMap<Long, TipoObjetoCorreo>();

    static {
    	TipoObjetoCorreo[] tipoObj = TipoObjetoCorreo.values();
        int pos = 0;
        while (pos < tipoObj.length) {
            hash.put(tipoObj[pos].getValorId(), tipoObj[pos]);
            pos++;
        }
    }
    private TipoObjetoCorreo(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }

    public static TipoObjetoCorreo getByValor(Long valorIdPredefinido) {
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
