package com.school.identity.domain;
import java.util.*;import org.springframework.data.jpa.repository.JpaRepository;
public interface PermissionRepository extends JpaRepository<Permission,UUID>{List<Permission> findAllByOrderByCodeAsc();}
