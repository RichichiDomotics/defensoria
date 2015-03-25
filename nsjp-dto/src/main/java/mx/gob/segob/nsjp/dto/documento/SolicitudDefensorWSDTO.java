/**
* Nombre del Programa : SolicitudDefensorWSDTO.java
* Autor                            : Asdrubal
* Compania                    : EnableIT
* Proyecto                      : NSJP                    Fecha: 06/02/2013
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
package mx.gob.segob.nsjp.dto.documento;

import java.io.Serializable;
import java.util.List;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;


/**
 * DTO para el transporte de datos de una solicitud de defensor 
 * entre Tribunal Oaxaca y Defensoria
 * @version 1.0
 * @author Asdrubal
 *
 */
public class SolicitudDefensorWSDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3183378445568595346L;
	
	private String confInstitucionId;
	private String numeroCausa;
	private List<String> delitos;
	private String folioSolicitud;
	private String folioElemento;
	private String nombreImputado;
	private String apellidoPaternoImputado;
	private String apellidoMaternoImputado;
	
	//Metodos Accesores
	public String getNumeroCausa() {
		return numeroCausa;
	}
	public void setNumeroCausa(String numeroCausa) {
		this.numeroCausa = numeroCausa;
	}
	public List<String> getDelitos() {
		return delitos;
	}
	public void setDelitos(List<String> delitos) {
		this.delitos = delitos;
	}
	public String getFolioSolicitud() {
		return folioSolicitud;
	}
	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}
	public String getFolioElemento() {
		return folioElemento;
	}
	public void setFolioElemento(String folioElemento) {
		this.folioElemento = folioElemento;
	}
	public String getNombreImputado() {
		return nombreImputado;
	}
	public void setNombreImputado(String nombreImputado) {
		this.nombreImputado = nombreImputado;
	}
	public String getApellidoPaternoImputado() {
		return apellidoPaternoImputado;
	}
	public void setApellidoPaternoImputado(String apellidoPaternoImputado) {
		this.apellidoPaternoImputado = apellidoPaternoImputado;
	}
	public String getApellidoMaternoImputado() {
		return apellidoMaternoImputado;
	}
	public void setApellidoMaternoImputado(String apellidoMaternoImputado) {
		this.apellidoMaternoImputado = apellidoMaternoImputado;
	}
	public String getConfInstitucionId() {
		return confInstitucionId;
	}
	public void setConfInstitucionId(String confInstitucionId) {
		this.confInstitucionId = confInstitucionId;
	}


}
