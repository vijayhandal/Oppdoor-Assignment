package com.vijay.travel.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijay.travel.model.Destination;

public interface DestinationRepository extends JpaRepository<Destination, UUID> {

}
