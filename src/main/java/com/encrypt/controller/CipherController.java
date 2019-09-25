package com.encrypt.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encrypt.service.AES;
import com.encrypt.service.SHA;

@RestController
public class CipherController {

	@Autowired
	private AES aes;
	@Autowired
	private SHA sha;
	
	@PostMapping("/encrypt")
	public Object encrypt(@RequestParam String encryptSrc,
						  @RequestParam String algorithm,
						  @RequestBody String body) {
		if(!"AES256".equals(algorithm.toUpperCase()) &&
		   !"SHA256".equals(algorithm.toUpperCase()))
			return Collections.singletonMap("status", "No such algorithm, only offer AES256 and SHA256");
			
		Map<String,Object> resultMap = new HashMap<>();
		
		resultMap.put("algorithm", algorithm.toUpperCase());
		
		if("AES256".equals(algorithm.toUpperCase()))
			resultMap.put("encrypt_result", aes.encrypt(encryptSrc));
		if("SHA256".equals(algorithm.toUpperCase()))
			resultMap.put("encrypt_result", sha.encrypt(encryptSrc));
		
		return resultMap;
	}
	
	@PostMapping("/decrypt")
	public Object decrypt(@RequestParam String decryptSrc,
			  			  @RequestParam String algorithm) {
		if(!"AES256".equals(algorithm.toUpperCase()))
			return Collections.singletonMap("status", "No such algorithm");
			
		Map<String,Object> resultMap = new HashMap<>();
		
		resultMap.put("algorithm", algorithm.toUpperCase());
		resultMap.put("decrypt_result", aes.decrypt(decryptSrc));
		
		return resultMap;
	}
	
}
