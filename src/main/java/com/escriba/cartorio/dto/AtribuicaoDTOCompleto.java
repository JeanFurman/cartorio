package com.escriba.cartorio.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AtribuicaoDTOCompleto {
	
	@Size(max=20)
	private String id;
	
	@NotBlank
	@Size(max = 50)
	private String nome;
	
	private boolean situacao = true;
	
}
