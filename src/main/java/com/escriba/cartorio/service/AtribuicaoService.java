package com.escriba.cartorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.escriba.cartorio.dto.AtribuicaoDTOCompleto;
import com.escriba.cartorio.dto.AtribuicaoDTOSimplificado;
import com.escriba.cartorio.exception.RegistroIdIgualException;
import com.escriba.cartorio.exception.RegistroNaoPodeSerRemovidoException;
import com.escriba.cartorio.exception.RegistroNomeIgualException;
import com.escriba.cartorio.exception.RegistroNotFoundException;
import com.escriba.cartorio.mapper.AtribuicaoMapper;
import com.escriba.cartorio.model.Atribuicao;
import com.escriba.cartorio.repository.AtribuicaoRepository;
import com.escriba.cartorio.repository.CartorioRepository;
import com.escriba.cartorio.util.PaginasUtil;

@Service
public class AtribuicaoService {

    private final AtribuicaoRepository atribuicaoRepository;
    
    private final CartorioRepository cartorioRepository;
	
	public AtribuicaoService(AtribuicaoRepository atribuicaoRepository, CartorioRepository cartorioRepository) {
		this.atribuicaoRepository = atribuicaoRepository;
		this.cartorioRepository = cartorioRepository;
	}
	
	public Page<AtribuicaoDTOSimplificado> listarTodasAsAtribuicoes(Pageable pageable){
		return PaginasUtil.fazPaginas(pageable.getPageNumber(), 
		AtribuicaoMapper.INSTANCE.atribuicoesToAtribuicoesDTOSimplificados(atribuicaoRepository.findAll()));
	}
	
	public AtribuicaoDTOCompleto listarAtribuicaoPorId(String id){
		return AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(
				atribuicaoRepository.findById(id).orElseThrow(() -> new RegistroNotFoundException((id)) ));
	}
	
	public String cadastrarAtribuicao(AtribuicaoDTOCompleto atribuicao){
		Atribuicao a = AtribuicaoMapper.INSTANCE.atribuicaoDTOCompletoToAtribuicao(atribuicao);
		atribuicaoRepository.findById(a.getId()).ifPresent(atribuicaoId -> {
			throw new RegistroIdIgualException();	
		});
		
		atribuicaoRepository.findByNome(a.getNome()).ifPresent(atribuicaoNome -> {
			throw new RegistroNomeIgualException(atribuicaoNome.getId());	
		});
		
		atribuicaoRepository.save(a);
		return "Registro cadastrado com sucesso!";
	}
	
	public String editarAtribuicao(String id, AtribuicaoDTOCompleto atribuicao){
		Atribuicao a = AtribuicaoMapper.INSTANCE.atribuicaoDTOCompletoToAtribuicao(atribuicao);
		if(id == null) {
			throw new IllegalArgumentException();
		}

		atribuicaoRepository.findByNome(a.getNome()).ifPresent(atribuicaoNome -> {
			if(id != null && !id.equals(atribuicaoNome.getId())) {
				throw new RegistroNomeIgualException(atribuicaoNome.getId());
			}
		});

		Atribuicao atribuicaoBusca = atribuicaoRepository.findById(id).orElseThrow(() -> new RegistroNotFoundException((id)));
		atribuicaoBusca.setNome(a.getNome());
		atribuicaoBusca.setSituacao(a.isSituacao());
		
		atribuicaoRepository.save(atribuicaoBusca);
		return "Registro editado com sucesso!";
	}
	
	public String removerAtribuicao(String id){
		if(id == null) {
			throw new IllegalArgumentException();
		}

		Atribuicao atribuicaoBusca = atribuicaoRepository.findById(id).orElseThrow(() -> new RegistroNotFoundException((id)));
		
		if(!cartorioRepository.findCartoriosByAtribuicaoId(id).isEmpty()) {
			throw new RegistroNaoPodeSerRemovidoException();
		}
		atribuicaoRepository.delete(atribuicaoBusca);
		
		return "Registro removido com sucesso!";
	}

}
