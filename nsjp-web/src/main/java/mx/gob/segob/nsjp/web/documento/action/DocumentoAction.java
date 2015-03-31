package mx.gob.segob.nsjp.web.documento.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.eslabon.EslabonDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.documento.form.AdjuntarDocumentoAsociadoAExpedienteForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rgama
 */
public class DocumentoAction extends GenericAction {

    private static final Logger logger =
            Logger.getLogger(GenericAction.class);
	@Autowired
	private DocumentoDelegate documentoDelegate;
	@Autowired
	public ExpedienteDelegate expedienteDelegate;
	@Autowired
	public EslabonDelegate eslabonDelegate;

	/**
	 * Metodo utilizado para adjuntar un documento y asociarlo a un expediente 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward adjuntarDocumentoAExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			logger.info("EJECUTANDO ADJUNTAR DOCUMENTO Y ASOCIARLO A UN EXPEDIENTE");
            Long idExpediente = 0L;
            Long idNumeroExpediente = NumberUtils.toLong(request.getParameter("idNumeroExpediente"),0);
            Long idEslabon = NumberUtils.toLong(request.getParameter("idEslabon"),0);
        


			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			
		    
            if(idEslabon > 0){
            	idExpediente = eslabonDelegate.obtenIdExpedienteDeUnEslabon(idEslabon);
				expedienteDTO.setExpedienteId(idExpediente);
            }
			
			AdjuntarDocumentoAsociadoAExpedienteForm adjuntarDocumentoAsociadoAExpedienteForm = (AdjuntarDocumentoAsociadoAExpedienteForm)form;			
			idExpediente = adjuntarDocumentoAsociadoAExpedienteForm.getExpedienteId();			
					
			if(idExpediente != null && idExpediente > 0){
				expedienteDTO.setExpedienteId(idExpediente);
			}else{
				if(idNumeroExpediente != null && idNumeroExpediente >0){
					ExpedienteDTO loExpedienteDTO = new ExpedienteDTO();
					loExpedienteDTO.setNumeroExpedienteId(idNumeroExpediente);
					idExpediente = expedienteDelegate.obtenerExpedienteIdPorNumExpId(loExpedienteDTO);
					expedienteDTO.setExpedienteId(idExpediente);
				}
			}			
				
			
			logger.info("expediente Id de la forma" + idExpediente);
			
			FormFile archivo = adjuntarDocumentoAsociadoAExpedienteForm.getArchivoAdjunto();
			String fileName    = archivo.getFileName();
			byte[] fileData    = archivo.getFileData();			        
			
			adjunto.setContenido(fileData);
			
			if(fileName != null){
				String[] extension = fileName.split("\\.");
				adjunto.setTipoArchivo("."+extension[extension.length-1]);
				adjunto.setNombreArchivo(extension[0]);
				logger.info("El nombre del archivo es: " + extension[0]);

			}

			
			adjunto.setUsuario(super.getUsuarioFirmado(request));
			
			funcionarioDTO = getUsuarioFirmado(request).getFuncionario();
			
			Actividades actividad = Actividades.ANEXAR_DOCUMENTO;
			
	
			DocumentoDTO loDocuemntoDTO = new DocumentoDTO();
			loDocuemntoDTO.setArchivoDigital(adjunto);			
       		/*Obligatorios de Documento*/
			loDocuemntoDTO.setNombreDocumento(adjunto.getNombreArchivo());
			loDocuemntoDTO.setFechaCreacion(new Date());
			//Se asigna el tipo de documento
			loDocuemntoDTO.setTipoDocumentoDTO(new ValorDTO(TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()));
			loDocuemntoDTO.setEsGuardadoParcial(false);
			
			Long idDocumento = documentoDelegate.adjuntarDocumentoAExpediente(expedienteDTO, loDocuemntoDTO, funcionarioDTO, actividad, TipoDocumento.ARCHIVO_ADJUNTADO);
			
			
			 if(idEslabon > 0){//Se asocia el documento generado al eslabon
				 documentoDelegate.asociarDocumentoAEslabon(new EslabonDTO(idEslabon), new DocumentoDTO(idDocumento));
	         }

   			logger.info("regreso"+ idDocumento);
			return mapping.findForward("success");
					
		} catch (Exception e) {		
			logger.info(e.getCause(),e);
			return mapping.findForward("fail");
		}
		
	}
	
}
