package com.project.my.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

  private final int status;
  private final String message;
  private final LocalDateTime time;
  private String stackTrace;
  private List<ValidationError> validErrors = new ArrayList<>(); // 초기화

  @Data
  @RequiredArgsConstructor
  private static class ValidationError {
    private final String field;
    private final String message;
  }

  // stackTrace가 존재하면 추가
  public void setStackTraceIfNeeded(String stackTrace) {
    if (stackTrace != null) {
      this.stackTrace = stackTrace;
    }
  }

  public void addValidationError(String field, String message){
    if (this.validErrors == null) {
      this.validErrors = new ArrayList<>();
    }
    this.validErrors.add(new ValidationError(field, message));
  }

}
