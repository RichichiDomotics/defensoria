/**
* Nombre del Programa : EnviarSolicitudServiceImp.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 03/09/2012
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
package mx.gob.segob.nsjp.service.sentencia.impl.transform;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviardocumento.DocumentoWSDTOTransformer;
import mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudTransformer;
import mx.gob.segob.nsjp.service.usuario.UsuarioService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */

@Service("enviarSolicitudService")
@Transactional
public class EnviarSolicitudServiceImp implements EnviarSolicitudService {

    /**
     * Logger.
     */
    private final static Logger LOGGER = Logger
            .getLogger(EnviarSolicitudServiceImp.class);

	
	
	@Autowired
	private ClienteGeneralService clienteGeneralService;
	
	@Autowired
	private RegistrarSolicitudService registrarSolicitudService;

	@Autowired
	private AsignarNumeroExpedienteService asignarNumeroExpedienteService;	
	
	@Autowired
	private BuscarExpedienteService buscarExpedienteService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private GuardarDocumentoService documentoService;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudService#enviarSolicitud(mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO, mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
	 */
	@Override
	public SolicitudDTO enviarSolicitud(SolicitudDTO solicitudDTO,
			Instituciones destino,
			List<DocumentoDTO> lstDocumentoAdjuntos, SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		return clienteGeneralService.enviarSolicitud(solicitudDTO, destino, lstDocumentoAdjuntos, sentenciaDTO);
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudService#recibirSolicitud(mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO)
	 */
	@Override
	public SolicitudWSDTO recibirSolicitud(SolicitudWSDTO solicitudWSDTO) throws NSJPNegocioException {
		
		try {
			SolicitudDTO solicitudDTO = SolicitudTransformer.transformarServerWSDTO2DTO(solicitudWSDTO);

			if(solicitudWSDTO.getNumeroCausaSentencia()== null
					&& solicitudDTO.getAreaDestino() == null
					&& solicitudDTO.getDestinatario() == null) {
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
			
			// De momento, esta solo para RS, hay que  obtener el expediente de otra forma
			ExpedienteDTO expedienteDTO = buscarExpedienteService
												.obtenerExpedientePorNumeroExpediente(
														solicitudWSDTO.getNumeroCausaSentencia());
			
			expedienteDTO.setArea(new AreaDTO(solicitudDTO.getAreaDestino()));
			expedienteDTO.setTipoExpediente(new ValorDTO(TipoExpediente.CARPETA_DE_EJECUCION.getValorId()));
			FuncionarioDTO funcionarioDTO = solicitudDTO.getDestinatario();
			UsuarioDTO usuarioDTO = usuarioService.consultarUsuarioPorClaveFuncionario(funcionarioDTO.getClaveFuncionario());
			expedienteDTO.setUsuario(usuarioDTO);
			
			ExpedienteDTO nuevoExp = asignarNumeroExpedienteService.asignarNumeroExpediente(expedienteDTO);

			solicitudDTO.setExpedienteDTO(nuevoExp);
			
			solicitudDTO = registrarSolicitudService.registrarSolicitud(solicitudDTO);
			
			List<DocumentoWSDTO> lstDocumentosAdjuntos = solicitudWSDTO.getLstDocumentosAdjuntos();
			
			
			if (lstDocumentosAdjuntos!= null 
					&& !lstDocumentosAdjuntos.isEmpty()) {
            	for (DocumentoWSDTO documentoWSDTO : lstDocumentosAdjuntos) {
            		DocumentoDTO documentoDTO = DocumentoWSDTOTransformer.transformarDTO(documentoWSDTO);
            		documentoService.guardarDocumento(documentoDTO, nuevoExp, null);
				}
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		
		
		return null;
	}
	
}
