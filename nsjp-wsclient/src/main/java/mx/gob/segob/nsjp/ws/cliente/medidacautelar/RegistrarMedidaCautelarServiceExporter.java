package mx.gob.segob.nsjp.ws.cliente.medidacautelar;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2012-06-30T02:47:51.076-05:00
 * Generated source version: 2.4.1
 * 
 */
@WebService(targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", name = "RegistrarMedidaCautelarServiceExporter")
@XmlSeeAlso({ObjectFactory.class})
public interface RegistrarMedidaCautelarServiceExporter {

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://ws.service.nsjp.segob.gob.mx/RegistrarMedidaCautelarServiceExporter/actualizarEstatusMedidaCautelarRequest", output = "http://ws.service.nsjp.segob.gob.mx/RegistrarMedidaCautelarServiceExporter/actualizarEstatusMedidaCautelarResponse", fault = {@FaultAction(className = NSJPNegocioException_Exception.class, value = "http://ws.service.nsjp.segob.gob.mx/RegistrarMedidaCautelarServiceExporter/actualizarEstatusMedidaCautelar/Fault/NSJPNegocioException")})
    @RequestWrapper(localName = "actualizarEstatusMedidaCautelar", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", className = "mx.gob.segob.nsjp.ws.cliente.medidacautelar.ActualizarEstatusMedidaCautelar")
    @WebMethod
    @ResponseWrapper(localName = "actualizarEstatusMedidaCautelarResponse", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", className = "mx.gob.segob.nsjp.ws.cliente.medidacautelar.ActualizarEstatusMedidaCautelarResponse")
    public java.lang.Boolean actualizarEstatusMedidaCautelar(
        @WebParam(name = "arg0", targetNamespace = "")
        mx.gob.segob.nsjp.ws.cliente.medidacautelar.MedidaCautelarWSDTO arg0
    ) throws NSJPNegocioException_Exception;

    @Action(input = "http://ws.service.nsjp.segob.gob.mx/RegistrarMedidaCautelarServiceExporter/registrarMedidaCautelarRequest", output = "http://ws.service.nsjp.segob.gob.mx/RegistrarMedidaCautelarServiceExporter/registrarMedidaCautelarResponse", fault = {@FaultAction(className = NSJPNegocioException_Exception.class, value = "http://ws.service.nsjp.segob.gob.mx/RegistrarMedidaCautelarServiceExporter/registrarMedidaCautelar/Fault/NSJPNegocioException")})
    @RequestWrapper(localName = "registrarMedidaCautelar", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", className = "mx.gob.segob.nsjp.ws.cliente.medidacautelar.RegistrarMedidaCautelar")
    @WebMethod
    @ResponseWrapper(localName = "registrarMedidaCautelarResponse", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", className = "mx.gob.segob.nsjp.ws.cliente.medidacautelar.RegistrarMedidaCautelarResponse")
    public void registrarMedidaCautelar(
        @WebParam(name = "arg0", targetNamespace = "")
        mx.gob.segob.nsjp.ws.cliente.medidacautelar.MedidaCautelarWSDTO arg0
    ) throws NSJPNegocioException_Exception;
}