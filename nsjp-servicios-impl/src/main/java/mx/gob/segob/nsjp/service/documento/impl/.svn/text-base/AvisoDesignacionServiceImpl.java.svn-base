package mx.gob.segob.nsjp.service.documento.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoMovimiento;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.involucrado.SituacionJuridica;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.avisodetencion.AvisoDetencionDAO;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.domicilio.DomicilioDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.involucrado.DefensaInvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.AvisoDetencion;
import mx.gob.segob.nsjp.model.DefensaInvolucrado;
import mx.gob.segob.nsjp.model.DefensaInvolucradoId;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Domicilio;
import mx.gob.segob.nsjp.model.Elemento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.RegistroBitacora;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.bitacora.RegistrarBitacoraService;
import mx.gob.segob.nsjp.service.documento.AvisoDesignacionService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.AvisoDesignacionTransformer;
import mx.gob.segob.nsjp.service.domicilio.impl.transform.DomicilioTransformer;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AvisoDesignacionServiceImpl implements AvisoDesignacionService {

	private final static Logger logger = Logger.getLogger(AvisoDesignacionServiceImpl.class);
	
	@Autowired
	private AvisoDetencionDAO avisoDetencionDAO;
	@Autowired
	private AvisoDesignacionDAO avisoDesignacionDAO;
	@Autowired
	private ElementoDAO elementoDAO;
	@Autowired
	private AsignarNumeroExpedienteService asignarNumeroExpedienteService;
	@Autowired 
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private SolicitudDefensorDAO solicitudDefensorDAO;
	@Autowired
	private GenerarFolioSolicitudService generarFolioSolicitudService;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private DefensaInvolucradoDAO defensaInvolucradoDAO;
	@Autowired
	private IngresarIndividuoService ingresarIndividuoService;
	@Autowired
	private RegistrarBitacoraService registrarBitacoraService;
	@Autowired
	private DocumentoDAO docDao;
    @Autowired
    private DomicilioDAO domDAO;
	
	@Override
	public List<AvisoDesignacionDTO> consultarAvisosDesignacion(
			EstatusNotificacion estado, FuncionarioDTO funcionario) throws NSJPNegocioException {
		
		Long clave = null;
		if(funcionario != null){
			clave = funcionario.getClaveFuncionario();
		}
		List<AvisoDesignacion> avisos = avisoDesignacionDAO.consultarAvisosDesignacion(estado.getValorId(), clave);
		List<AvisoDesignacionDTO> designaciones = new LinkedList<AvisoDesignacionDTO>();
		AvisoDesignacionDTO dto = null;
		for (AvisoDesignacion aviso : avisos) {
		    dto = AvisoDesignacionTransformer.transformar(aviso);
			List<Documento> lista = docDao.consultarDocumentosByExpedienteIdYForma(aviso.getExpediente().getExpedienteId(), Formas.SOLICITUD_DEFENSOR_EXTERNO);
			if (lista!=null && !lista.isEmpty()) {
			    dto.setTieneSolicitudDefensorExterno(Boolean.TRUE);
			}
			designaciones.add(dto);
		}
		
		return designaciones;
	}
	
	@Override
	public AvisoDesignacionDTO consultarAvisoDesignacion(Long idAviso) throws NSJPNegocioException {
	    logger.debug("Recuperando Aviso de Designación con id :: " + idAviso);
		AvisoDesignacion aviso 			= avisoDesignacionDAO.read(idAviso);
		 AvisoDesignacionDTO avisoDTO 	= AvisoDesignacionTransformer.transformar(aviso);
		if(aviso.getExpediente() != null){
            Involucrado defendido = defensaInvolucradoDAO.consultarInvolucradoNumeroExpedienteDefensoria(avisoDTO.getExpediente().getNumeroExpedienteId());
            if(defendido != null){
            	avisoDTO.getExpediente().setInputado(InvolucradoTransformer.transformarInvolucrado(defendido));
            }
		}
		return avisoDTO;
	}

	@Override
	public AvisoDesignacionDTO registrarAvisoDesignacion(AvisoDesignacionDTO designacion) throws NSJPNegocioException {
		

        String folio 					= "";
        Expediente expediente 			= null;
        AvisoDetencion avisoDetencion 	= null;
        AvisoDesignacion aviso 			= null;
        SolicitudDefensor solicitud 	= null;
        ExpedienteDTO nuevoExp 			= null;
        Involucrado invDefendido 		= null;
        boolean valDesignacion 			= false;
        if (designacion.getExpediente() == null) {

            if (designacion.getAvisoDetencion() != null) {
                avisoDetencion = avisoDetencionDAO.read(designacion.getAvisoDetencion().getDocumentoId());
                folio = avisoDetencion.getDetencion().getInvolucrado().getFolioElemento();
                // se obtiene el expediente del involucrado cuando se registro el aviso de detencion
                nuevoExp = new ExpedienteDTO(avisoDetencion.getDetencion().getInvolucrado().getExpediente().getExpedienteId());
            } else {
            	/// caso para Aviso Designacion
            	if(designacion.getSolicitudDefensor() == null ){
            		///designacion.getDocumentoId() Es el expdiente id
            		aviso			= avisoDesignacionDAO.read(designacion.getDocumentoId() );
            		Elemento ele	= elementoDAO.consultarElementoXIdExpXIdElemento(aviso.getExpediente().getExpedienteId(), aviso.getInvolucrado().getElementoId());
            		folio 			= ele.getFolioElemento();
            		nuevoExp		= new ExpedienteDTO(aviso.getExpediente().getExpedienteId());
            		valDesignacion	= true;
            	}
            	else{
	                solicitud = solicitudDefensorDAO.read(designacion.getSolicitudDefensor().getDocumentoId());
	                folio = solicitud.getFolioElementoDetenido();
	             // se obtiene el expediente del involucrado cuando se registro la solicitud de defensor
	                if (solicitud.getNumeroExpediente() != null ){
	                	nuevoExp = new ExpedienteDTO(solicitud.getNumeroExpediente().getExpediente().getExpedienteId());                	
	                }else{
	                	nuevoExp = new ExpedienteDTO();
	                }
            	}
            }

            InvolucradoDTO invProcuraduria = new InvolucradoDTO();
            invProcuraduria.setFolioElemento(folio);
            
            nuevoExp.setArea(new AreaDTO(Areas.COORDINACION_DEFENSORIA));
            CasoDTO caso = new CasoDTO();
            
            if(nuevoExp.getExpedienteId()!=null ){

//              enable IT: Se busca por elemento y por expediente para delimitar busqueda unica
                invDefendido = involucradoDAO.obtenerInvolucradoByFolioElementoExpediente(folio, nuevoExp.getExpedienteId());
            }else{
            	invDefendido = involucradoDAO.obtenerInvolucradoByFolioElemento(folio);
            }
//            enable IT: Se busca por elemento y por expediente para delimitar busqueda unica
            //invDefendido = involucradoDAO
            //        .obtenerInvolucradoByFolioElementoExpediente(folio, nuevoExp.getExpedienteId());
            final InvolucradoDTO defendido = InvolucradoTransformer.clonarInvolucrado(invDefendido, Calidades.DEFENDIDO, false);
            
            logger.info("defendidooooo Edaaaaad   ------ " + defendido.getNombresDemograficoDTO().get(0).getEdadAproximada());
            
            Domicilio domicilio 			= domDAO.obtenerDomicilioByRelacion(invDefendido.getElementoId(), new Long(Relaciones.RESIDENCIA.ordinal())); 
            Domicilio domicilioNotificacion = domDAO.obtenerDomicilioByRelacion(invDefendido.getElementoId(), new Long(Relaciones.NOTIFICACION.ordinal())); 
            
            if (domicilio!=null) {
                defendido.setDomicilio(DomicilioTransformer.transformarDomicilio(domicilio));
                defendido.getDomicilio().setElementoId(null);
            }
            if (domicilioNotificacion!=null) {
                defendido.setDomicilioNotificacion(DomicilioTransformer.transformarDomicilio(domicilioNotificacion));
                defendido.getDomicilioNotificacion().setElementoId(null);
            }
            
            if (designacion.getAvisoDetencion() != null) {
                nuevoExp.setEtapa(new ValorDTO(EtapasExpediente.INTEGRACION.getValorId()));
                caso.setNumeroGeneralCaso(designacion.getAvisoDetencion().getNumeroCasoAsociado());
                defendido.setValorSituacionJuridica(new ValorDTO(SituacionJuridica.IMPUTADO.getValorId()));
            } else {
            	if(designacion.getSolicitudDefensor() == null){
            		nuevoExp.setEtapa(new ValorDTO(EtapasExpediente.INTEGRACION.getValorId()));
                    caso.setNumeroGeneralCaso(aviso.getNumeroCasoAsociado());
                    defendido.setValorSituacionJuridica(new ValorDTO(SituacionJuridica.IMPUTADO.getValorId()));
            	}
            	else{
            		nuevoExp.setEtapa(new ValorDTO(EtapasExpediente.TECNICA.getValorId()));
            		caso.setNumeroGeneralCaso(designacion.getSolicitudDefensor().getNumeroCasoAsociado());
            		defendido.setValorSituacionJuridica(new ValorDTO(SituacionJuridica.IMPUTADO.getValorId()));
            	}
            }

            nuevoExp.setUsuario(designacion.getUsuario());
            nuevoExp = asignarNumeroExpedienteService.asignarNumeroExpedienteDefensoria(nuevoExp);
            if( valDesignacion == true ){
            	aviso.setExpediente( ExpedienteTransformer.transformarExpedienteBasicoDefensoria(nuevoExp));
            }
            designacion.setExpediente(nuevoExp);

            defendido.setElementoId(ingresarIndividuoService.ingresarIndividuo(defendido));
            DefensaInvolucrado defensaInvolucrado = new DefensaInvolucrado();
            final DefensaInvolucradoId id = new DefensaInvolucradoId();
            id.setInvolucradoId(defendido.getElementoId());
            id.setNumeroExpedienteId(nuevoExp.getNumeroExpedienteId());
            defensaInvolucrado.setId(id);
            defensaInvolucrado.setInvolucradoPg(new Involucrado(invDefendido.getElementoId()));
            defensaInvolucradoDAO.create(defensaInvolucrado);
            if (defendido.getValorSituacionJuridica() != null) {
                RegistroBitacora regBitSJ = new RegistroBitacora();

                regBitSJ.setFechaInicio(new Date());
                regBitSJ.setTipoMovimiento(new Valor(TipoMovimiento.CAMBIO_DE_SITUACION_JURIDICA.getValorId()));
                regBitSJ.setNuevo(String.valueOf(defendido.getValorSituacionJuridica().getIdCampo()));
                regBitSJ.setNumeroExpediente(new NumeroExpediente(nuevoExp.getNumeroExpedienteId()));
                registrarBitacoraService.registrarMovimientoDeExpedienteEnBitacora(regBitSJ);
            }

        } else {
            solicitud = solicitudDefensorDAO.read(designacion.getSolicitudDefensor().getDocumentoId());
            expediente = expedienteDAO.read(solicitud.getExpediente().getExpedienteId());
            NumeroExpediente ne = expediente.getNumeroExpedientes().iterator().next();
            designacion.getExpediente().setExpedienteId(expediente.getExpedienteId());
            designacion.getExpediente().setNumeroExpedienteId(ne.getNumeroExpedienteId());
            designacion.getExpediente().setNumeroExpediente(ne.getNumeroExpediente());
        }

        ///Validacion para el caso cuando es avisodetencion y avisodesignacion
        if( valDesignacion == false){
	        designacion.setEstatus(new ValorDTO(EstatusNotificacion.NO_ATENDIDA.getValorId()));
	        AvisoDesignacion avisoDesignacion = AvisoDesignacionTransformer.transformar(designacion);
	        avisoDesignacion.setForma(new Forma(Formas.SOLICITUD.getValorId()));
	        avisoDesignacion.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD.getValorId()));
	        avisoDesignacion.setFechaCreacion(Calendar.getInstance().getTime());
	        avisoDesignacion.setNombreDocumento("Aviso de Designacion ");
	        avisoDesignacion.setFolioNotificacion(generarFolioSolicitudService.generarFolioNotificacion());
	        avisoDesignacion.setConsecutivoNotificacion(avisoDesignacion.getFolioNotificacion().substring(3));
	        designacion.setDocumentoId(avisoDesignacionDAO.create(avisoDesignacion));
	
	        expediente = new Expediente(designacion.getExpediente().getExpedienteId());
	
	        if (designacion.getAvisoDetencion() != null) {
	            avisoDetencion.setEstatus(new Valor(EstatusNotificacion.ATENDIDA.getValorId()));
	            avisoDetencion.setExpediente(expediente);
	            avisoDetencionDAO.update(avisoDetencion);
	        } else {
	
	            solicitud.setEstatus(new Valor(EstatusSolicitud.CERRADA.getValorId()));
	            solicitudDefensorDAO.update(solicitud);
	        }
        }
        else{
        	designacion 	= AvisoDesignacionTransformer.transformar(aviso);
        	designacion.setExpediente(nuevoExp);
        	designacion.setEstatus(new ValorDTO(EstatusNotificacion.NO_ATENDIDA.getValorId()));
        	///aviso.setEstatus(new Valor(EstatusNotificacion.ATENDIDA.getValorId()));
        	///avisoDesignacionDAO.update(aviso);
        }
        return designacion;
    }
	
	@Override
    public AvisoDesignacionDTO registrarAvisoDesignacionPorReasignacionDefensor(
            ExpedienteDTO input) throws NSJPNegocioException {
        if (input.getNumeroExpedienteId() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        AvisoDesignacion aviso = new AvisoDesignacion();
        NumeroExpediente numExp = numeroExpedienteDAO.read(input
                .getNumeroExpedienteId());
        numExp.setFuncionario(new Funcionario(input.getUsuario()
                .getFuncionario().getClaveFuncionario()));
        numeroExpedienteDAO.update(numExp);
        AvisoDesignacion avisoAnt = null;
        for (AvisoDesignacion item : numExp.getExpediente()
                .getAvisoDesignaciones()) {
            if (avisoAnt == null
                    || item.getDocumentoId() > avisoAnt.getDocumentoId()) {
                avisoAnt = item;
            }
        }
        if (avisoAnt != null) {
            cerrarAvisoDesignacion(avisoAnt.getDocumentoId());
        }
        Expediente expediente = numExp.getExpediente();
        aviso.setExpediente(expediente);
        aviso.setEstatus(new Valor(EstatusNotificacion.NO_ATENDIDA.getValorId()));
        aviso.setForma(new Forma(Formas.SOLICITUD.getValorId()));
        aviso.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD.getValorId()));
        aviso.setFechaCreacion(Calendar.getInstance().getTime());
        aviso.setNombreDocumento("Aviso de Designacion ");
        aviso.setFolioNotificacion(generarFolioSolicitudService.generarFolioNotificacion());
        aviso.setConsecutivoNotificacion(aviso.getFolioNotificacion().substring(3));
        aviso.setDocumentoId(avisoDesignacionDAO.create(aviso));
        final RegistroBitacora regBitFun = new RegistroBitacora();

        regBitFun.setFechaInicio(new Date());
        regBitFun.setTipoMovimiento(new Valor(TipoMovimiento.ASIGNACION_DE_EXPEDIENTE.getValorId()));
        regBitFun.setNuevo("Sin defensor asignado");
        regBitFun.setNumeroExpediente(numExp);

        registrarBitacoraService.registrarMovimientoDeExpedienteEnBitacora(regBitFun);
        return AvisoDesignacionTransformer.transformar(aviso);
    }
	
	@Override
	public void designarAbogadoDefensor(AvisoDesignacionDTO designacion) throws NSJPNegocioException{
		Boolean esValorDetencion	= false;
		AvisoDesignacion aviso = avisoDesignacionDAO.read(designacion.getDocumentoId());
		Funcionario funcionario = new Funcionario();
		funcionario.setClaveFuncionario(designacion.getFuncionario().getClaveFuncionario());
		aviso.setFuncionario(funcionario);
		aviso.setFechaCreacion(Calendar.getInstance().getTime());
		//Agregado para actualizacion del estatus
		///aviso.setEstatus(new Valor(EstatusNotificacion.ATENDIDA.getValorId()));
		avisoDesignacionDAO.update(aviso);
		

		aviso = avisoDesignacionDAO.read(designacion.getDocumentoId());

		NumeroExpediente nExpediente = aviso.getExpediente().getNumeroExpedientes().iterator().next();
		funcionario = aviso.getFuncionario();
		nExpediente.setFuncionario(funcionario);
		
    	Long idEstatus = EstatusExpediente.ABIERTO_RESTAURATIVA.getValorId();
    	
    	if(nExpediente.getEtapa() != null){
	    	switch(EtapasExpediente.getByValor(nExpediente.getEtapa().getValorId())){
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
    	}
    	nExpediente.setEstatus(new Valor(idEstatus));
		numeroExpedienteDAO.update(nExpediente);
		
		final RegistroBitacora regBitFun = new RegistroBitacora();
		
		regBitFun.setFechaInicio(new Date());
		regBitFun.setTipoMovimiento(new Valor(TipoMovimiento.ASIGNACION_DE_EXPEDIENTE.getValorId()));
		regBitFun.setNuevo(String.valueOf(funcionario.getClaveFuncionario()));
		regBitFun.setNumeroExpediente(nExpediente);

		registrarBitacoraService.registrarMovimientoDeExpedienteEnBitacora(regBitFun);
		
		final RegistroBitacora regBitEstatus = new RegistroBitacora();
		
		regBitEstatus.setFechaInicio(new Date());
		regBitEstatus.setTipoMovimiento(new Valor(TipoMovimiento.CAMBIO_DE_ESTATUS_DE_EXPEDIENTE.getValorId()));
		regBitEstatus.setNuevo(String.valueOf(idEstatus));
		regBitEstatus.setNumeroExpediente(nExpediente);
		
		registrarBitacoraService.registrarMovimientoDeExpedienteEnBitacora(regBitEstatus);
		
	}

	@Override
	public void cerrarAvisoDesignacion(Long idAvisoDesignacion) {

		AvisoDesignacion aviso = avisoDesignacionDAO.read(idAvisoDesignacion);
		aviso.setEstatus(new Valor(EstatusNotificacion.ATENDIDA.getValorId()));
		avisoDesignacionDAO.update(aviso);
	}	
	
	@Override
	public List<AvisoDesignacionDTO> obtenerAvisosDesignacionPorEstatus(
			EstatusNotificacion estatusnotificacion, Long discriminanteId)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("SERVICIO PARA LA CONSULTA DE AVISOS DE DESIGNACION POR ESTATUS");

		List<AvisoDesignacion> avisosDesignacion 	= avisoDesignacionDAO.obtenerAvisosDesignacionPorEstatus(estatusnotificacion.getValorId(), discriminanteId);
		List<AvisoDesignacionDTO> lsResp 			= new ArrayList<AvisoDesignacionDTO>();
		if (!avisosDesignacion.isEmpty()){
			for(AvisoDesignacion designacion  : avisosDesignacion){
				AvisoDesignacionDTO aviso = AvisoDesignacionTransformer.transformar(designacion);
				/// Condicion para evitar mostar los registros que ya pasaraon del Coordinador al Defensor
				if(aviso.getExpediente() == null ){
					lsResp.add(aviso);
				}
			}
		}
		return lsResp;
	}
	
	@Override
	public AvisoDesignacionDTO atenderAvisoDesignacion(
			AvisoDesignacionDTO avisoDesignacionnDTO) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA COLOCAR EL ESTATUS DEL AVISO EN ATENDIDO ****/");

		if (avisoDesignacionnDTO.getDocumentoId()==null || avisoDesignacionnDTO.getUsuario()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		ExpedienteDTO nuevoExp = new ExpedienteDTO();
		nuevoExp.setArea(new AreaDTO(Areas.COORDINACION_DEFENSORIA));
		nuevoExp.setEtapa(new ValorDTO(EtapasExpediente.INTEGRACION.getValorId()));
		nuevoExp.setUsuario(avisoDesignacionnDTO.getUsuario());

		ExpedienteDTO expGenerado =  asignarNumeroExpedienteService.asignarNumeroExpediente(nuevoExp);
		Expediente expediente = expedienteDAO.read(expGenerado.getExpedienteId());
		logger.debug("/**** SE GENERO EL EXPEDIENTE PARA DEFENSORIA ****/");

		AvisoDesignacion avisoDesignacion	= avisoDesignacionDAO.read(avisoDesignacionnDTO.getDocumentoId());
		avisoDesignacion.setEstatus(new Valor(EstatusNotificacion.ATENDIDA.getValorId()));
		avisoDesignacion.setExpediente(expediente);
		avisoDesignacionDAO.update(avisoDesignacion);

		AvisoDesignacionDTO aviDetRetorno = new AvisoDesignacionDTO();
		aviDetRetorno.setDocumentoId(avisoDesignacion.getDocumentoId());
		ExpedienteDTO expedienteDTO = new ExpedienteDTO(expediente.getExpedienteId());
		expedienteDTO.setNumeroExpediente(expediente.getNumeroExpediente());
		aviDetRetorno.setExpedienteDTO(expedienteDTO);

		if (logger.isDebugEnabled())
			logger.debug("/**** SE ATENDIO CORRECTAMENTE EL AVISO DETENCION ****/");

		return aviDetRetorno;
	}
	
	
}
