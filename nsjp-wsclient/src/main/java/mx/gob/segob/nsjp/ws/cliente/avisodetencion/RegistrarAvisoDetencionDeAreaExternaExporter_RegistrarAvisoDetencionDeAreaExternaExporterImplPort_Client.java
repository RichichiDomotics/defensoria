
package mx.gob.segob.nsjp.ws.cliente.avisodetencion;

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
 * 2012-01-16T19:57:36.100-06:00
 * Generated source version: 2.4.2
 * 
 */
public final class RegistrarAvisoDetencionDeAreaExternaExporter_RegistrarAvisoDetencionDeAreaExternaExporterImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "RegistrarAvisoDetencionDeAreaExternaExporterImplService");

    private RegistrarAvisoDetencionDeAreaExternaExporter_RegistrarAvisoDetencionDeAreaExternaExporterImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RegistrarAvisoDetencionDeAreaExternaExporterImplService.WSDL_LOCATION;
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
      
        RegistrarAvisoDetencionDeAreaExternaExporterImplService ss = new RegistrarAvisoDetencionDeAreaExternaExporterImplService(wsdlURL, SERVICE_NAME);
        RegistrarAvisoDetencionDeAreaExternaExporter port = ss.getRegistrarAvisoDetencionDeAreaExternaExporterImplPort();  
        
        {
        System.out.println("Invoking registrarAvisoDetencion...");
        mx.gob.segob.nsjp.ws.cliente.avisodetencion.AvisoDetencionWSDTO _registrarAvisoDetencion_arg0 = null;
        java.lang.Long _registrarAvisoDetencion_arg1 = null;
        java.lang.String _registrarAvisoDetencion_arg2 = "";
        java.lang.Long _registrarAvisoDetencion_arg3 = null;
        try {
            mx.gob.segob.nsjp.ws.cliente.avisodetencion.AvisoDetencionWSDTO _registrarAvisoDetencion__return = port.registrarAvisoDetencion(_registrarAvisoDetencion_arg0, _registrarAvisoDetencion_arg1, _registrarAvisoDetencion_arg2, _registrarAvisoDetencion_arg3);
            System.out.println("registrarAvisoDetencion.result=" + _registrarAvisoDetencion__return);

        } catch (NSJPNegocioException_Exception e) { 
            System.out.println("Expected exception: NSJPNegocioException has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}