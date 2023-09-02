package com.escriba.cartorio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Situacao {

    @Id
    @Column(length = 20)
    @Size(max = 20)
    @NotBlank
    private String id;

    @NotBlank
    @Column(length = 50)
    @Size(max = 50)
    private String nome;
}