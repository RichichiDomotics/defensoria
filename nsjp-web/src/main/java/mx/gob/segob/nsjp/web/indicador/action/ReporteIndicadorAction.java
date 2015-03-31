/**
* Nombre del Programa : ReporteIndicadorAction.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 18/06/2012
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.web.indicador.action;

import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.CONTENT_TYPE_XLS;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_ATTACH_FILE_NAME;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_CACHE_CONTROL;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_CONTENT_DISPOSITION;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_NOCACHE;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_PRAGMA;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.EXTENSION_XLS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.indicador.Indicadores;
import mx.gob.segob.nsjp.delegate.indicador.IndicadorDelegate;
import mx.gob.segob.nsjp.dto.indicador.IndicadorDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import net.sf.jasperreports.engine.JRException;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Esta clase permite la generaci&oacute;n de los indicadores y ser generados
 * en formato de excel (xls)
 * 
 * @author GustavoBP
 */
public class ReporteIndicadorAction extends GenericAction {
	
	private static final Logger LOGGER= Logger
            .getLogger(ReporteIndicadorAction.class);
	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	
	//Se agregan constantes que hacen match con las llaves de los parametros de los nuevos indicadores.
	private static final String LLAVE_ESTATUS_AUDIENCIA_PROGRAMADA = "estadoProgramada";
	private static final String LLAVE_ESTATUS_AUDIENCIA_REPROGRAMADA = "estadoReprogramada";
	private static final String LLAVE_ESTATUS_AUDIENCIA_CANCELADA = "estadoCancelada";
	private static final String LLAVE_ESTATUS_AUDIENCIA_FINALIZADA = "estadoFinalizada";
	private static final String LLAVE_ROL_ID_JUEZ = "rolIdJuez";
	
	@Autowired
    private IndicadorDelegate indicadorDelegate;
	
	public ActionForward consultarIndicadoresInstitucion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
		
		try {
			
			List<IndicadorDTO> listaIndicadoresDTO = indicadorDelegate.consultarIndicadorPorInstitucionActual();
			
			converter.alias("listaIndicadores", java.util.List.class);
			converter.alias("indicadorDTO", IndicadorDTO.class);
			String xml = converter.toXML(listaIndicadoresDTO);

			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();		
			
		} catch (NSJPNegocioException e) {
			LOGGER.error(e.getMessage(), e);
		}
			
		return null;
	}
	
	public ActionForward generarReporteIndicador(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            //Parametros
            LOGGER.info("Tiempo Inicial::" + request.getParameter("tiempoI"));
            LOGGER.info("Tiempo Final::" + request.getParameter("tiempoF"));
            LOGGER.info("Tipo Indicador::" + request.getParameter("indicadorId"));
            
            String fechaInicial = request.getParameter("tiempoI");
            String fechaFin = request.getParameter("tiempoF");
            Long tipoIndicador = NumberUtils.toLong(request.getParameter("indicadorId"), 0L);
            Indicadores indicador = Indicadores.getByValor(tipoIndicador);
            
            String tituloReporte = indicador.getTitulo();
            String tituloReporteRecortado = tituloReporte;
            if(tituloReporte.length()>ConstantesGenerales.TAM_NOMBRE_ARCHIVO){
            	tituloReporteRecortado = tituloReporte.substring(0, ConstantesGenerales.TAM_NOMBRE_ARCHIVO);
            }
            LOGGER.info("Tiempo inicial::" + fechaInicial);
            LOGGER.info("Tiempo Final::" + fechaFin);
            LOGGER.info("Tipo Indicador::" + tipoIndicador);
            LOGGER.info("Tipo Indicador::" + indicador);

            HashMap<String, String> valores = new HashMap<String, String>();
    		//Nombre de las Columnas
    		List<String> nombreParametros = indicador.getParametros();

    		//
    		valores.put(nombreParametros.get(0).toString(), fechaInicial);
    		valores.put(nombreParametros.get(1).toString(), fechaFin);
    		
    		//Se agrega la llamada para completar los valores de los nuevos indicadores
    		completarMapaValores(indicador, valores);
    		
			List<Object[]> valoresResultado = indicadorDelegate
						.consultarIndicador(indicador, valores);
				
            ByteArrayOutputStream baos = this.generarReporteJasperExcel(tituloReporte, tituloReporteRecortado, valoresResultado);
            
            
            this.escribirReporteExcel(response, baos,
                    String.valueOf(tituloReporte)
                            .replace(" ", "")
                            + SDF.format(new Date()));
            
        } catch (NSJPNegocioException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (JRException e) {
        	 LOGGER.error(e.getMessage(), e);
		}
        return null;
    }


    public ByteArrayOutputStream generarReporteJasperExcel(
            String tituloReporte, String tituloReporteRecortado, 
            List<Object[]> parametrosReporte) throws JRException {
    	ByteArrayOutputStream baos = null;
    	
    	if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("parametrosReporte :: " + parametrosReporte);
        }
        Workbook wb = new HSSFWorkbook();
        Map<String, CellStyle> styles = createStyles(wb);
        
