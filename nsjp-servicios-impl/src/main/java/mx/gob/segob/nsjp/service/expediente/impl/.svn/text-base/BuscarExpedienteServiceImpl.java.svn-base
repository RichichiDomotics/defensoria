/**
 *
 * Nombre del Programa : BuscarExpedienteServiceImpl.java
 * Autor                            : Cesar Agustin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 05/04/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci&oacute;n del servicio para la b&uacute;squeda de expedientes.
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

package mx.gob.segob.nsjp.service.expediente.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoBusquedaCoordinadorAT;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.menu.EstatusMenuJAR;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.domicilio.DomicilioDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.PermisoExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.dao.involucrado.DefensaInvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.objeto.ObjetoDAO;
import mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dao.persona.NombreDemograficoDAO;
import mx.gob.segob.nsjp.dao.relacion.RelacionDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoHechoDelictivoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteUAVDDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.hecho.HechoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.lugar.AreaGeograficaDTO;
import mx.gob.segob.nsjp.dto.lugar.EspacioAereoDTO;
import mx.gob.segob.nsjp.dto.lugar.EspacioMaritimoDTO;
import mx.gob.segob.nsjp.dto.lugar.PuntoCarreteroDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.Aeronave;
import mx.gob.segob.nsjp.model.Animal;
import mx.gob.segob.nsjp.model.AreaGeografica;
import mx.gob.segob.nsjp.model.Arma;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.DocumentoOficial;
import mx.gob.segob.nsjp.model.Domicilio;
import mx.gob.segob.nsjp.model.Elemento;
import mx.gob.segob.nsjp.model.Embarcacion;
import mx.gob.segob.nsjp.model.EquipoComputo;
import mx.gob.segob.nsjp.model.EspacioAereo;
import mx.gob.segob.nsjp.model.EspacioMaritimo;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Explosivo;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Hecho;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.Joya;
import mx.gob.segob.nsjp.model.Lugar;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.Numerario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Objeto;
import mx.gob.segob.nsjp.model.ObraArte;
import mx.gob.segob.nsjp.model.Organizacion;
import mx.gob.segob.nsjp.model.Parametro;
import mx.gob.segob.nsjp.model.PermisoExpediente;
import mx.gob.segob.nsjp.model.PuntoCarretero;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.Sustancia;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.model.Vegetal;
import mx.gob.segob.nsjp.model.Vehiculo;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.bitacora.ConsultarRegistroBitacoraService;
import mx.gob.segob.nsjp.service.caso.impl.transform.CasoTransformer;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoTransfromer;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.AvisoDesignacionTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.AvisoHechoDelictivoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.domicilio.impl.transform.DomicilioTransformer;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.ConsultarDetalleExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ActividadTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.DatosGeneralesExpedienteTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.hecho.impl.transform.HechoTransformer;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.lugar.impl.transform.LugarTransformer;
import mx.gob.segob.nsjp.service.objeto.impl.transform.AlmacenTransformer;
import mx.gob.segob.nsjp.service.objeto.impl.transform.ObjetoTransformer;
import mx.gob.segob.nsjp.service.organizacion.impl.transform.OrganizacionTransformer;
import mx.gob.segob.nsjp.service.persona.impl.transform.NombreDemograficoTransformer;
import mx.gob.segob.nsjp.service.usuario.UsuarioService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;
import mx.gob.segob.nsjp.service.utilerias.enable.ConstructorCamposFaltantes;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Busca expedientes.
 *
 * @version 1.0
 * @author CesarAgustin
 * @version 1.0
 *
 */
@Service
@Transactional
public class BuscarExpedienteServiceImpl implements BuscarExpedienteService {

	static Map<Long,List<String>> lHmInvolucrados = new HashMap<Long, List<String>>();
    static Map<Long,List<String>> lHmObjetos = new HashMap<Long, List<String>>();
	private static String SEPARADOR = " - ";

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(BuscarExpedienteServiceImpl.class);

	@Autowired
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private DefensaInvolucradoDAO defensaInvolucradoDAO;
	@Autowired
	private NombreDemograficoDAO nombreDemograficoDAO;
    @Autowired
    private ActividadDAO activiDao ;
    @Autowired
    private DelitoDAO delitoDAO;
    @Autowired
    private NumeroExpedienteDAO numeroExpedienteDAO;
    @Autowired
    private NumeroExpedienteDAO noExpDao;
    @Autowired
    private ObjetoDAO objetoDAO;
    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private ParametroDAO parametroDAO;
    @Autowired
    private OrganizacionDAO organizacionDAO;
    @Autowired
    private ElementoDAO eleDao;
	@Autowired
	private ConsultarDocumentoService consultarDocumentoService;
    @Autowired
    private AvisoDesignacionDAO avisoDesignacionDAO;
    @Autowired
    private DomicilioDAO domicilioDAO;
    @Autowired
    private PermisoExpedienteDAO permisoExpedienteDAO;
    @Autowired
    private SolicitudDAO solicitudDAO;
    @Autowired
    private ConsultarRegistroBitacoraService consultarBitacoraService;
    @Autowired
    private UsuarioService usuarioService;
	@Autowired
	private ConsultarDetalleExpedienteService consultarDetalleExpedienteService;
    @Autowired
    private JerarquiaOrganizacionalDAO jerarquiaOrganizacionalDAO;
    @Autowired
    private RelacionDAO relacionDAO;

