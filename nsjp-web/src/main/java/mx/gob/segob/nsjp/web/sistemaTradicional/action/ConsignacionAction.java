/**
 * Nombre del Programa 	 : ConsignacionAction.java
 * Autor               	 : Alejandro Galaviz
 * Compania            	 : Ultrasist
 * Proyecto            	 : NSJP              			Fecha:14/06/2012
 * Marca de cambio     	 : N/A
 * Descripcion General   : Clase action para consignacion
 * Programa Dependiente  : N/A
 * Programa Subsecuente  : N/A
 * Cond. de ejecucion    : N/A
 * Dias de ejecucion     : N/A                             Horario: N/A
 *                               MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                 :N/A
 * Compania              :N/A
 * Proyecto              :N/A                                   Fecha: N/A
 * Modificacion          :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.sistemaTradicional.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase action para consignacion
 * @author AlejandroGA
 * @version 1.0
 */
public class ConsignacionAction extends GenericAction {
	
	/* Log de clase*/
	private static final Logger log  = Logger.getLogger(ConsignacionAction.class);
	
	@Autowired
	private FuncionarioDelegate funcionarioDelegate;
	
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	
	/***
	 * Funcion para consultar funcionarios por rol y distrito Sistema
	 * Tradicional
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarFuncionariosPorRolYDistrito(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("EJECUTANDO ACTION CONSULTAR FUNCIONARIOS POR ROL Y DISTRITO");

			Long idRol = NumberUtils.toLong(request.getParameter("idRol"), 0L);

			UsuarioDTO usuarioDto = super.getUsuarioFirmado(request);
			List<FuncionarioDTO> listaFuncionarios = new ArrayList<FuncionarioDTO>();

			if (usuarioDto != null
					&& usuarioDto.getFuncionario() != null
					&& usuarioDto.getFuncionario().getDiscriminante() != null
					&& usuarioDto.getFuncionario().getDiscriminante()
							.getDistrito() != null
					&& usuarioDto.getFuncionario().getDiscriminante()
							.getDistrito().getCatDistritoId() != null
					&& idRol > 0L) {

				listaFuncionarios = funcionarioDelegate
						.consultarFuncionariosPorRolyPorDistrito(idRol,
								usuarioDto.getFuncionario().getDiscriminante()
										.getDistrito().getCatDistritoId());
			}

			converter.alias("listaFuncionarios", java.util.List.class);
			converter.alias("FuncionarioDTO", FuncionarioDTO.class);
			String xml = converter.toXML(listaFuncionarios);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.info(e);
		}
		return null;
	}

	/***
	 * Funcion para consultar funcionarios por rol y distrito Sistema
	 * Tradicional
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward generarNumeroExpedienteConsignacion(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("EJECUTANDO ACTION CREAR ASIGNAR NUMERO EXPEDIENTE SISTEMA TRADICIONAL");

			log.info(":::::::::::::::VERIFICANDO PARAMETROS:::::::::::::::::::::::::::");
			Long expedienteId = NumberUtils.toLong(
					request.getParameter("expedienteId"), 0L);
			Long cveFuncionario = NumberUtils.toLong(
					request.getParameter("cveFuncionario"), 0L);

			log.info("expedienteId:::::::::::::::::::::::::::" + expedienteId);
			log.info("cveFuncionario:::::::::::::::::::::::::" + cveFuncionario);

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			UsuarioDTO usuarioDestinatario = new UsuarioDTO();
			FuncionarioDTO funcionarioDto = new FuncionarioDTO();
			String xml = "fail";

			if (expedienteId > 0L && cveFuncionario > 0L) {

				expedienteDTO.setExpedienteId(expedienteId);

				AreaDTO areaExp = new AreaDTO(
						Areas.AGENCIA_DEL_MINISTERIO_PUBLICO);
				expedienteDTO.setArea(areaExp);

				funcionarioDto.setClaveFuncionario(cveFuncionario);
				usuarioDestinatario.setFuncionario(funcionarioDto);
				expedienteDTO.setUsuario(usuarioDestinatario);

				expedienteDTO = expedienteDelegate
						.asignarNumeroExpediente(expedienteDTO);

				if (expedienteDTO != null) {
					xml = "succes";
				}
			}
			converter.alias("respuesta", String.class);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.info(e);
		}
		return null;
	}
	
	/***
	 * Funcion para consultar el estatus del numero expediente por su numero expediente id
	 * Tradicional
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarEstatusNumExpedienteByNumExpedienteId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("EJECUTANDO CONSULTAR ESTATUS NUMERO EXPEDIENTE BY NUMERO EXPEDIENTE ID");

			Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("idNumeroExpedienteOp"), 0L);
			Long estatusExpediente = 0L;
		
			if(numeroExpedienteId != null & numeroExpedienteId > 0L){
				estatusExpediente = expedienteDelegate.consultarEstatusNumeroExpedienteByNumeroExpedienteId(numeroExpedienteId);
			}
			
			if(estatusExpediente != null && estatusExpediente > 0L){
				converter.alias("estatusNumeroExpediente", String.class);
				String xml = converter.toXML(Long.toString(estatusExpediente));
				escribirRespuesta(response, xml);
				
			}
			
		} catch (Exception e) {
			log.info(e);
		}
		return null;
	}
	
	
	/***
	 * Permite consultar numeros de expedientes asociados a un identificador de
	 * expediente que tengan como responsable a un usuario que cuente con el rol
	 * asociado.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward actualizarEstatusNumerosDeExpedientesPorRolST(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("EJECUTANDO ACTUALIZAR ESTATUS NUMEROS DE EXPEDIENTE POR ROL ST");
			log.info("VERIFICANDO PARAMETROS:::::::::");
			log.info("ROLES USUARIO IDS:::::::::::::::::"+request.getParameter("rolesIds"));
			log.info("EXPEDIENTE ID:::::::::::::::::::::"+request.getParameter("expedienteId"));
			log.info("NVO. ESTATUS NUMERO EXPEDIENTE::::"+request.getParameter("nuevoEstatusNumeroExpediente"));
			log.info("NVO. ESTATUS EXPEDIENTE:::::::::::"+request.getParameter("nuevoEstatusExpediente"));

			String rolesIds = request.getParameter("rolesIds");
			Long expedienteId = NumberUtils.toLong(request.getParameter("expedienteId"), 0L);
			Long nuevoEstatusNumeroExpediente = NumberUtils.toLong(request.getParameter("nuevoEstatusNumeroExpediente"), 0L);
			Long nuevoEstatusExpediente = NumberUtils.toLong(request.getParameter("nuevoEstatusExpediente"), 0L);
			
			List<Long> listaRolesLong = new ArrayList<Long>();
			
			if(rolesIds != null){
				//Permite recuperar los id´s de los roles
				String[] listaRoles= rolesIds.split(",");
				
				for (String rolId : listaRoles) {
					listaRolesLong.add(Long.parseLong(rolId));
				}				
			}
					
			if (expedienteId != null & expedienteId > 0L) {
				expedienteDelegate
						.actualizarEstatusNumerosDeExpedientesPorRolST(
								listaRolesLong, expedienteId,
								nuevoEstatusNumeroExpediente,
								nuevoEstatusExpediente);

			}
			
			
		} catch (Exception e) {
			log.info(e);
		}
		return null;
	}
	
}
