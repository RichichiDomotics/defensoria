/**
 * Nombre del Programa : IndicadorDelegateImpl.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 19/06/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Implementacion de metodos para el acceso a los servicios de Consulta de Indicadores
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
package mx.gob.segob.nsjp.delegate.indicador.impl;

import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.indicador.Indicadores;
import mx.gob.segob.nsjp.delegate.indicador.IndicadorDelegate;
import mx.gob.segob.nsjp.dto.indicador.IndicadorDTO;
import mx.gob.segob.nsjp.service.indicador.ConsultarIndicadorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacion de metodos para el acceso a los servicios de Consulta de
 * Indicadores
 * 
 * @author GustavoBP
 */
@Service("IndicadorDelegate")
public class IndicadorDelegateImpl implements IndicadorDelegate {

	@Autowired
	private ConsultarIndicadorService consultarIndicadorService;

	@Override
	public List<Object[]> consultarIndicador(Indicadores indicador,
			HashMap<String, String> valores) throws NSJPNegocioException {

		return consultarIndicadorService.consultarIndicador(indicador, valores);
	}

	@Override
	public List<IndicadorDTO> consultarIndicadorPorInstitucionActual()
			throws NSJPNegocioException {
		
		return consultarIndicadorService.consultarIndicadorPorInstitucionActual();
	}
	

}
