package mx.gob.segob.nsjp.service.correo.Impl;

import java.util.List;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.funcionario.TipoEspecialidad;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.exceptions.CorreoNSJPException;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioAudienciaDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.persona.CorreoElectronicoDAO;
import mx.gob.segob.nsjp.dto.correo.CorreoDTO;
import mx.gob.segob.nsjp.dto.destinatario.DestinatariosDTO;
import mx.gob.segob.nsjp.model.ConfCorreo;
import mx.gob.segob.nsjp.model.CorreoElectronico;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.FuncionarioAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.persistencia.confcorreo.ConfCorreoDAO;
import mx.gob.segob.nsjp.persistencia.funcionarioExternoCorreo.FuncionarioExternoCorreoDAO;
import mx.gob.segob.nsjp.service.correo.EnviarCorreo;

@Service
@Transactional
public class EnviarCorreoImpl implements EnviarCorreo {

	@Autowired
	private ConfCorreoDAO confCorreoDAO;
	@Autowired
	private FuncionarioAudienciaDAO funcionarioAudienciaDAO;
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private CorreoElectronicoDAO correoElectronicoDAO;
	@Autowired
	private FuncionarioExternoCorreoDAO funcionarioExternoCorreoDAO;
	/* Log de clase */
	private static final Logger logger = Logger
			.getLogger(EnviarCorreoImpl.class);

	@SuppressWarnings("unused")
	@Override
	public void enviarCorreoPorAudiencia(Long idAudiencia) {
		CorreoDTO correoDTO = new CorreoDTO();
		
			try {
				correoDTO= this.ordenarRecipientes(idAudiencia);
			} catch (CorreoNSJPException e1) {
				logger.info(e1.getCause(),e1);
			}	
			
			try {
				this.enviar(correoDTO);
			} catch (CorreoNSJPException e) {
				logger.info(e.getCause(),e);
			}
	}

	private void enviar(CorreoDTO correoDTO) throws CorreoNSJPException{
		MimeMessage message = this.obtenerMessage();
		Properties userPassword = this.obtenerConexion();
		String protocol = this.obtenerProtocolo();
		Properties asuntoContenido = this.obtenerAsuntoContenido();
		
		
		correoDTO.setAsunto(asuntoContenido.getProperty("asunto"));
		try {
			correoDTO.setContenido(asuntoContenido.getProperty("contenido"));
		} catch (MessagingException e) {
			throw new CorreoNSJPException("El cuerpo del Mensaje en Base de Datos Tabla: \"confCorreo\" Llave: \"contenido\" esta mal formado", e.getCause());
		}

		
		try {
			message.setFrom(new InternetAddress(userPassword.getProperty("user")));
		} catch (AddressException e) {
			throw new CorreoNSJPException("El Usuario de Correo electronico Base de Datos Tabla: \"confCorreo\" Llave: \"user\" tiene una direccion de correo no valida", e.getCause());
		} catch (MessagingException e) {
			throw new CorreoNSJPException("El Usuario de Correo electronico Base de Datos Tabla: \"confCorreo\" Llave: \"user\" tiene una direccion de correo no valida", e.getCause());
		}

		if (correoDTO != null) {
			try {
				if (correoDTO.getDestinatarios() != null) {
					if (correoDTO.getDestinatarios().getArregloPara().length!=0)
							message.addRecipients(RecipientType.TO, correoDTO.getDestinatarios().getArregloPara());
					if (correoDTO.getDestinatarios().getArregloConCopia().length != 0)
						message.addRecipients(RecipientType.CC, correoDTO.getDestinatarios().getArregloConCopia());
					if (correoDTO.getDestinatarios().getArregloConCopiaOculta().length != 0)
						message.addRecipients(RecipientType.BCC, correoDTO.getDestinatarios().getArregloConCopiaOculta());
				}
			} catch (MessagingException e) {
				throw new CorreoNSJPException("Alguno de los involucrados en la Audiencia \"Funcionarios ó Involucrados\" tiene una direccion de correo no valida", e.getCause());
			}
			
			if(correoDTO.getAsunto()!=null)
				try {
					message.setSubject(correoDTO.getAsunto());
				} catch (MessagingException e) {
					throw new CorreoNSJPException("El Asunto del Mensaje en Base de Datos Tabla: \"confCorreo\" Llave: \"asunto\" esta mal formado", e.getCause());
				}

			if(correoDTO.getContenido()!=null)
				try {
					message.setContent(correoDTO.getContenido());
				} catch (MessagingException e) {
					throw new CorreoNSJPException("El cuerpo del Mensaje en Base de Datos Tabla: \"confCorreo\" Llave: \"contenido\" esta mal formado", e.getCause());
				}
		}
		
		Transport t;

		try {
			
			if(message!=null)
			if(message.getAllRecipients()!=null){
				t = Session.getInstance(this.obtenerProperties()).getTransport(protocol);
				t.connect(userPassword.getProperty("user"),userPassword.getProperty("password"));
				t.sendMessage(message, message.getAllRecipients());	
			}
			
			
		} catch (NoSuchProviderException e) {
			throw new CorreoNSJPException("No se puede obtener una sesión con el servidor de Correo Electronico", e.getCause());
		} catch (MessagingException e) {			
			throw new CorreoNSJPException("(USUARIO O CONTRASEÑA INVALIDO) No se puede establecer conexión o enviar mensaje de correo electronico,\"Favor de Contactar al proveedor de correo\"", e.getCause());
		}


	}
	
