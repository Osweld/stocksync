package com.stocksync.backend.tenantmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TenantTest {

    private static final String VALID_COMPANY_NAME = "Empresa Válida S.A.";
    private static final PlanId VALID_PLAN_ID = new PlanId("PREMIUM");
    private static final TenantStatus VALID_STATUS = TenantStatus.ACTIVE;

    private PlanId validPlanId;
    private TenantStatus validStatus;

    @BeforeEach
    void setUp() {
        validPlanId = VALID_PLAN_ID;
        validStatus = VALID_STATUS;
    }

    @Test
    @DisplayName("Should throw exception when company name is blank")
    void register_shouldThrowException_whenCompanyNameIsBlank() {

        String blankCompanyName = "   ";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Tenant.register(blankCompanyName, validStatus, validPlanId);
        });

        assertEquals("Company name cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when company name is null")
    void register_shouldThrowException_whenCompanyNameIsNull() {

        String blankCompanyName = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Tenant.register(blankCompanyName, validStatus, validPlanId);
        });

        assertEquals("Company name cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when Plan ID is blank")
    void register_shouldThrowException_whenPlanIdIsBlank() {

        PlanId blankPlanId = new PlanId("   ");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Tenant.register(VALID_COMPANY_NAME, validStatus, blankPlanId);
        });

        assertEquals("Plan ID cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when Plan ID is null")
    void register_shouldThrowException_whenPlanIdIsNull() {

        PlanId nullPlanId = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Tenant.register(VALID_COMPANY_NAME, validStatus, nullPlanId);
        });

        assertEquals("Plan ID cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when Tenant Status is null")
    void register_shouldThrowException_whenTenantStatusIsNull() {

        TenantStatus nullStatus = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Tenant.register(VALID_COMPANY_NAME, nullStatus, validPlanId);
        });

        assertEquals("Tenant status cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Should create Tenant when data is valid")
    void register_shouldCreateTenant_whenDataIsValid() {

        Tenant tenant = Tenant.register(VALID_COMPANY_NAME, VALID_STATUS, VALID_PLAN_ID);

        assertNotNull(tenant.getId(), "ID should be generated");
        assertEquals(VALID_COMPANY_NAME, tenant.getCompanyName());
        assertEquals(VALID_PLAN_ID, tenant.getPlanId());
        assertEquals(VALID_STATUS, tenant.getStatus());
        assertNotNull(tenant.getCreatedAt(), "CreatedAt should be set");
    }

}
