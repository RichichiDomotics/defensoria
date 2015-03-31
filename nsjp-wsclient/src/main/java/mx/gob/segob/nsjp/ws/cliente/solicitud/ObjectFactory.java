
package mx.gob.segob.nsjp.ws.cliente.solicitud;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.segob.nsjp.ws.cliente.solicitud package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NSJPNegocioException_QNAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "NSJPNegocioException");
    private final static QName _RecibirSolicitud_QNAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "recibirSolicitud");
    private final static QName _RecibirSolicitudResponse_QNAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "recibirSolicitudResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.segob.nsjp.ws.cliente.solicitud
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NSJPNegocioException }
     * 
     */
    public NSJPNegocioException createNSJPNegocioException() {
        return new NSJPNegocioException();
    }

    /**
     * Create an instance of {@link RecibirSolicitudResponse }
     * 
     */
    public RecibirSolicitudResponse createRecibirSolicitudResponse() {
        return new RecibirSolicitudResponse();
    }

    /**
     * Create an instance of {@link RecibirSolicitud }
     * 
     */
    public RecibirSolicitud createRecibirSolicitud() {
        return new RecibirSolicitud();
    }

    /**
     * Create an instance of {@link SolicitudWSDTO }
     * 
     */
    public SolicitudWSDTO createSolicitudWSDTO() {
        return new SolicitudWSDTO();
    }

    /**
     * Create an instance of {@link ArchivoDigitalWSDTO }
     * 
     */
    public ArchivoDigitalWSDTO createArchivoDigitalWSDTO() {
        return new ArchivoDigitalWSDTO();
    }

    /**
     * Create an instance of {@link GenericWSDTO }
     * 
     */
    public GenericWSDTO createGenericWSDTO() {
        return new GenericWSDTO();
    }

    /**
     * Create an instance of {@link FuncionarioWSDTO }
     * 
     */
    public FuncionarioWSDTO createFuncionarioWSDTO() {
        return new FuncionarioWSDTO();
    }

    /**
     * Create an instance of {@link DepartamentoWSDTO }
     * 
     */
    public DepartamentoWSDTO createDepartamentoWSDTO() {
        return new DepartamentoWSDTO();
    }

    /**
     * Create an instance of {@link AreaWSDTO }
     * 
     */
    public AreaWSDTO createAreaWSDTO() {
        return new AreaWSDTO();
    }

    /**
     * Create an instance of {@link DocumentoWSDTO }
     * 
     */
    public DocumentoWSDTO createDocumentoWSDTO() {
        return new DocumentoWSDTO();
    }

    /**
     * Create an instance of {@link ActividadWSDTO }
     * 
     */
    public ActividadWSDTO createActividadWSDTO() {
        return new ActividadWSDTO();
    }

    /**
     * Create an instance of {@link FuncionarioExternoWSDTO }
     * 
     */
    public FuncionarioExternoWSDTO createFuncionarioExternoWSDTO() {
        return new FuncionarioExternoWSDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NSJPNegocioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.service.nsjp.segob.gob.mx/", name = "NSJPNegocioException")
    public JAXBElement<NSJPNegocioException> createNSJPNegocioException(NSJPNegocioException value) {
        return new JAXBElement<NSJPNegocioException>(_NSJPNegocioException_QNAME, NSJPNegocioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecibirSolicitud }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.service.nsjp.segob.gob.mx/", name = "recibirSolicitud")
    public JAXBElement<RecibirSolicitud> createRecibirSolicitud(RecibirSolicitud value) {
        return new JAXBElement<RecibirSolicitud>(_RecibirSolicitud_QNAME, RecibirSolicitud.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecibirSolicitudResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.service.nsjp.segob.gob.mx/", name = "recibirSolicitudResponse")
    public JAXBElement<RecibirSolicitudResponse> createRecibirSolicitudResponse(RecibirSolicitudResponse value) {
        return new JAXBElement<RecibirSolicitudResponse>(_RecibirSolicitudResponse_QNAME, RecibirSolicitudResponse.class, null, value);
    }

}
