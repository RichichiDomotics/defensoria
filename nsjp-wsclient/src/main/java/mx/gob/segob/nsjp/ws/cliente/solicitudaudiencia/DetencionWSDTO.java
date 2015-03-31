
package mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para detencionWSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="detencionWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="fechaFinDetencion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaInicioDetencion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaRecepcionDetencion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="horaFinDetencion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="horaInicioDetencion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="horaRecepcionDetencion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lugarDetencion" type="{http://ws.service.nsjp.segob.gob.mx/}lugarWSDTO" minOccurs="0"/>
 *         &lt;element name="lugarDetencionProvicional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lugarDetencionWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}lugarWSDTO" minOccurs="0"/>
 *         &lt;element name="motivoDetencion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quienDetuvoAsFun" type="{http://ws.service.nsjp.segob.gob.mx/}funcionarioWSDTO" minOccurs="0"/>
 *         &lt;element name="quienDetuvoAsInv" type="{http://ws.service.nsjp.segob.gob.mx/}involucradoWSDTO" minOccurs="0"/>
 *         &lt;element name="strFechaFinDetencion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strFechaInicioDetencion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strFechaRecepcionDetencion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detencionWSDTO", propOrder = {
    "fechaFinDetencion",
    "fechaInicioDetencion",
    "fechaRecepcionDetencion",
    "horaFinDetencion",
    "horaInicioDetencion",
    "horaRecepcionDetencion",
    "lugarDetencion",
    "lugarDetencionProvicional",
    "lugarDetencionWSDTO",
    "motivoDetencion",
    "observaciones",
    "quienDetuvoAsFun",
    "quienDetuvoAsInv",
    "strFechaFinDetencion",
    "strFechaInicioDetencion",
    "strFechaRecepcionDetencion"
})
public class DetencionWSDTO
    extends GenericWSDTO
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaFinDetencion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaInicioDetencion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaRecepcionDetencion;
    protected String horaFinDetencion;
    protected String horaInicioDetencion;
    protected String horaRecepcionDetencion;
    protected LugarWSDTO lugarDetencion;
    protected String lugarDetencionProvicional;
    protected LugarWSDTO lugarDetencionWSDTO;
    protected String motivoDetencion;
    protected String observaciones;
    protected FuncionarioWSDTO quienDetuvoAsFun;
    protected InvolucradoWSDTO quienDetuvoAsInv;
    protected String strFechaFinDetencion;
    protected String strFechaInicioDetencion;
    protected String strFechaRecepcionDetencion;

    /**
     * Obtiene el valor de la propiedad fechaFinDetencion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaFinDetencion() {
        return fechaFinDetencion;
    }

    /**
     * Define el valor de la propiedad fechaFinDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaFinDetencion(XMLGregorianCalendar value) {
        this.fechaFinDetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaInicioDetencion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaInicioDetencion() {
        return fechaInicioDetencion;
    }

    /**
     * Define el valor de la propiedad fechaInicioDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaInicioDetencion(XMLGregorianCalendar value) {
        this.fechaInicioDetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaRecepcionDetencion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaRecepcionDetencion() {
        return fechaRecepcionDetencion;
    }

    /**
     * Define el valor de la propiedad fechaRecepcionDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaRecepcionDetencion(XMLGregorianCalendar value) {
        this.fechaRecepcionDetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad horaFinDetencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoraFinDetencion() {
        return horaFinDetencion;
    }

    /**
     * Define el valor de la propiedad horaFinDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoraFinDetencion(String value) {
        this.horaFinDetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad horaInicioDetencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoraInicioDetencion() {
        return horaInicioDetencion;
    }

    /**
     * Define el valor de la propiedad horaInicioDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoraInicioDetencion(String value) {
        this.horaInicioDetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad horaRecepcionDetencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoraRecepcionDetencion() {
        return horaRecepcionDetencion;
    }

    /**
     * Define el valor de la propiedad horaRecepcionDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoraRecepcionDetencion(String value) {
        this.horaRecepcionDetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad lugarDetencion.
     * 
     * @return
     *     possible object is
     *     {@link LugarWSDTO }
     *     
     */
    public LugarWSDTO getLugarDetencion() {
        return lugarDetencion;
    }

    /**
     * Define el valor de la propiedad lugarDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link LugarWSDTO }
     *     
     */
    public void setLugarDetencion(LugarWSDTO value) {
        this.lugarDetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad lugarDetencionProvicional.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLugarDetencionProvicional() {
        return lugarDetencionProvicional;
    }

    /**
     * Define el valor de la propiedad lugarDetencionProvicional.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLugarDetencionProvicional(String value) {
        this.lugarDetencionProvicional = value;
    }

    /**
     * Obtiene el valor de la propiedad lugarDetencionWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link LugarWSDTO }
     *     
     */
    public LugarWSDTO getLugarDetencionWSDTO() {
        return lugarDetencionWSDTO;
    }

    /**
     * Define el valor de la propiedad lugarDetencionWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link LugarWSDTO }
     *     
     */
    public void setLugarDetencionWSDTO(LugarWSDTO value) {
        this.lugarDetencionWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad motivoDetencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoDetencion() {
        return motivoDetencion;
    }

    /**
     * Define el valor de la propiedad motivoDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoDetencion(String value) {
        this.motivoDetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad observaciones.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Define el valor de la propiedad observaciones.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservaciones(String value) {
        this.observaciones = value;
    }

    /**
     * Obtiene el valor de la propiedad quienDetuvoAsFun.
     * 
     * @return
     *     possible object is
     *     {@link FuncionarioWSDTO }
     *     
     */
    public FuncionarioWSDTO getQuienDetuvoAsFun() {
        return quienDetuvoAsFun;
    }

    /**
     * Define el valor de la propiedad quienDetuvoAsFun.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionarioWSDTO }
     *     
     */
    public void setQuienDetuvoAsFun(FuncionarioWSDTO value) {
        this.quienDetuvoAsFun = value;
    }

    /**
     * Obtiene el valor de la propiedad quienDetuvoAsInv.
     * 
     * @return
     *     possible object is
     *     {@link InvolucradoWSDTO }
     *     
     */
    public InvolucradoWSDTO getQuienDetuvoAsInv() {
        return quienDetuvoAsInv;
    }

    /**
     * Define el valor de la propiedad quienDetuvoAsInv.
     * 
     * @param value
     *     allowed object is
     *     {@link InvolucradoWSDTO }
     *     
     */
    public void setQuienDetuvoAsInv(InvolucradoWSDTO value) {
        this.quienDetuvoAsInv = value;
    }

    /**
     * Obtiene el valor de la propiedad strFechaFinDetencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFechaFinDetencion() {
        return strFechaFinDetencion;
    }

    /**
     * Define el valor de la propiedad strFechaFinDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFechaFinDetencion(String value) {
        this.strFechaFinDetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad strFechaInicioDetencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFechaInicioDetencion() {
        return strFechaInicioDetencion;
    }

    /**
     * Define el valor de la propiedad strFechaInicioDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFechaInicioDetencion(String value) {
        this.strFechaInicioDetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad strFechaRecepcionDetencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFechaRecepcionDetencion() {
        return strFechaRecepcionDetencion;
    }

    /**
     * Define el valor de la propiedad strFechaRecepcionDetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFechaRecepcionDetencion(String value) {
        this.strFechaRecepcionDetencion = value;
    }

}
