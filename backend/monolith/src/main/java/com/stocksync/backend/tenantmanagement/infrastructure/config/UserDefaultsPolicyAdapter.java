package com.stocksync.backend.tenantmanagement.infrastructure.config;

import org.springframework.stereotype.Component;

import com.stocksync.backend.tenantmanagement.application.port.out.UserDefaultsPolicy;
import com.stocksync.backend.tenantmanagement.domain.model.UserStatus;

@Component
public class UserDefaultsPolicyAdapter implements UserDefaultsPolicy{

        private static final UserStatus DEFAULT_USER_STATUS = UserStatus.ACTIVE;

    @Override
    public UserStatus getDefaultStatus() {
        return DEFAULT_USER_STATUS;
    }

}
