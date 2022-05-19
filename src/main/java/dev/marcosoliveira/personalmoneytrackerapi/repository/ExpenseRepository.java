package dev.marcosoliveira.personalmoneytrackerapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.marcosoliveira.personalmoneytrackerapi.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  @Query("select i from Expense i where i.description = ?1 and month(i.date) = ?2 and year(i.date) = ?3")
  public List<Expense> findByDescriptionAndMonth(String description, Integer month, Integer year);

  @Query("select i from Expense i where " +
      "i.description = ?1 and month(i.date) = ?2 and year(i.date) = ?3 and not i.id = ?4")
  public List<Expense> findByDescriptionAndMonthNotId(String description, Integer month, Integer year, Long id);

  public List<Expense> findByDescription(String description);

}
