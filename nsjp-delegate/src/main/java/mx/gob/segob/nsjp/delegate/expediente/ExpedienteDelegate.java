/**
 *
 * Nombre del Programa : ExpedienteDelegate.java                                    
 * Autor                            : Cesar Agustin                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 30/03/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Interface del delegate para los servicios de expediente                       
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
package mx.gob.segob.nsjp.delegate.expediente;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.OrigenExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ActaCircunstanciadaDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteUAVDDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.NotaExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.RelNumExpedienteAuditoriaDTO;
import mx.gob.segob.nsjp.dto.expediente.TurnoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.InstitucionDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * @author CesarAgustin
 * @version 1.0
 */
public interface ExpedienteDelegate {
	
    /**
     * @author encesarvarga Enable IT ByYolo
     * Consulta el expediente compartidos a un Funcionario.
     * El metodo es implementado usando
     * {@link #consultarExpedientesCompartidosDefensor(Long)
     * consultarExpedientesCompartidosDefensor}
     * @param iClaveFuncionario se consultan expediente por clave.
     * @return Los expedientes con la informacion requerida.
     * @throws NSJPNegocioException En caso de parametros faltantes
     */
    List<ExpedienteDTO> consultarExpedientesCompartidosDefensor(Long iClaveFuncionario) throws NSJPNegocioException;

    /**
     * Consulta el expediente de un usuario en una etapa dada.
     * El metodo es implementado usando
     * {@link #consultarExpedientesPorEtapa(EtapasExpediente, Long, Long)
     * consultarExpedientesPorEtapa}
     * @param usuario El usuario del que se consultara un expediente.
     * @param idEtapa La etapa de la que se esta buscando el expediente.
     * @return Los expedientes con la informacion requerida.
     * @throws NSJPNegocioException En caso que usuario o idEtapa sean null
     */
    List<ExpedienteDTO> consultarExpedientesPorUsuarioYEtapa(UsuarioDTO usuario,
            Long idEtapa) throws NSJPNegocioException;

    /**
     * Consulta la siguiente informacion de un expediente, dado un expediente y
     * un usuario:
     * <ol>
     * <li> Numero de caso
     * <li> Numero de causa, que en Poder Judicial se cumple que el "numero de
     * causa" == "Numero de expediente"
     * <li> Etapa del expediente
     * <li> Nombre del imputado. Esta informacion se encuentra dentro de
     * ExpedienteDTO.involucrados[0] del expediente consultado.
     * <li> Delitos del imputado. Esta infomacion se encuentra dentro de
     * ExpedienteDTO.delitos
     * <li> Segun el CU "Enviar aviso de designación" se deberia consultar la
     * informacion "del individuo para el que se solicita el defensor" pero esta
     * informacion es la misma que el "Nombre del imputado"
     * </ol>
     * @param expedienteDto El expediente del cual se obtendra su detalle.
     * @param usuarioDto El usuario (funcionario) asociado al expediente
     * @return El detalle del expediente con la informacion requerida.
     * @throws NSJPNegocioException En caso que alguna de las siguientes
     * condiciones se cumpla:
     * <ol>
     * <li> {@code expedienteDto == null}
     * <li> {@code expedienteDto.ExpedienteId == null}
     * <li> {@code usuarioDto == null}
     * </ol>
     */
    ExpedienteDTO consultarDetalleExpediente(ExpedienteDTO expedienteDto,
            UsuarioDTO usuarioDto) throws NSJPNegocioException;

