package com.stocksync.backend.tenantmanagement.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    
    ACTIVE("Active", "User can log in and use assigned roles."),
    PENDING("Pending Invitation", "User account created, but invitation email not yet confirmed."),
    SUSPENDED("Locked/Inactive", "User account has been locked or deactivated by the Tenant Admin."),
    DELETED("Deleted", "User account has been soft-deleted from the system.");

    private final String displayName;
    private final String description;
    
   
    public boolean isActive() {
        return this == ACTIVE;
    }
}