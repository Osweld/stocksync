package com.stocksync.backend.tenantmanagement.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.stocksync.backend.tenantmanagement.application.port.out.UserRepositoryPort;
import com.stocksync.backend.tenantmanagement.domain.model.User;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.entity.UserEntity;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.mapper.UserMapper;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return userMapper.toDomain(savedEntity);
       
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

}
