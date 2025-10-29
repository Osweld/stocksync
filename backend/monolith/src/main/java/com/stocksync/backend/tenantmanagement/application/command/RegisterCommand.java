package com.stocksync.backend.tenantmanagement.application.command;

import jakarta.validation.constraints.*;

public record RegisterCommand(
    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    String companyName,
    
    @NotBlank(message = "Email is required")
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "Email must be a valid email address"
    )
    String email,
    
    @NotBlank(message = "Password is required")
    @Size(min = 8,max = 16, message = "Password must be between 8 and 16 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
    )
    String rawPassword,
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 75, message = "First name must be between 2 and 75 characters")
    String firstName,
    
    @NotBlank(message = "Last name is required") 
    @Size(min = 2, max = 75, message = "Last name must be between 2 and 75 characters")
    String lastName
) {}
