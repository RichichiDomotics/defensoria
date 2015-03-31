
package mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para notificacionWSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="notificacionWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}documentoWSDTO">
 *       &lt;sequence>
 *         &lt;element name="consecutivoNotificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="folioNotificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificacionWSDTO", propOrder = {
    "consecutivoNotificacion",
    "folioNotificacion"
})
public class NotificacionWSDTO
    extends DocumentoWSDTO
{

    protected String consecutivoNotificacion;
    protected String folioNotificacion;

    /**
     * Obtiene el valor de la propiedad consecutivoNotificacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsecutivoNotificacion() {
        return consecutivoNotificacion;
    }

    /**
     * Define el valor de la propiedad consecutivoNotificacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsecutivoNotificacion(String value) {
        this.consecutivoNotificacion = value;
    }

    /**
     * Obtiene el valor de la propiedad folioNotificacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioNotificacion() {
        return folioNotificacion;
    }

    /**
     * Define el valor de la propiedad folioNotificacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioNotificacion(String value) {
        this.folioNotificacion = value;
    }

}
