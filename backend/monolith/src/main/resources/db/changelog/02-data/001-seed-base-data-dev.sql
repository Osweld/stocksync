
INSERT INTO plan (id, name, description)
VALUES
    ('FREE', 'Free Plan', 'Basic features for a single user.'),
    ('PREMIUM', 'Premium Plan', 'Full features for growing teams.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO tenant_status (id, name, description)
VALUES
    ('TRIAL', 'Trial', 'Active trial period.'),
    ('ACTIVE', 'Active', 'Account is active and payment is current.'),
    ('SUSPENDED', 'Suspended', 'Account is suspended due to non-payment or violation.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO role (name)
VALUES
    ('ROLE_ADMIN'),      -- Tenant Owner / Administrator
    ('ROLE_SALES'),      -- Salesperson (manages sales orders)
    ('ROLE_WAREHOUSE')   -- Warehouse Manager (manages inventory and purchases)
ON CONFLICT (name) DO NOTHING;



INSERT INTO tenant (id, company_name, plan_id, status_id)
VALUES
    ('a375562a-3850-4757-b5e3-c751b1fca0c5', 'StockSync Test Co.', 'PREMIUM', 'ACTIVE')
ON CONFLICT (id) DO NOTHING;
INSERT INTO "user" (id, tenant_id, email, password_hash, first_name, last_name, is_active)
VALUES
    ('a375562a-3850-4757-b5e3-c751b1fca0c4',
     'a375562a-3850-4757-b5e3-c751b1fca0c5',
     'admin@stocksync.com',
     '$2a$10$3z.g1.h.sL81l.V/8.5.GO0iP.G6W/y.c2GO.iC5l.k.jH.B.1qcS', -- 'password123'
     'Test',
     'Admin',
     true)
ON CONFLICT (tenant_id, email) DO NOTHING;


INSERT INTO user_roles (user_id, role_id)
SELECT
    'a375562a-3850-4757-b5e3-c751b1fca0c4', 
    r.id                                   
FROM role r
WHERE r.name IN ('ROLE_ADMIN', 'ROLE_SALES', 'ROLE_WAREHOUSE')
ON CONFLICT (user_id, role_id) DO NOTHING;