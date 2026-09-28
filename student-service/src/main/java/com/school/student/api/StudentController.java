package com.school.student.api;

import com.school.student.application.AdmissionNumberGenerator;
import com.school.student.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/students")
class StudentController {
  private final StudentRepository students; private final AdmissionNumberGenerator numbers;
  StudentController(StudentRepository students, AdmissionNumberGenerator numbers) { this.students=students; this.numbers=numbers; }
  @GetMapping List<StudentResponse> list(@RequestHeader("X-School-Id") UUID schoolId) { return students.findAllBySchoolIdOrderByFirstNameAsc(schoolId).stream().map(StudentResponse::from).toList(); }
  @GetMapping("/{id}") StudentResponse get(@PathVariable("id") UUID id,@RequestHeader("X-School-Id") UUID schoolId) { return students.findByIdAndSchoolId(id,schoolId).map(StudentResponse::from).orElseThrow(()->new NotFoundException(id)); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Transactional StudentResponse create(@Valid @RequestBody CreateStudentRequest request,@RequestHeader("X-School-Id") UUID schoolId) {
    String admissionNo=numbers.next(schoolId);
    return StudentResponse.from(students.save(new Student(schoolId,admissionNo,request.firstName(),request.lastName(),request.dateOfBirth(),request.fatherName(),request.contactNumber(),request.aadhaarNumber(),request.samagraId(),request.address())));
  }
  @PutMapping("/{id}") StudentResponse update(@PathVariable("id") UUID id,@Valid @RequestBody CreateStudentRequest request,@RequestHeader("X-School-Id") UUID schoolId) {
    Student student=students.findByIdAndSchoolId(id,schoolId).orElseThrow(()->new NotFoundException(id));
    student.update(student.getAdmissionNo(),request.firstName(),request.lastName(),request.dateOfBirth(),request.fatherName(),request.contactNumber(),request.aadhaarNumber(),request.samagraId(),request.address());
    return StudentResponse.from(students.save(student));
  }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional void delete(@PathVariable("id") UUID id,@RequestHeader("X-School-Id") UUID schoolId) { Student student=students.findByIdAndSchoolId(id,schoolId).orElseThrow(()->new NotFoundException(id)); students.delete(student); students.flush(); }
  record CreateStudentRequest(@NotBlank @Size(max=100) String firstName,@Size(max=100) String lastName,@Past LocalDate dateOfBirth,@NotBlank @Size(max=200) String fatherName,@NotBlank @Pattern(regexp="^[0-9+ -]{7,20}$",message="Contact number is invalid") String contactNumber,@Pattern(regexp="^$|^[0-9]{12}$",message="Aadhaar number must contain 12 digits") String aadhaarNumber,@Size(max=30) String samagraId,@NotBlank @Size(max=1000) String address) { }
  record StudentResponse(UUID id,String admissionNo,String firstName,String lastName,LocalDate dateOfBirth,String fatherName,String contactNumber,String aadhaarNumber,String samagraId,String address,String status) { static StudentResponse from(Student s) { return new StudentResponse(s.getId(),s.getAdmissionNo(),s.getFirstName(),s.getLastName(),s.getDateOfBirth(),s.getFatherName(),s.getContactNumber(),s.getAadhaarNumber(),s.getSamagraId(),s.getAddress(),s.getStatus()); } }
  static class NotFoundException extends RuntimeException { NotFoundException(UUID id) { super("Student not found: "+id); } }
}
