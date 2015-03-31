
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
 *         &lt;element name="generando_JVLResult" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
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
    "generandoJVLResult"
})
@XmlRootElement(name = "generando_JVLResponse")
public class GenerandoJVLResponse {

    @XmlElement(name = "generando_JVLResult")
    protected byte[] generandoJVLResult;

    /**
     * Gets the value of the generandoJVLResult property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getGenerandoJVLResult() {
        return generandoJVLResult;
    }

    /**
     * Sets the value of the generandoJVLResult property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setGenerandoJVLResult(byte[] value) {
        this.generandoJVLResult = ((byte[]) value);
    }

}
