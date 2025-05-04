package org.example.expense_tracker.service;

import org.example.expense_tracker.model.Expense;
import org.example.expense_tracker.model.Category;
import org.example.expense_tracker.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExpenseServiceTest {

    @InjectMocks
    private ExpenseService expenseService;

    @Mock
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTotalExpensesForMonth() {
        // Данные для теста
        Expense expense1 = new Expense("Groceries", 50.0, LocalDate.of(2025, 5, 10), Category.FOOD);
        Expense expense2 = new Expense("Fuel", 30.0, LocalDate.of(2025, 5, 15), Category.TRANSPORT);
        List<Expense> expenses = Arrays.asList(expense1, expense2);

        // Мокаем поведение репозитория
        when(expenseRepository.findByDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(expenses);

        // Тестируем метод
        double total = expenseService.getTotalExpensesForMonth(2025, 5);
        assertEquals(80.0, total, 0.001); // delta для double-сравнения
    }

    @Test
    void getExpensesByCategory() {
        Expense expense = new Expense("Groceries", 50.0, LocalDate.of(2025, 5, 10), Category.FOOD);
        List<Expense> expenses = Arrays.asList(expense);

        // Мокаем поведение репозитория
        when(expenseRepository.findByCategory(Category.FOOD)).thenReturn(expenses);

        // Тестируем метод
        List<Expense> result = expenseService.getExpensesByCategory(Category.FOOD);
        assertFalse(result.isEmpty());
        assertEquals(Category.FOOD, result.get(0).getCategory());
    }
}
