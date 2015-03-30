/**
* Nombre del Programa : ConsultarDocumentoServiceImplTest.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 2 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Prueba unitaria para el servicio de consulta de documentos
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
package mx.gob.segob.nsjp.service.test.documento.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoForma;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.NotaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Prueba unitaria para el servicio de consulta de documentos.
 * @version 1.0
 * @author cesarAgutin
 *
 */
public class ConsultarDocumentoServiceImplTest extends BaseTestServicios<ConsultarDocumentoService> {

	public void testConsultarDocumentosExpediente() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		AreaDTO areaDTO = new AreaDTO(new Long(Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal()));
		DepartamentoDTO departamentoDTO = new DepartamentoDTO();
		ExpedienteDTO expedienteDTO = new ExpedienteDTO(160L);
		
		departamentoDTO.setArea(areaDTO);
		funcionarioDTO.setDepartamento(departamentoDTO);		
		usuarioDTO.setIdUsuario(4L);
		usuarioDTO.setFuncionario(funcionarioDTO);
		
		try {
			List<DocumentoDTO> respuesta = service.consultarDocumentosExpediente(expedienteDTO, usuarioDTO);
			assertTrue("Lalista debe tener minimo un registro", respuesta.size()>0);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void testConsultarNotasPorDocumento(){
		DocumentoDTO documentoDTO = new DocumentoDTO();
		documentoDTO.setDocumentoId(8L);
		
		try {
			List<NotaDTO> lNotasDTO = service.consultarNotasPorDocumento(documentoDTO.getDocumentoId());
			assertFalse(" La lista debe de contener notas asociadas al documento:"+documentoDTO.getDocumentoId(), lNotasDTO.isEmpty() );
			
			for (NotaDTO notaDTO : lNotasDTO) {
				logger.info(" Nota ID: "+ notaDTO.getIdNota()+ 
						" nombre:" +notaDTO.getNombreNota() +
						" Fecha Creacion:" + notaDTO.getFechaCreacion() +
						" Fechar Actualizacion:" + notaDTO.getFechaActualizacion() +
						" Descripcion:" + notaDTO.getDescripcion() +
						" Documento: " + notaDTO.getDocumento()
						);
				if ( notaDTO.getDocumento()!= null)
					logger.info(" Documento id:"+notaDTO.getDocumento().getDocumentoId()+
								" Documento Nombre:" +notaDTO.getDocumento().getNombreDocumento()
					);
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Permite consultar los documentos asociados a un expediente dado un Numero de expediente
	 * @author ricardog
	 */
	//GAMASOFT
	public void testConsultarDocumentosDeExpedientePorNumExp() {
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		expedienteDTO.setNumeroExpedienteId(9L);
		try {
			List<DocumentoDTO> respuesta = service.consultarDocumentosExpediente(expedienteDTO);
			assertTrue("Lalista debe tener minimo un registro", respuesta.size()>0);
			System.out.println("El total de documentos encotrados: " + respuesta.size());
			for (DocumentoDTO documentoDTO : respuesta) {
				System.out.println("id Documento: " + documentoDTO.getDocumentoId());
				System.out.println("Tipo documento: " + documentoDTO.getTipoDocumentoDTO().getValor());
				System.out.println("Nombre documento: " + documentoDTO.getNombreDocumento());
				System.out.println("Fecha creación: " + DateUtils.formatear(documentoDTO.getFechaCreacion()));						
				if(documentoDTO.getActividadDTO() != null){					
					ActividadDTO loActividad = documentoDTO.getActividadDTO();
					System.out.println("Fecha de creacion: " + loActividad.getFechaCreacion());
					System.out.println("Nombre actividad: " + loActividad.getNombre());
					if(loActividad.getUsuario() != null){
						//System.out.println("Funcionario: " + loActividad.getUsuario());
						FuncionarioDTO loFuncionarioDTO = loActividad.getUsuario().getFuncionario();
						System.out.println("Nombre funcionario: " + loFuncionarioDTO.getNombreCompleto());											
//						System.out.println("Area actual funcionario: " + loActividad.getUsuario().getArea().getNombre());
					}
				}
				if(documentoDTO.getArchivoDigital()!= null){
					logger.info("ArchivoDigital:"+documentoDTO.getArchivoDigital());
					logger.info("ArchivoDigital:"+documentoDTO.getArchivoDigital().getArchivoDigitalId());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void testConsultarDocumentosAudienciaByTipoForma () {
		List<DocumentoDTO> respuesta = new ArrayList<DocumentoDTO>();
		
		AudienciaDTO audienciaDTO = new AudienciaDTO();
		audienciaDTO.setId(44L);
		
		try {
			respuesta = service.consultarDocumentosAudienciaByTipoForma(audienciaDTO, TipoForma.RESOLUCION);
			assertTrue("Lalista debe tener minimo un registro", respuesta.size()>0);
			logger.info("El total de documentos encotrados: " + respuesta.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	public void testConsultarDocumentosAudiencia() {
		List<DocumentoDTO> respuesta = new ArrayList<DocumentoDTO>();
		
		AudienciaDTO audienciaDTO = new AudienciaDTO();
		audienciaDTO.setId(206L);
		
		try {
			respuesta = service.consultarDocumentosAudiencia(audienciaDTO);
			logger.info("El total de documentos encontrados: " + respuesta.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void testConsultarDocumentosDeMandamientoJudicialPorExpediente(){
		
		List<DocumentoDTO> respuesta = new ArrayList<DocumentoDTO>();
		
		ExpedienteDTO expedienteDto = new ExpedienteDTO();
		expedienteDto.setNumeroExpedienteId(385L);
		
		MandamientoDTO mandamientoDto = new MandamientoDTO();
		mandamientoDto.setDocumentoId(927L);
		
		try{
			respuesta = service.consultarDocumentosDeMandamientoJudicialPorExpediente(expedienteDto,mandamientoDto, TipoDocumento.ARCHIVO_ADJUNTADO.getValorId());
			
			logger.info("El total de documentos encontrados: " + respuesta.size());
			
			for (DocumentoDTO doc : respuesta) {
				logger.info("************************************************");
				logger.info("Id Docuemnto: "+doc.getDocumentoId()+": "+doc.getNombreDocumento());
				if(doc.getActividadDTO() != null){
					logger.info(" Expediente ID: "+doc.getExpedienteDTO().getExpedienteId());
					logger.info(" Número Expediente: "+doc.getExpedienteDTO().getNumeroExpediente());
					logger.info(" Número Expediente ID: "+doc.getExpedienteDTO().getNumeroExpedienteId());
					//logger.info(" Número de Caso: "+doc.getExpediente().getCaso().getCasoId() +" / "+doc.getExpediente().getCaso().getNumeroGeneralCaso());
				}else
					logger.info("No trae expediente");
				
				String tipo=(doc.getTipoDocumentoDTO()!=null) ? doc.getTipoDocumentoDTO().getValor():"No trae";
				logger.info(" Tipo de Documento: "+ tipo);
				logger.info(" Fecha de Creacion: "+doc.getFechaCreacion());

			}
		}catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
}

