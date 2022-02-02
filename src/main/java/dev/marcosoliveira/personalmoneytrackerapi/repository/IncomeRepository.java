package dev.marcosoliveira.personalmoneytrackerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.marcosoliveira.personalmoneytrackerapi.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {
  
}
