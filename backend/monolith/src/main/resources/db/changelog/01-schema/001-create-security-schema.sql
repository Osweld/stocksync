
CREATE TABLE plans (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    CONSTRAINT uq_plan_name UNIQUE(name)
);

CREATE TABLE tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_name VARCHAR(255) NOT NULL,
    plan_id VARCHAR(50) NOT NULL,
    status_id VARCHAR(50) NOT NULL, 
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_tenant_plan
        FOREIGN KEY(plan_id) 
        REFERENCES plans(id) 
        ON DELETE RESTRICT,
    CONSTRAINT chk_tenants_status
        CHECK (status_id IN ('ACTIVE', 'PENDING', 'SUSPENDED', 'DELETED'))
);

CREATE TABLE users ( 
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    status VARCHAR(50) NOT NULL, 
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_users_tenant
        FOREIGN KEY(tenant_id) 
        REFERENCES tenants(id) 
        ON DELETE CASCADE,
    CONSTRAINT chk_users_status
        CHECK (status IN ('ACTIVE', 'PENDING', 'SUSPENDED', 'DELETED')),
    CONSTRAINT uq_tenant_email UNIQUE(tenant_id, email)
);

CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    CONSTRAINT fk_user_roles_user
        FOREIGN KEY(user_id) 
        REFERENCES users(id) 
        ON DELETE CASCADE,
    CONSTRAINT chk_role_name
        CHECK (role_name IN ('ROLE_ADMIN', 'ROLE_SALES', 'ROLE_WAREHOUSE', 'ROLE_VIEWER')),

    PRIMARY KEY(user_id, role_name)
);
