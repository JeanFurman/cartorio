package com.escriba.cartorio.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.escriba.cartorio.model.Atribuicao;
import com.escriba.cartorio.model.Situacao;

import lombok.Data;

@Data
public class CartorioDTOCompleto {
	
	@Positive
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String nome;

    @Size(max = 250)
    private String observacao;

    @NotNull
    private Situacao situacao;

    @NotEmpty
    private List<Atribuicao> atribuicoes;

}
