package com.escriba.cartorio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escriba.cartorio.model.Situacao;

public interface SituacaoRepository extends JpaRepository<Situacao, String> {
	
	Optional<Situacao> findByNome(String name);
	
}
