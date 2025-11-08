package com.stocksync.backend.tenantmanagement.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("User Domain Model")
class UserTest {

    @Nested
    @DisplayName("When creating admin user successfully")
    class SuccessfulCreation {

        @Test
        @DisplayName("Should create admin user with valid data")
        void shouldCreateAdminUserWithValidData() {
            UUID tenantId = UUID.randomUUID();
            Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_ADMIN));
            User user = User.createAdmin(
                    tenantId,
                    "user@example.com",
                    "hashed-password",
                    "John",
                    "Doe",
                    UserStatus.ACTIVE,
                    roles
            );

            assertThat(user).isNotNull();
            assertThat(user.getId()).isNotNull();
            assertThat(user.getTenantId()).isEqualTo(tenantId);
            assertThat(user.getEmail()).isEqualTo("user@example.com");
            assertThat(user.getPasswordHash()).isEqualTo("hashed-password");
            assertThat(user.getFirstName()).isEqualTo("John");
            assertThat(user.getLastName()).isEqualTo("Doe");
            assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
            assertThat(user.getRoles()).containsExactlyInAnyOrderElementsOf(Set.of(Role.ROLE_ADMIN));
            assertThat(user.getCreatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Should generate distinct ids for different users")
        void shouldGenerateDistinctIds() {
            Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_ADMIN));
            User u1 = User.createAdmin(UUID.randomUUID(), "a1@example.com", "h1", "F", "L", UserStatus.ACTIVE, roles);
            User u2 = User.createAdmin(UUID.randomUUID(), "a2@example.com", "h2", "F2", "L2", UserStatus.ACTIVE, roles);

            assertThat(u1.getId()).isNotEqualTo(u2.getId());
        }

        @Test
        @DisplayName("Returned roles set must be unmodifiable")
        void rolesSetMustBeUnmodifiable() {
            Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_ADMIN));
            User user = User.createAdmin(UUID.randomUUID(), "user@example.com", "h", "First", "Last", UserStatus.ACTIVE, roles);

            assertThatThrownBy(() -> user.getRoles().add(Role.ROLE_ADMIN))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("When creating admin user with invalid data")
    class ValidationTests {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should fail when email is null or blank")
        void shouldFailWhenEmailIsNullOrBlank(String invalidEmail) {
            Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_ADMIN));
            assertThatThrownBy(() -> User.createAdmin(UUID.randomUUID(), invalidEmail, "h", "F", "L", UserStatus.ACTIVE, roles))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("email cannot be empty.");
        }

        @ParameterizedTest
        @ValueSource(strings = { "invalid", "a@b", "user@domain", "user@domain,", "user@domain." })
        @DisplayName("Should fail when email format is invalid")
        void shouldFailWhenEmailIsMalformed(String badEmail) {
            Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_ADMIN));
            assertThatThrownBy(() -> User.createAdmin(UUID.randomUUID(), badEmail, "h", "F", "L", UserStatus.ACTIVE, roles))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("A valid email is required.");
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("Should fail when roles is null")
        void shouldFailWhenRolesIsNull(Set<Role> nullRoles) {
            assertThatThrownBy(() -> User.createAdmin(UUID.randomUUID(), "u@example.com", "h", "F", "L", UserStatus.ACTIVE, nullRoles))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("At least one role is required.");
        }

        @Test
        @DisplayName("Should fail when roles is empty")
        void shouldFailWhenRolesIsEmpty() {
            assertThatThrownBy(() -> User.createAdmin(UUID.randomUUID(), "u@example.com", "h", "F", "L", UserStatus.ACTIVE, new HashSet<>()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("At least one role is required.");
        }

        @Test
        @DisplayName("Should fail when tenantId is null")
        void shouldFailWhenTenantIdIsNull() {
            Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_ADMIN));
            assertThatThrownBy(() -> User.createAdmin(null, "u@example.com", "h", "F", "L", UserStatus.ACTIVE, roles))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("tenantId cannot be null.");
        }

        @Test
        @DisplayName("Should fail when userStatus is null")
        void shouldFailWhenUserStatusIsNull() {
            Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_ADMIN));
            assertThatThrownBy(() -> User.createAdmin(UUID.randomUUID(), "u@example.com", "h", "F", "L", null, roles))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("userStatus cannot be null.");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should fail when passwordHash is null or blank")
        void shouldFailWhenPasswordHashIsNullOrBlank(String badPasswordHash) {
            Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_ADMIN));
            assertThatThrownBy(() -> User.createAdmin(UUID.randomUUID(), "u@example.com", badPasswordHash, "F", "L", UserStatus.ACTIVE, roles))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("passwordHash cannot be empty.");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should fail when firstName is null or blank")
        void shouldFailWhenFirstNameIsNullOrBlank(String badFirstName) {
            Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_ADMIN));
            assertThatThrownBy(() -> User.createAdmin(UUID.randomUUID(), "u@example.com", "h", badFirstName, "L", UserStatus.ACTIVE, roles))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("firstName cannot be empty.");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should fail when lastName is null or blank")
        void shouldFailWhenLastNameIsNullOrBlank(String badLastName) {
            Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_ADMIN));
            assertThatThrownBy(() -> User.createAdmin(UUID.randomUUID(), "u@example.com", "h", "F", badLastName, UserStatus.ACTIVE, roles))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("lastName cannot be empty.");
        }
    }
}
