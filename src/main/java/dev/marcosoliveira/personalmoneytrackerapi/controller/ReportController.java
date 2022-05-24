package dev.marcosoliveira.personalmoneytrackerapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.marcosoliveira.personalmoneytrackerapi.controller.dto.MonthlyReportDto;
import dev.marcosoliveira.personalmoneytrackerapi.repository.ReportRepository;

@RestController
@RequestMapping("/report")
public class ReportController {
  
  private ReportRepository reportRepository;  

  public ReportController(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  @GetMapping("/{year}/{month}")
  public MonthlyReportDto monthlyReport(@PathVariable int year, @PathVariable int month){
    return reportRepository.monthlyReport(year, month);
  }

}
