
package mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para eslabonWSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="eslabonWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="documento" type="{http://ws.service.nsjp.segob.gob.mx/}documentoWSDTO" minOccurs="0"/>
 *         &lt;element name="evidencia" type="{http://ws.service.nsjp.segob.gob.mx/}evidenciaWSDTO" minOccurs="0"/>
 *         &lt;element name="fechaEntrega" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaRecibe" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idTpoEslabon" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="numeroEslabon" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="observacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quienEntrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quienRecibe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ubicacionFisica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eslabonWSDTO", propOrder = {
    "documento",
    "evidencia",
    "fechaEntrega",
    "fechaRecibe",
    "idTpoEslabon",
    "numeroEslabon",
    "observacion",
    "posicion",
    "quienEntrega",
    "quienRecibe",
    "ubicacionFisica"
})
public class EslabonWSDTO
    extends GenericWSDTO
{

    protected DocumentoWSDTO documento;
    protected EvidenciaWSDTO evidencia;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaEntrega;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaRecibe;
    protected Long idTpoEslabon;
    protected Integer numeroEslabon;
    protected String observacion;
    protected String posicion;
    protected String quienEntrega;
    protected String quienRecibe;
    protected String ubicacionFisica;

    /**
     * Obtiene el valor de la propiedad documento.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoWSDTO }
     *     
     */
    public DocumentoWSDTO getDocumento() {
        return documento;
    }

    /**
     * Define el valor de la propiedad documento.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoWSDTO }
     *     
     */
    public void setDocumento(DocumentoWSDTO value) {
        this.documento = value;
    }

    /**
     * Obtiene el valor de la propiedad evidencia.
     * 
     * @return
     *     possible object is
     *     {@link EvidenciaWSDTO }
     *     
     */
    public EvidenciaWSDTO getEvidencia() {
        return evidencia;
    }

    /**
     * Define el valor de la propiedad evidencia.
     * 
     * @param value
     *     allowed object is
     *     {@link EvidenciaWSDTO }
     *     
     */
    public void setEvidencia(EvidenciaWSDTO value) {
        this.evidencia = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaEntrega.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * Define el valor de la propiedad fechaEntrega.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaEntrega(XMLGregorianCalendar value) {
        this.fechaEntrega = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaRecibe.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaRecibe() {
        return fechaRecibe;
    }

    /**
     * Define el valor de la propiedad fechaRecibe.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaRecibe(XMLGregorianCalendar value) {
        this.fechaRecibe = value;
    }

    /**
     * Obtiene el valor de la propiedad idTpoEslabon.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdTpoEslabon() {
        return idTpoEslabon;
    }

    /**
     * Define el valor de la propiedad idTpoEslabon.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdTpoEslabon(Long value) {
        this.idTpoEslabon = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroEslabon.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroEslabon() {
        return numeroEslabon;
    }

    /**
     * Define el valor de la propiedad numeroEslabon.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroEslabon(Integer value) {
        this.numeroEslabon = value;
    }

    /**
     * Obtiene el valor de la propiedad observacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Define el valor de la propiedad observacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservacion(String value) {
        this.observacion = value;
    }

    /**
     * Obtiene el valor de la propiedad posicion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosicion() {
        return posicion;
    }

    /**
     * Define el valor de la propiedad posicion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosicion(String value) {
        this.posicion = value;
    }

    /**
     * Obtiene el valor de la propiedad quienEntrega.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuienEntrega() {
        return quienEntrega;
    }

    /**
     * Define el valor de la propiedad quienEntrega.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuienEntrega(String value) {
        this.quienEntrega = value;
    }

    /**
     * Obtiene el valor de la propiedad quienRecibe.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuienRecibe() {
        return quienRecibe;
    }

    /**
     * Define el valor de la propiedad quienRecibe.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuienRecibe(String value) {
        this.quienRecibe = value;
    }

    /**
     * Obtiene el valor de la propiedad ubicacionFisica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    /**
     * Define el valor de la propiedad ubicacionFisica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUbicacionFisica(String value) {
        this.ubicacionFisica = value;
    }

}
