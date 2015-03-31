
package mx.gob.segob.nsjp.ws.enable.moreliapj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xmlSolicitud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "xmlSolicitud"
})
@XmlRootElement(name = "SolicitarAudiencia")
public class SolicitarAudiencia {

    protected String xmlSolicitud;

    /**
     * Obtiene el valor de la propiedad xmlSolicitud.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlSolicitud() {
        return xmlSolicitud;
    }

    /**
     * Define el valor de la propiedad xmlSolicitud.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlSolicitud(String value) {
        this.xmlSolicitud = value;
    }

}
