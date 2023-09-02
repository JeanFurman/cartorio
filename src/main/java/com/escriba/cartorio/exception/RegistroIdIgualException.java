package com.escriba.cartorio.exception;

public class RegistroIdIgualException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RegistroIdIgualException() {
		super("Registro já cadastrado");
	}
	
}