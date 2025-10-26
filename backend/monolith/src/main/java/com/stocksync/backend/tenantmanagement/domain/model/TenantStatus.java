package com.stocksync.backend.tenantmanagement.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TenantStatus {
    
    ACTIVE("Active", "Account is fully operational and in good standing."),
    PENDING("Pending Setup", "Account created but awaiting final internal setup or initial payment."),
    SUSPENDED("Suspended", "Account is locked, usually due to non-payment or a severe policy violation."),
    DELETED("Deleted", "Account has been marked for final deletion and is inaccessible.");

    private final String displayName;
    private final String description;
    
    public boolean isActive() {
        return this == ACTIVE;
    }
    
}
