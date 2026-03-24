-- 1. TBL_USER_ROLE
CREATE TABLE IF NOT EXISTS tbl_user_role
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    role_type
    VARCHAR
(
    50
) NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_user_role_role_type
    ON tbl_user_role (role_type);


-- 2. TBL_STORE
CREATE TABLE IF NOT EXISTS tbl_store
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    brand
    VARCHAR
(
    100
) NOT NULL,
    description TEXT,
    store_type VARCHAR
(
    50
),
    status VARCHAR
(
    20
),
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
    );


CREATE INDEX IF NOT EXISTS idx_store_brand
    ON tbl_store (brand);

CREATE INDEX IF NOT EXISTS idx_store_store_type
    ON tbl_store (store_type);

CREATE INDEX IF NOT EXISTS idx_store_status
    ON tbl_store (status);


-- 3. TBL_USER_INFO
CREATE TABLE IF NOT EXISTS tbl_user_info
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    email
    VARCHAR
(
    255
) NOT NULL,
    full_name VARCHAR
(
    150
) NOT NULL,
    phone VARCHAR
(
    30
),
    password VARCHAR
(
    255
) NOT NULL,
    role_id BIGSERIAL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMPTZ
    );


-- 5. TBL_USER_STORE
CREATE TABLE IF NOT EXISTS tbl_user_store
(
    user_id
    BIGSERIAL
    NOT
    NULL,
    store_id
    BIGINT
    NOT
    NULL,
    created_at
    TIMESTAMPTZ
    DEFAULT
    CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_user_info_email
    ON tbl_user_info (email);

CREATE INDEX IF NOT EXISTS idx_user_info_full_name
    ON tbl_user_info (full_name);

CREATE INDEX IF NOT EXISTS idx_user_info_phone
    ON tbl_user_info (phone);

CREATE INDEX IF NOT EXISTS idx_user_info_role_id
    ON tbl_user_info (role_id);

CREATE INDEX IF NOT EXISTS idx_user_info_store_id
    ON tbl_user_info (store_id);


-- 4. TBL_STORE_CONTACT
CREATE TABLE IF NOT EXISTS tbl_store_contact
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    store_id
    BIGINT
    NOT
    NULL,
    email
    VARCHAR
(
    255
),
    phone VARCHAR
(
    30
),
    address TEXT
    );


CREATE INDEX IF NOT EXISTS idx_store_contact_store_id
    ON tbl_store_contact (store_id);

CREATE INDEX IF NOT EXISTS idx_store_contact_email
    ON tbl_store_contact (email);

CREATE INDEX IF NOT EXISTS idx_store_contact_phone
    ON tbl_store_contact (phone);


-- Simple insert, skips if role_type already exists (PostgreSQL 9.5+)
INSERT INTO tbl_user_role (role_type)
SELECT role
FROM (VALUES ('ROLE_USER'),
             ('ROLE_ADMIN'),
             ('ROLE_STORE_ADMIN'),
             ('ROLE_BRANCH_CASHIER'),
             ('ROLE_BRANCH_MANAGER'),
             ('ROLE_STORE_MANAGER')) AS roles(role)
WHERE NOT EXISTS (SELECT 1
                  FROM tbl_user_role
                  WHERE role_type = roles.role);
