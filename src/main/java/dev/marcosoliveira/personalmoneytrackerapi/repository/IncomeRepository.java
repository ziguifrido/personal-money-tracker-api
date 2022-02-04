package dev.marcosoliveira.personalmoneytrackerapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.marcosoliveira.personalmoneytrackerapi.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {
  
  @Query("select i from Income i where i.description = ?1 and month(i.date) = ?2 and year(i.date) = ?3")
  public List<Income> findByDescriptionAndMonth(String description, Integer month, Integer year);

}
