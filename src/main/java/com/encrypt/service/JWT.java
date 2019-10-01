package com.encrypt.service;

import java.util.Map;

public interface JWT {
	public String getJwtToken(Map<String,Object> payload);
	public String getHeader(String jwtToken);
	public String getPayload(String jwtToken);
	public boolean valid(String jwtToken);
}
