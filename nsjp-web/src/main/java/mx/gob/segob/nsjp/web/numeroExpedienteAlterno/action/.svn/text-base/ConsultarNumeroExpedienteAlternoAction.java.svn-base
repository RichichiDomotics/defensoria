/**
 * Nombre del Programa 			:ConsultarNumeroExpedienteAlternoAction.java
 * Autor                     	: AlejandroGA
 * Compania                  	: Ultrasist
 * Proyecto                  	: NSJP                    Fecha: 26/junio/2012
 * Marca de cambio        		: N/A
 * Descripcion General    		: Clase Action consultar el numero expediente alterno
 * Programa Dependiente  		: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion        	: N/A
 * Dias de ejecucion         	: N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                     	: N/A
 * Compania              		: N/A
 * Proyecto                 	: N/A                                 Fecha: N/A
 * Modificacion           		: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.numeroExpedienteAlterno.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.numeroExpedienteAlterno.NumeroExpedienteAlternoDelegate;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase action para consultar el numero de expediente alterno
 * @author AlejandroGA
 * @version 1.0
 */
@Deprecated
public class ConsultarNumeroExpedienteAlternoAction extends GenericAction{

	@Autowired
	private NumeroExpedienteAlternoDelegate numeroExpedienteAlternoDelegate;
	
	/* Log de clase */
	private static final Logger logger = Logger
			.getLogger(ConsultarNumeroExpedienteAlternoAction.class);
	
	
	
	
	/**
	 * Metodo para consultar el consecutivo del numero de expediente alterno
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public ActionForward consultarConsecutivoNumeroExpAlterno(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			logger.info("EJECUTANDO ACTION CONSULTAR CONSECUTIVO NUMERO EXPEDIENTE ALTERNO");

			logger.info("::::::::::::::::::::VERIFICANDO PARAMETROS::::::::::::::::::::::");
			
			logger.info("numeroExpedienteId====="+request.getParameter("numeroExpedienteId"));
			
			Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpedienteId"),0L);
			
			
			
			ExpedienteDTO expedienteDto = new ExpedienteDTO();
			expedienteDto.setNumeroExpedienteId(numeroExpedienteId);
			String numeroExpedienteAlterno = "";
			
			if(numeroExpedienteId > 0L){
				numeroExpedienteAlterno = numeroExpedienteAlternoDelegate.obtenerNumeroExpedienteAlterno(super.getUsuarioFirmado(request),expedienteDto);
			}

			converter.alias("respuesta", String.class);
			String xml = converter.toXML(numeroExpedienteAlterno);
			escribirRespuesta(response, xml);

		} catch (NSJPNegocioException ex) {
			logger.error("Ocurrio un error en consultarCasoPorExpediente", ex);
			escribirError(response, ex);
		}
		return null;
	}
	
	/**
	 * Metodo para consultar el consecutivo del numero de expediente alterno
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public ActionForward consultarNumeroExpedienteAlterno(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			logger.info("EJECUTANDO ACTION CONSULTAR NUMERO EXPEDIENTE ALTERNO");

			logger.info("::::::::::::::::::::VERIFICANDO PARAMETROS::::::::::::::::::::::");
			
			logger.info("numeroExpedienteId====="+request.getParameter("numeroExpedienteId"));
			
			String numeroExpedienteAlterno = "";
			
			Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpedienteId"),0L);
				
			ExpedienteDTO expedienteDto = new ExpedienteDTO();
			expedienteDto.setNumeroExpedienteId(numeroExpedienteId);
			
			if(numeroExpedienteId > 0L){
				numeroExpedienteAlterno = numeroExpedienteAlternoDelegate.consultarNumeroExpedienteAlterno(expedienteDto);
			}

			converter.alias("respuesta", String.class);
			String xml = converter.toXML(numeroExpedienteAlterno);
			escribirRespuesta(response, xml);

		} catch (NSJPNegocioException ex) {
			logger.error("Ocurrio un error en consultarCasoPorExpediente", ex);
			escribirError(response, ex);
		}
		return null;
	}
	
}
