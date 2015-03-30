/**
 * Nombre del Programa : NumeroExpedienteDAOImplTest.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 28-jun-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.test.expediente.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
public class NumeroExpedienteDAOImplTest extends BaseTestPersistencia<NumeroExpedienteDAO> {

//    @Autowired
//    private UsuarioDAO usuarioDao;
//
//    @Autowired
//    private ExpedienteDAO expedienteDao;

    public void testObtenerNumeroExpediente(){
        logger.info("obtenerNumeroExpediente:");
        assert daoServcice != null;
        String numeroExpedienteExistente = "NSJYUCPG2011103337H";
        if (logger.isDebugEnabled()) {
            logger.debug("Buscando el expediente: = " + numeroExpedienteExistente);
        }
        NumeroExpediente ne = daoServcice.obtenerNumeroExpediente
                (numeroExpedienteExistente,26L);
        assertNotNull(numeroExpedienteExistente, ne);
        logger.debug("El detalle es : = " + ne);

        NumeroExpediente noExistente = daoServcice.obtenerNumeroExpediente("noexistente",0L);
        assertNull(noExistente);
    }

//    public void testAsociarNumExpediente(){
//        Usuario usuario = usuarioDao.read(1L);
//        assertNotNull("usuario.getFuncionario()", usuario.getFuncionario());
//        assertNotNull("usuario", usuario);
//        Expediente expediente = expedienteDao.read(1L);
//        expediente.setNumeroExpediente(TestUtilPersitencia.numeroExpedienteExistente());
//        daoServcice.asociarNumExpediente(expediente, usuario);
//    }
    
    public void testConsultarTOCAPorCausa(){
    	Long expedienteId=1L;
		List<NumeroExpediente> numeros = daoServcice.consultarTOCAPorCausa(expedienteId);
//		NumeroExpediente numero = daoServcice.read(expedienteId);
		logger.info("Existen "+numeros.size()+" números de expeditnete TOCA");
		for (NumeroExpediente num : numeros) {
			logger.info("--------------------------------");
			logger.info("Numero Exp: "+num.getNumeroExpedienteId()+" / "+num.getNumeroExpediente());
			logger.info("Expediente: "+num.getExpediente().getExpedienteId());
			logger.info("Fecha: "+num.getFechaApertura());
		}
    }
    
    public void testConsultarParidadUltimoExpediente() {
    	
    	try {
			Boolean esPar = daoServcice.consultarUltimaParidadAsignadaDeNumeroExpediente();
			logger.debug("Paridad:" + esPar);
		} catch (Exception e) {
			logger.error(e);
			assertNotNull("El servicio arrojó excepción",null);
		}
    	
    	
    }
    
    @SuppressWarnings("static-access")
	public void testConsultarCausasHistorico () {

		Calendar calTempDec = Calendar.getInstance();
		calTempDec.add(calTempDec.DATE, -5);
    	logger.info("Fecha Ini: " + calTempDec.getTime());
    	try {
			List<NumeroExpediente> respuesta = daoServcice.consultarCausasHistorico(calTempDec,26L);
			assertTrue("La lista debe tener minimo un registro : ", respuesta.size()>0);
			logger.info("La lista debe tener minimo un registro : " + respuesta.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
    }
    
    public void testConsultarByUsuarioArea(){
    	Long estatusExpediente = EstatusExpediente.CERRADO.getValorId();
    	Long idUsuario = 3L;
		Long areaId = 44L;
		Long agenciaId=2969L;
		
		List<NumeroExpediente> nexpedientes = daoServcice.consultarByUsuarioArea(idUsuario, areaId, estatusExpediente,agenciaId,null);
    	logger.info("Existen "+nexpedientes.size()+" numero expedientes");
    	for (NumeroExpediente nexp : nexpedientes) {
    		logger.info("-------------------------------");
    		logger.info("ID: "+nexp.getNumeroExpedienteId());
    		logger.info("Num: "+nexp.getNumeroExpediente());
    		logger.info("Area: "+nexp.getJerarquiaOrganizacional().getNombre());
    		logger.info("Usuario: "+nexp.getFuncionario().getNombreCompleto());
    		logger.info("Estado: "+nexp.getEstatus().getValor());
		}
    }
    
    public void testConsultarHistoricoCausasExpediente() {
    	List<NumeroExpediente> respuesta = daoServcice.consultarHistoricoCausasExpediente(new Date());
    	
    	assertTrue("La lista debe regresr minimo un registro ",respuesta.size()>0);
    	logger.info("La lista debe regresr minimo un registro :: "+respuesta.size());
    	
    	for (NumeroExpediente numeroExpediente : respuesta) {
    		logger.info("NumeroExpediente Causa ID :: "+numeroExpediente.getNumeroExpedienteId());
    		logger.info("Tiene numeros expedientes hijos :: "+numeroExpediente.getNumeroExpedientes());
		}
    }
    
    public void testObtenerNumeroExpedienteXExpediente(){
    	NumeroExpediente numero = daoServcice.obtenerNumeroExpedienteXExpediente(133L);
    	assertNotNull(numero);
    	logger.info("Id: "+numero.getNumeroExpedienteId());
    	logger.info("Numero: "+numero.getNumeroExpediente());
    }
    
    public void testConsultarNumeroExpedientePorNumeroCaso() {
    	List<NumeroExpediente> respuesta = daoServcice.consultarNumeroExpedientePorNumeroCaso("ZAC/FG/XX/PGU/2012/AA-00864");
    	
    	assertTrue("La lista debe tener minimo un registro ", respuesta.size()>0);
    	logger.info("La lista debe tener minimo un registro :: "+ respuesta.size());
    }
    
    public void testConsultarnumExpedienteHijos () {	
    	List<NumeroExpediente> respuesta = daoServcice.consultarnumExpedienteHijos(1L);
    	
    	assertTrue("L alista debe tener minimo un registro ", respuesta.size()>0);
    	logger.info("Lista :: "+respuesta.size());
    }
    
    public void testObtenerNumExpPorEstatusYMes () {
    	try {
			List<Object[]> respuesta = daoServcice.obtenerNumExpPorEstatusYMes(DateUtils.obtener("01/07/2011"), DateUtils.obtener("31/08/2011"), 
																			EstatusExpediente.ARCHIVO_DEFINITIVO);
			assertTrue("La lista debe regresar minimo un registro ", respuesta.size()>0);
			logger.info("La lista debe regresar minimo un registro "+ respuesta.size());
			for (Object[] objects : respuesta) {
				logger.info(" Mes "+objects[0]+" Num "+objects[1]);
			}			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
    }
    
    public void testObtenerCarpetaEjecucionPorInvolucrado() {
    	NumeroExpediente numExp = daoServcice.obtenerCarpetaEjecucionPorInvolucrado(new Long(70));
    	
    	assertNotNull(numExp);
    	logger.info("-------------------");
    	logger.info("ID Carpeta Ejecucion " + numExp.getNumeroExpedienteId());
    	logger.info("Numero Carpeta Ejecucion " + numExp.getNumeroExpediente());
    	logger.info("-------------------");
    }

    public void testBuscarNumeroExpedientePorCasoFolioImputado(){
    	String caso = "YUC/PG/XX/PGE/2011/AA-00227";
    	String folio = "F0001";
    	Long   funcionario = 14L;
    	NumeroExpediente ne = daoServcice.buscarNumeroExpedientePorCasoFolioImputado(caso, folio, funcionario);
    	if(ne != null){
    		logger.info("Numero de Expediente :: "+ne.getNumeroExpediente());
    	}else{
    		logger.info("No se encontró numero de expediente para los parametros buscados");
    	}
    }

    public void testEliminarNumeroExpediente(){
    	
    }
    
    public void testObteberExpedienteDefensaPorCasoFolioImputado(){
        String caso = "YUC/PG/XX/PGE/2011/AA-00227";
        String folio = "F0001";
        Long   funcionario = 14L;
        NumeroExpediente ne = daoServcice.obtenerExpedienteDefensaPorCasoFolioImputado(caso, folio, funcionario);
        if(ne != null){
            logger.info("Numero de Expediente :: "+ne.getNumeroExpediente());
        }else{
            logger.info("No se encontró numero de expediente para los parametros buscados");
        }
    }
    
    public void testConsultarNumExpPorFuncionarioYNumExp() {
    	NumeroExpediente respuesta = daoServcice.consultarNumExpPorFuncionarioYNumExp(new Long(3), new Long(1));
    	assertNotNull(respuesta);
    	
    	logger.info("----------------------");
    	logger.info("Numero Expediente :: "+respuesta.getNumeroExpediente());
    	logger.info("Numero Expediente ID :: "+respuesta.getNumeroExpedienteId());
    	logger.info("----------------------");
    }
    
    
    public void testConsultarNumExpPorFuncionario() {
    	List<NumeroExpediente> respuesta = daoServcice.consultarNumExpPorFuncionario(new Long(3));
    	assertNotNull(respuesta);
    	assertTrue("La lista debe tenr minimo un registro",respuesta.size()>0);
    	
    	logger.info("La lista debe tenr minimo un registro"+respuesta.size());
    	for (NumeroExpediente numeroExpediente : respuesta) {
    		logger.info("----------------------");
        	logger.info("Numero Expediente :: "+numeroExpediente.getNumeroExpediente());
        	logger.info("Numero Expediente ID :: "+numeroExpediente.getNumeroExpedienteId());
        	logger.info("----------------------");
		}    	    	
    }
    
    public void testConsultarExpedientesDelFuncionario() {
    	
    	Funcionario funcionario = new Funcionario();
    	funcionario.setClaveFuncionario(3L);
    	funcionario.setDiscriminante(new CatDiscriminante(1L));
		
		List<NumeroExpediente> respuesta = daoServcice.consultarExpedientesDelFuncionario(funcionario);
    	//assertNotNull(respuesta);
    	//assertTrue("La lista debe tenr minimo un registro",respuesta.size()>0);
    	
    	logger.info("La lista debe tenr minimo un registro"+respuesta.size());
    	for (NumeroExpediente numeroExpediente : respuesta) {
    		logger.info("----------------------");
        	logger.info("Numero Expediente :: "+numeroExpediente.getNumeroExpediente());
        	logger.info("Numero Expediente ID :: "+numeroExpediente.getNumeroExpedienteId());
        	logger.info("----------------------");
		}    	    	
    }
    public void testConsultarNumeroExpedienteXExpedienteId(){
    	
    	Long expedienteId = 12L;
		NumeroExpediente num = daoServcice.consultarNumeroExpedienteXExpedienteId(expedienteId);
		logger.info(" info: " + num);
    	
    }
    
    public void testCconsultarNumeroExpedienteByTipoYEstatus() {
    	List<NumeroExpediente> respuesta = daoServcice.consultarNumeroExpedienteByTipoYEstatus(null, new Long(2958),26L);
    	assertNotNull(respuesta);
    	assertTrue("La lista debe tenr minimo un registro",respuesta.size()>0);
    	
    	logger.info("La lista debe tenr minimo un registro"+respuesta.size());
    	for (NumeroExpediente numeroExpediente : respuesta) {
    		logger.info("----------------------");
        	logger.info("Numero Expediente :: "+numeroExpediente.getNumeroExpediente());
        	logger.info("Numero Expediente ID :: "+numeroExpediente.getNumeroExpedienteId());
        	logger.info("----------------------");
		}    	    	
    }
    
    public void testConsultarExpedientesPorFiltroST() {
//    	Long rolId = 0L;
//    	Long idDistrito = 1L;
//    	Long idDiscriminante = 0L;
    	
       	Long rolId = Roles.COORDINADOR_CONSIGNACION.getValorId();
    	Long idDistrito = 1L;
    	Long idDiscriminante = 0L;
    	Long idFuncionario = 278L;
    	List<Long> estatus = new ArrayList<Long>();
    	estatus.add(EstatusExpediente.ABIERTO.getValorId());

    	List<NumeroExpediente> respuesta = daoServcice.consultarExpedientesPorFiltroST(null, null, Areas.AGENCIA_DEL_MINISTERIO_PUBLICO.parseLong(),
    			estatus, idDiscriminante, rolId, idDistrito, idFuncionario);

    	for (NumeroExpediente numExpediente : respuesta) {
    		logger.info("----------------------");
    		logger.info("Numero Expediente Id :: "+numExpediente.getNumeroExpedienteId());
        	logger.info("Numero Expediente :: "+numExpediente.getNumeroExpediente());
        	logger.info("Fecha Creacion :: "+numExpediente.getExpediente().getFechaCreacion());
        	logger.info("Estatus :: "+numExpediente.getEstatus().getValorId());
        	logger.info("----------------------");
		}    	    	
    	
    	logger.info("respuesta.size() :: "+respuesta.size());
    	logger.info("TAMAÑO DE LA LISTA::::::::::::::::::::::::::::::::::::::::::::::."+respuesta.size());

    	
    }
    
    public void testConsultarNumeroExpedientePorFiltro() {
	
    	Date fechaInicio = new Date();
		Date fechaFin = new Date();    	
		
		Calendar calTempDec = Calendar.getInstance();
		calTempDec.setTime(fechaInicio);
		calTempDec.add(Calendar.DATE, -2);
		
		Calendar calTempDec2 = Calendar.getInstance();
		calTempDec2.setTime(fechaFin);
		calTempDec2.add(Calendar.DATE, 1);
		
    	List<NumeroExpediente> respuesta = daoServcice.consultarNumeroExpedientePorFiltro(calTempDec.getTime(), calTempDec2.getTime(), null, null, null);

    	logger.info("TAMAÑO DE LA LISTA::::::::::::::::::::::::::::::::::::::::::::::."+respuesta.size());
    	for (NumeroExpediente numExpediente : respuesta) {
    		logger.info("----------------------");
        	logger.info("Numero Expediente :: "+numExpediente.getNumeroExpediente());
        	logger.info("Fecha Creacion :: "+numExpediente.getExpediente().getFechaCreacion());
        	logger.info("Estatus :: "+numExpediente.getEstatus().getValorId());
        	logger.info("----------------------");
		}    	    	
    }
    
	public void testconsultarNumeroDeExpedienteConHechoPorFiltros() {

		Date fechaInicio = new Date();
		Date fechaFin = new Date();

		Calendar calTempDec = Calendar.getInstance();
		calTempDec.setTime(fechaInicio);
		calTempDec.add(Calendar.DATE, -5);

		Calendar calTempDec2 = Calendar.getInstance();
		calTempDec2.setTime(fechaFin);
		calTempDec2.add(Calendar.DATE, 1);

//		List<NumeroExpediente> respuesta = daoServcice
//				.consultarNumeroDeExpedienteConHechoPorFiltros(EstatusExpediente.NO_ATENDIDO, null, calTempDec.getTime(), calTempDec2.getTime());
		
		List<NumeroExpediente> respuesta = daoServcice
		.consultarNumeroDeExpedienteConHechoPorFiltros(EstatusExpediente.NO_ATENDIDO, null, null, null);

		logger.info("TAMAÑO DE LA LISTA::::::::::::::::::::::::::::::::::::::::::::::"
				+ respuesta.size());
		for (NumeroExpediente numExpediente : respuesta) {
			logger.info("----------------------");
			logger.info("Numero Expediente :: "
					+ numExpediente.getNumeroExpediente());
			logger.info("Fecha de Atencion :: " + numExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getFechaAtencion());
			logger.info("Estatus expediente:: " + numExpediente.getEstatus().getValorId());
			logger.info("Folio Notificacion :: "
					+ numExpediente.getExpediente().getHecho()
							.getAvisoHechoDelictivo().getFolioNotificacion());
			logger.info("----------------------");
		}
	}
	
	public void testConsultarNumeroDeExpedienteSinHechoPorFiltros() {

		Date fechaInicio = new Date();
		Date fechaFin = new Date();

		Calendar calTempDec = Calendar.getInstance();
		calTempDec.setTime(fechaInicio);
		calTempDec.add(Calendar.DATE, -5);

		Calendar calTempDec2 = Calendar.getInstance();
		calTempDec2.setTime(fechaFin);
		calTempDec2.add(Calendar.DATE, 1);

//		List<NumeroExpediente> respuesta = daoServcice
//				.consultarNumeroDeExpedienteConHechoPorFiltros(EstatusExpediente.NO_ATENDIDO, null, calTempDec.getTime(), calTempDec2.getTime());
		
		List<NumeroExpediente> respuesta = daoServcice
		.consultarNumeroDeExpedienteSinHechoPorFiltros(EstatusExpediente.NO_ATENDIDO, null, null, null);

		logger.info("TAMAÑO DE LA LISTA::::::::::::::::::::::::::::::::::::::::::::::"
				+ respuesta.size());
		for (NumeroExpediente numExpediente : respuesta) {
			logger.info("----------------------");
			logger.info("Numero Expediente :: "
					+ numExpediente.getNumeroExpediente());
			//logger.info("Fecha de Atencion :: " + numExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getFechaAtencion());
			//logger.info("Estatus expediente:: " + numExpediente.getEstatus().getValorId());
			//logger.info("Folio Notificacion :: "
			//		+ numExpediente.getExpediente().getHecho()
			//				.getAvisoHechoDelictivo().getFolioNotificacion());
			logger.info("----------------------");
		}
	}
	
	
    public void testConsultarNumerosDeExpedientesPorRolST(){
    	Long idExpediente = 2153L;
	
    	List<Long> idRoles = new ArrayList<Long>();
    	idRoles.add(Roles.COORDINADOR_CONSIGNACION.getValorId());
    	idRoles.add(Roles.CONSIGNADOR.getValorId());
    	idRoles.add(Roles.AGENTEMPSISTRAD.getValorId());
    	
		
		List<NumeroExpediente> nexpedientes = daoServcice.consultarNumerosDeExpedientesPorRolST(idRoles, idExpediente);
    	logger.info("Existen "+nexpedientes.size()+" numero expedientes");
    	for (NumeroExpediente nexp : nexpedientes) {
    		logger.info("-------------------------------");
    		logger.info("ID   : "+nexp.getNumeroExpedienteId());
    		logger.info("NE   : "+nexp.getNumeroExpediente());
    		logger.info("ExpId: "+nexp.getExpediente().getExpedienteId());
    		logger.info("FunId: "+nexp.getFuncionario().getClaveFuncionario());
		}
    }
    
    public void testConsultarEstatusNumeroExpedienteByNumeroExpedienteId(){
    	
    	Long numeroExpedienteId = 2284L;   	
		
		Long estatusExpediente = daoServcice.consultarEstatusNumeroExpedienteByNumeroExpedienteId(numeroExpedienteId);
    	logger.info("Estaus "+estatusExpediente);
    }
    
    public void testConsultarNumExpAlterno(){
    	
    	List<String> unidades = new ArrayList<String>();
    	unidades.add(0, "000");
    	
    	String numExpAlterno = daoServcice.obtenerNumeroExpedienteAlternoConsecutivo(1, 5, 1,unidades, "XXVIII", "2012","ZAC", null, null);
    	logger.info("Numero expediente alterno: "+numExpAlterno);
    }
    
    public void testConsultarNumeroExpedienteAlterno(){
    	
    	String numExpAlterno = daoServcice.consultarNumeroExpedienteAlterno(2556L);
    	logger.info("Numero expediente alterno: "+numExpAlterno);
    }
    
}
