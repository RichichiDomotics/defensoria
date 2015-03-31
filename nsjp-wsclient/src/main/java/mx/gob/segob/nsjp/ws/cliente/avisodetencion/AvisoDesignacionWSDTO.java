
package mx.gob.segob.nsjp.ws.cliente.avisodetencion;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for avisoDesignacionWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="avisoDesignacionWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="avisoDesignacionId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="delitos" type="{http://ws.service.nsjp.segob.gob.mx/}delitoWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="solicitud" type="{http://ws.service.nsjp.segob.gob.mx/}detencionWSDTO" minOccurs="0"/>
 *         &lt;element name="estatusNotificacionId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="folioNotificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCasoAsociado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "avisoDesignacionWSDTO", propOrder = {
    "avisoDesignacionId",
    "delitos",
    "solicitud",
    "estatusNotificacionId",
    "folioNotificacion",
    "numeroCasoAsociado"
})
public class AvisoDesignacionWSDTO  extends GenericWSDTO {

    protected Long avisoDesignacionId;
    @XmlElement(nillable = true)
    protected List<DelitoWSDTO> delitos;
    protected SolicitudDefensorWSDTO solicitud;
    protected Long estatusNotificacionId;
    protected String folioNotificacion;
    protected String numeroCasoAsociado;

    
    
	public Long getAvisoDesignacionId() {
		return avisoDesignacionId;
	}

	public void setAvisoDesignacionId(Long avisoDesignacionId) {
		this.avisoDesignacionId = avisoDesignacionId;
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
     * Gets the value of the detencion property.
     * 
     * @return
     *     possible object is
     *     {@link DetencionWSDTO }
     *     
     */
    public SolicitudDefensorWSDTO getSolicitud() {
        return solicitud;
    }

    /**
     * Sets the value of the detencion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetencionWSDTO }
     *     
     */
    public void setSolicitud(SolicitudDefensorWSDTO value) {
        this.solicitud = value;
    }

	public Long getEstatusNotificacionId() {
		return estatusNotificacionId;
	}

	public void setEstatusNotificacionId(Long estatusNotificacionId) {
		this.estatusNotificacionId = estatusNotificacionId;
	}

	public String getFolioNotificacion() {
		return folioNotificacion;
	}

	public void setFolioNotificacion(String folioNotificacion) {
		this.folioNotificacion = folioNotificacion;
	}

	public String getNumeroCasoAsociado() {
		return numeroCasoAsociado;
	}

	public void setNumeroCasoAsociado(String numeroCasoAsociado) {
		this.numeroCasoAsociado = numeroCasoAsociado;
	}
    
    
    
    
    
}
