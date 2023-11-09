package com.vijay.travel.web.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vijay.travel.exception.ExpenseAbsentException;
import com.vijay.travel.exception.HttpMethodNotImplementedException;
import com.vijay.travel.exception.IllegalRequestException;
import com.vijay.travel.model.Expense;
import com.vijay.travel.service.ExpenseService;

@RestController
@RequestMapping("expenses")
public class ExpenseController {

	private final ExpenseService expenseService;

	@Autowired
	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@GetMapping("{uuid}")
	ResponseEntity<Expense> getExpenseById(@PathVariable("uuid") UUID uuid, Authentication authentication) {

		String username = authentication.getName();
		Optional<Expense> optionalExpense = expenseService.getExpenseById(uuid, Optional.of(username));

		if (optionalExpense.isEmpty()) {
			throw new ExpenseAbsentException("Expense with uuid: " + uuid + " does not exist");
		}

		return ResponseEntity.ok(optionalExpense.get());
	}

	@GetMapping
	ResponseEntity<List<Expense>> getExpenses(@RequestParam(value = "low", required = false) BigDecimal low,
			@RequestParam(value = "high", required = false) BigDecimal high,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
			Authentication authentication) {

		if (page < 1) {
			throw new IllegalRequestException("Page number must be greater than or equal to 1");
		}
		if (limit < 1) {
			throw new IllegalRequestException("Page limit must be greater than or equal to 1");
		}

		String username = authentication.getName();
		Page<Expense> expensePage;

		if (low != null && high != null) {
			if (low.compareTo(high) > 0) {
				throw new IllegalRequestException(
						"The lower expense amount limit should be lesser or equal to than higher limit");
			} else {
				expensePage = expenseService.getExpensesByAmount(low, high, Optional.of(username), page, limit);
			}
		} else {
			expensePage = expenseService.getExpensesByUsername(username, page, limit);
		}

		return ResponseEntity.ok().header("X-Total-Count", String.valueOf(expensePage.getTotalElements()))
				.body(expensePage.getContent());
	}

	@PutMapping
	ResponseEntity<Expense> editExpense() {

		throw new HttpMethodNotImplementedException("Update itinerary method has net been implemented yet");
	}
}
