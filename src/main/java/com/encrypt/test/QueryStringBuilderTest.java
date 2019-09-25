package com.encrypt.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.util.StringUtils;

import com.encrypt.service.AES;

public class QueryStringBuilderTest {

	private StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) {
		QueryStringBuilderTest queryStringBuilder = new QueryStringBuilderTest();
		queryStringBuilder.add("鍵1", "");
		System.out.println(queryStringBuilder.toString());
		queryStringBuilder.add("key2", " 值2");
		System.out.println(queryStringBuilder.toString());
		queryStringBuilder.add("key3", "value3");
		System.out.println(queryStringBuilder.toString());
		
		AES aes = new AES();
		String result = aes.encrypt(queryStringBuilder.toString());
		System.out.println("After encode : " + result);
		System.out.println("After decode : " + aes.decrypt(result));
		try {
			System.out.println("After URL decode : " + URLDecoder.decode(aes.decrypt(result), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}

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
