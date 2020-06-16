package com.dev.republica.mapper;

import com.dev.republica.dto.SolicitacaoResponse;
import com.dev.republica.model.Solicitacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SolicitacaoMapper {

    SolicitacaoMapper INSTANCE = Mappers.getMapper(SolicitacaoMapper.class);

    @Mapping(expression = "java(solicitacao.getSolicitante().getNome())", target = "moradorNome")
    @Mapping(expression = "java(solicitacao.getRepublica().getNome())", target = "republicaNome")
    @Mapping(source = "status", target = "status")
    SolicitacaoResponse solicitacaoToResponse(Solicitacao solicitacao);

    List<SolicitacaoResponse> solicitacoesToResponse(List<Solicitacao> solicitacoes);

}
