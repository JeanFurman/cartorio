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

import com.escriba.cartorio.dto.SituacaoDTOCompleto;
import com.escriba.cartorio.mapper.SituacaoMapper;
import com.escriba.cartorio.model.Cartorio;
import com.escriba.cartorio.model.Situacao;
import com.escriba.cartorio.repository.CartorioRepository;
import com.escriba.cartorio.repository.SituacaoRepository;
import com.escriba.cartorio.util.InstanceCreatorUtil;

@ExtendWith(SpringExtension.class)
public class SituacaoServiceTest {

    @InjectMocks
	private SituacaoService situacaoService;
	
	@Mock
	private SituacaoRepository situacaoRepositoryMock;

    @Mock
    private CartorioRepository cartorioRepositoryMock;

    @Test
	void testListarTodasAsSituacoes_Success(){
    	
    	List<Situacao> situacao = Arrays.asList(InstanceCreatorUtil.criarSituacao());
        BDDMockito.when(situacaoRepositoryMock.findAll()).thenReturn(situacao);
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<SituacaoDTOCompleto> pages = situacaoService.listarTodasAsSituacoes(pageable);
		
		Assertions.assertThat(pages).isNotNull();
		Assertions.assertThat(pages.getContent()).isNotEmpty().hasSize(1);
	}
    
    @Test
	void testListarSituacaoPorId_Success(){
    	
    	BDDMockito.when(situacaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarSituacao()));
		
    	SituacaoDTOCompleto situacaoDTOCompleto = situacaoService.listarSituacaoPorId("id_situacao");
		
		Assertions.assertThat(situacaoDTOCompleto).isNotNull();
		Assertions.assertThat(situacaoDTOCompleto.getNome()).isEqualTo("Nome da Situacao");
	}

    @Test
	void testCadastrarSituacao_Success(){
    	
    	BDDMockito.when(situacaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
    	
    	BDDMockito.when(situacaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	String cadastroResponse = situacaoService.cadastrarSituacao(SituacaoMapper.INSTANCE.situacaoToSituacaoDTO(InstanceCreatorUtil.criarSituacao()));
		
		Assertions.assertThat(cadastroResponse).isNotNull();
		Assertions.assertThat(cadastroResponse).isEqualTo("Registro cadastrado com sucesso!");
	}
    
    @Test
	void testEditarSituacao_Success(){
    	
    	BDDMockito.when(situacaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarSituacao()));
    	
    	BDDMockito.when(situacaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	String editarResponse = situacaoService.editarSituacao(ArgumentMatchers.anyString(), SituacaoMapper.INSTANCE.situacaoToSituacaoDTO(InstanceCreatorUtil.criarSituacao()));
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro editado com sucesso!");
	}
	
    @Test
	void testRemoverSituacao_Success(){
    	
    	BDDMockito.when(situacaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarSituacao()));
    	
    	List<Cartorio> listaVazia = Collections.emptyList();
    	
    	BDDMockito.when(cartorioRepositoryMock.findCartoriosBySituacaoId(ArgumentMatchers.anyString())).thenReturn(listaVazia);
		
    	String editarResponse = situacaoService.removerSituacao(ArgumentMatchers.anyString());
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro removido com sucesso!");
	}

}
