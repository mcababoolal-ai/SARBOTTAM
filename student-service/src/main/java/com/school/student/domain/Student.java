package com.school.student.domain;

import jakarta.persistence.*;
import java.time.*;
import java.util.UUID;

@Entity @Table(name = "students")
public class Student {
  @Id private UUID id;
  @Column(name = "school_id", nullable = false) private UUID schoolId;
  @Column(name = "admission_no", nullable = false) private String admissionNo;
  @Column(name = "first_name", nullable = false) private String firstName;
  @Column(name = "last_name") private String lastName;
  @Column(name = "date_of_birth") private LocalDate dateOfBirth;
  @Column(name = "father_name") private String fatherName;
  @Column(name = "contact_number") private String contactNumber;
  @Column(name = "aadhaar_number") private String aadhaarNumber;
  @Column(name = "samagra_id") private String samagraId;
  @Column(name = "address") private String address;
  @Column(nullable = false) private String status;
  protected Student() { }
  public Student(UUID schoolId, String admissionNo, String firstName, String lastName, LocalDate dateOfBirth, String fatherName, String contactNumber, String aadhaarNumber, String samagraId, String address) { this.id=UUID.randomUUID(); this.schoolId=schoolId; update(admissionNo, firstName, lastName, dateOfBirth, fatherName, contactNumber, aadhaarNumber, samagraId, address); this.status="ACTIVE"; }
  public void update(String admissionNo, String firstName, String lastName, LocalDate dateOfBirth, String fatherName, String contactNumber, String aadhaarNumber, String samagraId, String address) { this.admissionNo=admissionNo; this.firstName=firstName; this.lastName=lastName; this.dateOfBirth=dateOfBirth; this.fatherName=fatherName; this.contactNumber=contactNumber; this.aadhaarNumber=aadhaarNumber; this.samagraId=samagraId; this.address=address; }
  public UUID getId(){return id;} public UUID getSchoolId(){return schoolId;} public String getAdmissionNo(){return admissionNo;} public String getFirstName(){return firstName;} public String getLastName(){return lastName;} public LocalDate getDateOfBirth(){return dateOfBirth;} public String getFatherName(){return fatherName;} public String getContactNumber(){return contactNumber;} public String getAadhaarNumber(){return aadhaarNumber;} public String getSamagraId(){return samagraId;} public String getAddress(){return address;} public String getStatus(){return status;}
}
