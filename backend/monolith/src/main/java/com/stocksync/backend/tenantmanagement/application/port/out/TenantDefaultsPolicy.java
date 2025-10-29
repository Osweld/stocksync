package com.stocksync.backend.tenantmanagement.application.port.out;

import com.stocksync.backend.tenantmanagement.domain.model.PlanId;
import com.stocksync.backend.tenantmanagement.domain.model.TenantStatus;

public interface TenantDefaultsPolicy {
    PlanId getDefaultPlanId(); 
    TenantStatus getInitialStatus();
}
