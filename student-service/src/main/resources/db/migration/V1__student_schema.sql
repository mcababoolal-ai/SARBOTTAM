CREATE TABLE academic_years (
  id UUID PRIMARY KEY, school_id UUID NOT NULL, name VARCHAR(30) NOT NULL,
  starts_on DATE NOT NULL, ends_on DATE NOT NULL, active BOOLEAN NOT NULL DEFAULT false,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(), UNIQUE (school_id, name)
);
CREATE TABLE classes (
  id UUID PRIMARY KEY, school_id UUID NOT NULL, academic_year_id UUID NOT NULL REFERENCES academic_years(id),
  name VARCHAR(50) NOT NULL, created_at TIMESTAMPTZ NOT NULL DEFAULT now(), UNIQUE (school_id, academic_year_id, name)
);
CREATE TABLE sections (
  id UUID PRIMARY KEY, school_id UUID NOT NULL, class_id UUID NOT NULL REFERENCES classes(id),
  name VARCHAR(20) NOT NULL, capacity INTEGER, UNIQUE (school_id, class_id, name), CHECK (capacity IS NULL OR capacity > 0)
);
CREATE TABLE students (
  id UUID PRIMARY KEY, school_id UUID NOT NULL, admission_no VARCHAR(50) NOT NULL,
  first_name VARCHAR(100) NOT NULL, last_name VARCHAR(100), date_of_birth DATE, gender VARCHAR(20),
  status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE', created_at TIMESTAMPTZ NOT NULL DEFAULT now(), updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  UNIQUE (school_id, admission_no)
);
CREATE TABLE guardians (
  id UUID PRIMARY KEY, school_id UUID NOT NULL, full_name VARCHAR(200) NOT NULL, relationship VARCHAR(50) NOT NULL,
  phone VARCHAR(30), email VARCHAR(254), created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE student_guardians (
  student_id UUID NOT NULL REFERENCES students(id), guardian_id UUID NOT NULL REFERENCES guardians(id), is_primary BOOLEAN NOT NULL DEFAULT false,
  PRIMARY KEY (student_id, guardian_id)
);
CREATE TABLE enrollments (
  id UUID PRIMARY KEY, school_id UUID NOT NULL, student_id UUID NOT NULL REFERENCES students(id), academic_year_id UUID NOT NULL REFERENCES academic_years(id),
  section_id UUID REFERENCES sections(id), enrolled_on DATE NOT NULL, status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
  UNIQUE (school_id, student_id, academic_year_id)
);
CREATE TABLE outbox_events (
  id UUID PRIMARY KEY, event_type VARCHAR(150) NOT NULL, aggregate_id UUID NOT NULL, school_id UUID NOT NULL,
  payload JSONB NOT NULL, created_at TIMESTAMPTZ NOT NULL DEFAULT now(), published_at TIMESTAMPTZ
);
CREATE INDEX idx_students_school_name ON students (school_id, first_name, last_name);
CREATE INDEX idx_outbox_unpublished ON outbox_events (created_at) WHERE published_at IS NULL;
