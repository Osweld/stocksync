package com.stocksync.backend.tenantmanagement.infrastructure.web.exception;

import org.springframework.http.HttpStatus;

public enum TenantManagementErrorCode {
    TENANT_NOT_FOUND("TM-001", "Tenant not found", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("TM-002", "Email already exists", HttpStatus.CONFLICT),
    USER_NOT_FOUND("TM-003", "User not found", HttpStatus.NOT_FOUND),
    INVALID_TENANT_STATUS("TM-004", "Invalid tenant status", HttpStatus.BAD_REQUEST),
    INVALID_ARGUMENT("TM-005", "Invalid argument", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    TenantManagementErrorCode(String code, String defaultMessage, HttpStatus httpStatus) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }

    public String code() { return code; }
    public String defaultMessage() { return defaultMessage; }
    public HttpStatus httpStatus() { return httpStatus; }
}
