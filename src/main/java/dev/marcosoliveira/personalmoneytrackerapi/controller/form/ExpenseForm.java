package dev.marcosoliveira.personalmoneytrackerapi.controller.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import dev.marcosoliveira.personalmoneytrackerapi.model.Category;
import dev.marcosoliveira.personalmoneytrackerapi.model.Expense;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExpenseForm {
  
  @NotNull @NotEmpty
  private String description;
  @NotNull 
  private BigDecimal value;
  @NotNull 
  private LocalDateTime date;
  private Category category;

  public Expense convert() {
    return new Expense(description, value, date, category);
  }

  public Expense update(Expense expense) {
    expense.setDescription(description);
    expense.setValue(value);
    expense.setDate(date);
    expense.setCategory(category);
    return expense;
  }

}
