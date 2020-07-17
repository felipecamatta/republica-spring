package com.dev.republica.service;

import com.dev.republica.dto.MoradorTarefaResolver;
import com.dev.republica.dto.TarefaRequest;
import com.dev.republica.dto.TarefaResponse;
import com.dev.republica.exception.MoradorNotFoundException;
import com.dev.republica.exception.MoradoresIsEmptyException;
import com.dev.republica.exception.RepublicaNotFoundException;
import com.dev.republica.exception.TarefaNotFoundException;
import com.dev.republica.mapper.TarefaMapper;
import com.dev.republica.model.*;
import com.dev.republica.repository.MoradorRepository;
import com.dev.republica.repository.MoradorTarefaRepository;
import com.dev.republica.repository.RepublicaRepository;
import com.dev.republica.repository.TarefaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TarefaService {

    private final MoradorRepository moradorRepository;
    private final MoradorTarefaRepository moradorTarefaRepository;
    private final RepublicaRepository republicaRepository;
    private final TarefaRepository tarefaRepository;


    public TarefaResponse getTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNotFoundException(id));

        return TarefaMapper.INSTANCE.tarefaToResponse(tarefa);
    }

    public List<TarefaResponse> getTarefaByRepublica(Long id, String ordenarPor, int pagina, int itemsPorPagina) {
        Republica republica = republicaRepository.findById(id)
                .orElseThrow(() -> new RepublicaNotFoundException(id));

        Page<Tarefa> tarefas = tarefaRepository.findByRepublica(republica, PageRequest.of(pagina, itemsPorPagina, Sort.by(ordenarPor)));

        return TarefaMapper.INSTANCE.tarefasToResponse(tarefas.getContent());
    }

    public List<TarefaResponse> getTarefaByRepublicaAndMorador(Long idRepublica, Long idMorador, String ordenarPor, int pagina, int itemsPorPagina) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador));

        Page<Tarefa> tarefas = tarefaRepository.findByRepublicaAndMorador(republica, morador, PageRequest.of(pagina, itemsPorPagina, Sort.by(ordenarPor)));

        return TarefaMapper.INSTANCE.tarefasToResponse(tarefas.getContent());
    }

    @Transactional
    public void save(TarefaRequest tarefaRequest, Long idRepublica) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        Tarefa tarefa = TarefaMapper.INSTANCE.toTarefa(tarefaRequest, republica);

        List<Morador> moradores = moradorRepository.findAllById(tarefaRequest.getMoradoresIds());

        if (moradores.isEmpty())
            throw new MoradoresIsEmptyException();

        List<MoradorTarefa> list = new ArrayList<>();

        for (int i = 0; i < moradores.size(); i++) {
            list.add(new MoradorTarefa(moradores.get(i), tarefa));
        }

        tarefa.setMoradorTarefas(list);

        tarefaRepository.save(tarefa);
    }

    public TarefaResponse update(Long id, TarefaRequest tarefaRequest) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNotFoundException(id));

        TarefaMapper.INSTANCE.updateTarefaFromRequest(tarefaRequest, tarefa);

        List<Morador> moradores = moradorRepository.findAllById(tarefaRequest.getMoradoresIds());

        if (moradores.isEmpty())
            throw new MoradoresIsEmptyException();

        List<MoradorTarefa> list = new ArrayList<>();

        for (MoradorTarefa moradorTarefa : tarefa.getMoradorTarefas()) {
            if (moradores.contains(moradorTarefa.getPk().getMorador())) {
                list.add(moradorTarefaRepository.save(moradorTarefa));
                moradores.remove(moradorTarefa.getPk().getMorador());
            } else {
                moradorTarefaRepository.delete(moradorTarefa);
            }
        }

        for (int i = 0; i < moradores.size(); i++) {
            list.add(moradorTarefaRepository.save(new MoradorTarefa(moradores.get(i), tarefa)));
        }

        tarefa.setMoradorTarefas(list);

        tarefaRepository.save(tarefa);

        return TarefaMapper.INSTANCE.tarefaToResponse(tarefa);
    }

    public void delete(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNotFoundException(id));

        tarefaRepository.delete(tarefa);
    }

    @Transactional
    public void resolver(Long idMorador, Long idTarefa, MoradorTarefaResolver comentario) {
        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador));

        Tarefa tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNotFoundException(idTarefa));

        MoradorTarefa moradorTarefa = moradorTarefaRepository.findById(new MoradorTarefaId(morador, tarefa))
                .orElseThrow();

        moradorTarefa.setComentario(comentario.getComentario());
        moradorTarefa.setFinalizada(true);
        moradorTarefa.setDataFinalizada(new Date());

        moradorTarefaRepository.save(moradorTarefa);

        tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNotFoundException(idTarefa));

        boolean controle = false;

        for (MoradorTarefa moradorTarefa1 : tarefa.getMoradorTarefas()) {
            if (moradorTarefa1.isFinalizada())
                controle = true;
        }

        if (controle)
            tarefa.setFinalizada(true);

        tarefaRepository.save(tarefa);
    }

}
