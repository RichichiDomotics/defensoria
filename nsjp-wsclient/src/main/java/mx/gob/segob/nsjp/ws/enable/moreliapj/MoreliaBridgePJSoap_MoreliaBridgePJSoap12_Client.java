
package mx.gob.segob.nsjp.ws.enable.moreliapj;

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
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.0
 * 2013-11-19T11:56:32.619-06:00
 * Generated source version: 2.7.0
 * 
 */
public final class MoreliaBridgePJSoap_MoreliaBridgePJSoap12_Client {

    private static final QName SERVICE_NAME = new QName("http://moreliapj.enable.ws.nsjp.segob.gob.mx/", "MoreliaBridgePJ");

    private MoreliaBridgePJSoap_MoreliaBridgePJSoap12_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = MoreliaBridgePJ.WSDL_LOCATION;
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
      
        MoreliaBridgePJ ss = new MoreliaBridgePJ(wsdlURL, SERVICE_NAME);
        MoreliaBridgePJSoap port = ss.getMoreliaBridgePJSoap12();  
        
        {
        System.out.println("Invoking solicitarAudiencia...");
        java.lang.String _solicitarAudiencia_xmlSolicitud = "_solicitarAudiencia_xmlSolicitud-1187746414";
        java.lang.String _solicitarAudiencia__return = port.solicitarAudiencia(_solicitarAudiencia_xmlSolicitud);
        System.out.println("solicitarAudiencia.result=" + _solicitarAudiencia__return);


        }

        System.exit(0);
    }

}
