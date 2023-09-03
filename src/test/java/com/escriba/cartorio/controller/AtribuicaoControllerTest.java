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

import com.escriba.cartorio.dto.AtribuicaoDTOCompleto;
import com.escriba.cartorio.dto.AtribuicaoDTOSimplificado;
import com.escriba.cartorio.mapper.AtribuicaoMapper;
import com.escriba.cartorio.service.AtribuicaoService;
import com.escriba.cartorio.util.InstanceCreatorUtil;
import com.escriba.cartorio.util.PaginasUtil;

@ExtendWith(SpringExtension.class)
public class AtribuicaoControllerTest {

	@InjectMocks
	private AtribuicaoController atribuicaoController;
	
	@Mock
	private AtribuicaoService atribuicaoServiceMock;

    @Test
	void testListarTodasAsAtribuicoes_Success(){
    	
    	List<AtribuicaoDTOSimplificado> atribuicao = AtribuicaoMapper.INSTANCE.atribuicoesToAtribuicoesDTOSimplificados(Arrays.asList(InstanceCreatorUtil.criarAtribuicao()));
    	
        BDDMockito.when(atribuicaoServiceMock.listarTodasAsAtribuicoes(ArgumentMatchers.any(Pageable.class))).thenReturn(PaginasUtil.fazPaginas(0,atribuicao));
		
        Pageable pageable = PageRequest.of(0, 10);
		
		Page<AtribuicaoDTOSimplificado> pages = atribuicaoController.listarTodasAsAtribuicoes(pageable);
		
		Assertions.assertThat(pages).isNotNull();
		Assertions.assertThat(pages.getContent()).isNotEmpty().hasSize(1);
	}
    
    @Test
	void testListarAtribuicaoPorId_Success(){
    	
    	BDDMockito.when(atribuicaoServiceMock.listarAtribuicaoPorId(ArgumentMatchers.anyString())).thenReturn(
    			AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao())) ;
		
    	AtribuicaoDTOCompleto atribuicaoDTOCompleto = atribuicaoController.listarAtribuicaoPorId(ArgumentMatchers.anyString());
		
		Assertions.assertThat(atribuicaoDTOCompleto).isNotNull();
		Assertions.assertThat(atribuicaoDTOCompleto.getNome()).isEqualTo("Nome da Atribuicao");
	}

    @Test
	void testCadastrarAtribuicao_Success(){
    	
    	BDDMockito.when(atribuicaoServiceMock.cadastrarAtribuicao(ArgumentMatchers.any(AtribuicaoDTOCompleto.class))).thenReturn("Registro cadastrado com sucesso!");
		
    	String cadastroResponse = atribuicaoController.cadastrarAtribuicao(AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao()));
		
		Assertions.assertThat(cadastroResponse).isNotNull();
		Assertions.assertThat(cadastroResponse).isEqualTo("Registro cadastrado com sucesso!");
	}
    
    @Test
	void testEditarAtribuicao_Success(){
    	
    	BDDMockito.when(atribuicaoServiceMock.editarAtribuicao(ArgumentMatchers.anyString(), ArgumentMatchers.any(AtribuicaoDTOCompleto.class)))
    	.thenReturn("Registro editado com sucesso!");
		
    	String editarResponse = atribuicaoController.editarSituacao("id_situacao", AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao()));
    	
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro editado com sucesso!");
	}
	
    @Test
	void testRemoverAtribuicao_Success(){
    	
    	BDDMockito.when(atribuicaoServiceMock.removerAtribuicao(ArgumentMatchers.anyString())).thenReturn("Registro removido com sucesso!");
		
    	String editarResponse = atribuicaoController.removerAtribuicao(ArgumentMatchers.anyString());
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro removido com sucesso!");
	}
	
}
