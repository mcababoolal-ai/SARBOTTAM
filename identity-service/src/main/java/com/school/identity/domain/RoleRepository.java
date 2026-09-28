package com.school.identity.domain;
import java.util.*;import org.springframework.data.jpa.repository.JpaRepository;
public interface RoleRepository extends JpaRepository<Role,UUID>{List<Role> findAllByCodeIn(Collection<String> codes);List<Role> findAllByOrderByNameAsc();}
