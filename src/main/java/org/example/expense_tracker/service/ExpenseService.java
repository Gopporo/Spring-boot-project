package org.example.expense_tracker.service;

import org.example.expense_tracker.dto.ExpenseDto;
import org.example.expense_tracker.model.Category;
import org.example.expense_tracker.model.Expense;
import org.example.expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    @Autowired
    ExpenseRepository repository;

    public List<ExpenseDto> getAllExpenses() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ExpenseDto> getExpenseById(Long id) {
        return repository.findById(id)
                .map(this::toDto);
    }

    public ExpenseDto createExpense(ExpenseDto dto) {
        Expense saved = repository.save(toEntity(dto));
        return toDto(saved);
    }

    public ExpenseDto updateExpense(Long id, ExpenseDto dto) {
        return repository.findById(id)
                .map(exp -> {
                    exp.setDescription(dto.getDescription());
                    exp.setAmount(dto.getAmount());
                    exp.setDate(dto.getDate());
                    exp.setCategory(dto.getCategory());
                    return toDto(repository.save(exp));
                })
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public void deleteExpense(Long id) {
        repository.deleteById(id);
    }

    private ExpenseDto toDto(Expense exp) {
        ExpenseDto dto = new ExpenseDto();
        dto.setDescription(exp.getDescription());
        dto.setAmount(exp.getAmount());
        dto.setDate(exp.getDate());
        dto.setCategory(exp.getCategory());
        return dto;
    }

    private Expense toEntity(ExpenseDto dto) {
        return Expense.builder()
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .category(dto.getCategory())
                .build();
    }

    public double getTotalExpensesForMonth(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return repository.findByDateBetween(start, end)
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public List<Expense> getExpensesByCategory(Category category) {
        return repository.findByCategory(category);
    }


}
