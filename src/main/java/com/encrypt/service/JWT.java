package com.encrypt.service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWT {
	@Value("${secrect_key}")
	private String SECRECT_KEY;
	private final static String ALGORITHM = "HmacSHA256";
	private final static String HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
	
//	public static void main(String[] args) {
//		JWT hs = new JWT();
//		
//		String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
//		String payload = "{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"iat\":1516239022}";
//		
//		String jwtToken = hs.getJwtToken(header, payload);
//		System.out.println(jwtToken);
//		System.out.println(jwtToken.length());
//		
//		System.out.println(hs.getHeader(jwtToken));
//		System.out.println(hs.getPayload(jwtToken));
//		System.out.println(hs.valid(jwtToken));
//	}
	
	public String getJwtToken(String payload) {
		String unsignedToken = getUnsignedToken(HEADER, payload);
		String signature = getSignature(unsignedToken);
		
		return unsignedToken + "." + signature;
	}
	
	private String getUnsignedToken(String header,String payload) {
		
		try {
			String encHeader = Base64.getUrlEncoder().encodeToString(header.getBytes("UTF-8")).replace("=", "");
			String encPayload = Base64.getUrlEncoder().encodeToString(payload.getBytes("UTF-8")).replace("=", "");
			return encHeader + "." + encPayload;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String getSignature(String unsignedToken) {
		try {
			Mac mac = Mac.getInstance(ALGORITHM);
			
			// specify KEY
            SecretKeySpec secretKey = new SecretKeySpec(SECRECT_KEY.getBytes("UTF-8"), ALGORITHM);
			
            mac.init(secretKey);
            
            return Base64.getUrlEncoder().encodeToString(mac.doFinal(unsignedToken.getBytes())).replace("=", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public String getHeader(String jwtToken) {
		if(jwtToken.split("\\.").length != 3)
			throw new RuntimeException();
		
		String header = jwtToken.substring(0, jwtToken.indexOf("."));
		
		return new String(Base64.getDecoder().decode(header));
	}
	
	public String getPayload(String jwtToken) {
		if(jwtToken.split("\\.").length != 3)
			throw new RuntimeException();
		
		String payload = jwtToken.substring(jwtToken.indexOf(".") + 1 ,jwtToken.lastIndexOf("."));
		
		return new String(Base64.getDecoder().decode(payload));
	}
	
	public boolean valid(String jwtToken) {
		if(jwtToken.split("\\.").length != 3)
			return false;
		
		if((new JSONObject(getPayload(jwtToken))).getLong("exp") < System.currentTimeMillis())
			return false;
		
		String unsignedToken = jwtToken.substring(0, jwtToken.lastIndexOf("."));
		String signature = jwtToken.substring(jwtToken.lastIndexOf(".") + 1);
		
		return signature.equals(getSignature(unsignedToken));
	}
	
}
