package com.escriba.cartorio.controller;

import java.util.Arrays;
import java.util.List;

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
import com.escriba.cartorio.service.SituacaoService;
import com.escriba.cartorio.util.InstanceCreatorUtil;
import com.escriba.cartorio.util.PaginasUtil;

@ExtendWith(SpringExtension.class)
public class SituacaoControllerTest {
	
	@InjectMocks
	private SituacaoController situacaoController;
	
	@Mock
	private SituacaoService situacaoServiceMock;

    @Test
	void testListarTodasAsSituacoes_Success(){
    	
    	List<SituacaoDTOCompleto> situacao = Arrays.asList(SituacaoMapper.INSTANCE.situacaoToSituacaoDTO(InstanceCreatorUtil.criarSituacao()));
    	
        BDDMockito.when(situacaoServiceMock.listarTodasAsSituacoes(ArgumentMatchers.any(Pageable.class))).thenReturn(PaginasUtil.fazPaginas(0,situacao));
		
        Pageable pageable = PageRequest.of(0, 10);
		
		Page<SituacaoDTOCompleto> pages = situacaoController.listarTodasAsSituacoes(pageable);
		
		Assertions.assertThat(pages).isNotNull();
		Assertions.assertThat(pages.getContent()).isNotEmpty().hasSize(1);
	}
    
    @Test
	void testListarSituacaoPorId_Success(){
    	
    	BDDMockito.when(situacaoServiceMock.listarSituacaoPorId(ArgumentMatchers.anyString())).thenReturn(
    			SituacaoMapper.INSTANCE.situacaoToSituacaoDTO(InstanceCreatorUtil.criarSituacao())) ;
		
    	SituacaoDTOCompleto situacaoDTOCompleto = situacaoController.listarSituacaoPorId(ArgumentMatchers.anyString());
		
		Assertions.assertThat(situacaoDTOCompleto).isNotNull();
		Assertions.assertThat(situacaoDTOCompleto.getNome()).isEqualTo("Nome da Situacao");
	}

    @Test
	void testCadastrarSituacao_Success(){
    	
    	BDDMockito.when(situacaoServiceMock.cadastrarSituacao(ArgumentMatchers.any(SituacaoDTOCompleto.class))).thenReturn("Registro cadastrado com sucesso!");
		
    	String cadastroResponse = situacaoController.cadastrarSituacao(SituacaoMapper.INSTANCE.situacaoToSituacaoDTO(InstanceCreatorUtil.criarSituacao()));
		
		Assertions.assertThat(cadastroResponse).isNotNull();
		Assertions.assertThat(cadastroResponse).isEqualTo("Registro cadastrado com sucesso!");
	}
    
    @Test
	void testEditarSituacao_Success(){
    	
    	BDDMockito.when(situacaoServiceMock.editarSituacao(ArgumentMatchers.anyString(), ArgumentMatchers.any(SituacaoDTOCompleto.class)))
    	.thenReturn("Registro editado com sucesso!");
		
    	String editarResponse = situacaoController.editarSituacao("id_situacao", SituacaoMapper.INSTANCE.situacaoToSituacaoDTO(InstanceCreatorUtil.criarSituacao()));

		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro editado com sucesso!");
	}
	
    @Test
	void testRemoverSituacao_Success(){
    	
    	BDDMockito.when(situacaoServiceMock.removerSituacao(ArgumentMatchers.anyString())).thenReturn("Registro removido com sucesso!");
		
    	String editarResponse = situacaoController.removerSituacao(ArgumentMatchers.anyString());
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro removido com sucesso!");
	}

}
