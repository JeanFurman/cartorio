package com.escriba.cartorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.escriba.cartorio.dto.SituacaoDTOCompleto;
import com.escriba.cartorio.exception.RegistroIdIgualException;
import com.escriba.cartorio.exception.RegistroNomeIgualException;
import com.escriba.cartorio.exception.RegistroNotFoundException;
import com.escriba.cartorio.mapper.SituacaoMapper;
import com.escriba.cartorio.model.Situacao;
import com.escriba.cartorio.repository.SituacaoRepository;
import com.escriba.cartorio.util.PaginasUtil;

@Service
public class SituacaoService {

	private final SituacaoRepository situacaoRepository;
	
	public SituacaoService(SituacaoRepository situacaoRepository) {
		this.situacaoRepository = situacaoRepository;
	}
	
	public Page<SituacaoDTOCompleto> listarTodasAsSituacoes(Pageable pageable){
		return PaginasUtil.fazPaginas(pageable.getPageNumber(), 
		SituacaoMapper.INSTANCE.situacoesToSituacoesDTO(situacaoRepository.findAll()));
	}
	
	public SituacaoDTOCompleto listarSituacaoPorId(String id){
		return SituacaoMapper.INSTANCE.situacaoToSituacaoDTO(situacaoRepository.findById(id).orElseThrow(() -> new RegistroNotFoundException((id)) ));
	}
	
	public String cadastrarSituacao(SituacaoDTOCompleto situacao){
		Situacao s = SituacaoMapper.INSTANCE.situacaoDTOToSituacao(situacao);
		situacaoRepository.findById(s.getId()).ifPresent(situacaoId -> {
			throw new RegistroIdIgualException();	
		});
		situacaoRepository.findByNome(s.getNome()).ifPresent(situacaoNome -> {
			throw new RegistroNomeIgualException(s.getId());	
		});
		situacaoRepository.save(s);
		return "Registro cadastrado com sucesso!";
	}
}