    /**
     * Genera un nuevo número de expediente.<br>
     * Al generar el número guarda un expediente en la BD.
     * 
     * @param expedienteDTO
     *            Obligatorio la <b>fechaApertura</b> y <b>area.areaId</b>.
     *            Opcional <b>casoDTO</b>.
     * @return <ul>
     *         <li>expedienteId</il>
     *         <li>numeroExpediente</il>
     *         <li>fechaApertura</il>
     *         </ul>
     * @throws NSJPNegocioException
     *             En caso de ocurrir algún error.
     */
    public ExpedienteDTO asignarNumeroExpediente(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;

    /**
     * Asigna los nuevos números de expediente al funcionario en sesión
     * @param listExpedientes
     * @return
     * @throws NSJPNegocioException
     */
    public Boolean asignarNumerosDeExpediente(List<ExpedienteDTO> listaExpedientes)
            throws NSJPNegocioException;
    
    /**
     * Genera un nuevo número de expediente a partir de un turno.<br>
     * Al generar el número guarda un expediente en la BD.
     * 
     * @param TurnoDTO
     *            Obligatorios <b>turnoId, usuario.idUsuario,
     *            expediente.area.areaId</b>.
     * @return <ul>
     *         <li>expedienteId</il>
     *         <li>numeroExpediente</il>
     *         <li>fechaApertura</il>
     * 
     *         </ul>
     * @throws NSJPNegocioException
     *             En caso de ocurrir algún error.
     */
    public ExpedienteDTO asignarNumeroExpediente(TurnoDTO turno)
            throws NSJPNegocioException;

    /**
     * Ejecura la búsqueda de expedientes en base al filtro.
     * 
     * @param filtrosBusquedaExpediente
     * @return Información básica de los expedientes
     * @throws NSJPNegocioException
     */
    public List<ExpedienteDTO> buscarExpedientes(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException;
    
    /**
     * Ejecura la búsqueda de expedientes en base al filtro.
     * 
     * @param filtrosBusquedaExpediente
     * @return Información básica de los expedientes
     * @throws NSJPNegocioException
     */
    public List<ExpedienteDTO> buscarExpedientesPorNumExpDiscriminanteArea(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException;
    
    /**
     * Consulta el detalle de un expediente en base a su identificador.
     * 
     * @param expedienteDTO
     *            Identificador del expdiente en el sistema e identificador del
     *            area: <b>expedienteId</b> y <b>area.areaId</b> <br>
     * @return El expediente.
     * @throws NSJPNegocioException
     *             En caso de ocurrir algún error de negocio al consultar.
     */
    ExpedienteDTO obtenerExpediente(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;
    /**
     * Consulta los expedientes de un caso para pintar el árbol de
     * casos-expedientes.
     * 
     * @param caso
     *            caso.<b>casoId</b> es requerido.
     * @return Una lista con los siguientes valores de expedientes asignados:
     *         <ul>
     *         <li>expedienteId</il>
     *         <li>numeroExpediente</il>
     *         </ul>
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarExpedientesPorCaso(CasoDTO caso)
            throws NSJPNegocioException;
    /**
     * Consulta los expedientes que contengan un involucrado con el alias
     * enviado como parametro
     * 
     * @param filtrosBusquedaExpediente
     * @return Lista de involucrados con su expedinete correspondiente
     * @throws NSJPNegocioException
     */
    List<InvolucradoDTO> consultarExpedientesPorFiltros(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException;
    
    /**
     * Consulta los expedientes que contengan un involucrado con el alias
     * enviado como parametro
     * 
     * @param filtrosBusquedaExpediente
     * @return Lista de involucrados con su expedinete correspondiente
     * @throws NSJPNegocioException
     */
    List<InvolucradoDTO> consultarExpedientesPorFiltrosYDiscriminante(
            FiltroExpedienteDTO filtrosBusquedaExpediente,UsuarioDTO usuarioFirmado)
            throws NSJPNegocioException;

    /**
     * Genera el expediente para el area de justicia alterna restaurativa
     * 
     * @param expedienteDTO
     * @return
     * @throws NSJPNegocioException
     *             expediente generado con su identificar
     */
    public ExpedienteDTO generarExpJusticaAltRest(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;

    /**
     * Obtiene los expedientes de acuerdo a una actividad, area y anio.
     * 
     * @param filtroExpedienteDTO
     * @return
     * @throws NSJPNegocioException
     */
    public List<ExpedienteDTO> consultarExpedienteActividadAreaAnio(
            FiltroExpedienteDTO filtroExpedienteDTO)
            throws NSJPNegocioException;
    
    /**
     * Busca los expedientes canalizador filtrando por área = UI y actividad = RECIBIR_CANALIZACION_UI
     * @param filtroExpedienteDTO
     * @return
     * @throws NSJPNegocioException
     */
    public List<ExpedienteDTO> consultarCanalizadosCoordinadorAmpGeneral(
            FiltroExpedienteDTO filtroExpedienteDTO)
            throws NSJPNegocioException;

   /**
    * Operación que realiza la funcionalidad de relacionar el documento con el número de Causa
    * 
    * @param documento
    * @param el número de causa
    * @return  
    * @throws NSJPNegocioException
    */
    public void asociarDocumentoCausaTOCA(DocumentoDTO documento, ExpedienteDTO causa, ActividadDTO actividadDTO)throws NSJPNegocioException;
    
	/**
	 * Este servicio permitirá al solicitante crear un número de expediente y 
	 * asignarlo a un expediente, o en su caso crear un nuevo expediente en el sistema.
	 * La información que tendrá e nuevo número de expediente es:
	 *     - Usuario firmado.
	 *   La información que tendrá el nuevo expediente es:
	 *     - Número de expediente.
	 *     - Usuario firmado.
	 * Para el manejo del Número de Expediente (NE) se tiene el siguiente formato:
	 *      LLL/II/DD/UU/AAAA/C-NNNNN   Ejeplo: 000/PR/15/RBO/2011/C-12345
	 *      
	 *      Longitud máxima: 26 (caracteres incluyendo '/')
	 *      
	 * en donde se devide en 3 partes: 
	 * 
	 * Prefijo:  LLL/II/DD/UU   => 000/PR/15/RBO
	 *     Parte del NE que se podrá configurar en cuanto a la longitud.
	 *     Longitud máxima: 13 (caracteres incluyendo '/')
	 *     En donde:
	 * 			L = Carácter Libre para completar la cadena {3 caracteres máximo}
	 * 			I = Prefijo Institución {2 caracteres máximo}
	 * 			D = Prefijo Distrito {2 caracteres máximo}
	 * 			U = Prefijo Unidad {3 caracteres máximo}
	 *     
	 * Año: /AAAA/    => /2011/
	 * 	   Parte del NE que representa el año actual del sistema, y es fijo.
	 * 	   Longitud exacta: 6 (caracteres incluyendo '/')
	 * 
	 * Subfijo:  C-NNNNN    => C-12345
	 * 		Parte del NE que es fija y representa el valor consecutivo del NE.
	 * 		Longitud exacta: 7 (caracteres incluyendo '-')
	 * 		En donde:
	 * 			C = Carácter que es un consecutivo en el rango de {A-Z}
	 * 			N = Número entero que representa un consecutivo en el rango {0-9}
	 * 
	 *  //TODO GBP Falta determinar la concurrencia del servicio
	 *  
	 * @param usuarioDTO
	 * @param expedienteDTO
	 * @param institucionDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO crearAsignarNumeroExpediente(UsuarioDTO usuarioDTO,
			ExpedienteDTO expedienteDTO, InstitucionDTO institucionDTO)
			throws NSJPNegocioException;
	
    /**
     * Servicio que realiza la funcionalidad de consultar el último número del
     * expediente que se tiene registrado en la Institución
     *
     * Regresa el número de expediente de tipo cadena, en caso contrario devuelve null.
     *
     * @param idInstitucion
     * @return
     * @throws NSJPNegocioException
     */
    public String consultarUltimoNumeroExpediente(Long idInstitucion) throws NSJPNegocioException;
	
	/**
	 * Servicio que realiza la funcionalidad de crear un expediente con:
	 * 	 Fecha de creación: fecha actual del sistema.
	 *   Estatus: EstatusExpedeinte.ABIERTO
	 * Regresa un objeto de tipo Expediente, en caso contrario regresa NULL.
	 * 
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO crearExpediente () throws NSJPNegocioException;
	
    /**
     * Servicio que realiza la funcionalidad de asociar el Número de Expediente a:
     * 	Usuario - Funcionario 
     *  Institucion - Jerarquia Organizacional.
     *  Expediente
     *  
     * @param expedienteDto
     * @throws NSJPNegocioException
     */
    public void asociarNumExpediente(ExpedienteDTO expedienteDto)
    throws NSJPNegocioException;

    /**
	 * Operación que realiza la funcionalidad de asociar la solicitud a la carpeta de investigación.
	 * Recibe como parámetros:
	 * - El número de expediente.
	 * - El identificador de la solicitud.
	 * Regresa verdadero en caso de haber realizado de forma correcta la asociación, 
	 * Arroja una excepción si se encontro con algún problema.
	 * 
	 * El proceso que sigue es el siguiente:
	 * 1.-Consulta la solicitud por id
     * 2.-Consulta el numero de expediente por NumeroExpediente (cadena)
     *    NOTA: El numero del expediente debe ser único, de acuerdo a la regla de negocio, 
     *          caso contrario, se arroja una excepción.
     * 3.-Asocia a la solicitud el expediente consultado.
     * 
	 * @param expedienteDTO
	 * @param idSolicitud
	 * @return
	 * @throws NSJPNegocioException
	 */
    public boolean asociarNumCarpetaASolicitud( ExpedienteDTO expedienteDTO, String numExpedientePadre_id, Long idSolicitud)throws NSJPNegocioException;
	
	/**
	 * Operación que realiza la funcionalidad de consultar el prefijo definido para la institución- área.
	 * Devuelve una cadena asignada al prefijo de acuerdo a lo siguiente:
	 * 		LLLL/II/DD/UU   {13 caracteres de longitud máxima}
	 * 	En donde:		
	 * 		L = Carácter Libre de relleno {3 caracteres}
	 * 		I = Prefijo Institución {2 caracteres}
	 * 		D = Prefijo Distrito {2 caracteres}
	 * 		U = Prefijo Unidad {3 caracteres}
	 * 
	 * Notas: 
	 * 	1.- En caso de que el área no este asociado a un distrito se completara con -- Ejemplo:  000/PR/--/RBP 
	 *  2.- El carácter libre permite completar la longitud del prefijo.
	 *  3.- El carácter libre, en una futura implementación será capturado por el usuario.
	 *  
	 * @param institucionDTO
	 * @return
	 */
	public String consultarPrefijo(InstitucionDTO institucionDTO) throws NSJPNegocioException;
	
	/**
     * Permite filtrar los Expediente en base a:
     * @param etapa Permite filtrar  por las difierentes etapas que puede tener un expediente
     * @param usuarioId Permite filtrar los expedientes designados a un defensor
     * @param areaId Representa el area a la que esta asociada el expediente
     * @return List<Expediente>
     */
    public List<ExpedienteDTO> consultarExpedientesPorEtapa(EtapasExpediente etapa, Long usuarioId,Long areaId) throws NSJPNegocioException;
    
    
    /**
     * Consulta los datos generales de un expediente en base a su identificador.
     * 
     * @param expedienteDTO
     *            Identificador del expdiente en el sistema e identificador del
     *            area: <b>expedienteId</b> y <b>area.areaId</b> <br>
     * @return Lista de documentos con los siguientes valores
		 * - id Documento
		 * - Tipo documento
		 * - Nombre documento
		 * - Fecha creación
     * @throws NSJPNegocioException
     *             En caso de ocurrir algún error de negocio al consultar.
     */    
    public DatosGeneralesExpedienteDTO obtenerDatosGeneralesDeExpediente(ExpedienteDTO expedienteDTO)
            		throws NSJPNegocioException;
    
    
    /**
	 * Metodo que permite actualizar el tipo de Expediente
	 * @param expedienteDTO.NumeroExpedienteId: Representa el numero del expediente a actualizar
	 * @param tipo: Representa el tipo de expediente, usar enum TipoExpediente
	 * @throws NSJPNegocioException
	 */
	public void actualizarTipoExpediente(ExpedienteDTO expedienteDTO, OrigenExpediente tipo) throws NSJPNegocioException;	
	

    /**
     * Obtiene el NumeroExpediente, de acuerdo al numero expediente enviado como parametro
     * @author cesarAgustin
     * @param expedienteDTO
     * 			-Cadena de numero de expediente
     * @return
     * @throws NSJPNegocioException
     */
    public ExpedienteDTO obtenerNumeroExpedienteByNumExp(ExpedienteDTO expedienteDTO,UsuarioDTO usuario) throws NSJPNegocioException; 

	    /**
     * Obtiene el expedienteDTO a partir del numero de expediente
     * @param numeroExpediente
     * @return ExpedienteDTO
     * @throws NSJPNegocioException
     */
    ExpedienteDTO obtenerExpedientePorNumeroExpediente(String numeroExpediente)throws NSJPNegocioException;

    /**
     * Consulta los expedientes que tienen al menos un evento (audiencia o recurso) con fecha de evento
     * entre la fecha actual y el periodo hacia atrás definido por el parámetro del sistmea de tiempo de 
     * consulta histórica y que sean de algún caso en particular
     * @param casoId caso a buscar
     * @param usuario Usuario que consulta
     * @return Lista de expediente con al menos un evento para el periodo histórico
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarExpedientesConEventosHistorico(Long casoId,UsuarioDTO usuario) throws NSJPNegocioException;
    
  
    /**
     * 
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarNumeroExpedienteHistorico (UsuarioDTO usuario) throws NSJPNegocioException;
    
    /**
     * 
     * @param tipoExpediente
     * @param estatusExpediente
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarNumeroExpedienteByEstatus (TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente) throws NSJPNegocioException;

    
    /**
	 * Metodo que permite registrar/actualizar las notas asociadas a un Expediente
	 * @param expedienteDTO.NumeroExpedienteId: Representa el numero del expediente a actualizar
	 * @param notas: Son las NotasExpedienteDTO que se desean guardar/actualizar.
	 * 	Si se manda el campo NotaExpedienteDTO.idNota entonces se acutualiza la nota, 
	 *  en caso contrario se crea la nota  
	 * @throws NSJPNegocioException
	 */
	public List<NotaExpedienteDTO> registrarActualizarNotasExpediente(List<NotaExpedienteDTO> notas,  ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
	
	/**
     * Permite consultar las notas asociadas a un expediente
	 * @param expedienteDTO.NumeroExpedienteId: Representa el numero del expediente 
     * @return List<NotaExpedienteDTO> 
     */
	public List<NotaExpedienteDTO> consultarNotasPorExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
	
	/**
     * Realiza algunas validaciones para saber si se debe de generar una nueva actividad en base de datos
     * y de esa forma queda canalizado el expediente:
     * @param inputExpediente: Atributos obligatorio
     * 		  - inputExpediente.ExpedienteId Representa el id del expediente que tendra la nueva actividad
     * 		  - inputExpediente.usuario.funcionario.claveFuncionario: Representa el usuario de sesion
     * @param actividadDTO
     * 		  - actividadDTO.tipoActividad : Representa el tipo de la actividad
     * 			* Actividades.CANALIZAR_JUSTICIA_ALTERNATIVA_RESTAURATIVA
     * 			* Actividades.CANALIZAR_UNIDAD_INVESTIGACION
     * @return
     * @throws NSJPNegocioException
     */
	public ActividadDTO canalizarCausa(ExpedienteDTO inputExpediente, ActividadDTO actividadDTO, Boolean bDelitoPrincipalGrave,
			Boolean bInputadoReincidente,
			Boolean bSeguimientoInterno) throws NSJPNegocioException;
	
	/**
	 * Función que realiza la inserción y registro de los elementos de un acta circunstanciada relacionados a un expediente dado
	 * @param actaDTO
	 * @param expedienteDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO registrarActaCircunstanciada (ActaCircunstanciadaDTO actaDTO, ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

	/**
	 * Consulta los expedientes relacionados al usuario de un area
	 * @author cesarAgustin
	 * @param usuarioDTO
	 * 			<li>idUsuario<li> identificador del usuario
	 * 			<li>areaActual<li> area del usuario 
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarExpedientesUsuarioArea (UsuarioDTO usuarioDTO) throws NSJPNegocioException;
	
	/**
	 * Consulta los expedientes de un área según los filtros dados. 
	 * 
	 * @param usuarioDTO: Identificador del usuario firmado y el área a que pertenece (Obligatorio)
	 * @param area: Identificador del área remitente
	 * @param estatusExpediente: Identificador del estado por el cual se filtran los expedientes
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarExpedientePorAreaEstatusRemitente (UsuarioDTO usuarioDTO,AreaDTO area, 
			Long estatusExpediente)throws NSJPNegocioException;

	/**
	 * Consulta la etapa en la que se encuentra el Expediente identificado por <code>idExpediente</code>
	 * @param numeroExpedienteId identificador del expediente del cual se quiere conocer la etapa.
	 * @return Etapa actual en la que se encuentra el expediente
	 * @throws NSJPNegocioException en caso de que el expediente no pueda cambiar de etapa
	 */
	public EtapasExpediente consultarEtapaDelExpediente(Long numeroExpedienteId) throws NSJPNegocioException;

	/**
	 * Cambia la etapa del expediente identificado por <code>numeroExpedienteId</code> por <code>etapa</code>
	 * @param numeroExpedienteId expediente al cual se desea cambiar la etapa
	 * @param etapa nueva etapa del expediente
	 * @throws NSJPNegocioException
	 */
	public void cambiarEtapaDelExpediente(Long numeroExpedienteId, EtapasExpediente etapa) throws NSJPNegocioException;


	/**
	 * Consulta números de expediente relacionados al caso de una solicitud
	 * @param solicitudId 
	 * @return Numeros de expediente encontrados
	 */
	List<ExpedienteDTO> consultarNumeroExpedientePorCasoDeSolicitud(Long solicitudId) throws NSJPNegocioException;
	/**
     * Asigna un numero de expediente a una solicitud
     * @param numeroExpedienteId Numero de expediente a asignar
     * @param solicitudId Solicitud a la cuál será asignado
     */
    void asignarNumeroExpedienteASolicitud(Long numeroExpedienteId,Long solicitudId) throws NSJPNegocioException;

    /**
     * Consulta los numerosExpediente del tipo Causa que tengan un numeroExpediente de tipo carpeta de investigacion 
     * en el estatus cerrado.
     * @author cesarAgustin
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarHistoricoCausasExpediente() throws NSJPNegocioException;
    
    /**
     * Consulta los numerosExpediente del tipo carpeta de ejecucion perteneciente a la causa enviada oomo parametro
     * @author cesarAgustin
     * @param expedienteDTO
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarCarpetasEjecucionPorCausa (ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
    /**
	 * Metodo que permite consultar las causas asociadas a un caso
	 * @param idCaso
	 * @return List<ExpedienteDTO>
	 * @throws NSJPNegocioException
	 */
    List<ExpedienteDTO> consultarCausasPorIdCaso(Long idCaso) throws NSJPNegocioException;

	/**
	 * Permite actualizar el reponsable a un conjunto de numerosExpediente
	 * @param idNumeroExpediente
	 * @param idFuncionario
	 * @throws NSJPNegocioException
	 */
	public void asociarExpedienteAFuncionario(Long idNumeroExpediente, Long idFuncionario) throws NSJPNegocioException;
    /**
	 * Consulta numeros de expediente de cierto tipo (TOCA, CAUSA o CARPETA) en base a los filstros enviados como parametro
	 * si un filtro es nulo entonces no se considera en la cosulta
	 * @param fechaInicio Límite inferior para la fecha de apertura
	 * @param fechaFin Límite superior para la fecha de apertura
	 * @param usuario Usuario al cuál debe de pertenecer el expediente
	 * @param tipo Tipo de expediente
	 * @param numeroExpedientePadreId Si existe el parámetro se consultan los número de expedientes
	 * cuya causa padre sea la enviada en este parámetro
	 * @return Lista con los números de expediente encontrados
	 */
	List<ExpedienteDTO> consultarExpedientesPorFiltro(Date fechaInicio,Date fechaFin,FuncionarioDTO usuario,
			TipoExpediente tipo,Long numeroExpedientePadreId) throws NSJPNegocioException;
	
	
	/**
     * Consulta números de expediente asociados a cierto caso
     * @param caso
     * @return
     */
	List<ExpedienteDTO> consultarNumeroExpedientePorNumeroCaso(String caso) throws NSJPNegocioException;
	/**
	 * Operación que permite realizar la consulta de un Acta Circunstanciada
	 * @param expedienteDTO: idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	ActaCircunstanciadaDTO consultarActaCircunstaciada( ExpedienteDTO expedienteDTO)throws NSJPNegocioException;
	
	/**
     * Enviar Carpera de Procuraduria a Defensoria
     * 
     * Declaracion del servicio que invoca al WS para, de acuerdo a: 
     * numeroGeneralCaso. Para consultar los expedientes asociados al caso, en Procuraduria.
     * folioSolicitud: Folio de la solicitud a la cual se va a guardar la informacion del lado de Defensoria.
     * 
     * @param numeroGeneralCaso  Del caso asociado al expediente
     * @param folioSolicitud  De defensoria en donde se guradará la información solicitada del expediente de procuraduria
     * @return  expedienteDTO asociado a la consulta obtenida, incluyendo los involucrados y objetos del expediente 
     * @throws NSJPNegocioException
     */
    public ExpedienteDTO enviarCarpetaDeInvestigacion(
    		String numeroGeneralCaso, String folioSolicitud)
            throws NSJPNegocioException;
    
    
    /**
	 * Servicio que permite actualizar el estatus del NumeroExpediente.
	 * Se requiere el Id del Expediente y el Valor del Estatus. 
	 * 
	 * @param expedienteDTO
	 * @return 
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO actualizarEstatusExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

	/**
     * Servicio para consultar Nota por Id
     * 
     * @param idNota
     * @return
     * @throws NSJPNegocioException
     */
    NotaExpedienteDTO consultarNotaPorId(Long idNota)throws NSJPNegocioException ;
    
	/**
	 * Obtiene numerosExpediente de un determinado tipo que se encuntren en un estatus especifico
	 * @author cesarAguistin
	 * @param tipoExpediente
	 * @param estatusExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO> consultarNumeroExpedienteByTipoYEstatus (TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente) throws NSJPNegocioException;

	/**
     * Obtiene numerosExpediente que se encuntren en un estatus especifico
     * @author vaguirre
     * @param estatusExpediente
     * @return
     * @throws NSJPNegocioException
     * 
     * 
     * 
     */
    List<ExpedienteDTO> consultarNumeroExpedienteByEstatus (EstatusExpediente estatusExpediente) throws NSJPNegocioException;
    
    /**
     * Obtiene los numeros de expedientes por estatus que contienen un hecho DTO
     * y un avisoHechoDelictivo, con el discriminante del usuario que pasamos como parametro
     * @param estatusExpediente
     * @param usuario
     * @param fechaInicio
     * @param fechaFin
     * @return Lista de Expdientes que contienen un hechoDTO
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarNumeroDeExpedienteConHechoPorFiltros(EstatusExpediente estatusExpediente, UsuarioDTO usuario,Date fechaInicio,Date fechaFin) throws NSJPNegocioException;
    
    /**
     * Obtiene los numeros de expedientes por estatus que contienen un hecho DTO 
     * y NO contienen un avisoHechoDelictivo, con el discriminante del usuario que pasamos como parametro
     * @param estatusExpediente
     * @param usuario
     * @param fechaInicio
     * @param fechaFin
     * @return Lista de Expdientes que contienen un hechoDTO
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarNumeroDeExpedienteSinHechoPorFiltros(EstatusExpediente estatusExpediente, UsuarioDTO usuario,Date fechaInicio,Date fechaFin) throws NSJPNegocioException;
    
	/**
	 * Obtiene la informacion a detalle de la carpeta de ejecucion
	 * @author cesarAgustin
	 * @param expedienteDTO
	 * 			<li>numeroExpedienteId<li>
	 * @return Carpeta ejecucion obtenida
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO obtenerDetalleCarpetaEjecucion (ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

	/**
	 * Obtiene los total de expedientes por mes de acuerdo al rango de fechas.
	 * @author cesarAgustin
	 * @param fechaInicial
	 * @param fechaFin
	 * @return
	 */
	List<Object[]> expedientesPorMes(Date fechaInicial, Date fechaFin);

	/**
	 * Obtiene el total por mes de los numeros de expediente que se encuentren en un estatus
	 * determinado y dentro de un rango de fechas.
	 * @author cesarAgustin
	 * @param inicial
	 * @param fin
	 * @param archivoTemporal
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<Object[]> obtenerNumExpPorEstatusYMes(Date inicial, Date fin,
			EstatusExpediente estatusExpediente) throws NSJPNegocioException;
	
    /**
     * Servicio que recupera el id del Expediente a partir del id del Número Expediente
     * @param expedienteDTO Requerido <b>numeroExpedienteId</b>.
     * @return expedienteId.
     * @throws NSJPNegocioException
     */
    Long obtenerExpedienteIdPorNumExpId(ExpedienteDTO expedienteDTO)
    throws NSJPNegocioException;
    
    /**
	 *	Servicio que realiza la consulta por Usuario (Funcionario), Área y Estatus
	 *	El área se encuentra denstro del usuarioDTo
	 * 	En caso de que no pase el estatus, se consulta de todos los expedientes por estatus.
	 * 
	 * @param usuarioDTO
	 * @param expedienteDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarExpedientesPorUsuarioAreaEstatus(
			UsuarioDTO usuarioDTO, Long estatus) throws NSJPNegocioException ;
	

	/**
	 * Servicio para crear un nuevo de Auditoría, que permita asignar un nuevo número de expediente, número de auditoría, 
	 * considerando el registro en la relación: RelNumExpedienteAuditoria
	 * 
	 * @param listaNumeroExpedienteAuditados
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO>  asignarNumeroExpedienteAuditoria(List<ExpedienteDTO> listaNumeroExpedienteAuditados) throws NSJPNegocioException;
	
	/**
	 * Servicio que permite la consulta de Numeros de Visitaduria de acuerdo a:
	 * ** RelNumExpedienteAuditoria
	 *   -relNumExpedienteAuditoriaId  Id de la relación.
	 *   -numeroAuditoriaId   Id del nuevo número de expediente generado por la auditoria.
	 *   
	 * ** Numero Expediente Auditado (numExpedienteAuditado)
	 *   -numeroExpedienteId
	 *   -jerarquiaOrganizacional.jerarquiaOrganizacionalId
	 *   -funcionario.claveFuncionario
	 *   -estatus.valorId
	 *   
	 * ** Numero Carpeta de Auditoria 
	 *   -numeroExpedienteId
	 *   --jerarquiaOrganizacional.jerarquiaOrganizacionalId
	 *   -funcionario.claveFuncionario
	 *   -estatus.valorId
	 *  
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<RelNumExpedienteAuditoriaDTO> consultarNumeroVisitaduriaPorFiltro(RelNumExpedienteAuditoriaDTO filtro )  throws NSJPNegocioException;

	ExpedienteDTO obtenerExpedienteDefensoria(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException;
	/**
	 * Permite consultar RelNumExpedienteAuditoria en base a un numero de auditoria
	 * @param idAuditoria: Identificador del numero de auditoria (Numero de expediente)
	 * @return RelNumExpedienteAuditoria
	 * @throws NSJPNegocioException
	 */
	RelNumExpedienteAuditoriaDTO consultarRelacionPorIdAuditoria(Long idAuditoria) throws NSJPNegocioException;

	/**
	 * Servicio que permite consultar los funcionarios, visitadores, por
	 * -Departamento del expediente Auditado 
	 * -Estatus de la relación
	 *  
	 * @param idEstatus
	 * @param idDepartamento
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<FuncionarioDTO> consultarFuncionariosPorEstatusDeparamento(Long idEstatus, Long idDepartamento) 
		throws NSJPNegocioException;
	
	/**
	 * Obtiene la informacion del expediente si el funcionario tiene permisos sobre el numeroExpediente.
	 * @author cesarAgustin
	 * @param claveFuncionario
	 * @param numExpId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO consultarNumExpPorFuncionario(Long claveFuncionario,
			Long numExpId) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param claveFuncionario
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarNumExpPorFuncionario(
			Long claveFuncionario) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param claveFuncionario
	 * @param numExpId
	 * @throws NSJPNegocioException
	 */
	public void asignarPermisoExpedienteFuncionario(Long claveFuncionario, Long numExpId, Date fechaLimite, Boolean permiso) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param claveFuncionario
	 * @param numExpId
	 * @throws NSJPNegocioException
	 */
	public void eliminarPermisoExpedienteFuncionario(Long claveFuncionario, Long numExpId) throws NSJPNegocioException;

	/**
	 *  Se hace la busqueda del Expediente al que se encuentra asociado, 
	 *  despues se hace la busqueda por el expedienteId y el Area del o los 
	 *  expedientes bajo ese criterio. Si se obtienen varios NEx por dicho filtro
	 *  se considera solo el primero.  
	 * 
	 * @param numeroExpediente
	 * @param areaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO consultarExpedienteRelacionadoAArea (String numeroExpediente, Long areaId  ) throws NSJPNegocioException ;
	/**
	 * Servicio que permite crear un nuevo Expediente, asi mismo copiara la información del:
	 * - Probable Responsable
	 * - Victima
	 * - Delito 
	 * descritos en delitoPersonaDTO
	 * @param delitoPersonaDTO
	 * @return ExpedienteDTO
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO generarNuevoExpedienteUAVD(DelitoPersonaDTO delitoPersonaDTO) throws NSJPNegocioException ;
	
	
    /**
     * Consulta el resumen con los datos generales de un expediente en base al identificador de la solicitud.
     * 
     * @param solicitudDTO
     *            Identificador de la solicitud
     * @return DatosGeneralesExpedienteUAVDDTO con el Resumen del expediente
     * @throws NSJPNegocioException
     *             En caso de ocurrir algún error de negocio al consultar.
     */    
    public DatosGeneralesExpedienteUAVDDTO obtenerResumenDeExpedienteUAVD(SolicitudDTO solicitudDTO)
            		throws NSJPNegocioException;
    
    
    /**
     * Metodo que permite crear un nuevo numero de expediente, expediente y su caso
     * @param expedienteDTO
     * @return ExpedienteDTO
     * @throws NSJPNegocioException
     */
    public ExpedienteDTO generarExpediente(ExpedienteDTO expedienteDTO)
		throws NSJPNegocioException;

    /**
	 * Servicio utilizado para obtener los expedientes recibidos de IPH, es decir, 
	 * remision IPH. 
	 * 
	 * @param estatusExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO> buscarRemisionesConIPH(EstatusExpediente estatusExpediente)throws NSJPNegocioException;
	
	 /**
		 * Servicio utilizado para obtener los expedientes recibidos de IPH de cada coordinador, es decir, 
		 * remision IPH. 
		 * @author Gustavo
		 * @param estatusExpediente
		 * @return
		 * @throws NSJPNegocioException
		 */
	List<ExpedienteDTO> buscarRemisionesConIPH(EstatusExpediente estatusExpediente,Long CatUIED)throws NSJPNegocioException;
	
	
    /**
     * Consulta el detalle de un expediente en base a su identificador.
     * 
     * @param expedienteDTO
     *            Identificador del expediente en el sistema e identificador del
     *            area: <b>expedienteId</b> y <b>area.areaId</b> <br>
     * @return El expediente.
     * @throws NSJPNegocioException
     *             En caso de ocurrir algún error de negocio al consultar.
     */
    ExpedienteDTO obtenerExpedientePorExpedienteId(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;
    
   
    /**
     * Consulta los expedientes de acuerdo a los filtros,
     * si las fechas de los filtros es null, no se tomará en cuenta,
     * los demas parametros son requeridos.
     * 
     * @param fechaInicio
     * @param fechaFin
     * @param usuario
     * @param estatusExpediente
	 * @param idFuncionario Permite filtrar los Numeros de expedientes por responsable
     * idDistrito
     * @return
     * @throws NSJPNegocioException
     */
	List<ExpedienteDTO> consultarExpedientesPorFiltroST(Date fechaInicio,
			Date fechaFin, UsuarioDTO usuario,
			List<Long> estatusExpediente, Long idDiscriminante , Long rolId, Long idDistrito, Long idFuncionario) throws NSJPNegocioException;
	
	/**
	 * Servicio que consulta en que area se ha iniciado un expediente
	 * @param expediente: expedienteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public JerarquiaOrganizacionalDTO consultarOrigendeExpediente (ExpedienteDTO expediente)throws NSJPNegocioException;
	
	/**
	 * Permite actualizar el discriminante de un expediente
	 * @param expediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Boolean actualizarCatDiscriminanteDeExpediente (ExpedienteDTO expedienteDto) throws NSJPNegocioException;

	/**
	 * @param relNumExpedienteAuditoriaDTO
	 * 		*relNumExpedienteAuditoriaDTO.numeroExpediente.Area area de la cual se requieren los num expedientes 
	 * 		*relNumExpedienteAuditoriaDTO.numeroExpediente.Estatus estatus en el cual deben estar los num expedientes
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<RelNumExpedienteAuditoriaDTO> consultarNumeroVisitaduriaUIE(
			RelNumExpedienteAuditoriaDTO relNumExpedienteAuditoriaDTO) throws NSJPNegocioException;
	

	/**
	 * Permite consultar numeros de expedientes asociados a un identificador de expediente
	 * que tengan como responsable a un usuario que cuente con el rol asociado.
	 * @param roles
	 * @param idExpediente
	 * @param nuevoEstatusNumeroExpediente
	 * @param nuevoEstatusExpediente
	 * @throws NSJPNegocioException
	 */
	public void actualizarEstatusNumerosDeExpedientesPorRolST(
			List<Long> roles, Long idExpediente,Long nuevoEstatusNumeroExpediente , Long nuevoEstatusExpediente) throws NSJPNegocioException;
	
	/**
	 * Perimite consultar el estatus del numero de expediente por numero expediente Id
	 * @param numeroExpedienteId
	 * @return Estatus Expediente
	 * @throws NSJPNegocioException
	 */
	public Long consultarEstatusNumeroExpedienteByNumeroExpedienteId(Long numeroExpedienteId) throws NSJPNegocioException;

	/**
	 * M&eacute;todo que reasigna un Funcionario a una lista de N&uacute;meros De Expedientes
	 * 
	 * @param lstExpedienteDTO N&uacute;mero del Expediente y clave del nuevo Funcionario responsable.  
	 * @return VERDADERO si realizo la reasignaci&oacute;n de lo contrario regresa FALSO.
	 * @throws NSJPNegocioException
	 */
	
	Boolean reasignarFuncionarioDeNumeroExpediente(List<ExpedienteDTO> lstExpedienteDTO, HistoricoExpedienteDTO historicoExpedienteDTO)
			throws NSJPNegocioException;
	
	/**
	 * Permite obtener un expediente en base al numero de Expediente y al numero de caso
	 * @param numeroExpediente
	 * @param numCaso
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO obtenerExpedientePorNumeroExpedienteYNumeroCaso(
			String numeroExpediente, String numCaso) throws NSJPNegocioException;
	/**
	 * Permite obtener una lista con los numeros de expedientes que pertenecen al expediente
	 * @param idExpediente de tipo ExpedienteDTO
	* @return List<String>
	 * @throws NSJPNegocioException
	 */
	public List<String> buscarNumerosExpedientesByIdExpediente(ExpedienteDTO idExpediente)throws NSJPNegocioException;
	
	
	/**
     * Consulta dedicada para el coordinadorAT en la cual se basa mediante los filtros de su menu.
     * @param filtroExpedienteDTO
     * @return List<ExpedienteDTO>
     * @throws NSJPNegocioException
     */
	public List<ExpedienteDTO> consultarExpedienteCoorAT(FiltroExpedienteDTO filtroExpedienteDTO)throws NSJPNegocioException ;
	
	/**
     * Asigna un numero de caso.<br>
     * 
     * @param expedienteDTO
     *            Obligatorio la <b>idExpediente</b> y <b>NumeroGeneralCaso</b>.
     * @return expedienteDTO
     * @throws NSJPNegocioException
     *             En caso de ocurrir algún error.
     */
    public ExpedienteDTO asignarNumeroCasoSolicitudDefensor(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;

	/**
	 *  Se hace la busqueda de Expedientes por Unidad de Investigacion y Por At Penal
	 *  para el Facilitador en Justicia Restaurativa.
	 * 
	 * @param numeroExpediente
	 * @author Dorian
	 * @param areaId
	 * @return
	 * @throws NSJPNegocioException
	 */
    public List<ExpedienteDTO> consultarExpedientesJusticiaRestaurativaPorArea (Long areaId, Long claveFuncionario  ) throws NSJPNegocioException;
    
    /**
     * Consulta los expedientes de un caso para pintar el árbol de
     * casos-expedientes.
     * @author Gustavo 
     * @param caso
     *            caso.<b>casoId</b> es requerido.
     * @return Una lista con los siguientes valores de expedientes asignados:
     *         <ul>
     *         <li>expedienteId</il>
     *         <li>numeroExpediente</il>
     *         </ul>
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarExpedientesPorCasoEna(CasoDTO caso)
            throws NSJPNegocioException;
    
 
    ExpedienteDTO relacion(ExpedienteDTO expediente)
            throws NSJPNegocioException;
    
    
    
}
