/**
* Nombre del Programa : ConsultarIndicadorServiceImpl.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 19/06/2012
* Marca de cambio        : N/A
* Descripcion General    : Implementacion de la interface del servicio para obtener los indicadores
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
package mx.gob.segob.nsjp.service.indicador.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.indicador.Indicadores;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.indicador.IndicadorDTO;
import mx.gob.segob.nsjp.service.configuracion.ObtenerConfiguracionService;
import mx.gob.segob.nsjp.service.indicador.ConsultarIndicadorService;
import mx.gob.segob.nsjp.service.indicador.impl.transform.IndicadorTransformer;

/**
 * Implementacion de los servicios para obtener los indicadores
 * 
 * @author GustavoBP
 */
@Service
@Transactional
public class ConsultarIndicadorServiceImpl implements ConsultarIndicadorService {

	private final static Logger LOGGER = Logger.getLogger(ConsultarIndicadorServiceImpl.class);
	
    @Autowired
    private ExpedienteDAO expedienteDAO;
    @Autowired
    private ObtenerConfiguracionService obtenerConfiguracionService;
	
	@Override
	public List<Object[]> consultarIndicador(Indicadores indicador,
			HashMap<String, String> valores)
			throws NSJPNegocioException {

		if(indicador==null || valores==null || valores.isEmpty() ){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES); 
		}
		LOGGER.info("--Servicio: ConsultarIndicador:");
		LOGGER.info("-Indicador:"+indicador);
		LOGGER.info("-Ivalores:"+ valores);
		
		//Ingresar el nombre de las columnas
		List<Object[]> lista = new ArrayList<Object[]>();
		String[] nombreColumnas = (String[]) indicador.getNombreColumnas().toArray();
		lista.add( nombreColumnas );
		
		lista.addAll( expedienteDAO.consultarIndicador(indicador, valores));
		
		return lista;
	}

	public List<IndicadorDTO> consultarIndicadorPorInstitucionActual()throws NSJPNegocioException{
		
		LOGGER.info("--Servicio: consultarIndicadorPorInstitucionActual:");
		ConfInstitucionDTO  intitucionActualDTO = obtenerConfiguracionService.consultarInstitucionActual();
		
		Instituciones institucionActual = Instituciones.getByValor(intitucionActualDTO.getConfInstitucionId());
		
		List<IndicadorDTO> listaIndicadoresInsDTO = new ArrayList<IndicadorDTO>();
		for (Indicadores indicador : Indicadores.values()) {
			if(indicador.getInstitucion().equals(institucionActual)){
				listaIndicadoresInsDTO.add(IndicadorTransformer.transformarIndicadorDTO(indicador));
			}
		}
		return listaIndicadoresInsDTO;
	}
}