//        Map<String, CellStyle> styles = createStyles(wb);
        Sheet sheet = wb.createSheet(tituloReporteRecortado);
        sheet.setDisplayGridlines(false);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
    	
        //the following three statements are required only for HSSF
        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short)1);
        printSetup.setFitWidth((short)1);

        //Renglon del titulo encabezado.
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(40);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(tituloReporte);
        titleCell.setCellStyle(styles.get("titulo"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$E$1"));
        
        //Renglon de Encabezados: Centrado
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(12.75f);
        
        //Columnas
        String[] encabezados = (String[]) parametrosReporte.get(0);
        for (int i = 0; i < encabezados.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(encabezados[i]);
            cell.setCellStyle(styles.get("encabezado"));
        }
		// Congelar el primer Renglon
		sheet.createFreezePane(0, 2);
        
		//Para los siguientes renglones
		Row row;
		Cell cell;
		for (int contRenglon = 2; contRenglon <= parametrosReporte.size(); contRenglon++){
			Object[] renglon =  parametrosReporte.get(contRenglon-1); 
			row = sheet.createRow(contRenglon);
			if(renglon==null)
				continue;
			for (int contColumna = 0; contColumna < renglon.length; contColumna++) {
				if(renglon[contColumna]==null)
					continue;
				cell = row.createCell(contColumna);
				cell.setCellValue(renglon[contColumna].toString());
				cell.setCellStyle(styles.get("Celda"));
			}
		}
		
		for (int i = 0; i < encabezados.length; i++) {
		 sheet.setColumnWidth(i, 256*33);
		}
		sheet.setZoom(4, 4);
		try {
			baos = new ByteArrayOutputStream();
			wb.write(baos);
			LOGGER.info("Escritura Exitosa!!");
		} catch (IOException e) {
			LOGGER.info(e.getMessage(), e);
		}
    	return baos;
    }

    /**
     * Escribe el reporte en el <code>OutputStream</code> del <code>response</code>.
     * @param response
     * @param baos
     * @param fileName
     */
    public void escribirReporteExcel(HttpServletResponse response,
            ByteArrayOutputStream baos, String fileName) {
        try {
        	
        	String fileNameWihtoutSpaces = fileName.replace(' ', '_');
            response.setContentType(CONTENT_TYPE_XLS);
            response.setHeader(ENCABEZADO_CONTENT_DISPOSITION,
            		ENCABEZADO_ATTACH_FILE_NAME
                    + fileNameWihtoutSpaces + EXTENSION_XLS);
            response.setHeader(ENCABEZADO_CACHE_CONTROL, 
            		ENCABEZADO_NOCACHE);
            response.setHeader(ENCABEZADO_PRAGMA,
            		ENCABEZADO_NOCACHE);
            response.setContentLength(baos.size());
            ServletOutputStream sos = response.getOutputStream();
            baos.writeTo(sos);
            sos.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    
    
    /**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        short borderColor = IndexedColors.GREY_50_PERCENT.getIndex();
        
        //Titulo
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("titulo", style);

        //Encabezados
        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("encabezado", style);

        //Celda
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("Celda", style);

        //Celdas azul
        Font celdaFuente = wb.createFont();
        celdaFuente.setFontHeightInPoints((short)14);
        celdaFuente.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(celdaFuente);
        styles.put("CeldaAzul", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("CeldaGris", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("CeldaGrisObscuro", style);

        return styles;
    }
    
    
    /**
     * M&eacute;todo que lleva a cabo la asignaci&oacute;n de los valores correspondientes a los 
     * par&acute;metros adicionales de los indicadores a consultar, dependiendo del id del indicador
     * que se planea consultar.
     * @param indicador - El Indicador sobre el cual se va a realizar la consulta.
     * @param valores - El mapa de llave - valor que se va a reemplazar para llevar a cabo la consulta 
     * 					del indicador correspondiente. 
     */
    private void completarMapaValores(Indicadores indicador, Map<String,String> valores){
    	if (indicador.getIndicadorId() .equals(Indicadores.INDICADOR_57.getIndicadorId())){
    		valores.put(LLAVE_ESTATUS_AUDIENCIA_PROGRAMADA, EstatusAudiencia.PROGRAMADA.getValorId().toString());
    		valores.put(LLAVE_ESTATUS_AUDIENCIA_REPROGRAMADA, EstatusAudiencia.REPROGRAMADA.getValorId().toString());
    		valores.put(LLAVE_ESTATUS_AUDIENCIA_CANCELADA, EstatusAudiencia.CANCELADA.getValorId().toString());
    	}else if(indicador.getIndicadorId() .equals(Indicadores.INDICADOR_59.getIndicadorId())){
    		valores.put(LLAVE_ESTATUS_AUDIENCIA_PROGRAMADA, EstatusAudiencia.PROGRAMADA.getValorId().toString());
    		valores.put(LLAVE_ESTATUS_AUDIENCIA_FINALIZADA, EstatusAudiencia.FINALIZADA.getValorId().toString());
    	}else if (indicador.getIndicadorId() .equals(Indicadores.INDICADOR_61.getIndicadorId())){
    		valores.put(LLAVE_ROL_ID_JUEZ, Roles.JUEZPJ.getValorId().toString());
    	}
    }
}