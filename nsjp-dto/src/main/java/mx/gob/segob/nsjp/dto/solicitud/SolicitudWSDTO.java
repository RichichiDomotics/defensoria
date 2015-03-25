/**
 * Nombre del Programa : SolicitudWSDTO.java
 * Autor                            : AntonioBV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 03 Sep 2012
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dto.solicitud;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioWSDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoWSDTO;

/**
 * 
 * @version 1.0
 * @author AntonioBV
 * 
 */
public class SolicitudWSDTO extends DocumentoWSDTO {

    	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2728668782953242544L;

	private Long idTipoSolicitud;
    private Long areaOrigen;
    private Long areaDestino;
    private String numeroCasoAsociado;
    private Long idEstatus;
    private FuncionarioWSDTO usuarioSolicitante;
    private Date fechaLimite;
    private Date fechaModificacion;
    private Date fechaCierre;    
    private String motivo;
    private Long solicitanteExterno;
    private Boolean esUrgente;
    private Long numeroExpedienteId;
    //documentos que viajan junto con la solicitud
    private List<DocumentoWSDTO> lstDocumentosAdjuntos;
    private String numeroCausaSentencia;
    
   
    /**
     * Campos a mostrar, estos campos se llenan dependiendo si es una solicitud externa o interna
     * con el resultado correspondiente
     */
    private String nombreInstitucionSolicitante;
    
    private String nombreSolicitanteExternoInterno;
    /**
     * Institucion
     */
    private Long idInstitucion;
    
    
    private FuncionarioWSDTO destinatario;
    /**
     * Folio de la solicitud.<br>
     * Se deben mostrar en todas las bandejas de solicitudes.
     */
    private String folioSolicitud;
    
    private String asuntoSolicitud;
    
    private String observaciones;
    
    
    private FuncionarioExternoWSDTO destinatarioInstExterna;
    
    private FuncionarioExternoWSDTO solicitanteInstExterna;
    
	
    public SolicitudWSDTO() {
		super();
	}


	/**
	 * Método de acceso al campo idTipoSolicitud.
	 * @return El valor del campo idTipoSolicitud
	 */
	public Long getIdTipoSolicitud() {
		return idTipoSolicitud;
	}


	/**
	 * Asigna el valor al campo idTipoSolicitud.
	 * @param idTipoSolicitud el valor idTipoSolicitud a asignar
	 */
	public void setIdTipoSolicitud(Long idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}


	/**
	 * Método de acceso al campo areaOrigen.
	 * @return El valor del campo areaOrigen
	 */
	public Long getAreaOrigen() {
		return areaOrigen;
	}


	/**
	 * Asigna el valor al campo areaOrigen.
	 * @param areaOrigen el valor areaOrigen a asignar
	 */
	public void setAreaOrigen(Long areaOrigen) {
		this.areaOrigen = areaOrigen;
	}


	/**
	 * Método de acceso al campo areaDestino.
	 * @return El valor del campo areaDestino
	 */
	public Long getAreaDestino() {
		return areaDestino;
	}


	/**
	 * Asigna el valor al campo areaDestino.
	 * @param areaDestino el valor areaDestino a asignar
	 */
	public void setAreaDestino(Long areaDestino) {
		this.areaDestino = areaDestino;
	}


	/**
	 * Método de acceso al campo numeroCasoAsociado.
	 * @return El valor del campo numeroCasoAsociado
	 */
	public String getNumeroCasoAsociado() {
		return numeroCasoAsociado;
	}


	/**
	 * Asigna el valor al campo numeroCasoAsociado.
	 * @param numeroCasoAsociado el valor numeroCasoAsociado a asignar
	 */
	public void setNumeroCasoAsociado(String numeroCasoAsociado) {
		this.numeroCasoAsociado = numeroCasoAsociado;
	}


	/**
	 * Método de acceso al campo idEstatus.
	 * @return El valor del campo idEstatus
	 */
	public Long getIdEstatus() {
		return idEstatus;
	}


	/**
	 * Asigna el valor al campo idEstatus.
	 * @param idEstatus el valor idEstatus a asignar
	 */
	public void setIdEstatus(Long idEstatus) {
		this.idEstatus = idEstatus;
	}


