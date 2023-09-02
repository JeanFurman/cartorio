package com.escriba.cartorio.exception;

public class RegistroNomeIgualException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RegistroNomeIgualException(String id) {
		super("Nome já informado no registro com código " + id );
	}
	
}