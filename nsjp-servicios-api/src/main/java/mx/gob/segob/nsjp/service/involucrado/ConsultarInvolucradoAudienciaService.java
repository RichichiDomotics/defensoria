/**
 * Nombre del Programa  : ConsultarInvolucradoAudienciaService.java
 * Autor                : Daniel Jiménez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 22 Jun 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Consulta los involucrados en una Audienia
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.involucrado;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.funcionario.Especialidades;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;

/**
 * Consulta los involucrados en una Audienia.<br>
 * Clase diseñada para las consultas de involucrados en una Aduciencia tanto por
 * categoria como generales.
 * 
 * @version 1.0
 * @author Daniel Jiménez
 * 
 */
public interface ConsultarInvolucradoAudienciaService {

    /**
     * Recupera a las víctimas e imputados de una audiencia
     * 
     * @param audienciaDTO
     *            Requerido <b>id</b>
     * @return
     */
    public List<InvolucradoViewDTO> obtenerImputadosVictimasAudiencia(
            AudienciaDTO audienciaDTO) throws NSJPNegocioException;
    /**
     * Recupera todos los involucrados de la audiencia.
     * 
     * @param audienciaDTO
     *            Requerido <b>id</b>
     * @return
     */
    public List<InvolucradoViewDTO> obtenerInvolucradosAudiencia(
            AudienciaDTO audienciaDTO) throws NSJPNegocioException;
    /**
     * Recupera a los imputados de una audiencia
     * 
     * @param audienciaDTO
     *            Requerido <b>id</b>
     * @return
     */
    public List<InvolucradoViewDTO> obtenerImputadosAudiencia(
            AudienciaDTO audienciaDTO) throws NSJPNegocioException;
    
    /**
     * Recupera los imputados de una audiencia que aún no sean programados a otra audiencia siguiente
     * a partir de la audiencia actual (pasada como parámetro).
     * Esto se resuelve incluir solo aquellos imputados que cumplan: su Maxima Audiencia
     * que sea exactamente la actual. 
     * 
     * @param audienciaDTO - IdAudiencia actual
     * @return
     * @throws NSJPNegocioException
     */
    List<InvolucradoViewDTO> obtenerImputadosSiguienteAudiencia(
			AudienciaDTO audienciaDTO) throws NSJPNegocioException;
    
    
    /**
     * Permite consultar los involucrados de una Audiencia por calidad
     * @param audienciaDTO
     * @param aoEspecialidades
     * @return
     * @throws NSJPNegocioException
     */
    
	public List<InvolucradoViewDTO> obtenerFuncionarioAudienciaPorTipoEspecialidad(
			AudienciaDTO audienciaDTO, List<Long> aoEspecialidades) throws NSJPNegocioException;
	
	/**
	 * Permite consultar los involucrados de una Audiencia por calidad
	 * @param audienciaDTO
	 * @param aoCalidades
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<InvolucradoViewDTO> obtenerInvolucradosAudienciaPorCalidades(
			AudienciaDTO audienciaDTO, List<Long> aoCalidades) throws NSJPNegocioException;
	/**
	 * Permite consultar los involucrados de una Audiencia por calidad
	 * @param audienciaDTO
	 * @param aoCalidades
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<InvolucradoDTO> obtenerInvolucradosDTOAudienciaPorCalidades(
			AudienciaDTO audienciaDTO, List<Long> aoCalidades)
			throws NSJPNegocioException;
	
	/**
	 * Permite obtener funcionarios asociados a una audiencia
	 * @param audienciaDTO
	 * @param aoEspecialidades
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> obtenerFuncionariosDTOAudienciaPorTipoEspecialidad(
			AudienciaDTO audienciaDTO, List<Long> aoEspecialidades)
			throws NSJPNegocioException;
    
}
