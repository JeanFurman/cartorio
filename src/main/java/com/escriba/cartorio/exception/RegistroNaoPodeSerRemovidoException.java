package com.escriba.cartorio.exception;

public class RegistroNaoPodeSerRemovidoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RegistroNaoPodeSerRemovidoException() {
		super("Registro utilizado em outro cadastro");
	}
	
}