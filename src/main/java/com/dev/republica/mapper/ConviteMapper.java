package com.dev.republica.mapper;

import com.dev.republica.dto.ConviteResponse;
import com.dev.republica.model.Convite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ConviteMapper {

    ConviteMapper INSTANCE = Mappers.getMapper(ConviteMapper.class);

    @Mapping(expression = "java(convite.getConvidado().getNome())", target = "moradorNome")
    @Mapping(expression = "java(convite.getRepublica().getNome())", target = "republicaNome")
    @Mapping(source = "status", target = "status")
    ConviteResponse conviteToResponse(Convite convite);

    List<ConviteResponse> convitesToResponse(List<Convite> convite);

}
