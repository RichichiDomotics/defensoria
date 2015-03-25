package mx.gob.segob.nsjp.dto.audiencia.pjsiaj;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;

public class ImputadoDelitoResolucionWSDTO extends GenericWSDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2643122116228085098L;
	private List<VariableResolucionWSDTO> variableResolucionWSDTO;
    private int consec_imp;
    private int imputado_pj;
    private int audienciaIdPJ;
    private int resolucionId;
    private int resolucionGrupo;
    private Date fecha;
    private int juez;
    private int juzgado;
	/**
	 * @return the variableResolucionWSDTO
	 */
	public List<VariableResolucionWSDTO> getVariableResolucionWSDTO() {
		return variableResolucionWSDTO;
	}
	/**
	 * @param variableResolucionWSDTO the variableResolucionWSDTO to set
	 */
	public void setVariableResolucionWSDTO(
			List<VariableResolucionWSDTO> variableResolucionWSDTO) {
		this.variableResolucionWSDTO = variableResolucionWSDTO;
	}
	/**
	 * @return the consec_imp
	 */
	public int getConsec_imp() {
		return consec_imp;
	}
	/**
	 * @param consec_imp the consec_imp to set
	 */
	public void setConsec_imp(int consec_imp) {
		this.consec_imp = consec_imp;
	}
	/**
	 * @return the imputado_pj
	 */
	public int getImputado_pj() {
		return imputado_pj;
	}
	/**
	 * @param imputado_pj the imputado_pj to set
	 */
	public void setImputado_pj(int imputado_pj) {
		this.imputado_pj = imputado_pj;
	}
	/**
	 * @return the audienciaIdPJ
	 */
	public int getAudienciaIdPJ() {
		return audienciaIdPJ;
	}
	/**
	 * @param audienciaIdPJ the audienciaIdPJ to set
	 */
	public void setAudienciaIdPJ(int audienciaIdPJ) {
		this.audienciaIdPJ = audienciaIdPJ;
	}
	/**
	 * @return the resolucionId
	 */
	public int getResolucionId() {
		return resolucionId;
	}
	/**
	 * @param resolucionId the resolucionId to set
	 */
	public void setResolucionId(int resolucionId) {
		this.resolucionId = resolucionId;
	}
	/**
	 * @return the resolucionGrupo
	 */
	public int getResolucionGrupo() {
		return resolucionGrupo;
	}
	/**
	 * @param resolucionGrupo the resolucionGrupo to set
	 */
	public void setResolucionGrupo(int resolucionGrupo) {
		this.resolucionGrupo = resolucionGrupo;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the juez
	 */
	public int getJuez() {
		return juez;
	}
	/**
	 * @param juez the juez to set
	 */
	public void setJuez(int juez) {
		this.juez = juez;
	}
	/**
	 * @return the juzgado
	 */
	public int getJuzgado() {
		return juzgado;
	}
	/**
	 * @param juzgado the juzgado to set
	 */
	public void setJuzgado(int juzgado) {
		this.juzgado = juzgado;
	}
}
