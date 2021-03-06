/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.segob.nsjp.dao.usuario.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.usuario.UsuarioDAO;
import mx.gob.segob.nsjp.model.Usuario;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.cfg.annotations.Nullability;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sawbona
 */
@Repository("usuarioDAO")
public class UsuarioDAOImpl extends GenericDaoHibernateImpl<Usuario, Long>
        implements UsuarioDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	public static final String PASSWORD_PARA_CIFRAR = "passwordparacifrar";


    @Override
    public Usuario consultarUsuarioPorClaveService(String claveUsuario) {
        final StringBuffer query = new StringBuffer();
        query.append("from Usuario s");
        query.append(" where s.claveUsuario = :claveUsuario");
        query.append(" and s.esActivo=").append(true);
        logger.debug("query :: " + query);
        Query hbq = super.getSession().createQuery(query.toString());
        hbq.setString("claveUsuario", claveUsuario);
        return (Usuario) hbq.uniqueResult();
    }


    @Override
	public Usuario consultarUsuarioPorClaveFuncionario(Long claveFuncionario) {
        final StringBuffer query = new StringBuffer();
        query.append("from Usuario s");
        query.append(" where s.funcionario.claveFuncionario = :clave");
        logger.debug("query :: " + query);
        Query hbq = super.getSession().createQuery(query.toString());
        hbq.setLong("clave", claveFuncionario);
        Usuario loUsuarioBD = (Usuario) hbq.uniqueResult();
        loUsuarioBD.setPasword(consultarPasswordXClaveUsuario(loUsuarioBD.getClaveUsuario()));
        return loUsuarioBD;
	}


    @Override
    public Usuario login(String claveUsuario, String pwd) {
    	final StringBuffer queryStr = new StringBuffer();
		queryStr.append("SELECT CONVERT(VARCHAR(300),DECRYPTBYPASSPHRASE('" + PASSWORD_PARA_CIFRAR +"',password))");
		queryStr.append(" FROM Usuario");
		queryStr.append(" WHERE cClaveUsuario = '");
		queryStr.append(claveUsuario);
		queryStr.append("'");
		
		String passwordBD = (String) super.getSession().createSQLQuery(queryStr.toString()).uniqueResult();
		logger.info("El password encotrado es: " + passwordBD + "el password sugerido es " + pwd);
		
		if(passwordBD != null && passwordBD.equals(pwd))
			return consultarUsuarioPorClaveService(claveUsuario);
		else
			return null;		
		
    }
    
    public Usuario registrarUsuario(Usuario usuario){
    	final StringBuffer queryStr = new StringBuffer();
		queryStr.append("INSERT INTO Usuario(cClaveUsuario,password,iClaveFuncionario)");
		queryStr.append(" VALUES('");
		queryStr.append(usuario.getClaveUsuario()+ "', ENCRYPTBYPASSPHRASE('" + PASSWORD_PARA_CIFRAR +"','")
				.append(usuario.getPasword()+"')," + usuario.getFuncionario().getClaveFuncionario() + ")");
		super.getSession().createSQLQuery(queryStr.toString()).executeUpdate();
		return consultarUsuarioPorClaveService(usuario.getClaveUsuario());
    }
    
    
    public void actualizarPasswordUsuario(Usuario usuario){
    	final StringBuffer queryStr = new StringBuffer();
		queryStr.append("UPDATE Usuario");
		queryStr.append(" SET password = (select ENCRYPTBYPASSPHRASE('" + PASSWORD_PARA_CIFRAR +"','")
				.append(usuario.getPasword()+"'))");
		queryStr.append(" WHERE Usuario_id = ");
		queryStr.append(usuario.getUsuarioId());		
		super.getSession().createSQLQuery(queryStr.toString()).executeUpdate();
    }
    
    
    @Override
    public String consultarPasswordXClaveUsuario(String cClaveUsuario) {
    	final StringBuffer queryStr = new StringBuffer();
		queryStr.append("SELECT CONVERT(VARCHAR(300),DECRYPTBYPASSPHRASE('" + PASSWORD_PARA_CIFRAR +"',password))");
		queryStr.append(" FROM Usuario");
		queryStr.append(" WHERE cClaveUsuario = '");
		queryStr.append(cClaveUsuario);
		queryStr.append("'");		
		return (String) super.getSession().createSQLQuery(queryStr.toString()).uniqueResult();		
		
    }
    
    //Metodo utilizado por el batch para desbloquear usuarios cada 15 minutos
    @Override
    public void actualizarSesionUsuario(){
    	final StringBuffer queryStr = new StringBuffer();
		queryStr.append("UPDATE Usuario");
		queryStr.append(" SET iSesion = 1");
		queryStr.append(" WHERE iSesion = 2");
		super.getSession().createSQLQuery(queryStr.toString()).executeUpdate();
    }
    
	@Override
	public Usuario consultarUsuarioXClaveFuncionario(Long claveFuncionario) {
    	final StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM Usuario u WHERE u.funcionario.claveFuncionario =");
		queryStr.append(claveFuncionario);	
		try {
			return (Usuario) super.getSession().createQuery(queryStr.toString()).uniqueResult();	
		} catch (Exception e) {
			logger.debug("Usuario no encontrado en BD local");
			Usuario usuario =null;
			return (usuario);
		}
			
	}


	@Override
	public List<Usuario> consultarUsuarioPorAgenciaYArea(Long idAgencia, Areas area) throws NSJPNegocioException {
		final StringBuffer queryStr = new StringBuffer();
		
		
		queryStr.append("select u FROM Usuario u ")
					.append("left join u.funcionario fu ")
					.append("left join fu.unidadIEspecializada uie ");
					
					if((idAgencia!=null ) && area!=null){
						if(idAgencia!=0){
							queryStr.append(" WHERE  uie.catUIEId = ").append(idAgencia)
							.append(" AND fu.area.jerarquiaOrganizacionalId=").append(area.parseLong());
						}
						
					}
					
					if(idAgencia!=null && area==null){
						queryStr.append(" WHERE  uie.catUIEId = ").append(idAgencia);
					}
					
					if(idAgencia==null ){
						if(area!=null)
							queryStr.append(" WHERE  fu.area.jerarquiaOrganizacionalId=").append(area.parseLong());
					}else if(idAgencia==0 ){
						if(area!=null)
							queryStr.append(" WHERE  fu.area.jerarquiaOrganizacionalId=").append(area.parseLong());
					}
					

					logger.debug("query:::"+queryStr);
		try {
			return (List<Usuario>) super.getSession().createQuery(queryStr.toString()).list();	
		} catch (Exception e) {
			logger.debug("No existe ningun usuario con la agencia y area solicitadas");
			List<Usuario> usuarios =new ArrayList<Usuario>();
			return (usuarios);
		}
	}
    
}
