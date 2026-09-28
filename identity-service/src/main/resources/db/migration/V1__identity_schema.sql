CREATE TABLE roles (
  id UUID PRIMARY KEY, code VARCHAR(50) NOT NULL UNIQUE, name VARCHAR(100) NOT NULL,
  description VARCHAR(500) NOT NULL, system_role BOOLEAN NOT NULL DEFAULT false
);
CREATE TABLE permissions (
  id UUID PRIMARY KEY, code VARCHAR(100) NOT NULL UNIQUE, name VARCHAR(150) NOT NULL,
  description VARCHAR(500) NOT NULL
);
CREATE TABLE role_permissions (
  role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
  permission_id UUID NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
  PRIMARY KEY (role_id, permission_id)
);
CREATE TABLE users (
  id UUID PRIMARY KEY, school_id UUID NOT NULL, keycloak_subject VARCHAR(100) UNIQUE,
  username VARCHAR(100) NOT NULL, email VARCHAR(254) NOT NULL, display_name VARCHAR(200) NOT NULL,
  status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE', created_at TIMESTAMPTZ NOT NULL DEFAULT now(), updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  UNIQUE (school_id, username), UNIQUE (school_id, email)
);
CREATE TABLE user_roles (
  user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  role_id UUID NOT NULL REFERENCES roles(id) ON DELETE RESTRICT,
  PRIMARY KEY (user_id, role_id)
);
CREATE INDEX idx_users_school_status ON users(school_id, status);

INSERT INTO roles (id, code, name, description, system_role) VALUES
  ('00000000-0000-0000-0000-000000000001','SCHOOL_ADMIN','School administrator','Full school administration access',true),
  ('00000000-0000-0000-0000-000000000002','PRINCIPAL','Principal','Academic and operational oversight',true),
  ('00000000-0000-0000-0000-000000000003','TEACHER','Teacher','Class and academic activity access',true),
  ('00000000-0000-0000-0000-000000000004','ACCOUNTANT','Accountant','Fees and finance access',true),
  ('00000000-0000-0000-0000-000000000005','PARENT','Parent','Access to linked child information',true),
  ('00000000-0000-0000-0000-000000000006','STUDENT','Student','Access to own academic information',true);

INSERT INTO permissions (id, code, name, description) VALUES
  ('10000000-0000-0000-0000-000000000001','USER_READ','View users','View users and role assignments'),
  ('10000000-0000-0000-0000-000000000002','USER_MANAGE','Manage users','Create users and assign roles'),
  ('10000000-0000-0000-0000-000000000003','STUDENT_READ','View students','View student records'),
  ('10000000-0000-0000-0000-000000000004','STUDENT_MANAGE','Manage students','Create and change student records'),
  ('10000000-0000-0000-0000-000000000005','FEE_MANAGE','Manage fees','Manage invoices and payments'),
  ('10000000-0000-0000-0000-000000000006','ATTENDANCE_MANAGE','Manage attendance','Record student attendance');

INSERT INTO role_permissions (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000001', id FROM permissions;
INSERT INTO role_permissions (role_id, permission_id) VALUES
  ('00000000-0000-0000-0000-000000000002','10000000-0000-0000-0000-000000000001'),
  ('00000000-0000-0000-0000-000000000002','10000000-0000-0000-0000-000000000003'),
  ('00000000-0000-0000-0000-000000000003','10000000-0000-0000-0000-000000000003'),
  ('00000000-0000-0000-0000-000000000003','10000000-0000-0000-0000-000000000006'),
  ('00000000-0000-0000-0000-000000000004','10000000-0000-0000-0000-000000000005');
