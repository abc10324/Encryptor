package com.encrypt.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class QueryStringBuilder {
	private StringBuilder sb = new StringBuilder();
	
	public void add(String key ,String value) {
		try {
			if(StringUtils.hasText(key)) {
				sb.append(URLEncoder.encode(key.trim(), "UTF-8"));
				sb.append("=");
				sb.append(URLEncoder.encode(value.trim(), "UTF-8"));
				sb.append("&");
			} else {
				throw new RuntimeException();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Override
	public String toString() {
		return sb.substring(0, sb.length() - 1);
	}
}
