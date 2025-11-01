package com.stocksync.backend.tenantmanagement.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("User Domain Model")
class UserTest {

    private static final UUID VALID_TENANT_ID = UUID.randomUUID();

    @Nested
    @DisplayName("When creating admin with invalid data")
    class InvalidAdminCreation {
        
        @ParameterizedTest
        @MethodSource("provideInvalidCreationScenarios")
        @DisplayName("Should reject invalid creation data")
        void shouldRejectInvalidCreationData(
            String scenario, UUID tenantId, String email, String passwordHash,
            String firstName, String lastName, UserStatus status, Set<Role> roles, 
            String expectedMessage) {
            
            assertThatThrownBy(() -> 
                User.createAdmin(tenantId, email, passwordHash, firstName, lastName, status, roles))
                .as("Failed scenario: %s", scenario)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
        }

        private static Stream<Arguments> provideInvalidCreationScenarios() {
            UUID validTenantId = UUID.randomUUID();
            String validEmail = "admin@acme.com";
            String validPasswordHash = "hashed_password";
            String validFirstName = "John";
            String validLastName = "Doe";
            UserStatus validStatus = UserStatus.ACTIVE;
            Set<Role> validRoles = Set.of(Role.ROLE_ADMIN);
            
            return Stream.of(
                Arguments.of("null tenantId", null, validEmail, validPasswordHash, 
                           validFirstName, validLastName, validStatus, validRoles, 
                           "tenantId cannot be null."),
                Arguments.of("null email", validTenantId, null, validPasswordHash, 
                           validFirstName, validLastName, validStatus, validRoles, 
                           "email cannot be empty."),
                Arguments.of("blank email", validTenantId, "   ", validPasswordHash, 
                           validFirstName, validLastName, validStatus, validRoles, 
                           "email cannot be empty."),
                Arguments.of("invalid email", validTenantId, "invalid-email", validPasswordHash, 
                           validFirstName, validLastName, validStatus, validRoles, 
                           "A valid email is required."),
                Arguments.of("null password", validTenantId, validEmail, null, 
                           validFirstName, validLastName, validStatus, validRoles, 
                           "passwordHash cannot be empty."),
                Arguments.of("blank password", validTenantId, validEmail, "   ", 
                           validFirstName, validLastName, validStatus, validRoles, 
                           "passwordHash cannot be empty."),
                Arguments.of("null firstName", validTenantId, validEmail, validPasswordHash,
                            null, validLastName, validStatus, validRoles, 
                            "firstName cannot be empty."),
                Arguments.of("blank firstName", validTenantId, validEmail, validPasswordHash,
                            "   ", validLastName, validStatus, validRoles, 
                            "firstName cannot be empty."),
                Arguments.of("null lastName", validTenantId, validEmail, validPasswordHash,
                            validFirstName, null, validStatus, validRoles, 
                            "lastName cannot be empty."),
                Arguments.of("blank lastName", validTenantId, validEmail, validPasswordHash,
                            validFirstName, "   ", validStatus, validRoles, 
                            "lastName cannot be empty."),
                Arguments.of("empty roles", validTenantId, validEmail, validPasswordHash, 
                           validFirstName, validLastName, validStatus, Set.of(), 
                           "At least one role is required."),
                Arguments.of("null roles", validTenantId, validEmail, validPasswordHash, 
                           validFirstName, validLastName, validStatus, null, 
                           "At least one role is required.")
            );
        }
    }
}
