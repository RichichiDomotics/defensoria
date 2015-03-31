package mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2011-09-27T11:39:10.937-05:00
 * Generated source version: 2.4.1
 * 
 */
@WebServiceClient(name = "RecibirNotificacionAudienciaExporterImplService", 
                  wsdlLocation = "http://localhost:8080/nsjp-web/RecibirNotificacionAudienciaExporterImplService?wsdl",
                  targetNamespace = "http://impl.ws.service.nsjp.segob.gob.mx/") 
public class RecibirNotificacionAudienciaExporterImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "RecibirNotificacionAudienciaExporterImplService");
    public final static QName RecibirNotificacionAudienciaExporterImplPort = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "RecibirNotificacionAudienciaExporterImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/nsjp-web/RecibirNotificacionAudienciaExporterImplService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(RecibirNotificacionAudienciaExporterImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/nsjp-web/RecibirNotificacionAudienciaExporterImplService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public RecibirNotificacionAudienciaExporterImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public RecibirNotificacionAudienciaExporterImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RecibirNotificacionAudienciaExporterImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    /**
     *
     * @return
     *     returns RecibirNotificacionAudienciaExporter
     */
    @WebEndpoint(name = "RecibirNotificacionAudienciaExporterImplPort")
    public RecibirNotificacionAudienciaExporter getRecibirNotificacionAudienciaExporterImplPort() {
        return super.getPort(RecibirNotificacionAudienciaExporterImplPort, RecibirNotificacionAudienciaExporter.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RecibirNotificacionAudienciaExporter
     */
    @WebEndpoint(name = "RecibirNotificacionAudienciaExporterImplPort")
    public RecibirNotificacionAudienciaExporter getRecibirNotificacionAudienciaExporterImplPort(WebServiceFeature... features) {
        return super.getPort(RecibirNotificacionAudienciaExporterImplPort, RecibirNotificacionAudienciaExporter.class, features);
    }

}