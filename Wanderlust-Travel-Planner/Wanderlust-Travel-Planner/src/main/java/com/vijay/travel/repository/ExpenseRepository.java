package com.vijay.travel.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijay.travel.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

}
