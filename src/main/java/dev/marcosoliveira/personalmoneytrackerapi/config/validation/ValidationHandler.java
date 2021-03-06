package dev.marcosoliveira.personalmoneytrackerapi.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationHandler {
  
  @Autowired
  private MessageSource messageSource;

  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public List<FormErrorDto> handle(MethodArgumentNotValidException exception){
    List<FormErrorDto> dtolist = new ArrayList<>();
    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

    fieldErrors.forEach(e -> {
      String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
      FormErrorDto dto = new FormErrorDto(e.getField(), message);
      dtolist.add(dto);
    });

    return dtolist;
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handle(HttpMessageNotReadableException exception){
    return ResponseEntity.badRequest().build();
  }
}
