/**
 * Nombre del Programa : ConfActividadDocumentoDAOImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 06-jul-2011
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
package mx.gob.segob.nsjp.dao.actividad.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.model.Valor;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
@Repository
public class ConfActividadDocumentoDAOImpl
    extends GenericDaoHibernateImpl<ConfActividadDocumento, Long>
    implements ConfActividadDocumentoDAO {

	@SuppressWarnings("unchecked")
    @Override
    public List<ConfActividadDocumento> consultarConfActividadDocumento(
            Long jerarquiaOrganizacionalId, NumeroExpediente numeroExpediente, Long idCategoriaActidad, Long confInstitucion) {
        // Debemos consultar el estado del expediente.
        Valor estatus = numeroExpediente.getEstatus();
        if (estatus == null) {
            throw new IllegalStateException("No es posible consulta conActivid"
                    + "adDocumento para el expediente = " + numeroExpediente.getNumeroExpediente() +
                    " Se requiere que el expediente tenga un estatus asociado");
        }
        // Buscamos los registros que tengan grupoActividad = estatus
        StringBuilder sb = new StringBuilder();
        if( confInstitucion == 2 ){
            sb.append(" SELECT conf FROM ConfActividadDocumento conf ").
            append(" WHERE  conf.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").append(jerarquiaOrganizacionalId).
    		append(" AND conf.muestraEnCombo = 1").
		    append(" ORDER BY ").
			append(" conf.tipoActividad.valor");
        }
        else{
            sb.append("SELECT conf FROM ConfActividadDocumento conf ").
            append("WHERE conf.grupoActividad = ").append(estatus.getValorId()).append(" ").
            append("AND conf.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").append(jerarquiaOrganizacionalId).
    		append(" AND conf.muestraEnCombo = 1");
		    /*if(idCategoriaActidad!= null){
		    	sb.append(" AND conf.categoriaActividad = ").append( idCategoriaActidad);
		    }*/
		    sb.append(" ORDER BY ");
			sb.append("conf.tipoActividad.valor");
        	
        }
        
        Query query = super.getSession().createQuery(sb.toString());
        return query.list();
    }
	

    @Override
    public ConfActividadDocumento consultaConfActividadDocumentoPorIdActividad(Long idTipoActividad) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT conf FROM ConfActividadDocumento conf ").
                append("WHERE conf.tipoActividad.valorId = :idTipoActividad");
        Query q = getSession().createQuery(sb.toString());
        q.setParameter("idTipoActividad", idTipoActividad);
        return (ConfActividadDocumento) q.uniqueResult();
    }
   
    @SuppressWarnings("unchecked")
	@Override
    public List<Valor> consultarEstadosDistintosPorJerarquiaOrganizacional(Long idJerarquiaOrganizacional) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT(estadoCambioExpediente)  FROM ConfActividadDocumento conf ").
                append("WHERE conf.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").
                append(idJerarquiaOrganizacional);
        Query q = getSession().createQuery(sb.toString());
        return q.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<ConfActividadDocumento> consultarActuacionesPorRol (Rol rol){
    	StringBuffer sb = new StringBuffer();
    	sb.append(" FROM ConfActividadDocumento cad ").
    	append (" WHERE cad.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").
    	append (rol.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId()).
    	append(" and cad.muestraEnCombo = "). 
    	append(true);
    	final PaginacionDTO pag = PaginacionThreadHolder.get();
    	return ejecutarQueryPaginado(sb, pag);
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<ConfActividadDocumento> consultarPorTipoActFormaAndJerarquia(
			Long idTipoActividad, Long idTipoForma, Long jeraquia) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM ConfActividadDocumento cad")
					.append(" WHERE cad.tipoActividad=").append(idTipoActividad)
					.append(" AND cad.forma=").append(idTipoForma)
					.append(" AND cad.jerarquiaOrganizacional=").append(jeraquia);
		Query query = super.getSession().createQuery(queryString.toString());		
		return query.list();
	}
}
