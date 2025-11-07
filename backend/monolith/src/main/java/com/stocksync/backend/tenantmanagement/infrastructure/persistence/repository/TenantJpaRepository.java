package com.stocksync.backend.tenantmanagement.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stocksync.backend.tenantmanagement.infrastructure.persistence.entity.TenantEntity;

public interface TenantJpaRepository extends JpaRepository<TenantEntity, UUID> {

}
