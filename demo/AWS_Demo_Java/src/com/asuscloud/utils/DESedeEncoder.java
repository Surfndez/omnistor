package com.asuscloud.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.DatatypeConverter;

public class DESedeEncoder {
	
    private static final String charSet = "utf-8";
 
    public static final String encryptThreeDESECB(String src, String key) {
    	String encstring = null;
    	try{
    		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(charSet));
    		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
    		SecretKey securekey = keyFactory.generateSecret(dks);
    		
    		Cipher cipher = Cipher.getInstance("DESede");
    		cipher.init(Cipher.ENCRYPT_MODE, securekey);
    		byte[] b = cipher.doFinal(src.getBytes());
    		encstring = DatatypeConverter.printBase64Binary(b);
    	}catch ( UnsupportedEncodingException e ){
    		
    	}catch ( InvalidKeyException e )
		{
			e.printStackTrace();
		}
		catch ( InvalidKeySpecException e )
		{
			e.printStackTrace();
		}
		catch ( NoSuchAlgorithmException e )
		{
			e.printStackTrace();
		}
		catch ( NoSuchPaddingException e )
		{
			e.printStackTrace();
		}
		catch ( IllegalBlockSizeException e )
		{
			e.printStackTrace();
		}
		catch ( BadPaddingException e )
		{
			e.printStackTrace();
		}
		return encstring;
    }

    public static final String decryptThreeDESECB(String src, String key) throws Exception {
    	String decstring = null;
    	try{
            byte[] bytesrc = DatatypeConverter.parseBase64Binary(src);
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(charSet));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            byte[] retByte = cipher.doFinal(bytesrc);
            decstring =  new String(retByte);
    	}catch ( UnsupportedEncodingException e ){
    		
    	}
		catch ( InvalidKeyException e )
		{
			e.printStackTrace();
		}
		catch ( InvalidKeySpecException e )
		{
			e.printStackTrace();
		}
		catch ( NoSuchAlgorithmException e )
		{
			e.printStackTrace();
		}
		catch ( NoSuchPaddingException e )
		{
			e.printStackTrace();
		}
		catch ( IllegalBlockSizeException e )
		{
			e.printStackTrace();
		}
		catch ( BadPaddingException e )
		{
			e.printStackTrace();
		}
		return decstring;
    }
}