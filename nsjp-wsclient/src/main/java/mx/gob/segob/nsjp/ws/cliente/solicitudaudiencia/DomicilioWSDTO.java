
package mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para domicilioWSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="domicilioWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}lugarWSDTO">
 *       &lt;sequence>
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asentamientoId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="calle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ciudadId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="edificio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entidadId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="entreCalle1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entreCalle2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="municipioId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="numeroExterior" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroInterior" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroLote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorCalleId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "domicilioWSDTO", propOrder = {
    "alias",
    "asentamientoId",
    "calle",
    "ciudadId",
    "edificio",
    "entidadId",
    "entreCalle1",
    "entreCalle2",
    "municipioId",
    "numeroExterior",
    "numeroInterior",
    "numeroLote",
    "referencias",
    "valorCalleId"
})
public class DomicilioWSDTO
    extends LugarWSDTO
{

    protected String alias;
    protected Long asentamientoId;
    protected String calle;
    protected Long ciudadId;
    protected String edificio;
    protected Long entidadId;
    protected String entreCalle1;
    protected String entreCalle2;
    protected Long municipioId;
    protected String numeroExterior;
    protected String numeroInterior;
    protected String numeroLote;
    protected String referencias;
    protected Long valorCalleId;

    /**
     * Obtiene el valor de la propiedad alias.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Define el valor de la propiedad alias.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Obtiene el valor de la propiedad asentamientoId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAsentamientoId() {
        return asentamientoId;
    }

    /**
     * Define el valor de la propiedad asentamientoId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAsentamientoId(Long value) {
        this.asentamientoId = value;
    }

    /**
     * Obtiene el valor de la propiedad calle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Define el valor de la propiedad calle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalle(String value) {
        this.calle = value;
    }

    /**
     * Obtiene el valor de la propiedad ciudadId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCiudadId() {
        return ciudadId;
    }

    /**
     * Define el valor de la propiedad ciudadId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCiudadId(Long value) {
        this.ciudadId = value;
    }

    /**
     * Obtiene el valor de la propiedad edificio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * Define el valor de la propiedad edificio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdificio(String value) {
        this.edificio = value;
    }

    /**
     * Obtiene el valor de la propiedad entidadId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEntidadId() {
        return entidadId;
    }

    /**
     * Define el valor de la propiedad entidadId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEntidadId(Long value) {
        this.entidadId = value;
    }

    /**
     * Obtiene el valor de la propiedad entreCalle1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntreCalle1() {
        return entreCalle1;
    }

    /**
     * Define el valor de la propiedad entreCalle1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntreCalle1(String value) {
        this.entreCalle1 = value;
    }

    /**
     * Obtiene el valor de la propiedad entreCalle2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntreCalle2() {
        return entreCalle2;
    }

    /**
     * Define el valor de la propiedad entreCalle2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntreCalle2(String value) {
        this.entreCalle2 = value;
    }

    /**
     * Obtiene el valor de la propiedad municipioId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMunicipioId() {
        return municipioId;
    }

    /**
     * Define el valor de la propiedad municipioId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMunicipioId(Long value) {
        this.municipioId = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroExterior.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroExterior() {
        return numeroExterior;
    }

    /**
     * Define el valor de la propiedad numeroExterior.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroExterior(String value) {
        this.numeroExterior = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroInterior.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroInterior() {
        return numeroInterior;
    }

    /**
     * Define el valor de la propiedad numeroInterior.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroInterior(String value) {
        this.numeroInterior = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroLote.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroLote() {
        return numeroLote;
    }

    /**
     * Define el valor de la propiedad numeroLote.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroLote(String value) {
        this.numeroLote = value;
    }

    /**
     * Obtiene el valor de la propiedad referencias.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencias() {
        return referencias;
    }

    /**
     * Define el valor de la propiedad referencias.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencias(String value) {
        this.referencias = value;
    }

    /**
     * Obtiene el valor de la propiedad valorCalleId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getValorCalleId() {
        return valorCalleId;
    }

    /**
     * Define el valor de la propiedad valorCalleId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setValorCalleId(Long value) {
        this.valorCalleId = value;
    }

}
