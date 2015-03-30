/**
* Nombre del Programa : GeneraEvidenciasConRetrasoServiceImpl.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 02/07/2012
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
package mx.gob.segob.nsjp.service.evidencia.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.evidencia.EstatusEvidencia;
import mx.gob.segob.nsjp.comun.enums.evidencia.TiposEslabon;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.eslabon.EslabonDAO;
import mx.gob.segob.nsjp.dao.evidencia.EvidenciaDAO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.model.Eslabon;
import mx.gob.segob.nsjp.model.Evidencia;
import mx.gob.segob.nsjp.service.evidencia.CambiarEstatusEvidenciaService;
import mx.gob.segob.nsjp.service.evidencia.ConsultarEvidenciasXEstatusService;
import mx.gob.segob.nsjp.service.evidencia.GeneraEvidenciasConRetrasoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class GeneraEvidenciasConRetrasoServiceImpl implements GeneraEvidenciasConRetrasoService {
	
    private final static Logger logger = Logger
    .getLogger(GeneraEvidenciasConRetrasoServiceImpl.class);
	
	@Autowired
	EvidenciaDAO evidenciaDAO;
	
	@Autowired
	EslabonDAO eslabonDAO;
	
	@Autowired
	private ConsultarEvidenciasXEstatusService consultarEvidenciasXEstatusService;
	
    @Autowired
    private CambiarEstatusEvidenciaService cambiarEstatusEvidenciaService;
	
    
	@Override
	public void generaEvidenciasConRetraso() throws NSJPNegocioException {
		 // Se consultan las evidencias con estatus de EN_PRESTAMO
		 List<EvidenciaDTO> loEvidencias = consultarEvidenciasXEstatusService.consultarEvidenciasXEstatus(EstatusEvidencia.EN_PRESTAMO.getValorId());
		 for (EvidenciaDTO evidenciaDTO : loEvidencias) {
			//Se consultan el ultimo eslabon de tipo SALIDA_A_PRESTAMO
			 logger.info(">>>>>> Id Evidencia:" + evidenciaDTO.getEvidenciaId());
			 Eslabon loEslabon = eslabonDAO.consultaUltimoEslabonXEvidenciaYTipo(new Evidencia(evidenciaDTO.getEvidenciaId()), TiposEslabon.SALIDA_A_PRESTAMO.getValorId());
			 if(loEslabon != null && loEslabon.getFechaFinPrestamo() != null){
				 //Se comparan las fechas
				 Date loFechaActual = new Date();
				 if(loEslabon.getFechaFinPrestamo().before(loFechaActual)){
					 //Se actualiza el Estatus de la evidencia a CON_RETRASO
					 evidenciaDTO.setEstatus( new ValorDTO(EstatusEvidencia.CON_RETRASO.getValorId()));
					 cambiarEstatusEvidenciaService.cambiarEstatusEvidencia(evidenciaDTO);
				 }
			 }
		}
		
	}
    
	
}
