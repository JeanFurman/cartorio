package com.escriba.cartorio.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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

import com.escriba.cartorio.dto.CartorioDTOCompleto;
import com.escriba.cartorio.dto.CartorioDTOSimplificado;
import com.escriba.cartorio.service.CartorioService;

@Validated
@RestController
@RequestMapping("/api/cartorio")
public class CartorioController {

	private final CartorioService cartorioService;
	
	public CartorioController(CartorioService cartorioService) {
		this.cartorioService = cartorioService;
	}
	
	@GetMapping
	public Page<CartorioDTOSimplificado> listarTodosOsCartorios(Pageable pageable){
		return cartorioService.listarTodosOsCartorios(pageable);
	}
	
	@GetMapping("/{id}")
	public CartorioDTOCompleto listarCartorioPorId(@PathVariable @Positive Long id){
		return cartorioService.listarCartorioPorId(id);
	}
	
	@PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String cadastrarCartorio(@RequestBody CartorioDTOCompleto cartorio) {
        return cartorioService.cadastrarCartorio(cartorio);
    }
	
	@PutMapping("/{id}")
    public String editarCartorio(@PathVariable @NotNull @Positive Long id,
            @RequestBody @Valid @NotNull CartorioDTOCompleto cartorio) {
        return cartorioService.editarCartorio(id, cartorio);
    }
	
	@DeleteMapping("/{id}")
    public String editarCartorio(@PathVariable @NotNull @Positive Long id) {
        return cartorioService.removerCartorio(id);
    }
	
}
