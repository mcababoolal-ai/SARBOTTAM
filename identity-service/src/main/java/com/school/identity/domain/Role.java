package com.school.identity.domain;
import jakarta.persistence.*;import java.util.*;
@Entity @Table(name="roles") public class Role { @Id private UUID id; @Column(nullable=false) private String code; @Column(nullable=false) private String name; protected Role(){} public UUID getId(){return id;} public String getCode(){return code;} public String getName(){return name;} }
