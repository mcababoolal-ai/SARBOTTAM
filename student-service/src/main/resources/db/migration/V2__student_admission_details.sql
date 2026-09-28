ALTER TABLE students
  ADD COLUMN father_name VARCHAR(200),
  ADD COLUMN contact_number VARCHAR(20),
  ADD COLUMN aadhaar_number VARCHAR(12),
  ADD COLUMN samagra_id VARCHAR(30),
  ADD COLUMN address TEXT;

CREATE UNIQUE INDEX uq_students_school_aadhaar
  ON students (school_id, aadhaar_number)
  WHERE aadhaar_number IS NOT NULL;

CREATE UNIQUE INDEX uq_students_school_samagra
  ON students (school_id, samagra_id)
  WHERE samagra_id IS NOT NULL;
