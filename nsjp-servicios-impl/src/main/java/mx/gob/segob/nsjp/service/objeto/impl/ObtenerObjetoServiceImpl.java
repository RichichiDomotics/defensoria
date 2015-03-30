/**
 * Nombre del Programa : ObtenerObjetoServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 Jul 2011
 * Marca de cambio        : N/A
 * Descripcion General    : mplmentación de servicio que obtiene un objeto
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
package mx.gob.segob.nsjp.service.objeto.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.objeto.ObjetoDAO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.model.Objeto;
import mx.gob.segob.nsjp.service.objeto.ObtenerObjetoService;
import mx.gob.segob.nsjp.service.objeto.impl.transform.ObjetoTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implmentación de servicio que obtiene un objeto.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ObtenerObjetoServiceImpl implements ObtenerObjetoService {
    private final static Logger logger = Logger
            .getLogger(ObtenerObjetoServiceImpl.class);
    @Autowired
    private ObjetoDAO objetoDAO;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.segob.nsjp.service.objeto.ObtenerObjetoService#objetoService(mx
     * .gob.segob.nsjp.dto.objeto.ObjetoDTO)
     */
    @Override
    public ObjetoDTO obtenerObjeto(ObjetoDTO input) throws NSJPNegocioException {
        if (input == null || input.getElementoId() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        if (logger.isInfoEnabled()) {
            logger.info("Inicia - obtenerObjeto(...)");
        }
        final Objeto pojo = this.objetoDAO.read(input.getElementoId());

        final ObjetoDTO resp = ObjetoTransformer.transformarObjeto(pojo);

        if (logger.isDebugEnabled()) {
            logger.debug("Tipo Objeto :: " + resp.getTipoObjeto()
                    + " con descripción :: " + resp.getDescripcion());
        }
        return resp;
    }
    
    @Override
    public List<ObjetoDTO> consultarObjetosXIdExpediente(Long idExpediente) throws NSJPNegocioException {
        if (idExpediente == null || idExpediente == 0) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        if (logger.isInfoEnabled()) {
            logger.info("Inicia - consultarObjetosXIdExpediente(...)");
        }
        List<Objeto> listaObj 			= new ArrayList<Objeto>();
        listaObj						= objetoDAO.consultarObjetosByExpediente(idExpediente);
        List<ObjetoDTO> listaObjetos 	= new ArrayList<ObjetoDTO>();
        for(Objeto objeto : listaObj ){
        	ObjetoDTO nvoObjeto	= new ObjetoDTO();
        	nvoObjeto	= ObjetoTransformer.transformarObjeto(objeto);
        	listaObjetos.add(nvoObjeto);
        }
        return listaObjetos;        
    }

}
