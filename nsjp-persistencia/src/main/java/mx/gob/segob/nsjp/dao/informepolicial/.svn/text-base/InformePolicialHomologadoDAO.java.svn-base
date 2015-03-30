package mx.gob.segob.nsjp.dao.informepolicial;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.DelitoIph;
import mx.gob.segob.nsjp.model.FaltaAdministrativaIph;
import mx.gob.segob.nsjp.model.InformePolicialHomologado;

public interface InformePolicialHomologadoDAO 
	   extends
	             GenericDao<InformePolicialHomologado,Long> {	
	public List<Object[]> ObtenerFolioIPH();
	public InformePolicialHomologado consultaInformePorFolio(Long folio);
	public InformePolicialHomologado consultarInformePorId(Long Id);
	public List<DelitoIph> consultarDelitosDeIPH(Long idInforme);
	public List<FaltaAdministrativaIph> consultarFaltaAdministrativaDeIPH(Long idInforme);
	public List<InformePolicialHomologado> consultarInformes(Long userActual);
	
	/**
	 * Obtiene el numero de IPH o IP resgistrados dentro de un rango de fechas.
	 * @author cesarAgustin
	 * @param fechaInicio
	 * @param fechaFin
	 * @param condicion
	 * @return
	 */
	public Long obtenerIPHPorFechas(Date fechaInicio, Date fechaFin, Boolean condicion);
	
	/**Enable IT JC.
	 * Obtiene el IPH asociado a un Expediente
	 * @param idExpediente
	 * @return El <code>InformePolicialHomologado</code> generador del Expediente o <code>null</code> si no existe IPH asociado
	 */
	InformePolicialHomologado obtenerIPHPorExpedienteId(Long idExpediente);
	
	/**
	 * eNABLE IT
	 * Borra los delitos asociados a un IPH
	 * @param idInforme
	 * @return int
	 */
	public int borrarDelitoIPH(Long idInforme);
	
}
