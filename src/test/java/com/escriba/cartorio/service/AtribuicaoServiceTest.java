package com.escriba.cartorio.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.escriba.cartorio.dto.AtribuicaoDTOCompleto;
import com.escriba.cartorio.dto.AtribuicaoDTOSimplificado;
import com.escriba.cartorio.mapper.AtribuicaoMapper;
import com.escriba.cartorio.model.Atribuicao;
import com.escriba.cartorio.model.Cartorio;
import com.escriba.cartorio.repository.AtribuicaoRepository;
import com.escriba.cartorio.repository.CartorioRepository;
import com.escriba.cartorio.util.InstanceCreatorUtil;

@ExtendWith(SpringExtension.class)
public class AtribuicaoServiceTest {

	@InjectMocks
	private AtribuicaoService atribuicaoService;
	
	@Mock
	private AtribuicaoRepository atribuicaoRepositoryMock;

    @Mock
    private CartorioRepository cartorioRepositoryMock;

    @Test
	void testListarTodasAsAtribuicoes_Sucess(){
    	
    	List<Atribuicao> atribuicao = Arrays.asList(InstanceCreatorUtil.criarAtribuicao());
        BDDMockito.when(atribuicaoRepositoryMock.findAll()).thenReturn(atribuicao);
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<AtribuicaoDTOSimplificado> pages = atribuicaoService.listarTodasAsAtribuicoes(pageable);
		
		Assertions.assertThat(pages).isNotNull();
		Assertions.assertThat(pages.getContent()).isNotEmpty().hasSize(1);
	}
    
    @Test
	void testListarAtribuicaoPorId_Success(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
		
    	AtribuicaoDTOCompleto atribuicaoDTOCompleto = atribuicaoService.listarAtribuicaoPorId(ArgumentMatchers.anyString());
		
		Assertions.assertThat(atribuicaoDTOCompleto).isNotNull();
		Assertions.assertThat(atribuicaoDTOCompleto.getNome()).isEqualTo("Nome da Atribuicao");
	}

    @Test
	void testCadastrarSituacao_Success(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	String cadastroResponse = atribuicaoService.cadastrarAtribuicao(AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao()));
		
		Assertions.assertThat(cadastroResponse).isNotNull();
		Assertions.assertThat(cadastroResponse).isEqualTo("Registro cadastrado com sucesso!");
	}
    
    @Test
	void testEditarSituacao_Success(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	String editarResponse = atribuicaoService.editarAtribuicao(ArgumentMatchers.anyString(), AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao()));
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro editado com sucesso!");
	}
	
    @Test
	void testRemoverSituacao_Success(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
    	
    	List<Cartorio> listaVazia = Collections.emptyList();
    	
    	BDDMockito.when(cartorioRepositoryMock.findCartoriosBySituacaoId(ArgumentMatchers.anyString())).thenReturn(listaVazia);
		
    	String editarResponse = atribuicaoService.removerAtribuicao(ArgumentMatchers.anyString());
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro removido com sucesso!");
	}
	
}
