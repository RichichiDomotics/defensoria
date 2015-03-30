/**
* Nombre del Programa : ModificarIndividuoServiceImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 9 May 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servicio para realizar la actualizacion de la informacion de un individuo
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
package mx.gob.segob.nsjp.service.involucrado.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.enums.relacion.TipoRelacion;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.involucrado.AliasInvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.DefensaInvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.DetencionDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoNacionalidadDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoOcupacionDAO;
import mx.gob.segob.nsjp.dao.involucrado.ServidorPublicoDAO;
import mx.gob.segob.nsjp.dao.persona.CorreoElectronicoDAO;
import mx.gob.segob.nsjp.dao.persona.NombreDemograficoDAO;
import mx.gob.segob.nsjp.dao.persona.PersonaDAO;
import mx.gob.segob.nsjp.dao.persona.TelefonoDAO;
import mx.gob.segob.nsjp.dao.relacion.RelacionDAO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.model.AliasInvolucrado;
import mx.gob.segob.nsjp.model.Calidad;
import mx.gob.segob.nsjp.model.CorreoElectronico;
import mx.gob.segob.nsjp.model.DefensaInvolucrado;
import mx.gob.segob.nsjp.model.Detencion;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.InvolucradoNacionalidad;
import mx.gob.segob.nsjp.model.InvolucradoNacionalidadId;
import mx.gob.segob.nsjp.model.InvolucradoOcupacion;
import mx.gob.segob.nsjp.model.InvolucradoOcupacionId;
import mx.gob.segob.nsjp.model.MediaFiliacion;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.Persona;
import mx.gob.segob.nsjp.model.Relacion;
import mx.gob.segob.nsjp.model.SeniaParticular;
import mx.gob.segob.nsjp.model.ServidorPublico;
import mx.gob.segob.nsjp.model.Telefono;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.domicilio.IngresarDomicilioService;
import mx.gob.segob.nsjp.service.domicilio.ModificarDomicilioServicio;
import mx.gob.segob.nsjp.service.involucrado.EliminarInvolucradoService;
import mx.gob.segob.nsjp.service.involucrado.ModificarIndividuoService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.DetencionTransformer;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.organizacion.EliminarOrganizacionService;
import mx.gob.segob.nsjp.service.organizacion.IngresarOrganizacionService;
import mx.gob.segob.nsjp.service.organizacion.ModificarOrganizacionService;
import mx.gob.segob.nsjp.service.relacion.GenerarRelacionService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para realizar la actualizacion de la informacion de un individuo.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Service
@Transactional
public class ModificarIndividuoServiceImpl implements ModificarIndividuoService {

	/**
	 *
	 */
	private final static Logger logger = Logger.getLogger(ModificarIndividuoServiceImpl.class);

	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private AliasInvolucradoDAO aliasInvolucradoDAO;
	@Autowired
	private InvolucradoNacionalidadDAO involucradoNacionalidadDAO;
	@Autowired
	private InvolucradoOcupacionDAO involucradoOcupacionDAO;
	@Autowired
	private TelefonoDAO telefonoDAO;
	@Autowired
	private CorreoElectronicoDAO correoElectronicoDAO;
	@Autowired
	private RelacionDAO relacionDAO;
	@Autowired
	private NombreDemograficoDAO nombreDemograficoDAO;
	@Autowired
	private PersonaDAO personaDAO;
	@Autowired
	private ServidorPublicoDAO servidorPublicoDAO;
	@Autowired
	private EliminarInvolucradoService eliminarInvolucradoService;
	@Autowired
	private EliminarOrganizacionService eliminarOrganizacionService;
	@Autowired
	private IngresarOrganizacionService ingresarOrganizacionService;
	@Autowired
	private ModificarOrganizacionService modificarOrganizacionService;
	@Autowired
	private ModificarDomicilioServicio modificarDomicilioServicio;
	@Autowired
	private IngresarDomicilioService ingresarDomicilioService;
	@Autowired
	private GenerarRelacionService generarRelacionService;
	@Autowired
	private DefensaInvolucradoDAO defensaInvolucradoDAO;
	@Autowired
	private DetencionDAO detencionDAO;
	

	@Override
	public Long actualizarIndividuo(InvolucradoDTO involucradoDTO) throws NSJPNegocioException {

		if (logger.isDebugEnabled()){
			logger.debug("/**** SERVICIO PARA MODIFICACION DE INVOLUCRADOS ****/");
		}
		if (!(involucradoDTO.getCalidadDTO()!=null)){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		//Obtener datos involucrado actual registrado en BD
		Involucrado invoBD = involucradoDAO.read(involucradoDTO.getElementoId());
		logger.info("Expediente : " + invoBD.getExpediente().getExpedienteId());

		//Convertir el involucradoDTO a involucrado
		Involucrado invoMod = InvolucradoTransformer.transformarInvolucrado(involucradoDTO);
		logger.info("involucrado DEFENSORIA ----- " + invoMod.getNombreDemograficos());
		Long idCalidadInv = invoMod.getCalidad().getTipoCalidad().getValorId();


		//OSD NSJP 47 Modificar denunciante
//		if (idCalidadInv.equals(Calidades.DENUNCIANTE.getValorId())) {
//			if (logger.isDebugEnabled())
//				logger.debug("/**** EL INDVIDUO ES UN DENUNCIANTE ****/");
//
//			if (invoBD.getCondicion()!= null && invoMod.getCondicion()!= null && !(invoBD.getCondicion().equals(invoMod.getCondicion()))) {
//				if (logger.isDebugEnabled())
//					logger.debug("/**** LA RELACION DENUNCIANTE-VICTIMA FUE MODIFICADA ****/");
//				if (invoMod.getCondicion().intValue()==1) {
//					List<Involucrado> listVictimas = involucradoDAO.obtenerInvByIdExpAndCalidad(invoBD.getExpediente().getExpedienteId(),
//														Calidades.VICTIMA_PERSONA.getValorId(),null);
//					for (Involucrado indVictima : listVictimas) {
//						logger.debug("/**** ID VICTIMA A ELIMINAR ****/" + indVictima.getElementoId());
//						eliminarInvolucradoService.eliminarInvolucrado(new InvolucradoDTO(indVictima.getElementoId()));
//					}
//				}
//			}
//		}
		List<Detencion> listDetencion = new ArrayList<Detencion>();
		DefensaInvolucrado defensaInv = null;

		if (idCalidadInv.equals(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId())) {
			if (invoBD.getEsDetenido()!= null &&  invoMod.getEsDetenido()!= null && !(invoBD.getEsDetenido().equals(invoMod.getEsDetenido()))) {
				if (!invoMod.getEsDetenido()) {
					List<Involucrado> listProResp = involucradoDAO.obtenerInvByIdExpAndCalidad(invoBD.getExpediente().getExpedienteId(),
							Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(),null);
					for (Involucrado invProResp : listProResp) {
						defensaInv = defensaInvolucradoDAO.consultarDefensaInvolucrado(invProResp.getElementoId());
						if (defensaInv!= null){
							defensaInv.setInvolucradoPg(null);
							defensaInvolucradoDAO.update(defensaInv);
						}
						
						listDetencion = detencionDAO.consultarDetencionByInvolucrado(invProResp.getElementoId());
						if (listDetencion!= null){
							for (Detencion detencion : listDetencion){
							detencion.setInvolucrado(null);
							detencionDAO.update(detencion);
							}
						}
						eliminarInvolucradoService.eliminarInvolucrado(new InvolucradoDTO(invProResp.getElementoId()));
					}
				}
			}
		}
		
		//Calidades
		if (idCalidadInv.equals(Calidades.QUIEN_DETUVO.getValorId())) {
			List<Relacion> relaciones = relacionDAO.obtenerRelacionSimple(involucradoDTO.getElementoId(), new Long(Relaciones.QUIEN_DETUVO.ordinal()));
			for (Relacion relacion : relaciones) {
				relacionDAO.delete(relacion);
			}
		}

		//ORGANIZACION
		if (idCalidadInv.equals(Calidades.DENUNCIANTE.getValorId()) ||
				idCalidadInv.equals(Calidades.VICTIMA_PERSONA.getValorId()) ||
				idCalidadInv.equals(Calidades.DENUNCIANTE_ORGANIZACION.getValorId()) ||
				(idCalidadInv.equals(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId()))||
				(idCalidadInv.equals(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId()))) {
			//En caso de que se modifique el tipo de persona
			if (invoBD.getTipoPersona()!= null && invoMod.getTipoPersona()!= null && !(invoBD.getTipoPersona().equals(invoMod.getTipoPersona()))) {
				//Si cambio a Persona Fisica, se elimina la organización y sus dependencias
				if (invoMod.getTipoPersona().equals("Fisica")) {
					if (logger.isDebugEnabled())
						logger.debug("/**** EL INDVIDUO MODIFICO SU TIPO DE PERSONA A FISICA ****/");

						List<Relacion> lRelaciones = relacionDAO.obtenerRelacionSimple(invoMod.getElementoId(), new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()) );
						if(lRelaciones !=null &&!lRelaciones.isEmpty() ){
							OrganizacionDTO  organizacionDTO = involucradoDTO.getOrganizacionDTO();
							organizacionDTO.setElementoId(lRelaciones.get(0).getElementoByComplementoId().getElementoId());
							logger.info(" Id de Organización: "+ organizacionDTO.getElementoId());

							eliminarOrganizacionService.eliminarOrganizacion(organizacionDTO);
						}
				} else if (invoMod.getTipoPersona().equals("Moral")) { //Si cambio a Persona Moral, se ingresa la organiacion
					if (logger.isDebugEnabled())
						logger.debug("/**** EL INDVIDUO MODIFICO SU TIPO DE PERSONA A MORAL ****/");
					//Crear una nueva organizacion con toda sus dependencias.
//					if (involucradoDTO.getOrganizacionDTO()!=null)
//						ingresarOrganizacionService.ingresarOrganizacion(involucradoDTO.getOrganizacionDTO());
				}
			}
			//Si es moral DEBE de contener una organización
//			if (invoMod.getTipoPersona().equals("Moral") && involucradoDTO.getOrganizacionDTO()!= null && involucradoDTO.getOrganizacionDTO().getOrganizacionId()!= null) {
			//Para obtener el Id de organización
			if (invoMod.getTipoPersona().equals("Moral") && involucradoDTO.getOrganizacionDTO()!= null){
				if (logger.isDebugEnabled())
					logger.debug("/**** EL INDVIDUO ES PERSONA A MORAL Y ES UNA ORGANIZACION ****/");

				//Obtener la relacion de Organizacion
				List<Relacion> lRelaciones = relacionDAO.obtenerRelacionSimple(invoMod.getElementoId(), new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()) );
				//Realizar actualizacion de organizacion
				if(lRelaciones != null && !lRelaciones.isEmpty() ){
					OrganizacionDTO  organizacionDTO = involucradoDTO.getOrganizacionDTO();
					logger.info(" Id de Organización: "+ lRelaciones.get(0).getElementoByComplementoId().getElementoId());

					organizacionDTO.setElementoId(lRelaciones.get(0).getElementoByComplementoId().getElementoId());
					involucradoDTO.setOrganizacionDTO(organizacionDTO);

					modificarOrganizacionService.modificarOrganizacion(involucradoDTO.getOrganizacionDTO());
				}
				else{ //Se ingresa una nueva organización
					Calidad calidad = new Calidad();
					OrganizacionDTO organizacionDTO = involucradoDTO.getOrganizacionDTO();

					//Enable JC. Agregar ExpedienteDTO a Organizacion DTO (lanzaba excepcion en invocacion ingresarOrganizacionService.ingresarOrganizacion())
					organizacionDTO.setExpedienteDTO(involucradoDTO.getExpedienteDTO());

					//Obtener calidad
		        	if(organizacionDTO.getCalidadDTO()!=null)
		        		calidad.setDescripcionEstadoFisico(organizacionDTO.getCalidadDTO().getDescripcionEstadoFisico()!=null?organizacionDTO.getCalidadDTO().getDescripcionEstadoFisico():null);
		        	//Tipo de Organización
		        	calidad.setTipoCalidad(new Valor(involucradoDTO.getCalidadDTO().getCalidades().getValorId()));
		        	invoBD.setCalidad(calidad);

		        	OrganizacionDTO nuevaOrganizacionDTO = ingresarOrganizacionService.ingresarOrganizacion(involucradoDTO.getOrganizacionDTO());
		        	Long idOrganizacion = nuevaOrganizacionDTO .getElementoId();
		        	logger.debug("/**** Se genero la organizacion con id" + idOrganizacion + " ****/");
		        	//Generar relacion involucrado - organizacion
		        	if(idOrganizacion>0)
		        		generarRelacionService.generarRelacion(invoBD.getElementoId(), idOrganizacion, Relaciones.ORGANIZACION_INVOLUCRADA, TipoRelacion.IMPLICITA.getValorId().shortValue());
				}
			}
		}



		//SERVIDOR PUBLICO  Agregar/Actualizar/Eliminar
		//TODO GBP Crear servicio eliminarAsociacionFuncionario
		if (!invoMod.getEsServidor().equals(invoBD.getEsServidor())) {
			ServidorPublico servidorPublico = new ServidorPublico();
			if (!invoMod.getEsServidor()) {
				logger.debug("/**** EL INDVIDUO MODIFICO COMO NO ES SERVIDOR PUBLICO ****/");
				servidorPublico = servidorPublicoDAO.obtenerServidorPublicoInvolucrado(invoBD.getElementoId());
				servidorPublicoDAO.delete(servidorPublico);
			} else {
				logger.debug("/**** EL INDVIDUO MODIFICO COMO SERVIDOR PUBLICO ****/");
				if (involucradoDTO.getServidorPublicoDTO()!=null) {
					servidorPublico = InvolucradoTransformer.transformarServidorPublico(involucradoDTO.getServidorPublicoDTO());
					servidorPublico.setInvolucrado(invoBD);
					servidorPublicoDAO.create(servidorPublico);
				}
			}
		} else {
			if (involucradoDTO.getServidorPublicoDTO()!=null) {
				ServidorPublico servidorPublico = servidorPublicoDAO.obtenerServidorPublicoInvolucrado(involucradoDTO.getElementoId());
				servidorPublico = InvolucradoTransformer.transformarServidorPublicoUpdate(servidorPublico, involucradoDTO.getServidorPublicoDTO());
				servidorPublicoDAO.update(servidorPublico);
			}
		}

		//Idioma, Escolaridad, estadoCivil, Religión, Ocupación. Son modificados en entidad involucrado ver InvolucradoTransformer.
        List<InvolucradoNacionalidad> nacionalidades = involucradoNacionalidadDAO.consultarNacionalidadByInvolucrado(invoBD.getElementoId());
		if (nacionalidades!=null && !nacionalidades.isEmpty()) {
			logger.debug("/**** SE OBTUVIERON NACIONALIDADES DEL INDVIDUO ****/");
			for (InvolucradoNacionalidad invNacionalidad : nacionalidades) {
				involucradoNacionalidadDAO.delete(invNacionalidad);
			}
		}

        // Ocupaciones
		List<InvolucradoOcupacion> ocupaciones = involucradoOcupacionDAO.consultarOcupacionByInvolucrado(invoBD.getElementoId());
		
		if (ocupaciones != null && !ocupaciones.isEmpty()) {
			logger.debug("/**** SE OBTUVIERON OCUPACIONES DEL INDVIDUO ****/");
			for (InvolucradoOcupacion invOcupacion : ocupaciones) {
				involucradoOcupacionDAO.delete(invOcupacion);
			}
		}
		
        //DATOS GENERALES
        //Nombre Demografico (Uno solo)
