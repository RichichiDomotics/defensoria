
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
 *         &lt;element name="hay_AudienciaResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "hayAudienciaResult"
})
@XmlRootElement(name = "hay_AudienciaResponse")
public class HayAudienciaResponse {

    @XmlElement(name = "hay_AudienciaResult")
    protected int hayAudienciaResult;

    /**
     * Gets the value of the hayAudienciaResult property.
     * 
     */
    public int getHayAudienciaResult() {
        return hayAudienciaResult;
    }

    /**
     * Sets the value of the hayAudienciaResult property.
     * 
     */
    public void setHayAudienciaResult(int value) {
        this.hayAudienciaResult = value;
    }

}
