
package mx.gob.segob.nsjp.ws.cliente.avisodetencion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registrarAvisoSinDetencionResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registrarAvisoSinDetencionResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ws.service.nsjp.segob.gob.mx/}AvisoDesignacionWSDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registrarAvisoSinDetencionResponse", propOrder = {
    "_return"
})
public class RegistrarAvisoSinDetencionResponse {

    @XmlElement(name = "return")
    protected AvisoDesignacionWSDTO _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link AvisoDesignacionWSDTO }
     *     
     */
    public AvisoDesignacionWSDTO getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link AvisoDesignacionWSDTO }
     *     
     */
    public void setReturn(AvisoDesignacionWSDTO value) {
        this._return = value;
    }

}
