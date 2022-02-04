package dev.marcosoliveira.personalmoneytrackerapi.config.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class FormErrorDto {
  
  private String field;
  private String error;


}
