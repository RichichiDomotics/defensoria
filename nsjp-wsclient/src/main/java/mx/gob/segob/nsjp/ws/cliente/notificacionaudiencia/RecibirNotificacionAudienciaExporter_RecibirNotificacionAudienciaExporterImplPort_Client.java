
package mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2011-09-27T11:39:10.812-05:00
 * Generated source version: 2.4.1
 * 
 */
public final class RecibirNotificacionAudienciaExporter_RecibirNotificacionAudienciaExporterImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "RecibirNotificacionAudienciaExporterImplService");

    private RecibirNotificacionAudienciaExporter_RecibirNotificacionAudienciaExporterImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RecibirNotificacionAudienciaExporterImplService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        RecibirNotificacionAudienciaExporterImplService ss = new RecibirNotificacionAudienciaExporterImplService(wsdlURL, SERVICE_NAME);
        RecibirNotificacionAudienciaExporter port = ss.getRecibirNotificacionAudienciaExporterImplPort();  
        
        {
        System.out.println("Invoking recibirNotificacionAudiencia...");
        mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.SolicitudAudienciaBasicoWSDTO _recibirNotificacionAudiencia_arg0 = null;
        try {
            java.lang.Boolean _recibirNotificacionAudiencia__return = port.recibirNotificacionAudiencia(_recibirNotificacionAudiencia_arg0);
            System.out.println("recibirNotificacionAudiencia.result=" + _recibirNotificacionAudiencia__return);

        } catch (NSJPNegocioException_Exception e) { 
            System.out.println("Expected exception: NSJPNegocioException has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
