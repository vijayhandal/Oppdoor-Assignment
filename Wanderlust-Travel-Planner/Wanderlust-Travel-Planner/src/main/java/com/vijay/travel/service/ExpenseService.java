package com.vijay.travel.service;

import org.springframework.data.domain.Page;

import com.vijay.travel.model.Expense;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseService {

    Optional<Expense> getExpenseById(UUID uuid, Optional<String> matchUsername);

    Page<Expense> getExpensesByUsername(String username, int page, int limit);

    Page<Expense> getExpensesByAmount(BigDecimal low, BigDecimal high, Optional<String> matchUsername, int page, int limit);

    Expense createExpense(UUID itineraryId, Expense expense, Optional<String> matchUsername);

    Expense editExpense(UUID uuid, Expense expense, Optional<String> matchUsername);

}
