package dev.marcosoliveira.personalmoneytrackerapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private BigDecimal value;
  private LocalDateTime date;
  private Category category;

  public Expense() {
  }

  public Expense(String description, BigDecimal value, LocalDateTime date, Category category) {
    this.description = description;
    this.value = value;
    this.date = date;
    this.category = category == null ? Category.OTHER : category;
  }

}
