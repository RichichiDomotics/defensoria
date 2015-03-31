
package mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para departamentoWSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="departamentoWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="area" type="{http://ws.service.nsjp.segob.gob.mx/}areaWSDTO" minOccurs="0"/>
 *         &lt;element name="departamentoId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "departamentoWSDTO", propOrder = {
    "area",
    "departamentoId"
})
public class DepartamentoWSDTO
    extends GenericWSDTO
{

    protected AreaWSDTO area;
    protected Long departamentoId;

    /**
     * Obtiene el valor de la propiedad area.
     * 
     * @return
     *     possible object is
     *     {@link AreaWSDTO }
     *     
     */
    public AreaWSDTO getArea() {
        return area;
    }

    /**
     * Define el valor de la propiedad area.
     * 
     * @param value
     *     allowed object is
     *     {@link AreaWSDTO }
     *     
     */
    public void setArea(AreaWSDTO value) {
        this.area = value;
    }

    /**
     * Obtiene el valor de la propiedad departamentoId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDepartamentoId() {
        return departamentoId;
    }

    /**
     * Define el valor de la propiedad departamentoId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDepartamentoId(Long value) {
        this.departamentoId = value;
    }

}
