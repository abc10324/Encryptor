package com.encrypt.service;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class AES {
	private static String HASH_KEY = "12345678901234567890123456789012";
	private static String HASH_IV = "1234567890123456";
	private static String ALGORITHM = "AES/CBC/NoPadding";
	
//	public static void main(String[] args) {
//		AES aes = new AES();
//		
//		System.out.println(aes.encrypt("你好阿"));
//		System.out.println(aes.decrypt(aes.encrypt("你好阿")));
//	}
	
	public String encrypt(String msg) {
		try {
			// specify IV
            IvParameterSpec iv = new IvParameterSpec(HASH_IV.getBytes());
			
			// specify KEY
            SecretKeySpec secretKey = new SecretKeySpec(HASH_KEY.getBytes(), "AES");
			
            // use self-defined padding method
            // java didn't provide pkcs7padding
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);    
            
            return bytesToHex(cipher.doFinal(addPkcs7padding(msg).getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public String decrypt(String encryptMsg) {
		try {
			// specify IV
            IvParameterSpec iv = new IvParameterSpec(HASH_IV.getBytes("UTF-8"));
			
			// specify KEY
            SecretKeySpec secretKey = new SecretKeySpec(HASH_KEY.getBytes("UTF-8"), "AES");
			
            // use self-defined padding method
            // java didn't provide pkcs7padding
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);    
            
            return removePkcs7padding(new String(cipher.doFinal(hexToBytes(encryptMsg)),"UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	private String addPkcs7padding(String data) throws UnsupportedEncodingException {
        int base = 16;
        int padding = base - (data.getBytes("UTF-8").length % base);
        
        StringBuilder sb = new StringBuilder(data);
        for (int i = 0; i < padding; i++) {
        	sb.append((char) padding);
        }

        return sb.toString();
    }
	
	public String removePkcs7padding(String data) {
		
		char[] cArr = data.toCharArray();
		char sub = cArr[cArr.length - 1];
		
		return data.substring(0, data.length() - (int) sub);
	}
	
	private String bytesToHex(byte[] byteArr) {
        StringBuilder sb = new StringBuilder();
        
        for (byte b : byteArr) {
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString();
    }
	
	private byte[] hexToBytes(String hexString) {
		byte[] bytes = new byte[hexString.length() / 2];
		
		for(int i=0 ; i<bytes.length ; i++)
			bytes[i] = (byte) Integer.parseInt(hexString.substring(2*i, 2*i+2),16);
		
		return bytes;
	}
}
