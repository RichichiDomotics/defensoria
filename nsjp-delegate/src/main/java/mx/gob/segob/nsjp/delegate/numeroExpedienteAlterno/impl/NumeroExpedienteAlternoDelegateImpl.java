/**
 * Nombre del Programa 			: NumeroExpedienteAlternoDelegateImpl.java
 * Autor                     	: AlejandroGA
 * Compania                  	: Ultrasist
 * Proyecto                  	: NSJP                    Fecha: 26/junio/2012
 * Marca de cambio        		: N/A
 * Descripcion General    		: Clase delegate Implement para administrar el numero
 * 								: de expediente alterno
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
package mx.gob.segob.nsjp.delegate.numeroExpedienteAlterno.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.numeroExpedienteAlterno.NumeroExpedienteAlternoDelegate;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Clase delegate Implement para administrar el numero de expediente alterno
 * @author AlejandroGA
 * @version 1.0
 */
@Deprecated
@Service("NumeroExpedienteAlternoDelegate")
public class NumeroExpedienteAlternoDelegateImpl implements NumeroExpedienteAlternoDelegate{

	@Autowired
	private AsignarNumeroExpedienteService asignarNumeroExpedienteService; 	
	
	
	@Override
	public String obtenerNumeroExpedienteAlterno(UsuarioDTO usuario,ExpedienteDTO expediente)
			throws NSJPNegocioException {
		return asignarNumeroExpedienteService.obtenerNumeroExpedienteAlterno(usuario,expediente);
	}

	@Override
	public String consultarNumeroExpedienteAlterno(ExpedienteDTO expediente) throws NSJPNegocioException {
		return asignarNumeroExpedienteService.consultarNumeroExpedienteAlterno(expediente);
	}

}
