package com.stocksync.backend.tenantmanagement.application.exception;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String email) {
        super("Email is already registered by another user or tenant: " + email);
    }

}
