package com.stocksync.backend.tenantmanagement.infrastructure.web.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.stocksync.backend.tenantmanagement.application.exception.EmailAlreadyExistsException;

@ControllerAdvice(basePackages = "com.stocksync.backend.tenantmanagement.infrastructure.web.controller")
public class TenantManagementExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex, WebRequest request) {
        TenantManagementErrorCode errorCode = TenantManagementErrorCode.EMAIL_ALREADY_EXISTS;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(errorCode.httpStatus().value())
                .error(errorCode.defaultMessage())
                .message(ex.getMessage())
                .path(getPath(request))
                .code(errorCode.code())
                .build();
        return new ResponseEntity<>(errorResponse, errorCode.httpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        TenantManagementErrorCode errorCode = TenantManagementErrorCode.INVALID_ARGUMENT;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(errorCode.httpStatus().value())
                .error(errorCode.defaultMessage())
                .message(ex.getMessage())
                .path(getPath(request))
                .code(errorCode.code())
                .details(null)
                .build();
        return new ResponseEntity<>(errorResponse, errorCode.httpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        TenantManagementErrorCode errorCode = TenantManagementErrorCode.INVALID_ARGUMENT;

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(TenantManagementErrorCode.INVALID_ARGUMENT.httpStatus().value())
                .error(TenantManagementErrorCode.INVALID_ARGUMENT.defaultMessage())
                .message(ex.getMessage())
                .path(getPath(request))
                .code(TenantManagementErrorCode.INVALID_ARGUMENT.code())
                .details(errors)
                .build();

        return new ResponseEntity<>(errorResponse, errorCode.httpStatus());
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

}
