package com.vijay.travel.service;

import org.springframework.data.domain.Page;

import com.vijay.travel.model.Destination;

import java.util.Optional;
import java.util.UUID;

public interface DestinationService {

    Optional<Destination> getDestinationById(UUID uuid, Optional<String> matchUsername);

    Destination updateDestination(UUID uuid, Destination destination, Optional<String> matchUsername);

    Destination deleteDestination(UUID uuid, Optional<String> matchUsername);
}
