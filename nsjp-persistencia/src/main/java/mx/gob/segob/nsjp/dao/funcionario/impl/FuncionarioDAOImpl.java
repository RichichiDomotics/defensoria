/**
 * Nombre del Programa : FuncionarioDAOImpl.java
 * Autor                            : cesarAgutin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 25 Apr 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementacion de los metodos de acceso a datos de la entidad Funcionario
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
package mx.gob.segob.nsjp.dao.funcionario.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.enums.funcionario.TipoEspecialidad;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.model.Almacen;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.Evidencia;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.FuncionarioAudiencia;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Rol;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementacion de los metodos de acceso a datos de la entidad Funcionario.
 *
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Repository
public class FuncionarioDAOImpl extends
        GenericDaoHibernateImpl<Funcionario, Long> implements FuncionarioDAO {

    @Autowired
    private NumeroExpedienteDAO numeroExpedienteDAO;

    @Autowired
    private JerarquiaOrganizacionalDAO jerarquiaDAO;

    @SuppressWarnings("unchecked")
    @Override
	public List<Funcionario> consultarSubordinadosUAVD(){
    	StringBuffer queryString = new StringBuffer();
    	queryString.append("FROM Funcionario f where f.area.jerarquiaOrganizacionalId in(")
    				.append(Areas.COORDINACION_ATENCION_VICTIMAS.ordinal())
    				.append(",")
    				.append(Areas.ATENCION_VICTIMAS.ordinal())
    				.append(",")
    				.append(Areas.ATENCION_JURIDICA.ordinal())
    				.append(",")
    				.append(Areas.ATENCION_PSICOLOGICA.ordinal())
    				.append(")");
    	Query query = super.getSession().createQuery(queryString.toString());
    	return query.list();
    }

    /**
     * Metodo que permite consultar a una lista de funcionarios por su puesto
     * Este metodo sera usado para obtener la informacion de:
     * -Abogado defensor.
     * -Coordinador de defensores.
     * -Coordinador de fiscales.
     * -Coordinador de servicios periciales.
     * -Fiscal general.
     * -Fiscal.
     * -Juez.
     * -Magistrado.
     * @param idPuesto
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Funcionario> consultarFuncionariosPorRol(Long idPuesto) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT f ").append(" FROM Funcionario f ").append(" WHERE f.puesto.valorId = :idPuesto");
        queryString.append(" ORDER BY f.nombreFuncionario");
        Query query = super.getSession().createQuery(queryString.toString());
        query.setParameter("idPuesto", idPuesto);
        return query.list();
    }

    @SuppressWarnings("unchecked")
	public List<Funcionario> consultarFuncionariosPorRolMultiRol(Long idRol) {
        StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT F FROM Funcionario F ").append(
				" LEFT JOIN  F.usuario.usuarioRoles as UR ").append(
				" WHERE 1= 1 ")
				;
		if( idRol !=null && idRol > 0){
			queryString. append(" AND UR.rol.rolId = ").append(
					idRol);
		}
		queryString.append(" AND F.usuario.esActivo= ").append(Boolean.TRUE);
        queryString.append(" ORDER BY F.nombreFuncionario");
        Query query = super.getSession().createQuery(queryString.toString());
        return query.list();
    }

    @Override
    public Funcionario consultarFuncionarioXExpediente(NumeroExpediente numeroExpediente) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT ne ").
                append(" FROM NumeroExpediente ne ").
                append(" WHERE ne.numeroExpediente = :expediente ").
        		append(" AND ne.numeroExpedienteId = :numeroExpedienteId ");
        Query query = super.getSession().createQuery(queryString.toString());
        query.setParameter("expediente", numeroExpediente.getNumeroExpediente());
        query.setParameter("numeroExpedienteId", numeroExpediente.getNumeroExpedienteId());

        NumeroExpediente rstNumeroExpediente = null;
        rstNumeroExpediente = (NumeroExpediente) query.uniqueResult();
        Funcionario resultado = null;
        if (rstNumeroExpediente != null) {
            resultado = rstNumeroExpediente.getFuncionario();
        }
        return resultado;
    }

    /**
     * Metodo que permite consultar a un funcionario por su puesto Este metodo
     * sera usado para obtener la informacion de: - Abogado defensor -
     * Coordinador de Periciales - Coordinador de Defensores
     *
     * @param idPuesto
     * @return
     */
    public Funcionario consultarFuncionarioPorPuesto(Long idPuesto) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT f ").append(" FROM Funcionario f ").append(" WHERE f.puesto.valorId = :idPuesto");
        queryString.append(" ORDER BY f.nombreFuncionario");
        Query query = super.getSession().createQuery(queryString.toString());
        query.setParameter("idPuesto", idPuesto);

        return (Funcionario) query.uniqueResult();
    }
    /*
     * (non-Javadoc)
     *
     * @see mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO#
     * consultarFuncionariosPorEspecialidadYPuestoDisponiblesParaFechaYHoraAudiencia
     * (java.util.Date, java.lang.Integer,
     * mx.gob.segob.nsjp.comun.enums.funcionario.Especialidades,
     * mx.gob.segob.nsjp.comun.enums.funcionario.Puestos)
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<Funcionario> consultarFuncionariosPorEspecialidadYPuestoDisponiblesParaFechaYHoraAudiencia(
            Date fecha, Integer duracionEstimada,
            Boolean paridadJuez, Funcionario funcionario) throws NSJPNegocioException {
        String minInicioAudiencia = "(datepart(HOUR, ja.audiencia.fechaAudiencia ) * 60 + 	datepart(MINUTE, ja.audiencia.fechaAudiencia ))";
        String minFinAudiencia = "(datepart(HOUR, ja.audiencia.fechaAudiencia ) * 60 + datepart(MINUTE, ja.audiencia.fechaAudiencia ) + ja.audiencia.duracionEstimada)";
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int minInicioAudienciaAProgramar = cal.get(Calendar.HOUR_OF_DAY) * 60
                + cal.get(Calendar.MINUTE);
        int minFinAudienciaAProgramar = minInicioAudienciaAProgramar
                + duracionEstimada;


        StringBuffer strQuery = new StringBuffer();

        strQuery.append(" from Funcionario j where 1=1");
        if (funcionario.getDiscriminante() != null){
        	if (funcionario.getDiscriminante().getCatDiscriminanteId() != null){
        		strQuery.append(" AND j.discriminante.catDiscriminanteId = ");
        		strQuery.append(funcionario.getDiscriminante().getCatDiscriminanteId());
        	}
        }

        strQuery.append(" AND j.usuario.usuarioId in ");
        strQuery.append(" (select ur.usuario.usuarioId from UsuarioRol ur where ur.rol.rolId = "+ Roles.JUEZPJ.getValorId() + " ) and ");
        strQuery.append((paridadJuez!=null? " (j.esPar = "+ (paridadJuez?"1":"0") + " or j.esPar is null) and " :""));
        strQuery.append(" not exists ( ");
        strQuery.append(" select ja.id.claveFuncionario ");
        strQuery.append(" from FuncionarioAudiencia ja  where ");
        strQuery.append(" ja.funcionario = j ");
        strQuery.append(" and CONVERT (nvarchar, ja.audiencia.fechaAudiencia, 103) = ? ");
        strQuery.append(" and " + "( " + " ( ");
   		strQuery.append(minInicioAudiencia);
   		strQuery.append( "  < ? and ? < ");
        strQuery.append( minFinAudiencia);
        strQuery.append( "  ) ");
        strQuery.append( "  or ");
        strQuery.append( "( ");
        strQuery.append( minInicioAudiencia);
        strQuery.append( " < ? and ? < ");
        strQuery.append( minFinAudiencia);
        strQuery.append( " ) ");
        strQuery.append( " or ");
        strQuery.append( " (? < ");
        strQuery.append( minInicioAudiencia);
        strQuery.append( " and ");
        strQuery.append( minInicioAudiencia);
        strQuery.append( " < ?) or ");
        strQuery.append( " (? < ");
        strQuery.append( minFinAudiencia);
        strQuery.append( " and ");
        strQuery.append( minFinAudiencia);
        strQuery.append( " < ?) ");
        strQuery.append( " ) ");
        strQuery.append( ") order by j.cargaTrabajo asc ");

        return getHibernateTemplate().find(strQuery.toString(),
                DateUtils.formatear(fecha),
                minInicioAudienciaAProgramar,
                minInicioAudienciaAProgramar,
                minFinAudienciaAProgramar, minFinAudienciaAProgramar,
                minInicioAudienciaAProgramar,
                minFinAudienciaAProgramar,
                minInicioAudienciaAProgramar, minFinAudienciaAProgramar);
    }

    @Override
    public void deleteFuncionarioAudiencia(List<FuncionarioAudiencia> funcs) {
        getHibernateTemplate().deleteAll(funcs);
    }

    @SuppressWarnings("unchecked")
	public List<Funcionario> consultarFuncionariosPorFiltroYRol(Funcionario filtro,Long idRol) {

    	final PaginacionDTO pag = PaginacionThreadHolder.get();
    	StringBuffer queryString = new StringBuffer();

        if(filtro.getBuscarPorJerarquiasHijas()!= null && filtro.getBuscarPorJerarquiasHijas()) {
        	queryString.append(" SELECT new Funcionario( F, UR.rol ) FROM Funcionario F ");
        } else {
        	queryString.append("SELECT F FROM Funcionario F");
        }
        queryString.append(" LEFT JOIN  F.usuario.usuarioRoles as UR ");
        queryString.append(" WHERE 1 = 1 ");

		if (idRol != null && idRol > 0) {
			queryString.append(" AND UR.rol.rolId = ").append(idRol);
		}

		// Permite filtrar por el nombre
		if (filtro.getNombreFuncionario() != null) {
			queryString.append(" AND lower(F.nombreFuncionario) like \'%")
					.append(filtro.getNombreFuncionario().toLowerCase()).append("%\'");
		}

		// Permite filtrar por el apellido paterno
		if (filtro.getApellidoPaternoFuncionario() != null) {
			queryString
					.append(" AND lower(F.apellidoPaternoFuncionario) like \'%")
					.append(filtro.getApellidoPaternoFuncionario().toLowerCase())
					.append("%\'");
		}

		// Permite filtrar por el apellido materno
		if (filtro.getApellidoMaternoFuncionario() != null) {
			queryString
					.append(" AND lower(F.apellidoMaternoFuncionario) like \'%")
					.append(filtro.getApellidoMaternoFuncionario().toLowerCase())
					.append("%\'");
		}

		// Permite filtrar por la Especialidad
		if (filtro.getEspecialidad() != null
				&& filtro.getEspecialidad().getValorId() != null) {
			queryString.append(" AND F.especialidad.valorId = ").append(
					filtro.getEspecialidad().getValorId());
		}

		// Permite filtrar por el Tipo de Especialidad
		if (filtro.getTipoEspecialidad() != null
				&& filtro.getTipoEspecialidad().getValorId() != null) {
			queryString.append(" AND F.tipoEspecialidad.valorId = ").append(
					filtro.getTipoEspecialidad().getValorId());
		}

		// Permite filtrar por Puesto
		if (filtro.getPuesto() != null) {
			queryString.append(" AND F.puesto.valorId = ").append(
					filtro.getPuesto().getValorId());
		}

		//Permite filtrar por la UIE
        if(filtro.getUnidadIEspecializada()!=null){
			queryString.append(" AND F.unidadIEspecializada = "
					+ filtro.getUnidadIEspecializada().getCatUIEId());
        }

        //Permite filtrar por discriminante
        if(filtro.getDiscriminante()!=null && filtro.getDiscriminante().getCatDiscriminanteId()!=null){
			queryString.append(" AND F.discriminante.catDiscriminanteId = "
					+ filtro.getDiscriminante().getCatDiscriminanteId());
        }

        queryString.append(" ORDER BY F.nombreFuncionario ASC ");

        return super.ejecutarQueryPaginado(queryString, pag);
    }

    @SuppressWarnings("unchecked")
	public List<Funcionario> consultarFuncionarioXFiltro(Funcionario filtro,Long esCoorAMPGeneral) {
        StringBuffer queryString = new StringBuffer();
        // Para no discrimiar el rol se hacen joins
        if(filtro.getBuscarPorJerarquiasHijas()!= null && filtro.getBuscarPorJerarquiasHijas()) {
        	queryString.append(" SELECT new Funcionario( p, r ) FROM Funcionario p ");
        	queryString.append(" LEFT JOIN  p.usuario u ");
        	queryString.append(" LEFT JOIN u.usuarioRoles ur ");
        	queryString.append(" LEFT JOIN ur.rol r ");
        } else {
        	queryString.append(" SELECT p FROM Funcionario p ");
        }

        queryString.append("WHERE 1=1");

        // Permite filtrar por el nombre
        if (filtro.getNombreFuncionario() != null) {
            queryString.append(" AND lower(p.nombreFuncionario) like \'%").append(filtro.getNombreFuncionario()).append("%\'");
        }

        // Permite filtrar por el apellido paterno
        if (filtro.getApellidoPaternoFuncionario() != null) {
            queryString.append(" AND lower(p.apellidoPaternoFuncionario) like \'%").append(filtro.getApellidoPaternoFuncionario()).append("%\'");
        }

        // Permite filtrar por el apellido materno
        if (filtro.getApellidoMaternoFuncionario() != null) {
            queryString.append(" AND lower(p.apellidoMaternoFuncionario) like \'%").append(filtro.getApellidoMaternoFuncionario()).append("%\'");
        }

        // Permite filtrar por la Especialidad
        if (filtro.getEspecialidad() != null
                && filtro.getEspecialidad().getValorId() != null) {
            queryString.append(" AND p.especialidad.valorId = ").append(
                    filtro.getEspecialidad().getValorId());
        }

        // Permite filtrar por el Tipo de Especialidad
        if (filtro.getTipoEspecialidad() != null
                && filtro.getTipoEspecialidad().getValorId() != null) {
            queryString.append(" AND p.tipoEspecialidad.valorId = ").append(
                    filtro.getTipoEspecialidad().getValorId());
        }

        // Permite filtrar por Puesto
        if(filtro.getPuesto() != null
        		&& filtro.getPuesto().getValorId() != null){
        	queryString.append(" AND p.puesto.valorId = ").append(
                    filtro.getPuesto().getValorId());
        }

        // Permite filtrar por Número de empleado
        if(filtro.getNumeroEmpleado()!=null)
        {
        	queryString.append(" AND p.numeroEmpleado = '").append(
                    filtro.getNumeroEmpleado()).append("'");
        }

     // Permite filtrar por Identificador de personal operativo
        if(filtro.getClaveFuncionario()!=null)
        	queryString.append(" AND p.claveFuncionario ="+filtro.getClaveFuncionario());
     // Permite filtrar por CURP
        if(filtro.getCurp()!=null)
        	queryString.append(" AND lower(p.curp) like \'%").append(filtro.getCurp()).append("%\'");
     // Permite filtrar por RFC (con homoclave)
        if(filtro.getRfc()!=null)
        	queryString.append(" AND lower(p.rfc) like \'%").append(filtro.getRfc()).append("%\'");
     // Permite filtrar por Correo electrónico (email))
        if(filtro.getEmail()!=null)
        	queryString.append(" AND lower(p.email) like \'%").append(filtro.getEmail()).append("%\'");
     // Permite filtrar por Cédula profesional
        if(filtro.getCedula()!=null)
        	queryString.append(" AND lower(p.cedula) like \'%").append(filtro.getCedula()).append("%\'");
     // Permite filtrar por Área
        if(filtro.getArea() != null){
        	if(filtro.getInstitucion().getJerarquiaOrganizacionalId()!=null){
        		if(filtro.getBuscarPorJerarquiasHijas()!= null && filtro.getBuscarPorJerarquiasHijas()) {
//        			queryString.append(" AND ( u.esActivo = 1) ");
	        		queryString.append(" AND ( r.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
	        		queryString.append(filtro.getArea().getJerarquiaOrganizacionalId());
        			queryString.append(" OR r.jerarquiaOrganizacional.jerarquiaOrganizacionalId IN ( " );
        			queryString.append(" select v.jerarquiaOrganizacionalId ");
        			queryString.append(" from JerarquiaOrganizacional v ");
        			queryString.append(" where v.jerarquiaOrgResponsable = ");
        			queryString.append(filtro.getArea().getJerarquiaOrganizacionalId());
        			queryString.append(" ) ) ");
        		} else {
	        		queryString.append(" AND p.claveFuncionario in( ");
	        		queryString.append(" SELECT funcionario.claveFuncionario FROM Usuario WHERE usuarioId in(");
	        		queryString.append(" SELECT id.usuarioId FROM UsuarioRol WHERE id.rolId in(");
	        		queryString.append(" SELECT rolId FROM Rol WHERE ( jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
	        		queryString.append(filtro.getArea().getJerarquiaOrganizacionalId());
	        		if(filtro.getRol() != null && filtro.getRol().getRolId()>0L){
	        			queryString.append(" AND rolId = ")
	        					   .append(filtro.getRol().getRolId());
	        		}
	        		queryString.append(" ) ) ) ) ");
        		}
        		//Codigo anterior
//        		queryString.append(" AND p.area.jerarquiaOrganizacionalId = ").append(filtro.getArea().getJerarquiaOrganizacionalId());
        	}
        }
        //Permite filtrar por la UIE
        if(filtro.getUnidadIEspecializada()!=null) {
        	queryString.append(" AND p.unidadIEspecializada = "+filtro.getUnidadIEspecializada().getCatUIEId());
        }

        // Permite filtrar por Discriminante
        if(filtro.getDiscriminante() != null
        		&& (esCoorAMPGeneral == null || !esCoorAMPGeneral.equals(1L))){
        	CatDiscriminante catDiscriminante = filtro.getDiscriminante();
        	if(catDiscriminante.getCatDiscriminanteId() != null && !catDiscriminante.getCatDiscriminanteId().equals(0L)){
        		queryString.append(" AND p.discriminante.catDiscriminanteId = " + catDiscriminante.getCatDiscriminanteId() + " ");
        	}
        }

        queryString.append(" ORDER BY p.nombreFuncionario");

        final PaginacionDTO pag = PaginacionThreadHolder.get();
        return ejecutarQueryPaginado(queryString, pag);
    }

    /**
     * Metodo que permite consultar los defensores de acuerdo a un tipo de Defensa
     * @param idTipoDefensa Recibe el tipo de defensa que se va a consultar.
     * @return Devuelve un listado de defensores que ejercen ese tipo de Defensa.
     * @author ricardo
     */
    //NOTA: Tipo defensa -> tipoEspecialidad y Especialidad(es)
    @SuppressWarnings("unchecked")
	public List<Funcionario> consultarDefensorPorTipoDefensa(Long idTipoDefensa) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT f FROM Funcionario f ").append(" WHERE f.puesto.valorId = ").append(Puestos.ABOGADO_DEFENSOR.getValorId()).append(" AND f.tipoEspecialidad.valorId = :idTipoDefensa");
        queryString.append(" ORDER BY f.nombreFuncionario");
        Query query = super.getSession().createQuery(queryString.toString());
        query.setParameter("idTipoDefensa", idTipoDefensa);

        return query.list();
    }

    /**
     * Metodo que permite consultar los defensores
     * @return Devuelve un listado de defensores
     * @author ricardo
     */
    @SuppressWarnings("unchecked")
    public List<Funcionario> consultarDefensores() {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT f FROM Funcionario f ").append(" WHERE f.puesto.valorId = ").append(Puestos.ABOGADO_DEFENSOR.getValorId());
        queryString.append(" ORDER BY f.nombreFuncionario");

        Query query = super.getSession().createQuery(queryString.toString());
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Funcionario> consultarPeritosPorNombre(Funcionario criterios) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT f FROM Funcionario f ").
                append("WHERE f.tipoEspecialidad.valorId = :especialidadPerito ");
        // nombre del funcionarios
        if (criterios.getNombreFuncionario() != null) {
            queryString.append("AND lower(f.nombreFuncionario) "
                    + "LIKE :nombreFuncionario");
        }
        // apellido paterno
        if (criterios.getApellidoPaternoFuncionario() != null) {
            queryString.append("AND lower(f.apellidoPaternoFuncionario) "
                    + "LIKE :apellidoPaternoFuncionario");
        }
        // apellido maternos
        if (criterios.getApellidoMaternoFuncionario() != null) {
            queryString.append("AND lower(f.apellidoMaternoFuncionario) LIKE "
                    + ":apellidoMaternoFuncionario");
        }
        queryString.append(" ORDER BY f.nombreFuncionario");
        Query query = super.getSession().createQuery(queryString.toString());
        // Buscamos los funcionarios cuya especialidad son peritos...
        query.setParameter("especialidadPerito", TipoEspecialidad.PERICIAL.getValorId());
        // Volvemos a validar el nombre, el apellido paterno y materno.
        if (criterios.getNombreFuncionario() != null) {
            query.setParameter("nombreFuncionario",
                    "%"+ criterios.getNombreFuncionario().toLowerCase() + "%");
        }
        if (criterios.getApellidoPaternoFuncionario() != null) {
            query.setParameter("apellidoPaternoFuncionario",
                    "%"+criterios.getApellidoPaternoFuncionario().toLowerCase()+"%");
        }
        if (criterios.getApellidoMaternoFuncionario() != null) {
            query.setParameter("apellidoMaternoFuncionario",
                    "%"+criterios.getApellidoMaternoFuncionario().toLowerCase() + "%");
        }
        return query.list();
    }

    @Override
    public void asociarPeritoExpediente(Funcionario perito, Expediente expediente) {
        NumeroExpediente numExpediente =
                numeroExpedienteDAO.obtenerNumeroExpediente(expediente.getNumeroExpediente(),null);
        if (numExpediente != null) {
            numExpediente.setFuncionario(perito);
            numeroExpedienteDAO.saveOrUpdate(numExpediente);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Funcionario consultarFuncionarioPorEvidencia(Evidencia evidencia) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT e.funcionario FROM Evidencia e ").
                append("WHERE e.evidenciaId = :evidenciaId");
        Query query = super.getSession().createQuery(sb.toString());
        query.setParameter("evidenciaId", evidencia.getEvidenciaId());
        Funcionario funcionarioEvidencia = (Funcionario) query.uniqueResult();
        return funcionarioEvidencia;
    }


    @SuppressWarnings("unchecked")
	public List<Funcionario> consultarFuncionariosPorAreaYPuesto(Long area,
			Long puesto) {
        StringBuffer consulta = new StringBuffer();
        	consulta.append("SELECT f FROM Funcionario f ")
        			.append("JOIN f.usuario.usuarioRoles AS ur ")
        			.append("WHERE f.area.jerarquiaOrganizacionalId = "+area+" ")
        			.append("AND ur.rol.jerarquiaOrganizacional.jerarquiaOrganizacionalId = "+area+" ");
        if(puesto != null){
        	consulta.append("AND f.puesto.valorId = "+puesto);
        }
        consulta.append(" ORDER BY f.nombreFuncionario");
        Query query = super.getSession().createQuery(consulta.toString());

        return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> consultarFuncionarioPorDepartamento(
			Long idDepartamento) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("FROM Funcionario f");
		queryString.append(" WHERE f.area="+idDepartamento);
		queryString.append(" ORDER BY f.nombreFuncionario");
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	public Funcionario obtenerInformacionDefensorPorId(Long id_Defensor) {
		final StringBuffer queryStr = new StringBuffer();
        queryStr.append(" from Funcionario d");
        queryStr.append(" where d.claveFuncionario = ");
        queryStr.append(id_Defensor);
        Query qry = super.getSession().createQuery(queryStr.toString());
        return (Funcionario) qry.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Funcionario> consultarFuncionariosSubordinados(
			Funcionario funcionario,  Long areaId, Long jerarquia) {

		////Enable JC. Se modifica query para cargar funcionarios que pertenecen a las jerarquias
		////dependientes de la jerarquia actual del usuario. Se agrega referencia <jerarquiaDAO>
		List<JerarquiaOrganizacional> subJerarquias = jerarquiaDAO.consultarJerarquiaOrganizacionalDependienteExcepto(areaId, null);
		StringBuffer strbSubJO = new StringBuffer();
		if(subJerarquias != null)
			for(JerarquiaOrganizacional jerOrg : subJerarquias){
				strbSubJO.append(jerOrg.getJerarquiaOrganizacionalId()).append(",");
			}

		String idsJerarquiasSub = strbSubJO.toString();

		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Funcionario f WHERE ")
					.append("(f.funcionarioJefe=").append(funcionario.getClaveFuncionario());
		if(subJerarquias == null)
			queryString.append(") OR (f.area=").append(areaId).append(" AND ")
						.append(" f.area.tipoJerarquia=").append(jerarquia).append(")");
		else
			queryString.append(") OR f.area in (").append(idsJerarquiasSub)
						.append(areaId).append(")");
		/////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////

		if (funcionario.getDiscriminante() != null
				&& funcionario.getDiscriminante().getCatDiscriminanteId() != null) {
			queryString
					.append(" AND f.discriminante.catDiscriminanteId=")
					.append(funcionario.getDiscriminante()
							.getCatDiscriminanteId()).append("");
		}
		queryString.append(" ORDER BY f.nombreFuncionario");
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(queryString, pag);
	}

	@Override
	public Funcionario obtenerDefensorAsignadoAExpediente(Long expedienteId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT ne.funcionario FROM NumeroExpediente ne ")
					.append("WHERE ne.expediente=").append(expedienteId);
		Query query = super.getSession().createQuery(queryString.toString());
		return (Funcionario) query.uniqueResult();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> obtenerFuncionariosConUsuario()
			throws NSJPNegocioException {
        StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append("SELECT f ")
        		.append("FROM Funcionario f ")
        		.append("WHERE f.usuario.usuarioId > 0")
        		.append(" AND f.usuario.esActivo=").append(Boolean.TRUE)
        		.append(" ORDER BY f.nombreFuncionario");
        Query query = super.getSession().createQuery(hqlQuery.toString());
        return query.list();
	}

    @SuppressWarnings("unchecked")
	@Override
    public Funcionario consultaFuncionarioPorNombreInstitucionPuesto(Funcionario funcionario) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT f FROM Funcionario f").
                append(" WHERE f.nombreFuncionario = :nombre").
                append(" AND f.apellidoPaternoFuncionario = :paterno").
                append(" AND f.apellidoMaternoFuncionario = :materno").
                append(" AND f.puesto.valor = :valor");
        Query q = getSession().createQuery(sb.toString());
        q.setParameter("nombre", funcionario.getNombreFuncionario());
        q.setParameter("paterno", funcionario.getApellidoPaternoFuncionario());
        q.setParameter("materno", funcionario.getApellidoMaternoFuncionario());
        q.setParameter("valor", funcionario.getPuesto().getValor());
        List<Funcionario> candidatos = q.list();
        Funcionario resultado = null;
        if (candidatos != null) {
            for (Funcionario candidato : candidatos) {
                if (candidato.getInstitucion().getJerarquiaOrganizacionalId() == funcionario.getInstitucion().getJerarquiaOrganizacionalId()) {
                    resultado = candidato;
                    break;
                }
            }
        }
        return resultado;
    }

	@Override
	public boolean existeDisponibilidad(Long claveFuncionario, Date time,
			Long limite) {
        final SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        final StringBuffer qry = new StringBuffer();
        qry.append(" select SUM(a.duracionEstimada)");
        qry.append(" from SalaAudiencia s");
        qry.append(" INNER JOIN s.audiencias a");
        qry.append(" where s.salaAudienciaId = ");
        qry.append(claveFuncionario);
        qry.append("and CONVERT (nvarchar, a.fechaAudiencia, 112)=");
        qry.append(formato.format(time));

        Query query = super.getSession().createQuery(qry.toString());

        Long suma = (Long) query.uniqueResult();
        if (logger.isDebugEnabled()) {
            logger.debug("qry :: " + qry);
            logger.debug("suma :: " + suma);
            logger.debug("limiteOcupacion :: " + limite);
        }
        if(limite != null){
        	return (suma == null || suma < limite);
        }
        return (suma == null);
	}

	@Override
	public Funcionario obtenerFuncionarioXNumeroEmpleado(String numeroEmpleado) {
		final StringBuffer qry = new StringBuffer();

		qry.append(" FROM Funcionario f");
		qry.append(" WHERE f.numeroEmpleado="+numeroEmpleado);

		Query query = super.getSession().createQuery(qry.toString());
		return (Funcionario) query.uniqueResult();
	}

	@Override
	public Almacen obtenerAlmacenXFuncionario(Long idFuncionario) {
		final StringBuffer qry = new StringBuffer();

		qry.append(" SELECT ea.almacen FROM  EncargadoAlmacen ea");
		qry.append(" WHERE ea.funcionario="+idFuncionario);

		Query query = super.getSession().createQuery(qry.toString());
		return (Almacen) query.uniqueResult();
	}

    @SuppressWarnings("unchecked")
	@Override
    public Funcionario obtenerFuncionarioPorNombreCompleto(String nombreCompleto) {
        StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append(" FROM Funcionario f ");
        hqlQuery.append(" WHERE f.nombreFuncionario+' '+ f.apellidoPaternoFuncionario");
        hqlQuery.append("+' '+f.apellidoMaternoFuncionario = '"+nombreCompleto+"'");
        logger.debug("hqlQuery :: " + hqlQuery);
        Query query = super.getSession().createQuery(hqlQuery.toString());
        List<Funcionario> temp = query.list();
        if (temp!=null && temp.size()==1) {
            return temp.get(0);
        }
        return null;
    }

    @Override
    public Long countFuncionarios() {
        StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append(" SELECT count(*) ");
        hqlQuery.append(" FROM Funcionario f ");
        logger.debug("hqlQuery :: " + hqlQuery);
        Query query = super.getSession().createQuery(hqlQuery.toString());
        return  (Long) query.uniqueResult();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> consultarFuncionariosXAgencia(Long idAgencia) {
		StringBuffer hqlQuery = new StringBuffer();

        hqlQuery.append("SELECT f ")
        		.append("FROM Funcionario f ")
        		.append("WHERE f.agencia="+idAgencia);
        hqlQuery.append(" ORDER BY f.nombreFuncionario");

        Query query = super.getSession().createQuery(hqlQuery.toString());
        return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JerarquiaOrganizacional> consultarAreasXAgencia(Long idAgencia) {
		StringBuffer hqlQuery = new StringBuffer();

        hqlQuery.append("SELECT distinct f.area ")
        		.append("FROM Funcionario f ")
        		.append("WHERE f.agencia="+idAgencia);

        Query query = super.getSession().createQuery(hqlQuery.toString());
        return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> consultarFuncionariosPorDiscriminante(Long catDiscriminanteId,Long idUIE) {
		StringBuffer hqlQuery = new StringBuffer();

        hqlQuery.append(" SELECT f ")
        		.append(" FROM Funcionario f ")
        		.append(" WHERE f.discriminante.catDiscriminanteId ="+catDiscriminanteId);
        if(idUIE!=null && idUIE!=0L)
        	hqlQuery.append(" AND f.unidadIEspecializada = "+idUIE);
		hqlQuery.append(" order by  f.nombreFuncionario");

        Query query = super.getSession().createQuery(hqlQuery.toString());
        return query.list();
	}

	@Override
    public Funcionario consultarFuncionarioXNumeroExpYTipo (String numeroExpediente, Long area) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT ne ").
                append(" FROM NumeroExpediente ne ").
                append(" WHERE ne.numeroExpediente = :numeroExpediente");
        if(area != null){
            queryString.append(" AND ne.numeroExpediente.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").append(area);
        }
        Query query = super.getSession().createQuery(queryString.toString());
        query.setParameter("numeroExpediente", numeroExpediente);
        NumeroExpediente loBDnumeroExpediente = null;
        loBDnumeroExpediente = (NumeroExpediente) query.uniqueResult();
        Funcionario resultado = null;
        if (loBDnumeroExpediente != null) {
            resultado = loBDnumeroExpediente.getFuncionario();
        }
        return resultado;
    }

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO#consultarFuncionariosXCriterio(mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> consultarFuncionariosXCriterio(
			CriterioConsultaFuncionarioExternoDTO criterioConsultaFuncionarioExternoDTO,
			Funcionario funcionario)
			throws NSJPNegocioException {

		List<Funcionario> funcionariosExpediente = null;
		List<Funcionario> funcionariosRol = null;
		List<Funcionario> funcionariosExternos = null;

		if(!validaDiscriminante(criterioConsultaFuncionarioExternoDTO)
				&& !validaRol(criterioConsultaFuncionarioExternoDTO)
				&& !validaNumeroCaso(criterioConsultaFuncionarioExternoDTO)
				&& !validaNumeroExpediente(criterioConsultaFuncionarioExternoDTO)
				&& !validaFuncionarioExterno(criterioConsultaFuncionarioExternoDTO) ) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		if (validaDiscriminante(criterioConsultaFuncionarioExternoDTO)
				|| ( validaRol(criterioConsultaFuncionarioExternoDTO)
						 && !validaFuncionarioExterno(criterioConsultaFuncionarioExternoDTO) )){

			String consultaFuncionario = creaQueryConsultaXCriterioFuncionario(criterioConsultaFuncionarioExternoDTO);
			Query query = super.getSession().createQuery(consultaFuncionario);
			agregarParametrosQueryFuncionario(query, criterioConsultaFuncionarioExternoDTO);
			logger.debug("Query de funcionario :: " + query);
			funcionariosRol = (List<Funcionario>) query.list();
		}

		if (validaNumeroCaso(criterioConsultaFuncionarioExternoDTO)
					|| validaNumeroExpediente(criterioConsultaFuncionarioExternoDTO)){

			String consultaExpediente = creaQueryConsultaXCriterioExpediente(criterioConsultaFuncionarioExternoDTO);
			Query queryExpediente = super.getSession().createQuery(consultaExpediente);
			agregarParametrosQueryExpediente(queryExpediente, criterioConsultaFuncionarioExternoDTO);
			logger.debug("Query de expediente :: " + queryExpediente);
			funcionariosExpediente = (List<Funcionario>) queryExpediente.list();
		}

		if(validaFuncionarioExterno(criterioConsultaFuncionarioExternoDTO)){
			//este funcionario se utiliza para traer los funcionarios externos pertenecientes a un area y/o departamento
			if(funcionario != null) {

				if (criterioConsultaFuncionarioExternoDTO.getRolDTO() != null){
					RolDTO rolDTO = criterioConsultaFuncionarioExternoDTO.getRolDTO();
					Rol rol = new Rol(rolDTO.getRolId());
					funcionario.setRol(rol);
				}

				funcionariosExternos = this.consultarFuncionarioXFiltro(funcionario, null);
			}
		}

		List<Funcionario> funcionariosConsolidados = consolidarFuncionarios(funcionariosExpediente, funcionariosRol, funcionariosExternos);

		return funcionariosConsolidados;
	}

	/**
	 * M&eacute;todo utilitario que se encarga de validar que el n&uacute;mero del caso
	 * venga dentro de los criterios sobre los cuales se va a filtrar la consulta.
	 * @param criterio - El DTO con los filtros que se van a inyectar en la consulta.
	 * @return boolean - bandera que indica si el n&uacute;mero del caso se encuentra
	 * 					 en el criterio.
	 */
	private boolean validaNumeroCaso(CriterioConsultaFuncionarioExternoDTO criterio){
		boolean valido = false;
		if (criterio.getExpendienteDTO()!= null
				&& criterio.getExpendienteDTO().getCasoDTO() != null
				&& criterio.getExpendienteDTO().getCasoDTO().getNumeroGeneralCaso() != null
				&& !criterio.getExpendienteDTO().getCasoDTO().getNumeroGeneralCaso().isEmpty()){
			valido = true;
		}
		return valido;
	}

	/**
	 * M&eacute;todo utilitario que se encarga de validar que el n&uacute;mero del expediente
	 * venga dentro de los criterios sobre los cuales se va a filtrar la consulta.
	 * @param criterio - El DTO con los filtros que se van a inyectar en la consulta.
	 * @return boolean - bandera que indica si el n&uacute;mero del expediente se encuentra
	 * 					 en el criterio.
	 */
	private boolean validaNumeroExpediente(CriterioConsultaFuncionarioExternoDTO criterio){
		boolean valido = false;
		if (criterio.getExpendienteDTO()!= null
				&& criterio.getExpendienteDTO().getNumeroExpediente() != null
				&& !criterio.getExpendienteDTO().getNumeroExpediente().isEmpty()){
			valido = true;
		}
		return valido;
	}

	/**
	 * M&eacute;todo utilitario que se encarga de validar que se reciba un &aacute;rea y un
	 * departamento como los criterios sobre los cuales se va a filtrar la consulta.
	 * @param criterio - El DTO con los filtros que se van a inyectar en la consulta.
	 * @return boolean - bandera que indica si el nombre del discriminante se encuentra
	 * 					 en el criterio.
	 */
	private boolean validaFuncionarioExterno (CriterioConsultaFuncionarioExternoDTO criterio){
		boolean valido = false;

		if (criterio.getFuncionarioDTO()!= null
				&& criterio.getFuncionarioDTO().getDepartamento() != null
				&& criterio.getFuncionarioDTO().getDepartamento().getArea() != null
				&& criterio.getFuncionarioDTO().getDepartamento().getArea().getAreaId()>0L){
			valido = true;
		}
		return valido;
	}



	/**
	 * M&eacute;todo utilitario que se encarga de validar que el nombre del rol
	 * venga dentro de los criterios sobre los cuales se va a filtrar la consulta.
	 * @param criterio - El DTO con los filtros que se van a inyectar en la consulta.
	 * @return boolean - bandera que indica si el nombre del rol se encuentra
	 * 					 en el criterio.
	 */
	private boolean validaRol(CriterioConsultaFuncionarioExternoDTO criterio){
		boolean valido = false;
		if (criterio.getRolDTO()!= null
				&& criterio.getRolDTO().getNombreRol() != null
				&& !criterio.getRolDTO().getNombreRol().isEmpty()){
			valido = true;
		}
		return valido;
	}

	/**
	 * M&eacute;todo utilitario que se encarga de validar que el nombre del discriminante
	 * venga dentro de los criterios sobre los cuales se va a filtrar la consulta.
	 * @param criterio - El DTO con los filtros que se van a inyectar en la consulta.
	 * @return boolean - bandera que indica si el nombre del discriminante se encuentra
	 * 					 en el criterio.
	 */
	private boolean validaDiscriminante (CriterioConsultaFuncionarioExternoDTO criterio){
		boolean valido = false;
		if (criterio.getFuncionarioDTO()!= null
				&& criterio.getFuncionarioDTO().getDiscriminante() != null
				&& criterio.getFuncionarioDTO().getDiscriminante().getNombre() != null
				&& !criterio.getFuncionarioDTO().getDiscriminante().getNombre().isEmpty()){
			valido = true;
		}
		return valido;
	}

	/**
	 * M&eacute;todo que lleva a cabo la creación de la consulta que se va a ejecutar dentro de la
	 * base de datos para obtener los datos del funcionario en base a un criterio.
	 * @param criterio - El DTO con los filtros que se van a inyectar en la consulta.
	 * @return String - La consulta generada en un formato de cadena de HQL.
	 */
	private String creaQueryConsultaXCriterioFuncionario(CriterioConsultaFuncionarioExternoDTO criterio){
		StringBuffer sb = new StringBuffer(" SELECT f ")
								   .append(" FROM Funcionario as f ")
								   .append(" LEFT JOIN f.usuario.usuarioRoles as ur")
								   .append(" WHERE 1 = 1 ");
		if (validaRol(criterio)){
			sb.append(" AND ur.rol.nombreRol = :nombreRol ");
		}
		if(validaDiscriminante(criterio)){
			sb.append(" AND f.discriminante.nombre = :nombreDiscriminante ");
		}
		return sb.toString();
	}

	/**
	 * M&eacute;todo que lleva a cabo la creación de la consulta que se va a ejecutar dentro de la
	 * base de datos para obtener los datos del funcionario en base a un criterio.
	 * @param criterio - El DTO con los filtros que se van a inyectar en la consulta.
	 * @return String - La consulta generada en un formato de cadena de HQL.
	 */
	private String creaQueryConsultaXCriterioExpediente(CriterioConsultaFuncionarioExternoDTO criterio){
		StringBuffer sb = new StringBuffer(" SELECT ne.funcionario ")
								   .append(" FROM NumeroExpediente as ne ")
								   .append(" WHERE 1 = 1 ");
		if (validaNumeroExpediente(criterio)){
			sb.append(" AND ne.numeroExpediente = :numExp ");
		}
		if (validaNumeroCaso(criterio)){
			sb.append(" AND ne.expediente.caso.numeroGeneralCaso = :numCaso ");
		}
		return sb.toString();
	}

	/**
	 * M&eacute;todo que se encarga de reemplazar los parametros a la consulta de funcionarios
	 * por criterio (Rol y distrito).
	 * @param query - La consulta que se va a ejecutar en la base de datos.
	 * @param criterio - El DTO con los filtros que se van a inyectar en la consulta.
	 */
	private void agregarParametrosQueryFuncionario(Query query, CriterioConsultaFuncionarioExternoDTO criterio){
		if (validaRol(criterio)){
			query.setParameter("nombreRol", criterio.getRolDTO().getNombreRol());
		}
		if(validaDiscriminante(criterio)){
			query.setParameter("nombreDiscriminante", criterio.getFuncionarioDTO().getDiscriminante().getNombre());
		}
	}

	/**
	 * M&eacute;todo que se encarga de reemplazar los parametros a la consulta de funcionarios
	 * por criterio (Expediente).
	 * @param query - La consulta que se va a ejecutar en la base de datos.
	 * @param criterio - El DTO con los filtros que se van a inyectar en la consulta.
	 */
	private void agregarParametrosQueryExpediente(Query query, CriterioConsultaFuncionarioExternoDTO criterio){
		if (validaNumeroExpediente(criterio)){
			query.setParameter("numExp", criterio.getExpendienteDTO().getNumeroExpediente());
		}
		if (validaNumeroCaso(criterio)){
			query.setParameter("numCaso", criterio.getExpendienteDTO().getCasoDTO().getNumeroGeneralCaso());
		}
	}

	/**
	 * M&eacute;todo utilitario que se encarga de llevar a cabo la consolidaci&oacute;n de los
	 * funcionarios que se obtienen a partir de la consulta por criterios.
	 * @param funsExpediente - Funcionarios obtenidos por n&uacute;mero de expediente y/o caso
	 * @param funsFuncionario - Funcionarios obtenidos por rol y/o distrito.
	 * @param funsExternos - Funcionarios obtenidos por &aacute;rea y/o departamento
	 * @return List<Funcionario> - Lista de funcionarios consolidada, evitando repetidos.
	 */
	private List<Funcionario> consolidarFuncionarios(
					List<Funcionario> funsExpediente,
					List<Funcionario> funsFuncionario,
					List<Funcionario> funsExternos){
		HashMap<Long, Funcionario> consolidado = new HashMap<Long, Funcionario>();
		if (funsExpediente != null && !funsExpediente.isEmpty()){
			for (Funcionario exp : funsExpediente){
				if (!consolidado.containsKey(exp.getClaveFuncionario())){
					consolidado.put(exp.getClaveFuncionario(), exp);
				}
			}
		}

		if (funsFuncionario != null && !funsFuncionario.isEmpty()){
			for (Funcionario fun : funsFuncionario){
				if (!consolidado.containsKey(fun.getClaveFuncionario())){
					consolidado.put(fun.getClaveFuncionario(), fun);
				}
			}
		}

		if (funsExternos != null && !funsExternos.isEmpty()){
			for (Funcionario externo : funsExternos){
				if (!consolidado.containsKey(externo.getClaveFuncionario())){
					consolidado.put(externo.getClaveFuncionario(), externo);
				}
			}
		}
		return new ArrayList<Funcionario>(consolidado.values());
	}


	@SuppressWarnings("unchecked")
	public List<Funcionario> consultarFuncionariosPorRolyPorDistrito(Long idRol, Long idDistrito) {
        StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT F FROM Funcionario F ").append(
				" LEFT JOIN  F.usuario.usuarioRoles as UR ").append(
				" WHERE 1= 1 ");

		queryString.append(" AND F.usuario.esActivo= ").append(Boolean.TRUE);

		if( idRol !=null && idRol > 0){
			queryString. append(" AND UR.rol.rolId = ").append(
					idRol);
		}

		if( idDistrito !=null && idDistrito > 0){
			queryString. append(" AND F.discriminante.distrito.catDistritoId = ").append(
					idDistrito);
		}

        final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("F.nombreFuncionario");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("2")) {
				queryString.append(" order by ");
				queryString.append("F.apellidoPaternoFuncionario");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("3")) {
				queryString.append(" order by ");
				queryString.append("F.apellidoMaternoFuncionario");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		return super.ejecutarQueryPaginado(queryString, pag);
    }

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO#validarFuncionariosRol(java.util.List, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Funcionario> validarFuncionariosRol(List<Funcionario> funcionarios, Long idRol)throws NSJPNegocioException{

		if(funcionarios == null || funcionarios.isEmpty() || idRol == null || idRol <= 0 ){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT F FROM Funcionario F ").append(
				" LEFT JOIN  F.usuario.usuarioRoles as UR ").append(
				" WHERE ");

		queryString.append(" F.claveFuncionario in (");
		int j = 0;
		for(Funcionario func: funcionarios){
			if(j>0){
				queryString.append(",");
			}
			queryString.append(func.getClaveFuncionario());
			j++;
		}
		queryString.append(")");
		queryString. append(" AND UR.rol.rolId = ").append(idRol);
		queryString.append(" ORDER BY F.nombreFuncionario, F.apellidoPaternoFuncionario, F.apellidoMaternoFuncionario");

        final PaginacionDTO pag = PaginacionThreadHolder.get();
        return super.ejecutarQueryPaginado(queryString, pag);
    }


	@SuppressWarnings("unchecked")
	public List<Funcionario> consultarFuncionariosDeAudienciaPorEspecialidad(Long idAudiencia, List<Long> especialidades) {

    	StringBuffer queryStr = new StringBuffer();

    	String idsEspecialidades = "";

    	if(especialidades != null && especialidades.size() > 0){
    		for (Long id : especialidades) {
    			idsEspecialidades = idsEspecialidades + id + ",";
    		}
    		idsEspecialidades = idsEspecialidades.substring(0, idsEspecialidades.lastIndexOf(","));
    	}


		queryStr.append("SELECT F FROM Funcionario F where 1=1 ");

		if(especialidades != null && especialidades.size() > 0){
			queryStr.append(" AND F.tipoEspecialidad.valorId IN (");
			queryStr.append(idsEspecialidades);
			queryStr.append(")");
		}

		queryStr.append(" AND F.claveFuncionario IN (");
			queryStr.append(" SELECT fa.funcionario.claveFuncionario FROM FuncionarioAudiencia fa where fa.audiencia.audienciaId = ").append(idAudiencia);
		queryStr.append(")");


        final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryStr.append(" order by ");
				queryStr.append("F.nombreFuncionario");
				queryStr.append(" ").append(pag.getDirOrd());
			}
		}

		return super.ejecutarQueryPaginado(queryStr, pag);
    }

	@Override
	public Funcionario consultarFuncionarioPorClaveFuncionario(
			Long claveFuncionario) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM Funcionario f where f.claveFuncionario = ").append(claveFuncionario);
		logger.debug("yolo::: query funcionario pro Clave funcionario"+ queryStr);

		  Query query = super.getSession().createQuery(queryStr.toString());

	        return (Funcionario) query.uniqueResult();
	}


}
