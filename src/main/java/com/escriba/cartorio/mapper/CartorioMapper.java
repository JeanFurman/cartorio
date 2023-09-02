package com.escriba.cartorio.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.escriba.cartorio.dto.CartorioDTOCompleto;
import com.escriba.cartorio.dto.CartorioDTOSimplificado;
import com.escriba.cartorio.model.Cartorio;

@Mapper
public interface CartorioMapper {
    CartorioMapper INSTANCE = Mappers.getMapper(CartorioMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "observacao", source = "observacao")
    @Mapping(target = "situacao", source = "situacao")
    @Mapping(target = "atribuicoes", source = "atribuicoes")
    Cartorio cartorioDTOCompletoToCartorio(CartorioDTOCompleto dtoCompleto);
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "observacao", source = "observacao")
    @Mapping(target = "situacao", source = "situacao")
    @Mapping(target = "atribuicoes", source = "atribuicoes")
    CartorioDTOCompleto cartorioToCartorioDTOCompleto(Cartorio cartorio);

    @Mapping(target = "observacao", ignore = true)
    @Mapping(target = "situacao", ignore = true)
    @Mapping(target = "atribuicoes", ignore = true)
    List<CartorioDTOSimplificado> cartoriosToCartoriosDTOSimplificados(List<Cartorio> cartorios);

    List<CartorioDTOCompleto> cartoriosToCartoriosDTOCompletos(List<Cartorio> cartorios);
}