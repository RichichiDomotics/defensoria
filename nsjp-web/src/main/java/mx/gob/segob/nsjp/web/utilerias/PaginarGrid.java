package mx.gob.segob.nsjp.web.utilerias;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PaginarGrid {
    public static Paginacion calcularPaginacion(HttpServletRequest req, long registrosTotales){
    	String paginaStr = req.getParameter("page");
    	if(paginaStr == null || paginaStr.trim().equals("")) return null;

    	String registrosStr = req.getParameter("rows");
    	if(registrosStr != null && registrosStr.trim().equals("")) return null;

    	Paginacion paginacion = new Paginacion();
        paginacion.totalRegistros = registrosTotales;
        paginacion.numPagina = Integer.parseInt(paginaStr.trim());
        paginacion.regXpag = Integer.parseInt(registrosStr.trim());
        paginacion.regDesde = paginacion.numPagina*paginacion.regXpag-paginacion.regXpag;

        if(paginacion.regDesde < 0){
        	paginacion.regDesde = 0;
        }

        if(paginacion.regXpag>0){
            if(paginacion.totalRegistros > 0){
            	paginacion.totalPaginas = (int) (paginacion.totalRegistros / paginacion.regXpag);
                if((paginacion.totalRegistros % paginacion.regXpag)>0){
                	paginacion.totalPaginas++;
                }
            }
        }
        if(paginacion.totalPaginas < paginacion.numPagina){
        	paginacion.numPagina = paginacion.totalPaginas;
        }
        return paginacion;
    }

    public static <T> List<T> obtenerElementosSegunPaginacion(Paginacion paginacion, List<T> listaCompleta){
        List<T> listaResultado = new ArrayList<T>();
        int elementoActual;
        for(int i = 0; i < paginacion.regXpag; i++){
            elementoActual = paginacion.regDesde+i;
            if(elementoActual < listaCompleta.size())
                listaResultado.add(listaCompleta.get(elementoActual));
            else break;
        }
        return listaResultado;
    }
}
