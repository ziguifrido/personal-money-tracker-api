package dev.marcosoliveira.personalmoneytrackerapi.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dev.marcosoliveira.personalmoneytrackerapi.controller.dto.MonthlyReportDto;

@Repository
public class ReportRepository {

  private EntityManager entityManager;

  public ReportRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public MonthlyReportDto monthlyReport(int year, int month) {
    String sqlString = "select " +
        "(select sum(value) from expense where year(date) = ?1 and month(date) = ?2) as totalExpense, " +
        "(select sum(value) from income where year(date) = ?1 and month(date) = ?2) as totalIncome, " +
        "((select sum(value) from income where year(date) = ?1 and month(date) = ?2) - (select sum(value) from expense where year(date) = ?1 and month(date) = ?2)) as finalBalance " +
        "from dual";

    Query query = entityManager.createNativeQuery(sqlString);
    query.setParameter(1, year);
    query.setParameter(2, month);

    MonthlyReportDto monthlyReportDto = new MonthlyReportDto((Object[]) query.getSingleResult());

    String sqlString2 = "select category, sum(value) from (select * from expense where year(date) = ?1 and month(date) = ?2) group by category;";

    Query query2 = entityManager.createNativeQuery(sqlString2);
    query2.setParameter(1, year);
    query2.setParameter(2, month);

    @SuppressWarnings("unchecked")
    List<Object[]> expensesPerCategorylist = (List<Object[]>) query2.getResultList();

    monthlyReportDto.loadExpensePerCategory(expensesPerCategorylist);

    return monthlyReportDto;
  }

}
