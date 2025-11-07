
INSERT INTO plans (id, name, description)
VALUES
    ('TRIAL', 'Trial Plan', 'Limited features for a trial period.'),
    ('FREE', 'Free Plan', 'Basic features for a single user.'),
    ('PREMIUM', 'Premium Plan', 'Full features for growing teams.'),
    ('ENTERPRISE', 'Enterprise Plan', 'Advanced features for large organizations.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO tenants (id, company_name, plan_id, status_id)
VALUES
    ('a375562a-3850-4757-b5e3-c751b1fca0c5', 'StockSync Test Co.', 'PREMIUM', 'ACTIVE'),
    ('b475673b-4961-5868-c6f4-d862c2fdb1d6', 'Demo Corp', 'TRIAL', 'PENDING'),
    ('c586784c-5a72-6979-d7f5-e973d3fec2e7', 'Enterprise Ltd.', 'ENTERPRISE', 'ACTIVE')
ON CONFLICT (id) DO NOTHING;

INSERT INTO users (id, tenant_id, email, password_hash, first_name, last_name, status)
VALUES
    ('a375562a-3850-4757-b5e3-c751b1fca0c4',
     'a375562a-3850-4757-b5e3-c751b1fca0c5',
     'admin@stocksync.com',
     '$2a$10$3z.g1.h.sL81l.V/8.5.GO0iP.G6W/y.c2GO.iC5l.k.jH.B.1qcS', -- 'password123'
     'Test',
     'Admin',
     'ACTIVE'),
    ('b475673b-4961-5868-c6f4-d862c2fdb1d5',
     'b475673b-4961-5868-c6f4-d862c2fdb1d6',
     'demo@democorp.com',
     '$2a$10$3z.g1.h.sL81l.V/8.5.GO0iP.G6W/y.c2GO.iC5l.k.jH.B.1qcS', -- 'password123'
     'Demo',
     'User',
     'PENDING'),
    ('c586784c-5a72-6979-d7f5-e973d3fec2e6',
     'c586784c-5a72-6979-d7f5-e973d3fec2e7',
     'sales@enterpriseltd.com',
     '$2a$10$3z.g1.h.sL81l.V/8.5.GO0iP.G6W/y.c2GO.iC5l.k.jH.B.1qcS', -- 'password123'
     'Sales',
     'Manager',
     'ACTIVE'),
    ('d697895d-6b83-7a8a-e8f6-f084e4ffd3f8',
     'c586784c-5a72-6979-d7f5-e973d3fec2e7',
     'warehouse@enterpriseltd.com',
     '$2a$10$3z.g1.h.sL81l.V/8.5.GO0iP.G6W/y.c2GO.iC5l.k.jH.B.1qcS', -- 'password123'
     'Warehouse',
     'Operator',
     'SUSPENDED')
ON CONFLICT (tenant_id, email) DO NOTHING;

INSERT INTO user_roles (user_id, role_name)
VALUES
    ('a375562a-3850-4757-b5e3-c751b1fca0c4', 'ROLE_ADMIN'),
    ('a375562a-3850-4757-b5e3-c751b1fca0c4', 'ROLE_SALES'),
    ('a375562a-3850-4757-b5e3-c751b1fca0c4', 'ROLE_WAREHOUSE'),
    ('b475673b-4961-5868-c6f4-d862c2fdb1d5', 'ROLE_VIEWER'),
    ('c586784c-5a72-6979-d7f5-e973d3fec2e6', 'ROLE_SALES'),
    ('d697895d-6b83-7a8a-e8f6-f084e4ffd3f8', 'ROLE_WAREHOUSE')
ON CONFLICT (user_id, role_name) DO NOTHING;