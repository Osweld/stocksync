package com.stocksync.backend.tenantmanagement.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Tenant Domain Model")
class TenantTest {

    @Nested
    @DisplayName("When registering tenant successfully")
    class SuccessfulRegistrationTests {

        @Test
        @DisplayName("Should create tenant with valid data")
        void shouldCreateTenantWithValidData() {

            Tenant tenant = Tenant.register("Mi Empresa", TenantStatus.ACTIVE, new PlanId("PREMIUM"));

            assertThat(tenant).isNotNull();
            assertThat(tenant.getCompanyName()).isEqualTo("Mi Empresa");
            assertThat(tenant.getStatus()).isEqualTo(TenantStatus.ACTIVE);
            assertThat(tenant.getPlanId().value()).isEqualTo("PREMIUM");
        }

        @Test
        @DisplayName("Should generate ID automatically")
        void shouldGenerateIdAutomatically() {
            Tenant tenant1 = Tenant.register("Empresa A", TenantStatus.ACTIVE, new PlanId("A"));
            Tenant tenant2 = Tenant.register("Empresa B", TenantStatus.ACTIVE, new PlanId("B"));

            assertThat(tenant1.getId()).isNotNull();
            assertThat(tenant2.getId()).isNotNull();
            assertThat(tenant1.getId()).isNotEqualTo(tenant2.getId());
        }

        @Test
        @DisplayName("Should set current timestamp")
        void shouldSetCurrentTimestamp() {

            OffsetDateTime before = OffsetDateTime.now();
            Tenant tenant = Tenant.register("Mi Empresa", TenantStatus.ACTIVE, new PlanId("PREMIUM"));
            OffsetDateTime after = OffsetDateTime.now();

            assertThat(tenant.getCreatedAt()).isNotNull();
            assertThat(tenant.getCreatedAt()).isAfterOrEqualTo(before);
            assertThat(tenant.getCreatedAt()).isBeforeOrEqualTo(after);
        }
    }

    @Nested
    @DisplayName("When registering tenant with invalid data")
    class ValidationTests {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { " ", "   ", "\t", "\n" })
        void shouldThrowExceptionWhenCompanyNameIsInvalid(String invalidName) {
            assertThatThrownBy(() -> Tenant.register(invalidName, TenantStatus.ACTIVE, new PlanId("PREMIUM")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Company name cannot be empty.");
        }

        @ParameterizedTest
        @NullSource
        void shouldThrowExceptionWhenPlanIdIsNull(PlanId nullPlanId) {
            assertThatThrownBy(() -> Tenant.register("Mi Empresa", TenantStatus.ACTIVE, nullPlanId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Plan ID cannot be empty.");
        }

        @Test
        void shouldThrowExceptionWhenPlanIdValueIsBlank() {
            assertThatThrownBy(() -> Tenant.register("Mi Empresa", TenantStatus.ACTIVE, new PlanId("   ")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Plan ID cannot be empty.");
        }

        @ParameterizedTest
        @NullSource
        void shouldThrowExceptionWhenStatusIsNull(TenantStatus nullStatus) {
            assertThatThrownBy(() -> Tenant.register("Mi Empresa", nullStatus, new PlanId("PREMIUM")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Tenant status cannot be null.");
        }
    }

}