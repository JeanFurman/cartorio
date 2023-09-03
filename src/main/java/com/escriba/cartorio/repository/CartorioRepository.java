package com.escriba.cartorio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.escriba.cartorio.model.Cartorio;

public interface CartorioRepository extends JpaRepository<Cartorio, Long> {
	
	Optional<Cartorio> findByNome(String name);
	
	@Query("SELECT c FROM Cartorio c WHERE c.situacao.id = :situacaoId")
    List<Cartorio> findCartoriosBySituacaoId(@Param("situacaoId") String situacaoId);
	
	@Query("SELECT c FROM Cartorio c JOIN c.atribuicoes a WHERE a.id = :atribuicaoId")
    List<Cartorio> findCartoriosByAtribuicaoId(@Param("atribuicaoId") String atribuicaoId);
	
}
