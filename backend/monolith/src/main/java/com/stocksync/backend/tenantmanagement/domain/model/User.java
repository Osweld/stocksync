package com.stocksync.backend.tenantmanagement.domain.model;

import java.util.Set;
import java.util.UUID;
import java.time.OffsetDateTime;
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
    private final OffsetDateTime createdAt;

    public static User createAdmin(UUID tenantId, String email, String passwordHash, String firstName, String lastName,
            UserStatus userStatus, Set<Role> roles) {

        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("At least one role is required.");
        }

        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId cannot be null.");
        }

        if (userStatus == null) {
            throw new IllegalArgumentException("userStatus cannot be null.");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email cannot be empty.");
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("A valid email is required.");
        }

        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("passwordHash cannot be empty.");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName cannot be empty.");
        }
        
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName cannot be empty.");
        }

        return new User(
                UUID.randomUUID(),
                tenantId,
                email,
                passwordHash,
                firstName,
                lastName,
                userStatus,
                Collections.unmodifiableSet(roles),
                OffsetDateTime.now());
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(this.roles);
    }

}
