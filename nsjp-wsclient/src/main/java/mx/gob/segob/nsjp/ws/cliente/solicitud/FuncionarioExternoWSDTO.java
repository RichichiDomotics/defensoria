
package mx.gob.segob.nsjp.ws.cliente.solicitud;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for funcionarioExternoWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="funcionarioExternoWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://impl.ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="apellidoMaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoPaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="audienciaId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="caso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="confInstId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="cveFuncionarioInstExt" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="cveFuncionarioPJ" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puesto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "funcionarioExternoWSDTO", propOrder = {
    "apellidoMaterno",
    "apellidoPaterno",
    "area",
    "audienciaId",
    "caso",
    "confInstId",
    "cveFuncionarioInstExt",
    "cveFuncionarioPJ",
    "email",
    "nombre",
    "puesto"
})
public class FuncionarioExternoWSDTO
    extends GenericWSDTO
{

    protected String apellidoMaterno;
    protected String apellidoPaterno;
    protected String area;
    protected Long audienciaId;
    protected String caso;
    protected Long confInstId;
    protected Long cveFuncionarioInstExt;
    protected Long cveFuncionarioPJ;
    protected String email;
    protected String nombre;
    protected String puesto;

    /**
     * Gets the value of the apellidoMaterno property.
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
     * Sets the value of the apellidoMaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoMaterno(String value) {
        this.apellidoMaterno = value;
    }

    /**
     * Gets the value of the apellidoPaterno property.
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
     * Sets the value of the apellidoPaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoPaterno(String value) {
        this.apellidoPaterno = value;
    }

    /**
     * Gets the value of the area property.
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
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArea(String value) {
        this.area = value;
    }

    /**
     * Gets the value of the audienciaId property.
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
     * Sets the value of the audienciaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAudienciaId(Long value) {
        this.audienciaId = value;
    }

    /**
     * Gets the value of the caso property.
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
     * Sets the value of the caso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaso(String value) {
        this.caso = value;
    }

    /**
     * Gets the value of the confInstId property.
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
     * Sets the value of the confInstId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setConfInstId(Long value) {
        this.confInstId = value;
    }

    /**
     * Gets the value of the cveFuncionarioInstExt property.
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
     * Sets the value of the cveFuncionarioInstExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCveFuncionarioInstExt(Long value) {
        this.cveFuncionarioInstExt = value;
    }

    /**
     * Gets the value of the cveFuncionarioPJ property.
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
     * Sets the value of the cveFuncionarioPJ property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCveFuncionarioPJ(Long value) {
        this.cveFuncionarioPJ = value;
    }

    /**
     * Gets the value of the email property.
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
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the nombre property.
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
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the puesto property.
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
     * Sets the value of the puesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPuesto(String value) {
        this.puesto = value;
    }

}
