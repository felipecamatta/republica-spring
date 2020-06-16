package com.dev.republica.mapper;

import com.dev.republica.dto.MoradorTarefaDto;
import com.dev.republica.dto.TarefaRequest;
import com.dev.republica.dto.TarefaResponse;
import com.dev.republica.model.Morador;
import com.dev.republica.model.MoradorTarefa;
import com.dev.republica.model.Republica;
import com.dev.republica.model.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper(imports = Date.class)
public interface TarefaMapper {

    TarefaMapper INSTANCE = Mappers.getMapper(TarefaMapper.class);

    @Mapping(source = "morador", target = "pk.morador")
    @Mapping(source = "tarefa", target = "pk.tarefa")
    MoradorTarefa moradorToMoradorTarefa(Morador morador, Tarefa tarefa);

    @Mapping(expression = "java(moradorTarefa.getPk().getMorador().getId())", target = "moradorId")
    @Mapping(expression = "java(moradorTarefa.getPk().getTarefa().getId())", target = "tarefaId")
    MoradorTarefaDto moradorTarefaToMoradorTarefaDto(MoradorTarefa moradorTarefa);

    List<MoradorTarefaDto> moradoresTarefaToMoradoresTarefaDto(List<MoradorTarefa> moradoresTarefa);

    @Mapping(target = "id", ignore = true)
    @Mapping(expression = "java(new Date())", target = "dataAgendamento")
    @Mapping(source = "republica", target = "republica")
    Tarefa toTarefa(TarefaRequest tarefaRequest, Republica republica);

    @Mapping(expression = "java(tarefa.getRepublica().getId())", target = "republicaId")
    @Mapping(source = "tarefa.moradorTarefas", target = "moradorTarefasDto")
    TarefaResponse tarefaToResponse(Tarefa tarefa);

    List<TarefaResponse> tarefasToResponse(List<Tarefa> tarefa);

    void updateTarefaFromRequest(TarefaRequest tarefaRequest, @MappingTarget Tarefa tarefa);

}
