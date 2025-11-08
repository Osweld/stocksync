package com.stocksync.backend.tenantmanagement.infrastructure.config;

import org.springframework.stereotype.Component;

import com.stocksync.backend.tenantmanagement.application.port.out.TenantDefaultsPolicy;
import com.stocksync.backend.tenantmanagement.domain.model.PlanId;
import com.stocksync.backend.tenantmanagement.domain.model.TenantStatus;
import com.stocksync.backend.tenantmanagement.domain.model.UserStatus;

@Component
public class TenantDefaultsPolicyAdapter implements TenantDefaultsPolicy {

    private static final PlanId DEFAULT_PLAN_ID = new PlanId("FREE");
    private static final TenantStatus INITIAL_TENANT_STATUS = TenantStatus.ACTIVE;
    private static final UserStatus DEFAULT_USER_STATUS = UserStatus.ACTIVE;


    @Override
    public PlanId getDefaultPlanId() {
        return DEFAULT_PLAN_ID;
    }

    @Override
    public TenantStatus getInitialStatus() {
        return INITIAL_TENANT_STATUS;   
    }

    @Override
    public UserStatus getDefaultUserStatus() {
        return DEFAULT_USER_STATUS;
    }

    

   

}
