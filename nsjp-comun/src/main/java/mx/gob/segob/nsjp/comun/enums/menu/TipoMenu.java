/**
 * 
 */
package mx.gob.segob.nsjp.comun.enums.menu;

import java.util.HashMap;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.menu.TipoMenu;

/**
 * @author LuisMG
 *
 */
public enum TipoMenu {
	  	IZQUIERDO(0L),//Menú parte izquierda
	    ARRIBA(1L), //Menú parte de arriba
	    DERECHO(2L);//Menú parte derecha
	    
	    private Long id;

	    private final static Map<Long, TipoMenu> hash = new HashMap<Long, TipoMenu>();

	    static {
	    	TipoMenu[] objs = TipoMenu.values();
	        int pos = 0;
	        while (pos < objs.length) {
	            hash.put(objs[pos].getId(), objs[pos]);
	            pos++;
	        }
	    }
	    /**
	     * 
	     * @param valorIdPredefinidod
	     */
	    private TipoMenu(Long id) {
	        this.id = id;
	    }

	    /**
	     * 
	     * @param valorIdPredefinido
	     * @return
	     */
	    public static TipoMenu getById(Long id) {
	        return hash.get(id);
	    }

	    /**
	     * Mï¿½todo de acceso al campo valorId.
	     * 
	     * @return El valor del campo valorId asociado en le BD.
	     */
	    public Long getId() {
	        return id;
	    }

}
