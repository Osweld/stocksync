package com.stocksync.backend.tenantmanagement.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Tenant {

    private final UUID id;
    private final String companyName;
    private final TenantStatus status;
    private final PlanId planId;
    private final OffsetDateTime createdAt;

    public static Tenant register(String companyName, TenantStatus status, PlanId planId) {

        if(companyName == null || companyName.isBlank()){
            throw new IllegalArgumentException("Company name cannot be empty.");
        }

        if(planId == null || planId.value().isBlank()){
            throw new IllegalArgumentException("Plan ID cannot be empty.");
        }

        if(status == null){
            throw new IllegalArgumentException("Tenant status cannot be null.");
        }

        return new Tenant(
            UUID.randomUUID(),
            companyName,
            status,
            planId,
            OffsetDateTime.now()
        );
    }

}
