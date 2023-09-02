package com.escriba.cartorio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.escriba.cartorio.model.Atribuicao;
import com.escriba.cartorio.model.Cartorio;

public interface AtribuicaoRepository extends JpaRepository<Atribuicao, String> {
	
	Optional<Atribuicao> findByNome(String name);
	
	@Query("SELECT c FROM Cartorio c JOIN c.atribuicoes a WHERE a.id = :atribuicaoId")
    List<Cartorio> findCartoriosByAtribuicaoId(@Param("atribuicaoId") String atribuicaoId);

	
}
