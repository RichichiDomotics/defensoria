
package mx.gob.segob.nsjp.web.evento.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento;
import mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusMedida;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusTurno;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoTurno;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.delito.DelitoDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.documento.MandamientoJudicialDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.expediente.TurnoDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.medidasalternas.MedidasAlternasDelegate;
import mx.gob.segob.nsjp.delegate.medidascautelares.MedidasCautelaresDelegate;
import mx.gob.segob.nsjp.delegate.sentencia.SentenciaDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.FiltroAudienciaDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioExtranjeroDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.TurnoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaAlternaDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudTranscripcionAudienciaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.documento.form.AdjuntarDocumentoAMandamientoJudicialForm;
import mx.gob.segob.nsjp.web.evento.form.MedidaAlternaForm;
import mx.gob.segob.nsjp.web.evento.form.MedidaCautelarForm;
import mx.gob.segob.nsjp.web.solicitud.form.VisorSolicitudAudienciaForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cuauhtemoc Paredes Serrano
 *
 */
public class ConsultaEventosPJENCAction extends GenericAction{

	/**Log de clase*/
	private static final Logger log  = Logger.getLogger(ConsultaEventosPJENCAction.class);

	@Autowired
	public AudienciaDelegate audienciaDelegate;

	@Autowired
	public InvolucradoDelegate involucradoDelegate;

	@Autowired
	public FuncionarioDelegate funcionarioDelegate;

	@Autowired
	public SolicitudDelegate solicitudDelegate;

	@Autowired
	public MedidasCautelaresDelegate medidasCaulelaresDelegate;

	@Autowired
	public ExpedienteDelegate expedienteDelegate;
	@Autowired
	private DelitoDelegate delitoDelegate;
	@Autowired
	private MandamientoJudicialDelegate mandamientoJudicialDelegate;
	@Autowired
	private CatalogoDelegate catalogoDelegate;
	@Autowired
	private MedidasAlternasDelegate medidasAlternasDelegate;
	@Autowired
	private TurnoDelegate turnoDelegate;
	@Autowired
	private SentenciaDelegate sentenciaDelegate;
	@Autowired
	private DocumentoDelegate documentoDelegate;


	@SuppressWarnings("unused")
	private final static String KEY_SESSION_EVENTO = "KEY_SESSION_EVENTO_DTO";

