package com.escriba.cartorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.escriba.cartorio.dto.AtribuicaoDTOCompleto;
import com.escriba.cartorio.dto.AtribuicaoDTOSimplificado;
import com.escriba.cartorio.exception.RegistroNotFoundException;
import com.escriba.cartorio.mapper.AtribuicaoMapper;
import com.escriba.cartorio.repository.AtribuicaoRepository;
import com.escriba.cartorio.util.PaginasUtil;

@Service
public class AtribuicaoService {

    private final AtribuicaoRepository atribuicaoRepository;
	
	public AtribuicaoService(AtribuicaoRepository atribuicaoRepository) {
		this.atribuicaoRepository = atribuicaoRepository;
	}
	
	public Page<AtribuicaoDTOSimplificado> listarTodasAsAtribuicoes(Pageable pageable){
		return PaginasUtil.fazPaginas(pageable.getPageNumber(), 
		AtribuicaoMapper.INSTANCE.atribuicoesToAtribuicoesDTOSimplificados(atribuicaoRepository.findAll()));
	}
	
	public AtribuicaoDTOCompleto listarAtribuicaoPorId(String id){
		return AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(
				atribuicaoRepository.findById(id).orElseThrow(() -> new RegistroNotFoundException((id)) ));
	}

}
