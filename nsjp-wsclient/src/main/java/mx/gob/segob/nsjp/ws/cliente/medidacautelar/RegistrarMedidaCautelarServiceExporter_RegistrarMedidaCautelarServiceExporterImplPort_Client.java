
package mx.gob.segob.nsjp.ws.cliente.medidacautelar;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
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
 * 2012-06-30T02:47:50.978-05:00
 * Generated source version: 2.4.1
 * 
 */
public final class RegistrarMedidaCautelarServiceExporter_RegistrarMedidaCautelarServiceExporterImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "RegistrarMedidaCautelarServiceExporterImplService");

    private RegistrarMedidaCautelarServiceExporter_RegistrarMedidaCautelarServiceExporterImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RegistrarMedidaCautelarServiceExporterImplService.WSDL_LOCATION;
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
      
        RegistrarMedidaCautelarServiceExporterImplService ss = new RegistrarMedidaCautelarServiceExporterImplService(wsdlURL, SERVICE_NAME);
        RegistrarMedidaCautelarServiceExporter port = ss.getRegistrarMedidaCautelarServiceExporterImplPort();  
        
        {
        System.out.println("Invoking actualizarEstatusMedidaCautelar...");
        mx.gob.segob.nsjp.ws.cliente.medidacautelar.MedidaCautelarWSDTO _actualizarEstatusMedidaCautelar_arg0 = null;
        try {
            java.lang.Boolean _actualizarEstatusMedidaCautelar__return = port.actualizarEstatusMedidaCautelar(_actualizarEstatusMedidaCautelar_arg0);
            System.out.println("actualizarEstatusMedidaCautelar.result=" + _actualizarEstatusMedidaCautelar__return);

        } catch (NSJPNegocioException_Exception e) { 
            System.out.println("Expected exception: NSJPNegocioException has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking registrarMedidaCautelar...");
        mx.gob.segob.nsjp.ws.cliente.medidacautelar.MedidaCautelarWSDTO _registrarMedidaCautelar_arg0 = null;
        try {
            port.registrarMedidaCautelar(_registrarMedidaCautelar_arg0);

        } catch (NSJPNegocioException_Exception e) { 
            System.out.println("Expected exception: NSJPNegocioException has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
