package com.stocksync.backend.tenantmanagement.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.stocksync.backend.tenantmanagement.domain.model.Tenant;

public interface TenantRepositoryPort {

    Optional<Tenant> findById(UUID tenantId);
    Tenant save(Tenant tenant);

}
