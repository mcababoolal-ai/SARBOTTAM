package com.school.identity.domain;
import java.util.*;import org.springframework.data.jpa.repository.JpaRepository;
public interface UserAccountRepository extends JpaRepository<UserAccount,UUID>{List<UserAccount> findAllBySchoolIdOrderByDisplayNameAsc(UUID schoolId);Optional<UserAccount> findByIdAndSchoolId(UUID id,UUID schoolId);boolean existsBySchoolIdAndUsername(UUID schoolId,String username);boolean existsBySchoolIdAndEmail(UUID schoolId,String email);}
