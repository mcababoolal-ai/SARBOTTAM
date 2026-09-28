ALTER TABLE student_guardians
  DROP CONSTRAINT student_guardians_student_id_fkey,
  ADD CONSTRAINT student_guardians_student_id_fkey
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE;

ALTER TABLE enrollments
  DROP CONSTRAINT enrollments_student_id_fkey,
  ADD CONSTRAINT enrollments_student_id_fkey
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE;
