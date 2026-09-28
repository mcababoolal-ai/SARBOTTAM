package com.school.student.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmissionNumberGenerator {
  private static final String NEXT_NUMBER = """
      INSERT INTO admission_number_counters (school_id, admission_date, last_value)
      VALUES (?, CURRENT_DATE, 1)
      ON CONFLICT (school_id, admission_date)
      DO UPDATE SET last_value = admission_number_counters.last_value + 1
      RETURNING last_value
      """;
  private final JdbcTemplate jdbcTemplate;
  public AdmissionNumberGenerator(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }
  public String next(UUID schoolId) {
    Integer sequence = jdbcTemplate.queryForObject(NEXT_NUMBER, Integer.class, schoolId);
    return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + String.format("%03d", sequence);
  }
}
