/**
 * Nombre del Programa : AsignarNumeroExpedienteServiceImpl.java
 * Autor                            : cesar
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 Apr 2011
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
package mx.gob.segob.nsjp.service.expediente.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.caso.EstatusCaso;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.AcronimoNumExpAlterno;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusTurno;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.ConsecutivosUtil;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatUIEspecializadaDAO;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteAdscritoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteRestDefensoriaDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteTecnicoDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.RelNumExpedienteAuditoriaDAO;
import mx.gob.segob.nsjp.dao.expediente.TurnoDAO;
import mx.gob.segob.nsjp.dao.expediente.impl.NumeroExpedienteDAOImpl;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioDAO;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.TurnoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.CatUIEspecializada;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.ExpedienteAdscrito;
import mx.gob.segob.nsjp.model.ExpedienteRestDefensoria;
import mx.gob.segob.nsjp.model.ExpedienteTecnico;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Parametro;
import mx.gob.segob.nsjp.model.RelNumExpedienteAuditoria;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.Turno;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.actividad.AdministradorActividadesService;
import mx.gob.segob.nsjp.service.caso.AsignarNumeroCasoService;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author cesarAgustin
 * 
 */
@Service
@Transactional
public class AsignarNumeroExpedienteServiceImpl
        implements
            AsignarNumeroExpedienteService {

    @Autowired
    private AdministradorActividadesService adminActividadesService;
    @Autowired
    private NumeroExpedienteDAO numeroExpedienteDAO;    
    @Autowired
    private ActividadDAO actividadDAO;
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private ExpedienteDAO expedienteDAO;
    @Autowired
    private ExpedienteRestDefensoriaDAO expRestDefensoriaDAO;
    @Autowired
    private ExpedienteAdscritoDAO expedienteAdscritoDAO;
    @Autowired
    private ExpedienteTecnicoDAO expedienteTecnicoDAO;
    @Autowired
    private TurnoDAO turnoDao;
    @Autowired
    private AsignarNumeroCasoService casoService;
    @Autowired
    private NumeroExpedienteDAO noExpDao;
    @Autowired
    private NumeroExpedienteDAOImpl noExpDaoImp;
    @Autowired
    private SolicitudDAO solicitudDAO;
    @Autowired
    private ConfInstitucionDAO confInsDao;
    @Autowired
    private CasoDAO casoDao;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired
	private RelNumExpedienteAuditoriaDAO relNumExpedienteAuditoriaDAO;
	@Autowired
	private AsignarNumeroCasoService asignarNumeroCasoService;
	@Autowired
	private ValorDAO valorDAO;
	@Autowired
	private CatUIEspecializadaDAO catUIEspecializadaDAO;
	@Autowired 
	private ParametroDAO parametroDAO; 

    private static final Logger logger = Logger
            .getLogger(AsignarNumeroExpedienteServiceImpl.class);
      
    private synchronized String obtenerNumeroExpediente(Long idArea, UsuarioDTO aoUsuarioFirmado) throws NSJPNegocioException{
    	
    	String libre = "NSJ";
    	String consecutivo = null;
        String incrementoString = "";
        String ultimoNumero = null;
        int incremento =  0;
    	
    	if(idArea == Areas.AGENCIA_DEL_MINISTERIO_PUBLICO.ordinal()){
    		libre = "SJT";
    	}
    	
    	logger.debug("Id Area ::: " + idArea);
        
        //YUC
        String entidadFederativa = "";
        String institucion = confInsDao.consultarInsitucionActual().getMonograma();
        Calendar calTemp = Calendar.getInstance();
        String anio = String.valueOf(calTemp.get(Calendar.YEAR));
        
        // Si es procuraduria consulta el ultimo numero de expediente no importando el area
        ConfInstitucion confInstitucion = confInstitucionDAO.consultarInsitucionActual();
        if(confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId()) && idArea != Areas.AGENCIA_DEL_MINISTERIO_PUBLICO.ordinal())
        	ultimoNumero = expedienteDAO.obtenerUltimoNumeroDeExpediente(null);
        else
        	ultimoNumero = expedienteDAO.obtenerUltimoNumeroDeExpediente(idArea);
        logger.debug("ultimo Numero : : : " + ultimoNumero );
        
        if (ultimoNumero != null && idArea != Areas.DEPARTAMENTO_SALA.ordinal()) {
            consecutivo = ultimoNumero.substring(0,5);
            incremento = Integer.parseInt(consecutivo) + 1;
            incrementoString = String.valueOf(incremento);
//            incrementoString = ConsecutivosUtil.incrementarConsecutivo(consecutivo, 5);
        } 
        else if (ultimoNumero == null && idArea != Areas.DEPARTAMENTO_SALA.ordinal())
        	incrementoString = "00001";
        else {
        	incrementoString = idArea == Areas.DEPARTAMENTO_CAUSA.ordinal()?ultimoNumero.substring(0,5):"00001";
//        	incrementoString = ConsecutivosUtil.incrementarConsecutivo(null, 5);
        }

        incrementoString = StringUtils.leftPad(incrementoString, 5, "0");
        
        logger.debug("Incremento string  ::  " + incrementoString );
        //YUC se hace referencia al mismo servicio que consulta el prefijo del estado.
        entidadFederativa = asignarNumeroCasoService.obtenerPrefijoDelEstado();
        
        String numeroExpediente = null;
        
        if (!institucion.equalsIgnoreCase("DE")){
        	numeroExpediente = libre
                    + entidadFederativa
                    + institucion
                    + (aoUsuarioFirmado != null && aoUsuarioFirmado.getFuncionario() != null && aoUsuarioFirmado.getFuncionario().getDiscriminante() != null && aoUsuarioFirmado.getFuncionario().getDiscriminante().getDistrito() != null ? aoUsuarioFirmado.getFuncionario().getDiscriminante().getDistrito().getClaveDistrito() : "--");
        	if(confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())||confInstitucion.getConfInstitucionId().equals(Instituciones.PJ.getValorId()) ){
        		numeroExpediente=numeroExpediente+ (aoUsuarioFirmado != null && aoUsuarioFirmado.getFuncionario() != null 
        				&& aoUsuarioFirmado.getFuncionario().getDiscriminante() != null 
        				&& aoUsuarioFirmado.getFuncionario().getDiscriminante().getClave() != null ? 
        						aoUsuarioFirmado.getFuncionario().getDiscriminante().getClave() : "---");
        	}else{
        		numeroExpediente=numeroExpediente+"000";
        	}

            numeroExpediente=numeroExpediente+ anio+ incrementoString;
        }
        
        numeroExpediente= incrementoString + "/" + anio;
        
        logger.debug("Numero Expediente ::::::::::   " + numeroExpediente);
        
        return numeroExpediente;
    	
	}
    
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String obtenerNumExpXExpIdAreaId(ExpedienteDTO expediente, Long areaId)
    		throws NSJPNegocioException {
    	String numeroExpediente = "";
    	logger.debug("expediente ID : : " + expediente.getExpedienteId() + " area ID : : " + areaId);
    	
        Long numeroExpedienteId = numeroExpedienteDAO.obteneUltimoNumeroExpedienteIdXAreaExpId(
    			expediente.getExpedienteId(), areaId);
        
    	if(numeroExpedienteId!=null){
    		numeroExpediente = numeroExpedienteDAO.read(numeroExpedienteId).getNumeroExpediente();
    		logger.debug(" No  Expediente : : " + numeroExpediente);
    	}
    	else{
    		numeroExpediente = obtenerNumeroExpedienteAlterno(expediente.getUsuario(), null);	
    	}
    	
    	return numeroExpediente;
    }
    
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public synchronized ExpedienteDTO asignarNumeroExpedienteDefensoria(
            ExpedienteDTO input) throws NSJPNegocioException {
        if (logger.isDebugEnabled()){
            logger.debug("inputExpediente :: "+ input);
        }
        
        if (input.getArea() == null || input.getArea().getAreaId() == null||input.getUsuario()==null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        
        // OJO - VERIFICAR SI NO SE NECESITA EN ESTE FLUJO, LA CONDICIONAL DEL N�MERO DE EXPEDIENTE ALTERNO
        // obtenerNumeroExpediente(); � obtenerNumeroExpedienteAlterno();
        // No se utiliza si es exclusivamente para defensor�a - PUSH ATENTION -
        String strNumeroExpediente = obtenerNumeroExpediente(Areas.DEFENSORIA.parseLong(), input.getUsuario());
        
        logger.debug("Num Expediente Generado : : : " +  strNumeroExpediente);
        Calendar calendar = Calendar.getInstance();
        Expediente expediente = null;
        if(input.getExpedienteId() == null){
        	expediente = new Expediente();
        }else{
        	expediente = ExpedienteTransformer.transformarExpedienteBasicoDefensoria(input);
        }
        expediente.setFechaCreacion(new Date());
        NumeroExpediente numeroExpediente = new NumeroExpediente();
        
        numeroExpediente.setNumeroExpediente(strNumeroExpediente);
        numeroExpediente.setFechaApertura(calendar.getTime());
        numeroExpediente.setEstatus(new Valor(EstatusExpediente.ABIERTO.getValorId()));
        numeroExpediente.setJerarquiaOrganizacional(new JerarquiaOrganizacional(Areas.DEFENSORIA.parseLong()));
        numeroExpediente.setFuncionario( new Funcionario( input.getUsuario().getFuncionario().getClaveFuncionario()));
        
        if (input.getFechaApertura()==null){
        	input.setFechaApertura(calendar.getTime());
        }

        if(input.getTipoExpediente() != null){
        	numeroExpediente.setTipoExpediente(new Valor(input.getTipoExpediente().getIdCampo()));
        }
        
        if(input.getEtapa() != null){
        	numeroExpediente.setEtapa(new Valor(input.getEtapa().getIdCampo()));
        	Long idEtapa = input.getEtapa().getIdCampo();
        	numeroExpediente.setEtapa(new Valor(idEtapa));
        	
        	Long idEstatus = EstatusExpediente.ABIERTO_RESTAURATIVA.getValorId();
        	switch(EtapasExpediente.getByValor(idEtapa)){
    			case ASESORIA:
    				idEstatus = EstatusExpediente.ABIERTO.getValorId();
    				break;
        		case CONCILIACION_Y_MEDIACION:
        			idEstatus = EstatusExpediente.ABIERTO_RESTAURATIVA.getValorId();
        			break;
        		case INTEGRACION:
        			idEstatus = EstatusExpediente.ABIERTO_INTEGRACION.getValorId();
        			break;
        		case TECNICA:
        			idEstatus = EstatusExpediente.ABIERTO_TECNICA_SIN_CARPETA.getValorId();
        			break;
        		case EJECUCION:
        			idEstatus = EstatusExpediente.ABIERTO_EJECUCION.getValorId();
        			break;
        	}
        	numeroExpediente.setEstatus(new Valor(idEstatus));
        }
        
        if (input.getExpedienteId() == null) {
            if (input.getCasoDTO() != null){
            	if(input.getCasoDTO().getCasoId() != null) {
            		expediente.setCaso(new Caso(input.getCasoDTO().getCasoId()));
            	}else{
            		Caso caso = casoDao.obtenerCasoByNumeroGeneral(input.getCasoDTO().getNumeroGeneralCaso());
            		if(caso != null){
            			expediente.setCaso(caso);
            		}
            	}
            }
        	expediente.setNumeroExpediente(strNumeroExpediente);
        	
        	if(expediente.getExpedienteId() != null){
        		expedienteDAO.update(expediente);
        	}else{
        		expediente.setExpedienteId(expedienteDAO.create(expediente));
        	}
        	input.setExpedienteId(expediente.getExpedienteId());
        }
        numeroExpediente.setExpediente(new Expediente(input.getExpedienteId()));
        
        Long idNumeroExpediente = this.noExpDao.create(numeroExpediente);

        input.setNumeroExpediente(strNumeroExpediente);
        input.setNumeroExpedienteId(idNumeroExpediente);
        return input;
    }
    
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public synchronized ExpedienteDTO asignarNumeroExpediente(
            ExpedienteDTO inputExpediente) throws NSJPNegocioException {
    	//Permite saber si es necesario usar el mismo numero de expediente para procuraduraia
    	boolean esMismoNumeroExpediente = false;
    	boolean esNull=true;
    	
        logger.info("Inicia - asignarNumeroExpediente(...)");
        
        //logger.info("Clave Romana Distrito : : : " +  inputExpediente.getUsuario().getFuncionario().getDiscriminante().getDistrito().getClaveRomanaDistrito());
		//logger.info("CatDiscriminante" + inputExpediente.getUsuario().getFuncionario().getDiscriminante().getClave());
        
        if (logger.isDebugEnabled()){
            logger.debug("inputExpediente :: "+ inputExpediente);
        }
        if (inputExpediente.getArea() == null
        		|| inputExpediente.getArea().getAreaId() == null) {
        	throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        
        if (inputExpediente.getUsuario() == null
				|| inputExpediente.getUsuario().getFuncionario() == null
				|| inputExpediente.getUsuario().getFuncionario()
						.getClaveFuncionario() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
        
        ExpedienteDTO nuevoExp = new ExpedienteDTO();
        String numeroExpediente  = null;

        /* Si ya tiene un numero de expediente para el area, ya no se debe de generar uno nuevo,
        por el contrario se debe de consultar. Solo aplica para la institucion de PGJ */
        ConfInstitucion confInstitucion = confInstitucionDAO.consultarInsitucionActual();
        logger.debug("EL valor de confInstitucion :: "+ inputExpediente);
        if(inputExpediente.getExpedienteId() != null && confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId()) ){
            logger.debug("Area_ID :: "+ inputExpediente.getArea().getAreaId() + "  Expediente ID : : " + inputExpediente.getExpedienteId());
            logger.debug("inputExpediente No. Exp :  : " + inputExpediente.getNumeroExpediente());
            logger.debug("Jerarquia Abreviatura :: : : " + confInstitucion.getMonograma());
        	//Buscar el ultimo numero de expediente asociado al expedidiente y por area.
        	//Regresa nulo en caso de que no exista un numero expediente asociado a esa area
        	Expediente expediente = expedienteDAO.buscarUltimoNumeroPorExpedienteIdAreaId(
        			inputExpediente.getExpedienteId(), 
        			inputExpediente.getArea().getAreaId());
            logger.debug("EL Expediente :: "+ expediente);
            logger.debug("Area ID :: "+ inputExpediente.getArea().getAreaId() + "  Expediente ID : : " + inputExpediente.getExpedienteId());
            
        	if(expediente !=null ){
        		nuevoExp.setNumeroExpediente(expediente.getNumeroExpediente());
        		nuevoExp.setNumeroExpedienteId(expediente.getNumeroExpedienteId());
        		logger.info("##Datos NEX - NumeroExpediente: "+ nuevoExp.getNumeroExpediente() + " - NumeroExpedienteId:  " + nuevoExp.getNumeroExpedienteId());
        		
        		if(Areas.AGENCIA_DEL_MINISTERIO_PUBLICO.ordinal() != inputExpediente.getArea().getAreaId() && inputExpediente.getUsuario().getAreaActual().getAreaId() != Areas.COORDINACION_ATENCION_VICTIMAS.ordinal() ){
        			//Codigo para generar nueva nomenclatura en la Unidad de Investigaci�n
        			logger.info("Num. Expediente " + nuevoExp.getNumeroExpediente());
        			return nuevoExp;
        		}else{
        			//Si es AGENCIA_DEL_MINISTERIO_PUBLICO -> Sistema Tradicional
        			esMismoNumeroExpediente = true;
        		}
        		
        	}else{
        		//PASO NECESARIO PARA UNIFICAR EL NUMERO DE EXPEDIENTE EN PROCURADURIA
        		expediente = expedienteDAO.buscarUltimoNumeroPorExpedienteIdAreaId(
            			inputExpediente.getExpedienteId(), null);
        		
        		if(expediente !=null ){
            		nuevoExp.setNumeroExpediente(expediente.getNumeroExpediente());
            		logger.debug("Num Exp. :: " + expediente.getNumeroExpediente());
            		nuevoExp.setNumeroExpedienteId(expediente.getNumeroExpedienteId());
            		logger.debug("No. Exp. ID  : : : " + expediente.getNumeroExpedienteId());
            		esMismoNumeroExpediente = true;
            	}
        		
        	}
        }
        
        Expediente expediente = new Expediente();
        expediente.setPertenceConfInst(confInstitucionDAO.consultarInsitucionActual());

        expediente.setFechaCreacion(new Date());


        Calendar calTemp = Calendar.getInstance();
        if (inputExpediente.getFechaApertura()!=null){
        	calTemp.setTime(inputExpediente.getFechaApertura()); 
        }else{
        	calTemp.setTime(new Date());
        	inputExpediente.setFechaApertura(new Date());
        }
        UsuarioDTO loUsuarioFirmado = inputExpediente.getUsuario();
//        String numeroExpediente  = null;
        
    	//Se obtiene configuracion para determinar el algoritmo de generacion
    	//del numero de expediente
    	Parametro parametro = parametroDAO.obtenerPorClave(Parametros.NUMERO_EXPEDIENTE_ALTERNO);  
    	logger.info("Parametro:"+ parametro + " Valor Parametro : : " + parametro.getValor());

    	//Si esta prendida la bandera, se genera el numero de expediente alterno
    	if(parametro != null && parametro.getValor() != null && parametro.getValor().equals("1") && !esMismoNumeroExpediente){
    		ConfInstitucion institucionActual = parametroDAO.consultarInsitucionActual();
    		logger.info("institucionActual:"+ institucionActual.getMonograma());
    		
        	//Si es Fiscalia y la configuraci�n del Parametro Numero alterno 
        	//esta prendida, se ejecuta dicho algoritmo.
        	//Caso contrario continua con la generacion normal
    		if (institucionActual.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())) {
    			
    			// Si el expediente trae la jerarqu�a UI y tiene al menos un n�mero de expediente
    			// asociado con jerarqu�a Policia Ministerial, se debe conservar el �ltimo n�mero 
    			// de expediente, en caso contrario, es el flujo normal.
    			if(inputExpediente.getArea().getAreaId()==Areas.UNIDAD_INVESTIGACION.ordinal()){

        			numeroExpediente = obtenerNumExpXExpIdAreaId(inputExpediente,new Long(Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal()));

    			}
    			// Si el expediente trae la jerarqu�a Policia Mninisterial y tiene al menos un n�mero 
    			// de expediente asociado con la jerarqu�a UI, se debe conservar el �ltimo n�mero de
    			// expediente, en caso contrario, es el flujo normal.
    			else if(inputExpediente.getArea().getAreaId()==Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal()){

        			numeroExpediente = obtenerNumExpXExpIdAreaId(inputExpediente,new Long(Areas.UNIDAD_INVESTIGACION.ordinal()));

    			}
   				else{
   					numeroExpediente = obtenerNumeroExpedienteAlterno(inputExpediente.getUsuario(), null);
    			}
        	} else{
				numeroExpediente = obtenerNumeroExpediente(inputExpediente.getArea().getAreaId(),loUsuarioFirmado);
			}
    	} 
    	else{ //Se genera el numero de expediente normalmente 
	        if(esMismoNumeroExpediente == true){
	        	numeroExpediente = obtenerNumeroExpedienteAlterno(inputExpediente.getUsuario(), nuevoExp);
	        }
	        else{
	        	numeroExpediente = obtenerNumeroExpediente(inputExpediente.getArea().getAreaId(),loUsuarioFirmado);
	        }
    	}
    	
        logger.info("el numeroExpediente GENERADO ES: " + numeroExpediente);

        expediente.setNumeroExpediente(numeroExpediente);

        if (inputExpediente.getCasoDTO() != null
                && inputExpediente.getCasoDTO().getCasoId() != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Asociando al caso con id :: "
                        + inputExpediente.getCasoDTO().getCasoId());
            }
            expediente.setCaso(new Caso(inputExpediente.getCasoDTO()
                    .getCasoId()));
        }
        

        this.adminActividadesService.generarActividad(inputExpediente, nuevoExp, expediente);

        NumeroExpediente noExpBD = new NumeroExpediente();
        noExpBD.setNumeroExpediente(numeroExpediente);
        noExpBD.setNumExpAlterno(numeroExpediente);
        
        noExpBD.setFechaApertura(new Date());

        if (inputExpediente.getExpedienteId() == null) {
            nuevoExp.setExpedienteId(expediente.getExpedienteId());
            noExpBD.setExpediente(new Expediente( expediente.getExpedienteId()));
        } else {
            nuevoExp.setExpedienteId(inputExpediente.getExpedienteId());
            noExpBD.setExpediente(new Expediente(inputExpediente.getExpedienteId()));
        }
        if (inputExpediente.getCausaPadre()!=null){
            noExpBD.setNumeroExpedientePadre(new NumeroExpediente(inputExpediente.getCausaPadre().getNumeroExpedienteId()));
            noExpBD.setTipoExpediente(new Valor(inputExpediente.getTipoExpediente().getIdCampo()));
        }
        
        if(inputExpediente!=null)
        	if(inputExpediente.getUsuario()!=null)
        		if(inputExpediente.getUsuario().getAreaActual()!=null)
        			if(inputExpediente.getUsuario().getAreaActual().getAreaId()!=null)
        				esNull=false;
        if(!esNull){
            if(inputExpediente.getUsuario().getAreaActual().getAreaId() == Areas.COORDINACION_ATENCION_VICTIMAS.ordinal())
            	noExpBD.setJerarquiaOrganizacional(new JerarquiaOrganizacional(inputExpediente.getUsuario().getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId()));
            else
            	noExpBD.setJerarquiaOrganizacional(new JerarquiaOrganizacional(inputExpediente.getArea().getAreaId()));
        }else{
        	noExpBD.setJerarquiaOrganizacional(new JerarquiaOrganizacional(inputExpediente.getArea().getAreaId()));
        }

        	
        noExpBD.setFuncionario( new Funcionario( inputExpediente.getUsuario().getFuncionario().getClaveFuncionario()));
        if (inputExpediente.getTipoExpediente()!=null && inputExpediente.getTipoExpediente().getIdCampo()!=null){
        	noExpBD.setTipoExpediente(new Valor(inputExpediente.getTipoExpediente().getIdCampo()));
        }
        if (inputExpediente.getEstatus()!=null && inputExpediente.getEstatus().getIdCampo()!=null){
            noExpBD.setEstatus(new Valor(inputExpediente.getEstatus().getIdCampo()));
        }
        
        /*Enable GB seteo estatus expediente para sentencia en reinsersionsocial*/
        ConfInstitucion confInstitucionActual = confInstitucionDAO.consultarInsitucionActual();
        logger.info("confInstitucion ::\n"+confInstitucionActual);
        if(confInstitucionActual != null && confInstitucionActual.getConfInstitucionId().equals(Instituciones.RS.getValorId())){ //si es reinsercion social
        	noExpBD.setTipoExpediente(new Valor(TipoExpediente.CARPETA_DE_EJECUCION.getValorId()));
            noExpBD.setEstatus(new Valor(EstatusExpediente.POR_ATENDER.getValorId()));
        }
        
        Long idNoExpNuevo = this.noExpDao.create(noExpBD);

        nuevoExp.setNumeroExpediente(numeroExpediente);
        nuevoExp.setNumeroExpedienteId(idNoExpNuevo);
        nuevoExp.setFechaApertura(inputExpediente.getFechaApertura());
        if (inputExpediente.getEstatus() != null
                && inputExpediente.getEstatus().getIdCampo() != null) {
            nuevoExp.setEstatus(new ValorDTO(inputExpediente.getEstatus()
                    .getIdCampo()));
        }
        //Bandera para el numero de expediente
        nuevoExp.setEsNuevo(true);
    
        return nuevoExp;
    }

    @Override
    public Boolean asignarNumerosDeExpediente(
    		List<ExpedienteDTO> listaExpedientes) throws NSJPNegocioException {

    	if(listaExpedientes==null || listaExpedientes.isEmpty()){
    		return false;
    	}
    	
    	for (ExpedienteDTO inputExpediente : listaExpedientes) {
            if (!(inputExpediente.getArea() == null || inputExpediente.getArea().getAreaId() == null ||
                	inputExpediente.getUsuario() == null || inputExpediente.getUsuario().getFuncionario() == null ||
                	inputExpediente.getUsuario().getFuncionario().getClaveFuncionario() == null)) {
            	
                asignarNumeroExpediente(inputExpediente);
								 
				Actividad act=new Actividad();
				act.setExpediente(new Expediente(inputExpediente.getExpedienteId()));
				act.setFuncionario(new Funcionario(inputExpediente.getUsuario().getFuncionario().getClaveFuncionario() ));
				act.setTipoActividad(new Valor(Actividades.ATENDER_CANALIZACION_UI.getValorId()));
				act.setFechaCreacion(new Date());
				
				actividadDAO.create(act);

            }            
		}    	
    	
    	return true;
    }
    
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public synchronized ExpedienteDTO asignarNumeroExpedienteTipo(
            ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
        if (logger.isDebugEnabled())
            logger.debug("/**** ASIGNAR NUMERO DE EXPEDIENTE DEL TIPO ****/");

        String ultimoNumero = "";
        String libre = "NSJ";
        //YUC
        String entidadFederativa = "";
        String institucion = "DEFE";
        String numeroExpediente = "";
        Long expedienteId = new Long(0);

        Calendar calTemp = Calendar.getInstance();
        String anio = String.valueOf(calTemp.get(Calendar.YEAR));
        
        //YUC se hace referencia al mismo servicio que consulta el prefijo del estado.
        entidadFederativa = asignarNumeroCasoService.obtenerPrefijoDelEstado();
        
        numeroExpediente = libre + entidadFederativa + institucion + anio;

        Expediente expediente = ExpedienteTransformer
                .transformarExpediente(expedienteDTO);

        ultimoNumero = expedienteDAO.obtenerUltimoNumero(1L); //TODO CAS Quitar codigo duro al enviar el idArea        
        if (expediente instanceof ExpedienteAdscrito) {
            ExpedienteAdscrito expedienteAd = (ExpedienteAdscrito) expediente;
            numeroExpediente = numeroExpediente
                    + formatoUltimoNumero(ultimoNumero);
            expedienteAd.setNumeroExpediente(numeroExpediente);
            expedienteId = expedienteAdscritoDAO.create(expedienteAd);
            logger.debug("ExpedienteAdscrito");
        } else if (expediente instanceof ExpedienteTecnico) {
            ExpedienteTecnico expedienteTec = (ExpedienteTecnico) expediente;
            numeroExpediente = numeroExpediente
                    + formatoUltimoNumero(ultimoNumero);
            expedienteTec.setNumeroExpediente(numeroExpediente);
            expedienteId = expedienteTecnicoDAO.create(expedienteTec);
            logger.debug("ExpedienteTecnico");
        } else if (expediente instanceof ExpedienteRestDefensoria) {
            ExpedienteRestDefensoria expedienteRest = (ExpedienteRestDefensoria) expediente;
            numeroExpediente = numeroExpediente
                    + formatoUltimoNumero(ultimoNumero);
            expedienteRest.setNumeroExpediente(numeroExpediente);
            expedienteId = expRestDefensoriaDAO.create(expedienteRest);
            logger.debug("ExpedienteRestDefensoria");
        }
        return new ExpedienteDTO(expedienteId);
    }

    public String formatoUltimoNumero(String ultimoNumero) {
        String incrementoString = null;
        if (ultimoNumero != null) {
            String consecutivo = ultimoNumero.substring(
                    ultimoNumero.length() - 5, ultimoNumero.length());

            incrementoString = ConsecutivosUtil.incrementarConsecutivo(
                    consecutivo, 6);
        } else {
            logger.debug("Comienza el incremento");
            incrementoString = ConsecutivosUtil.incrementarConsecutivo(null, 6);
        }

        // rellenar con ceros a la izq
        StringUtils.leftPad(incrementoString, 5, "0");

        return incrementoString;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public synchronized ExpedienteDTO asignarNumeroExpediente(TurnoDTO turno)
            throws NSJPNegocioException {

        logger.debug("turno en asignarNumeroExpediente(TurnoDTO turno):: " + turno);
        if (turno.getUsuario() == null
                || turno.getUsuario().getIdUsuario() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        // se crea el caso como paliativo, debe existir un boton que genere el caso
        final CasoDTO casoReq = new CasoDTO();
        casoReq.setFechaApertura(new Date());
        casoReq.setEstatus(EstatusCaso.INVESTIGACION);
        //Enable IT: Se elimina la creacion de Caso para Jerarquia 44, req 64
        CasoDTO niuCaso = new CasoDTO();
        
        if (!turno.getUsuario().getAreaActual().getAreaId().equals(Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong())){
        	niuCaso = this.casoService.asignarNumeroCaso(casoReq,turno.getUsuario().getFuncionario());
        }
        ExpedienteDTO expParam = new ExpedienteDTO();
        expParam.setFechaApertura(new Date());
        expParam.setUsuario(turno.getUsuario());
        expParam.setArea(turno.getExpediente().getArea());
        expParam.setCasoDTO(niuCaso);
        logger.debug("Antes de llamar a asignarNumeroExpediente(expParam)");
        ExpedienteDTO expNuevo = asignarNumeroExpediente(expParam);
        logger.debug("Despues de llamar a asignarNumeroExpediente(expParam)");

        Turno tnoBD = this.turnoDao.read(turno.getTurnoId());
        tnoBD.setExpediente(new Expediente(expNuevo.getExpedienteId()));
        tnoBD.setUsuario(new Usuario(turno.getUsuario().getIdUsuario()));
        tnoBD.setFechaAtencion(new Date());
        tnoBD.setEstatus(new Valor(EstatusTurno.ATENDIDO.getValorId()));

        this.turnoDao.update(tnoBD);
        expNuevo.setCasoDTO(niuCaso);
        return expNuevo;
    }
    
    private FuncionarioDTO obtenerFuncionario() {     
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		DepartamentoDTO departamento = new DepartamentoDTO();
		departamento.setDepartamentoId(13L);//Robos
		AreaDTO area = new AreaDTO();		
		area.setAreaId(1L);// Atencion temprana administrativa 
		departamento.setArea(area);
		funcionarioDTO.setDepartamento(departamento);
		return funcionarioDTO;
	}

    /*
     * (non-Javadoc)
     * @see mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService#asignarNumeroExpedienteASolicitud(java.lang.Long, java.lang.Long)
     */
	@Override
	public void asignarNumeroExpedienteASolicitud(Long numeroExpedienteId,
			Long solicitudId) throws NSJPNegocioException{
		Solicitud sol = solicitudDAO.read(solicitudId);
		if(sol != null){
			sol.setNumeroExpediente(new NumeroExpediente(numeroExpedienteId));
			solicitudDAO.saveOrUpdate(sol);
		}
	}
	

	 @Override
	 @Transactional(isolation = Isolation.READ_COMMITTED)
	 public synchronized List<ExpedienteDTO> asignarNumeroExpedienteAuditoria(List<ExpedienteDTO> listaNumeroExpedienteAuditados) throws NSJPNegocioException{
		 List<ExpedienteDTO> numeroAuditoriaNuevosDTO = new ArrayList<ExpedienteDTO>();

		 if (listaNumeroExpedienteAuditados== null || listaNumeroExpedienteAuditados.isEmpty())
	        	throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		 
		 for (ExpedienteDTO expedienteDTO : listaNumeroExpedienteAuditados) {
			 //Se requiere: Area, Clave del Funcionario Visitador, IdNumeroExpediente a Auditar.  
			 if(expedienteDTO==null || expedienteDTO.getNumeroExpedienteId()==null || expedienteDTO.getNumeroExpedienteId()<0 ||
					 expedienteDTO.getArea()==null || expedienteDTO.getArea().getAreaId()==null ||
					 expedienteDTO.getUsuario()==null || expedienteDTO.getUsuario().getFuncionario()==null || 
					 expedienteDTO.getUsuario().getFuncionario().getClaveFuncionario()==null || 
					 expedienteDTO.getTipoExpediente()==null || expedienteDTO.getTipoExpediente().getIdCampo()==null)
				 throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			 
			//Se requiere consultar el expediente Id para generar la actividad nueva.
			Expediente expediente = expedienteDAO.consultarExpedientePorIdNumerExpediente(expedienteDTO.getNumeroExpedienteId());
			ExpedienteDTO expDTO = ExpedienteTransformer.transformaExpediente(expediente);
			
			List<NumeroExpediente> numExpendiente = noExpDao.consultarNumeroExpedientesXExpediente(expediente.getExpedienteId());
			for (NumeroExpediente numeroExpediente : numExpendiente) {
				expDTO.setNumeroExpediente(numeroExpediente.getNumeroExpediente());
			}
			
			Usuario usuario = usuarioDAO.consultarUsuarioPorClaveFuncionario(expedienteDTO.getUsuario().getFuncionario().getClaveFuncionario());
			UsuarioDTO usuarioDTO = UsuarioTransformer.transformarUsuario(usuario);
			
			String numeroExpediente = "";
	    	Parametro parametro = parametroDAO.obtenerPorClave(Parametros.NUMERO_EXPEDIENTE_ALTERNO);
	    	Boolean numExpAlterno = false;
	    		    	
	    	//Si esta prendida la bandera, se genera el numero de expediente alterno
	    	if(parametro != null && parametro.getValor() != null
					&& parametro.getValor().equals("1")){
	    		ConfInstitucion institucionActual = parametroDAO.consultarInsitucionActual();
	    		
	        	//Si es Fiscalia y la configuraci�n del Parametro Numero alterno esta prendida, se ejecuta dicho algoritmo.
	        	//Caso contrario continua con la generacion normal
	    		if (institucionActual.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())) {
					numeroExpediente = obtenerNumeroExpedienteAlternoUnidadVisitaduria(usuarioDTO, expDTO);
					numExpAlterno = true;
	        	} else{
					numeroExpediente = obtenerNumeroExpediente(expedienteDTO.getArea().getAreaId(), expedienteDTO.getUsuario());
				}
	    	} 
	    	else{
	    		numeroExpediente = obtenerNumeroExpediente(expedienteDTO.getArea().getAreaId(), expedienteDTO.getUsuario());
	    	}
			expediente.setNumeroExpediente(numeroExpediente);
			
			ExpedienteDTO nuevoExp = new ExpedienteDTO();
			//Generar Actividad
			ActividadDTO actividadDTO = new ActividadDTO();
			actividadDTO.setTipoActividad(Actividades.NOTIFICAR_AUDITORIA);
			expedienteDTO.setExpedienteId( expediente.getExpedienteId());
			actividadDTO = adminActividadesService.generarActividadAExpediente(expedienteDTO, actividadDTO);
			nuevoExp.setActividadActual(actividadDTO);
			
			NumeroExpediente noExpBD = new NumeroExpediente();
	        noExpBD.setNumeroExpediente(numeroExpediente);
	        noExpBD.setFechaApertura(new Date());
            noExpBD.setExpediente(new Expediente( expediente.getExpedienteId()));
            noExpBD.setJerarquiaOrganizacional(new JerarquiaOrganizacional(expedienteDTO.getArea().getAreaId()));
            noExpBD.setFuncionario( new Funcionario( expedienteDTO.getUsuario().getFuncionario().getClaveFuncionario()));
            noExpBD.setTipoExpediente(new Valor (expedienteDTO.getTipoExpediente().getIdCampo()));
            
            if(numExpAlterno==true){
            	noExpBD.setNumExpAlterno(numeroExpediente);
            }

            nuevoExp.setExpedienteId(expediente.getExpedienteId());
            
            Long idNoExpNuevo = this.noExpDao.create(noExpBD);

            //Registrar en la relacion de NumExpedienteAuditoria
            RelNumExpedienteAuditoria auditoria = new RelNumExpedienteAuditoria();
            auditoria.setNumeroExpediente(new NumeroExpediente(expedienteDTO.getNumeroExpedienteId()));
    		auditoria.setNumeroAuditoriaId(idNoExpNuevo);
    		
    		Long idAuditoria = relNumExpedienteAuditoriaDAO.create(auditoria);
    		logger.info(" IdAuditoria Creado:"+ idAuditoria);
            
    		nuevoExp.setExpedienteId(expediente.getExpedienteId());
            nuevoExp.setNumeroExpediente(numeroExpediente);
            nuevoExp.setNumeroExpedienteId(idNoExpNuevo);
            nuevoExp.setFechaApertura(expedienteDTO.getFechaApertura());
            
            Valor tipoExp = new Valor();
            
            tipoExp = valorDAO.read(expedienteDTO.getTipoExpediente().getIdCampo());
            
            if(tipoExp != null){
            	
            	ValorDTO valorTipo = new ValorDTO();
            	
            	if(tipoExp.getValor() != null){
            		valorTipo.setValor(tipoExp.getValor());
            	}
            	if(tipoExp.getValorId() != null){
            		valorTipo.setIdCampo(tipoExp.getValorId());
            	}
            	 nuevoExp.setTipoExpediente(valorTipo);
            }
            //Se agrega a la lista
            numeroAuditoriaNuevosDTO.add(nuevoExp);
		 }
		 
		 return numeroAuditoriaNuevosDTO;
	 }

	 
	 @Override
	 @Transactional
	 public ExpedienteDTO asignarNumeroExpedienteCarpetaEjecucion(Long expedienteId) throws NSJPNegocioException{
		 logger.info("Inicia - asignarNumeroExpedienteCarpetaEjecucion("+ expedienteId+")");
		 if (expedienteId== null || expedienteId<0)
	        	throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		 
		//Se obtiene el expediente completo
		NumeroExpediente numeroExpCausa = noExpDao.consultarNumeroExpedienteXExpedienteId(expedienteId);
		if(numeroExpCausa==null | numeroExpCausa.getJerarquiaOrganizacional()==null || 
				numeroExpCausa.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId()==null ||
				numeroExpCausa.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId() < 0 ||
				numeroExpCausa.getFuncionario()==null || numeroExpCausa.getFuncionario().getClaveFuncionario()==null ||
				numeroExpCausa.getFuncionario().getClaveFuncionario() < 0 ||
				numeroExpCausa.getNumeroExpedienteId()==null || numeroExpCausa.getNumeroExpedienteId()<0 )
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		
		//Que se necesita: Area, ID=null, CausaPadre (numeroExpedienteID), TipoExpediente, Estatus y Funcionario
		ExpedienteDTO expedienteDTOGenerar = new ExpedienteDTO();
		expedienteDTOGenerar.setArea(new AreaDTO(
				numeroExpCausa.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId()));
		expedienteDTOGenerar.setExpedienteId(null);
		
		ExpedienteDTO causaPadre = new ExpedienteDTO(expedienteId);
		causaPadre.setNumeroExpedienteId(numeroExpCausa.getNumeroExpedienteId());
		causaPadre.setNumeroExpediente(numeroExpCausa.getNumeroExpediente());
		expedienteDTOGenerar.setCausaPadre(causaPadre);

		if( numeroExpCausa.getExpediente()!= null && numeroExpCausa.getExpediente().getCaso()!= null){
			logger.info("Caso Id#" + numeroExpCausa.getExpediente().getCaso().getCasoId());
			expedienteDTOGenerar.setCasoDTO(new CasoDTO(numeroExpCausa.getExpediente().getCaso().getCasoId()));
		}
		
		expedienteDTOGenerar.setTipoExpediente(new ValorDTO(TipoExpediente.CARPETA_DE_EJECUCION.getValorId()));
		expedienteDTOGenerar.setEstatus(new ValorDTO(EstatusExpediente.ABIERTO.getValorId()));
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setFuncionario(new FuncionarioDTO( numeroExpCausa.getFuncionario().getClaveFuncionario()));
		expedienteDTOGenerar.setUsuario(usuarioDTO);
        
		//Generar el Expediente, Numero de Expediente y Actividad
		ExpedienteDTO carpetaEjecucion = asignarNumeroExpediente(expedienteDTOGenerar);
		
		logger.info("El expediente recien creado es " + carpetaEjecucion.getExpedienteId());
       	
        return carpetaEjecucion;
	 }

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService#obtenerNumeroExpedienteAlterno(mx.gob.segob.nsjp.dto.usuario.UsuarioDTO)
	 */
	@Override
	public String obtenerNumeroExpedienteAlterno(UsuarioDTO usuario, ExpedienteDTO expediente) throws NSJPNegocioException {
		
		String numExpediente = null;
		
		if (usuario.getFuncionario() == null 
				|| usuario.getFuncionario().getDiscriminante() == null
				|| usuario.getFuncionario().getDiscriminante().getDistrito() == null
				|| usuario.getFuncionario().getDiscriminante().getDistrito().getClaveRomanaDistrito() == null
				|| usuario.getFuncionario().getDiscriminante().getDistrito().getClaveRomanaDistrito().isEmpty()){
			throw new NSJPNegocioException(CodigoError.CLAVE_ROMANA_DISTRITO_INEXISTENTE);
		}
		
		if (usuario.getFuncionario().getJerarquiaOrganizacional() == null
				|| usuario.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.debug("EXPEDIENTE ::::  " + expediente);
//		logger.debug("Numero Expediente ::  " + expediente.getNumeroExpediente() + "  No. Expediente ID  :: " + expediente.getNumeroExpedienteId());
		
		//TODO - ATE se debe de obtener la unidad, verificar si se trata de la jerarquia organizacional
		Long areaId =  usuario.getAreaActual().getAreaId(); //usuario.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId();
		logger.info(" areaId:"+usuario.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId());
		logger.info(" AreaidF:"+usuario.getAreaActual());
		logger.info(" AreaidF:"+usuario.getAreaActual().getAreaId());
				
		List<String> unidad = new ArrayList<String>();
		
		switch (Areas.values()[areaId.intValue()]) {

		case ATENCION_TEMPRANA_PG_PENAL:
			unidad.add(0, AcronimoNumExpAlterno.ATENCION_TEMPRANA_PG_PENAL.getAcronimo());
			break;

		case ATENCION_TEMPRANA_PG_NO_PENAL:
			unidad.add(0,AcronimoNumExpAlterno.ATENCION_TEMPRANA_PG_NO_PENAL.getAcronimo());
			break;

		case JUSTICIA_ALTERNATIVA_RESTAURATIVA:
			unidad.add(0,AcronimoNumExpAlterno.JUSTICIA_ALTERNATIVA_RESTAURATIVA.getAcronimo());
			break;

		case UNIDAD_INVESTIGACION:
		case COORDINACION_UNIDAD_INVESTIGACION:
			boolean error=false;
			if(usuario.getAreaActual().getAreaId().equals(usuario.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId())){
				try {
					unidad = consultarClavesUIE(usuario);
				} catch (Exception e) {
					// TODO: handle exception
					// controlando la excepcion por parametros insuficientes
					error=true;
					unidad.add(AcronimoNumExpAlterno.UNIDAD_INVESTIGACION.getAcronimo());
				}
			}
			if(!error){
				unidad.add(AcronimoNumExpAlterno.UNIDAD_INVESTIGACION.getAcronimo());
			}
			unidad.add(AcronimoNumExpAlterno.COORDINACION_POLICIA_MINISTERIAL.getAcronimo());
			break;
		case COORDINACION_POLICIA_MINISTERIAL:
			if(usuario.getAreaActual().getAreaId().equals(usuario.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId())){
				unidad = consultarClavesDeEspecialidadPM(usuario);
			}
			unidad.add(AcronimoNumExpAlterno.UNIDAD_INVESTIGACION.getAcronimo());
			break;
		case VISITADURIA:
			unidad.add(0,AcronimoNumExpAlterno.VISITADURIA.getAcronimo());
			break;
		case COORDINACION_ATENCION_VICTIMAS:
			unidad.add(0,AcronimoNumExpAlterno.COORDINACION_ATENCION_VICTIMAS.getAcronimo());
			break;
		case AGENCIA_DEL_MINISTERIO_PUBLICO:
			unidad.add(0,AcronimoNumExpAlterno.AGENCIA_DEL_MINISTERIO_PUBLICO.getAcronimo());
			break;	
		default:
			unidad.add(0,AcronimoNumExpAlterno.SIN_AREA.getAcronimo());
			break;
		}
		
		Integer anio = Calendar.getInstance().get(Calendar.YEAR);
		String distrito = usuario.getFuncionario().getDiscriminante().getDistrito().getClaveRomanaDistrito();
		String region = usuario.getFuncionario().getDiscriminante().getDistrito().getClaveDistrito();
		String agencia = usuario.getFuncionario().getDiscriminante().getClave(); 
		numExpediente = (expediente != null)?expediente.getNumeroExpediente():"";
		
		
		logger.info("REGIOOOOOOOOOOOON --------- " + region);
		logger.info("Clave Romana Distrito : : : " +  distrito);
		logger.info("CatDiscriminante Clave  AGENCIAAAAAAAAAAAAA -------- " + usuario.getFuncionario().getDiscriminante().getClave());
		
		//Se hace referencia al mismo servicio que consulta el prefijo del estado.
//        String monoEntFederativa = asignarNumeroCasoService.obtenerPrefijoDelEstado();
		
		//Se dejan en c&oacute;digo duro las variables para obtener el m&aacute;ximo consecutivo desde la BD. 
		//As&iacute; como el incremento del consecutivo.
		String noExpAlterno;
		if (areaId == Areas.UNIDAD_INVESTIGACION.ordinal() || areaId == Areas.COORDINACION_UNIDAD_INVESTIGACION.ordinal()){
			String posIni = noExpDao.consultarPosInicialNumExp(areaId, agencia);
			int iniPos = 0;
			if (posIni != null)
				iniPos = Integer.parseInt(posIni);
			noExpAlterno = noExpDao.obtenerNumeroExpedienteAlternoConsecutivo(8, 5, 1, unidad, region, anio.toString(),agencia, numExpediente, areaId);
		}
		else
			noExpAlterno = noExpDao.obtenerNumeroExpedienteAlternoConsecutivo(1, 5, 1, unidad, region, anio.toString(),agencia, numExpediente, areaId);
		
		//Se lleva a cabo la actualizacion del No. de expediente alterno.
//		NumeroExpediente noExpBase = noExpDao.read(expediente.getNumeroExpedienteId());
		
//		logger.info(noExpBase);
//		if (noExpBase != null ){
//			noExpBase.setNumExpAlterno(noExpAlterno);
//			noExpDao.merge(noExpBase);
//		}
		
		return noExpAlterno;
	}

	@Override
	public String obtenerNumeroExpedienteAlternoUnidadVisitaduria(UsuarioDTO usuario, ExpedienteDTO expediente) throws NSJPNegocioException {
		if (usuario.getFuncionario() == null 
				|| usuario.getFuncionario().getDiscriminante() == null
				|| usuario.getFuncionario().getDiscriminante().getDistrito() == null
				|| usuario.getFuncionario().getDiscriminante().getDistrito().getClaveRomanaDistrito() == null
				|| usuario.getFuncionario().getDiscriminante().getDistrito().getClaveRomanaDistrito().isEmpty()){
			throw new NSJPNegocioException(CodigoError.CLAVE_ROMANA_DISTRITO_INEXISTENTE);
		}
		
//		List<String> unidad = new ArrayList<String>();
//		unidad.add(0,AcronimoNumExpAlterno.VISITADURIA.getAcronimo());
		String unidad = AcronimoNumExpAlterno.VISITADURIA.getAcronimo();
		Integer anio = Calendar.getInstance().get(Calendar.YEAR);
		String distrito = usuario.getFuncionario().getDiscriminante().getDistrito().getClaveRomanaDistrito();		
        String monoEntFederativa = asignarNumeroCasoService.obtenerPrefijoDelEstado();		
//		String noExpAlterno = noExpDao.obtenerNumeroExpedienteAlternoConsecutivo(1, 5, 1, unidad, distrito, anio.toString(),monoEntFederativa, expediente.getNumeroExpediente());
		String noExpAlterno = noExpDaoImp.crearNumeroExpedienteAlterno(null, unidad, anio.toString(), distrito, monoEntFederativa, expediente.getNumeroExpediente());
		return noExpAlterno;
	}

	/**
	 * M&eacute;todo para obtener la clave catUIE del usuario y ponerlo en la posici&oacute;n cero
	 * de la lista, y adem&aacute;s obtener todo el cat&aacute;logo de claves UIEs sin repetir la del
	 * usuario
	 *  
	 * @param usuario
	 * @return lista de claves UIEs encontradas, en la posici&oacute;n cero, se encuentra la UIE
	 * a la que pertenece el usuario que se recibe como par&aacute;metro
	 * @throws NSJPNegocioException
	 */
	private List<String> consultarClavesUIE(UsuarioDTO usuario)
			throws NSJPNegocioException {

		if (usuario == null
				|| usuario.getFuncionario() == null
				|| usuario.getFuncionario().getUnidadIEspecializada() == null
				|| usuario.getFuncionario().getUnidadIEspecializada()
						.getClaveUIE() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<String> unidadesUIE = new ArrayList<String>();

		logger.info("CAT UIE DEL USUARIO="
				+ usuario.getFuncionario().getUnidadIEspecializada()
						.getClaveUIE());

		unidadesUIE.add(0, usuario.getFuncionario().getUnidadIEspecializada()
				.getAcronimo().trim());

		List<CatUIEspecializada> listaCatUIE = catUIEspecializadaDAO
				.consultarTodos();

		for (CatUIEspecializada catUIE : listaCatUIE) {
			if (!(catUIE.getAcronimo().toString().trim().equals(unidadesUIE
					.get(0).toString().trim()))) {
				logger.info("CAT UIE AGREGANDO A LA LISTA="
						+ catUIE.getAcronimo());
				unidadesUIE.add(catUIE.getAcronimo().toString());
			}
		}
		
		return unidadesUIE;
	}

	/**
	 * M&eacute;todo para obtener la clave catUIE del usuario, mediante el nombre de la especialidad asociada al funcionario.
	 * En caso de que el se obtenga un valor, se coloca en el acronimo de la UEI, en otro caso se asigna un 
	 * valor por default. En ambos casos, se coloca en la posici&oacute;n cero
	 * de la lista, y adem&aacute;s obtener todo el cat&aacute;logo de claves UIEs sin repetir la del
	 * usuario
	 *  
	 * @param usuario
	 * @return lista de claves UIEs encontradas, en la posici&oacute;n cero, se encuentra la UIE
	 * a la que pertenece el usuario que se recibe como par&aacute;metro
	 * @throws NSJPNegocioException
	 */
	private List<String> consultarClavesDeEspecialidadPM(UsuarioDTO usuario)
			throws NSJPNegocioException {
		boolean op=true;
		if (usuario == null
				|| usuario.getFuncionario() == null
				|| usuario.getFuncionario().getEspecialidad() == null
				|| usuario.getFuncionario().getEspecialidad()
						.getIdCampo() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<String> unidadesEspecialidadMP = new ArrayList<String>();

		logger.info("CAT Especialidad DEL USUARIO="
				+ usuario.getFuncionario().getEspecialidad()
						.getIdCampo());
		//consulta de la especialidad ui
		CatUIEspecializada catUIEspecializada=catUIEspecializadaDAO.findByName(usuario.getFuncionario().getEspecialidad().getNombreCampo());
		if(catUIEspecializada!=null){
			unidadesEspecialidadMP.add(0, catUIEspecializada.getAcronimo());
		}else{
			unidadesEspecialidadMP.add(0, AcronimoNumExpAlterno.COORDINACION_POLICIA_MINISTERIAL.getAcronimo());
			op=false;
		}
		
		List<CatUIEspecializada> listaCatUIE = catUIEspecializadaDAO
				.consultarTodos();

		for (CatUIEspecializada catUIE : listaCatUIE) {
			if (!(catUIE.getAcronimo().toString().trim().equals(unidadesEspecialidadMP
					.get(0).toString().trim()))) {
				logger.info("CAT UIE AGREGANDO A LA LISTA="
						+ catUIE.getAcronimo());
				unidadesEspecialidadMP.add(catUIE.getAcronimo().toString());
			}
		}
		if(op){
			unidadesEspecialidadMP.add(AcronimoNumExpAlterno.COORDINACION_POLICIA_MINISTERIAL.getAcronimo());
		}
		return unidadesEspecialidadMP;
	}
	
	
	@Override
	public String consultarNumeroExpedienteAlterno(ExpedienteDTO expediente) throws NSJPNegocioException {
		
		if(expediente == null || expediente.getNumeroExpedienteId() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
	
		String numeroExpedienteAlterno = "";
		
		//Si el parametro esta encendido en BD
		Parametro parametroNumExpAlterno = parametroDAO.obtenerPorClave(Parametros.NUMERO_EXPEDIENTE_ALTERNO);
		
		if(parametroNumExpAlterno != null && parametroNumExpAlterno.getValor() != null && parametroNumExpAlterno.getValor().equals("1")){
			//Se lleva a cabo la consulta del numero de expediente alterno
			numeroExpedienteAlterno = noExpDao.consultarNumeroExpedienteAlterno(expediente.getNumeroExpedienteId());
		}
		if(numeroExpedienteAlterno == null){
			numeroExpedienteAlterno="";
		}
		
		return numeroExpedienteAlterno;
	}
}
