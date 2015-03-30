package mx.gob.segob.nsjp.service.test.medida.impl;

import java.util.Calendar;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMedida;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.medida.MedidaCautelarWSDTO;
import mx.gob.segob.nsjp.service.medida.RegistrarMedidaCautelarService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

public class RegistrarMedidaCautelarServiceImplTest extends
		BaseTestServicios<RegistrarMedidaCautelarService> {

	public void testRegistrarMedidaCautelar() throws NSJPNegocioException{
	
		MedidaCautelarWSDTO medida = new MedidaCautelarWSDTO();
		
		medida.setNombreSujeto("RIcardo");
		medida.setaPaternoSujeto("GAma");
		medida.setaMaternoSujeto("GArcia");
		medida.setFolioDocumento("PJ/201200512");
        medida.setIdValorPeriodicidad(1L);
        medida.setIdValorTipoMedida(1L);
        medida.setFechaInicio(Calendar.getInstance().getTime());
        medida.setFechaFin(Calendar.getInstance().getTime());
        medida.setDescripcionMedida("Descripcion de la medida cautelar");
        medida.setActivo(true);
        medida.setJuezOrdena("Daniel Alejandro Jiménez Ventura");
        medida.setFechaCreacion(Calendar.getInstance().getTime());
		
        medida.setNumeroCaso(" ZAC/FG/XX/PGU/2012/AA-00167");
        
        //TODO falta agregar el archivo
        
		service.registrarMedidaCautelar(medida);
	}
	
	public void testActualizarEstatusMedidaCautelar(){
		MedidaCautelarWSDTO medidaCautelar = new MedidaCautelarWSDTO();
		medidaCautelar.setFolioDocumento("PJ/201100034");
		medidaCautelar.setIdEstatus(EstatusMedida.NO_ATENDIDA.getValorId());

		Boolean regreso = false;
		try {
			regreso = service.actualizarEstatusMedidaCautelar(medidaCautelar);
			logger.info("Regreso:"+ regreso);
		} catch (NSJPNegocioException e) {
			logger.debug(e.getMessage(), e);
		}
	}
}
