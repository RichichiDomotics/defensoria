package mx.gob.segob.nsjp.ws.cliente.respuestatranscripcion;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2011-10-25T16:09:02.718-05:00
 * Generated source version: 2.4.1
 * 
 */
@WebService(targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", name = "RegistrarTranscripcionAudienciaExporter")
@XmlSeeAlso({ObjectFactory.class})
public interface RegistrarTranscripcionAudienciaExporter {

    @Action(input = "http://ws.service.nsjp.segob.gob.mx/RegistrarTranscripcionAudienciaExporter/registrarTranscripcionAudienciaRequest", output = "http://ws.service.nsjp.segob.gob.mx/RegistrarTranscripcionAudienciaExporter/registrarTranscripcionAudienciaResponse", fault = {@FaultAction(className = NSJPNegocioException_Exception.class, value = "http://ws.service.nsjp.segob.gob.mx/RegistrarTranscripcionAudienciaExporter/registrarTranscripcionAudiencia/Fault/NSJPNegocioException")})
    @RequestWrapper(localName = "registrarTranscripcionAudiencia", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", className = "mx.gob.segob.nsjp.ws.cliente.respuestatranscripcion.RegistrarTranscripcionAudiencia")
    @WebMethod
    @ResponseWrapper(localName = "registrarTranscripcionAudienciaResponse", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", className = "mx.gob.segob.nsjp.ws.cliente.respuestatranscripcion.RegistrarTranscripcionAudienciaResponse")
    public void registrarTranscripcionAudiencia(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        mx.gob.segob.nsjp.ws.cliente.respuestatranscripcion.DocumentoWSDTO arg1
    ) throws NSJPNegocioException_Exception;
}