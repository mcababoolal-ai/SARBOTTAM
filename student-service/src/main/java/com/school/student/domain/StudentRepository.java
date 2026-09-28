package com.school.student.domain;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<Student, UUID> { Optional<Student> findByIdAndSchoolId(UUID id, UUID schoolId); List<Student> findAllBySchoolIdOrderByFirstNameAsc(UUID schoolId); boolean existsBySchoolIdAndAdmissionNo(UUID schoolId, String admissionNo); }
