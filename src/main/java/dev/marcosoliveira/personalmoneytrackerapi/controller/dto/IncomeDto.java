package dev.marcosoliveira.personalmoneytrackerapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import dev.marcosoliveira.personalmoneytrackerapi.model.Income;
import lombok.Data;

@Data
public class IncomeDto {

  private String description;
  private BigDecimal value;
  private LocalDateTime date;

  public IncomeDto(Income income){
    this.description = income.getDescription();
    this.value = income.getValue();
    this.date = income.getDate();
  }

  public static List<IncomeDto> convert(List<Income> incomeList) {
    return incomeList.stream().map(IncomeDto::new).collect(Collectors.toList());
  }

  
}
