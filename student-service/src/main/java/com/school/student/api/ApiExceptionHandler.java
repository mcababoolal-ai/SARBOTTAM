package com.school.student.api;

import java.time.Instant;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
class ApiExceptionHandler {
  @ExceptionHandler(StudentController.NotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND) ApiError notFound(RuntimeException e) { return new ApiError("STUDENT_NOT_FOUND", e.getMessage(), Instant.now()); }
  record ApiError(String code, String message, Instant timestamp) { }
}
