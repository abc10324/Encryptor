package com.encrypt.test;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.encrypt.service.AES;

public class AES256EncryptorTest {
	
	private static String HASH_KEY = "12345678901234567890123456789012";
	private static String HASH_IV = "1234567890123456";
	
	public static void main(String[] args) {
		try {
			String msg = "HelloHello";
			
			// specify IV
            IvParameterSpec iv = new IvParameterSpec(HASH_IV.getBytes("UTF-8"));
			
			// specify KEY
            SecretKeySpec secretKey = new SecretKeySpec(HASH_KEY.getBytes("UTF-8"), "AES");
			
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);    
            System.out.println("AES_CBC_PKCS5PADDING IV:" + cipher.getIV());
            System.out.println("AES_CBC_PKCS5PADDING Algoritm:" + cipher.getAlgorithm());
            
            byte[] byteCipherText = cipher.doFinal(addPkcs7padding(msg).getBytes("UTF-8"));
//            byte[] byteCipherText = cipher.doFinal(msg.getBytes("UTF-8"));
            System.out.println("加密前 : " + msg);
            System.out.println("加密結果 : " + new String(byteCipherText));
            String src = Base64.getEncoder().encodeToString(byteCipherText);
            System.out.println("加密結果的Base64編碼：" + src);
            System.out.println("加密結果的Hex編碼：" + bytesToHex(byteCipherText));
            System.out.println("加密結果的Hex解碼 : " + new String(hexToBytes(bytesToHex(byteCipherText))));
            System.out.println(new String(hexToBytes(bytesToHex(byteCipherText))).equals(new String(byteCipherText)));
//            System.out.println("加密結果的Hex編碼：" + bytesToHex_1(byteCipherText));
            
            Cipher cipher2 = Cipher.getInstance("AES/CBC/NoPadding");
//            Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher2.init(Cipher.DECRYPT_MODE, secretKey, iv);  
            
//            System.out.println("解密來源：" + new String(Base64.getDecoder().decode(src)));
            System.out.println("解密來源：" + bytesToHex(byteCipherText));
            byte[] decryptByte = cipher2.doFinal(hexToBytes(bytesToHex(byteCipherText)));
            System.out.println("解密後 : " + new String(decryptByte));
            System.out.println("解密後 : " + removePkcs7padding(new String(decryptByte)));
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String addPkcs7padding(String data) {
        int bs = 16;
        int padding = bs - (data.length() % bs);
        System.out.printf("padding = %s\n", padding);
        
        String padding_text = "";
        for (int i = 0; i < padding; i++) {
            padding_text += (char)padding;
        }
        
        System.out.printf("after padding = %s\n", data + padding_text);
        return data + padding_text;
    }
	
	public static String removePkcs7padding(String data) {
		
		char[] cArr = data.toCharArray();
		char sub = cArr[cArr.length - 1];
		
		return data.substring(0, data.length() - (int) sub);
	}
	
	private static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }
	
	public static String bytesToHex_1(byte[] bytes) {  
	    StringBuffer sb = new StringBuffer();  
	    for(int i = 0; i < bytes.length; i++) {  
	        String hex = Integer.toHexString(bytes[i] & 0xFF);  
	        if(hex.length() < 2){  
	            sb.append(0);  
	        }  
	        sb.append(hex);  
	    }  
	    return sb.toString();  
	}
	
	public static byte[] hexToBytes(String hexString) {
		byte[] bytes = new byte[hexString.length() / 2];
		
		for(int i=0 ; i<bytes.length ; i++)
			bytes[i] = (byte) Integer.parseInt(hexString.substring(2*i, 2*i+2),16);
		
		return bytes;
	}

}
