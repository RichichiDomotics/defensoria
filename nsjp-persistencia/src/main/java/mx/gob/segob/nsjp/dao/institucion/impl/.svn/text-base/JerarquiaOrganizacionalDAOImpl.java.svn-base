/**
 * Nombre del Programa : JerarquiaOrganizacionalDAOImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 22 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementación del DAO para JerarquiaOrganizacional
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
package mx.gob.segob.nsjp.dao.institucion.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.TipoJerarquia;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del DAO para JerarquiaOrganizacional.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository
public class JerarquiaOrganizacionalDAOImpl
        extends
            GenericDaoHibernateImpl<JerarquiaOrganizacional, Long>
        implements
            JerarquiaOrganizacionalDAO {

    @Override
    public List<JerarquiaOrganizacional> consultarCatalogoSencilloInstituciones() {
        return this.consultarNodosByTipo(TipoJerarquia.INSTITUCION);
    }

    @Override
    public List<JerarquiaOrganizacional> consultarCatalogoSencilloAreas() {
        return this.consultarNodosByTipo(TipoJerarquia.AREA);
    }

    @Override
    public List<JerarquiaOrganizacional> consultarCatalogoSencilloDepartamentos() {
        return this.consultarNodosByTipo(TipoJerarquia.DEPARTAMENTO);
    }

    @Override
    public List<JerarquiaOrganizacional> consultarAreasByPadre(
            Long idJerOrgPadre) {
        return this.consultarNodosByTipoAndPadre(TipoJerarquia.AREA,
                idJerOrgPadre);
    }

    @Override
    public List<JerarquiaOrganizacional> consultarDepartamentosByPadre(
            Long idJerOrgPadre) {
        return this.consultarNodosByTipoAndPadre(TipoJerarquia.DEPARTAMENTO,
                idJerOrgPadre);
    }
    
    @Override
    public List<JerarquiaOrganizacional> consultarDepartamentosAgenciaDelMinisterioPublico(
            Long idJerOrgPadre){
    	return this.consultarDepartamentosAgenciaTradicional(idJerOrgPadre);
    }

    private List<JerarquiaOrganizacional> consultarNodosByTipo(
            TipoJerarquia tipo) {

        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new JerarquiaOrganizacional(v.jerarquiaOrganizacionalId, v.nombre)");
        queryStr.append(" from JerarquiaOrganizacional v ");
        queryStr.append(" where v.tipoJerarquia.valorId = ");
        queryStr.append(tipo.getValorId());
        queryStr.append(" order by v.nombre");
        @SuppressWarnings("unchecked")
        List<JerarquiaOrganizacional> resp = super.getHibernateTemplate().find(
                queryStr.toString());
        if (logger.isDebugEnabled()) {

            logger.debug("resp.size() :: " + resp.size());
        }
        return resp;
    }

    private List<JerarquiaOrganizacional> consultarDepartamentosAgenciaTradicional(Long idJerOrgPadre) {

        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new JerarquiaOrganizacional(v.jerarquiaOrganizacionalId, v.nombre)");
        queryStr.append(" from JerarquiaOrganizacional v ");
        queryStr.append(" where v.jerarquiaOrganizacionalId = ");
        queryStr.append(idJerOrgPadre);
        queryStr.append(" order by v.nombre");
        @SuppressWarnings("unchecked")
        List<JerarquiaOrganizacional> resp = super.getHibernateTemplate().find(
                queryStr.toString());
        if (logger.isDebugEnabled()) {

            logger.debug("resp.size() :: " + resp.size());
        }
        return resp;
    }
    
    private List<JerarquiaOrganizacional> consultarNodosByTipoAndPadre(
            TipoJerarquia tipo, Long idJerOrgPadre) {

        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new JerarquiaOrganizacional(v.jerarquiaOrganizacionalId, v.nombre)");
        queryStr.append(" from JerarquiaOrganizacional v ");
        queryStr.append(" where v.jerarquiaOrgResponsable = ");
        queryStr.append(idJerOrgPadre);
        if(tipo != null) {
        	queryStr.append(" and v.tipoJerarquia.valorId = ");
        	queryStr.append(tipo.getValorId());
    	}
        queryStr.append(" order by v.nombre");
        @SuppressWarnings("unchecked")
        List<JerarquiaOrganizacional> resp = super.getHibernateTemplate().find(
                queryStr.toString());
        if (logger.isDebugEnabled()) {

            logger.debug("resp.size() :: " + resp.size());
        }
        return resp;
    }

    @Override
    public List<JerarquiaOrganizacional> consultarCatalogoSencilloNoInstituciones() {

        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new JerarquiaOrganizacional(v.jerarquiaOrganizacionalId, v.nombre)");
        queryStr.append(" from JerarquiaOrganizacional v ");
        queryStr.append(" where ");
        queryStr.append(" v.jerarquiaOrgResponsable is not null");
        queryStr.append(" order by v.nombre");
        return super.ejecutarQueryPaginado(queryStr,  PaginacionThreadHolder.get());
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<JerarquiaOrganizacional> consultarJerarquiaOrganizacionalDependienteExcepto(
			Long jerarquiaResponsableId, List<Long> idsJerarquiaOrgADescartar) {
		String cadenaJOADescartar= "";
		
		if(idsJerarquiaOrgADescartar!= null && !idsJerarquiaOrgADescartar.isEmpty())
			cadenaJOADescartar = idsJerarquiaOrgADescartar.toString().substring(1, idsJerarquiaOrgADescartar.toString().length()-1);
		
		logger.info("cadenaJOADescartar:"+cadenaJOADescartar);
		
		final StringBuffer query = new StringBuffer();
		query.append("select new JerarquiaOrganizacional(JOTP.jerarquiaOrganizacionalId, JOTP.nombre)");
        query.append(" from JerarquiaOrganizacional JOTP ");
        query.append(" WHERE JOTP.jerarquiaOrgResponsable.jerarquiaOrganizacionalId = ");
        query.append(jerarquiaResponsableId);
		if( !cadenaJOADescartar.isEmpty()){
			query.append(" AND JOTP.jerarquiaOrganizacionalId NOT IN (")
				.append(cadenaJOADescartar)
				.append(" ) ");
		}
		query.append(" order by JOTP.nombre");
		
		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());

		return hbq.list();
	}
    
    /**
     * Obtienen el lista con los elementos del &aacute;rbol jerarqu&iacute;as dependientes de la ra&iacute;z enviada
     * @param raiz Jerarquía raíz
     * @return Lista lineal con el &aacute;rbol  de jerarqu&iacute;as subordinadas.
     */
    @Transactional
    public List<JerarquiaOrganizacional> getArbolJerarquiasDependientes(JerarquiaOrganizacional raiz){
    	raiz = this.read(raiz.getJerarquiaOrganizacionalId());

    	List<JerarquiaOrganizacional> lstRaiz = new ArrayList<JerarquiaOrganizacional>();
    	lstRaiz.add(raiz);
    	return getHijos(lstRaiz);
    }
    
    /*
     * Funci&oacute;n recursiva que obtiene los hijos de una jerarqu&iacute;a
     * 
     */
    @Transactional
    private List<JerarquiaOrganizacional> getHijos(List<JerarquiaOrganizacional> lstPadres){

    	List<JerarquiaOrganizacional> lstHijos = new ArrayList<JerarquiaOrganizacional>();
    	for (JerarquiaOrganizacional padre : lstPadres) {
			lstHijos.add(padre);
			
			List<JerarquiaOrganizacional> lstSubordinadas = this.consultarNodosByTipoAndPadre(null, padre.getJerarquiaOrganizacionalId());
			if (lstSubordinadas != null && !lstSubordinadas.isEmpty() ){
				lstHijos.addAll(getHijos(lstSubordinadas));
			}
    	}
    	return lstHijos;
    }
    
}
