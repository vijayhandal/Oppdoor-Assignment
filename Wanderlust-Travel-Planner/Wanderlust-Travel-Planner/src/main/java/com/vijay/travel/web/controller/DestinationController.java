package com.vijay.travel.web.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vijay.travel.exception.DestinationAbsentException;
import com.vijay.travel.model.Destination;
import com.vijay.travel.service.DestinationService;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

	@Autowired
	private DestinationService destinationService;

	@GetMapping("/{uuid}")
	ResponseEntity<Destination> getDestinationById(@PathVariable("uuid") UUID uuid, Authentication authentication) {

		String username = authentication.getName();
		Optional<Destination> optionalDestination = destinationService.getDestinationById(uuid, Optional.of(username));

		if (optionalDestination.isEmpty()) {
			throw new DestinationAbsentException(uuid + " doen't exist");
		}

		return ResponseEntity.ok(optionalDestination.get());
	}

	@DeleteMapping("/{uuid}")
	ResponseEntity<Destination> deleteDestination(@PathVariable("uuid") UUID uuid, Authentication authentication) {

		String username = authentication.getName();
		Destination destination = destinationService.deleteDestination(uuid, Optional.of(username));

		return ResponseEntity.ok(destination);
	}
}
