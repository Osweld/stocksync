package com.stocksync.backend.tenantmanagement.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.stocksync.backend.tenantmanagement.domain.model.User;
import com.stocksync.backend.tenantmanagement.infrastructure.persistence.entity.UserEntity;

@Component
public class UserMapper {


    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setTenantId(user.getTenantId());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setStatus(user.getStatus());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setRoles(user.getRoles());
        return entity;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
            entity.getId(),
            entity.getTenantId(),
            entity.getEmail(),
            entity.getPasswordHash(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getStatus(),
            entity.getRoles(),
            entity.getCreatedAt()
           
        );
    }

}
