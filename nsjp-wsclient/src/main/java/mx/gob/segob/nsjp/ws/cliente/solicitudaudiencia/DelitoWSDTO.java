
package mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para delitoWSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="delitoWSDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="claveDelito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claveInterInstitucional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esGrave" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="esPrincipal" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="esProbable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="idCatDelito" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nombreDelito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delitoWSDTO", propOrder = {
    "claveDelito",
    "claveInterInstitucional",
    "esGrave",
    "esPrincipal",
    "esProbable",
    "idCatDelito",
    "nombreDelito"
})
public class DelitoWSDTO {

    protected String claveDelito;
    protected String claveInterInstitucional;
    protected Boolean esGrave;
    protected Boolean esPrincipal;
    protected Boolean esProbable;
    protected Long idCatDelito;
    protected String nombreDelito;

    /**
     * Obtiene el valor de la propiedad claveDelito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveDelito() {
        return claveDelito;
    }

    /**
     * Define el valor de la propiedad claveDelito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveDelito(String value) {
        this.claveDelito = value;
    }

    /**
     * Obtiene el valor de la propiedad claveInterInstitucional.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveInterInstitucional() {
        return claveInterInstitucional;
    }

    /**
     * Define el valor de la propiedad claveInterInstitucional.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveInterInstitucional(String value) {
        this.claveInterInstitucional = value;
    }

    /**
     * Obtiene el valor de la propiedad esGrave.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsGrave() {
        return esGrave;
    }

    /**
     * Define el valor de la propiedad esGrave.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsGrave(Boolean value) {
        this.esGrave = value;
    }

    /**
     * Obtiene el valor de la propiedad esPrincipal.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsPrincipal() {
        return esPrincipal;
    }

    /**
     * Define el valor de la propiedad esPrincipal.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsPrincipal(Boolean value) {
        this.esPrincipal = value;
    }

    /**
     * Obtiene el valor de la propiedad esProbable.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsProbable() {
        return esProbable;
    }

    /**
     * Define el valor de la propiedad esProbable.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsProbable(Boolean value) {
        this.esProbable = value;
    }

    /**
     * Obtiene el valor de la propiedad idCatDelito.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdCatDelito() {
        return idCatDelito;
    }

    /**
     * Define el valor de la propiedad idCatDelito.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdCatDelito(Long value) {
        this.idCatDelito = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreDelito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreDelito() {
        return nombreDelito;
    }

    /**
     * Define el valor de la propiedad nombreDelito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreDelito(String value) {
        this.nombreDelito = value;
    }

}
