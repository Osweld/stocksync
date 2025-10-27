package com.stocksync.backend.tenantmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    private static final UUID VALID_TENANT_ID = UUID.randomUUID();
    private static final String VALID_EMAIL = "test@example.com";
    private static final String VALID_PASSWORD_HASH = "hashed_password";
    private static final String VALID_FIRST_NAME = "John";
    private static final String VALID_LAST_NAME = "Doe";
    private static final UserStatus VALID_STATUS = UserStatus.ACTIVE;
    private static final Set<Role> VALID_ROLES = Set.of(Role.ROLE_ADMIN, Role.ROLE_SALES, Role.ROLE_WAREHOUSE);

    //Role
    @Test
    @DisplayName("Should throw exception when roles set is empty")
    void register_shouldThrowException_whenRolesSetIsEmpty() {
        Set<Role> emptyRoles = Set.of();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_STATUS, emptyRoles);
        });

        assertEquals("At least one role is required.", exception.getMessage());
    }


    //TenantId
    @Test
    @DisplayName("Should throw exception when tenant id is null")
    void register_shouldThrowException_whenTenantIdIsNull() {

        UUID nullTenantId = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(nullTenantId, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_STATUS, VALID_ROLES);
        });

        assertEquals("tenantId cannot be null.", exception.getMessage());
    }

    //Email
    @Test
    @DisplayName("Should throw exception when email is invalid")
    void register_shouldThrowException_whenEmailIsInvalid() {

        String invalidEmail = "invalid_email";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, invalidEmail, VALID_PASSWORD_HASH, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_STATUS, VALID_ROLES);
        });

        assertEquals("A valid email is required.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when email is null")
    void register_shouldThrowException_whenEmailIsNull() {

        String nullEmail = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, nullEmail, VALID_PASSWORD_HASH, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_STATUS, VALID_ROLES);
        });

        assertEquals("email cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when email is blank")
    void register_shouldThrowException_whenEmailIsBlank() {
        String blankEmail = "   ";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, blankEmail, VALID_PASSWORD_HASH, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_STATUS, VALID_ROLES);
        });

        assertEquals("email cannot be empty.", exception.getMessage());
    }

    //PasswordHash
    @Test
    @DisplayName("Should throw exception when password hash is null")
    void register_shouldThrowException_whenPasswordHashIsNull() {

        String nullPasswordHash = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, VALID_EMAIL, nullPasswordHash, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_STATUS, VALID_ROLES);
        });

        assertEquals("passwordHash cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when password hash is blank")
    void register_shouldThrowException_whenPasswordHashIsBlank() {

        String blankPasswordHash = "   ";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, VALID_EMAIL, blankPasswordHash, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_STATUS, VALID_ROLES);
        });

        assertEquals("passwordHash cannot be empty.", exception.getMessage());
    }

    //FirstName
    @Test
    @DisplayName("Should throw exception when first name is null")
    void register_shouldThrowException_whenFirstNameIsNull() {
        String nullFirstName = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, VALID_EMAIL, VALID_PASSWORD_HASH, nullFirstName, VALID_LAST_NAME, VALID_STATUS, VALID_ROLES);
        });

        assertEquals("firstName cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when first name is blank")
    void register_shouldThrowException_whenFirstNameIsBlank() {

        String blankFirstName = "   ";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, VALID_EMAIL, VALID_PASSWORD_HASH, blankFirstName, VALID_LAST_NAME, VALID_STATUS, VALID_ROLES);
        });

        assertEquals("firstName cannot be empty.", exception.getMessage());
    }

    //LastName
    @Test
    @DisplayName("Should throw exception when last name is null")
    void register_shouldThrowException_whenLastNameIsNull() {

        String nullLastName = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_FIRST_NAME, nullLastName, VALID_STATUS, VALID_ROLES);
        });

        assertEquals("lastName cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when last name is blank")
    void register_shouldThrowException_whenLastNameIsBlank() {

        String blankLastName = "   ";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_FIRST_NAME, blankLastName, VALID_STATUS, VALID_ROLES);
        });

        assertEquals("lastName cannot be empty.", exception.getMessage());
    }

    //Status
    @Test
    @DisplayName("Should throw exception when status is null")
    void register_shouldThrowException_whenStatusIsNull() {
        
        UserStatus nullStatus = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User.createAdmin(VALID_TENANT_ID, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_FIRST_NAME, VALID_LAST_NAME, nullStatus, VALID_ROLES);
        });

        assertEquals("initialStatus cannot be null.", exception.getMessage());
    }
}
