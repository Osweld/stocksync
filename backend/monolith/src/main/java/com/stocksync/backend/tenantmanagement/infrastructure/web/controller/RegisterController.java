package com.stocksync.backend.tenantmanagement.infrastructure.web.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stocksync.backend.tenantmanagement.application.command.RegisterCommand;
import com.stocksync.backend.tenantmanagement.application.service.RegistrationService;
import com.stocksync.backend.tenantmanagement.infrastructure.dto.request.RegistrationRequest;
import com.stocksync.backend.tenantmanagement.infrastructure.dto.response.RegistrationResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/register")
@RequiredArgsConstructor
public class RegisterController {

    private static final String SUCCESSFUL_REGISTRATION_MESSAGE = "Registration successful. Please check your email to verify your account.";

    private final RegistrationService registrationService;


    ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest request) {
        UUID tenantId = registrationService.registerTenant(
            new RegisterCommand(
              request.companyName(),
                request.email(),
                request.rawPassword(),
                request.firstName(),
                request.lastName()
            )
        );
        return new ResponseEntity<>(new RegistrationResponse(
            tenantId, SUCCESSFUL_REGISTRATION_MESSAGE
        ), HttpStatus.CREATED);
    }
}