	/**
	 * Metodo utilizado para la consulta de audiencias del dia.
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarAudienciaPorJuicioOralPJENC(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR AUDIENCIAS DE TIPO DE JUICIO ORAL");

			FiltroAudienciaDTO filtro = new FiltroAudienciaDTO();
			filtro.setTipoAudiencia(TipoAudiencia.JUICIO_ORAL.getValorId());
			List<AudienciaDTO> listaDeAudiencias = audienciaDelegate.buscarAudiencias(filtro);

			if (log.isDebugEnabled()){
			    log.debug("SOLICITUD AUDIENCIA" + listaDeAudiencias);
			}
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			int lTotalRegistros = listaDeAudiencias.size();

			writer.print("<records>" + lTotalRegistros + "</records>");
			for (AudienciaDTO audienciaDTO : listaDeAudiencias) {
			    if (log.isDebugEnabled()){
			        log.debug("SOLICITUD AUDIENCIA" + audienciaDTO);
			    }
				writer.print("<row id='"+ audienciaDTO.getId() + "'>");
				writer.print("<cell>"+ audienciaDTO.getExpediente().getNumeroExpediente() +  "</cell>");
				writer.print("<cell>"+ audienciaDTO.getCaracter() + "</cell>");
				writer.print("<cell>"+ audienciaDTO.getTipoAudiencia().getValor()+ "</cell>");
				String fechaSolicitud=DateUtils.formatear(audienciaDTO.getFechaEvento());
				writer.print("<cell>"+ fechaSolicitud + "</cell>");
				String horaSolicitud=DateUtils.formatearHora(audienciaDTO.getFechaEvento());
				writer.print("<cell>"+ horaSolicitud + "</cell>");
				writer.print("<cell>"+ audienciaDTO.getSala().getUbicacionSala()+ "</cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}

	/**
	 * Metodo utilizado para realizar el registro de objetos
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward registrarObjetosPJENC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action registar Objeto para encargado causa");

			Long numRenglones = Long.parseLong(request.getParameter("numRenglones")) ;

			log.info("NUM DE RENGLONES"+ numRenglones);

			Integer i=1;

			while(i<=numRenglones){

				String descripcion = request.getParameter("descripcion"+i);
				log.info("DESCRIPCION"+ descripcion);
				String noCustodia = request.getParameter("noCustodia"+i);
				log.info("NO CUSTODIA"+ noCustodia);
				Long noPrueba =  Long.parseLong(request.getParameter("noPrueba"+i)) ;
				log.info("NO DE PRUEBA"+ noPrueba);
				Long audienciaId = Long.parseLong(request.getParameter("idEvento")) ;
				log.info("ID DE AUDIENCIA"+ audienciaId);
				Long institucion =  Long.parseLong(request.getParameter("institucion"+i)) ;
				log.info("INSTITUCION"+ institucion);
				Long edoFisico =  Long.parseLong(request.getParameter("estadoFisico"+i)) ;
				log.info("ESTADO FISICO"+ edoFisico);

				audienciaDelegate.registrarObjetoEnAudiencia(audienciaId, institucion, descripcion, edoFisico, noCustodia, noPrueba);

				i++;

			}



		} catch (Exception e) {
			log.info(e.getCause(),e);

		}
		return mapping.findForward("succes");
	}

	/**
	 * Metodo utilizado para realizar el paso del parametro id del evento
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward visorAudienciaPJENC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action visor audiencia PJENC");

			String idEvento = request.getParameter("idEvento");

			log.info("id evento:::"+ idEvento);


		} catch (Exception e) {
			log.info(e.getCause(),e);

		}
		return mapping.findForward("succes");
	}


	/**
	 * Metodo utilizado para registrar Testigos
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward registrarTestigosPJENC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action registar Testigos para encargado causa");

			Long numRenglones = Long.parseLong(request.getParameter("numRenglones")) ;

			log.info("NUM DE RENGLONES"+ numRenglones);

			String ids = request.getParameter("ids") ;

			log.info("Ids"+ ids);

			String[] renglones = ids.split(",");

			List<NombreDemograficoDTO> nombresDemograficos = new ArrayList<NombreDemograficoDTO>();

			NombreDemograficoDTO nombreDemograficoDTO = new NombreDemograficoDTO();
			InvolucradoDTO testigoDTO = new InvolucradoDTO();
			AudienciaDTO audienciaDTO = new AudienciaDTO();
			DomicilioDTO domicilioDTO = new DomicilioDTO();

			int e=0;
			String cont = null;

			for(e=0;e<renglones.length;e++){

			cont = renglones[e];

				//datos simples
				Long audienciaId = Long.parseLong(request.getParameter("idEvento")) ;
				String nombre = request.getParameter("nombreTes"+ cont);
				String aPaterno = request.getParameter("apPatTes"+cont);
				String aMaterno =  request.getParameter("apMatTes"+cont) ;
				Long institucion =  Long.parseLong(request.getParameter("institucionTes"+cont)) ;

				log.info("ID DE AUDIENCIA"+ audienciaId);
				log.info("INSTITUCION"+ institucion);
				log.info("NOMBRE"+ nombre);
				log.info("A PATERNO"+ aPaterno);
				log.info("A MATERNO"+ aMaterno);

				//Seteamos el nombre, apPat y apMat
				nombreDemograficoDTO.setNombre(nombre);
				nombreDemograficoDTO.setApellidoPaterno(aPaterno);
				nombreDemograficoDTO.setApellidoMaterno(aMaterno);

				//Agregamos el objeto nombreDemografico a la lista
				nombresDemograficos.add(nombreDemograficoDTO);

				//Seteamos la lista de nombres al involucrado
				testigoDTO.setNombresDemograficoDTO(nombresDemograficos);

				//Seteamos el id de la audiencia
				audienciaDTO.setId(audienciaId);

				//domicilio
				String pais = request.getParameter("pais"+cont);
				String codigoPostal = request.getParameter("codigoPostal"+cont);
				String entidadFederativa = request.getParameter("entidadFederativa"+cont);
				String ciudad = request.getParameter("ciudad"+cont);
				String delegacionMunicipio = request.getParameter("delegacionMunicipio"+cont);
				String asentamientoColonia = request.getParameter("asentamientoColonia"+cont);
				String tipoAsentamiento = request.getParameter("tipoAsentamiento"+cont);
				String tipoCalle = request.getParameter("tipoCalle"+cont);
				String calle = request.getParameter("calle"+cont);
				String numExterior = request.getParameter("numExterior"+cont);
				String numInterior = request.getParameter("numInterior"+cont);
				String referencias = request.getParameter("referencias"+cont);
				String entreCalle = request.getParameter("entreCalle"+cont);
				String ycalle = request.getParameter("ycalle"+cont);
				String aliasDomicilio = request.getParameter("aliasDomicilio"+cont);
				String edificio = request.getParameter("edificio"+cont);
				String longitud = request.getParameter("longitud"+cont);
				String latitud = request.getParameter("latitud"+cont);

				log.info("PAIS="+pais);
				log.info("CP="+codigoPostal);
				log.info("ENTIDAD FEDERATIVA="+entidadFederativa);
				log.info("CIUDAD="+ciudad);
				log.info("DELEGACION-MUNICIPIO="+delegacionMunicipio);
				log.info("ASENTAMIENTO COLONIA="+asentamientoColonia);
				log.info("TIPO ASENTAMIENTO="+tipoAsentamiento);
				log.info("TIPO CALLE="+tipoCalle);
				log.info("CALLE="+calle);
				log.info("NUMERO EXTERIOR="+numExterior);
				log.info("NUMERO INTERIOR="+numInterior);
				log.info("REFERENCIAS="+referencias);
				log.info("ENTRE CALLE="+entreCalle);
				log.info("Y CALLE="+ycalle);
				log.info("ALIAS DOMICILIO="+aliasDomicilio);
				log.info("EDIFICIO="+edificio);
				log.info("LONGITUD="+longitud);
				log.info("LATITUD="+latitud);





			//CUANDO EL PAIS ES MEXICO
			if((Long.parseLong(pais)==10 || pais.equalsIgnoreCase("mexico") || pais.equalsIgnoreCase("méxico")) && (Long.parseLong(pais)!= -1L)){

				//parte izquierda de la pantalla ingresar domicilio
					//entidad federativa
				if(!entidadFederativa.equalsIgnoreCase("")){

					if(Long.parseLong(entidadFederativa)!= -1L ){
						EntidadFederativaDTO entidadDTO = new EntidadFederativaDTO();
						entidadDTO.setEntidadFederativaId(Long.parseLong(entidadFederativa));
						domicilioDTO.setEntidadDTO(entidadDTO);
					}
				}

					//ciudad
				if(!ciudad.equalsIgnoreCase("")){

					if(Long.parseLong(ciudad)!= -1L ){
						CiudadDTO ciudadDTO = new CiudadDTO();
						ciudadDTO.setCiudadId(Long.parseLong(ciudad));
						domicilioDTO.setCiudadDTO(ciudadDTO);
					}
				}
					//delegacion-municipio
				if(!delegacionMunicipio.equalsIgnoreCase("")){

					if(Long.parseLong(delegacionMunicipio)!= -1L ){
						MunicipioDTO municipioDTO = new MunicipioDTO();
						municipioDTO.setMunicipioId(Long.parseLong(delegacionMunicipio));
						domicilioDTO.setMunicipioDTO(municipioDTO);
					}
				}

					//asentamiento-colonia
				if(!asentamientoColonia.equalsIgnoreCase("")){

					if(Long.parseLong(asentamientoColonia)!= -1L ){
						AsentamientoDTO asentamientoDTO = new AsentamientoDTO();
						asentamientoDTO.setAsentamientoId(Long.parseLong(asentamientoColonia));
						domicilioDTO.setAsentamientoDTO(asentamientoDTO);
					}
				}

					//tipo de calle
				if(!tipoCalle.equalsIgnoreCase("")){

					if(Long.parseLong(tipoCalle) != -1){

						ValorDTO valorCalleId = new ValorDTO(Long.parseLong(tipoCalle));
						domicilioDTO.setValorCalleId(valorCalleId);
					}
				}

				//parte derecha de la pantalla ingresar domicilio
				domicilioDTO.setCalle(calle);
				domicilioDTO.setNumeroExterior(numExterior);
				domicilioDTO.setNumeroInterior(numInterior);
				domicilioDTO.setEntreCalle1(entreCalle);
				domicilioDTO.setEntreCalle2(ycalle);
				domicilioDTO.setAlias(aliasDomicilio);
				domicilioDTO.setEdificio(edificio);
				domicilioDTO.setReferencias(referencias);

				if(!longitud.equalsIgnoreCase("")){
					domicilioDTO.setLongitud(longitud);
				}
				else{
					domicilioDTO.setLongitud(null);
				}
				if(latitud != null && !latitud.isEmpty()){
					domicilioDTO.setLatitud(latitud);
				}
				else{
					domicilioDTO.setLatitud(null);
				}

				//Seteamos la calidad del domicilio
				CalidadDTO calidadDomicilioDTO = new CalidadDTO();
				calidadDomicilioDTO.setCalidades(Calidades.DOMICILIO);
				domicilioDTO.setCalidadDTO(calidadDomicilioDTO);
				domicilioDTO.setFechaCreacionElemento(new Date());


				//Seteamos el testigo con su domicilio
				testigoDTO.setDomicilio(domicilioDTO);

			}
			//CUANDO EL PAIS NO ES MEXICO
			else{

				DomicilioExtranjeroDTO domicilioExtranjeroDTO = new DomicilioExtranjeroDTO();

				//Parte izq de la pantalla ingresar domicilio
				if(!pais.equalsIgnoreCase("")){
					if(Long.parseLong(pais)!= -1L){

							//id del pais
						domicilioExtranjeroDTO.setPais(pais);
					}
				}

				domicilioExtranjeroDTO.setCodigoPostal(codigoPostal);
				domicilioExtranjeroDTO.setEstado(entidadFederativa);
				domicilioExtranjeroDTO.setCiudad(ciudad);
				domicilioExtranjeroDTO.setMunicipio(delegacionMunicipio);
				domicilioExtranjeroDTO.setAsentamientoExt(asentamientoColonia);

				//parte derecha de la pantalla ingresar domicilio
				domicilioExtranjeroDTO.setCalle(calle);
				domicilioExtranjeroDTO.setNumeroExterior(numExterior);
				domicilioExtranjeroDTO.setNumeroInterior(numInterior);
				domicilioExtranjeroDTO.setEntreCalle1(entreCalle);
				domicilioExtranjeroDTO.setEntreCalle2(ycalle);
				domicilioExtranjeroDTO.setAlias(aliasDomicilio);
				domicilioExtranjeroDTO.setEdificio(edificio);
				domicilioExtranjeroDTO.setReferencias(referencias);

				if(!longitud.equalsIgnoreCase("")){
					domicilioExtranjeroDTO.setLongitud(longitud);
				}
				else{
					domicilioExtranjeroDTO.setLongitud(null);
				}
				if(!latitud.equalsIgnoreCase("")){
					domicilioExtranjeroDTO.setLatitud(latitud);
				}
				else{
					domicilioExtranjeroDTO.setLatitud(null);
				}

				//Seteamos la calidad del domicilio

				CalidadDTO calidadDomicilioExtranjeroDTO = new CalidadDTO();
				calidadDomicilioExtranjeroDTO.setCalidades(Calidades.DOMICILIO);
				domicilioExtranjeroDTO.setCalidadDTO(calidadDomicilioExtranjeroDTO);
				domicilioExtranjeroDTO.setFechaCreacionElemento(new Date());

				//Seteamos el domicilio extranjero de notificaciones a la persona
				testigoDTO.setDomicilio(domicilioExtranjeroDTO);
		}

				audienciaDelegate.registrarTestigoEnAudiencia(audienciaDTO, testigoDTO);

			}


		} catch (Exception e) {
			log.info(e.getCause(),e);

		}
		return mapping.findForward("succes");
	}

	/**
	 * Metodo utilizado para realizar la consulta de solicitudes de transcripciones de audiencia NO atendidas
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarSolicitudesDeTranscripcionDeAudienciaPJENC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO ACTION ----consultarSolicitudesDeTranscripcionDeAudienciaPJENC");

			//List<SolicitudDTO> listaSolicitudesTranscripcionAudiencia = solicitudDelegate.consultarSolicitudXEstatus(EstatusSolicitud.ABIERTA.getValorId(), getUsuarioFirmado(request),TiposSolicitudes.TRANSCRIPCION_AUDIENCIA.getValorId());
			List<SolicitudTranscripcionAudienciaDTO> listaSolicitudesTranscripcionAudiencia = solicitudDelegate.consultarSolicitudTranscripcion(getUsuarioFirmado(request));
			log.info("EJECUTANDO ACTION -----listaSolicitudesTranscripcionAudiencia -- "+ listaSolicitudesTranscripcionAudiencia.size());

			if(listaSolicitudesTranscripcionAudiencia != null){

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");

				int lTotalRegistros = listaSolicitudesTranscripcionAudiencia.size();

				writer.print("<records>" + lTotalRegistros + "</records>");

				for (SolicitudTranscripcionAudienciaDTO solicitudTranscripcionDTO : listaSolicitudesTranscripcionAudiencia) {


				    //Id del row
				    if(solicitudTranscripcionDTO.getAudiencia().getId() != null){
					log.info("ID DE LA SOLICITUD DE AUDIENCIA:"+solicitudTranscripcionDTO.getAudiencia().getId());
				    	writer.print("<row id='"+ solicitudTranscripcionDTO.getAudiencia().getId() + "'>");
					}
					else{
						writer.print("<row id='"+ 1 + "'>");
					}
				    //No de Causa
				    if(solicitudTranscripcionDTO.getAudiencia().getExpediente().getCausaPadre() != null){
				    	writer.print("<cell>"+ solicitudTranscripcionDTO.getAudiencia().getExpediente().getCausaPadre().getNumeroExpediente() +  "</cell>");
					}
					else{
						writer.print("<cell>"+ solicitudTranscripcionDTO.getAudiencia().getExpediente().getNumeroExpediente()+ "</cell>");
					}
				    //Fecha-hora solicitud
				    if(solicitudTranscripcionDTO.getFechaCreacion() != null){
						String fechaSolicitud=DateUtils.formatear(solicitudTranscripcionDTO.getFechaCreacion());
						String horaSolicitud=DateUtils.formatearHora(solicitudTranscripcionDTO.getFechaCreacion());
						writer.print("<cell>"+ fechaSolicitud+" "+ horaSolicitud + "</cell>");
					}
					else{
						writer.print("<cell>"+ "---"+ "</cell>");
					}
				    //Institucion
					if(solicitudTranscripcionDTO.getInstitucion() != null){
						writer.print("<cell>"+ solicitudTranscripcionDTO.getInstitucion().getNombreInst()+ "</cell>");
					}
					else{
						writer.print("<cell>"+ "---"+ "</cell>");
					}
					//Id de la audiencia
				    if(solicitudTranscripcionDTO.getAudiencia().getId() != null){
						writer.print("<cell>"+ solicitudTranscripcionDTO.getAudiencia().getId()+ "</cell>");
					}
					else{
						writer.print("<cell>"+ "---"+ "</cell>");
					}
					//Numero de toca
				    if(solicitudTranscripcionDTO.getAudiencia().getExpediente().getCausaPadre() == null){
				    	writer.print("<cell>"+ solicitudTranscripcionDTO.getAudiencia().getExpediente().getNumeroExpediente() +  "</cell>");
					}
					else{
						writer.print("<cell>"+"---"+ "</cell>");
					}

					writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
			}

		} catch (Exception e) {
			log.info(e.getCause(),e);

		}
		return mapping.findForward("succes");
	}


	/**
	 * Metodo utilizado para realizar la transcripcion de la audiencia,
	 * carga algunos datos ademas de la plantilla del documento
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward mostrarTranscripcionDeAudiencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action MOSTRAR TRANSCRIPCION DE AUDIENCIA");

			String idSolicitudTranscripcion = request.getParameter("idSolicitudTranscripcion");

			log.info("ID DE LA SOLICITUD DE TRANSCRIPCION:::"+ idSolicitudTranscripcion);

			if( !idSolicitudTranscripcion.equalsIgnoreCase("") && idSolicitudTranscripcion != null){

				SolicitudDTO solicitudTranscripcion = new SolicitudDTO();
				solicitudTranscripcion.setDocumentoId(Long.parseLong(idSolicitudTranscripcion));
				log.info("ANTES DEL DELEGATE------------ consultarAudienciaBySolicictudTranscripcionId");
				AudienciaDTO audiencia =  audienciaDelegate.consultarAudienciaBySolicictudTranscripcionId(solicitudTranscripcion);
				log.info("DESPUES DEL DELEGATE------------ consultarAudienciaBySolicictudTranscripcionId");
				if(audiencia != null){
					request.setAttribute("formaId",Formas.ACTA);
					request.setAttribute("estatusSolicitud",idSolicitudTranscripcion);
					request.setAttribute("transcripcionAudienciaDTO", audiencia);
				}
			}


		} catch (Exception e) {
			log.info(e.getCause(),e);

		}
		return mapping.findForward("succes");
	}

	public ActionForward consultaInvolucradosCausaPJENC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {
			String numeroCausa=request.getParameter("numeroCausa");


			ExpedienteDTO expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroCausa);
			request.getSession().setAttribute("idCausa", expedienteDTO.getNumeroExpedienteId());
			request.getSession().setAttribute("numeroCausa", numeroCausa);
			List<InvolucradoDTO> listaInvolucradosCausa = expedienteDTO.getInvolucradosDTO();

			if(listaInvolucradosCausa != null && !listaInvolucradosCausa.isEmpty()){

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");

				int lTotalRegistros = listaInvolucradosCausa.size();

				writer.print("<records>" + lTotalRegistros + "</records>");

				for (InvolucradoDTO involucradoDTO : listaInvolucradosCausa) {
				    if(involucradoDTO.getCalidadDTO().getValorIdCalidad().getIdCampo().equals(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId())){
						if(involucradoDTO.getMedidasCautelaresDTO() != null && !involucradoDTO.getMedidasCautelaresDTO().isEmpty()){
							List<MedidaCautelarDTO> listMedidas=involucradoDTO.getMedidasCautelaresDTO();
							for(MedidaCautelarDTO medidaCautelarDTO : listMedidas){
							    //Id del row
							    if(involucradoDTO.getElementoId() != null){
							    	writer.print("<row id='"+ involucradoDTO.getElementoId() +","+medidaCautelarDTO.getDocumentoId()+"'>");
								}
								else{
									writer.print("<row id='"+ 1 + ",0'>");
								}
							    //Nombre
							    if(involucradoDTO.getNombreCompleto() != null){
							    	writer.print("<cell>"+ involucradoDTO.getNombreCompleto() +  "</cell>");
								}
								else{
									writer.print("<cell>"+ "---"+ "</cell>");
								}
							    //Medida Cautelar
							    if(medidaCautelarDTO.getValorTipoMedida().getValor() != null){
							    	writer.print("<cell>"+ medidaCautelarDTO.getValorTipoMedida().getValor() +  "</cell>");
								}
								else{
									writer.print("<cell>"+ "---"+ "</cell>");
								}
							    //Descripcion de la Medida
							    if(medidaCautelarDTO.getValorTipoMedida().getNombreCampo() != null){
							    	writer.print("<cell>"+ medidaCautelarDTO.getValorTipoMedida().getNombreCampo() +  "</cell>");
								}
								else{
									writer.print("<cell>"+ "---"+ "</cell>");
								}
							    //Periodo de Aplicacion
							    if(medidaCautelarDTO.getStrFechaInicio() != null && medidaCautelarDTO.getStrFechaFin() != null){
							    	writer.print("<cell>"+ medidaCautelarDTO.getStrFechaInicio() +" a "+medidaCautelarDTO.getStrFechaFin()+"</cell>");
								}
								else{
									writer.print("<cell>"+ "---"+ "</cell>");
								}
							    //Periodicidad
							    if(medidaCautelarDTO.getValorPeriodicidad() != null){
							    	writer.print("<cell>"+ medidaCautelarDTO.getValorPeriodicidad().getValor() +  "</cell>");
								}
								else{
									writer.print("<cell>"+ "---"+ "</cell>");
								}
							    //Encargado de Seguimiento
							    if(medidaCautelarDTO.getSeguimiento() != null){
							    	writer.print("<cell>"+ medidaCautelarDTO.getSeguimiento() +  "</cell>");
								}
								else{
									writer.print("<cell>"+ "---"+ "</cell>");
								}

								writer.print("</row>");
							}
						}else{
						    //Id del row
						    if(involucradoDTO.getElementoId() != null){
							log.info("ID DE involucrado:"+involucradoDTO.getElementoId());
						    	writer.print("<row id='"+ involucradoDTO.getElementoId() +",0'>");
							}
							else{
								writer.print("<row id='"+ 1 + "'>");
							}
						    //Nombre
						    if(involucradoDTO.getNombreCompleto() != null){
						    	writer.print("<cell>"+ involucradoDTO.getNombreCompleto() +  "</cell>");
							}
							else{
								writer.print("<cell>"+ "---"+ "</cell>");
							}
						    //Medida Cautelar
							writer.print("<cell>"+ "---"+ "</cell>");
						    //Descripcion de la Medida
							writer.print("<cell>"+ "---"+ "</cell>");
						    //Periodo de Aplicacion
							writer.print("<cell>"+ "---"+ "</cell>");
						    //Periodicidad
							writer.print("<cell>"+ "---"+ "</cell>");
						    //Encargado de Seguimiento
							writer.print("<cell>"+ "---"+ "</cell>");

							writer.print("</row>");
						}
				    }

				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public ActionForward consultaMedidasCautelaresInvolucradoPJENC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {
			String numero=request.getParameter("rowid");
			Long idInvolucrado=null;
			Long idMedidaCautelar=null;
			String[] idsDatos = numero.split(",");
			if(idsDatos.length > 0){
				idInvolucrado=Long.parseLong(idsDatos[0]);
				if(idsDatos.length>1)
					idMedidaCautelar=Long.parseLong(idsDatos[1]);
			}
			if(idInvolucrado != null && idInvolucrado > 0){
				MedidaCautelarDTO medidaCautelar= medidasCaulelaresDelegate.obtenerDetalleMedidaCautelar(idMedidaCautelar, idInvolucrado);

				converter.alias("medidaCautelar", MedidaCautelarDTO.class);
				converter.alias("involucrado", InvolucradoDTO.class);
				converter.alias("nombresDemograficoDTO", NombreDemograficoDTO.class);

				String xml = converter.toXML(medidaCautelar);
				response.setContentType("text/xml");
				PrintWriter pw = response.getWriter();
				pw.print(xml);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Metodo utilizado para obtener el detalle del objeto medida alternativa
	 * de un expediente
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarMedidasAlternasInvolucradoPJADE(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {
			String numero=request.getParameter("rowid");

			Long idInvolucrado=null;
			Long idMedidaAlternativa=null;

			if(numero != null){
				String[] idsDatos = numero.split(",");


				if(idsDatos.length > 0){
					idInvolucrado=Long.parseLong(idsDatos[0]);
					if(idsDatos.length>1)
						idMedidaAlternativa=Long.parseLong(idsDatos[1]);
				}


				if(idInvolucrado != null && idInvolucrado > 0){
					MedidaAlternaDTO medidaAlterna= medidasAlternasDelegate.obtenerDetalleMedidaAlterna(idMedidaAlternativa, idInvolucrado);

					converter.alias("medidaAlterna", MedidaAlternaDTO.class);
					converter.alias("involucrado", InvolucradoDTO.class);
					converter.alias("nombresDemograficoDTO", NombreDemograficoDTO.class);

					String xml = converter.toXML(medidaAlterna);
					response.setContentType("text/xml");
					PrintWriter pw = response.getWriter();
					pw.print(xml);
					pw.flush();
					pw.close();
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Metodo utilizado para obtener a los probables responsables
	 * de un expediente
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarImputadosPorNumExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {

			String numeroCausa = request.getParameter("numeroExpediente");

			log.info("EJECUTANDO ACTION CONSULTAR IMPUTADOS POR NUM DE EXPEDIENTE");
			log.info("**************VERIFICANDO PARAMETROS***********************");
			log.info("numeroCausa="+numeroCausa);

			if (numeroCausa != null && !numeroCausa.equalsIgnoreCase("")) {

				Calidades[] calidades = new Calidades[] {
						Calidades.PROBABLE_RESPONSABLE_PERSONA,
						Calidades.PROBABLE_RESPONSABLE_ORGANIZACION,
						};

				List<InvolucradoDTO> listaInvolucradosCausa = involucradoDelegate
				.obtenerInvolucradosPorExpedienteYCalidades(numeroCausa,calidades, null, null);

			    //Inicia la iteracion sobre Almacenes
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");
				final PaginacionDTO pag = PaginacionThreadHolder.get();
	            if (pag != null && pag.getTotalRegistros() != null) {
	                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
	                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
	            } else {
	                writer.print("<total>0</total>");
	                writer.print("<records>0</records>");
	            }


				for (InvolucradoDTO involucradoDTO : listaInvolucradosCausa) {
						writer.print("<row id='"+ involucradoDTO.getElementoId()+"'>");
						log.info("involucradoDTO.getNombreCompleto()=.............."+involucradoDTO.getNombreCompleto());

							if(involucradoDTO.getNombreCompleto()!=null){
								writer.print("<cell>" +involucradoDTO.getNombreCompleto()+ "</cell>");
							}
							else{
								writer.print("<cell>" +"---"+ "</cell>");
							}
				    	writer.print("</row>");


				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}



	public ActionForward consultaMedidaCautelarInvolucradoPJENC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {
			log.info("EJECUTANDO CONSULTAR MEDIDAS CAULTELARES DEL INVOLUCRADO");

			String numeroCausa=request.getParameter("numeroCausa");
			String idInvolucrado=request.getParameter("idInvolucrado");

			log.info("NUMERO DE CAUSA: "+numeroCausa);
			log.info("ID DEL INVOLUCRADO: "+idInvolucrado);

				if(idInvolucrado != null && idInvolucrado != "" && numeroCausa != null && numeroCausa !=""){

					ExpedienteDTO expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroCausa);

					List<InvolucradoDTO> listaInvolucrados = expedienteDTO.getInvolucradosDTO();

					if(listaInvolucrados != null && !listaInvolucrados.isEmpty()){

						int lTotalRegistros =0;

						response.setContentType("text/xml; charset=UTF-8");
						response.setHeader("Cache-Control", "no-cache");
						PrintWriter writer = response.getWriter();

						writer.print("<rows>");

						for(InvolucradoDTO involucrado: listaInvolucrados){
							if(involucrado.getElementoId() == Long.parseLong(idInvolucrado)){
								if(involucrado.getCalidadDTO().getValorIdCalidad().getIdCampo().equals(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId())){
									if(involucrado.getMedidasCautelaresDTO() != null && !involucrado.getMedidasCautelaresDTO().isEmpty()){

										List<MedidaCautelarDTO> listMedidas=involucrado.getMedidasCautelaresDTO();
										lTotalRegistros = listMedidas.size();
									}
								}
							}
						}

						writer.print("<records>" + lTotalRegistros + "</records>");

						for (InvolucradoDTO involucradoDTO : listaInvolucrados) {

							if(involucradoDTO.getElementoId() == Long.parseLong(idInvolucrado)){

							    if(involucradoDTO.getCalidadDTO().getValorIdCalidad().getIdCampo().equals(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId())){

									if(involucradoDTO.getMedidasCautelaresDTO() != null && !involucradoDTO.getMedidasCautelaresDTO().isEmpty()){

										List<MedidaCautelarDTO> listMedidas=involucradoDTO.getMedidasCautelaresDTO();

										for(MedidaCautelarDTO medidaCautelarDTO : listMedidas){
										    //Id del row
										    if(involucradoDTO.getElementoId() != null){
										    	writer.print("<row id='"+ involucradoDTO.getElementoId() +","+medidaCautelarDTO.getDocumentoId()+"'>");
											}
											else{
												writer.print("<row id='"+ 1 + ",0'>");
											}
										    //Nombre
										    if(involucradoDTO.getNombreCompleto() != null){
										    	writer.print("<cell>"+ involucradoDTO.getNombreCompleto() +  "</cell>");
											}
											else{
												writer.print("<cell>"+ "---"+ "</cell>");
											}
										    //Medida Cautelar
										    if(medidaCautelarDTO.getValorTipoMedida().getValor() != null){
										    	writer.print("<cell>"+ medidaCautelarDTO.getValorTipoMedida().getValor() +  "</cell>");
											}
											else{
												writer.print("<cell>"+ "---"+ "</cell>");
											}
										    //Descripcion de la Medida
										    if(medidaCautelarDTO.getValorTipoMedida().getNombreCampo() != null){
										    	writer.print("<cell>"+ medidaCautelarDTO.getValorTipoMedida().getNombreCampo() +  "</cell>");
											}
											else{
												writer.print("<cell>"+ "---"+ "</cell>");
											}
										    //Periodo de Aplicacion
										    if(medidaCautelarDTO.getFechaInicio() != null && medidaCautelarDTO.getFechaFin() != null){
										    	writer.print("<cell>"+ medidaCautelarDTO.getFechaInicio() +" a "+medidaCautelarDTO.getFechaFin()+"</cell>");
											}
											else{
												writer.print("<cell>"+ "---"+ "</cell>");
											}
										    //Periodicidad
										    if(medidaCautelarDTO.getValorPeriodicidad().getValor() != null){
										    	writer.print("<cell>"+ medidaCautelarDTO.getValorPeriodicidad().getValor() +  "</cell>");
											}
											else{
												writer.print("<cell>"+ "---"+ "</cell>");
											}
										    //Encargado de Seguimiento
										    if(medidaCautelarDTO.getSeguimiento() != null){
										    	writer.print("<cell>"+ medidaCautelarDTO.getSeguimiento() +  "</cell>");
											}
											else{
												writer.print("<cell>"+ "---"+ "</cell>");
											}

											writer.print("</row>");
										}
									}
									else{
									    //Id del row
									    if(involucradoDTO.getElementoId() != null){
										log.info("ID DE involucrado:"+involucradoDTO.getElementoId());
									    	writer.print("<row id='"+ involucradoDTO.getElementoId() +",0'>");
										}
										else{
											writer.print("<row id='"+ 1 + "'>");
										}
									    //Nombre
									    if(involucradoDTO.getNombreCompleto() != null){
									    	writer.print("<cell>"+ involucradoDTO.getNombreCompleto() +  "</cell>");
										}
										else{
											writer.print("<cell>"+ "---"+ "</cell>");
										}
									    //Medida Cautelar
										writer.print("<cell>"+ "---"+ "</cell>");
									    //Descripcion de la Medida
										writer.print("<cell>"+ "---"+ "</cell>");
									    //Periodo de Aplicacion
										writer.print("<cell>"+ "---"+ "</cell>");
									    //Periodicidad
										writer.print("<cell>"+ "---"+ "</cell>");
									    //Encargado de Seguimiento
										writer.print("<cell>"+ "---"+ "</cell>");

										writer.print("</row>");
									}
							    }
							}
						}
						writer.print("</rows>");
						writer.flush();
						writer.close();
					}

				}

			} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * Metodo utilizado para realizar el paso del parametros
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward enviaResolutivo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action envia resolutivo");

			String idResolutivo = request.getParameter("idResolutivo");
			log.info("LLEGA RESOLUTIVO:::"+ idResolutivo);
			request.setAttribute("idResolutivo",idResolutivo);

			String idAudiencia = request.getParameter("idAudiencia");
			log.info("LLEGA AUDIENCIA="+ idAudiencia);
			request.setAttribute("idAudiencia",idAudiencia);

			String formaId = request.getParameter("formaId");
			log.info("LLEGA FORMA="+formaId);
			request.setAttribute("formaId",formaId);

			String nombre = request.getParameter("nombre");
			log.info("LLEGA NOMBRE="+nombre);

			if(nombre!=null){

				String aPaterno = request.getParameter("aPaterno");
				log.info("LLEGA ="+aPaterno);

				String aMaterno = request.getParameter("aMaterno");
				log.info("LLEGA ="+aMaterno);

				String alias = request.getParameter("alias");
				log.info("LLEGA ="+alias);

				String delitos = request.getParameter("delitos");
				log.info("LLEGA ="+delitos);

				String motivoDetencion = request.getParameter("motivoDetencion");
				log.info("LLEGA ="+motivoDetencion);

				String nombreQuienAutoriza = request.getParameter("nombreQuienAutoriza");
				log.info("LLEGA ="+nombreQuienAutoriza);

				String puestoQuienAutoriza = request.getParameter("puestoQuienAutoriza");
				log.info("LLEGA ="+puestoQuienAutoriza);

				String funcionarioId = request.getParameter("funcionarioId");
				log.info("LLEGA ="+funcionarioId);

				ArrayList<NombreDemograficoDTO> nombresDemograficoDTO = new ArrayList<NombreDemograficoDTO>();
				NombreDemograficoDTO nombreDemograficoDTO = new NombreDemograficoDTO();
				nombreDemograficoDTO.setNombre(nombre);
				nombreDemograficoDTO.setApellidoPaterno(aPaterno);
				nombreDemograficoDTO.setApellidoMaterno(aMaterno);
				nombresDemograficoDTO.add(nombreDemograficoDTO);

				InvolucradoDTO involucradoDTO = new InvolucradoDTO();
				involucradoDTO.setNombresDemograficoDTO(nombresDemograficoDTO);

				SolicitudDTO solicitudDTO = new SolicitudDTO();
				solicitudDTO.setInvolucradoDTO(involucradoDTO);

				//tipoOperacion(OperacionDocumento.REGISTRAR_ORDEN_DETENCION);

				//request.getSession().setAttribute(KEY_SESSION_EVENTO + idEvento, solicitudDTO);


			}


		} catch (Exception e) {
			log.info(e.getCause(),e);

		}
		return mapping.findForward("succes");
	}


	/**
	 * Action para recopilar los datos de la medida cautelar e invocar al
	 * guardar medida cautelar
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward guardarMedidaCautelar(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("******EJECUTANDO ACTION GUARDAR MEDIDA CAUTELAR******");

			MedidaCautelarForm forma = (MedidaCautelarForm) form;

			log.info("VERIFICANDO PARAMETOS::::::");
			log.info("FechaFin........................"+forma.getFechaFin());
			log.info("FechaInicio....................."+forma.getFechaInicio());
			log.info("MedidaCautelar.................."+forma.getMedidaCautelar());
			log.info("Seguimiento....................."+forma.getSeguimiento());
			log.info("MedidaCautelarId................"+forma.getMedidaCautelarId());
			log.info("Periodicidad...................."+forma.getPeriodicidad());
			log.info("DescripcionMedidaCautelar......."+forma.getDescripcionMedidaCautelar());
			log.info("NumeroExpediente................"+forma.getNumeroExpediente());
			log.info("rowid..........................."+forma.getRowid());
			log.info("IdInvolucrado..................."+forma.getIdInvolucrado());

			Long medidaCautelarIdObtenido = null;

			MedidaCautelarDTO medida = new MedidaCautelarDTO();

			if (forma.getMedidaCautelarId() != null && forma.getMedidaCautelarId() > 0L) {
				if (forma.isActivo()) {

					medida.setEsActivo(false);
					medida.setDocumentoId(forma.getMedidaCautelarId());
					medidasCaulelaresDelegate.desactivarMedidaCautelar(medida);
					medidaCautelarIdObtenido = medida.getDocumentoId();
				}
			}
			else {

				if (forma.getIdInvolucrado() != null && forma.getIdInvolucrado() > 0L) {

					InvolucradoDTO involucrado = new InvolucradoDTO();

					involucrado.setElementoId(forma.getIdInvolucrado());
					medida.setInvolucrado(involucrado);
				}

				if (forma.getNumeroExpediente() != null) {

					ExpedienteDTO expedienteTrabajo = expedienteDelegate
							.obtenerExpedientePorNumeroExpediente(forma
									.getNumeroExpediente());

					if (expedienteTrabajo != null) {
						super.setExpedienteTrabajo(request, expedienteTrabajo);
						medida.setExpedienteDTO(expedienteTrabajo);
						if (expedienteTrabajo.getNumeroExpediente() != null) {
							medida.setNumeroCausa(expedienteTrabajo
									.getNumeroExpediente());
							log.debug(medida.getNumeroCausa()); //posible Error numero de causa 25 c.
						}
						if (expedienteTrabajo.getCasoDTO() != null
								&& expedienteTrabajo.getCasoDTO()
										.getNumeroGeneralCaso() != null) {
							medida.setNumeroCaso(expedienteTrabajo.getCasoDTO()
									.getNumeroGeneralCaso());
						}
					}
				}

				if (forma.getPeriodicidad() != null
						&& !forma.getPeriodicidad().equals(-1L)
						&& forma.getPeriodicidad() > 0L) {

					medida.setValorPeriodicidad(new ValorDTO(forma
							.getPeriodicidad()));
				}

				if (forma.getMedidaCautelar() != null
						&& !forma.getMedidaCautelar().equals(-1L)
						&& forma.getMedidaCautelar() > 0L) {
					medida.setValorTipoMedida(new ValorDTO(forma
							.getMedidaCautelar()));
				}

				if (forma.getSeguimiento() != null
						&& !forma.getSeguimiento().trim().isEmpty()) {
					medida.setSeguimiento(forma.getSeguimiento());
				}

				if (forma.getFechaInicio() != null) {
					medida.setFechaInicio(DateUtils.obtener(forma.getFechaInicio()));
				}

				if (forma.getFechaFin() != null) {
					medida.setFechaFin(DateUtils.obtener(forma.getFechaFin()));
				}

				if (forma.getDescripcionMedidaCautelar() != null
						&& !forma.getDescripcionMedidaCautelar().trim()
								.isEmpty()) {
					medida.setDescripcionMedida(forma
							.getDescripcionMedidaCautelar());
				}

				/*Enable JC. Se valida que existan todos los datos
				 * del funcionario asociado al usuario cargado en sesion,
				 * sino se consulta desde BD*/
				UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);
				if(usuarioFirmado != null){
					FuncionarioDTO func = usuarioFirmado.getFuncionario();
					if(func != null && func.getClaveFuncionario() != null && func.getClaveFuncionario() > 0){
						medida.setUsuario(usuarioFirmado);
					}else{
						FuncionarioDTO funcDTO = funcionarioDelegate.obtenerFuncionarioPorUsuarioId(usuarioFirmado.getIdUsuario());
						if(funcDTO != null){
							usuarioFirmado.setFuncionario(funcDTO);
							medida.setUsuario(usuarioFirmado);
						}
					}
				}

				medida.setEstatus(new ValorDTO(EstatusMedida.NO_ATENDIDA
						.getValorId()));

				log.debug(medida.getEstatus());
				log.debug(medida.getEstatus().getIdCampo());

				medidaCautelarIdObtenido = medidasCaulelaresDelegate
						.ingresarMedidaCautelar(medida);

				log.info("MEDIDA_CAULTELAR_ID OBTENIDO::"
						+ medidaCautelarIdObtenido);
			}

			MedidaCautelarForm medidaCautelarForm = new MedidaCautelarForm();

			medidaCautelarForm.setMedidaCautelarId(medidaCautelarIdObtenido);

			String xml = null;
			PrintWriter pw = null;
			converter.alias("medidaCautelarForm", MedidaCautelarForm.class);
			xml = converter.toXML(medidaCautelarForm);
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();

		} catch (Exception e) {
			log.info(e.getCause(), e);
		}
		return null;
	}



	/**
	 * Envía una medida cautelar ya guardada a una institución indicada
	 * por el parámetro 'institucionDestino'
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward enviarMedidaCautelarInstitucion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		Long idInstitucionDestino = NumberUtils.toLong(request.getParameter("institucionDestino"), 0L);
		Long idMedidaCautelar     =	NumberUtils.toLong(request.getParameter("medidaCautelarId"), 0L);

		log.info("enviarMedidaCautelarInstitucion --- "+idInstitucionDestino);

		try {
			if(!idInstitucionDestino.equals(0L) && !idMedidaCautelar.equals(0L)){
				Instituciones institucionDestino = Instituciones.getByValor(idInstitucionDestino);
				log.info("Institucion:"+ institucionDestino);
				medidasCaulelaresDelegate.enviarMedidaCautelarInstitucion(idMedidaCautelar, institucionDestino);
			}
			escribirRespuesta(response, converter.toXML(idMedidaCautelar));
		} catch (NSJPNegocioException e) {
			escribirRespuesta(response, converter.toXML(-1));
			log.info(e.getCause(), e);
		}
		return null;
	}

	/**
	 * Envía un mandamiento judicial a SSP
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward enviarMandamientoJudicial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.debug("Antes de enviar mandamiento judicial");
			mandamientoJudicialDelegate.enviarMandamientoJudicial(NumberUtils.toLong(request.getParameter("mandamientoId")));
			log.debug("Mandamiento judicial "+ request.getParameter("mandamientoId") + " enviado");
			escribirRespuesta(response,converter.toXML(request.getParameter("mandamientoId")));
		} catch (NSJPNegocioException e) {
			//Significa que no se pudo enviar el mandamiento
			converter.alias("respuesta",String.class);
			escribirRespuesta(response,converter.toXML("fallo_envio_de_mandamiento_judicial"));
			log.error(e.getMessage(),e);

		}

		return null;


	}


	/**
	 * Metodo utilizado para adjuntar un documento y asociarlo a un mandamiento
	 * judicial
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward adjuntarDocumentoAMandamientoJudicial(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("****EJECUTANDO ADJUNTAR DOCUMENTO A MANDAMIENTO JUDICIAL ACTION*****");

			AdjuntarDocumentoAMandamientoJudicialForm formaAdjuntarDocumento = (AdjuntarDocumentoAMandamientoJudicialForm) form;

			ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();
			MandamientoDTO mandamientoJudicialDTO = new MandamientoDTO();

			mandamientoJudicialDTO.setDocumentoId(formaAdjuntarDocumento
					.getMandamientoJudicialId());

			FormFile archivo = formaAdjuntarDocumento.getArchivoAdjunto();
			String fileName = archivo.getFileName();
			byte[] fileData = archivo.getFileData();

			adjunto.setContenido(fileData);

			if (fileName != null) {
				String[] extension = fileName.split("\\.");
				adjunto.setTipoArchivo("." + extension[extension.length - 1]);
				adjunto.setNombreArchivo(extension[0]);
				log.info("El nombre del archivo es: " + extension[0]);
			}

			adjunto.setUsuario(super.getUsuarioFirmado(request));

			DocumentoDTO loDocuemntoDTO = new DocumentoDTO();
			loDocuemntoDTO.setArchivoDigital(adjunto);
			/* Obligatorios de Documento */
			loDocuemntoDTO.setNombreDocumento(adjunto.getNombreArchivo());
			loDocuemntoDTO.setFechaCreacion(new Date());
			// Se asigna el tipo de documento
			loDocuemntoDTO.setTipoDocumentoDTO(new ValorDTO(
					TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()));
			loDocuemntoDTO.setEsGuardadoParcial(false);

			Long idArchivoDig = mandamientoJudicialDelegate
					.adjuntarDocumentoAMandamientoJudicial(loDocuemntoDTO,
							mandamientoJudicialDTO,
							TipoDocumento.ARCHIVO_ADJUNTADO);

			log.info("regreso" + idArchivoDig);
			return mapping.findForward("success");

		} catch (Exception e) {
			log.info(e.getCause(), e);
			return mapping.findForward("fail");
		}

	}

	/**
	 * Obtiene y prepara los datos para mostrar el grid de la bandeja inicial del encargado de causa
	 * Audiencias que tengan al menos una solicitud asociada y que están en cierto estado (abiertas, en proceso o cerradas)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarAudienciasBandejaInicialEncargadoCausa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String estadoSolicitud = request.getParameter("estadoSolicitud");
			EstatusSolicitud[] estadosEnum = null;
			if(estadoSolicitud != null){
				String []estadoSeparados = estadoSolicitud.split(",");
				estadosEnum = new EstatusSolicitud[estadoSeparados.length];
				for(int i=0;i<estadoSeparados.length;i++){
					estadosEnum[i] = EstatusSolicitud.getByValor(NumberUtils.toLong(estadoSeparados[i]));
				}
			}
			UsuarioDTO usuario = getUsuarioFirmado(request);
			List<AudienciaDTO> audiencias = audienciaDelegate
					.consultarAudienciasConSolicitudesPorTipoYEstado(
							new TiposSolicitudes[] {
									TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA,
									TiposSolicitudes.AUDIO_VIDEO },
							estadosEnum, usuario);

			if(audiencias != null){

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");

				final PaginacionDTO pag = PaginacionThreadHolder.get();
				log.debug("pag :: " + pag);
	            if (pag != null && pag.getTotalRegistros() != null) {
	                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
	                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
	            } else {
	                writer.print("<total>0</total>");
	                writer.print("<records>0</records>");
	            }

				for (AudienciaDTO aud : audiencias) {
				    	writer.print("<row id='"+ aud.getId() + "'>");
					    	writer.print("<cell>" +  (aud.getExpediente() !=null && aud.getExpediente().getCasoDTO() != null?
					    			 aud.getExpediente().getCasoDTO().getNumeroGeneralCaso():"") + "</cell>");
					    	writer.print("<cell>" +  (aud.getExpediente() !=null ?
					    			 aud.getExpediente().getNumeroExpediente():"") + "</cell>");
					    	writer.print("<cell>" +  (aud.getCaracter() !=null ? aud.getCaracter():"") + "</cell>");
					    	writer.print("<cell>" +  (aud.getTipoAudiencia() != null && aud.getTipoAudiencia().getValor() != null ?
					    			aud.getTipoAudiencia().getValor():"") + "</cell>");
					    	writer.print("<cell>" +  (aud.getFechaEvento() != null ? DateUtils.formatear(aud.getFechaEvento()):"") + "</cell>");
					    	writer.print("<cell>" +  (aud.getFechaEvento() != null ? DateUtils.formatearHora(aud.getFechaEvento()):"") + "</cell>");
					    	writer.print("<cell>" +  (aud.getSala() != null && aud.getSala().getNombreSala() != null ?
					    			aud.getSala().getNombreSala():"") + "</cell>");
					writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
			}

		} catch (Exception e) {
			log.info(e.getCause(),e);

		}
		return null;
	}

	/**
	 * Obtiene una lista de jueces asignados a una audiencia en formato XML
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 * @throws NSJPNegocioException
	 */
	public ActionForward consultarJuecesDeAudiencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		AudienciaDTO aduiencia = new AudienciaDTO(NumberUtils.toLong(request.getParameter("audienciaId")));
		try {
			List<InvolucradoViewDTO> involucrados = involucradoDelegate.obtenerInvolucradosAudiencia(aduiencia);

			List<FuncionarioDTO> funcionarios = new ArrayList<FuncionarioDTO>();
			for(InvolucradoViewDTO inv:involucrados){
				if(inv.isFuncionario()){
					FuncionarioDTO temp = new FuncionarioDTO(inv.getInvolucradoId());
					funcionarios.add(temp);
				}
			}

			List<InvolucradoViewDTO> jueces = funcionarioDelegate.validarFuncionariosPorRol(funcionarios, Roles.JUEZPJ.getValorId());

			converter.alias("listaJueces", LinkedList.class);
			converter.alias("juez", InvolucradoViewDTO.class);
			escribirRespuesta(response, converter.toXML(jueces));


		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(),e);
		}


		return null;



	}

	/**
	 * Obtiene una lista de la relación de los probables reponsables con su víctima y delito
	 * de los probables reponsables involucrados en la audiencia.
	 * Es posible y válido que se repita un probable responsable para más de un delito y/o víctima
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 * @throws NSJPNegocioException
	 */
	public ActionForward consultarProbablesResponsablesPorVictimaYDelito(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		AudienciaDTO aduiencia = new AudienciaDTO(NumberUtils.toLong(request.getParameter("audienciaId")));
		try {


			List<InvolucradoViewDTO> involucrados = involucradoDelegate.obtenerImputadosAudiencia(aduiencia);
			List<DelitoPersonaDTO> delitosInvolucrado = new ArrayList<DelitoPersonaDTO>();

			for(InvolucradoViewDTO inv:involucrados){
				delitosInvolucrado.addAll(delitoDelegate.consultarDelitosVictimaPorImputado(inv.getInvolucradoId()));

			}

			//Nombre PR
			//Nombre Victima
			//Delito
			//Calidad Actual
			//Nueva Calidad

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");


			writer.print("<records>" + delitosInvolucrado.size() + "</records>");

			List<CatalogoDTO> catSituacion = catalogoDelegate.recuperarCatalogo(Catalogos.SITUACION_JURIDICA);

			for (DelitoPersonaDTO delito : delitosInvolucrado) {
					InvolucradoDTO involucrado = delito.getProbableResponsable();
					Long situacionJur = involucradoDelegate.obtenerSituacionJuridicaInvolucrado(involucrado);
					if(situacionJur == null){
						situacionJur = 0L;
					}

					log.info("situacionJur "+ situacionJur);

			    	writer.print("<row id='"+ delito.getProbableResponsable().getElementoId() +"'>");
			    	writer.print("<cell>" +  delito.getProbableResponsable().getNombreCompleto() + "</cell>");
			    	if (delito.getVictima()!=null)
			    		writer.print("<cell>" +  delito.getVictima().getNombreCompleto() + "</cell>");
			    	else
			    		writer.print("<cell>" + "-" + "</cell>");
			    	writer.print("<cell>" +  (delito.getDelito()!=null && delito.getDelito().getCatDelitoDTO() != null
			    			?delito.getDelito().getCatDelitoDTO().getNombre():"") + "</cell>");
			    	writer.print("<cell>" +  (delito.getProbableResponsable().getCalidadDTO()!=null?
			    			delito.getProbableResponsable().getCalidadDTO().getValorIdCalidad().getValor():"") + "</cell>");
			    	writer.print("<cell><![CDATA[" +
			    			"<select id='delito_"+delito.getDelitoPersonaId() + "'>");
			    		for (CatalogoDTO catDTO:catSituacion) {
			    			writer.print("<option value='"+catDTO.getClave());
			    				if (situacionJur.equals(catDTO.getClave()))
			    					writer.print("' SELECTED>"+catDTO.getValor());
			    				else
			    					writer.print("'>" +catDTO.getValor());
			    			writer.print("</option>");
			    		}
			    	writer.print("</select>"+ "]]></cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();



		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}



	public ActionForward consultarSolicitudesTransAudioVideoPorEstatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		log.info("EJECUTANDO ACTION,consultarSolicitudesTransAudioVideoPorEstatus");
		Long  audienciaId = NumberUtils.toLong(request.getParameter("idAudiencia"));
		Long  enumTipoSolicitud = NumberUtils.toLong(request.getParameter("enumTipoSolicitud"));

		log.info("idAudiencia="+audienciaId);
		log.info("enumTipoSolicitud="+enumTipoSolicitud);

		try {
			List<SolicitudTranscripcionAudienciaDTO> listaTranscripcionAudioVideo =
				solicitudDelegate.consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(audienciaId, enumTipoSolicitud, EstatusSolicitud.ABIERTA.getValorId());
			listaTranscripcionAudioVideo.addAll(solicitudDelegate.consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(audienciaId, enumTipoSolicitud, EstatusSolicitud.EN_PROCESO.getValorId()));
			listaTranscripcionAudioVideo.addAll(solicitudDelegate.consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(audienciaId, enumTipoSolicitud, EstatusSolicitud.CERRADA.getValorId()));

			if(listaTranscripcionAudioVideo != null){
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				writer.print("<rows>");
				writer.print("<records>" + listaTranscripcionAudioVideo.size() + "</records>");
				for (SolicitudTranscripcionAudienciaDTO SolTransAudioVideo : listaTranscripcionAudioVideo) {
				    writer.print("<row id='" + SolTransAudioVideo.getDocumentoId()+ "'>");
				    	writer.print("<cell>" + (SolTransAudioVideo.getNombreSolicitante()!= null ? SolTransAudioVideo.getNombreSolicitante():"") + "</cell>");
				    	writer.print("<cell>" + (SolTransAudioVideo.getInstitucion()!=null ? SolTransAudioVideo.getInstitucion().getNombreInst():"")+ "</cell>");
				    	writer.print("<cell><![CDATA[" +
				    			"<input type='checkbox' id='ordenar_"+SolTransAudioVideo.getDocumentoId()+"' "+
				    			(SolTransAudioVideo.getEstatus().getIdCampo().equals(EstatusSolicitud.EN_PROCESO.getValorId())?"checked disabled='true'":"")+
				    			"'>"+
				    			"]]></cell>");
				    	writer.print("<cell><![CDATA[" +
				    			"<input type='checkbox' disabled "+
				    			(SolTransAudioVideo.getEstatus().getIdCampo().equals(EstatusSolicitud.CERRADA.getValorId())?"checked":"")+
				    			"/>"+
				    	"]]></cell>");
			    	writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
			}
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * Consulta una lista de medidas cautelares registradas relacionadas a cierto número de expediente.
	 * Prepara el resultado para llenar un grid
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarMedidasCautelaresPorNumeroExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {

			UsuarioDTO usuario = getUsuarioFirmado(request);

			List<MedidaCautelarDTO> listaMedidas = medidasCaulelaresDelegate.consultarMedidasCautelaresPorNumeroExpedienteOCausa(request.getParameter("numeroExpediente"),usuario);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros() + "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}

			for (MedidaCautelarDTO medida : listaMedidas) {

				writer.print("<row id='"+ medida.getInvolucrado().getElementoId()+","+medida.getDocumentoId() + "'>");

			    	writer.print("<cell>" + medida.getInvolucrado().getNombreCompleto() + "</cell>");

			    	writer.print("<cell>" +  (medida!=null?medida.getValorTipoMedida().getValor():"") + "</cell>");

			    	writer.print("<cell>" + (medida!=null && medida.getFechaCreacion() != null ? DateUtils.formatear(medida.getFechaCreacion()): "-") + "</cell>");
			    	writer.print("<cell>" + (medida!=null && medida.getFechaInicio() != null ? DateUtils.formatear(medida.getFechaInicio()): "-") + "</cell>");
			    	writer.print("<cell>" + (medida!=null && medida.getFechaFin() != null ? DateUtils.formatear(medida.getFechaFin()): "-") + "</cell>");

			    	writer.print("<cell>" +  ( (medida!=null && medida.getValorPeriodicidad()!=null &&
			    			medida.getValorPeriodicidad().getValor()!=null)? medida.getValorPeriodicidad().getValor() :"") + "</cell>");

			    	writer.print("<cell>" +  (medida!=null?medida.getDescripcionMedida():"") + "</cell>");

			    	writer.print("<cell>" +  ( (medida!=null && medida.getEstatus()!=null)? medida.getEstatus().getValor():"") + "</cell>");

			    	writer.print("<cell>" +  (medida!=null?medida.getSeguimiento():"") + "</cell>");


		    	writer.print("</row>");
			}

			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.info(e.getCause(),e);
		}
		return null;
	}



	/**
	 * Consulta una lista de Alternativas registradas relacionadas a cierto número de expediente.
	 * Prepara el resultado para llenar un grid
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarMedidasAlternativasPorNumeroExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {

			UsuarioDTO usuario = getUsuarioFirmado(request);
			List<InvolucradoDTO> involucrados = medidasAlternasDelegate
					.consultarInvolucradosConMedidasAlternativasPorCarpetaEjecucion(
							request.getParameter("numeroExpedieteId"), usuario);
			if(involucrados != null){

				//Tamaño de los renglones del Grid
				Integer numeroRenglones=0;

				for (InvolucradoDTO invo : involucrados) {
					if(invo.getMedidasAlternasDTO() != null && !invo.getMedidasAlternasDTO().isEmpty()){
						numeroRenglones = numeroRenglones + invo.getMedidasAlternasDTO().size();
					}
				}

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");
				writer.print("<records>" + numeroRenglones + "</records>");
				for (InvolucradoDTO invo : involucrados) {
					if(invo.getMedidasAlternasDTO() != null && !invo.getMedidasAlternasDTO().isEmpty()){
						for(MedidaAlternaDTO medidaInvolucrado : invo.getMedidasAlternasDTO()){
							writer.print("<row id='"+ invo.getElementoId()+","+
					    			(medidaInvolucrado!=null?medidaInvolucrado.getDocumentoId():"") + "'>");
						    	writer.print("<cell>" +  invo.getNombreCompleto() + "</cell>");
						    	writer.print("<cell>" +  (medidaInvolucrado!=null?medidaInvolucrado.getValorTipoMedida().getValor():"") + "</cell>");
						    	writer.print("<cell>" +  (medidaInvolucrado!=null?medidaInvolucrado.getDescripcionMedida():"") + "</cell>");
						    	writer.print("<cell>" + (medidaInvolucrado!=null?DateUtils.formatear(medidaInvolucrado.getFechaInicio()):"") + " - " +
						    			(medidaInvolucrado!=null?DateUtils.formatear(medidaInvolucrado.getFechaFin()):"")+ "</cell>");
						    	writer.print("<cell>" +  (medidaInvolucrado!=null && medidaInvolucrado.getValorPeriodicidad()!=null?
						    			medidaInvolucrado.getValorPeriodicidad().getValor():"-") + "</cell>");
						    	writer.print("<cell>" +  (medidaInvolucrado!=null?medidaInvolucrado.getSeguimiento():"") + "</cell>");
					    	writer.print("</row>");
						}
					}
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
			}

		} catch (Exception e) {
			log.info(e.getCause(),e);

		}
		return null;
	}

	/**
	 * Consulta una lista de mandamientos judiciales relacionados a cierto número de expediente.
	 * Prepara el resultado para llenar un grid
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarMandamientosJudicialesPorCausa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {
			log.debug("consultarMandamientosJudicialesPorCausa >>> ");
			log.debug("numero_causa: " + request.getParameter("numeroExpediente"));

			UsuarioDTO usuario = getUsuarioFirmado(request);
			String numeroExpediente = request.getParameter("numeroExpediente");

			List<MandamientoDTO> mandamientos = new ArrayList<MandamientoDTO>();

			if(numeroExpediente!=null && !numeroExpediente.isEmpty()){
				mandamientos = mandamientoJudicialDelegate.
						consultarMandamientosPorNumeroExpediente(numeroExpediente,usuario);
			}

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }

			for (MandamientoDTO mandamiento : mandamientos) {

			    	writer.print("<row id='"+ mandamiento.getInvolucrado().getElementoId()+ ","+ mandamiento.getMedida().getDocumentoId()+ "'>");

			    	if(mandamiento.getInvolucrado()!=null &&
			    	   mandamiento.getInvolucrado().getNombreCompleto()!=null){
			    		writer.print("<cell>" + mandamiento.getInvolucrado().getNombreCompleto() + "</cell>");
			    	}
			    	else{
			    		writer.print("<cell> --- </cell>");
			    	}

			    	if(mandamiento.getMedida()!=null &&
			    	   mandamiento.getMedida().getValorTipoMedida()!=null &&
			    	   mandamiento.getMedida().getValorTipoMedida().getValor()!=null
			    	){
			    		writer.print("<cell>" + mandamiento.getMedida().getValorTipoMedida().getValor() + "</cell>");
			    	}
			    	else{
			    		writer.print("<cell> --- </cell>");
			    	}

			    	if(mandamiento.getFechaCreacion()!=null){
			    		writer.print("<cell>" + DateUtils.formatear(mandamiento.getFechaCreacion()) + "</cell>");
			    	}
			    	else{
			    		writer.print("<cell> --- </cell>");
			    	}

			    	if(mandamiento.getEstatus()!=null &&
			    	   mandamiento.getEstatus().getValor()!=null){
			    		writer.print("<cell>" + mandamiento.getEstatus().getValor() + "</cell>");
			    	}
			    	else{
			    		writer.print("<cell> --- </cell>");
			    	}

			    	writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException e) {
			log.error(e.getCause(),e);
		}

		return null;
	}

	/**
	 * Consulta una lista de mandamientos judiciales en un periodo de tiempo
	 * Prepara el resultado para llenar un grid
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarMandamientosJudicialesPorPeriodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {
			log.debug("consultarMandamientosJudicialesPorPeriodo >>> ");
			log.debug("fecha_inicio: "+request.getParameter("fechaInicio"));
			log.debug("fecha_Fin: "+request.getParameter("fechaFin"));
			log.debug("estatus_mandamiento: "+request.getParameter("estatusMandamiento"));

			Date fechaInicio = DateUtils.obtener(request.getParameter("fechaInicio"));
			Date fechaFin = DateUtils.obtener(request.getParameter("fechaFin"));
			Long estatusMandamiento = Long.parseLong(request.getParameter("estatusMandamiento"));

			//TODO - ACT - Cambiar el metodo para consultar por periodo
			MandamientoDTO mandamientoDTO= new MandamientoDTO();
			mandamientoDTO.setFechaInicial(fechaInicio);
			mandamientoDTO.setFechaFinal(fechaFin);
			mandamientoDTO.setEstatus(new ValorDTO(estatusMandamiento));

			List<MandamientoDTO> mandamientos = mandamientoJudicialDelegate.
					consultarMandamientoPorFiltro(mandamientoDTO,null);

			log.debug("TERMINE consultarMandamientosJudicialesPorPeriodo >>> ");
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }

			for (MandamientoDTO mandamiento : mandamientos) {



			    	writer.print("<row id='"+ mandamiento.getDocumentoId()+","+(mandamiento.getEstatus()!=null?mandamiento.getEstatus().getValor():"") + "'>");
			    	writer.print("<cell>" +  DateUtils.formatear(mandamiento.getFechaCreacion()) + "</cell>");
			    	writer.print("<cell>" +  (mandamiento.getTipoMandamiento()!=null?mandamiento.getTipoMandamiento().getValor():"") + "</cell>");
			    	writer.print("<cell>" +  (mandamiento.getEstatus()!=null?mandamiento.getEstatus().getValor():"") +   "</cell>");
			    	writer.print("</row>");


			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			log.error(e.getCause(),e);
		}

		return null;
	}

	/**
	 * Consulta una lista de mandamientos judiciales en un periodo de tiempo
	 * Prepara el resultado para llenar un grid
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarGridDeMandamientoJudicialPorExpedienteYTipo(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {
			log.debug("*****EJECUTANDO CONSULTAR DOCUMENTOS DE MANDAMIENTO*********");
			log.debug("mandamientoJudicialId: "
					+ request.getParameter("mandamientoJudicialId"));
			log.debug("tipoDocumento: " + request.getParameter("tipoDocumento"));
			log.debug("numeroExpedienteId: "
					+ request.getParameter("numeroExpedienteId"));

			Long mandamientoJudicialId = NumberUtils.toLong(
					request.getParameter("mandamientoJudicialId"), 0L);
			Long tipoDocumento = NumberUtils.toLong(
					request.getParameter("tipoDocumento"), 0L);
			Long numeroExpedienteId = NumberUtils.toLong(
					request.getParameter("numeroExpedienteId"), 0L);

			MandamientoDTO mandamientoDto = new MandamientoDTO();
			mandamientoDto.setDocumentoId(mandamientoJudicialId);

			ExpedienteDTO expedienteDto = new ExpedienteDTO();
			expedienteDto.setNumeroExpedienteId(numeroExpedienteId);

			List<DocumentoDTO> listaDocumentosAsociados = documentoDelegate
					.consultarDocumentosDeMandamientoJudicialPorExpediente(
							expedienteDto, mandamientoDto, tipoDocumento);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros()
						+ "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}

			for (DocumentoDTO documentoDto : listaDocumentosAsociados) {

				writer.print("<row id='" + documentoDto.getDocumentoId() + "'>");
				writer.print("<cell>" + documentoDto.getStrFechaCreacion()
						+ "</cell>");
				if (documentoDto.getNombreDocumento() != null) {
					writer.print("<cell>" + documentoDto.getNombreDocumento()
							+ "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}
				writer.print("</row>");

			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			log.error(e.getCause(), e);
		}

		return null;
	}

	/**
	 * Actualiza el estatus de un mandamiento judicial
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward actualizarMandamientoJudicial(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("::::::::::EJECUTANDO ACTION CAMBIAR ESTATUS DE MANDAMIENTO JUDICIAL::::::::::");

			Long nuevoEstado = NumberUtils.toLong(request.getParameter("estatusMandamiento"));
			Long mandamientoId = NumberUtils.toLong(request.getParameter("mandamientoId"));
			Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpedienteId"), 0L);
			String numeroExpediente = request.getParameter("numeroExpediente");
			String numeroGeneralCaso = request.getParameter("numeroGeneralCaso");

			log.debug(":::::::::::VERIFICANDO PARAMETROS::::::::::::::");
			log.debug("mandamientoId....." + mandamientoId);
			log.debug("nuevoEstado.............." + nuevoEstado);
			log.debug("numeroExpedienteId..." + numeroExpedienteId);
			log.debug("numeroExpediente....." + numeroExpediente);
			log.debug("numeroGeneralCaso...." + numeroGeneralCaso);

			MandamientoDTO mandamiento = new MandamientoDTO();
			mandamiento.setDocumentoId(mandamientoId);
			mandamiento.setEstatus(new ValorDTO(nuevoEstado));

			mandamientoJudicialDelegate.actualizarMandamiento(mandamiento);

			if(numeroGeneralCaso != null && !numeroGeneralCaso.trim().isEmpty()){

				DocumentoDTO documento = obtenerDocumentoPorActividadYNumeroExpedienteId(
						numeroExpedienteId,
						numeroExpediente,
						Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL);

				if (documento != null && documento.getDocumentoId() != null
						&& documento.getDocumentoId() > 0L) {

					List<DocumentoDTO> lstDocumentoDTO = new ArrayList<DocumentoDTO>();
					ActividadDTO actividadDTO = new ActividadDTO();

					actividadDTO.setTipoActividad(Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL);
					actividadDTO.setActividadId(ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL.getValorId());
					documento.setActividadDTO(actividadDTO);
					lstDocumentoDTO.add(documento);
					documentoDelegate.enviarDocumentoAInstitucion(lstDocumentoDTO, numeroGeneralCaso, Instituciones.PGJ);
				}

			}

			escribirRespuesta(response, converter.toXML(mandamiento));

		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Metodo para obtener el ultimo documento de una expediente con una
	 * actividad
	 *
	 * @param numeroExpedienteId
	 * @param numeroExpediente
	 * @throws NSJPNegocioException
	 */
	public DocumentoDTO obtenerDocumentoPorActividadYNumeroExpedienteId(Long numeroExpedienteId,
			String numeroExpediente, Actividades tipoActividad)
			throws NSJPNegocioException {

		log.info("::::::::Metodo obtenerDocumentoPorActividad");
		DocumentoDTO filtroDTO = new DocumentoDTO();
		ActividadDTO actividadDTO = new ActividadDTO();
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();

		actividadDTO.setTipoActividad(tipoActividad);
		filtroDTO.setActividadDTO(actividadDTO);

		if (numeroExpedienteId != null && numeroExpedienteId > 0L) {
			expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
		} else {
			if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
				expedienteDTO.setNumeroExpediente(numeroExpediente);
				expedienteDTO = expedienteDelegate
						.obtenerNumeroExpedienteByNumExp(expedienteDTO, null);
			}
		}

		if (expedienteDTO != null
				&& expedienteDTO.getNumeroExpedienteId() != null) {

			filtroDTO.setExpedienteDTO(expedienteDTO);
			filtroDTO = documentoDelegate.consultarUltimoDocumentoPorActividadYExpedienteId(filtroDTO);
		}
		return filtroDTO;
	}


	/**
	 * Consulta una lista de medidas cautelares registradas relacionadas a cierto número de expediente.
	 * Prepara el resultado para llenar un grid
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultaDocumentosEncargadoCausa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {



		try {
			Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpedieteId"));

			List<MedidaCautelarDTO> medidasCautelares = medidasCaulelaresDelegate.obtenerMedidasCautelaresPorExpediente(numeroExpedienteId);
			if(medidasCautelares != null){

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");


				writer.print("<records>" + medidasCautelares.size() + "</records>");

				for (MedidaCautelarDTO medida : medidasCautelares) {

					//Nombre del involucrado
					//Medida cautelar
					//Descripción (de medida cautelar)
					//Periodo de imposición
					//Periodicidad
					//Encargado seguimiento


				    	writer.print("<row id='"+ medida.getDocumentoId() + "'>");
				    	//writer.print("<cell>" +  (aud.getExpediente() !=null && aud.getExpediente().getCasoDTO() != null?
				    	//		 aud.getExpediente().getCasoDTO().getNumeroGeneralCaso():"") + "</cell>");



					writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
			}

		} catch (Exception e) {
			log.info(e.getCause(),e);

		}
		return null;
	}

	/**
	 * Actualiza las solicitudes de transcripción y audio/video de una audiencia al estaus  EN_PROCESO para
	 * que sean retomadas por el encargado de informática o el traductor
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward ordenarSolicitudesTranscripcionyAV(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String idsDeSolicitudes = request.getParameter("solicitudesIds");
		if(idsDeSolicitudes != null){

			String []idsSeparados = idsDeSolicitudes.split(",");
			if(idsSeparados != null && idsSeparados.length > 0){
				SolicitudDTO solicitudParam = new SolicitudDTO();
				//Se reciben los ids de las solicitudes en una cadena separada por comas
				for(String idSol:idsSeparados){
					if(NumberUtils.toLong(idSol) > 0){
						solicitudParam.setDocumentoId(NumberUtils.toLong(idSol));
						try {
							solicitudDelegate.actualizaEstatusSolicitud(solicitudParam, EstatusSolicitud.EN_PROCESO);
						} catch (NSJPNegocioException e) {
							log.error(e.getMessage(),e);
						}
					}



				}

			}

		}
		escribirRespuesta(response, converter.toXML(idsDeSolicitudes));
		return  null;
	}

	/**
	 * Actualiza las solicitudes de transcripción y audio/video de una audiencia al estaus  EN_PROCESO para
	 * que sean retomadas por el encargado de informática o el traductor
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward actualizarSituacionJuridica(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String mensaje = "";
		try {
			JSONParser parser = new JSONParser();

			StringBuilder sb = new StringBuilder();
		    BufferedReader br = request.getReader();
		    String str;
		    while( (str = br.readLine()) != null ){
		        sb.append(str);
		    }
		    Object obj = parser.parse(sb.toString());

		    JSONArray jsonArray = (JSONArray) obj;

			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				JSONObject imputado = iterator.next();

				log.info("/******* valor id:: " + imputado.get("involucradoId"));
				log.info("/******* valor situacion juridica:: " + imputado.get("situacionJuridica"));

				InvolucradoDTO involucradoDTO = new InvolucradoDTO();
				involucradoDTO.setElementoId(NumberUtils.createLong(imputado.get("involucradoId").toString()));
				involucradoDTO.setUsuario(super.getUsuarioFirmado(request));

				SentenciaDTO sentenciaDTO = new SentenciaDTO();

				if(imputado.get("todosLosDatos").toString().toLowerCase().equals("true")){

					ValorDTO valorDTO = new ValorDTO(NumberUtils.createLong(imputado.get("tipoSentencia").toString()));
					sentenciaDTO.setValorDTO(valorDTO);

					sentenciaDTO.setBlesionado(Boolean.parseBoolean(imputado.get("esLesionado").toString()));

					sentenciaDTO.setDfechaInicioPena(DateUtils.obtener(imputado.get("fechaInicio").toString()));

					sentenciaDTO.setDfechaFinPena(DateUtils.obtener(imputado.get("fechaFin").toString()));

					sentenciaDTO.setIpuntosPorAcumular(NumberUtils.createLong(imputado.get("puntosPorAcumular").toString()));

					sentenciaDTO.setBcumplida(Boolean.FALSE);

					sentenciaDTO.setCnus(imputado.get("nus").toString());

					sentenciaDTO.setCnumeroCausa("");
				}

				involucradoDelegate.actualizarSituacionJuridicaInvolucrado(involucradoDTO, NumberUtils.createLong(imputado.get("situacionJuridica").toString()), sentenciaDTO);
				mensaje = "Los Datos se han guardado con exito.";
			}
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			mensaje = "Ha ocurrdio un error.";
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			mensaje = "Ha ocurrdio un error.";
		} finally {
			try {
			response.setContentType("text/javascript; charset=ISO-8859-1");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("{\"mensaje\":\""+mensaje+"\"}");
			writer.flush();
			writer.close();
			} catch(Exception e){
				log.error(e.getMessage(), e);
			}
		}
		return  null;
	}



	/**
	 * buscar Involucrados Audiencia Por Causa
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarInvolucradosAudienciaPorCausa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		log.info("entrando a  buscarInvolucradosAudienciaPorCausa" );

		try {
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setNumeroExpediente(request.getParameter("numCausa"));

			log.info("/**** Numero de causa"+request.getParameter("numCausa"));

			UsuarioDTO usuario = getUsuarioFirmado(request);

			ExpedienteDTO numExpediente = expedienteDelegate.obtenerNumeroExpedienteByNumExp(expedienteDTO,usuario);

			List<InvolucradoDTO> probablesResponsables = involucradoDelegate.consultarProbablesResponsablesDetenidos(numExpediente, null);

			List<DelitoPersonaDTO> delitosInvolucrado = new ArrayList<DelitoPersonaDTO>();

			for(InvolucradoDTO inv:probablesResponsables){
				delitosInvolucrado.addAll(delitoDelegate.consultarDelitosVictimaPorImputado(inv.getElementoId()));
			}

			//Nombre PR
			//Nombre Victima
			//Delito
			//Calidad Actual
			//Nueva Calidad

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros() + "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}

			List<CatalogoDTO> catSituacion = catalogoDelegate.recuperarCatalogo(Catalogos.SITUACION_JURIDICA);

			for (DelitoPersonaDTO delito : delitosInvolucrado) {
					Long situacionJur = delito.getProbableResponsable().getValorSituacionJuridica()!=null?delito.getProbableResponsable().getValorSituacionJuridica().getIdCampo():new Long(0);

			    	writer.print("<row id='"+ delito.getProbableResponsable().getElementoId() +"'>");
			    	writer.print("<cell>" +  delito.getProbableResponsable().getNombreCompleto() + "</cell>");
			    	if (delito.getVictima()!=null)
			    		writer.print("<cell>" +  delito.getVictima().getNombreCompleto() + "</cell>");
			    	else
			    		writer.print("<cell>" + "-" + "</cell>");
			    	writer.print("<cell>" +  (delito.getDelito()!=null && delito.getDelito().getCatDelitoDTO() != null
			    			?delito.getDelito().getCatDelitoDTO().getNombre():"") + "</cell>");
			    	writer.print("<cell>" +  (delito.getProbableResponsable().getCalidadDTO()!=null?
			    			delito.getProbableResponsable().getCalidadDTO().getValorIdCalidad().getValor():"") + "</cell>");
			    	writer.print("<cell><![CDATA[" +
			    			"<select id='delito_"+delito.getDelitoPersonaId() + "'>");
			    		for (CatalogoDTO catDTO:catSituacion) {
			    			writer.print("<option value='"+catDTO.getClave());
			    				if (situacionJur.equals(catDTO.getClave()))
			    					writer.print("' SELECTED>"+catDTO.getValor());
			    				else
			    					writer.print("'>" +catDTO.getValor());
			    			writer.print("</option>");
			    		}
			    	writer.print("</select>"+ "]]></cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}


	public ActionForward registrarMedidaAlternativa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {
			String strInvolucrado = request.getParameter("idInvolucrado");
			log.info("ejecutando action guardar medida Alternativa --- "+strInvolucrado);
			String expedienteid = request.getParameter("numeroUnicoExpediente");
			log.info("ejecutando action guardar medida Alternativa --- "+expedienteid);
			String numeroCarpetaE = request.getParameter("numeroCarpetaE");
			log.info("ejecutando action guardar medida Alternativa --- "+numeroCarpetaE);



			MedidaAlternaForm forma = (MedidaAlternaForm) form;
			Long resultado = null;
			MedidaAlternaDTO medida = new MedidaAlternaDTO();

			InvolucradoDTO involucrado = new InvolucradoDTO();
			involucrado.setElementoId(Long.parseLong(strInvolucrado));


				ExpedienteDTO expedienteDTO = new ExpedienteDTO();
				expedienteDTO.setExpedienteId(NumberUtils.toLong(expedienteid));
				medida.setNumeroCarpetaEjecucion(numeroCarpetaE);
				medida.setInvolucrado(involucrado);
				medida.setExpedienteDTO(expedienteDTO);
				medida.setValorPeriodicidad(new ValorDTO(forma.getPeriodicidad()));
				ValorDTO valorGenerico = new ValorDTO();
				valorGenerico = new ValorDTO();
				Long valorMedida=0L;
				log.info("log para la forma --- "+forma);
				if(forma.getMedidaCautelar()!=null){
					log.info("ejecutando action guardar medida alternativa forma.getMedidaCautelar() --- "+forma.getMedidaCautelar());
					valorMedida=Long.parseLong(forma.getMedidaCautelar());


				valorGenerico.setIdCampo(valorMedida);
				medida.setValorTipoMedida(valorGenerico);

				if(forma.getSeguimiento() != null){
					log.info("ejecutando action guardar medida alternativa forma.getSeguimiento() --- "+forma.getSeguimiento());
					medida.setSeguimiento(forma.getSeguimiento());
				}
				if(forma.getFechaInicio() != null){
					medida.setFechaInicio(DateUtils.obtener(forma.getFechaInicio()));
				}
				if(forma.getFechaFin() != null){
					medida.setFechaFin(DateUtils.obtener(forma.getFechaFin()));
				}
				medida.setDescripcionMedida(request.getParameter("descripcionMedidaCautelar"));
				medida.setUsuario(super.getUsuarioFirmado(request));
				log.info("Datos pde la forma ::::::::"+medida);
				resultado = medidasAlternasDelegate.registrarMedidaAlterna(medida);
				log.info("ejecutando action guardar medida alternativa  fin --- "+resultado);

			}

			MedidaCautelarForm medidaCautelarForm = new MedidaCautelarForm();
			medidaCautelarForm.setMedidaCautelarId(resultado);
			String xml = null;
			PrintWriter pw = null;
			converter.alias("medidaCautelarForm",MedidaCautelarForm.class);
			xml = converter.toXML(medidaCautelarForm);
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (NSJPNegocioException e) {
			log.error(e);
		}

		//escribirRespuesta(response, converter.toXML(idsDeSolicitudes));
		return  null;
	}

	public ActionForward enviarMedidaAlterna(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {
			String documentoId = request.getParameter("medidaAlternaId");
			log.info("Enviar Medida Alterna ID :: "+documentoId);
			MedidaAlternaDTO medidaAlternaDTO = medidasAlternasDelegate.enviarMedidaAlternaASSP(Long.parseLong(documentoId));

			String xml = null;
			PrintWriter pw = null;
			converter.alias("medidaCautelarForm",MedidaCautelarForm.class);
			xml = converter.toXML("");
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			escribirRespuesta(response, converter.toXML(medidaAlternaDTO));
		} catch (NSJPNegocioException e) {
			log.error(e);
		}

		return  null;
	}

	/**
	 * Metodo usado para consultar los turnos de accion penal privada en
	 * el usuario encargado de causa.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarTurnosAccPenalPrivada(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException{
		try{
			log.info("EJECUTANDO ACTION CONSULOTAR TURNOS ACCION PENAL PRIVADA");
			log.info("*****************VERIFICANDO PARAMETROS******************");
			log.info("ESTADO="+request.getParameter("estado"));

			Long estado = NumberUtils.toLong(request.getParameter("estado"),0L);

			if(estado > 0L){
				TurnoDTO turnoFiltro = new TurnoDTO();
				turnoFiltro.setTipoTurno(TipoTurno.JUDICIAL);
				turnoFiltro.setEstado(EstatusTurno.getByValor(estado));
				UsuarioDTO usuario = getUsuarioFirmado(request);
				turnoFiltro.setUsuario(usuario);

				List<TurnoDTO> listaTurnos = turnoDelegate.obtenerTurnosPorFiltro(turnoFiltro);

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");

				final PaginacionDTO pag = PaginacionThreadHolder.get();
				log.debug("pag :: " + pag);
				if (pag != null && pag.getTotalRegistros() != null) {
					writer.print("<total>" + pag.getTotalPaginas() + "</total>");
					writer.print("<records>" + pag.getTotalRegistros() + "</records>");
				} else {
					writer.print("<total>0</total>");
					writer.print("<records>0</records>");
				}

				for (TurnoDTO turno : listaTurnos) {

			    	writer.print("<row id='"+turno.getTurnoId()+"+"+(turno.getExpediente() != null ? turno.getExpediente().getNumeroExpediente():"null")+"'>");
			    	writer.print("<cell>"+ turno.getStrFechaAtencion()+ "</cell>");
			    	writer.print("<cell>"+ turno.getStrHoraAtencion()+ "</cell>");
			    	writer.print("<cell>"+ turno.getNombreCompleto()+ "</cell>");

			    	/*Enable It agregando Rows para mostrar el numero de causa en tribunal al encargado de causas*/
			    	if(turno.getExpediente()!= null){
				    	if(turno.getExpediente().getNumeroExpediente()!= null && turno.getExpediente().getNumeroExpediente()!=""){
				    		writer.print("<cell>"+ turno.getExpediente().getNumeroExpediente()+ "</cell>");
				    	}else{
				    		writer.print("<cell> - - </cell>");
				    	}
			    	}else{
			    		writer.print("<cell> - - </cell>");
			    	}

				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
			}

		}catch (NSJPNegocioException e) {
			log.error(e);
		}
		return null;
	}


	/**
	 * Metodo usado para cambiar el estado del turno de accion penal privada
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward actualizaEstadoDelTurno(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, NSJPNegocioException{
		log.info("EJECUTANDO ACTION ACTUALIZAR ESTADO DEL TURNO");
		log.info("*****************VERIFICANDO PARAMETROS******************");
		log.info("ID DEL TURNO DE ATENCION="+request.getParameter("turnoId"));
		log.info("EXPEDIENTE ID...........="+request.getParameter("idExpediente"));

		Long turnoId = NumberUtils.toLong(request.getParameter("turnoId"),0L);
		Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"),0L);
		UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);

		if(turnoId > 0 && idExpediente > 0){

			ExpedienteDTO expedienteDto = new ExpedienteDTO();
			expedienteDto.setExpedienteId(idExpediente);
			TurnoDTO turnoModificadoDTO = new TurnoDTO();
			turnoModificadoDTO.setUsuario(usuarioDTO);
			turnoModificadoDTO.setTurnoId(turnoId);
			turnoModificadoDTO.setExpediente(expedienteDto);
			turnoModificadoDTO.setEstado(EstatusTurno.ATENDIDO);
			turnoDelegate.actualizarTurno(turnoModificadoDTO);

		}
		return mapping.findForward("succes");
	}

	/**
	 * Metodo usado para cambiar el estado del turno de accion penal privada
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarExpedienteByIdPJENC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, NSJPNegocioException{
		log.info("EJECUTANDO ACTION CONSULTAR EXPEDIENTE");
		log.info("*****************VERIFICANDO PARAMETROS******************");
		log.info("ID DEL EXPEDIENTE="+request.getParameter("expedienteId"));

		String numeroExpRelacion = request.getParameter("expedienteId");

		if(numeroExpRelacion != null && !numeroExpRelacion.equalsIgnoreCase("")){

			ExpedienteDTO expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpRelacion);
			super.setExpedienteTrabajo(request, expedienteDTO);
			expedienteDTO.setConsulta(false);
			request.getSession().setAttribute("numeroExpediente", expedienteDTO.getNumeroExpediente());
			converter.alias("expedienteDTO", ExpedienteDTO.class);
			escribirRespuesta(response, converter.toXML(expedienteDTO));
		}

		return null;
	}

	/**
	 * Metodo para pasar como parametro el id de la audiencia
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward recargaVisor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, NSJPNegocioException{
		log.info("EJECUTANDO ACTION RECARGAR VISOR");
		log.info("*****************VERIFICANDO PARAMETROS******************");
		VisorSolicitudAudienciaForm forma = (VisorSolicitudAudienciaForm) form;
		log.info("idAudiencia="+forma.getIdAudiencia());
		log.info("idVisor="+forma.getIdVisor());
		log.info("idVisor="+forma.getIdAudienciaSiguiente());

		if (forma.getIdVisor() != null && forma.getIdAudiencia() != null){
			if(forma.getIdVisor().equals(1L)){
				request.getSession().setAttribute("idEvento",forma.getIdAudiencia());
				return mapping.findForward("succes");
			}
			if(forma.getIdVisor().equals(2L)){
				request.getSession().setAttribute("idEvento",forma.getIdAudiencia());
				request.setAttribute("idAudienciaSiguiente",forma.getIdAudienciaSiguiente());
				return mapping.findForward("succesPJENS");
			}
			if(forma.getIdVisor().equals(3L)){
				request.getSession().setAttribute("idEvento",forma.getIdAudiencia());
				request.setAttribute("idAudienciaSiguiente",forma.getIdAudienciaSiguiente());
				return mapping.findForward("succesPJJUE");
			}
		}


		return null;
	}

	/**
	 * Actualiza las solicitudes de transcripción y audio/video de una audiencia al estaus  EN_PROCESO para
	 * que sean retomadas por el encargado de informática o el traductor
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hernández
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward obtenerNUSDelInvolucreado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {

			JSONParser parser = new JSONParser();

			StringBuilder sb = new StringBuilder();
		    BufferedReader br = request.getReader();
		    String str;
		    while( (str = br.readLine()) != null ){
		        sb.append(str);
		    }
		    Object obj = parser.parse(sb.toString());

		    JSONArray jsonArray = (JSONArray) obj;


			Iterator<JSONObject> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				JSONObject imputado = iterator.next();

				log.info("/******* valor id:: " + imputado.get("involucradoId"));
				log.info("/******* valor situacion juridica:: " + imputado.get("situacionJuridica"));

				InvolucradoDTO involucradoDTO = new InvolucradoDTO();
				involucradoDTO.setElementoId(NumberUtils.createLong(imputado.get("involucradoId").toString()));
				if(imputado.get("yaEstaSentenciado").toString().toLowerCase().equals("false")){

					involucradoDTO = involucradoDelegate.obtenerInvolucrado(involucradoDTO);

					HashMap<String, String> hashMap = sentenciaDelegate.consultarNUSTOJSON(involucradoDTO);

					imputado.put("listaNUS", hashMap);

					log.info("IMPUTADO\n: " + imputado.get("listaNUS"));

				}

			}

			StringWriter out = new StringWriter();
			jsonArray.writeJSONString(out);
			log.info("IMPUTADOS CON NUS:" + out.toString());
			response.setContentType("text/javascript;charset=UTF-8");
			response.getWriter().println(out.toString());


		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
		}
		return  null;
	}

	public ActionForward consultarDocumentosDeMedidasCautelaresPorExpedienteYTipo(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {
			log.debug("*****EJECUTANDO CONSULTAR DOCUMENTOS DE MEDIDAS*********");
			log.debug("medidaCautelarlId: "
					+ request.getParameter("medidaCautelarlId"));
			log.debug("tipoDocumento: " + request.getParameter("tipoDocumento"));
			log.debug("numeroExpedienteId: "
					+ request.getParameter("numeroExpedienteId"));

			Long medidaCautelarlId = NumberUtils.toLong(
					request.getParameter("medidaCautelarlId"), 0L);
			Long tipoDocumento = NumberUtils.toLong(
					request.getParameter("tipoDocumento"), 0L);
			Long numeroExpedienteId = NumberUtils.toLong(
					request.getParameter("numeroExpedienteId"), 0L);

			MedidaCautelarDTO medidaCautelarDTO = new MedidaCautelarDTO();
			medidaCautelarDTO.setDocumentoId(medidaCautelarlId);

			ExpedienteDTO expedienteDto = new ExpedienteDTO();
			expedienteDto.setNumeroExpedienteId(numeroExpedienteId);

			List<DocumentoDTO> listaDocumentosAsociados = documentoDelegate
					.consultarDocumentosDeMedidasCautelaresPorExpedienteYTipo(
							expedienteDto, medidaCautelarDTO, tipoDocumento);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros()
						+ "</records>");
				log.debug("Total Pags :: " + pag.getTotalPaginas() + "   Total Registros : : " + pag.getTotalRegistros());
			} else {
				log.debug("- --- - - ");
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}

			for (DocumentoDTO documentoDto : listaDocumentosAsociados) {

				writer.print("<row id='" + documentoDto.getDocumentoId() + "'>");
				writer.print("<cell>" + documentoDto.getStrFechaCreacion()
						+ "</cell>");
				log.debug(documentoDto.getDocumentoId());
				log.debug(documentoDto.getStrFechaCreacion());

				if (documentoDto.getNombreDocumento() != null) {
					writer.print("<cell>" + documentoDto.getNombreDocumento()
							+ "</cell>");
					log.debug(documentoDto.getNombreDocumento());
				} else {
					writer.print("<cell>" + "---" + "</cell>");
					log.debug("---");
				}
				writer.print("</row>");

			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			log.error(e.getCause(), e);
		}

		return null;
	}

	/**
	 * Metodo utilizado para adjuntar un documento y asociarlo a una medida
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward adjuntarDocumentoAMedidaCautelar(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("****EJECUTANDO ADJUNTAR DOCUMENTO A MEDIDA ACTION*****");

			AdjuntarDocumentoAMandamientoJudicialForm formaAdjuntarDocumento = (AdjuntarDocumentoAMandamientoJudicialForm) form;

			ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();
			MedidaDTO medidaDTO = new MedidaDTO();

			medidaDTO.setDocumentoId(formaAdjuntarDocumento
					.getMandamientoJudicialId());

			log.info("Medida Id: " + formaAdjuntarDocumento
					.getMandamientoJudicialId());

			FormFile archivo = formaAdjuntarDocumento.getArchivoAdjunto();
			String fileName = archivo.getFileName();
			byte[] fileData = archivo.getFileData();

			adjunto.setContenido(fileData);

			if (fileName != null) {
				String[] extension = fileName.split("\\.");
				adjunto.setTipoArchivo("." + extension[extension.length - 1]);
				adjunto.setNombreArchivo(extension[0]);
				log.info("El nombre del archivo es: " + extension[0]);
			}

			adjunto.setUsuario(super.getUsuarioFirmado(request));

			DocumentoDTO loDocuemntoDTO = new DocumentoDTO();
			loDocuemntoDTO.setArchivoDigital(adjunto);
			/* Obligatorios de Documento */
			loDocuemntoDTO.setNombreDocumento(adjunto.getNombreArchivo());
			loDocuemntoDTO.setFechaCreacion(new Date());
			// Se asigna el tipo de documento
			loDocuemntoDTO.setTipoDocumentoDTO(new ValorDTO(
					TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()));
			loDocuemntoDTO.setEsGuardadoParcial(false);

			Long idArchivoDig = medidasCaulelaresDelegate.adjuntarDocumentoAMedida
					(loDocuemntoDTO,
							medidaDTO,
							TipoDocumento.ARCHIVO_ADJUNTADO);

			log.info("regreso" + idArchivoDig);
			return mapping.findForward("success");

		} catch (Exception e) {
			log.info(e.getCause(), e);
			return mapping.findForward("fail");
		}

	}


}
