/**
* Nombre del Programa : ObjetoWSDTO.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 24/08/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : DTO de intercambio entre sistemas para transportar los datos básicos de un Objeto.                      
* Programa Dependiente  :N/A                                                      
* Programa Subsecuente :N/A                                                      
* Cond. de ejecucion        :N/A                                                      
* Dias de ejecucion          :N/A                             Horario: N/A       
*                              MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                       :N/A                                                           
* Compania               :N/A                                                           
* Proyecto                 :N/A                                   Fecha: N/A       
* Modificacion           :N/A                                                           
*------------------------------------------------------------------------------           
*/
package mx.gob.segob.nsjp.ws.cliente.avisodetencion;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for objetoWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="objetoWSDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iIdentificadorAlmacen" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="tipoObjeto" type="{http://www.w3.org/2001/XMLSchema}objetos" minOccurs="0"/>
 *         &lt;element name="idTipoObjeto" type="{http://www.w3.org/2001/XMLSchema}Long" minOccurs="0"/>
 *         &lt;element name="valorByCondicionFisicaVal" type="{http://www.w3.org/2001/XMLSchema}Long" minOccurs="0"/>
 *         &lt;element name="nombreObjeto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relacionHechoVal" type="{http://www.w3.org/2001/XMLSchema}Long" minOccurs="0"/>
 *         &lt;element name="almacen" type="{http://www.w3.org/2001/XMLSchema}Long" minOccurs="0"/>
 *         &lt;element name="esPertenencia" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="esActivo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "objetoWSDTO", propOrder = {
    "descripcion",
    "iIdentificadorAlmacen",
    "tipoObjeto",
    "idTipoObjeto",
    "valorByCondicionFisicaVal",
    "nombreObjeto",
    "relacionHechoVal",
    "almacen",
    "esPertenencia",
    "esActivo"
})

public class ObjetoWSDTO extends GenericWSDTO{

	private String 				descripcion;
	private Long 				iIdentificadorAlmacen;
	private Objetos 			tipoObjeto;
	private Long 				idTipoObjeto;
	private Long 				valorByCondicionFisicaVal;
	private String 				nombreObjeto;
	private Long 				relacionHechoVal;
	private Long 				almacen;
	private Boolean 			esPertenencia;
	private Boolean 			esActivo;



	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Long getiIdentificadorAlmacen() {
		return iIdentificadorAlmacen;
	}


	public void setiIdentificadorAlmacen(Long iIdentificadorAlmacen) {
		this.iIdentificadorAlmacen = iIdentificadorAlmacen;
	}


	public Objetos getTipoObjeto() {
		return tipoObjeto;
	}


	public void setTipoObjeto(Objetos tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}


	public Long getIdTipoObjeto() {
		return idTipoObjeto;
	}


	public void setIdTipoObjeto(Long idTipoObjeto) {
		this.idTipoObjeto = idTipoObjeto;
	}


	public Long getValorByCondicionFisicaVal() {
		return valorByCondicionFisicaVal;
	}


	public void setValorByCondicionFisicaVal(Long valorByCondicionFisicaVal) {
		this.valorByCondicionFisicaVal = valorByCondicionFisicaVal;
	}


	public String getNombreObjeto() {
		return nombreObjeto;
	}


	public void setNombreObjeto(String nombreObjeto) {
		this.nombreObjeto = nombreObjeto;
	}


	public Long getRelacionHechoVal() {
		return relacionHechoVal;
	}


	public void setRelacionHechoVal(Long relacionHechoVal) {
		this.relacionHechoVal = relacionHechoVal;
	}

	public Long getAlmacen() {
		return almacen;
	}


	public void setAlmacen(Long almacen) {
		this.almacen = almacen;
	}


	public Boolean getEsPertenencia() {
		return esPertenencia;
	}


	public void setEsPertenencia(Boolean esPertenencia) {
		this.esPertenencia = esPertenencia;
	}
	
	public Boolean getEsActivo() {
		return esActivo;
	}


	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}


}
