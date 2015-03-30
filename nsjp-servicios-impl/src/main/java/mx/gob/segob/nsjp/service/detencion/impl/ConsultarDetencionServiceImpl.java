/**
 * Nombre del Programa : ConsultarDetencionServiceImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 05-jul-2011
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
package mx.gob.segob.nsjp.service.detencion.impl;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.domicilio.DomicilioDAO;
import mx.gob.segob.nsjp.dao.involucrado.DetencionDAO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.model.Detencion;
import mx.gob.segob.nsjp.model.Domicilio;
import mx.gob.segob.nsjp.service.detencion.ConsultarDetencionService;
import mx.gob.segob.nsjp.service.domicilio.impl.transform.DomicilioTransformer;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.DetencionTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
@Repository
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ConsultarDetencionServiceImpl implements ConsultarDetencionService {

    /**
      * Logger de la clase.
      */
    private final static Logger logger = Logger
            .getLogger(ConsultarDetencionServiceImpl.class);

    @Autowired
    private DetencionDAO detencionDao;
    
    @Autowired
    private DomicilioDAO domicilioDao; 

    /**
     * {@inheritDoc}
     */
    @Override
    public DetencionDTO consultarDetencion(Long idInvolucrado, String expediente)
            throws NSJPNegocioException {
        if(idInvolucrado == null){
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        Detencion detencion = detencionDao.consultarDetencion(idInvolucrado, expediente);
        return detencion != null? DetencionTransformer.transformarDetencion(detencion): null;
    }

	@Override
	public DomicilioDTO consultarDomicilioDetencion(Long elementoId)
			throws NSJPNegocioException {
		
		logger.info("SERVICIO PARA CONSULTAR DOMCILIO DE DETENCION");
		
		if(elementoId == null){
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
		
		Domicilio domicilioBd = domicilioDao.read(elementoId);
		DomicilioDTO domicilioDto = DomicilioTransformer.transformarDomicilio(domicilioBd);
		
		
		return domicilioDto;
	}

}
