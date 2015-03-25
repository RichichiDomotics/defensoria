/**
* Nombre del Programa : PDFPropiedad.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 13/07/2012
* Marca de cambio        : N/A
* Descripcion General    : Enumeración para la configuracion de la propiedades del archivo PDF.  
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
package mx.gob.segob.nsjp.comun.enums.pdf;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeración para la configuracion de la propiedades del archivo PDF.
 * 
 * @author GustavoBP
 */
public enum PDFPropiedad {
	Oficio(1L, "Oficio", "<style> @page { size: 215.9mm 355.6mm;} </style>"), //Oficio: 215.9 mm ancho y 355.6 mm alto.
	Carta(2L, "Carta", "<style> @page { size: 215.9mm 279.4mm;} </style>"), //Carta: 215.9 mm ancho y 279.4 mm alto.
	A5(3L, "A5", "<style> @page { size:A5;} </style>"), //A5: 148 mm ancho y 210 mm alto.
	A4(4L, "A4", "<style> @page { size:A4;} </style>"), //A4: 210 mm ancho y 297 mm alto.
	A3(5L, "A3", "<style> @page { size:A3;} </style>"), //A3: 297 mm ancho y 420 mm alto.
	B5(6L, "B5", "<style> @page { size:B5;} </style>"), //B3: 176 mm ancho por 250 mm alto.
	B4(7L, "B4", "<style> @page { size:B4;} </style>"), //B4: 250 mm ancho por 353 mm alto.
	Letter(8L, "Letter", "<style> @page { size:letter;} </style>"), //letter: 215.9 mm(8.5 inches) ancho y 279.4 mm(11 inches) alto
	Legal(9L, "Legal", "<style> @page { size:legal;} </style>"), //legal: 215.9 mm(8.5 inches) ancho por 355.6mm (14 inches) alto.
	Ledger(10L, "Ledger", "<style> @page { size:ledger;} </style>"); //ledger: 279.4 mm(11 inches) ancho por 431.8mm (17 inches) alto. 

	private Long valorId;
	private String nombre;
	private String html;
	
	private final static Map<Long, PDFPropiedad> hash = new HashMap<Long, PDFPropiedad>();
	
	static {
		PDFPropiedad[] valores = PDFPropiedad.values();
		int pos = 0;
		while (pos < valores.length) {
			hash.put(valores[pos].getValorId(), valores[pos]);
			pos++;
		}
	}
	 
	/**
	 * 
	 * @param valorId
	 * @param nombre
	 * @param html
	 */
	private PDFPropiedad(Long valorId, String nombre, String html) {
		this.valorId = valorId;
		this.nombre = nombre;
		this.html = html;
	}
	
	public static PDFPropiedad getByValor(Long valorIdPredefinido) {
        return hash.get(valorIdPredefinido);
    }
	
	/**
	 * @return the valorId
	 */
	public Long getValorId() {
		return valorId;
	}


	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}
}
