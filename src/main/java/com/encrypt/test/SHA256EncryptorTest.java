package com.encrypt.test;

import java.security.MessageDigest;

public class SHA256EncryptorTest {

	public static void main(String[] args) {
		try {
			String src = "Hello";
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] encryptBytes = md.digest(src.getBytes("UTF-8"));
			String result = bytesToHex(encryptBytes).toUpperCase();
			
			System.out.println(result);
			
			
			String src_1 = "HelloHelloHello";
			MessageDigest md_1 = MessageDigest.getInstance("SHA-256");
			byte[] encryptBytes_1 = md_1.digest(src_1.getBytes("UTF-8"));
			String result_1 = bytesToHex(encryptBytes_1).toUpperCase();
			
			System.out.println(result_1);
			
			System.out.println("result 1 equals result 2 : " + result.equals(result_1));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	private static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }

}
