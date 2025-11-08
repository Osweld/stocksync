package com.stocksync.backend.tenantmanagement.application.command;


public record RegisterCommand(
    String companyName,
    String email,
    String rawPassword,
    String firstName,
    String lastName
) {}
