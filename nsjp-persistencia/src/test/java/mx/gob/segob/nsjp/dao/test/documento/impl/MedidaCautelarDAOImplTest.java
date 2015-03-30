/**
* Nombre del Programa : MedidaCautelarDAOImplTest.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 18/08/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMedida;
import mx.gob.segob.nsjp.comun.enums.medida.TipoMedida;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dao.documento.MedidaCautelarDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.MedidaCautelar;
import mx.gob.segob.nsjp.model.NumeroExpediente;

/**
 * Clase de pruebas unitarias para el dao de MedidaCautelar
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author Emigdio
 *
 */
public class MedidaCautelarDAOImplTest extends BaseTestPersistencia<MedidaCautelarDAO>{
	
	public void testConsultarMedidasCautelaresPorExpediente(){
		
		List<MedidaCautelar> medidas = daoServcice.obtenerMedidasCautelaresPorExpediente(1L);
		logger.debug("Medidas:" + medidas.size());
		
	}
	
	public void testobtenerMedidasCautelaresPorNumeroExpediente(){	        
	    List<MedidaCautelar> medidas = daoServcice.obtenerMedidasCautelaresPorNumeroExpediente("NSJYUCPJ20114433333",26L);
	    logger.debug("Medidas:" + medidas.size());	        
	}

	public void testobtenerMedidasCautelares(){
		MedidaCautelarDTO mc = null;
	    List<NumeroExpediente> expedientes = new ArrayList<NumeroExpediente>(); 
	    //mc.setNumeroCausa("NSJZACFG010012012339AQ");

	    expedientes=daoServcice.consultarMedidasCautelaresPorFiltro(mc);
    	
	    for (NumeroExpediente expediente : expedientes) {
			logger.info(expediente.getNumeroExpediente());
		}
	    
	    logger.info("Total:  " + expedientes.size());
	}   
	   
	public void testObtenerMedCauPorFechasYTipoNedida() {
		try {
			Long respuesta = daoServcice.obtenerMedCauPorFechasYTipoNedida(DateUtils.obtener("01/07/2011"), DateUtils.obtener("01/09/2011"), TipoMedida.GARANTIA_ECONOMICA.getValorId());
		
			assertNotNull(respuesta);
			logger.info("Respuesta :: "+respuesta);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}

	public void testObtenerMedidasCautelaresPorFiltro(){
		Calendar fechaActual =Calendar.getInstance();
		fechaActual.setTime(new Date());
		Date fecha = fechaActual.getTime();
//		fecha =null;
		Long[] idES=  
				{EstatusMedida.NO_ATENDIDA.getValorId(), 
				EstatusMedida.EN_PROCESO.getValorId()};
		List<Long> estatusId = Arrays.asList(idES);
		
		List<MedidaCautelar> listaResultado = daoServcice.obtenerMedidasCautelaresPorFiltro(fecha, estatusId);
		assertFalse("La lista debe de tener almenos un registro", listaResultado.isEmpty());
		logger.info(" Lista:"+ listaResultado.size());
		for (MedidaCautelar medidaCautelar : listaResultado) {
			logger.info(" Media:"+ medidaCautelar.getDocumentoId() );
			logger.info(" Media:"+ medidaCautelar.getEstatus() );
			logger.info(" Media:"+ medidaCautelar.getEstatus().getValorId() );
			logger.info(" Media:"+ medidaCautelar.getEstatus().getValor() );
			logger.info(" Media:"+ medidaCautelar.getValorTipoMedida().getValorId() );
			logger.info(" Media:"+ medidaCautelar.getValorTipoMedida().getValor() );
		}
		logger.info(" Lista:"+ listaResultado.size());
	}
	
	public void testObtenerMedidasCautelaresPorFiltroByGama() throws ParseException{
		MedidaCautelarDTO medidaCautelar = new MedidaCautelarDTO();
		
				
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setDiscriminante(new CatDiscriminanteDTO(2L));
		medidaCautelar.setFuncionario(funcionarioDTO);
		//medidaCautelar.setNumeroCausa("NSJZACPJ0100220123337P");

		SimpleDateFormat loFormat = new SimpleDateFormat("dd/MM/yyyy");
		medidaCautelar.setFechaInicio(loFormat.parse("04/07/2012"));
		medidaCautelar.setFechaFin(new Date());

		medidaCautelar.setEstatus(new ValorDTO(2523L));
		
		
		List<MedidaCautelar> medidas = daoServcice.obtenerMedidasCautelaresPorFiltro(medidaCautelar);
		for (MedidaCautelar loMedidaBD : medidas) {
			logger.debug("Medidas:" + loMedidaBD.getDocumentoId());
		}
		logger.debug("Medidas:  >>>>>> " + medidas.size() + " <<<<<<<<<");
		
		
	}
}
