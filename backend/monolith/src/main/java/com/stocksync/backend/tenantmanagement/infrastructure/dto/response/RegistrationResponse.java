package com.stocksync.backend.tenantmanagement.infrastructure.dto.response;

import java.util.UUID;

public record RegistrationResponse(
    UUID tenantId,
    String message
) {


}
