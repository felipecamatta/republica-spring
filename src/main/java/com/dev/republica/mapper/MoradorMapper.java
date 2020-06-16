package com.dev.republica.mapper;

import com.dev.republica.dto.MoradorRequest;
import com.dev.republica.dto.MoradorResponse;
import com.dev.republica.model.Morador;
import com.dev.republica.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface MoradorMapper {

    MoradorMapper INSTANCE = Mappers.getMapper(MoradorMapper.class);

    @Mapping(source = "user", target = "user")
    Morador toMorador(MoradorRequest moradorRequest, User user);

    void updateMoradorFromRequest(MoradorRequest moradorRequest, @MappingTarget Morador morador);

    @Mapping(expression = "java(morador.getRepublica() != null ? morador.getRepublica().getId() : null)", target = "idRepublica")
    MoradorResponse moradorToResponse(Morador morador);

    List<MoradorResponse> moradoresToResponse(List<Morador> moradores);

}
