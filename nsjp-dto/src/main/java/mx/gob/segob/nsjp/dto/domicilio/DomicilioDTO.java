/**
*
* Nombre del Programa : DomicilioDTO.java                                    
* Autor                            : Cesar Agustin                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 05/04/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : DTO para la transferencia de datos entre la vista y servicios de domicilio                     
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

package mx.gob.segob.nsjp.dto.domicilio;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;


/**
 * @author CesarAgustin
 * @version 1.0	
 */
public class DomicilioDTO extends LugarDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2368977224584166658L;
		
	private String calle;
	private String numeroExterior;
	private String numeroInterior;
	private String numeroLote;
	private String referencias;
	private String entreCalle1;
	private String entreCalle2;
	private String alias;
	private String edificio;
	private ValorDTO valorCalleId;	
	private InvolucradoDTO involucradoDTO;
	private EntidadFederativaDTO entidadDTO;
	private AsentamientoDTO asentamientoDTO;
	private MunicipioDTO municipioDTO;
	private CiudadDTO ciudadDTO;
	
	public DomicilioDTO(String calle, String numeroExterior,
			String numeroInterior, String numeroLote, String referencias,
			String entreCalle1, String entreCalle2, String alias,
			String edificio, Boolean esNotificacion) {
		super();		
		this.calle = calle;
		this.numeroExterior = numeroExterior;
		this.numeroInterior = numeroInterior;
		this.numeroLote = numeroLote;
		this.referencias = referencias;
		this.entreCalle1 = entreCalle1;
		this.entreCalle2 = entreCalle2;
		this.alias = alias;
		this.edificio = edificio;		
		this.edificio = edificio;
	}
	
	/**
	 * 
	 */
	public DomicilioDTO() {
		
	}
		
	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}
	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}
	/**
	 * @return the numeroExterior
	 */
	public String getNumeroExterior() {
		return numeroExterior;
	}
	/**
	 * @param numeroExterior the numeroExterior to set
	 */
	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}
	/**
	 * @return the numeroInterior
	 */
	public String getNumeroInterior() {
		return numeroInterior;
	}
	/**
	 * @param numeroInterior the numeroInterior to set
	 */
	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}
	/**
	 * @return the numeroLote
	 */
	public String getNumeroLote() {
		return numeroLote;
	}
	/**
	 * @param numeroLote the numeroLote to set
	 */
	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}
	/**
	 * @return the referencias
	 */
	public String getReferencias() {
		return referencias;
	}
	/**
	 * @param referencias the referencias to set
	 */
	public void setReferencias(String referencias) {
		this.referencias = referencias;
	}
	/**
	 * @return the entreCalle1
	 */
	public String getEntreCalle1() {
		return entreCalle1;
	}
	/**
	 * @param entreCalle1 the entreCalle1 to set
	 */
	public void setEntreCalle1(String entreCalle1) {
		this.entreCalle1 = entreCalle1;
	}
	/**
	 * @return the entreCalle2
	 */
	public String getEntreCalle2() {
		return entreCalle2;
	}
	/**
	 * @param entreCalle2 the entreCalle2 to set
	 */
	public void setEntreCalle2(String entreCalle2) {
		this.entreCalle2 = entreCalle2;
	}
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * @return the edificio
	 */
	public String getEdificio() {
		return edificio;
	}
	/**
	 * @param edificio the edificio to set
	 */
	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	/**
	 * Método de acceso al campo involucradoDTO.
	 * @return El valor del campo involucradoDTO
	 */
	public InvolucradoDTO getInvolucradoDTO() {
		return involucradoDTO;
	}

	/**
	 * Asigna el valor al campo involucradoDTO.
	 * @param involucradoDTO el valor involucradoDTO a asignar
	 */
	public void setInvolucradoDTO(InvolucradoDTO involucradoDTO) {
		this.involucradoDTO = involucradoDTO;
	}

	/**
	 * Método de acceso al campo asentamientoDTO.
	 * @return El valor del campo asentamientoDTO
	 */
	public AsentamientoDTO getAsentamientoDTO() {
		return asentamientoDTO;
	}

	/**
	 * Asigna el valor al campo asentamientoDTO.
	 * @param asentamientoDTO el valor asentamientoDTO a asignar
	 */
	public void setAsentamientoDTO(AsentamientoDTO asentamientoDTO) {
		this.asentamientoDTO = asentamientoDTO;
	}

	/**
	 * Método de acceso al campo valorCalleId.
	 * @return El valor del campo valorCalleId
	 */
	public ValorDTO getValorCalleId() {
		return valorCalleId;
	}

	/**
	 * Asigna el valor al campo valorCalleId.
	 * @param valorCalleId el valor valorCalleId a asignar
	 */
	public void setValorCalleId(ValorDTO valorCalleId) {
		this.valorCalleId = valorCalleId;
	}

	/**
	 * Método de acceso al campo entidadDTO.
	 * @return El valor del campo entidadDTO
	 */
	public EntidadFederativaDTO getEntidadDTO() {
		return entidadDTO;
	}

	/**
	 * Asigna el valor al campo entidadDTO.
	 * @param entidadDTO el valor entidadDTO a asignar
	 */
	public void setEntidadDTO(EntidadFederativaDTO entidadDTO) {
		this.entidadDTO = entidadDTO;
	}

	/**
	 * Método de acceso al campo municipioDTO.
	 * @return El valor del campo municipioDTO
	 */
	public MunicipioDTO getMunicipioDTO() {
		return municipioDTO;
	}

	/**
	 * Asigna el valor al campo municipioDTO.
	 * @param municipioDTO el valor municipioDTO a asignar
	 */
	public void setMunicipioDTO(MunicipioDTO municipioDTO) {
		this.municipioDTO = municipioDTO;
	}

	/**
	 * Método de acceso al campo ciudadDTO.
	 * @return El valor del campo ciudadDTO
	 */
	public CiudadDTO getCiudadDTO() {
		return ciudadDTO;
	}

	/**
	 * Asigna el valor al campo ciudadDTO.
	 * @param ciudadDTO el valor ciudadDTO a asignar
	 */
	public void setCiudadDTO(CiudadDTO ciudadDTO) {
		this.ciudadDTO = ciudadDTO;
	}

	/**
	 * M&eacute;todo que regresa la direcci&oacute;n en un <code>String<code> 
	 * @return 	<code>String<code> con los campos que no sean <code>null<code> 
	 * 			ordenados por: Tipo Calle, Calle, No. Exterior, No. Interior, 
	 * 			No. Lote, Edificio, Tipo Asentamiento, Asentamiento, C.P., 
	 * 			Ciudad, Municipio y Entidad
	 */
	public String getDireccion(){
		StringBuilder direccion = new StringBuilder();

		ValorDTO valorDTO = getValorCalleId();
		if (valorDTO != null) {
			if (!valorDTO.getValor().isEmpty()) {
				direccion.append(valorDTO.getValor()).append(" ");
			}
		}
		if (getCalle() != null && !getCalle().isEmpty()) {
			direccion.append(getCalle()).append(" ");
		}
		if (getNumeroExterior() != null && !getNumeroExterior().isEmpty()) {
			direccion.append("No. Ext. ").append(getNumeroExterior())
					.append(" ");
		}
		if (getNumeroInterior() != null && !getNumeroInterior().isEmpty()) {
			direccion.append("No. Int. ").append(getNumeroInterior())
					.append(" ");
		}
		if (getNumeroLote() != null && !getNumeroLote().isEmpty()) {
			direccion.append("Lote ").append(getNumeroLote()).append(" ");
		}
		if (getEdificio() != null && !getEdificio().isEmpty()) {
			direccion.append("Edificio ").append(getEdificio()).append(" ");
		}

		if (getAsentamientoDTO() != null) {
			AsentamientoDTO asentamientoDTO = getAsentamientoDTO();
			if (asentamientoDTO.getTipoAsentamientoDTO() != null) {
				TipoAsentamientoDTO tipoAsentamientoDTO = asentamientoDTO
						.getTipoAsentamientoDTO();
				if (!tipoAsentamientoDTO.getTipoAsentamiento().isEmpty()) {
					direccion.append(tipoAsentamientoDTO.getTipoAsentamiento())
							.append(" ");
				}
			}
			if (asentamientoDTO.getNombreAsentamiento() != null
					&& !asentamientoDTO.getNombreAsentamiento().isEmpty()) {
				direccion.append(asentamientoDTO.getNombreAsentamiento())
						.append(" ");
			}
			if (asentamientoDTO.getCodigoPostal() != null
					&& !asentamientoDTO.getCodigoPostal().isEmpty()) {
				direccion.append("C.P. ")
						.append(asentamientoDTO.getCodigoPostal()).append(" ");
			}
		}

		if (getCiudadDTO() != null) {
			CiudadDTO ciudadDTO = getCiudadDTO();
			if (ciudadDTO.getNombreCiudad() != null
					&& !ciudadDTO.getNombreCiudad().isEmpty()) {
				direccion.append(ciudadDTO.getNombreCiudad()).append(" ");
			}
		}
		if (getMunicipioDTO() != null) {
			MunicipioDTO municipioDTO = getMunicipioDTO();
			if (municipioDTO.getNombreMunicipio() != null
					&& !municipioDTO.getNombreMunicipio().isEmpty()) {
				direccion.append(municipioDTO.getNombreMunicipio()).append(" ");
			}
		}
		if (getEntidadDTO() != null) {
			EntidadFederativaDTO entidadFederativaDTO = getEntidadDTO();
			if (entidadFederativaDTO.getNombreEntidad() != null
					&& !entidadFederativaDTO.getNombreEntidad().isEmpty()) {
				direccion.append(entidadFederativaDTO.getNombreEntidad())
						.append(" ");
			}
		}

		return direccion.toString();
	}
	
	/**
	 * Enable IT Metodo para obtener todo el domicilio del involucrado
	 * @return
	 */
	public String getDomicilioCompleto(){
		StringBuilder strb = new StringBuilder();
		if(valorCalleId != null)
			strb.append(" " + valorCalleId.getValor().trim());
		
		if(calle != null && !calle.equals(""))
			strb.append(" " + calle.trim()); 
		
		if(numeroExterior != null && !numeroExterior.equals(""))
			strb.append(" No. " + numeroExterior.trim());
		
		if(numeroInterior != null && !numeroInterior.equals(""))
			strb.append(", Interior " + numeroInterior.trim());

		if(entreCalle1 != null && !entreCalle1.trim().equals(""))
			strb.append(", entre " + entreCalle1);
		
		if(entreCalle2 != null && !entreCalle2.trim().equals(""))
			strb.append(" y " + entreCalle2);
		
		if(asentamientoDTO != null){ 
			if(asentamientoDTO.getTipoAsentamientoDTO() != null && asentamientoDTO.getTipoAsentamientoDTO().getTipoAsentamiento() != null){
				strb.append(", " + asentamientoDTO.getTipoAsentamientoDTO().getTipoAsentamiento().trim());
			}
			if(asentamientoDTO.getNombreAsentamiento() != null){
				strb.append(" " + asentamientoDTO.getNombreAsentamiento().trim());
			}
		}
			
		if(ciudadDTO != null && ciudadDTO.getNombreCiudad() != null && !ciudadDTO.getNombreCiudad().equals(""))
			strb.append(", " + ciudadDTO.getNombreCiudad().trim());
		
		if(municipioDTO != null && municipioDTO.getNombreMunicipio() != null && !municipioDTO.getNombreMunicipio().equals(""))
			strb.append(", " + municipioDTO.getNombreMunicipio().trim());
		
		if(ciudadDTO != null && ciudadDTO.getEntidadFederativaDTO() != null && ciudadDTO.getEntidadFederativaDTO().getNombreEntidad() != null 
				&& !ciudadDTO.getEntidadFederativaDTO().getNombreEntidad().equals(""))
			strb.append(", " + ciudadDTO.getEntidadFederativaDTO().getNombreEntidad().trim());
		
		return 	strb.toString().toUpperCase();
	}
	
}
