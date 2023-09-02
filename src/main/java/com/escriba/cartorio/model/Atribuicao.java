package com.escriba.cartorio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(
    name = "atribuicao",
    uniqueConstraints = @UniqueConstraint(columnNames = "id")
)
public class Atribuicao {
	
	@Id
	@Column(length = 20)
	@NotBlank
	@Size(max=20)
	private String id;
	
	@NotBlank
	@Column(length = 50)
	@Size(max = 50)
	private String nome;
	
	private boolean situacao = true;
	
}
