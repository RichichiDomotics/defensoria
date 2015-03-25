package mx.gob.segob.nsjp.oaxaca.comun.encript;

public enum EncriptadorEnum {
	
	URL_SERVICIOS_ENCRIPTADOR("http://127.0.0.1:9696/EncriptadorRijndael.asmx?WSDL"),
	URL_SERVICIOS_ENCRIPTADOR_LOCAL("http://127.0.0.1:3747/EncriptadorRijndael.asmx?WSDL"),
	LLAVE("w@t¢hwÿrD"),
	ESPACIO_DE_NOMBRES("http://enable.com.mx/"),
	NOMBRE_SERVICIO("Service1");
	
	private String valor;
	
	private EncriptadorEnum(String valor){
		this.valor = valor;
	}
	
	public String getValor(){
		return valor;
	}
}
