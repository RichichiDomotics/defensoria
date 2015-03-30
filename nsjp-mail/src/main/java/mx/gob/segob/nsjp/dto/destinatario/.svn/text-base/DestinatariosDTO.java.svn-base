package mx.gob.segob.nsjp.dto.destinatario;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage.RecipientType;

import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.model.CorreoElectronico;

public class DestinatariosDTO {

	// private NombreDemograficoDTO nombre;
	private List<InternetAddress> para= new ArrayList<InternetAddress>();
	private List<InternetAddress> conCopia= new ArrayList<InternetAddress>();
	private List<InternetAddress> conCopiaOculta=new ArrayList<InternetAddress>();
	
	public InternetAddress[] getArregloPara() {
		if(this.para !=null){
			InternetAddress[] para = new InternetAddress[this.para.size()];
			int i = 0;
			for (InternetAddress correo : this.para) {
				para[i] = correo;
				i++;
			}
			return para;	
		}else return null;
		
	}

	public void setPara(List<CorreoElectronico> para) throws AddressException {
		List<InternetAddress> direcciones = new ArrayList<InternetAddress>();
		for (CorreoElectronico correo : para) {
			direcciones.add(new InternetAddress(correo
					.getDireccionElectronica()));
		}
		this.para = direcciones;
	}

	public void setPara(String para) throws AddressException {
		this.para.add(new InternetAddress(para));
	}

	// public List<InternetAddress> getConCopia() {
	// return conCopia;
	// }

	public InternetAddress[] getArregloConCopia() {
		if(this.conCopia !=null){
			InternetAddress[] para = new InternetAddress[this.conCopia.size()];
			int i = 0;
			for (InternetAddress correo : this.conCopia) {
				para[i] = correo;
				i++;
			}
			return para;
		}else return null;

	}

	public void setConCopia(List<CorreoElectronico> conCopia)
			throws AddressException {
		List<InternetAddress> direcciones = new ArrayList<InternetAddress>();
		for (CorreoElectronico correo : conCopia) {
			direcciones.add(new InternetAddress(correo
					.getDireccionElectronica()));
		}
		this.conCopia = direcciones;
	}

	public void setConCopia(String conCopia) throws AddressException {
		this.conCopia.add(new InternetAddress(conCopia));
	}

	// public List<InternetAddress> getConCopiaOculta() {
	// return conCopiaOculta;
	// }

	public InternetAddress[] getArregloConCopiaOculta() {
		if(this.conCopiaOculta !=null){
			InternetAddress[] para = new InternetAddress[this.conCopiaOculta.size()];
			int i = 0;
			for (InternetAddress correo : this.conCopiaOculta) {
				para[i] = correo;
				i++;
			}
			return para;
			
		}else return null;

	}

	public void setConCopiaOculta(List<CorreoElectronico> conCopiaOculta)
			throws AddressException {
		List<InternetAddress> direcciones = new ArrayList<InternetAddress>();
		for (CorreoElectronico correo : conCopiaOculta) {
			direcciones.add(new InternetAddress(correo
					.getDireccionElectronica()));
		}
		this.conCopiaOculta = direcciones;
	}

	public void setConCopiaOculta(String conCopiaOculta)
			throws AddressException {
		this.conCopiaOculta.add(new InternetAddress(conCopiaOculta));
	}
	
	
	public List<InternetAddress> getPara() {
		return para;
	}

	public List<InternetAddress> getConCopia() {
		return conCopia;
	}

	public List<InternetAddress> getConCopiaOculta() {
		return conCopiaOculta;
	}

	public void addAllPara(List<InternetAddress> para) {
		this.para.addAll(para);
	}

	public void addAllConCopia(List<InternetAddress> conCopia) {
		this.conCopia.addAll(conCopia);
	}

	public void addAllCopiaOculta(List<InternetAddress> conCopiaOculta) {
		this.conCopiaOculta.addAll(conCopiaOculta);
	}

	@Override
	public String toString() {
		return "DestinatarioDTO [para=" + para + ", conCopia=" + conCopia
				+ ", conCopiaOculta=" + conCopiaOculta + "]";
	}
	
	

}
