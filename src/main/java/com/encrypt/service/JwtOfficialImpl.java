package com.encrypt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtOfficialImpl implements com.encrypt.service.JWT {
	
	@Value("${secrect_key}")
	private String SECRECT_KEY;
	private Algorithm alg;
	
	@PostConstruct
	public void postConstruct() {
		this.alg = Algorithm.HMAC256(SECRECT_KEY);
	}
	
	@Override
	public String getJwtToken(Map<String,Object> payload) {
		Builder builder = JWT.create();
		builder.withIssuer(payload.get("iss").toString());
		builder.withExpiresAt(new Date((long) payload.get("exp")));
		
		return builder.sign(alg);
	}

	@Override
	public String getHeader(String jwtToken) {
		DecodedJWT jwt = JWT.require(alg).build().verify(jwtToken);
		return jwt.getHeader();
	}

	@Override
	public String getPayload(String jwtToken) {
		DecodedJWT jwt = JWT.require(alg).build().verify(jwtToken);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		jwt.getClaims().forEach((key,claim) -> {
			resultMap.put(key, claim.asString());
		});
		
		return (new JSONObject(resultMap)).toString();
	}

	@Override
	public boolean valid(String jwtToken) {
		try {
			JWT.require(alg).build().verify(jwtToken);
		} catch (JWTVerificationException e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}

}
