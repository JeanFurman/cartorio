package com.escriba.cartorio.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Cartorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 150)
    @Size(max = 150)
    private String nome;

    @Column(length = 250)
    @Size(max = 250)
    private String observacao;

    @NotNull
    @ManyToOne
    private Situacao situacao;

    @NotEmpty
    @ManyToMany
    @JoinTable(name = "cartorio_atribuicao",
               joinColumns = @JoinColumn(name = "cartorio_id"),
               inverseJoinColumns = @JoinColumn(name = "atribuicao_id"))
    private List<Atribuicao> atribuicoes;
}
