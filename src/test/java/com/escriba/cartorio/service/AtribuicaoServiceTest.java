package com.escriba.cartorio.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
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
import com.escriba.cartorio.exception.RegistroIdIgualException;
import com.escriba.cartorio.exception.RegistroNaoPodeSerRemovidoException;
import com.escriba.cartorio.exception.RegistroNomeIgualException;
import com.escriba.cartorio.exception.RegistroNotFoundException;
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
	void testListarAtribuicaoPorId_RaisesRegistroNotFoundException(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	RegistroNotFoundException excecao = assertThrows(RegistroNotFoundException.class, () -> atribuicaoService.listarAtribuicaoPorId("AtribuicaoInexistente"));
    	
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Registro AtribuicaoInexistente não encontrado!");
	}

    @Test
	void testCadastrarAtribuicao_Success(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	String cadastroResponse = atribuicaoService.cadastrarAtribuicao(AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao()));
		
		Assertions.assertThat(cadastroResponse).isNotNull();
		Assertions.assertThat(cadastroResponse).isEqualTo("Registro cadastrado com sucesso!");
	}
    
    @Test
	void testCadastrarAtribuicao_RaisesRegistroIdIgualException(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	RegistroIdIgualException excecao = assertThrows(RegistroIdIgualException.class, () -> atribuicaoService.cadastrarAtribuicao(
    			AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao())));
    	
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Registro já cadastrado");
	}
    
    @Test
	void testCadastrarAtribuicao_RaisesRegistroNomeIgualException(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
		
    	RegistroNomeIgualException excecao = assertThrows(RegistroNomeIgualException.class, () -> atribuicaoService.cadastrarAtribuicao(
    			AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao())));
    	
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Nome já informado no registro com código id_atribuicao");
	}
    
    @Test
	void testEditarAtribuicao_Success(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	String editarResponse = atribuicaoService.editarAtribuicao(ArgumentMatchers.anyString(), AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao()));
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro editado com sucesso!");
	}
    
    @Test
	void testEditarAtribuicao_RaisesIllegalArgumentException(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
				
    	assertThrows(IllegalArgumentException.class, () -> atribuicaoService.editarAtribuicao(
    			null, AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao())));
	}
	
    
    @Test
	void testEditarAtribuicao_RaisesRegistroNomeIgualException(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
				
    	RegistroNomeIgualException excecao = assertThrows(RegistroNomeIgualException.class, () -> atribuicaoService.editarAtribuicao(
    			ArgumentMatchers.anyString(), AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao())));
    	
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Nome já informado no registro com código id_atribuicao");
	}
	
    
    @Test
	void testEditarAtribuicao_RaisesRegistroNotFoundException(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	RegistroNotFoundException excecao = assertThrows(RegistroNotFoundException.class, () -> atribuicaoService.editarAtribuicao(
    			"id_atribuicao", AtribuicaoMapper.INSTANCE.atribuicaoToAtribuicaoDTOCompleto(InstanceCreatorUtil.criarAtribuicao())));
    	
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Registro id_atribuicao não encontrado!");
	}
	
	
    @Test
	void testRemoverAtribuicao_Success(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
    	
    	List<Cartorio> listaVazia = Collections.emptyList();
    	
    	BDDMockito.when(cartorioRepositoryMock.findCartoriosByAtribuicaoId(ArgumentMatchers.anyString())).thenReturn(listaVazia);
		
    	String editarResponse = atribuicaoService.removerAtribuicao(ArgumentMatchers.anyString());
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro removido com sucesso!");
	}
    
    @Test
	void testRemoverAtribuicao_RaisesIllegalArgumentException(){
    	    					
    	assertThrows(IllegalArgumentException.class, () -> atribuicaoService.removerAtribuicao(null));
    	    	
	}
    
    @Test
	void testRemoverAtribuicao_RaisesRegistroNotFoundException(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	RegistroNotFoundException excecao = assertThrows(RegistroNotFoundException.class, () -> atribuicaoService.removerAtribuicao("id_atribuicao"));
    	
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Registro id_atribuicao não encontrado!");
	}
    
    @Test
	void testRemoverAtribuicao_RaisesRegistroNaoPodeSerRemovidoException(){
    	
    	BDDMockito.when(atribuicaoRepositoryMock.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarAtribuicao()));
    	
    	List<Cartorio> listaVazia = new ArrayList<>();
    	
    	listaVazia.add(InstanceCreatorUtil.criarCartorio());
    	
    	BDDMockito.when(cartorioRepositoryMock.findCartoriosByAtribuicaoId(ArgumentMatchers.anyString())).thenReturn(listaVazia);
		
    	RegistroNaoPodeSerRemovidoException excecao = assertThrows(RegistroNaoPodeSerRemovidoException.class, () -> atribuicaoService.removerAtribuicao("id_atribuicao"));
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Registro utilizado em outro cadastro");
	}
	
}
