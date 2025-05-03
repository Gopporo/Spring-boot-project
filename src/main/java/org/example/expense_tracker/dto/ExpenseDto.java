package org.example.expense_tracker.dto;

import org.example.expense_tracker.model.Category;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseDto {
    private String description;
    private double amount;
    private LocalDate date;
    private Category category;

    // Геттеры и сеттеры
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
