
package mx.gob.segob.nsjp.ws.cliente.administrarPermisosAudiencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for audienciaJAVSTransporteWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="audienciaJAVSTransporteWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="audiencia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="bytesRegistroMaestroJVL" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="dirIPJAVS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="directoriosServidorJAVS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passwordJAVS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registroMaestroJVL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultadoPermisoAudiencia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="usuarioJAVS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "audienciaJAVSTransporteWSDTO", propOrder = {
    "audiencia",
    "bytesRegistroMaestroJVL",
    "dirIPJAVS",
    "directoriosServidorJAVS",
    "passwordJAVS",
    "registroMaestroJVL",
    "resultadoPermisoAudiencia",
    "usuarioJAVS"
})
public class AudienciaJAVSTransporteWSDTO
    extends GenericWSDTO
{

    protected Long audiencia;
    protected byte[] bytesRegistroMaestroJVL;
    protected String dirIPJAVS;
    protected String directoriosServidorJAVS;
    protected String passwordJAVS;
    protected String registroMaestroJVL;
    protected Long resultadoPermisoAudiencia;
    protected String usuarioJAVS;

    /**
     * Gets the value of the audiencia property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAudiencia() {
        return audiencia;
    }

    /**
     * Sets the value of the audiencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAudiencia(Long value) {
        this.audiencia = value;
    }

    /**
     * Gets the value of the bytesRegistroMaestroJVL property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBytesRegistroMaestroJVL() {
        return bytesRegistroMaestroJVL;
    }

    /**
     * Sets the value of the bytesRegistroMaestroJVL property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBytesRegistroMaestroJVL(byte[] value) {
        this.bytesRegistroMaestroJVL = ((byte[]) value);
    }

    /**
     * Gets the value of the dirIPJAVS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirIPJAVS() {
        return dirIPJAVS;
    }

    /**
     * Sets the value of the dirIPJAVS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirIPJAVS(String value) {
        this.dirIPJAVS = value;
    }

    /**
     * Gets the value of the directoriosServidorJAVS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectoriosServidorJAVS() {
        return directoriosServidorJAVS;
    }

    /**
     * Sets the value of the directoriosServidorJAVS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectoriosServidorJAVS(String value) {
        this.directoriosServidorJAVS = value;
    }

    /**
     * Gets the value of the passwordJAVS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPasswordJAVS() {
        return passwordJAVS;
    }

    /**
     * Sets the value of the passwordJAVS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPasswordJAVS(String value) {
        this.passwordJAVS = value;
    }

    /**
     * Gets the value of the registroMaestroJVL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistroMaestroJVL() {
        return registroMaestroJVL;
    }

    /**
     * Sets the value of the registroMaestroJVL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistroMaestroJVL(String value) {
        this.registroMaestroJVL = value;
    }

    /**
     * Gets the value of the resultadoPermisoAudiencia property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getResultadoPermisoAudiencia() {
        return resultadoPermisoAudiencia;
    }

    /**
     * Sets the value of the resultadoPermisoAudiencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setResultadoPermisoAudiencia(Long value) {
        this.resultadoPermisoAudiencia = value;
    }

    /**
     * Gets the value of the usuarioJAVS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuarioJAVS() {
        return usuarioJAVS;
    }

    /**
     * Sets the value of the usuarioJAVS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuarioJAVS(String value) {
        this.usuarioJAVS = value;
    }

}
