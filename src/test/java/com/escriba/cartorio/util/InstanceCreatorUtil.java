package com.escriba.cartorio.util;

import java.util.ArrayList;
import java.util.List;

import com.escriba.cartorio.model.Atribuicao;
import com.escriba.cartorio.model.Cartorio;
import com.escriba.cartorio.model.Situacao;

public class InstanceCreatorUtil {

	public static Situacao criarSituacao() {
		Situacao situacao = new Situacao();
		situacao.setId("id_situacao");
		situacao.setNome("Nome da Situacao");
		return situacao;
	}

	public static Atribuicao criarAtribuicao() {
		Atribuicao atribuicao = new Atribuicao();
		atribuicao.setId("id_atribuicao");
		atribuicao.setNome("Nome da Atribuicao");
		atribuicao.setSituacao(true);
		return atribuicao;
	}
	
	public static Cartorio criarCartorio() {
		 Cartorio cartorio = new Cartorio();
        cartorio.setNome("Nome do Cartorio");
        cartorio.setObservacao("Observação do Cartorio");
        cartorio.setSituacao(criarSituacao());
        
        List<Atribuicao> atribuicoes = new ArrayList<>();
        atribuicoes.add(criarAtribuicao());
        cartorio.setAtribuicoes(atribuicoes);

        return cartorio;
	}
	
}
