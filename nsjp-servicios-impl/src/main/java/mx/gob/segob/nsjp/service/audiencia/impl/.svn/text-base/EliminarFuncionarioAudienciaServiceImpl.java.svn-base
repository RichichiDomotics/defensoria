/**
 * Nombre del Programa 	: EliminarFuncionarioAudienciaServiceImpl.java
 * Autor               	: AlejandroGA
 * Compania            	: Ultrasist
 * Proyecto             : NSJP                    Fecha: 6 Ago 2012
 * Marca de cambio      : N/A
 * Descripcion General  : Clase para Eliminar Funcionario Audiencia
 * Programa Dependiente :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion   :N/A
 * Dias de ejecucion    :N/A                             Horario: N/A
 *                           MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor				:N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.audiencia.impl;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioAudienciaDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.FuncionarioAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioAudienciaId;
import mx.gob.segob.nsjp.service.audiencia.EliminarFuncionarioAudienciaService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para eliminar un funcionario de una audiencia
 * 
 * @author AlejandroGA
 * @version 1.0
 */
@Service
@Transactional
public class EliminarFuncionarioAudienciaServiceImpl implements
		EliminarFuncionarioAudienciaService {

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(EliminarFuncionarioAudienciaServiceImpl.class);

	@Autowired
	private FuncionarioAudienciaDAO funAudDao;

	@Override
	public void eliminarFuncionarioAudiencia(FuncionarioDTO funcionario,
			AudienciaDTO audiencia) throws NSJPNegocioException {

		if (funcionario.getClaveFuncionario() == null
				|| funcionario.getClaveFuncionario() <= 0L
				|| audiencia.getId() == null || audiencia.getId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		FuncionarioAudiencia relacion = new FuncionarioAudiencia();
		FuncionarioAudienciaId relId = new FuncionarioAudienciaId();

		relId.setClaveFuncionario(funcionario.getClaveFuncionario());
		relId.setAudienciaId(audiencia.getId());

		relacion.setId(relId);

		this.funAudDao.delete(relacion);
		logger.info("Fin -  EliminarFuncionarioAudiencia(...)");

	}
}
