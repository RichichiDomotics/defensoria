/**
 * Nombre del Programa : ClienteGeneralService.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 25 Jul 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Cliente para conectarse a los web services de cualquier instituci?n
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
package mx.gob.segob.nsjp.service.infra;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.MedidaCautelar;

/**
 * Cliente para conectarse a los web services de cualquier instituci&oacute;n.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public interface ClienteGeneralService {
    /**
     * Se conecta a la intituci&oacute;n indicada por <code>target</code> para generar
     * un acuse de recibo a la solicitud con el id indicado en
     * <code>idSolicitud</code>.
     * 
     * @param idSolicitud
     * @param target
     *            Instituci&oacute;n a la que se debe conectar
     * @throws NSJPNegocioException
     */
    public void enviarAcuseRecibo(Long idSolicitud, Instituciones target)
            throws NSJPNegocioException;

    /**
     * Se conecta a la intituci&oacute;n indicada por <code>target</code> para generar
     * un acuse de recibo a la solicitud con el id indicado en
     * <code>idSolicitud</code>.
     * 
     * @param idSolicitud
     * @param target
     *            Instituci&oacute;n a la que se debe conectar
     * @throws NSJPNegocioException
     */
    public void enviarReplicaCaso(ExpedienteDTO expConCaso, ConfInstitucion target)
            throws NSJPNegocioException;
    
    Boolean enviarNotificacionAudienciaFuncionarioExtenor(Audiencia audiencia, String nombreFuncionario, Instituciones target,
            String folioNotificacion, String consecutivoNotificacion) throws NSJPNegocioException;

    /**
     * Se conecta a la intituci&oacute;n indicada por <code>target</code> para consultar
     * Funcionarios por id del catDiscriminante
     * @param catDiscriminanteId
     * @param target
     * @return
     * @throws NSJPNegocioException
     */
    public List<FuncionarioDTO> consultarFuncionariosXTribunal(
      		 Long catDiscriminanteId,Instituciones target) throws NSJPNegocioException;
    /**
     * Cliente que se conecta a la instituci&oacute;n indicada por <code>target</code> para consultar 
     * los tribunales por el <code>distritoId</code> 
     * @param distritoId
     * @param target
     * @return
     * @throws NSJPNegocioException
     */
    List<CatDiscriminanteDTO> consultarTribunalesPorDistrito( Long distritoId, Instituciones target) throws NSJPNegocioException ;
    
    /**
	 * Cliente que permite enviar un Documento a cualquier otra instituci&oacute;n mediante un WebService. 
	 * @param lstDocumentoDTO lista con los documentos a env&iacute;ar
	 * @param numeroDeCaso
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long enviarDocumentoAInstitucion(
			List<DocumentoDTO> lstDocumentoDTO, String numeroDeCaso, Instituciones target) throws NSJPNegocioException;
    
	/**
     * Cliente que se conecta a la instituci&oacute;n indicada por <code>target</code> para consultar 
     * las agencias por el <code>distritoId</code> 
     * @param distritoId
     * @param target
     * @return
     * @throws NSJPNegocioException
     */
    List<CatDiscriminanteDTO> consultarAgenciasPorDistrito( Long distritoId, Instituciones target) throws NSJPNegocioException;
    
    /**
	 * M&eacute;todo utilizado para llevar a cabo la consulta de los funcionarios pertenencientes 
	 * a una instituci&oacute;n en espec&iacute;fico en base a un criterio establecido.
	 * @param criterioConsultaFuncionarioExternoDTO - El criterio sobre el cual se va a llevar a 
	 * 												  cabo la consulta.
	 * @param target - La institución sobre la cual se va a invocar el servicio.
	 * @return List<FuncionarioDTO> - Lista de funcionarios que cumplen con el criterio 
	 * 		   						  de b&uacute;squeda. 
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> consultarFuncionariosXCriterio(CriterioConsultaFuncionarioExternoDTO 
			criterioConsultaFuncionarioExternoDTO, Instituciones target) throws NSJPNegocioException;
    
    
    /**
	 * Cliente que permite enviar una Sentencia a la instituci&oacute;n indicada por <code>intitucion</code>. 
	 * @param sentenciaDTO
	 * @param Institucion destino
	 * @return True o False si se ha enviado la información
	 * @throws NSJPNegocioException
	 */    
    Boolean enviarSentencia(
			SentenciaDTO sentenciaDTO, Instituciones institucion)throws NSJPNegocioException;    

    /**
     * Cliente que permite consultar Distritos mediante un Web Services
     * @param target
     * @return
     * @throws NSJPNegocioException
     */
    public List<CatDistritoDTO> consultarDistritos(Instituciones target) throws NSJPNegocioException;


	/**
	 * Enviar la Medida Cautelar de acuerdo a la institución
	 * 
	 * @param input
	 *            Medida a enviar
	 * @param institucionEnviar
	 *            Institución a la que se va enviar.
	 * @throws NSJPNegocioException
	 */
	public void enviarMedidaCautelarInstitucion(MedidaCautelar medidaCautelar,
			Instituciones institucionEnviar) throws NSJPNegocioException;

	/**
	 * Cliente para enviar una Medida Cautelar a una institución en particular,
	 * el servicio lo que hace es actualizar el estatus de dicha medida.
	 * 
	 * @param medidaCautelar
	 * @param institucionDestino
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Boolean actualizarEstatusMedidaCautelarInstitucion(
			MedidaCautelar medidaCautelar, Instituciones institucionDestino)
			throws NSJPNegocioException;

	
	
	/**
	 * Cliente para enviar actualizar el estatus de dicho Mandamiento.
	 * 
	 * @param mandamientoJudicial
	 * @param institucionDestino
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Boolean actualizarEstatusMandamientoInstitucion(
			Mandamiento mandamientoJudicial, Instituciones institucionDestino)
			throws NSJPNegocioException;
	
	
    /**
     * Cliente que permite enviar una Sentencia a la instituci&oacute;n indicada por <code>destino</code>.
     * @param solicitudDTO
     * @param destino
     * @param lstDocumentoAdjuntos
     * @param sentenciaDTO
     * @return
     * @throws NSJPNegocioException
     */
	
	SolicitudDTO enviarSolicitud(SolicitudDTO solicitudDTO,
			Instituciones destino, List<DocumentoDTO> lstDocumentoAdjuntos,
			SentenciaDTO sentenciaDTO) throws NSJPNegocioException;    
	
		

}
