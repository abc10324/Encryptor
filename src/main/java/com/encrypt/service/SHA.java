package com.encrypt.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class SHA {

	public String encrypt(String msg) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] encryptBytes = md.digest(msg.getBytes("UTF-8"));
			return bytesToHex(encryptBytes).toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	private String bytesToHex(byte[] byteArr) {
        StringBuilder sb = new StringBuilder();
        
        for (byte b : byteArr) {
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString();
    }
	
}
