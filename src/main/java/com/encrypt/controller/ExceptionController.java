package com.encrypt.controller;

import java.util.Collections;

import org.json.JSONException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler
	public Object runtimeException(RuntimeException e) {
		return Collections.singletonMap("status", "error");
	}

	@ExceptionHandler
	public Object jsonException(JSONException e) {
		return Collections.singletonMap("status", "JSON syntax error");
	}
	
}
