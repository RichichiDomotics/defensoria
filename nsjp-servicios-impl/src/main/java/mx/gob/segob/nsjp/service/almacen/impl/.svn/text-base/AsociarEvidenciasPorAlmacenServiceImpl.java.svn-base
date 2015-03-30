/**
 * Nombre del Programa : AsociarEvidenciasPorAlmacenServiceImpl.java
 * Autor                            : rgama
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 29-Feb-2012
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.almacen.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.evidencia.EvidenciaDAO;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.model.Evidencia;
import mx.gob.segob.nsjp.service.almacen.AsociarEvidenciasPorAlmacenService;
import mx.gob.segob.nsjp.service.objeto.AsociarObjetoAlmacenService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @version 1.0
 * @author rgama
 */
@Repository
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AsociarEvidenciasPorAlmacenServiceImpl implements AsociarEvidenciasPorAlmacenService {

    /**
      * Logger de la clase.
      */
    private final static Logger logger = Logger
            .getLogger(AsociarEvidenciasPorAlmacenServiceImpl.class);

    @Autowired
    private EvidenciaDAO evidenciaDao;
    @Autowired
    private AsociarObjetoAlmacenService asociarObjetoAlmacenService;
    
	@Override
	public void asociarEvidenciasPorAlmacen(List<EvidenciaDTO> evidencias,
			AlmacenDTO almacen) throws NSJPNegocioException {
		if (evidencias != null && !evidencias.isEmpty()  &&
				almacen != null && almacen.getIdentificadorAlmacen() != null && almacen.getIdentificadorAlmacen() > 0) {
				//Asocia el objeto al almacen por cada una de las evidencias
				for (EvidenciaDTO evidenciaDTO : evidencias) {
					if(evidenciaDTO.getEvidenciaId() != null && evidenciaDTO.getEvidenciaId() > 0){
						Evidencia loEvidencia = evidenciaDao.read(evidenciaDTO.getEvidenciaId());
						if(loEvidencia != null && loEvidencia.getObjeto()!= null && loEvidencia.getObjeto().getElementoId() != null)
							asociarObjetoAlmacenService.asociarObjetoAlmacen(new ObjetoDTO(loEvidencia.getObjeto().getElementoId()), almacen);
						logger.info("Se asocia la evidencia " + evidenciaDTO.getEvidenciaId() + " al almecen " + almacen.getIdentificadorAlmacen());				
					}
				}
		}else   	
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
	}

	
}
