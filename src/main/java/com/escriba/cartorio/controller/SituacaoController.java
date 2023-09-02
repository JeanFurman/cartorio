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

import com.escriba.cartorio.dto.SituacaoDTOCompleto;
import com.escriba.cartorio.service.SituacaoService;

@Validated
@RestController
@RequestMapping("/api/situacao")
public class SituacaoController {
	
	private final SituacaoService situacaoService;
	
	public SituacaoController(SituacaoService situacaoService) {
		this.situacaoService = situacaoService;
	}
	
	@GetMapping
	public Page<SituacaoDTOCompleto> listarTodasAsAtribuicoes(Pageable pageable){
		return situacaoService.listarTodasAsSituacoes(pageable);
	}

	@GetMapping("/{id}")
	public SituacaoDTOCompleto listarSituacaoPorId(@PathVariable String id){
		return situacaoService.listarSituacaoPorId(id);
	}

	@PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String cadastrarSituacao(@RequestBody @Valid @NotNull SituacaoDTOCompleto situacao) {
        return situacaoService.cadastrarSituacao(situacao);
    }
	
	@PutMapping("/{id}")
    public String editarSituacao(@PathVariable @NotNull String id,
            @RequestBody @Valid @NotNull SituacaoDTOCompleto situacao) {
        return situacaoService.editarSituacao(id, situacao);
    }
	
	@DeleteMapping("/{id}")
    public String removerSituacao(@PathVariable @NotNull String id) {
        return situacaoService.removerSituacao(id);
    }

}
