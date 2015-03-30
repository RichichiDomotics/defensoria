/**
 * Nombre del Programa  : RecibirMedidaCautelarServiceImpl.java
 * Autor                : Daniel Jiménez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 29 Ago 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Implementación del servicio encargado de recibir y 
 * registrar en la BDD objetos de tipo Medida Cautelar
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.medida.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusMedida;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.MedidaCautelarDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaCautelarWSDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.MedidaCautelar;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;
import mx.gob.segob.nsjp.service.medida.RegistrarMedidaCautelarService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("registrarMedidaCautelarService")
@Transactional
public class RegistrarMedidaCautelarServiceImpl implements
		RegistrarMedidaCautelarService {
    /**
     * Logger.
     */
    public static final Logger logger = Logger.getLogger(RegistrarMedidaCautelarServiceImpl.class);

	@Autowired
	private IngresarIndividuoService ingresarIndividuoService;
	@Autowired
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private MedidaCautelarDAO medidaCautelarDAO; 
	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
    private AsignarNumeroExpedienteService asignarNumeroExpedienteService;
    @Autowired
    private ParametroDAO paramDao;
    @Autowired
    private UsuarioDAO usrDao;
    @Autowired
    private ConfInstitucionDAO confInstitucionDAO;
    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private CasoDAO casoDAO;
    @Autowired
    private BuscarExpedienteService buscarExpedienteService;
    @Autowired
    private InvolucradoDAO involucradoDAO;
    @Autowired
    private ConfActividadDocumentoDAO confActividadDocumentoDAO;
    @Autowired
    private NumeroExpedienteDAO numeroExpedienteDAO;
    
    
    
	@Override
	public void registrarMedidaCautelar(MedidaCautelarWSDTO medidaCautelar) throws NSJPNegocioException {
			
        MedidaCautelar medida = new MedidaCautelar();
        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA REGISTRAR MEDIDA CAUTELAR EN PG Y EN SSP ****/");
        }
		
		
		ExpedienteDTO expGenerado = null;
		ConfInstitucion institucionActual = confInstitucionDAO.consultarInsitucionActual();
		if(Instituciones.SSP.getValorId().equals(institucionActual.getConfInstitucionId())){
			ExpedienteDTO input = new ExpedienteDTO();
	        input.setArea(new AreaDTO(Areas.COORDINACION_SEGUIMIENTO_SSP));
	        Usuario usrRobot = this.usrDao.read(this.paramDao.obtenerPorClave(Parametros.ID_USUARIO_ROBOT_SISTEMA).getValorAsLong());
	        input.setUsuario(UsuarioTransformer.transformarUsuario(usrRobot));
	        expGenerado = asignarNumeroExpedienteService.asignarNumeroExpediente(input);
	        
		}else if(Instituciones.PGJ.getValorId().equals(institucionActual.getConfInstitucionId()) 
				&& medidaCautelar.getNumeroCaso()!=null ){

			expGenerado = new ExpedienteDTO();
			
			Caso casoBD = casoDAO.consultarCasoPorNumeroCaso(medidaCautelar
					.getNumeroCaso());
			
			if(casoBD!=null && casoBD.getCasoId()!=null){
				
				//Buscar para el usuario del Area de unidad de investigación
				CasoDTO casoDTO = new CasoDTO();
				casoDTO.setCasoId(casoBD.getCasoId());
				
				UsuarioDTO usuarioDTO = new UsuarioDTO();
				usuarioDTO
				.setAreaActual(new AreaDTO(Areas.UNIDAD_INVESTIGACION));
				casoDTO.setUsuario(usuarioDTO);
				
				List<ExpedienteDTO> respuesta = buscarExpedienteService
						.consultarExpedientesPorCaso(casoDTO);
				
				if (respuesta != null && !respuesta.isEmpty()
						&& respuesta.get(0) != null
						&& respuesta.get(0).getNumeroExpedienteId() != null) {
					logger.info("Numero existente!!!!");
					//Obtener el Numero de Expediente Id a asociar
					expGenerado.setNumeroExpedienteId(respuesta.get(0).getNumeroExpedienteId());
					expGenerado.setExpedienteId(respuesta.get(0).getExpedienteId());
				}
			}
		}
		if(expGenerado==null || expGenerado.getNumeroExpedienteId()==null){
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		
        logger.debug("/**** SE OBTUVO EL EXPEDINTE CON ID :: "+ expGenerado.getExpedienteId() + "****/");
        
        Expediente expediente =  expedienteDAO.read(expGenerado.getExpedienteId());
        
        if(Instituciones.SSP.getValorId().equals(institucionActual.getConfInstitucionId())){
			expediente.setFechaCreacion(Calendar.getInstance().getTime());
			expediente.setPertenceConfInst(new ConfInstitucion(Instituciones.SSP.getValorId()));
			expedienteDAO.saveOrUpdate(expediente);
			
			
			InvolucradoDTO sujeto = new InvolucradoDTO();
			NombreDemograficoDTO ndDTO = new NombreDemograficoDTO();
			ndDTO.setNombre(medidaCautelar.getNombreSujeto());
			ndDTO.setApellidoPaterno(medidaCautelar.getaPaternoSujeto());
			ndDTO.setApellidoMaterno(medidaCautelar.getaMaternoSujeto());
			sujeto.getNombresDemograficoDTO().add(ndDTO);
			CalidadDTO calidad = new CalidadDTO();
			calidad.setCalidades(Calidades.PROBABLE_RESPONSABLE_PERSONA);
			sujeto.setCalidadDTO(calidad);
			sujeto.setExpedienteDTO(new ExpedienteDTO(expediente.getExpedienteId()));
			sujeto.setElementoId(ingresarIndividuoService.ingresarIndividuo(sujeto));
			
			medida.setInvolucrado(new Involucrado(sujeto.getElementoId()));
        }else{
			Involucrado involucrado = involucradoDAO
					.consultarInvolucradoPorFolioElemento(medidaCautelar
							.getFolioProbableResponsable());
			medida.setInvolucrado(involucrado);
        }
	
        if(medidaCautelar.getArchivoDigital()!=null){
	        ArchivoDigital archivo = new ArchivoDigital();
	    	archivo.setNombreArchivo(medidaCautelar.getArchivoDigital().getNombreArchivo());
	    	archivo.setTipoArchivo(medidaCautelar.getArchivoDigital().getTipoArchivo());
	    	archivo.setContenido(medidaCautelar.getArchivoDigital().getContenido());
	    	archivo.setArchivoDigitalId(archivoDigitalDAO.create(archivo));
	    	
	    	medida.setArchivoDigital(archivo);
        }
		
    	medida.setNumeroExpediente(new NumeroExpediente(expGenerado.getNumeroExpedienteId()));
        medida.setTextoParcial(medidaCautelar.getTextoParcial());
		medida.setFolioDocumento(medidaCautelar.getFolioDocumento());
		if(medidaCautelar.getIdValorPeriodicidad()!=null && medidaCautelar.getIdValorPeriodicidad() > 0){
			medida.setValorPeriodicidad(new Valor(medidaCautelar.getIdValorPeriodicidad()));
		}
        medida.setValorTipoMedida(new Valor(medidaCautelar.getIdValorTipoMedida()));
        medida.setFechaInicio(medidaCautelar.getFechaInicio());
        medida.setFechaFin(medidaCautelar.getFechaFin());
        medida.setDescripcionMedida(medidaCautelar.getDescripcionMedida());
        medida.setEsActivo(medidaCautelar.isActivo());
        medida.setJuezOrdena(medidaCautelar.getJuezOrdena());
        medida.setNumeroCarpetaEjecucion(medidaCautelar.getNumeroCarpetaEjecucion());
        medida.setNumeroCausa(medidaCautelar.getNumeroCausa());
        medida.setNumeroCaso(medidaCautelar.getNumeroCaso());
        medida.setFechaCreacion(medidaCautelar.getFechaCreacion());
        medida.setEstatus(new Valor(EstatusMedida.NO_ATENDIDA.getValorId()));

        //Se obtiene de la institucion que se envia 
        if(medidaCautelar.getConfInstitucionId()!=null){
        	medida.setConfInstitucion(new ConfInstitucion(medidaCautelar.getConfInstitucionId()));	
        }
        
      //OBTENER CONF ACTIVIDAD DOCUMENTO
		ConfActividadDocumento confActividadDocumento = confActividadDocumentoDAO
				.read(mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento.GENERAR_MEDIDA_CAUTELAR
						.getValorId());
		
		if (confActividadDocumento == null
				|| confActividadDocumento.getForma() == null
				|| confActividadDocumento.getForma().getFormaId() == null
				|| confActividadDocumento.getTipoDocumento() == null
				|| confActividadDocumento.getTipoDocumento().getValorId() == null
				|| confActividadDocumento.getTipoDocumento().getValor() == null
				|| confActividadDocumento.getTipoActividad() == null
				|| confActividadDocumento.getTipoActividad().getValorId() == null
				|| confActividadDocumento.getTipoActividad().getValor() == null) {

			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		
		medida.setForma(new Forma(confActividadDocumento.getForma().getFormaId()));
		medida.setTipoDocumento(new Valor(confActividadDocumento.getTipoDocumento().getValorId()));
		medida.setNombreDocumento(confActividadDocumento.getTipoDocumento().getValor());
		
		//Obtenemos el numero de expediente para obtener el funcionario responsable
		NumeroExpediente numExp =numeroExpedienteDAO.read(expGenerado.getNumeroExpedienteId());
		
		//GENERAR LA ACTIVIDAD ASOCIADA AL DOCUMENTO Y AL EXPEDIENTE
		
		Actividad actividad = new Actividad();
		//Seteamos el funcionario responsable
		actividad.setFuncionario(numExp.getFuncionario());
		
		actividad.setExpediente(expediente);
		
		Valor tipoActividad = new Valor(confActividadDocumento
				.getTipoActividad().getValorId(), confActividadDocumento
				.getTipoActividad().getValor());
		
		actividad.setTipoActividad(tipoActividad);
		actividad.setFechaCreacion(new Date());
		
		medida.setActividad(actividad);
		actividad.setDocumento(medida);
	
        medida.setDocumentoId(medidaCautelarDAO.create(medida));
	}
	
	public Boolean actualizarEstatusMedidaCautelar(MedidaCautelarWSDTO medidaCautelarWSDTO) throws NSJPNegocioException{
		Boolean regreso = false;
		
        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA ACTUALIZAR ESTATUS DE MEDIDA CAUTELAR EN LA INSTITUCIÓN INVOCADA ****/");
        }
        
        logger.info("DATOS DE LA MEDIDA:"+medidaCautelarWSDTO);
        logger.info("ConfInstitucion:"+medidaCautelarWSDTO.getConfInstitucionId());
        logger.info("NumeroCaso:"+medidaCautelarWSDTO.getNumeroCaso());
        logger.info("NumeroCarpetaEjecucion:"+medidaCautelarWSDTO.getNumeroCarpetaEjecucion());
        logger.info("NumeroCausa:"+medidaCautelarWSDTO.getNumeroCausa());
        logger.info("NumeroCausa:"+medidaCautelarWSDTO.getFolioDocumento());
        logger.info("NumeroCausa:"+medidaCautelarWSDTO.getIdEstatus());
		
        //Buscar la medida Cautelar por Folio
        Documento documento = documentoDAO.consultarDocumentoPorFolio(medidaCautelarWSDTO.getFolioDocumento());
		MedidaCautelar medidaCautelar = null;
		if (documento instanceof MedidaCautelar) {
			medidaCautelar = (MedidaCautelar) documento;
		}
        if(medidaCautelar!=null){
        	logger.info(" ESTATUS ANTERIOR:"+ medidaCautelar.getEstatus());
        	medidaCautelar.setEstatus(new Valor( medidaCautelarWSDTO.getIdEstatus()));
        	logger.info(" NUEVO ESTATUS:"+ medidaCautelarWSDTO.getIdEstatus());
        	medidaCautelarDAO.update(medidaCautelar);
        	regreso = true;
        }
        
		return regreso;
	}

}
