package com.stocksync.backend.tenantmanagement.domain.model;

import java.util.Set;
import java.util.UUID;
import java.util.Collections;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private final UUID id;
    private final UUID tenantId;
    private final String email;
    private final String passwordHash;
    private final String firstName;
    private final String lastName;
    private final UserStatus status;
    private final Set<Role> roles;

    public static User createAdmin(UUID tenantId, String email, String passwordHash, String firstName, String lastName, UserStatus initialStatus, Set<Role> roles) {

        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID is required.");
        }
        if (email == null || email.isBlank() || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("A valid email is required.");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash is required.");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name is required.");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name is required.");
        }
        if (initialStatus == null) {
            throw new IllegalArgumentException("Initial status is required.");
        }
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("At least one role is required.");
        }

        return new User(
                UUID.randomUUID(),
                tenantId,
                email,
                passwordHash,
                firstName,
                lastName,
                initialStatus,
                Collections.unmodifiableSet(roles)
        );
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(this.roles);
    }

}
