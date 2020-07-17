package com.dev.republica.service;

import com.dev.republica.dto.DataChart;
import com.dev.republica.dto.FinancaRequest;
import com.dev.republica.dto.FinancaResponse;
import com.dev.republica.exception.FinancaNotFoundException;
import com.dev.republica.exception.MoradorNotFoundException;
import com.dev.republica.exception.MoradoresIsEmptyException;
import com.dev.republica.exception.RepublicaNotFoundException;
import com.dev.republica.mapper.FinancaMapper;
import com.dev.republica.model.*;
import com.dev.republica.repository.FinancaMoradorRepository;
import com.dev.republica.repository.FinancaRepository;
import com.dev.republica.repository.MoradorRepository;
import com.dev.republica.repository.RepublicaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional
public class FinancaService {

    private final GraficoService graficoService;
    private final FinancaRepository financaRepository;
    private final FinancaMoradorRepository financaMoradorRepository;
    private final MoradorRepository moradorRepository;
    private final RepublicaRepository republicaRepository;

    public FinancaResponse getFinanca(Long id) {
        Financa financa = financaRepository.findById(id)
                .orElseThrow(() -> new FinancaNotFoundException(id));

        return FinancaMapper.INSTANCE.financaToResponse(financa);
    }

    public List<FinancaResponse> getFinancaByRepublica(Long id, String tipo, String descricao, Date dataInicio,
                                                       Date dataFim, Boolean efetivado, String ordenarPor, int pagina,
                                                       int itemsPorPagina) {
        Republica republica = republicaRepository.findById(id)
                .orElseThrow(() -> new RepublicaNotFoundException(id));

        Page<Financa> financas = financaRepository.findByRepublica(
                republica, tipo, ("%" + descricao + "%"), dataInicio, dataFim, efetivado, PageRequest.of(pagina, itemsPorPagina, Sort.by(ordenarPor)));

        return FinancaMapper.INSTANCE.financasToResponse(financas.getContent());
    }

    public List<FinancaResponse> getFinancaByRepublicaAndMorador(Long idRepublica, Long idMorador, String tipo,
                                                                 String descricao, Date dataInicio, Date dataFim,
                                                                 String ordenarPor, int pagina,
                                                                 int itemsPorPagina) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador));

        Page<Financa> financas = financaRepository.findByRepublicaAndMorador(republica, morador, tipo,
                ("%" + descricao + "%"), dataInicio, dataFim, PageRequest.of(pagina, itemsPorPagina, Sort.by(ordenarPor)));

        return FinancaMapper.INSTANCE.financasToResponse(financas.getContent());
    }

    @Transactional
    public void save(FinancaRequest financaRequest, Long idRepublica) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        List<Morador> moradores = moradorRepository.findAllById(financaRequest.getMoradoresIds());

        if (moradores.isEmpty())
            throw new MoradoresIsEmptyException();

        List<FinancaMorador> financaMoradores = new ArrayList<>();

        for (int i = 0; i < financaRequest.getNumeroParcelas(); i++) {
            Financa financa;

            financa = FinancaMapper.INSTANCE.toFinanca(financaRequest, republica);

            if (financaRequest.getNumeroParcelas() > 1)
                financa.setDescricao(financa.getDescricao() + " " + (i + 1) + "/" + financaRequest.getNumeroParcelas());

            financa.setValor(financaRequest.getValor() / financaRequest.getNumeroParcelas());

            for (int j = 0; j < moradores.size(); j++) {
                financaMoradores.add(new FinancaMorador(financa, moradores.get(j), financa.getValor() / moradores.size()));
            }

            financa.setFinancaMoradores(financaMoradores);

            financaRepository.save(financa);
        }
    }

    @Transactional
    public FinancaResponse update(Long id, FinancaRequest financaRequest) {
        Financa financa = financaRepository.findById(id)
                .orElseThrow(() -> new FinancaNotFoundException(id));

        FinancaMapper.INSTANCE.updateFinancaFromRequest(financaRequest, financa);

        List<Morador> moradores = moradorRepository.findAllById(financaRequest.getMoradoresIds());

        if (moradores.isEmpty())
            throw new MoradoresIsEmptyException();

        List<FinancaMorador> list = new ArrayList<>();

        for (FinancaMorador financaMorador : financa.getFinancaMoradores()) {
            if (moradores.contains(financaMorador.getPk().getMorador())) {
                list.add(financaMoradorRepository.save(financaMorador));
                moradores.remove(financaMorador.getPk().getMorador());
            } else {
                financaMoradorRepository.delete(financaMorador);
            }
        }

        for (int i = 0; i < moradores.size(); i++) {
            list.add(financaMoradorRepository.save(new FinancaMorador(financa, moradores.get(i), financa.getValor() / moradores.size())));
        }

        financa.setFinancaMoradores(list);

        financaRepository.save(financa);

        return FinancaMapper.INSTANCE.financaToResponse(financa);

    }

    public void delete(Long id) {
        Financa financa = financaRepository.findById(id)
                .orElseThrow(() -> new FinancaNotFoundException(id));

        financaRepository.delete(financa);
    }

    public void pagar(Long idMorador, Long idFinanca) {
        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador));

        Financa financa = financaRepository.findById(idFinanca)
                .orElseThrow(() -> new FinancaNotFoundException(idFinanca));

        FinancaMorador financaMorador = financaMoradorRepository.findById(new FinancaMoradorId(financa, morador))
                .orElseThrow();

        financaMorador.setPago(true);
        financaMorador.setDataPagamento(new java.util.Date());

        financaMoradorRepository.save(financaMorador);

        financa = financaRepository.findById(idFinanca)
                .orElseThrow(() -> new FinancaNotFoundException(idFinanca));

        boolean controle = false;

        for (FinancaMorador financaMorador1 : financa.getFinancaMoradores()) {
            if (financaMorador1.isPago())
                controle = true;
        }

        if (controle)
            financa.setEfetivado(true);

        financaRepository.save(financa);
    }

    public DataChart getChart(@PathVariable Long idRepublica, @PathVariable int mes, @PathVariable int ano) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate termino = inicio.plusMonths(1);

        Map<Integer, Float> despesas = graficoService.getTotalDespesas(republica, java.sql.Date.valueOf(inicio),
                java.sql.Date.valueOf(termino));
        Map<Integer, Float> receitas = graficoService.getTotalReceitas(republica, java.sql.Date.valueOf(inicio),
                java.sql.Date.valueOf(termino));

        return new DataChart(idRepublica, despesas, receitas);
    }

}
