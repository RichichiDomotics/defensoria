
package mx.gob.segob.nsjp.ws.cliente.agendaraudienciajavs;

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
 * 2012-06-08T17:04:00.823-05:00
 * Generated source version: 2.4.1
 * 
 */
public final class PrincipalSoap_PrincipalSoap12_Client {

    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "Principal");

    private PrincipalSoap_PrincipalSoap12_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = Principal.WSDL_LOCATION;
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
      
        Principal ss = new Principal(wsdlURL, SERVICE_NAME);
        PrincipalSoap port = ss.getPrincipalSoap12();  
        
        {
        System.out.println("Invoking agendarAudiencia...");
        java.lang.String _agendarAudiencia_sAudiencia = "";
        java.lang.String _agendarAudiencia_sUsuario = "";
        int _agendarAudiencia__return = port.agendarAudiencia(_agendarAudiencia_sAudiencia, _agendarAudiencia_sUsuario);
        System.out.println("agendarAudiencia.result=" + _agendarAudiencia__return);


        }
        {
        System.out.println("Invoking estadoAudiencia...");
        java.lang.String _estadoAudiencia_sAudiencia = "";
        java.lang.String _estadoAudiencia_sUsuario = "";
        int _estadoAudiencia__return = port.estadoAudiencia(_estadoAudiencia_sAudiencia, _estadoAudiencia_sUsuario);
        System.out.println("estadoAudiencia.result=" + _estadoAudiencia__return);


        }
        {
        System.out.println("Invoking ligasPath...");
        java.lang.String _ligasPath_sAudiencia = "";
        java.lang.String _ligasPath_sUsuario = "";
        java.lang.String _ligasPath__return = port.ligasPath(_ligasPath_sAudiencia, _ligasPath_sUsuario);
        System.out.println("ligasPath.result=" + _ligasPath__return);


        }
        {
        System.out.println("Invoking generandoJVL...");
        java.lang.String _generandoJVL_sAudiencia = "";
        java.lang.String _generandoJVL_sUsuario = "";
        byte[] _generandoJVL__return = port.generandoJVL(_generandoJVL_sAudiencia, _generandoJVL_sUsuario);
        System.out.println("generandoJVL.result=" + _generandoJVL__return);


        }
        {
        System.out.println("Invoking consultarAudiencia...");
        java.lang.String _consultarAudiencia_sAudiencia = "";
        java.lang.String _consultarAudiencia_sUsuario = "";
        int _consultarAudiencia__return = port.consultarAudiencia(_consultarAudiencia_sAudiencia, _consultarAudiencia_sUsuario);
        System.out.println("consultarAudiencia.result=" + _consultarAudiencia__return);


        }
        {
        System.out.println("Invoking eliminarAudiencia...");
        java.lang.String _eliminarAudiencia_sAudiencia = "";
        java.lang.String _eliminarAudiencia_sUsuario = "";
        int _eliminarAudiencia__return = port.eliminarAudiencia(_eliminarAudiencia_sAudiencia, _eliminarAudiencia_sUsuario);
        System.out.println("eliminarAudiencia.result=" + _eliminarAudiencia__return);


        }
        {
        System.out.println("Invoking hayAudiencia...");
        java.lang.String _hayAudiencia_sAudiencia = "";
        java.lang.String _hayAudiencia_sUsuario = "";
        int _hayAudiencia__return = port.hayAudiencia(_hayAudiencia_sAudiencia, _hayAudiencia_sUsuario);
        System.out.println("hayAudiencia.result=" + _hayAudiencia__return);


        }
        {
        System.out.println("Invoking generandoJVLCadena...");
        java.lang.String _generandoJVLCadena_sAudiencia = "";
        java.lang.String _generandoJVLCadena_sUsuario = "";
        java.lang.String _generandoJVLCadena__return = port.generandoJVLCadena(_generandoJVLCadena_sAudiencia, _generandoJVLCadena_sUsuario);
        System.out.println("generandoJVLCadena.result=" + _generandoJVLCadena__return);


        }
        {
        System.out.println("Invoking generandoJVLPaths...");
        java.lang.String _generandoJVLPaths_sAudiencia = "";
        java.lang.String _generandoJVLPaths_sUsuario = "";
        java.lang.String _generandoJVLPaths__return = port.generandoJVLPaths(_generandoJVLPaths_sAudiencia, _generandoJVLPaths_sUsuario);
        System.out.println("generandoJVLPaths.result=" + _generandoJVLPaths__return);


        }

        System.exit(0);
    }

}
