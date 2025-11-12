package com.stocksync.backend.tenantmanagement.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.stocksync.backend.tenantmanagement.domain.model.PlanId;
import com.stocksync.backend.tenantmanagement.domain.model.Tenant;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.entity.PlanEntity;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.entity.TenantEntity;

@Component
public class TenantMapper {


    public Tenant toDomain(TenantEntity entity) {

        if (entity == null) {
            return null;
        }
        return new Tenant(
            entity.getId(),
            entity.getCompanyName(),
            entity.getTenantStatus(),
            new PlanId(entity.getPlan().getId()),
            entity.getCreatedAt()
        );
    }

    public TenantEntity toEntity(Tenant tenant) {
        if (tenant == null) {
            return null;
        }

        PlanEntity planEntity = new PlanEntity();
        planEntity.setId(tenant.getPlanId().value());

        TenantEntity entity = new TenantEntity();
        entity.setId(tenant.getId());
        entity.setCompanyName(tenant.getCompanyName());
        entity.setTenantStatus(tenant.getStatus());
        entity.setPlan(planEntity);
        entity.setCreatedAt(tenant.getCreatedAt());
        return entity;
    }
}
