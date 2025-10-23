CREATE TABLE plan(
        id VARCHAR(50) PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        description TEXT,
        CONSTRAINT uq_plan_name UNIQUE(name)
);

CREATE TABLE tenant_status (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    CONSTRAINT uq_tenant_status_name UNIQUE(name)
);

CREATE TABLE tenant (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_name VARCHAR(255) NOT NULL,
    plan_id VARCHAR(50) NOT NULL,
    status_id VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_tenant_plan
        FOREIGN KEY(plan_id) 
        REFERENCES plan(id) 
        ON DELETE RESTRICT,

    CONSTRAINT fk_tenant_status
        FOREIGN KEY(status_id) 
        REFERENCES tenant_status(id) 
        ON DELETE RESTRICT
);


CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE "user" ( 
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_tenant
        FOREIGN KEY(tenant_id) 
        REFERENCES tenant(id) 
        ON DELETE CASCADE, 
    CONSTRAINT uq_tenant_email UNIQUE(tenant_id, email)
);

CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    role_id INTEGER NOT NULL,

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY(user_id) 
        REFERENCES "user"(id) 
        ON DELETE CASCADE,

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY(role_id) 
        REFERENCES role(id) 
        ON DELETE CASCADE,

    PRIMARY KEY(user_id, role_id)
);
