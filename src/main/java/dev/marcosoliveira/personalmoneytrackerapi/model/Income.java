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
public class Income {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private BigDecimal value;
  private LocalDateTime date;

  public Income() {}

  public Income(String description, BigDecimal value, LocalDateTime date) {
    this.description = description;
    this.value = value;
    this.date = date;
  }
  
}
