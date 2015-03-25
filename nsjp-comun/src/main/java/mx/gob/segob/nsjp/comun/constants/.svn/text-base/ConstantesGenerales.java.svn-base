package mx.gob.segob.nsjp.comun.constants;

/**
 * Clase abstracta de constantes generales de la aplicaci贸n
 * @author Emigdio
 *
 */
public abstract class ConstantesGenerales {
	/**
	 * Tipo de contenido de la respuesta al emitir un reporte/documento en PDF
	 */
	public static final String CONTENT_TYPE_PDF="application/pdf";
	/**
	 * Tipo de contenido de la respuesta al emitir un reporte/documento en Excel
	 */
	public static final String CONTENT_TYPE_XLS="application/excel";
	/**
	 * Tipo de contenido de la respuesta al emitir un XML
	 */
	public static final String CONTENT_TYPE_XML = "text/xml";
	/**
	 * Header de HTML para el content disposition
	 */
	public static final String ENCABEZADO_CONTENT_DISPOSITION = "Content-Disposition";
	/**
	 * Header de HTML para el archivo adjunto
	 */
	public static final String ENCABEZADO_ATTACH_FILE_NAME ="attachment; filename=";
	public static final String ENCABEZADO_INLINE_FILE_NAME ="inline; filename=";
	/**
	 * Header de html para el control de cache
	 */
	public static final String ENCABEZADO_CACHE_CONTROL ="Cache-Control";
	/**
	 * Header de HTML para el pragma
	 */
	public static final String ENCABEZADO_PRAGMA ="pragma";
	/**
	 * Header de HTML para el no-cache
	 */
	public static final String ENCABEZADO_NOCACHE ="no-cache";
	/**
	 * Extensi贸n de un archivo PDF
	 */
	public static final String EXTENSION_PDF =".pdf";
	/**
	 * Extensi贸n de un archivo Excel
	 */
	public static final String EXTENSION_XLS =".xls";
	/**
	 * Tamanio maximo del nombre de un archivo 
	 */
	public static final int TAM_NOMBRE_ARCHIVO = 20;
		
	public final static String HTML_APERTURA = "<html>";
	
	public final static String HEAD_APERTURA = "<head>";
	
	public final static String HEAD_CIERRE = "</head>";
	
	public final static String BODY_TAG_APERTURA = "<body>";
	
	public final static String BODY_TAG_CIERRE = "</body>";
	
	// Tags definidas para la recuperaci髇 del documento en el editor
	public final static String CUERPO_DOCUMENTO_TAG_APERTURA = "<cuerpoDocumento>";
	
	public final static String CUERPO_DOCUMENTO_TAG_CIERRE = "</cuerpoDocumento>";
	
	// Tag definida para la b鷖queda del documento en el xml recuperado en el JSP  
	public final static String CUERPO_DOCUMENTO_TAG_BUSQUEDA = "cuerpoDocumento";

	// Tags definidas para la recuperaci髇 del nu_mero de folio en el editor
	public final static String NUMERO_FOLIO_TAG_APERTURA = "<numeroFolio>";
	
	public final static String NUMERO_FOLIO_TAG_CIERRE = "</numeroFolio>";
	
	//Tag definida para la b鷖queda del nu_mero de folio en el xml recuperado en el JSP
	public final static String NUMERO_FOLIO_TAG_BUSQUEDA = "numeroFolio";
	
	public final static String HTML_CIERRE = "</html>";
	
	public final static String C_DATA_OPEN = "<![CDATA[";
	
	public final static String C_DATA_CLOSE = "]]>";
	
	public final static String APERTURA_CAMPO_FORMATO = "&lt;";
	
	public final static String CIERRE_CAMPO_FORMATO = "&gt;";
	
	public final static String[] HORAS_AUDIENCIA = new String[] { "09:00",
			"09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
			"13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00",
			"16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30",
			"20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00" };
	
	
	
	//Audiencias
	/**
	 * N煤mero de jueces necesarios para una audiencia del tipo Juicio Oral
	 */
	public final static int JUECES_AUTOMATICOS_JUICIO_ORAL = 3;
	/**
	 * N煤mero de jueces necesarios para una audiencia que no sea del tipo Juicio Oral
	 */
	public final static int JUECES_AUTOMATICOS_PREDETERMINADO = 1;
	/**
	 * N煤mero de jueces sustitutos para la asignaci贸n autom谩tica 
	 */
	public final static int JUECES_AUTOMATICOS_SUSTITUTOS = 1;
	
	public final static int DIAS_ATRAS_CONSULTAS_HISTORICAS=120;
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	// El siguiente bloque de valores, no deben ser modificados, a menos de que los valores de retorno en el
	// WS-JAVS se modifiquen, de lo contrario, el flujo normal de tales acciones no ser谩 as铆.
	
	// Constantes de retorno del Servidor Web de JAVS - Agendar audiencias
	
	public final static int FALLO_CONEXION_WEB_SERVICE_JAVS=100;
	public final static int EXITO_AGENDAR_JAVS=1;
	public final static int EXITO_AGENDAR_JAVS_SIN_ARCH_OUT=2;
	public final static int EXITO_AGENDAR_JAVS_SIN_ARCH_IN=3;
	public final static int EXITO_AGENDAR_JAVS_SIN_ARCH_INOUT=4;
	public final static int ERROR_AGENDAR_JAVS=0;
	public final static int ERROR_PARAMETROS_CONEXION=5;
	public final static int ERROR_ELEMENTOS_INSUFICIENTES=6;
	public final static int ERROR_CREDENCIALES=7;
	
	// Constantes de retorno del Servidor Web de JAVS - Consultar estado audiencias
	
	public final static int AUDIENCIA_NO_ACTIVA=8;
	public final static int FALLO_GENERAL=11;	
	public final static int FALLO_GENERAL_JAVS=12;
	public final static int ERROR_CREDENCIALES_CONSULTA=13;
	public final static int AUDIENCIA_PROCESO=21;
	public final static int AUDIENCIA_TERMINO=20;
	
	public final static int NO_ES_JAVS=24;
	
	// Constantes de retorno del Servidor Web de JAVS - Eliminar audiencias
	
	public final static int ERROR_ELIMINACION=27;
	public final static int EXITO_ELIMINACION=26;
	public final static int NO_HAY_AUDIENCIAS=25;
	public final static int ERROR_SERVICIO_ELIMINACION=28;
	
	// Constantes de retorno del Servidor Web de JAVS - Consultar audiencias    8, 11, 12, 13, 25
	
	public final static int RESOLUTIVOS_ACTUALIZADOS=9;
	public final static int AUDIENCIA_ACTUALIZADA=10;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
//	Prueba para imprimir  caracteres
//	public static void main(String  args[]){
//		for(int cont=0; cont < CARACTERES_HTML.length; cont++){
//		}
//	}
}
