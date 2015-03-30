/**
 * Nombre del Programa : 			CatAreasNegocioDAOImplTest.java
 * Autor               : AlejandroGA
 * Compania            : Ultrasist
 * Proyecto            : NSJP                    Fecha: 23/05/2012
 * Marca de cambio     : N/A
 * Descripcion General : Tests de la Implementacion DAO de Areas de Negocio
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
package mx.gob.segob.nsjp.dao.test.catalogo.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dao.catalogo.CatAreasNegocioDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CatAreasNegocio;

/**
 * Tests de la Implementacion DAO de Areas de Negocio
 * @author AlejandroGA
 * @version 1.0
 */
public class CatAreasNegocioDAOImplTest extends BaseTestPersistencia<CatAreasNegocioDAO> {
	
	
	public void testsConsultarTodos(){
		
		List<CatAreasNegocio> listaCatAreaNeg = new ArrayList<CatAreasNegocio>();
		
		try{
			listaCatAreaNeg = daoServcice.consultarTodos();
			
			logger.debug("Tamaño de la lista:::"+ listaCatAreaNeg.size());
			for(CatAreasNegocio catAreaNeg:listaCatAreaNeg){
				logger.debug("        Id :: "+ catAreaNeg.getCatAreasNegocioId());
				logger.debug("    Nombre :: "+ catAreaNeg.getNombreCatAN());
				logger.debug("Institucion:: "+ catAreaNeg.getConfInstitucion().getNombreInst());
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void testsConsultarCatAreasNegocioPorFiltro(){
		
		CatAreasNegocio filtroCatAreasNegocio = new CatAreasNegocio();
		
		filtroCatAreasNegocio.setCatAreasNegocioId(10L);
		
		try{
			CatAreasNegocio CatAreasNegObtenido = daoServcice.consultarCatAreasNegocioPorFiltro(filtroCatAreasNegocio);
			
			if(CatAreasNegObtenido != null){
				logger.debug("        Id :: "+ CatAreasNegObtenido.getCatAreasNegocioId());
				logger.debug("    Nombre :: "+ CatAreasNegObtenido.getNombreCatAN());
				logger.debug("Institucion:: "+ CatAreasNegObtenido.getConfInstitucion().getNombreInst());
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void testsConsultarNumeroDeFuncionariosPorAreaNegocio(){
		
		Long numeroDeFuncionarios = 0L;
		
		try{
			CatAreasNegocio filtroCatAreasNegocio = new CatAreasNegocio();
			filtroCatAreasNegocio.setCatAreasNegocioId(10L);
			
			numeroDeFuncionarios = daoServcice.consultarNumeroDeFuncionariosPorAreaNegocio(filtroCatAreasNegocio);
			
			logger.debug("Numero de Funcionarios asociados al area:: "+ numeroDeFuncionarios);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
