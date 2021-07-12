package codegurus.cmm.service;


import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cryptography.EgovCryptoService;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;
import egovframework.rte.fdl.cryptography.impl.ARIACipher;


public class UtiltiyCrypto {

	private static final Logger LOGGER = LoggerFactory.getLogger(UtiltiyCrypto.class);

	/** 암호화서비스 */
	@Resource(name = "egovEnvCryptoService")
	EgovEnvCryptoService cryptoService;
	
	@Resource(name="ARIACryptoService")
    EgovCryptoService ariaCryptoService;

    @Resource(name = "passwordEncoder")
    private EgovPasswordEncoder passwordEncoder;

    public static String plainPassword = "egovframe";   
    //EgovProperties.getProperty("crypto.plain.password");
    public static String passwdAlgorithm = EgovProperties.getProperty("crypto.password.algorithm");
    
	/**
     * 암호화
     *
     * @param encrypt
     */
    public String encrypt(String encrypt) {

    	try {
			//return cryptoService.encrypt(encrypt); // Handles URLEncoding.
			return cryptoService.encryptNone(encrypt); // Does not handle URLEncoding.
        } catch(IllegalArgumentException e) {
    		LOGGER.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
        } catch (Exception e) {
        	LOGGER.error("[" + e.getClass() +"] :" + e.getMessage());
        }
		return encrypt;
    }
    
    /**
     * 복호화
     *
     * @param decrypt
     */
    public String decrypt(String decrypt){

    	try {
    		//return cryptoService.decrypt(decrypt); // Handles URLDecoding.
    		return cryptoService.decryptNone(decrypt); // Does not handle URLDecoding.
        } catch(IllegalArgumentException e) {
    		LOGGER.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
        } catch (Exception e) {
        	LOGGER.error("[" + e.getClass() +"] :" + e.getMessage());
        }
		return decrypt;
    }


    public String ariaEncrypt(String plainText) {

            String encodeText = null;

            try {
               byte[] encrypted = ariaCryptoService.encrypt(plainText.getBytes("UTF-8"), plainPassword);
               encodeText = Base64.encodeBase64String(encrypted);
            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
            }
            
            return encodeText;

    }

    public String ariaDecrypt(String encodeText) {

            String plainText = null;

            try {
                   byte[] base64dec = Base64.decodeBase64(encodeText);
                   byte[] decrypted = ariaCryptoService.decrypt(base64dec, plainPassword);

                   plainText = new String(decrypted, "UTF-8");

            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
            }

            return plainText;
    }
    
    public String ariaDirectEncrypt(String str) {
    	
    	try {
	    
    		ARIACipher cipher = new ARIACipher();
			cipher.setPassword("sadf");
			
			str = Base64.encodeBase64String(cipher.encrypt(str.getBytes("UTF-8")));
    	
    	} catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

    	return str;
    	
    }
    
    public String ariaDirectDecrypt(String str) {
    	
    	 try {
    		 
    		 ARIACipher cipher = new ARIACipher();
 			 cipher.setPassword("sadf");
 			
             byte[] base64dec = Base64.decodeBase64(str.getBytes("UTF-8"));
             byte[] decrypted = cipher.decrypt(base64dec);

             str = new String(decrypted, "UTF-8");
	
	     } catch (UnsupportedEncodingException uee) {
	         uee.printStackTrace();
	     }
    	
    	return str;
    	
    }
    
}