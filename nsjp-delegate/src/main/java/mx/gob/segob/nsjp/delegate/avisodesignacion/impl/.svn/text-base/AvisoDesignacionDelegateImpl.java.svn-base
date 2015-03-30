/**
* Nombre del Programa : AvisoDetencionDelegateImpl.java
* Autor                            : Hugo Serrano
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del Delegate para los Avisos de Detencion
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
package mx.gob.segob.nsjp.delegate.avisodesignacion.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.avisodesignacion.AvisoDesignacionDelegate;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDetencionDTO;
import mx.gob.segob.nsjp.service.documento.AvisoDesignacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacion del Delegate para los Avisos de Detencion
 * @version 1.0
 * @author Tattva-IT
 *
 */

@Service
public class AvisoDesignacionDelegateImpl implements AvisoDesignacionDelegate {
	
	@Autowired
	private AvisoDesignacionService avisoDesignacionService;
	
	@Override
	public List<AvisoDesignacionDTO> obtenerAvisosDesignacionPorEstatus(EstatusNotificacion estatusnotificacion, Long discriminanteId) throws NSJPNegocioException {
		return this.avisoDesignacionService.obtenerAvisosDesignacionPorEstatus(estatusnotificacion, discriminanteId);
	}
	
	@Override
	public AvisoDesignacionDTO atenderAvisoDesignacion(AvisoDesignacionDTO avisoDesignacionDTO) throws NSJPNegocioException {
		return avisoDesignacionService.atenderAvisoDesignacion(avisoDesignacionDTO);
	}
    
}
