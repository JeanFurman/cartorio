package com.escriba.cartorio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.escriba.cartorio.exception.RegistroIdIgualException;
import com.escriba.cartorio.exception.RegistroNomeIgualException;
import com.escriba.cartorio.exception.RegistroNotFoundException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(RegistroNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleRegistroNotFoundException(RegistroNotFoundException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleIllegalArgumentException(IllegalArgumentException e) {
		return "Valor inválido";
	}
	
	@ExceptionHandler(RegistroIdIgualException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleRegistroIdIgualException(RegistroIdIgualException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(RegistroNomeIgualException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleRegistroNomeIgualException(RegistroNomeIgualException e) {
		return e.getMessage();
	}

}