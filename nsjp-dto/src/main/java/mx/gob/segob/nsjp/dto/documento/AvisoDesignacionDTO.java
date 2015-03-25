package mx.gob.segob.nsjp.dto.documento;

import java.util.LinkedList;
import java.util.List;

import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;


public class AvisoDesignacionDTO extends NotificacionDTO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8235395455368351285L;
	private String detenido;
	private FuncionarioDTO funcionario;
    private ExpedienteDTO expediente;
    private AvisoDetencionDTO avisoDetencion = null;
    private SolicitudDefensorDTO solicitudDefensor = null;
    private Boolean tieneSolicitudDefensorExterno = Boolean.FALSE;
    private List<DelitoDTO> delitos = new LinkedList<DelitoDTO>();
    
    /**
	 * Regresa el valor de la propiedad detenido
	 * @return the detenido
	 */
	public String getDetenido() {
		return detenido;
	}

	/**
	 * Establece el valor de la propiedad detenido
	 * @param detenido valo detenido a almacenar
	 */
	public void setDetenido(String detenido) {
		this.detenido = detenido;
	}
	
    /**
	 * Regresa el valor de la propiedad funcionario
	 * @return the funcionario
	 */
	public FuncionarioDTO getFuncionario() {
		return funcionario;
	}
	
	/**
	 * Establece el valor de la propiedad funcionario
	 * @param funcionario valo funcionario a almacenar
	 */
	public void setFuncionario(FuncionarioDTO funcionario) {
		this.funcionario = funcionario;
	}
	
	/**
	 * Regresa el valor de la propiedad expediente
	 * @return the expediente
	 */
	public ExpedienteDTO getExpediente() {
		return expediente;
	}
	
	/**
	 * Establece el valor de la propiedad expediente
	 * @param expediente valo expediente a almacenar
	 */
	public void setExpediente(ExpedienteDTO expediente) {
		this.expediente = expediente;
	}
	
	/**
	 * Regresa el valor de la propiedad avisoDetencion
	 * @return the avisoDetencion
	 */
	public AvisoDetencionDTO getAvisoDetencion() {
		return avisoDetencion;
	}
	
	/**
	 * Establece el valor de la propiedad avisoDetencion
	 * @param avisoDetencion valo avisoDetencion a almacenar
	 */
	public void setAvisoDetencion(AvisoDetencionDTO avisoDetencion) {
		this.avisoDetencion = avisoDetencion;
	}
	
	/**
	 * Regresa el valor de la propiedad solicitudDefensor
	 * @return the solicitudDefensor
	 */
	public SolicitudDefensorDTO getSolicitudDefensor() {
		return solicitudDefensor;
	}

	/**
	 * Establece el valor de la propiedad solicitudDefensor
	 * @param solicitudDefensor valo solicitudDefensor a almacenar
	 */
	public void setSolicitudDefensor(SolicitudDefensorDTO solicitudDefensor) {
		this.solicitudDefensor = solicitudDefensor;
	}

    /**
     * Método de acceso al campo tieneSolicitudDefensorExterno.
     * @return El valor del campo tieneSolicitudDefensorExterno
     */
    public Boolean getTieneSolicitudDefensorExterno() {
        return tieneSolicitudDefensorExterno;
    }

    /**
     * Asigna el valor al campo tieneSolicitudDefensorExterno.
     * @param tieneSolicitudDefensorExterno el valor tieneSolicitudDefensorExterno a asignar
     */
    public void setTieneSolicitudDefensorExterno(
            Boolean tieneSolicitudDefensorExterno) {
        this.tieneSolicitudDefensorExterno = tieneSolicitudDefensorExterno;
    }
	
	/**
	 * Regresa el valor de la propiedad delitos
	 * @return the delitos
	 */
	public List<DelitoDTO> getDelitos() {
		return delitos;
	}

	/**
	 * Establece el valor de la propiedad delitos
	 * @param delitos valo delitos a almacenar
	 */
	public void setDelitos(List<DelitoDTO> delitos) {
		this.delitos = delitos;
	}
    
}
