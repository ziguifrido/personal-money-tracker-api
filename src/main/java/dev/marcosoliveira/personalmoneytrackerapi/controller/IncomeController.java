package dev.marcosoliveira.personalmoneytrackerapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.marcosoliveira.personalmoneytrackerapi.controller.dto.IncomeDto;
import dev.marcosoliveira.personalmoneytrackerapi.model.Income;
import dev.marcosoliveira.personalmoneytrackerapi.repository.IncomeRepository;


@RestController
@RequestMapping("/income")
public class IncomeController {

  @Autowired
  private IncomeRepository incomeRepository;

  @GetMapping
  public List<IncomeDto> list() {
    List<Income> incomeList = incomeRepository.findAll();

    return IncomeDto.convert(incomeList);
  }
  
}
