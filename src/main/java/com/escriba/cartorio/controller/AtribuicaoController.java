package com.escriba.cartorio.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String cadastrarAtribuicao(@RequestBody @Valid @NotNull AtribuicaoDTOCompleto atribuicao) {
        return atribuicaoService.cadastrarAtribuicao(atribuicao);
    }
	
	@PutMapping("/{id}")
    public String editarSituacao(@PathVariable @NotNull String id,
            @RequestBody @Valid @NotNull AtribuicaoDTOCompleto atribuicao) {
        return atribuicaoService.editarAtribuicao(id, atribuicao);
    }
	
	@DeleteMapping("/{id}")
    public String removerAtribuicao(@PathVariable @NotNull String id) {
        return atribuicaoService.removerAtribuicao(id);
    }

}
