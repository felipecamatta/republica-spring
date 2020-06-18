package com.dev.republica.mapper;

import com.dev.republica.dto.FinancaMoradorDto;
import com.dev.republica.dto.FinancaRequest;
import com.dev.republica.dto.FinancaResponse;
import com.dev.republica.model.Financa;
import com.dev.republica.model.FinancaMorador;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper(imports = Date.class)
public interface FinancaMapper {

    FinancaMapper INSTANCE = Mappers.getMapper(FinancaMapper.class);

    @Mapping(source = "financa", target = "pk.financa")
    @Mapping(source = "morador", target = "pk.morador")
    FinancaMorador moradorToFinancaMorador(Financa financa, Morador morador);

    @Mapping(expression = "java(financaMorador.getPk().getFinanca().getId())", target = "financaId")
    @Mapping(expression = "java(financaMorador.getPk().getMorador().getId())", target = "moradorId")
    FinancaMoradorDto financaMoradorToFinancaMoradorDto(FinancaMorador financaMorador);

    List<FinancaMoradorDto> financaMoradoresToFinancaMoradoresDto(List<FinancaMorador> financaMoradores);

    @Mapping(target = "id", ignore = true)
    @Mapping(expression = "java(new Date())", target = "dataLancamento")
    @Mapping(source = "republica", target = "republica")
    Financa toFinanca(FinancaRequest financaRequest, Republica republica);

    @Mapping(expression = "java(financa.getRepublica().getId())", target = "republicaId")
    @Mapping(source = "financa.financaMoradores", target = "financaMoradoresDto")
    FinancaResponse financaToResponse(Financa financa);

    List<FinancaResponse> financasToResponse(List<Financa> financas);

    void updateFinancaFromRequest(FinancaRequest financaRequest, @MappingTarget Financa financa);

}
