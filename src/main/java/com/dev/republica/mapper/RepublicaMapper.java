package com.dev.republica.mapper;

import com.dev.republica.dto.RepublicaRequest;
import com.dev.republica.dto.RepublicaResponse;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper(imports = Date.class, uses = MoradorMapper.class)
public interface RepublicaMapper {

    RepublicaMapper INSTANCE = Mappers.getMapper(RepublicaMapper.class);

    @Mapping(expression = "java(new Date())", target = "dataFundacao")
    @Mapping(source = "republicaRequest.nome", target = "nome")
    @Mapping(target = "dataExtincao", ignore = true)
    @Mapping(expression = "java(republicaRequest.getNumeroVagas() - 1)", target = "numeroVagasDisponiveis")
    @Mapping(source = "representante", target = "representante")
    Republica toRepublica(RepublicaRequest republicaRequest, Morador representante);

    RepublicaResponse republicaToResponse(Republica republica);

    void updateRepublicaFromRequest(RepublicaRequest republicaRequest, @MappingTarget Republica republica);

    List<RepublicaResponse> republicasToResponse(List<Republica> republica);

}
