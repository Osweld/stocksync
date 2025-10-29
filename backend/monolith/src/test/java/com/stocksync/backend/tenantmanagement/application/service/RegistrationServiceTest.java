package com.stocksync.backend.tenantmanagement.application.service;

import com.stocksync.backend.tenantmanagement.application.command.RegisterCommand;
import com.stocksync.backend.tenantmanagement.application.port.out.TenantDefaultsPolicy;
import com.stocksync.backend.tenantmanagement.application.port.out.TenantRepositoryPort;
import com.stocksync.backend.tenantmanagement.application.port.out.UserDefaultsPolicy;
import com.stocksync.backend.tenantmanagement.application.port.out.UserRepositoryPort;
import com.stocksync.backend.tenantmanagement.domain.model.PlanId;
import com.stocksync.backend.tenantmanagement.domain.model.Role;
import com.stocksync.backend.tenantmanagement.domain.model.Tenant;
import com.stocksync.backend.tenantmanagement.domain.model.TenantStatus;
import com.stocksync.backend.tenantmanagement.domain.model.User;
import com.stocksync.backend.tenantmanagement.domain.model.UserStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private TenantDefaultsPolicy tenantDefaultsPolicy;
    @Mock
    private UserRepositoryPort userRepository;
    @Mock
    private TenantRepositoryPort tenantRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserDefaultsPolicy userDefaultsPolicy;

    @InjectMocks
    private RegistrationService registrationService;

    private RegisterCommand validCommand;
    private static final PlanId DEFAULT_PLAN_ID = new PlanId("FREE_PLAN");
    private static final TenantStatus INITIAL_STATUS = TenantStatus.ACTIVE;
    private static final UserStatus DEFAULT_USER_STATUS = UserStatus.ACTIVE;
    private static final String VALID_PASSWORD = "SecurePass123";
    private static final String HASHED_PASSWORD = "hashed_SecurePass123";
    private static final String VALID_EMAIL = "admin@acme.com";
    private static final String VALID_COMPANY_NAME = "Acme Corp";
    private static final String VALID_FIRST_NAME = "John";
    private static final String VALID_LAST_NAME = "Doe";

    @BeforeEach
    void setUp() {
        validCommand = new RegisterCommand(
            VALID_COMPANY_NAME,
            VALID_EMAIL,
            VALID_PASSWORD,
            VALID_FIRST_NAME,
            VALID_LAST_NAME
        );
    }


    @Nested
    @DisplayName("Successful Registration Scenarios")
    class SuccessfulRegistration {
        
        @Test
        @DisplayName("Should execute full transaction and return tenant ID when all data is valid")
        void register_ShouldExecuteFullTransaction_WhenAllDataIsValid() {
            // Given
            when(tenantDefaultsPolicy.getDefaultPlanId()).thenReturn(DEFAULT_PLAN_ID);
            when(tenantDefaultsPolicy.getInitialStatus()).thenReturn(INITIAL_STATUS);
            when(userDefaultsPolicy.getDefaultStatus()).thenReturn(DEFAULT_USER_STATUS);
            when(passwordEncoder.encode(VALID_PASSWORD)).thenReturn(HASHED_PASSWORD);
            when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
            when(tenantRepository.save(any(Tenant.class))).thenAnswer(invocation -> invocation.getArgument(0));
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // When
            UUID tenantId = registrationService.registerTenant(validCommand);

            // Then
            assertThat(tenantId).isNotNull();

            // Verify interactions
            verify(userRepository, times(1)).findByEmail(VALID_EMAIL);
            verify(tenantRepository, times(1)).save(any(Tenant.class));
            verify(userRepository, times(1)).save(any(User.class));
            verify(passwordEncoder, times(1)).encode(VALID_PASSWORD);

            // Verify domain objects creation
            ArgumentCaptor<Tenant> tenantCaptor = ArgumentCaptor.forClass(Tenant.class);
            verify(tenantRepository).save(tenantCaptor.capture());
            Tenant savedTenant = tenantCaptor.getValue();

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(userCaptor.capture());
            User savedUser = userCaptor.getValue();

            // Assert domain logic
            assertThat(savedTenant.getId()).isEqualTo(tenantId);
            assertThat(savedTenant.getPlanId()).isEqualTo(DEFAULT_PLAN_ID);
            assertThat(savedTenant.getStatus()).isEqualTo(INITIAL_STATUS);
            assertThat(savedUser.getTenantId()).isEqualTo(tenantId);
            assertThat(savedUser.getPasswordHash()).isEqualTo(HASHED_PASSWORD);
            assertThat(savedUser.getRoles()).contains(Role.ROLE_ADMIN);
        }
    }

}