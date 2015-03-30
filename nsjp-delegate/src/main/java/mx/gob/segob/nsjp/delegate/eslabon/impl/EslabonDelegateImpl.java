/**
 * 
 */
package mx.gob.segob.nsjp.delegate.eslabon.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.eslabon.EslabonDelegate;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.service.eslabon.ConsultarEslabonService;
import mx.gob.segob.nsjp.service.eslabon.GuardarEslabonService;
import mx.gob.segob.nsjp.service.evidencia.ConsultarDocumentosDEvidenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author adrian
 *
 */
@Service("eslabonDelegate")
public class EslabonDelegateImpl implements EslabonDelegate {
	@Autowired
	private GuardarEslabonService guardarEslabonService;
	@Autowired
	private ConsultarEslabonService consultarEslabonService;
    @Autowired
    private ConsultarDocumentosDEvidenciaService documentos;


	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.eslabon.EslabonDelegate#guardarEslabon(mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO, mx.gob.segob.nsjp.dto.evidencia.EslabonDTO)
	 */
	@Override
	public Long guardarEslabon(EvidenciaDTO evidenciaDTO, EslabonDTO eslabonDTO)
			throws NSJPNegocioException {
		return guardarEslabonService.guardarEslabon(evidenciaDTO, eslabonDTO);
	}

	@Override
	public Long asociarDocumentoAEslabon(EslabonDTO eslabonDTO)
			throws NSJPNegocioException {
		return guardarEslabonService.asociarDocumentoAEslabon(eslabonDTO);
	}

	public List<EslabonDTO> consultarEslabonesPorEvidencia(EvidenciaDTO evidenciaDTO) throws NSJPNegocioException{
		return consultarEslabonService.consultarEslabonesPorEvidencia(evidenciaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.eslabon.EslabonDelegate#tieneEslabonPorEvidenciaYObjeto(java.lang.Long)
	 */
	@Override
	public Boolean tieneEslabonPorEvidenciaYObjeto(Long idObjeto)
			throws NSJPNegocioException {
		return consultarEslabonService.tieneEslabonPorEvidenciaYObjeto(idObjeto);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.eslabon.EslabonDelegate#esEvidenciaNoTieneEslabon(java.lang.Long)
	 */
	@Override
	public Boolean esEvidenciaNoTieneEslabon(Long idObjeto)
			throws NSJPNegocioException {
		return consultarEslabonService.esEvidenciaNoTieneEslabon(idObjeto);
	}
	
	public EslabonDTO consultaUltimoEslabonXEvidenciaYTipo(EvidenciaDTO evidenciaDTO, Long tipoEslabon) throws NSJPNegocioException{
		return consultarEslabonService.consultaUltimoEslabonXEvidenciaYTipo(evidenciaDTO, tipoEslabon);
	}
	public List<DocumentoDTO> consultarDocumentosXIdEslabon(EslabonDTO eslabonDTO) throws NSJPNegocioException{
		return documentos.consultarDocumentosXIdEslabon(eslabonDTO); 
	}

	@Override
	public Long obtenIdExpedienteDeUnEslabon(Long eslabonId) throws NSJPNegocioException {
		return consultarEslabonService.obtenIdExpedienteDeUnEslabon(eslabonId);

	}
	
	public EslabonDTO consultaEslabonPorId(Long idEslabon) throws NSJPNegocioException{
		return consultarEslabonService.consultaEslabonPorId(idEslabon);

	}

}

