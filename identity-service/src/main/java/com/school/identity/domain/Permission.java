package com.school.identity.domain;
import jakarta.persistence.*;import java.util.*;
@Entity @Table(name="permissions") public class Permission { @Id private UUID id; @Column(nullable=false) private String code; @Column(nullable=false) private String name; @Column(nullable=false) private String description; protected Permission(){} public UUID getId(){return id;} public String getCode(){return code;} public String getName(){return name;} public String getDescription(){return description;} }
