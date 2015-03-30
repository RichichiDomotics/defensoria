package mx.gob.segob.nsjp.dao.chat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;


public class StringMD {

    //algoritmos
    public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA1 = "SHA-1";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    public static String SHA512 = "SHA-512";
    private static final Logger logger = Logger.getLogger(StringMD.class);


    /***
     * Convierte un arreglo de bytes a String usando valores hexadecimales
     * @param digest arreglo de bytes a convertir
     * @return String creado a partir de <code>digest</code>
     */
    private static String toHexadecimal(byte[] digest){
        String hash = "";
        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    /***
     * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.
     * @param message texto a encriptar
     * @param algorithm algoritmo de encriptacion, puede ser: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
     * @return mensaje encriptado
     */
    public static String getStringMessageDigest(String message, String algorithm){
        byte[] digest = null;
        byte[] buffer = message.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            logger.debug("Error creando Digest");
        }
        return toHexadecimal(digest);
    } 
    
    public static void main(String[] args) {
    		        String mensaje = "Pa55w0rd";
    		        logger.debug("Mensaje = " + mensaje);
    		        logger.debug("MD2 = " + StringMD.getStringMessageDigest(mensaje, StringMD.MD2));
    		        logger.debug("MD5 = " + StringMD.getStringMessageDigest(mensaje, StringMD.MD5));
    		        logger.debug("SHA-1 = " + StringMD.getStringMessageDigest(mensaje, StringMD.SHA1));
    		        logger.debug("SHA-256 = " + StringMD.getStringMessageDigest(mensaje, StringMD.SHA256));
    		        logger.debug("SHA-384 = " + StringMD.getStringMessageDigest(mensaje, StringMD.SHA384));
    		        logger.debug("SHA-512 = " + StringMD.getStringMessageDigest(mensaje, StringMD.SHA512));
    		        String numero="1e79fab7354496bcaf8126a592f4413427076db693bbb154bc871eb5bd7467e5";
    		        String encript=StringMD.getStringMessageDigest("Pa55w0rd", StringMD.MD5);
    		        logger.debug("Encriptado:"+encript);
    		        logger.debug("Comparador:"+StringMD.toHexadecimal(encript.getBytes()));
    		        try {
//						String encriptado=BlowFish.encriptar("Pa55w0rd", "1lw5G2t4I97F02v");
//						logger.debug("BlowFish:"+encriptado);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    		    }
    
    
}
