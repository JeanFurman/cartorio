package com.escriba.cartorio.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.escriba.cartorio.model.Atribuicao;

import lombok.Data;

@Data
public class CartorioDTOSimplificado {

	@Positive
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String nome;
    
}
