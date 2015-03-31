package mx.gob.segob.nsjp.web.evento.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.metamodel.SetAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.OrigenExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.delegate.caso.CasoDelegate;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.sentencia.SentenciaDelegate;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.TurnoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoAudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.caso.form.BuscarCasosForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrarSentenciaAction  extends GenericAction{

	
	@Autowired
	public CasoDelegate casoDelegate;
	
	@Autowired
	public NumeroExpedienteDAO numeroExpedienteDAO;
	
	@Autowired
	public SentenciaDelegate sentenciaDelegate;
	

	@Autowired
	public CatalogoDelegate catalogoDelegate;
	
	@Autowired
	public ExpedienteDelegate expedienteDelegate;
	
	private static final Logger log = Logger.getLogger(RegistrarSentenciaAction.class);
	
	public ActionForward validaVisor(ActionMapping mapping,ActionForm form, HttpServletRequest request,	HttpServletResponse response){

		Long valor=Long.parseLong(request.getParameter("val"));
		String respuesta = null;
		
		log.debug("Valor de id desde jsp ::"+valor);
		
		try{
		
			if(valor != null ){
				/*q el caso exista*/
				if(valor ==1){
					respuesta="caso";	
				}else if(valor ==2){
				/*el caso no existe*/
					respuesta="nocaso";
				}
			}else{
				log.debug("El parametro llego null");
			}
			
		}catch(Exception e) {
		    log.error(e.getMessage(), e);
		    }
		
		return mapping.findForward(respuesta);
		
	}
	
	
	public ActionForward obtenerNumeroExpedienteId(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)throws IOException {
	
		Long idExpediente=Long.parseLong(request.getParameter("idExpediente"));

		if(idExpediente!= null ){
		log.debug("Expediente Id ::"+idExpediente);
	
		NumeroExpediente ne=numeroExpedienteDAO.obtenerNumeroExpedienteXExpediente(idExpediente);
			
				
		log.debug("numeroExpedienteId :: "+ne.getNumeroExpedienteId().toString());
			
		//request.getSession().setAttribute("numeroExpedienteIdEna", ne.getNumeroExpedienteId());
		
		String xml="<neid>"+ne.getNumeroExpedienteId()+"</neid>";
		
		
		if(log.isDebugEnabled()){
			log.info(xml);			
		}
		
		response.setContentType("text/xml");
		escribirRespuesta(response, xml);
		
		
		}
		
		return null;

	
	}
	
	public ActionForward cargaExpedientes(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)throws IOException {
		
		log.info("EJECUTANDO ACTION BUSQUEDA INICIAL Expedientes");
		
		String numerocaso=request.getParameter("numerocaso");
		
		log.debug("numerocaso ::"+numerocaso);
		
		CasoDTO caso=new CasoDTO();
		caso.setNumeroGeneralCaso(numerocaso);
		
		List<ExpedienteDTO> listaExpedientes;
		List<CatalogoDTO> listaPenas;
		
		try {
			
			if(numerocaso !=null && numerocaso != "" ){
				
				listaExpedientes=expedienteDelegate.consultarExpedientesPorCasoEna(caso);
				  
				
				converter.alias("ListaExpedientes",java.util.List.class);
				converter.alias("ExpedienteDTO", ExpedienteDTO.class);
				log.debug(converter.toXML(listaExpedientes));
				//response.setContentType("text/xml");
				//escribirRespuesta(response, converter.toXML(listaExpedientes));
				
				String listExpXML=converter.toXML(listaExpedientes);
				
				
				/*Obteniendo el tipo de penas */
				listaPenas=catalogoDelegate.recuperarCatalogoCompleto(Catalogos.TIPO_SENTENCIA);
				
				String listPenasXML=converter.toXML(listaPenas);
				
				
				converter.alias("ListaPenas", java.util.List.class);
				converter.alias("penasDTO", CatalogoDTO.class);
				log.debug(converter.toXML(listaPenas));

				String listCompleto=listExpXML+listPenasXML;
				
				response.setContentType("text/xml");
				//escribirRespuesta(response, converter.toXML(listaPenas));
				escribirRespuesta(response, listCompleto);
							
				
				
			}
			
			
		} catch (NSJPNegocioException e) {
			escribirError(response, e);
			log.fatal(e);
			log.error(e);
		}
		return null;
		
	}
	
	public ActionForward cargaCasos(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)throws IOException {
	
		log.info("EJECUTANDO ACTION BUSQUEDA INICIAL CASOS");
		
		List<CasoDTO> listaCasos;
		try {
	
			listaCasos = casoDelegate.consultarCasos();
			
			converter.alias("casos", java.util.List.class);
			converter.alias("caso", CasoDTO.class);
			log.debug(converter.toXML(listaCasos));
			response.setContentType("text/xml");
			escribirRespuesta(response, converter.toXML(listaCasos));
			
		} catch (NSJPNegocioException e) {
			escribirError(response, e);
			log.fatal(e);
			log.error(e);
		}

		
		return null;
		
	}
	
	@SuppressWarnings({ "deprecation", "null" })
	public ActionForward guardarSentencia(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)throws  NSJPNegocioException {
		
		
		log.debug("/*******************************************************/");
		log.debug("/*				GUARDAR SENTENCIA ACTIION			  */");
		log.debug("/*******************************************************/");
		
		Date finicio;
		Date ffin;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		
		//bandara que indica de donde proviene
		//1-> existe en sigi
		//2-> nueva sentencia
		String respuesta=request.getParameter("respuesta");
		
		String caso=request.getParameter("numeroCaso");
		Long involucradosId=Long.parseLong(request.getParameter("involucrados"));
		String fechainiciosen=request.getParameter("fechaInicio");
		String fechafinsen=request.getParameter("fechaFin");
		String delito=request.getParameter("delito");
		String cNUS=request.getParameter("cNUS");
		Long tipoPenaVal=Long.parseLong(request.getParameter("tipoPenaval"));

		log.info("**********************************************************");
		log.info("Numero de caso desde la vista :: "+ caso);
		log.info("involucradoID :: "+ involucradosId);
		log.info("fecha inicio sentencia:: "+ fechainiciosen);
		log.info("fecha fin sentencia :: "+ fechafinsen);
		log.info("delito :: "+ delito);
		log.info("numero unico de sentencia:: "+ cNUS);
		log.info("Pena Val:: "+ tipoPenaVal);
		log.info("**********************************************************");

		Long numeroExpedienteId = null;
		String numeroExpediente=null;
		
		ExpedienteDTO exp=new ExpedienteDTO();
		SentenciaDTO sentencia=new SentenciaDTO();
	if(respuesta != null){	
		
		if (respuesta.equals("2")){ //Es por fuera ,Si ya genere el caso ,expediente y numero de expediente solo almaceno la sentencia

			numeroExpediente=request.getParameter("numeroExpediente");
			log.info("Numero Expediente :: "+ numeroExpediente);
			
			if(request.getParameter("numeroExpedienteId")!=null){
				
				numeroExpedienteId=Long.parseLong(request.getParameter("numeroExpedienteId"));
				log.info("numero ExpedienteId:: "+ numeroExpedienteId);
				
				exp.setNumeroExpedienteId(numeroExpedienteId);
				exp.setNumeroExpediente(numeroExpediente);	
				sentencia.setCnumeroCausa(numeroExpediente);
				sentencia.setNumeroExpedienteDTO(exp);
			}else{
//				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}	
		}
		
		if(respuesta.equals("1")){
			
			UsuarioDTO usuarioDTO =  getUsuarioFirmado(request);

			/*LEER EL CASO A PARTIR DEL # DE CASO DE LA VISTA */
			CasoDTO casoDTO=new CasoDTO();
			casoDTO.setNumeroGeneralCaso(caso);
			log.info("Agregando al expedienteDTO el caso # :: "+caso);
			exp.setCasoDTO(casoDTO);
			exp.setUsuario(usuarioDTO);
			
			exp.setArea(new AreaDTO(Areas.SSP));
			exp = expedienteDelegate.generarExpediente(exp);
			exp.setConsulta(false);
			sentencia.setCnumeroCausa(exp.getNumeroExpediente());
			sentencia.setNumeroExpedienteDTO(exp);
		
		}
		
	}else{ 
		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}	
	
		
		try {
			

				finicio=dateFormat.parse(fechainiciosen);
				ffin=dateFormat.parse(fechafinsen);
				
				ValorDTO tipoSen=new ValorDTO();
				tipoSen.setIdCampo(tipoPenaVal);

				InvolucradoDTO inv=new InvolucradoDTO();
				inv.setElementoId(involucradosId);

				sentencia.setValorDTO(tipoSen);
				sentencia.setInvolucradoDTO(inv);
				sentencia.setDfechaInicioPena(finicio);
				sentencia.setDfechaFinPena(ffin);
				sentencia.setBlesionado(false);
				sentencia.setIpuntosPorAcumular(1000L);
				sentencia.setBcumplida(false);
				sentencia.setCnus(cNUS);
				
				
				sentencia=sentenciaDelegate.realizarAltaSentencia(sentencia);
				
				if(sentencia.getSentenciaId()!= null){
					log.debug("Sentencia Creada correctamente con el ID :: "+sentencia.getSentenciaId());
					
					converter.alias("SentenciaDTO", SentenciaDTO.class);
					String xml = converter.toXML(sentencia);
					log.info("Sentencia:: "+xml);
					//mandamos la respuesta al cliente
					escribir(response, xml,null);
					
					
					
				}else{
					log.debug("Algo paso.... \n No se pudo crear la sentencia..");
				}
		
		} catch (NSJPNegocioException e) {
			escribirError(response, e);
			log.fatal(e);
			log.error(e);
		}catch (ParseException e){
			e.printStackTrace();
		}
		
		
		return null;
		
	}

	
	public ActionForward generarCasoExpediente(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)throws IOException {
		
		log.info("EJECUTANDO ACTION Genera CASO  y Expediente :::");

		try {
			
			log.info("ejecutando Action consultar Action en el metodo nuevoExpedienteREinsercion");
			ExpedienteDTO expedienteDTO=new ExpedienteDTO();
//			expedienteDTO.setArea(new AreaDTO(Areas.UNIDAD_INVESTIGACION));
			expedienteDTO.setFechaApertura(new Date());
			UsuarioDTO usuario=super.getUsuarioFirmado(request);
			expedienteDTO.setArea(usuario.getAreaActual());
			usuario.getFuncionario().setDepartamento(new DepartamentoDTO(usuario.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId(),null));
			usuario.getFuncionario().getDepartamento().setArea(new AreaDTO(usuario.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId()));
			expedienteDTO.setUsuario(usuario);
			
			/**********************************************************/
			String numExpedNuevo=request.getParameter("numeroExpediente");
			
			String casoNuevo=request.getParameter("casoNuevo");
			
			//Long delitoId=Long.parseLong(request.getParameter("delito").trim());
			
			//Long invol=Long.parseLong(request.getParameter("involucradoId"));
			
			
			if(numExpedNuevo != null && casoNuevo!= null /*&& delitoId !=null*/ ){
				
				expedienteDTO.setNumeroExpediente(numExpedNuevo);
				CasoDTO casoDTO=new CasoDTO();
				casoDTO.setNumeroGeneralCaso(casoNuevo);
				log.info("Agregando al expedienteDTO el caso # :: "+casoNuevo);
				expedienteDTO.setCasoDTO(casoDTO);
				
				/*
				DelitoDTO del=new DelitoDTO();
				del.setDelitoId(delitoId);
				expedienteDTO.setDelitoPrincipal(del);
				*/
				//e xpedienteDTO.setInputado(new InvolucradoDTO(invol));
			}
			/**********************************************************/
			expedienteDTO = expedienteDelegate.generarExpediente(expedienteDTO);
			expedienteDTO.setConsulta(false);
			
			request.getSession().setAttribute("expedienteID", expedienteDTO.getExpedienteId());
			
			
			
			converter.alias("expedienteDTO", ExpedienteDTO.class);
			String xml = converter.toXML(expedienteDTO);
			
			
			
			
			log.info("XML:::\n"+xml);
			
			//super.escribirRespuesta(response, xml);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
		
	}
	
		

	public ActionForward crearRelacionDelPer(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)throws IOException {
		
		log.info("Action Relacion Delito-Persona....");

		try {
		
			
			/**********************************************************/
			String idExpediente=request.getParameter("idExpediente");
			
			//String casoNuevo=request.getParameter("casoNuevo");
			
			Long delitoId=Long.parseLong(request.getParameter("delito").trim());
			
			Long invol=Long.parseLong(request.getParameter("involucradoId"));
			
			ExpedienteDTO expedienteDTO=new ExpedienteDTO();
			expedienteDTO.setExpedienteId(Long.parseLong(idExpediente));
			
			InvolucradoDTO inv=new InvolucradoDTO();
			inv.setElementoId(invol);
			
			if(idExpediente != null && invol!= null && delitoId !=null ){
				
				DelitoDTO del=new DelitoDTO();
				del.setDelitoId(delitoId);
				expedienteDTO.setDelitoPrincipal(del);
				expedienteDTO.setInputado(new InvolucradoDTO(invol));
				//expedienteDTO.addInvolucradoDTO(inv);
				
			}
			/**********************************************************/
			expedienteDTO=expedienteDelegate.relacion(expedienteDTO);
			
			
			converter.alias("expedienteDTO", ExpedienteDTO.class);
			String xml = converter.toXML(expedienteDTO);
			
			log.info("XML:::\n"+xml);
			
			//super.escribirRespuesta(response, xml);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
		
	}
	
}
