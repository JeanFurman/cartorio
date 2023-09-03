package com.escriba.cartorio.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.escriba.cartorio.model.Atribuicao;
import com.escriba.cartorio.util.InstanceCreatorUtil;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AtribuicaoRepositoryTest {

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
        Atribuicao atribuicao = InstanceCreatorUtil.criarAtribuicao();
        atribuicaoRepository.save(atribuicao);

        Optional<Atribuicao> AtribuicaoEncontrado = atribuicaoRepository.findByNome("Nome da Atribuicao");

        Assertions.assertThat(AtribuicaoEncontrado).isPresent();
        Assertions.assertThat(AtribuicaoEncontrado.get().getNome()).isEqualTo("Nome da Atribuicao");
    }

    @Test
    void testFindByNome_NotFound() {
        Optional<Atribuicao> AtribuicaoNaoEncontrada = atribuicaoRepository.findByNome("Nome Inexistente");

        Assertions.assertThat(AtribuicaoNaoEncontrada).isNotPresent();
    }


}
