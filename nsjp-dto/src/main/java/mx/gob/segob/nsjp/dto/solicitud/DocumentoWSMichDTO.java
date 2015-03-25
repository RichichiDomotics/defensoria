package mx.gob.segob.nsjp.dto.solicitud;

public class DocumentoWSMichDTO {
	
//	private byte[] archivoByte;
//	private short consec_doc;
	private String archivo;
	private String consec_doc;
	private String formato;
	private String nombre;


	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
//	public byte[] getArchivoByte() {
//		return archivoByte;
//	}
//	public void setArchivoByte(byte[] archivoByte) {
//		this.archivoByte = archivoByte;
//	}
	public String getConsec_doc() {
		return consec_doc;
	}
	public void setConsec_doc(String consec_doc) {
		this.consec_doc = consec_doc;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