	/**
	 * Método de acceso al campo usuarioSolicitante.
	 * @return El valor del campo usuarioSolicitante
	 */
	public FuncionarioWSDTO getUsuarioSolicitante() {
		return usuarioSolicitante;
	}


	/**
	 * Asigna el valor al campo usuarioSolicitante.
	 * @param usuarioSolicitante el valor usuarioSolicitante a asignar
	 */
	public void setUsuarioSolicitante(FuncionarioWSDTO usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}


	/**
	 * Método de acceso al campo fechaLimite.
	 * @return El valor del campo fechaLimite
	 */
	public Date getFechaLimite() {
		return fechaLimite;
	}


	/**
	 * Asigna el valor al campo fechaLimite.
	 * @param fechaLimite el valor fechaLimite a asignar
	 */
	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}


	/**
	 * Método de acceso al campo fechaModificacion.
	 * @return El valor del campo fechaModificacion
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}


	/**
	 * Asigna el valor al campo fechaModificacion.
	 * @param fechaModificacion el valor fechaModificacion a asignar
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}


	/**
	 * Método de acceso al campo fechaCierre.
	 * @return El valor del campo fechaCierre
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}


	/**
	 * Asigna el valor al campo fechaCierre.
	 * @param fechaCierre el valor fechaCierre a asignar
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}


	/**
	 * Método de acceso al campo motivo.
	 * @return El valor del campo motivo
	 */
	public String getMotivo() {
		return motivo;
	}


	/**
	 * Asigna el valor al campo motivo.
	 * @param motivo el valor motivo a asignar
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}


	/**
	 * Método de acceso al campo solicitanteExterno.
	 * @return El valor del campo solicitanteExterno
	 */
	public Long getSolicitanteExterno() {
		return solicitanteExterno;
	}


	/**
	 * Asigna el valor al campo solicitanteExterno.
	 * @param solicitanteExterno el valor solicitanteExterno a asignar
	 */
	public void setSolicitanteExterno(Long solicitanteExterno) {
		this.solicitanteExterno = solicitanteExterno;
	}


	/**
	 * Método de acceso al campo esUrgente.
	 * @return El valor del campo esUrgente
	 */
	public Boolean getEsUrgente() {
		return esUrgente;
	}


	/**
	 * Asigna el valor al campo esUrgente.
	 * @param esUrgente el valor esUrgente a asignar
	 */
	public void setEsUrgente(Boolean esUrgente) {
		this.esUrgente = esUrgente;
	}


	/**
	 * Método de acceso al campo nombreInstitucionSolicitante.
	 * @return El valor del campo nombreInstitucionSolicitante
	 */
	public String getNombreInstitucionSolicitante() {
		return nombreInstitucionSolicitante;
	}


	/**
	 * Asigna el valor al campo nombreInstitucionSolicitante.
	 * @param nombreInstitucionSolicitante el valor nombreInstitucionSolicitante a asignar
	 */
	public void setNombreInstitucionSolicitante(String nombreInstitucionSolicitante) {
		this.nombreInstitucionSolicitante = nombreInstitucionSolicitante;
	}


	/**
	 * Método de acceso al campo nombreSolicitanteExternoInterno.
	 * @return El valor del campo nombreSolicitanteExternoInterno
	 */
	public String getNombreSolicitanteExternoInterno() {
		return nombreSolicitanteExternoInterno;
	}


	/**
	 * Asigna el valor al campo nombreSolicitanteExternoInterno.
	 * @param nombreSolicitanteExternoInterno el valor nombreSolicitanteExternoInterno a asignar
	 */
	public void setNombreSolicitanteExternoInterno(
			String nombreSolicitanteExternoInterno) {
		this.nombreSolicitanteExternoInterno = nombreSolicitanteExternoInterno;
	}


	/**
	 * Método de acceso al campo idInstitucion.
	 * @return El valor del campo idInstitucion
	 */
	public Long getIdInstitucion() {
		return idInstitucion;
	}


	/**
	 * Asigna el valor al campo idInstitucion.
	 * @param idInstitucion el valor idInstitucion a asignar
	 */
	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}


	/**
	 * Método de acceso al campo destinatario.
	 * @return El valor del campo destinatario
	 */
	public FuncionarioWSDTO getDestinatario() {
		return destinatario;
	}


	/**
	 * Asigna el valor al campo destinatario.
	 * @param destinatario el valor destinatario a asignar
	 */
	public void setDestinatario(FuncionarioWSDTO destinatario) {
		this.destinatario = destinatario;
	}


	/**
	 * Método de acceso al campo folioSolicitud.
	 * @return El valor del campo folioSolicitud
	 */
	public String getFolioSolicitud() {
		return folioSolicitud;
	}


	/**
	 * Asigna el valor al campo folioSolicitud.
	 * @param folioSolicitud el valor folioSolicitud a asignar
	 */
	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}


	/**
	 * Método de acceso al campo asuntoSolicitud.
	 * @return El valor del campo asuntoSolicitud
	 */
	public String getAsuntoSolicitud() {
		return asuntoSolicitud;
	}


	/**
	 * Asigna el valor al campo asuntoSolicitud.
	 * @param asuntoSolicitud el valor asuntoSolicitud a asignar
	 */
	public void setAsuntoSolicitud(String asuntoSolicitud) {
		this.asuntoSolicitud = asuntoSolicitud;
	}


	/**
	 * Método de acceso al campo observaciones.
	 * @return El valor del campo observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}


	/**
	 * Asigna el valor al campo observaciones.
	 * @param observaciones el valor observaciones a asignar
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	/**
	 * Método de acceso al campo destinatarioInstExterna.
	 * @return El valor del campo destinatarioInstExterna
	 */
	public FuncionarioExternoWSDTO getDestinatarioInstExterna() {
		return destinatarioInstExterna;
	}


	/**
	 * Asigna el valor al campo destinatarioInstExterna.
	 * @param destinatarioInstExterna el valor destinatarioInstExterna a asignar
	 */
	public void setDestinatarioInstExterna(
			FuncionarioExternoWSDTO destinatarioInstExterna) {
		this.destinatarioInstExterna = destinatarioInstExterna;
	}


	/**
	 * Método de acceso al campo solicitanteInstExterna.
	 * @return El valor del campo solicitanteInstExterna
	 */
	public FuncionarioExternoWSDTO getSolicitanteInstExterna() {
		return solicitanteInstExterna;
	}


	/**
	 * Asigna el valor al campo solicitanteInstExterna.
	 * @param solicitanteInstExterna el valor solicitanteInstExterna a asignar
	 */
	public void setSolicitanteInstExterna(
			FuncionarioExternoWSDTO solicitanteInstExterna) {
		this.solicitanteInstExterna = solicitanteInstExterna;
	}


	/**
	 * Método de acceso al campo numeroExpedienteId.
	 * @return El valor del campo numeroExpedienteId
	 */
	public Long getNumeroExpedienteId() {
		return numeroExpedienteId;
	}


	/**
	 * Asigna el valor al campo numeroExpedienteId.
	 * @param numeroExpedienteId el valor numeroExpedienteId a asignar
	 */
	public void setNumeroExpedienteId(Long numeroExpedienteId) {
		this.numeroExpedienteId = numeroExpedienteId;
	}


	/**
	 * Método de acceso al campo lstDocumentosAdjuntos.
	 * @return El valor del campo lstDocumentosAdjuntos
	 */
	public List<DocumentoWSDTO> getLstDocumentosAdjuntos() {
		return lstDocumentosAdjuntos;
	}


	/**
	 * Asigna el valor al campo lstDocumentosAdjuntos.
	 * @param lstDocumentosAdjuntos el valor lstDocumentosAdjuntos a asignar
	 */
	public void setLstDocumentosAdjuntos(List<DocumentoWSDTO> lstDocumentosAdjuntos) {
		this.lstDocumentosAdjuntos = lstDocumentosAdjuntos;
	}


	/**
	 * Método de acceso al campo numeroCausaSentencia.
	 * @return El valor del campo numeroCausaSentencia
	 */
	public String getNumeroCausaSentencia() {
		return numeroCausaSentencia;
	}


	/**
	 * Asigna el valor al campo numeroCausaSentencia.
	 * @param numeroCausaSentencia el valor numeroCausaSentencia a asignar
	 */
	public void setNumeroCausaSentencia(String numeroCausaSentencia) {
		this.numeroCausaSentencia = numeroCausaSentencia;
	}
	
}
