
package mx.gob.segob.nsjp.ws.cliente.consultardistritos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for catDistritoWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catDistritoWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="catDistritoId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="claveDistrito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreDist" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catDistritoWSDTO", propOrder = {
    "catDistritoId",
    "claveDistrito",
    "nombreDist"
})
public class CatDistritoWSDTO
    extends GenericWSDTO
{

    protected Long catDistritoId;
    protected String claveDistrito;
    protected String nombreDist;

    /**
     * Gets the value of the catDistritoId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCatDistritoId() {
        return catDistritoId;
    }

    /**
     * Sets the value of the catDistritoId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCatDistritoId(Long value) {
        this.catDistritoId = value;
    }

    /**
     * Gets the value of the claveDistrito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveDistrito() {
        return claveDistrito;
    }

    /**
     * Sets the value of the claveDistrito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveDistrito(String value) {
        this.claveDistrito = value;
    }

    /**
     * Gets the value of the nombreDist property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreDist() {
        return nombreDist;
    }

    /**
     * Sets the value of the nombreDist property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreDist(String value) {
        this.nombreDist = value;
    }

}
