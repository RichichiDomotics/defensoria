/**
 * Nombre del Programa : DocumentoDAOImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.dao.documento.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.UsuarioRol;
import mx.gob.segob.nsjp.model.Valor;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository
public class DocumentoDAOImpl extends GenericDaoHibernateImpl<Documento, Long>
		implements DocumentoDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<Documento> consultarDocumentoPorExpediente(Long expedienteId,
			UsuarioDTO usuarioDTO) {

		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT d ")
				.append("FROM Documento d LEFT JOIN d.actividad da ")
				.append(" INNER JOIN da.funcionario daf ");


		/* linea agregada se filta por peritoAmp /*byYolo */
		if (usuarioDTO != null && usuarioDTO.getRolActivo() != null) {
			logger.info("Rol Activo Usuario :::::::: " + usuarioDTO.getRolActivo());
			logger.info("Jerarquiaaa USUARIOO :::::::::::::::::::: "+ usuarioDTO.getAreaActual().getAreaId());
			if (usuarioDTO.getRolActivo().equals("peritoAmp")
					|| usuarioDTO.getRolActivo().equals("coordPerFis")
					|| usuarioDTO.getRolActivo().equals("uavd")
					|| usuarioDTO.getRolActivo().equals("uavdAtnPsicologica")
					|| usuarioDTO.getRolActivo().equals("uavdJuridico")
					|| usuarioDTO.getRolActivo().equals("uavdTrabajoSocial")) {
				queryString.append("LEFT JOIN da.funcionario daf ")
						.append("LEFT JOIN daf.usuario dafu ")
						.append("LEFT JOIN dafu.usuarioRoles dafuu ")
						.append("LEFT JOIN dafuu.rol dafuur ");
			}
		}
		// else if (usuarioDTO.getRolACtivo().equals("coordinadorJAR")){
		// queryString.append(" INNER JOIN da.funcionario daf ");
		// }
		/* fin de lineas agregadas /*byYolo */
		queryString.append("WHERE da.expediente=").append(expedienteId);

		/* lineas agregadas /*byYolo */
		if (usuarioDTO != null && usuarioDTO.getRolActivo() != null){
			if (usuarioDTO.getRolActivo().equals("peritoAmp") || usuarioDTO.getRolActivo().equals("coordPerFis")) {
				queryString.append(" AND dafuur.rolId = ").append(Roles.PERITOAMP.getValorId()); // el rol de peritamp es 12
			} else if (usuarioDTO.getRolActivo().equalsIgnoreCase("coordinadorJAR") || usuarioDTO.getRolActivo().equalsIgnoreCase("facilitador")) {
				queryString.append(" AND daf.area.jerarquiaOrganizacionalId in (7, 11)"	);
			} else if (usuarioDTO.getRolActivo().equalsIgnoreCase("agentemp") || usuarioDTO.getRolActivo().equalsIgnoreCase("coordinadorAmp")){
				queryString.append(" AND daf.area.jerarquiaOrganizacionalId in (9, 10) " );
			}
		}
		/* fin de lineas agregadas /*byYolo */

		queryString.append(" AND da.documento is not null ");

		// enable IT se agrega filtro de documentos por roll

		if (usuarioDTO != null && usuarioDTO.getRolActivo() != null)
			if (usuarioDTO.getRolActivo().equals("uavd")
					|| usuarioDTO.getRolActivo().equals("uavdAtnPsicologica")
					|| usuarioDTO.getRolActivo().equals("uavdJuridico")
					|| usuarioDTO.getRolActivo().equals("uavdTrabajoSocial")) {
				queryString
						.append(" AND (dafuur.rolId = 10 or dafuur.rolId = 44 or dafuur.rolId = 45 or dafuur.rolId = 46)");

			}

		// .append("AND d.archivoDigital is not null");

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			queryString.append(" ORDER BY ");
			int orden = NumberUtils.toInt(pag.getCampoOrd(), 0);
			switch (orden) {
			case 2019: // Area FIXME DAJV poner la ruta del area a la que
						// hace referencia el documento
				queryString.append(" da");
				break;
			case 2020: // Fecha actividad
				queryString.append(" da.fechaCreacion");
				break;
			case 2021: // Nombre de la actividad
				queryString.append(" da.tipoActividad.valor");
				break;
			case 2022: // Tipo del documento
				queryString.append(" d.tipoDocumento.valor");
				break;
			case 2023: // Nombre del documento
				queryString.append(" d.nombreDocumento");
				break;
			case 2024: // Fecha del documento
				queryString.append(" d.fechaCreacion");
				break;
			// FIXME DAJV poner la ruta de ordenamientos
			default:
				queryString.append(" d.documentoId");
				break;
			}
			queryString.append(" " + pag.getDirOrd());
		}

		return super.ejecutarQueryPaginado(queryString, pag);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Documento> consultarDocumentoPorIds(String idSeleccionados) {

		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT d ")
				.append(" FROM Documento d LEFT JOIN d.actividad da ")
				.append(" INNER JOIN da.funcionario daf ")
				.append(" WHERE d.documentoId IN (").append(idSeleccionados)
				.append(") AND da.documento IS NOT NULL");
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();

		return super.ejecutarQueryPaginado(queryString, pag);

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosXExpedienteYTipoDocumento(
			Long numExpId, Long tipoDocumento) {
		StringBuffer queryString = new StringBuffer();

		queryString
				.append("SELECT new Documento(d.documentoId, d.nombreDocumento,e.expedienteId, ne.numeroExpediente, e.caso.casoId, e.caso.numeroGeneralCaso, d.tipoDocumento.valorId, d.tipoDocumento.valor, d.fechaCreacion, d.folioDocumento, d.descripcion)")
				.append(" FROM Documento d LEFT JOIN d.actividad da")
				.append(" LEFT JOIN da.expediente e")
				.append(" LEFT JOIN e.numeroExpedientes ne")
				.append(" WHERE 1=1");
		if (numExpId != null) {
			queryString.append(" AND ne.numeroExpedienteId=").append(numExpId);
		}
		queryString.append(" AND da.documento is not null ").append(
				" AND d.archivoDigital is not null");

		if (tipoDocumento != null && tipoDocumento > 0) {
			queryString.append(" AND d.tipoDocumento=").append(tipoDocumento);
		}

		logger.info("\n\r/***** " + queryString.toString());

		final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("d.folioDocumento");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("2")) {
				queryString.append(" order by ");
				queryString.append("d.fechaCreacion");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		return super.ejecutarQueryPaginado(queryString, pag);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosSinActividadXExpedienteYTipoDocumento(
			Long numExpId, Long tipoDocumento) {
		StringBuffer queryString = new StringBuffer();

		queryString
				.append("SELECT new Documento(m.documentoId,m.nombreDocumento,m.fechaCreacion) ")
				.append(" FROM Medida m").append(" WHERE 1=1");
		if (numExpId != null) {
			queryString.append(" AND m.numeroExpediente.numeroExpedienteId=")
					.append(numExpId);
		}
		queryString.append(" AND m.archivoDigital is not null ");

		if (tipoDocumento != null && tipoDocumento > 0) {
			queryString.append(" AND m.tipoDocumento=").append(tipoDocumento);
		}

		logger.info("\n\r/***** " + queryString.toString());

		final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("m.fechaCreacion");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		return super.ejecutarQueryPaginado(queryString, pag);

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Documento> consultarDocumentosPorUsuario(Usuario usuario,
			Long tipoDocumento) {
		if (logger.isDebugEnabled()) {
			logger.debug("ConsultarDocumentosPorUsuario = " + usuario);
		}
		Funcionario funcionario = usuario.getFuncionario();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT docs FROM Documento docs ")
				.append("WHERE docs.responsableDocumento = ")
				.append(funcionario.getClaveFuncionario())
				.append("AND docs.tipoDocumento = ").append(tipoDocumento);
		Query query = super.getSession().createQuery(sb.toString());
		List list = query.list();
		if (logger.isDebugEnabled()) {
			logger.debug("list.size() = " + list.size());
		}
		return list;
	}

	@Override
	public Documento consultarDocumentoXExpediente(Expediente expediente,
			Long tipoDocumento) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT d FROM Documento d ")
				.append("INNER JOIN d.actividad da ")
				.append("INNER JOIN da.expediente e ")
				.append("INNER JOIN e.numeroExpedientes ne ")
				.append("WHERE ne.numeroExpediente = :numeroExpediente ")
				.append("AND d.tipoDocumento = ").append(tipoDocumento);
		Query query = super.getSession().createQuery(sb.toString());
		query.setParameter("numeroExpediente", expediente.getNumeroExpediente());
		return (Documento) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosPorNumeroExpedienteId(
			Long numeroExpedienteId) {

		// StringBuilder sb = new StringBuilder();
		// sb.append("SELECT d FROM Documento d ")
		// .append("INNER JOIN d.actividad da ")
		// .append("INNER JOIN da.expediente e ")
		// .append("INNER JOIN e.numeroExpedientes ne ")
		// .append("WHERE ne.numeroExpedienteId = ? ");
		// return getHibernateTemplate().find(sb.toString(),
		// numeroExpedienteId);

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT d FROM Documento d ")
				.append("INNER JOIN d.actividad da ")
				.append("INNER JOIN da.expediente e ")
				.append("INNER JOIN e.numeroExpedientes ne ")
				.append("WHERE ne.numeroExpedienteId =")
				.append(numeroExpedienteId);

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		logger.debug("pag :: " + pag);
		return super.ejecutarQueryPaginado(sb, pag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosAudienciaByTipoForma(
			Long audienciaId, Long tipoForma) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT d FROM Documento d ")
				.append("WHERE d.resolutivo.audiencia=").append(audienciaId)
				.append(" AND d.forma.tipoForma=").append(tipoForma);
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Documento consultarDocumentoPorDocumentoIdLigero(Long documentoId) {

		StringBuffer queryString = new StringBuffer();
		queryString
				.append("SELECT new Documento(d.documentoId, d.nombreDocumento, d.forma.formaId, d.tipoDocumento.valorId, d.tipoDocumento.valor, d.fechaCreacion) ");

		queryString.append("FROM Documento d ").append("WHERE d.documentoId=")
				.append(documentoId);

		logger.info("/***** " + queryString.toString());
		Query query = super.getSession().createQuery(queryString.toString());
		List<Documento> temp = query.list();
		if (temp != null && !temp.isEmpty()) {
			return temp.get(0);
		}
		return null;
	}

	@Override
	public Documento consultarDocumentoPorId(Long documentoId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Documento d ").append("WHERE d.documentoId=")
				.append(documentoId);

		Query query = super.getSession().createQuery(queryString.toString());
		return (Documento) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosXTipoDocumento(
			Long idTipoDocumento) {
		StringBuffer queryString = new StringBuffer();

		queryString
				.append("SELECT new Documento(d.documentoId, d.tipoDocumento,")
				.append(" d.archivoDigital, d.nombreDocumento,")
				.append(" d.fechaCreacion, d.responsableDocumento,")
				.append(" d.actividad, d.esEnviado)");
		queryString.append(" FROM Documento d");
		queryString.append(" WHERE d.tipoDocumento=" + idTipoDocumento);

		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	public String obtenerUltimoFolioDocumento() throws NSJPNegocioException {
		final StringBuffer query = new StringBuffer();
		query.append("SELECT MAX(d.folioDocumento) ");
		query.append("FROM Documento d ");
		query.append("WHERE d.confInstitucion.esInstalacionActual = true  ");
		query.append("and d.folioDocumento like '%"
				+ Calendar.getInstance().get(Calendar.YEAR) + "%'");
		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());
		return (String) hbq.uniqueResult();
	}

	@Override
	public List<Documento> consultarDocumentosByExpedienteIdYForma(
			Long expedienteId, Formas tipoforma) {

		StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT new Documento(d.documentoId)")
				.append(" FROM Documento d LEFT JOIN d.actividad da")
				.append(" LEFT JOIN da.expediente e").append(" WHERE 1=1");
		if (expedienteId != null) {
			queryString.append(" AND e.expedienteId=").append(expedienteId);
		}
		if (tipoforma != null) {
			queryString.append(" AND d.forma.formaId=").append(
					tipoforma.getValorId());
		}
		queryString.append(" AND da.documento is not null ").append(
				" AND d.archivoDigital is not null");
		logger.info("queryString :: " + queryString.toString());

		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();

	}

	@Override
	@SuppressWarnings("unchecked")
	public Documento consultarDocumentoPorArchivoDigital(Long archivoDigitalId) {

		StringBuffer queryString = new StringBuffer();
		// queryString.append("SELECT d ")
		// .append("FROM Documento d LEFT JOIN d.actividad da")
		// .append(" WHERE da.documento is not null ")
		// .append("AND d.archivoDigital=").append(archivoDigitalId);

		queryString.append("SELECT d ").append("FROM Documento d")
				.append(" INNER JOIN d.actividad da ")
				.append(" INNER JOIN d.archivoDigital dd ")
				.append(" WHERE da.documento is not null ")
				.append("AND d.archivoDigital=").append(archivoDigitalId);

		Query query = super.getSession().createQuery(queryString.toString());
		return (Documento) query.uniqueResult();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Documento> consultarDocumentosReinsercionSocial(
			Funcionario funcionario, Documento documento,
			NumeroExpediente numeroExpediente) throws NSJPNegocioException {
		if (logger.isDebugEnabled()) {
			logger.debug("ConsultarDocumentosPorUsuario = " + funcionario);
		}

		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" SELECT docs FROM Documento docs ")
				.append(" INNER JOIN docs.actividad da ")
				.append(" INNER JOIN da.expediente e ")
				.append(" INNER JOIN e.numeroExpedientes ne ")
				.append(" WHERE docs.responsableDocumento = ")
				.append(" " + funcionario.getClaveFuncionario() + " ")
				.append(" AND ne.numeroExpedienteId = ")
				.append(" " + numeroExpediente.getNumeroExpedienteId() + " ");
		// .append(" AND docs.documentoId NOT IN ( ")
		// .append(" SELECT sol.documentoId FROM Solicitud sol ")
		// .appsend(" ) ");
		// valida por el rolActivo
		// if(funcionario.getUsuario() != null
		// && funcionario.getUsuario().getUsuarioRoles() != null
		// && !funcionario.getUsuario().getUsuarioRoles().isEmpty()){

		// Set<UsuarioRol> lstRoles =
		// funcionario.getUsuario().getUsuarioRoles();
		// Rol rolActivo = null;
		// for (UsuarioRol usuarioRol : lstRoles) {
		// if(usuarioRol.getEsPrincipal()) {
		// rolActivo = usuarioRol.getRol();
		// break;
		// }
		// }
		// if(rolActivo != null){
		// strQuery.append(" AND da.tipoActividad IN ( ")
		// .append(" SELECT fd.valorId FROM Rol r ")
		// .append(" LEFT JOIN r.facultadDocumentos fd WHERE r.rolId =  ")
		// .append(rolActivo.getRolId())
		// .append(" ) ");
		// }
		// }

		// .append("AND docs.tipoDocumento = ").append(documento.getTipoDocumento());

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		if (pag != null && pag.getCampoOrd() != null
				&& !pag.getCampoOrd().isEmpty()) {
			strQuery.append(" ORDER BY ");
			strQuery.append(pag.getCampoOrd());
			strQuery.append(" ").append(pag.getDirOrd());
		}

		return ejecutarQueryPaginado(strQuery, pag);
	}

	public Documento consultarDocumentoPorFolio(String folio)
			throws NSJPNegocioException {
		if (folio == null || folio.trim().isEmpty()) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT d FROM Documento d WHERE d.folioDocumento = '")
				.append(folio).append("'");

		Query query = super.getSession().createQuery(queryString.toString());
		return (Documento) query.uniqueResult();
	}

	@Override
	public Documento consultarDocumentoFiltro(Documento filtro)
			throws NSJPNegocioException {
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" SELECT d FROM Documento d ");
			if (filtro.getActividad() != null || filtro.getExpediente() != null) {
				queryString.append(" INNER JOIN d.actividad da ");
			}

			if (filtro.getExpediente() != null) {
				queryString.append(" INNER JOIN da.expediente e ");
			}

			if (filtro.getExpediente() != null
					&& filtro.getExpediente().getNumeroExpedienteId() != null) {
				queryString.append(" INNER JOIN e.numeroExpedientes ne ");
			}

			queryString.append(" WHERE 1 =1 ");

			if (filtro.getActividad() != null) {
				if (filtro.getActividad().getTipoActividad() != null) {
					queryString.append(" AND da.tipoActividad.valorId = ");
					queryString.append(filtro.getActividad().getTipoActividad()
							.getValorId());
				}
			}

			if (filtro.getExpediente() != null
					&& filtro.getExpediente().getNumeroExpedienteId() != null) {

				queryString.append(" AND  ne.numeroExpedienteId in = ");

				queryString.append(" ")
						.append(filtro.getExpediente().getNumeroExpedienteId())
						.append(" ");
			}

			Query query = super.getSession()
					.createQuery(queryString.toString());
			return (Documento) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosPorTipoActividadYNumExpedienteId(
			Long numExpId, Long tipoActividad) throws NSJPNegocioException {

		final PaginacionDTO pag = PaginacionThreadHolder.get();

		StringBuffer queryString = new StringBuffer();
		queryString
				.append(" SELECT d FROM Documento d INNER JOIN d.actividad da INNER JOIN da.expediente e INNER JOIN e.numeroExpedientes ne");
		queryString.append(" WHERE da.tipoActividad.valorId = ");
		queryString.append(tipoActividad);
		queryString.append(" AND  ne.numeroExpedienteId = ");
		queryString.append(numExpId);
		queryString
				.append(" AND d.archivoDigital.archivoDigitalId IS NOT NULL ");
		queryString.append(" ORDER BY d.fechaCreacion DESC ");

		return ejecutarQueryPaginado(queryString, pag);
	}

}
