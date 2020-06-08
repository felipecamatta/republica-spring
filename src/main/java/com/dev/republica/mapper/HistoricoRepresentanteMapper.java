package com.dev.republica.mapper;

import com.dev.republica.dto.HistoricoRepresentanteResponse;
import com.dev.republica.model.HistoricoRepresentante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HistoricoRepresentanteMapper {

    HistoricoRepresentanteMapper INSTANCE = Mappers.getMapper(HistoricoRepresentanteMapper.class);

    @Mapping(expression = "java(historicoRepresentante.getRepresentante().getNome())", target = "nomeRepresentante")
    HistoricoRepresentanteResponse historicoRepresentanteToResponse(HistoricoRepresentante historicoRepresentante);

    List<HistoricoRepresentanteResponse> historicoRepresentantesToResponse(List<HistoricoRepresentante> historicoRepresentantes);

}
