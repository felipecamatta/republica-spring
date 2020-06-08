package com.dev.republica.service;

import com.dev.republica.dto.RepublicaRequest;
import com.dev.republica.dto.RepublicaResponse;
import com.dev.republica.exception.MoradorNotFoundException;
import com.dev.republica.exception.RepublicaNotFoundException;
import com.dev.republica.mapper.RepublicaMapper;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import com.dev.republica.model.Role;
import com.dev.republica.model.User;
import com.dev.republica.repository.MoradorRepository;
import com.dev.republica.repository.RepublicaRepository;
import com.dev.republica.repository.RoleRepository;
import com.dev.republica.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class RepublicaService {

    private final AuthService authService;
    private final RoleService roleService;
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
                .orElseThrow(() -> new RepublicaNotFoundException(id.toString()));

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

        moradorRepository.save(representante);
    }

    public ResponseEntity<RepublicaResponse> update(Long id, RepublicaRequest republicaRequest) {
        Republica republica = republicaRepository.findById(id)
                .orElseThrow(() -> new RepublicaNotFoundException(id.toString()));

        if ((republica.getNumeroVagas() - republica.getNumeroVagasDisponiveis()) > republicaRequest.getNumeroVagas()) {
            return ResponseEntity.badRequest().build();
        }

        RepublicaMapper.INSTANCE.updateRepublicaFromRequest(republicaRequest, republica);

        republicaRepository.save(republica);

        return ResponseEntity.ok().body(RepublicaMapper.INSTANCE.republicaToResponse(republica));
    }

    public void delete(Long id) {
    }

    public boolean adicionarMorador(Long idRepublica, Long idMorador) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica.toString()));

        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador.toString()));

        boolean status = republica.adicionarMorador(morador);

        if (status) {
            moradorRepository.save(morador);
            republicaRepository.save(republica);

            User userMorador = userRepository.findById(morador.getId())
                    .orElseThrow();
            userMorador.addRole(roleService.getMoradorRole());
            userRepository.save(userMorador);

            return true;
        }

        return false;
    }

    public boolean removerMorador(Long idRepublica, Long idMorador) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica.toString()));

        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador.toString()));

        boolean status = republica.removerMorador(morador);

        if (status) {
            moradorRepository.save(morador);
            republicaRepository.save(republica);

            User userMorador = userRepository.findById(morador.getId())
                    .orElseThrow();
            userMorador.removeRole(roleService.getMoradorRole());
            userRepository.save(userMorador);

            return true;
        }

        return false;
    }

    public boolean alterarRepresentante(Long idRepublica, Long idNovoRepresentante) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica.toString()));

        Morador antigoRepresentante = republica.getRepresentante();

        Morador novoRepresentante = moradorRepository.findById(idNovoRepresentante)
                .orElseThrow(() -> new MoradorNotFoundException(idNovoRepresentante.toString()));

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
            return true;
        }

        return false;
    }

}
