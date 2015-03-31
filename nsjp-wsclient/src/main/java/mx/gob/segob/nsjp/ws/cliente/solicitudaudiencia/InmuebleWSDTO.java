
package mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para inmuebleWSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="inmuebleWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}objetoWSDTO">
 *       &lt;sequence>
 *         &lt;element name="superficie" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tipoConstruccion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="tipoInmueble" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="unidadMedidaConstruccion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="unidadMedidaTerreno" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="mConstruccion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inmuebleWSDTO", propOrder = {
    "superficie",
    "tipoConstruccion",
    "tipoInmueble",
    "unidadMedidaConstruccion",
    "unidadMedidaTerreno",
    "mConstruccion"
})
public class InmuebleWSDTO
    extends ObjetoWSDTO
{

    protected Integer superficie;
    protected Long tipoConstruccion;
    protected Long tipoInmueble;
    protected Long unidadMedidaConstruccion;
    protected Long unidadMedidaTerreno;
    protected Integer mConstruccion;

    /**
     * Obtiene el valor de la propiedad superficie.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSuperficie() {
        return superficie;
    }

    /**
     * Define el valor de la propiedad superficie.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSuperficie(Integer value) {
        this.superficie = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoConstruccion.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTipoConstruccion() {
        return tipoConstruccion;
    }

    /**
     * Define el valor de la propiedad tipoConstruccion.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTipoConstruccion(Long value) {
        this.tipoConstruccion = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoInmueble.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTipoInmueble() {
        return tipoInmueble;
    }

    /**
     * Define el valor de la propiedad tipoInmueble.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTipoInmueble(Long value) {
        this.tipoInmueble = value;
    }

    /**
     * Obtiene el valor de la propiedad unidadMedidaConstruccion.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUnidadMedidaConstruccion() {
        return unidadMedidaConstruccion;
    }

    /**
     * Define el valor de la propiedad unidadMedidaConstruccion.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUnidadMedidaConstruccion(Long value) {
        this.unidadMedidaConstruccion = value;
    }

    /**
     * Obtiene el valor de la propiedad unidadMedidaTerreno.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUnidadMedidaTerreno() {
        return unidadMedidaTerreno;
    }

    /**
     * Define el valor de la propiedad unidadMedidaTerreno.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUnidadMedidaTerreno(Long value) {
        this.unidadMedidaTerreno = value;
    }

    /**
     * Obtiene el valor de la propiedad mConstruccion.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMConstruccion() {
        return mConstruccion;
    }

    /**
     * Define el valor de la propiedad mConstruccion.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMConstruccion(Integer value) {
        this.mConstruccion = value;
    }

}
