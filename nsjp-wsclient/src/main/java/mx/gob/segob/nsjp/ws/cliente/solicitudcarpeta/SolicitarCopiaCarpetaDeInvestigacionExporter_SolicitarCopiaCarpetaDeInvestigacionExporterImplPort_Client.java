
package mx.gob.segob.nsjp.ws.cliente.solicitudcarpeta;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2012-01-16T19:57:30.318-06:00
 * Generated source version: 2.4.2
 * 
 */
public final class SolicitarCopiaCarpetaDeInvestigacionExporter_SolicitarCopiaCarpetaDeInvestigacionExporterImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "SolicitarCopiaCarpetaDeInvestigacionExporterImplService");

    private SolicitarCopiaCarpetaDeInvestigacionExporter_SolicitarCopiaCarpetaDeInvestigacionExporterImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SolicitarCopiaCarpetaDeInvestigacionExporterImplService.WSDL_LOCATION;
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
      
        SolicitarCopiaCarpetaDeInvestigacionExporterImplService ss = new SolicitarCopiaCarpetaDeInvestigacionExporterImplService(wsdlURL, SERVICE_NAME);
        SolicitarCopiaCarpetaDeInvestigacionExporter port = ss.getSolicitarCopiaCarpetaDeInvestigacionExporterImplPort();  
        
        {
        System.out.println("Invoking solicitarCopiaCarpetaDeInvestigacion...");
        mx.gob.segob.nsjp.ws.cliente.solicitudcarpeta.SolicitudAudienciaBasicoWSDTO _solicitarCopiaCarpetaDeInvestigacion_arg0 = null;
        java.lang.Long _solicitarCopiaCarpetaDeInvestigacion_arg1 = null;
        try {
            mx.gob.segob.nsjp.ws.cliente.solicitudcarpeta.SolicitudAudienciaBasicoWSDTO _solicitarCopiaCarpetaDeInvestigacion__return = port.solicitarCopiaCarpetaDeInvestigacion(_solicitarCopiaCarpetaDeInvestigacion_arg0, _solicitarCopiaCarpetaDeInvestigacion_arg1);
            System.out.println("solicitarCopiaCarpetaDeInvestigacion.result=" + _solicitarCopiaCarpetaDeInvestigacion__return);

        } catch (NSJPNegocioException_Exception e) { 
            System.out.println("Expected exception: NSJPNegocioException has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
