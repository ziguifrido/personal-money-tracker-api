package dev.marcosoliveira.personalmoneytrackerapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.marcosoliveira.personalmoneytrackerapi.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  public List<Expense> findByDescription(String description);

  @Query("select e from Expense e where e.description = ?1 and month(e.date) = ?2 and year(e.date) = ?3")
  public List<Expense> findByDescriptionAndMonth(String description, Integer month, Integer year);

  @Query("select e from Expense e where " +
      "e.description = ?1 and month(e.date) = ?2 and year(e.date) = ?3 and not e.id = ?4")
  public List<Expense> findByDescriptionAndMonthNotId(String description, Integer month, Integer year, Long id);

  @Query("select e from Expense e where year(e.date) = ?1 and month(e.date) = ?2")
  public List<Expense> findByMonth(int year, int month);

}
