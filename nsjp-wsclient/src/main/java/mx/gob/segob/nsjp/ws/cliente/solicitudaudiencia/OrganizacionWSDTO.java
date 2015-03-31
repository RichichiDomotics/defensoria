
package mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para organizacionWSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="organizacionWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}elementoWSDTO">
 *       &lt;sequence>
 *         &lt;element name="areaDeInfluencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionDelictiva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direccionInternet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domicilioWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}domicilioWSDTO" minOccurs="0"/>
 *         &lt;element name="giro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreCorto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreOrganizacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroActaConstitutiva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propositoCiberespacio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="representanteLegal" type="{http://ws.service.nsjp.segob.gob.mx/}involucradoWSDTO" minOccurs="0"/>
 *         &lt;element name="rfc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoCiberespacio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorByComunidadVirtualVal" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="valorByOrganizacionFormalVal" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="valorBySectorGubernamentalVal" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="valorByTipoOrganizacionVal" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "organizacionWSDTO", propOrder = {
    "areaDeInfluencia",
    "descripcionDelictiva",
    "direccionInternet",
    "domicilioWSDTO",
    "giro",
    "nombreCorto",
    "nombreOrganizacion",
    "numeroActaConstitutiva",
    "propositoCiberespacio",
    "representanteLegal",
    "rfc",
    "tipoCiberespacio",
    "valorByComunidadVirtualVal",
    "valorByOrganizacionFormalVal",
    "valorBySectorGubernamentalVal",
    "valorByTipoOrganizacionVal"
})
public class OrganizacionWSDTO
    extends ElementoWSDTO
{

    protected String areaDeInfluencia;
    protected String descripcionDelictiva;
    protected String direccionInternet;
    protected DomicilioWSDTO domicilioWSDTO;
    protected String giro;
    protected String nombreCorto;
    protected String nombreOrganizacion;
    protected String numeroActaConstitutiva;
    protected String propositoCiberespacio;
    protected InvolucradoWSDTO representanteLegal;
    protected String rfc;
    protected String tipoCiberespacio;
    protected Long valorByComunidadVirtualVal;
    protected Long valorByOrganizacionFormalVal;
    protected Long valorBySectorGubernamentalVal;
    protected Long valorByTipoOrganizacionVal;

    /**
     * Obtiene el valor de la propiedad areaDeInfluencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaDeInfluencia() {
        return areaDeInfluencia;
    }

    /**
     * Define el valor de la propiedad areaDeInfluencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaDeInfluencia(String value) {
        this.areaDeInfluencia = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionDelictiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionDelictiva() {
        return descripcionDelictiva;
    }

    /**
     * Define el valor de la propiedad descripcionDelictiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionDelictiva(String value) {
        this.descripcionDelictiva = value;
    }

    /**
     * Obtiene el valor de la propiedad direccionInternet.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccionInternet() {
        return direccionInternet;
    }

    /**
     * Define el valor de la propiedad direccionInternet.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccionInternet(String value) {
        this.direccionInternet = value;
    }

    /**
     * Obtiene el valor de la propiedad domicilioWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link DomicilioWSDTO }
     *     
     */
    public DomicilioWSDTO getDomicilioWSDTO() {
        return domicilioWSDTO;
    }

    /**
     * Define el valor de la propiedad domicilioWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link DomicilioWSDTO }
     *     
     */
    public void setDomicilioWSDTO(DomicilioWSDTO value) {
        this.domicilioWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad giro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiro() {
        return giro;
    }

    /**
     * Define el valor de la propiedad giro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiro(String value) {
        this.giro = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreCorto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * Define el valor de la propiedad nombreCorto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreCorto(String value) {
        this.nombreCorto = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreOrganizacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    /**
     * Define el valor de la propiedad nombreOrganizacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreOrganizacion(String value) {
        this.nombreOrganizacion = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroActaConstitutiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroActaConstitutiva() {
        return numeroActaConstitutiva;
    }

    /**
     * Define el valor de la propiedad numeroActaConstitutiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroActaConstitutiva(String value) {
        this.numeroActaConstitutiva = value;
    }

    /**
     * Obtiene el valor de la propiedad propositoCiberespacio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropositoCiberespacio() {
        return propositoCiberespacio;
    }

    /**
     * Define el valor de la propiedad propositoCiberespacio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropositoCiberespacio(String value) {
        this.propositoCiberespacio = value;
    }

    /**
     * Obtiene el valor de la propiedad representanteLegal.
     * 
     * @return
     *     possible object is
     *     {@link InvolucradoWSDTO }
     *     
     */
    public InvolucradoWSDTO getRepresentanteLegal() {
        return representanteLegal;
    }

    /**
     * Define el valor de la propiedad representanteLegal.
     * 
     * @param value
     *     allowed object is
     *     {@link InvolucradoWSDTO }
     *     
     */
    public void setRepresentanteLegal(InvolucradoWSDTO value) {
        this.representanteLegal = value;
    }

    /**
     * Obtiene el valor de la propiedad rfc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * Define el valor de la propiedad rfc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfc(String value) {
        this.rfc = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoCiberespacio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCiberespacio() {
        return tipoCiberespacio;
    }

    /**
     * Define el valor de la propiedad tipoCiberespacio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCiberespacio(String value) {
        this.tipoCiberespacio = value;
    }

    /**
     * Obtiene el valor de la propiedad valorByComunidadVirtualVal.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getValorByComunidadVirtualVal() {
        return valorByComunidadVirtualVal;
    }

    /**
     * Define el valor de la propiedad valorByComunidadVirtualVal.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setValorByComunidadVirtualVal(Long value) {
        this.valorByComunidadVirtualVal = value;
    }

    /**
     * Obtiene el valor de la propiedad valorByOrganizacionFormalVal.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getValorByOrganizacionFormalVal() {
        return valorByOrganizacionFormalVal;
    }

    /**
     * Define el valor de la propiedad valorByOrganizacionFormalVal.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setValorByOrganizacionFormalVal(Long value) {
        this.valorByOrganizacionFormalVal = value;
    }

    /**
     * Obtiene el valor de la propiedad valorBySectorGubernamentalVal.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getValorBySectorGubernamentalVal() {
        return valorBySectorGubernamentalVal;
    }

    /**
     * Define el valor de la propiedad valorBySectorGubernamentalVal.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setValorBySectorGubernamentalVal(Long value) {
        this.valorBySectorGubernamentalVal = value;
    }

    /**
     * Obtiene el valor de la propiedad valorByTipoOrganizacionVal.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getValorByTipoOrganizacionVal() {
        return valorByTipoOrganizacionVal;
    }

    /**
     * Define el valor de la propiedad valorByTipoOrganizacionVal.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setValorByTipoOrganizacionVal(Long value) {
        this.valorByTipoOrganizacionVal = value;
    }

}
