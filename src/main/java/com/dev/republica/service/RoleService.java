package com.dev.republica.service;

import com.dev.republica.model.Authorities;
import com.dev.republica.model.Role;
import com.dev.republica.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getSemTetoRole() {
        return roleRepository.findByName(Authorities.ROLE_SEMTETO)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }

    public Role getMoradorRole() {
        return roleRepository.findByName(Authorities.ROLE_MORADOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }

    public Role getRepresentanteRole() {
        return roleRepository.findByName(Authorities.ROLE_REPRESENTANTE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }

}
