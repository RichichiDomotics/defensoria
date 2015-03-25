package mx.gob.segob.nsjp.dto.solicitud;

import java.util.List;


public class SolicitudMichDTO {
	
	private String fec_envio;
	private String fec_inicio;
	private String materia;
	private String mp;
	private String nombre;
	private String nuc;
	private String prioridad;
	private String promovente;
	private String solicitudId;
	private String tipo_despacho;
	private String tipo_ini;
	private String tipo_promocion;
	private String tipo_solicitud;
	private String unidad_inv;
	
	private List<ImputadoDTO> imputados;
	private List<ImputadoDelitoDTO> imputadosDelitos;
	private List<OfendidoDTO> ofendidos;
	private List<ImpDelOfendidoDTO> impDeOfendidos;
	private List<DocumentoWSMichDTO> Documentos;
	
	public List<OfendidoDTO> getOfendidos() {
		return ofendidos;
	}
	public void setOfendidos(List<OfendidoDTO> ofendidos) {
		this.ofendidos = ofendidos;
	}
	public List<ImpDelOfendidoDTO> getImpDeOfendidos() {
		return impDeOfendidos;
	}
	public void setImpDeOfendidos(List<ImpDelOfendidoDTO> impDeOfendidos) {
		this.impDeOfendidos = impDeOfendidos;
	}
	public List<DocumentoWSMichDTO> getDocumentos() {
		return Documentos;
	}
	public void setDocumentos(List<DocumentoWSMichDTO> documentos) {
		Documentos = documentos;
	}
	
	public List<ImputadoDTO> getImputados() {
		return imputados;
	}
	public void setImputados(List<ImputadoDTO> imputados) {
		this.imputados = imputados;
	}
	
	public List<ImputadoDelitoDTO> getImputadosDelitos() {
		return imputadosDelitos;
	}
	public void setImputadosDelitos(List<ImputadoDelitoDTO> imputadosDelitos) {
		this.imputadosDelitos = imputadosDelitos;
	}
	
	
	public String getTipo_promocion() {
		return tipo_promocion;
	}
	public void setTipo_promocion(String tipo_promocion) {
		this.tipo_promocion = tipo_promocion;
	}
	public String getFec_envio() {
		return fec_envio;
	}
	public void setFec_envio(String fec_envio) {
		this.fec_envio = fec_envio;
	}
	public String getFec_inicio() {
		return fec_inicio;
	}
	public void setFec_inicio(String fec_inicio) {
		this.fec_inicio = fec_inicio;
	}
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public String getMp() {
		return mp;
	}
	public void setMp(String mp) {
		this.mp = mp;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNuc() {
		return nuc;
	}
	public void setNuc(String nuc) {
		this.nuc = nuc;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getPromovente() {
		return promovente;
	}
	public void setPromovente(String promovente) {
		this.promovente = promovente;
	}
	public String getSolicitudId() {
		return solicitudId;
	}
	public void setSolicitudId(String solicitudId) {
		this.solicitudId = solicitudId;
	}
	public String getTipo_despacho() {
		return tipo_despacho;
	}
	public void setTipo_despacho(String tipo_despacho) {
		this.tipo_despacho = tipo_despacho;
	}
	public String getTipo_ini() {
		return tipo_ini;
	}
	public void setTipo_ini(String tipo_ini) {
		this.tipo_ini = tipo_ini;
	}
	public String getTipo_solicitud() {
		return tipo_solicitud;
	}
	public void setTipo_solicitud(String tipo_solicitud) {
		this.tipo_solicitud = tipo_solicitud;
	}
	public String getUnidad_inv() {
		return unidad_inv;
	}
	public void setUnidad_inv(String unidad_inv) {
		this.unidad_inv = unidad_inv;
	}

}
