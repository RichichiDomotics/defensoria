package mx.gob.segob.nsjp.web.seguridadPublica.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.delegate.avisodetencion.AvisoDetencionDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.hecho.HechoDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudTrasladoImputadoDelegate;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.hecho.HechoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudTrasladoImputadoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.detencion.ConsultarDetencionService;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class SeguridadPublicaAction extends GenericAction{
	private static final Logger log  = Logger.getLogger(SeguridadPublicaAction.class);

	@Autowired
	ExpedienteDelegate expedienteDelegate;
	
	@Autowired
	InvolucradoDelegate involucradoDelegate;

	@Autowired
	public AvisoDetencionDelegate avisoDetencionDelegate;
	
	@Autowired
	public SolicitudTrasladoImputadoDelegate solicitudTrasladoDelegate;
	
    @Autowired
    private ConsultarDetencionService consultarDetencionService;
    
    @Autowired
    private HechoDelegate hechoDelegate; 
	
	public ActionForward generarExpedienteSSP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action generar expediente");
			
			UsuarioDTO usuario = getUsuarioFirmado(request);
			ExpedienteDTO expediente = new ExpedienteDTO();
			expediente.setUsuario(usuario);
			
			expediente.setFechaApertura(new Date());
			expediente.setArea(new AreaDTO(Areas.POLICIA_SSP));
			ExpedienteDTO expRespuesta = expedienteDelegate.asignarNumeroExpediente(expediente);
			
			//Generamos el expediente con hecho
			if(expRespuesta != null){
				
				HechoDTO hecho = new HechoDTO();
				hecho.setExpediente(expRespuesta);
				hecho = hechoDelegate.ingresarHecho(hecho);
				expRespuesta.setHechoDTO(hecho);
			}
			converter.alias("expedienteDTO", ExpedienteDTO.class);
			converter.alias("hechoDTO", HechoDTO.class);
			String xml = converter.toXML(expRespuesta);
			
			if (log.isDebugEnabled()) {
				log.debug(xml);
			}
			escribirRespuesta(response, xml);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response,null);
		}
		return null;
	}

	/**
	 * MEtodo para Enviar los avisos de detencion de los probables responsables de un IPH
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward enviarAvisosDetencionIPH(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {
			
			
			String numeroExpediente=request.getParameter("numeroExpediente");
			log.info("numero expediente: "+numeroExpediente);
			
			Long idDistrito =NumberUtils.toLong(request.getParameter("idDistrito"),0) ;
			log.info("Identificador del Distrito: "+idDistrito);
			
			ExpedienteDTO expedienteDTO=expedienteDelegate.obtenerNumeroExpedienteByNumExp(new ExpedienteDTO(null,numeroExpediente),null);			
			Long idInvolucrado =NumberUtils.toLong(request.getParameter("idInvolucrado"),0) ;
			
			DetencionDTO detencionDTO = consultarDetencionService.consultarDetencion(idInvolucrado,null);
			log.info("Fecha Inicio Detencion: "+detencionDTO.getFechaInicioDetencion());


			if(idInvolucrado > 0L){

				UsuarioDTO usuarioDTO=super.getUsuarioFirmado(request);
				
				InvolucradoDTO involucradoDTO=new InvolucradoDTO();
				involucradoDTO.setElementoId(idInvolucrado);				
				InvolucradoDTO involucradoIPHDTO = involucradoDelegate.obtenerInvolucrado(involucradoDTO);		
				//Se asigna el expediente al involucrado consultado
				involucradoIPHDTO.setExpedienteDTO(expedienteDTO);
				detencionDTO.setUsuario(usuarioDTO);
				detencionDTO.setInvolucradoDTO(involucradoIPHDTO);
				detencionDTO.setFechaRecepcionDetencion(new Date());
				
				avisoDetencionDelegate.enviarAvisoDetencion(detencionDTO,idDistrito,null,
						(usuarioDTO.getFuncionario()!= null ? usuarioDTO.getFuncionario().getClaveFuncionario(): null));
			}
			
			String xml = "";
			if (log.isDebugEnabled()) {
				log.debug(xml);
			}
			escribirRespuesta(response, xml);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	
	public ActionForward consultarSolicitudesTrasladoPorEstatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			EstatusSolicitud estatusSolicitud = EstatusSolicitud.getByValor(Long.parseLong(request.getParameter("estatus")));
			
			List<SolicitudTrasladoImputadoDTO> solicitudDTOs = new ArrayList<SolicitudTrasladoImputadoDTO>();
			solicitudDTOs = (List<SolicitudTrasladoImputadoDTO>) solicitudTrasladoDelegate.consultarSolicitudTrasladoPorEstatus(estatusSolicitud);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");		

			int lTotalRegistros = solicitudDTOs.size();
			writer.print("<records>" + lTotalRegistros + "</records>");

			for(SolicitudTrasladoImputadoDTO solicitudDTO : solicitudDTOs){

				writer.print("<row id='"+solicitudDTO.getDocumentoId()+ "'>");
				writer.print("<cell>"+ solicitudDTO.getNumeroCasoAsociado()+  "</cell>");
				
				writer.print("<cell>");
				if(solicitudDTO.getDestinatario() != null){
					writer.print(solicitudDTO.getDestinatario().getNombreCompleto());
				}else{
					writer.print("---");
				}
				writer.print("</cell>");

				writer.print("<cell>");
				if(solicitudDTO.getInvolucrado() != null){
					writer.print(solicitudDTO.getInvolucrado().getNombreCompleto());
				}else{
					writer.print("---");
				}
				writer.print("</cell>");

				writer.print("<cell>");
				if(solicitudDTO.getAudiencia() != null){
					writer.print(solicitudDTO.getAudiencia().getFechaEvento());
				}
				writer.print("</cell>");

				writer.print("<cell>");
				if(solicitudDTO.getAudiencia() != null){
					writer.print(solicitudDTO.getAudiencia().getLugarEvento());
				}else{
					writer.print("---");
				}
				writer.print("</cell>");
				
				writer.print("</row>");
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
