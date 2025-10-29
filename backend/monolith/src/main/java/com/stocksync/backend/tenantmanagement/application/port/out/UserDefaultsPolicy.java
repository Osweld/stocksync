package com.stocksync.backend.tenantmanagement.application.port.out;

import com.stocksync.backend.tenantmanagement.domain.model.UserStatus;

public interface UserDefaultsPolicy {

    UserStatus getDefaultStatus();

}
