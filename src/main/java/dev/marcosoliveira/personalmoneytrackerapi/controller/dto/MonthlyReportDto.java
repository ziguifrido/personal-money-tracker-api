package dev.marcosoliveira.personalmoneytrackerapi.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class MonthlyReportDto {

  BigDecimal totalIncome;
  BigDecimal totalExpense;
  BigDecimal finalBalance;
  List<ExpensePerCategoryDto> expensePerCategory;

  public MonthlyReportDto(Object[] obj){
    this.totalIncome = (BigDecimal) obj[0];
    this.totalExpense = (BigDecimal) obj[1];
    this.finalBalance = (BigDecimal) obj[2];
  }
  
  public void loadExpensePerCategory(List<Object[]> objList){
    this.expensePerCategory = objList.stream().map(ExpensePerCategoryDto::new).collect(Collectors.toList());
  }
}
