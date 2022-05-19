package dev.marcosoliveira.personalmoneytrackerapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.marcosoliveira.personalmoneytrackerapi.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

  public List<Income> findByDescription(String description);

  @Query("select i from Income i where i.description = ?1 and month(i.date) = ?2 and year(i.date) = ?3")
  public List<Income> findByDescriptionAndMonth(String description, Integer month, Integer year);

  @Query("select i from Income i where " +
      "i.description = ?1 and month(i.date) = ?2 and year(i.date) = ?3 and not i.id = ?4")
  public List<Income> findByDescriptionAndMonthNotId(String description, Integer month, Integer year, Long id);

  @Query("select i from Income i where year(i.date) = ?1 and month(i.date) = ?2")
  public List<Income> findByMonth(int year, int month);

}
