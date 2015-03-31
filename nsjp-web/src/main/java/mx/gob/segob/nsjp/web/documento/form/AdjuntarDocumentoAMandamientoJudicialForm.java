/**
 * 
 */
package mx.gob.segob.nsjp.web.documento.form;

import mx.gob.segob.nsjp.web.base.form.GenericForm;

import org.apache.struts.upload.FormFile;

/**
 * Clase para obtener los parametros para adjuntar un documento a un mandamiento judicial
 * @author AlejandroGA
 *
 */
public class AdjuntarDocumentoAMandamientoJudicialForm extends GenericForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** archivo adjunto */
	private FormFile archivoAdjunto;

	/**Id del mandamiento judicial*/
	private Long mandamientoJudicialId;
	
	/**Id del numero de expediente*/
	private Long idNumeroExpediente;
	
	/** tipo de archivo*/
	private Long tipo;	
	
	/** Folio del documento*/
	private String folio;
	
	/**Descripcion del archivo*/
	private String descripcion;
	

	/**
	 * @return the archivoAdjunto
	 */
	public FormFile getArchivoAdjunto() {
		return archivoAdjunto;
	}

	/**
	 * @param archivoAdjunto the archivoAdjunto to set
	 */
	public void setArchivoAdjunto(FormFile archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}

	/**
	 * @return the mandamientoJudicialId
	 */
	public Long getMandamientoJudicialId() {
		return mandamientoJudicialId;
	}

	/**
	 * @param mandamientoJudicialId the mandamientoJudicialId to set
	 */
	public void setMandamientoJudicialId(Long mandamientoJudicialId) {
		this.mandamientoJudicialId = mandamientoJudicialId;
	}

	/**
	 * @return the idNumeroExpediente
	 */
	public Long getIdNumeroExpediente() {
		return idNumeroExpediente;
	}

	/**
	 * @param idNumeroExpediente the idNumeroExpediente to set
	 */
	public void setIdNumeroExpediente(Long idNumeroExpediente) {
		this.idNumeroExpediente = idNumeroExpediente;
	}

	/**
	 * @return the tipo
	 */
	public Long getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
