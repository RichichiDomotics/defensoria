package mx.gob.segob.nsjp.dto.correo;

import java.io.IOException;
import java.io.OutputStream;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;


import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.destinatario.DestinatariosDTO;

/**
 * 
 * @author encesarvarga
 *Método que contiene un objeto correo 
 */
public class CorreoDTO extends GenericDTO{
	
	private Long audienciaId;	 
	private DestinatariosDTO destinatarios;
	private String asunto;
	private Multipart contenido=new MimeMultipart(); 
	private String contenidoSimple;
	
	
	public String getContenidoSimple() {
		return contenidoSimple;
	}
	public DestinatariosDTO getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(DestinatariosDTO destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public Multipart getContenido() {
		return contenido;
	}
	public void setContenido(String cuerpoMensaje) throws MessagingException {
		BodyPart messageBodyPart = new MimeBodyPart();  
		messageBodyPart.setContent(cuerpoMensaje, "text/html");
		this.contenido.addBodyPart(messageBodyPart);
		this.contenidoSimple=cuerpoMensaje;
	}
	
	

}
