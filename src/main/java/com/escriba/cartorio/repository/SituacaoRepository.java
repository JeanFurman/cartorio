package com.escriba.cartorio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.escriba.cartorio.model.Cartorio;
import com.escriba.cartorio.model.Situacao;

public interface SituacaoRepository extends JpaRepository<Situacao, String> {
	
	Optional<Situacao> findByNome(String name);
	
}
