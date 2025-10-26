package com.stocksync.backend.tenantmanagement.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN("Tenant Administrator", "Allows full administrative control within the tenant's account."),
    ROLE_SALES("Sales Representative", "Allows creation and modification of Sales Orders and Customers."),
    ROLE_WAREHOUSE("Warehouse Operator", "Allows handling of Inventory, Stock Movements, and Purchase Orders."),
    ROLE_VIEWER("Viewer/Auditor", "Read-only access to most reports and data.");
    
    private final String displayName;
    private final String description;
}