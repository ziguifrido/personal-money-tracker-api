package dev.marcosoliveira.personalmoneytrackerapi.controller.dto;

import java.math.BigDecimal;

import dev.marcosoliveira.personalmoneytrackerapi.model.Category;
import lombok.Data;

@Data
public class ExpensePerCategoryDto {

  Category category;
  BigDecimal value;

  public ExpensePerCategoryDto(Object[] obj) {
    this.category = Category.values()[(int) obj[0]];
    this.value = (BigDecimal) obj[1];
  }

}
