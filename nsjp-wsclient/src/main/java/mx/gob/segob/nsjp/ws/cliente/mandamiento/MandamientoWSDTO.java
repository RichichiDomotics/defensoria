
package mx.gob.segob.nsjp.ws.cliente.mandamiento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for mandamientoWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mandamientoWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}documentoWSDTO">
 *       &lt;sequence>
 *         &lt;element name="estatus" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="fechaEjecuacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaFinal" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaInicial" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="folioInvolucrado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idResolutivo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idTipoMandamiento" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idTipoSentencia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nombreInvolucrado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCaso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCausa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aMaternoInvolucrado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aPaternoInvolucrado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mandamientoWSDTO", propOrder = {
    "estatus",
    "fechaEjecuacion",
    "fechaFinal",
    "fechaInicial",
    "folioInvolucrado",
    "idResolutivo",
    "idTipoMandamiento",
    "idTipoSentencia",
    "nombreInvolucrado",
    "numeroCaso",
    "numeroCausa",
    "aMaternoInvolucrado",
    "aPaternoInvolucrado"
})
public class MandamientoWSDTO
    extends DocumentoWSDTO
{

    protected Long estatus;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaEjecuacion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaFinal;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaInicial;
    protected String folioInvolucrado;
    protected Long idResolutivo;
    protected Long idTipoMandamiento;
    protected Long idTipoSentencia;
    protected String nombreInvolucrado;
    protected String numeroCaso;
    protected String numeroCausa;
    protected String aMaternoInvolucrado;
    protected String aPaternoInvolucrado;

    /**
     * Gets the value of the estatus property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEstatus() {
        return estatus;
    }

    /**
     * Sets the value of the estatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEstatus(Long value) {
        this.estatus = value;
    }

    /**
     * Gets the value of the fechaEjecuacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaEjecuacion() {
        return fechaEjecuacion;
    }

    /**
     * Sets the value of the fechaEjecuacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaEjecuacion(XMLGregorianCalendar value) {
        this.fechaEjecuacion = value;
    }

    /**
     * Gets the value of the fechaFinal property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Sets the value of the fechaFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaFinal(XMLGregorianCalendar value) {
        this.fechaFinal = value;
    }

    /**
     * Gets the value of the fechaInicial property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaInicial() {
        return fechaInicial;
    }

    /**
     * Sets the value of the fechaInicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaInicial(XMLGregorianCalendar value) {
        this.fechaInicial = value;
    }

    /**
     * Gets the value of the folioInvolucrado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioInvolucrado() {
        return folioInvolucrado;
    }

    /**
     * Sets the value of the folioInvolucrado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioInvolucrado(String value) {
        this.folioInvolucrado = value;
    }

    /**
     * Gets the value of the idResolutivo property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdResolutivo() {
        return idResolutivo;
    }

    /**
     * Sets the value of the idResolutivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdResolutivo(Long value) {
        this.idResolutivo = value;
    }

    /**
     * Gets the value of the idTipoMandamiento property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdTipoMandamiento() {
        return idTipoMandamiento;
    }

    /**
     * Sets the value of the idTipoMandamiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdTipoMandamiento(Long value) {
        this.idTipoMandamiento = value;
    }

    /**
     * Gets the value of the idTipoSentencia property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdTipoSentencia() {
        return idTipoSentencia;
    }

    /**
     * Sets the value of the idTipoSentencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdTipoSentencia(Long value) {
        this.idTipoSentencia = value;
    }

    /**
     * Gets the value of the nombreInvolucrado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreInvolucrado() {
        return nombreInvolucrado;
    }

    /**
     * Sets the value of the nombreInvolucrado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreInvolucrado(String value) {
        this.nombreInvolucrado = value;
    }

    /**
     * Gets the value of the numeroCaso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCaso() {
        return numeroCaso;
    }

    /**
     * Sets the value of the numeroCaso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCaso(String value) {
        this.numeroCaso = value;
    }

    /**
     * Gets the value of the numeroCausa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCausa() {
        return numeroCausa;
    }

    /**
     * Sets the value of the numeroCausa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCausa(String value) {
        this.numeroCausa = value;
    }

    /**
     * Gets the value of the aMaternoInvolucrado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMaternoInvolucrado() {
        return aMaternoInvolucrado;
    }

    /**
     * Sets the value of the aMaternoInvolucrado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMaternoInvolucrado(String value) {
        this.aMaternoInvolucrado = value;
    }

    /**
     * Gets the value of the aPaternoInvolucrado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPaternoInvolucrado() {
        return aPaternoInvolucrado;
    }

    /**
     * Sets the value of the aPaternoInvolucrado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPaternoInvolucrado(String value) {
        this.aPaternoInvolucrado = value;
    }

}
