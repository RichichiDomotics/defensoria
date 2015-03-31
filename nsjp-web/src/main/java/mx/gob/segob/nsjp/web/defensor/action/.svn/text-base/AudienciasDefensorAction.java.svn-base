package mx.gob.segob.nsjp.web.defensor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.configuracion.ConfiguracionDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class AudienciasDefensorAction extends GenericAction {
	
	private static final Logger log = Logger.getLogger(AdministrarDefensaEjecucionAction.class);
	
	@Autowired
	private AudienciaDelegate audienciaDelegate;
	
	@Autowired
	private ConfiguracionDelegate configuracionDelegate;
	
	public ActionForward consultarDetalleAudienciaDefensoria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			Long idAudiencia = NumberUtils.toLong(request.getParameter("idAudiencia"), -1);
			AudienciaDTO audiencia = new AudienciaDTO();
			audiencia.setId(idAudiencia);
			audiencia = audienciaDelegate.consultarAudienciaFromPoderJudicial(audiencia);
			
			String xml = null;
			PrintWriter pw = null;
			converter.alias("audiencia", AudienciaDTO.class);
			converter.alias("involucrado", InvolucradoViewDTO.class);
			xml = converter.toXML(audiencia);
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();

		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public ActionForward consultarAudienciasDefensor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			Long claveFuncionarioExt=0L;
			String cadenaEstatus="";
			String cadenaTipo="";
			Long confInstId=0L;
			
			
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(
					GenericAction.KEY_SESSION_USUARIO_FIRMADO);
			
			String ne = (String) request.getParameter("numExp");
			
			if(usuarioDTO!=null && usuarioDTO.getFuncionario()!=null &&
					usuarioDTO.getFuncionario().getClaveFuncionario()!=null){
				claveFuncionarioExt = usuarioDTO.getFuncionario().getClaveFuncionario();
			}
			
			cadenaEstatus = EstatusSolicitud.ABIERTA.getValorId().toString() + "," +
							EstatusSolicitud.CERRADA.getValorId().toString() + "," + 
							EstatusSolicitud.EN_PROCESO.getValorId().toString();

			cadenaTipo = TiposSolicitudes.AUDIO_VIDEO.getValorId().toString();
			
			ConfInstitucionDTO confInstDto = configuracionDelegate.consultarInstitucionActual();
			
			if(confInstDto!=null && confInstDto.getConfInstitucionId()!=null){
				confInstId = confInstDto.getConfInstitucionId();
			}						
			
			String fechaInicio = request.getParameter("inicio");
			String fechaFin = request.getParameter("fin");		
			String desarrolloJAVS = request.getParameter("desarrolloJAVS");
			
			log.info("ejecuntando consultarAudienciasDefensor....");
			Calendar calendario = Calendar.getInstance();
			AudienciaDTO audiencia = new AudienciaDTO();
			audiencia.setEstatusAudiencia(new ValorDTO(EstatusAudiencia.PROGRAMADA.getValorId()));
				
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaCreacionInicio = null;
			Date fechaCreacionFin = null;
			if (!confInstId.equals(2L)) {
				if(fechaInicio == null || fechaInicio == ""){
					audiencia.setFechaFiltroInicio(calendario.getTime());
				}
				else{
					fechaCreacionInicio = formato.parse(fechaInicio);
					audiencia.setFechaFiltroInicio(fechaCreacionInicio);
				}
				
				if(fechaFin == null || fechaInicio == ""){
					audiencia.setFechaFiltroFin(calendario.getTime());
				}
				else{
					fechaCreacionFin = formato.parse(fechaFin);
					audiencia.setFechaFiltroFin(fechaCreacionFin);
				}										
			}
			
			//Se obtiene el distrito del usuario firmado en session
			UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
            if (usuarioFirmado.getFuncionario() != null
             && usuarioFirmado.getFuncionario().getDiscriminante() != null
             && usuarioFirmado.getFuncionario().getDiscriminante().getDistrito() != null
             && usuarioFirmado.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId() != null) {
    			log.info("El Distrito del usuario firmado en sesion es: " + usuarioFirmado.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId());
            	audiencia.setIdDistritoFiltroAudiencias(usuarioFirmado.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId());
            }			
			
//			Enable IT: se modifica la informacion a presentar de las audiencias y se realiza la consulta local
            List<AudienciaDTO> audienciaDTOs = audienciaDelegate
					.consultarAudienciasByFechasyFuncionario(
							claveFuncionarioExt, audiencia);
			log.info("Audiencias "+audienciaDTOs);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			final PaginacionDTO pag = PaginacionThreadHolder.get();
			Long lTotalRegistros = (long) audienciaDTOs.size();
			pag.setTotalRegistros(lTotalRegistros);
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<page>" + pag.getPage() + "</page>");
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
            	writer.print("<page>0</page>");
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }
			
					
			String caso = " - ", tipo = " - ", numeroCausa = " - ";
			String fecha = " - ", sala = " - ", juzgado = " - ";
			String numeroExpediente = "";
			String estatus = " - ";
			
			for(AudienciaDTO audienciaDTO : audienciaDTOs){
				if (ne == null || audienciaDTO.getExpediente().getNumeroExpedienteId().equals(Long.parseLong(ne))) {
			
					if(audienciaDTO.getExpediente() !=null && audienciaDTO.getExpediente().getCasoDTO()!=null ){
						caso = audienciaDTO.getExpediente().getCasoDTO().getNumeroGeneralCaso();
					} else {
						caso = " - ";
					}
					if(audienciaDTO.getExpediente() !=null && audienciaDTO.getExpediente().getNumeroExpediente()!=null ){
						numeroExpediente = audienciaDTO.getExpediente().getNumeroExpediente();
					} else {
						numeroExpediente = " - ";
					}
				
				//	falta causa
					if (audienciaDTO.getCausadto() != null && audienciaDTO.getCausadto().getcCausaPenalIdPJ() != null){
						System.out.println("si hay causa + " + audienciaDTO.getCausadto().getcCausaPenalIdPJ());
						numeroCausa = audienciaDTO.getCausadto().getcCausaPenalIdPJ();
					}
					if (audienciaDTO.getCatAudienciadto() != null
							&& audienciaDTO.getCatAudienciadto().getDescripcion() != null) {
						tipo = audienciaDTO.getCatAudienciadto().getDescripcion();
					}
				
					if(audienciaDTO.getStrHoraEvento()!=null && audienciaDTO.getStrFechaEvento()!=null ){
						fecha= audienciaDTO.getStrFechaEvento() +" - "+ audienciaDTO.getStrHoraEvento();
					}
					
					sala = audienciaDTO.getSala().getNombreSala();
//				for(FuncionarioDTO funcionario :  audienciaDTO.getFuncionarios()){
//					if(funcionario.getPuesto().getIdCampo() == Puestos.ABOGADO_DEFENSOR.getValorId()){
//						defensor += " SI ";
//					}
//				}
					juzgado = audienciaDTO.getSala().getCatJuzgadoDTO().getcDescripcion();
					estatus = audienciaDTO.getEstatusAudiencia().getValor();
				
					writer.print("<row id='"+audienciaDTO.getId()+ "'>");
					if(desarrolloJAVS!=null && desarrolloJAVS.equals("TRUE")){
						writer.print("<cell>" + audienciaDTO.getId() + "</cell>");
					}

					writer.print("<cell>" + numeroExpediente + "</cell>");
					writer.print("<cell>" + caso + "</cell>");		
//					writer.print("<cell>" + c + "</cell>");
//					writer.print("<cell>" + tipo + "</cell>");
//					writer.print("<cell>" + fecha + "</cell>");
//					writer.print("<cell>" + sala + "</cell>");
//					writer.print("<cell>" + defensor + "</cell>");
					writer.print("<cell>" + numeroCausa + "</cell>");		
					writer.print("<cell>" + tipo + "</cell>");
					writer.print("<cell>" + fecha + "</cell>");
					writer.print("<cell>" + sala + "</cell>");
					writer.print("<cell>" + juzgado + "</cell>");
					writer.print("<cell>" + estatus + "</cell>");
					writer.print("</row>");
				}
			}
			
			writer.print("</rows>");
			writer.flush();
			writer.close();
			

		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
		return null;
	}



}
