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

import dev.marcosoliveira.personalmoneytrackerapi.controller.dto.ExpenseDto;
import dev.marcosoliveira.personalmoneytrackerapi.controller.form.ExpenseForm;
import dev.marcosoliveira.personalmoneytrackerapi.model.Expense;
import dev.marcosoliveira.personalmoneytrackerapi.repository.ExpenseRepository;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

  @Autowired
  private ExpenseRepository expenseRepository;

  // --------------------------------------- GET ---------------------------------------

  @GetMapping
  public Page<ExpenseDto> list(@RequestParam(required = false) String description,
      @PageableDefault(sort = "date", direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {

    Page<Expense> expenseList = description != null 
        ? expenseRepository.findByDescription(description, pageable)
        : expenseRepository.findAll(pageable);

    return ExpenseDto.convertPage(expenseList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseDto> detail(@PathVariable Long id) {
    Optional<Expense> optional = expenseRepository.findById(id);

    if (!optional.isPresent())
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(new ExpenseDto(optional.get()));
  }

  @GetMapping("/{year}/{month}")
  public Page<ExpenseDto> listMonth(@PathVariable int year, @PathVariable int month,
    @PageableDefault(sort = "date", direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {
      
    Page<Expense> expenseList = expenseRepository.findByMonth(year, month, pageable);

    return ExpenseDto.convertPage(expenseList);
  }

  // --------------------------------------- POST ---------------------------------------
  @PostMapping
  @Transactional
  public ResponseEntity<ExpenseDto> create(@RequestBody @Valid ExpenseForm form, UriComponentsBuilder uriBuilder) {
    Expense expense = form.convert();

    List<Expense> list = expenseRepository.findByDescriptionAndMonth(
        expense.getDescription(),
        expense.getDate().getMonthValue(),
        expense.getDate().getYear());

    if (!list.isEmpty())
      return ResponseEntity.unprocessableEntity().build();

    expenseRepository.save(expense);

    URI uri = uriBuilder.path("/expense/{id}").buildAndExpand(expense.getId()).toUri();
    return ResponseEntity.created(uri).body(new ExpenseDto(expense));
  }

  // --------------------------------------- PUT ---------------------------------------
  @Transactional
  @PutMapping("/{id}")
  public ResponseEntity<ExpenseDto> update(@PathVariable Long id, @RequestBody @Valid ExpenseForm form) {
    List<Expense> list = expenseRepository.findByDescriptionAndMonthNotId(
        form.getDescription(),
        form.getDate().getMonthValue(),
        form.getDate().getYear(),
        id);

    if (!list.isEmpty())
      return ResponseEntity.unprocessableEntity().build();

    Optional<Expense> optional = expenseRepository.findById(id);

    if (!optional.isPresent())
      return ResponseEntity.notFound().build();

    Expense expense = form.update(optional.get());

    return ResponseEntity.ok(new ExpenseDto(expense));
  }

  // --------------------------------------- DELETE ---------------------------------------
  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    if (!expenseRepository.existsById(id))
      return ResponseEntity.notFound().build();

    expenseRepository.deleteById(id);

    return ResponseEntity.ok().build();
  }

}
