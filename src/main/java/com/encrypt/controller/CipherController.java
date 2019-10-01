package com.encrypt.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encrypt.service.AES;
import com.encrypt.service.JWT;
import com.encrypt.service.SHA;

@RestController
public class CipherController {

	@Autowired
	private AES aes;
	@Autowired
	private SHA sha;
	@Autowired
	@Qualifier("jwtOfficialImpl")
	private JWT jwt;
	
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
	
	@PostMapping("/Login")
	public Object getJwtToken(@RequestParam String user,@RequestParam String password) {
			
		if(StringUtils.hasText(user) && StringUtils.hasText(password)) {
			Map<String,Object> reqMap = new HashMap<String, Object>();
			reqMap.put("iss", user);
			
			// 10 minutes expiration
			reqMap.put("exp", System.currentTimeMillis() + 1000 * 60 * 10);
			
			String token = jwt.getJwtToken(reqMap);
			
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("jwt_token", token);
			resultMap.put("exp", reqMap.get("exp"));
			
			return resultMap;
		} else {
			return Collections.singletonMap("status", "login fail");
		}
	}
	
	@GetMapping("/Access")
	public Object accessByJwtToken(@RequestParam("jwt_token") String jwtToken) {
		if(jwt.valid(jwtToken)) {
			String user = (new JSONObject(jwt.getPayload(jwtToken))).getString("iss");
			System.out.println(jwt.getHeader(jwtToken));
			return Collections.singletonMap("result", "access success by user '" + user + "'");
		}
		
		return Collections.singletonMap("status", "access fail");
	}
	
}
