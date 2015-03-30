/**
* Nombre del Programa 		: CatFormaNotificacionDAOImpl.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 16 Aug 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 							Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 							Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dao.documento.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.CatFormaNotificacionDAO;
import mx.gob.segob.nsjp.model.CatFormaNotificacion;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
@Repository
public class CatFormaNotificacionDAOImpl extends GenericDaoHibernateImpl<CatFormaNotificacion, Long>
		implements CatFormaNotificacionDAO {

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.documento.CatFormaNotificacionDAO#consultarFormasNotificacionValidas()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CatFormaNotificacion> consultarFormasNotificacionValidas() {
		StringBuilder queryString = new StringBuilder("SELECT cfn ");
								   queryString.append("FROM CatFormaNotificacion cfn ");
								   queryString.append("WHERE cfn.valida = 1");
		
		Query query = getSession().createQuery(queryString.toString());
		return (List<CatFormaNotificacion>) query.list();
	}

}
