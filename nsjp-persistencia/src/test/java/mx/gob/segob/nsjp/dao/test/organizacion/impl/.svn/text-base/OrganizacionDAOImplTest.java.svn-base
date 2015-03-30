/**
* Nombre del Programa : OrganizacionDAOImplTest.java                                    
* Autor                            : Tattva-IT                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 27/04/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Prueba para el DAO de Organizacion                      
* Programa Dependiente  :N/A                                                      
* Programa Subsecuente :N/A                                                      
* Cond. de ejecucion        :N/A                                                      
* Dias de ejecucion          :N/A                             Horario: N/A       
*                              MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                       :N/A                                                           
* Compania               :N/A                                                           
* Proyecto                 :N/A                                   Fecha: N/A       
* Modificacion           :N/A                                                           
*------------------------------------------------------------------------------           
*/
package mx.gob.segob.nsjp.dao.test.organizacion.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.CatRelacion;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Organizacion;

/**
 * Prueba para el DAO de Organizacion
 * @version 1.0
 * @author Tattva-IT
 *
 */
public class OrganizacionDAOImplTest extends BaseTestPersistencia<OrganizacionDAO> {
	public void testobtenerOrganizacionByRelacion() {
		Organizacion organizacion = daoServcice.obtenerOrganizacionByRelacion(3821L, new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()));
				
		assertTrue("Organizacion", organizacion.getElementoId()>0);
		logger.info("Organizacion" + organizacion.getElementoId());
	}
	
	public void testConsultarOrganizacionesPorAudiencia(){
		Audiencia audiencia = new Audiencia();
		audiencia.setAudienciaId(467L);
		List<Organizacion> organizaciones = daoServcice.consultarOrganizacionesAudiencia(audiencia);
		assertTrue("Organizaciones", !organizaciones.isEmpty());
		for (Organizacion org : organizaciones){
			logger.info("Organizacion Id: "+org.getElementoId());
		}
	}
	
	public void testConsultarInvolucradoPorOrganizacion(){
		Organizacion org = new Organizacion();
		org.setElementoId(2675L);
		CatRelacion catRelacion = new CatRelacion();
		catRelacion.setCatRelacionId(59L);
		Involucrado invl = daoServcice.obtenerInvolucradoByRelacion(org, catRelacion);
		assertTrue("Involucrado", invl != null);
		logger.info("Involucrado Id: "+invl.getElementoId());
		logger.info("Tipo persona involucrado: "+invl.getTipoPersona());
	}
}
