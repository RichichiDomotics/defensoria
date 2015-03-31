
package mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para expedienteWSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="expedienteWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="aeronaveWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}aeronaveWSDTO" minOccurs="0"/>
 *         &lt;element name="animalWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}animalWSDTO" minOccurs="0"/>
 *         &lt;element name="armaWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}armaWSDTO" minOccurs="0"/>
 *         &lt;element name="casoWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}casoWSDTO" minOccurs="0"/>
 *         &lt;element name="delitoPrincipal" type="{http://ws.service.nsjp.segob.gob.mx/}delitoWSDTO" minOccurs="0"/>
 *         &lt;element name="delitos" type="{http://ws.service.nsjp.segob.gob.mx/}delitoWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="documentoOficialWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}documentoOficialWSDTO" minOccurs="0"/>
 *         &lt;element name="documentosDTO" type="{http://ws.service.nsjp.segob.gob.mx/}documentoWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="embarcacionWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}embarcacionWSDTO" minOccurs="0"/>
 *         &lt;element name="equipoComputoWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}equipoComputoWSDTO" minOccurs="0"/>
 *         &lt;element name="explosivoWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}explosivoWSDTO" minOccurs="0"/>
 *         &lt;element name="fechaApertura" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaCierre" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="hechoDTO" type="{http://ws.service.nsjp.segob.gob.mx/}hechoWSDTO" minOccurs="0"/>
 *         &lt;element name="inmuebleWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}inmuebleWSDTO" minOccurs="0"/>
 *         &lt;element name="involucradosDTO" type="{http://ws.service.nsjp.segob.gob.mx/}involucradoWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="joyaWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}joyaWSDTO" minOccurs="0"/>
 *         &lt;element name="narrativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numerarioWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}numerarioWSDTO" minOccurs="0"/>
 *         &lt;element name="numeroExpediente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroExpedienteId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="objetosDTO" type="{http://ws.service.nsjp.segob.gob.mx/}objetoWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="obraArteWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}obraArteWSDTO" minOccurs="0"/>
 *         &lt;element name="pertenceConfInst" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="strFechaApertura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strFechaCierre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strHoraApertura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strHoraCierre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sustanciaWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}sustanciaWSDTO" minOccurs="0"/>
 *         &lt;element name="telefoniaWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}telefoniaWSDTO" minOccurs="0"/>
 *         &lt;element name="vegetalWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}vegetalWSDTO" minOccurs="0"/>
 *         &lt;element name="vehiculoWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}vehiculoWSDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expedienteWSDTO", propOrder = {
    "aeronaveWSDTO",
    "animalWSDTO",
    "armaWSDTO",
    "casoWSDTO",
    "delitoPrincipal",
    "delitos",
    "documentoOficialWSDTO",
    "documentosDTO",
    "embarcacionWSDTO",
    "equipoComputoWSDTO",
    "explosivoWSDTO",
    "fechaApertura",
    "fechaCierre",
    "hechoDTO",
    "inmuebleWSDTO",
    "involucradosDTO",
    "joyaWSDTO",
    "narrativa",
    "numerarioWSDTO",
    "numeroExpediente",
    "numeroExpedienteId",
    "objetosDTO",
    "obraArteWSDTO",
    "pertenceConfInst",
    "strFechaApertura",
    "strFechaCierre",
    "strHoraApertura",
    "strHoraCierre",
    "sustanciaWSDTO",
    "telefoniaWSDTO",
    "vegetalWSDTO",
    "vehiculoWSDTO"
})
public class ExpedienteWSDTO
    extends GenericWSDTO
{

    protected AeronaveWSDTO aeronaveWSDTO;
    protected AnimalWSDTO animalWSDTO;
    protected ArmaWSDTO armaWSDTO;
    protected CasoWSDTO casoWSDTO;
    protected DelitoWSDTO delitoPrincipal;
    @XmlElement(nillable = true)
    protected List<DelitoWSDTO> delitos;
    protected DocumentoOficialWSDTO documentoOficialWSDTO;
    @XmlElement(nillable = true)
    protected List<DocumentoWSDTO> documentosDTO;
    protected EmbarcacionWSDTO embarcacionWSDTO;
    protected EquipoComputoWSDTO equipoComputoWSDTO;
    protected ExplosivoWSDTO explosivoWSDTO;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaApertura;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaCierre;
    protected HechoWSDTO hechoDTO;
    protected InmuebleWSDTO inmuebleWSDTO;
    @XmlElement(nillable = true)
    protected List<InvolucradoWSDTO> involucradosDTO;
    protected JoyaWSDTO joyaWSDTO;
    protected String narrativa;
    protected NumerarioWSDTO numerarioWSDTO;
    protected String numeroExpediente;
    protected Long numeroExpedienteId;
    @XmlElement(nillable = true)
    protected List<ObjetoWSDTO> objetosDTO;
    protected ObraArteWSDTO obraArteWSDTO;
    protected Long pertenceConfInst;
    protected String strFechaApertura;
    protected String strFechaCierre;
    protected String strHoraApertura;
    protected String strHoraCierre;
    protected SustanciaWSDTO sustanciaWSDTO;
    protected TelefoniaWSDTO telefoniaWSDTO;
    protected VegetalWSDTO vegetalWSDTO;
    protected VehiculoWSDTO vehiculoWSDTO;

    /**
     * Obtiene el valor de la propiedad aeronaveWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link AeronaveWSDTO }
     *     
     */
    public AeronaveWSDTO getAeronaveWSDTO() {
        return aeronaveWSDTO;
    }

    /**
     * Define el valor de la propiedad aeronaveWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link AeronaveWSDTO }
     *     
     */
    public void setAeronaveWSDTO(AeronaveWSDTO value) {
        this.aeronaveWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad animalWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link AnimalWSDTO }
     *     
     */
    public AnimalWSDTO getAnimalWSDTO() {
        return animalWSDTO;
    }

    /**
     * Define el valor de la propiedad animalWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link AnimalWSDTO }
     *     
     */
    public void setAnimalWSDTO(AnimalWSDTO value) {
        this.animalWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad armaWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link ArmaWSDTO }
     *     
     */
    public ArmaWSDTO getArmaWSDTO() {
        return armaWSDTO;
    }

    /**
     * Define el valor de la propiedad armaWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link ArmaWSDTO }
     *     
     */
    public void setArmaWSDTO(ArmaWSDTO value) {
        this.armaWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad casoWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link CasoWSDTO }
     *     
     */
    public CasoWSDTO getCasoWSDTO() {
        return casoWSDTO;
    }

    /**
     * Define el valor de la propiedad casoWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link CasoWSDTO }
     *     
     */
    public void setCasoWSDTO(CasoWSDTO value) {
        this.casoWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad delitoPrincipal.
     * 
     * @return
     *     possible object is
     *     {@link DelitoWSDTO }
     *     
     */
    public DelitoWSDTO getDelitoPrincipal() {
        return delitoPrincipal;
    }

    /**
     * Define el valor de la propiedad delitoPrincipal.
     * 
     * @param value
     *     allowed object is
     *     {@link DelitoWSDTO }
     *     
     */
    public void setDelitoPrincipal(DelitoWSDTO value) {
        this.delitoPrincipal = value;
    }

    /**
     * Gets the value of the delitos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delitos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelitos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DelitoWSDTO }
     * 
     * 
     */
    public List<DelitoWSDTO> getDelitos() {
        if (delitos == null) {
            delitos = new ArrayList<DelitoWSDTO>();
        }
        return this.delitos;
    }

    /**
     * Obtiene el valor de la propiedad documentoOficialWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoOficialWSDTO }
     *     
     */
    public DocumentoOficialWSDTO getDocumentoOficialWSDTO() {
        return documentoOficialWSDTO;
    }

    /**
     * Define el valor de la propiedad documentoOficialWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoOficialWSDTO }
     *     
     */
    public void setDocumentoOficialWSDTO(DocumentoOficialWSDTO value) {
        this.documentoOficialWSDTO = value;
    }

    /**
     * Gets the value of the documentosDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentosDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentosDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentoWSDTO }
     * 
     * 
     */
    public List<DocumentoWSDTO> getDocumentosDTO() {
        if (documentosDTO == null) {
            documentosDTO = new ArrayList<DocumentoWSDTO>();
        }
        return this.documentosDTO;
    }

    /**
     * Obtiene el valor de la propiedad embarcacionWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link EmbarcacionWSDTO }
     *     
     */
    public EmbarcacionWSDTO getEmbarcacionWSDTO() {
        return embarcacionWSDTO;
    }

    /**
     * Define el valor de la propiedad embarcacionWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link EmbarcacionWSDTO }
     *     
     */
    public void setEmbarcacionWSDTO(EmbarcacionWSDTO value) {
        this.embarcacionWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad equipoComputoWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link EquipoComputoWSDTO }
     *     
     */
    public EquipoComputoWSDTO getEquipoComputoWSDTO() {
        return equipoComputoWSDTO;
    }

    /**
     * Define el valor de la propiedad equipoComputoWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link EquipoComputoWSDTO }
     *     
     */
    public void setEquipoComputoWSDTO(EquipoComputoWSDTO value) {
        this.equipoComputoWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad explosivoWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link ExplosivoWSDTO }
     *     
     */
    public ExplosivoWSDTO getExplosivoWSDTO() {
        return explosivoWSDTO;
    }

    /**
     * Define el valor de la propiedad explosivoWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link ExplosivoWSDTO }
     *     
     */
    public void setExplosivoWSDTO(ExplosivoWSDTO value) {
        this.explosivoWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaApertura.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaApertura() {
        return fechaApertura;
    }

    /**
     * Define el valor de la propiedad fechaApertura.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaApertura(XMLGregorianCalendar value) {
        this.fechaApertura = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaCierre.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaCierre() {
        return fechaCierre;
    }

    /**
     * Define el valor de la propiedad fechaCierre.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaCierre(XMLGregorianCalendar value) {
        this.fechaCierre = value;
    }

    /**
     * Obtiene el valor de la propiedad hechoDTO.
     * 
     * @return
     *     possible object is
     *     {@link HechoWSDTO }
     *     
     */
    public HechoWSDTO getHechoDTO() {
        return hechoDTO;
    }

    /**
     * Define el valor de la propiedad hechoDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link HechoWSDTO }
     *     
     */
    public void setHechoDTO(HechoWSDTO value) {
        this.hechoDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad inmuebleWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link InmuebleWSDTO }
     *     
     */
    public InmuebleWSDTO getInmuebleWSDTO() {
        return inmuebleWSDTO;
    }

    /**
     * Define el valor de la propiedad inmuebleWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link InmuebleWSDTO }
     *     
     */
    public void setInmuebleWSDTO(InmuebleWSDTO value) {
        this.inmuebleWSDTO = value;
    }

    /**
     * Gets the value of the involucradosDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the involucradosDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvolucradosDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvolucradoWSDTO }
     * 
     * 
     */
    public List<InvolucradoWSDTO> getInvolucradosDTO() {
        if (involucradosDTO == null) {
            involucradosDTO = new ArrayList<InvolucradoWSDTO>();
        }
        return this.involucradosDTO;
    }

    /**
     * Obtiene el valor de la propiedad joyaWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link JoyaWSDTO }
     *     
     */
    public JoyaWSDTO getJoyaWSDTO() {
        return joyaWSDTO;
    }

    /**
     * Define el valor de la propiedad joyaWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link JoyaWSDTO }
     *     
     */
    public void setJoyaWSDTO(JoyaWSDTO value) {
        this.joyaWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad narrativa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNarrativa() {
        return narrativa;
    }

    /**
     * Define el valor de la propiedad narrativa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNarrativa(String value) {
        this.narrativa = value;
    }

    /**
     * Obtiene el valor de la propiedad numerarioWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link NumerarioWSDTO }
     *     
     */
    public NumerarioWSDTO getNumerarioWSDTO() {
        return numerarioWSDTO;
    }

    /**
     * Define el valor de la propiedad numerarioWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link NumerarioWSDTO }
     *     
     */
    public void setNumerarioWSDTO(NumerarioWSDTO value) {
        this.numerarioWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroExpediente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    /**
     * Define el valor de la propiedad numeroExpediente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroExpediente(String value) {
        this.numeroExpediente = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroExpedienteId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeroExpedienteId() {
        return numeroExpedienteId;
    }

    /**
     * Define el valor de la propiedad numeroExpedienteId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeroExpedienteId(Long value) {
        this.numeroExpedienteId = value;
    }

    /**
     * Gets the value of the objetosDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objetosDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjetosDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjetoWSDTO }
     * 
     * 
     */
    public List<ObjetoWSDTO> getObjetosDTO() {
        if (objetosDTO == null) {
            objetosDTO = new ArrayList<ObjetoWSDTO>();
        }
        return this.objetosDTO;
    }

    /**
     * Obtiene el valor de la propiedad obraArteWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link ObraArteWSDTO }
     *     
     */
    public ObraArteWSDTO getObraArteWSDTO() {
        return obraArteWSDTO;
    }

    /**
     * Define el valor de la propiedad obraArteWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link ObraArteWSDTO }
     *     
     */
    public void setObraArteWSDTO(ObraArteWSDTO value) {
        this.obraArteWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad pertenceConfInst.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPertenceConfInst() {
        return pertenceConfInst;
    }

    /**
     * Define el valor de la propiedad pertenceConfInst.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPertenceConfInst(Long value) {
        this.pertenceConfInst = value;
    }

    /**
     * Obtiene el valor de la propiedad strFechaApertura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFechaApertura() {
        return strFechaApertura;
    }

    /**
     * Define el valor de la propiedad strFechaApertura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFechaApertura(String value) {
        this.strFechaApertura = value;
    }

    /**
     * Obtiene el valor de la propiedad strFechaCierre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFechaCierre() {
        return strFechaCierre;
    }

    /**
     * Define el valor de la propiedad strFechaCierre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFechaCierre(String value) {
        this.strFechaCierre = value;
    }

    /**
     * Obtiene el valor de la propiedad strHoraApertura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrHoraApertura() {
        return strHoraApertura;
    }

    /**
     * Define el valor de la propiedad strHoraApertura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrHoraApertura(String value) {
        this.strHoraApertura = value;
    }

    /**
     * Obtiene el valor de la propiedad strHoraCierre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrHoraCierre() {
        return strHoraCierre;
    }

    /**
     * Define el valor de la propiedad strHoraCierre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrHoraCierre(String value) {
        this.strHoraCierre = value;
    }

    /**
     * Obtiene el valor de la propiedad sustanciaWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link SustanciaWSDTO }
     *     
     */
    public SustanciaWSDTO getSustanciaWSDTO() {
        return sustanciaWSDTO;
    }

    /**
     * Define el valor de la propiedad sustanciaWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link SustanciaWSDTO }
     *     
     */
    public void setSustanciaWSDTO(SustanciaWSDTO value) {
        this.sustanciaWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad telefoniaWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link TelefoniaWSDTO }
     *     
     */
    public TelefoniaWSDTO getTelefoniaWSDTO() {
        return telefoniaWSDTO;
    }

    /**
     * Define el valor de la propiedad telefoniaWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link TelefoniaWSDTO }
     *     
     */
    public void setTelefoniaWSDTO(TelefoniaWSDTO value) {
        this.telefoniaWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad vegetalWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link VegetalWSDTO }
     *     
     */
    public VegetalWSDTO getVegetalWSDTO() {
        return vegetalWSDTO;
    }

    /**
     * Define el valor de la propiedad vegetalWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link VegetalWSDTO }
     *     
     */
    public void setVegetalWSDTO(VegetalWSDTO value) {
        this.vegetalWSDTO = value;
    }

    /**
     * Obtiene el valor de la propiedad vehiculoWSDTO.
     * 
     * @return
     *     possible object is
     *     {@link VehiculoWSDTO }
     *     
     */
    public VehiculoWSDTO getVehiculoWSDTO() {
        return vehiculoWSDTO;
    }

    /**
     * Define el valor de la propiedad vehiculoWSDTO.
     * 
     * @param value
     *     allowed object is
     *     {@link VehiculoWSDTO }
     *     
     */
    public void setVehiculoWSDTO(VehiculoWSDTO value) {
        this.vehiculoWSDTO = value;
    }

}
