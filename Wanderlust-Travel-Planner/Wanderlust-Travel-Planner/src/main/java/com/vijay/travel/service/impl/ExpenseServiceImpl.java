package com.vijay.travel.service.impl;

import com.vijay.travel.exception.ExpenseAbsentException;
import com.vijay.travel.exception.ItineraryAbsentException;
import com.vijay.travel.model.Expense;
import com.vijay.travel.model.Itinerary;
import com.vijay.travel.repository.ExpenseRepository;
import com.vijay.travel.repository.ItineraryRepository;
import com.vijay.travel.repository.UserRepository;
import com.vijay.travel.service.ExpenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseServiceImpl extends AbstractUserDependentEntityServiceImpl<Expense, UUID> implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ItineraryRepository itineraryRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository, ItineraryRepository itineraryRepository) {

        super(userRepository);
        this.expenseRepository = expenseRepository;
        this.itineraryRepository = itineraryRepository;
    }

    @Override
    protected String extractUsernameFromEntity(Expense expense) {

        return expense.getItinerary().getDestination().getUser().getUsername();
    }

    @Override
    public Optional<Expense> getExpenseById(UUID uuid, Optional<String> matchUsername) {

        Assert.notNull(uuid, "UUID cannot be null");
        Assert.notNull(matchUsername, "Optional username cannot be null");

        return super.getEntityById(uuid, matchUsername, expenseRepository);
    }




    @Override
    public Expense createExpense(UUID itineraryId, Expense expense, Optional<String> matchUsername) {

        Assert.notNull(itineraryId, "itinerary uuid cannot be null");
        Assert.notNull(expense, "Expense cannot be null");
        Assert.notNull(matchUsername, "Optional username cannot be null");

        String itineraryAbsentErrMsg = "Itinerary with UUID: " + itineraryId + " does not exist";
        Itinerary fetchedItinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new ItineraryAbsentException(itineraryAbsentErrMsg));

        if (matchUsername.isPresent()) {

            String provided = matchUsername.get();
            String actual = fetchedItinerary.getDestination().getUser().getUsername();

            if (!provided.equals(actual)) {
                throw new ItineraryAbsentException(itineraryAbsentErrMsg);
            }
        }

        expense
                .setUuid(null)
                .setItinerary(fetchedItinerary);

        return expenseRepository.save(expense);
    }

    @Override
    public Expense editExpense(UUID uuid, Expense expense, Optional<String> matchUsername) {

        throw new RuntimeException("Method not implemented");
    }

	@Override
	public Page<Expense> getExpensesByUsername(String username, int page, int limit) {
		return null;
	}

	@Override
	public Page<Expense> getExpensesByAmount(BigDecimal low, BigDecimal high, Optional<String> matchUsername, int page,
			int limit) {
		return null;
	}

}
