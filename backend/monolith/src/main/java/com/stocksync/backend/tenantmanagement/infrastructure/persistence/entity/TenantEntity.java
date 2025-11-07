package com.stocksync.backend.tenantmanagement.infrastructure.persistence.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.stocksync.backend.tenantmanagement.domain.model.TenantStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tenants")
public class TenantEntity {


    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_id", nullable = false, length = 50)
    private TenantStatus statusId;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @ManyToOne()
    @JoinColumn(name = "plan_id", nullable = false)
    private PlanEntity plan;

    

}
