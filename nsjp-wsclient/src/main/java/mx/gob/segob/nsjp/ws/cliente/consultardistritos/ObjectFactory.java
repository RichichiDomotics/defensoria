
package mx.gob.segob.nsjp.ws.cliente.consultardistritos;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.segob.nsjp.ws.cliente.consultardistritos package. 
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

    private final static QName _ConsultarDistritos_QNAME = new QName("http://ws.service.nsjp.segob.gob.mx/", "consultarDistritos");
    private final static QName _ConsultarDistritosResponse_QNAME = new QName("http://ws.service.nsjp.segob.gob.mx/", "consultarDistritosResponse");
    private final static QName _NSJPNegocioException_QNAME = new QName("http://ws.service.nsjp.segob.gob.mx/", "NSJPNegocioException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.segob.nsjp.ws.cliente.consultardistritos
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultarDistritos }
     * 
     */
    public ConsultarDistritos createConsultarDistritos() {
        return new ConsultarDistritos();
    }

    /**
     * Create an instance of {@link ConsultarDistritosResponse }
     * 
     */
    public ConsultarDistritosResponse createConsultarDistritosResponse() {
        return new ConsultarDistritosResponse();
    }

    /**
     * Create an instance of {@link NSJPNegocioException }
     * 
     */
    public NSJPNegocioException createNSJPNegocioException() {
        return new NSJPNegocioException();
    }

    /**
     * Create an instance of {@link CatDistritoWSDTO }
     * 
     */
    public CatDistritoWSDTO createCatDistritoWSDTO() {
        return new CatDistritoWSDTO();
    }

    /**
     * Create an instance of {@link GenericWSDTO }
     * 
     */
    public GenericWSDTO createGenericWSDTO() {
        return new GenericWSDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarDistritos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.nsjp.segob.gob.mx/", name = "consultarDistritos")
    public JAXBElement<ConsultarDistritos> createConsultarDistritos(ConsultarDistritos value) {
        return new JAXBElement<ConsultarDistritos>(_ConsultarDistritos_QNAME, ConsultarDistritos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarDistritosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.nsjp.segob.gob.mx/", name = "consultarDistritosResponse")
    public JAXBElement<ConsultarDistritosResponse> createConsultarDistritosResponse(ConsultarDistritosResponse value) {
        return new JAXBElement<ConsultarDistritosResponse>(_ConsultarDistritosResponse_QNAME, ConsultarDistritosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NSJPNegocioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.nsjp.segob.gob.mx/", name = "NSJPNegocioException")
    public JAXBElement<NSJPNegocioException> createNSJPNegocioException(NSJPNegocioException value) {
        return new JAXBElement<NSJPNegocioException>(_NSJPNegocioException_QNAME, NSJPNegocioException.class, null, value);
    }

}
