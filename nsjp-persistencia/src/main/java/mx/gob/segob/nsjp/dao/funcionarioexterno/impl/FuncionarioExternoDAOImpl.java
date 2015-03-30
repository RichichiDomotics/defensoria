/**
* Nombre del Programa 		: FuncionarioExternoDAOImpl.java
* Autor 					: AAAV
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 06 Jun 2012
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
package mx.gob.segob.nsjp.dao.funcionarioexterno.impl;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoDAO;
import mx.gob.segob.nsjp.model.FuncionarioExterno;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AAAV
 *
 */
@Repository
public class FuncionarioExternoDAOImpl extends GenericDaoHibernateImpl<FuncionarioExterno, Long>
		implements FuncionarioExternoDAO {
	
	@Override
	public FuncionarioExterno consultarFuncExternoPorClaveFuncExt(Long claveFuncExt, Long claveInst){
		final StringBuffer queryString = new StringBuffer();
		
		queryString.append("SELECT fe FROM FuncionarioExterno fe")
			.append(" WHERE fe.cveFuncionarioInstExt="+claveFuncExt)
			.append(" AND fe.confInstitucion.confInstitucionId="+claveInst);		

		Query query = super.getSession().createQuery(queryString.toString());
		return (FuncionarioExterno) query.uniqueResult();
	}
}
