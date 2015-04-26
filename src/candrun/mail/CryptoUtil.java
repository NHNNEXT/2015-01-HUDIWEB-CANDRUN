package candrun.mail;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
 
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptoUtil {

    private static String key() {
     
     return "cadrun_141021"; 
    }
    
    private static Key getKey() throws Exception {
     
     return (key().length() == 24) ? getKey2(key()) : getKey1(key());     
    }
    
    private static String getInstance() throws Exception {
     
     return (key().length() == 24) ? "DESede/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding";
    }
    
    private static Key getKey1(String keyValue) throws Exception {
     
     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
     DESKeySpec desKeySpec = new DESKeySpec( keyValue.getBytes() );
     
     return keyFactory.generateSecret( desKeySpec );
    }
    
    private static Key getKey2(String keyValue) throws Exception {
     
     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
     DESKeySpec desKeySpec = new DESKeySpec( keyValue.getBytes() );
     
     return keyFactory.generateSecret( desKeySpec );
    }
    
    public static String encrypt(String input) throws Exception {
     
     if(input == null || input.length() == 0) return "";
     
     Cipher cipher = Cipher.getInstance( getInstance() );
        cipher.init( Cipher.ENCRYPT_MODE, getKey() );
        
        byte [] inputBytes = input.getBytes("UTF8");
        byte [] outputBytes = cipher.doFinal( inputBytes );


        return new BASE64Encoder().encode( outputBytes );
    }
    

    public static String decrypt(String input) throws Exception {
        
     if(input == null || input.length() == 0) return "";
     
     Cipher cipher = Cipher.getInstance( getInstance() );
        cipher.init( Cipher.DECRYPT_MODE, getKey() );
     
     byte [] inputBytes = new BASE64Decoder().decodeBuffer( input );
     byte [] outputBytes = cipher.doFinal( inputBytes );
        
        return new String( outputBytes, "UTF8" );
    }
 
}