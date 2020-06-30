package com.dev.republica.service;

import com.dev.republica.dto.RepublicaRequest;
import com.dev.republica.dto.RepublicaResponse;
import com.dev.republica.exception.MoradorNotFoundException;
import com.dev.republica.exception.RepublicaNotFoundException;
import com.dev.republica.mapper.RepublicaMapper;
import com.dev.republica.model.*;
import com.dev.republica.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class RepublicaService {

    private final AuthService authService;
    private final RoleService roleService;
    private final FinancaRepository financaRepository;
    private final HistoricoMoradorRepository historicoMoradorRepository;
    private final HistoricoRepresentanteRepository historicoRepresentanteRepository;
    private final MoradorRepository moradorRepository;
    private final RepublicaRepository republicaRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<RepublicaResponse> getRepublicasDisponiveis(String nome, String vantagens) {
        List<Republica> disponiveis = republicaRepository.findByNumeroVagasDisponiveisGreaterThanAndNomeContainingAndVantagensContaining(
                0, nome, vantagens);

        return RepublicaMapper.INSTANCE.republicasToResponse(disponiveis);
    }

    @Transactional(readOnly = true)
    public RepublicaResponse getRepublica(Long id) {
        Republica republica = republicaRepository.findById(id)
                .orElseThrow(() -> new RepublicaNotFoundException(id));

        return RepublicaMapper.INSTANCE.republicaToResponse(republica);
    }

    @Transactional
    public void save(RepublicaRequest republicaRequest) {
        Morador representante = authService.getCurrentUser().getMorador();

        Republica republica = republicaRepository.save(RepublicaMapper.INSTANCE.toRepublica(republicaRequest, representante));

        representante.setRepublica(republica);

        representante.setRepresentante(true);

        User userRepresentante = userRepository.findById(representante.getId())
                .orElseThrow();
        userRepresentante.addRole(roleService.getMoradorRole());
        userRepresentante.addRole(roleService.getRepresentanteRole());
        userRepository.save(userRepresentante);

        historicoMoradorRepository.save(new HistoricoMorador(representante, republica));

        historicoRepresentanteRepository.save(new HistoricoRepresentante(republica, representante));

        moradorRepository.save(representante);
    }

    public ResponseEntity<RepublicaResponse> update(Long id, RepublicaRequest republicaRequest) {
        Republica republica = republicaRepository.findById(id)
                .orElseThrow(() -> new RepublicaNotFoundException(id));

        if ((republica.getNumeroVagas() - republica.getNumeroVagasDisponiveis()) > republicaRequest.getNumeroVagas()) {
            return ResponseEntity.badRequest().build();
        }

        RepublicaMapper.INSTANCE.updateRepublicaFromRequest(republicaRequest, republica);

        republicaRepository.save(republica);

        return ResponseEntity.ok().body(RepublicaMapper.INSTANCE.republicaToResponse(republica));
    }

    public String delete(Long idRepublica) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        List<Financa> financas = financaRepository.findByRepublica(republica);

        boolean flag = true;

        for (Financa financa : financas) {
            if (!financa.isEfetivado()) {
                flag = false;
                break;
            }
        }

        if (flag) {
            for (Morador morador : republica.getMoradores()) {
                morador.setRepublica(null);
                morador.setRepresentante(false);

                HistoricoMorador historicoMorador = historicoMoradorRepository.findTopByMoradorOrderByIdDesc(morador);
                historicoMorador.setDataSaida(new Date());
                historicoMoradorRepository.save(historicoMorador);

                moradorRepository.save(morador);

                return "República excluída!";
            }
        }

        return "Não foi possível excluir a república! Despesas pendentes";
    }

    @Transactional
    public boolean alterarRepresentante(Long idRepublica, Long idNovoRepresentante) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        Morador antigoRepresentante = republica.getRepresentante();

        Morador novoRepresentante = moradorRepository.findById(idNovoRepresentante)
                .orElseThrow(() -> new MoradorNotFoundException(idNovoRepresentante));

        if (novoRepresentante.getRepublica().equals(republica)) {
            republica.alterarRepresentante(novoRepresentante);

            moradorRepository.save(antigoRepresentante);
            moradorRepository.save(novoRepresentante);

            User userAntigoRepresentante = userRepository.findById(antigoRepresentante.getId())
                    .orElseThrow();

            User userNovoRepresentante = userRepository.findById(novoRepresentante.getId())
                    .orElseThrow();

            Role representanteRole = roleService.getRepresentanteRole();

            userAntigoRepresentante.removeRole(representanteRole);
            userRepository.save(userAntigoRepresentante);

            userNovoRepresentante.addRole(representanteRole);
            userRepository.save(userNovoRepresentante);

            republicaRepository.save(republica);

            HistoricoRepresentante historicoRepresentante = historicoRepresentanteRepository.findTopByRepublicaOrderByIdDesc(republica);
            historicoRepresentante.setDataFimMandato(new Date());
            historicoRepresentanteRepository.save(historicoRepresentante);

            historicoRepresentanteRepository.save(new HistoricoRepresentante(republica, novoRepresentante));

            return true;
        }

        return false;
    }

}
