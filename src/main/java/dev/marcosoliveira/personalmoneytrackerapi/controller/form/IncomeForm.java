package dev.marcosoliveira.personalmoneytrackerapi.controller.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import dev.marcosoliveira.personalmoneytrackerapi.model.Income;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IncomeForm {
  
  @NotNull @NotEmpty
  private String description;
  @NotNull 
  private BigDecimal value;
  @NotNull 
  private LocalDateTime date;

  public Income convert() {
    return new Income(description, value, date);
  }

  public Income update(Income income) {
    income.setDescription(description);
    income.setValue(value);
    income.setDate(date);
    return income;
  }

}
