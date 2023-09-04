package com.escriba.cartorio.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.escriba.cartorio.exception.RegistroIdIgualException;
import com.escriba.cartorio.exception.RegistroNomeIgualException;
import com.escriba.cartorio.exception.RegistroNotFoundException;
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
	void testListarCartorioPorId_Success(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
		
    	CartorioDTOCompleto cartorioDTOCompleto = cartorioService.listarCartorioPorId(ArgumentMatchers.anyLong());
		
		Assertions.assertThat(cartorioDTOCompleto).isNotNull();
		Assertions.assertThat(cartorioDTOCompleto.getNome()).isEqualTo("Nome do Cartorio");
		Assertions.assertThat(cartorioDTOCompleto.getSituacao().getNome()).isEqualTo("Nome da Situacao");
	}
    
    @Test
	void testListarCartorioPorId_RaisesRegistroNotFoundException(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
		
    	RegistroNotFoundException excecao = assertThrows(RegistroNotFoundException.class, () -> cartorioService.listarCartorioPorId(1L));
    	
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Registro 1 não encontrado!");
	}

    @Test
	void testCadastrarCartorio_Success(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
    	
    	BDDMockito.when(cartorioRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	String cadastroResponse = cartorioService.cadastrarCartorio(CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio()));
		
		Assertions.assertThat(cadastroResponse).isNotNull();
		Assertions.assertThat(cadastroResponse).isEqualTo("Registro cadastrado com sucesso!");
	}
    
    @Test
	void testCadastrarCartorio_RaisesRegistroIdIgualException(){
    	
    	Cartorio c = InstanceCreatorUtil.criarCartorio();
    	c.setId(1L);
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
    	
    	BDDMockito.when(cartorioRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	RegistroIdIgualException excecao = assertThrows(RegistroIdIgualException.class, () -> cartorioService.cadastrarCartorio(
    			CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(c)));
    	
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Registro já cadastrado");
	}
    
    @Test
	void testCadastrarCartorio_RaisesIllegalArgumentException(){
    	
    	Cartorio c = InstanceCreatorUtil.criarCartorio();
    	c.setId(1L);
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
    	
    	BDDMockito.when(cartorioRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	assertThrows(IllegalArgumentException.class, () -> cartorioService.cadastrarCartorio(
    			CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(c)));
    	
	}
    
    @Test
	void testCadastrarCartorio_RaisesRegistroNomeIgualException(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
    	
    	BDDMockito.when(cartorioRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
		
    	RegistroNomeIgualException excecao = assertThrows(RegistroNomeIgualException.class, () -> cartorioService.cadastrarCartorio(
    			CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio())));
    	
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Nome já informado no registro com código null");
	}
    
    @Test
	void testEditarCartorio_Success(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
    	
    	BDDMockito.when(cartorioRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
    	String editarResponse = cartorioService.editarCartorio(ArgumentMatchers.anyLong(), CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio()));
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro editado com sucesso!");
	}
    
    @Test
   	void testEditarCartorio_RaisesIllegalArgumentException(){
       	
       	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
       	
       	BDDMockito.when(cartorioRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
   				
       	assertThrows(IllegalArgumentException.class, () -> cartorioService.editarCartorio(
       			null, CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio())));
   	}
   	
       
    @Test
   	void testEditarCartorio_RaisesRegistroNomeIgualException(){
       	
       	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
       	
       	BDDMockito.when(cartorioRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
   				
       	RegistroNomeIgualException excecao = assertThrows(RegistroNomeIgualException.class, () -> cartorioService.editarCartorio(
       			1L, CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio())));
       	
       	Assertions.assertThat(excecao.getMessage()).isEqualTo("Nome já informado no registro com código null");
   	}
   	
       
    @Test
   	void testEditarCartorio_RaisesRegistroNotFoundException(){
       	
       	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
       	
       	BDDMockito.when(cartorioRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
   		
       	RegistroNotFoundException excecao = assertThrows(RegistroNotFoundException.class, () -> cartorioService.editarCartorio(
       			1L, CartorioMapper.INSTANCE.cartorioToCartorioDTOCompleto(InstanceCreatorUtil.criarCartorio())));
       	
       	Assertions.assertThat(excecao.getMessage()).isEqualTo("Registro null não encontrado!");
   	}
	
    @Test
	void testRemoverCartorio_Success(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(InstanceCreatorUtil.criarCartorio()));
		
    	String editarResponse = cartorioService.removerCartorio(ArgumentMatchers.anyLong());
		
		Assertions.assertThat(editarResponse).isNotNull();
		Assertions.assertThat(editarResponse).isEqualTo("Registro removido com sucesso!");
	}
    
    @Test
	void testRemoverAtribuicao_RaisesIllegalArgumentException(){
    	    					
    	assertThrows(IllegalArgumentException.class, () -> cartorioService.removerCartorio(null));
    	    	
	}
    
    @Test
	void testRemoverAtribuicao_RaisesRegistroNotFoundException(){
    	
    	BDDMockito.when(cartorioRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
		
    	RegistroNotFoundException excecao = assertThrows(RegistroNotFoundException.class, () -> cartorioService.removerCartorio(1L));
    	
    	Assertions.assertThat(excecao.getMessage()).isEqualTo("Registro null não encontrado!");
	}

}
