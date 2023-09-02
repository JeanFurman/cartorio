package com.escriba.cartorio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escriba.cartorio.model.Cartorio;

public interface CartorioRepository extends JpaRepository<Cartorio, Long> {
	
	Optional<Cartorio> findByNome(String name);
	
}
