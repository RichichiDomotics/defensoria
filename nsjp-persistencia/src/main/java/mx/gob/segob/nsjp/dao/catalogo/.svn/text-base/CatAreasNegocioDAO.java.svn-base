/**
* Nombre del Programa : 			CatAreasNegocioDAO.java
* Autor               : AlejandroGA
* Compania            : Ultrasist
* Proyecto            : NSJP                    Fecha: 17/05/2012
* Marca de cambio     : N/A
* Descripcion General : Interface DAO de Areas de Negocio
* Programa Dependiente: N/A
* Programa Subsecuente: N/A
* Cond. de ejecucion  : N/A
* Dias de ejecucion   : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor               :N/A
* Compania            :N/A
* Proyecto            :N/A                                 Fecha: N/A
* Modificacion        :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dao.catalogo;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.CatAreasNegocio;

/**
 * @author AlejandroGA
 * 
 */
public interface CatAreasNegocioDAO extends GenericDao<CatAreasNegocio, Long> {

	/**
	 * Metodo para consultar los valores de la tabla catAreasNegocio
	 * @return Lista de valores contenidos en la tabla
	 */
	List<CatAreasNegocio> consultarTodos();
	
	/**
	 * Metodo para consultar un catAreaNegocio por filtro
	 * @param filtroCatAreasNegocio
	 * @return objeto encontrado 
	 */
	CatAreasNegocio consultarCatAreasNegocioPorFiltro (CatAreasNegocio filtroCatAreasNegocio);
	
	/**
	 * Metodo para consultar cuantos funcionarios existen asociados a una area de negocio
	 * @param catAreasNeg
	 * @return
	 */
	Long consultarNumeroDeFuncionariosPorAreaNegocio (CatAreasNegocio catAreasNeg);
}
