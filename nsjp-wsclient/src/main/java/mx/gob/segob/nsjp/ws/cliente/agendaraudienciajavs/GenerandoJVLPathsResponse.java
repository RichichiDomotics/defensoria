
package mx.gob.segob.nsjp.ws.cliente.agendaraudienciajavs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="generando_JVL_PathsResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "generandoJVLPathsResult"
})
@XmlRootElement(name = "generando_JVL_PathsResponse")
public class GenerandoJVLPathsResponse {

    @XmlElement(name = "generando_JVL_PathsResult")
    protected String generandoJVLPathsResult;

    /**
     * Gets the value of the generandoJVLPathsResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenerandoJVLPathsResult() {
        return generandoJVLPathsResult;
    }

    /**
     * Sets the value of the generandoJVLPathsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenerandoJVLPathsResult(String value) {
        this.generandoJVLPathsResult = value;
    }

}
