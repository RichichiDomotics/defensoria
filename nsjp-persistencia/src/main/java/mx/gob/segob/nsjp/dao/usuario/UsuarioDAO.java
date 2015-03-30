/*

 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.segob.nsjp.dao.usuario;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Usuario;

/**
 *
 * @author sawbona
 */
public interface UsuarioDAO extends GenericDao<Usuario, Long> {

    public Usuario consultarUsuarioPorClaveService(String claveUsuario);

	public Usuario consultarUsuarioPorClaveFuncionario(Long claveFuncionario);
	
	public Usuario login(String claveUsuario, String pwd);
	
	public Usuario registrarUsuario(Usuario usuario);
	
	public void actualizarPasswordUsuario(Usuario usuario);
	
	public String consultarPasswordXClaveUsuario(String claveUsuario);
	
	public void actualizarSesionUsuario();
	
	/**
	 * @author encesarvarga (Enable IT)
	 * @param claveFuncionario
	 * @return {@link Usuario}
	 */
	public Usuario consultarUsuarioXClaveFuncionario(Long claveFuncionario);
	
	/**
	 * @author encesarvarga (Enable IT)
	 * @param claveFuncionario
	 * @return {@link Usuario}	
	 * consulta por Agencia(CatUIE) y por Area (Jerarquia)
	*/
	public List<Usuario> consultarUsuarioPorAgenciaYArea(Long idAgencia, Areas area) throws NSJPNegocioException;
}
