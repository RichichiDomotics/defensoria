package mx.gob.segob.nsjp.web.solicitud.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.solicitud.PermisoSolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.utilerias.Paginacion;
import mx.gob.segob.nsjp.web.utilerias.PaginarGrid;

public class ConsultarTodasSolicitudesUAVDAction extends GenericAction {
    private static final Logger logger = Logger.getLogger(ConsultarTodasSolicitudesUAVDAction.class);

    @Autowired
    private SolicitudDelegate solicitudDelegate;
    @Autowired
    private FuncionarioDelegate funcionarioDelegate;
    @Autowired
    private ExpedienteDelegate expedienteDlegate;

    public ActionForward cargarGridTodasSolicitudesCoordinador(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp){
        try{
            List<PermisoSolicitudDTO> solicitudesPropias = obtenerSolicitudesPropiasCoordinador(req);

            Long coordinadorId = getUsuarioFirmado(req).getFuncionario().getClaveFuncionario();
            List<PermisoSolicitudDTO> solicitudesCompartidas = obtenerSolicitudesCompartidasFuncionario(coordinadorId);

            List<PermisoSolicitudDTO> todasSolicitudes = quitarSolicitudesDuplicadas(solicitudesPropias, solicitudesCompartidas);

            resp.setContentType("text/xml; charset=UTF-8");
            resp.setHeader("Cache-Control", "no-cache");

            PrintWriter writer = resp.getWriter();
            writer.print("<rows>");
            PaginacionDTO pag = PaginacionThreadHolder.get();

            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }
            //for (PermisoSolicitudDTO permisoSolicitudDTO : solicitudesPropiasPaginadas) {
            for (PermisoSolicitudDTO permisoSolicitudDTO : todasSolicitudes) {
                SolicitudDTO solicitudDTO = permisoSolicitudDTO.getSolicitudDTO();
                //obtenemos el expediente relacionado a la solicitud
                ExpedienteDTO expediente= new ExpedienteDTO();
                expediente.setArea(new AreaDTO(Areas.COORDINACION_ATENCION_VICTIMAS.parseLong()));
                expediente.setNumeroExpedienteId(solicitudDTO.getExpedienteDTO().getNumeroExpedienteId());
                logger.info("Id de numero Expediente "+solicitudDTO.getExpedienteDTO().getNumeroExpedienteId());
                Long idExpe=expedienteDlegate.obtenerExpedienteIdPorNumExpId(expediente);
                logger.info("Id de Expediente "+idExpe);
                expediente.setExpedienteId(idExpe);
                expediente.setInvolucradosSolicitados(true);
                //obtenemos el expediente
                expediente = expedienteDlegate.obtenerExpediente(expediente);

                writer.print("<row id='"+solicitudDTO.getDocumentoId()+","+expediente.getExpedienteId()+","+expediente.getNumeroExpediente()+","+expediente.getNumeroExpedienteId()+","+solicitudDTO.getTipoSolicitudDTO().getIdCampo()+"'>");
                writer.print("<cell><![CDATA[<div class='celdaGrid'>"+solicitudDTO.getFolioSolicitud()+" </div>]]></cell>");
                writer.print("<cell><![CDATA[<div class='celdaGrid'>"+DateUtils.formatear(solicitudDTO.getFechaCreacion())+" </div>]]></cell>");
                writer.print("<cell><![CDATA[<div class='celdaGrid'>"+DateUtils.formatearHora(solicitudDTO.getFechaCreacion())+" </div>]]></cell>");

                converter.alias("expedienteDTO", ExpedienteDTO.class);
                if(expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA)==null || expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA).size()==0)
                {
                    if (expediente
                            .getInvolucradoByCalidad(Calidades.DENUNCIANTE) != null
                            && expediente.getInvolucradoByCalidad(
                                    Calidades.DENUNCIANTE).size() > 0
                            && expediente
                                    .getInvolucradoByCalidad(
                                            Calidades.DENUNCIANTE).get(0)
                                    .getCondicion().equals(new Short("1"))) {

                        writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2;color:#393939;'>"
                                + expediente
                                        .getInvolucradoByCalidad(
                                                Calidades.DENUNCIANTE).get(0)
                                        .getNombreCompleto()
                                + " </div]]></cell>");
                    } else {
                        writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>---</div]]></cell>");
                    }
                }
                else
                {
                    writer.print("<cell><![CDATA[<div class='celdaGrid'>"+expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA).get(0).getNombreCompleto()+" </div>]]></cell>");
                }
                if(expediente.getDelitos()==null || expediente.getDelitos().size()==0)
                {
                    writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
                }
                else
                {
                    writer.print("<cell><![CDATA[<div class='celdaGrid'>"+expediente.getDelitos().get(0).getCatDelitoDTO().getNombre()+" </div>]]></cell>");
                }
                if(solicitudDTO.getAreaOrigen()==null || solicitudDTO.getAreaOrigen().longValue()==0)
                {
                    writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
                }
                else
                {
                //obtenemos el id del area y el texto
                Long uno=solicitudDTO.getAreaOrigen().longValue();
                Areas[] ar=Areas.values();
                String nombreArea="";
                for (Areas areas : ar)
                {
                       if(areas.parseLong().equals(uno))
                       {
                           nombreArea=areas.name();
                     }
                 }
                writer.print("<cell><![CDATA[<div class='celdaGrid'>"+nombreArea+" </div>]]></cell>");
                }
                writer.print("</row>");
            }
            writer.print("</rows>");
            writer.flush();
            writer.close();
        }catch(Exception e){
            logger.error("Error al cargar grid de solicitudes propias y compartidas del Coordinador de UAVD", e);
        }
        return mapping.findForward("success");
    }

    public ActionForward cargarGridTodasSolicitudesSubAreas(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp){
        try{
            List<PermisoSolicitudDTO> solicitudesPropias = obtenerSolicitudesPropiasSubAreas(req);

            Long subordinadoId = getUsuarioFirmado(req).getFuncionario().getClaveFuncionario();
            List<PermisoSolicitudDTO> solicitudesCompartidas = obtenerSolicitudesCompartidasFuncionario(subordinadoId);

            List<PermisoSolicitudDTO> todasSolicitudes = quitarNumExpedientesDuplicados(solicitudesPropias, solicitudesCompartidas);

            //Enable JC. Calcular valores de paginacion (nueva clase).
            Paginacion paginacion = PaginarGrid.calcularPaginacion(req, todasSolicitudes.size());
            List<PermisoSolicitudDTO> solicitudesPropiasPaginadas = PaginarGrid.<PermisoSolicitudDTO>obtenerElementosSegunPaginacion(paginacion, todasSolicitudes);

            resp.setContentType("text/xml; charset=UTF-8");
            resp.setHeader("Cache-Control", "no-cache");

            PrintWriter writer = resp.getWriter();
            writer.print("<rows>");
            writer.print("<total>" + paginacion.getTotalPaginas() + "</total>");
            writer.print("<records>" + paginacion.getTotalRegistros() + "</records>");
            for (PermisoSolicitudDTO permisoSol : solicitudesPropiasPaginadas) {
                ExpedienteDTO expediente = permisoSol.getSolicitudDTO().getExpedienteDTO();

                //obtenemos la solicitud relacionada al expediente
                expediente.setInvolucradosSolicitados(true);
                expediente=expedienteDlegate.obtenerExpediente(expediente);
                List<SolicitudDTO> listaSolicitudes=solicitudDelegate.consultarSolicitudesPorExpediente(expediente);
                SolicitudDTO solicitudDTO=new SolicitudDTO();
                if(listaSolicitudes!=null && listaSolicitudes.size()>0)
                {
                    solicitudDTO=listaSolicitudes.get(0);

                    writer.print("<row id='"+solicitudDTO.getDocumentoId()+","+expediente.getExpedienteId()+","+expediente.getNumeroExpediente()+","+expediente.getNumeroExpedienteId()+","+solicitudDTO.getTipoSolicitudDTO().getIdCampo()+"'>");
                    writer.print("<cell><![CDATA[<div class='celdaGrid'>"+expediente.getNumeroExpediente()+" </div>]]></cell>");
                    writer.print("<cell><![CDATA[<div class='celdaGrid'>"+DateUtils.formatear(solicitudDTO.getFechaCreacion())+" </div>]]></cell>");
                    converter.alias("expedienteDTO", ExpedienteDTO.class);
                    if(expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA)==null || expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA).size()==0)
                    {
                        if (expediente.getInvolucradoByCalidad(Calidades.DENUNCIANTE) != null
                                && expediente.getInvolucradoByCalidad(
                                        Calidades.DENUNCIANTE).size() > 0
                                && expediente
                                        .getInvolucradoByCalidad(
                                                Calidades.DENUNCIANTE).get(0)
                                        .getCondicion().equals(new Short("1"))) {

                            writer.print("<cell><![CDATA[<div class='celdaGrid'>"
                                    + expediente
                                            .getInvolucradoByCalidad(
                                                    Calidades.DENUNCIANTE)
                                            .get(0).getNombreCompleto()
                                    + " </div]]></cell>");
                        } else {
                            writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div]]></cell>");
                        }
                    }
                    else
                    {
                        writer.print("<cell><![CDATA[<div class='celdaGrid'>"+expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA).get(0).getNombreCompleto()+" </div>]]></cell>");
                    }
                    if(expediente.getDelitos()==null || expediente.getDelitos().size()==0)
                    {
                        writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
                    }
                    else
                    {
                        writer.print("<cell><![CDATA[<div class='celdaGrid'>"+expediente.getDelitos().get(0).getCatDelitoDTO().getNombre()+" </div>]]></cell>");
                    }
                    writer.print("</row>");
                }
            }
            writer.print("</rows>");
            writer.flush();
            writer.close();
        }catch(Exception e){
            logger.error("Error al cargar solicitudes propias y compartidas de las areas de UAVD", e);
        }

        return mapping.findForward("success");
    }

    public ActionForward cargarGridSubordinadosUAVD(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp){
        try{
            List<FuncionarioDTO> subordinados = funcionarioDelegate.obtenerSubordinadosUAVD();

            //Enable JC. Calcular valores de paginacion (nueva clase).
            Paginacion paginacion = PaginarGrid.calcularPaginacion(req, subordinados.size());
            List<FuncionarioDTO> subordinadosPaginados = PaginarGrid.<FuncionarioDTO>obtenerElementosSegunPaginacion(paginacion, subordinados);

            resp.setContentType("text/xml; charset=UTF-8");
            resp.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = resp.getWriter();
            writer.print("<rows>");
            writer.print("<total>" + paginacion.getTotalPaginas() + "</total>");
            writer.print("<records>" + paginacion.getTotalRegistros() + "</records>");

            for (FuncionarioDTO funcionario : subordinadosPaginados) {
                logger.info("id_funcionario_cargarGridSubordinados:: "+funcionario.getClaveFuncionario());
                writer.print("<row id='"+funcionario.getClaveFuncionario()+"'>");
                writer.print("<cell>"+funcionario.getNombreCompleto()+"</cell>");
                if(funcionario.getJerarquiaOrganizacional() != null){
                    writer.print("<cell>"+funcionario.getJerarquiaOrganizacional().getNombre()+"</cell>");
                }else{
                    writer.print("<cell>-</cell>");
                }
                writer.print("</row>");
            }

            writer.print("</rows>");
            writer.flush();
            writer.close();
        }catch(Exception e){
            logger.error("Error al cargar grid de subordinados en UAVD al asignar permisos a subordinados",e);
        }

        return mapping.findForward("success");
    }

    public ActionForward cargarGridSolicitudesCompartidasSubordinado(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
        	UsuarioDTO usrActual = super.getUsuarioFirmado(request);
            Long idAreaActual = usrActual.getAreaActual().getAreaId();
        	Long subordinadoId = NumberUtils.toLong(request.getParameter("claveFuncionario"),0);


            List<PermisoSolicitudDTO> permisoSolicitudes = obtenerSolicitudesCompartidasFuncionario(subordinadoId);

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

            for (PermisoSolicitudDTO permisoSolicitud : permisoSolicitudes) {
            	SolicitudDTO solicitudDTO = permisoSolicitud.getSolicitudDTO();
            	ExpedienteDTO expediente = solicitudDTO.getExpedienteDTO();
            	writer.print("<row id='"+solicitudDTO.getDocumentoId()+","+expediente.getExpedienteId()+","+expediente.getNumeroExpediente()+","+expediente.getNumeroExpedienteId()+","+solicitudDTO.getTipoSolicitudDTO().getIdCampo()+"'>");
                if(idAreaActual.intValue() == 12)
                	writer.print("<cell>"+permisoSolicitud.getSolicitudDTO().getFolioSolicitud()+"</cell>");
                else
                	writer.print("<cell>"+permisoSolicitud.getSolicitudDTO().getExpedienteDTO().getNumeroExpediente()+"</cell>");

                writer.print("<cell>"+DateUtils.formatear(permisoSolicitud.getFechaLimite())+"</cell>");

                writer.print("<cell> SI </cell>");

                if(permisoSolicitud.getEsEscritura()){
                    writer.print("<cell> SI </cell>");
                }else{
                    writer.print("<cell> NO </cell>");
                }
                writer.print("</row>");
            }

            writer.print("</rows>");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return mapping.findForward("success");
    }

    public ActionForward cargarGridMenuSolicitudesCompartidas(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
    	try {
    		UsuarioDTO usrActual = getUsuarioFirmado(request);
    		Long funcionarioId = usrActual.getFuncionario().getClaveFuncionario();
    		long areaFunc = usrActual.getAreaActual().getAreaId();
            List<PermisoSolicitudDTO> solicitudesCompartidas = obtenerSolicitudesCompartidasFuncionario(funcionarioId);
            if(areaFunc == 12) construirGridMenuSolicitudesCompartidasCoordinador(response, solicitudesCompartidas);
            else construirGridMenuSolicitudesCompartidasSubAreas(response, solicitudesCompartidas);

		} catch (Exception e) {
			logger.error("Error al cargar pestaña de solicitudes compartidas",e);
		}
		return null;
    }

    private void construirGridMenuSolicitudesCompartidasCoordinador(HttpServletResponse response,
    			List<PermisoSolicitudDTO> solicitudesCompartidas) throws IOException, NSJPNegocioException{
    	//generamos el HTML del grid
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();

		writer.print("<rows>");
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		logger.debug("pag :: " + pag);
        if (pag != null && pag.getTotalRegistros() != null) {
            writer.print("<total>" + pag.getTotalPaginas() + "</total>");
            writer.print("<records>" + pag.getTotalRegistros() + "</records>");
        } else {
            writer.print("<total>0</total>");
            writer.print("<records>0</records>");
        }

		for (PermisoSolicitudDTO permSolDTO : solicitudesCompartidas) {
			SolicitudDTO solicitudDTO = permSolDTO.getSolicitudDTO();
			//obtenemos el expediente relacionado a la solicitud
			ExpedienteDTO expediente= new ExpedienteDTO();
			expediente.setArea(new AreaDTO(Areas.COORDINACION_ATENCION_VICTIMAS.parseLong()));
			expediente.setNumeroExpedienteId(solicitudDTO.getExpedienteDTO().getNumeroExpedienteId());
			logger.info("Id de numero Expediente "+solicitudDTO.getExpedienteDTO().getNumeroExpedienteId());
			Long idExpe=expedienteDlegate.obtenerExpedienteIdPorNumExpId(expediente);
			logger.info("Id de Expediente "+idExpe);
			expediente.setExpedienteId(idExpe);
			expediente.setInvolucradosSolicitados(true);
			//obtenemos el expediente
			expediente = expedienteDlegate.obtenerExpediente(expediente);

			writer.print("<row id='"+solicitudDTO.getDocumentoId()+","+expediente.getExpedienteId()+","+expediente.getNumeroExpediente()+","+expediente.getNumeroExpedienteId()+","+solicitudDTO.getTipoSolicitudDTO().getIdCampo()+"'>");
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"+solicitudDTO.getFolioSolicitud()+" </div>]]></cell>");
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"+DateUtils.formatear(solicitudDTO.getFechaCreacion())+" </div>]]></cell>");
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"+DateUtils.formatearHora(solicitudDTO.getFechaCreacion())+" </div>]]></cell>");


			converter.alias("expedienteDTO", ExpedienteDTO.class);
			logger.info("expedienteDTO_UAVD:: "+converter.toXML(expediente));
			if(expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA)==null || expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA).size()==0)
			{
				if (expediente
						.getInvolucradoByCalidad(Calidades.DENUNCIANTE) != null
						&& expediente.getInvolucradoByCalidad(
								Calidades.DENUNCIANTE).size() > 0
						&& expediente
								.getInvolucradoByCalidad(
										Calidades.DENUNCIANTE).get(0)
								.getCondicion().equals(new Short("1"))) {

					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2;color:#393939;'>"
							+ expediente
									.getInvolucradoByCalidad(
											Calidades.DENUNCIANTE).get(0)
									.getNombreCompleto()
							+ " </div]]></cell>");
				} else {
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>---</div]]></cell>");
				}
			}
			else
			{
				writer.print("<cell><![CDATA[<div class='celdaGrid'>"+expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA).get(0).getNombreCompleto()+" </div>]]></cell>");
			}
			if(expediente.getDelitos()==null || expediente.getDelitos().size()==0)
			{
				writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
			}
			else
			{
				writer.print("<cell><![CDATA[<div class='celdaGrid'>"+expediente.getDelitos().get(0).getCatDelitoDTO().getNombre()+" </div>]]></cell>");
			}
			if(solicitudDTO.getAreaOrigen()==null || solicitudDTO.getAreaOrigen().longValue()==0)
			{
				writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
			}
			else
			{
			//obtenemos el id del area y el texto
			Long uno=solicitudDTO.getAreaOrigen().longValue();
		    Areas[] ar=Areas.values();
		    String nombreArea="";
		    for (Areas areas : ar)
		    {
		       	if(areas.parseLong().equals(uno))
		       	{
		       		nombreArea=areas.name();
		     	}
		     }
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"+nombreArea+" </div>]]></cell>");
			}
			writer.print("</row>");
		}
		writer.print("</rows>");
		writer.flush();
		writer.close();
    }

    private void construirGridMenuSolicitudesCompartidasSubAreas(HttpServletResponse response,
			List<PermisoSolicitudDTO> solicitudesCompartidas) throws NSJPNegocioException, IOException{
    	//generamos el HTML del grid
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();

		writer.print("<rows>");
		int lTotalRegistros=solicitudesCompartidas.size();
		writer.print("<records>" + lTotalRegistros + "</records>");

		for (PermisoSolicitudDTO permSolDTO : solicitudesCompartidas) {
			ExpedienteDTO expediente = permSolDTO.getSolicitudDTO().getExpedienteDTO();
			//obtenemos la solicitud relacionada al expediente
			expediente.setInvolucradosSolicitados(true);
			expediente = expedienteDlegate.obtenerExpediente(expediente);
			List<SolicitudDTO> listaSolicitudes = solicitudDelegate.consultarSolicitudesPorExpediente(expediente);
			SolicitudDTO solicitudDTO=new SolicitudDTO();
			if(listaSolicitudes!=null && listaSolicitudes.size()>0)
			{
				solicitudDTO=listaSolicitudes.get(0);

				writer.print("<row id='"+solicitudDTO.getDocumentoId()+","+expediente.getExpedienteId()+","+expediente.getNumeroExpediente()+","+expediente.getNumeroExpedienteId()+","+solicitudDTO.getTipoSolicitudDTO().getIdCampo()+"'>");
				writer.print("<cell><![CDATA[<div class='celdaGrid'>"+expediente.getNumeroExpediente()+" </div>]]></cell>");
				writer.print("<cell><![CDATA[<div class='celdaGrid'>"+DateUtils.formatear(solicitudDTO.getFechaCreacion())+" </div>]]></cell>");
				converter.alias("expedienteDTO", ExpedienteDTO.class);
				if(expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA)==null || expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA).size()==0)
				{
					if (expediente.getInvolucradoByCalidad(Calidades.DENUNCIANTE) != null
							&& expediente.getInvolucradoByCalidad(
									Calidades.DENUNCIANTE).size() > 0
							&& expediente
									.getInvolucradoByCalidad(
											Calidades.DENUNCIANTE).get(0)
									.getCondicion().equals(new Short("1"))) {

						writer.print("<cell><![CDATA[<div class='celdaGrid'>"
								+ expediente
										.getInvolucradoByCalidad(
												Calidades.DENUNCIANTE)
										.get(0).getNombreCompleto()
								+ " </div]]></cell>");
					} else {
						writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div]]></cell>");
					}
				}
				else
				{
					writer.print("<cell><![CDATA[<div class='celdaGrid'>"+expediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA).get(0).getNombreCompleto()+" </div>]]></cell>");
				}
				if(expediente.getDelitos()==null || expediente.getDelitos().size()==0)
				{
					writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
				}
				else
				{
					writer.print("<cell><![CDATA[<div class='celdaGrid'>"+expediente.getDelitos().get(0).getCatDelitoDTO().getNombre()+" </div>]]></cell>");
				}
				writer.print("</row>");
			}
		}
		writer.print("</rows>");
		writer.flush();
		writer.close();
    }



    private List<PermisoSolicitudDTO> obtenerSolicitudesCompartidasFuncionario(Long funcionarioId){
        try{
            return funcionarioDelegate.consultarSolicitudesConPermisoFuncionario(funcionarioId);
        }catch(Exception e){
            logger.error("Error al obtener solicitudes compartidas del funcionario con id"+funcionarioId, e);
        }
        return new ArrayList<PermisoSolicitudDTO>();
    }

    private List<PermisoSolicitudDTO> obtenerSolicitudesPropiasCoordinador(HttpServletRequest request){
        List<PermisoSolicitudDTO> permisosSol = new ArrayList<PermisoSolicitudDTO>();
        try{
            String tipoSolicitud=request.getParameter("tipoSoliciutd");
            String estatusSolicitud=request.getParameter("estatus");
            //construimos la lista de los tipos de solicitud a consultar
            String[] idTiposols=tipoSolicitud.split(",");
            logger.info("idsTipoSols:: "+tipoSolicitud);
            List<Long> idsTipSols=new ArrayList<Long>();
            logger.info("arrIDsTipoSols:: "+idTiposols);
            for (String long1 : idTiposols) {
                idsTipSols.add(Long.parseLong(long1));
            }

            //construimos la lista de los estatus a consultar
            String[] idEstatus=estatusSolicitud.split(",");
            List<Long> idsEstatus=new ArrayList<Long>();
            logger.info("arrIDsEstatus:: "+idEstatus);
            for (String long1 : idEstatus) {
                idsEstatus.add(Long.parseLong(long1));
            }

            List<SolicitudDTO> listaSolicitudes = solicitudDelegate.consultarSolicitudesParaAtender(idsEstatus,idsTipSols, getUsuarioFirmado(request).getAreaActual().getAreaId(), null,null);
            permisosSol = convertirSolicitudesAPermisos(listaSolicitudes);
        } catch (Exception e) {
            logger.info(e.getCause(),e);
        }
        return permisosSol;
    }

    private List<PermisoSolicitudDTO> obtenerSolicitudesPropiasSubAreas(HttpServletRequest request){
        List<PermisoSolicitudDTO> permisosSol = new ArrayList<PermisoSolicitudDTO>();
        try {
            logger.info("ejecutando UnidadAtencionVictimasDelitoAction - consultarInicialExpPsicologicosUAVDGrid:#####");

            String idCadenaArea= request.getParameter("cadenaArea");

            FiltroExpedienteDTO filtroExpedienteDTO=new FiltroExpedienteDTO();
            filtroExpedienteDTO.setAnio(new Long(Calendar.getInstance().get(Calendar.YEAR)));
            logger.info("cadenaArea_UAVD:: "+idCadenaArea);
            if(idCadenaArea.equals("PSICO"))
            {
                filtroExpedienteDTO.setIdArea(new Long(Areas.COORDINACION_ATENCION_VICTIMAS.ordinal()));
                filtroExpedienteDTO.setIdActividad(Actividades.ASIGNAR_SOLICITUD_DE_AYUDA.getValorId());
            }
            else if(idCadenaArea.equals("TRABSOC"))
            {
                filtroExpedienteDTO.setIdArea(new Long(Areas.COORDINACION_ATENCION_VICTIMAS.ordinal()));
                filtroExpedienteDTO.setIdActividad(Actividades.ENVIAR_ESTUDIO_SOCIOECONOMICO.getValorId());
            }
            else if(idCadenaArea.equals("ATNJUR"))
            {
                filtroExpedienteDTO.setIdArea(new Long(Areas.COORDINACION_ATENCION_VICTIMAS.ordinal()));
                filtroExpedienteDTO.setIdActividad(Actividades.PROPORCIONAR_APOYO_LEGAL.getValorId());
            }

            UsuarioDTO usuarioDTO =  getUsuarioFirmado(request);
            filtroExpedienteDTO.setUsuario(usuarioDTO);

            //lista de los expedientes
            List<ExpedienteDTO> listaExpedienteDTOs = expedienteDlegate.consultarExpedienteActividadAreaAnio(filtroExpedienteDTO);
            permisosSol = convertirExpedientesAPermisos(listaExpedienteDTOs);
        }catch(Exception e){
            logger.error("Error al cargar grid de expedientes propios de las areas de uavd",e);
        }
        return permisosSol;
    }

    private List<PermisoSolicitudDTO> convertirSolicitudesAPermisos(List<SolicitudDTO> solicitudes){
        List<PermisoSolicitudDTO> permisosSol = new ArrayList<PermisoSolicitudDTO>();
        if(solicitudes != null){
            for(SolicitudDTO solDTO : solicitudes){
                PermisoSolicitudDTO permSolDTO = new PermisoSolicitudDTO();
                permSolDTO.setSolicitudDTO(solDTO);
                permisosSol.add(permSolDTO);
            }
        }
        return permisosSol;
    }

    private List<PermisoSolicitudDTO> convertirExpedientesAPermisos(List<ExpedienteDTO> expedientes) throws NSJPNegocioException{
        List<PermisoSolicitudDTO> permisosSol = new ArrayList<PermisoSolicitudDTO>();
        SolicitudDTO solDTO = new SolicitudDTO();
        if(expedientes != null){
            for(ExpedienteDTO expDTO : expedientes){
                List<SolicitudDTO> listaSolicitudes = solicitudDelegate.consultarSolicitudesPorExpediente(expDTO);
                if(listaSolicitudes != null && listaSolicitudes.size() > 0){
                    solDTO = listaSolicitudes.get(0);
                }
                solDTO.setExpedienteDTO(expDTO);
                PermisoSolicitudDTO permSolDTO = new PermisoSolicitudDTO();
                permSolDTO.setSolicitudDTO(solDTO);
                permisosSol.add(permSolDTO);
            }
        }
        return permisosSol;
    }

    private List<PermisoSolicitudDTO> quitarSolicitudesDuplicadas(List<PermisoSolicitudDTO> permisosPropios, List<PermisoSolicitudDTO> permisosCompartidos){
    	List<PermisoSolicitudDTO> listaSinDuplicados = new ArrayList<PermisoSolicitudDTO>();
    	if(permisosPropios != null && permisosCompartidos != null){
    		listaSinDuplicados.addAll(permisosPropios);
    		for(PermisoSolicitudDTO permSol : permisosCompartidos){
    			if(esCandidatoParaAgregarASolicitudesPropias(permisosPropios, permSol))
    				listaSinDuplicados.add(permSol);
    		}
    	}
    	return listaSinDuplicados;
    }

    private List<PermisoSolicitudDTO> quitarNumExpedientesDuplicados(List<PermisoSolicitudDTO> permisosPropios, List<PermisoSolicitudDTO> permisosCompartidos){
    	List<PermisoSolicitudDTO> listaSinDuplicados = new ArrayList<PermisoSolicitudDTO>();
    	if(permisosPropios != null && permisosCompartidos != null){
    		listaSinDuplicados.addAll(permisosPropios);
    		for(PermisoSolicitudDTO permSol : permisosCompartidos){
    			if(estaEnListaExpedientesPropios(permisosPropios, permSol)){
    				listaSinDuplicados.add(permSol);
    			}
    		}
    	}
    	return listaSinDuplicados;
    }

    private boolean esCandidatoParaAgregarASolicitudesPropias(List<PermisoSolicitudDTO> permisosPropios, PermisoSolicitudDTO permisoCompartido){
		for(PermisoSolicitudDTO permSolPropio : permisosPropios){
			if(permisoCompartido.getSolicitudDTO().getFolioSolicitud().equals(permSolPropio.getSolicitudDTO().getFolioSolicitud())){
				return false;
			}
		}
		return true;
    }

    private boolean estaEnListaExpedientesPropios(List<PermisoSolicitudDTO> permisosPropios, PermisoSolicitudDTO permisoCompartido){
		for(PermisoSolicitudDTO permSolPropio : permisosPropios){
			if(permisoCompartido.getSolicitudDTO().getExpedienteDTO().getNumeroExpediente()
					.equals(permSolPropio.getSolicitudDTO().getExpedienteDTO().getNumeroExpediente())){
				return false;
			}
		}
		return true;
    }
}