	private CorreoDTO ordenarRecipientes(Long idAudiencia) throws CorreoNSJPException{
		CorreoDTO correoDTO = new CorreoDTO();
		DestinatariosDTO destinatariosDTO = new DestinatariosDTO();		

		List<Involucrado> involucrados= involucradoDAO.consultarInvolucradosDeAudienciaPorCalidad(idAudiencia, null);
		
		//Se obtienen calidades y roles parametrizados en BD.
		Properties properties = obtenerRecipientes();
		
		if(!involucrados.isEmpty())
			for (Involucrado involucrado : involucrados) {
				DestinatariosDTO destinatariosTemp = new DestinatariosDTO();	
				switch (Calidades.getByValor(involucrado.getCalidad().getTipoCalidad().getValorId())) {
					case PROBABLE_RESPONSABLE_PERSONA:  						
					try {
						destinatariosTemp = this.agregarABandeja(Calidades.PROBABLE_RESPONSABLE_PERSONA, involucrado, properties);
					} catch (AddressException e) {
						throw new CorreoNSJPException("El \"PROBABLE_RESPONSABLE_PERSONA\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
					}
						break;
					case VICTIMA_PERSONA: 
					try {
						destinatariosTemp = this.agregarABandeja(Calidades.VICTIMA_PERSONA, involucrado, properties);
					} catch (AddressException e) {
						throw new CorreoNSJPException("El \"VICTIMA_PERSONA\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
					}
						break;
					case DENUNCIANTE: 
					try {
						destinatariosTemp = this.agregarABandeja(Calidades.DENUNCIANTE, involucrado, properties);
					} catch (AddressException e) {
						throw new CorreoNSJPException("El \"DENUNCIANTE\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
					}
						break;
					case TESTIGO:
					try {
						destinatariosTemp = this.agregarABandeja(Calidades.TESTIGO, involucrado, properties);
					} catch (AddressException e) {
						throw new CorreoNSJPException("El \"TESTIGO\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
					}
						break;
					default:
						break;
				}
				
				//se agrega los correos ya ordenados en sus recipientes para los "Involucrados" al Objeto que se retornara y se utilizara para enviarse por correo
				if(destinatariosTemp!=null){
					if(!destinatariosTemp.getPara().isEmpty())
						destinatariosDTO.addAllPara(destinatariosTemp.getPara());
					if(!destinatariosTemp.getConCopia().isEmpty())
						destinatariosDTO.addAllConCopia(destinatariosTemp.getConCopia());
					if(!destinatariosTemp.getConCopiaOculta().isEmpty())
						destinatariosDTO.addAllCopiaOculta(destinatariosTemp.getConCopiaOculta());
				}					
			}

			
			List<FuncionarioAudiencia> funcionarioAudiencias =funcionarioAudienciaDAO.consultaFuncionariosPorAudiencia(idAudiencia);
			
			if(!funcionarioAudiencias.isEmpty())
			for (FuncionarioAudiencia funcionarioAudiencia : funcionarioAudiencias) {
				FuncionarioExterno funcionarioExterno=null;
				 funcionarioExterno= funcionarioExternoCorreoDAO.consultarCorreoFuncionarioExternosPorAudienciaId(funcionarioAudiencia.getFuncionario().getClaveFuncionario());
				 if(funcionarioExterno!=null){
					 if(funcionarioExterno.getEmail()!=null){
						 if(funcionarioAudiencia.getFuncionario()!=null){
							 if(funcionarioAudiencia.getFuncionario().getEmail()!=null){
								if(!funcionarioAudiencia.getFuncionario().getEmail().equals(funcionarioExterno.getEmail())){
									funcionarioAudiencia.getFuncionario().setEmail(funcionarioExterno.getEmail());
									funcionarioAudienciaDAO.update(funcionarioAudiencia);
								}
							 }else{
								funcionarioAudiencia.getFuncionario().setEmail(funcionarioExterno.getEmail());
								funcionarioAudienciaDAO.update(funcionarioAudiencia);
							 }
						}
						
					}
				 }					
			}
				
				List<FuncionarioAudiencia> funcionarioAudiencias2 = funcionarioAudienciaDAO.consultaFuncionariosPorAudiencia(idAudiencia);
				
				if(!funcionarioAudiencias2.isEmpty())
				for (FuncionarioAudiencia funcionarioAudiencia : funcionarioAudiencias2) {
					DestinatariosDTO destinatariosTemp = new DestinatariosDTO();
					
//					switch (Jerarquias.getByValor(funcionarioAudiencia.getFuncionario().getArea().getJerarquiaOrganizacionalId())) {
					switch (TipoEspecialidad.getByValor(funcionarioAudiencia.getFuncionario().getTipoEspecialidad().getValorId())) {
					case JUEZ:
						try {
							destinatariosTemp = this.agregarABandeja(Roles.JUEZPJ, funcionarioAudiencia.getFuncionario(), properties);
						} catch (AddressException e) {
							throw new CorreoNSJPException("El \"JUEZPJ\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
						}
						break;
					case DEFENSOR:
						try {
							destinatariosTemp = this.agregarABandeja(Roles.DEFENSOR, funcionarioAudiencia.getFuncionario(), properties);
						} catch (AddressException e) {
							throw new CorreoNSJPException("El \"DEFENSOR\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
						}
						break;
					case MINISTERIO_PUBLICO:
						try {
							destinatariosTemp = this.agregarABandeja(Roles.AGENTEMP, funcionarioAudiencia.getFuncionario(), properties);
						} catch (AddressException e) {
							throw new CorreoNSJPException("El \"MINISTERIO PUBLICO\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
						}
						break;			
					case PERICIAL:
						try {
							destinatariosTemp = this.agregarABandeja(Roles.PERITOAMP, funcionarioAudiencia.getFuncionario(), properties);
						} catch (AddressException e) {
							throw new CorreoNSJPException("El \"COORDINADORPER\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
						}
						break;		
					case POLICIA:
						try {
							destinatariosTemp = this.agregarABandeja(Roles.POLICIAMINISTER, funcionarioAudiencia.getFuncionario(), properties);
						} catch (AddressException e) {
							throw new CorreoNSJPException("El \"POLICIA MINISTERIAL\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
						}
						break;							
//					case DEPARTAMENTO_SALA:
//						try {
//							destinatariosTemp = this.agregarABandeja(Roles.ENCARGADOSALA, funcionarioAudiencia.getFuncionario(), properties);
//						} catch (AddressException e) {
//							throw new CorreoNSJPException("El \"ENCARGADOSALA\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
//						}
//						break;
//					case COORDINACION_DEFENSORIA:
//						try {
//							destinatariosTemp = this.agregarABandeja(Roles.COORDINADORDEF, funcionarioAudiencia.getFuncionario(), properties);
//						} catch (AddressException e) {
//							throw new CorreoNSJPException("El \"COORDINADORDEF\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
//						}
//						break;					
//					case COORDINACION_UNIDAD_INVESTIGACION:
//						try {
//							destinatariosTemp = this.agregarABandeja(Roles.COORDINADORAMP, funcionarioAudiencia.getFuncionario(), properties);
//						} catch (AddressException e) {
//							throw new CorreoNSJPException("El \"COORDINADORAMP\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
//						}
//						break;
//					case COORDINACION_PERICIALES_PG:
//						try {
//							destinatariosTemp = this.agregarABandeja(Roles.COORDINADORPER, funcionarioAudiencia.getFuncionario(), properties);
//						} catch (AddressException e) {
//							throw new CorreoNSJPException("El \"COORDINADORPER\" de la audiencia: "+idAudiencia+" tiene un correo electronico no valido",e.getCause());
//						}
//						break;			
					default:
						break;
					}
									
					//se agrega los correos ya ordenados en sus recipientes para los "Funcionarios" al Objeto que se retornara y se utilizara para enviarse por correo
					if(destinatariosTemp!=null){
						if(destinatariosTemp.getPara()!=null)
							destinatariosDTO.addAllPara(destinatariosTemp.getPara());
						if(destinatariosTemp.getConCopia()!=null)
							destinatariosDTO.addAllCopiaOculta(destinatariosTemp.getConCopia());
						if(destinatariosTemp.getConCopiaOculta()!=null)
							destinatariosDTO.addAllCopiaOculta(destinatariosTemp.getConCopiaOculta());
					}
				}
					
		correoDTO.setDestinatarios(destinatariosDTO);
			
		
		return correoDTO;
	}
		
	private DestinatariosDTO agregarABandeja(Calidades calidades, Involucrado involucrado, Properties properties) throws AddressException{
		
		DestinatariosDTO destinatariosDTO=new DestinatariosDTO();
		List<CorreoElectronico> correoElectronicos=null;
		
		if(properties.getProperty(calidades.name()).equals("para")){
			correoElectronicos =correoElectronicoDAO.consultarCorreosByPersona(involucrado.getElementoId());
				if(!correoElectronicos.isEmpty())		
					destinatariosDTO.setPara(correoElectronicos);			
		}else if(properties.getProperty(calidades.name()).equals("cc")){
			correoElectronicos =correoElectronicoDAO.consultarCorreosByPersona(involucrado.getElementoId());
				if(!correoElectronicos.isEmpty())		
					destinatariosDTO.setConCopia(correoElectronicos);
		}else if(properties.getProperty(calidades.name()).equals("bcc")){
			correoElectronicos =correoElectronicoDAO.consultarCorreosByPersona(involucrado.getElementoId());
				if(!correoElectronicos.isEmpty())		
					destinatariosDTO.setConCopiaOculta(correoElectronicos);
		}
		
		return destinatariosDTO;
		
	}
	
	private DestinatariosDTO agregarABandeja(Roles rol, Funcionario funcionario, Properties properties) throws AddressException{
				
		DestinatariosDTO destinatariosDTO=new DestinatariosDTO();
		
		if(properties.getProperty(rol.name()).equals("para")){
				if(funcionario.getEmail()!=null)
					destinatariosDTO.setPara(funcionario.getEmail());			
		}else if(properties.getProperty(rol.name()).equals("cc")){
			if(funcionario.getEmail()!=null)
				destinatariosDTO.setConCopia(funcionario.getEmail());
		}else if(properties.getProperty(rol.name()).equals("bcc")){
			if(funcionario.getEmail()!=null)
				destinatariosDTO.setConCopiaOculta(funcionario.getEmail());
		}
		
		return destinatariosDTO;
	}
	

	// obtiene Properties para la configuracion del correo
	private Properties obtenerProperties() {
		List<ConfCorreo> confCorreos = confCorreoDAO.obtenerConfiguracionSession();
		Properties properties = new Properties();
		for (ConfCorreo confCorreo : confCorreos) {
			properties.setProperty(confCorreo.getllave().trim(), confCorreo.getValor().trim());
		}
		return properties;
	}

	// obtiene el mensaje ya configurado listo para usarse.
	private MimeMessage obtenerMessage() {
		MimeMessage message = new MimeMessage(Session.getInstance(this.obtenerProperties()));
		return message;
	}
	

	// Regresa el protocolo de conexion del servidor de correo SMT, POP etc...
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	private String obtenerProtocolo() {
		return confCorreoDAO.obtenerConfiguracionTransport().getValor();
	}

	// Regresa datos de la conexion User y Password
	private Properties obtenerConexion() {
		List<ConfCorreo> confCorreos = confCorreoDAO.obtenerConfiguracionConexion();
		Properties properties = new Properties();
		for (ConfCorreo confCorreo : confCorreos) {
			properties.setProperty(confCorreo.getllave().trim(), confCorreo.getValor().trim());
		}
		return properties;
	}
	
	// Regresa datos de configuracion de los recipientes
	private Properties obtenerRecipientes() {
		List<ConfCorreo> confCorreos = confCorreoDAO.obtenerConfiguracionRecipientes();
		Properties properties = new Properties();
		for (ConfCorreo confCorreo : confCorreos) {
			properties.setProperty(confCorreo.getllave().trim(), confCorreo.getValor().trim());
		}
		return properties;
	}
	
	//
	private Properties obtenerAsuntoContenido() {
		List<ConfCorreo> confCorreos = confCorreoDAO.obtenerConfiguracionAsuntoContenido();
		Properties properties = new Properties();
		for (ConfCorreo confCorreo : confCorreos) {
			properties.setProperty(confCorreo.getllave().trim(), confCorreo.getValor().trim());
		}
		return properties;
	}

}