//		if (invoMod.getNombreDemograficos()!=null && !invoMod.getNombreDemograficos().isEmpty()) {
//			List<NombreDemografico> nombres = nombreDemograficoDAO.consutarNombresByInvolucrado(invoBD.getElementoId());
//			if (nombres!=null && !nombres.isEmpty()) {
//				logger.debug("/**** SE OBTUVIERON NOMBRES DEMOGRAFICOS DEL INDIVIDUO ****/");
//				logger.info("Sexo Nombre Demografico Def --- " + nombres.get(0).getSexo());
//				for (NombreDemografico nomDemografico : nombres) {
//					nombreDemograficoDAO.update(nomDemografico);
//					///nombreDemograficoDAO.delete(nomDemografico);
//
//				}
//			}
//
//			//Actualizar esVerdadero en true para actualizar
//			//Resuelve incidencia en donde al actualizar un individuo,.. y pasar null el valor de esVerdadero
//			//se hace la consulta en el action para mostrarse como anónimo
//			for (NombreDemografico nombreDemografico : invoMod.getNombreDemograficos()) {
//				nombreDemografico.setEsVerdadero(true);
//			}
//		} 

		//Alias
		List<AliasInvolucrado> alias = aliasInvolucradoDAO.consultarAliasByInvolucrado(invoBD.getElementoId());
		if (alias!=null && !alias.isEmpty()) {
			 logger.debug("/**** SE OBTUVIERON ALIAS DEL INDVIDUO ****/");
			 for (AliasInvolucrado aliasInv : alias) {
				aliasInvolucradoDAO.delete(aliasInv);
			}
		}
		//Medios de Contacto -Telefono
		List<Telefono> telefonos = telefonoDAO.consultarTelefonosByPersona(invoBD.getElementoId());
		if (telefonos!=null && !telefonos.isEmpty()) {
			logger.debug("/**** SE OBTUVIERON TELEFONOS DEL INDIVIDUO ****/");
			for (Telefono telefono : telefonos) {
				telefonoDAO.delete(telefono);
			}
		}
		//Medios de Contacto -Correo electronico
		List<CorreoElectronico> correos = correoElectronicoDAO.consultarCorreosByPersona(invoBD.getElementoId());
		if (correos!=null && !correos.isEmpty()) {
			logger.debug("/**** SE OBTUVIERON CORREOS DEL INDIVIDUO ****/");
				for (CorreoElectronico correoElectronico : correos) {
					correoElectronicoDAO.delete(correoElectronico);
				}
		}

		//Domicilio	Verificar si se Modifica o se Crea uno nuevo
		List<Relacion> lRelaciones = relacionDAO.obtenerRelacionSimple(involucradoDTO.getElementoId(), new Long(Relaciones.RESIDENCIA.ordinal()));
		if (involucradoDTO.getDomicilio()!=null ){
		    involucradoDTO.getDomicilio().setExpedienteDTO(involucradoDTO.getExpedienteDTO());
			//Si existe un domicilio en BD, se actualiza con los nuevos datos, se mantiene la realacion
			if(!lRelaciones.isEmpty() && lRelaciones.get(0).getElementoByComplementoId()!= null ){
				DomicilioDTO domicilioDTO = involucradoDTO.getDomicilio();
				domicilioDTO.setElementoId(lRelaciones.get(0).getElementoByComplementoId().getElementoId());
				modificarDomicilioServicio.modificarDomicilio(domicilioDTO);
			}
			else{  //se da de alta el Domicilio y se crea una nueva relacion
				Long idDomicilio = ingresarDomicilioService.ingresarDomicilio(involucradoDTO.getDomicilio());
				//Se crea la relación entre Domicilio y Representante
				generarRelacionService.generarRelacion(involucradoDTO.getElementoId(), idDomicilio, Relaciones.RESIDENCIA, (short)0);
			}
		}//No se elimina de BD, el domicilio del involucrado

		//Domicilio	Verificar si se Modifica o se Crea uno nuevo
		lRelaciones = relacionDAO.obtenerRelacionSimple(involucradoDTO.getElementoId(), new Long(Relaciones.NOTIFICACION.ordinal()));
		if (involucradoDTO.getDomicilioNotificacion()!=null ){
		    involucradoDTO.getDomicilioNotificacion().setExpedienteDTO(involucradoDTO.getExpedienteDTO());
			//Si existe un domicilio en BD, se actualiza con los nuevos datos, se mantiene la realacion
			if(!lRelaciones.isEmpty() && lRelaciones.get(0).getElementoByComplementoId()!= null ){
				DomicilioDTO domicilioDTO = involucradoDTO.getDomicilioNotificacion();
				domicilioDTO.setElementoId(lRelaciones.get(0).getElementoByComplementoId().getElementoId());
				modificarDomicilioServicio.modificarDomicilio(domicilioDTO);
			}
			else{  //se da de alta el Domicilio y se crea una nueva relacion
				Long idDomicilio = ingresarDomicilioService.ingresarDomicilio(involucradoDTO.getDomicilioNotificacion());
				//Se crea la relación entre Domicilio Notificacion y Representante
				generarRelacionService.generarRelacion(involucradoDTO.getElementoId(), idDomicilio, Relaciones.NOTIFICACION, (short)0);
			}
		}//No se elimina de BD, el domicilio de notificacion


		invoBD = InvolucradoTransformer.involucradoUpdate(invoBD, invoMod);

		if(invoBD.getNombreDemograficos() != null){
			for (NombreDemografico nombreDemografico : invoBD.getNombreDemograficos()) {
				nombreDemografico.setNombreDemograficoId(null);
			}
		}

		//Verificar si es probable responsable o victima
        logger.info("/**** PROBABLE_RESPONSABLE_PERSONA:" + involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.PROBABLE_RESPONSABLE_PERSONA) +
        		" /**** VICTIMA_PERSONA:" + involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.VICTIMA_PERSONA));


        if (involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.PROBABLE_RESPONSABLE_PERSONA)
        		|| involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.VICTIMA_PERSONA)) {

        	if(involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.VICTIMA_PERSONA)){
        		invoBD.setCondicion(new Short("1"));
        	}
        	
        	//Si se va a actualizar la MediaFiliacion
    		if (!invoBD.getMediaFiliacions().isEmpty() && involucradoDTO.getMediaFiliacionDTO()!= null){
    			logger.debug("/**** SE OBTUVO MEDIA AFILIACION DEL INDIVIDUO ****/");
    			for (Iterator iterator = invoBD.getMediaFiliacions().iterator(); iterator.hasNext();) {
    				MediaFiliacion mediaFiliacion = (MediaFiliacion) iterator.next();
    				logger.info(" MediaFilacion Id:"+ mediaFiliacion.getMediaFiliacionId());
    				mediaFiliacion = InvolucradoTransformer.transformarMediaFilacionUpdate(mediaFiliacion, involucradoDTO.getMediaFiliacionDTO());

    				//Senia Particular a Actualizar o Nueva
    				if (involucradoDTO.getMediaFiliacionDTO().getSeniaParticularDTO() !=null){
    					logger.debug("/**** SE OBTUVO SENIA PARTICULAR DEL INDIVIDUO ****/");
    					SeniaParticular  seniaParticularN = mediaFiliacion.getSeniaParticular();
    					seniaParticularN = InvolucradoTransformer.transformarSeniaParticularUpdate(seniaParticularN, involucradoDTO.getMediaFiliacionDTO().getSeniaParticularDTO());
    					seniaParticularN.setMediaFiliacion(mediaFiliacion);
    					mediaFiliacion.setSeniaParticular(seniaParticularN);
    				}
    			}
    		}
    		//Si es una nueva MediaFiliacion
    		else if( involucradoDTO.getMediaFiliacionDTO()!= null ){
    			logger.debug("/**** SE CREARA NUEVA MEDIA AFILIACION DEL INDIVIDUO ****/");
    			MediaFiliacion mediaFiliacion = InvolucradoTransformer.transformarMediaFilacion(involucradoDTO.getMediaFiliacionDTO() );
    			//Senia Particular Nueva
    			if(involucradoDTO.getMediaFiliacionDTO().getSeniaParticularDTO() != null ){
        			logger.debug("/**** SE CREARA NUEVA SENIA PARTICULAR DEL INDIVIDUO ****/");
    				SeniaParticular  seniaParticularN = InvolucradoTransformer.transformarSeniaParticularDTO(involucradoDTO.getMediaFiliacionDTO().getSeniaParticularDTO());
    				seniaParticularN.setMediaFiliacion(mediaFiliacion);
					mediaFiliacion.setSeniaParticular(seniaParticularN);
    			}
    			//Referencia de Involucrado
    			mediaFiliacion.setInvolucrado(invoBD);
    			Set<MediaFiliacion> filiaciones = new HashSet<MediaFiliacion>();
    			filiaciones.add(mediaFiliacion);
    			invoBD.setMediaFiliacions(filiaciones);
    		}
        }

		/*Mostrar fechas/*Yolo*/
		if (!involucradoDTO.getDetenciones().isEmpty()) {
			Set<Detencion> colDetencion = new HashSet<Detencion>();
			for (DetencionDTO detencionDTO : involucradoDTO.getDetenciones()) {
				System.out.println("Dentro de ciclo");
				Detencion detencion = DetencionTransformer.transformarDetencion(detencionDTO);
				detencion.setInvolucrado(invoBD);
				colDetencion.add(detencion);
			}
			invoBD.setDetencions(colDetencion);
		}
		
		invoBD.setEsMismoDomicilio(involucradoDTO.getEsMismoDomicilio());
		involucradoDAO.update(invoBD);
		
		if (defensaInv!= null){
			defensaInv.setInvolucradoPg(invoBD);
			defensaInvolucradoDAO.update(defensaInv);
		}

		for(Detencion det :listDetencion){
			det.setInvolucrado(invoBD);
			detencionDAO.update(det);
		}


		// Generar relaciones quien detuvo
		if (involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.QUIEN_DETUVO)) {
			for (Long idDetenido : involucradoDTO.getIdsDetenidos()) {
				generarRelacionService.generarRelacion(invoBD.getElementoId(),idDetenido, Relaciones.QUIEN_DETUVO,
				TipoRelacion.EXPLICITA.getValorId().shortValue());
			}
		}

		//Actualizar los datos que estanrelacionados al involucrado Ocupaciones, Nacionalidades, Alias

		//Obtener Ocupaciones
        if (involucradoDTO.getValorIdOcupacion()!=null && involucradoDTO.getValorIdOcupacion().size()>0) {
        	logger.debug("Se obtuvo ocupaciones");
        	for (ValorDTO invOcupacionDTO : involucradoDTO.getValorIdOcupacion()) {
        		if (invOcupacionDTO.getIdCampo()!=null) {
        			InvolucradoOcupacion invOcupacion = new InvolucradoOcupacion();
					InvolucradoOcupacionId invOcId = new InvolucradoOcupacionId();
					invOcId.setInvolucradoId(invoBD.getElementoId());
					invOcId.setValorId(invOcupacionDTO.getIdCampo());
					invOcupacion.setId(invOcId);
					invOcupacion.setInvolucrado(new Involucrado(invoBD.getElementoId()));
					invOcupacion.setValor(new Valor(invOcupacionDTO.getIdCampo()));
					involucradoOcupacionDAO.create(invOcupacion);
        		}
        	}
        }

        //Obtener nacionalidades
        if (involucradoDTO.getValorIdNacionalidad()!=null && involucradoDTO.getValorIdNacionalidad().size()>0) {
        	logger.debug("Se obtuvo nacionalidades");
        	for (ValorDTO invNacionalidadDTO : involucradoDTO.getValorIdNacionalidad()) {
        		if (invNacionalidadDTO.getIdCampo()!=null) {
        			InvolucradoNacionalidad invNacionalidad = new InvolucradoNacionalidad();
					InvolucradoNacionalidadId invNacId = new InvolucradoNacionalidadId();
					invNacId.setInvolucradoId(invoBD.getElementoId());
					invNacId.setValorId(invNacionalidadDTO.getIdCampo());
					invNacionalidad.setId(invNacId);
					invNacionalidad.setInvolucrado(new Involucrado(invoBD.getElementoId()));
					invNacionalidad.setValor(new Valor(invNacionalidadDTO.getIdCampo()));
					involucradoNacionalidadDAO.create(invNacionalidad);
        		}
        	}
        }

		return invoBD.getElementoId();
	}
}
