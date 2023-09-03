package com.escriba.cartorio.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.escriba.cartorio.model.Situacao;
import com.escriba.cartorio.util.InstanceCreatorUtil;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SituacaoRepositoryTest {

    @Autowired
    private SituacaoRepository situacaoRepository;
    
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

        Optional<Situacao> SituacaoEncontrada = situacaoRepository.findByNome("Nome da Situacao");
        
        Assertions.assertThat(SituacaoEncontrada).isPresent();
        Assertions.assertThat(SituacaoEncontrada.get().getNome()).isEqualTo("Nome da Situacao");
    }

    @Test
    void testFindByNome_NotFound() {

        Optional<Situacao> SituacaoNaoEncontrada = situacaoRepository.findByNome("Nome Inexistente");

        Assertions.assertThat(SituacaoNaoEncontrada).isNotPresent();
    }

   
}