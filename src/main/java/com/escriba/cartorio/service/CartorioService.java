package com.escriba.cartorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.escriba.cartorio.dto.CartorioDTOCompleto;
import com.escriba.cartorio.dto.CartorioDTOSimplificado;
import com.escriba.cartorio.exception.RegistroNotFoundException;
import com.escriba.cartorio.mapper.CartorioMapper;
import com.escriba.cartorio.repository.AtribuicaoRepository;
import com.escriba.cartorio.repository.CartorioRepository;
import com.escriba.cartorio.util.PaginasUtil;

@Service
public class CartorioService {

    private final CartorioRepository cartorioRepository;
    private final AtribuicaoRepository atribuicaoRepository;
	
	public CartorioService(CartorioRepository cartorioRepository, AtribuicaoRepository atribuicaoRepository) {
		this.cartorioRepository = cartorioRepository;
		this.atribuicaoRepository = atribuicaoRepository;
	}
	
	public Page<CartorioDTOSimplificado> listarTodosOsCartorios(Pageable pageable){
		return PaginasUtil.fazPaginas(pageable.getPageNumber(), 
		CartorioMapper.INSTANCE.cartoriosToCartoriosDTOSimplificados(cartorioRepository.findAll()));
	}
	
	public CartorioDTOCompleto listarCartorioPorId(Long id){
		
		return CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(cartorioRepository.findById(id)
				.orElseThrow(() -> new RegistroNotFoundException((String.valueOf(id))) ));
	}

}
