package com.stocksync.backend.tenantmanagement.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tenant Domain Model")
class TenantTest {

    private static final String VALID_COMPANY_NAME = "Empresa Válida S.A.";
    private static final PlanId VALID_PLAN_ID = new PlanId("PREMIUM");
    private static final TenantStatus VALID_STATUS = TenantStatus.ACTIVE;
    
    // Invalid test data constants
    private static final String BLANK_COMPANY_NAME = "   ";
    private static final PlanId BLANK_PLAN_ID = new PlanId("   ");

    @Nested
    @DisplayName("When creating with invalid data")
    class InvalidCreation {
        
        @Test
        @DisplayName("Should throw exception when company name is blank")
        void companyNameBlank() {
            assertThatThrownBy(() -> Tenant.register(BLANK_COMPANY_NAME, VALID_STATUS, VALID_PLAN_ID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Company name cannot be empty.");
        }

        @Test
        @DisplayName("Should throw exception when company name is null")
        void companyNameNull() {
            assertThatThrownBy(() -> Tenant.register(null, VALID_STATUS, VALID_PLAN_ID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Company name cannot be empty.");
        }

        @Test
        @DisplayName("Should throw exception when Plan ID is blank")
        void planIdBlank() {
            assertThatThrownBy(() -> Tenant.register(VALID_COMPANY_NAME, VALID_STATUS, BLANK_PLAN_ID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Plan ID cannot be empty.");
        }

        @Test
        @DisplayName("Should throw exception when Plan ID is null")
        void planIdNull() {
            assertThatThrownBy(() -> Tenant.register(VALID_COMPANY_NAME, VALID_STATUS, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Plan ID cannot be empty.");
        }

        @Test
        @DisplayName("Should throw exception when Tenant Status is null")
        void tenantStatusNull() {
            assertThatThrownBy(() -> Tenant.register(VALID_COMPANY_NAME, null, VALID_PLAN_ID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Tenant status cannot be null.");
        }
    }

    @Nested
    @DisplayName("When creating with valid data")
    class ValidCreation {
        
        @Test
        @DisplayName("Should create tenant with all attributes set")
        void shouldCreateTenantWithAllAttributes() {
            // When
            Tenant tenant = Tenant.register(VALID_COMPANY_NAME, VALID_STATUS, VALID_PLAN_ID);

            // Then
            assertThat(tenant)
                .extracting(
                    Tenant::getCompanyName,
                    Tenant::getPlanId, 
                    Tenant::getStatus
                )
                .containsExactly(
                    VALID_COMPANY_NAME,
                    VALID_PLAN_ID,
                    VALID_STATUS
                );
        }

        @Test
        @DisplayName("Should generate ID and creation timestamp")
        void shouldGenerateIdAndTimestamp() {
            // When
            Tenant tenant = Tenant.register(VALID_COMPANY_NAME, VALID_STATUS, VALID_PLAN_ID);

            // Then
            assertThat(tenant.getId())
                .as("Generated ID should not be null")
                .isNotNull();
                
            assertThat(tenant.getCreatedAt())
                .as("Creation timestamp should be set")
                .isNotNull();
        }
    }
}