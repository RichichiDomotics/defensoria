package mx.gob.segob.nsjp.persistencia.correo.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.elemento.TipoElemento;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.model.Calidad;
import mx.gob.segob.nsjp.model.ConfCorreo;
import mx.gob.segob.nsjp.model.CorreoElectronico;
import mx.gob.segob.nsjp.persistencia.correo.CorreoDAO;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CorreoDAOImpl extends
		GenericDaoHibernateImpl<CorreoElectronico, Long> implements CorreoDAO {
	@Autowired
	private InvolucradoDAO involucradoDAO;

	@SuppressWarnings("unchecked")
	public List<CorreoElectronico> ConsultarCorreosInvolucradosPorAudienciaId(
			Long audienciaId) {

		final StringBuffer qryStr = new StringBuffer();
		final StringBuffer elementosIdTipoPersona = new StringBuffer();

		elementosIdTipoPersona.append("SELECT ia.involucrado.elementoId ")
				.append(" FROM InvolucradoAudiencia ia ")
				.append(" WHERE ia.id.audienciaId=" + audienciaId);

		qryStr.append("FROM CorreoElectronico c")
				.append(" WHERE c.persona.elementoId IN ").append("(")
				.append(elementosIdTipoPersona).append(")");

		final Query qry = super.getSession().createQuery(qryStr.toString());
		return qry.list();

	}

	@Override
	public List<CorreoElectronico> ConsultarCorreosFuncionariosPorAudienciaId(
			Long audienciaId) {

		final StringBuffer qryStr = new StringBuffer();
		final StringBuffer claveFuncionarios = new StringBuffer();

		claveFuncionarios.append("SELECT fa.funcionario.claveFuncionario ")
				.append(" FROM FuncionarioAudiencia fa ")
				.append(" WHERE fa.audiencia.audienciaId=" + audienciaId);

		qryStr.append("FROM CorreoElectronico c")
				.append(" WHERE c.funcionario.claveFuncionario IN ")
				.append("(").append(claveFuncionarios).append(")");

		final Query qry = super.getSession().createQuery(qryStr.toString());
		return qry.list();
	}

	@Override
	public List<CorreoElectronico> ConsultarCorreosFuncionariosEInvolucradosPorAudienciaId(
			Long audienciaId) {
		List<CorreoElectronico> correoElectronicos = this
				.ConsultarCorreosInvolucradosPorAudienciaId(audienciaId);
		correoElectronicos.addAll(this
				.ConsultarCorreosFuncionariosPorAudienciaId(audienciaId));
		return correoElectronicos;
	}

}
