package mx.gob.segob.nsjp.persistencia.confcorreo.Impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.enums.TipoObjetoCorreo;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.model.ConfCorreo;
import mx.gob.segob.nsjp.model.CorreoElectronico;
import mx.gob.segob.nsjp.persistencia.confcorreo.ConfCorreoDAO;

@Repository
public class ConfCorreoDAOImpl extends
		GenericDaoHibernateImpl<CorreoElectronico, Long> implements
		ConfCorreoDAO {

	@Override
	public List<ConfCorreo> obtenerConfiguracionSession() {

		final StringBuffer qryStr = new StringBuffer();

		qryStr.append("FROM ConfCorreo c").append(" WHERE tipoObjeto = ")
				.append(TipoObjetoCorreo.SESSION.getValorId());

		final Query qry = super.getSession().createQuery(qryStr.toString());
		return qry.list();
	}

	public List<ConfCorreo> obtenerConfiguracionConexion() {

		final StringBuffer qryStr = new StringBuffer();

		qryStr.append("FROM ConfCorreo c").append(" WHERE tipoObjeto = ")
				.append(TipoObjetoCorreo.CONNECT.getValorId());

		final Query qry = super.getSession().createQuery(qryStr.toString());
		return qry.list();
	}

	public ConfCorreo obtenerConfiguracionTransport() {

		final StringBuffer qryStr = new StringBuffer();

		qryStr.append("FROM ConfCorreo c").append(" WHERE tipoObjeto = ")
				.append(TipoObjetoCorreo.TRANSPORT.getValorId());

		final Query qry = super.getSession().createQuery(qryStr.toString());
		return (ConfCorreo) qry.uniqueResult();
	}

	public List<ConfCorreo> obtenerConfiguracionRecipientes() {

		final StringBuffer qryStr = new StringBuffer();

		qryStr.append("FROM ConfCorreo c").append(" WHERE tipoObjeto = ")
				.append(TipoObjetoCorreo.RECIPIENT.getValorId());

		final Query qry = super.getSession().createQuery(qryStr.toString());
		return qry.list();
	}

	@Override
	public List<ConfCorreo> obtenerConfiguracionAsuntoContenido() {
		final StringBuffer qryStr = new StringBuffer();

		qryStr.append("FROM ConfCorreo c").append(" WHERE tipoObjeto = ")
				.append(TipoObjetoCorreo.ASUNTOCONTENIDO.getValorId());

		final Query qry = super.getSession().createQuery(qryStr.toString());
		return qry.list();
	}

	@Override
	public ConfCorreo esCorreoActivo() {
		final StringBuffer qryStr = new StringBuffer();
		qryStr.append("FROM ConfCorreo c").append(" WHERE tipoObjeto = ")
				.append(TipoObjetoCorreo.ACTIVO.getValorId());

		final Query qry = super.getSession().createQuery(qryStr.toString());
		return (ConfCorreo) qry.uniqueResult();
	}

}
