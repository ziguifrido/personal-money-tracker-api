package dev.marcosoliveira.personalmoneytrackerapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.marcosoliveira.personalmoneytrackerapi.controller.dto.IncomeDto;
import dev.marcosoliveira.personalmoneytrackerapi.controller.form.IncomeForm;
import dev.marcosoliveira.personalmoneytrackerapi.model.Income;
import dev.marcosoliveira.personalmoneytrackerapi.repository.IncomeRepository;


@RestController
@RequestMapping("/income")
public class IncomeController {

  @Autowired
  private IncomeRepository incomeRepository;

  @PostMapping
  @Transactional
  public ResponseEntity<IncomeDto> create(@RequestBody @Valid IncomeForm form, UriComponentsBuilder uriBuilder) {
    Income income = form.convert();
    incomeRepository.save(income);

    URI uri = uriBuilder.path("/income/{id}").buildAndExpand(income.getId()).toUri();
    return ResponseEntity.created(uri).body(new IncomeDto(income));
  }

  @GetMapping
  public List<IncomeDto> list() {
    List<Income> incomeList = incomeRepository.findAll();

    return IncomeDto.convert(incomeList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<IncomeDto> detail(@PathVariable Long id) {
    Optional<Income> optional = incomeRepository.findById(id);

    if (!optional.isPresent())
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(new IncomeDto(optional.get()));
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    if (!incomeRepository.existsById(id))
      return ResponseEntity.notFound().build();

    incomeRepository.deleteById(id);

    return ResponseEntity.ok().build();
  }
  
}
