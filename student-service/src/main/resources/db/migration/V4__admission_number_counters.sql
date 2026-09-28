CREATE TABLE admission_number_counters (
  school_id UUID NOT NULL,
  admission_date DATE NOT NULL,
  last_value INTEGER NOT NULL,
  PRIMARY KEY (school_id, admission_date),
  CHECK (last_value > 0)
);
