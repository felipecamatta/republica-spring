package com.dev.republica.service;

import com.dev.republica.model.Financa;
import com.dev.republica.model.Republica;
import com.dev.republica.repository.FinancaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class GraficoService {

    private final FinancaRepository financaRepository;

    public Map<Integer, Float> getChartData(Republica republica, Date inicio, Date termino) {
        return null;
    }

    public Map<Integer, Float> getTotalReceitas(Republica republica, Date inicio, Date termino) {
        Map<Integer, Float> map = new HashMap<>();
        List<Financa> receitas = getReceitas(republica, inicio, termino);

        receitas.forEach(receita -> {
            LocalDate dataLancamento = receita.getDataLancamento().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();

            float valor = map.getOrDefault(dataLancamento.getDayOfMonth(), 0f);

            map.put(dataLancamento.getDayOfMonth(), valor + receita.getValor());
        });

        return map;
    }

    public Map<Integer, Float> getTotalDespesas(Republica republica, Date inicio, Date termino) {
        Map<Integer, Float> map = new HashMap<>();
        List<Financa> despesas = getDespesas(republica, inicio, termino);

        despesas.forEach(despesa -> {
            LocalDate dataLancamento = despesa.getDataLancamento().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();

            float valor = map.getOrDefault(dataLancamento.getDayOfMonth(), 0f);

            map.put(dataLancamento.getDayOfMonth(), valor - despesa.getValor());
        });

        return map;
    }

    public List<Financa> getReceitas(Republica republica, Date inicio, Date termino) {
        return financaRepository.findByRepublicaAndTipoAndDataLancamentoBetween(republica, "RECEITA", inicio,
                termino);
    }

    public List<Financa> getDespesas(Republica republica, Date inicio, Date termino) {
        return financaRepository.findByRepublicaAndTipoAndDataLancamentoBetween(republica, "DESPESA", inicio,
                termino);
    }

}
