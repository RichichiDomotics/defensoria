/**
 * Nombre del Programa : IngresarMedidasCautelaresService.java
 * Autor                            : Hugo Serrano
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 8 Jul 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato del Service para ingresar medidas cautelares
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
package mx.gob.segob.nsjp.service.medidascautelares;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaDTO;

/**
 * Contrato del Service para ingresar medidas cautelares
 * 
 * @version 1.0
 * @author Tattva-IT
 * 
 */
public interface IngresarMedidasCautelaresService {

    /**
     * 
     * @param medida
     * @return Long
     * @throws NSJPNegocioException
     */
    Long ingresarMedidaCautelar(MedidaCautelarDTO medida)
            throws NSJPNegocioException;
    /**
     * Desactiva la medida cautelar
     * @param medidaCautelar
     * @throws NSJPNegocioException
     */
    void desactivarMedidaCautelar(MedidaCautelarDTO medidaCautelar)
            throws NSJPNegocioException;
    /**
     * Realiza el envío de una medida cautelar de acuerdo a la institución
     * Se debe de contar ya con la medida en BD y su documento digital emitido
     * 
     * @param medidaId Medida a enviar
	 * @param institucionEnviar Institución a la que se va enviar.
	 * @throws NSJPNegocioException
     */
    void enviarMedidaCautelarInstitucion(Long medidaId, Instituciones institucionEnviar) throws NSJPNegocioException;

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
			Long medidaId, Instituciones institucionDestino)
			throws NSJPNegocioException;

	
	/**
	 * Adjunta un documento a una medida, sin crear actividad ni adjuntarlo al expediente
	 * @param documentoDTO
	 * @param medidaDTO
	 * @param tipoDocumento
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long adjuntarDocumentoAMedida(
			DocumentoDTO documentoDTO, MedidaDTO medidaDTO,
			TipoDocumento tipoDocumento) throws NSJPNegocioException;
	
}
