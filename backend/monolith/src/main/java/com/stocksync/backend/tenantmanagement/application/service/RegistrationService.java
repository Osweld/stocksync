package com.stocksync.backend.tenantmanagement.application.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stocksync.backend.tenantmanagement.application.command.RegisterCommand;
import com.stocksync.backend.tenantmanagement.application.exception.EmailAlreadyExistsException;
import com.stocksync.backend.tenantmanagement.application.port.in.RegisterTenantUseCase;
import com.stocksync.backend.tenantmanagement.application.port.out.TenantDefaultsPolicy;
import com.stocksync.backend.tenantmanagement.application.port.out.TenantRepositoryPort;
import com.stocksync.backend.tenantmanagement.application.port.out.UserDefaultsPolicy;
import com.stocksync.backend.tenantmanagement.application.port.out.UserRepositoryPort;
import com.stocksync.backend.tenantmanagement.domain.model.Role;
import com.stocksync.backend.tenantmanagement.domain.model.Tenant;
import com.stocksync.backend.tenantmanagement.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationService implements RegisterTenantUseCase {

    private static final Set<Role> DEFAULT_ADMIN_ROLES = Set.of(Role.ROLE_ADMIN);

    private final TenantDefaultsPolicy tenantDefaultsPolicy;
    private final UserRepositoryPort userRepository;
    private final TenantRepositoryPort tenantRepository;
    private final UserDefaultsPolicy userDefaultsPolicy;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UUID registerTenant(RegisterCommand command) {

        validateCommand(command);

        Tenant tenant = Tenant.register(
                command.companyName(),
                tenantDefaultsPolicy.getInitialStatus(),
                tenantDefaultsPolicy.getDefaultPlanId());

        String encodedPassword = passwordEncoder.encode(command.rawPassword());

        User adminUser = User.createAdmin(
                tenant.getId(),
                command.email(),
                encodedPassword,
                command.firstName(),
                command.lastName(),
                userDefaultsPolicy.getDefaultStatus(),
                DEFAULT_ADMIN_ROLES);

        Tenant tenantSaved = tenantRepository.save(tenant);
        userRepository.save(adminUser);
        return tenantSaved.getId();

    }

    private void validateCommand(RegisterCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("RegisterCommand cannot be null");
        }
        // Validaciones básicas que no duplican las de Bean Validation
        if (userRepository.findByEmail(command.email()).isPresent()) {
            throw new EmailAlreadyExistsException(command.email());
        }
    }


}
