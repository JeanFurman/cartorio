package com.escriba.cartorio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escriba.cartorio.model.Atribuicao;

public interface AtribuicaoRepository extends JpaRepository<Atribuicao, String> {
	
	Optional<Atribuicao> findByNome(String name);
	
}
