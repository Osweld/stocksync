package com.stocksync.backend.tenantmanagement.application.port.out;

import java.util.Optional;

import com.stocksync.backend.tenantmanagement.domain.model.User;

public interface UserRepositoryPort {

    User save(User user);
    Optional<User> findByEmail(String email);

}
