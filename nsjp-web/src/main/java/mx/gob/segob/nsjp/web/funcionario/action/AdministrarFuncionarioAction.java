/**
* Nombre del Programa : AdministrarFuncionarioAction.java
* Autor                            : SergioDC
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 20 Jul 2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
* Programa Dependiente  :N/A
* Programa Subsecuente :N/A
* Cond. de ejecucion        :N/A
* Dias de ejecucion          :N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       :N/A
* Compania               :N/A
* Proyecto                 :N/A                                 Fecha: N/A
* Modificacion           :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.funcionario.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.usuario.UsuarioDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatAreasNegocioDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatUIEspecializadaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.PersonaDTO;
import mx.gob.segob.nsjp.dto.persona.TelefonoDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.funcionario.form.FuncionarioForm;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author SergioDC
 *
 */
public class AdministrarFuncionarioAction extends GenericAction{

	private static final Logger log = Logger.getLogger(AdministrarFuncionarioAction.class);
	@Autowired
	private FuncionarioDelegate funcionarioDelegate;
	@Autowired
	private AudienciaDelegate audienciaDelegate;
	@Autowired
	private UsuarioDelegate usuarioDelegate;	
	/**
	 * Metodo utilizado para guardar los datos de un Funcionario
	 *  CU Registrar Nuevo Perito (Funcionario)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null,
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward guardarFuncionario(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			Long idFuncionario = NumberUtils.toLong(request.getParameter("idFuncionario"), 0);			
			log.info("el id del funcionario es"+idFuncionario);
			
			FuncionarioForm forma = (FuncionarioForm)form;
			
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			
			funcionarioDTO.setNumeroEmpleado(forma.getNumeroEmpleado());
			funcionarioDTO.setNombreFuncionario(forma.getNombre());
			funcionarioDTO.setApellidoPaternoFuncionario(forma.getApellidoPaterno());
			funcionarioDTO.setApellidoMaternoFuncionario(forma.getApellidoMaterno());
			funcionarioDTO.setSexo(forma.getSexo());
			funcionarioDTO.setRfc(forma.getRfc());
			funcionarioDTO.setCurp(forma.getCurp());
			funcionarioDTO.setFechaNacimiento(DateUtils.obtener(forma.getFechaNacimiento()));			
						
			if(forma.getFechaIngreso()==null || forma.getFechaIngreso().equals("null") || forma.getFechaIngreso().equals("")){					
				funcionarioDTO.setFechaIngreso(new Date());
			}
			else{
				if(!forma.getFechaIngreso().equals("SIN FECHA")){
					funcionarioDTO.setFechaIngreso(DateUtils.obtener(forma.getFechaIngreso()));
				}
			}
			
			//Area de negocio y el area
			funcionarioDTO.setJerarquiaOrganizacional(null);
			funcionarioDTO.setCatAreaNegocio(new CatAreasNegocioDTO(forma.getIdAreaDeNegocio()));
			
			log.info("area de negocio: "+forma.getIdAreaDeNegocio());
			
			CatDiscriminanteDTO discrimi = new CatDiscriminanteDTO();
			discrimi.setCatDiscriminanteId(forma.getAgenciaId());
			funcionarioDTO.setDiscriminante(discrimi);
			
			//Ingresar CatUIEspecializada
			if(forma.getUnidadInvEspId() != null){
				CatUIEspecializadaDTO catUIEspecializadaDto = new CatUIEspecializadaDTO();
				catUIEspecializadaDto.setCatUIEId(forma.getUnidadInvEspId());
				funcionarioDTO.setUnidadIEspecializada(catUIEspecializadaDto);
			}else{
				funcionarioDTO.setUnidadIEspecializada(null);
			}
			
			ValorDTO valorGenerico = new ValorDTO();
			Long puesto=0L;
			if(forma.getPuesto() !=null){
				puesto=Long.parseLong(forma.getPuesto());
			}else{
				puesto=null;
			}
			valorGenerico.setIdCampo(puesto);
			funcionarioDTO.setPuesto(valorGenerico);

			valorGenerico = new ValorDTO();
			Long tipoEspecialidad=0L;
			if(forma.getTipoEspecialidad() !=null){
				tipoEspecialidad=Long.parseLong(forma.getTipoEspecialidad());
			}else{
				tipoEspecialidad=null;
			}
			valorGenerico.setIdCampo(tipoEspecialidad);
			funcionarioDTO.setTipoEspecialidad(valorGenerico);

			Long especialidad=0L;
			if(forma.getEspecialidad() !=null && !forma.getEspecialidad().isEmpty()){
				especialidad=Long.parseLong(forma.getEspecialidad());
				valorGenerico = new ValorDTO(especialidad);
				funcionarioDTO.setEspecialidad(valorGenerico);
			}
			
			if (StringUtils.isNotBlank(forma.getFuncionario())){
			    funcionarioDTO.setFuncionarioJefe(new FuncionarioDTO(Long.parseLong(forma.getFuncionario())));
			}
			
			PersonaDTO personaDTO = new PersonaDTO();
			ArrayList<TelefonoDTO> telefonosDTOs = new ArrayList<TelefonoDTO>();
			ArrayList<CorreoElectronicoDTO> correoElectronicoDTOs =  new ArrayList<CorreoElectronicoDTO>();
			
			String strTelefonos = forma.getMedioContactoTelefono();
			StringTokenizer lstStrTelefonos = new StringTokenizer(strTelefonos,"|");
			while (lstStrTelefonos.hasMoreElements()) {
				String strTelefono = (String) lstStrTelefonos.nextElement();
				String[] datosTelefono = strTelefono.split(",");

				TelefonoDTO telefono = new TelefonoDTO();

				ValorDTO valorTipoTelefono = new ValorDTO();
				valorTipoTelefono.setIdCampo(new Long(datosTelefono[0]));
				telefono.setValorTipoTelefono(valorTipoTelefono);
				telefono.setCodigoPais(datosTelefono[1]);
				telefono.setCodigoArea(datosTelefono[2]);
				telefono.setNumeroTelefonico(datosTelefono[3]);
				telefonosDTOs.add(telefono);
			}
			if(!telefonosDTOs.isEmpty()){				
				personaDTO.setTelefonosDTO(telefonosDTOs);
			}
			
			if(!forma.getMedioContactoCorreo().trim().isEmpty()){
				String[] datosCorreo = forma.getMedioContactoCorreo().split(",");
				for (int i = 0; i < datosCorreo.length; i++) {
					CorreoElectronicoDTO correo = new CorreoElectronicoDTO();
					correo.setDireccionElectronica(datosCorreo[i]);
					correoElectronicoDTOs.add(correo);					
				}
			}
			
			if(!correoElectronicoDTOs.isEmpty()){				
				personaDTO.setCorreosDTO(correoElectronicoDTOs);
			}

			funcionarioDTO.setPersona(personaDTO);
			
			FuncionarioDTO resp = new FuncionarioDTO();
			if(idFuncionario==0){
					 resp = funcionarioDelegate.ingresarFuncionario(funcionarioDTO);
					}else{
						funcionarioDTO.setClaveFuncionario(idFuncionario);
						 resp = funcionarioDelegate.modificarDefensor(funcionarioDTO);						
					}
			
			log.info("el valor es:" + resp);

			String xml = null;
			PrintWriter pw = null;
			converter.alias("funcionarioDTO",FuncionarioDTO.class);			
			xml = converter.toXML(resp);
			log.info("el valor es:" + xml);
			response.setContentType("text/xml");
			pw = response.getWriter();
			log.info("el valor despues del response es:" + xml);
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	
	/**
	 * Metodo utilizado para guardar los datos de un Funcionario
	 *  CU Registrar Nuevo Perito (Funcionario)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null,
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward guardarFuncionarioPorNombreYPuesto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			String nombreFunc = request.getParameter("nombreFunc");
			String apPatFunc = request.getParameter("apPatFunc");
			String apMatFunc = request.getParameter("apMatFunc");
			Long puesto = NumberUtils.toLong(request.getParameter("puesto"),0);
			Long tipoEspecialidad = NumberUtils.toLong(request.getParameter("tipoEspecialidad"),0);
			Long idAudiencia = NumberUtils.toLong(request.getParameter("idAudiencia"),0);
			Long claveFuncionario = NumberUtils.toLong(request.getParameter("claveFuncionario"),0);
			
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			funcionarioDTO.setNombreFuncionario(nombreFunc);
			funcionarioDTO.setApellidoPaternoFuncionario(apPatFunc);
			funcionarioDTO.setApellidoMaternoFuncionario(apMatFunc);
			funcionarioDTO.setJerarquiaOrganizacional(new JerarquiaOrganizacionalDTO(Areas.PJ.parseLong()));

			ValorDTO valorGenerico = new ValorDTO();
			valorGenerico.setIdCampo(puesto);
			funcionarioDTO.setPuesto(valorGenerico);

			valorGenerico = new ValorDTO();
			if(tipoEspecialidad<1){
				tipoEspecialidad = null;
			}
			valorGenerico.setIdCampo(tipoEspecialidad);
			funcionarioDTO.setTipoEspecialidad(valorGenerico);
			
			if(claveFuncionario < 1){
				claveFuncionario=null;
			}
			funcionarioDTO.setClaveFuncionario(claveFuncionario);
			
			AudienciaDTO audienciaDTO = new AudienciaDTO();
			audienciaDTO.setId(idAudiencia);
			
			//Se ingresa el funcionario con el Discriminante del funcionario que esta en sesion
			UsuarioDTO usuarioDTO=super.getUsuarioFirmado(request);
			if(usuarioDTO != null && usuarioDTO.getFuncionario()!= null && usuarioDTO.getFuncionario().getDiscriminante()!= null){
				funcionarioDTO.setDiscriminante(usuarioDTO.getFuncionario().getDiscriminante());
			}
			
			 audienciaDelegate.ingresarFuncionarioAudiencia(funcionarioDTO, audienciaDTO);
			
			converter.alias("String",String.class);
			String xml = converter.toXML("ok");
			escribir(response, xml,null);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para guardar los datos de un Funcionario
	 *  CU Registrar Nuevo Perito (Funcionario)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null,
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward elimiarFuncionarioDeAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			Long idAudiencia = NumberUtils.toLong(request.getParameter("idAudiencia"),0L);
			Long claveFuncionario = NumberUtils.toLong(request.getParameter("claveFuncionario"),0L);
			
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			funcionarioDTO.setClaveFuncionario(claveFuncionario);
			
			AudienciaDTO audienciaDTO = new AudienciaDTO();
			audienciaDTO.setId(idAudiencia);
			
			audienciaDelegate.eliminarFuncionarioAudiencia(funcionarioDTO, audienciaDTO);
			
			converter.alias("String",String.class);
			String xml = converter.toXML("ok");
			escribir(response, xml,null);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para guardar los datos de asistencia de un Funcionario
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null,
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward registrarAsistenciaFuncionario(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {			
			log.info("Action registrarAsistenciaFuncionario");
			Long claveFuncionario = NumberUtils.toLong(request.getParameter("claveFuncionario"),0);
			log.info("llega claveFuncionario: "+ claveFuncionario);
			Long idAudiencia = NumberUtils.toLong(request.getParameter("idAudiencia"),0);
			log.info("llega idAudiencia: "+ idAudiencia);
			Boolean esTitular = BooleanUtils.toBoolean(request.getParameter("esTitular"), "true", "false");
			log.info("llega esTitular: "+ esTitular);
			Boolean presente = BooleanUtils.toBoolean(request.getParameter("presente"), "true", "false");
			log.info("llega presente: "+ presente);
						
			audienciaDelegate.registrarAsistenciaFuncionario(claveFuncionario, idAudiencia, presente, esTitular);
			
			log.info("Action registrarAsistenciaFuncionario: Asistencia guardada");
			converter.alias("String",String.class);
			String xml = converter.toXML("ok");
			escribir(response, xml,null);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para guardar los datos de asistencia de un Involucrado en audiencia
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null,
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward registrarAsistenciaInvolucradoAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {			
			log.info("Action registrarAsistenciaInvolucradoAudiencia");
			Long claveFuncionario = NumberUtils.toLong(request.getParameter("claveFuncionario"),0);
			log.info("llega claveFuncionario: "+ claveFuncionario);
			Long idAudiencia = NumberUtils.toLong(request.getParameter("idAudiencia"),0);
			log.info("llega idAudiencia: "+ idAudiencia);
			Boolean presente = BooleanUtils.toBoolean(request.getParameter("presente"), "true", "false");
			log.info("llega presente: "+ presente);
						
			audienciaDelegate.registrarAsistenciaInvolucrado(claveFuncionario, idAudiencia, presente);
			
			log.info("Action registrarAsistenciaFuncionario: Asistencia guardada");
			converter.alias("String",String.class);
			String xml = converter.toXML("ok");
			escribir(response, xml,null);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para modificar los datos de un Funcionario
	 *  CU modificar defensor (Funcionario)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null,
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward modificarFuncionario(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			FuncionarioForm forma = (FuncionarioForm)form;
			String funcionarioId = request.getParameter("funcionarioId");
			FuncionarioDTO funcionarioConsulta = new FuncionarioDTO();
			funcionarioConsulta.setClaveFuncionario(Long.parseLong(funcionarioId));
			
			FuncionarioDTO funcionarioDTO = funcionarioDelegate.obtenerInformacionDefensor(funcionarioConsulta);
			
			ValorDTO valorGenerico = new ValorDTO();

			Long tipoEspecialidad=0L;
			if(forma.getTipoEspecialidad() !=null){
				tipoEspecialidad=Long.parseLong(forma.getTipoEspecialidad());
			}else{
				tipoEspecialidad=null;
			}
			valorGenerico.setIdCampo(tipoEspecialidad);
			funcionarioDTO.setTipoEspecialidad(valorGenerico);

			Long especialidad=0L;			
			if(forma.getEspecialidad() !=null && !forma.getEspecialidad().isEmpty() ){
				especialidad=Long.parseLong(forma.getEspecialidad());
				valorGenerico = new ValorDTO(especialidad);
				funcionarioDTO.setEspecialidad(valorGenerico);
			}

			PersonaDTO personaDTO = new PersonaDTO();
			ArrayList<TelefonoDTO> telefonosDTOs = new ArrayList<TelefonoDTO>();
			ArrayList<CorreoElectronicoDTO> correoElectronicoDTOs =  new ArrayList<CorreoElectronicoDTO>();
			
			String strTelefonos = forma.getMedioContactoTelefono();
			StringTokenizer lstStrTelefonos = new StringTokenizer(strTelefonos,"|");
			while (lstStrTelefonos.hasMoreElements()) {
				String strTelefono = (String) lstStrTelefonos.nextElement();
				String[] datosTelefono = strTelefono.split(",");

				TelefonoDTO telefono = new TelefonoDTO();

				ValorDTO valorTipoTelefono = new ValorDTO();
				valorTipoTelefono.setIdCampo(new Long(datosTelefono[0]));
				telefono.setValorTipoTelefono(valorTipoTelefono);
				telefono.setCodigoPais(datosTelefono[1]);
				telefono.setCodigoArea(datosTelefono[2]);
				telefono.setNumeroTelefonico(datosTelefono[3]);
				telefonosDTOs.add(telefono);
			}
			if(!telefonosDTOs.isEmpty()){				
				personaDTO.setTelefonosDTO(telefonosDTOs);
			}
			
			if(!forma.getMedioContactoCorreo().trim().isEmpty()){
				String[] datosCorreo = forma.getMedioContactoCorreo().split(",");
				for (int i = 0; i < datosCorreo.length; i++) {
					CorreoElectronicoDTO correo = new CorreoElectronicoDTO();
					correo.setDireccionElectronica(datosCorreo[i]);
					correoElectronicoDTOs.add(correo);					
				}
			}
			
			if(!correoElectronicoDTOs.isEmpty()){				
				personaDTO.setCorreosDTO(correoElectronicoDTOs);
			}

			funcionarioDTO.setPersona(personaDTO);
			
			
			FuncionarioDTO resp = funcionarioDelegate.modificarDefensor(funcionarioDTO);
			log.info("el valor de la respuesta actualizada:" + resp.getClaveFuncionario());

			String xml = null;
			PrintWriter pw = null;
			converter.alias("FuncionarioForm",FuncionarioForm.class);
			
			FuncionarioForm retorno = new FuncionarioForm();
			xml = converter.toXML(retorno);
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
	
	public ActionForward ingresarImagenDelElementoFuncionario(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long idFuncionario = null;		
		Long administrador = null;
		String comparaUsuario ="";
		log.debug("yolot::: entro a ingresarImagenDelElementoFuncionario" );
		try {			
			FuncionarioForm forma = (FuncionarioForm) form;
			int valor=0;			
			idFuncionario=NumberUtils.toLong(request.getParameter("idFuncionario"),0);				
			administrador=NumberUtils.toLong(request.getParameter("administrador"),0);
			
			//Permite guardar la foto de un elemento
			if(forma!=null)
				if(forma.getArchivo()!=null)
				valor=forma.getArchivo().getFileSize();	
			
			
			if(forma.getArchivo() != null && valor != 0 ){				
				ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();

				FormFile archivo = forma.getArchivo();
		        adjunto.setContenido(archivo.getFileData());
				adjunto.setNombreArchivo(archivo.getFileName());
				adjunto.setTipoArchivo(archivo.getContentType());
				adjunto.setUsuario(super.getUsuarioFirmado(request));
				log.debug("yolot::: ants de adjuntarArchivoAFuncionario" );
				funcionarioDelegate.adjuntarArchivoAFuncionario(idFuncionario, adjunto);		
				
			}
						
		} catch (Exception e) {
			log.error(e.getMessage(), e);			
		}	
		comparaUsuario=request.getParameter("comparaUsuario");
		return new ActionForward(mapping.findForward("success").getPath()+"?idFuncionario=" + idFuncionario+"&administrador=" + administrador+"&comparaUsuario="+comparaUsuario, false);
	}	
	
	
	public ActionForward consultarConsignadores(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			Long idRol = NumberUtils.toLong(request.getParameter("idRol"),0);
			Long idDistrito = NumberUtils.toLong(request.getParameter("idDistrito"),0);
			
			List<FuncionarioDTO> funcionarioDTOs = funcionarioDelegate.consultarFuncionariosPorRolyPorDistrito(idRol, idDistrito);
			if(log.isDebugEnabled()){				
			    log.debug("Funcionarios: " + funcionarioDTOs.size());
			}				
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
			
			for(FuncionarioDTO funcionarioDTO : funcionarioDTOs){
					writer.print("<row id='"+funcionarioDTO.getClaveFuncionario()+ "'>");
					writer.print("<cell>"+funcionarioDTO.getNombreFuncionario()+ " " + funcionarioDTO.getApellidoPaternoFuncionario() + " " + 
										  funcionarioDTO.getApellidoMaternoFuncionario() +							
								 "</cell>");
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

	/**
	 * 
	 * M&eacute;todo que inicializa la consulta de funcionarios para la reasignaci&oacute;n de expedientes.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	
	public ActionForward cambiarResponsableExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			FuncionarioForm funcionarioForm = (FuncionarioForm)form;
			String genralamp=request.getParameter("ampGeneral");			
			if(funcionarioForm.getFuncionarioDTO() == null){				
				funcionarioForm.setFuncionarioDTO(new FuncionarioDTO());
			}
			request.setAttribute("ampGeneral", genralamp);
		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
		
		return mapping.findForward("inicializar");			
	}
	
	/**
	 * 
	 * M&eacute;todo que consulta los funcionarios por filtro
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	
	public ActionForward consultarFuncionariosFiltro(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			Long idRol = NumberUtils.toLong(request.getParameter("idRol"),0);
			Long esCordinadorGeneralAMP = NumberUtils.toLong(request.getParameter("esCordinadorGeneralAMP"),0L);
			Boolean esGrid = request.getParameter("esGrid")!= null ? Boolean.parseBoolean(request.getParameter("esGrid")): Boolean.FALSE;
			FuncionarioForm funcionarioForm = (FuncionarioForm)form;

			if(funcionarioForm.getFuncionarioDTO() != null){				
				
				FuncionarioDTO funcionarioDTO = funcionarioForm.getFuncionarioDTO();
				RolDTO rolDTO = new RolDTO();
				rolDTO.setRolId(idRol);
				funcionarioDTO.setEsCoordinadoGenaral(esCordinadorGeneralAMP);
				List<FuncionarioDTO> funcionarioDTOs = funcionarioDelegate.consultarFuncionarioXFiltroYRol(funcionarioDTO, rolDTO);
				if(log.isDebugEnabled()){				
				    log.debug("Funcionarios: " + funcionarioDTOs.size());
				}				
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				
				if (esGrid){
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
					
					for(FuncionarioDTO tempDTO : funcionarioDTOs){
							writer.print("<row id='"+tempDTO.getClaveFuncionario()+ "'>");
							writer.print("<cell>"+tempDTO.getClaveFuncionario()	+ "</cell>");
							writer.print("<cell>"+tempDTO.getNombreFuncionario()+ " " + tempDTO.getApellidoPaternoFuncionario() + " " + 
												  tempDTO.getApellidoMaternoFuncionario() +	"</cell>");
	
							UsuarioDTO usuarioDTO = tempDTO.getUsuario();
							if (usuarioDTO != null 
									&& usuarioDTO.getUsuarioRoles() != null
									&& !usuarioDTO.getUsuarioRoles()
											.isEmpty()) {
								for (UsuarioRolDTO element : usuarioDTO
										.getUsuarioRoles()) {
									if (element.getEsPrincipal()) {
										usuarioDTO.setRolActivo(element.getRol().getDescripcionRol());
										break;
									}
								}
							}
	
							if(usuarioDTO != null && usuarioDTO.getRolActivo() != null){
								writer.print("<cell>" + usuarioDTO.getRolActivo() + "</cell>");
							} else {
								writer.print("<cell> --- </cell>");
							}
	
							DepartamentoDTO departamentoDTO = tempDTO.getDepartamento();
							AreaDTO areaDTO = departamentoDTO != null ? departamentoDTO.getArea() : null;
							if(areaDTO != null && areaDTO.getNombre() != null) {
								writer.print("<cell>" + areaDTO.getNombre() + "</cell>");	
							} else {
								writer.print("<cell> --- </cell>");
							}
	
							writer.print("</row>");
					}
					writer.print("</rows>");
				} else {					
					converter.alias("funcionarioDTOs", java.util.List.class);
					converter.alias("FuncionarioDTO", FuncionarioDTO.class);
					String xml = converter.toXML(funcionarioDTOs);
					writer.append(xml);
				}
				writer.flush();
				writer.close();
			}

		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
		return null;
	}		
}
