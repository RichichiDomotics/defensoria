/**
 * Nombre del Programa : ConfInstitucionDAO.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : DAO para ConfInstitucion
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
package mx.gob.segob.nsjp.dao.institucion;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.ConfInstitucion;

/**
 * DAO para ConfInstitucion.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public interface ConfInstitucionDAO extends GenericDao<ConfInstitucion, Long> {

    /**
     * Consulta para llave - valor.
     * 
     * @return Lista de <code>ConfInstitucion</code> con los valores
     *         confInstitucionId y nombreInst
     */
    List<ConfInstitucion> consultarCatalogoSingle() throws NSJPNegocioException;


    /**
     * Consulta las intituciones dferentes a la actual.
     * 
     * @return
     * @throws NSJPNegocioException
     */
    List<ConfInstitucion> consultarDemasIntituciones()
            throws NSJPNegocioException;


    /**
     * Servicio que permite consultar la instituci�n actual
     * @return
     * @throws NSJPNegocioException
     */
	ConfInstitucion consultarIntitucionActual();
	
	/**
	 * Permite consultar el detalle de una insituci�n en base a la clave
	 * @param clave
	 * @return
	 */
	public ConfInstitucion consultarIntitucionPorClave(String clave);
	
	/**
	 * Permite consultar el detalle de una insituci�n en base al id
	 * @author encesarvarga
	 * @param confInstitucionId
	 * @return ConfInstitucion
	 */
	public ConfInstitucion consultarIntitucionPorId(Long confInstitucionId);

}
