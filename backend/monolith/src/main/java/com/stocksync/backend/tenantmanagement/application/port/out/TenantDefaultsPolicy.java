package com.stocksync.backend.tenantmanagement.application.port.out;

import com.stocksync.backend.tenantmanagement.domain.model.PlanId;
import com.stocksync.backend.tenantmanagement.domain.model.TenantStatus;
import com.stocksync.backend.tenantmanagement.domain.model.UserStatus;

public interface TenantDefaultsPolicy {
    PlanId getDefaultPlanId(); 
    TenantStatus getInitialStatus();
    UserStatus getDefaultUserStatus();
}
