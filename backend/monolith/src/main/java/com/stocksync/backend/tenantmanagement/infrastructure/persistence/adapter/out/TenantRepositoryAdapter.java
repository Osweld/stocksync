package com.stocksync.backend.tenantmanagement.infrastructure.persistence.adapter.out;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.stocksync.backend.tenantmanagement.application.port.out.TenantRepositoryPort;
import com.stocksync.backend.tenantmanagement.domain.model.Tenant;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.entity.TenantEntity;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.repository.TenantJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TenantRepositoryAdapter implements TenantRepositoryPort {

    private final TenantJpaRepository tenantJpaRepository;

    @Override
    public Optional<Tenant> findById(UUID tenantId) {
        return null;
    }

    @Override
    public Tenant save(Tenant tenant) {
        return null;
    }


}
