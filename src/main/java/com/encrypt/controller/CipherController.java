package com.encrypt.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encrypt.service.AES;

@RestController
public class CipherController {

	@Autowired
	private AES aes;
	
	@PostMapping("/encrypt")
	public Object encrypt(@RequestParam String encryptSrc,
						  @RequestParam String algorithm,
						  @RequestBody String body) {
		if(!"AES256".equals(algorithm.toUpperCase()))
			return Collections.singletonMap("status", "No such algorithm");
			
		Map<String,Object> resultMap = new HashMap<>();
		
		resultMap.put("algorithm", "AES256");
		resultMap.put("encrypt_result", aes.encrypt(encryptSrc));
		
		return resultMap;
	}
	
	@PostMapping("/decrypt")
	public Object decrypt(@RequestParam String decryptSrc,
			  			  @RequestParam String algorithm) {
		if(!"AES256".equals(algorithm.toUpperCase()))
			return Collections.singletonMap("status", "No such algorithm");
			
		Map<String,Object> resultMap = new HashMap<>();
		
		resultMap.put("algorithm", "AES256");
		resultMap.put("decrypt_result", aes.decrypt(decryptSrc));
		
		return resultMap;
	}
	
}