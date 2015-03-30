/**
 * Nombre del Programa : 			CatAreasNegocioDAOImpl.java
 * Autor               : AlejandroGA
 * Compania            : Ultrasist
 * Proyecto            : NSJP                    Fecha: 17/05/2012
 * Marca de cambio     : N/A
 * Descripcion General : Implementacion DAO de Areas de Negocio
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
package mx.gob.segob.nsjp.dao.catalogo.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.catalogo.CatAreasNegocioDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.CatAreasNegocio;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author AlejandroGA
 * 
 */
@Repository
public class CatAreasNegocioDAOImpl extends
		GenericDaoHibernateImpl<CatAreasNegocio, Long> implements
		CatAreasNegocioDAO {

	/**
	 * Metodo para consultar los valores de la tabla catAreasNegocio 
	 * que pertenecen a la institucion actual
	 * @return Lista de valores contenidos en la tabla
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CatAreasNegocio> consultarTodos() {
				
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM CatAreasNegocio catAN ");
		queryStr.append(" WHERE catAN.confInstitucion.confInstitucionId = ");
		queryStr.append(" (SELECT confIns.confInstitucionId from ConfInstitucion confIns WHERE confIns.esInstalacionActual = 1)");
		queryStr.append(" ORDER BY catAN.nombreCatAN");
		
		logger.debug("queryStr :: " + queryStr);
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		logger.debug("pag :: " + pag);
		return super.ejecutarQueryPaginado(queryStr, pag);
	}
	
	/**
	 * Metodo para consultar CatAreasNegocio por filtro
	 */
	@Override
	public CatAreasNegocio consultarCatAreasNegocioPorFiltro(
			CatAreasNegocio filtroCatAreasNegocio) {
		
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM CatAreasNegocio catAreasNeg WHERE 1=1");

		if (filtroCatAreasNegocio != null
				&& filtroCatAreasNegocio.getCatAreasNegocioId() != null){
			
			queryStr.append(" AND catAreasNeg.catAreasNegocioId = ").append(
					filtroCatAreasNegocio.getCatAreasNegocioId());
		}
			

		if (filtroCatAreasNegocio != null
				&& filtroCatAreasNegocio.getNombreCatAN() != null
				&& !filtroCatAreasNegocio.getNombreCatAN().trim().isEmpty()){
			
			queryStr.append(" AND lower(catAreasNeg.nombreCatAN) = '")
			.append(filtroCatAreasNegocio.getNombreCatAN()
					.toLowerCase()).append("' ");
		}
			

		if (filtroCatAreasNegocio != null
				&& filtroCatAreasNegocio.getConfInstitucion() != null
				&& filtroCatAreasNegocio.getConfInstitucion()
						.getConfInstitucionId() != null){
			
			queryStr.append(" AND catAreasNeg.confInstitucion.confInstitucionId = ")
					.append(filtroCatAreasNegocio.getConfInstitucion()
							.getConfInstitucionId());
		}

		if (filtroCatAreasNegocio != null
				&& filtroCatAreasNegocio.getEsUIE() != null) {

			queryStr.append(" AND catAreasNeg.esUIE = ").append(
					filtroCatAreasNegocio.getEsUIE());
		}
		
		Query query = super.getSession().createQuery(queryStr.toString());

		return (CatAreasNegocio) query.uniqueResult();
	}
	

	/**
	 * Metodo para consultar el numero funcionario asociados a una area de negocio
	 */
	@Override
	public Long consultarNumeroDeFuncionariosPorAreaNegocio(CatAreasNegocio catAreasNeg) {
		final StringBuffer queryStr = new StringBuffer();
		
		if (catAreasNeg != null && catAreasNeg.getCatAreasNegocioId() != null) {
			queryStr.append(
					"SELECT COUNT(f.claveFuncionario) FROM Funcionario f WHERE f.catAreaNegocio.catAreasNegocioId =")
					.append(catAreasNeg.getCatAreasNegocioId());
		}

		Query query = super.getSession().createQuery(queryStr.toString());
		return (Long)query.uniqueResult();
	}
}
