package com.escriba.cartorio.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escriba.cartorio.dto.AtribuicaoDTOCompleto;
import com.escriba.cartorio.dto.AtribuicaoDTOSimplificado;
import com.escriba.cartorio.service.AtribuicaoService;

@Validated
@RestController
@RequestMapping("/api/atribuicao")
public class AtribuicaoController {
	
	private final AtribuicaoService atribuicaoService;
	
	
	public AtribuicaoController(AtribuicaoService atribuicaoService) {
		this.atribuicaoService = atribuicaoService;
	}
	
	@GetMapping
	public Page<AtribuicaoDTOSimplificado> listarTodasAsAtribuicoes(Pageable pageable){
		return atribuicaoService.listarTodasAsAtribuicoes(pageable);
	}
	
	@GetMapping("/{id}")
	public AtribuicaoDTOCompleto listarAtribuicaoPorId(@PathVariable String id){
		return atribuicaoService.listarAtribuicaoPorId(id);
	}


}
