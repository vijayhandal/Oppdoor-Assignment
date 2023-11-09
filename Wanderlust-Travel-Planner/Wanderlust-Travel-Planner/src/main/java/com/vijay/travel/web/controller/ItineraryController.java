package com.vijay.travel.web.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vijay.travel.exception.HttpMethodNotImplementedException;
import com.vijay.travel.exception.IllegalRequestException;
import com.vijay.travel.exception.ItineraryAbsentException;
import com.vijay.travel.model.Itinerary;
import com.vijay.travel.service.ItineraryService;

@RestController
@RequestMapping("itineraries")
public class ItineraryController {

	private final ItineraryService itineraryService;

	@Autowired
	public ItineraryController(ItineraryService itineraryService) {
		this.itineraryService = itineraryService;
	}

	@GetMapping("{uuid}")
	ResponseEntity<Itinerary> getItineraryById(@PathVariable("uuid") UUID uuid, Authentication authentication) {

		String username = authentication.getName();
		Optional<Itinerary> optionalItinerary = itineraryService.getItineraryById(uuid, Optional.of(username));

		if (optionalItinerary.isEmpty()) {
			throw new ItineraryAbsentException("Itinerary with uuid: " + uuid + " does not exist");
		}

		return ResponseEntity.ok(optionalItinerary.get());
	}

	@GetMapping
	ResponseEntity<List<Itinerary>> getItinerariesByUsername(
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
		Page<Itinerary> itineraryPage = itineraryService.getItinerariesByUsername(username, page, limit);

		return ResponseEntity.ok().header("X-Total-Count", String.valueOf(itineraryPage.getTotalElements()))
				.body(itineraryPage.getContent());
	}

	@PutMapping
	ResponseEntity<Itinerary> editItinerary() {

		throw new HttpMethodNotImplementedException("Update itinerary method has net been implemented yet");
	}

	@DeleteMapping("{uuid}")
	ResponseEntity<Itinerary> deleteItinerary(@PathVariable("uuid") UUID uuid, Authentication authentication) {

		String username = authentication.getName();
		Itinerary itinerary = itineraryService.deleteItinerary(uuid, Optional.of(username));

		return ResponseEntity.ok(itinerary);
	}
}
