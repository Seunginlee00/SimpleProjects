package com.project.my.common.exception;

import io.swagger.v3.oas.annotations.Hidden;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  public static final String TRACE = "trace";

  @Value("${error.printStackTrace:false}")
  private boolean printStackTrace;

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
    return buildErrorResponse(ex, ex.getMessage(), HttpStatus.valueOf(statusCode.value()), request);
  }

  private ResponseEntity<Object> buildErrorResponse(Exception exception,
      String message,
      HttpStatus httpStatus,
      WebRequest request) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus.value(), message, LocalDateTime.now());
    if (printStackTrace && isTraceOn(request)) {
      errorResponseDto.setStackTrace(ExceptionUtils.getStackTrace(exception));
    }
    return ResponseEntity.status(httpStatus).body(errorResponseDto);
  }

  private boolean isTraceOn(WebRequest request) {
    String[] value = request.getParameterValues(TRACE);
    return Objects.nonNull(value)
        && value.length > 0
        && value[0].contentEquals("true");
  }

  // 412 Validate Exception
  @Override
  @Hidden
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        "Validation error. Check 'errors' field for details.",
        LocalDateTime.now()
    );

    // 유효성 검사 오류를 추가
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      errorResponseDto.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.unprocessableEntity().body(errorResponseDto);
  }

  // 403 Access Denied Exception
  @ExceptionHandler(AccessDeniedException.class)
  @Hidden
  @ResponseStatus(HttpStatus.FORBIDDEN) // 403 Forbidden
  public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
    log.error("Access denied", exception);
    return buildErrorResponse(exception, "Access denied", HttpStatus.FORBIDDEN, request);
  }

  // 409 AlreadyExistElementException
  @ExceptionHandler(AlreadyExistElementException.class)
  @Hidden
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Object> handleAlreadyExistElementException(AlreadyExistElementException alreadyExistElementException, WebRequest request){
    log.error("Failed to element is already exist", alreadyExistElementException);
    return buildErrorResponse(alreadyExistElementException, alreadyExistElementException.getMessage(), HttpStatus.CONFLICT, request);
  }

  // 404 notfound
  @ExceptionHandler(NotFoundElementException.class)
  @Hidden
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleNotFoundElementException(
      NotFoundElementException notFoundException, WebRequest request){
    log.error("Failed to element is not found", notFoundException);
    return buildErrorResponse(notFoundException, notFoundException.getMessage(), HttpStatus.NOT_FOUND, request);
  }


  // 500 Uncaught Exception
  @ExceptionHandler(Exception.class)
  @Hidden
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
    log.error("Internal error occurred", exception);
    return buildErrorResponse(exception, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }



}