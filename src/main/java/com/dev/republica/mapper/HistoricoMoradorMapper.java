package com.dev.republica.mapper;

import com.dev.republica.dto.HistoricoMoradorResponse;
import com.dev.republica.model.HistoricoMorador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HistoricoMoradorMapper {

    HistoricoMoradorMapper INSTANCE = Mappers.getMapper(HistoricoMoradorMapper.class);

    @Mapping(expression = "java(historicoMorador.getRepublica().getNome())", target = "republica")
    @Mapping(expression = "java(historicoMorador.getRepublica().getRepresentante().getNome())", target = "representante")
    @Mapping(expression = "java(historicoMorador.getRepublica().getRepresentante().getTelefone())", target = "contatoRepresentante")
    HistoricoMoradorResponse historicoMoradorToResponse(HistoricoMorador historicoMorador);

    List<HistoricoMoradorResponse> historicoMoradoresToResponse(List<HistoricoMorador> historicoMoradores);

}
