
package mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para cadenaDeCustodiaWSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cadenaDeCustodiaWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="cadenaDeCustodiaId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="expediente" type="{http://ws.service.nsjp.segob.gob.mx/}expedienteWSDTO" minOccurs="0"/>
 *         &lt;element name="fechaIntercambio" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaLevantamiento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaTraslado" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="folio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="observacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quienEmbala" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quienEntrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quienRecibe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quienTransporta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cadenaDeCustodiaWSDTO", propOrder = {
    "cadenaDeCustodiaId",
    "expediente",
    "fechaIntercambio",
    "fechaLevantamiento",
    "fechaTraslado",
    "folio",
    "observacion",
    "quienEmbala",
    "quienEntrega",
    "quienRecibe",
    "quienTransporta"
})
public class CadenaDeCustodiaWSDTO
    extends GenericWSDTO
{

    protected Long cadenaDeCustodiaId;
    protected ExpedienteWSDTO expediente;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaIntercambio;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaLevantamiento;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaTraslado;
    protected String folio;
    protected String observacion;
    protected String quienEmbala;
    protected String quienEntrega;
    protected String quienRecibe;
    protected String quienTransporta;

    /**
     * Obtiene el valor de la propiedad cadenaDeCustodiaId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCadenaDeCustodiaId() {
        return cadenaDeCustodiaId;
    }

    /**
     * Define el valor de la propiedad cadenaDeCustodiaId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCadenaDeCustodiaId(Long value) {
        this.cadenaDeCustodiaId = value;
    }

    /**
     * Obtiene el valor de la propiedad expediente.
     * 
     * @return
     *     possible object is
     *     {@link ExpedienteWSDTO }
     *     
     */
    public ExpedienteWSDTO getExpediente() {
        return expediente;
    }

    /**
     * Define el valor de la propiedad expediente.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpedienteWSDTO }
     *     
     */
    public void setExpediente(ExpedienteWSDTO value) {
        this.expediente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaIntercambio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaIntercambio() {
        return fechaIntercambio;
    }

    /**
     * Define el valor de la propiedad fechaIntercambio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaIntercambio(XMLGregorianCalendar value) {
        this.fechaIntercambio = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaLevantamiento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaLevantamiento() {
        return fechaLevantamiento;
    }

    /**
     * Define el valor de la propiedad fechaLevantamiento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaLevantamiento(XMLGregorianCalendar value) {
        this.fechaLevantamiento = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaTraslado.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaTraslado() {
        return fechaTraslado;
    }

    /**
     * Define el valor de la propiedad fechaTraslado.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaTraslado(XMLGregorianCalendar value) {
        this.fechaTraslado = value;
    }

    /**
     * Obtiene el valor de la propiedad folio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Define el valor de la propiedad folio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolio(String value) {
        this.folio = value;
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
     * Obtiene el valor de la propiedad quienEmbala.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuienEmbala() {
        return quienEmbala;
    }

    /**
     * Define el valor de la propiedad quienEmbala.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuienEmbala(String value) {
        this.quienEmbala = value;
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
     * Obtiene el valor de la propiedad quienTransporta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuienTransporta() {
        return quienTransporta;
    }

    /**
     * Define el valor de la propiedad quienTransporta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuienTransporta(String value) {
        this.quienTransporta = value;
    }

}
