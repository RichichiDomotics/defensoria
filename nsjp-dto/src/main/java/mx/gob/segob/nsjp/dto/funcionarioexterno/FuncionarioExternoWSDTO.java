/**
 * Nombre del Programa : FuncionarioExternoWSDTO.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 05 Jun 2012
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
package mx.gob.segob.nsjp.dto.funcionarioexterno;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public class FuncionarioExternoWSDTO extends GenericWSDTO {

	private static final long serialVersionUID = 6527114378400165331L;
	private Long confInstId;
	private Long cveFuncionarioPJ;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String area;
	private String puesto;
	private String email;
	private Long cveFuncionarioInstExt;
	private String caso;	
	private Long audienciaId;

    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
	public Long getAudienciaId() {
		return audienciaId;
	}

    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
	public void setAudienciaId(Long audienciaId) {
		this.audienciaId = audienciaId;
	}


    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
	public Long getConfInstId() {
		return confInstId;
	}
	
    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
	public void setConfInstId(Long confInstId) {
		this.confInstId = confInstId;
	}
	
    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
	public Long getCveFuncionarioPJ() {
		return cveFuncionarioPJ;
	}
	
    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
	public void setCveFuncionarioPJ(Long cveFuncionarioPJ) {
		this.cveFuncionarioPJ = cveFuncionarioPJ;
	}
	
    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getNombre() {
		return nombre;
	}
	
    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	
    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	
    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	
    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getArea() {
		return area;
	}
	
    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setArea(String area) {
		this.area = area;
	}
	
    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getPuesto() {
		return puesto;
	}
	
    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
		
    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getEmail() {
		return email;
	}
	
    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setEmail(String email) {
		this.email = email;
	}
		
    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
	public Long getCveFuncionarioInstExt() {
		return cveFuncionarioInstExt;
	}
	
    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
	public void setCveFuncionarioInstExt(Long cveFuncionarioInstExt) {
		this.cveFuncionarioInstExt = cveFuncionarioInstExt;
	}
		
    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getCaso() {
		return caso;
	}
	
    /**
     * Sets the value of the cadenaTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setCaso(String caso) {
		this.caso = caso;
	}
}
