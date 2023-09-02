package com.escriba.cartorio.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.escriba.cartorio.dto.SituacaoDTOCompleto;
import com.escriba.cartorio.model.Situacao;

@Mapper
public interface SituacaoMapper {
    SituacaoMapper INSTANCE = Mappers.getMapper(SituacaoMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    SituacaoDTOCompleto situacaoToSituacaoDTO(Situacao situacao);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    Situacao situacaoDTOToSituacao(SituacaoDTOCompleto situacaoDTO);

    List<SituacaoDTOCompleto> situacoesToSituacoesDTO(List<Situacao> situacoes);
}
