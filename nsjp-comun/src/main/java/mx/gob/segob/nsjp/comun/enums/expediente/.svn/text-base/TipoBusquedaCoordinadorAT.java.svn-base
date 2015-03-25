/**
 * 
 */
package mx.gob.segob.nsjp.comun.enums.expediente;

import java.util.HashMap;
import java.util.Map;

/**
 * @author IgnacioFO
 *
 */
public enum TipoBusquedaCoordinadorAT {
	  EXPEDIENTES_ATP_DIA(1L),
	  EXPEDIENTES_ATP_TODOS(2L),
	  EXPEDIENTES_ATP_AGENCIA(3L),
	  EXPEDIENTES_ATP_USUARIO(4L),
	  EXPEDIENTES_AT_DIA(5L),
	  EXPEDIENTES_AT_TODOS(6L),
	  EXPEDIENTES_AT_AGENCIA(7L),
	  EXPEDIENTES_AT_USUARIO(8L)
	  ;
	  
	  
	  
	  
	  
	  
	  
	  private final static Map<Long, TipoBusquedaCoordinadorAT> hash = new HashMap<Long, TipoBusquedaCoordinadorAT>();
	  static {
		  TipoBusquedaCoordinadorAT[] objs = TipoBusquedaCoordinadorAT.values();
	      int pos = 0;
	      while (pos < objs.length) {
	    	  hash.put(objs[pos].getId(), objs[pos]);
	          pos++;
	      }
	  }
	  private Long tipoId;
	  private TipoBusquedaCoordinadorAT(Long valorIdPredefinido) {
		  this.tipoId = valorIdPredefinido;
	  }
	  public static TipoBusquedaCoordinadorAT getByValor(Long valorIdPredefinido) {
		  return hash.get(valorIdPredefinido);
	  }
	  public Long getId() {
		  return tipoId;
	  }
}
