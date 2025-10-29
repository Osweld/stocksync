package com.stocksync.backend.tenantmanagement.application.port.in;

import java.util.UUID;

import com.stocksync.backend.tenantmanagement.application.command.RegisterCommand;

public interface RegisterTenantUseCase {

    UUID registerTenant(RegisterCommand command);

}
