package mx.gob.segob.nsjp.web.notificacion.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.delegate.notificacion.NotificacionDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.CatFormaNotificacionDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jacob Lobaco
 */
public class GenerarNotificacionAction extends GenericAction {

    private static final Logger logger =
            Logger.getLogger(GenerarNotificacionAction.class);
    @Autowired
    private NotificacionDelegate notificacionDelegate;

    public ActionForward armaNotificacion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("request = " + request);
            }
            FuncionarioDTO funcionarioDto = getUsuarioFirmado(request).getFuncionario();
            NotificacionDTO notificacionDto = notificacionDelegate.consultarUltimaNotificacionPorAnio(funcionarioDto);
            converter.alias("Notificacion", NotificacionDTO.class);
            notificacionDto.setMotivo("Informe de notificación");
            String notificacionXml = converter.toXML(notificacionDto);
            if (logger.isDebugEnabled()) {
                logger.debug("notificacionXml = " + notificacionXml);
            }
            converter.alias("Funcionario", FuncionarioDTO.class);
            String funcionarioXML = converter.toXML(funcionarioDto);
            if (logger.isDebugEnabled()) {
                logger.debug("funcionarioXML = " + funcionarioXML);
            }
            escribirRespuesta(response, notificacionXml + funcionarioXML);
        } catch (NSJPNegocioException ex) {
            java.util.logging.Logger.getLogger(GenerarNotificacionAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ActionForward consultaDestinatarios(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<Destinatario> destinatarios = new LinkedList<Destinatario>();
        int total = 12;
        for(int i = 0; i < total; ++i){
            Destinatario destinatario = new Destinatario();
            destinatario.setClaveFuncionario((long)i);
            destinatario.setNombreFuncionario("Cosme" + i);
            DepartamentoDTO departamentoDTO = new DepartamentoDTO();
            AreaDTO areaDTO = new AreaDTO();
            areaDTO.setNombre("area" + i);
            departamentoDTO.setArea(areaDTO);
            destinatario.setDepartamento(departamentoDTO);
            destinatario.setEmail(i + "@ultra.com");
            destinatarios.add(destinatario);
        }
        Grid<Destinatario> paginaGrid = new Grid<Destinatario>
                //colNames:['idDestinatario','Nombre','Puesto', 'Correo Electrónico', 'Principal', "Copia"],
                (1, 32, 10, destinatarios, "claveFuncionario", "nombreFuncionario", "departamento.area.nombre", "email", "principal", "copia");
        if (logger.isDebugEnabled()) {
            logger.debug("paginaGrid = " + paginaGrid);
        }
        escribirRespuesta(response, paginaGrid.toString());
        return null;
    }

    private class Destinatario extends FuncionarioDTO{
        private Boolean principal = false;
        private Boolean copia = false;

        public Boolean getCopia() {
            return copia;
        }

        public void setCopia(Boolean copia) {
            this.copia = copia;
        }

        public Boolean getPrincipal() {
            return principal;
        }

        public void setPrincipal(Boolean principal) {
            this.principal = principal;
        }

    }
    
    public ActionForward generarNotificacion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	try {
			
			logger.info("Generar Notificacion");
			
			String documentoId = request.getParameter("documentoId");
			logger.info("id documentoId.... "+ documentoId);
			String idEvento = request.getParameter("id");
			logger.info("id de evento.... "+ idEvento);
			String elementoId = request.getParameter("elementoId");
			logger.info("id elementoId.... "+ elementoId);
			String esFuncionario = request.getParameter("esFuncionario");
			logger.info("esFuncionario.... "+ esFuncionario);
			Long formaNotificacion = NumberUtils.toLong(request.getParameter("formaNotificacion"), 0L);
			
			NotificacionDTO notificacionDTO = new NotificacionDTO();
			AudienciaDTO audienciaDTO = new AudienciaDTO();
			audienciaDTO.setId(Long.parseLong(idEvento));
						
			notificacionDTO.setEstatus(new ValorDTO(EstatusNotificacion.EN_PROCESO.getValorId()));
			
			if(formaNotificacion != null && formaNotificacion > 0L){
				
				CatFormaNotificacionDTO catFormaNotificacionDTO = new CatFormaNotificacionDTO();
				catFormaNotificacionDTO.setCatFormaNotificacionId(formaNotificacion);
				notificacionDTO.setCatFormaNotificacionDTO(catFormaNotificacionDTO);
			}
			
			Long notificacion = 0L;
			if(!esFuncionario.equals("1")){
				InvolucradoDTO involucradoDTO = new InvolucradoDTO();
				involucradoDTO.setElementoId(Long.parseLong(elementoId));
				notificacion = notificacionDelegate.guardarNotificacion(notificacionDTO, audienciaDTO, involucradoDTO);
			}else{
				FuncionarioDTO funcionario = new FuncionarioDTO();
				String nombre = request.getParameter("nombre");
				funcionario.setNombreCompleto(nombre);
				logger.info("nombre.... "+ nombre);
				
				String institucion = request.getParameter("institucion");
				funcionario.setInstitucion(new ConfInstitucionDTO(Long.parseLong(institucion)));
				logger.info("institucion.... "+ institucion);
				
				funcionario.setClaveFuncionario(Long.parseLong(elementoId));
				notificacion = notificacionDelegate.guardarNotificacion(notificacionDTO, audienciaDTO, funcionario);
			}							
			String xml = null;
			PrintWriter pw = null;
			//converter.alias("defensorDTOs",  java.util.List.class);
			converter.alias("NotificacionDTO", NotificacionDTO.class);
			xml = converter.toXML(notificacion);
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			logger.info("resultado de la Notificacion" + notificacion );	
						
			

		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		}
		return null;	
    }
    
    /**
     * Metodo para actualizar los datos de una notificacion (generico)
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward actualizarNotificacion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	try {
			
			logger.info("*******************ACTUALIZAR NOTIFICACION********************************");
			logger.info("VERIFICANDO PARAMETOS:::::::::::::::::::::::::::::::::::::::::::::::::::::");
			
			Long documentoId = NumberUtils.toLong(request.getParameter("idNotificacion"),0L);
			logger.info("id documentoId.... "+ documentoId);
			
			String fechaRecepcion = request.getParameter("fechaRecepcion");
			logger.info("id fechaRecepcion.... "+ fechaRecepcion);
			
			String horaRecepcion = request.getParameter("horaRecepcion");
			logger.info("id horaRecepcion.... "+ horaRecepcion);
			
			Long estatusNotifId = NumberUtils.toLong(request.getParameter("estatusNotifId"),0L);
			logger.info("id estatusNotifId.... "+ estatusNotifId);
			
			String motivoCancelacion = request.getParameter("motivoCancelacion");
			logger.info("id motivoCancelacion.... "+ motivoCancelacion);
			
			NotificacionDTO notificacionDTO = new NotificacionDTO();
			
			if(documentoId != null && documentoId > 0L){
				notificacionDTO.setDocumentoId(documentoId);
			}
			
			if (fechaRecepcion != null && horaRecepcion != null
					&& !fechaRecepcion.isEmpty() && !horaRecepcion.isEmpty()) {
				
				Date fecha = DateUtils.obtener(fechaRecepcion, horaRecepcion);
				notificacionDTO.setFechaCitado(fecha);
			}
			
			if(estatusNotifId != null && estatusNotifId > 0L){
				ValorDTO estatus = new ValorDTO();
				estatus.setIdCampo(estatusNotifId);
				notificacionDTO.setEstatus(estatus);
			}
			
			if(motivoCancelacion != null && !motivoCancelacion.isEmpty()){
				notificacionDTO.setMotivo(motivoCancelacion);
			}
			
			/**
			 * Se pueden agregar los otros campos de la notificacion,
			 * solo verificar que se encuentren en el transformer
			 */
			
			notificacionDelegate.actualizarNotificacion(notificacionDTO);
			
			String xml = null;
			converter.alias("NotificacionDTO", NotificacionDTO.class);
			xml = converter.toXML(notificacionDTO);
			escribirRespuesta(response, xml);
			
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		}
		return null;	
    }
    
    public ActionForward enviarNotificacionByWebService(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	try {

			Long idNotificacion = NumberUtils.toLong(request.getParameter("documentoId"),0);
			logger.info("id documentoId.... "+ idNotificacion);
			if(idNotificacion > 0){	
				NotificacionDTO notificacionDTO = new NotificacionDTO();
				notificacionDTO.setDocumentoId(idNotificacion);
				
				Long idAudiencia = NumberUtils.toLong(request.getParameter("id"), 0);
				
				AudienciaDTO audienciaDTO = new AudienciaDTO();
				audienciaDTO.setId(idAudiencia);
				
				FuncionarioDTO funcionario = new FuncionarioDTO();
				funcionario.setNombreCompleto(request.getParameter("nombre"));
				
				Long institucion = NumberUtils.toLong(request.getParameter("institucion"),0);
				funcionario.setInstitucion(new ConfInstitucionDTO(institucion));
				
				this.notificacionDelegate.enviarNotificacion(idNotificacion, idAudiencia, funcionario.getNombreCompleto(), Instituciones.getByValor(institucion));
				
				
			}
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		}
		return null;	
    }
    
}