    /**
	 *
	 */
	@Override
	public List<ExpedienteDTO> buscarExpedientes(
			FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException {

        List<Expediente> expedientes = new ArrayList<Expediente>();

        if (filtrosBusquedaExpediente.getNumeroExpediente() != null) {
            Long areaId = null;
            if (filtrosBusquedaExpediente.getUsuario() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual()
                            .getAreaId() != null)
                areaId = filtrosBusquedaExpediente.getUsuario().getAreaActual()
                        .getAreaId();
            /*
            * Usado para obtener el discriminante Id
            */
            long discriminanteId = 0L;

			if (filtrosBusquedaExpediente.getUsuario() != null
					&& filtrosBusquedaExpediente.getUsuario().getFuncionario() != null
					&& filtrosBusquedaExpediente.getUsuario().getFuncionario()
							.getDiscriminante() != null
					&& filtrosBusquedaExpediente.getUsuario().getFuncionario()
							.getDiscriminante().getCatDiscriminanteId() != null) {

				discriminanteId = filtrosBusquedaExpediente.getUsuario()
						.getFuncionario().getDiscriminante()
						.getCatDiscriminanteId();
			}

            expedientes = expedienteDAO.buscarExpedientes("%"
                    + filtrosBusquedaExpediente.getNumeroExpediente() + "%",
                    areaId,discriminanteId);
        } else {
            logger.info("/**** Consultar Expedientes por evidencia ****/");
            List<Long> expLong = expedienteDAO
                    .consultarExpedientesPorEvidencia(
                    		filtrosBusquedaExpediente);
            for (Long idExp : expLong) {
            	Expediente exp=expedienteDAO.read(idExp);
            	CasoDTO casoDTO=CasoTransformer.transformarCasoBasico(exp.getCaso());
            	casoDTO.setFiltroExpedienteDTO(filtrosBusquedaExpediente);
            	List<Expediente> listExpedientes= expedienteDAO.consultarExpedientesPorIdCaso(casoDTO);
            	for (Expediente expediente : listExpedientes) {
            		expedientes.add(expediente);
				}
            }
        }

        final List<ExpedienteDTO> expedientesDto = new ArrayList<ExpedienteDTO>();
        ExpedienteDTO eDto = null;
        List<NumeroExpediente> numeros = null;
        for (Expediente expediente : expedientes) {

            if (filtrosBusquedaExpediente.getUsuario() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual()
                            .getAreaId() != null) {
                numeros = this.noExpDao.consultarNumeroExpedientes(
                        expediente.getExpedienteId(), filtrosBusquedaExpediente
                                .getUsuario().getAreaActual().getAreaId());
            } else {
                numeros = this.noExpDao
                        .consultarNumeroExpedientesXExpediente(expediente
                                .getExpedienteId());
            }

            for (NumeroExpediente ne : numeros) {
                eDto = ExpedienteTransformer
                        .transformarExpedienteBasico(expediente);
                eDto.setNumeroExpediente(ne.getNumeroExpediente());
                expedientesDto.add(eDto);
            }

        }
        return expedientesDto;
    }


	 /**
	 *
	 */
	@Override
	public List<ExpedienteDTO> buscarExpedientesPorNumExpDiscriminanteArea(
			FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException {

		if (filtrosBusquedaExpediente == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		if (filtrosBusquedaExpediente.getUsuario() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		if (filtrosBusquedaExpediente.getUsuario().getInstitucion() == null
				|| filtrosBusquedaExpediente.getUsuario().getInstitucion()
						.getConfInstitucionId() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		if (filtrosBusquedaExpediente.getUsuario().getFuncionario() == null
				|| filtrosBusquedaExpediente.getUsuario().getFuncionario()
						.getDiscriminante() == null
				|| filtrosBusquedaExpediente.getUsuario().getFuncionario()
						.getDiscriminante().getCatDiscriminanteId() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if (filtrosBusquedaExpediente.getUsuario().getAreaActual() == null
				|| filtrosBusquedaExpediente.getUsuario().getAreaActual()
						.getAreaId() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<Expediente> expedientes = new ArrayList<Expediente>();
        Long institucionId = 0L;

        institucionId = filtrosBusquedaExpediente.getUsuario()
				.getInstitucion().getConfInstitucionId();

		if (filtrosBusquedaExpediente.getNumeroExpediente() != null) {
			logger.info("/**** Consultar Expedientes por numero expediente****/");
			if (institucionId.equals(Instituciones.PJ.getValorId())) {
				//Permite a todos los usuarios de PJ filtrar unicamente por claveFuncionario y CatDiscriminante
				filtrosBusquedaExpediente.getUsuario().getAreaActual().setAreaId(0L);
				filtrosBusquedaExpediente.getUsuario().getFuncionario().setClaveFuncionario(0L);
			}

			if (filtrosBusquedaExpediente.getUsuario().getAreaActual() != null
					&& filtrosBusquedaExpediente.getUsuario().getAreaActual().getAreaId() != null
					&& filtrosBusquedaExpediente.getUsuario().getAreaActual().getAreaId() > 0L
					&& filtrosBusquedaExpediente.getUsuario().getAreaActual()
							.getBuscarEnJerarquia() != null
					&& filtrosBusquedaExpediente.getUsuario().getAreaActual()
							.getBuscarEnJerarquia()) {

				JerarquiaOrganizacional raiz = new JerarquiaOrganizacional(
						filtrosBusquedaExpediente.getUsuario().getAreaActual().getAreaId());
				List<JerarquiaOrganizacional> lstJerarquiasDependientes = jerarquiaOrganizacionalDAO
						.getArbolJerarquiasDependientes(raiz);


				if (lstJerarquiasDependientes != null
						&& !lstJerarquiasDependientes.isEmpty()) {
					if(filtrosBusquedaExpediente.getJerarquiaOrgSubordinadas() == null) {
						filtrosBusquedaExpediente.setJerarquiaOrgSubordinadas(new HashSet<JerarquiaOrganizacionalDTO>());
					}
					for (JerarquiaOrganizacional areaDep : lstJerarquiasDependientes) {
						filtrosBusquedaExpediente.getJerarquiaOrgSubordinadas().add(new JerarquiaOrganizacionalDTO(areaDep.getJerarquiaOrganizacionalId()));
					}
				}
			}
			expedientes = expedienteDAO.buscadorDeExpedientes(filtrosBusquedaExpediente);
		} else {
			logger.info("/**** Consultar Expedientes por evidencia ****/");
			List<Long> expLong = expedienteDAO
					.consultarExpedientesPorEvidencia(
							filtrosBusquedaExpediente);
			for (Long idExp : expLong) {
				expedientes.add(expedienteDAO.read(idExp));
			}
		}

		final List<ExpedienteDTO> expedientesDto = new ArrayList<ExpedienteDTO>();
		ExpedienteDTO eDto = null;
		List<Long> ids=new ArrayList<Long>();
		if(filtrosBusquedaExpediente.getFiltroEspecificoDeAreaRolActual()!=null &&
				filtrosBusquedaExpediente.getFiltroEspecificoDeAreaRolActual().equals(Areas.UNIDAD_INVESTIGACION.parseLong())){
			Map mapa=new HashMap<Long, Expediente>();
			List<Expediente> expedientesCompletos = new ArrayList<Expediente>();
			for (Expediente expediente : expedientes) {
				boolean op=false;
				if(mapa.isEmpty()){
					mapa.put(expediente.getExpedienteId(), expediente);
					expedientesCompletos.add(expediente);
					op=true;
				}else if (!mapa.containsKey(expediente.getExpedienteId())){
					mapa.put(expediente.getExpedienteId(), expediente);
					expedientesCompletos.add(expediente);
					op=true;
				}
				if(op){
					List<NumeroExpediente> listaAlterna=numeroExpedienteDAO.consultarNumeroExpedientesXExpediente(expediente.getExpedienteId());
					List<Expediente> expedientesAdjuntos = new ArrayList<Expediente>();
					boolean existeUI=false;
					Long idNumeroExpediente=-1L;
					Long idFuncionario=-1L;
					if(listaAlterna.isEmpty()){

					}
					for (NumeroExpediente numeroExpediente : listaAlterna) {
						if(numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.UNIDAD_INVESTIGACION.parseLong())){
							existeUI=true;
							idNumeroExpediente=numeroExpediente.getNumeroExpedienteId();
							idFuncionario=numeroExpediente.getFuncionario().getClaveFuncionario();
						}
						if(!expediente.getNumeroExpediente().equals(numeroExpediente.getNumeroExpediente())&&(
								numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.UNIDAD_INVESTIGACION.parseLong())||
								numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong())||
								numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong()))){
							Expediente expedienteAlterno=new Expediente();
							List<NumeroExpediente> lista=new ArrayList<NumeroExpediente>();
							lista.add(numeroExpediente);
							Set<NumeroExpediente> set=new HashSet<NumeroExpediente>();
							set.addAll(lista);
							expediente.setNumeroExpedientes(set);
							BeanUtils.copyProperties(expediente,expedienteAlterno);
							expedienteAlterno.setNumeroExpediente(numeroExpediente.getNumeroExpediente());
							expedienteAlterno.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
							expedientesAdjuntos.add(expedienteAlterno);
							CatDiscriminante catDiscriminante=expedienteDAO.read(expediente.getExpedienteId()).getDiscriminante();
							expediente.setDiscriminante(catDiscriminante);
						}else{
							List<NumeroExpediente> lista=new ArrayList<NumeroExpediente>();
							lista.add(numeroExpediente);
							Set<NumeroExpediente> set=new HashSet<NumeroExpediente>();
							set.addAll(lista);
							expediente.setNumeroExpedientes(set);
							CatDiscriminante catDiscriminante=expedienteDAO.read(expediente.getExpedienteId()).getDiscriminante();
							expediente.setDiscriminante(catDiscriminante);

						}
					}
					Long idnumeroDeexpedientePapa=0L;
					boolean entro=false;
					for (Expediente expediente2 : expedientesAdjuntos) {
						if(existeUI){
							if(filtrosBusquedaExpediente.getUsuario()!=null &&
									filtrosBusquedaExpediente.getUsuario().getFuncionario()!=null&&
									filtrosBusquedaExpediente.getUsuario().getFuncionario().getClaveFuncionario()!=null&&
									filtrosBusquedaExpediente.getUsuario().getFuncionario().getClaveFuncionario().equals(idFuncionario)){
								expediente.setNumeroExpedienteId(idNumeroExpediente);
								expediente2.setNumeroExpedienteId(idNumeroExpediente);
								expediente.setIdNumeroExpedienteBusquedaATP(idNumeroExpediente);
								expediente2.setIdNumeroExpedienteBusquedaATP(idNumeroExpediente);
								expedientesCompletos.add(expediente2);
								ids.add(idNumeroExpediente);
								idnumeroDeexpedientePapa=idNumeroExpediente;
							}else{
								expediente.setNumeroExpedienteId(-2L);
								expediente2.setNumeroExpedienteId(-2L);
								expediente.setIdNumeroExpedienteBusquedaATP(-2L);
								expediente2.setIdNumeroExpedienteBusquedaATP(-2L);
								expedientesCompletos.add(expediente2);
								ids.add(-2L);
								idnumeroDeexpedientePapa=-2L;
							}
						}else{
							expediente.setNumeroExpedienteId(-1L);
							expediente2.setNumeroExpedienteId(-1L);
							expediente.setIdNumeroExpedienteBusquedaATP(-1L);
							expediente2.setIdNumeroExpedienteBusquedaATP(-1L);
							expedientesCompletos.add(expediente2);
							ids.add(-1L);
							idnumeroDeexpedientePapa=-1L;
						}
						entro=true;
					}
					if(!entro){
						if(existeUI){
							if(filtrosBusquedaExpediente.getUsuario()!=null &&
									filtrosBusquedaExpediente.getUsuario().getFuncionario()!=null&&
									filtrosBusquedaExpediente.getUsuario().getFuncionario().getClaveFuncionario()!=null&&
									filtrosBusquedaExpediente.getUsuario().getFuncionario().getClaveFuncionario().equals(idFuncionario)){
								idnumeroDeexpedientePapa=idNumeroExpediente;
							}else{
								idnumeroDeexpedientePapa=-2L;
							}
						}else{
							idnumeroDeexpedientePapa=-1L;
						}
					}
					ids.add(idnumeroDeexpedientePapa);
				}
			}
			expedientes=null;
			expedientes=expedientesCompletos;
			expedientes = paginacionManualExpedientes(expedientes);
			ids=paginacionManualIds(ids);
			int contador=0;
			for (Expediente expediente : expedientes) {
	            eDto = ExpedienteTransformer
	                    .transformarExpedienteBasico(expediente);
	            eDto.setNumeroExpediente(expediente.getNumeroExpediente());
	            Long id=ids.get(contador);
	            if(id==null){
	            	eDto.setIdNumeroExpedienteBusquedaATP(-1L);
	            }else{
	            	eDto.setIdNumeroExpedienteBusquedaATP(id);
	            }

	            expedientesDto.add(eDto);
	            contador++;
	        }


	        return expedientesDto;

		}
		for (Expediente expediente : expedientes) {
            eDto = ExpedienteTransformer
                    .transformarExpedienteBasico(expediente);
            eDto.setNumeroExpediente(expediente.getNumeroExpediente());
            
            Calidades[] cal=new Calidades[1];
            cal[0]=Calidades.PROBABLE_RESPONSABLE_PERSONA;
            List<Involucrado> involucrados= involucradoDAO.obtenerInvolucradosPorExpedienteYCalidades(expediente.getExpedienteId(), cal, null);
            
            /*Se incluye involucrado/*Enable IT ByYolo*/
            Involucrado inputado= new Involucrado();
            inputado=involucrados.get(0);
            for (Involucrado involucrado : involucrados) {
				if(involucrado.getElementoId()>inputado.getElementoId()){
					inputado=involucrado;
				}
			}
            
            List<NumeroExpediente> numeroExpedientes = new ArrayList<NumeroExpediente>();
            numeroExpedientes.addAll(expediente.getNumeroExpedientes());
            
            for (NumeroExpediente numeroExpediente : numeroExpedientes) {
            	eDto.setNumeroExpediente(numeroExpediente.getNumeroExpediente());
			}
            
            eDto.setInputado(InvolucradoTransformer.transformarInvolucrado(inputado));
            expedientesDto.add(eDto);

        }


        return expedientesDto;
    }

	private List<Expediente> paginacionManualExpedientes(
			List<Expediente> expsRespuesta) {
		final PaginacionDTO pag=PaginacionThreadHolder.get();
		if((pag.getPage()*pag.getRows())-pag.getRows()>expsRespuesta.size()){
			pag.setPage(1);
		}
		int inicio=0;
		if(pag.getPage()<=1){
			inicio=0;
		}else{
			inicio=((pag.getPage()-1)*pag.getRows());
		}
		int indiceFinal=inicio+pag.getRows();
		List<Expediente> listaAlterna=expsRespuesta;
		expsRespuesta=new ArrayList<Expediente>();
		if(indiceFinal>=listaAlterna.size()){
			for (int i = inicio; i < listaAlterna.size(); i++) {
				expsRespuesta.add(listaAlterna.get(i));
			}
		}else{
			for (int i = inicio; i < indiceFinal; i++) {
				expsRespuesta.add(listaAlterna.get(i));
			}
		}
		pag.setTotalRegistros((long)listaAlterna.size());
		pag.setPaginacionHecha(true);
		PaginacionThreadHolder.set(pag);
		return expsRespuesta;
	}
	private List<Long> paginacionManualIds(
			List<Long> expsRespuesta) {
		final PaginacionDTO pag=PaginacionThreadHolder.get();
		if((pag.getPage()*pag.getRows())-pag.getRows()>expsRespuesta.size()){
			pag.setPage(1);
		}
		int inicio=0;
		if(pag.getPage()<=1){
			inicio=0;
		}else{
			inicio=((pag.getPage()-1)*pag.getRows());
		}
		int indiceFinal=inicio+pag.getRows();
		List<Long> listaAlterna=expsRespuesta;
		expsRespuesta=new ArrayList<Long>();
		if(indiceFinal>=listaAlterna.size()){
			for (int i = inicio; i < listaAlterna.size(); i++) {
				expsRespuesta.add(listaAlterna.get(i));
			}
		}else{
			for (int i = inicio; i < indiceFinal; i++) {
				expsRespuesta.add(listaAlterna.get(i));
			}
		}
		pag.setTotalRegistros((long)listaAlterna.size());
		pag.setPaginacionHecha(true);
		PaginacionThreadHolder.set(pag);
		return expsRespuesta;
	}

	@Override
	public ExpedienteDTO obtenerExpediente(ExpedienteDTO input)throws NSJPNegocioException {
        logger.info("/**** Obtener Expediente por ID ****/");
        logger.debug("ExpedienteDTO.numeroExpedienteId :: "+input.getNumeroExpedienteId());
        if (logger.isDebugEnabled()) {
            this.logBanderas(input);
        }
        NumeroExpediente numeroExpediente = new NumeroExpediente();
        ExpedienteDTO output = new ExpedienteDTO();
        if (input.getNumeroExpedienteId() != null) {
            List<InvolucradoDTO> involucradosDto = new ArrayList<InvolucradoDTO>();
            numeroExpediente 			= numeroExpedienteDAO.read(input.getNumeroExpedienteId());
            final Expediente expediente = numeroExpediente.getExpediente();
            output 						= extraerExpediente(input, numeroExpediente,involucradosDto, expediente);
            output.setExpedienteId(expedienteDAO.obtenerExpedienteIdPorIdNumerExpediente(input.getNumeroExpedienteId()));
        }
        return output;
    }

    @Override
    public ExpedienteDTO obtenerExpedientePorExpedienteId(ExpedienteDTO input)
            throws NSJPNegocioException {
        logger.info("/**** Obtener Expediente por ID ****/");
        logger.debug("ExpedienteDTO.numeroExpedienteId :: "
                + input.getExpedienteId());
        if (logger.isDebugEnabled()) {
            this.logBanderas(input);
        }
        ExpedienteDTO output = new ExpedienteDTO();

        if (input.getExpedienteId() != null) {
            List<InvolucradoDTO> involucradosDto = new ArrayList<InvolucradoDTO>();

            final Expediente expediente = this.expedienteDAO.read(input
                    .getExpedienteId());
            output = extraerExpediente(input, null,
                    involucradosDto, expediente);
        }
        return output;
    }

    /**
     * @param input
     * @param numeroExpediente
     * @param involucradosDto
     * @param expediente
     * @return
     * @throws NSJPNegocioException
     */
    private ExpedienteDTO extraerExpediente(ExpedienteDTO input,NumeroExpediente numeroExpediente,List<InvolucradoDTO> involucradosDto, final Expediente expediente)
            									throws NSJPNegocioException {
        logger.info("Inicia - extraerExpediente(...)");
        ExpedienteDTO output;
        Hecho hecho;
        List<Involucrado> involucrados = new ArrayList<Involucrado>();
        List<Objeto> objetosExpediente = new ArrayList<Objeto>();
        List<Documento> documentos = new ArrayList<Documento>();
        List<AvisoDesignacion> designaciones = new ArrayList<AvisoDesignacion>();
        // los delitos se obtienen en el transformer
        output = ExpedienteTransformer.transformaExpediente(expediente);
        // se valida que el numero de expediente es nulo para el escenario del
        // clonado del expediente
        if (numeroExpediente != null) {
            output.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
            output.setFechaApertura(numeroExpediente.getFechaApertura());
            output.setNumeroExpediente(numeroExpediente.getNumeroExpediente());
            output.setArea(new AreaDTO(numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId(), numeroExpediente.getJerarquiaOrganizacional().getNombre()));

            if (numeroExpediente.getEtapa() != null) {
                output.setEtapa(new ValorDTO(numeroExpediente.getEtapa().getValorId(), numeroExpediente.getEtapa().getValor()));
            }
            // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de
            // output.getEstatus();
            // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
            // output.getEstatusNumeroExpediente();
            if (expediente!=null && expediente.getEstatus()!= null) {
            	output.setEstatus(new ValorDTO(expediente.getEstatus().getValorId(), expediente.getEstatus().getValor()));
            }

            if(numeroExpediente.getEstatus()!=null){
                output.setEstatusNumeroExpediente(ValorTransformer.transformar(numeroExpediente.getEstatus()));
            }

            // ------------------------------------------------------------------------------------------------

            if (numeroExpediente.getTipoExpediente() != null) {
                output.setTipoExpediente(new ValorDTO(numeroExpediente.getTipoExpediente().getValorId(), numeroExpediente.getTipoExpediente().getValor()));
            }

            if (numeroExpediente.getAlmacen() != null)
                output.setAlmacenDTO(AlmacenTransformer.transformarAlmacenBasico(numeroExpediente.getAlmacen()));

            //Duenio o responsable del tramite
            if(numeroExpediente.getFuncionario()!=null ){
        		output.setResponsableTramite(FuncionarioTransformer.transformarFuncionario(numeroExpediente.getFuncionario()));
            }
        }
        if (input.isAvisosDesignacionSolicitados() && numeroExpediente != null) {
            designaciones = avisoDesignacionDAO.consultarAvisosDesignacionPorIdExpediente(numeroExpediente.getExpediente().getExpedienteId());
            if (designaciones != null) {
                for (AvisoDesignacion aviso : designaciones) {
                    output.getAvisosDesignacion().add(AvisoDesignacionTransformer.transformar(aviso));
                }
            }
        }
        ///cehcar aqui la infomacion que se extrae de lo objetos y evidencias
        if (input.isObjetosSolicitados()) {
            objetosExpediente = objetoDAO.consultarObjetosByExpediente(expediente.getExpedienteId());
            if (objetosExpediente != null) {
                for (Objeto objeto : objetosExpediente) {
                    output.getObjetosDTO().add(ObjetoTransformer.transformarObjeto(objeto));
                }
            }
        }
        if (input.isHechoSolicitado()) {
            hecho = expediente.getHecho();
            if (hecho != null && hecho.getHechoId() != null) {
                output.setHechoDTO(HechoTransformer.transformarHecho(hecho));
                if (hecho.getLugar() != null) {
                    Lugar lg = hecho.getLugar();
                    logger.debug("lg.getClass().getSimpleName()->"+ lg.getClass().getSimpleName());

                    if (lg instanceof Domicilio) {
                        logger.debug("EL LUGAR DEL HECHO ES UN DOMICILIO ");
                        DomicilioDTO domHecho = DomicilioTransformer.transformarDomicilio((Domicilio) lg);
                        output.getHechoDTO().setLugar(domHecho);
                    } else if (lg instanceof AreaGeografica) {
                        logger.debug("EL LUGAR DEL HECHO ES UN AREA GEOGRAFICA ");
                        AreaGeograficaDTO areaGeo = LugarTransformer.transformarAreaGeografica((AreaGeografica) lg);
                        output.getHechoDTO().setLugar(areaGeo);
                    } else if (lg instanceof EspacioAereo) {
                        logger.debug("EL LUGAR DEL HECHO ES UN ESPACIO AEREO ");
                        EspacioAereoDTO espacioAereo = LugarTransformer.transformarEspacioAereo((EspacioAereo) lg);
                        output.getHechoDTO().setLugar(espacioAereo);
                    } else if (lg instanceof EspacioMaritimo) {
                        logger.debug("EL LUGAR DEL HECHO ES UN ESPACIO MARITIMO ");
                        EspacioMaritimoDTO espacioMar = LugarTransformer.transformarEspacioMaritimo((EspacioMaritimo) lg);
                        output.getHechoDTO().setLugar(espacioMar);
                    } else if (lg instanceof PuntoCarretero) {
                        logger.debug("EL LUGAR DEL HECHO ES UN ESPACIO MARITIMO ");
                        PuntoCarreteroDTO punCarr = LugarTransformer.transformarPuntoCarretero((PuntoCarretero) lg);
                        output.getHechoDTO().setLugar(punCarr);
                    }
                }
            }
        }
        if (input.isInvolucradosSolicitados()) {
            involucrados = involucradoDAO.consultarInvolucradosByExpediente(expediente.getExpedienteId());
            if (involucrados.size() > 0) {
                for (Involucrado involucrado : involucrados) {
                    InvolucradoDTO invoDto = InvolucradoTransformer.transformarInvolucrado(involucrado);
                    if (input.isDomicliosInvolucradoSolicitados()) {
                        Domicilio domicilio = domicilioDAO.obtenerDomicilioByRelacion(invoDto.getElementoId(), new Long(Relaciones.RESIDENCIA.ordinal()));
                        Domicilio domicilioNotificacion = domicilioDAO.obtenerDomicilioByRelacion(invoDto.getElementoId(), new Long(Relaciones.NOTIFICACION.ordinal()));
                        if (domicilio != null) {
                        	invoDto.setDomicilio(DomicilioTransformer.transformarDomicilio(domicilio));
                        }
                        if (domicilioNotificacion != null) {
                            invoDto.setDomicilioNotificacion(DomicilioTransformer.transformarDomicilio(domicilioNotificacion));
                        }
                    }
                    // Si es persona Moral Consultar Organizacion
                    if (invoDto.getTipoPersona().equals(0L)) {
                        Organizacion organizacion = organizacionDAO.obtenerOrganizacionByRelacion(invoDto.getElementoId(),new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()));
                        invoDto.setOrganizacionDTO(OrganizacionTransformer.transformarOrganizacion(organizacion));
                    }
                    ConstructorCamposFaltantes.crearNombresDemograficosParaDTO(relacionDAO, organizacionDAO, involucrado, invoDto);
                    involucradosDto.add(invoDto);
                }
            }
            output.setInvolucradosDTO(involucradosDto);
        }
        if (input.isDocumentosSolicitados()) {
        	/*Se agrega parametro usuarioDTO para cumplir con la firma va null/*byYolo*/
        	UsuarioDTO usuarioDTO = new UsuarioDTO();
            documentos = documentoDAO.consultarDocumentoPorExpediente(expediente.getExpedienteId(), usuarioDTO);
            if (documentos != null) {
                for (Documento loDocumento : documentos) {
                    output.getDocumentosDTO().add(DocumentoTransformer.transformarDocumento(loDocumento));
                }
            }
        }
        final Actividad actAct = this.activiDao.obtenerActividadActual(expediente.getExpedienteId());
        output.setActividadActual(ActividadTransformer.transformarActividad(actAct));
        if (numeroExpediente != null) {
            output.setEtapasPasadas(this.consultarBitacoraService.consultarEtapasPasadas(output));
        }
        output.setEsReplicado(expediente.getEsReplicado()==null? false: expediente.getEsReplicado());
        logger.info("Fin - extraerExpediente(...)");
        return output;
    }

	@Override
	public ExpedienteDTO obtenerExpedienteDefensoria(ExpedienteDTO input)
            throws NSJPNegocioException {
        logger.info("/**** Obtener Expediente por ID ****/");
        if (logger.isDebugEnabled()) {
            this.logBanderas(input);
        }
        NumeroExpediente numeroExpediente = new NumeroExpediente();
        ExpedienteDTO output = new ExpedienteDTO();
        List<InvolucradoDTO> involucradosDto = new ArrayList<InvolucradoDTO>();

        List<Involucrado> involucrados = new ArrayList<Involucrado>();
        List<Objeto> objetosExpediente = new ArrayList<Objeto>();
        List<Documento> documentos = new ArrayList<Documento>();
        List<AvisoDesignacion> designaciones = new ArrayList<AvisoDesignacion>();
        Hecho hecho = new Hecho();
        Expediente expediente = new Expediente();
        if (input.getNumeroExpedienteId() != null) {
            numeroExpediente = numeroExpedienteDAO.read(input.getNumeroExpedienteId());
            expediente = numeroExpediente.getExpediente();
            //los delitos se obitienen en el transformer
            output = ExpedienteTransformer.transformaExpediente(expediente);
            output.setNumeroExpedienteId(numeroExpediente
                    .getNumeroExpedienteId());
            output.setFechaApertura(numeroExpediente.getFechaApertura());
            output.setNumeroExpediente(numeroExpediente.getNumeroExpediente());
            if (numeroExpediente.getEtapa() != null) {
                output.setEtapa(new ValorDTO(numeroExpediente.getEtapa()
                        .getValorId(), numeroExpediente.getEtapa().getValor()));
            }

    	    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de
            // output.getEstatus();
            // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
            // output.getEstatusNumeroExpediente();

            if (numeroExpediente!=null &&
            	numeroExpediente.getExpediente()!=null &&
            	numeroExpediente.getExpediente().getEstatus() != null) {
                output.setEstatus(new ValorDTO(numeroExpediente.getExpediente().getEstatus()
                        .getValorId()));
            }

            if(numeroExpediente!=null && numeroExpediente.getEstatus()!=null){
                 output.setEstatusNumeroExpediente(ValorTransformer.transformar(numeroExpediente.getEstatus()));
            }

            // ------------------------------------------------------------------------------------------------

            if (numeroExpediente.getAlmacen() != null)
                output.setAlmacenDTO(AlmacenTransformer
                        .transformarAlmacenBasico(numeroExpediente.getAlmacen()));

            if (input.isAvisosDesignacionSolicitados()) {
                designaciones = avisoDesignacionDAO
                        .consultarAvisosDesignacionPorIdExpediente(numeroExpediente
                                .getExpediente().getExpedienteId());
                if (designaciones != null) {
                    for (AvisoDesignacion aviso : designaciones) {
                        output.getAvisosDesignacion().add(
                                AvisoDesignacionTransformer.transformar(aviso));
                    }
                }
            }
            if (input.isObjetosSolicitados()) {
                objetosExpediente = objetoDAO
                        .consultarObjetosByExpediente(expediente
                                .getExpedienteId());
                if (objetosExpediente != null) {
                    for (Objeto objeto : objetosExpediente) {
                        output.getObjetosDTO().add(
                                ObjetoTransformer.transformarObjeto(objeto));
                    }
                }
            }
            if (input.isHechoSolicitado()) {
                hecho = expediente.getHecho();
                if (hecho != null && hecho.getHechoId() != null) {
                    output.setHechoDTO(HechoTransformer.transformarHecho(hecho));
                    if (hecho.getLugar() != null) {
                        Lugar lg = hecho.getLugar();
                        logger.debug("lg.getClass().getSimpleName()->" +lg.getClass().getSimpleName());

                        if (lg instanceof Domicilio) {
                            logger.debug("EL LUGAR DEL HECHO ES UN DOMICILIO ");
                            DomicilioDTO domHecho = DomicilioTransformer
                                    .transformarDomicilio((Domicilio) lg);
                            output.getHechoDTO().setLugar(domHecho);
                        } else if (lg instanceof AreaGeografica) {
                            logger.debug("EL LUGAR DEL HECHO ES UN AREA GEOGRAFICA ");
                            AreaGeograficaDTO areaGeo = LugarTransformer
                                    .transformarAreaGeografica((AreaGeografica) lg);
                            output.getHechoDTO().setLugar(areaGeo);
                        } else if (lg instanceof EspacioAereo) {
                            logger.debug("EL LUGAR DEL HECHO ES UN ESPACIO AEREO ");
                            EspacioAereoDTO espacioAereo = LugarTransformer
                                    .transformarEspacioAereo((EspacioAereo) lg);
                            output.getHechoDTO().setLugar(espacioAereo);
                        } else if (lg instanceof EspacioMaritimo) {
                            logger.debug("EL LUGAR DEL HECHO ES UN ESPACIO MARITIMO ");
                            EspacioMaritimoDTO espacioMar = LugarTransformer
                                    .transformarEspacioMaritimo((EspacioMaritimo) lg);
                            output.getHechoDTO().setLugar(espacioMar);
                        } else if (lg instanceof PuntoCarretero) {
                            logger.debug("EL LUGAR DEL HECHO ES UN ESPACIO MARITIMO ");
                            PuntoCarreteroDTO punCarr = LugarTransformer
                                    .transformarPuntoCarretero((PuntoCarretero) lg);
                            output.getHechoDTO().setLugar(punCarr);
                        }
                    }
                }
            }
            if (input.isInvolucradosSolicitados()) {
                involucrados = involucradoDAO.consultarInvolucradosByExpediente(expediente.getExpedienteId());
                if (involucrados.size() > 0) {
                    for (Involucrado involucrado : involucrados) {
                        InvolucradoDTO invoDto = InvolucradoTransformer.transformarInvolucrado(involucrado);
                        if (input.isDomicliosInvolucradoSolicitados()) {
                            Domicilio domicilio = domicilioDAO.obtenerDomicilioByRelacion(invoDto.getElementoId(), new Long(Relaciones.RESIDENCIA.ordinal()));
                            Domicilio domicilioNotificacion = domicilioDAO.obtenerDomicilioByRelacion(invoDto.getElementoId(), new Long(Relaciones.NOTIFICACION.ordinal()));
                            if (domicilio != null) {
                                invoDto.setDomicilio(DomicilioTransformer.transformarDomicilio(domicilio));
                            }
                            if (domicilioNotificacion != null) {
                                invoDto.setDomicilioNotificacion(DomicilioTransformer.transformarDomicilio(domicilioNotificacion));
                            }
                        }

                        // Si es persona Moral Consultar Organizacion
                        if (invoDto.getTipoPersona().equals(0L)) {
                            Organizacion organizacion = organizacionDAO.obtenerOrganizacionByRelacion(invoDto.getElementoId(),new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()));
                            invoDto.setOrganizacionDTO(OrganizacionTransformer.transformarOrganizacion(organizacion));
                        }

                        if(involucrado.getCalidad().getTipoCalidad().getValorId().longValue() == Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId().longValue()){
                        	output.setSolicitante(invoDto);
                        }

                        involucradosDto.add(invoDto);
                    }

                    Involucrado defendido = defensaInvolucradoDAO.consultarInvolucradoNumeroExpedienteDefensoria(input.getNumeroExpedienteId());
                    if(defendido != null){
                    	output.setInputado(InvolucradoTransformer.transformarInvolucrado(defendido));
                    }
                }
                output.setInvolucradosDTO(involucradosDto);
            }
            if (input.isDocumentosSolicitados()) {
            	/*Se agrega parametro usuarioDTO para cumplir con la firma va null/*byYolo*/
            	UsuarioDTO usuarioDTO = new UsuarioDTO();
                documentos = documentoDAO
                        .consultarDocumentoPorExpediente(expediente
                                .getExpedienteId(), usuarioDTO);
                if (documentos != null) {
                    for (Documento loDocumento : documentos) {
                        output.getDocumentosDTO().add(
                                DocumentoTransformer
                                        .transformarDocumento(loDocumento));
                    }
                }
            }
            final Actividad actAct = this.activiDao
                    .obtenerActividadActual(expediente.getExpedienteId());
            output.setActividadActual(ActividadTransformer
                    .transformarActividad(actAct));
        }

        return output;
    }


	/**
	 * Manda al log ls banderas actividas par ala obtenci&oacute;n del expediente.
	 * @param input
	 */
    private void logBanderas(ExpedienteDTO input) {
        logger.debug("******************** FILTROS *******************");
        if (input.isMedidasCautelaresSolicitadas())
            logger.debug("**  medidasCautelaresSolicitadas Activado     **");
        if (input.isInvolucradosSolicitados())
            logger.debug("**  involucradosSolicitados Activado          **");
        if (input.isDomicliosInvolucradoSolicitados())
            logger.debug("**  domicliosInvolucradoSolicitados Activado  **");
        if (input.isObjetosSolicitados())
            logger.debug("**  objetosSolicitados Activado               **");
        if (input.isCadenasCustodiaSolicitadas())
            logger.debug("**  cadenasCustodiaSolicitadas Activado       **");
        if (input.isDocumentosSolicitados())
            logger.debug("**  documentosSolicitados Activado            **");
        if (input.isHechoSolicitado())
            logger.debug("**  hechoSolicitado Activado                  **");
        if (input.isAvisosDesignacionSolicitados())
            logger.debug("**  avisosDesignacionSolicitados Activado     **");
        logger.debug("******************** FILTROS *******************");
    }
	@Override
	public List<ExpedienteDTO> consultarExpedientesPorCaso(CasoDTO caso)
			throws NSJPNegocioException {

		List<Expediente> fromBD = null;
		if(caso.getCasoId() != null){
			fromBD = this.expedienteDAO
				.consultarExpedientesPorIdCaso(caso.getCasoId(), caso
						.getUsuario().getAreaActual().getAreaId());
			return ExpedienteTransformer.transformarExpedientesBasico(fromBD);
		}else{
			fromBD = this.expedienteDAO
			.consultarExpedientesPorNumeroCaso(caso.getNumeroGeneralCaso());
			List<ExpedienteDTO> expedientes = ExpedienteTransformer.transformarExpedientesBasico(fromBD);

			for (ExpedienteDTO expedienteDTO : expedientes) {
				List<Involucrado> involucrados = involucradoDAO
				.obtenerInvByIdExpAndCalidad(expedienteDTO.getExpedienteId(),
						Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(), null);
				for (Involucrado involucrado : involucrados) {
					expedienteDTO.getInvolucradosDTO().add(InvolucradoTransformer.transformarInvolucrado(involucrado));
				}
			}

			return expedientes;
		}
	}

	@Override
	public List<InvolucradoDTO> buscarExpedientesPorFiltros(
			FiltroExpedienteDTO filtrosBusquedaExpediente)
			throws NSJPNegocioException {

		List<InvolucradoDTO> involucradosDTO = new ArrayList<InvolucradoDTO>();
		List<Involucrado> involucrados = new ArrayList<Involucrado>();

		if (filtrosBusquedaExpediente.getAlias() != null
				&& filtrosBusquedaExpediente.getTipoBusqueda().equals(
						new Long(0))) {

			involucrados = involucradoDAO
					.consultarExpedientesByAlias(filtrosBusquedaExpediente
							.getAlias());
		} else if (filtrosBusquedaExpediente.getAlias() != null
				&& filtrosBusquedaExpediente.getTipoBusqueda().equals(
						new Long(1))) {

			involucrados = involucradoDAO.consultarExpedientesByAliasLike("%"
					+ filtrosBusquedaExpediente.getAlias() + "%");
		} else {
			String nombre = "%";
			String apellidos = "%";
			String apellidoMat = "%";

			if (filtrosBusquedaExpediente.getNombre() != null
					&& filtrosBusquedaExpediente.getNombre().length() > 0)
				nombre = nombre + filtrosBusquedaExpediente.getNombre();
			if (filtrosBusquedaExpediente.getApellidos() != null
					&& filtrosBusquedaExpediente.getApellidos().length() > 0)
				apellidos = apellidos
						+ filtrosBusquedaExpediente.getApellidos();
			if (filtrosBusquedaExpediente.getApellidoMat()!=null && !filtrosBusquedaExpediente.getApellidoMat().isEmpty())
				apellidoMat = apellidoMat + filtrosBusquedaExpediente.getApellidoMat();

			nombre = nombre + "%";
			apellidos = apellidos + "%";
			apellidoMat = apellidoMat + "%";
			//Se coloca el siguiente codigo para que se pueda pasar un DTO y los parametros solos
			//esto es para poder pasar mas parametros de filtrado como el discriminante y el usuario
				filtrosBusquedaExpediente.setNombre(nombre);
				filtrosBusquedaExpediente.setApellidos(apellidos);
				filtrosBusquedaExpediente.setApellidoMat(apellidoMat);



			involucrados = involucradoDAO.consultarExpedientesByNombre(filtrosBusquedaExpediente);
		}

		for (Involucrado involucrado : involucrados) {
			involucradosDTO.add(InvolucradoTransformer
					.transformarInvolucradoBasico(involucrado));
		}

		for (InvolucradoDTO involucradoDTO : involucradosDTO) {
			List<NombreDemografico> nombres = nombreDemograficoDAO
					.consutarNombresByInvolucrado(involucradoDTO
							.getElementoId());
			involucradoDTO
					.setNombresDemograficoDTO(NombreDemograficoTransformer
							.transformarNombreDemograficoBasico(nombres));
		}

		return involucradosDTO;
	}

	@Override
	public List<InvolucradoDTO> buscarExpedientesPorFiltrosYDiscriminante(
			FiltroExpedienteDTO filtrosBusquedaExpediente,UsuarioDTO usuarioFirmado)
			throws NSJPNegocioException {

		List<InvolucradoDTO> involucradosDTO = new ArrayList<InvolucradoDTO>();
		List<Involucrado> involucrados = new ArrayList<Involucrado>();



		if (filtrosBusquedaExpediente.getAlias() != null
				&& filtrosBusquedaExpediente.getTipoBusqueda().equals(
						new Long(0))) {

			involucrados = involucradoDAO
					.consultarExpedientesByAlias(filtrosBusquedaExpediente
							.getAlias());
		} else if (filtrosBusquedaExpediente.getAlias() != null
				&& filtrosBusquedaExpediente.getTipoBusqueda().equals(
						new Long(1))) {

			involucrados = involucradoDAO.consultarExpedientesByAliasLike("%"
					+ filtrosBusquedaExpediente.getAlias() + "%");
		} else {
			String nombre = "%";
			String apellidos = "%";
			String apellidoMat = "%";

			if (filtrosBusquedaExpediente.getNombre() != null
					&& filtrosBusquedaExpediente.getNombre().length() > 0)
				nombre = nombre + filtrosBusquedaExpediente.getNombre();
			if (filtrosBusquedaExpediente.getApellidos() != null
					&& filtrosBusquedaExpediente.getApellidos().length() > 0)
				apellidos = apellidos
						+ filtrosBusquedaExpediente.getApellidos();
			if (!filtrosBusquedaExpediente.getApellidoMat().isEmpty())
				apellidoMat = apellidoMat + filtrosBusquedaExpediente.getApellidoMat();

			nombre = nombre + "%";
			apellidos = apellidos + "%";
			apellidoMat = apellidoMat + "%";
			//Se coloca el siguiente codigo para que se pueda pasar un DTO y los parametros solos
			//esto es para poder pasar mas parametros de filtrado como el discriminante y el usuario
				filtrosBusquedaExpediente.setNombre(nombre);
				filtrosBusquedaExpediente.setApellidos(apellidos);
				filtrosBusquedaExpediente.setApellidoMat(apellidoMat);


			involucrados = involucradoDAO.consultarExpedientesByNombre(filtrosBusquedaExpediente);
		}

		if (usuarioFirmado != null
				&& usuarioFirmado.getInstitucion() != null
				&& usuarioFirmado.getInstitucion().getConfInstitucionId() != null
				&& usuarioFirmado.getInstitucion().getConfInstitucionId().equals(Instituciones.PJ.getValorId())) {

			for (Involucrado involucrado : involucrados) {

				if (usuarioFirmado.getFuncionario()!= null
						&& usuarioFirmado.getFuncionario().getDiscriminante() != null
						&& usuarioFirmado.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null
						&& involucrado.getExpediente() != null
						&& involucrado.getExpediente().getDiscriminante() != null
						&& involucrado.getExpediente().getDiscriminante().getCatDiscriminanteId() != null
						&& usuarioFirmado
								.getFuncionario()
								.getDiscriminante()
								.getCatDiscriminanteId()
								.equals(involucrado.getExpediente()
										.getDiscriminante()
										.getCatDiscriminanteId())) {

					involucradosDTO.add(InvolucradoTransformer
							.transformarInvolucradoBasico(involucrado));
				}
			}

		} else {
			for (Involucrado involucrado : involucrados) {
				involucradosDTO.add(InvolucradoTransformer
						.transformarInvolucradoBasico(involucrado));
			}
		}

		for (InvolucradoDTO involucradoDTO : involucradosDTO) {
			List<NombreDemografico> nombres = nombreDemograficoDAO
					.consutarNombresByInvolucrado(involucradoDTO
							.getElementoId());
			involucradoDTO
					.setNombresDemograficoDTO(NombreDemograficoTransformer
							.transformarNombreDemograficoBasico(nombres));
		}

		return involucradosDTO;
	}

	@Override
	public List<ExpedienteDTO> consultarExpedienteActividadAreaAnio(
			FiltroExpedienteDTO filtroExpedienteDTO)
			throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEEDIENTES CORRESPONDIENTES A UNA ACTIVIDAD, AREA Y A&Ntilde;O ****/");
		if (filtroExpedienteDTO.getAnio() == null
				|| filtroExpedienteDTO.getIdArea() == null
				|| filtroExpedienteDTO.getIdActividad() == null)
			logger.error(CodigoError.PARAMETROS_INSUFICIENTES);

		if (filtroExpedienteDTO.getUsuario() == null
				|| filtroExpedienteDTO.getUsuario().getFuncionario() == null
				|| filtroExpedienteDTO.getUsuario().getFuncionario()
						.getJerarquiaOrganizacional() == null
				|| filtroExpedienteDTO.getUsuario().getFuncionario()
						.getJerarquiaOrganizacional()
						.getJerarquiaOrganizacionalId() == null
				|| filtroExpedienteDTO.getUsuario().getFuncionario()
						.getClaveFuncionario() != null
				|| filtroExpedienteDTO.getUsuario().getFuncionario()
						.getClaveFuncionario() < 0) {
			logger.error(CodigoError.PARAMETROS_INSUFICIENTES);
		}


		List<NumeroExpediente> expsRespuesta = new ArrayList<NumeroExpediente>();

		//Si es de alguna area de uavd no debe filtrar por catDiscriminante pero SI por clave funcionario
		if (filtroExpedienteDTO.getUsuario().getRolACtivo()
				.getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId()
				.equals(Areas.ATENCION_JURIDICA.parseLong()) || filtroExpedienteDTO.getUsuario().getRolACtivo()
				.getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId()
				.equals(Areas.ATENCION_PSICOLOGICA.parseLong())
				|| filtroExpedienteDTO.getUsuario().getRolACtivo()
				.getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId()
				.equals(Areas.ATENCION_VICTIMAS.parseLong()) ) {

			filtroExpedienteDTO.setIdDiscriminante(null);
			filtroExpedienteDTO.setIdFuncionario(filtroExpedienteDTO.getUsuario().getFuncionario().getClaveFuncionario());
			expsRespuesta = expedienteDAO.consultarExpedientesActividadAreaAnio(filtroExpedienteDTO);
		}
		else{

			//Si no es del area de uavd debe aplicar el filtro por catDiscriminante pero NO por claveFuncionario
	        long discriminanteId = 0L;

			if (filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante() != null &&
				filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {
				discriminanteId = filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getCatDiscriminanteId();
			}

			Long claveFuncionario = filtroExpedienteDTO.getUsuario().getFuncionario().getClaveFuncionario();

				RolDTO rol = filtroExpedienteDTO.getUsuario().getRolACtivo().getRol();
				Roles rolAsociado = Roles.getByValor(rol.getRolId());
				//ESte switch se ejecuta en cascada, es decir, para todos los case se ejecuta la ultima instrucción
				switch (rolAsociado) {
					case COORDINADORAMP:
					case COORDINADORJAR:
					case COORDINADORDEF:
					case COORDINADORPER:
					case COORDINADORVIS:
					case COORDPERFIS: {
						logger.info(" **** Es Coordinador  **** " + rolAsociado);
						claveFuncionario = null;
					}
				}

				filtroExpedienteDTO.setIdDiscriminante(discriminanteId);
				filtroExpedienteDTO.setIdFuncionario(claveFuncionario);
				expsRespuesta = expedienteDAO.consultarExpedientesActividadAreaAnio(filtroExpedienteDTO);
		}

		if(filtroExpedienteDTO.getEstatusMenuCoorJAr()!=null){
			List<NumeroExpediente> listaAlterna=expsRespuesta;
			expsRespuesta=new ArrayList<NumeroExpediente>();
			for (NumeroExpediente numeroExpediente : listaAlterna) {
				boolean op=true;
				if(filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.PORASIGNAR.getValorId())){
					Expediente expediente=expedienteDAO.buscarUltimoNumeroPorExpedienteIdAreaId(numeroExpediente.getExpediente().getExpedienteId(), Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
					if(expediente!=null){
						op=false;
					}
				}else if(filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.ASIGNADOS.getValorId())){
					op=false;
					Expediente expediente=expedienteDAO.buscarUltimoNumeroPorExpedienteIdAreaId(numeroExpediente.getExpediente().getExpedienteId(), Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
					if(expediente!=null){
						List<Expediente>listExpedientes=expedienteDAO.consultarExpedientesPorActividadActualyExpedienteID(Actividades.ATENDER_CANALIZACION_JAR.getValorId(), numeroExpediente.getExpediente().getExpedienteId());
						if(listExpedientes!=null && !listExpedientes.isEmpty()){
							op=true;
						}

					}
				}else if(filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.PROPIOS.getValorId())){
					op=false;
					Expediente expediente=expedienteDAO.buscarUltimoNumeroPorExpedienteIdAreaId(numeroExpediente.getExpediente().getExpedienteId(), Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
					if(expediente!=null){
						op=true;
						List<Expediente>listExpedientes=expedienteDAO.consultarExpedientesPorActividadActualyExpedienteID(Actividades.ATENDER_CANALIZACION_JAR.getValorId(), numeroExpediente.getExpediente().getExpedienteId());
						if(listExpedientes!=null && !listExpedientes.isEmpty()){
							op=false;
						}

					}
				}
				if(op){
					expsRespuesta.add(numeroExpediente);
				}
			}
		}

		List<ExpedienteDTO> expsDTO = new ArrayList<ExpedienteDTO>();
		if(expsRespuesta.isEmpty()){
			return  new ArrayList<ExpedienteDTO>(expsDTO);
		}
		if(filtroExpedienteDTO.getEstatusMenuCoorJAr()!=null &&
				(filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.ASIGNADOS.getValorId())||
						filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.PORASIGNAR.getValorId())||
						filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.PROPIOS.getValorId())
						)){
			expsRespuesta = paginacionManualJAR(expsRespuesta);
		}
		for (NumeroExpediente numeroExpediente : expsRespuesta) {

			ExpedienteDTO expedienteDTO = ExpedienteTransformer
					.transformarExpedienteBasico(numeroExpediente);

			if (numeroExpediente.getExpediente().getActividads() != null) {
				for (Actividad actividad : numeroExpediente.getExpediente()
						.getActividads()) {
					if (actividad.getTipoActividad().getValorId()
							.equals(filtroExpedienteDTO.getIdActividad()))
						expedienteDTO.setActividadActual(ActividadTransformer
								.transformarActividad(actividad));
				}
			}

			List<Involucrado> involucrados = involucradoDAO
					.obtenerInvByIdExpAndCalidad(numeroExpediente
							.getExpediente().getExpedienteId(),
							Calidades.DENUNCIANTE.getValorId(), null);
			for (Involucrado involucrado : involucrados) {
				expedienteDTO.addInvolucradoDTO(InvolucradoTransformer
						.transformarInvolucradoBasico(involucrado));
			}

			List<Delito> listDelitos = delitoDAO
					.obtenerDelitoPorExpediente(numeroExpediente
							.getExpediente().getExpedienteId());

			for (Delito delito : listDelitos) {
				if (delito.getEsPrincipal() == true) {
					DelitoDTO delitoDTO = DelitoTransfromer
							.transformarDelito(delito);
					expedienteDTO.setDelitoPrincipal(delitoDTO);
				}
			}

			List<NumeroExpediente> numHijos = numeroExpedienteDAO.consultarnumExpedienteHijos(numeroExpediente.getNumeroExpedienteId());

			if (numHijos!=null) {
				List<ExpedienteDTO> numHijosDTO = new ArrayList<ExpedienteDTO>();
				for (NumeroExpediente numeroExpedienteHijo : numHijos) {
					numHijosDTO.add(ExpedienteTransformer.transformarExpedienteBasico(numeroExpedienteHijo));
				}
				expedienteDTO.setNumExpHijos(numHijosDTO);
			}

			//Si se trata de expedientes de procuraduria
			ConfInstitucion confInstitucion = this.expedienteDAO.consultarInsitucionActual();
        	if(confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId()) ){
        		if(numeroExpediente.getExpediente() != null && numeroExpediente.getExpediente().getEstatus() != null)
      			expedienteDTO.setEstatusExpedientePadre(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(),numeroExpediente.getExpediente().getEstatus().getValor()));
        	}

        	// ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de
            // expedienteDTO.getEstatus();
            // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
            // expedienteDTO.getEstatusNumeroExpediente();

            if(numeroExpediente!=null && numeroExpediente.getExpediente()!=null &&
               numeroExpediente.getExpediente().getEstatus()!=null){
            	expedienteDTO.setEstatus(new ValorDTO(
            			numeroExpediente.getExpediente().getEstatus().getValorId(),
            			numeroExpediente.getExpediente().getEstatus().getValor()));
            }

        	if(numeroExpediente!=null &&
        	   numeroExpediente.getEstatus()!=null &&
        	   numeroExpediente.getEstatus().getValor()!=null){
        		expedienteDTO.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus().getValorId(),
        				numeroExpediente.getEstatus().getValor()));
        	}

        	// ------------------------------------------------------------------------------------------------

			expsDTO.add(expedienteDTO);
		} // for

		return  new ArrayList<ExpedienteDTO>(expsDTO);
	}

	private List<NumeroExpediente> paginacionManualJAR(
			List<NumeroExpediente> expsRespuesta) {
		final PaginacionDTO pag=PaginacionThreadHolder.get();
		if((pag.getPage()*pag.getRows())-pag.getRows()>expsRespuesta.size()){
			pag.setPage(1);
		}
		int inicio=0;
		if(pag.getPage()<=1){
			inicio=0;
		}else{
			inicio=((pag.getPage()-1)*pag.getRows());
		}
		int indiceFinal=inicio+pag.getRows();
		List<NumeroExpediente> listaAlterna=expsRespuesta;
		expsRespuesta=new ArrayList<NumeroExpediente>();
		if(indiceFinal>=listaAlterna.size()){
			for (int i = inicio; i < listaAlterna.size(); i++) {
				expsRespuesta.add(listaAlterna.get(i));
			}
		}else{
			for (int i = inicio; i < indiceFinal; i++) {
				expsRespuesta.add(listaAlterna.get(i));
			}
		}


		pag.setTotalRegistros((long)listaAlterna.size());
		pag.setPaginacionHecha(true);
		PaginacionThreadHolder.set(pag);
		return expsRespuesta;
	}

	@Override
	public List<ExpedienteDTO> consultarCanalizadosCoordinadorAmpGeneral(
			FiltroExpedienteDTO filtroExpedienteDTO)
			throws NSJPNegocioException {

		if (filtroExpedienteDTO==null || filtroExpedienteDTO.getIdArea()==null || filtroExpedienteDTO.getIdActividad()==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<NumeroExpediente> expsRespuesta = new ArrayList<NumeroExpediente>();

		expsRespuesta = expedienteDAO
			.consultarExpedientesCanalizados(filtroExpedienteDTO);

		List<ExpedienteDTO> expsDTO = new ArrayList<ExpedienteDTO>();
		for (NumeroExpediente numeroExpediente : expsRespuesta) {

			ExpedienteDTO expedienteDTO = ExpedienteTransformer
					.transformarExpedienteBasico(numeroExpediente);

			if (numeroExpediente.getExpediente().getActividads() != null) {
				for (Actividad actividad : numeroExpediente.getExpediente()
						.getActividads()) {
					if (actividad.getTipoActividad().getValorId()
							.equals(filtroExpedienteDTO.getIdActividad()))
						expedienteDTO.setActividadActual(ActividadTransformer
								.transformarActividad(actividad));
				}
			}

			List<Involucrado> involucrados = involucradoDAO
					.obtenerInvByIdExpAndCalidad(numeroExpediente
							.getExpediente().getExpedienteId(),
							Calidades.DENUNCIANTE.getValorId(), null);
			for (Involucrado involucrado : involucrados) {
				expedienteDTO.addInvolucradoDTO(InvolucradoTransformer
						.transformarInvolucradoBasico(involucrado));
			}

			List<Delito> listDelitos = delitoDAO
					.obtenerDelitoPorExpediente(numeroExpediente
							.getExpediente().getExpedienteId());

			for (Delito delito : listDelitos) {
				if (delito.getEsPrincipal() == true) {
					DelitoDTO delitoDTO = DelitoTransfromer
							.transformarDelito(delito);
					expedienteDTO.setDelitoPrincipal(delitoDTO);
				}
			}

			List<NumeroExpediente> numHijos = numeroExpedienteDAO.consultarnumExpedienteHijos(numeroExpediente.getNumeroExpedienteId());

			if (numHijos!=null) {
				List<ExpedienteDTO> numHijosDTO = new ArrayList<ExpedienteDTO>();
				for (NumeroExpediente numeroExpedienteHijo : numHijos) {
					numHijosDTO.add(ExpedienteTransformer.transformarExpedienteBasico(numeroExpedienteHijo));
				}
				expedienteDTO.setNumExpHijos(numHijosDTO);
			}

			//Si se trata de expedientes de procuraduria
			ConfInstitucion confInstitucion = this.expedienteDAO.consultarInsitucionActual();
        	if(confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId()) ){
        		if(numeroExpediente.getExpediente() != null && numeroExpediente.getExpediente().getEstatus() != null)
      			expedienteDTO.setEstatusExpedientePadre(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(),numeroExpediente.getExpediente().getEstatus().getValor()));
        	}

       	    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de
            // expedienteDTO.getEstatus();
            // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
            // expedienteDTO.getEstatusNumeroExpediente();

       	    if(numeroExpediente!=null && numeroExpediente.getExpediente()!=null &&
       	       numeroExpediente.getExpediente().getEstatus()!=null){
                   		expedienteDTO.setEstatus(new ValorDTO(numeroExpediente.getExpediente().getEstatus()
                   				.getValorId(), numeroExpediente.getExpediente().getEstatus().getValor()));
            }

    		if(numeroExpediente!=null &&
    		   numeroExpediente.getEstatus()!= null && numeroExpediente.getEstatus().getValor()!= null){
    			expedienteDTO.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus().getValorId(),numeroExpediente.getEstatus().getValor()));
			}

    		// ------------------------------------------------------------------------------------------------

			expsDTO.add(expedienteDTO);
		} // for

		return  new ArrayList<ExpedienteDTO>(expsDTO);
	}

	/**
	 * Metodo que permite consultar los datos generales de un expediente
	 *
	 */
	@Override
	public DatosGeneralesExpedienteDTO obtenerDatosGeneralesExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		logger.info("/**** Consultar Datos Generales de Expediente por ID ****/");

		NumeroExpediente numeroExpediente = new NumeroExpediente();
		DatosGeneralesExpedienteDTO expedienteRetorno = new DatosGeneralesExpedienteDTO();

		List<Involucrado> involucrados = new ArrayList<Involucrado>();
		List<Objeto> objetosExpediente = new ArrayList<Objeto>();

		Expediente expediente = new Expediente();
		if (expedienteDTO.getNumeroExpedienteId() != null) {
			numeroExpediente = numeroExpedienteDAO.read(expedienteDTO
					.getNumeroExpedienteId());
			expediente = numeroExpediente.getExpediente();

			expedienteRetorno.setFechaApertura(DateUtils.formatear(expediente.getFechaCreacion()));
			expedienteRetorno.setExpedienteId(expediente.getExpedienteId());

			//Permite calcular el numero de documentos del expediente
			List<DocumentoDTO> listaDocumentoDTOs=consultarDocumentoService.consultarDocumentosExpediente(expedienteDTO);
			if(listaDocumentoDTOs != null){
				expedienteRetorno.setTotalDocumentosDelExpediente(listaDocumentoDTOs.size());
			} else {
				expedienteRetorno.setTotalDocumentosDelExpediente(0);
			}

			//SE OBTIENEN LOS INVOLUCRADOS DEL EXPEDIENTE
			involucrados = involucradoDAO.consultarInvolucradosByExpediente(expediente.getExpedienteId());
			//SE OBTIENEN LOS OBJETOS DEL EXPEDIENTE
			objetosExpediente = objetoDAO.consultarObjetosByExpediente(expediente.getExpedienteId());

			expedienteRetorno = DatosGeneralesExpedienteTransformer
					.transformaDatosGeneralesExpedienteDTO(objetosExpediente,
							expedienteRetorno);

			//Se obtiene el estatus del expediente
			if (numeroExpediente.getEstatus() != null){
				expedienteRetorno.setEstatusNumeroExpediente(numeroExpediente
						.getEstatus().getValor());
			}
			else{
				expedienteRetorno.setEstatusNumeroExpediente("");
			}

			//Si se trata de expedientes de procuraduria
			ConfInstitucion confInstitucion = this.expedienteDAO.consultarInsitucionActual();
        	if(confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId()) ){
        		if(numeroExpediente.getExpediente() != null && numeroExpediente.getExpediente().getEstatus() != null){
        			expedienteDTO.setEstatusExpedientePadre(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(),numeroExpediente.getExpediente().getEstatus().getValor()));
        			expedienteRetorno.setEstatusNumeroExpediente(numeroExpediente.getExpediente().getEstatus().getValor());
        		}
        	}


			//Se obtiene el orgien del documento
			if (expediente.getOrigen() != null){
				expedienteRetorno.setOrigenExpediente(expediente.getOrigen().getValor());
			} else {
				expedienteRetorno.setOrigenExpediente("");
			}

			//Se obtiene el orgien del Numero de expediente
			//Atenci&oacute;n Temprana Penal / Unidad de Investigaci&oacute;n
			if (numeroExpediente.getJerarquiaOrganizacional() != null){
				expedienteRetorno.setOrigenNumeroExpediente("Origen: "  + consultarDetalleExpedienteService.
								consultarOrigendeExpediente(new ExpedienteDTO(expediente.getExpedienteId())).getNombre());
			} else {
				expedienteRetorno.setOrigenNumeroExpediente("");
			}
		}

		// Responsable del expediente
		if(numeroExpediente!=null && numeroExpediente.getFuncionario()!=null){
			String responsableTramite = "";
			if(numeroExpediente.getFuncionario().getNombreFuncionario()!=null){
				responsableTramite = numeroExpediente.getFuncionario().getNombreFuncionario();
			}
			if(numeroExpediente.getFuncionario().getApellidoPaternoFuncionario()!=null){
				if(responsableTramite !=""){
					responsableTramite += " " + numeroExpediente.getFuncionario().getApellidoPaternoFuncionario();
				}
				else{
					responsableTramite = numeroExpediente.getFuncionario().getApellidoPaternoFuncionario();
				}
			}
			if(numeroExpediente.getFuncionario().getApellidoMaternoFuncionario()!=null){
				if(responsableTramite!=""){
					responsableTramite += " " + numeroExpediente.getFuncionario().getApellidoMaternoFuncionario();
				}
				else{
					responsableTramite = numeroExpediente.getFuncionario().getApellidoMaternoFuncionario();
				}
			}
			expedienteRetorno.setResponsableTramite(responsableTramite);
		}
		else{
			expedienteRetorno.setResponsableTramite("");
		}

	    // Estatus actuaci&oacute;n
		if(numeroExpediente!=null && numeroExpediente.getEstatus()!=null
				&& numeroExpediente.getEstatus().getValor()!=null){
			expedienteRetorno.setEstatusActuacion(numeroExpediente.getEstatus().getValor());
		}
		else{
			expedienteRetorno.setEstatusActuacion("");
		}

		//Se agrega el numero de expediente alterno al dto regresado
		expedienteRetorno.setNumeroExpedienteAlterno(numeroExpediente.getNumExpAlterno());

		//Permite saber el Total de DENUNCIANTE(s)
		List<Involucrado> denunciantes = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
				Calidades.DENUNCIANTE.getValorId(), null);
		List<Involucrado> denunciantesOrganizacion = involucradoDAO.obtenerInvByIdExpAndCalidad(
				expediente.getExpedienteId(),
				Calidades.DENUNCIANTE_ORGANIZACION.getValorId(), null);
		expedienteRetorno.setTotalDenunciantes(denunciantes.size() + denunciantesOrganizacion.size());
		//Permite llenar el HashMap de Involucrados
		lHmInvolucrados.put(Calidades.DENUNCIANTE.getValorId(), obtenerDetalleDeInvolucrado(denunciantes));
        lHmInvolucrados.put(Calidades.DENUNCIANTE_ORGANIZACION.getValorId(), obtenerDetalleDeInvolucrado(denunciantesOrganizacion));

		//Permite saber el Total de VICTIMA(s)
        List<Involucrado> victimas = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
        		Calidades.VICTIMA_PERSONA.getValorId(), null);
		expedienteRetorno.setTotalVictimas(victimas.size());
		List <String> listaDeInvolucrados = obtenerDetalleDeInvolucrado(victimas);

		//Permite validar si el denunciante es la victima
		Involucrado loDenunciante = null;
		if(denunciantes  != null && denunciantes .size()>0){
			loDenunciante = denunciantes .get(0);
		}

		if(loDenunciante != null && loDenunciante.getCondicion()== 1){
			expedienteRetorno.setTotalVictimas(victimas.size() + 1);
			logger.info("El denunciante es victima");
			logger.info("El id del denunciante es " + loDenunciante.getElementoId());
			listaDeInvolucrados.addAll(obtenerDetalleDeInvolucrado(denunciantes));
		}else{
			expedienteRetorno.setTotalVictimas(victimas.size()) ;
			logger.info("El denunciante NO es victima");
		}
		lHmInvolucrados.put(Calidades.VICTIMA_PERSONA.getValorId(), listaDeInvolucrados);

		//Permite saber el Total de PROBABLES RESPONSABLE(s)
        List<Involucrado> probableResps = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
        		Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(), null);
        List<Involucrado> probableRespsOrganizacion = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
        		Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId(), null);
		int totalPR = probableResps.size() + probableRespsOrganizacion.size();
		expedienteRetorno.setTotalProbablesResposables(totalPR);
		lHmInvolucrados.put(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(), obtenerDetalleDeInvolucrado(probableResps));
		lHmInvolucrados.put(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId(), obtenerDetalleDeInvolucrado(probableRespsOrganizacion));

		//Permite saber el Total de TESTIGO(s)
		List<Involucrado> testigos = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
        		Calidades.TESTIGO.getValorId(), null);
		expedienteRetorno.setTotalTestigos(testigos.size());
		lHmInvolucrados.put(Calidades.TESTIGO.getValorId(), obtenerDetalleDeInvolucrado(testigos));

		//Permite saber el Total de TRADUCTOR(s)
		List<Involucrado> traductores = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
        		Calidades.TRADUCTOR.getValorId(), null);
		expedienteRetorno.setTotalTraductores(traductores.size());
		lHmInvolucrados.put(Calidades.TRADUCTOR.getValorId(), obtenerDetalleDeInvolucrado(traductores));

		//Permite saber el Total de QUIEN DETUVO(s)
		List<Involucrado> quienesDetuvieron = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
        		Calidades.QUIEN_DETUVO.getValorId(), null);
		expedienteRetorno.setQuienDetuvo(String.valueOf(quienesDetuvieron.size()));
		lHmInvolucrados.put(Calidades.QUIEN_DETUVO.getValorId(), obtenerDetalleDeInvolucrado(quienesDetuvieron));
		expedienteRetorno.setInvolucrados(lHmInvolucrados);

		//Permite saber si una denuncia es anonima o no
		if(denunciantes.size() > 0 ){
			Involucrado loDenun= denunciantes.get(0);
			expedienteRetorno.setEsDesconocido(loDenun.getDesconocido());
		}else{
			expedienteRetorno.setEsDesconocido("");
		}

		if (involucrados.size() > 0){
			expedienteRetorno.setTotalProbablesResposables(totalPR);
		} else {
			expedienteRetorno.setTotalProbablesResposables(0);
		}

		//Permite obtener el detalle de los objetos
		 //Se ingresan los objetos al hashmap de los objetos
		lHmObjetos.put(Objetos.VEHICULO.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.VEHICULO));
        lHmObjetos.put(Objetos.EQUIPO_DE_COMPUTO.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.EQUIPO_DE_COMPUTO));
        lHmObjetos.put(Objetos.EQUIPO_TELEFONICO.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.EQUIPO_TELEFONICO));
        lHmObjetos.put(Objetos.ARMA.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.ARMA));
        lHmObjetos.put(Objetos.EXPLOSIVO.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.EXPLOSIVO));
        lHmObjetos.put(Objetos.SUSTANCIA.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.SUSTANCIA));
        lHmObjetos.put(Objetos.ANIMAL.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.ANIMAL));
        lHmObjetos.put(Objetos.AERONAVE.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.AERONAVE));
        lHmObjetos.put(Objetos.EMBARCACION.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.EMBARCACION));
        lHmObjetos.put(Objetos.INMUEBLE.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.INMUEBLE));
        lHmObjetos.put(Objetos.NUMERARIO.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.NUMERARIO));
        lHmObjetos.put(Objetos.VEGETAL.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.VEGETAL));
        lHmObjetos.put(Objetos.DOCUMENTO_OFICIAL.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.DOCUMENTO_OFICIAL));
        lHmObjetos.put(Objetos.JOYA.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.JOYA));
        lHmObjetos.put(Objetos.OBRA_DE_ARTE.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.OBRA_DE_ARTE));
        lHmObjetos.put(Objetos.OTRO.getValorId(),obtenerDetalleDeObjetosPorTipo(objetosExpediente,Objetos.OTRO));
        expedienteRetorno.setObjetos(lHmObjetos);

		return expedienteRetorno;
	}

	@Override
	public ExpedienteDTO obtenerExpedientePorNumeroExpediente(
			String numeroExpediente) throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** WRAPPER PARA OBTENER EL NUMERO EXPEDIENTE ID****/");

		ExpedienteDTO resp = new ExpedienteDTO();

		Long expId = this.expedienteDAO
		.consultarExpedientePorNumeroExpediente(numeroExpediente);
		logger.debug("Numero Expediente ID obtenido " + expId);

		if (expId == null)
			logger.error(CodigoError.FORMATO);
		else {
			ExpedienteDTO ex = new ExpedienteDTO();
			ex.setNumeroExpedienteId(expId);
			ex.setMedidasCautelaresSolicitadas(true);
			ex.setInvolucradosSolicitados(true);
			resp = this.obtenerExpediente(ex);
		}
		return resp;
	}

	@Override
	public ExpedienteDTO obtenerExpedientePorNumeroExpedienteYNumeroCaso(
			String numeroExpediente, String numCaso) throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** WRAPPER PARA OBTENER EL NUMERO EXPEDIENTE ID y numero de caso****/");

		ExpedienteDTO resp = new ExpedienteDTO();

		Long expId = this.expedienteDAO
				.obtenerIdNumExpedientePorNumeroExpedienteYNumeroCaso(numeroExpediente, numCaso);
		logger.debug("numeroExpediente: " + expId);
		logger.debug("numCaso:" + numCaso);

		if (expId == null)
			logger.error(CodigoError.FORMATO);
		else {
			ExpedienteDTO ex = new ExpedienteDTO();
			ex.setNumeroExpedienteId(expId);
			ex.setMedidasCautelaresSolicitadas(true);
			ex.setInvolucradosSolicitados(true);
			resp = this.obtenerExpediente(ex);
		}
		return resp;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService#
	 * consultarExpedientesConEventosHistorico
	 * (mx.gob.segob.nsjp.dto.usuario.UsuarioDTO)
	 */
	@Override
	public List<ExpedienteDTO> consultarExpedientesConEventosHistorico(
			Long casoId, UsuarioDTO usuario) throws NSJPNegocioException {
		List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
		List<NumeroExpediente> numsExpediente = numeroExpedienteDAO
				.consultarExpedientesConEventosHistorico(casoId,
						usuario.getIdUsuario());
		for (NumeroExpediente numExp : numsExpediente) {
			expedientes.add(ExpedienteTransformer
					.transformarExpedienteBasico(numExp));
		}
		return expedientes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService#
	 * obtenerNumeroExpedienteByNumExp
	 * (mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public ExpedienteDTO obtenerNumeroExpedienteByNumExp(
			ExpedienteDTO expedienteDTO,UsuarioDTO usuario) throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER EL NUMERO EXPEDIENTE POR numeroExpediente ****/");

		if (expedienteDTO.getNumeroExpediente() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		/*
		* Usado para obtener el discriminante Id
		*/
		  long discriminanteId = 0L;


		if (usuario != null
				&& usuario.getFuncionario() != null
				&& usuario.getFuncionario().getDiscriminante() != null
				&& usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

			discriminanteId = usuario.getFuncionario().getDiscriminante()
					.getCatDiscriminanteId();
		}


		NumeroExpediente numExpediente = numeroExpedienteDAO
				.obtenerNumeroExpediente(expedienteDTO.getNumeroExpediente(),discriminanteId);

		ExpedienteDTO expRespuestaDTO = ExpedienteTransformer
				.transformarExpedienteBasico(numExpediente);

		return expRespuestaDTO;
	}

	@SuppressWarnings("static-access")
	@Override
	public List<ExpedienteDTO> consultarNumeroExpedienteHistorico(UsuarioDTO usuario)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR LAS CAUSAS HISTORICO ****/");

		Parametro param = parametroDAO.obtenerPorClave(Parametros.LIMITE_HISTORICO_CONSULTAS);
		Long dias = new Long(param.getValor());

		Calendar calTempDec = Calendar.getInstance();
		calTempDec.setTime(new Date());
		calTempDec.add(calTempDec.DATE, -dias.intValue());

		/*
		* Usado para obtener el discriminante Id
		*/
		long discriminanteId = 0L;

		if (usuario != null
				&& usuario.getFuncionario() != null
				&& usuario.getFuncionario().getDiscriminante() != null
				&& usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

			discriminanteId = usuario.getFuncionario().getDiscriminante()
					.getCatDiscriminanteId();
		}

		List<NumeroExpediente> respuesta = numeroExpedienteDAO
				.consultarCausasHistorico(calTempDec,discriminanteId);
		List<ExpedienteDTO> listRespuesta = new ArrayList<ExpedienteDTO>();
		for (NumeroExpediente numeroExpediente : respuesta) {
			List<NumeroExpediente> listCarpEjec = numeroExpedienteDAO
					.consultarCarpetasEjecucionPorCausa(numeroExpediente
							.getNumeroExpedienteId());
			ExpedienteDTO expRespuesta = ExpedienteTransformer
					.transformarExpedienteBasico(numeroExpediente);

			List<ExpedienteDTO> listCarpEjecRespuesta = new ArrayList<ExpedienteDTO>();
			for (NumeroExpediente carpetaEjec : listCarpEjec) {
				listCarpEjecRespuesta.add(ExpedienteTransformer
						.transformarExpedienteBasico(carpetaEjec));
			}
			expRespuesta.setNumExpHijos(listCarpEjecRespuesta);
			listRespuesta.add(expRespuesta);
		}

		return listRespuesta;
	}

	@Override
	public List<ExpedienteDTO> consultarNumeroExpedienteByEstatus(
			TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente)
			throws NSJPNegocioException {

		List<NumeroExpediente> listNumExp = numeroExpedienteDAO
				.consultarNumeroExpedienteByEstatus(tipoExpediente,
						estatusExpediente);

		List<ExpedienteDTO> listRespuesta = new ArrayList<ExpedienteDTO>();
		for (NumeroExpediente numeroExpediente : listNumExp) {
			listRespuesta.add(ExpedienteTransformer
					.transformarExpedienteBasico(numeroExpediente));
		}

		return listRespuesta;
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientesUsuarioArea(
			UsuarioDTO usuarioDTO) throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR LOS EXPEDIENTES ASOCIADOS A UN USUARIO ****/");

		if (usuarioDTO.getAreaActual() == null
				|| usuarioDTO.getAreaActual().getAreaId() == null
				|| usuarioDTO.getFuncionario()== null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		/*
         * Usado para setear la agencia y consultar turnos para agencias de PGJ
         */
        long agenciaId = 0L;

        ConfInstitucion confInstitucionPG = expedienteDAO.consultarInsitucionActual();
        if(confInstitucionPG.getConfInstitucionId().equals(Instituciones.PGJ.getValorId()) ){
			if (usuarioDTO != null
					&& usuarioDTO.getFuncionario() != null
					&& usuarioDTO.getFuncionario().getDiscriminante() != null
					&& usuarioDTO.getFuncionario().getDiscriminante()
							.getCatDiscriminanteId() != null) {

				agenciaId = usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId();
			}
        }

		List<NumeroExpediente> numsExpedientes = numeroExpedienteDAO
				.consultarByUsuarioArea(usuarioDTO.getFuncionario().getClaveFuncionario(), usuarioDTO
						.getAreaActual().getAreaId(), EstatusExpediente.ABIERTO.getValorId(),agenciaId,null);

		List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
		for (NumeroExpediente numeroExpediente : numsExpedientes) {
            Expediente expediente = numeroExpediente.getExpediente();
            //los delitos se obitienen en el transformer
            ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformaExpediente(expediente);
            expedienteDTO.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
            expedienteDTO.setFechaApertura(numeroExpediente.getFechaApertura());
            expedienteDTO.setNumeroExpediente(numeroExpediente.getNumeroExpediente());

    	    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de
            // expedienteDTO.getEstatus();
            // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
            // expedienteDTO.getEstatusNumeroExpediente();

            if (numeroExpediente.getExpediente()!=null &&
            	numeroExpediente.getExpediente().getEstatus()!=null) {
                expedienteDTO.setEstatus(new ValorDTO( numeroExpediente.getExpediente().getEstatus().getValorId()));
            }

            if(numeroExpediente.getEstatus()!=null){
            	expedienteDTO.setEstatusNumeroExpediente(ValorTransformer.transformar(numeroExpediente.getEstatus()));
            }

            // ------------------------------------------------------------------------------------------------

            if (numeroExpediente.getEtapa()!=null) {
                expedienteDTO.setEtapa(new ValorDTO( numeroExpediente.getEtapa().getValorId()));
            }
            //Setear delito
			if (expedienteDTO.getDelitoPrincipal()!= null)
				expedienteDTO.setDelitoPrincipal(DelitoTransfromer.transformarDelito(delitoDAO.read(expedienteDTO.getDelitoPrincipal().getDelitoId())));

			expedientes.add(expedienteDTO);
		}
		return expedientes;
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientePorAreaEstatusRemitente(
			UsuarioDTO usuarioDTO, AreaDTO area, Long estatusExpediente)
			throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR LOS EXPEDIENTES DE UN AREA PARA UN USARIO Y FILTRADO ****/");

		/* Verificar Par&aacute;metros */
		if (usuarioDTO == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if (usuarioDTO.getAreaActual() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		/*
         * Usado para setear la agencia y consultar turnos para agencias de PGJ
         */
        long agenciaId = 0L;

        ConfInstitucion confInstitucionPG = expedienteDAO.consultarInsitucionActual();
        if(confInstitucionPG.getConfInstitucionId().equals(Instituciones.PGJ.getValorId()) ){
			if (usuarioDTO != null
					&& usuarioDTO.getFuncionario() != null
					&& usuarioDTO.getFuncionario().getDiscriminante() != null
					&& usuarioDTO.getFuncionario().getDiscriminante()
							.getCatDiscriminanteId() != null) {

				agenciaId = usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId();
			}
        }

		List<ExpedienteDTO> expedientesDTO = new ArrayList<ExpedienteDTO>();
			//  if (area != null) {
			// Nota: List<Expediente> expedientes = expedienteDAO
			//       .buscarExpedientesRemitentes(usuarioDTO.getAreaActual()
			//  	 .getAreaId(), area.getAreaId());
			// La consulta no esta construido!!!! devolvía null!!!
			//			for (Expediente exp : expedientes) {
			//					expedientesDTO.add(ExpedienteTransformer.transformaExpediente(exp));
			//			}
			// }
		if (estatusExpediente != null) {
			logger.debug("/*** SE CONSULTAN DEL USUARIO POR ESTATUS ++++/");
			List<NumeroExpediente> numsExpedientes = numeroExpedienteDAO
					.consultarByUsuarioArea(usuarioDTO.getIdUsuario(),
							usuarioDTO.getAreaActual().getAreaId(), estatusExpediente,agenciaId,null);

			for (NumeroExpediente numeroExpediente : numsExpedientes) {
				ExpedienteDTO expeDTO = ExpedienteTransformer.transformarExpedienteDenunciante(numeroExpediente);
				List<Involucrado> ivols = involucradoDAO.consultarInvolucradosByExpediente(numeroExpediente.getExpediente().getExpedienteId());
				List<InvolucradoDTO> denunciantes=new ArrayList<InvolucradoDTO>();
				for (Involucrado inv : ivols) {
					Elemento elemento = eleDao.read(inv.getElementoId());
					if(elemento.getCalidad().getCalidadId().equals(Calidades.DENUNCIANTE.getValorId()))
						denunciantes.add(InvolucradoTransformer.transformarInvolucrado(inv));
				}
				expeDTO.setInvolucradosDTO(denunciantes);
				expedientesDTO.add(expeDTO);
			}
		}

		return expedientesDTO;
	}

	@Override
	public List<ExpedienteDTO> consultarHistoricoCausasExpediente() throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR LOS EXPEDIENTES DE TIPO CAUSA CON CARPETA DE INESTIGACION EN ESTATUS CERRADO ****/");

		Parametro parametro = parametroDAO.obtenerPorClave(Parametros.LIMITE_HISTORICO_CONSULTAS);
		Long dias = new Long(parametro.getValor());

		Calendar calTemp = Calendar.getInstance();
		calTemp.setTime(new Date());
		calTemp.add(Calendar.DATE, -dias.intValue());

		List<NumeroExpediente> numerosExpCausa = numeroExpedienteDAO.consultarHistoricoCausasExpediente(calTemp.getTime());

		List<ExpedienteDTO> numsExpDTORetorno = new ArrayList<ExpedienteDTO>();
		for (NumeroExpediente numeroExpediente : numerosExpCausa) {
			numsExpDTORetorno.add(ExpedienteTransformer.transformarExpedienteBasico(numeroExpediente));
		}
		return numsExpDTORetorno;
	}

	@Override
	public List<ExpedienteDTO> consultarCarpetasEjecucionPorCausa(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR LAS CARPETAS DE EJECUCION DE UNA CAUSA ****/");

		if (expedienteDTO.getNumeroExpedienteId() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		List<NumeroExpediente> numerosExpCarpeta = numeroExpedienteDAO.consultarCarpetasEjecucionPorCausa(expedienteDTO.getNumeroExpedienteId());

		List<ExpedienteDTO> numsExpDTORetorno = new ArrayList<ExpedienteDTO>();
		for (NumeroExpediente numeroExpediente : numerosExpCarpeta) {
			numsExpDTORetorno.add(ExpedienteTransformer.transformarExpedienteBasico(numeroExpediente));
		}
		return numsExpDTORetorno;
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService#consultarExpedientesPorFiltro(java.util.Date, java.util.Date, mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO, mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente, java.lang.Long)
	 */
	@Override
	public List<ExpedienteDTO> consultarExpedientesPorFiltro(Date fechaInicio,
			Date fechaFin, FuncionarioDTO usuario, TipoExpediente tipo,
			Long numeroExpedientePadreId)  throws NSJPNegocioException{

		Funcionario funcionario = FuncionarioTransformer.transformarFuncionario(usuario);

		List<NumeroExpediente> numeros =
			numeroExpedienteDAO.consultarNumeroExpedientePorFiltro(fechaInicio, fechaFin, funcionario, tipo, numeroExpedientePadreId);
		List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
		List<Involucrado> lista = new LinkedList<Involucrado>();
		ExpedienteDTO expediente = null;
		for(NumeroExpediente ne : numeros){
			expediente = ExpedienteTransformer.transformarExpedienteBasico(ne);
			lista = involucradoDAO.consultarInvolucradosByExpediente(ne.getExpediente().getExpedienteId());
			for(Involucrado inv : lista){
				expediente.addInvolucradoDTO(InvolucradoTransformer.transformarInvolucradoBasico(inv));
			}
			expedientes.add(expediente);
		}
		return expedientes;
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService#consultarNumeroExpedientePorNumeroCaso(java.lang.String)
	 */
	@Override
	public List<ExpedienteDTO> consultarNumeroExpedientePorNumeroCaso(
			String caso) {
		List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
		List<NumeroExpediente> numeros = numeroExpedienteDAO.consultarNumeroExpedientePorNumeroCaso(caso);
		for(NumeroExpediente ne:numeros){

			expedientes.add(ExpedienteTransformer.transformarExpedienteBasico(ne));

		}
		return expedientes;
	}


	public List<String> obtenerDetalleDeInvolucrado(List<Involucrado> involucrados){
		List<String> llDetalle = new ArrayList<String>();

		for (Involucrado loInvolucrado : involucrados) {
			  List<NombreDemografico> lNombreDemografico = null;
			  if(loInvolucrado.getElementoId() != null && nombreDemograficoDAO != null)
				  lNombreDemografico = nombreDemograficoDAO.consutarNombresByInvolucrado(loInvolucrado.getElementoId());
	   		  if (lNombreDemografico!= null && !lNombreDemografico.isEmpty() ){
	   			  llDetalle.add(lNombreDemografico.get(0).getNombre() +
	   					  " " + lNombreDemografico.get(0).getApellidoPaterno() +
	   					  " " + lNombreDemografico.get(0).getApellidoMaterno());
	   		  }else{/**Enable JC*/
	   			  llDetalle.add(ConstructorCamposFaltantes.crearNombreDeInvolucradoSinNombreDemografico(relacionDAO, organizacionDAO, loInvolucrado));
	   		  }
		}
		return llDetalle;
	}


	private static List<String> obtenerDetalleDeObjetosPorTipo(List<Objeto> objetosExpediente,
			Objetos tipoObjeto) {
		List<String> llDetalle = new ArrayList<String>();

		for (Objeto objeto : objetosExpediente) {
			if(objeto.getValorByTipoObjetoVal().getValorId().equals(tipoObjeto.getValorId())){
				llDetalle.add(obtenDetalleDeObjeto(objeto));
			}
		}
		return llDetalle;
	}

	 public static String obtenDetalleDeObjeto(Objeto elemento){
		  String cadena = "";

		  //OBJETOS
   		  if(elemento instanceof Vehiculo){
   			  Vehiculo loObjeto = (Vehiculo)elemento;
   			  cadena = cadena + "Veh&#237;culo" + SEPARADOR;
   			  cadena = (loObjeto.getValorByTipoVehiculo() != null ? cadena + loObjeto.getValorByTipoVehiculo().getValor() + SEPARADOR:cadena);
   			  cadena = (loObjeto.getPlaca() != null ? cadena + loObjeto.getPlaca() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof EquipoComputo){
   			  EquipoComputo loObjeto = (EquipoComputo)elemento;
   			  cadena = cadena + "Equipo de C&#243;mputo" + SEPARADOR;
   			  cadena = (loObjeto.getTipoEquipo() != null ? cadena + loObjeto.getTipoEquipo().getValor() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof Arma){
   			  Arma loObjeto = (Arma)elemento;
   			  cadena = cadena + "Arma" + SEPARADOR;
   			  cadena = (loObjeto.getTipoArma() != null ? cadena + loObjeto.getTipoArma().getValor() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof Explosivo){
   			  Explosivo loObjeto = (Explosivo)elemento;
   			  cadena = cadena + "Explosivo" + SEPARADOR;
   			  cadena = (loObjeto.getTipoExplosivo() != null ? cadena + loObjeto.getTipoExplosivo().getValor() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof Aeronave){
   			  Aeronave loObjeto = (Aeronave)elemento;
   			  cadena = cadena + "Aeronave" + SEPARADOR;
   			  cadena = (loObjeto.getTipoAeroNave() != null ? cadena + loObjeto.getTipoAeroNave().getValor() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof Animal){
   			  Animal loObjeto = (Animal)elemento;
   			  cadena = cadena + "Animal" + SEPARADOR;
   			  cadena = (loObjeto.getTipoAnimal() != null ? cadena + loObjeto.getTipoAnimal().getValor() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof DocumentoOficial){
   			  DocumentoOficial loObjeto = (DocumentoOficial)elemento;
   			  cadena = cadena + "Documento Oficial" + SEPARADOR;
   			  cadena = (loObjeto.getTipoDocumento() != null ? cadena + loObjeto.getTipoDocumento().getValor() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof Embarcacion){
   			  Embarcacion loObjeto = (Embarcacion)elemento;
   			  cadena = cadena + "Embarcaci&#243;n" + SEPARADOR;
   			  cadena = (loObjeto.getTipoEmbarcacion() != null ? cadena + loObjeto.getTipoEmbarcacion().getValor() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof Joya){
   			  Joya loObjeto = (Joya)elemento;
   			  cadena = cadena + "Joya" + SEPARADOR;
   			  cadena = (loObjeto.getTipoJoya() != null ? cadena + loObjeto.getTipoJoya().getValor() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof Numerario){
   			  Numerario loObjeto = (Numerario)elemento;
   			  cadena = cadena + "Numerario" + SEPARADOR;
   			  cadena = (loObjeto.getMoneda() != null ? cadena + loObjeto.getMoneda() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof ObraArte){
   			  ObraArte loObjeto = (ObraArte)elemento;
   			  cadena = cadena + "Obra de Arte" + SEPARADOR;
   			  cadena = (loObjeto.getTipoObraArte() != null ? cadena + loObjeto.getTipoObraArte().getValor() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof Sustancia){
   			  Sustancia loObjeto = (Sustancia)elemento;
   			  cadena = cadena + "Sustancia" + SEPARADOR;
   			  cadena = (loObjeto.getTipoSustancia() != null ? cadena + loObjeto.getTipoSustancia().getValor() + SEPARADOR:cadena);
   		  }
   		  if(elemento instanceof Vegetal){
   			  Vegetal loObjeto = (Vegetal)elemento;
   			  cadena = cadena + "Vegetal" + SEPARADOR;
   			  cadena = (loObjeto.getTipoVegetal() != null ? cadena + loObjeto.getTipoVegetal().getValor() + SEPARADOR:cadena);
   		  }
   		  if(cadena.lastIndexOf(SEPARADOR) != -1)
   			  cadena = cadena.substring(0, cadena.lastIndexOf(SEPARADOR));
	return cadena;
	}

	@Override
	public List<ExpedienteDTO> consultarNumeroExpedienteByTipoYEstatus(
			TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente,UsuarioDTO usuario)
			throws NSJPNegocioException {

		/*
		* Usado para obtener el discriminante Id
		*/
		  long discriminanteId = 0L;


		if (usuario != null
				&& usuario.getFuncionario() != null
				&& usuario.getFuncionario().getDiscriminante() != null
				&& usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

			discriminanteId = usuario.getFuncionario().getDiscriminante()
					.getCatDiscriminanteId();
		}

        List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO
                .consultarNumeroExpedienteByTipoYEstatus(tipoExpediente != null
                        ? tipoExpediente.getValorId()
                        : null,
                        estatusExpediente != null
                                ? estatusExpediente.getValorId()
                                : null,discriminanteId);

		List<ExpedienteDTO> expRetorno = new ArrayList<ExpedienteDTO>();
		for (NumeroExpediente numeroExpediente : numerosExpedientes) {
			ExpedienteDTO expDTO = ExpedienteTransformer.transformarExpedienteBasico(numeroExpediente);
			List<AudienciaDTO> audiencias = new ArrayList<AudienciaDTO>();
			for (Audiencia audiencia : numeroExpediente.getAudiencias()) {
				AudienciaDTO audDTO = AudienciaTransformer.transformarDTO(audiencia);
				audiencias.add(audDTO);
			}
			expDTO.setAudiencias(audiencias);
			if (numeroExpediente.getExpediente().getHecho()!=null && numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo()!=null) {
			    HechoDTO hechoDto = new HechoDTO();
			    hechoDto.setHechoId(numeroExpediente.getExpediente().getHecho().getHechoId());
			    AvisoHechoDelictivoDTO avisoDto = new AvisoHechoDelictivoDTO();
			    avisoDto.setDocumentoId(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getDocumentoId());
			    avisoDto.setFolioNotificacion(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getFolioNotificacion());
			    avisoDto.setFolioDocumento(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getFolioDocumento());
			    avisoDto.setFechaAtencion(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getFechaAtencion());
			    avisoDto.setFechaCreacion(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getFechaCreacion());

			    if (numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getCatDelito()!=null) {
			        CatDelitoDTO delDto = new CatDelitoDTO();
			        delDto.setCatDelitoId(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getCatDelito().getCatDelitoId());
			        delDto.setNombre(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getCatDelito().getNombre());
			        avisoDto.setCatDelito(delDto);
			    }

			    hechoDto.setAvisoHechoDelictivo(avisoDto);
			    expDTO.setHechoDTO(hechoDto);
			}
			expRetorno.add(expDTO);
		}
		return expRetorno;
	}

    @Override
    public Long obtenerExpedienteIdPorNumExpId(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException {
        return this.expedienteDAO.obtenerExpedienteIdPorIdNumerExpediente(expedienteDTO.getNumeroExpedienteId());
    }

    @Override
	public List<ExpedienteDTO> consultarExpedientesPorUsuarioAreaEstatus(
			UsuarioDTO usuarioDTO, Long estatus) throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR LOS EXPEDIENTES ASOCIADOS A UN USUARIO, &Aacute;REA y ESTATUS (Opcional) ****/");

		if (usuarioDTO.getFuncionario()== null || usuarioDTO.getFuncionario().getClaveFuncionario() == null )
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		List<NumeroExpediente> numsExpedientes = null;

		/*
         * Usado para setear la agencia y consultar turnos para agencias de PGJ
         */
        long discriminanteId = 0L;

		if (usuarioDTO != null
				&& usuarioDTO.getFuncionario() != null
				&& usuarioDTO.getFuncionario().getDiscriminante() != null
				&& usuarioDTO.getFuncionario().getDiscriminante()
						.getCatDiscriminanteId() != null) {

			discriminanteId = usuarioDTO.getFuncionario().getDiscriminante()
					.getCatDiscriminanteId();
		}


		if(usuarioDTO.getAreaActual()!=  null && usuarioDTO.getAreaActual().getAreaId() != null){
			numsExpedientes = numeroExpedienteDAO
			.consultarByUsuarioArea(usuarioDTO.getFuncionario().getClaveFuncionario(), usuarioDTO
					.getAreaActual().getAreaId(), estatus,discriminanteId,usuarioDTO.getFuncionario().getUnidadIEspecializada().getCatUIEId());
		}else{
			numsExpedientes = numeroExpedienteDAO
			.consultarByUsuarioArea(usuarioDTO.getFuncionario().getClaveFuncionario(), null, estatus,discriminanteId,null);
		}

		List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
		for (NumeroExpediente numeroExpediente : numsExpedientes) {
			Expediente expediente = numeroExpediente.getExpediente();
            //los delitos se obitienen en el transformer
            ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformaExpediente(expediente);
            expedienteDTO.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
            expedienteDTO.setFechaApertura(numeroExpediente.getFechaApertura());
            expedienteDTO.setNumeroExpediente(numeroExpediente.getNumeroExpediente());

    	    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de
            // expedienteDTO.getEstatus();
            // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
            // expedienteDTO.getEstatusNumeroExpediente();

            if(numeroExpediente.getExpediente()!=null &&
               numeroExpediente.getExpediente().getEstatus()!=null){
            	expedienteDTO.setEstatus(
            	new ValorDTO( numeroExpediente.getExpediente().getEstatus().getValorId(), numeroExpediente.getExpediente().getEstatus().getValor()));
            }

            if(numeroExpediente.getEstatus()!=null){
            	expedienteDTO.setEstatusNumeroExpediente(ValorTransformer.transformar(numeroExpediente.getEstatus()));
            }

            // ------------------------------------------------------------------------------------------------

            //Si se trata de expedientes de procuraduria
			ConfInstitucion confInstitucion = this.expedienteDAO.consultarInsitucionActual();
        	if(confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId()) ){
        		if(numeroExpediente.getExpediente() != null && numeroExpediente.getExpediente().getEstatus() != null)
      			expedienteDTO.setEstatusExpedientePadre(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(),numeroExpediente.getExpediente().getEstatus().getValor()));
        	}

        	List<Involucrado> involucradosExp =involucradoDAO.consultarInvolucradosByExpediente(expediente.getExpedienteId());
        	List<InvolucradoDTO> involucradosDTO = new ArrayList<InvolucradoDTO>();

        	for (Involucrado involucrado : involucradosExp) {
				involucradosDTO.add(InvolucradoTransformer.transformarInvolucrado(involucrado));
			}

           expedienteDTO.setInvolucradosDTO(involucradosDTO);
            //Setear delito
			if (expedienteDTO.getDelitoPrincipal()!= null)
				expedienteDTO.setDelitoPrincipal(DelitoTransfromer.transformarDelito(delitoDAO.read(expedienteDTO.getDelitoPrincipal().getDelitoId())));

			expedientes.add(expedienteDTO);
		}
		return expedientes;
	}

	@Override
	public ExpedienteDTO consultarNumExpPorFuncionarioYNumExp(Long claveFuncionario,
			Long numExpId) throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER LA INFORMACION DEL EXPEDIENTE SI EL FUNCIONARIO TIENE LOS PERMISOS REQUERIDOS ****/");

		if (claveFuncionario==null || numExpId==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		NumeroExpediente numeroExpediente = numeroExpedienteDAO.consultarNumExpPorFuncionarioYNumExp(claveFuncionario, numExpId);

		if (numeroExpediente!=null) {
			ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformarExpedienteBasico(numeroExpediente);

			PermisoExpediente permisoExpediente = permisoExpedienteDAO.obtnerPermisoFuncionario(claveFuncionario, numExpId);
			if (permisoExpediente!=null) {
				expedienteDTO.setEsEscritura(permisoExpediente.getEsEscritura());
				expedienteDTO.setFechaLimitePermiso(permisoExpediente.getFechaLimite());
			}
			else
				expedienteDTO.setEsPropietario(true);

			return expedienteDTO;
		}

		return null;
	}

	@Override
	public List<ExpedienteDTO> consultarNumExpPorFuncionario(
			Long claveFuncionario) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEDIENTES PROPIOS Y CON PERMISOS DEL FUNCIONARIO ****/");
		if (claveFuncionario==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		List<NumeroExpediente> expedientesFuncionario = numeroExpedienteDAO.consultarNumExpPorFuncionario(claveFuncionario);

		List<ExpedienteDTO> expFuncionarioDTO = new ArrayList<ExpedienteDTO>();

		for (NumeroExpediente numeroExpediente : expedientesFuncionario) {
			expFuncionarioDTO.add(ExpedienteTransformer.transformarExpediente(numeroExpediente));
		}

		return expFuncionarioDTO;
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientesDelFuncionario(
			UsuarioDTO usuario) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEDIENTES PROPIOS DEL FUNCIONARIO ****/");
		if (usuario==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		if(usuario.getFuncionario()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		if(usuario.getFuncionario().getClaveFuncionario()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		/*
		* Usado para setear la agencia y consultar turnos para agencias de PGJ
		*/

		  ConfInstitucion confInstitucionPG = expedienteDAO.consultarInsitucionActual();
		  if(!confInstitucionPG.getConfInstitucionId().equals(Instituciones.PGJ.getValorId()) ){
			if (usuario != null
				&& usuario.getFuncionario() != null
				&& usuario.getFuncionario().getDiscriminante() != null
				&& usuario.getFuncionario().getDiscriminante()
				.getCatDiscriminanteId() != null) {
					usuario.getFuncionario().getDiscriminante().setCatDiscriminanteId(0L);
				}
		  }

		  Funcionario funcionario = FuncionarioTransformer.transformarFuncionario(usuario.getFuncionario());

		if (usuario.getAreaActual() != null
				&& usuario.getAreaActual()
						.getBuscarEnJerarquia() != null
				&& usuario.getAreaActual()
						.getBuscarEnJerarquia()) {

			funcionario.getArea().setJerarquiaOrgSubordinadas(
					new HashSet<JerarquiaOrganizacional>());

			JerarquiaOrganizacional raiz = funcionario.getArea();
			List<JerarquiaOrganizacional> lstJerarquiasDependientes = jerarquiaOrganizacionalDAO
					.getArbolJerarquiasDependientes(raiz);

			if (lstJerarquiasDependientes != null
					&& !lstJerarquiasDependientes.isEmpty()) {
				funcionario.getArea().getJerarquiaOrgSubordinadas()
						.addAll(lstJerarquiasDependientes);
			}
		}
		List<NumeroExpediente> expedientesFuncionario = numeroExpedienteDAO.consultarExpedientesDelFuncionario(funcionario);
		List<ExpedienteDTO> expFuncionarioDTO = new ArrayList<ExpedienteDTO>();

		for (NumeroExpediente numeroExpediente : expedientesFuncionario) {
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
			expedienteDTO.setInvolucradosSolicitados(true);
			expedienteDTO = obtenerExpediente(expedienteDTO);

			expFuncionarioDTO.add(expedienteDTO);
		}

		return expFuncionarioDTO;
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientescConPermisoFuncionario(
			Long claveFuncionario) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEDIENTES CON PERMISO DEL FUNCIONARIO ****/");
		if (claveFuncionario==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		UsuarioDTO usuarioDTO = usuarioService.consultarUsuarioPorClaveFuncionario(claveFuncionario);

		/*
		* Usado para turnos para agencias de PGJ
		*/
		  long discriminanteId = 0L;

		if (usuarioDTO != null
				&& usuarioDTO.getFuncionario() != null
				&& usuarioDTO.getFuncionario().getDiscriminante() != null
				&& usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

			discriminanteId = usuarioDTO.getFuncionario().getDiscriminante()
					.getCatDiscriminanteId();
		}

		List<NumeroExpediente> expedientesFuncionario = permisoExpedienteDAO.consultarExpedientescConPermisoFuncionario(claveFuncionario,discriminanteId);
		List<ExpedienteDTO> expFuncionarioDTO = new ArrayList<ExpedienteDTO>();

		for (NumeroExpediente numeroExpediente : expedientesFuncionario) {
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
			expedienteDTO.setInvolucradosSolicitados(true);
			expedienteDTO = obtenerExpediente(expedienteDTO);

			PermisoExpediente permisoExpediente = permisoExpedienteDAO.obtnerPermisoFuncionario(claveFuncionario, numeroExpediente.getNumeroExpedienteId());
			expedienteDTO.setFechaLimitePermiso(permisoExpediente.getFechaLimite());
			expedienteDTO.setEsEscritura(permisoExpediente.getEsEscritura());
			expFuncionarioDTO.add(expedienteDTO);
		}

		return expFuncionarioDTO;
	}

	public ExpedienteDTO consultarExpedienteRelacionadoAArea (String numeroExpediente, Long areaId  ) throws NSJPNegocioException {

		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		if(numeroExpediente==null || numeroExpediente.trim().isEmpty() || areaId==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

			Long expedienteId = expedienteDAO.consultarExpedienteIdPorNumeroExpediente(numeroExpediente);
			if(expedienteId==null)
				throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);

			List<NumeroExpediente>  listaNumeroExpediente = expedienteDAO.buscarNumeroExpedientes(expedienteId, areaId);

			if( listaNumeroExpediente!= null && !listaNumeroExpediente.isEmpty()){
				expedienteDTO = ExpedienteTransformer.transformarExpediente( listaNumeroExpediente.get(0) );
			}
		return expedienteDTO;
	}


	/**
	 * Metodo que permite consultar el resumen con los datos generales de un expediente de UAVD	 *
	 */
	@Override
	public DatosGeneralesExpedienteUAVDDTO obtenerResumenDeExpedienteUAVD(
			SolicitudDTO solicitudDTO) throws NSJPNegocioException {
		logger.info("/**** Consultar Datos Generales de Expediente por ID en UAVD ****/");


		if(solicitudDTO ==null || solicitudDTO.getDocumentoId() == null || solicitudDTO.getDocumentoId() <= 0)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		DatosGeneralesExpedienteUAVDDTO loResumen = new DatosGeneralesExpedienteUAVDDTO();
		NumeroExpediente numeroExpediente = null;

		//Se consulta la solicitud para obtener el NumeroExpediente_id asociado
		Solicitud loSolicitudBD = solicitudDAO.consultarSolicitudPorId(solicitudDTO.getDocumentoId());
		logger.info("/**** loSolicitudBD.getNumeroExpediente() UAVD ****/" + loSolicitudBD.getNumeroExpediente().getNumeroExpedienteId());
		if(loSolicitudBD != null){
			loResumen.setTipoSolicitud(loSolicitudBD.getTipoSolicitud() != null && loSolicitudBD.getTipoSolicitud().getValor() != null ?
					loSolicitudBD.getTipoSolicitud().getValor(): "-");

			Expediente expediente = new Expediente();
			if ( loSolicitudBD.getNumeroExpediente().getNumeroExpedienteId() != null) {
				numeroExpediente = numeroExpedienteDAO.read(loSolicitudBD.getNumeroExpediente().getNumeroExpedienteId());
				expediente = numeroExpediente.getExpediente();

				List<Involucrado> victimas = new ArrayList<Involucrado>();

				//MP Solicitante
				loResumen.setAmpSolicitante(loSolicitudBD.getFuncionarioSolicitante() != null ?
						loSolicitudBD.getFuncionarioSolicitante().getNombreCompleto():"-");
				//Area solicitante
				loResumen.setAreaSolicitante(loSolicitudBD.getFuncionarioSolicitante() != null &&
						loSolicitudBD.getFuncionarioSolicitante().getArea()!= null?
								loSolicitudBD.getFuncionarioSolicitante().getArea().getNombre():"-");

				//Fecha de creacion
				loResumen.setFechaDeCreacionDelExpediente(expediente != null && expediente.getFechaCreacion()  != null ?
						DateUtils.formatear(expediente.getFechaCreacion()):"-");
				//Estatus del expediente
				loResumen.setEstatusDelExpediente(loSolicitudBD.getNumeroExpediente() != null && loSolicitudBD.getNumeroExpediente().getEstatus()  != null ?
						loSolicitudBD.getNumeroExpediente().getEstatus().getValor():"-");

				loResumen.setExpedienteId(expediente.getExpedienteId());

				//Permite consultar el nombre de la victima
				victimas = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
						        		Calidades.VICTIMA_PERSONA.getValorId(), null);

				if (victimas == null || victimas.size() == 0) {

					// Permite consultar si un denunciante es victima tambien
					victimas = involucradoDAO.obtenerInvByIdExpAndCalidad(
							expediente.getExpedienteId(),
							Calidades.DENUNCIANTE.getValorId(), null);

					if (victimas != null && victimas.size() > 0) {
						if (victimas.get(0).getCondicion() != null
								&& victimas.get(0).getCondicion()
										.equals(new Short("0"))) {
							victimas = null;
						}
					}
				}

				List <String> listaDeInvolucrados = obtenerDetalleDeInvolucrado(victimas);
				loResumen.setNombreDeLaVictima(listaDeInvolucrados != null && listaDeInvolucrados.size() >0 ? listaDeInvolucrados.get(0) : "-");

				//Se consulta el delito obtenido
				List<Delito> delitosBD = delitoDAO.obtenerDelitoPorExpediente(expediente.getExpedienteId());
				if(delitosBD != null && delitosBD.size() >= 0)
					loResumen.setDelito(delitosBD.get(0).getCatDelito() != null && delitosBD.get(0).getCatDelito().getNombre() != null ?
							delitosBD.get(0).getCatDelito().getNombre(): "-");
				else
					loResumen.setDelito("-");

				if(numeroExpediente!=null &&
				   numeroExpediente.getEstatus()!=null &&
				   numeroExpediente.getEstatus().getValor()!=null){
					loResumen.setEstatusActuacion(numeroExpediente.getEstatus().getValor());
				}
				else{
					loResumen.setEstatusActuacion("");
				}

				if(numeroExpediente!=null &&
				   numeroExpediente.getFuncionario()!=null){
					String responsableTramite = "";
					if(numeroExpediente.getFuncionario().getNombreFuncionario()!=null){
						responsableTramite = numeroExpediente.getFuncionario().getNombreFuncionario();
					}
					if(numeroExpediente.getFuncionario().getApellidoPaternoFuncionario()!=null){
						if(responsableTramite != ""){
							responsableTramite += " " + numeroExpediente.getFuncionario().getApellidoPaternoFuncionario();
						}
						else{
							responsableTramite = numeroExpediente.getFuncionario().getApellidoPaternoFuncionario();
						}
					}
					if(numeroExpediente.getFuncionario().getApellidoMaternoFuncionario()!=null){
						if(responsableTramite != ""){
							responsableTramite += " " + numeroExpediente.getFuncionario().getApellidoMaternoFuncionario();
						}
						else{
							responsableTramite = numeroExpediente.getFuncionario().getApellidoMaternoFuncionario();
						}
					}
					loResumen.setResponsableTramite(responsableTramite);
				}
				else{
					loResumen.setResponsableTramite("");
				}
			}
		}
		return loResumen;
	}


	public List<ExpedienteDTO> buscarRemisionesConIPH(EstatusExpediente estatusExpediente)throws NSJPNegocioException{

		List<ExpedienteDTO> expedientesDTO = new ArrayList<ExpedienteDTO>();

        List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO
                .consultarNumeroExpedienteByTipoYEstatus(null,
                        estatusExpediente != null
                                ? estatusExpediente.getValorId()
                                : null,0L);

        for (NumeroExpediente numeroExpediente : numerosExpedientes) {
        	ExpedienteDTO ex = new ExpedienteDTO();
			ex.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
			ex.setInvolucradosSolicitados(true);

			ExpedienteDTO exDTO = this.obtenerExpediente(ex);

		    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de
            // exDTO.getEstatus();
            // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
            // exDTO.getEstatusNumeroExpediente();

			if(numeroExpediente.getEstatus()!=null){
				exDTO.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus()
                        .getValorId(), numeroExpediente.getEstatus().getValor()));
			}

			if (numeroExpediente.getExpediente()!=null &&
				numeroExpediente.getExpediente().getEstatus()!= null) {
				exDTO.setEstatus(new ValorDTO(
						numeroExpediente.getExpediente().getEstatus().getValorId(),
						numeroExpediente.getExpediente().getEstatus().getValor()));
		    }

 		    // ------------------------------------------------------------------------------------------------

			expedientesDTO.add(exDTO);
		}

        return expedientesDTO;
	}



	@Override
	public List<ExpedienteDTO> buscarRemisionesConIPH(EstatusExpediente estatusExpediente,Long CatUIED)throws NSJPNegocioException{

		List<ExpedienteDTO> expedientesDTO = new ArrayList<ExpedienteDTO>();

        List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO.consultarNumeroExpedienteByTipoYEstatus(estatusExpediente != null
                ? estatusExpediente.getValorId(): null, CatUIED);


        for (NumeroExpediente numeroExpediente : numerosExpedientes) {
        	ExpedienteDTO ex = new ExpedienteDTO();
			ex.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
			ex.setInvolucradosSolicitados(true);

			ExpedienteDTO exDTO = this.obtenerExpediente(ex);

			if(numeroExpediente.getEstatus()!=null){
				exDTO.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus()
                        .getValorId(), numeroExpediente.getEstatus().getValor()));
			}

			if (numeroExpediente.getExpediente()!=null &&
				numeroExpediente.getExpediente().getEstatus()!= null) {
				exDTO.setEstatus(new ValorDTO(
						numeroExpediente.getExpediente().getEstatus().getValorId(),
						numeroExpediente.getExpediente().getEstatus().getValor()));
		    }

			expedientesDTO.add(exDTO);
		}

        return expedientesDTO;
	}

	/**
	 * Metodo que consulta los expedientes de acuerdo al filtro
	 */
	@Override
	public List<ExpedienteDTO> consultarExpedientesPorFiltroST(
			Date fechaInicio, Date fechaFin, UsuarioDTO usuario,
			List<Long> estatusExpediente, Long discriminanteId,Long rolId , Long idDistrito, Long idFuncionario) throws NSJPNegocioException {

		logger.info("/****CONSULTA LOS EXPEDIENTES PARA SISTEMA TRADICIONAL ****/");


		if(usuario ==null || estatusExpediente == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		if (usuario.getFuncionario() == null
				|| usuario.getFuncionario().getJerarquiaOrganizacional() == null
				|| usuario.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		/*
		* Usado para obtener el discriminante Id
		*/


		if (discriminanteId == 0 && usuario.getFuncionario().getDiscriminante() != null
				&& usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

			discriminanteId = usuario.getFuncionario().getDiscriminante()
					.getCatDiscriminanteId();
		}

		List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO
				.consultarExpedientesPorFiltroST(fechaInicio, fechaFin, usuario
						.getFuncionario().getJerarquiaOrganizacional()
						.getJerarquiaOrganizacionalId(), estatusExpediente,discriminanteId, rolId, idDistrito, idFuncionario);

		List<ExpedienteDTO> expedientesDTO = new ArrayList<ExpedienteDTO>();

		 for (NumeroExpediente numeroExpediente : numerosExpedientes) {
	        	ExpedienteDTO ex = new ExpedienteDTO();
				ex.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
				ex.setInvolucradosSolicitados(true);
				ExpedienteDTO exDTO = this.obtenerExpediente(ex);
				expedientesDTO.add(exDTO);
			}

		 return expedientesDTO;
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientesPorIdCaso(CasoDTO caso)
			throws NSJPNegocioException {

		List<Expediente> fromBD = null;

		fromBD = this.expedienteDAO.consultarExpedientesPorIdCaso(caso);

		return ExpedienteTransformer.transformarExpedientesBasico(fromBD);

	}

	@Override
	public List<ExpedienteDTO> consultarNumeroDeExpedienteConHechoPorFiltros(
			EstatusExpediente estatusExpediente, UsuarioDTO usuarioDto,
			Date fechaInicio, Date fechaFin) throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("/*BIENVENIDO AL SERVICIO PARA CONSULTAR EXPEDIENTES CON AVISO HECHO DELICTIVO POR FILTRO*/");
		}

		if (estatusExpediente == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Long discriminante = null;

		if (usuarioDto != null
				&& usuarioDto.getFuncionario() != null
				&& usuarioDto.getFuncionario().getDiscriminante() != null
				&& usuarioDto.getFuncionario().getDiscriminante()
						.getCatDiscriminanteId() != null) {

			discriminante = usuarioDto.getFuncionario().getDiscriminante()
					.getCatDiscriminanteId();
		}

		List<NumeroExpediente> listaNumerosExpedientes = numeroExpedienteDAO
				.consultarNumeroDeExpedienteConHechoPorFiltros(
						estatusExpediente, discriminante, fechaInicio, fechaFin);

		List<ExpedienteDTO> expRetorno = new ArrayList<ExpedienteDTO>();

		for (NumeroExpediente numeroExpediente : listaNumerosExpedientes) {

			ExpedienteDTO expDTO = ExpedienteTransformer
					.transformarExpedienteBasico(numeroExpediente);

			HechoDTO hechoDto = new HechoDTO();

			hechoDto.setHechoId(numeroExpediente.getExpediente().getHecho()
					.getHechoId());

			AvisoHechoDelictivoDTO avisoDto = AvisoHechoDelictivoTransformer
					.transformarAvisoDtoBasico(numeroExpediente.getExpediente()
							.getHecho().getAvisoHechoDelictivo());

			hechoDto.setAvisoHechoDelictivo(avisoDto);
			expDTO.setHechoDTO(hechoDto);
			expRetorno.add(expDTO);
		}
		return expRetorno;
	}

	@Override
	public List<ExpedienteDTO> consultarNumeroDeExpedienteSinHechoPorFiltros(
			EstatusExpediente estatusExpediente, UsuarioDTO usuarioDto,
			Date fechaInicio, Date fechaFin) throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("/*BIENVENIDO AL SERVICIO PARA CONSULTAR EXPEDIENTES SIN AVISO HECHO DELICTIVO POR FILTRO*/");
		}

		if (estatusExpediente == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Long discriminante = null;

		if (usuarioDto != null
				&& usuarioDto.getFuncionario() != null
				&& usuarioDto.getFuncionario().getDiscriminante() != null
				&& usuarioDto.getFuncionario().getDiscriminante()
						.getCatDiscriminanteId() != null) {

			discriminante = usuarioDto.getFuncionario().getDiscriminante()
					.getCatDiscriminanteId();
		}

		List<NumeroExpediente> listaNumerosExpedientes = numeroExpedienteDAO
				.consultarNumeroDeExpedienteSinHechoPorFiltros(
						estatusExpediente, discriminante, fechaInicio, fechaFin);

		List<ExpedienteDTO> expRetorno = new ArrayList<ExpedienteDTO>();

		for (NumeroExpediente numeroExpediente : listaNumerosExpedientes) {

			ExpedienteDTO expDTO = ExpedienteTransformer
					.transformarExpedienteBasico(numeroExpediente);

			HechoDTO hechoDto = new HechoDTO();

			hechoDto.setHechoId(numeroExpediente.getExpediente().getHecho()
					.getHechoId());

			expDTO.setHechoDTO(hechoDto);
			expRetorno.add(expDTO);
		}
		return expRetorno;
	}


	/**
	 * Permite consultar numeros de expedientes asociados a un identificador de expediente (opcionalmente)
	 * que tengan como responsable a un usuario que cuente con el rol asociado.
	 */
	@Override
	public void actualizarEstatusNumerosDeExpedientesPorRolST(
			List<Long> roles, Long idExpediente,Long nuevoEstatusNumeroExpediente , Long nuevoEstatusExpediente) throws NSJPNegocioException {

		logger.info("/**** NUMEROS DE EXPEDIENTES A ACTUALIZAR PARA SISTEMA TRADICIONAL ****/");

		if(idExpediente ==null || nuevoEstatusNumeroExpediente == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO.consultarNumerosDeExpedientesPorRolST(roles, idExpediente);

		//Se actualiza el estatus de la lista de numeros de expedientes
		 for (NumeroExpediente numeroExpediente : numerosExpedientes) {
			 NumeroExpediente loNnumeroExpedienteBD= numeroExpedienteDAO.read(numeroExpediente.getNumeroExpedienteId());
			 loNnumeroExpedienteBD.setEstatus(new Valor(nuevoEstatusNumeroExpediente));
			 numeroExpedienteDAO.update(loNnumeroExpedienteBD);
			 logger.info("Numero Expediente actualizado: " + numeroExpediente.getNumeroExpedienteId() + " al estatus: " + nuevoEstatusNumeroExpediente);
		}

		 logger.info("Total de Numeros de expedientes actualizados: " + numerosExpedientes.size() + " al estatus: " + nuevoEstatusNumeroExpediente);


		//Se actualiza el estatus del expediente
		if(nuevoEstatusExpediente != null && nuevoEstatusExpediente > 0){
			 Expediente loExpedienteBD= expedienteDAO.read(idExpediente);
			 loExpedienteBD.setEstatus(new Valor(nuevoEstatusExpediente));
			 expedienteDAO.update(loExpedienteBD);
			 logger.info("Expediente actualizado: " + idExpediente + " al estatus: " + nuevoEstatusExpediente);
		}


	}


	@Override
	public List<String> buscarNumerosExpedientesByIdExpediente(
			ExpedienteDTO idExpediente) throws NSJPNegocioException {
		Expediente expediente=expedienteDAO.read(idExpediente.getExpedienteId());
		List<String> numerosExpediente=new ArrayList<String>();
		for (NumeroExpediente numeros : expediente.getNumeroExpedientes()) {
			numerosExpediente.add(numeros.getNumeroExpediente());
		}
		return numerosExpediente;
	}


	@Override
	public List<ExpedienteDTO> consultarExpedienteCoorAT(
			FiltroExpedienteDTO filtroExpedienteDTO)
			throws NSJPNegocioException {
		if(filtroExpedienteDTO ==null || filtroExpedienteDTO.getTipoBusquedaCoorAT() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		List<ExpedienteDTO> listExpedienteDTOs=new ArrayList<ExpedienteDTO>();

		try{
			TipoBusquedaCoordinadorAT tipo=TipoBusquedaCoordinadorAT.getByValor(filtroExpedienteDTO.getTipoBusquedaCoorAT());
			List<Expediente> list=new ArrayList<Expediente>();
			switch (tipo) {
			case EXPEDIENTES_ATP_DIA:
				if(filtroExpedienteDTO.getFechaBusqueda()==null){
					filtroExpedienteDTO.setFechaBusqueda(new Date());
				}
				list=expedienteDAO.consultaExpedientesDoorAT(filtroExpedienteDTO);
				for (Expediente expediente : list) {
					//Enable JC. Mostrar denunciante y delito principal en bandeja del COORDINADOR_AT
					Expediente expTemp = expedienteDAO.read(expediente.getExpedienteId());
					expediente.setDelitos(expTemp.getDelitos());

					ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformaExpediente(expediente);

					List<Involucrado> involucrados = involucradoDAO.consultarInvolucradosByExpediente(expediente.getExpedienteId());
					List<InvolucradoDTO> involucradosDTO = new ArrayList<InvolucradoDTO>();
					for (Involucrado involucrado : involucrados) {
						involucradosDTO.add(InvolucradoTransformer.transformarInvolucrado(involucrado));
					}

					for (InvolucradoDTO involucradoDTO : involucradosDTO) {
						List<NombreDemografico> nombres = nombreDemograficoDAO.consutarNombresByInvolucrado(involucradoDTO.getElementoId());
						involucradoDTO.setNombresDemograficoDTO(NombreDemograficoTransformer.transformarNombreDemografico(nombres));
					}

					expedienteDTO.setInvolucradosDTO(involucradosDTO);
					////////////
					listExpedienteDTOs.add(expedienteDTO);
				}
				break;
			case EXPEDIENTES_ATP_TODOS:
				list=expedienteDAO.consultaExpedientesDoorAT(filtroExpedienteDTO);
				for (Expediente expediente : list) {
					listExpedienteDTOs.add(ExpedienteTransformer.transformaExpediente(expediente));
				}
				break;
			case EXPEDIENTES_ATP_AGENCIA:
				if(filtroExpedienteDTO.getIdAgencia()==null){
					throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				}
				list=expedienteDAO.consultaExpedientesDoorAT(filtroExpedienteDTO);
				for (Expediente expediente : list) {
					listExpedienteDTOs.add(ExpedienteTransformer.transformaExpediente(expediente));
				}
				break;
			case EXPEDIENTES_ATP_USUARIO:
				if(filtroExpedienteDTO.getIdFuncionario()==null){
					throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				}
				list=expedienteDAO.consultaExpedientesDoorAT(filtroExpedienteDTO);
				for (Expediente expediente : list) {
					listExpedienteDTOs.add(ExpedienteTransformer.transformaExpediente(expediente));
				}
				break;
			case EXPEDIENTES_AT_DIA:
				if(filtroExpedienteDTO.getFechaBusqueda()==null){
					filtroExpedienteDTO.setFechaBusqueda(new Date());
				}
				list=expedienteDAO.consultaExpedientesDoorAT(filtroExpedienteDTO);
				for (Expediente expediente : list) {
					listExpedienteDTOs.add(ExpedienteTransformer.transformaExpediente(expediente));
				}
				break;
			case EXPEDIENTES_AT_TODOS:
				list=expedienteDAO.consultaExpedientesDoorAT(filtroExpedienteDTO);
				for (Expediente expediente : list) {
					listExpedienteDTOs.add(ExpedienteTransformer.transformaExpediente(expediente));
				}
				break;
			case EXPEDIENTES_AT_AGENCIA:
				if(filtroExpedienteDTO.getIdAgencia()==null){
					throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				}
				list=expedienteDAO.consultaExpedientesDoorAT(filtroExpedienteDTO);
				for (Expediente expediente : list) {
					listExpedienteDTOs.add(ExpedienteTransformer.transformaExpediente(expediente));
				}
				break;
			case EXPEDIENTES_AT_USUARIO:
				if(filtroExpedienteDTO.getIdFuncionario()==null){
					throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				}
				list=expedienteDAO.consultaExpedientesDoorAT(filtroExpedienteDTO);
				for (Expediente expediente : list) {
					listExpedienteDTOs.add(ExpedienteTransformer.transformaExpediente(expediente));
				}
				break;
			default:
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}

		}catch (Exception e) {
			logger.info("error en consultarExpedienteCoorAT:"+e);
			throw new NSJPNegocioException(CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);
		}
		return listExpedienteDTOs;
	}




	@Override
	public List<ExpedienteDTO> consultarExpedientescPorCasoEna(CasoDTO caso) throws NSJPNegocioException {
		List<Expediente> fromBD = null;
			fromBD = this.expedienteDAO
			.consultarExpedientesPorNumeroCasoEna(caso.getNumeroGeneralCaso());
			List<ExpedienteDTO> expedientes = ExpedienteTransformer.transformarExpedientesBasico(fromBD);
			return expedientes;

	}

	
	public List<ExpedienteDTO> consultarExpedientesJusticiaRestaurativaPorArea (Long areaId, Long claveFuncionario  ) throws NSJPNegocioException {
		
		/*int i=0, cont=0;
		String consecutivo = null;
		boolean agregar=false;*/
		List<ExpedienteDTO> listExpedienteDTOs	= new ArrayList<ExpedienteDTO>();
		
		if(areaId==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		List<NumeroExpediente>  listaNumeroExpediente = expedienteDAO.buscarNumerosDeExpedientesPorArea(areaId, claveFuncionario);
		for (NumeroExpediente numeroExpediente : listaNumeroExpediente) {
			ExpedienteDTO expedienteDTO				= new ExpedienteDTO();
			///consecutivo = numeroExpediente.getNumeroExpediente().substring(0,5);
			if( listaNumeroExpediente!= null && !listaNumeroExpediente.isEmpty()){
				List<Involucrado> involucrados = involucradoDAO.obtenerInvByIdExpAndCalidad(numeroExpediente.getExpediente().getExpedienteId(),Calidades.DENUNCIANTE.getValorId(), null);
				for (Involucrado involucrado : involucrados) {
					expedienteDTO.addInvolucradoDTO(InvolucradoTransformer.transformarInvolucradoBasico(involucrado));
				}
				
				List<Delito> listDelitos = delitoDAO.obtenerDelitoPorExpediente(numeroExpediente.getExpediente().getExpedienteId());
				for (Delito delito : listDelitos) {
					if (delito.getEsPrincipal() == true) {
						DelitoDTO delitoDTO = DelitoTransfromer.transformarDelito(delito);
						expedienteDTO.setDelitoPrincipal(delitoDTO);
					}
				}
				expedienteDTO.setNumeroExpediente(numeroExpediente.getNumeroExpediente());
				expedienteDTO.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
				expedienteDTO.setFechaApertura(numeroExpediente.getFechaApertura());
				
				listExpedienteDTOs.add(expedienteDTO);
				/*if (numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId() == Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal()){ 
					expedienteDTO = ExpedienteTransformer.transformarExpediente( listaNumeroExpediente.get(i) );
					cont=0;
				}
				else if (consecutivo.equals(numeroExpediente.getNumeroExpediente().substring(0,5)) && numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId() == areaId){
					agregar = (cont<1)?true:false;
					cont++;
				}
				else if(consecutivo.equals(numeroExpediente.getNumeroExpediente().substring(0,5)) && numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId() != areaId)
					cont++;
				
				if (agregar){
					List<Involucrado> involucrados = involucradoDAO.obtenerInvByIdExpAndCalidad(numeroExpediente.getExpediente().getExpedienteId(),Calidades.DENUNCIANTE.getValorId(), null);
					for (Involucrado involucrado : involucrados) {
						expedienteDTO.addInvolucradoDTO(InvolucradoTransformer.transformarInvolucradoBasico(involucrado));
					}
					
					List<Delito> listDelitos = delitoDAO.obtenerDelitoPorExpediente(numeroExpediente.getExpediente().getExpedienteId());

					for (Delito delito : listDelitos) {
						if (delito.getEsPrincipal() == true) {
							DelitoDTO delitoDTO = DelitoTransfromer.transformarDelito(delito);
							expedienteDTO.setDelitoPrincipal(delitoDTO);
						}
					}		
					listExpedienteDTOs.add(expedienteDTO);
					agregar = false;
				}*/
			}
			///i++;
		}
		return listExpedienteDTOs;
	}


	@Override
	public List<ExpedienteDTO> consultarExpedientesCompartidosDefensor(
			Long iClaveFuncionario) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEDIENTES PROPIOS Y CON PERMISOS DEL FUNCIONARIO ****/");
		if (iClaveFuncionario==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		List<NumeroExpediente> expedientesFuncionario = numeroExpedienteDAO.consultarExpedientesCompartidosAFuncionario(iClaveFuncionario);

		List<ExpedienteDTO> listRetorno = new ArrayList<ExpedienteDTO>();
		List<Involucrado> lista = null;
		ExpedienteDTO expediente =null;
		for (NumeroExpediente numeroExpediente : expedientesFuncionario) {
			expediente = ExpedienteTransformer.transformarExpedienteDefensoria(numeroExpediente);
			lista = involucradoDAO.consultarInvolucradosByExpediente(numeroExpediente.getExpediente().getExpedienteId());
			for(Involucrado inv : lista){
				expediente.addInvolucradoDTO(InvolucradoTransformer.transformarInvolucradoBasico(inv));
			}
            Involucrado defendido = defensaInvolucradoDAO.consultarInvolucradoNumeroExpedienteDefensoria(expediente.getNumeroExpedienteId());
            if(defendido != null){
            	expediente.setInputado(InvolucradoTransformer.transformarInvolucrado(defendido));
            }
			listRetorno.add(expediente);
			
		}

		return listRetorno;
	}


}
