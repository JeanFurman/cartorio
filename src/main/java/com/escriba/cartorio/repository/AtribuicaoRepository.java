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
	
}
