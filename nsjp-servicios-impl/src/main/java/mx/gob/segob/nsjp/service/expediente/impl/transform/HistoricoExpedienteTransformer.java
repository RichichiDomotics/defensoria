/**
* Nombre del Programa : HistoricoExpedienteTransformer.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 29/06/2012
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
package mx.gob.segob.nsjp.service.expediente.impl.transform;

import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.HistoricoExpediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class HistoricoExpedienteTransformer {

	/**
	 * M&eacute;todo que trasforma un {@code HistoricoExpedienteDTO} en un {@code HistoricoExpediente}
	 * 
	 * @param historicoExpedienteDTO 
	 * @return HistoricoExpediente
	 */
	public static HistoricoExpediente transformar(HistoricoExpedienteDTO historicoExpedienteDTO){
		if (historicoExpedienteDTO != null) {
			Funcionario anterior = FuncionarioTransformer.transformarFuncionario(historicoExpedienteDTO.getFuncionarioAnterior());
			Funcionario asigna = FuncionarioTransformer.transformarFuncionario(historicoExpedienteDTO.getFuncionarioAsigna());
			Funcionario actual = FuncionarioTransformer.transformarFuncionario(historicoExpedienteDTO.getFuncionarioActual());
			NumeroExpediente numeroExpediente = ExpedienteTransformer.obtenerDeExpedienteDTO(historicoExpedienteDTO.getNumeroExpediente());

				HistoricoExpediente historicoExpediente = new HistoricoExpediente(
						historicoExpedienteDTO.getHistoricoExpedienteId(), 
						anterior, 
						asigna, 
						numeroExpediente, 
						actual, 
						historicoExpedienteDTO.getdFechaInicio(), 
						historicoExpedienteDTO.getdFechaFin()
				);
				return historicoExpediente;
		} 
		return null; 
	}

	/**
	 * M&eacute;todo que trasforma un {@code HistoricoExpediente} en un {@code HistoricoExpedienteDTO}
	 * 
	 * @param historicoExpediente 
	 * @return HistoricoExpedienteDTO
	 */
	public static HistoricoExpedienteDTO transformar(HistoricoExpediente historicoExpediente){
		if (historicoExpediente != null) {
			FuncionarioDTO anterior = FuncionarioTransformer.transformarFuncionario(historicoExpediente.getFuncionarioAnterior());
			FuncionarioDTO asigna = FuncionarioTransformer.transformarFuncionario(historicoExpediente.getFuncionarioAsigna());
			FuncionarioDTO actual = FuncionarioTransformer.transformarFuncionario(historicoExpediente.getFuncionarioActual());
			ExpedienteDTO numeroExpediente = ExpedienteTransformer.transformarExpediente(historicoExpediente.getNumeroExpediente());

				HistoricoExpedienteDTO historicoExpedienteDTO = new HistoricoExpedienteDTO(
						historicoExpediente.getHistoricoExpedienteId(), 
						anterior, 
						asigna, 
						numeroExpediente, 
						actual, 
						historicoExpediente.getDFechaInicio(), 
						historicoExpediente.getDFechaFin()
				);
				return historicoExpedienteDTO;
		} 
		return null; 
	}	
	
}
