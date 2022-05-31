package dev.marcosoliveira.personalmoneytrackerapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  // --------------------------------------- GET ---------------------------------------
  @GetMapping
  public Page<IncomeDto> list(@RequestParam(required = false) String description,
    @PageableDefault(sort = "date", direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {

    Page<Income> incomeList = description != null ? 
      incomeRepository.findByDescription(description, pageable) : 
      incomeRepository.findAll(pageable);

    return IncomeDto.convertPage(incomeList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<IncomeDto> detail(@PathVariable Long id) {
    Optional<Income> optional = incomeRepository.findById(id);

    if (!optional.isPresent())
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(new IncomeDto(optional.get()));
  }

  @GetMapping("/{year}/{month}")
  public Page<IncomeDto> listMonth(@PathVariable int year, @PathVariable int month,
    @PageableDefault(sort = "date", direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {
    
    Page<Income> incomeList = incomeRepository.findByMonth(year, month, pageable);
    
    return IncomeDto.convertPage(incomeList);
  }

  // --------------------------------------- POST ---------------------------------------
  @PostMapping
  @Transactional
  public ResponseEntity<IncomeDto> create(@RequestBody @Valid IncomeForm form, UriComponentsBuilder uriBuilder) {
    Income income = form.convert();

    List<Income> list = incomeRepository.findByDescriptionAndMonth(
      income.getDescription(),
      income.getDate().getMonthValue(),
      income.getDate().getYear());

    if (!list.isEmpty())
      return ResponseEntity.unprocessableEntity().build();
    
    incomeRepository.save(income);

    URI uri = uriBuilder.path("/income/{id}").buildAndExpand(income.getId()).toUri();
    return ResponseEntity.created(uri).body(new IncomeDto(income));
  }

  // --------------------------------------- PUT ---------------------------------------
  @Transactional
  @PutMapping("/{id}")
  public ResponseEntity<IncomeDto> update(@PathVariable Long id, @RequestBody @Valid IncomeForm form){
    List<Income> list = incomeRepository.findByDescriptionAndMonthNotId(
      form.getDescription(),
      form.getDate().getMonthValue(),
      form.getDate().getYear(),
      id);

    if (!list.isEmpty())
      return ResponseEntity.unprocessableEntity().build();

    Optional<Income> optional = incomeRepository.findById(id);

    if (!optional.isPresent())
      return ResponseEntity.notFound().build();

    Income income = form.update(optional.get());

    return ResponseEntity.ok(new IncomeDto(income));
  }

  // --------------------------------------- DELETE ---------------------------------------
  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    if (!incomeRepository.existsById(id))
      return ResponseEntity.notFound().build();

    incomeRepository.deleteById(id);

    return ResponseEntity.ok().build();
  }
  
}
