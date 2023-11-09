package com.vijay.travel.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijay.travel.model.Itinerary;

public interface ItineraryRepository extends JpaRepository<Itinerary, UUID> {

}
