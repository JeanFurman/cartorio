package com.escriba.cartorio.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.escriba.cartorio.model.Atribuicao;
import com.escriba.cartorio.model.Cartorio;
import com.escriba.cartorio.model.Situacao;
import com.escriba.cartorio.util.InstanceCreatorUtil;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartorioRepositoryTest {
	
	@Autowired
    private CartorioRepository cartorioRepository;
	
	@Autowired
    private SituacaoRepository situacaoRepository;
	
	@Autowired
    private AtribuicaoRepository atribuicaoRepository;
	
	@BeforeEach
    void setUp() {
		
        Flyway flyway = Flyway.configure().dataSource("jdbc:h2:mem:testdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        		"sa", "password").load();
        flyway.migrate();
    }

    @Test
    void testFindByNome_Success() {
    	Situacao situacao = InstanceCreatorUtil.criarSituacao();
    	situacaoRepository.save(situacao);
    	
    	Atribuicao atribuicao = InstanceCreatorUtil.criarAtribuicao();
        atribuicaoRepository.save(atribuicao);
    	
        Cartorio cartorio = new Cartorio();
        cartorio.setNome("Nome do Cartorio");
        cartorio.setObservacao("Observacao do Cartorio");
        cartorio.setSituacao(situacao); 
        cartorio.setAtribuicoes(Arrays.asList(atribuicao));
        cartorioRepository.save(cartorio);

        Optional<Cartorio> foundCartorio = cartorioRepository.findByNome("Nome do Cartorio");

        assertTrue(foundCartorio.isPresent());
        assertNotNull(foundCartorio.get().getId());
    }
    
    @Test
    void testSaveComListaVazia_RaisesConstraintViolationException() {
    	Situacao situacao = InstanceCreatorUtil.criarSituacao();
    	situacaoRepository.save(situacao);
    	
    	
        Cartorio cartorio = new Cartorio();
        cartorio.setNome("Nome do Cartorio");
        cartorio.setObservacao("Observacao do Cartorio");
        cartorio.setSituacao(situacao); 
        assertThrows(ConstraintViolationException.class, () -> cartorioRepository.save(cartorio));

    }

    @Test
    void testFindByNome_NotFound() {
        Optional<Cartorio> foundCartorio = cartorioRepository.findByNome("Nome Inexistente");

        assertFalse(foundCartorio.isPresent());
    }

    @Test
    void testFindCartoriosByAtribuicaoId_Success() {

        Atribuicao atribuicao = InstanceCreatorUtil.criarAtribuicao();
        atribuicaoRepository.save(atribuicao);

        Situacao situacao = InstanceCreatorUtil.criarSituacao();
        situacaoRepository.save(situacao);

        Cartorio cartorio = new Cartorio();
        cartorio.setNome("Nome do Cartorio");
        cartorio.setObservacao("Observacao do Cartorio");
        cartorio.setSituacao(situacao);
        cartorio.setAtribuicoes(Arrays.asList(atribuicao));
        Cartorio CartorioSalvo = cartorioRepository.save(cartorio);

        List<Cartorio> cartorios = cartorioRepository.findCartoriosByAtribuicaoId("id_atribuicao");

        assertEquals(1, cartorios.size());
        assertEquals(CartorioSalvo.getId(), cartorios.get(0).getId());
    }
    
    @Test
    void testFindCartoriosBySituacaoId_Success() {
        Situacao situacao = InstanceCreatorUtil.criarSituacao();
        situacaoRepository.save(situacao);

        Atribuicao atribuicao = InstanceCreatorUtil.criarAtribuicao();
        atribuicaoRepository.save(atribuicao);

        Cartorio cartorio = new Cartorio();
        cartorio.setNome("Nome do Cartorio");
        cartorio.setObservacao("Observacao do Cartorio");
        cartorio.setSituacao(situacao);
        cartorio.setAtribuicoes(Arrays.asList(atribuicao));
        cartorioRepository.save(cartorio);

        List<Cartorio> cartorios = cartorioRepository.findCartoriosBySituacaoId("id_situacao");

        assertEquals(1, cartorios.size());
    }

}
