package com.escriba.cartorio.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.escriba.cartorio.dto.CartorioDTOCompleto;
import com.escriba.cartorio.dto.CartorioDTOSimplificado;
import com.escriba.cartorio.exception.RegistroIdIgualException;
import com.escriba.cartorio.exception.RegistroNomeIgualException;
import com.escriba.cartorio.exception.RegistroNotFoundException;
import com.escriba.cartorio.mapper.CartorioMapper;
import com.escriba.cartorio.model.Cartorio;
import com.escriba.cartorio.repository.CartorioRepository;
import com.escriba.cartorio.util.PaginasUtil;

@Validated
@Service
public class CartorioService {

    private final CartorioRepository cartorioRepository;
	
	public CartorioService(CartorioRepository cartorioRepository) {
		this.cartorioRepository = cartorioRepository;
	}
	
	public Page<CartorioDTOSimplificado> listarTodosOsCartorios(Pageable pageable){
		return PaginasUtil.fazPaginas(pageable.getPageNumber(), 
		CartorioMapper.INSTANCE.cartoriosToCartoriosDTOSimplificados(cartorioRepository.findAll()));
	}
	
	public CartorioDTOCompleto listarCartorioPorId(Long id){
		
		return CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(cartorioRepository.findById(id)
				.orElseThrow(() -> new RegistroNotFoundException((String.valueOf(id))) ));
	}
	
	public String cadastrarCartorio(@Valid CartorioDTOCompleto cartorio){
		Cartorio c = CartorioMapper.INSTANCE.cartorioDTOCompletoToCartorio(cartorio);
		if(c.getId() != null) {
			cartorioRepository.findById(c.getId()).ifPresent(cartorioId -> {
				throw new RegistroIdIgualException();	
			});
			throw new IllegalArgumentException();
		}
		cartorioRepository.findByNome(c.getNome()).ifPresent(cartorioNome -> {
			throw new RegistroNomeIgualException(String.valueOf(cartorioNome.getId()));	
		});
		cartorioRepository.save(c);
		return "Registro cadastrado com sucesso!";
	}
	
	public String editarCartorio(Long id, CartorioDTOCompleto cartorio){
		Cartorio c = CartorioMapper.INSTANCE.cartorioDTOCompletoToCartorio(cartorio);
		if(id == null) {
			throw new IllegalArgumentException();
		}

		cartorioRepository.findByNome(c.getNome()).ifPresent(cartorioNome -> {
			if(id != null && !id.equals(cartorioNome.getId())) {
				throw new RegistroNomeIgualException(String.valueOf(cartorioNome.getId()));
			}
		});

		Cartorio cartorioBusca = cartorioRepository.findById(id).orElseThrow(() -> new RegistroNotFoundException((String.valueOf(id))));
		
		cartorioBusca.setNome(c.getNome());
		cartorioBusca.setObservacao(c.getObservacao());
		cartorioBusca.setSituacao(c.getSituacao());
		cartorioBusca.getAtribuicoes().clear();
		c.getAtribuicoes().forEach(cartorioBusca.getAtribuicoes()::add);
		cartorioRepository.save(cartorioBusca);
		
		return "Registro editado com sucesso!";
	}
	
	public String removerCartorio(Long id){
		if(id == null) {
			throw new IllegalArgumentException();
		}

		Cartorio cartorioBusca = cartorioRepository.findById(id).orElseThrow(() -> new RegistroNotFoundException((String.valueOf(id))));
		
		cartorioBusca.getAtribuicoes().clear();
		cartorioRepository.delete(cartorioBusca);
		
		return "Registro removido com sucesso!";
	}

}
