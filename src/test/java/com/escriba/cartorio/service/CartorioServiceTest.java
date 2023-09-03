package com.escriba.cartorio.service;

import java.util.Arrays;
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

import com.escriba.cartorio.dto.CartorioDTOCompleto;
import com.escriba.cartorio.dto.CartorioDTOSimplificado;
import com.escriba.cartorio.mapper.CartorioMapper;
import com.escriba.cartorio.model.Cartorio;
import com.escriba.cartorio.repository.AtribuicaoRepository;
import com.escriba.cartorio.repository.CartorioRepository;
import com.escriba.cartorio.repository.SituacaoRepository;
import com.escriba.cartorio.util.InstanceCreatorUtil;

@ExtendWith(SpringExtension.class)
public class CartorioServiceTest {
	
	@InjectMocks
	private CartorioService cartorioService;
	
	@Mock
	private AtribuicaoRepository atribuicaoRepositoryMock;

    @Mock
    private CartorioRepository cartorioRepositoryMock;
    
    @Mock
	private SituacaoRepository situacaoRepositoryMock;

    @Test
	void testListarTodosOsCartorios_Sucess(){
    	
    	List<Cartorio> cartorio = Arrays.asList(InstanceCreatorUtil.criarCartorio());
        BDDMockito.when(cartorioRepositoryMock.findAll()).thenReturn(cartorio);
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<CartorioDTOSimplificado> pages = cartorioService.listarTodosOsCartorios(pageable);
		
		Assertions.assertThat(pages).isNotNull();
		Assertions.assertThat(pages.getContent()).isNotEmpty().hasSize(1);
	}
    
    @Test
	void testListarAtribuicaoPorId_Success(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
		
    	CartorioDTOCompleto cartorioDTOCompleto = cartorioService.listarCartorioPorId(ArgumentMatchers.anyLong());
		
		Assertions.assertThat(cartorioDTOCompleto).isNotNull();
		Assertions.assertThat(cartorioDTOCompleto.getNome()).isEqualTo("Nome do Cartorio");
		Assertions.assertThat(cartorioDTOCompleto.getSituacao().getNome()).isEqualTo("Nome da Situacao");
	}

    @Test
	void testCadastrarSituacao_Success(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
    	
    	BDDMockito.when(cartorioRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	String cadastroResponse = cartorioService.cadastrarCartorio(CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio()));
		
		Assertions.assertThat(cadastroResponse).isNotNull();
		Assertions.assertThat(cadastroResponse).isEqualTo("Registro cadastrado com sucesso!");
	}
    
    @Test
	void testEditarSituacao_Success(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
    	
    	BDDMockito.when(cartorioRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	String editarResponse = cartorioService.editarCartorio(ArgumentMatchers.anyLong(), CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio()));
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro editado com sucesso!");
	}
	
    @Test
	void testRemoverSituacao_Success(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
		
    	String editarResponse = cartorioService.removerCartorio(ArgumentMatchers.anyLong());
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro removido com sucesso!");
	}

}
