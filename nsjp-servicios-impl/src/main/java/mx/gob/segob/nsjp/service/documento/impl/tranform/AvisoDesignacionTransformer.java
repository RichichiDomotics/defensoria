package mx.gob.segob.nsjp.service.documento.impl.tranform;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.AvisoDetencion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.SolicitudDefensorDelito;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.avisodetencion.impl.transform.AvisoDetencionTransformer;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoTransfromer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.persona.impl.transform.NombreDemograficoTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudDefensorTransformer;

import org.apache.log4j.Logger;

public class AvisoDesignacionTransformer {
	private final static Logger logger = Logger.getLogger(AvisoDesignacionTransformer.class);
	
	public static AvisoDesignacionDTO transformar(AvisoDesignacion aviso) {
		AvisoDesignacionDTO designacion = new AvisoDesignacionDTO();
        designacion.setDocumentoId(aviso.getDocumentoId());
        designacion.setConsecutivoNotificacion(aviso.getConsecutivoNotificacion());
        designacion.setDomicilio(aviso.getDomicilio());
        designacion.setFechaCitado(aviso.getFechaCitado());
        designacion.setLugar(aviso.getLugar());
        designacion.setLugarCitado(aviso.getLugarCitado());
        designacion.setMotivo(aviso.getMotivo());
        designacion.setPenalidades(aviso.getPenalidades());
        designacion.setPersonaAutoriza(aviso.getPersonaAutoriza());
        designacion.setPuestoAutoriza(aviso.getPersonaAutoriza());
        designacion.setNumeroCasoAsociado(aviso.getNumeroCasoAsociado());
        designacion.setFechaCreacion(aviso.getFechaCreacion());
        designacion.setFolioNotificacion(aviso.getFolioNotificacion());
        designacion.setClaveDiscriminanteOrigen(aviso.getClaveDiscriminanteOrigen());
        if(aviso.getConfInstitucion() != null){
        	designacion.setConfInstitucion(new ConfInstitucionDTO(aviso.getConfInstitucion().getConfInstitucionId(), aviso.getConfInstitucion().getNombreInst()));
        }
        if(aviso.getExpediente() != null){
        	//Condicion por que causaba un error 
        	if( aviso.getExpediente().getNumeroExpedientes().size() > 0 ){
            	NumeroExpediente ne  = aviso.getExpediente().getNumeroExpedientes().iterator().next();
            	designacion.setExpediente(ExpedienteTransformer.transformarExpedienteBasicoDefensoria(ne));
            	logger.info("**********************************************************");
    			logger.info("Expediente ID : "+aviso.getExpediente().getExpedienteId());
    			logger.info("**********************************************************");
    			logger.info("CASO          : "+aviso.getExpediente().getCaso());
    			logger.info("**********************************************************");
            	if(aviso.getExpediente().getCaso() != null){
            		String numero = aviso.getExpediente().getCaso().getNumeroGeneralCaso();
            		Long idnumero = aviso.getExpediente().getCaso().getCasoId();
            		designacion.getExpediente().setCasoDTO(new CasoDTO(idnumero, numero));
            	}
        	}
        }
        
        if(aviso.getFuncionario() != null){
        	designacion.setFuncionario(FuncionarioTransformer.transformarFuncionarioBasico(aviso.getFuncionario()));
        }

        if(aviso.getSolicitudDefensor() != null){
        	designacion.setSolicitudDefensor(SolicitudDefensorTransformer.transformarSolicitudDefensoriaBasico(aviso.getSolicitudDefensor()));
        }
        
        if(aviso.getAvisoDetencion() != null){
        	designacion.setAvisoDetencion(AvisoDetencionTransformer.transformarAvisoDetencion(aviso.getAvisoDetencion()));
        }
        if( aviso.getInvolucrado() != null ){
            NombreDemograficoDTO nombre	 =NombreDemograficoTransformer.transformarNombreDemografico((NombreDemografico)aviso.getInvolucrado().getNombreDemograficos().toArray()[0]);
    		designacion.setDetenido(nombre.getNombreCompleto());
    		if( aviso.getSolicitudDefensor().getSolicitudDefensorDelitos() != null ){
    			List<DelitoDTO> delitos			= new ArrayList<DelitoDTO>();
    			for(SolicitudDefensorDelito defDel  : aviso.getSolicitudDefensor().getSolicitudDefensorDelitos()  ){
    				DelitoDTO delito	= DelitoTransfromer.transformarDelito(defDel.getDelito());
    				delitos.add(delito);
    			}
    			designacion.setDelitos(delitos);
    		}
        }
        return designacion;
	}
	
	
	public static AvisoDesignacionDTO transformarBasico(AvisoDesignacion aviso) {
		AvisoDesignacionDTO designacion = new AvisoDesignacionDTO();
        designacion.setDocumentoId(aviso.getDocumentoId());
        designacion.setConsecutivoNotificacion(aviso.getConsecutivoNotificacion());
        designacion.setDomicilio(aviso.getDomicilio());
        designacion.setFechaCitado(aviso.getFechaCitado());
        designacion.setLugar(aviso.getLugar());
        designacion.setLugarCitado(aviso.getLugarCitado());
        designacion.setMotivo(aviso.getMotivo());
        designacion.setPenalidades(aviso.getPenalidades());
        designacion.setPersonaAutoriza(aviso.getPersonaAutoriza());
        designacion.setPuestoAutoriza(aviso.getPersonaAutoriza());
        designacion.setNumeroCasoAsociado(aviso.getNumeroCasoAsociado());
        designacion.setFechaCreacion(aviso.getFechaCreacion());
        designacion.setFolioNotificacion(aviso.getFolioNotificacion());
        if(aviso.getConfInstitucion() != null){
        	designacion.setConfInstitucion(new ConfInstitucionDTO(aviso.getConfInstitucion().getConfInstitucionId(), aviso.getConfInstitucion().getNombreInst()));
        }
        if(aviso.getExpediente() != null){
        	NumeroExpediente ne  = aviso.getExpediente().getNumeroExpedientes().iterator().next();
        	designacion.setExpediente(ExpedienteTransformer.transformarExpedienteBasico(ne));
        	logger.info("**********************************************************");
			logger.info("Expediente ID : "+aviso.getExpediente().getExpedienteId());
			logger.info("**********************************************************");
			logger.info("CASO          : "+aviso.getExpediente().getCaso());
			logger.info("**********************************************************");
        	if(aviso.getExpediente().getCaso() != null){
        		String numero = aviso.getExpediente().getCaso().getNumeroGeneralCaso();
        		Long idnumero = aviso.getExpediente().getCaso().getCasoId();
        		designacion.getExpediente().setCasoDTO(new CasoDTO(idnumero, numero));
        	}
        }
        
        if(aviso.getFuncionario() != null){
        	designacion.setFuncionario(FuncionarioTransformer.transformarFuncionario(aviso.getFuncionario()));
        }

        if(aviso.getSolicitudDefensor() != null){
        	designacion.setSolicitudDefensor(SolicitudDefensorTransformer.transformarSolicitudDefensoriaBasico(aviso.getSolicitudDefensor()));
        }
        
        if(aviso.getAvisoDetencion() != null){
        	designacion.setAvisoDetencion(AvisoDetencionTransformer.transformarAvisoDetencion(aviso.getAvisoDetencion()));
        }
        return designacion;
	}

	public static AvisoDesignacion transformar(AvisoDesignacionDTO designacion) {
        AvisoDesignacion avisoDesignacion = new AvisoDesignacion();
        avisoDesignacion.setEstatus(new Valor(designacion.getEstatus().getIdCampo()));
        
        if(designacion.getExpediente() != null){
        	avisoDesignacion.setExpediente(new Expediente(designacion.getExpediente().getExpedienteId()));
        }
        
        if(designacion.getFuncionario() != null){
        	avisoDesignacion.setFuncionario(FuncionarioTransformer.transformarFuncionario(designacion.getFuncionario()));
        }

        if(designacion.getSolicitudDefensor() != null){
        	SolicitudDefensor solicitud = new SolicitudDefensor();
        	solicitud.setDocumentoId(designacion.getSolicitudDefensor().getDocumentoId());
        	avisoDesignacion.setSolicitudDefensor(solicitud);
        }
        
        if(designacion.getAvisoDetencion() != null){
        	AvisoDetencion detencion = new AvisoDetencion();
        	detencion.setDocumentoId(designacion.getAvisoDetencion().getDocumentoId());
        	avisoDesignacion.setAvisoDetencion(detencion);
        }
        
        return avisoDesignacion;
	}

}
