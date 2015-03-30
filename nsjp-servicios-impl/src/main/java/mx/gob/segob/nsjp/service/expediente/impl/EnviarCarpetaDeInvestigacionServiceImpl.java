/**
 * Nombre del Programa : EnviarCarpetaDeInvestigacionServiceImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 22-jul-2011
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
package mx.gob.segob.nsjp.service.expediente.impl;

import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoMovimiento;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteWSDTO;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.RegistroBitacora;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.bitacora.RegistrarBitacoraService;
import mx.gob.segob.nsjp.service.expediente.ActualizarCarpetaDeInvestigacionService;
import mx.gob.segob.nsjp.service.expediente.EnviarCarpetaDeInvestigacionService;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarcarpetainvestigacion.ExpedienteWSDTOTransformer;
import mx.gob.segob.nsjp.service.solicitud.ActualizarEstatusSolicitudService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Este es la implementacion del ws que recibe el expedienteWSDTO para
 * registrarlo en la base de datos.
 * @version 1.0
 * @author Jacob Lobaco
 */
@Service("enviarCarpetaDeInvestigacionService")
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Transactional
public class EnviarCarpetaDeInvestigacionServiceImpl implements
        EnviarCarpetaDeInvestigacionService {
    @Autowired
    private RegistrarBitacoraService registrarBitacoraService;
	 /**
     * Logger de la clase.
     */
   private final static Logger logger = Logger
           .getLogger(EnviarCarpetaDeInvestigacionServiceImpl.class);
   
	
	@Autowired 
	ActualizarCarpetaDeInvestigacionService actualizarCarpetaDeInvestigacionService;
	@Autowired
	private ActualizarEstatusSolicitudService actualizarEstatusSolicitudService;
	@Autowired
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private SolicitudDAO solicitudDAO;
	
    @Override
    public Long enviarCarpetaDeInvestigacion(ExpedienteWSDTO expedienteWSDTO,String numeroGeneralCaso, String folioSolicitud)
			throws NSJPNegocioException{
		System.out.print("Empezando crpeta de investigavion");
		Long idExpediente2 = 0L;
		Expediente expediente=new Expediente();
		final Date date = new Date();
		final SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String fecha = formatFecha.format(date);
		if(expedienteWSDTO== null || !(numeroGeneralCaso!= null && !numeroGeneralCaso.trim().isEmpty())
				|| !(folioSolicitud != null && !folioSolicitud.trim().isEmpty()))
			return 0L;

		System.out.print("*************** RECIBIDA CARPETA INVESTIGACION ***************");
		System.out.print(" Expediente : " + expedienteWSDTO);
		System.out.print(" numeroGeneralCaso : " + numeroGeneralCaso);
		System.out.print(" folioSolicitud : " + folioSolicitud);
		expediente.setFechaCreacion(new Date());
		ExpedienteDTO expedienteDTO =  ExpedienteWSDTOTransformer.expedienteWsdto2ExpedienteDto(expedienteWSDTO);
		idExpediente2= this.expedienteDAO.create(expediente);
		/*System.out.print("FECHA INICIAL " + date);
		expedienteDTO.setFechaApertura(date);*/
		System.out.print(" id expediente : " + idExpediente2);
		expedienteDTO.setExpedienteId(idExpediente2);
		//expedienteDTO = actualizarCarpetaDeInvestigacionService.actualizarExpedienteDeCarpetaInvestigacionDefensoria(expedienteDTO);
		System.out.print("************  RECIBIDA ************ ");
		//ExpedientePrint.imprimirDatosExpediente(expedienteDTO);

		//Consultar el expediente por folioSolicitud
		ExpedienteDTO  expedienteBDDTO = actualizarCarpetaDeInvestigacionService.consultarExpedientePorFolioSolicitud(folioSolicitud);
		if( expedienteBDDTO == null || expedienteBDDTO.getExpedienteId()==null ||expedienteBDDTO.getExpedienteId()<0 )
			return 0L;

		System.out.print("Expediente encontrado ID: " + expedienteBDDTO.getExpedienteId());

		expedienteDTO.setExpedienteId(expedienteBDDTO.getExpedienteId());
		expedienteDTO.setNumeroExpedienteId(expedienteBDDTO.getNumeroExpedienteId());

		expedienteDTO = actualizarCarpetaDeInvestigacionService.actualizarExpedienteDeCarpetaInvestigacionDefensoria(expedienteDTO);

		Long idExpediente = actualizarCarpetaDeInvestigacionService.actualizarExpedientePorFolioSolicitud(folioSolicitud, EstatusExpediente.ABIERTO_TECNICA_CON_CARPETA);
		actualizarEstatusSolicitudService.actualizaEstatusSolicitud(folioSolicitud, EstatusSolicitud.CERRADA);
		System.out.print("*************** RECIBIDA CARPETA INVESTIGACION *************** [OK]");
		RegistroBitacora regBitEta = new RegistroBitacora();

		regBitEta.setFechaInicio(new Date());
		regBitEta.setTipoMovimiento(new Valor(
				TipoMovimiento.RECIBIR_CARPETA_DE_INVESTIGACION.getValorId()));
		regBitEta.setNuevo("Carpeta de Investigación Recibida");
		regBitEta.setNumeroExpediente(new NumeroExpediente(idExpediente));
		registrarBitacoraService.registrarMovimientoDeExpedienteEnBitacora(regBitEta);
		return expedienteDTO!=null?expedienteDTO.getExpedienteId():0L;
    }
}
