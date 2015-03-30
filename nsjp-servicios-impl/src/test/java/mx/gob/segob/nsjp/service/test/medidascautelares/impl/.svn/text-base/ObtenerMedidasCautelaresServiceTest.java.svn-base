package mx.gob.segob.nsjp.service.test.medidascautelares.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMedida;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.service.medidascautelares.ObtenerMedidasCautelaresService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

public class ObtenerMedidasCautelaresServiceTest extends BaseTestServicios<ObtenerMedidasCautelaresService> {

	public void testObtenerMedidasCautelaresServiceImpl() throws NSJPNegocioException{
		Long idMedidaCautelar = 0L;
		Long idInvolucrado = 1080L;
		
		MedidaCautelarDTO medida = service.obtenerDetalleMedidaCautelar(idMedidaCautelar, idInvolucrado);
		
		logger.info("Medida Cautelar id :: "+medida);
		
	}
	
	public void testConsultarMedidasPorEstatus() throws NSJPNegocioException
	{
		List<MedidaCautelarDTO> medidas = service.consultaMedidasCautelaresPorEstatus(new MedidaCautelarDTO());
		logger.debug("Medidas encontradas: " + medidas.size());
		for(MedidaCautelarDTO row:medidas)
		{
			logger.debug("Folio: " + row.getFolioDocumento());
		}
	}
	/**
	 * Prueba para obtener involucrados a crear medidas cautelares
	 * @author Emigdio Hernández
	 */
	public void testConsultarInvolucradosConMedidasCautelaresPorNumeroExpedienteOCausa(){
	
		
		try {
			
			List<InvolucradoDTO> invDTO = 
					service.consultarInvolucradosConMedidasCautelaresPorNumeroExpedienteOCausa("NSJYUCPJ20114433333");
			assertFalse("La lista no puede ser vacia:", invDTO.isEmpty());
			logger.debug("Involucrados:" + invDTO);
			for (InvolucradoDTO involucradoDTO : invDTO) {
				logger.debug("Involucrados:" + involucradoDTO);
				logger.debug("Involucrados:" + involucradoDTO.getElementoId());
				logger.debug("Involucrados:" + involucradoDTO.getMedidasCautelaresDTO());
				if(involucradoDTO.getMedidasCautelaresDTO()!= null){
					logger.debug("Involucrados -Total Medidas:" + involucradoDTO.getMedidasCautelaresDTO().size());
					List<MedidaCautelarDTO> medidasDTO = involucradoDTO.getMedidasCautelaresDTO();
					for (MedidaCautelarDTO medidaCautelarDTO : medidasDTO) {
						logger.debug("MedidasDTO:" + medidaCautelarDTO.getDocumentoId());
						logger.debug("MedidasDTO:" + medidaCautelarDTO.getDescripcion());
						logger.debug("MedidasDTO:" + medidaCautelarDTO.getDescripcionMedida());
						logger.debug("MedidasDTO:" + medidaCautelarDTO.getCompromisoPeriodico());
						if(medidaCautelarDTO.getCompromisoPeriodico()!= null){
							logger.debug("MedidasDTO:" + medidaCautelarDTO.getCompromisoPeriodico().getMonto());
							logger.debug("MedidasDTO:" + medidaCautelarDTO.getCompromisoPeriodico().getFechaCompromisos());
						}
					}
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(),e);
		}
	
	
	}
	
	
	public void testObtenerMedidasCautelaresPorFechaIncumplientoDelDiaAnterior() throws NSJPNegocioException
	{
		List<MedidaCautelarDTO> medidas = service.obtenerMedidasCautelaresPorFechaIncumplientoDelDiaAnterior();
		logger.debug("Medidas encontradas: " + medidas.size());
		for(MedidaCautelarDTO row:medidas)	
		{
			logger.debug("Medida ID: " + row.getDocumentoId());
			logger.debug("Medida ID: " + row.getValorTipoMedida());
		}
	}
	
	public void testObtenerMedidasCautelaresPorFiltro() throws NSJPNegocioException
	{
		Calendar fechaActual =Calendar.getInstance();
		fechaActual.setTime(new Date());
		Date fecha = fechaActual.getTime();
		fecha=null;
		Long[] idES=  
				{EstatusMedida.NO_ATENDIDA.getValorId(), 
				EstatusMedida.EN_PROCESO.getValorId()};
		List<Long> estatusId =  Arrays.asList(idES);

		List<MedidaCautelarDTO> listaResultado = service.obtenerMedidasCautelaresPorFiltro(fecha, estatusId);
		assertFalse("La lista debe de tener almenos un registro", listaResultado.isEmpty());
		logger.info(" Lista:"+ listaResultado.size());
		for (MedidaCautelarDTO medidaCautelar : listaResultado) {
			logger.info(" Media:"+ medidaCautelar.getDocumentoId() );
			logger.info(" Media:"+ medidaCautelar.getEstatus() );
			logger.info(" Media:"+ medidaCautelar.getEstatus().getIdCampo());
			logger.info(" Media:"+ medidaCautelar.getEstatus().getValor() );
			logger.info(" Media:"+ medidaCautelar.getValorTipoMedida() );
		}
		logger.info(" Lista:"+ listaResultado.size());
	}
}
