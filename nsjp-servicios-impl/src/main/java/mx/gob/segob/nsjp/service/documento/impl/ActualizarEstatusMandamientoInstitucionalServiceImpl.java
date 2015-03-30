/**
 * Nombre del Programa : actualizarEstatusMandamientoInstitucionalService.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 7 Sep 2011
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
package mx.gob.segob.nsjp.service.documento.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.documento.ActualizarEstatusMandamientoInstitucionalService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para registrar el mandamiento enviado por PJ.
 * 
 * @version 1.0
 * @author rgama
 * 	
 */
@Service("actualizarEstatusMandamientoInstitucionalService")
@Transactional
public class ActualizarEstatusMandamientoInstitucionalServiceImpl implements
	ActualizarEstatusMandamientoInstitucionalService {
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(ActualizarEstatusMandamientoInstitucionalServiceImpl.class);
	@Autowired
	private MandamientoDAO mandamientoDao;
	@Autowired
	private DocumentoDAO documentoDAO;

	
	public Boolean actualizarEstatusMandamientoJudicial(MandamientoWSDTO mandamientoWSDTO) throws NSJPNegocioException{
		Boolean regreso = false;
		
        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA ACTUALIZAR ESTATUS DE MANDAMIENTO JUDICIAL EN LA INSTITUCIÓN INVOCADA ****/");
        }
        
        logger.info("DATOS DEL MANDAMIENTO:"+mandamientoWSDTO);
        logger.info("ConfInstitucion:"+mandamientoWSDTO.getConfInstitucionId());
        logger.info("NumeroCaso:"+mandamientoWSDTO.getNumeroCaso());
        logger.info("NumeroCausa:"+mandamientoWSDTO.getNumeroCausa());
        logger.info("Folio Documento:"+mandamientoWSDTO.getFolioDocumento());
		
        //Buscar mandamiento Judicial por Folio
        Documento documento = documentoDAO.consultarDocumentoPorFolio(mandamientoWSDTO.getFolioDocumento());
        
		Mandamiento loMandamientoBD = null;
		if (documento instanceof Mandamiento) {
			loMandamientoBD = (Mandamiento) documento;
		}
        if(loMandamientoBD!=null){
        	logger.info(" ESTATUS ANTERIOR:"+ loMandamientoBD.getEstatus().getValorId());
        	loMandamientoBD.setEstatus(new Valor( mandamientoWSDTO.getEstatus()));
        	logger.info(" NUEVO ESTATUS:"+ mandamientoWSDTO.getEstatus());
        	mandamientoDao.update(loMandamientoBD);
        	regreso = true;
        }
        
		return regreso;
	}

}
