package dev.marcosoliveira.personalmoneytrackerapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import dev.marcosoliveira.personalmoneytrackerapi.model.Category;
import dev.marcosoliveira.personalmoneytrackerapi.model.Expense;
import lombok.Data;

@Data
public class ExpenseDto {

  private String description;
  private BigDecimal value;
  private LocalDateTime date;
  private Category category;

  public ExpenseDto(Expense expense) {
    this.description = expense.getDescription();
    this.value = expense.getValue();
    this.date = expense.getDate();
    this.category = expense.getCategory();
  }

  public static List<ExpenseDto> convert(List<Expense> expenseList) {
    return expenseList.stream().map(ExpenseDto::new).collect(Collectors.toList());
  }

}
