/**
*
* Nombre del Programa : GuardarArchivoDigitalServiceImplTest.java                                    
* Autor                            : Emigdio Hernández                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 30/05/2011
* Marca de cambio        : N/A                                                     
* Descripcion General    : Prueba unitaria para el servicio de guardar archivo digital                    
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
package mx.gob.segob.nsjp.service.test.forma.impl;

import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.BODY_TAG_APERTURA;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.BODY_TAG_CIERRE;

import java.io.ByteArrayOutputStream;
import java.io.StringBufferInputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.HTMLUtils;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.service.forma.BuscarFormaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Prueba unitaria para el servicio de guardar archivo digital
 * @author Emigdio Herández
 * @version 1.0
 */
public class BuscarFormaServiceImplTest extends BaseTestServicios<BuscarFormaService> {

	public void testBuscarForma() {
		try {
			FormaDTO forma = service.buscarForma(new Long(1));			
			assertTrue("El servicio debe retornar la Forma consultada", forma != null);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void testConsultarTodasLasFormas () {
		try {
			List<FormaDTO> lstFormaDTO =  service.consultarTodasLasForma();
			for (FormaDTO formaDTO : lstFormaDTO) {
				if (formaDTO.getFormaId() == 364){ 
					try {
						logger.info("FORMA ID: " + formaDTO.getFormaId());
						ByteArrayOutputStream baos = generarReportePDFDeHTML(formaDTO.getCuerpo());
						logger.info(baos != null ? "EXITO" : "NULLPOINTER");	
					} catch (Exception e) {
						logger.error("ERROR: ", e);
					}
				}
			}
			
			assertTrue("El servicio debe retornar todas las Forma ", (lstFormaDTO != null && !lstFormaDTO.isEmpty()));
		} catch (NSJPNegocioException e) {
			logger.error("ERROR: ", e);
		}
	}	
	

public ByteArrayOutputStream generarReportePDFDeHTML(String xHTML){
		
		ByteArrayOutputStream archivoPDF = null;
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//logger.info(xHTML);
			//logger.info("******************************************************************************************+");
			xHTML = HTMLUtils.encodeHtmlToXhtml(BODY_TAG_APERTURA 
    				+ xHTML +
    				BODY_TAG_CIERRE);
			logger.info(xHTML);
			logger.info("******************************************************************************************+");
			logger.info("******************************************************************************************+");
			logger.info("******************************************************************************************+");			
		    @SuppressWarnings("deprecation")
			Document doc = builder.parse(new StringBufferInputStream(xHTML));

		    ITextRenderer renderer = new ITextRenderer();
		    renderer.setDocument(doc, null);
		    
	        archivoPDF = new ByteArrayOutputStream();
	        
		    renderer.layout();
		    
		    renderer.createPDF(archivoPDF);
		}catch (Exception e) {
			logger.error(e);
		}
		
	    
	    return archivoPDF;
		
	}
	
	
	
}
