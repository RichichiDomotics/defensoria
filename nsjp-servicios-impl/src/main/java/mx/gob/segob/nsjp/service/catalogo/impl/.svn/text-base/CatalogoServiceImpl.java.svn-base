/**
 *
 * Nombre del Programa : CatalogoServiceImpl.java                                    
 * Autor                            : Vladimir Aguirre                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 30/03/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Implementaci&oacute;n del servicio que obtiene catalogos                      
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
package mx.gob.segob.nsjp.service.catalogo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.catalogo.TipoDiscriminante;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.audiencia.SalaAudienciaDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatAreasNegocioDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoCausaDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoClasificacionDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoLugarDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoModalidadDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoModusDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoSituacionDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDiscriminateDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDistritoDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatFaltaAdministrativaDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatUIEspecializadaDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatalogoDAO;
import mx.gob.segob.nsjp.dao.documento.CatFormaNotificacionDAO;
import mx.gob.segob.nsjp.dao.domicilio.AsentamientoDAO;
import mx.gob.segob.nsjp.dao.domicilio.CatTipoAsentamientoDAO;
import mx.gob.segob.nsjp.dao.domicilio.CiudadDAO;
import mx.gob.segob.nsjp.dao.domicilio.EntidadFederativaDAO;
import mx.gob.segob.nsjp.dao.domicilio.MunicipioDAO;
import mx.gob.segob.nsjp.dao.forma.FormaDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatAreasNegocioDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatFaltaAdministrativaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.model.Asentamiento;
import mx.gob.segob.nsjp.model.CatAreasNegocio;
import mx.gob.segob.nsjp.model.CatDelito;
import mx.gob.segob.nsjp.model.CatDelitoCausa;
import mx.gob.segob.nsjp.model.CatDelitoClasificacion;
import mx.gob.segob.nsjp.model.CatDelitoLugar;
import mx.gob.segob.nsjp.model.CatDelitoModalidad;
import mx.gob.segob.nsjp.model.CatDelitoModus;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.CatDistrito;
import mx.gob.segob.nsjp.model.CatFormaNotificacion;
import mx.gob.segob.nsjp.model.CatTipoAsentamiento;
import mx.gob.segob.nsjp.model.CatUIEspecializada;
import mx.gob.segob.nsjp.model.Ciudad;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.EntidadFederativa;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.Municipio;
import mx.gob.segob.nsjp.model.Parametro;
import mx.gob.segob.nsjp.model.SalaAudiencia;
import mx.gob.segob.nsjp.model.SituacionImputado;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.catalogo.CatalogoService;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatAreaNegocioTransformer;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatalogoTransformer;
import mx.gob.segob.nsjp.service.informepolicial.impl.transform.CatDelitoTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vaguirre
 * 
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CatalogoServiceImpl implements CatalogoService {
    /**
     * Logger.
     */
    private final static Logger logger = Logger
            .getLogger(CatalogoServiceImpl.class);
    /**
     * Dao para el acceso al catalogo.
     */
    @Autowired
    private CatalogoDAO catDAO = null;
    @Autowired
    private EntidadFederativaDAO entidadFedDAO = null;
    @Autowired
    private MunicipioDAO mpioDAO = null;
    @Autowired
    private CatTipoAsentamientoDAO tipoAsentamientoDAO = null;
    @Autowired
    private CiudadDAO cdDAO = null;
    @Autowired
    private AsentamientoDAO asentamientoDAO = null;
    @Autowired
    private CatDelitoDAO delitoDao = null;
    @Autowired
    private CatDistritoDAO distritoDao = null;
    @Autowired
    private CatDiscriminateDAO discriminanteDao = null;
    @Autowired
    private ConfInstitucionDAO confInstiDao;
    @Autowired
    private JerarquiaOrganizacionalDAO jOrgDao;
    @Autowired
    private CatFaltaAdministrativaDAO faltaAdministrativaDao;
    @Autowired
    private FormaDAO formaDao;
    @Autowired
    private ConfActividadDocumentoDAO confActDocDao;
    @Autowired
    private ParametroDAO paramDao;
    @Autowired
    private CatDelitoClasificacionDAO delitoClasificacionDao;
    @Autowired
    private CatDelitoLugarDAO delitoLugarDao;
    @Autowired
    private CatDelitoModalidadDAO delitoModalidadDao;
    @Autowired
    private CatDelitoModusDAO delitoModusDao;
    @Autowired
    private CatDelitoCausaDAO delitoCausaDao;
    @Autowired
    private CatDelitoSituacionDAO delitoSituacionDao;
    @Autowired
    private CatDelitoDAO catDelitoDao;
    @Autowired
    private CatUIEspecializadaDAO catUIEspecializadaDAO; 
    @Autowired
    private SalaAudienciaDAO salaAudienciaDAO;
    @Autowired
    private CiudadDAO ciudadDAO;
    @Autowired
    private CatAreasNegocioDAO catAreasNegocioDAO;
    @Autowired
    private CatFormaNotificacionDAO catFormaNotificacionDAO;
    
    @Override
    public List<CatalogoDTO> recuperarCatalogo(Catalogos cat)
            throws NSJPNegocioException {
        if (logger.isDebugEnabled()) {
            logger.debug("Catalogo a recuperar :: " + cat);
        }
        switch (cat) {
            case VACIO :
                return Collections.emptyList();
            case TIPO_ASENTAMIENTO :
                List<CatTipoAsentamiento> tipos = this.tipoAsentamientoDAO
                        .consultarTodos();
                return CatalogoTransformer.transformarTipoAsentamiento(tipos);
            case INSTITUCION_CON_NSJP :
                List<ConfInstitucion> insnsjp = this.confInstiDao
                        .consultarCatalogoSingle();
                return CatalogoTransformer.transformarInstitucion(insnsjp);
            case INSTITUCION :
                List<JerarquiaOrganizacional> ins = this.jOrgDao
                        .consultarCatalogoSencilloInstituciones();
                return CatalogoTransformer.transformarJerarquias(ins);
            case AREA :
                List<JerarquiaOrganizacional> ars = this.jOrgDao
                        .consultarCatalogoSencilloNoInstituciones();
                return CatalogoTransformer.transformarJerarquias(ars);
            case DEPARTAMENTO :
                List<JerarquiaOrganizacional> deps = this.jOrgDao
                        .consultarCatalogoSencilloDepartamentos();
                return CatalogoTransformer.transformarJerarquias(deps);
            case DELITO :
                List<CatDelito> dels = this.delitoDao
                        .consultarTodos();
                return CatalogoTransformer.transformarDelitosSingle(dels);
            case DISTRITOS :
                List<CatDistrito> distritos = this.distritoDao.consultarTodos();
                return CatalogoTransformer.transformarDistritosSingle(distritos);
            case AGENCIAS :
                List<CatDiscriminante> discriminantes = null;
            	ConfInstitucion confInstitucion = this.confInstiDao.consultarInsitucionActual();
            	if(confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId()))
                	discriminantes = this.discriminanteDao.consultarDiscriminantesXTipo(TipoDiscriminante.AGENCIA.ordinal());
                else
                	discriminantes = this.discriminanteDao.consultarDiscriminantesXTipo(TipoDiscriminante.TRIBUNAL.ordinal());
                return CatalogoTransformer.transformarDiscriminanteSingle(discriminantes);
            case PLANTILLAS :
                List<Forma> forms = this.formaDao
                        .findAll("nombre",true);
                return CatalogoTransformer.transformarFormasSingle(forms);    
            case ACTUACIONES :
                List<ConfActividadDocumento> actus = this.confActDocDao
                	.findAll("tipoActividad.valor",true);
                return CatalogoTransformer.transformarActuacionSingle(actus);     
            case PARAMETROS :
                List<Parametro> params = this.paramDao
                        .findAll("descripcion",true);
                return CatalogoTransformer.transformarParametro(params);                 
            case CLASIFICACION_DELITO:
            	List<CatDelitoClasificacion> delitoClasificacion =  this.delitoClasificacionDao.consultarTodos();
            	return CatalogoTransformer.transformarDelitoClasificacion(delitoClasificacion);
            case LUGAR_DELITO:
            	List<CatDelitoLugar> delitoLugar =  this.delitoLugarDao.consultarTodos();
            	return CatalogoTransformer.transformarDelitoLugar(delitoLugar);
            case MODALIDAD_DELITO:
            	List<CatDelitoModalidad> delitoModalidad =  this.delitoModalidadDao.consultarTodos();
            	return CatalogoTransformer.transformarDelitoModalidad(delitoModalidad);
            case MODUS_DELITO:
            	List<CatDelitoModus> delitoModus =  this.delitoModusDao.consultarTodos();
            	return CatalogoTransformer.transformarDelitoModus(delitoModus);
            case CAUSA_DELITO:
            	List<CatDelitoCausa> delitoCausa =  this.delitoCausaDao.consultarTodos();
            	return CatalogoTransformer.transformarDelitoCausa(delitoCausa);
            case SITUACION_DELITO:
            	List<SituacionImputado> delitoSituacion =  this.delitoSituacionDao.consultarTodos();
            	return CatalogoTransformer.transformarDelitoSituacion(delitoSituacion);
            case TIPO_UNIDAD_INVESTIGACION_ESPECIALIZADA:
            	List<CatUIEspecializada> tiposUnidadInves =  this.catUIEspecializadaDAO.consultarTodos();
            	return CatalogoTransformer.transformarCatUIEspecializada(tiposUnidadInves);
//            case SALA_TEMPORAL:
//            	List<SalaTemporal> salasTemporales = this.salaTemporalDAO.consultarTodos();
//            	return CatalogoTransformer.transformarSalaTemporal(salasTemporales);
            case SALA_AUDIENCIA:
            	List<SalaAudiencia> salasAudiencia = this.salaAudienciaDAO.consultarTodos();
            	return CatalogoTransformer.transformarSalaAudiencia(salasAudiencia);
            case CIUDAD:
            	List<Ciudad> ciudades = this.ciudadDAO.findAll("nombreCiudad",true);
            	return CatalogoTransformer.transformarCiudad(ciudades);
            case ENTIDAD_FEDERATIVA:
            	List<EntidadFederativa> entidadesFed = this.entidadFedDAO.findAll("nombre",true);
            	return CatalogoTransformer.transformarEF(entidadesFed);
            case DELEGACION_MUNICIPIO:
            	List<Municipio> municipios = this.mpioDAO.findAll("nombreMunicipio",true);
            	return CatalogoTransformer.transformarMpio(municipios);
            case TIPO_SENTENCIA:
            	List<Valor> tiposDeSentencia = this.catDAO.recuperarCatalogoCompleto(cat);
            	return CatalogoTransformer.transformarValor(tiposDeSentencia);
            case AREAS_DE_NEGOCIO:
            	List<CatAreasNegocio> areasDeNegocio = this.catAreasNegocioDAO.consultarTodos();
            	return CatalogoTransformer.transformarCatAreasNegocio(areasDeNegocio);
            case CAT_FORMAS_NOTIFICACION:
            	List<CatFormaNotificacion> formasNotificacion = catFormaNotificacionDAO.consultarFormasNotificacionValidas();
            	return CatalogoTransformer.transformarCatFormasNotificacion(formasNotificacion);
            default :
                final List<Valor> fromBD = this.catDAO
                        .recuperarCatalogoSencillo(cat);
                if (fromBD == null || fromBD.isEmpty()) {
                    List<CatalogoDTO> dummy = new ArrayList<CatalogoDTO>();
                    dummy.add(new CatalogoDTO(1L, "No existen datos en la BD"));
                    // FIXME eliminar al terminar de cargar todo los
                    // catalogos.
                    return dummy;
                }
                return CatalogoTransformer.transformarValor(fromBD);
        } // end switch
    }

    @Override
    public List<CatalogoDTO> recuperarCatalogoDependiente(Catalogos catHijo,
            Long idValorPadre) throws NSJPNegocioException {
        if (logger.isDebugEnabled()) {
            logger.debug("Catalogo a recuperar :: " + catHijo
                    + " con el valor del padre :: " + idValorPadre);
        }

        switch (catHijo) {
            case VACIO :
                return Collections.emptyList();
            case ENTIDAD_FEDERATIVA :
                List<EntidadFederativa> estados = this.entidadFedDAO
                        .consultarPorPais(idValorPadre);
                return CatalogoTransformer.transformarEF(estados);
            case CIUDAD :
                List<Ciudad> cdds = this.cdDAO
                        .consultarPorEntidadFederativa(idValorPadre);
                return CatalogoTransformer.transformarCiudad(cdds);
            case DELEGACION_MUNICIPIO :
                List<Municipio> mpios = this.mpioDAO
                        .consultarPorEntidadFederativa(idValorPadre);
                return CatalogoTransformer.transformarMpio(mpios);
            case TIPO_ASENTAMIENTO :
                List<CatTipoAsentamiento> tipos = this.tipoAsentamientoDAO
                        .consultarTodos();
                return CatalogoTransformer.transformarTipoAsentamiento(tipos);
                
            case AREA :
                List<JerarquiaOrganizacional> ars = this.jOrgDao
                        .consultarAreasByPadre(idValorPadre);
                return CatalogoTransformer.transformarJerarquias(ars);
            case DEPARTAMENTO :
                List<JerarquiaOrganizacional> deps = this.jOrgDao
                        .consultarDepartamentosByPadre(idValorPadre);
                return CatalogoTransformer.transformarJerarquias(deps);                
            default :
                final List<Valor> fromBD = this.catDAO
                        .recuperarCatalogoDependiente(catHijo, idValorPadre);
                if (fromBD == null || fromBD.isEmpty()) {
                    List<CatalogoDTO> dummy = new ArrayList<CatalogoDTO>();
                    dummy.add(new CatalogoDTO(1L, "No existen datos en la BD"));
                    // FIXME eliminar al terminar de cargar todo los
                    // catalogos.
                    return dummy;
                }
                return CatalogoTransformer.transformarValor(fromBD);
        } // end switch

    }

    @Override
    public List<CatalogoDTO> consultarAsentamiento(Long idMpio, Long idCiudad,
            Long idTipoAsentamiento) throws NSJPNegocioException {

        if (idMpio == null && idCiudad == null && idTipoAsentamiento == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        List<Asentamiento> ases = this.asentamientoDAO.consultar(idMpio,
                idCiudad, idTipoAsentamiento);
        return CatalogoTransformer.transformarAsentamiento(ases);
    }

    @Override
    public List<CatalogoDTO> recuperarCatalogoCompleto(Catalogos cat)
            throws NSJPNegocioException {
        List<Valor> temp = this.catDAO.recuperarCatalogoCompleto(cat);

        List<CatalogoDTO> resp = CatalogoTransformer
                .transformarValorCompleto(temp);

        return resp;
    }

    @Override
    public List<CatDelitoDTO> consultarDelito() throws NSJPNegocioException {
        return CatalogoTransformer.transformarDelitos(this.delitoDao
                .consultarTodos());
    }

    @Override
    public List<CatDelitoDTO>  consultarDelitosSinIdsGrid(String idsGrid) throws NSJPNegocioException{    
        return CatalogoTransformer.transformarDelitos(this.delitoDao
                .consultarDelitosSinIdsGrid(idsGrid));        		
    }
    
    @Override
    public List<CatDelitoDTO>  consultarDelitoIdSeleccionados(String idsGrid) throws NSJPNegocioException{    
        return CatalogoTransformer.transformarDelitos(this.delitoDao
                .consultarDelitoIdSeleccionados(idsGrid));
    }

	@Override
	public List<CatFaltaAdministrativaDTO> consultarCatalogoFaltaAdministrativa()
			throws NSJPNegocioException {
		return	CatalogoTransformer.transformarFaltaAdministrativa(faltaAdministrativaDao.consultarCatalogoFaltaAdministrativa());	
	}

	public List<CatDelitoDTO> consultarCatDelitoPorFilro(CatDelitoDTO catDelitoFiltroDTO) throws NSJPNegocioException{
		if(catDelitoFiltroDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		CatDelito catDelitoFiltro = CatDelitoTransformer.transformarCatDelito(catDelitoFiltroDTO);
		List<CatDelito> catDelitosFiltro = delitoDao.consultarCatDelitoPorFilro(catDelitoFiltro);
		
		List<CatDelitoDTO> catDelitosFiltroDTO = new ArrayList<CatDelitoDTO>();
		for (CatDelito catDelitoTemp : catDelitosFiltro) 
			catDelitosFiltroDTO.add( CatDelitoTransformer.transformarCatDelito(catDelitoTemp));

		return catDelitosFiltroDTO;
	}

	@Override
	public CatDelitoDTO consultarCatDelitoPorId(Long catDelitoId)
			throws NSJPNegocioException {
		if(catDelitoId == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
			
		if (logger.isDebugEnabled()) {
            logger.debug("BINVENIDO AL SERVICIO PARA OBTENER UN catDelito POR SU ID :: "+ catDelitoId);
        }
		
		return CatDelitoTransformer.transformarCatDelitoCompleto(catDelitoDao.read(catDelitoId));
	}
	
	@Override
	public CatAreasNegocioDTO consultarCatAreaNegocioPorId(Long catAreaNegId)
			throws NSJPNegocioException {
		if(catAreaNegId == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		if (logger.isDebugEnabled()) {
            logger.debug("BINVENIDO AL SERVICIO PARA OBTENER catAreaNegocio POR SU ID :: "+ catAreaNegId);
        }
		return CatAreaNegocioTransformer.transformarCatAreasNegocio(catAreasNegocioDAO.read(catAreaNegId));
	}

	@Override
	public CatDelitoDTO guardarActualizarCatDelito(CatDelitoDTO inputCatDelitoDto)
			throws NSJPNegocioException {
		
		 logger.debug("BINVENIDO AL SERVICIO GUARDAR O ACTUALIZAR UN OBJETO CatDelito");
			
		if(inputCatDelitoDto == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatDelitoDto.getCatDelitoId() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatDelitoDto.getClaveDelito() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatDelitoDto.getNombre() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatDelitoDto.getEsGrave() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}	
		if(inputCatDelitoDto.getPenaMinimaAnios() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatDelitoDto.getPenaMinimaMeses() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatDelitoDto.getPenaMinimaDias() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatDelitoDto.getPenaMaximaAnios() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatDelitoDto.getPenaMaximaMeses() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatDelitoDto.getPenaMaximaDias() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if (inputCatDelitoDto.getUnidadIEspecializada() == null
				|| inputCatDelitoDto.getUnidadIEspecializada().getCatUIEId() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if (inputCatDelitoDto.getClaveInterInstitucional() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		CatDelito filtroCatDelito = new CatDelito();
				
		filtroCatDelito.setClaveDelito(inputCatDelitoDto.getClaveDelito());
		
		//Buscamos la clave
		CatDelito catDelitoEncontrado = catDelitoDao.consultarCatDelitoPorFiltro(filtroCatDelito);
		
		//Actualizar objeto
		if(inputCatDelitoDto.getCatDelitoId() > 0L){
			if(catDelitoEncontrado != null){
				if(catDelitoEncontrado.getCatDelitoId().equals(inputCatDelitoDto.getCatDelitoId())){
				
					CatDelito catDelUpdate = new CatDelito();
					catDelUpdate = catDelitoDao.read(inputCatDelitoDto.getCatDelitoId());
					catDelitoDao.update(CatDelitoTransformer.transformarCatDelitoDtoUpdate(inputCatDelitoDto,catDelUpdate));
					return inputCatDelitoDto;
				}
			}
			else{
				CatDelito catDelUpdate = new CatDelito();
				catDelUpdate = catDelitoDao.read(inputCatDelitoDto.getCatDelitoId());
				catDelitoDao.update(CatDelitoTransformer.transformarCatDelitoDtoUpdate(inputCatDelitoDto,catDelUpdate));
				return inputCatDelitoDto;
			}
		}
		//El objeto es nuevo
		else{
			if(catDelitoEncontrado == null){
				CatDelito catDelNew = new CatDelito(); 
				catDelNew = CatDelitoTransformer.transformarCatDelitoDtpCompleto(inputCatDelitoDto);
				catDelitoDao.create(catDelNew);
				return inputCatDelitoDto;
			}
			//Si el catDelitoEncontrado != null ya existe un objeto con la misma clave
		}
		return null;
	}
	
	@Override
	public CatAreasNegocioDTO guardarActualizarCatAreasNegocio(CatAreasNegocioDTO inputCatAreasNegocioDto)
			throws NSJPNegocioException {
		
		 logger.debug("BINVENIDO AL SERVICIO GUARDAR O ACTUALIZAR UN OBJETO CatAreasNegocio");
			
		if(inputCatAreasNegocioDto == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatAreasNegocioDto.getCatAreasNegocioId() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if (inputCatAreasNegocioDto.getNombreCatAreaNegocio() == null
				|| inputCatAreasNegocioDto.getNombreCatAreaNegocio().trim()
						.isEmpty()) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if (inputCatAreasNegocioDto.getConfInstitucion() == null
				|| inputCatAreasNegocioDto.getConfInstitucion()
						.getConfInstitucionId() == null
				|| inputCatAreasNegocioDto.getConfInstitucion()
						.getConfInstitucionId().equals(0L)) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if(inputCatAreasNegocioDto.getEsUIE() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}	
		
		
		CatAreasNegocio filtroCatAreasNegocio = new CatAreasNegocio();
		
		filtroCatAreasNegocio.setNombreCatAN(inputCatAreasNegocioDto
				.getNombreCatAreaNegocio());
		
		filtroCatAreasNegocio.setConfInstitucion(ConfInstitucionTransformer
				.transformarInstitucion(inputCatAreasNegocioDto
						.getConfInstitucion()));

		//Buscamos por nombre del catatologo y confInstitucionId
		CatAreasNegocio catAreasNegocioEncontrado = catAreasNegocioDAO.consultarCatAreasNegocioPorFiltro(filtroCatAreasNegocio);
		
		//Actualizar objeto
		if(inputCatAreasNegocioDto.getCatAreasNegocioId() > 0L){
			//Si el catAreasNegocioEncontrado != null ya existe un objeto con el mismo nombre
			if(catAreasNegocioEncontrado != null){
				if(catAreasNegocioEncontrado.getCatAreasNegocioId().equals(inputCatAreasNegocioDto.getCatAreasNegocioId())){
				
					CatAreasNegocio catAreaNegUpdate = new CatAreasNegocio();
					catAreaNegUpdate = catAreasNegocioDAO.read(inputCatAreasNegocioDto.getCatAreasNegocioId());
					catAreasNegocioDAO.update(CatAreaNegocioTransformer.traformarCatAreaNegocioDtoUpdate(inputCatAreasNegocioDto, catAreaNegUpdate));
					return inputCatAreasNegocioDto;
				}
			}
			else{	
				CatAreasNegocio catAreaNegUpdate = new CatAreasNegocio();
				catAreaNegUpdate = catAreasNegocioDAO.read(inputCatAreasNegocioDto.getCatAreasNegocioId());
				catAreasNegocioDAO.update(CatAreaNegocioTransformer.traformarCatAreaNegocioDtoUpdate(inputCatAreasNegocioDto, catAreaNegUpdate));
				return inputCatAreasNegocioDto;
			}
		}
		//El objeto es nuevo
		else{
			if(catAreasNegocioEncontrado == null){
				CatAreasNegocio catAreaNegNuevo = new CatAreasNegocio();
				catAreaNegNuevo = CatAreaNegocioTransformer.transformarCatAreasNegocio(inputCatAreasNegocioDto);
				catAreasNegocioDAO.create(catAreaNegNuevo);
				return inputCatAreasNegocioDto;
			}
		}
		return null;
	}

	@Override
	public Long eliminarCatDelito(Long inputCatDelitoId)
			throws NSJPNegocioException {

			Long banderaElimina = 0L;
		 	logger.debug("BINVENIDO AL SERVICIO PARA ELIMINAR UN OBJETO CatDelito");
			
			if(inputCatDelitoId == 0L){
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
			
			Long numeroDelitos = 0L;
			CatDelito catDelitoRemove = new CatDelito();

			catDelitoRemove.setCatDelitoId(inputCatDelitoId);
			
			numeroDelitos = catDelitoDao.consultarNumeroDelitosPorCatDelitoId(catDelitoRemove);
			
			if(numeroDelitos <= 0L){	//El delito NO est&aacute; siendo usado
				catDelitoRemove = catDelitoDao.read(catDelitoRemove.getCatDelitoId());
				if(catDelitoRemove != null){
					//EL DELITO FUE BORRADO
					catDelitoDao.delete(catDelitoRemove);
					banderaElimina = 1L;
				}else{
					//El delito ya NO existe
					banderaElimina = 2L;
				}					
			} else{
				// Hay delitos asociados al catDelito
				banderaElimina = -1L;
			}

			return banderaElimina;
	}
	
	/**
	 * Metodo para eliminar un catAreaNegocio del catalogo
	 */
	@Override
	public Long eliminarCatAreasNegocio(Long catAreasNegocioId)
			throws NSJPNegocioException {

		Long banderaElimina = 0L;
		
		logger.debug("BINVENIDO AL SERVICIO PARA ELIMINAR UN OBJETO CatAreasNegocio");
		if (catAreasNegocioId == 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Long numeroFuncionarios = 0L;
		
		CatAreasNegocio catAreasNegRemove = new CatAreasNegocio();

		catAreasNegRemove.setCatAreasNegocioId(catAreasNegocioId);

		numeroFuncionarios = catAreasNegocioDAO
				.consultarNumeroDeFuncionariosPorAreaNegocio(catAreasNegRemove);

		if (numeroFuncionarios <= 0L) { // El CatAreaNegocio NO est&aacute; siendo
										// usado
			catAreasNegRemove = catAreasNegocioDAO.read(catAreasNegRemove
					.getCatAreasNegocioId());

			if (catAreasNegRemove != null) {
				// El CatAreaNeg sera borrado
				catAreasNegocioDAO.delete(catAreasNegRemove);
				banderaElimina = 1L;
			} else {
				// El CatAreaNeg ya NO existe
				banderaElimina = 2L;
			}
		} else {
			// Hay CatAreaNeg asociados al funcionario
			banderaElimina = -1L;
		}

		return banderaElimina;
	}
	
	public Long eliminarDistrito(Long idCatDistrito)
	throws NSJPNegocioException {
		Long banderaElimina = null;
	 	logger.debug("BINVENIDO AL SERVICIO PARA ELIMINAR UN DISTRITO");
		
		if(idCatDistrito == 0L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		CatDistrito loCatDistrito = null;
		loCatDistrito = distritoDao.read(idCatDistrito);
		if(loCatDistrito != null){
			try {				
				distritoDao.delete(loCatDistrito);
				banderaElimina = 0L;
			} catch (DataAccessException e) {
				e.printStackTrace();

				logger.debug("No es posible eliminar dado que existen relaciones");
				//El distrito ya NO existe
				banderaElimina = -1L;
			}
		}else
			banderaElimina = 2L;
		return banderaElimina;
	}
	
	public Long eliminarAgencia(Long idAgencia)
	throws NSJPNegocioException {
		Long banderaElimina = null;
	 	logger.debug("BINVENIDO AL SERVICIO PARA ELIMINAR UNA AGENCIA");
		
		if(idAgencia == 0L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		CatDiscriminante loCatDiscriminante = null;
		loCatDiscriminante = discriminanteDao.read(idAgencia);
		if(loCatDiscriminante != null){
			try {				
				discriminanteDao.delete(loCatDiscriminante);
				banderaElimina = 0L;
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.debug("No es posible eliminar dado que existen relaciones");
				//La agencia ya NO existe
				banderaElimina = -1L;
			}
		}else
			banderaElimina = 2L;
		return banderaElimina;
	}
	
	
	@Override
	public CatFaltaAdministrativaDTO consultarFaltaAdministrativa(Long idFaltaAdministrativa)
			throws NSJPNegocioException {		
		if (idFaltaAdministrativa == null || idFaltaAdministrativa == 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}		
		return	CatalogoTransformer.transformarFaltaAdministrativa(faltaAdministrativaDao.read(idFaltaAdministrativa));	
	}
	
	
	@Override
	public CatDelitoDTO consultarDelito(Long idDelito)
			throws NSJPNegocioException {
		if (idDelito == null || idDelito == 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		return	CatDelitoTransformer.transformarCatDelito(delitoDao.read(idDelito));	
	}
	
	
	public ConfInstitucionDTO consultarIntitucionPorClave(String clave) throws NSJPNegocioException {
		return ConfInstitucionTransformer.transformarInstitucion(confInstiDao.consultarIntitucionPorClave(clave));
	}
	
}
