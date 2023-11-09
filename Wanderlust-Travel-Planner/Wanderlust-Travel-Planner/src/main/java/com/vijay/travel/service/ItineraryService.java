package com.vijay.travel.service;

import org.springframework.data.domain.Page;

import com.vijay.travel.model.Itinerary;

import java.util.Optional;
import java.util.UUID;

public interface ItineraryService {

    Optional<Itinerary> getItineraryById(UUID uuid, Optional<String> matchUsername);

    Page<Itinerary> getItinerariesByUsername(String username, int page, int limit);

    Itinerary createItinerary(UUID destinationId, Itinerary itinerary, Optional<String> matchUsername);

    Itinerary editItinerary(UUID uuid, Itinerary itinerary, Optional<String> matchUsername);

    Itinerary deleteItinerary(UUID uuid, Optional<String> matchUsername);
}
