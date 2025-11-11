package com.stocksync.backend.tenantmanagement.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.stocksync.backend.tenantmanagement.application.port.out.TenantRepositoryPort;
import com.stocksync.backend.tenantmanagement.domain.model.Tenant;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.entity.TenantEntity;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.mapper.TenantMapper;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.repository.TenantJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TenantRepositoryAdapter implements TenantRepositoryPort {

    private final TenantJpaRepository tenantJpaRepository;
    private final TenantMapper tenantMapper;

    @Override
    public Optional<Tenant> findById(UUID tenantId) {
        return tenantJpaRepository.findById(tenantId)
                .map(tenantMapper::toDomain);
    }

    @Override
    public Tenant save(Tenant tenant) {

        TenantEntity entity = tenantMapper.toEntity(tenant);
        TenantEntity savedEntity = tenantJpaRepository.save(entity);
        return tenantMapper.toDomain(savedEntity);
       
    }


}
