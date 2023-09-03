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
import com.escriba.cartorio.dto.CartorioDTOCompleto;
import com.escriba.cartorio.dto.CartorioDTOSimplificado;
import com.escriba.cartorio.mapper.AtribuicaoMapper;
import com.escriba.cartorio.mapper.CartorioMapper;
import com.escriba.cartorio.service.CartorioService;
import com.escriba.cartorio.util.InstanceCreatorUtil;
import com.escriba.cartorio.util.PaginasUtil;

@ExtendWith(SpringExtension.class)
public class CartorioControllerTest {

	@InjectMocks
	private CartorioController cartorioController;
	
	@Mock
	private CartorioService cartorioServiceMock;

    @Test
	void testListarTodosOsCartorios_Success(){
    	
    	List<CartorioDTOSimplificado> cartorio = CartorioMapper.INSTANCE.cartoriosToCartoriosDTOSimplificados(Arrays.asList(InstanceCreatorUtil.criarCartorio()));
    	
        BDDMockito.when(cartorioServiceMock.listarTodosOsCartorios(ArgumentMatchers.any(Pageable.class))).thenReturn(PaginasUtil.fazPaginas(0,cartorio));
		
        Pageable pageable = PageRequest.of(0, 10);
		
		Page<CartorioDTOSimplificado> pages = cartorioController.listarTodosOsCartorios(pageable);
		
		Assertions.assertThat(pages).isNotNull();
		Assertions.assertThat(pages.getContent()).isNotEmpty().hasSize(1);
	}
    
    @Test
	void testListarCartorioPorId_Success(){
    	
    	BDDMockito.when(cartorioServiceMock.listarCartorioPorId(ArgumentMatchers.anyLong())).thenReturn(
    			CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio())) ;
		
    	CartorioDTOCompleto cartorioDTOCompleto = cartorioController.listarCartorioPorId(ArgumentMatchers.anyLong());
		
		Assertions.assertThat(cartorioDTOCompleto).isNotNull();
		Assertions.assertThat(cartorioDTOCompleto.getNome()).isEqualTo("Nome do Cartorio");
	}

    @Test
	void testCadastrarCartorio_Success(){
    	
    	BDDMockito.when(cartorioServiceMock.cadastrarCartorio(ArgumentMatchers.any(CartorioDTOCompleto.class))).thenReturn("Registro cadastrado com sucesso!");
		
    	String cadastroResponse = cartorioController.cadastrarCartorio(CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio()));
		
		Assertions.assertThat(cadastroResponse).isNotNull();
		Assertions.assertThat(cadastroResponse).isEqualTo("Registro cadastrado com sucesso!");
	}
    
    @Test
	void testEditarCartorio_Success(){
    	
    	BDDMockito.when(cartorioServiceMock.editarCartorio(ArgumentMatchers.anyLong(), ArgumentMatchers.any(CartorioDTOCompleto.class)))
    	.thenReturn("Registro editado com sucesso!");
		
    	String editarResponse = cartorioController.editarCartorio(1L, CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio()));
    	
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro editado com sucesso!");
	}
	
    @Test
	void testRemoverAtribuicao_Success(){
    	
    	BDDMockito.when(cartorioServiceMock.removerCartorio(ArgumentMatchers.anyLong())).thenReturn("Registro removido com sucesso!");
		
    	String editarResponse = cartorioController.removerCartorio(ArgumentMatchers.anyLong());
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro removido com sucesso!");
	}
	
}
