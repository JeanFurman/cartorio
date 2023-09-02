package com.escriba.cartorio.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.escriba.cartorio.dto.AtribuicaoDTOCompleto;
import com.escriba.cartorio.dto.AtribuicaoDTOSimplificado;
import com.escriba.cartorio.model.Atribuicao;

@Mapper
public interface AtribuicaoMapper {
    AtribuicaoMapper INSTANCE = Mappers.getMapper(AtribuicaoMapper.class);
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "situacao", source = "situacao")
    Atribuicao atribuicaoDTOCompletoToAtribuicao(AtribuicaoDTOCompleto dto);
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "situacao", source = "situacao")
    AtribuicaoDTOCompleto atribuicaoToAtribuicaoDTOCompleto(Atribuicao atribuicao);

    @Mapping(target = "situacao", constant = "true") 
    List<AtribuicaoDTOSimplificado> atribuicoesToAtribuicoesDTOSimplificados(List<Atribuicao> atribuicoes);
    
    List<AtribuicaoDTOCompleto> atribuicoesToAtribuicoesDTOCompletos(List<Atribuicao> atribuicoes);
}