package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Valor;

public class MandamientoDAOImplTest extends BaseTestPersistencia<MandamientoDAO>  {

	public void testConsultarMandamientoPorFiltro() throws ParseException {
		//String numeroExpediente="NSJZACPJ0100220123337R";
		String numeroExpediente= null;
		
		Mandamiento mandamiento=new Mandamiento();
		
		Long estado=EstatusMandamiento.EN_PROCESO.getValorId();		
		mandamiento.setEstatus(new Valor(estado));
		
		SimpleDateFormat loFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicial= loFormat.parse("13/08/2012");
		//mandamiento.setFechaInicial(fechaInicial);		
				
		Date fechaFinal= loFormat.parse("20/08/2012");		
		//mandamiento.setFechaFinal(fechaFinal);		
		
		List<Mandamiento> Mandamientos = daoServcice.consultarMandamientoPorFiltro(mandamiento, numeroExpediente,null);
		
		for (Mandamiento Md :Mandamientos) {
			logger.info("----------------------");
			logger.info("Mandamiento_id: " + Md.getDocumentoId());
		}
		logger.info("Existen " + Mandamientos.size()+ " Mandamientos");
		
	}
	
	public void testFiltro(){
		UsuarioDTO us = new UsuarioDTO();
		FuncionarioDTO fun = new FuncionarioDTO();
		fun.setClaveFuncionario(16L);
		us.setFuncionario(fun);		
		MandamientoDTO mandamiento = new MandamientoDTO();
		mandamiento.setUsuario(us);
		List<NumeroExpediente> ne =daoServcice.consultarMandamientosJudicialesPorFiltro(mandamiento);
		for (NumeroExpediente numeroExpediente : ne) {
			logger.info(numeroExpediente.getNumeroExpediente());
		}
	}		
}
